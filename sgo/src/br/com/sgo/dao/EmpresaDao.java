package br.com.sgo.dao;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Empresa;

@Component
public class EmpresaDao  extends Dao<Empresa> {

	private Session session;

	public EmpresaDao(Session session) {
		super(session, Empresa.class);
		this.session = session;
	}

}
