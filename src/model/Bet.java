package model;

public class Bet {
	
	private int ID;
	private int state;
	private int userID;
	
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
