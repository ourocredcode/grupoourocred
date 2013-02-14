package br.com.sgo.dao;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Perfil;

@Component
public class PerfilDao extends Dao<Perfil> {

	private Session session;

	public PerfilDao(Session session) {
		super(session, Perfil.class);
		this.session = session;
	}

}
