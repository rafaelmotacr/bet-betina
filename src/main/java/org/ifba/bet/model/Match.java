package org.ifba.bet.model;

import org.ifba.bet.dao.team.TeamDaoPostgres;

public class Match {

	// Atributos de instância
	
	private int id;
	private int state;
	private int result;
	private int homeTeamId;
	private int awayTeamId;
	private double homeTeamOdd;
	private double awayTeamOdd;
	private double drawOdd;
	private TeamDaoPostgres teamDao = new TeamDaoPostgres();
	
	// Atributos estáticos para possíveis estados da partida
	
	public static final int MATCH_ON_GOING = 1;
	public static final int MATCH_FINISHED = 0;
	
	// Atributos estáticos para possíveis resultados da partida
	
	public static final int RESULT_ON_GOING = 0;
	public static final int RESULT_HOME_WIN = 1;
	public static final int RESULT_AWAY_WIN = 2;
	public static final int RESULT_DRAW = 3;
	
	public Match(int id, int state, int homeTeamId, int awayTeamId, double homeTeamOdd, double awayTeamOdd,
			double drawOdd) {
		super();
		this.id = id;
		this.state = state;
		this.homeTeamId = homeTeamId;
		this.awayTeamId = awayTeamId;
		this.homeTeamOdd = homeTeamOdd;
		this.awayTeamOdd = awayTeamOdd;
		this.drawOdd = drawOdd;
	}

	public Match(int id, int state, int result, int homeTeamId, int awayTeamId, double homeTeamOdd, double awayTeamOdd,
			double drawOdd) {
		super();
		this.id = id;
		this.state = state;
		this.result = result;
		this.homeTeamId = homeTeamId;
		this.awayTeamId = awayTeamId;
		this.homeTeamOdd = homeTeamOdd;
		this.awayTeamOdd = awayTeamOdd;
		this.drawOdd = drawOdd;
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

	public double getHomeTeamOdd() {
		return homeTeamOdd;
	}

	public void setHomeTeamOdd(double homeTeamOdd) {
		this.homeTeamOdd = homeTeamOdd;
	}

	public double getAwayTeamOdd() {
		return awayTeamOdd;
	}

	public void setAwayTeamOdd(double awayTeamOdd) {
		this.awayTeamOdd = awayTeamOdd;
	}

	public double getDrawOdd() {
		return drawOdd;
	}

	public void setDrawOdd(double drawOdd) {
		this.drawOdd = drawOdd;
	}

	@Override
	public String toString() {
		String toString = null;
		toString = teamDao.findTeamById(homeTeamId).getName() + " X " + teamDao.findTeamById(awayTeamId).getName();
		return toString;
	}

}
