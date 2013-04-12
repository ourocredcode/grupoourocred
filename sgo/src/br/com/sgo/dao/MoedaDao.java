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
import br.com.sgo.modelo.Moeda;

@Component
public class MoedaDao extends Dao<Moeda> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsMoeda;

	private final String sqlMoeda = "SELECT MOEDA.moeda_id, MOEDA.nome, MOEDA.empresa_id"
			+ ", MOEDA.organizacao_id FROM MOEDA (NOLOCK)";

	public MoedaDao(Session session, ConnJDBC conexao) {
		super(session, Moeda.class);
		this.conexao = conexao;
	}

	public Collection<Moeda> buscaAllMoeda() {
		String sql = sqlMoeda;
		this.conn = this.conexao.getConexao();
		Collection<Moeda> moedas = new ArrayList<Moeda>();
		try {
			this.stmt = conn.prepareStatement(sql);
			this.rsMoeda = this.stmt.executeQuery();
			while (rsMoeda.next()) {
				Moeda moeda = new Moeda();
				moeda.setMoeda_id(rsMoeda.getLong("moeda_id"));
				moeda.setNome(rsMoeda.getString("nome"));
				moedas.add(moeda);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsMoeda, stmt, conn);
		return moedas;
	}

	public Collection<Moeda> buscaMoedaByNome(String nome) {
		String sql = sqlMoeda;
		if (nome != null)
			sql += " WHERE MOEDA.nome like ?";
		this.conn = this.conexao.getConexao();
		Collection<Moeda> moedas = new ArrayList<Moeda>();
		try {
			this.stmt = conn.prepareStatement(sql);
			this.stmt.setString(1, "%" + nome + "%");
			this.rsMoeda = this.stmt.executeQuery();
			Moeda moeda = new Moeda();
			while (rsMoeda.next()) {
				moeda.setMoeda_id(rsMoeda.getLong("moeda_id"));
				moeda.setNome(rsMoeda.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsMoeda, stmt, conn);
		return moedas;
	}

	public Moeda buscaMoedaByEmOrNo(Long empresa, Long organizacao, String nome) {
		String sql = sqlMoeda;
		if (empresa != null)
			sql += " AND MOEDA.empresa_id = ?";
		if (organizacao != null)
			sql += " AND MOEDA.organizacao_id = ?";
		if (nome != null)
			sql += " AND (MOEDA.nome like ?)";
		this.conn = this.conexao.getConexao();
		Moeda moeda = null;
		try {
			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa);
			this.stmt.setLong(2, organizacao);
			this.stmt.setString(3, "%" + nome + "%");
			this.rsMoeda = this.stmt.executeQuery();
			while (rsMoeda.next()) {
				moeda = new Moeda();
				moeda.setMoeda_id(rsMoeda.getLong("moeda_id"));
				moeda.setNome(rsMoeda.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsMoeda, stmt, conn);
		return moeda;
	}

}