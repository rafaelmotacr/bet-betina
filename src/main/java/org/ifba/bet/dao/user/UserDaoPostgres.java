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

	@Override
	public boolean login(String email, String originalPassword) {
		String sql = "SELECT user_password FROM user_tb WHERE user_email = ?;";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String dbPassword = rs.getString("user_password");
				rs.close();
				ps.close();
				return BCrypt.checkpw(originalPassword, dbPassword);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao Buscar Sua Conta no Banco de Dados.");
		}
		return false;
	}

	@Override
	public User findUserByEmail(String email) {
		User user = null;
		String sql = "SELECT * FROM user_tb WHERE user_email = ?;";
		Connection conn =  DatabaseConnectionSingleton.getInstance().getConnection();
		try {
			PreparedStatement ps;
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			rs.next();
			user = new User(rs.getInt("user_id"), rs.getInt("user_access_level"), rs.getDouble("user_balance"),
					rs.getString("user_name"), rs.getString("user_email"), rs.getInt("user_favorite_team_id"));
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Erro ao Buscar Sua Conta no Banco de Dados.");
		}
		return user;
	}

	@Override
	public void insertUser(String name, String email, String password, int accessLevel) throws SQLException {
		String sql = "INSERT INTO user_tb (user_access_level, user_name, user_email, user_password, user_balance) VALUES (?, ?, ?, ?, ?);";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		String encryptedPassword = InputManipulation.generateHashedPassword(password);
		ps.setInt(1, accessLevel);
		ps.setString(2, name);
		ps.setString(3, email);
		ps.setString(4, encryptedPassword);
		ps.setDouble(5, User.USER_STANDARD_INITAL_BALANCE);
		ps.executeUpdate();
		ps.close();
	}

	@Override
	public void deletUser(User user) throws SQLException {
		String sql = "DELETE FROM user_tb WHERE user_id = ?;";
		Connection conn =  DatabaseConnectionSingleton.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, user.getId());
		ps.executeUpdate();
		ps.close();
	}

	@Override
	public int getTotalBets(int userId){
		String sql = "SELECT COUNT (*) AS total_user_bets FROM bet_tb WHERE user_id = ?;";
		int totalUserBets = 0;
		try {
			Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			totalUserBets = rs.getInt("total_user_bets");
			ps.close();
			rs.close();
			return totalUserBets;
		} catch (SQLException e) {
			System.out.println("Erro ao calcular o seu total de apostas paizão;");
		}
		return totalUserBets;
	}

	
	@Override
	public int getTotalWinnedBets(int userId) {
		String sql = "SELECT COUNT (*) AS total_wins FROM bet_tb WHERE user_id = ? AND bet_state = ?;";
		int totalWinnedBets = 0;
		try {
			Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, Bet.WIN);
			ResultSet rs = ps.executeQuery();
			rs.next();
			totalWinnedBets = rs.getInt("total_wins");
			ps.close();
			rs.close();
			return totalWinnedBets;
		} catch (SQLException e) {
			System.out.println("Erro ao calcular o seu total de apostas ganhas paizão;");
		}
		return totalWinnedBets;
	}

	@Override
	public int getTotalLosedBets(int userId) {
		String sql = "SELECT COUNT (*) AS total_loses FROM bet_tb WHERE user_id = ? AND bet_state = ?;";
		int totalLosedBets = 0;
		try {
			Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, Bet.LOSE);
			ResultSet rs = ps.executeQuery();
			rs.next();
			totalLosedBets = rs.getInt("total_loses");
			ps.close();
			rs.close();
			return totalLosedBets;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public String getFavoriteTeam(User user) throws SQLException {
		String sql ="SELECT team_name AS favorite_team" 
				+ "	FROM team_tb INNER JOIN user_tb"
				+ "	ON (user_tb.user_favorite_team_id = team_tb.team_id)"
				+ "	WHERE user_tb.user_id = ?;";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		String teamName = null;
		ps.setInt(1, user.getId());
		ResultSet rs = ps.executeQuery();
		rs.next();
		teamName = rs.getString("favorite_team");
		ps.close();
		rs.close();
		return teamName;
	}

	@Override
	public void updateUserPassword(User user, String newPassword) throws SQLException {
		String sql = "UPDATE user_tb SET user_password = ? WHERE user_id = ?;";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		String encryptedPassword = InputManipulation.generateHashedPassword(newPassword);
		ps.setString(1, encryptedPassword);
		ps.setInt(2, user.getId());
		ps.executeUpdate();
		ps.close();
	}

	@Override
	public void updateUserName(User user, String newUserName) throws SQLException {
		String sql = "UPDATE user_tb SET user_name = ? WHERE user_id = ?;";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, newUserName);
		ps.setInt(2, user.getId());
		ps.executeUpdate();
		user.setName(newUserName);
		ps.close();
	}
	
	@Override
	public void updateUserEmail(User user, String newEmail) throws SQLException {
		String sql = "UPDATE user_tb SET user_email = ? WHERE user_id = ?;";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, newEmail);
		ps.setInt(2, user.getId());
		ps.executeUpdate();
		user.setEmail(newEmail);
		ps.close();
	}

	@Override
	public void updateUserFavoriteTeam(User user, Team team) throws SQLException {
		String sql = "UPDATE user_tb SET user_favorite_team_id = ? WHERE user_id = ?;";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, team.getId());
		ps.setInt(2, user.getId());
		ps.executeUpdate();
		user.setFavoriteTeam(team.getId());
		ps.close();
	}

	@Override
	public void updateUserBalance(User user, Double newBalance) throws SQLException {
		String sql = "UPDATE user_tb SET user_balance = ? WHERE user_id = ?;";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setDouble(1, newBalance);
		ps.setInt(2, user.getId());
		ps.executeUpdate();
		user.setBalance(newBalance);
		ps.close();
	}

	@Override
	public ArrayList<Bet> getAllBets(int userId) throws SQLException {
		String sql = "SELECT * FROM bet_tb WHERE user_id = ? ORDER BY bet_id;";
		Connection conn = DatabaseConnectionSingleton.getInstance().getConnection();
		ArrayList<Bet> betArray = new ArrayList<Bet>();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, userId);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			betArray.add(new Bet(rs.getInt("bet_id"), rs.getInt("bet_state"), rs.getInt("user_id")));
		}
		ps.close();
		rs.close();
		return betArray;
	}
}
