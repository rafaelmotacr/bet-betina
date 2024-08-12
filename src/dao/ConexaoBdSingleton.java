package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBdSingleton {

	private Connection connection;
	private static ConexaoBdSingleton instance;
	
	private ConexaoBdSingleton() {
		try {
			
			// Conexao com o BD de Rafael
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bet-betina-prod", "postgres", "106927");
			
			// Conexao com o BD do IFBA
			// conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/betina", "postgres", "alunoifba");
			
			// Conexao com o BD de Iuri
			 //conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bet", "postgres", "admin");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	public Connection getConexao() {
        return connection;
    }
	
	public static ConexaoBdSingleton getInstance() {
		if (instance == null) {
			instance = new ConexaoBdSingleton();
		}
		return instance;
	}
}