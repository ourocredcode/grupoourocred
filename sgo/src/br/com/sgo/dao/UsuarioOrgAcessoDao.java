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
import br.com.sgo.modelo.Usuario;
import br.com.sgo.modelo.UsuarioOrgAcesso;

@Component
public class UsuarioOrgAcessoDao extends Dao<UsuarioOrgAcesso> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsUsuarioOrgAcesso;

	private final String sqlUsuarioOrgAcesso = "SELECT USUARIOORGACESSO.organizacao_id, USUARIOORGACESSO.empresa_id, USUARIOORGACESSO.usuario_id FROM USUARIOORGACESSO (NOLOCK) ";

	private final String sqlUsuariosOrgAcesso = "SELECT USUARIOORGACESSO.empresa_id, EMPRESA.nome as empresa_nome, USUARIOORGACESSO.organizacao_id "
			+ ", ORGANIZACAO.nome AS organizacao_nome, USUARIOORGACESSO.usuario_id, USUARIO.nome AS usuario_nome, USUARIO.chave, USUARIOORGACESSO.isactive "
			+ " FROM ((USUARIOORGACESSO (NOLOCK) INNER JOIN EMPRESA (NOLOCK) ON USUARIOORGACESSO.empresa_id = EMPRESA.empresa_id) "
			+ " INNER JOIN ORGANIZACAO(NOLOCK) ON USUARIOORGACESSO.organizacao_id = ORGANIZACAO.organizacao_id) "
			+ " INNER JOIN USUARIO (NOLOCK) ON USUARIOORGACESSO.usuario_id = USUARIO.usuario_id ";

	public UsuarioOrgAcessoDao(Session session, ConnJDBC conexao) {

		super(session, UsuarioOrgAcesso.class);
		this.conexao = conexao;

	}

	public Collection<UsuarioOrgAcesso> buscaAllUsuarioOrgAcessoByEmpresaOrganizacao(
			Long empresa_id, Long organizacao_id) {

		String sql = sqlUsuariosOrgAcesso;

		if (empresa_id != null)
			sql += " WHERE USUARIOORGACESSO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND USUARIOORGACESSO.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<UsuarioOrgAcesso> usuariosOrgAcesso = new ArrayList<UsuarioOrgAcesso>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsUsuarioOrgAcesso = this.stmt.executeQuery();

			while (rsUsuarioOrgAcesso.next()) {

				UsuarioOrgAcesso usuarioOrgAcesso = new UsuarioOrgAcesso();
				Empresa empresa = new Empresa();
				Organizacao organizacao = new Organizacao();
				Usuario usuario = new Usuario();

				empresa.setEmpresa_id(rsUsuarioOrgAcesso.getLong("empresa_id"));
				empresa.setNome(rsUsuarioOrgAcesso.getString("empresa_nome"));

				organizacao.setOrganizacao_id(rsUsuarioOrgAcesso
						.getLong("organizacao_id"));
				organizacao.setNome(rsUsuarioOrgAcesso
						.getString("organizacao_nome"));

				usuario.setUsuario_id(rsUsuarioOrgAcesso.getLong("usuario_id"));
				usuario.setNome(rsUsuarioOrgAcesso.getString("usuario_nome"));
				usuario.setChave(rsUsuarioOrgAcesso.getString("chave"));

				usuarioOrgAcesso.setEmpresa(empresa);
				usuarioOrgAcesso.setOrganizacao(organizacao);
				usuarioOrgAcesso.setUsuario(usuario);
				usuarioOrgAcesso.setIsActive(rsUsuarioOrgAcesso
						.getBoolean("isactive"));

				usuariosOrgAcesso.add(usuarioOrgAcesso);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsUsuarioOrgAcesso, stmt, conn);

		return usuariosOrgAcesso;

	}

	public UsuarioOrgAcesso buscaUsuarioOrgAcessoByEmpresaOrganizacaoUsuarioPerfil(
			Long empresa_id, Long organizacao_id, Long usuario_id) {

		String sql = sqlUsuarioOrgAcesso;

		this.conn = this.conexao.getConexao();

		if (empresa_id != null)
			sql += " WHERE USUARIOORGACESSO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND USUARIOORGACESSO.organizacao_id = ?";
		if (usuario_id != null)
			sql += " AND USUARIOORGACESSO.usuario_id = ?";

		UsuarioOrgAcesso usuarioOrgAcesso = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, usuario_id);

			this.rsUsuarioOrgAcesso = this.stmt.executeQuery();

			while (rsUsuarioOrgAcesso.next()) {

				Empresa empresa = new Empresa();
				Organizacao organizacao = new Organizacao();
				usuarioOrgAcesso = new UsuarioOrgAcesso();
				Usuario usuario = new Usuario();

				empresa.setEmpresa_id(rsUsuarioOrgAcesso.getLong("empresa_id"));
				organizacao.setOrganizacao_id(rsUsuarioOrgAcesso
						.getLong("organizacao_id"));
				usuario.setUsuario_id(rsUsuarioOrgAcesso.getLong("usuario_id"));

				usuarioOrgAcesso.setEmpresa(empresa);
				usuarioOrgAcesso.setOrganizacao(organizacao);
				usuarioOrgAcesso.setUsuario(usuario);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsUsuarioOrgAcesso, stmt, conn);
		return usuarioOrgAcesso;
	}

	public void insert(UsuarioOrgAcesso usuarioOrgAcesso) throws SQLException {

		String sql = "INSERT INTO USUARIOORGACESSO (usuario_id, empresa_id, organizacao_id, created, updated, createdby, updatedby, isactive) VALUES (?,?,?,?,?,?,?,?)";

		this.conn = this.conexao.getConexao();

		try {

			this.conn.setAutoCommit(false);

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, usuarioOrgAcesso.getUsuario().getUsuario_id());
			this.stmt.setLong(2, usuarioOrgAcesso.getEmpresa().getEmpresa_id());
			this.stmt.setLong(3, usuarioOrgAcesso.getOrganizacao()
					.getOrganizacao_id());
			this.stmt.setTimestamp(4, new Timestamp(usuarioOrgAcesso
					.getCreated().getTimeInMillis()));
			this.stmt.setTimestamp(5, new Timestamp(usuarioOrgAcesso
					.getUpdated().getTimeInMillis()));
			this.stmt.setLong(6, usuarioOrgAcesso.getCreatedBy()
					.getUsuario_id());
			this.stmt.setLong(7, usuarioOrgAcesso.getUpdatedBy()
					.getUsuario_id());
			this.stmt.setBoolean(8, usuarioOrgAcesso.getIsActive());

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

	public void altera(UsuarioOrgAcesso usuarioOrgAcesso) throws SQLException {

		String sql = "UPDATE UsuarioOrgAcesso SET updated=? , updatedby=?, isactive=? "
				+ " WHERE UsuarioOrgAcesso.empresa_id=? AND UsuarioOrgAcesso.organizacao_id=? AND UsuarioOrgAcesso.usuario_id=? ";

		this.conn = this.conexao.getConexao();

		try {

			this.conn.setAutoCommit(false);

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setTimestamp(1, new Timestamp(usuarioOrgAcesso
					.getUpdated().getTimeInMillis()));
			this.stmt.setLong(2, usuarioOrgAcesso.getUpdatedBy()
					.getUsuario_id());
			this.stmt.setBoolean(3, usuarioOrgAcesso.getIsActive());
			this.stmt.setLong(4, usuarioOrgAcesso.getEmpresa().getEmpresa_id());
			this.stmt.setLong(5, usuarioOrgAcesso.getOrganizacao()
					.getOrganizacao_id());
			this.stmt.setLong(6, usuarioOrgAcesso.getUsuario().getUsuario_id());

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
