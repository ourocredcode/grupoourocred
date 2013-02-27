package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class PerfilDao extends Dao<Perfil> {

	private Session session;
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsUsuarioPerfil;

	public PerfilDao(Session session,ConnJDBC conexao) {
		super(session, Perfil.class);
		this.session = session;
		this.conexao = conexao;
	}

	public Collection<UsuarioPerfil> buscaPerfilPorUsuario(Usuario u){

		return new ArrayList<UsuarioPerfil>();

	}

}
