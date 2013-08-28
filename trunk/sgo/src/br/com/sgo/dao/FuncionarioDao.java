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
import br.com.sgo.modelo.Funcao;
import br.com.sgo.modelo.Funcionario;
import br.com.sgo.modelo.Operacao;
import br.com.sgo.modelo.ParceiroNegocio;

@Component
public class FuncionarioDao extends Dao<Funcionario> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsFuncionario;
	
	private String sqlFuncionario = "SELECT FUNCIONARIO.funcionario_id, FUNCIONARIO.empresa_id, FUNCIONARIO.organizacao_id, FUNCIONARIO.funcao_id, FUNCIONARIO.departamento_id " +
			", FUNCIONARIO.parceironegocio_id, FUNCIONARIO.nome, FUNCIONARIO.isactive, FUNCIONARIO.supervisorfuncionario_id FROM FUNCIONARIO (NOLOCK) "; 
			
	private String sqlFuncionarios =  "SELECT FUNCIONARIO.empresa_id, EMPRESA.nome AS empresa_nome, FUNCIONARIO.organizacao_id, ORGANIZACAO.nome AS organizacao_nome "+
									", FUNCIONARIO.departamento_id, DEPARTAMENTO.nome AS departamento_nome, FUNCIONARIO.funcao_id, FUNCAO.nome AS funcao_nome "+
									", FUNCIONARIO.supervisorfuncionario_id, FUNCIONARIO_1.nome AS supervisor_nome, FUNCIONARIO.operacao_id, OPERACAO.nome AS operacao_nome "+
									", FUNCIONARIO.funcionario_id, FUNCIONARIO.nome AS funcionario_nome, FUNCIONARIO.parceironegocio_id, FUNCIONARIO.isactive, FUNCIONARIO.apelido" +
									", PARCEIRONEGOCIO.cpf, PARCEIRONEGOCIO.nome AS parceironegocio_nome "+
									" FROM (((((((FUNCIONARIO (NOLOCK) LEFT JOIN FUNCAO (NOLOCK) ON FUNCIONARIO.funcao_id = FUNCAO.funcao_id) "+ 
									" 		INNER JOIN EMPRESA (NOLOCK) ON FUNCIONARIO.empresa_id = EMPRESA.empresa_id) "+
									" 		LEFT JOIN DEPARTAMENTO (NOLOCK) ON FUNCIONARIO.departamento_id = DEPARTAMENTO.departamento_id) "+
									" 		LEFT JOIN OPERACAO (NOLOCK) ON FUNCIONARIO.operacao_id = OPERACAO.operacao_id) "+
									" 		INNER JOIN ORGANIZACAO (NOLOCK) ON FUNCIONARIO.organizacao_id = ORGANIZACAO.organizacao_id) "+ 
									" 		LEFT JOIN FUNCIONARIO AS FUNCIONARIO_1 (NOLOCK) ON FUNCIONARIO.supervisorfuncionario_id = FUNCIONARIO_1.funcionario_id) "+ 
									" 		INNER JOIN ((USUARIOPERFIL (NOLOCK) INNER JOIN PERFIL (NOLOCK) ON USUARIOPERFIL.perfil_id = PERFIL.perfil_id) "+
									" 		RIGHT JOIN USUARIO (NOLOCK) ON USUARIOPERFIL.usuario_id = USUARIO.usuario_id) ON FUNCIONARIO.parceironegocio_id = USUARIO.parceironegocio_id) "+ 
									" 		INNER JOIN PARCEIRONEGOCIO (NOLOCK) ON FUNCIONARIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id ";

	public FuncionarioDao(Session session, ConnJDBC conexao) {

		super(session, Funcionario.class);
		this.conexao = conexao;

	}
	
	public Collection<Funcionario> buscaFuncionariosByEmpOrg(Long empresa_id, Long organizacao_id) {

		String sql = sqlFuncionarios;

		sql += " WHERE FUNCIONARIO.empresa_id = ? AND FUNCIONARIO.organizacao_id = ? AND USUARIO.isactive = 1 AND USUARIOPERFIL.isactive = 1 ORDER BY FUNCIONARIO_1.nome, FUNCIONARIO.nome   " ;

		this.conn = this.conexao.getConexao();
		
		Collection<Funcionario> funcionarios = new ArrayList<Funcionario>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsFuncionario = this.stmt.executeQuery();

			while (rsFuncionario.next()) {

				getFuncionarios(funcionarios);	

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsFuncionario, stmt, conn);
		return funcionarios;
	}

	public Collection<Funcionario> buscaFuncionarioToFillCombosByEmpOrg(Long empresa_id, Long organizacao_id) {

		String sql = sqlFuncionario;

		if (empresa_id != null)
			sql += " WHERE FUNCIONARIO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND FUNCIONARIO.organizacao_id = ? AND FUNCIONARIO.isactive = 1";

		this.conn = this.conexao.getConexao();
		
		Collection<Funcionario> funcionarios = new ArrayList<Funcionario>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsFuncionario = this.stmt.executeQuery();

			while (rsFuncionario.next()) {

				Funcionario funcionario = new Funcionario();

				funcionario.setFuncionario_id(rsFuncionario.getLong("funcionario_id"));
				funcionario.setNome(rsFuncionario.getString("nome"));

				funcionarios.add(funcionario);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsFuncionario, stmt, conn);
		return funcionarios;
	}

	public Collection<Funcionario> buscaSupervisorFuncionarioByPerfil(Long empresa_id, Long organizacao_id, String perfil) {

		String sql = "SELECT FUNCIONARIO.funcionario_id AS supervisorfuncionario_id, FUNCIONARIO.nome AS supervisor_nome,  FUNCIONARIO.empresa_id "+
					", EMPRESA.nome AS empresa_nome, FUNCIONARIO.organizacao_id, ORGANIZACAO.nome AS organizacao_nome "+
			" FROM ((((FUNCIONARIO (NOLOCK) INNER JOIN EMPRESA (NOLOCK) ON FUNCIONARIO.empresa_id = EMPRESA.empresa_id) "+
				" INNER JOIN ORGANIZACAO (NOLOCK) ON FUNCIONARIO.organizacao_id = ORGANIZACAO.organizacao_id) "+
				" INNER JOIN USUARIO (NOLOCK) ON FUNCIONARIO.parceironegocio_id = USUARIO.parceironegocio_id) "+
				" LEFT JOIN USUARIOPERFIL (NOLOCK) ON USUARIO.usuario_id = USUARIOPERFIL.usuario_id) "+
				" INNER JOIN PERFIL (NOLOCK) ON USUARIOPERFIL.perfil_id = PERFIL.perfil_id ";
			
		if (empresa_id != null)
			sql += " WHERE EMPRESA.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND ORGANIZACAO.organizacao_id = ? ";
		if (perfil != null)
			sql += " AND PERFIL.nome like ? AND USUARIO.isactive = 1 AND PERFIL.isactive = 1 AND USUARIOPERFIL.isactive = 1";

		this.conn = this.conexao.getConexao();

		Collection<Funcionario> supervisores = new ArrayList<Funcionario>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + perfil + "%");

			this.rsFuncionario = this.stmt.executeQuery();

			while (rsFuncionario.next()) {

				Funcionario supervisor = new Funcionario();

				supervisor.setFuncionario_id(rsFuncionario.getLong("supervisorfuncionario_id"));
				supervisor.setNome(rsFuncionario.getString("supervisor_nome"));

				supervisores.add(supervisor);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsFuncionario, stmt, conn);
		return supervisores;
	}

	public Funcionario buscaFuncionarioPorParceiroNegocio(Long parceironegocio_id) {

		String sql = sqlFuncionarios;

		sql += " WHERE FUNCIONARIO.parceironegocio_id = ?  " ;

		this.conn = this.conexao.getConexao();

		Funcionario funcionario = new Funcionario();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, parceironegocio_id);

			this.rsFuncionario = this.stmt.executeQuery();

			while (rsFuncionario.next()) {

				getFuncionario(funcionario);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsFuncionario, stmt, conn);
		return funcionario;
	}

	public Funcionario buscaFuncionarioByEmpOrgFuncionario(Long funcionario_id) {

		String sql = sqlFuncionarios;

		if (funcionario_id != null)
			sql += " WHERE FUNCIONARIO.funcionario_id = ? ";

		this.conn = this.conexao.getConexao();

		Funcionario funcionario = new Funcionario();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, funcionario_id);

			this.rsFuncionario = this.stmt.executeQuery();

			while (rsFuncionario.next()) {

				ParceiroNegocio parceiroNegocio = new ParceiroNegocio();
				parceiroNegocio.setParceiroNegocio_id(rsFuncionario.getLong("parceironegocio_id"));
				funcionario.setParceiroNegocio(parceiroNegocio);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsFuncionario, stmt, conn);
		return funcionario;
	}
	
	public Collection<Funcionario> buscaFuncionariosBySupervisor(Long empresa_id , Long organizacao_id, Long supervisor_id) {

		String sql = sqlFuncionarios;

		sql += " WHERE EMPRESA.empresa_id = ? AND ORGANIZACAO.organizacao_id = ? AND USUARIO.supervisor_usuario_id = ? AND USUARIO.isactive = 1 AND USUARIOPERFIL.isactive = 1 " ;

		this.conn = this.conexao.getConexao();
		
		Collection<Funcionario> funcionarios = new ArrayList<Funcionario>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, supervisor_id);

			this.rsFuncionario = this.stmt.executeQuery();

			while (rsFuncionario.next()) {

				getFuncionarios(funcionarios);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsFuncionario, stmt, conn);
		return funcionarios;
	}

	public Collection<Funcionario> buscaFuncionariosByPerfil(Long empresa_id, Long organizacao_id, String perfil) {

		String sql = sqlFuncionarios;

		sql += " WHERE USUARIO.empresa_id = ?  AND USUARIO.organizacao_id = ?  AND PERFIL.nome like  ? AND USUARIO.isactive = 1 AND USUARIOPERFIL.isactive = 1" ;

		this.conn = this.conexao.getConexao();
		
		Collection<Funcionario> funcionarios = new ArrayList<Funcionario>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + perfil + "%");

			this.rsFuncionario = this.stmt.executeQuery();

			while (rsFuncionario.next()) {

				getFuncionarios(funcionarios);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsFuncionario, stmt, conn);
		return funcionarios;
	}

	private void getFuncionario(Funcionario funcionario) throws SQLException {

		ParceiroNegocio parceiro = new ParceiroNegocio();
		//ParceiroNegocio supervisor = new ParceiroNegocio();
		Funcionario supervisorFuncionario = new Funcionario();
		Departamento d = new Departamento();
		Funcao f = new Funcao();
		Operacao o = new Operacao();

		parceiro.setParceiroNegocio_id(rsFuncionario.getLong("parceironegocio_id"));
		parceiro.setNome(rsFuncionario.getString("parceironegocio_nome"));
		parceiro.setIsFuncionario(true);
		
		supervisorFuncionario.setFuncionario_id(rsFuncionario.getLong("supervisorfuncionario_id"));
		supervisorFuncionario.setNome(rsFuncionario.getString("supervisor_nome"));
		
		d.setDepartamento_id(rsFuncionario.getLong("departamento_id"));
		d.setNome(rsFuncionario.getString("departamento_nome"));

		f.setFuncao_id(rsFuncionario.getLong("funcao_id"));
		f.setNome(rsFuncionario.getString("funcao_nome"));
		
		o.setOperacao_id(rsFuncionario.getLong("operacao_id"));
		o.setNome(rsFuncionario.getString("operacao_nome"));

		funcionario.setFuncionario_id(rsFuncionario.getLong("funcionario_id"));
		funcionario.setNome(rsFuncionario.getString("funcionario_nome"));
		funcionario.setFuncao(f);
		funcionario.setDepartamento(d);
		funcionario.setOperacao(o);
		funcionario.setParceiroNegocio(parceiro);
		funcionario.setSupervisorFuncionario(supervisorFuncionario);
		funcionario.setApelido(rsFuncionario.getString("apelido"));
	
	}

	private void getFuncionarios(Collection<Funcionario> funcionarios) throws SQLException {

		ParceiroNegocio parceiro = new ParceiroNegocio();
		Funcionario funcionario = new Funcionario();
		Funcionario supervisorFuncionario = new Funcionario();
		Departamento d = new Departamento();		
		Funcao f = new Funcao();
		Operacao operacao = new Operacao();

		parceiro.setParceiroNegocio_id(rsFuncionario.getLong("parceironegocio_id"));
		parceiro.setNome(rsFuncionario.getString("parceironegocio_nome"));

		parceiro.setCpf(rsFuncionario.getString("cpf"));
		parceiro.setIsFuncionario(true);

		supervisorFuncionario.setFuncionario_id(rsFuncionario.getLong("supervisorfuncionario_id"));
		supervisorFuncionario.setNome(rsFuncionario.getString("supervisor_nome"));

		d.setDepartamento_id(rsFuncionario.getLong("departamento_id"));
		d.setNome(rsFuncionario.getString("departamento_nome"));

		f.setFuncao_id(rsFuncionario.getLong("funcao_id"));
		f.setNome(rsFuncionario.getString("funcao_nome"));

		operacao.setOperacao_id(rsFuncionario.getLong("operacao_id"));
		operacao.setNome(rsFuncionario.getString("operacao_nome") == null ? "" : rsFuncionario.getString("operacao_nome"));
		
		funcionario.setParceiroNegocio(parceiro);
		funcionario.setSupervisorFuncionario(supervisorFuncionario);
		funcionario.setDepartamento(d);
		funcionario.setFuncao(f);
		funcionario.setOperacao(operacao);
		
		funcionario.setFuncionario_id(rsFuncionario.getLong("funcionario_id"));
		funcionario.setNome(rsFuncionario.getString("funcionario_nome"));
		funcionario.setApelido(rsFuncionario.getString("apelido") == null ? "" : rsFuncionario.getString("apelido"));	

		funcionarios.add(funcionario);

	}

}