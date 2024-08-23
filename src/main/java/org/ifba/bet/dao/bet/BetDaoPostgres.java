package org.ifba.bet.dao.bet;

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

}
