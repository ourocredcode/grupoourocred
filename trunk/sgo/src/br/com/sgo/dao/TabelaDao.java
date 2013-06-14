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
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Tabela;
import br.com.sgo.modelo.TipoTabela;

@Component
public class TabelaDao extends Dao<Tabela> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsTabelas;
	
	private final String sqlTabela = "SELECT TABELA.tabela_id, TABELA.empresa_id, TABELA.organizacao_id, TABELA.tipotabela_id, TABELA.nome FROM TABELA (NOLOCK) ";

	private final String sqlTabelas = "SELECT TABELA.empresa_id, EMPRESA.nome as empresa_nome, TABELA.organizacao_id "+
			", ORGANIZACAO.nome as organizacao_nome, TABELA.tabela_id, TABELA.nome AS tabela_nome, TABELA.isactive "+
			", TABELA.tipotabela_id, TIPOTABELA.nome as tipotabela_nome "+
			" FROM ((TABELA (NOLOCK) INNER JOIN TIPOTABELA (NOLOCK) ON TABELA.tipotabela_id = TIPOTABELA.tipotabela_id) "+ 
			" INNER JOIN EMPRESA (NOLOCK) ON TABELA.empresa_id = EMPRESA.empresa_id) "+
			" INNER JOIN ORGANIZACAO (NOLOCK) ON TABELA.organizacao_id = ORGANIZACAO.organizacao_id ";

	public TabelaDao(Session session, ConnJDBC conexao) {

		super(session, Tabela.class);
		this.conexao = conexao;

	}

	public Collection<Tabela> buscaAllTabela(Long empresa_id,Long organizacao_id) {

		String sql = sqlTabelas;
		
		if (empresa_id != null)
			sql += " WHERE TABELA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND TABELA.organizacao_id = ? ORDER BY TABELA.nome";

		this.conn = this.conexao.getConexao();

		Collection<Tabela> tabelas = new ArrayList<Tabela>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsTabelas = this.stmt.executeQuery();

			while (rsTabelas.next()) {

				getTabela(tabelas);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsTabelas, stmt, conn);

		return tabelas;

	}

	public Collection<Tabela> buscaTabelas(Long empresa_id,Long organizacao_id, String nome) {

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

		String sql = " SELECT BANCOPRODUTOTABELA.tabela_id, TABELA.nome as tabela_nome " +
				"				FROM ((BANCOPRODUTOTABELA (NOLOCK) " +
				"		INNER JOIN PRODUTO (NOLOCK) ON BANCOPRODUTOTABELA.produto_id = PRODUTO.produto_id) " +
				"		INNER JOIN BANCO (NOLOCK) ON BANCOPRODUTOTABELA.banco_id = BANCO.banco_id) " +
				"		INNER JOIN TABELA (NOLOCK) ON BANCOPRODUTOTABELA.tabela_id = TABELA.tabela_id " +
				"			 WHERE BANCO.banco_id = ? ";

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

	public Tabela buscaTabelasByEmpOrgTipoNome(Long empresa_id, Long organizacao_id, Long tipoTabela_id, String nome) {

		String sql = sqlTabela;

		if (empresa_id != null)
			sql += " WHERE TABELA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND TABELA.organizacao_id = ?";
		if (tipoTabela_id != null)
			sql += " AND TABELA.tipotabela_id = ?";
		if (nome != null)
			sql += " AND TABELA.nome = ?";

		this.conn = this.conexao.getConexao();

		Tabela tabela = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, tipoTabela_id);
			this.stmt.setString(4, nome);

			this.rsTabelas = this.stmt.executeQuery();

			while (rsTabelas.next()) {
				
				tabela = new Tabela();

				tabela.setTabela_id(rsTabelas.getLong("tabela_id"));
				tabela.setNome(rsTabelas.getString("nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			this.conexao.closeConnection(rsTabelas, stmt, conn);

		}

		return tabela;

	}
	
	public Tabela buscaTabelasByCoeficiente(Long coeficiente_id) {

		String sql = " SELECT " +
				"			COEFICIENTE.coeficiente_id, COEFICIENTE.tabela_id, TABELA.nome as tabela_nome, TABELA.prazo " +
				"	FROM COEFICIENTE INNER JOIN TABELA ON COEFICIENTE.tabela_id = TABELA.tabela_id WHERE COEFICIENTE.coeficiente_id = ? ";
		
		this.conn = this.conexao.getConexao();

		Tabela tabela = new Tabela();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, coeficiente_id);

			this.rsTabelas = this.stmt.executeQuery();

			while (rsTabelas.next()) {

				tabela.setTabela_id(rsTabelas.getLong("tabela_id"));
				tabela.setNome(rsTabelas.getString("tabela_nome"));
				//TODO
				//tabela.setPrazo(rsTabelas.getInt("prazo"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			this.conexao.closeConnection(rsTabelas, stmt, conn);

		}

		return tabela;

	}
	
	private void getTabela(Collection<Tabela> tabelas) throws SQLException {

		Tabela tabela = new Tabela();				
		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();
		TipoTabela tipoTabela = new TipoTabela();

		empresa.setEmpresa_id(rsTabelas.getLong("empresa_id"));
		empresa.setNome(rsTabelas.getString("empresa_nome"));

		organizacao.setOrganizacao_id(rsTabelas.getLong("organizacao_id"));
		organizacao.setNome(rsTabelas.getString("organizacao_nome"));

		tipoTabela.setTipoTabela_id(rsTabelas.getLong("tipotabela_id"));
		tipoTabela.setNome(rsTabelas.getString("tipotabela_nome"));


		tabela.setEmpresa(empresa);
		tabela.setOrganizacao(organizacao);
		tabela.setTipoTabela(tipoTabela);
		
		tabela.setTabela_id(rsTabelas.getLong("tabela_id"));
		tabela.setNome(rsTabelas.getString("tabela_nome"));
		tabela.setIsActive(rsTabelas.getBoolean("isactive"));

		tabelas.add(tabela);

	}

}
