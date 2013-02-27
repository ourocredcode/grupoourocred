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

	public UsuarioPerfilDao(Session session,ConnJDBC conexao) {
		super(session, UsuarioPerfil.class);
		this.session = session;
		this.conexao = conexao;
	}

	public Collection<Perfil> buscaPerfilPorUsuario(Usuario u){

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

		this.conexao.closeConnection(conn);

		return perfis;
	}

}
