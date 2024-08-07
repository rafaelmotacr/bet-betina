package model;

public class Bid {
	
	private int ID;
	private Double paidValue;
	private int guess;
	private int betID;
	private int matchID;
	
	public Bid(int iD, double paidValue, int guess, int betID, int matchID) {
		super();
		ID = iD;
		this.paidValue = paidValue;
		this.guess = guess;
		this.betID = betID;
		this.matchID = matchID;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
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
