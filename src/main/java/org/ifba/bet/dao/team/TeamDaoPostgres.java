package org.ifba.bet.dao.team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.ifba.bet.dao.connection.DatabaseConnectionSingleton;
import org.ifba.bet.model.Team;

public class TeamDaoPostgres implements TeamDao {
	
	@Override
	public void insertTeam(String name, String abbreviation) throws SQLException {
		String sql = "INSERT INTO team_tb (team_name, team_abbreviation) VALUES (?, ?);";
		abbreviation = abbreviation.toUpperCase();
		Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, name);
		ps.setString(2, abbreviation);
		ps.executeUpdate();
		ps.close();
	}

	@Override
	public void deleteTeam(Team team) throws SQLException {
		String sql = "DELETE FROM team_tb WHERE team_id = ?;";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, team.getID());
		ps.executeUpdate();
		ps.close();
	}

	@Override
	public void updateAbbreviation(Team team, String abbreviation) throws SQLException {
		String sql = "UPDATE team_tb SET team_abbreviation = ? WHERE team_id = ?;";
		abbreviation = abbreviation.toUpperCase();
		Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, abbreviation);
		ps.setInt(2, team.getID());
		ps.executeUpdate();
		ps.close();
	}

	@Override
	public void updateTeamName(Team team, String name) throws SQLException {
		String sql = "UPDATE team_tb SET team_name = ? WHERE team_id = ?;";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, name);
		ps.setInt(2, team.getID());
		ps.executeUpdate();
		ps.close();
	}

	@Override
	public ArrayList<Team> getAllTeams() throws SQLException {
		String sql = "SELECT * FROM team_tb ORDER BY (team_id) DESC;";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		ArrayList<Team> teamsList = new ArrayList<Team>();
		while (rs.next()) {
			teamsList.add(new Team(rs.getInt("team_id"), rs.getString("team_name"), rs.getString("team_abbreviation")));
		}
		ps.close();
		rs.close();
		return teamsList;
	}

	@Override
	public ArrayList<Team> getAllTeams(String filter) throws SQLException {
		String sql = "SELECT * FROM team_tb WHERE LOWER(team_name) LIKE LOWER(?) "
				+ "OR LOWER(team_abbreviation) LIKE LOWER(?) ORDER BY team_id DESC;";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ArrayList<Team> teamsList = new ArrayList<Team>();
		String teamNameSearch = "%" + filter + "%";
		String teamAbbreviationSearch = "%" + filter + "%";
		ps.setString(1, teamNameSearch);
		ps.setString(2, teamAbbreviationSearch);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			teamsList.add(new Team(rs.getInt("team_id"), rs.getString("team_name"), rs.getString("team_abbreviation")));
		}
		ps.close();
		rs.close();
		return teamsList;
	}

//	Define como nulo todos os usuários que tem um time favoritado excluído
	@Override
	public void fixUsersFavoriteTeamAfterDelete(Team team) throws SQLException {
		String sql = "UPDATE user_tb SET user_favorite_team_id = null WHERE user_favorite_team_id = ?;"; 
		Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, team.getID());
		ps.executeUpdate();
		ps.close();
	}

	@Override
	public Team findTeamById(int id) {
		String sql = "SELECT * FROM team_tb WHERE team_id = ?;";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConnection(); 
		Team team = null;
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			team = new Team(rs.getInt("team_id"), rs.getString("team_name"), rs.getString("team_abbreviation"));
			ps.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println("Erro ao busca time por id.");
		}
		return team;
	}
}
