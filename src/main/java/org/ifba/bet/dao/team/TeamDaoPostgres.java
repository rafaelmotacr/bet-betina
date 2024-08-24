package org.ifba.bet.dao.team;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.ifba.bet.dao.connection.DatabaseConnectionSingleton;
import org.ifba.bet.model.Team;

public class TeamDaoPostgres implements TeamDao {
	@Override
	public void insertTeam(String name, String abbreviation) throws SQLException {

		abbreviation = abbreviation.toUpperCase();
		PreparedStatement ps = DatabaseConnectionSingleton.getInstance().getConexao()
				.prepareStatement("INSERT INTO team_tb (team_name, team_abbreviation) VALUES (?, ?)");
		ps.setString(1, name);
		ps.setString(2, abbreviation);
		ps.executeUpdate();
	}

	@Override
	public void deleteTeam(Team team) throws SQLException {
		try {
			PreparedStatement ps = DatabaseConnectionSingleton.getInstance().getConexao()
					.prepareStatement("DELETE FROM team_tb WHERE team_id = ? ");
			ps.setInt(1, team.getID());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateAbbreviation(Team team, String abbreviation) throws SQLException {
		abbreviation = abbreviation.toUpperCase();
		try {
			PreparedStatement ps = DatabaseConnectionSingleton.getInstance().getConexao()
					.prepareStatement("UPDATE team_tb SET team_abbreviation = ? WHERE team_id = ?");
			ps.setString(1, abbreviation);
			ps.setInt(2, team.getID());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateTeamName(Team team, String name) throws SQLException {
		try {
			PreparedStatement ps = DatabaseConnectionSingleton.getInstance().getConexao()
					.prepareStatement("UPDATE team_tb SET team_name = ? WHERE team_id = ?");
			ps.setString(1, name);
			ps.setInt(2, team.getID());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Team> getAllTeams() throws SQLException {
		ArrayList<Team> teamsList = new ArrayList<Team>();
		PreparedStatement ps = DatabaseConnectionSingleton.getInstance().getConexao()
				.prepareStatement("SELECT * FROM team_tb ORDER BY (team_id) DESC");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			teamsList.add(new Team(rs.getInt("team_id"), rs.getString("team_name"), rs.getString("team_abbreviation")));
		}
		return teamsList;
	}

	@Override
	public ArrayList<Team> getAllTeams(String filter) throws SQLException {
		ArrayList<Team> teamsList = new ArrayList<Team>();

		String sql = "SELECT * FROM team_tb " + "WHERE LOWER(team_name) LIKE LOWER(?) "
				+ "OR LOWER(team_abbreviation) LIKE LOWER(?) " + "ORDER BY team_id DESC";

		PreparedStatement ps = DatabaseConnectionSingleton.getInstance().getConexao().prepareStatement(sql);

		String teamNameSearch = "%" + filter + "%";
		String teamAbbreviationSearch = "%" + filter + "%";

		ps.setString(1, teamNameSearch);
		ps.setString(2, teamAbbreviationSearch);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			teamsList.add(new Team(rs.getInt("team_id"), rs.getString("team_name"), rs.getString("team_abbreviation")));
		}
		return teamsList;
	}

//	Define como nulo todos os usuários que tem um time favoritado excluído
	@Override
	public void fixUsersFavoriteTeamAfterDelete(Team team) throws SQLException {
		try {
			PreparedStatement ps = DatabaseConnectionSingleton.getInstance().getConexao().prepareStatement(
					"UPDATE user_tb SET user_favorite_team_id = null \r\n" + "WHERE user_favorite_team_id = ?");
			ps.setInt(1, team.getID());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Team findTeamById(int id) {

		Team team = null;

		PreparedStatement ps;
		try {
			ps = DatabaseConnectionSingleton.getInstance().getConexao()
					.prepareStatement("SELECT * FROM team_tb WHERE team_id = ?");
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			rs.next();
			team = new Team(rs.getInt("team_id"), rs.getString("team_name"), rs.getString("team_abbreviation"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return team;
	}

}
