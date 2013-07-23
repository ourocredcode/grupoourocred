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

@Component
public class OrganizacaoDao extends Dao<Organizacao> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsOrganizacoes;

	private final String sqlOrganizacao = "SELECT ORGANIZACAO.organizacao_id, ORGANIZACAO.empresa_id " +
			", ORGANIZACAO.isactive, ORGANIZACAO.nome FROM ORGANIZACAO (NOLOCK) ";
	
	private final String sqlOrganizacoes = "SELECT ORGANIZACAO.organizacao_id, ORGANIZACAO.nome as organizacao_nome "+
										", ORGANIZACAO.empresa_id, EMPRESA.nome as empresa_nome, ORGANIZACAO.isactive "+
										" FROM ORGANIZACAO (NOLOCK) INNER JOIN EMPRESA (NOLOCK) ON ORGANIZACAO.empresa_id = EMPRESA.empresa_id ";
	
	public OrganizacaoDao(Session session, ConnJDBC conexao) {

		super(session, Organizacao.class);
		this.conexao = conexao;

	}

	public Collection<Organizacao> buscaAllOrganizacaoByEmp(Long empresa_id) {

		String sql = sqlOrganizacoes;

		if (empresa_id != null)
			sql += " WHERE ORGANIZACAO.empresa_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<Organizacao> organizacoes = new ArrayList<Organizacao>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);

			this.rsOrganizacoes = this.stmt.executeQuery();

			while (rsOrganizacoes.next()) {

				getOrganizacao(organizacoes);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsOrganizacoes, stmt, conn);

		return organizacoes;

	}

	public Organizacao buscaOrganizacaoByEmpNome(Long empresa_id, String nome) {

		String sql = sqlOrganizacao;

		if (empresa_id != null)
			sql += " WHERE ORGANIZACAO.empresa_id = ? ";		
		if (nome != null)
			sql += " AND ORGANIZACAO.nome = ? ";

		this.conn = this.conexao.getConexao();

		Organizacao organizacao = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);			
			this.stmt.setString(2, nome);

			this.rsOrganizacoes = this.stmt.executeQuery();

			while (rsOrganizacoes.next()) {

				organizacao = new Organizacao();

				organizacao.setOrganizacao_id(rsOrganizacoes.getLong("organizacao_id"));
				organizacao.setNome(rsOrganizacoes.getString("nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsOrganizacoes, stmt, conn);
		return organizacao;
	}
	
	public Collection<Organizacao> buscaOrganizacoes(Long empresa_id, String org_nome) {

		String sql = "SELECT ORGANIZACAO.organizacao_id, ORGANIZACAO.nome from ORGANIZACAO (NOLOCK) "
				+ "	WHERE ORGANIZACAO.empresa_id = ? AND ORGANIZACAO.nome like ? ";
		this.conn = this.conexao.getConexao();
		Collection<Organizacao> organizacoes = new ArrayList<Organizacao>();
		try {
			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setString(2, "%" + org_nome + "%");
			this.rsOrganizacoes = this.stmt.executeQuery();

			while (rsOrganizacoes.next()) {
				Organizacao organizacao = new Organizacao();

				organizacao.setOrganizacao_id(rsOrganizacoes.getLong("organizacao_id"));
				organizacao.setNome(rsOrganizacoes.getString("nome"));

				organizacoes.add(organizacao);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsOrganizacoes, stmt, conn);

		return organizacoes;

	}

	public Collection<Organizacao> buscaOrganizacoesByEmpresa(Long empresa_id) {

		String sql = "SELECT ORGANIZACAO.organizacao_id, ORGANIZACAO.nome from ORGANIZACAO (NOLOCK) "
				+ "	WHERE ORGANIZACAO.empresa_id = ? ";

		this.conn = this.conexao.getConexao();
		
		Collection<Organizacao> organizacoes = new ArrayList<Organizacao>();
		
		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);

			this.rsOrganizacoes = this.stmt.executeQuery();

			while (rsOrganizacoes.next()) {

				Organizacao organizacao = new Organizacao();

				organizacao.setOrganizacao_id(rsOrganizacoes.getLong("organizacao_id"));
				organizacao.setNome(rsOrganizacoes.getString("nome"));

				organizacoes.add(organizacao);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsOrganizacoes, stmt, conn);

		return organizacoes;

	}
	
	private void getOrganizacao(Collection<Organizacao> organizacoes) throws SQLException {

		Empresa empresa = new Empresa();				
		Organizacao organizacao = new Organizacao();
		
		empresa.setEmpresa_id(rsOrganizacoes.getLong("empresa_id"));
		empresa.setNome(rsOrganizacoes.getString("empresa_nome"));

		organizacao.setOrganizacao_id(rsOrganizacoes.getLong("organizacao_id"));
		organizacao.setNome(rsOrganizacoes.getString("organizacao_nome"));
		organizacao.setIsActive(rsOrganizacoes.getBoolean("isactive"));

		organizacao.setEmpresa(empresa);
		organizacoes.add(organizacao);

	}	

}