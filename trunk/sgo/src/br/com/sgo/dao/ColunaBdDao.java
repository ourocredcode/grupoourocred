package br.com.sgo.dao;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.ColunaBd;

@Component
public class ColunaBdDao extends Dao<ColunaBd> {

	private Session session;

	public ColunaBdDao(Session session) {
		super(session, ColunaBd.class);
		this.session = session;
	}

}
