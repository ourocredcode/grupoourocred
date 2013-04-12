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
import br.com.sgo.modelo.TipoConta;

@Component
public class TipoContaDao extends Dao<TipoConta> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsTipoConta;

	private final String sqlTiposConta = "SELECT TIPOCONTA.tipoconta_id, TIPOCONTA.nome, TIPOCONTA.empresa_id, TIPOCONTA.organizacao_id FROM TIPOCONTA (NOLOCK)";

	public TipoContaDao(Session session, ConnJDBC conexao) {
		super(session, TipoConta.class);
		this.conexao = conexao;
	}

	public Collection<TipoConta> buscaAllTiposConta() {
		String sql = sqlTiposConta;
		this.conn = this.conexao.getConexao();
		Collection<TipoConta> tiposconta = new ArrayList<TipoConta>();
		try {
			this.stmt = conn.prepareStatement(sql);
			this.rsTipoConta = this.stmt.executeQuery();
			while (rsTipoConta.next()) {
				TipoConta tipoconta = new TipoConta();
				tipoconta.setTipoConta_id(rsTipoConta.getLong("tipoconta_id"));
				tipoconta.setNome(rsTipoConta.getString("nome"));
				tiposconta.add(tipoconta);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsTipoConta, stmt, conn);
		return tiposconta;
	}

	public Collection<TipoConta> buscaTipoContaByName(String nome) {
		String sql = sqlTiposConta;
		if (nome != null)
			sql += " WHERE TIPOCONTA.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<TipoConta> tiposconta = new ArrayList<TipoConta>();
		try {
			this.stmt = conn.prepareStatement(sql);
			this.stmt.setString(1, "%" + nome + "%");

			this.rsTipoConta = this.stmt.executeQuery();
			TipoConta tipoconta = new TipoConta();
			while (rsTipoConta.next()) {
				tipoconta.setTipoConta_id(rsTipoConta.getLong("tipoconta_id"));
				tipoconta.setNome(rsTipoConta.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsTipoConta, stmt, conn);
		return tiposconta;
	}

	public TipoConta buscaTipoContaByEmpOrgName(Long empresa_id,
			Long organizacao_id, String nome) {

		String sql = sqlTiposConta;

		if (empresa_id != null)
			sql += " WHERE TIPOCONTA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND TIPOCONTA.organizacao_id = ?";
		if (nome != null)
			sql += " AND (TIPOCONTA.nome like ?)";

		this.conn = this.conexao.getConexao();

		TipoConta tipoConta = null;

		try {
			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");

			this.rsTipoConta = this.stmt.executeQuery();

			while (rsTipoConta.next()) {

				tipoConta = new TipoConta();

				tipoConta.setTipoConta_id(rsTipoConta.getLong("tipoconta_id"));
				tipoConta.setNome(rsTipoConta.getString("nome"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsTipoConta, stmt, conn);
		return tipoConta;
	}
}