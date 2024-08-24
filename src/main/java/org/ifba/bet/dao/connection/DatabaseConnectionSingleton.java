package org.ifba.bet.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionSingleton {

	private Connection connection;
	private static DatabaseConnectionSingleton instance;

	private DatabaseConnectionSingleton() {
		try {

			// Conexao com o BD de Rafael
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bet", "postgres", "106927");

			// Conexao com o BD do IFBA
//			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bet-betina-prod", "postgres","alunoifba");

			// Conexao com o BD de Iuri
//			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bet", "postgres","admin");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Connection getConexao() {
		return connection;
	}

	public static DatabaseConnectionSingleton getInstance() {
		if (instance == null) {
			instance = new DatabaseConnectionSingleton();
		}
		return instance;
	}
}