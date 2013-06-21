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
import br.com.sgo.modelo.Departamento;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;

@Component
public class DepartamentoDao extends Dao<Departamento> {
	
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsDepartamento;
	
	private final String sqlDepartamento = "SELECT DEPARTAMENTO.departamento_id, DEPARTAMENTO.nome FROM DEPARTAMENTO (NOLOCK) ";

	private final String sqlDepartamentos = "SELECT DEPARTAMENTO.departamento_id, DEPARTAMENTO.isactive, DEPARTAMENTO.nome AS departamento_nome, EMPRESA.empresa_id "+
						", EMPRESA.nome AS empresa_nome, ORGANIZACAO.organizacao_id, ORGANIZACAO.nome AS organizacao_nome "+
						" FROM ORGANIZACAO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) INNER JOIN DEPARTAMENTO (NOLOCK) "+
						" ON EMPRESA.empresa_id = DEPARTAMENTO.empresa_id) ON ORGANIZACAO.organizacao_id = DEPARTAMENTO.organizacao_id ";

	public DepartamentoDao(Session session, ConnJDBC conexao) {

		super(session, Departamento.class);
		this.conexao = conexao;

	}

	public Collection<Departamento> buscaAllDepartamento(Long empresa_id, Long organizacao_id) {

		String sql = sqlDepartamentos;

		this.conn = this.conexao.getConexao();

		Collection<Departamento> departamentos = new ArrayList<Departamento>();
		
		if (empresa_id != null)
			sql += " WHERE DEPARTAMENTO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND DEPARTAMENTO.organizacao_id = ?";
		
		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsDepartamento = this.stmt.executeQuery();

			while (rsDepartamento.next()) {

				getDepartamento(departamentos);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsDepartamento, stmt, conn);

		return departamentos;

	}
	
	public Collection<Departamento> buscaDepartamentos(Long empresa_id,Long organizacao_id, String nome) {

		String sql = sqlDepartamentos;

		this.conn = this.conexao.getConexao();

		Collection<Departamento> departamentos = new ArrayList<Departamento>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");

			this.rsDepartamento = this.stmt.executeQuery();

			while (rsDepartamento.next()) {

				getDepartamento(departamentos);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsDepartamento, stmt, conn);
		return departamentos;
	}
	
	public Departamento buscaDepartamentoByEmpOrgNome(Long empresa_id, Long organizacao_id, String nome) {

		String sql = sqlDepartamento;

		if (empresa_id != null)
			sql += " WHERE DEPARTAMENTO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND DEPARTAMENTO.organizacao_id = ?";
		if (nome != null)
			sql += " AND DEPARTAMENTO.nome = ? ";

		this.conn = this.conexao.getConexao();

		Departamento departamento = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, nome);
			
			this.rsDepartamento = this.stmt.executeQuery();

			while (rsDepartamento.next()) {

				departamento = new Departamento();
				
				departamento.setDepartamento_id(rsDepartamento.getLong("departamento_id"));
				departamento.setNome(rsDepartamento.getString("nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsDepartamento, stmt, conn);
		return departamento;
	}
	
	public Departamento buscaDepartamentoById(Long departamento_id) {

		String sql = "select DEPARTAMENTO.departamento_id, DEPARTAMENTO.nome from DEPARTAMENTO (NOLOCK) WHERE DEPARTAMENTO.departamento_id = ? ";

		this.conn = this.conexao.getConexao();

		Departamento departamento = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, departamento_id);

			this.rsDepartamento = this.stmt.executeQuery();

			while (rsDepartamento.next()) {

				departamento = new Departamento();

				departamento.setDepartamento_id(rsDepartamento.getLong("departamento_id"));
				departamento.setNome(rsDepartamento.getString("nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsDepartamento, stmt, conn);
		return departamento;
	}

	private void getDepartamento(Collection<Departamento> departamentos) throws SQLException {

		Departamento departamento = new Departamento();

		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao(); 

		empresa.setEmpresa_id(rsDepartamento.getLong("empresa_id"));
		empresa.setNome(rsDepartamento.getString("empresa_nome"));

		organizacao.setOrganizacao_id(rsDepartamento.getLong("organizacao_id"));
		organizacao.setNome(rsDepartamento.getString("organizacao_nome"));

		departamento.setEmpresa(empresa);
		departamento.setOrganizacao(organizacao);
		departamento.setDepartamento_id(rsDepartamento.getLong("departamento_id"));
		departamento.setNome(rsDepartamento.getString("departamento_nome"));
		departamento.setIsActive(rsDepartamento.getBoolean("isactive"));

		departamentos.add(departamento);

	}

}
