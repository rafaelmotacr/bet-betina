package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Team;

public class TeamDaoPostgres implements TeamDao {
	
	@Override
	public Team findTeamByName(String nome) throws SQLException {
		
		Team team = null;
		
		PreparedStatement ps = ConexaoBdSingleton
				.getInstance()
				.getConexao().prepareStatement("SELECT * FROM team_tb WHERE team_name LIKE ?");
				
		ps.setString(1, "%" + nome + "%");
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		
		team = new Team(rs.getInt("team_id"),
						rs.getString("team_name"), 
						rs.getString("team_abbreviation"));
		return team;
	}
	
	@Override
	public Team findTeamByAbbreviation(String abreviacao) throws SQLException {
		
		Team team = null;
		
		PreparedStatement ps = ConexaoBdSingleton
				.getInstance()
				.getConexao().prepareStatement("SELECT * FROM team_tb WHERE team_abbreviation = ?");
				
		ps.setString(1, abreviacao);
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		
		team = new Team(rs.getInt("team_id"),
						rs.getString("team_name"), 
						rs.getString("team_abbreviation"));
		return team;
	}
	
	
	@Override
	public void insertTeam(String name, String abbreviation) throws SQLException {
		
		PreparedStatement ps = ConexaoBdSingleton
				.getInstance()
				.getConexao().prepareStatement("INSERT INTO team_tb (team_name, abbreviation) VALUES (?, ?)");
				ps.setString(1, name);
				ps.setString(2, abbreviation);
				ps.executeUpdate();
	}
	
	
	@Override
	public void deleteTeam(Team team) throws SQLException {
		try {
			PreparedStatement ps = ConexaoBdSingleton
					.getInstance()
					.getConexao().prepareStatement("DELETE FROM team_tb WHERE team_id = ? ");
					ps.setInt(1,team.getID());
					ps.executeUpdate();
			}catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	
	@Override
	public void updateTeamName(Team team, String newName, String newAbbreviation) throws SQLException {
		try {
			PreparedStatement ps = ConexaoBdSingleton
					.getInstance()
					.getConexao().prepareStatement("UPDATE team_tb SET team_name = ?, "
												  + "team_abbreviation = ? "
												  + "WHERE team_id = ?");
					ps.setString(1, newName);
					ps.setString(2, newAbbreviation);
					ps.setInt(3, team.getID());
					
					ps.executeUpdate();
			}catch (SQLException e) {
				e.printStackTrace();
			}finally{
				team.setName(newName);
				team.setAbbreviation(newAbbreviation);
			}
	}

	@Override
	public ArrayList<Team> getAllTeams() throws SQLException {
		ArrayList <Team> teamsList = new ArrayList <Team> (); 
		PreparedStatement ps = ConexaoBdSingleton
				.getInstance()
				.getConexao().prepareStatement("SELECT * FROM team_tb ORDER BY (team_id) DESC");
		ResultSet rs= ps.executeQuery();
		ArrayList<Team> contatos = new ArrayList<Team>();
		while (rs.next()) {
			teamsList.add(new Team(rs.getInt("team_id"),
					rs.getString("team_name"), 
					rs.getString("team_abbreviation")));
		}
		return teamsList;
	}
}
