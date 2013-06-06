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

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsProdutos;

	public ProdutoDao(Session session, ConnJDBC conexao) {
		super(session, Produto.class);
		this.conexao = conexao;
	}
	
	public Collection<Produto> buscaProdutosByEmpOrg(Long empresa_id,Long organizacao_id) {

		String sql = "select PRODUTO.produto_id, PRODUTO.nome from PRODUTO (NOLOCK) WHERE PRODUTO.empresa_id = ? AND PRODUTO.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<Produto> produtos = new ArrayList<Produto>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

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

	
	public Collection<Produto> buscaProdutos(Long empresa_id,Long organizacao_id, String nome) {

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
	
	public Collection<Produto> buscaProdutosByBanco(Long banco_id) {

		String sql = "SELECT DISTINCT PRODUTOBANCO.produto_id, PRODUTO.nome as produto_nome, PRODUTOBANCO.banco_id, BANCO.nome as banco_nome " +
				" FROM (PRODUTOBANCO (NOLOCK) INNER JOIN PRODUTO (NOLOCK) ON PRODUTOBANCO.produto_id = PRODUTO.produto_id) " +
				"	INNER JOIN BANCO (NOLOCK) ON PRODUTOBANCO.banco_id = BANCO.banco_id WHERE BANCO.banco_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<Produto> produtos = new ArrayList<Produto>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, banco_id);

			this.rsProdutos = this.stmt.executeQuery();

			while (rsProdutos.next()) {

				Produto produto = new Produto();

				produto.setProduto_id(rsProdutos.getLong("produto_id"));
				produto.setNome(rsProdutos.getString("produto_nome"));

				produtos.add(produto);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsProdutos, stmt, conn);

		return produtos;

	}
	
	public Produto buscaProdutoById(Long produto_id) {

		String sql = "select PRODUTO.produto_id, PRODUTO.nome from PRODUTO (NOLOCK) WHERE PRODUTO.produto_id = ? ";

		this.conn = this.conexao.getConexao();

		Produto produto = new Produto();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, produto_id);

			this.rsProdutos = this.stmt.executeQuery();

			while (rsProdutos.next()) {

				produto.setProduto_id(rsProdutos.getLong("produto_id"));
				produto.setNome(rsProdutos.getString("nome"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsProdutos, stmt, conn);

		return produto;

	}
	
	public Produto buscaProdutoByEmpresaOrgCategoriaGrupoSubGrupoTipoNome(Long empresa_id, Long organizacao_id, Long categoria_id, Long grupoProduto_id
			, Long subGrupoProduto_id, Long tipoProduto_id, String nome) {
		
		String sql = "SELECT PRODUTO.produto_id, PRODUTO.nome from PRODUTO (NOLOCK) " +
				" WHERE PRODUTO.empresa_id = ? AND PRODUTO.organizacao_id = ? AND PRODUTO.categoria_id = ? AND PRODUTO.grupoproduto_id = ? " +
				" AND PRODUTO.subgrupoproduto_id = ? AND PRODUTO.tipoproduto_id = ? AND PRODUTO.nome = ?";

		this.conn = this.conexao.getConexao();

		Produto produto = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, categoria_id);
			this.stmt.setLong(4, grupoProduto_id);
			this.stmt.setLong(5, subGrupoProduto_id);
			this.stmt.setLong(6, tipoProduto_id);
			this.stmt.setString(7, "%" + nome + "%");

			this.rsProdutos = this.stmt.executeQuery();

			while (rsProdutos.next()) {

				produto = new Produto();
				produto.setProduto_id(rsProdutos.getLong("produto_id"));
				produto.setNome(rsProdutos.getString("nome"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsProdutos, stmt, conn);
		
		return produto;
	}
}
