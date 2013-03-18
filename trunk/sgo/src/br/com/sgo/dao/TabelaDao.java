package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Tabela;

@Component
public class TabelaDao extends Dao<Tabela> {

	private Session session;	
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;

	public TabelaDao(Session session , ConnJDBC conexao) {
		super(session, Tabela.class);
		this.session = session;
		this.conexao =conexao;
	}

}
