package br.com.sgo.infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnJDBC {
	
	private Connection conn;
	
	public Connection getConexao() {
		return getConexao("net.sourceforge.jtds.jdbc.Driver", "jdbc:jtds:sqlserver://SRVOUROHOM:1433;DatabaseName=sgobd", "ouro_app_us", "ouro_app_pw");
	}
	
	public Connection getConexao(String driver, String urlConexao, String user, String pass) {
		try {
			Class.forName(driver);
			this.conn = DriverManager.getConnection(urlConexao, user, pass); 
		} catch (ClassNotFoundException e) {
			System.out.println("Exc: ControladorConexao - Driver nao encontrado.");
			System.out.println(e);
		} catch (SQLException e) {
			System.out.println("Exc: ControladorConexao - Erro ao criar uma nova conexao.");
			System.out.println(e);
		}
		return this.conn;
	}
	
	public void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Exc: ControladorConexao - Erro ao fechar Conexao.");
		}
	}

	public void closeConnection(Statement stmt, Connection conn) {
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("Exc: ControladorConexao - Erro ao fechar Statement e Conexao.");
		}
	}

	public void closeConnection(ResultSet rs, Statement stmt, Connection conn) {
		try {
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("Exc: ControladorConexao - Erro ao fechar Resultset, Statement e Conexao.");
		}
	}

}
