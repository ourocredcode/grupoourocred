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
import br.com.sgo.modelo.Operacao;

@Component
public class OperacaoDao extends Dao<Operacao> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsOperacoes;

	private final String sqlOperacao = "SELECT OPERACAO.operacao_id, OPERACAO.empresa_id, OPERACAO.organizacao_id" +
			", OPERACAO.isactive, OPERACAO.nome FROM OPERACAO (NOLOCK) ";
	
	private final String sqlOperacoes = " SELECT OPERACAO.empresa_id, EMPRESA.nome AS empresa_nome, OPERACAO.organizacao_id "+
				", ORGANIZACAO.nome AS organizacao_nome, OPERACAO.operacao_id, OPERACAO.nome AS operacao_nome, OPERACAO.isactive "+
				" FROM (OPERACAO (NOLOCK) INNER JOIN EMPRESA (NOLOCK) ON OPERACAO.empresa_id = EMPRESA.empresa_id) " +
				" INNER JOIN ORGANIZACAO (NOLOCK) ON OPERACAO.organizacao_id = ORGANIZACAO.organizacao_id ";


	public OperacaoDao(Session session, ConnJDBC conexao) {

		super(session, Operacao.class);		
		this.conexao = conexao;		

	}
	
	public Collection<Operacao> buscaAllOperacao(Long empresa_id, Long organizacao_id) {

		String sql = sqlOperacoes;
		
		if (empresa_id != null)
			sql += " WHERE OPERACAO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND OPERACAO.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<Operacao> operacoes = new ArrayList<Operacao>();

		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsOperacoes = this.stmt.executeQuery();

			while (rsOperacoes.next()) {
				
				getOperacao(operacoes);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsOperacoes, stmt, conn);

		return operacoes;

	}

	public Operacao buscaOperacaoByEmpOrgNome(Long empresa_id, Long organizacao_id, String nome) {

		String sql = sqlOperacao;

		if (empresa_id != null)
			sql += " WHERE OPERACAO.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND OPERACAO.organizacao_id = ? ";
		if (nome != null)
			sql += " AND OPERACAO.nome = ? ";

		this.conn = this.conexao.getConexao();

		Operacao operacao = null;

		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, organizacao_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, nome);
			
			this.rsOperacoes = this.stmt.executeQuery();

			while (rsOperacoes.next()) {

				operacao = new Operacao();

				operacao.setOperacao_id(rsOperacoes.getLong("operacao_id"));
				operacao.setNome(rsOperacoes.getString("nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsOperacoes, stmt, conn);
		return operacao;
	}

	public Collection<Operacao> buscaOperacoes(Long empresa_id, Long organizacao_id, String nome) {

		String sql = sqlOperacoes;

		if (empresa_id != null)
			sql += " WHERE OPERACAO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND OPERACAO.organizacao_id = ?";
		if (nome != null)
			sql += " AND OPERACAO.nome like ?";
		
		this.conn = this.conexao.getConexao();

		Collection<Operacao> operacoes = new ArrayList<Operacao>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");

			this.rsOperacoes = this.stmt.executeQuery();

			while (rsOperacoes.next()) {

				Operacao operacao = new Operacao();

				operacao.setOperacao_id(rsOperacoes.getLong("operacao_id"));
				operacao.setNome(rsOperacoes.getString("nome"));

				operacoes.add(operacao);
			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsOperacoes, stmt, conn);

		return operacoes;

	}

	public Collection<Operacao> buscaOperacoes(Long empresa_id, Long organizacao_id) {

		String sql = sqlOperacoes;

		if (empresa_id != null)
			sql += " WHERE OPERACAO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND OPERACAO.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<Operacao> operacoes = new ArrayList<Operacao>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsOperacoes = this.stmt.executeQuery();

			while (rsOperacoes.next()) {

				Operacao operacao = new Operacao();

				operacao.setOperacao_id(rsOperacoes.getLong("operacao_id"));
				operacao.setNome(rsOperacoes.getString("operacao_nome"));

				operacoes.add(operacao);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsOperacoes, stmt, conn);

		return operacoes;

	}

	private void getOperacao(Collection<Operacao> operacoes)throws SQLException {

		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();
		Operacao operacao = new Operacao();

		empresa.setEmpresa_id(rsOperacoes.getLong("empresa_id"));
		empresa.setNome(rsOperacoes.getString("empresa_nome"));
		
		organizacao.setOrganizacao_id(rsOperacoes.getLong("organizacao_id"));		
		organizacao.setNome(rsOperacoes.getString("organizacao_nome"));
		
		operacao.setEmpresa(empresa);
		operacao.setOrganizacao(organizacao);
		operacao.setOperacao_id(rsOperacoes.getLong("operacao_id"));
		operacao.setNome(rsOperacoes.getString("operacao_nome"));
		operacao.setIsActive(rsOperacoes.getBoolean("isactive"));

		operacoes.add(operacao);

	}

}
