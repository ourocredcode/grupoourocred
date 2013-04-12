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
import br.com.sgo.modelo.Seguro;

@Component
public class SeguroDao extends Dao<Seguro> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsSeguro;

	private final String sqlSeguro = "SELECT SEGURO.seguro_id, SEGURO.nome, SEGURO.empresa_id"
			+ ", SEGURO.organizacao_id FROM SEGURO (NOLOCK)";

	public SeguroDao(Session session, ConnJDBC conexao) {
		super(session, Seguro.class);
		this.conexao = conexao;
	}

	public Collection<Seguro> buscaAllSeguro() {
		String sql = sqlSeguro;
		this.conn = this.conexao.getConexao();
		Collection<Seguro> seguros = new ArrayList<Seguro>();
		try {
			this.stmt = conn.prepareStatement(sql);
			this.rsSeguro = this.stmt.executeQuery();
			while (rsSeguro.next()) {
				Seguro seguro = new Seguro();
				seguro.setSeguro_id(rsSeguro.getLong("seguro_id"));
				seguro.setNome(rsSeguro.getString("nome"));
				seguros.add(seguro);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsSeguro, stmt, conn);
		return seguros;
	}

	public Collection<Seguro> buscaSeguroByNome(String nome) {
		String sql = sqlSeguro;
		if (nome != null)
			sql += " WHERE SEGURO.nome like ?";
		this.conn = this.conexao.getConexao();
		Collection<Seguro> seguros = new ArrayList<Seguro>();
		try {
			this.stmt = conn.prepareStatement(sql);
			this.stmt.setString(1, "%" + nome + "%");
			this.rsSeguro = this.stmt.executeQuery();
			Seguro seguro = new Seguro();
			while (rsSeguro.next()) {
				seguro.setSeguro_id(rsSeguro.getLong("seguro_id"));
				seguro.setNome(rsSeguro.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsSeguro, stmt, conn);
		return seguros;
	}

	public Seguro buscaSeguroByEmOrNo(Long empresa, Long organizacao,
			String nome) {
		String sql = sqlSeguro;
		if (empresa != null)
			sql += " AND SEGURO.empresa_id = ?";
		if (organizacao != null)
			sql += " AND SEGURO.organizacao_id = ?";
		if (nome != null)
			sql += " AND (SEGURO.nome like ?)";
		this.conn = this.conexao.getConexao();
		Seguro seguro = null;
		try {
			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa);
			this.stmt.setLong(2, organizacao);
			this.stmt.setString(3, "%" + nome + "%");
			this.rsSeguro = this.stmt.executeQuery();
			while (rsSeguro.next()) {
				seguro = new Seguro();
				seguro.setSeguro_id(rsSeguro.getLong("seguro_id"));
				seguro.setNome(rsSeguro.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsSeguro, stmt, conn);
		return seguro;
	}

}