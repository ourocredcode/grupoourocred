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
import br.com.sgo.modelo.Banco;
import br.com.sgo.modelo.BancoProdutoTabela;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Produto;
import br.com.sgo.modelo.Tabela;

@Component
public class BancoProdutoTabelaDao extends Dao<BancoProdutoTabela> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsBancoProdutoTabela;

	private final String sqlBancoProdutoTabela = "SELECT BANCOPRODUTOTABELA.bancoprodutotabela_id, BANCOPRODUTOTABELA.empresa_id " +
										", BANCOPRODUTOTABELA.organizacao_id, BANCOPRODUTOTABELA.banco_id, BANCOPRODUTOTABELA.prazo " +
										", BANCOPRODUTOTABELA.produto_id, BANCOPRODUTOTABELA.tabela_id FROM BANCOPRODUTOTABELA ";

	private final String sqlBancoProdutoTabelas = "SELECT BANCOPRODUTOTABELA.empresa_id, EMPRESA.nome as empresa_nome, BANCOPRODUTOTABELA.organizacao_id "+
									", ORGANIZACAO.nome as organizacao_nome, BANCOPRODUTOTABELA.banco_id, BANCO.nome as banco_nome "+
									", BANCOPRODUTOTABELA.produto_id, PRODUTO.nome as produto_nome, BANCOPRODUTOTABELA.tabela_id, TABELA.nome as tabela_nome " +
									", BANCOPRODUTOTABELA.prazo, BANCOPRODUTOTABELA.isactive "+
									" FROM (TABELA (NOLOCK) INNER JOIN (PRODUTO (NOLOCK) INNER JOIN (ORGANIZACAO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) "+
									" INNER JOIN BANCOPRODUTOTABELA (NOLOCK) ON EMPRESA.empresa_id = BANCOPRODUTOTABELA.empresa_id) "+
									" ON ORGANIZACAO.organizacao_id = BANCOPRODUTOTABELA.organizacao_id) ON PRODUTO.produto_id = BANCOPRODUTOTABELA.produto_id) "+
									" ON TABELA.tabela_id = BANCOPRODUTOTABELA.tabela_id) INNER JOIN BANCO ON BANCOPRODUTOTABELA.banco_id = BANCO.banco_id ";

	public BancoProdutoTabelaDao(Session session, ConnJDBC conexao) {

		super(session, BancoProdutoTabela.class);
		this.conexao = conexao;

	}
	
	public Collection<BancoProdutoTabela> buscaAllBancoProdutoTabelaByEmpOrg(Long empresa_id, Long organizacao_id) {

		String sql = sqlBancoProdutoTabelas;
		
		if (empresa_id != null)
			sql += " WHERE BANCOPRODUTOTABELA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND BANCOPRODUTOTABELA.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<BancoProdutoTabela> bancoProdutoTabelas = new ArrayList<BancoProdutoTabela>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsBancoProdutoTabela = this.stmt.executeQuery();

			while (rsBancoProdutoTabela.next()) {

				getBancoProdutoTabela(bancoProdutoTabelas);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsBancoProdutoTabela, stmt, conn);

		return bancoProdutoTabelas;

	}
	
	public Collection<BancoProdutoTabela> buscaBancoProdutoTabelasByEmpOrgoBancoProdutoTabela(Long empresa_id, Long organizacao_id, Long banco_id, Long produto_id, Long tabela_id) {

		String sql = sqlBancoProdutoTabela;
		
		if (empresa_id != null)
			sql += " WHERE BANCOPRODUTOTABELA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND BANCOPRODUTOTABELA.organizacao_id = ?";
		if (banco_id != null)
			sql += " AND BANCOPRODUTOTABELA.banco_id = ?";
		if (produto_id != null)
			sql += " AND BANCOPRODUTOTABELA.produto_id = ?";
		if (tabela_id != null)
			sql += " AND BANCOPRODUTOTABELA.tabela_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<BancoProdutoTabela> bancoProdutoTabelas = new ArrayList<BancoProdutoTabela>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, banco_id);
			this.stmt.setLong(4, produto_id);
			this.stmt.setLong(5, tabela_id);

			this.rsBancoProdutoTabela = this.stmt.executeQuery();

			while (rsBancoProdutoTabela.next()) {

				BancoProdutoTabela bancoProdutoTabela = new BancoProdutoTabela();
				bancoProdutoTabela.setBancoProdutoTabela_id(rsBancoProdutoTabela.getLong("bancoprodutotabela_id"));
				bancoProdutoTabela.setPrazo(rsBancoProdutoTabela.getInt("prazo"));
				
				bancoProdutoTabelas.add(bancoProdutoTabela);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsBancoProdutoTabela, stmt, conn);

		return bancoProdutoTabelas;

	}
	
	public BancoProdutoTabela buscaBancoProdutoTabelaByEmpOrgoBancoProdutoTabela(Long empresa_id, Long organizacao_id, Long banco_id, Long produto_id, Long tabela_id) {

		String sql = sqlBancoProdutoTabela;

		if (empresa_id != null)
			sql += " WHERE BANCOPRODUTOTABELA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND BANCOPRODUTOTABELA.organizacao_id = ?";
		if (banco_id != null)
			sql += " AND BANCOPRODUTOTABELA.banco_id = ?";
		if (produto_id != null)
			sql += " AND BANCOPRODUTOTABELA.produto_id = ?";
		if (tabela_id != null)
			sql += " AND BANCOPRODUTOTABELA.tabela_id = ?";

		this.conn = this.conexao.getConexao();

		BancoProdutoTabela bancoProdutoTabela = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, banco_id);
			this.stmt.setLong(4, produto_id);
			this.stmt.setLong(5, tabela_id);

			this.rsBancoProdutoTabela= this.stmt.executeQuery();

			while (rsBancoProdutoTabela.next()) {

				bancoProdutoTabela = new BancoProdutoTabela();
				bancoProdutoTabela.setBancoProdutoTabela_id(rsBancoProdutoTabela.getLong("bancoprodutotabela_id"));
				bancoProdutoTabela.setPrazo(rsBancoProdutoTabela.getInt("prazo"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsBancoProdutoTabela, stmt, conn);
		return bancoProdutoTabela;

	}


	private void getBancoProdutoTabela(Collection<BancoProdutoTabela> bancoProdutoTabelas) throws SQLException {

		BancoProdutoTabela bancoProdutoTabela = new BancoProdutoTabela();

		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();
		Banco banco = new Banco();
		Produto produto = new Produto();
		Tabela tabela = new Tabela();
		
		empresa.setEmpresa_id(rsBancoProdutoTabela.getLong("empresa_id"));
		empresa.setNome(rsBancoProdutoTabela.getString("empresa_nome"));
		
		organizacao.setOrganizacao_id(rsBancoProdutoTabela.getLong("organizacao_id"));
		organizacao.setNome(rsBancoProdutoTabela.getString("organizacao_nome"));
		
		banco.setBanco_id(rsBancoProdutoTabela.getLong("banco_id"));
		banco.setNome(rsBancoProdutoTabela.getString("banco_nome"));
		
		produto.setProduto_id(rsBancoProdutoTabela.getLong("produto_id"));
		produto.setNome(rsBancoProdutoTabela.getString("produto_nome"));
		
		tabela.setTabela_id(rsBancoProdutoTabela.getLong("tabela_id"));
		tabela.setNome(rsBancoProdutoTabela.getString("tabela_nome"));

		bancoProdutoTabela.setEmpresa(empresa);
		bancoProdutoTabela.setOrganizacao(organizacao);
		bancoProdutoTabela.setBanco(banco);
		bancoProdutoTabela.setProduto(produto);
		bancoProdutoTabela.setTabela(tabela);
		bancoProdutoTabela.setPrazo(rsBancoProdutoTabela.getInt("prazo"));
		bancoProdutoTabela.setIsActive(rsBancoProdutoTabela.getBoolean("isactive"));

		bancoProdutoTabelas.add(bancoProdutoTabela);

	}

}
