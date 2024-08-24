package org.ifba.bet.dao.bid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.ifba.bet.dao.connection.DatabaseConnectionSingleton;
import org.ifba.bet.model.Bid;

public class BidDaoPostgres implements BidDao {

	@Override
	public void insertBid(int betId, int matchId, int guess, Double paidValue) throws SQLException {
		String sql = "INSERT INTO bid_tb (bet_id, match_id, bid_guess, bid_paid_value) VALUES (?, ?, ?, ?);";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConexao();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, betId);
		ps.setInt(2, matchId);
		ps.setInt(3, guess);
		ps.setDouble(4, paidValue);
		ps.executeUpdate();
	}

	@Override
	public ArrayList<Bid> getAllBids(int betId) throws SQLException {
		String sql = "SELECT * FROM bid_tb WHERE bet_id = ?;";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConexao();
		ArrayList<Bid> bidArray = new ArrayList<Bid>();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, betId);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			// public Bid(double paidValue, int guess, int matchID)
			bidArray.add(new Bid(rs.getDouble("bid_paid_value"), rs.getInt("bid_guess"), rs.getInt("match_id")));
		}
		return bidArray;
	}

	@Override
	public void deleteBid(Bid bid) throws SQLException {
		String sql = "	DELETE FROM bid_tb WHERE bet_id = ? AND match_id = ?;";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConexao();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, bid.getBetID());
		ps.setInt(2, bid.getMatchID());
		ps.executeUpdate();
	}

	@Override
	public void updateBid(Bid bid) throws SQLException {
		String sql = "UPDATE bid_tb SET bid_guess = ?, bid_paid_value = ? WHERE bet_id = ? AND match_id = ?;";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConexao();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, bid.getGuess());
		ps.setDouble(2, bid.getPaidValue());
		ps.setInt(3, bid.getBetID());
		ps.setInt(4, bid.getMatchID());
		ps.executeUpdate();
	}
}
