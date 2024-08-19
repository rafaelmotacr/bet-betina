package model;

public class Match {
	
	private int id;
	private int state;
	private Double odd;
	private int result;
	private String homeTeamName;
	private String awayTeamName;
	
	public Match(int id, int state, Double odd, String homeTeamName, String awayTeamName) {
		super();
		this.id = id;
		this.state = state;
		this.odd = odd;
		this.homeTeamName = homeTeamName;
		this.awayTeamName = awayTeamName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Double getOdd() {
		return odd;
	}

	public void setOdd(Double odd) {
		this.odd = odd;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getHomeTeamName() {
		return homeTeamName;
	}

	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}

	public String getAwayTeamName() {
		return awayTeamName;
	}

	public void setAwayTeamName(String awayTeamName) {
		this.awayTeamName = awayTeamName;
	}

	@Override
	public String toString() {
		return  "ODD:" + odd + " - " + homeTeamName + " x " + awayTeamName;
	}

}
