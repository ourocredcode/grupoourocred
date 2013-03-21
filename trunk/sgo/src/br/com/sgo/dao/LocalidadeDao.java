package br.com.sgo.dao;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Localidade;

@Component
public class LocalidadeDao extends Dao<Localidade> {

	private ConnJDBC conexao;

	public LocalidadeDao(Session session,ConnJDBC conexao) {
		super(session, Localidade.class);
		this.conexao = conexao;
	}

}