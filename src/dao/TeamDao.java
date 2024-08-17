package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Team;

public interface TeamDao {
	public Team findTeamByName(String nome) throws SQLException;
	public Team findTeamByAbbreviation(String abreviacao) throws SQLException;
	public void insertTeam(String name, String abbreviation) throws SQLException;
	public void deleteTeam(Team team) throws SQLException;
	public void updateTeamName(Team team, String newName, String NewAbbreviation) throws SQLException;
	public ArrayList <Team> getAllTeams() throws SQLException;
}
