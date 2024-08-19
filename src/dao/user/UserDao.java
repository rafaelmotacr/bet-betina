package dao.user;

import java.sql.SQLException;

import model.Team;
import model.User;

public interface UserDao {
	
	public boolean login(String email, String senha) throws SQLException;
	public User findUserByEmail(String email) throws SQLException;
	public void insertUser(String nome, String email, String senha, int accessLevel) throws SQLException;
	public void deletUser(User user) throws SQLException;
	public int getTotalBets(User user) throws SQLException;
	public String getFavoriteTeam(User user) throws SQLException;
	public void updateUserPassword(User user, String newPassword) throws SQLException;
	public void updateUserName(User user, String newUserName) throws SQLException;
	public void updateUserEmail(User user, String newEmail) throws SQLException;
	public void updateUserFavoriteTeam(User user, Team team) throws SQLException;
}
