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

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsTipoProdutos;

	public TipoProdutoDao(Session session, ConnJDBC conexao) {

		super(session, TipoProduto.class);
		this.conexao = conexao;

	}

	public Collection<TipoProduto> buscaTipoProdutosByEmpOrgNome(String nome) {

		String sql = "select TIPOPRODUTO.tipoproduto_id, TIPOPRODUTO.nome from TIPOPRODUTO (NOLOCK) "
				+ "	WHERE TIPOPRODUTO.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<TipoProduto> tiposProduto = new ArrayList<TipoProduto>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setString(1, "%" + nome + "%");

			this.rsTipoProdutos = this.stmt.executeQuery();

			while (rsTipoProdutos.next()) {
				TipoProduto tipoProduto = new TipoProduto();

				tipoProduto.setTipoProduto_id(rsTipoProdutos.getLong("TipoProduto_id"));
				tipoProduto.setNome(rsTipoProdutos.getString("nome"));

				tiposProduto.add(tipoProduto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsTipoProdutos, stmt, conn);

		return tiposProduto;

	}

	public Collection<TipoProduto> buscaAllTiposProdutoByEmpresaOrganizacao() {

		String sql = "select TIPOPRODUTO.tipoproduto_id, TIPOPRODUTO.nome from TIPOPRODUTO (NOLOCK) ";

		this.conn = this.conexao.getConexao();

		Collection<TipoProduto> tiposProduto = new ArrayList<TipoProduto>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.rsTipoProdutos = this.stmt.executeQuery();

			while (rsTipoProdutos.next()) {
				TipoProduto tipoProduto = new TipoProduto();

				tipoProduto.setTipoProduto_id(rsTipoProdutos.getLong("tipoproduto_id"));
				tipoProduto.setNome(rsTipoProdutos.getString("nome"));

				tiposProduto.add(tipoProduto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsTipoProdutos, stmt, conn);

		return tiposProduto;

	}

}
