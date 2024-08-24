package org.ifba.bet.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.ifba.bet.dao.connection.DatabaseConnectionSingleton;
import org.ifba.bet.model.Bet;
import org.ifba.bet.model.Team;
import org.ifba.bet.model.User;
import org.ifba.bet.util.InputManipulation;
import org.mindrot.jbcrypt.BCrypt;

public class UserDaoPostgres implements UserDao {

	private Double saldoInicialPadrao = 1500d;

	@Override
	public boolean login(String email, String originalPassword) {
		String sql = "SELECT user_password FROM user_tb WHERE user_email = ?;";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConexao();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String dbPassword = rs.getString("user_password");
				return BCrypt.checkpw(originalPassword, dbPassword);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao Buscar Sua Conta no Banco de Dados.");
		}
		return false;
	}

	@Override
	public User findUserByEmail(String email) throws SQLException {

		User user = null;

		PreparedStatement ps = DatabaseConnectionSingleton.getInstance().getConexao()
				.prepareStatement("SELECT * FROM user_tb WHERE user_email = ?");

		ps.setString(1, email);

		ResultSet rs = ps.executeQuery();
		rs.next();

		user = new User(rs.getInt("user_id"), rs.getInt("user_access_level"), rs.getDouble("user_balance"),
				rs.getString("user_name"), rs.getString("user_email"), rs.getString("user_password"),
				rs.getInt("user_favorite_team_id"));
		return user;
	}

	@Override
	public void insertUser(String name, String email, String password, int accessLevel) throws SQLException {
		String encryptedPassword = InputManipulation.generateHashedPassword(password);
		PreparedStatement ps = DatabaseConnectionSingleton.getInstance().getConexao().prepareStatement(
				"INSERT INTO user_tb (user_access_level, user_name, user_email, user_password, user_balance) VALUES (?, ?, ?, ?, ?)");
		ps.setInt(1, accessLevel);
		ps.setString(2, name);
		ps.setString(3, email);
		ps.setString(4, encryptedPassword);
		ps.setDouble(5, saldoInicialPadrao);
		ps.executeUpdate();
	}

	@Override
	public void deletUser(User user) throws SQLException {
		PreparedStatement ps = DatabaseConnectionSingleton.getInstance().getConexao()
				.prepareStatement("DELETE FROM user_tb WHERE user_id = ? ");
		ps.setInt(1, user.getID());
		ps.executeUpdate();
	}

	@Override
	public int getTotalBets(User user) throws SQLException {

		PreparedStatement ps = DatabaseConnectionSingleton.getInstance().getConexao().prepareStatement(
				"SELECT COUNT(*) as total_user_bets FROM bet_tb AS bt INNER JOIN user_tb AS u ON bt.user_id = u.user_id WHERE bt.user_id = ?");

		ps.setInt(1, user.getID());

		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getInt("total_user_bets");
	}

	@Override
	public String getFavoriteTeam(User user) throws SQLException {

		PreparedStatement ps = DatabaseConnectionSingleton.getInstance().getConexao().prepareStatement(
				"SELECT team_abbreviation AS favorite_team \r\n" + "		FROM team_tb INNER JOIN user_tb\r\n"
						+ "		ON (user_tb.user_favorite_team_id = team_tb.team_id)\r\n"
						+ "		WHERE user_tb.user_id = ?");

		ps.setInt(1, user.getID());

		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getString("favorite_team");
	}

	@Override
	public void updateUserPassword(User user, String newPassword) throws SQLException {
		PreparedStatement ps = DatabaseConnectionSingleton.getInstance().getConexao()
				.prepareStatement("UPDATE user_tb SET user_password = ? \r\n" + "WHERE(user_id = ?)");
		ps.setString(1, newPassword);
		ps.setInt(2, user.getID());
		ps.executeUpdate();
	}

	@Override
	public void updateUserName(User user, String newUserName) throws SQLException {
		PreparedStatement ps = DatabaseConnectionSingleton.getInstance().getConexao()
				.prepareStatement("UPDATE user_tb SET user_name = ? \r\n" + "WHERE(user_id = ?)");
		ps.setString(1, newUserName);
		ps.setInt(2, user.getID());
		ps.executeUpdate();
		user.setName(newUserName);
	}

	@Override
	public void updateUserEmail(User user, String newEmail) throws SQLException {
		PreparedStatement ps = DatabaseConnectionSingleton.getInstance().getConexao()
				.prepareStatement("UPDATE user_tb SET user_email = ? \r\n" + "WHERE(user_id = ?)");
		ps.setString(1, newEmail);
		ps.setInt(2, user.getID());
		ps.executeUpdate();
		user.setEmail(newEmail);
	}

	@Override
	public void updateUserFavoriteTeam(User user, Team team) throws SQLException {
		PreparedStatement ps = DatabaseConnectionSingleton.getInstance().getConexao()
				.prepareStatement("UPDATE user_tb SET user_favorite_team_id = ? \r\n" + "WHERE user_id = ?");
		ps.setInt(1, team.getID());
		ps.setInt(2, user.getID());
		ps.executeUpdate();
		user.setFavoriteTeam(team.getID());
	}

	@Override
	public void updateUserBalance(User user, Double newBalance) throws SQLException {
		PreparedStatement ps = DatabaseConnectionSingleton.getInstance().getConexao()
				.prepareStatement("UPDATE user_tb SET user_balance = ? \r\n" + "WHERE user_id = ?");
		ps.setDouble(1, newBalance);
		ps.setInt(2, user.getID());
		ps.executeUpdate();
		user.setBalance(newBalance);
	}

	@Override
	public ArrayList<Bet> getAllBets(int userId) throws SQLException {
		String sql = "SELECT * FROM bet_tb WHERE user_id = ? ORDER BY bet_id";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConexao();
		ArrayList<Bet> betArray = new ArrayList<Bet>();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, userId);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			betArray.add(new Bet(rs.getInt("bet_id"), rs.getInt("bet_state"), rs.getInt("user_id")));
		}
		return betArray;
	}

}
