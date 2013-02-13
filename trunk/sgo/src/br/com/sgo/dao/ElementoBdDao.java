package br.com.sgo.dao;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.ElementoBd;

@Component
public class ElementoBdDao extends Dao<ElementoBd> {

	private Session session;

	public ElementoBdDao(Session session) {
		super(session, ElementoBd.class);
		this.session = session;
	}

}
