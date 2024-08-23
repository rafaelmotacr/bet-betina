package org.ifba.bet.dao.match;

import java.sql.SQLException;
import java.util.ArrayList;

import org.ifba.bet.model.Match;
import org.ifba.bet.model.Team;

public interface MatchDao {
	
	public ArrayList<Match> findTeamMatches(Team team) throws SQLException;
	
	public ArrayList<Match> getAllMatchs() throws SQLException;

	public ArrayList<Match> getAllMatchs(String filter) throws SQLException;

	public ArrayList<Match> getActiveMatchs() throws SQLException;

	public ArrayList<Match> getActiveMatchs(String filter) throws SQLException;


}
