package model;

public class Match {
	
	private int id;
	private int state;
	private int result;
	private Double homeTeamOdd;
	private Double awayTeamOdd;
	private Double drawOdd;
	private String homeTeamName;
	private String awayTeamName;
	
	public Match(int id, int state, Double homeTeamOdd, Double awayTeamOdd, Double drawOdd,
			String homeTeamName, String awayTeamName) {
		super();
		this.id = id;
		this.state = state;
		this.homeTeamOdd = homeTeamOdd;
		this.awayTeamOdd = awayTeamOdd;
		this.drawOdd = drawOdd;
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

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public Double getHomeTeamOdd() {
		return homeTeamOdd;
	}

	public void setHomeTeamOdd(Double homeTeamOdd) {
		this.homeTeamOdd = homeTeamOdd;
	}

	public Double getAwayTeamOdd() {
		return awayTeamOdd;
	}

	public void setAwayTeamOdd(Double awayTeamOdd) {
		this.awayTeamOdd = awayTeamOdd;
	}

	public Double getDrawOdd() {
		return drawOdd;
	}

	public void setDrawOdd(Double drawOdd) {
		this.drawOdd = drawOdd;
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

}
