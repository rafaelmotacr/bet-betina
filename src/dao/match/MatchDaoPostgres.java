package dao.match;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.connection.ConexaoBdSingleton;
import model.Match;

public class MatchDaoPostgres implements MatchDao {

	@Override
	public ArrayList<Match> getAllMatchs() throws SQLException {
		ArrayList<Match> matchsArray = new ArrayList<Match>();
		String sql = "SELECT \r\n"
				+ "    match_tb.match_id AS ID,\r\n"
				+ "    match_tb.match_state AS state,\r\n"
				+ "    match_tb.match_odd AS ODD,\r\n"
				+ "    home_team.team_name AS home_team_name,\r\n"
				+ "    away_team.team_name AS away_team_name\r\n"
				+ "FROM \r\n"
				+ "    match_tb\r\n"
				+ "JOIN \r\n"
				+ "    team_tb AS home_team ON match_tb.match_home_team = home_team.team_id\r\n"
				+ "JOIN \r\n"
				+ "    team_tb AS away_team ON match_tb.match_away_team = away_team.team_id;\r\n"
				+ "";
		PreparedStatement ps = ConexaoBdSingleton
		        .getInstance()
		        .getConexao()
		        .prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			//public Match(int id, int state, Double odd, String homeTeamName, String awayTeamName) {
			matchsArray.add(new Match(rs.getInt("ID"),
					rs.getInt("state"),
					rs.getDouble("ODD"),
					rs.getString("home_team_name"),
					rs.getString("away_team_name")));
		}
		return matchsArray;
	}

}
