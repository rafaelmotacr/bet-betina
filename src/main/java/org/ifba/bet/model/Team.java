package org.ifba.bet.model;

public class Team {

	private int ID;
	private String name;
	private String abbreviation;

	public Team(int iD, String name, String abbreviation) {
		super();
		ID = iD;
		this.name = name;
		this.abbreviation = abbreviation;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	@Override
	public String toString() {
		return name + " - [" + abbreviation + "]";
	}

}
