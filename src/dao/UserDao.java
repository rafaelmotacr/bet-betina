package dao;

import java.sql.SQLException;

import model.User;

public interface UserDao {
	
	public boolean login(String email, String senha) throws SQLException;
	public User findUserByLoguin(String email, String senha) throws SQLException;
	public void insertUser(String nome, String email, String senha) throws SQLException;
	public void deletUser(User user) throws SQLException;
	public int getTotalApostas(User user) throws SQLException;
	
}
