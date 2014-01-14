package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Banco;
import br.com.sgo.modelo.Coeficiente;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Tabela;


@Component
public class CoeficienteDao extends Dao<Coeficiente> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsCoeficiente;

	private String sqlCoeficientes = "SELECT COEFICIENTE.empresa_id, EMPRESA.nome AS empresa_nome, COEFICIENTE.organizacao_id "+
									", ORGANIZACAO.nome AS organizacao_nome, COEFICIENTE.banco_id, BANCO.nome AS banco_nome, COEFICIENTE.tabela_id "+
									", TABELA.nome AS tabela_nome, COEFICIENTE.coeficiente_id, COEFICIENTE.created, COEFICIENTE.updated, COEFICIENTE.isactive "+
									", COEFICIENTE.percentualmeta, COEFICIENTE.valor "+
									" FROM (((COEFICIENTE (NOLOCK) INNER JOIN BANCO (NOLOCK) ON COEFICIENTE.banco_id = BANCO.banco_id) "+ 
									" INNER JOIN TABELA (NOLOCK) ON COEFICIENTE.tabela_id = TABELA.tabela_id) "+
									" INNER JOIN EMPRESA (NOLOCK) ON COEFICIENTE.empresa_id = EMPRESA.empresa_id) "+
									" INNER JOIN ORGANIZACAO (NOLOCK) ON COEFICIENTE.organizacao_id = ORGANIZACAO.organizacao_id ";

	public CoeficienteDao(Session session, ConnJDBC conexao) {

		super(session, Coeficiente.class);
		this.conexao = conexao;

	}

	public Collection<Coeficiente> buscaAllCoeficientesByEmpOrg(Long empresa_id, Long organizacao_id) {

		String sql = sqlCoeficientes;

		if (empresa_id != null)
			sql += " WHERE COEFICIENTE.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND COEFICIENTE.organizacao_id = ?";
		
		sql += " AND COEFICIENTE.isactive = 1 ";
		
		this.conn = this.conexao.getConexao();

		Collection<Coeficiente> coeficientes = new ArrayList<Coeficiente>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsCoeficiente = this.stmt.executeQuery();

			while (rsCoeficiente.next()) {

				getCoeficientes(coeficientes);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsCoeficiente, stmt, conn);
		return coeficientes;
	}

	public Collection<Coeficiente> buscaCoeficientesToMenuByEmpOrg(Long empresa_id, Long organizacao_id) {

		String sql = sqlCoeficientes;

		if (empresa_id != null)
			sql += " WHERE COEFICIENTE.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND COEFICIENTE.organizacao_id = ? AND COEFICIENTE.isactive = 1 ";
		
		this.conn = this.conexao.getConexao();

		Collection<Coeficiente> coeficientes = new ArrayList<Coeficiente>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsCoeficiente = this.stmt.executeQuery();

			while (rsCoeficiente.next()) {

				getCoeficientes(coeficientes);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsCoeficiente, stmt, conn);
		return coeficientes;
	}

	public Collection<Coeficiente> buscaCoeficientesByBancoProduto(Long banco_id, Long produto_id) {
		
		String sql = " SELECT COEFICIENTE.tabela_id, TABELA.nome AS tabela_nome, COEFICIENTE.coeficiente_id, COEFICIENTE.valor, COEFICIENTE.percentualmeta "+
				" FROM (((BANCOPRODUTOTABELA (NOLOCK) INNER JOIN TABELA (NOLOCK) ON BANCOPRODUTOTABELA.tabela_id = TABELA.tabela_id) "+ 
				" INNER JOIN COEFICIENTE (NOLOCK) ON TABELA.tabela_id = COEFICIENTE.tabela_id) "+ 
				" INNER JOIN BANCO (NOLOCK) ON BANCOPRODUTOTABELA.banco_id = BANCO.banco_id AND COEFICIENTE.banco_id = BANCO.banco_id) "+
				" INNER JOIN PRODUTO (NOLOCK) ON BANCOPRODUTOTABELA.produto_id = PRODUTO.produto_id "+
				" WHERE " +
				"   BANCOPRODUTOTABELA.isactive = 1 AND " +
				"	COEFICIENTE.isactive = 1 AND " +
				"	BANCO.isactive = 1 AND " +
				"	PRODUTO.isactive = 1 AND " +
				"	TABELA.isactive = 1 AND " +
				"	PRODUTO.produto_id = ? AND " +
				"	BANCO.banco_id = ? " +
				" ORDER BY BANCO.nome, PRODUTO.nome, TABELA.nome ";

		this.conn = this.conexao.getConexao();

		Collection<Coeficiente> coeficientes = new ArrayList<Coeficiente>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1,produto_id);
			this.stmt.setLong(2,banco_id);

			this.rsCoeficiente = this.stmt.executeQuery();
			while (rsCoeficiente.next()) {

				Tabela tabela = new Tabela();
				tabela.setTabela_id(rsCoeficiente.getLong("tabela_id"));
				tabela.setNome(rsCoeficiente.getString("tabela_nome"));;

				Coeficiente coeficiente = new Coeficiente();
				coeficiente.setCoeficiente_id(rsCoeficiente.getLong("coeficiente_id"));
				coeficiente.setValor(rsCoeficiente.getDouble("valor"));
				coeficiente.setPercentualMeta(rsCoeficiente.getDouble("percentualmeta"));
				
				coeficiente.setTabela(tabela);

				coeficientes.add(coeficiente);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsCoeficiente, stmt, conn);
		return coeficientes;

	}

	public Collection<Coeficiente> buscaCoeficientesByBanco(Long empresa_id, Long organizacao_id, Long bancoId) {

		String sql = sqlCoeficientes;

		this.conn = this.conexao.getConexao();

		Collection<Coeficiente> coeficientes = new ArrayList<Coeficiente>();

		sql += " WHERE " +
				" BANCO.isactive = 1 " +
				" AND TABELA.isactive = 1 " +
				" AND TABELA.empresa_id = ? " +
				" AND TABELA.organizacao_id = ? " +
				" AND BANCO.banco_id = ? " +
				" AND COEFICIENTE.isactive = 1 " +
				" ORDER BY BANCO.nome, TABELA.nome ";


		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1,empresa_id);
			this.stmt.setLong(2,organizacao_id);
			this.stmt.setLong(3,bancoId);

			this.rsCoeficiente = this.stmt.executeQuery();
			while (rsCoeficiente.next()) {

				getCoeficientes(coeficientes);

			}
		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			this.conexao.closeConnection(rsCoeficiente, stmt, conn);

		}

		return coeficientes;
	}

	public Collection<Coeficiente> buscaCoeficientesByTabela(Long tabelaId) {

		String sql = sqlCoeficientes;

		this.conn = this.conexao.getConexao();

		Collection<Coeficiente> coeficientes = new ArrayList<Coeficiente>();

		sql += " WHERE " +
				" COEFICIENTE.updated is null " +
				" AND BANCO.isactive = 1 " +
				" AND TABELA.isactive = 1 " +
				" AND TABELA.tabela_id = ? " +
				" ORDER BY BANCO.nome, TABELA.nome ";


		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1,tabelaId);

			this.rsCoeficiente = this.stmt.executeQuery();

			while (rsCoeficiente.next()) {
				
				getCoeficientes(coeficientes);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsCoeficiente, stmt, conn);
		return coeficientes;
	}

	public Collection<Coeficiente> buscaCoeficientesByBancoTabela(Long bancoId,Long tabelaId) {

		String sql = sqlCoeficientes;

		this.conn = this.conexao.getConexao();

		Collection<Coeficiente> coeficientes = new ArrayList<Coeficiente>();

		sql += " WHERE " +
				" COEFICIENTE.updated is null " +
				" AND BANCO.isactive = 1 " +
				" AND TABELA.isactive = 1 " +
				" AND BANCO.banco_id = ? AND TABELA.tabela_id = ? " +
				" ORDER BY BANCO.nome, TABELA.nome ";

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1,bancoId);
			this.stmt.setLong(2,tabelaId);

			this.rsCoeficiente = this.stmt.executeQuery();
			while (rsCoeficiente.next()) {

				getCoeficientes(coeficientes);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsCoeficiente, stmt, conn);
		return coeficientes;
	}

	public Collection<Calendar> buscaDatasInclusao(Long bancoId, Long produtoId) {
		
		this.conn = this.conexao.getConexao();

		Collection<Calendar> calendars = new ArrayList<Calendar>();

		String sql = "SELECT " + 
			"COEFICIENTE.created " +
		"FROM (((((COEFICIENTE (NOLOCK) INNER JOIN EMPRESA (NOLOCK) ON COEFICIENTE.empresa_id = EMPRESA.empresa_id) " + 
			"INNER JOIN ORGANIZACAO (NOLOCK) ON COEFICIENTE.organizacao_id = ORGANIZACAO.organizacao_id) " + 
			"INNER JOIN TABELA (NOLOCK) ON COEFICIENTE.tabela_id = TABELA.tabela_id) " + 
			"INNER JOIN BANCOPRODUTOTABELA (NOLOCK) ON TABELA.tabela_id = BANCOPRODUTOTABELA.tabela_id) " + 
			"INNER JOIN PRODUTO (NOLOCK) ON BANCOPRODUTOTABELA.produto_id = PRODUTO.produto_id) " +  
			"INNER JOIN BANCO (NOLOCK) ON BANCOPRODUTOTABELA.banco_id = BANCO.banco_id WHERE BANCO.banco_id = ? AND PRODUTO.produto_id = ? AND BANCO.isActive = 1 " +
			" AND PRODUTO.isActive = 1 AND TABELA.isActive = 1 ";

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1,bancoId);
			this.stmt.setLong(2,produtoId);

			this.rsCoeficiente = this.stmt.executeQuery();
			while (rsCoeficiente.next()) {

				Calendar cal = new GregorianCalendar();
				cal.setTime(rsCoeficiente.getDate("created"));

				calendars.add(cal);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		return calendars;
	}

	public Collection<Coeficiente> buscaCoeficientesExcluidos(String bancoNome, String produtoNome, Calendar inicio,Calendar fim) {

		String sql = " SELECT COEFICIENTE.empresa_id, EMPRESA.nome AS empresa_nome, COEFICIENTE.organizacao_id " +
							" , ORGANIZACAO.nome AS organizacao_nome, COEFICIENTE.banco_id, BANCO.nome AS banco_nome, COEFICIENTE.tabela_id " +
							" , TABELA.nome AS tabela_nome, COEFICIENTE.coeficiente_id, COEFICIENTE.created, COEFICIENTE.updated, COEFICIENTE.isactive " + 
							" , COEFICIENTE.percentualmeta, COEFICIENTE.valor " + 
							" FROM ((((( COEFICIENTE (NOLOCK) INNER JOIN BANCO (NOLOCK) ON COEFICIENTE.banco_id = BANCO.banco_id) " +  
							" INNER JOIN TABELA (NOLOCK) ON COEFICIENTE.tabela_id = TABELA.tabela_id) " + 
							" INNER JOIN EMPRESA (NOLOCK) ON COEFICIENTE.empresa_id = EMPRESA.empresa_id)  " +
							" INNER JOIN ORGANIZACAO (NOLOCK) ON COEFICIENTE.organizacao_id = ORGANIZACAO.organizacao_id) " +
							" INNER JOIN BANCOPRODUTOTABELA(NOLOCK) ON  BANCOPRODUTOTABELA.tabela_id = TABELA.tabela_id AND BANCOPRODUTOTABELA.banco_id = BANCO.banco_id) " +
							" INNER JOIN PRODUTO (NOLOCK) ON PRODUTO.produto_id = BANCOPRODUTOTABELA.produto_id " +
							" WHERE " +
							" COEFICIENTE.isactive = 0 " +
							" AND COEFICIENTE.created >= ? and COEFICIENTE.created <= ? " +
							" AND BANCO.banco_id = ? AND PRODUTO.produto_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<Coeficiente> coeficientes = new ArrayList<Coeficiente>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setDate(1,new java.sql.Date(inicio.getTimeInMillis()));
			this.stmt.setDate(2,new java.sql.Date(fim.getTimeInMillis()));
			this.stmt.setString(3,bancoNome);
			this.stmt.setString(4,produtoNome);

			this.rsCoeficiente = this.stmt.executeQuery();

			while (rsCoeficiente.next()) {

				getCoeficientes(coeficientes);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsCoeficiente, stmt, conn);
		return coeficientes;
	}
	
	public Coeficiente buscaCoeficienteById(Long produto_id) {

		String sql = "select COEFICIENTE.coeficiente_id, COEFICIENTE.valor from COEFICIENTE (NOLOCK) WHERE COEFICIENTE.coeficiente_id = ? ";

		this.conn = this.conexao.getConexao();

		Coeficiente coeficiente = new Coeficiente();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, produto_id);

			this.rsCoeficiente = this.stmt.executeQuery();

			while (rsCoeficiente.next()) {

				coeficiente.setCoeficiente_id(rsCoeficiente.getLong("coeficiente_id"));
				coeficiente.setValor(rsCoeficiente.getDouble("valor"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsCoeficiente, stmt, conn);
		return coeficiente;
	}
	
	private void getCoeficientes(Collection<Coeficiente> coeficientes) throws SQLException {
		
		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();
		Coeficiente coeficiente = new Coeficiente();
		Tabela tabela = new Tabela();
		Banco banco = new Banco();
		Calendar created = new GregorianCalendar();
		Calendar updated = new GregorianCalendar();

		empresa.setEmpresa_id(rsCoeficiente.getLong("empresa_id"));
		empresa.setNome(rsCoeficiente.getString("empresa_nome"));

		organizacao.setOrganizacao_id(rsCoeficiente.getLong("organizacao_id"));
		organizacao.setNome(rsCoeficiente.getString("organizacao_nome"));
		
		tabela.setTabela_id(rsCoeficiente.getLong("tabela_id"));
		tabela.setNome(rsCoeficiente.getString("tabela_nome"));

		banco.setBanco_id(rsCoeficiente.getLong("banco_id"));
		banco.setNome(rsCoeficiente.getString("banco_nome"));

		coeficiente.setCoeficiente_id(rsCoeficiente.getLong("coeficiente_id"));
		coeficiente.setValor(rsCoeficiente.getDouble("valor"));
		coeficiente.setPercentualMeta(rsCoeficiente.getDouble("percentualmeta"));

		if(rsCoeficiente.getDate("created") != null) {
			created.setTime(rsCoeficiente.getDate("created"));
			coeficiente.setCreated(created);
		}

		if(rsCoeficiente.getDate("updated") != null) {
			updated.setTime(rsCoeficiente.getDate("updated"));
			coeficiente.setUpdated(updated);
		}

		coeficiente.setEmpresa(empresa);
		coeficiente.setOrganizacao(organizacao);
		coeficiente.setTabela(tabela);
		coeficiente.setBanco(banco);

		coeficientes.add(coeficiente);

	}

}
