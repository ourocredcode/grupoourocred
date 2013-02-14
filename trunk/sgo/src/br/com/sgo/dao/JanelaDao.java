package br.com.sgo.dao;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Janela;

@Component
public class JanelaDao extends Dao<Janela> {

	private Session session;

	public JanelaDao(Session session) {
		super(session, Janela.class);
		this.session = session;
	}

}
