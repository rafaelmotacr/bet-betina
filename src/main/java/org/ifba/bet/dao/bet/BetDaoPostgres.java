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
import org.ifba.bet.model.Bid;

public class BetDaoPostgres implements BetDao {

	@Override
	public int insertBet(int userId, int betState) throws SQLException {
		String sql = "INSERT INTO bet_tb (user_id, bet_state) VALUES (?, ?);";
		int id = 0;
		Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, userId);
		ps.setInt(2, betState);
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		id = rs.getInt(1);
	    ps.close();
	    rs.close();
		return id;
	}

	@Override
	public void deleteBet(int betId) throws SQLException {
		String sql = "DELETE FROM bet_tb WHERE bet_id = ?;";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, betId);
		ps.executeUpdate();
	    ps.close();
	}

	@Override
	public ArrayList<Bid> getAllBids(int betId) throws SQLException {
		String sql = "SELECT * FROM bid_tb WHERE bet_id = ?;";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
		ArrayList<Bid> bidArray = new ArrayList<Bid>();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, betId);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			bidArray.add(new Bid(rs.getDouble("bid_paid_value"), rs.getInt("bid_guess"), rs.getInt("bet_id"),
					rs.getInt("match_id")));
		}
	    ps.close();
	    rs.close();
		return bidArray;
	}

	@Override
	public void updateBetsState(int newState, int matchId) throws SQLException {
		String sql = "UPDATE bet_tb SET bet_state = ? WHERE bet_id IN"
				+ "	(SELECT bet_id FROM bid_tb WHERE match_id = ?);";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, newState);
		ps.setInt(2, matchId);
		ps.executeUpdate();
	}

	@Override
	public int getTotalBids(int betId) {
		String sql = "SELECT COUNT (*) AS total_bids FROM bid_tb WHERE bet_id = ?;";
		int totalBids = 0;
		try {
			Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, betId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			totalBids = rs.getInt("total_bids");
		    ps.close();
		    rs.close();
		} catch (SQLException e) {
			System.out.println("Erro Ao Consultar o Total de Lances");
		}
		return totalBids;
	}

	@Override
	public int getCorrectBids(int betId) {
		String sql = "SELECT COUNT(*) AS correct_bids\r\n" + "FROM match_tb \r\n" + "INNER JOIN (\r\n"
				+ "    SELECT match_id, bid_guess\r\n" + "    FROM bid_tb \r\n" + "    WHERE bet_id = ?\r\n"
				+ ") AS bids_tb\r\n" + "ON bids_tb.match_id = match_tb.match_id\r\n"
				+ "WHERE bids_tb.bid_guess = match_tb.match_result;";
		int totalCorrectBids = 0;
		try {
			Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, betId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			totalCorrectBids = rs.getInt("correct_bids");
		    ps.close();
		    rs.close();
		} catch (SQLException e) {
			System.out.println("Erro Ao Consultar o Total de Lances corretos;");
		}
		return totalCorrectBids;
	}

	@Override
	public double getBetTotalValue(int betId) {
		double totalBetValue = 0;
		String sql = "SELECT SUM (bid_tb.bid_paid_value) AS total_bet_value"
				+ "	FROM bid_tb WHERE bet_id = ?;";
		try {
			Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, betId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			totalBetValue = rs.getDouble("total_bet_value");
			ps.close();
			rs.close();
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
				+ "    WHERE match_tb.match_state != 0 " + ");";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, betId);
			ResultSet rs = ps.executeQuery();
			boolean result; 
			if (rs.next()) {
				result =  rs.getBoolean(1);
				ps.close();
				rs.close();
				return !result;
			}
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
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
				+ "            WHEN bid_tb.bid_guess = 1 THEN match_tb.match_odd_home\r\n"
				+ "            WHEN bid_tb.bid_guess = 2 THEN match_tb.match_odd_away\r\n"
				+ "            WHEN bid_tb.bid_guess = 3 THEN match_tb.match_odd_draw\r\n"
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
			Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, betId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			betPayout = rs.getDouble("total_payout");
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Erro Ao Consultar o custo total da aposta");
		}
		BigDecimal bigDecimal = new BigDecimal(betPayout);
		bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
		return bigDecimal.doubleValue();
	}

	@Override
	public void updateBetState(int newState, int userId, int betId) {
		String sql = "UPDATE bet_tb SET bet_state = ? "
				+ "WHERE user_id = ? AND bet_id = ?;";
		try {
			Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, newState);
			ps.setInt(2, userId);
			ps.setInt(3, betId);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
