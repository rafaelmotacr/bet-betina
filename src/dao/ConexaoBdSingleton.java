package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBdSingleton {

	private Connection conexao;
	private static ConexaoBdSingleton instance;
	
	private ConexaoBdSingleton() {
		try {
			// Conexao com o BD de Rafael
			conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/betgree", "postgres", "106927");
			
			// Conexao com o BD do IFBA
			//conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "alunoifba");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	public Connection getConexao() {
        return conexao;
    }
	
	public static ConexaoBdSingleton getInstance() {
		if (instance == null) {
			instance = new ConexaoBdSingleton();
		}
		return instance;
	}
	
}
