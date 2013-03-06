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
import br.com.sgo.modelo.Perfil;
import br.com.sgo.modelo.Usuario;
import br.com.sgo.modelo.UsuarioPerfil;

@Component
public class UsuarioPerfilDao extends Dao<UsuarioPerfil> {

	private Session session;
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsUsuarioPerfil;
	private ResultSet rsEmpresaPerfil;
	private ResultSet rsOrganizacaoPerfil;

	public UsuarioPerfilDao(Session session,ConnJDBC conexao) {
		super(session, UsuarioPerfil.class);
		this.session = session;
		this.conexao = conexao;
	}

	public Collection<Perfil> buscaUsuarioPerfilAcesso(Usuario u){

		String sql = "SELECT PERFIL.perfil_id ,PERFIL.nome  FROM (ORGANIZACAO (NOLOCK) " +
				"INNER JOIN (USUARIO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) " +	
				"INNER JOIN USUARIOPERFIL (NOLOCK)  " +
					"ON EMPRESA.empresa_id = USUARIOPERFIL.empresa_id) " +
					"ON USUARIO.usuario_id = USUARIOPERFIL.usuario_id) " +
					"ON ORGANIZACAO.organizacao_id = USUARIOPERFIL.organizacao_id) " +
				"INNER JOIN PERFIL (NOLOCK) " +
					"ON USUARIOPERFIL.perfil_id = PERFIL.perfil_id " +
				"WHERE USUARIOPERFIL.isactive=1 and USUARIOPERFIL.usuario_id = ?";

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

		this.conexao.closeConnection(rsUsuarioPerfil,stmt,conn);

		return perfis;
	}
	
	public Collection<Empresa> buscaEmpresaPerfilAcesso(Long perfil_id, Long usuario_id){

		String sql = "SELECT DISTINCT(EMPRESA.empresa_id), EMPRESA.nome " +
				" FROM (ORGANIZACAO (NOLOCK) " +
				" INNER JOIN (((USUARIO (NOLOCK) " +
				" INNER JOIN (EMPRESA (NOLOCK) " +
				" INNER JOIN USUARIOPERFIL (NOLOCK) ON EMPRESA.empresa_id = USUARIOPERFIL.empresa_id) ON USUARIO.usuario_id = USUARIOPERFIL.usuario_id) " +
				" INNER JOIN PERFIL (NOLOCK) ON USUARIOPERFIL.perfil_id = PERFIL.perfil_id) " +
				" INNER JOIN PERFILORGACESSO (NOLOCK) ON (PERFILORGACESSO.perfil_id = PERFIL.perfil_id) " +
				" 	AND (USUARIOPERFIL.perfil_id = PERFILORGACESSO.perfil_id)) ON ORGANIZACAO.organizacao_id = PERFILORGACESSO.organizacao_id) " +
				" INNER JOIN USUARIOORGACESSO ON (USUARIOORGACESSO.organizacao_id = ORGANIZACAO.organizacao_id) AND (USUARIO.usuario_id = USUARIOORGACESSO.usuario_id) " +
				" WHERE USUARIO.isactive=1 " +
				"	AND USUARIOPERFIL.isactive=1 " +
				"	AND PERFILORGACESSO.isactive=1 " +
				"	AND USUARIOORGACESSO.isactive=1 " +
				"	AND PERFIL.perfil_id = ? " +
				"   AND USUARIO.usuario_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<Empresa> empresas = new ArrayList<Empresa>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1,perfil_id);
			this.stmt.setLong(2,usuario_id);

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

		this.conexao.closeConnection(rsUsuarioPerfil,stmt,conn);

		return empresas;

	}
	
	public Collection<Organizacao> buscaOrganizacaoPerfilAcesso(Long perfil_id, Long empresa_id,Long usuario_id){

		String sql = "SELECT ORGANIZACAO.organizacao_id, ORGANIZACAO.nome " +
				" FROM (ORGANIZACAO (NOLOCK) " +
				" INNER JOIN (((USUARIO (NOLOCK) " +
				" INNER JOIN (EMPRESA (NOLOCK) " +
				" INNER JOIN USUARIOPERFIL (NOLOCK) ON EMPRESA.empresa_id = USUARIOPERFIL.empresa_id) ON USUARIO.usuario_id = USUARIOPERFIL.usuario_id) " +
				" INNER JOIN PERFIL (NOLOCK) ON USUARIOPERFIL.perfil_id = PERFIL.perfil_id) " +
				" INNER JOIN PERFILORGACESSO (NOLOCK) ON (PERFILORGACESSO.perfil_id = PERFIL.perfil_id) " +
				" 	AND (USUARIOPERFIL.perfil_id = PERFILORGACESSO.perfil_id)) ON ORGANIZACAO.organizacao_id = PERFILORGACESSO.organizacao_id) " +
				" INNER JOIN USUARIOORGACESSO ON (USUARIOORGACESSO.organizacao_id = ORGANIZACAO.organizacao_id) AND (USUARIO.usuario_id = USUARIOORGACESSO.usuario_id) " +
				" WHERE USUARIO.isactive=1 " +
				"	AND USUARIOPERFIL.isactive=1 " +
				"	AND PERFILORGACESSO.isactive=1 " +
				"	AND USUARIOORGACESSO.isactive=1 " +
				"	AND PERFIL.perfil_id = ? " +
				"   AND USUARIO.usuario_id = ? " +
				"   AND EMPRESA.empresa_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<Organizacao> organizacoes = new ArrayList<Organizacao>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1,perfil_id);
			this.stmt.setLong(2,usuario_id);
			this.stmt.setLong(3,empresa_id);

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

		this.conexao.closeConnection(rsUsuarioPerfil,stmt,conn);

		return organizacoes;

	}

}
