package br.com.sgo.dao;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.CampoFormulario;

@Component
public class CampoFormularioDao extends Dao<CampoFormulario> {

	private Session session;	
	private ConnJDBC conexao;

	public CampoFormularioDao(Session session, ConnJDBC conexao) {
		super(session, CampoFormulario.class);
		this.session = session;
		this.conexao = conexao;
	}


}
