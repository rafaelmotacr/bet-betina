package org.ifba.bet.dao.bet;

import java.sql.SQLException;
import java.util.ArrayList;

import org.ifba.bet.model.Bid;

public interface BetDao {

	public int insertBet(int userId, int betState) throws SQLException;

	public void deleteBet(int betId) throws SQLException;

	public void updateBetsState(int newState, int matchId) throws SQLException;

	public void updateBetState(int newState, int userId, int betId);

	public ArrayList<Bid> getAllBids(int betId) throws SQLException;

	public int getTotalBids(int betId);

	public int getCorrectBids(int betId);

	public double getBetTotalValue(int betId);

	public boolean isBetCompleted(int betId);

	public boolean isBetCorrect(int betId);

	public double getBetPayout(int betId);

}
