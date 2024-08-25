package org.ifba.bet.model;

public class Bet {

	private int ID;
	private int state;
	private int userID;
	
	public static final int CLOSED = 0;
	public static final int OPEN = 1;
	public static final int WIN = 2;
	public static final int LOSE = 3;

	public Bet(int iD, int state, int userID) {
		super();
		ID = iD;
		this.state = state;
		this.userID = userID;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

}
