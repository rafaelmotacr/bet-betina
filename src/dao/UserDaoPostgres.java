package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class UserDaoPostgres implements UserDao {
	
	private Double saldoInicialPadrao = 500.000d;

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
		
		user = new User(rs.getInt("user_id"),
						rs.getInt("access_level_id"),
						rs.getDouble("user_balance"),
						rs.getString("user_name"), 
						rs.getString("user_email"), 
						rs.getString("user_password"));
		return user;
		
	}

	@Override
	public void insertUser(String nome, String email, String senha) throws SQLException {
		
		try {
		PreparedStatement ps = ConexaoBdSingleton
				.getInstance()
				.getConexao().prepareStatement("INSERT INTO user_tb (access_level_id, user_name, user_email, user_password, user_balance) VALUES (?, ?, ?, ?, ?)");
				ps.setInt(1,2 );
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
	public int getTotalApostas(User user) throws SQLException{
		
		PreparedStatement ps = ConexaoBdSingleton
				.getInstance()
				.getConexao().prepareStatement("SELECT COUNT(*) as total_user_bets FROM bet_tb AS bt INNER JOIN user_tb AS u ON bt.user_id = u.user_id WHERE bt.user_id = ?");	

		ps.setInt(1, user.getID());
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getInt("total_user_bets");
	}
		
}
