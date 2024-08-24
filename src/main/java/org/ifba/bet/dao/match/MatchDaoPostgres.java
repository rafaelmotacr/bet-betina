package org.ifba.bet.dao.match;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.ifba.bet.dao.connection.DatabaseConnectionSingleton;
import org.ifba.bet.model.Match;
import org.ifba.bet.model.Team;
import org.ifba.bet.util.OddGenerator;

public class MatchDaoPostgres implements MatchDao {



	@Override
	public ArrayList<Match> getAllMatchs() throws SQLException {
	    ArrayList<Match> matchArray = new ArrayList<Match>();
	    String sql = "SELECT " +
	            "    match_tb.match_id AS id, " +
	            "    home_team.team_id AS home_team_id, " +
	            "    away_team.team_id AS away_team_id, " +
	            "    match_tb.match_state AS state, " +
	            "    match_tb.match_home_odd AS home_team_odd, " +
	            "    match_tb.match_away_odd AS away_team_odd, " +
	            "    match_tb.match_draw_odd AS draw_odd, " +
	            "    match_tb.match_result AS result " +
	            "FROM " +
	            "    match_tb " +
	            "JOIN " +
	            "    team_tb AS home_team " +
	            "    ON match_tb.match_home_team = home_team.team_id " +
	            "JOIN " +
	            "    team_tb AS away_team " +
	            "    ON match_tb.match_away_team = away_team.team_id " +
	            "ORDER BY match_id DESC;";
	    PreparedStatement ps = DatabaseConnectionSingleton.getInstance().getConexao().prepareStatement(sql);
	    ResultSet rs = ps.executeQuery();

	    while (rs.next()) {
	        matchArray.add(new Match(rs.getInt("id"), rs.getInt("state"), rs.getInt("result"),
	                rs.getInt("home_team_id"), rs.getInt("away_team_id"), rs.getDouble("home_team_odd"),
	                rs.getDouble("away_team_odd"), rs.getDouble("draw_odd")));
	    }
	    return matchArray;
	}


	@Override
	public ArrayList<Match> getAllMatchs(String filter) throws SQLException {
	    ArrayList<Match> matchArray = new ArrayList<Match>();
	    String sql = "SELECT " +
	            "    match_tb.match_id AS id, " +
	            "    home_team.team_id AS home_team_id, " +
	            "    away_team.team_id AS away_team_id, " +
	            "    match_tb.match_state AS state, " +
	            "    match_tb.match_home_odd AS home_team_odd, " +
	            "    match_tb.match_away_odd AS away_team_odd, " +
	            "    match_tb.match_draw_odd AS draw_odd, " +
	            "    match_tb.match_result AS result " +
	            "FROM " +
	            "    match_tb " +
	            "JOIN " +
	            "    team_tb AS home_team " +
	            "    ON match_tb.match_home_team = home_team.team_id " +
	            "JOIN " +
	            "    team_tb AS away_team " +
	            "    ON match_tb.match_away_team = away_team.team_id " +
	            "ORDER BY match_id DESC;";
	    PreparedStatement ps = DatabaseConnectionSingleton.getInstance().getConexao().prepareStatement(sql);
	    ResultSet rs = ps.executeQuery();
	    while (rs.next()) {
	        Match match = new Match(rs.getInt("id"), rs.getInt("state"), rs.getInt("result"), rs.getInt("home_team_id"),
	                rs.getInt("away_team_id"), rs.getDouble("home_team_odd"), rs.getDouble("away_team_odd"),
	                rs.getDouble("draw_odd"));

	        if (match.toString().toLowerCase().contains(filter)) {
	            matchArray.add(match);
	        }
	    }
	    return matchArray;
	}


//calma calabreso
	@Override
	public ArrayList<Match> findTeamMatches(Team team) throws SQLException {
	    ArrayList<Match> matchArray = new ArrayList<Match>();
	    String sql = "SELECT " +
	            "    match_tb.match_id AS id, " +
	            "    match_tb.match_state AS state, " +
	            "    match_tb.match_result AS result, " +
	            "    match_tb.match_home_odd AS home_team_odd, " +
	            "    match_tb.match_away_odd AS away_team_odd, " +
	            "    match_tb.match_draw_odd AS draw_odd, " +
	            "    home_team.team_id AS home_team_id, " +
	            "    away_team.team_id AS away_team_id " +
	            "FROM " +
	            "    match_tb " +
	            "JOIN " +
	            "    team_tb AS home_team ON match_tb.match_home_team = home_team.team_id " +
	            "JOIN " +
	            "    team_tb AS away_team ON match_tb.match_away_team = away_team.team_id " +
	            "WHERE " +
	            "    away_team.team_id = ? OR home_team.team_id = ?;";
	    PreparedStatement ps = DatabaseConnectionSingleton.getInstance().getConexao().prepareStatement(sql);
	    ps.setInt(1, team.getID());
	    ps.setInt(2, team.getID());
	    ResultSet rs = ps.executeQuery();
	    while (rs.next()) {
	        matchArray.add(new Match(rs.getInt("id"), rs.getInt("state"), rs.getInt("result"),
	                rs.getInt("home_team_id"), rs.getInt("away_team_id"), rs.getDouble("home_team_odd"),
	                rs.getDouble("away_team_odd"), rs.getDouble("draw_odd")));
	    }
	    return matchArray;
	}

	@Override
	public ArrayList<Match> getActiveMatchs() throws SQLException {
	    ArrayList<Match> matchArray = new ArrayList<Match>();
	    String sql = "SELECT " +
	            "    match_tb.match_id AS id, " +
	            "    home_team.team_id AS home_team_id, " +
	            "    away_team.team_id AS away_team_id, " +
	            "    match_tb.match_state AS state, " +
	            "    match_tb.match_home_odd AS home_team_odd, " +
	            "    match_tb.match_away_odd AS away_team_odd, " +
	            "    match_tb.match_draw_odd AS draw_odd, " +
	            "    match_tb.match_result AS result " +
	            "FROM " +
	            "    match_tb " +
	            "JOIN " +
	            "    team_tb AS home_team ON match_tb.match_home_team = home_team.team_id " +
	            "JOIN " +
	            "    team_tb AS away_team ON match_tb.match_away_team = away_team.team_id " +
	            "WHERE " +
	            "    match_tb.match_state = 1 " +
	            "ORDER BY match_id DESC;";
	    PreparedStatement ps = DatabaseConnectionSingleton.getInstance().getConexao().prepareStatement(sql);
	    ResultSet rs = ps.executeQuery();

	    while (rs.next()) {
	        matchArray.add(new Match(rs.getInt("id"), rs.getInt("state"), rs.getInt("result"),
	                rs.getInt("home_team_id"), rs.getInt("away_team_id"), rs.getDouble("home_team_odd"),
	                rs.getDouble("away_team_odd"), rs.getDouble("draw_odd")));
	    }
	    return matchArray;
	}


	@Override
	public ArrayList<Match> getActiveMatchs(String filter) throws SQLException {
	    ArrayList<Match> matchArray = new ArrayList<Match>();
	    String sql = "SELECT " +
	            "    match_tb.match_id AS id, " +
	            "    home_team.team_id AS home_team_id, " +
	            "    away_team.team_id AS away_team_id, " +
	            "    match_tb.match_state AS state, " +
	            "    match_tb.match_home_odd AS home_team_odd, " +
	            "    match_tb.match_away_odd AS away_team_odd, " +
	            "    match_tb.match_draw_odd AS draw_odd, " +
	            "    match_tb.match_result AS result " +
	            "FROM " +
	            "    match_tb " +
	            "JOIN " +
	            "    team_tb AS home_team ON match_tb.match_home_team = home_team.team_id " +
	            "JOIN " +
	            "    team_tb AS away_team ON match_tb.match_away_team = away_team.team_id " +
	            "WHERE " +
	            "    match_tb.match_state = 1 " +
	            "ORDER BY match_id DESC;";
	    PreparedStatement ps = DatabaseConnectionSingleton.getInstance().getConexao().prepareStatement(sql);
	    ResultSet rs = ps.executeQuery();

	    while (rs.next()) {
	        Match match = new Match(rs.getInt("id"), rs.getInt("state"), rs.getInt("result"),
	                rs.getInt("home_team_id"), rs.getInt("away_team_id"), rs.getDouble("home_team_odd"),
	                rs.getDouble("away_team_odd"), rs.getDouble("draw_odd"));

	        if (match.toString().toLowerCase().contains(filter)) {
	            matchArray.add(match);
	        }
	    }
	    return matchArray;
	}

	@Override
	public void insertMatch(int homeTeamId, int awayTeamId) throws SQLException {
	    String sql = "INSERT INTO match_tb (match_state, match_home_team, match_away_team, match_home_odd, match_away_odd, match_draw_odd) VALUES " +
	            "(1, ?, ?, ?, ?, ?);";
	    double homeTeamOdd = OddGenerator.calculateOdds();
	    double awayTeamOdd = OddGenerator.calculateOdds();
	    double drawOdd = OddGenerator.calculateOdds();
	    Connection conn = DatabaseConnectionSingleton.getInstance().getConexao();
	    PreparedStatement ps = conn.prepareStatement(sql);
	    ps.setInt(1, homeTeamId);
	    ps.setInt(2, awayTeamId);
	    ps.setDouble(3, homeTeamOdd);
	    ps.setDouble(4, awayTeamOdd);
	    ps.setDouble(5, drawOdd);
	    ps.executeUpdate();
	}



	@Override
	public void updateMatchResult(int result, int matchId) throws SQLException {
	    String sql = "UPDATE match_tb SET match_result = ? WHERE match_id = ?;";
	    Connection conn = DatabaseConnectionSingleton.getInstance().getConexao();
	    PreparedStatement ps = conn.prepareStatement(sql);
	    ps.setInt(1, result);
	    ps.setInt(2, matchId);
	    ps.executeUpdate();
	}


	@Override
	public void updateMatchState(int state, int matchId) throws SQLException {
	    String sql = "UPDATE match_tb SET match_state = ? WHERE match_id = ?;";
	    Connection conn = DatabaseConnectionSingleton.getInstance().getConexao();
	    PreparedStatement ps = conn.prepareStatement(sql);
	    ps.setInt(1, state);
	    ps.setInt(2, matchId);
	    ps.executeUpdate();
	}

}
