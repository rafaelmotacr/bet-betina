package org.ifba.bet.model;

public class Bid {

	private Double paidValue;
	private int guess;
	private int betId;
	private int matchId;

	public static final int GUESS_TO_HOME_WIN = 1;
	public static final int GUESS_TO_AWAY_WIN = 2;
	public static final int GUESS_TO_DRAW = 3;
	
	public Bid(double paidValue, int guess, int matchId) {
		super();
		this.paidValue = paidValue;
		this.guess = guess;
		this.matchId = matchId;
	}

	public Bid(double paidValue, int guess, int betId, int matchId) {
		super();
		this.paidValue = paidValue;
		this.guess = guess;
		this.betId = betId;
		this.matchId = matchId;
	}

	public double getPaidValue() {
		return paidValue;
	}

	public void setPaidValue(double paidValue) {
		this.paidValue = paidValue;
	}

	public int getGuess() {
		return guess;
	}

	public void setGuess(int guess) {
		this.guess = guess;
	}

	public int getBetId() {
		return betId;
	}

	public void setBetId(int betId) {
		this.betId = betId;
	}

	public int getMatchId() {
		return matchId;
	}

	public void setMatchdId(int matchId) {
		this.matchId = matchId;
	}

}
