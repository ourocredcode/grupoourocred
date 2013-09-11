package br.com.sgo.infra;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class ConexaoPN {

	public Connection getConexao() {

		Properties properties = new Properties();

		try {

			FileInputStream fis = new FileInputStream("////localhost//sistemas//_repositorio//properties//connpn.properties");

			properties.load(fis);
			
		} catch (IOException e) {

			e.printStackTrace();

		}

		String driver = properties.getProperty("jdbc.driver");  
	    String user   = properties.getProperty("jdbc.user");   
	    String senha = 	properties.getProperty("jdbc.pass");  
	    String url =  	properties.getProperty("jdbc.url");

	    try	{   
	        Class.forName(driver);   
	        Connection con = null;   
	        con = (Connection) DriverManager.getConnection(url, user, senha);   
	        return con;
	    }

	    catch (ClassNotFoundException ex)	{   

	    	System.err.print(ex.getMessage()); 
	    	
	    	return null;

	    }

	    catch (SQLException e)	{   

	    	System.err.print(e.getMessage());   

	    	return null;

	    }
	}

	public void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Exc: ControladorConexao - Erro ao fechar Conexao.");
		}
	}
}