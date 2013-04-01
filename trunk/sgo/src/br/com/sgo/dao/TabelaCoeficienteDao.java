package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.TabelaCoeficiente;

@Component
public class TabelaCoeficienteDao extends Dao<TabelaCoeficiente> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsTabelaCoeficiente;
	
	private final String sqlTabelaCoeficiente= "SELECT TABELACOEFICIENTE.tabelacoeficiente_id, TABELACOEFICIENTE.nome, TABELACOEFICIENTE.empresa_id" +
			", TABELACOEFICIENTE.organizacao_id FROM TABELACOEFICIENTE (NOLOCK)";  
	
	public TabelaCoeficienteDao(Session session, ConnJDBC conexao) {
		super(session, TabelaCoeficiente.class);
		this.conexao = conexao;
	}

	public Collection<TabelaCoeficiente> buscaAllTabelaCoeficiente(){
		String sql = sqlTabelaCoeficiente;
		this.conn = this.conexao.getConexao();
		Collection<TabelaCoeficiente> coeficientes = new ArrayList<TabelaCoeficiente>();
		try {
			this.stmt = conn.prepareStatement(sql);
			this.rsTabelaCoeficiente = this.stmt.executeQuery();
			while (rsTabelaCoeficiente.next()) {				
				TabelaCoeficiente coeficiente = new TabelaCoeficiente();
				coeficiente.setTabelaCoeficiente_id(rsTabelaCoeficiente.getLong("tabelacoeficiente_id"));
				coeficiente.setNome(rsTabelaCoeficiente.getString("nome"));
				coeficientes.add(coeficiente);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsTabelaCoeficiente, stmt, conn);
		return coeficientes;
	}

	public Collection<TabelaCoeficiente> buscaTabelaCoeficienteByNome(String nome){
		String sql = sqlTabelaCoeficiente;
		if(nome != null)
			sql += 	" WHERE TABELACOEFICIENTE.nome like ?";
		this.conn = this.conexao.getConexao();
		Collection<TabelaCoeficiente> coeficientes = new ArrayList<TabelaCoeficiente>();
		try {
			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setString(1,"%"+  nome + "%");
			this.rsTabelaCoeficiente = this.stmt.executeQuery();
			TabelaCoeficiente coeficiente = new TabelaCoeficiente();
			while (rsTabelaCoeficiente.next()) {
				coeficiente.setTabelaCoeficiente_id(rsTabelaCoeficiente.getLong("tabelacoeficiente_id"));
				coeficiente.setNome(rsTabelaCoeficiente.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsTabelaCoeficiente, stmt, conn);
		return coeficientes;
	}
	
	public TabelaCoeficiente buscaTabelaCoeficienteByEmOrNo(Long empresa, Long organizacao, String nome){
		String sql = sqlTabelaCoeficiente;
		if(empresa != null)
			sql += 	" AND TABELACOEFICIENTE.empresa_id = ?";
		if(organizacao != null)
			sql += 	" AND TABELACOEFICIENTE.organizacao_id = ?";		
		if(nome != null)
			sql += 	" AND (TABELACOEFICIENTE.nome like ?)";
		this.conn = this.conexao.getConexao();		
		TabelaCoeficiente coeficiente = null;		
		try {
			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1, empresa);
			this.stmt.setLong(2, organizacao);
			this.stmt.setString(3,"%"+  nome + "%");
			this.rsTabelaCoeficiente = this.stmt.executeQuery();
			while (rsTabelaCoeficiente.next()) {
				coeficiente = new TabelaCoeficiente();
				coeficiente.setTabelaCoeficiente_id(rsTabelaCoeficiente.getLong("tabelacoeficiente_id"));
				coeficiente.setNome(rsTabelaCoeficiente.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsTabelaCoeficiente, stmt, conn);
		return coeficiente;
	}

}