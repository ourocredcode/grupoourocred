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
import br.com.sgo.modelo.ParceiroNegocio;

@Component
public class FuncionarioDao extends Dao<Funcionario> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsFuncionario;
	
	private String sqlFuncionario = "SELECT FUNCIONARIO.funcionario_id, FUNCIONARIO.empresa_id, FUNCIONARIO.organizacao_id, FUNCIONARIO.funcao_id, FUNCIONARIO.departamento_id " +
			", FUNCIONARIO.parceironegocio_id, FUNCIONARIO.nome, FUNCIONARIO.isactive FROM FUNCIONARIO (NOLOCK) "; 
			
	private String sqlFuncionarios =  " SELECT FUNCIONARIO.empresa_id,  EMPRESA.nome, FUNCIONARIO.organizacao_id, ORGANIZACAO.nome, PARCEIRONEGOCIO.parceironegocio_id,  " +
			"	PARCEIRONEGOCIO.cpf, FUNCIONARIO.apelido, " +
			"   PARCEIRONEGOCIO.nome as parceironegocio_nome, FUNCIONARIO.funcao_id, FUNCAO.nome as funcao_nome, FUNCIONARIO.departamento_id, FUNCIONARIO.funcionario_id, " +
			"	DEPARTAMENTO.nome as departamento_nome, FUNCIONARIO.supervisor_funcionario_id, SUPER.nome as supervisor_nome FROM  " +
			"	(((((((ORGANIZACAO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) " +
			"								INNER JOIN PARCEIRONEGOCIO (NOLOCK)   ON EMPRESA.empresa_id = PARCEIRONEGOCIO.empresa_id)  ON ORGANIZACAO.organizacao_id = PARCEIRONEGOCIO.organizacao_id)   " +
			"								INNER JOIN FUNCIONARIO (NOLOCK) ON PARCEIRONEGOCIO.parceironegocio_id = FUNCIONARIO.parceironegocio_id)   " +
			"								LEFT JOIN PARCEIRONEGOCIO AS SUPER (NOLOCK) ON FUNCIONARIO.supervisor_funcionario_id = SUPER.parceironegocio_id) " +
			"								INNER JOIN DEPARTAMENTO (NOLOCK) ON FUNCIONARIO.departamento_id = DEPARTAMENTO.departamento_id)  " +
			"								INNER JOIN USUARIO (NOLOCK) ON USUARIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id)  " +
			"								INNER JOIN USUARIOPERFIL (NOLOCK) ON USUARIO.usuario_id = USUARIOPERFIL.usuario_id) " +
			"								INNER JOIN PERFIL (NOLOCK) ON PERFIL.perfil_id = USUARIOPERFIL.perfil_id) " +
			"								INNER JOIN FUNCAO (NOLOCK) ON FUNCIONARIO.funcao_id = FUNCAO.funcao_id   ";

	public FuncionarioDao(Session session, ConnJDBC conexao) {
		super(session, Funcionario.class);
		this.conexao = conexao;
	}
	
	public Collection<Funcionario> buscaFuncionariosByEmpOrg(Long empresa_id, Long organizacao_id) {

		String sql = sqlFuncionarios;

		sql += " WHERE PARCEIRONEGOCIO.empresa_id = ? AND PARCEIRONEGOCIO.organizacao_id = ? ORDER BY SUPER.nome, FUNCIONARIO.nome  " ;

		this.conn = this.conexao.getConexao();
		
		Collection<Funcionario> funcionarios = new ArrayList<Funcionario>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsFuncionario = this.stmt.executeQuery();

			while (rsFuncionario.next()) {

				ParceiroNegocio parceiro = new ParceiroNegocio();
				ParceiroNegocio supervisor = new ParceiroNegocio();
				Departamento d = new Departamento();
				Funcao f = new Funcao();
				Funcionario funcionario = new Funcionario();
				

				parceiro.setParceiroNegocio_id(rsFuncionario.getLong("parceironegocio_id"));
				parceiro.setNome(rsFuncionario.getString("parceironegocio_nome"));
				parceiro.setIsFuncionario(true);

				d.setDepartamento_id(rsFuncionario.getLong("departamento_id"));
				d.setNome(rsFuncionario.getString("departamento_nome"));

				f.setFuncao_id(rsFuncionario.getLong("funcao_id"));
				f.setNome(rsFuncionario.getString("funcao_nome"));

				funcionario.setFuncionario_id(rsFuncionario.getLong("funcionario_id"));
				funcionario.setFuncao(f);
				funcionario.setDepartamento(d);
				funcionario.setParceiroNegocio(parceiro);
				
				supervisor.setParceiroNegocio_id(rsFuncionario.getLong("supervisor_funcionario_id"));
				supervisor.setNome(rsFuncionario.getString("supervisor_nome"));
				funcionario.setSupervisor(supervisor);
				
				funcionarios.add(funcionario);
				

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

	public Funcionario buscaFuncionarioById(Long funcionario_id) {

		String sql = sqlFuncionario;

		if (funcionario_id != null)
			sql += " WHERE FUNCIONARIO.funcionario_id = ?";

		this.conn = this.conexao.getConexao();

		Funcionario funcionario = null;

		try {
			
			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, funcionario_id);

			this.rsFuncionario = this.stmt.executeQuery();			

			while (rsFuncionario.next()) {

				funcionario = new Funcionario();				
				funcionario.setFuncionario_id(rsFuncionario.getLong("funcionario_id"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsFuncionario, stmt, conn);
		return funcionario;
	}

	public Funcionario buscaFuncionarioPorParceiroNegocio(Long parceironegocio_id) {

		String sql = sqlFuncionarios;

		sql += " WHERE PARCEIRONEGOCIO.parceironegocio_id = ?  " ;

		this.conn = this.conexao.getConexao();

		Funcionario funcionario = new Funcionario();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, parceironegocio_id);

			this.rsFuncionario = this.stmt.executeQuery();

			while (rsFuncionario.next()) {

				ParceiroNegocio parceiro = new ParceiroNegocio();
				ParceiroNegocio supervisor = new ParceiroNegocio();
				Departamento d = new Departamento();
				Funcao f = new Funcao();

				parceiro.setParceiroNegocio_id(rsFuncionario.getLong("parceironegocio_id"));
				parceiro.setNome(rsFuncionario.getString("parceironegocio_nome"));
				parceiro.setIsFuncionario(true);
				
				supervisor.setParceiroNegocio_id(rsFuncionario.getLong("supervisor_funcionario_id"));
				supervisor.setNome(rsFuncionario.getString("supervisor_nome"));

				d.setDepartamento_id(rsFuncionario.getLong("departamento_id"));
				d.setNome(rsFuncionario.getString("departamento_nome"));

				f.setFuncao_id(rsFuncionario.getLong("funcao_id"));
				f.setNome(rsFuncionario.getString("funcao_nome"));

				funcionario.setFuncionario_id(rsFuncionario.getLong("funcionario_id"));
				funcionario.setFuncao(f);
				funcionario.setDepartamento(d);
				funcionario.setParceiroNegocio(parceiro);
				funcionario.setSupervisor(supervisor);
				funcionario.setApelido(rsFuncionario.getString("apelido"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsFuncionario, stmt, conn);
		return funcionario;
	}

	public Collection<Funcionario> buscaFuncionariosBySupervisor(Long empresa_id , Long organizacao_id, Long supervisor_id) {

		String sql = sqlFuncionarios;

		sql += " WHERE EMPRESA.empresa_id = ? AND ORGANIZACAO.organizacao_id = ? AND USUARIO.supervisor_usuario_id = ? " ;

		this.conn = this.conexao.getConexao();
		
		Collection<Funcionario> funcionarios = new ArrayList<Funcionario>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, supervisor_id);

			this.rsFuncionario = this.stmt.executeQuery();

			while (rsFuncionario.next()) {

				ParceiroNegocio parceiro = new ParceiroNegocio();
				ParceiroNegocio supervisor = new ParceiroNegocio();
				Funcionario funcionario = new Funcionario();

				parceiro.setParceiroNegocio_id(rsFuncionario.getLong("parceironegocio_id"));
				parceiro.setNome(rsFuncionario.getString("parceironegocio_nome"));
				parceiro.setCpf(rsFuncionario.getString("cpf"));
				parceiro.setIsFuncionario(true);

				supervisor.setParceiroNegocio_id(rsFuncionario.getLong("supervisor_funcionario_id"));
				supervisor.setNome(rsFuncionario.getString("supervisor_nome"));
				funcionario.setSupervisor(supervisor);

				funcionario.setFuncionario_id(rsFuncionario.getLong("funcionario_id"));
				funcionario.setApelido(rsFuncionario.getString("apelido"));
				funcionario.setParceiroNegocio(parceiro);

				funcionarios.add(funcionario);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsFuncionario, stmt, conn);
		return funcionarios;
	}
	
	public Collection<Funcionario> buscaFuncionariosByPerfil(Long empresa_id, Long organizacao_id, String perfil) {

		String sql = sqlFuncionarios;

		sql += " WHERE USUARIO.empresa_id = ?  AND USUARIO.organizacao_id = ?  AND PERFIL.nome like  ? " ;

		this.conn = this.conexao.getConexao();
		
		Collection<Funcionario> funcionarios = new ArrayList<Funcionario>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + perfil + "%");

			this.rsFuncionario = this.stmt.executeQuery();

			while (rsFuncionario.next()) {

				ParceiroNegocio parceiro = new ParceiroNegocio();
				Funcionario funcionario = new Funcionario();

				parceiro.setParceiroNegocio_id(rsFuncionario.getLong("parceironegocio_id"));
				parceiro.setNome(rsFuncionario.getString("parceironegocio_nome"));
				parceiro.setIsFuncionario(true);

				funcionario.setFuncionario_id(rsFuncionario.getLong("funcionario_id"));
				funcionario.setParceiroNegocio(parceiro);

				funcionarios.add(funcionario);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsFuncionario, stmt, conn);
		return funcionarios;
	}

}
