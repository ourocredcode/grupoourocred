package br.com.sgo.dao;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Usuario;

@Component
public class UsuarioDao extends Dao<Usuario> {
	
	private Session session;

	public UsuarioDao(Session session) {
		super(session, Usuario.class);
		this.session = session;
	}

}
