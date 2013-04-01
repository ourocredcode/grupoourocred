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
import br.com.sgo.modelo.Convenio;

@Component
public class ConvenioDao extends Dao<Convenio> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsConvenio;
	
	private final String sqlConvenio= "SELECT CONVENIO.convenio_id, CONVENIO.nome, CONVENIO.empresa_id" +
			", CONVENIO.organizacao_id FROM CONVENIO (NOLOCK)";  
	
	public ConvenioDao(Session session, ConnJDBC conexao) {
		super(session, Convenio.class);
		this.conexao = conexao;
	}

	public Collection<Convenio> buscaAllConvenio(){
		String sql = sqlConvenio;
		this.conn = this.conexao.getConexao();
		Collection<Convenio> convenios = new ArrayList<Convenio>();
		try {
			this.stmt = conn.prepareStatement(sql);
			this.rsConvenio = this.stmt.executeQuery();
			while (rsConvenio.next()) {				
				Convenio convenio = new Convenio();
				convenio.setConvenio_id(rsConvenio.getLong("convenio_id"));
				convenio.setNome(rsConvenio.getString("nome"));
				convenios.add(convenio);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsConvenio, stmt, conn);
		return convenios;
	}

	public Collection<Convenio> buscaConvenioByNome(String nome){
		String sql = sqlConvenio;
		if(nome != null)
			sql += 	" WHERE CONVENIO.nome like ?";
		this.conn = this.conexao.getConexao();
		Collection<Convenio> convenios = new ArrayList<Convenio>();
		try {
			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setString(1,"%"+  nome + "%");
			this.rsConvenio = this.stmt.executeQuery();
			Convenio convenio = new Convenio();
			while (rsConvenio.next()) {
				convenio.setConvenio_id(rsConvenio.getLong("convenio_id"));
				convenio.setNome(rsConvenio.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsConvenio, stmt, conn);
		return convenios;
	}
	
	public Convenio buscaConvenioByEmOrNo(Long empresa, Long organizacao, String nome){
		String sql = sqlConvenio;
		if(empresa != null)
			sql += 	" AND CONVENIO.empresa_id = ?";
		if(organizacao != null)
			sql += 	" AND CONVENIO.organizacao_id = ?";		
		if(nome != null)
			sql += 	" AND (CONVENIO.nome like ?)";
		this.conn = this.conexao.getConexao();		
		Convenio convenio = null;		
		try {
			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1, empresa);
			this.stmt.setLong(2, organizacao);
			this.stmt.setString(3,"%"+  nome + "%");
			this.rsConvenio = this.stmt.executeQuery();
			while (rsConvenio.next()) {
				convenio = new Convenio();
				convenio.setConvenio_id(rsConvenio.getLong("convenio_id"));
				convenio.setNome(rsConvenio.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsConvenio, stmt, conn);
		return convenio;
	}

}