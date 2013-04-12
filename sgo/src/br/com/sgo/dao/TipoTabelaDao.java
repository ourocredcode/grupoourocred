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
import br.com.sgo.modelo.TipoTabela;

@Component
public class TipoTabelaDao extends Dao<TipoTabela> {

	private Session session;
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsTiposTabela;

	public TipoTabelaDao(Session session, ConnJDBC conexao) {
		super(session, TipoTabela.class);
		this.session = session;
		this.conexao = conexao;
	}

	public Collection<TipoTabela> buscaTiposTabela(Long empresa_id,
			Long organizacao_id, String nome) {

		String sql = "select TIPOTABELA.tipotabela_id, TIPOTABELA.nome from TIPOTABELA (NOLOCK) "
				+ "	WHERE TIPOTABELA.empresa_id = ? AND TIPOTABELA.organizacao_id = ? AND TIPOTABELA.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<TipoTabela> tiposTabela = new ArrayList<TipoTabela>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");
			this.rsTiposTabela = this.stmt.executeQuery();

			while (rsTiposTabela.next()) {
				TipoTabela tipoTabela = new TipoTabela();

				tipoTabela.setTipoTabela_id(rsTiposTabela
						.getLong("tipotabela_id"));
				tipoTabela.setNome(rsTiposTabela.getString("nome"));

				tiposTabela.add(tipoTabela);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsTiposTabela, stmt, conn);

		return tiposTabela;

	}

	public Collection<TipoTabela> buscaTiposTabela(Long empresa_id,
			Long organizacao_id) {

		String sql = "select TIPOTABELA.tipotabela_id , TIPOTABELA.nome from TIPOTABELA (NOLOCK) "
				+ "	WHERE TIPOTABELA.empresa_id = ? AND TIPOTABELA.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<TipoTabela> tiposTabela = new ArrayList<TipoTabela>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.rsTiposTabela = this.stmt.executeQuery();

			while (rsTiposTabela.next()) {
				TipoTabela tipoTabela = new TipoTabela();

				tipoTabela.setTipoTabela_id(rsTiposTabela
						.getLong("tipotabela_id"));
				tipoTabela.setNome(rsTiposTabela.getString("nome"));

				tiposTabela.add(tipoTabela);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsTiposTabela, stmt, conn);

		return tiposTabela;

	}

}
