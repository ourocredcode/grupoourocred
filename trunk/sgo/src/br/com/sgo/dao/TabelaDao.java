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
import br.com.sgo.modelo.Tabela;

@Component
public class TabelaDao extends Dao<Tabela> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsTabelas;

	public TabelaDao(Session session, ConnJDBC conexao) {
		super(session, Tabela.class);
		this.conexao = conexao;
	}

	public Collection<Tabela> buscaTabelas(Long empresa_id,
			Long organizacao_id, String nome) {

		String sql = "select TABELA.tabela_id, TABELA.nome from TABELA (NOLOCK) WHERE TABELA.empresa_id = ? AND TABELA.organizacao_id = ? AND TABELA.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<Tabela> tabelas = new ArrayList<Tabela>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");
			this.rsTabelas = this.stmt.executeQuery();

			while (rsTabelas.next()) {
				Tabela tabela = new Tabela();

				tabela.setTabela_id(rsTabelas.getLong("tabela_id"));
				tabela.setNome(rsTabelas.getString("nome"));

				tabelas.add(tabela);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsTabelas, stmt, conn);

		return tabelas;

	}
	
	public Collection<Tabela> buscaTabelasByProduto(Long produto_id) {

		String sql = "SELECT PRODUTOBANCO.tabela_id, TABELA.nome as tabela_nome FROM " +
				"			((PRODUTOBANCO (NOLOCK) INNER JOIN PRODUTO (NOLOCK) ON PRODUTOBANCO.produto_id = PRODUTO.produto_id) " +
				"		INNER JOIN BANCO (NOLOCK) ON PRODUTOBANCO.banco_id = BANCO.banco_id) " +
				"		INNER JOIN TABELA (NOLOCK) ON PRODUTOBANCO.tabela_id = TABELA.tabela_id  WHERE PRODUTO.produto_id = ? ";
		
		this.conn = this.conexao.getConexao();

		Collection<Tabela> tabelas = new ArrayList<Tabela>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, produto_id);

			this.rsTabelas = this.stmt.executeQuery();

			while (rsTabelas.next()) {

				Tabela tabela = new Tabela();

				tabela.setTabela_id(rsTabelas.getLong("tabela_id"));
				tabela.setNome(rsTabelas.getString("tabela_nome"));

				tabelas.add(tabela);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsTabelas, stmt, conn);

		return tabelas;

	}
	
	public Collection<Tabela> buscaTabelasByBanco(Long banco_id) {

		String sql = " SELECT PRODUTOBANCO.tabela_id, TABELA.nome as tabela_nome FROM  " +
				"		((PRODUTOBANCO (NOLOCK) INNER JOIN PRODUTO (NOLOCK) ON PRODUTOBANCO.produto_id = PRODUTO.produto_id) " +
				" INNER JOIN BANCO (NOLOCK) ON PRODUTOBANCO.banco_id = BANCO.banco_id)  " +
				" INNER JOIN TABELA (NOLOCK) ON PRODUTOBANCO.tabela_id = TABELA.tabela_id  WHERE BANCO.banco_id = ? ";
		
		this.conn = this.conexao.getConexao();

		Collection<Tabela> tabelas = new ArrayList<Tabela>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, banco_id);

			this.rsTabelas = this.stmt.executeQuery();

			while (rsTabelas.next()) {

				Tabela tabela = new Tabela();

				tabela.setTabela_id(rsTabelas.getLong("tabela_id"));
				tabela.setNome(rsTabelas.getString("tabela_nome"));

				tabelas.add(tabela);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			this.conexao.closeConnection(rsTabelas, stmt, conn);

		}

		return tabelas;

	}

}
