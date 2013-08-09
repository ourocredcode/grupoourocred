package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Perfil;
import br.com.sgo.modelo.Usuario;
import br.com.sgo.modelo.UsuarioPerfil;

@Component
public class UsuarioPerfilDao extends Dao<UsuarioPerfil> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsUsuarioPerfil;
	private ResultSet rsEmpresaPerfil;
	private ResultSet rsOrganizacaoPerfil;
	
	private final String sqlPerfil = " SELECT USUARIOPERFIL.empresa_id, USUARIOPERFIL.organizacao_id, USUARIOPERFIL.usuario_id, USUARIOPERFIL.perfil_id, USUARIOPERFIL.isactive FROM USUARIOPERFIL (NOLOCK) ";
	
	private final String sqlUsuarioPerfis = "SELECT USUARIOPERFIL.usuario_id, USUARIO.nome as usuario_nome, USUARIOPERFIL.perfil_id, PERFIL.nome as perfil_nome "+
						", USUARIOPERFIL.empresa_id, EMPRESA.nome as empresa_nome, USUARIOPERFIL.organizacao_id, ORGANIZACAO.nome as organizacao_nome, USUARIOPERFIL.isactive "+
						" FROM (((USUARIOPERFIL (NOLOCK) INNER JOIN EMPRESA (NOLOCK) ON USUARIOPERFIL.empresa_id = EMPRESA.empresa_id) "+
						" INNER JOIN ORGANIZACAO (NOLOCK) ON USUARIOPERFIL.organizacao_id = ORGANIZACAO.organizacao_id) "+
						" INNER JOIN USUARIO (NOLOCK) ON USUARIOPERFIL.usuario_id = USUARIO.usuario_id) "+
						" INNER JOIN PERFIL (NOLOCK) ON USUARIOPERFIL.perfil_id = PERFIL.perfil_id ";

	public UsuarioPerfilDao(Session session, ConnJDBC conexao) {

		super(session, UsuarioPerfil.class);
		this.conexao = conexao;
	
	}

	public UsuarioPerfil buscaUsuarioPerfilByEmpresaOrganizacaoUsuarioPerfil(Long empresa_id, Long organizacao_id, Long usuario_id, Long perfil_id) {

		String sql = sqlPerfil; 

		this.conn = this.conexao.getConexao();
		
		if (empresa_id != null)
			sql += " WHERE USUARIOPERFIL.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND USUARIOPERFIL.organizacao_id = ?";
		if (usuario_id != null)
			sql += " AND USUARIOPERFIL.usuario_id = ?";
		if (perfil_id != null)
			sql += " AND USUARIOPERFIL.perfil_id = ?";

		UsuarioPerfil usuarioPerfil = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, usuario_id);
			this.stmt.setLong(4, perfil_id);

			this.rsUsuarioPerfil = this.stmt.executeQuery();

			while (rsUsuarioPerfil.next()) {

				usuarioPerfil = new UsuarioPerfil();
				Empresa empresa = new Empresa();
				Organizacao organizacao = new Organizacao();
				Perfil perfil = new Perfil();
				Usuario usuario = new Usuario();
				
				empresa.setEmpresa_id(rsUsuarioPerfil.getLong("empresa_id"));
				organizacao.setOrganizacao_id(rsUsuarioPerfil.getLong("organizacao_id"));
				perfil.setPerfil_id(rsUsuarioPerfil.getLong("perfil_id"));
				usuario.setUsuario_id(rsUsuarioPerfil.getLong("usuario_id"));			
				usuarioPerfil.setIsActive(rsUsuarioPerfil.getBoolean("isactive"));

				usuarioPerfil.setEmpresa(empresa);
				usuarioPerfil.setOrganizacao(organizacao);
				usuarioPerfil.setPerfil(perfil);
				usuarioPerfil.setUsuario(usuario);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsUsuarioPerfil, stmt, conn);
		return usuarioPerfil;
	}

	public Collection<UsuarioPerfil> buscaAllUsuarioPerfilByEmpresaOrganizacao(Long empresa_id, Long organizacao_id) {

		String sql = sqlUsuarioPerfis;

		if (empresa_id != null)
			sql += " WHERE USUARIOPERFIL.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND USUARIOPERFIL.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<UsuarioPerfil> usuarioPerfis = new ArrayList<UsuarioPerfil>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsUsuarioPerfil = this.stmt.executeQuery();

			while (rsUsuarioPerfil.next()) {

				UsuarioPerfil usuarioPerfil = new UsuarioPerfil();
				Empresa empresa = new Empresa();
				Organizacao organizacao = new Organizacao();
				Perfil perfil = new Perfil();
				Usuario usuario = new Usuario();
				
				empresa.setEmpresa_id(rsUsuarioPerfil.getLong("empresa_id"));
				empresa.setNome(rsUsuarioPerfil.getString("empresa_nome"));

				organizacao.setOrganizacao_id(rsUsuarioPerfil.getLong("organizacao_id"));
				organizacao.setNome(rsUsuarioPerfil.getString("organizacao_nome"));				
				
				perfil.setPerfil_id(rsUsuarioPerfil.getLong("perfil_id"));
				perfil.setNome(rsUsuarioPerfil.getString("perfil_nome"));				

				usuario.setUsuario_id(rsUsuarioPerfil.getLong("usuario_id"));
				usuario.setNome(rsUsuarioPerfil.getString("usuario_nome"));	
				
				usuarioPerfil.setEmpresa(empresa);
				usuarioPerfil.setOrganizacao(organizacao);
				usuarioPerfil.setPerfil(perfil);
				usuarioPerfil.setUsuario(usuario);
				usuarioPerfil.setIsActive(rsUsuarioPerfil.getBoolean("isactive"));

				usuarioPerfis.add(usuarioPerfil);
				

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsUsuarioPerfil, stmt, conn);

		return usuarioPerfis;

	}
	
	public Collection<Perfil> buscaUsuarioPerfilAcesso(Usuario u) {

		String sql = "SELECT PERFIL.perfil_id ,PERFIL.nome  FROM (ORGANIZACAO (NOLOCK) "
				+ "INNER JOIN (USUARIO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) "
				+ "INNER JOIN USUARIOPERFIL (NOLOCK)  "
				+ "ON EMPRESA.empresa_id = USUARIOPERFIL.empresa_id) "
				+ "ON USUARIO.usuario_id = USUARIOPERFIL.usuario_id) "
				+ "ON ORGANIZACAO.organizacao_id = USUARIOPERFIL.organizacao_id) "
				+ "INNER JOIN PERFIL (NOLOCK) "
				+ "ON USUARIOPERFIL.perfil_id = PERFIL.perfil_id "
				+ "WHERE USUARIOPERFIL.isactive=1 and USUARIOPERFIL.usuario_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<Perfil> perfis = new ArrayList<Perfil>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, u.getUsuario_id());
			this.rsUsuarioPerfil = this.stmt.executeQuery();

			while (rsUsuarioPerfil.next()) {

				Perfil p = new Perfil();

				p.setPerfil_id(rsUsuarioPerfil.getLong("perfil_id"));
				p.setNome(rsUsuarioPerfil.getString("nome"));

				perfis.add(p);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsUsuarioPerfil, stmt, conn);

		return perfis;
	}

	public Collection<Empresa> buscaEmpresaPerfilAcesso(Long perfil_id,	Long usuario_id) {

		String sql = "SELECT DISTINCT(EMPRESA.empresa_id), EMPRESA.nome "
				+ " FROM (ORGANIZACAO (NOLOCK) "
				+ " INNER JOIN (((USUARIO (NOLOCK) "
				+ " INNER JOIN (EMPRESA (NOLOCK) "
				+ " INNER JOIN USUARIOPERFIL (NOLOCK) ON EMPRESA.empresa_id = USUARIOPERFIL.empresa_id) ON USUARIO.usuario_id = USUARIOPERFIL.usuario_id) "
				+ " INNER JOIN PERFIL (NOLOCK) ON USUARIOPERFIL.perfil_id = PERFIL.perfil_id) "
				+ " INNER JOIN PERFILORGACESSO (NOLOCK) ON (PERFILORGACESSO.perfil_id = PERFIL.perfil_id) "
				+ " 	AND (USUARIOPERFIL.perfil_id = PERFILORGACESSO.perfil_id)) ON ORGANIZACAO.organizacao_id = PERFILORGACESSO.organizacao_id) "
				+ " INNER JOIN USUARIOORGACESSO ON (USUARIOORGACESSO.organizacao_id = ORGANIZACAO.organizacao_id) AND (USUARIO.usuario_id = USUARIOORGACESSO.usuario_id) "
				+ " WHERE USUARIO.isactive=1 "
				+ "	AND USUARIOPERFIL.isactive=1 "
				+ "	AND PERFILORGACESSO.isactive=1 "
				+ "	AND USUARIOORGACESSO.isactive=1 "
				+ "	AND PERFIL.perfil_id = ? "
				+ "   AND USUARIO.usuario_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<Empresa> empresas = new ArrayList<Empresa>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, perfil_id);
			this.stmt.setLong(2, usuario_id);

			this.rsEmpresaPerfil = this.stmt.executeQuery();

			while (rsEmpresaPerfil.next()) {

				Empresa empresa = new Empresa();

				empresa.setEmpresa_id(rsEmpresaPerfil.getLong("empresa_id"));
				empresa.setNome(rsEmpresaPerfil.getString("nome"));

				empresas.add(empresa);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsEmpresaPerfil, stmt, conn);

		return empresas;

	}

	public Collection<Organizacao> buscaOrganizacaoPerfilAcesso(Long perfil_id,
			Long empresa_id, Long usuario_id) {

		String sql = "SELECT ORGANIZACAO.organizacao_id, ORGANIZACAO.nome "
				+ " FROM (ORGANIZACAO (NOLOCK) "
				+ " INNER JOIN (((USUARIO (NOLOCK) "
				+ " INNER JOIN (EMPRESA (NOLOCK) "
				+ " INNER JOIN USUARIOPERFIL (NOLOCK) ON EMPRESA.empresa_id = USUARIOPERFIL.empresa_id) ON USUARIO.usuario_id = USUARIOPERFIL.usuario_id) "
				+ " INNER JOIN PERFIL (NOLOCK) ON USUARIOPERFIL.perfil_id = PERFIL.perfil_id) "
				+ " INNER JOIN PERFILORGACESSO (NOLOCK) ON (PERFILORGACESSO.perfil_id = PERFIL.perfil_id) "
				+ " 	AND (USUARIOPERFIL.perfil_id = PERFILORGACESSO.perfil_id)) ON ORGANIZACAO.organizacao_id = PERFILORGACESSO.organizacao_id) "
				+ " INNER JOIN USUARIOORGACESSO ON (USUARIOORGACESSO.organizacao_id = ORGANIZACAO.organizacao_id) AND (USUARIO.usuario_id = USUARIOORGACESSO.usuario_id) "
				+ " WHERE USUARIO.isactive=1 "
				+ "	AND USUARIOPERFIL.isactive=1 "
				+ "	AND PERFILORGACESSO.isactive=1 "
				+ "	AND USUARIOORGACESSO.isactive=1 "
				+ "	AND PERFIL.perfil_id = ? "
				+ "   AND USUARIO.usuario_id = ? "
				+ "   AND EMPRESA.empresa_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<Organizacao> organizacoes = new ArrayList<Organizacao>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, perfil_id);
			this.stmt.setLong(2, usuario_id);
			this.stmt.setLong(3, empresa_id);

			this.rsOrganizacaoPerfil = this.stmt.executeQuery();

			while (rsOrganizacaoPerfil.next()) {

				Organizacao organizacao = new Organizacao();

				organizacao.setOrganizacao_id(rsOrganizacaoPerfil.getLong("organizacao_id"));
				organizacao.setNome(rsOrganizacaoPerfil.getString("nome"));

				organizacoes.add(organizacao);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsOrganizacaoPerfil, stmt, conn);

		return organizacoes;

	}

	public void insert(UsuarioPerfil usuarioPerfil) throws SQLException {

		String sql = "INSERT INTO USUARIOPERFIL (empresa_id, organizacao_id, usuario_id, perfil_id, created, updated, createdby, updatedby, isactive) "
				+ "    VALUES (?,?,?,?,?,?,?,?,?)";

		this.conn = this.conexao.getConexao();

		try {

			this.conn.setAutoCommit(false);

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, usuarioPerfil.getEmpresa().getEmpresa_id());
			this.stmt.setLong(2, usuarioPerfil.getOrganizacao().getOrganizacao_id());
			this.stmt.setLong(3, usuarioPerfil.getUsuario().getUsuario_id());
			this.stmt.setLong(4, usuarioPerfil.getPerfil().getPerfil_id());
			this.stmt.setTimestamp(5, new Timestamp(usuarioPerfil.getCreated().getTimeInMillis()));
			this.stmt.setTimestamp(6, new Timestamp(usuarioPerfil.getUpdated().getTimeInMillis()));
			this.stmt.setLong(7, usuarioPerfil.getCreatedBy().getUsuario_id());
			this.stmt.setLong(8, usuarioPerfil.getUpdatedBy().getUsuario_id());
			this.stmt.setBoolean(9, usuarioPerfil.getIsActive());

			this.stmt.executeUpdate();

			this.conn.commit();

		} catch (SQLException e) {

			this.conn.rollback();
			throw e;

		} finally {

			this.conn.setAutoCommit(true);

		}
		this.conexao.closeConnection(stmt, conn);
	}

	public void altera(UsuarioPerfil usuarioPerfil) throws SQLException {

		String sql = "UPDATE USUARIOPERFIL SET updated=? , updatedby=?, isactive=? "
				+ " WHERE USUARIOPERFIL.empresa_id=? AND USUARIOPERFIL.organizacao_id=? AND USUARIOPERFIL.usuario_id=? AND USUARIOPERFIL.perfil_id=? ";

		this.conn = this.conexao.getConexao();

		try {

			this.conn.setAutoCommit(false);

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setTimestamp(1, new Timestamp(usuarioPerfil.getUpdated().getTimeInMillis()));
			this.stmt.setLong(2, usuarioPerfil.getUpdatedBy().getUsuario_id());
			this.stmt.setBoolean(3, usuarioPerfil.getIsActive());
			this.stmt.setLong(4, usuarioPerfil.getEmpresa().getEmpresa_id());
			this.stmt.setLong(5, usuarioPerfil.getOrganizacao().getOrganizacao_id());
			this.stmt.setLong(6, usuarioPerfil.getUsuario().getUsuario_id());
			this.stmt.setLong(7, usuarioPerfil.getPerfil().getPerfil_id());

			this.stmt.executeUpdate();

			this.conn.commit();

		} catch (SQLException e) {

			this.conn.rollback();
			throw e;

		} finally {

			this.conn.setAutoCommit(true);

		}

		this.conexao.closeConnection(stmt, conn);
	}

}
