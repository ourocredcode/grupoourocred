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
import br.com.sgo.modelo.Modalidade;

@Component
public class ModalidadeDao extends Dao<Modalidade> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsModalidade;
	
	private final String sqlModalidade= "SELECT MODALIDADE.modalidade_id, MODALIDADE.nome, MODALIDADE.empresa_id" +
			", MODALIDADE.organizacao_id FROM MODALIDADE (NOLOCK)";  
	
	public ModalidadeDao(Session session, ConnJDBC conexao) {
		super(session, Modalidade.class);
		this.conexao = conexao;
	}

	public Collection<Modalidade> buscaAllModalidade(){
		String sql = sqlModalidade;
		this.conn = this.conexao.getConexao();
		Collection<Modalidade> modalidades = new ArrayList<Modalidade>();
		try {
			this.stmt = conn.prepareStatement(sql);
			this.rsModalidade = this.stmt.executeQuery();
			while (rsModalidade.next()) {				
				Modalidade modalidade = new Modalidade();
				modalidade.setModalidade_id(rsModalidade.getLong("modalidade_id"));
				modalidade.setNome(rsModalidade.getString("nome"));
				modalidades.add(modalidade);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsModalidade, stmt, conn);
		return modalidades;
	}

	public Collection<Modalidade> buscaModalidadeByNome(String nome){
		String sql = sqlModalidade;
		if(nome != null)
			sql += 	" WHERE MODALIDADE.nome like ?";
		this.conn = this.conexao.getConexao();
		Collection<Modalidade> modalidades = new ArrayList<Modalidade>();
		try {
			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setString(1,"%"+  nome + "%");
			this.rsModalidade = this.stmt.executeQuery();
			Modalidade modalidade = new Modalidade();
			while (rsModalidade.next()) {
				modalidade.setModalidade_id(rsModalidade.getLong("modalidade_id"));
				modalidade.setNome(rsModalidade.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsModalidade, stmt, conn);
		return modalidades;
	}
	
	public Modalidade buscaModalidadeByEmOrNo(Long empresa, Long organizacao, String nome){
		String sql = sqlModalidade;
		if(empresa != null)
			sql += 	" AND MODALIDADE.empresa_id = ?";
		if(organizacao != null)
			sql += 	" AND MODALIDADE.organizacao_id = ?";		
		if(nome != null)
			sql += 	" AND (MODALIDADE.nome like ?)";
		this.conn = this.conexao.getConexao();		
		Modalidade modalidade = null;		
		try {
			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1, empresa);
			this.stmt.setLong(2, organizacao);
			this.stmt.setString(3,"%"+  nome + "%");
			this.rsModalidade = this.stmt.executeQuery();
			while (rsModalidade.next()) {
				modalidade = new Modalidade();
				modalidade.setModalidade_id(rsModalidade.getLong("modalidade_id"));
				modalidade.setNome(rsModalidade.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsModalidade, stmt, conn);
		return modalidade;
	}

}