package model;

public class Match {
	
	private int ID;
	private int state;
	private Double ODD;
	private int result;
	private int homeTeamId;
	private int awayTeamId;
	
	public Match(int iD, int state, Double oDD, int result, int homeTeamId, int awayTeamId) {
		super();
		ID = iD;
		this.state = state;
		ODD = oDD;
		this.result = result;
		this.homeTeamId = homeTeamId;
		this.awayTeamId = awayTeamId;
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

	public Double getODD() {
		return ODD;
	}

	public void setODD(Double oDD) {
		ODD = oDD;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getHomeTeamId() {
		return homeTeamId;
	}

	public void setHomeTeamId(int homeTeamId) {
		this.homeTeamId = homeTeamId;
	}

	public int getAwayTeamId() {
		return awayTeamId;
	}

	public void setAwayTeamId(int awayTeamId) {
		this.awayTeamId = awayTeamId;
	}
	
}
