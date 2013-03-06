package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.hibernate.Session;
import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.PerfilJanelaAcesso;

@Component
public class PerfilJanelaAcessoDao extends Dao<PerfilJanelaAcesso>{

	private Session session;	
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsPerfilJanelaAcesso;

	public PerfilJanelaAcessoDao(Session session, ConnJDBC conexao) {
		
		super(session, PerfilJanelaAcesso.class);
		this.session = session;
		this.conexao = conexao;
		
	}

}
