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
import br.com.sgo.modelo.Agencia;

@Component
public class AgenciaDao extends Dao<Agencia> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsAgencia;

	private final String sqlAgencias = "SELECT AGENCIA.agencia_id, AGENCIA.nome, AGENCIA.codigoagencia, AGENCIA.empresa_id, AGENCIA.organizacao_id, AGENCIA.banco_id FROM AGENCIA (NOLOCK)";

	public AgenciaDao(Session session, ConnJDBC conexao) {
		super(session, Agencia.class);
		this.conexao = conexao;
	}

	public Collection<Agencia> buscaAllAgencia() {
		String sql = sqlAgencias;
		this.conn = this.conexao.getConexao();
		Collection<Agencia> agencias = new ArrayList<Agencia>();
		try {
			this.stmt = conn.prepareStatement(sql);
			this.rsAgencia = this.stmt.executeQuery();
			while (rsAgencia.next()) {
				Agencia agencia = new Agencia();
				agencia.setAgencia_id(rsAgencia.getLong("agencia_id"));
				agencia.setNome(rsAgencia.getString("nome"));
				agencias.add(agencia);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsAgencia, stmt, conn);
		return agencias;
	}

	public Agencia buscaAgenciaByEmOrBanNome(Long empresa_id,
			Long organizacao_id, Long banco_id, String nome) {
		String sql = sqlAgencias;
		if (empresa_id != null)
			sql += " WHERE AGENCIA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND AGENCIA.organizacao_id = ?";
		if (banco_id != null)
			sql += " AND AGENCIA.banco_id = ?";
		if (nome != null)
			sql += " AND (AGENCIA.codigoagencia like ?)";
		this.conn = this.conexao.getConexao();
		Agencia agencia = null;
		try {
			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, banco_id);
			this.stmt.setString(4, "%" + nome + "%");
			this.rsAgencia = this.stmt.executeQuery();
			while (rsAgencia.next()) {
				agencia = new Agencia();
				agencia.setAgencia_id(rsAgencia.getLong("agencia_id"));
				agencia.setNome(rsAgencia.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsAgencia, stmt, conn);
		return agencia;
	}

	public Agencia buscaAgenciaByEmOrBaCa(Long empresa_id, Long organizacao_id,Long banco_id, String codigoagencia) {
		
		String sql = sqlAgencias;
		
		if (empresa_id != null)
			sql += " WHERE AGENCIA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND AGENCIA.organizacao_id = ?";
		if (banco_id != null)
			sql += " AND AGENCIA.banco_id = ?";
		if (codigoagencia != null)
			sql += " AND (AGENCIA.codigoagencia like ?)";
		
		this.conn = this.conexao.getConexao();
		
		Agencia agencia = null;
		
		try {
			
			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, banco_id);
			this.stmt.setString(4, "%" + codigoagencia + "%");
			
			this.rsAgencia = this.stmt.executeQuery();
			
			while (rsAgencia.next()) {
				
				agencia = new Agencia();
				agencia.setAgencia_id(rsAgencia.getLong("agencia_id"));
				agencia.setNome(rsAgencia.getString("nome"));
				
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
			
		}
		
		this.conexao.closeConnection(rsAgencia, stmt, conn);
		return agencia;
	}

}