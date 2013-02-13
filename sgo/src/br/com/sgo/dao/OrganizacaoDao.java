package br.com.sgo.dao;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Organizacao;

@Component
public class OrganizacaoDao extends Dao<Organizacao> {

	private Session session;

	public OrganizacaoDao(Session session) {
		super(session, Organizacao.class);
		this.session = session;
	}

}
