package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Perfil;
import br.com.sgo.modelo.Usuario;

@Component
public class UsuarioDao extends Dao<Usuario> {

	private Session session;
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsUsuarios;


	public UsuarioDao(Session session, ConnJDBC conexao) {
		super(session, Usuario.class);
		this.session = session;
		this.conexao = conexao;
	}

	public Long salva(Usuario a){

		this.session.saveOrUpdate(a);

		return a.getUsuario_id();
	}

	public Usuario find(String login, String senha) {

		String hql = "from Usuario u where u.chave = :login and u.senha = :senha";

		Query query = session.createQuery(hql)
				.setParameter("login", login)
				.setParameter("senha", senha);
		
		return (Usuario) query.uniqueResult();

	}

	public Collection<Usuario> buscaUsuarios(Long empresa_id, Long organizacao_id, String nome){

		String sql = "SELECT USUARIO.usuario_id, USUARIO.nome FROM ((USUARIO (NOLOCK) " +
				"INNER JOIN PARCEIRONEGOCIO (NOLOCK) ON USUARIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id) " +
				"INNER JOIN EMPRESA (NOLOCK) ON USUARIO.empresa_id = EMPRESA.empresa_id) " +
				"INNER JOIN ORGANIZACAO (NOLOCK) ON USUARIO.organizacao_id = ORGANIZACAO.organizacao_id	" +
				"WHERE USUARIO.isactive=1 AND PARCEIRONEGOCIO.isactive=1 AND PARCEIRONEGOCIO.isfuncionario=1" +
				"AND USUARIO.empresa_id = ? AND USUARIO.organizacao_id = ? AND USUARIO.nome like ? ";

		this.conn = this.conexao.getConexao();

		Collection<Usuario> usuarios = new ArrayList<Usuario>();
		try {

			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1, empresa_id);			
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" +  nome + "%");			
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

}
