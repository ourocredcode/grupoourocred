package br.com.sgo.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Usuario;

@Component
public class UsuarioDao extends Dao<Usuario> {
	
	private Session session;

	public UsuarioDao(Session session) {
		super(session, Usuario.class);
		this.session = session;
	}
	
	public Long salva(Usuario a){

    	this.session.saveOrUpdate(a);

    	return a.getUsuario_id();
    }
	
	public Usuario find(String login, String senha) {

		String hql = "from Usuario u where u.chave = :login and u.senha = :senha";

		Query query = session.createQuery(hql)
			.setParameter("login", login)
			.setParameter("senha", senha);

		return (Usuario) query.uniqueResult();

	}

}
