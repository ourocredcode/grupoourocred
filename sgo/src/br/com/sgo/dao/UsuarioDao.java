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
import br.com.sgo.modelo.ParceiroNegocio;
import br.com.sgo.modelo.Usuario;

@Component
public class UsuarioDao extends Dao<Usuario> {

	private Session session;
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsUsuarios;

	private final String sqlUsuario = "SELECT USUARIO.usuario_id, USUARIO.nome, USUARIO.chave FROM USUARIO (NOLOCK) ";  

	private final String sqlUsuarios = "SELECT USUARIO.empresa_id, EMPRESA.nome AS empresa_nome, USUARIO.organizacao_id, ORGANIZACAO.nome AS organizacao_nome "+
						", USUARIO.parceironegocio_id, PARCEIRONEGOCIO.nome AS parceironegocio_nome, USUARIO.usuario_id, USUARIO.nome AS usuario_nome "+
						", USUARIO.supervisor_usuario_id, USUARIO_1.nome as supervisorusuario_nome, USUARIO.chave AS login, USUARIO.email, USUARIO.isactive "+
						" FROM ((ORGANIZACAO INNER JOIN (EMPRESA (NOLOCK) INNER JOIN USUARIO (NOLOCK) ON EMPRESA.empresa_id = USUARIO.empresa_id) "+ 
						" ON ORGANIZACAO.organizacao_id = USUARIO.organizacao_id) INNER JOIN PARCEIRONEGOCIO (NOLOCK) ON USUARIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id) "+ 
						" LEFT JOIN USUARIO (NOLOCK) AS USUARIO_1 ON USUARIO.supervisor_usuario_id = USUARIO_1.usuario_id ";

	public UsuarioDao(Session session, ConnJDBC conexao) {
		super(session, Usuario.class);
		this.session = session;
		this.conexao = conexao;
	}

	public Long salva(Usuario a) {

		this.session.saveOrUpdate(a);
		return a.getUsuario_id();

	}
	
	public Usuario buscaUsuarioById(Long usuario_id ) {

		String sql = " SELECT USUARIO.usuario_id, USUARIO.nome, USUARIO.chave, USUARIO.apelido FROM USUARIO (NOLOCK) WHERE USUARIO.usuario_id = ? ";
 

		Usuario usuario = null;

		this.conn = this.conexao.getConexao();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, usuario_id);

			this.rsUsuarios = this.stmt.executeQuery();

			while (rsUsuarios.next()) {
				
				usuario = new Usuario();

				usuario.setUsuario_id(rsUsuarios.getLong("usuario_id"));
				usuario.setNome(rsUsuarios.getString("nome"));
				usuario.setApelido(rsUsuarios.getString("apelido"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsUsuarios, stmt, conn);
		return usuario;

	}

	public Collection<Usuario> buscaAllUsuariosByEmpresaOrganizacao(Long empresa_id, Long organizacao_id) {

		String sql = sqlUsuarios;

		if (empresa_id != null)
			sql += " WHERE USUARIO.empresa_id = ? ";		
		if (organizacao_id != null)
			sql += " AND USUARIO.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<Usuario> usuarios = new ArrayList<Usuario>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsUsuarios = this.stmt.executeQuery();

			while (rsUsuarios.next()) {

				Usuario usuario = new Usuario();
				Usuario supervisorUsuario = new Usuario();
				Empresa empresa = new Empresa();
				Organizacao organizacao = new Organizacao();
				ParceiroNegocio parceiroNegocio = new ParceiroNegocio();

				empresa.setEmpresa_id(rsUsuarios.getLong("empresa_id"));
				empresa.setNome(rsUsuarios.getString("empresa_nome"));

				organizacao.setOrganizacao_id(rsUsuarios.getLong("organizacao_id"));
				organizacao.setNome(rsUsuarios.getString("organizacao_nome"));

				parceiroNegocio.setParceiroNegocio_id(rsUsuarios.getLong("parceironegocio_id"));
				parceiroNegocio.setNome(rsUsuarios.getString("parceironegocio_nome"));

				supervisorUsuario.setUsuario_id(rsUsuarios.getLong("supervisor_usuario_id"));
				supervisorUsuario.setNome(rsUsuarios.getString("supervisorusuario_nome"));

				usuario.setEmpresa(empresa);
				usuario.setOrganizacao(organizacao);
				usuario.setParceiroNegocio(parceiroNegocio);
				usuario.setSupervisorUsuario(supervisorUsuario);

				usuario.setUsuario_id(rsUsuarios.getLong("usuario_id"));				
				usuario.setNome(rsUsuarios.getString("usuario_nome"));
				usuario.setChave(rsUsuarios.getString("login"));
				usuario.setEmail(rsUsuarios.getString("email"));
				usuario.setIsActive(rsUsuarios.getBoolean("isactive"));

				usuarios.add(usuario);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsUsuarios, stmt, conn);
		return usuarios;
	}

	public Usuario find(String login, String senha) {

		String sql = " SELECT USUARIO.usuario_id, USUARIO.empresa_id, EMPRESA.nome as empresa_nome , USUARIO.organizacao_id, ORGANIZACAO.nome as organizacao_nome " +
				"		FROM (USUARIO (NOLOCK) INNER JOIN EMPRESA (NOLOCK) ON USUARIO.empresa_id = EMPRESA.empresa_id) " +
				"							   INNER JOIN ORGANIZACAO (NOLOCK) ON USUARIO.organizacao_id = ORGANIZACAO.organizacao_id " +
				"		WHERE USUARIO.chave = ? AND USUARIO.senha = ? AND USUARIO.isactive = 1 ";
 

		Usuario usuario = null;

		this.conn = this.conexao.getConexao();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setString(1, login);
			this.stmt.setString(2, senha);

			this.rsUsuarios = this.stmt.executeQuery();

			while (rsUsuarios.next()) {
				
				usuario = new Usuario();

				Empresa empresa = new Empresa();
				Organizacao organizacao = new Organizacao();
				
				empresa.setEmpresa_id(rsUsuarios.getLong("empresa_id"));
				empresa.setNome(rsUsuarios.getString("empresa_nome"));
				
				organizacao.setOrganizacao_id(rsUsuarios.getLong("organizacao_id"));
				organizacao.setNome(rsUsuarios.getString("organizacao_nome"));

				usuario.setEmpresa(empresa);
				usuario.setOrganizacao(organizacao);
				usuario.setUsuario_id(rsUsuarios.getLong("usuario_id"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsUsuarios, stmt, conn);
		return usuario;

	}

	public Collection<Usuario> buscaUsuarios(Long empresa_id, Long organizacao_id, String nome) {

		String sql = " SELECT USUARIO.usuario_id, USUARIO.nome FROM ((USUARIO (NOLOCK) "
				+ "INNER JOIN PARCEIRONEGOCIO (NOLOCK) ON USUARIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id) "
				+ "INNER JOIN EMPRESA (NOLOCK) ON USUARIO.empresa_id = EMPRESA.empresa_id) "
				+ "INNER JOIN ORGANIZACAO (NOLOCK) ON USUARIO.organizacao_id = ORGANIZACAO.organizacao_id	"
				+ "WHERE USUARIO.isactive=1 AND PARCEIRONEGOCIO.isactive=1 AND PARCEIRONEGOCIO.isfuncionario=1"
				+ "AND USUARIO.empresa_id = ? AND USUARIO.organizacao_id = ? AND USUARIO.nome like ? ";

		this.conn = this.conexao.getConexao();

		Collection<Usuario> usuarios = new ArrayList<Usuario>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");

			this.rsUsuarios = this.stmt.executeQuery();

			while (rsUsuarios.next()) {

				Usuario usuario = new Usuario();
				usuario.setUsuario_id(rsUsuarios.getLong("usuario_id"));
				usuario.setNome(rsUsuarios.getString("nome"));
				usuarios.add(usuario);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsUsuarios, stmt, conn);
		return usuarios;
	}

	public Usuario buscaUsuarioByEmpresaOrganizacaoChave(Long empresa_id, Long organizacao_id, String chave) {

		String sql = sqlUsuario; 

		this.conn = this.conexao.getConexao();
		
		if (empresa_id != null)
			sql += " WHERE USUARIO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND USUARIO.organizacao_id = ?";
		if (chave != null)
			sql += " AND USUARIO.chave = ?";

		Usuario usuario = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, chave);

			this.rsUsuarios = this.stmt.executeQuery();

			while (rsUsuarios.next()) {

				usuario = new Usuario();
				usuario.setUsuario_id(rsUsuarios.getLong("usuario_id"));
				usuario.setUsuario_id(rsUsuarios.getLong("chave"));

			}
		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsUsuarios, stmt, conn);
		return usuario;
	}

	public Usuario buscaUsuario(Long empresa_id, Long organizacao_id, String cpf) {

		String sql = "SELECT USUARIO.usuario_id, USUARIO.nome, USUARIO.apelido FROM ((USUARIO (NOLOCK) "
				+ "INNER JOIN PARCEIRONEGOCIO (NOLOCK) ON USUARIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id) "
				+ "INNER JOIN EMPRESA (NOLOCK) ON USUARIO.empresa_id = EMPRESA.empresa_id) "
				+ "INNER JOIN ORGANIZACAO (NOLOCK) ON USUARIO.organizacao_id = ORGANIZACAO.organizacao_id	"
				+ "WHERE USUARIO.isactive=1 AND PARCEIRONEGOCIO.isactive=1 AND PARCEIRONEGOCIO.isfuncionario=1"
				+ "AND USUARIO.empresa_id = ? AND USUARIO.organizacao_id = ? AND USUARIO.chave like ? ";

		this.conn = this.conexao.getConexao();

		Usuario usuario = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, cpf);

			this.rsUsuarios = this.stmt.executeQuery();

			while (rsUsuarios.next()) {

				usuario = new Usuario();

				usuario.setUsuario_id(rsUsuarios.getLong("usuario_id"));
				usuario.setNome(rsUsuarios.getString("nome"));
				usuario.setApelido(rsUsuarios.getString("apelido"));

			}
		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsUsuarios, stmt, conn);
		return usuario;
	}
	
	public Usuario buscaUsuarioByParceiroNegocio(Long empresa_id, Long organizacao_id, Long parceiro_id) {

		String sql = "SELECT EMPRESA.empresa_id, ORGANIZACAO.organizacao_id, USUARIO.usuario_id,   " +
				"			 USUARIO.parceironegocio_id, SUPER.usuario_id as supervisor_id , " +
				"			 USUARIO.isactive, USUARIO.chave, USUARIO.nome as usuario_nome , USUARIO.descricao, USUARIO.email,  " +
				"			 USUARIO.senha, USUARIO.apelido, USUARIO.telefone FROM  (((USUARIO (NOLOCK)  " +
				"	 INNER JOIN PARCEIRONEGOCIO (NOLOCK) ON USUARIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id)  " +
				"	 INNER JOIN EMPRESA (NOLOCK) ON USUARIO.empresa_id = EMPRESA.empresa_id)  " +
				"	 INNER JOIN ORGANIZACAO (NOLOCK) ON USUARIO.organizacao_id = ORGANIZACAO.organizacao_id)  " +
				"	 LEFT JOIN  USUARIO AS SUPER (NOLOCK) ON USUARIO.supervisor_usuario_id = SUPER.usuario_id " +
				"	 WHERE USUARIO.isactive = 1  " +
				"	 AND PARCEIRONEGOCIO.isactive = 1  " +
				"	 AND PARCEIRONEGOCIO.isfuncionario = 1  " +
				"	 AND USUARIO.empresa_id = ?  " +
				"    AND USUARIO.organizacao_id = ?  " +
				"	 AND USUARIO.parceironegocio_id = ? ";

		this.conn = this.conexao.getConexao();

		Usuario usuario = new Usuario();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, parceiro_id);

			this.rsUsuarios = this.stmt.executeQuery();

			while (rsUsuarios.next()) {

				Empresa empresa = new Empresa();
				Organizacao organizacao = new Organizacao();

				Usuario supervisor = new Usuario();
				ParceiroNegocio parceiroNegocio = new ParceiroNegocio();

				empresa.setEmpresa_id(rsUsuarios.getLong("empresa_id"));
				organizacao.setOrganizacao_id(rsUsuarios.getLong("organizacao_id"));
				supervisor.setUsuario_id(rsUsuarios.getLong("supervisor_id"));
				parceiroNegocio.setParceiroNegocio_id(rsUsuarios.getLong("parceironegocio_id"));

				usuario.setEmpresa(empresa);
				usuario.setOrganizacao(organizacao);
				usuario.setUsuario_id(rsUsuarios.getLong("usuario_id"));
				usuario.setSupervisorUsuario(supervisor);
				usuario.setParceiroNegocio(parceiroNegocio);
				usuario.setIsActive(true);
				usuario.setChave(rsUsuarios.getString("chave"));
				usuario.setNome(rsUsuarios.getString("usuario_nome"));
				usuario.setDescricao(rsUsuarios.getString("descricao"));
				usuario.setEmail(rsUsuarios.getString("email"));
				usuario.setSenha(rsUsuarios.getString("senha"));
				usuario.setTelefone(rsUsuarios.getString("telefone"));
				usuario.setApelido(rsUsuarios.getString("apelido"));


			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsUsuarios, stmt, conn);
		return usuario;

	}
	
	public Collection<Usuario> buscaUsuariosBySupervisor(Long empresa_id, Long organizacao_id, Long supervisor_id) {

		String sql = " SELECT USUARIO.usuario_id, USUARIO.nome, USUARIO.apelido, PARCEIRONEGOCIO.cpf, PARCEIRONEGOCIO.parceironegocio_id " +
				"	FROM ((USUARIO (NOLOCK) "
				+ " 		INNER JOIN PARCEIRONEGOCIO (NOLOCK) ON USUARIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id) "
				+ "			INNER JOIN EMPRESA (NOLOCK) ON USUARIO.empresa_id = EMPRESA.empresa_id) "
				+ "			INNER JOIN ORGANIZACAO (NOLOCK) ON USUARIO.organizacao_id = ORGANIZACAO.organizacao_id	"
				+ "	WHERE USUARIO.isactive = 1 AND PARCEIRONEGOCIO.isactive = 1 AND PARCEIRONEGOCIO.isfuncionario = 1"
				+ "			AND USUARIO.empresa_id = ? AND USUARIO.organizacao_id = ? AND USUARIO.supervisor_usuario_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<Usuario> usuarios = new ArrayList<Usuario>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, supervisor_id);

			this.rsUsuarios = this.stmt.executeQuery();

			while (rsUsuarios.next()) {

				Usuario usuario = new Usuario();
				ParceiroNegocio parceiroNegocio = new ParceiroNegocio();

				usuario.setUsuario_id(rsUsuarios.getLong("usuario_id"));
				usuario.setNome(rsUsuarios.getString("nome"));
				usuario.setApelido(rsUsuarios.getString("apelido"));
				
				parceiroNegocio.setParceiroNegocio_id(rsUsuarios.getLong("parceironegocio_id"));
				parceiroNegocio.setCpf(rsUsuarios.getString("cpf"));
				
				usuario.setParceiroNegocio(parceiroNegocio);

				usuarios.add(usuario);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsUsuarios, stmt, conn);

		return usuarios;

	}

	public Collection<Usuario> buscaUsuariosByPerfilDepartamento(Long empresa_id, Long organizacao_id, String perfil, String departamento) {

		String sql = " SELECT " +
						" USUARIO.usuario_id, USUARIO.nome, USUARIO.apelido, DEPARTAMENTO.nome, USUARIO.parceironegocio_id FROM  USUARIO (NOLOCK) " +   
						" INNER JOIN EMPRESA (NOLOCK) ON USUARIO.empresa_id = EMPRESA.empresa_id   " + 
						" INNER JOIN ORGANIZACAO (NOLOCK) ON USUARIO.organizacao_id = ORGANIZACAO.organizacao_id " +	  
						" INNER JOIN USUARIOPERFIL (NOLOCK) ON USUARIO.usuario_id = USUARIOPERFIL.usuario_id   " +
						" INNER JOIN PERFIL (NOLOCK) ON PERFIL.perfil_id = USUARIOPERFIL.perfil_id  " +
						" INNER JOIN FUNCIONARIO (NOLOCK) ON FUNCIONARIO.parceironegocio_id  = USUARIO.parceironegocio_id " +
						" INNER JOIN DEPARTAMENTO (NOLOCK) ON DEPARTAMENTO.departamento_id = FUNCIONARIO.departamento_id " +
					" WHERE   " +
					" USUARIO.empresa_id = ? " +
					" AND USUARIO.organizacao_id = ? " +  
					" AND PERFIL.nome like ? " +
					" AND DEPARTAMENTO.nome like ? AND USUARIO.isactive = 1 AND FUNCIONARIO.isactive = 1 ";

		this.conn = this.conexao.getConexao();

		Collection<Usuario> usuarios = new ArrayList<Usuario>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + perfil + "%");
			this.stmt.setString(4, "%" + departamento + "%");

			this.rsUsuarios = this.stmt.executeQuery();

			while (rsUsuarios.next()) {

				Usuario usuario = new Usuario();

				usuario.setUsuario_id(rsUsuarios.getLong("usuario_id"));
				usuario.setNome(rsUsuarios.getString("nome"));
				usuario.setApelido(rsUsuarios.getString("apelido"));

				usuarios.add(usuario);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsUsuarios, stmt, conn);
		return usuarios;
	}

}
