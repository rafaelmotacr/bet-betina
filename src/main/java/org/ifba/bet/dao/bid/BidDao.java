package org.ifba.bet.dao.bid;

import java.sql.SQLException;
import java.util.ArrayList;

import org.ifba.bet.model.Bid;

public interface BidDao {
	// INSERT INTO bid_tb (bid_guess, bet_id, match_id, bid_paid_value)
	public void insertBid(int betId, int matchId, int guess, Double paidValue) throws SQLException;

	public void deleteBid(Bid bid) throws SQLException;

	public void updateBid(Bid bid) throws SQLException;

	public ArrayList<Bid> getAllBids(int betId) throws SQLException;

}
