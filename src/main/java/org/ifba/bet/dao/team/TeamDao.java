package org.ifba.bet.dao.team;

import java.sql.SQLException;
import java.util.ArrayList;

import org.ifba.bet.model.Team;

public interface TeamDao {
	public void insertTeam(String name, String abbreviation) throws SQLException;

	public void deleteTeam(Team team) throws SQLException;

	public void updateTeamName(Team team, String name) throws SQLException;

	public void updateAbbreviation(Team team, String name) throws SQLException;

	public ArrayList<Team> getAllTeams() throws SQLException;

	public ArrayList<Team> getAllTeams(String filter) throws SQLException;

	public void fixUsersFavoriteTeamAfterDelete(Team team) throws SQLException;

	public Team findTeamById(int id);
}
