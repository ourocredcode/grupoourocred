package br.com.sgo.dao;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.TipoDadoBd;

@Component
public class TipoDadoBdDao extends Dao<TipoDadoBd> {

	private Session session;

	public TipoDadoBdDao(Session session) {
		super(session, TipoDadoBd.class);
		this.session = session;
	}

}
