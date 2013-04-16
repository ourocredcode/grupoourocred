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

	private String sqlCoeficiente = "SELECT " +
			"COEFICIENTE.empresa_id, EMPRESA.nome as empresa_nome, " +
			"COEFICIENTE.organizacao_id, ORGANIZACAO.nome as organizacao_nome, " + 
			"COEFICIENTE.tabela_id, TABELA.nome as tabela_nome, COEFICIENTE.created, " +
			"PRODUTOBANCO.produto_id, PRODUTO.nome as produto_nome , " + 
			"PRODUTOBANCO.banco_id, BANCO.nome as banco_nome, COEFICIENTE.coeficiente_id, COEFICIENTE.valor, COEFICIENTE.percentualmeta " +
		"FROM (((((COEFICIENTE (NOLOCK) INNER JOIN EMPRESA (NOLOCK) ON COEFICIENTE.empresa_id = EMPRESA.empresa_id) " + 
			"INNER JOIN ORGANIZACAO (NOLOCK) ON COEFICIENTE.organizacao_id = ORGANIZACAO.organizacao_id) " + 
			"INNER JOIN TABELA (NOLOCK) ON COEFICIENTE.tabela_id = TABELA.tabela_id) " + 
			"INNER JOIN PRODUTOBANCO (NOLOCK) ON TABELA.tabela_id = PRODUTOBANCO.tabela_id) " + 
			"INNER JOIN PRODUTO (NOLOCK) ON PRODUTOBANCO.produto_id = PRODUTO.produto_id) " + 
			"INNER JOIN BANCO (NOLOCK) ON PRODUTOBANCO.banco_id = BANCO.banco_id ";

	public CoeficienteDao(Session session, ConnJDBC conexao) {
		super(session, Coeficiente.class);
		this.conexao = conexao;
	}

	public Collection<Coeficiente> buscaCoeficientes() {

		String sql = sqlCoeficiente;
		this.conn = this.conexao.getConexao();

		Collection<Coeficiente> coeficientes = new ArrayList<Coeficiente>();

		sql += " WHERE " +
				" COEFICIENTE.updated is null " +
				" AND BANCO.isactive = 1 " +
				" AND PRODUTO.isactive = 1 " +
				" AND TABELA.isactive = 1 " +
				" ORDER BY BANCO.nome, PRODUTO.nome, TABELA.nome ";

		try {

			this.stmt = conn.prepareStatement(sql);
			this.rsCoeficiente = this.stmt.executeQuery();
			while (rsCoeficiente.next()) {

				Empresa empresa = new Empresa();
				empresa.setEmpresa_id(rsCoeficiente.getLong("empresa_id"));
				empresa.setNome(rsCoeficiente.getString("empresa_nome"));

				Organizacao organizacao = new Organizacao();
				organizacao.setOrganizacao_id(rsCoeficiente.getLong("organizacao_id"));
				organizacao.setNome(rsCoeficiente.getString("organizacao_nome"));
				
				Tabela tabela = new Tabela();
				tabela.setTabela_id(rsCoeficiente.getLong("tabela_id"));
				tabela.setNome(rsCoeficiente.getString("tabela_nome"));
				
				Banco banco = new Banco();
				banco.setBanco_id(rsCoeficiente.getLong("tabela_id"));
				banco.setNome(rsCoeficiente.getString("banco_nome"));
				
				tabela.setBanco(banco);

				Coeficiente coeficiente = new Coeficiente();
				coeficiente.setCoeficiente_id(rsCoeficiente.getLong("coeficiente_id"));
				coeficiente.setValor(rsCoeficiente.getDouble("valor"));
				coeficiente.setPercentualMeta(rsCoeficiente.getDouble("percentualmeta"));
				
				Calendar created = new GregorianCalendar();
				created.setTime(rsCoeficiente.getDate("created"));

				coeficiente.setCreated(created);

				coeficiente.setEmpresa(empresa);
				coeficiente.setOrganizacao(organizacao);
				coeficiente.setTabela(tabela);

				coeficientes.add(coeficiente);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsCoeficiente, stmt, conn);
		return coeficientes;

	}

	public Collection<Coeficiente> buscaCoeficientes(String bancoNome,String produtoNome) {
		
		String sql = sqlCoeficiente;
		this.conn = this.conexao.getConexao();

		Collection<Coeficiente> coeficientes = new ArrayList<Coeficiente>();

		sql += " WHERE " +
				" COEFICIENTE.updated is null " +
				" AND BANCO.isactive = 1 " +
				" AND PRODUTO.isactive = 1 " +
				" AND TABELA.isactive = 1 " +
				" AND PRODUTO.nome = ? AND BANCO.nome = ? " +
				" ORDER BY BANCO.nome, PRODUTO.nome, TABELA.nome ";


		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setString(1,produtoNome);
			this.stmt.setString(2,bancoNome);
			this.rsCoeficiente = this.stmt.executeQuery();
			while (rsCoeficiente.next()) {

				Coeficiente coeficiente = new Coeficiente();
				coeficiente.setCoeficiente_id(rsCoeficiente.getLong("coeficiente_id"));
				coeficiente.setValor(rsCoeficiente.getDouble("valor"));
				coeficiente.setPercentualMeta(rsCoeficiente.getDouble("percentualmeta"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsCoeficiente, stmt, conn);
		return coeficientes;

	}

	public Collection<Coeficiente> buscaCoeficientesByBanco(Long bancoId) {

		String sql = sqlCoeficiente;
		this.conn = this.conexao.getConexao();

		Collection<Coeficiente> coeficientes = new ArrayList<Coeficiente>();

		sql += " WHERE " +
				" COEFICIENTE.updated is null " +
				" AND BANCO.isactive = 1 " +
				" AND PRODUTO.isactive = 1 " +
				" AND TABELA.isactive = 1 " +
				" AND BANCO.banco_id = ? " +
				" ORDER BY BANCO.nome, PRODUTO.nome, TABELA.nome ";


		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1,bancoId);

			this.rsCoeficiente = this.stmt.executeQuery();
			while (rsCoeficiente.next()) {

				Empresa empresa = new Empresa();
				empresa.setEmpresa_id(rsCoeficiente.getLong("empresa_id"));
				empresa.setNome(rsCoeficiente.getString("empresa_nome"));

				Organizacao organizacao = new Organizacao();
				organizacao.setOrganizacao_id(rsCoeficiente.getLong("organizacao_id"));
				organizacao.setNome(rsCoeficiente.getString("organizacao_nome"));
				
				Tabela tabela = new Tabela();
				tabela.setTabela_id(rsCoeficiente.getLong("tabela_id"));
				tabela.setNome(rsCoeficiente.getString("tabela_nome"));
				
				Banco banco = new Banco();
				banco.setBanco_id(rsCoeficiente.getLong("tabela_id"));
				banco.setNome(rsCoeficiente.getString("banco_nome"));
				
				tabela.setBanco(banco);

				Coeficiente coeficiente = new Coeficiente();
				coeficiente.setCoeficiente_id(rsCoeficiente.getLong("coeficiente_id"));
				coeficiente.setValor(rsCoeficiente.getDouble("valor"));
				coeficiente.setPercentualMeta(rsCoeficiente.getDouble("percentualmeta"));
				

				Calendar created = new GregorianCalendar();
				created.setTime(rsCoeficiente.getDate("created"));

				coeficiente.setCreated(created);

				coeficiente.setEmpresa(empresa);
				coeficiente.setOrganizacao(organizacao);
				coeficiente.setTabela(tabela);
				
				coeficientes.add(coeficiente);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsCoeficiente, stmt, conn);
		return coeficientes;

	}
	
	public Collection<Coeficiente> buscaCoeficientesByTabela(Long tabelaId) {

		String sql = sqlCoeficiente;
		this.conn = this.conexao.getConexao();

		Collection<Coeficiente> coeficientes = new ArrayList<Coeficiente>();

		sql += " WHERE " +
				" COEFICIENTE.updated is null " +
				" AND BANCO.isactive = 1 " +
				" AND PRODUTO.isactive = 1 " +
				" AND TABELA.isactive = 1 " +
				" AND TABELA.tabela_id = ? " +
				" ORDER BY BANCO.nome, PRODUTO.nome, TABELA.nome ";


		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1,tabelaId);

			this.rsCoeficiente = this.stmt.executeQuery();
			while (rsCoeficiente.next()) {
				
				Empresa empresa = new Empresa();
				empresa.setEmpresa_id(rsCoeficiente.getLong("empresa_id"));
				empresa.setNome(rsCoeficiente.getString("empresa_nome"));

				Organizacao organizacao = new Organizacao();
				organizacao.setOrganizacao_id(rsCoeficiente.getLong("organizacao_id"));
				organizacao.setNome(rsCoeficiente.getString("organizacao_nome"));
				
				Tabela tabela = new Tabela();
				tabela.setTabela_id(rsCoeficiente.getLong("tabela_id"));
				tabela.setNome(rsCoeficiente.getString("tabela_nome"));
				
				Banco banco = new Banco();
				banco.setBanco_id(rsCoeficiente.getLong("tabela_id"));
				banco.setNome(rsCoeficiente.getString("banco_nome"));
				
				tabela.setBanco(banco);

				Coeficiente coeficiente = new Coeficiente();
				coeficiente.setCoeficiente_id(rsCoeficiente.getLong("coeficiente_id"));
				coeficiente.setValor(rsCoeficiente.getDouble("valor"));
				coeficiente.setPercentualMeta(rsCoeficiente.getDouble("percentualmeta"));
				

				Calendar created = new GregorianCalendar();
				created.setTime(rsCoeficiente.getDate("created"));

				coeficiente.setCreated(created);
				
				coeficiente.setEmpresa(empresa);
				coeficiente.setOrganizacao(organizacao);
				coeficiente.setTabela(tabela);

				coeficientes.add(coeficiente);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsCoeficiente, stmt, conn);
		return coeficientes;

	}

	public Collection<Coeficiente> buscaCoeficientes(Long bancoId,Long tabelaId) {

		String sql = sqlCoeficiente;
		this.conn = this.conexao.getConexao();

		Collection<Coeficiente> coeficientes = new ArrayList<Coeficiente>();

		sql += " WHERE " +
				" COEFICIENTE.updated is null " +
				" AND BANCO.isactive = 1 " +
				" AND PRODUTO.isactive = 1 " +
				" AND TABELA.isactive = 1 " +
				" AND BANCO.banco_id = ? AND TABELA.tabela_id = ? " +
				" ORDER BY BANCO.nome, PRODUTO.nome, TABELA.nome ";

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1,bancoId);
			this.stmt.setLong(2,tabelaId);

			this.rsCoeficiente = this.stmt.executeQuery();
			while (rsCoeficiente.next()) {

				Empresa empresa = new Empresa();
				empresa.setEmpresa_id(rsCoeficiente.getLong("empresa_id"));
				empresa.setNome(rsCoeficiente.getString("empresa_nome"));

				Organizacao organizacao = new Organizacao();
				organizacao.setOrganizacao_id(rsCoeficiente.getLong("organizacao_id"));
				organizacao.setNome(rsCoeficiente.getString("organizacao_nome"));
				
				Tabela tabela = new Tabela();
				tabela.setTabela_id(rsCoeficiente.getLong("tabela_id"));
				tabela.setNome(rsCoeficiente.getString("tabela_nome"));
				
				Banco banco = new Banco();
				banco.setBanco_id(rsCoeficiente.getLong("tabela_id"));
				banco.setNome(rsCoeficiente.getString("banco_nome"));
				
				tabela.setBanco(banco);

				Coeficiente coeficiente = new Coeficiente();
				coeficiente.setCoeficiente_id(rsCoeficiente.getLong("coeficiente_id"));
				coeficiente.setValor(rsCoeficiente.getDouble("valor"));
				coeficiente.setPercentualMeta(rsCoeficiente.getDouble("percentualmeta"));
				

				Calendar created = new GregorianCalendar();
				created.setTime(rsCoeficiente.getDate("created"));

				coeficiente.setCreated(created);
				
				coeficiente.setEmpresa(empresa);
				coeficiente.setOrganizacao(organizacao);
				coeficiente.setTabela(tabela);

				coeficientes.add(coeficiente);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsCoeficiente, stmt, conn);
		return coeficientes;

	}

	public Collection<Calendar> buscaDatasInclusao(String bancoNome, String produtoNome) {
		
		this.conn = this.conexao.getConexao();
		Collection<Calendar> calendars = new ArrayList<Calendar>();

		String sql = "SELECT " + 
			"COEFICIENTE.created " +
		"FROM (((((COEFICIENTE (NOLOCK) INNER JOIN EMPRESA (NOLOCK) ON COEFICIENTE.empresa_id = EMPRESA.empresa_id) " + 
			"INNER JOIN ORGANIZACAO (NOLOCK) ON COEFICIENTE.organizacao_id = ORGANIZACAO.organizacao_id) " + 
			"INNER JOIN TABELA (NOLOCK) ON COEFICIENTE.tabela_id = TABELA.tabela_id) " + 
			"INNER JOIN PRODUTOBANCO (NOLOCK) ON TABELA.tabela_id = PRODUTOBANCO.tabela_id) " + 
			"INNER JOIN PRODUTO (NOLOCK) ON PRODUTOBANCO.produto_id = PRODUTO.produto_id) " +  
			"INNER JOIN BANCO (NOLOCK) ON PRODUTOBANCO.banco_id = BANCO.banco_id WHERE BANCO.nome like ? AND PRODUTO.nome like ? AND BANCO.isActive = 1 " +
			" AND PRODUTO.isActive = 1 AND TABELA.isActive = 1 ";

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setString(1,bancoNome);
			this.stmt.setString(2,produtoNome);

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

	public Collection<Coeficiente> buscaCoeficientesExcluidos(String bancoNome, String produtoNome,Calendar inicio,Calendar fim) {

		String sql = sqlCoeficiente;
		this.conn = this.conexao.getConexao();

		Collection<Coeficiente> coeficientes = new ArrayList<Coeficiente>();

		sql += " WHERE " +
				" COEFICIENTE.updated != null " +
				" AND COEFICIENTE.created >= ? and COEFICIENTE.created <= ? " +
				" AND BANCO.nome = ? AND PRODUTO.nome = ? ";

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setDate(1,new java.sql.Date(inicio.getTimeInMillis()));
			this.stmt.setDate(2,new java.sql.Date(fim.getTimeInMillis()));
			this.stmt.setString(3,bancoNome);
			this.stmt.setString(4,produtoNome);

			this.rsCoeficiente = this.stmt.executeQuery();
			while (rsCoeficiente.next()) {

				Coeficiente coeficiente = new Coeficiente();
				coeficiente.setCoeficiente_id(rsCoeficiente.getLong("coeficiente_id"));
				coeficiente.setValor(rsCoeficiente.getDouble("valor"));
				coeficiente.setPercentualMeta(rsCoeficiente.getDouble("percentualmeta"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsCoeficiente, stmt, conn);
		return coeficientes;

	}
	
	/* 
	

	@SuppressWarnings("unchecked")
	public Collection<Coeficiente> buscaPorBanco(String banco) {

		String hql = "from Coeficiente c where c.banco = :banco and c.exclusao = null";

		Query query = session.createQuery(hql)
				.setParameter("banco", banco);

		return query.list();

	}
	
	

	@SuppressWarnings("unchecked")
	
	*/
}
