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
import br.com.sgo.modelo.NaturezaProfissional;

@Component
public class NaturezaProfissionalDao extends Dao<NaturezaProfissional> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsNaturezaProfissional;

	private final String sqlNaturezaProfissional = "SELECT NATUREZAPROFISSIONAL.naturezaprofissional_id, NATUREZAPROFISSIONAL.nome, NATUREZAPROFISSIONAL.empresa_id"
			+ ", NATUREZAPROFISSIONAL.organizacao_id FROM NATUREZAPROFISSIONAL (NOLOCK)";

	public NaturezaProfissionalDao(Session session, ConnJDBC conexao) {
		super(session, NaturezaProfissional.class);
		this.conexao = conexao;
	}

	public Collection<NaturezaProfissional> buscaAllNaturezaProfissional() {
		String sql = sqlNaturezaProfissional;
		this.conn = this.conexao.getConexao();
		Collection<NaturezaProfissional> naturezas = new ArrayList<NaturezaProfissional>();
		try {
			this.stmt = conn.prepareStatement(sql);
			this.rsNaturezaProfissional = this.stmt.executeQuery();
			while (rsNaturezaProfissional.next()) {
				NaturezaProfissional natureza = new NaturezaProfissional();
				natureza.setNaturezaProfissional_id(rsNaturezaProfissional
						.getLong("naturezaprofissional_id"));
				natureza.setNome(rsNaturezaProfissional.getString("nome"));
				naturezas.add(natureza);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsNaturezaProfissional, stmt, conn);
		return naturezas;
	}

	public Collection<NaturezaProfissional> buscaNaturezaProfissionalByNome(
			String nome) {
		String sql = sqlNaturezaProfissional;
		if (nome != null)
			sql += " WHERE NATUREZAPROFISSIONAL.nome like ?";
		this.conn = this.conexao.getConexao();
		Collection<NaturezaProfissional> naturezas = new ArrayList<NaturezaProfissional>();
		try {
			this.stmt = conn.prepareStatement(sql);
			this.stmt.setString(1, "%" + nome + "%");
			this.rsNaturezaProfissional = this.stmt.executeQuery();
			NaturezaProfissional natureza = new NaturezaProfissional();
			while (rsNaturezaProfissional.next()) {
				natureza.setNaturezaProfissional_id(rsNaturezaProfissional
						.getLong("naturezaprofissional_id"));
				natureza.setNome(rsNaturezaProfissional.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsNaturezaProfissional, stmt, conn);
		return naturezas;
	}

	public NaturezaProfissional buscaNaturezaProfissionalByEmOrNo(Long empresa,
			Long organizacao, String nome) {
		String sql = sqlNaturezaProfissional;
		if (empresa != null)
			sql += " AND NATUREZAPROFISSIONAL.empresa_id = ?";
		if (organizacao != null)
			sql += " AND NATUREZAPROFISSIONAL.organizacao_id = ?";
		if (nome != null)
			sql += " AND (NATUREZAPROFISSIONAL.nome like ?)";
		this.conn = this.conexao.getConexao();
		NaturezaProfissional natureza = null;
		try {
			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa);
			this.stmt.setLong(2, organizacao);
			this.stmt.setString(3, "%" + nome + "%");
			this.rsNaturezaProfissional = this.stmt.executeQuery();
			while (rsNaturezaProfissional.next()) {
				natureza = new NaturezaProfissional();
				natureza.setNaturezaProfissional_id(rsNaturezaProfissional
						.getLong("naturezaprofissional_id"));
				natureza.setNome(rsNaturezaProfissional.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsNaturezaProfissional, stmt, conn);
		return natureza;
	}

}