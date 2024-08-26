package org.ifba.bet.model;

public class Bet {

	// Atributos de instância
	
	private int id;
	private int state;
	private int userID;
	
	// Atributos estáticos
	
	public static final int CLOSED = 0;
	public static final int OPEN = 1;
	public static final int WIN = 2;
	public static final int LOSE = 3;

	public Bet(int id, int state, int userID) {
		super();
		this.id = id;
		this.state = state;
		this.userID = userID;
	}

	public int getId() {
		return id;
	}

	public void setId(int iD) {
		id = iD;
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
