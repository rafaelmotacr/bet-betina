package dao.match;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.connection.ConexaoBdSingleton;
import model.Match;
import model.Team;

public class MatchDaoPostgres implements MatchDao {

	@Override
	public ArrayList<Match> getAllMatchs() throws SQLException {
	    ArrayList<Match> matchsArray = new ArrayList<Match>();
	    String sql = "SELECT " +
	                 "    match_tb.match_id AS ID, " +
	                 "    match_tb.match_state AS state, " +
	                 "    match_tb.match_home_team_odd AS home_team_odd, " +
	                 "    match_tb.match_away_team_odd AS away_team_odd, " +
	                 "    match_tb.match_draw_odd AS draw_odd, " +
	                 "    home_team.team_name AS home_team_name, " +
	                 "    away_team.team_name AS away_team_name " +
	                 "FROM " +
	                 "    match_tb " +
	                 "JOIN " +
	                 "    team_tb AS home_team ON match_tb.match_home_team = home_team.team_id " +
	                 "JOIN " +
	                 "    team_tb AS away_team ON match_tb.match_away_team = away_team.team_id;";

	    PreparedStatement ps = ConexaoBdSingleton
	            .getInstance()
	            .getConexao()
	            .prepareStatement(sql);
	    ResultSet rs = ps.executeQuery();

	    while (rs.next()) {
	        matchsArray.add(new Match(
	            rs.getInt("ID"),
	            rs.getInt("state"),
	            rs.getDouble("home_team_odd"),
	            rs.getDouble("away_team_odd"),
	            rs.getDouble("draw_odd"),
	            rs.getString("home_team_name"),
	            rs.getString("away_team_name")
	        ));
	    }
	    return matchsArray;
	}


	@Override
	public ArrayList<Match> findTeamMatches(Team team) throws SQLException {
		ArrayList<Match> matchsArray = new ArrayList<Match>();
		String sql = "SELECT\r\n"+
                "    match_tb.match_id AS ID, " +
                "    match_tb.match_state AS state, " +
                "    match_tb.match_home_team_odd AS home_team_odd, " +
                "    match_tb.match_away_team_odd AS away_team_odd, " +
                "    match_tb.match_draw_odd AS draw_odd, " +
                "    home_team.team_name AS home_team_name, " +
                "    away_team.team_name AS away_team_name " +
                "FROM " +
                "    match_tb " +
                "JOIN " +
                "    team_tb AS home_team ON match_tb.match_home_team = home_team.team_id " +
                "JOIN " +
                "    team_tb AS away_team ON match_tb.match_away_team = away_team.team_id;" +
				"WHERE away_team.team_id = 4 or home_team.team_id = 4";
		PreparedStatement ps = ConexaoBdSingleton
		        .getInstance()
		        .getConexao()
		        .prepareStatement(sql);
		ps.setInt(1, team.getID());
		ps.setInt(2, team.getID());
		ResultSet rs = ps.executeQuery();
	    while (rs.next()) {
	        matchsArray.add(new Match(
	            rs.getInt("ID"),
	            rs.getInt("state"),
	            rs.getDouble("home_team_odd"),
	            rs.getDouble("away_team_odd"),
	            rs.getDouble("draw_odd"),
	            rs.getString("home_team_name"),
	            rs.getString("away_team_name")
	        ));
	    }
		return matchsArray;
	}

}
