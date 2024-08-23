package org.ifba.bet.dao.bet;

import java.sql.SQLException;
import java.util.ArrayList;

import org.ifba.bet.model.Bid;

public interface BetDao {

	public int insertBet(int userId, int betState) throws SQLException;

	public void deleteBet(int betId) throws SQLException;

	public ArrayList<Bid> getAllBids(int betId) throws SQLException;

}
