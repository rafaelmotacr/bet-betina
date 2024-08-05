package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class UserDaoPostgres implements UserDao {

	public boolean login(String email, String senha) throws SQLException {
		
		PreparedStatement ps = ConexaoBdSingleton
				.getInstance()
				.getConexao().prepareStatement("SELECT * FROM user_tb WHERE user_email = ? AND user_password = ?");	

		ps.setString(1, email);
		ps.setString(2, senha);
		
		return ps.executeQuery().next();
	}
	
	public User findUserByLoguin(String email, String senha) throws SQLException {
	
		User user = null;
		PreparedStatement ps = ConexaoBdSingleton
				.getInstance()
				.getConexao().prepareStatement("SELECT * FROM user_tb WHERE user_email = ? AND user_password = ?");	

		ps.setString(1, email);
		ps.setString(2, senha);
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		
		try {
			user = new User(rs.getInt("user_id"),
							rs.getInt("access_level_id"), 
							rs.getString("user_name"), 
							rs.getString("user_email"), 
							rs.getString("user_password"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
		
	}

	@Override
	public void insertUser(String nome, String email, String senha) throws SQLException {
		
		try {
		PreparedStatement ps = ConexaoBdSingleton
				.getInstance()
				.getConexao().prepareStatement("INSERT INTO user_tb (access_level_id, user_name, user_email, user_password) VALUES (?, ?, ?, ?)");
				ps.setInt(1,2 );
				ps.setString(2, nome);
				ps.setString(3, email);
				ps.setString(4, senha);
				ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deletUser(User user) throws SQLException {
		try {
		PreparedStatement ps = ConexaoBdSingleton
				.getInstance()
				.getConexao().prepareStatement("DELETE FROM user_tb WHERE user_id = ? ");
				ps.setInt(1,user.getID());
				ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
}
