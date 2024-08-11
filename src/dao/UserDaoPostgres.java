package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;
import util.BCrypt;

public class UserDaoPostgres implements UserDao {
	
	private Double saldoInicialPadrao = 500.000d;
	@Override
	public boolean login(String email, String originalPassword) {
	    try (PreparedStatement ps = ConexaoBdSingleton
	            .getInstance()
	            .getConexao()
	            .prepareStatement("SELECT user_password FROM user_tb WHERE user_email = ?")) {

	        ps.setString(1, email);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                String dbPassword = rs.getString("user_password");
	                return BCrypt.checkpw(originalPassword, dbPassword);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	@Override
	public User findUserByEmail(String email) throws SQLException {
		
		User user = null;
		
		PreparedStatement ps = ConexaoBdSingleton
				.getInstance()
				.getConexao().prepareStatement("SELECT * FROM user_tb WHERE user_email = ?");	

		ps.setString(1, email);
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		
		user = new User(rs.getInt("user_id"),
						rs.getInt("access_level_id"),
						rs.getDouble("user_balance"),
						rs.getString("user_name"), 
						rs.getString("user_email"), 
						rs.getString("user_password"));
		return user;	
	}
	@Override
	public void insertUser(String nome, String email, String senha, int accessLevel) throws SQLException {
		
		try {
		PreparedStatement ps = ConexaoBdSingleton
				.getInstance()
				.getConexao().prepareStatement("INSERT INTO user_tb (access_level_id, user_name, user_email, user_password, user_balance) VALUES (?, ?, ?, ?, ?)");
				ps.setInt(1, accessLevel);
				ps.setString(2, nome);
				ps.setString(3, email);
				ps.setString(4, senha);
				ps.setDouble(5, saldoInicialPadrao);
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

	@Override
	public int getTotalBets(User user) throws SQLException{
		
		PreparedStatement ps = ConexaoBdSingleton
				.getInstance()
				.getConexao().prepareStatement("SELECT COUNT(*) as total_user_bets FROM bet_tb AS bt INNER JOIN user_tb AS u ON bt.user_id = u.user_id WHERE bt.user_id = ?");	

		ps.setInt(1, user.getID());
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getInt("total_user_bets");
	}

	@Override
	public void updateUserPassword(User user, String newPassword) throws SQLException {
		try {
		PreparedStatement ps = ConexaoBdSingleton
				.getInstance()
				.getConexao().prepareStatement("UPDATE user_tb SET user_password = ? \r\n"
						+ "WHERE(user_id = ?)");
				ps.setString(1, newPassword);
				ps.setInt(2, user.getID());
				ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void updateUserName(User user, String newUserName) throws SQLException{
		try {
		PreparedStatement ps = ConexaoBdSingleton
				.getInstance()
				.getConexao().prepareStatement("UPDATE user_tb SET user_name = ? \r\n"
						+ "WHERE(user_id = ?)");
				ps.setString(1,newUserName);
				ps.setInt(2, user.getID());
				ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void updateUserEmail(User user, String newEmail) throws SQLException{
		try {
		PreparedStatement ps = ConexaoBdSingleton
				.getInstance()
				.getConexao().prepareStatement("UPDATE user_tb SET user_email = ? \r\n"
						+ "WHERE(user_id = ?)");
				ps.setString(1, newEmail);
				ps.setInt(2, user.getID());
				ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
}
