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
import br.com.sgo.modelo.Produto;

@Component
public class ProdutoDao extends Dao<Produto> {

	private Session session;
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsProdutos;

	public ProdutoDao(Session session, ConnJDBC conexao) {
		super(session, Produto.class);
		this.session = session;
		this.conexao = conexao;
	}

	public Collection<Produto> buscaProdutos(Long empresa_id,
			Long organizacao_id, String nome) {

		String sql = "select PRODUTO.produto_id, PRODUTO.nome from PRODUTO (NOLOCK) WHERE PRODUTO.empresa_id = ? AND PRODUTO.organizacao_id = ? AND PRODUTO.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<Produto> produtos = new ArrayList<Produto>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");
			this.rsProdutos = this.stmt.executeQuery();

			while (rsProdutos.next()) {
				Produto produto = new Produto();

				produto.setProduto_id(rsProdutos.getLong("produto_id"));
				produto.setNome(rsProdutos.getString("nome"));

				produtos.add(produto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsProdutos, stmt, conn);

		return produtos;

	}

}
