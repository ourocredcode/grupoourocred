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
import br.com.sgo.modelo.TipoProduto;

@Component
public class TipoProdutoDao extends Dao<TipoProduto> {

	private Session session;
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsTipoProdutos;

	public TipoProdutoDao(Session session, ConnJDBC conexao) {
		super(session, TipoProduto.class);
		this.session = session;
		this.conexao = conexao;
	}

	public Collection<TipoProduto> buscaTipoProdutos(Long empresa_id,
			Long organizacao_id, String nome) {

		String sql = "select TIPOPRODUTO.tipoproduto_id, TIPOPRODUTO.nome from TIPOPRODUTO (NOLOCK) "
				+ "	WHERE TIPOPRODUTO.empresa_id = ? AND TIPOPRODUTO.organizacao_id = ? AND TIPOPRODUTO.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<TipoProduto> tipoProdutos = new ArrayList<TipoProduto>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");
			this.rsTipoProdutos = this.stmt.executeQuery();

			while (rsTipoProdutos.next()) {
				TipoProduto tipoProduto = new TipoProduto();

				tipoProduto.setTipoProduto_id(rsTipoProdutos
						.getLong("TipoProduto_id"));
				tipoProduto.setNome(rsTipoProdutos.getString("nome"));

				tipoProdutos.add(tipoProduto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsTipoProdutos, stmt, conn);

		return tipoProdutos;

	}

	public Collection<TipoProduto> buscaTipoProdutos(Long empresa_id,
			Long organizacao_id) {

		String sql = "select TIPOPRODUTO.TipoProduto_id, TIPOPRODUTO.nome from TIPOPRODUTO (NOLOCK) "
				+ "	WHERE TIPOPRODUTO.empresa_id = ? AND TIPOPRODUTO.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<TipoProduto> tipoProdutos = new ArrayList<TipoProduto>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.rsTipoProdutos = this.stmt.executeQuery();

			while (rsTipoProdutos.next()) {
				TipoProduto tipoProduto = new TipoProduto();

				tipoProduto.setTipoProduto_id(rsTipoProdutos
						.getLong("TipoProduto_id"));
				tipoProduto.setNome(rsTipoProdutos.getString("nome"));

				tipoProdutos.add(tipoProduto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsTipoProdutos, stmt, conn);

		return tipoProdutos;

	}

}
