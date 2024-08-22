package org.ifba.bet.dao.user;

import java.sql.SQLException;

import org.ifba.bet.model.Team;
import org.ifba.bet.model.User;

public interface UserDao {

	public boolean login(String email, String password) throws SQLException;

	public User findUserByEmail(String email) throws SQLException;

	public void insertUser(String name, String email, String password, int accessLevel) throws SQLException;

	public void deletUser(User user) throws SQLException;

	public int getTotalBets(User user) throws SQLException;

	public String getFavoriteTeam(User user) throws SQLException;

	public void updateUserPassword(User user, String newPassword) throws SQLException;

	public void updateUserName(User user, String newUserName) throws SQLException;

	public void updateUserEmail(User user, String newEmail) throws SQLException;

	public void updateUserFavoriteTeam(User user, Team team) throws SQLException;
}
