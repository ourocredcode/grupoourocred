package br.com.sgo.dao;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.ParceiroLocalidade;

@Component
public class ParceiroLocalidadeDao extends Dao<ParceiroLocalidade> {

	private ConnJDBC conexao;

	public ParceiroLocalidadeDao(Session session,ConnJDBC conexao) {
		super(session, ParceiroLocalidade.class);
		this.conexao = conexao;
	}

}