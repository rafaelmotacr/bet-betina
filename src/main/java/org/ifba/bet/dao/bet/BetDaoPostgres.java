package org.ifba.bet.dao.bet;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.ifba.bet.dao.connection.DatabaseConnectionSingleton;
import org.ifba.bet.model.Bet;
import org.ifba.bet.model.Bid;

public class BetDaoPostgres implements BetDao {

	@Override
	public int insertBet(int userId, int betState) throws SQLException {
		Connection conn = DatabaseConnectionSingleton.getInstance().getConexao();
		String sql = "INSERT INTO bet_tb (user_id, bet_state) VALUES (?, ?)";
		PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, userId);
		ps.setInt(2, betState);
		ps.executeUpdate();
		ResultSet getBetId = ps.getGeneratedKeys();
		getBetId.next();
		return getBetId.getInt(1);
	}

	@Override
	public void deleteBet(int betId) throws SQLException {
		String sql = "DELETE FROM bet_tb WHERE bet_id = ?";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConexao();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, betId);
		ps.executeUpdate();
	}

	@Override
	public ArrayList<Bid> getAllBids(int betId) throws SQLException {
		String sql = "SELECT * FROM bid_tb \r\n" + "WHERE bet_id = ?";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConexao();
		ArrayList<Bid> bidArray = new ArrayList<Bid>();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, betId);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			// public Bid(double paidValue, int guess, int betId, int matchID)
			bidArray.add(new Bid(rs.getDouble("bid_paid_value"), rs.getInt("bid_guess"), rs.getInt("bet_id"),
					rs.getInt("match_id")));
		}
		return bidArray;
	}

	@Override
	public void updateBetsState(int newState, int matchId) throws SQLException {
		String sql = "update bet_tb set bet_state = ?\r\n" + "	where bet_id in\r\n"
				+ "	(select bet_id from bid_tb\r\n" + "where match_id = ?)";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConexao();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, newState);
		ps.setInt(2, matchId);
		ps.executeUpdate();
	}

	@Override
	public ArrayList<Bet> getAllBets(int user_id) throws SQLException {
		String sql = "SELECT * FROM bet_tb \r\n" + "WHERE user_id = ?";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConexao();
		ArrayList<Bet> betArray = new ArrayList<Bet>();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, user_id);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			betArray.add(new Bet(rs.getInt("bet_id"), rs.getInt("bet_state"), rs.getInt("user_id")));
		}
		return betArray;
	}

	@Override
	public int getTotalBids(int betId) {
		String sql = "SELECT COUNT (*) AS total_bids\r\n" + "FROM bid_tb WHERE bet_id = ?";
		int totalBids = 0;
		try {
			Connection conn = DatabaseConnectionSingleton.getInstance().getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, betId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			totalBids = rs.getInt("total_bids");
		} catch (SQLException e) {
			System.out.println("Erro Ao Consultar o Total de Lances");
		}
		return totalBids;
	}

	@Override
	public int getCorrectBids(int betId) {
		int totalCorrectBids = 0;
		String sql = "SELECT COUNT(*) AS correct_bids\r\n" + "FROM match_tb \r\n" + "INNER JOIN (\r\n"
				+ "    SELECT match_id, bid_guess\r\n" + "    FROM bid_tb \r\n" + "    WHERE bet_id = ?\r\n"
				+ ") AS bids_tb\r\n" + "ON bids_tb.match_id = match_tb.match_id\r\n"
				+ "WHERE bids_tb.bid_guess = match_tb.match_result;";
		try {
			Connection conn = DatabaseConnectionSingleton.getInstance().getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, betId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			totalCorrectBids = rs.getInt("correct_bids");
		} catch (SQLException e) {
			System.out.println("Erro Ao Consultar o Total de Lances");
		}

		return totalCorrectBids;
	}

	@Override
	public double getBetTotalValue(int betId) {
		double totalBetValue = 0;
		String sql = "SELECT SUM total_bet_value(bid_tb.bid_paid_value)\r\n" + "FROM bid_tb WHERE bet_id = ?";
		try {
			Connection conn = DatabaseConnectionSingleton.getInstance().getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, betId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			totalBetValue = rs.getDouble("total_bet_value");
		} catch (SQLException e) {
			System.out.println("Erro Ao Consultar o custo total da aposta");
		}

		return totalBetValue;
	}

	@Override
	public boolean isBetCompleted(int betId) {
		String sql = "SELECT EXISTS (" + "    SELECT 1 " + "    FROM match_tb " + "    INNER JOIN ( "
				+ "        SELECT match_id " + "        FROM bid_tb " + "        WHERE bet_id = ? "
				+ "    ) AS bids_tb " + "    ON match_tb.match_id = bids_tb.match_id "
				+ "    WHERE match_tb.match_state != 0 " + ")";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConexao();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, betId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return !rs.getBoolean(1);
			}

		} catch (SQLException e) {
			System.out.println("Erro ao Buscar Sua Conta no Banco de Dados.");
		}
		return false;
	}

	@Override
	public boolean isBetCorrect(int betId) {
		return getTotalBids(betId) == getCorrectBids(betId);
	}

	@Override
	public double getBetPayout(int betId) {
		double betPayout = 0;
		String sql = "SELECT \r\n"
				+ "    SUM(bid_tb.bid_paid_value * \r\n"
				+ "        CASE \r\n"
				+ "            WHEN bid_tb.bid_guess = 1 THEN match_tb.match_home_odd\r\n"
				+ "            WHEN bid_tb.bid_guess = 2 THEN match_tb.match_away_odd\r\n"
				+ "            WHEN bid_tb.bid_guess = 3 THEN match_tb.match_draw_odd\r\n"
				+ "            ELSE 0\r\n"
				+ "        END\r\n"
				+ "    ) AS total_payout\r\n"
				+ "FROM \r\n"
				+ "    bid_tb\r\n"
				+ "JOIN \r\n"
				+ "    match_tb \r\n"
				+ "    ON bid_tb.match_id = match_tb.match_id\r\n"
				+ "WHERE \r\n"
				+ "    bid_tb.bet_id = ?;";
		try {
			Connection conn = DatabaseConnectionSingleton.getInstance().getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, betId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			betPayout = rs.getDouble("total_payout");
		} catch (SQLException e) {
			System.out.println("Erro Ao Consultar o custo total da aposta");
		}
		
		BigDecimal bigDecimal = new BigDecimal(betPayout);
		bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);

		return bigDecimal.doubleValue();
	}

	@Override
	public void updateBetState(int newState, int userId, int betId) {
		String sql = "UPDATE bet_tb SET bet_state = ?\r\n"
				+ "WHERE user_id = ? AND bet_id = ?";
		try {
			Connection conn = DatabaseConnectionSingleton.getInstance().getConexao();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, newState);
			ps.setInt(2, userId);
			ps.setInt(3, betId);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
