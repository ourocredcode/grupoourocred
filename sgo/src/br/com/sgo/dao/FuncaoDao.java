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
import br.com.sgo.modelo.Funcao;
import br.com.sgo.modelo.Organizacao;

@Component
public class FuncaoDao extends Dao<Funcao> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsFuncao;
	
	private final String sqlFuncao = "SELECT FUNCAO.funcao_id, FUNCAO.nome FROM FUNCAO (NOLOCK) ";

	private final String sqlFuncoes = "SELECT FUNCAO.funcao_id, FUNCAO.isactive, FUNCAO.nome AS funcao_nome, EMPRESA.empresa_id "+
						", EMPRESA.nome AS empresa_nome, ORGANIZACAO.organizacao_id, ORGANIZACAO.nome AS organizacao_nome "+
						" FROM ORGANIZACAO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) INNER JOIN FUNCAO (NOLOCK) "+
						" ON EMPRESA.empresa_id = FUNCAO.empresa_id) ON ORGANIZACAO.organizacao_id = FUNCAO.organizacao_id ";

	public FuncaoDao(Session session, ConnJDBC conexao) {

		super(session, Funcao.class);
		this.conexao = conexao;

	}

	public Collection<Funcao> buscaAllFuncao(Long empresa_id, Long organizacao_id) {

		String sql = sqlFuncoes;

		this.conn = this.conexao.getConexao();

		Collection<Funcao> funcoes = new ArrayList<Funcao>();
		
		if (empresa_id != null)
			sql += " WHERE FUNCAO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND FUNCAO.organizacao_id = ?";
		
		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsFuncao = this.stmt.executeQuery();

			while (rsFuncao.next()) {

				getFuncao(funcoes);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsFuncao, stmt, conn);

		return funcoes;

	}
	
	public Collection<Funcao> buscaFuncoes(Long empresa_id,Long organizacao_id, String nome) {

		String sql = sqlFuncoes;

		this.conn = this.conexao.getConexao();

		Collection<Funcao> funcoes = new ArrayList<Funcao>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");

			this.rsFuncao = this.stmt.executeQuery();

			while (rsFuncao.next()) {

				getFuncao(funcoes);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsFuncao, stmt, conn);
		return funcoes;
	}
	
	public Funcao buscaFuncaoByEmpOrgNome(Long empresa_id, Long organizacao_id, String nome) {

		String sql = sqlFuncao;

		if (empresa_id != null)
			sql += " WHERE FUNCAO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND FUNCAO.organizacao_id = ?";
		if (nome != null)
			sql += " AND FUNCAO.nome = ? ";

		this.conn = this.conexao.getConexao();

		Funcao funcao = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, nome);
			
			this.rsFuncao = this.stmt.executeQuery();

			while (rsFuncao.next()) {

				funcao = new Funcao();
				
				funcao.setFuncao_id(rsFuncao.getLong("funcao_id"));
				funcao.setNome(rsFuncao.getString("nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsFuncao, stmt, conn);
		return funcao;
	}
	
	public Funcao buscaFuncaoById(Long funcao_id) {

		String sql = "select FUNCAO.funcao_id, FUNCAO.nome from FUNCAO (NOLOCK) WHERE FUNCAO.funcao_id = ? ";

		this.conn = this.conexao.getConexao();

		Funcao funcao = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, funcao_id);

			this.rsFuncao = this.stmt.executeQuery();

			while (rsFuncao.next()) {

				funcao = new Funcao();

				funcao.setFuncao_id(rsFuncao.getLong("funcao_id"));
				funcao.setNome(rsFuncao.getString("nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsFuncao, stmt, conn);
		return funcao;
	}

	private void getFuncao(Collection<Funcao> funcoes) throws SQLException {

		Funcao funcao = new Funcao();

		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao(); 

		empresa.setEmpresa_id(rsFuncao.getLong("empresa_id"));
		empresa.setNome(rsFuncao.getString("empresa_nome"));

		organizacao.setOrganizacao_id(rsFuncao.getLong("organizacao_id"));
		organizacao.setNome(rsFuncao.getString("organizacao_nome"));

		funcao.setEmpresa(empresa);
		funcao.setOrganizacao(organizacao);
		funcao.setFuncao_id(rsFuncao.getLong("funcao_id"));
		funcao.setNome(rsFuncao.getString("funcao_nome"));
		funcao.setIsActive(rsFuncao.getBoolean("isactive"));

		funcoes.add(funcao);

	}

}
