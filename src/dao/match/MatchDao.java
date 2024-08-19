package dao.match;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Match;

public interface MatchDao {
	public ArrayList <Match> getAllMatchs() throws SQLException;

}
