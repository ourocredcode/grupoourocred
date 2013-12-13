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
import br.com.sgo.modelo.CategoriaProduto;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.GrupoProduto;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Produto;
import br.com.sgo.modelo.SubGrupoProduto;
import br.com.sgo.modelo.TipoProduto;

@Component
public class ProdutoDao extends Dao<Produto> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsProdutos;
	
	private final String sqlProdutos = " SELECT PRODUTO.produto_id, PRODUTO.isactive, PRODUTO.nome AS produto_nome "+
								", PRODUTO.isprodutocontrato, PRODUTO.empresa_id, EMPRESA.nome AS empresa_nome "+
								", PRODUTO.organizacao_id, ORGANIZACAO.nome AS organizacao_nome, PRODUTO.categoriaproduto_id "+
								", CATEGORIAPRODUTO.nome AS categoriaproduto_nome, PRODUTO.grupoproduto_id, GRUPOPRODUTO.nome AS grupoproduto_nome "+
								", PRODUTO.subgrupoproduto_id, SUBGRUPOPRODUTO.nome AS subgrupoproduto_nome "+
								", PRODUTO.tipoproduto_id, TIPOPRODUTO.nome AS tipoproduto_nome "+
								" FROM ((((ORGANIZACAO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) "+
								" INNER JOIN PRODUTO (NOLOCK) ON EMPRESA.empresa_id = PRODUTO.empresa_id) ON ORGANIZACAO.organizacao_id = PRODUTO.organizacao_id) "+ 
								" INNER JOIN CATEGORIAPRODUTO (NOLOCK) ON PRODUTO.categoriaproduto_id = CATEGORIAPRODUTO.categoriaproduto_id) "+
								" INNER JOIN GRUPOPRODUTO (NOLOCK) ON PRODUTO.grupoproduto_id = GRUPOPRODUTO.grupoproduto_id) "+
								" INNER JOIN SUBGRUPOPRODUTO (NOLOCK) ON PRODUTO.subgrupoproduto_id = SUBGRUPOPRODUTO.subgrupoproduto_id) "+ 
								" INNER JOIN TIPOPRODUTO (NOLOCK) ON PRODUTO.tipoproduto_id = TIPOPRODUTO.tipoproduto_id ";

	public ProdutoDao(Session session, ConnJDBC conexao) {

		super(session, Produto.class);
		this.conexao = conexao;

	}

	public Collection<Produto> buscaAllProdutosByEmpOrg(Long empresa_id,Long organizacao_id) {

		String sql = sqlProdutos;
		
		if (empresa_id != null)
			sql += " WHERE PRODUTO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND PRODUTO.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<Produto> produtos = new ArrayList<Produto>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsProdutos = this.stmt.executeQuery();

			while (rsProdutos.next()) {

				getProduto(produtos);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsProdutos, stmt, conn);

		return produtos;

	}

	public Collection<Produto> buscaProdutoBancoByEmpOrgBanco(Long empresa_id,Long organizacao_id, Long banco_id) {

		String sql = "SELECT BANCOPRODUTO.empresa_id, EMPRESA.nome as empresa_nome, BANCOPRODUTO.organizacao_id "+
					", ORGANIZACAO.nome AS organizacao_nome, BANCOPRODUTO.produto_id, PRODUTO.nome AS produto_nome "+
					" FROM ((BANCOPRODUTO (NOLOCK) INNER JOIN EMPRESA (NOLOCK) ON BANCOPRODUTO.empresa_id = EMPRESA.empresa_id) "+ 
					" INNER JOIN ORGANIZACAO (NOLOCK) ON BANCOPRODUTO.organizacao_id = ORGANIZACAO.organizacao_id) "+
					" INNER JOIN PRODUTO (NOLOCK) ON BANCOPRODUTO.produto_id = PRODUTO.produto_id " +
					" WHERE BANCOPRODUTO.empresa_id = ? AND BANCOPRODUTO.organizacao_id = ? AND BANCOPRODUTO.banco_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<Produto> produtos = new ArrayList<Produto>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, banco_id);

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
	
	public Collection<Produto> buscaProdutoBancoByEmpOrgBancoConvenio(Long empresa_id,Long organizacao_id, Long banco_id, Long convenio_id) {

		String sql = "SELECT BANCOPRODUTO.empresa_id, EMPRESA.nome as empresa_nome, BANCOPRODUTO.organizacao_id "+
					", ORGANIZACAO.nome AS organizacao_nome, BANCOPRODUTO.produto_id, PRODUTO.nome AS produto_nome "+
					" FROM ((BANCOPRODUTO (NOLOCK) INNER JOIN EMPRESA (NOLOCK) ON BANCOPRODUTO.empresa_id = EMPRESA.empresa_id) "+ 
					" INNER JOIN ORGANIZACAO (NOLOCK) ON BANCOPRODUTO.organizacao_id = ORGANIZACAO.organizacao_id) "+
					" INNER JOIN PRODUTO (NOLOCK) ON BANCOPRODUTO.produto_id = PRODUTO.produto_id " +
					" WHERE BANCOPRODUTO.empresa_id = ? AND BANCOPRODUTO.organizacao_id = ? AND BANCOPRODUTO.banco_id = ? and BANCOPRODUTO.convenio_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<Produto> produtos = new ArrayList<Produto>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, banco_id);
			this.stmt.setLong(4, convenio_id);

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

	public Collection<Produto> buscaProdutosByEmpOrg(Long empresa_id,Long organizacao_id) {

		String sql = "select PRODUTO.produto_id, PRODUTO.nome as produto_nome from PRODUTO (NOLOCK) WHERE PRODUTO.empresa_id = ? AND PRODUTO.organizacao_id = ?";

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
				produto.setNome(rsProdutos.getString("produto_nome"));

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

		String sql = "SELECT DISTINCT(BANCOPRODUTO.produto_id), PRODUTO.nome as produto_nome, BANCOPRODUTO.banco_id, BANCO.nome as banco_nome " +
				" FROM (BANCOPRODUTO (NOLOCK) INNER JOIN PRODUTO (NOLOCK) ON BANCOPRODUTO.produto_id = PRODUTO.produto_id) " +
				"	INNER JOIN BANCO (NOLOCK) ON BANCOPRODUTO.banco_id = BANCO.banco_id WHERE BANCO.banco_id = ? ";

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
				" WHERE PRODUTO.empresa_id = ? AND PRODUTO.organizacao_id = ? AND PRODUTO.categoriaproduto_id = ? AND PRODUTO.grupoproduto_id = ? " +
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
			this.stmt.setString(7, nome);

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
	
	private void getProduto(Collection<Produto> produtos) throws SQLException {

		Produto produto = new Produto();
		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();
		CategoriaProduto categoriaProduto = new CategoriaProduto();
		GrupoProduto grupoProduto = new GrupoProduto();
		SubGrupoProduto subGrupoProduto = new SubGrupoProduto();
		TipoProduto tipoProduto = new TipoProduto();

		empresa.setEmpresa_id(rsProdutos.getLong("empresa_id"));
		empresa.setNome(rsProdutos.getString("empresa_nome"));

		organizacao.setOrganizacao_id(rsProdutos.getLong("organizacao_id"));
		organizacao.setNome(rsProdutos.getString("organizacao_nome"));

		categoriaProduto.setCategoriaProduto_id(rsProdutos.getLong("categoriaproduto_id"));
		categoriaProduto.setNome(rsProdutos.getString("categoriaproduto_nome"));

		grupoProduto.setGrupoProduto_id(rsProdutos.getLong("grupoproduto_id"));
		grupoProduto.setNome(rsProdutos.getString("grupoproduto_nome"));

		subGrupoProduto.setSubGrupoProduto_id(rsProdutos.getLong("subgrupoproduto_id"));
		subGrupoProduto.setNome(rsProdutos.getString("subgrupoproduto_nome"));

		tipoProduto.setTipoProduto_id(rsProdutos.getLong("tipoproduto_id"));
		tipoProduto.setNome(rsProdutos.getString("tipoproduto_nome"));

		produto.setEmpresa(empresa);
		produto.setOrganizacao(organizacao);
		produto.setCategoriaProduto(categoriaProduto);
		produto.setGrupoProduto(grupoProduto);
		produto.setSubGrupoProduto(subGrupoProduto);
		produto.setTipoProduto(tipoProduto);

		produto.setProduto_id(rsProdutos.getLong("produto_id"));
		produto.setNome(rsProdutos.getString("produto_nome"));		
		produto.setIsActive(rsProdutos.getBoolean("isactive"));

		produtos.add(produto);

	}

}
