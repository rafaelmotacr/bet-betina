package org.ifba.bet.dao.match;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.ifba.bet.dao.connection.DatabaseConnectionSingleton;
import org.ifba.bet.model.Match;
import org.ifba.bet.model.Team;

public class MatchDaoPostgres implements MatchDao {

	@Override
	public ArrayList<Match> getAllMatchs() throws SQLException {
		ArrayList<Match> matchsArray = new ArrayList<Match>();
		String sql = "SELECT " + "    match_tb.match_id AS id, " + "    match_tb.match_state AS state, "
				+ "    match_tb.match_home_team_odd AS home_team_odd, "
				+ "    match_tb.match_away_team_odd AS away_team_odd, " + "    match_tb.match_draw_odd AS draw_odd, "
				+ "    home_team.team_id AS home_team_id, " + "    away_team.team_id AS away_team_id " + "FROM "
				+ "    match_tb " + "JOIN "
				+ "    team_tb AS home_team ON match_tb.match_home_team = home_team.team_id " + "JOIN "
				+ "    team_tb AS away_team ON match_tb.match_away_team = away_team.team_id;";

		PreparedStatement ps = DatabaseConnectionSingleton.getInstance().getConexao().prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			// public Match(int id, int state, int homeTeamId, int awayTeamId, double
			// homeTeamOdd, double awayTeamOdd, double drawOdd) {
			matchsArray.add(
					new Match(rs.getInt("id"), rs.getInt("state"), rs.getInt("home_team_id"), rs.getInt("away_team_id"),
							rs.getDouble("home_team_odd"), rs.getDouble("away_team_odd"), rs.getDouble("draw_odd")));
		}
		return matchsArray;
	}

	@Override
	public ArrayList<Match> getAllMatchs(String filter) throws SQLException {
		ArrayList<Match> matchsArray = new ArrayList<Match>();
		String sql = "SELECT " + "    match_tb.match_id AS id, " + "    match_tb.match_state AS state, "
				+ "    match_tb.match_home_team_odd AS home_team_odd, "
				+ "    match_tb.match_away_team_odd AS away_team_odd, " + "    match_tb.match_draw_odd AS draw_odd, "
				+ "    home_team.team_id AS home_team_id, " + "    away_team.team_id AS away_team_id " + "FROM "
				+ "    match_tb " + "JOIN "
				+ "    team_tb AS home_team ON match_tb.match_home_team = home_team.team_id " + "JOIN "
				+ "    team_tb AS away_team ON match_tb.match_away_team = away_team.team_id;";

		PreparedStatement ps = DatabaseConnectionSingleton.getInstance().getConexao().prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Match match = new Match(rs.getInt("id"), rs.getInt("state"), rs.getInt("home_team_id"),
					rs.getInt("away_team_id"), rs.getDouble("home_team_odd"), rs.getDouble("away_team_odd"),
					rs.getDouble("draw_odd"));

			if (match.toString().toLowerCase().contains(filter)) {
				matchsArray.add(match);
			}
		}
		return matchsArray;
	}

//calma calabreso
	@Override
	public ArrayList<Match> findTeamMatches(Team team) throws SQLException {
		ArrayList<Match> matchsArray = new ArrayList<Match>();
		String sql = "SELECT " + "    match_tb.match_id AS id, " + "    match_tb.match_state AS state, "
				+ "    match_tb.match_home_team_odd AS home_team_odd, "
				+ "    match_tb.match_away_team_odd AS away_team_odd, " + "    match_tb.match_draw_odd AS draw_odd, "
				+ "    home_team.team_id AS home_team_id, " + "    away_team.team_id AS away_team_id " + "FROM "
				+ "    match_tb " + "JOIN "
				+ "    team_tb AS home_team ON match_tb.match_home_team = home_team.team_id " + "JOIN "
				+ "    team_tb AS away_team ON match_tb.match_away_team = away_team.team_id " + "WHERE "
				+ "    away_team.team_id = ? OR home_team.team_id = ?;";
		PreparedStatement ps = DatabaseConnectionSingleton.getInstance().getConexao().prepareStatement(sql);
		ps.setInt(1, team.getID());
		ps.setInt(2, team.getID());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			matchsArray.add(
					new Match(rs.getInt("id"), rs.getInt("state"), rs.getInt("home_team_id"), rs.getInt("away_team_id"),
							rs.getDouble("home_team_odd"), rs.getDouble("away_team_odd"), rs.getDouble("draw_odd")));
		}
		return matchsArray;
	}

	@Override
	public ArrayList<Match> getActiveMatchs() throws SQLException {
		ArrayList<Match> matchsArray = new ArrayList<Match>();
		String sql = "SELECT " + "    match_tb.match_id AS id, " + "    match_tb.match_state AS state, "
				+ "    match_tb.match_home_team_odd AS home_team_odd, "
				+ "    match_tb.match_away_team_odd AS away_team_odd, " + "    match_tb.match_draw_odd AS draw_odd, "
				+ "    home_team.team_id AS home_team_id, " + "    away_team.team_id AS away_team_id " + "FROM "
				+ "    match_tb " + "JOIN "
				+ "    team_tb AS home_team ON match_tb.match_home_team = home_team.team_id " + "JOIN "
				+ "    team_tb AS away_team ON match_tb.match_away_team = away_team.team_id " + "WHERE "
				+ "    match_tb.match_state = 1;";
		PreparedStatement ps = DatabaseConnectionSingleton.getInstance().getConexao().prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			matchsArray.add(
					new Match(rs.getInt("id"), rs.getInt("state"), rs.getInt("home_team_id"), rs.getInt("away_team_id"),
							rs.getDouble("home_team_odd"), rs.getDouble("away_team_odd"), rs.getDouble("draw_odd")));
		}
		return matchsArray;
	}

	@Override
	public ArrayList<Match> getActiveMatchs(String filter) throws SQLException {
		ArrayList<Match> matchsArray = new ArrayList<Match>();
		String sql = "SELECT " + "    match_tb.match_id AS id, " + "    match_tb.match_state AS state, "
				+ "    match_tb.match_home_team_odd AS home_team_odd, "
				+ "    match_tb.match_away_team_odd AS away_team_odd, " + "    match_tb.match_draw_odd AS draw_odd, "
				+ "    home_team.team_id AS home_team_id, " + "    away_team.team_id AS away_team_id " + "FROM "
				+ "    match_tb " + "JOIN "
				+ "    team_tb AS home_team ON match_tb.match_home_team = home_team.team_id " + "JOIN "
				+ "    team_tb AS away_team ON match_tb.match_away_team = away_team.team_id " + "WHERE "
				+ "    match_tb.match_state = 1;";
		PreparedStatement ps = DatabaseConnectionSingleton.getInstance().getConexao().prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Match match = new Match(rs.getInt("id"), rs.getInt("state"), rs.getInt("home_team_id"),
					rs.getInt("away_team_id"), rs.getDouble("home_team_odd"), rs.getDouble("away_team_odd"),
					rs.getDouble("draw_odd"));

			if (match.toString().toLowerCase().contains(filter)) {
				matchsArray.add(match);
			}
		}
		return matchsArray;
	}

}
