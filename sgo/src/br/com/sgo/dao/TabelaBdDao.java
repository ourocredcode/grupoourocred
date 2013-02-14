package br.com.sgo.dao;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.TabelaBd;

@Component
public class TabelaBdDao extends Dao<TabelaBd> {

	private Session session;

	public TabelaBdDao(Session session) {
		super(session, TabelaBd.class);
		this.session = session;
	}

}
