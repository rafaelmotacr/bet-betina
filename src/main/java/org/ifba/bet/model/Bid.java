package org.ifba.bet.model;

public class Bid {

	private Double paidValue;
	private int guess;
	private int betID;
	private int matchID;

	public Bid(double paidValue, int guess, int matchID) {
		super();
		this.paidValue = paidValue;
		this.guess = guess;
		this.matchID = matchID;
	}

	public Bid(double paidValue, int guess, int betId, int matchID) {
		super();
		this.paidValue = paidValue;
		this.guess = guess;
		this.betID = betId;
		this.matchID = matchID;
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

	public int getBetID() {
		return betID;
	}

	public void setBetID(int betID) {
		this.betID = betID;
	}

	public int getMatchID() {
		return matchID;
	}

	public void setMatchID(int matchID) {
		this.matchID = matchID;
	}

}
