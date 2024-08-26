package org.ifba.bet.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionSingleton {

	private Connection connection;
	private static DatabaseConnectionSingleton instance;
	
    private static final String URL = "jdbc:postgresql://localhost:5433/bet-betina-prod";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    
	private DatabaseConnectionSingleton() {
		
		
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Connection getConnection() {
		return connection;
	}

	public static DatabaseConnectionSingleton getInstance() {
		if (instance == null) {
			instance = new DatabaseConnectionSingleton();
		}
		return instance;
	}
}