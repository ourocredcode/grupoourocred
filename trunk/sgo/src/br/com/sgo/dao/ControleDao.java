package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Controle;

@Component
public class ControleDao extends Dao<Controle> {

	private Session session;
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsControle;

	public ControleDao(Session session, ConnJDBC conexao) {
		super(session, Controle.class);
		this.session = session;
		this.conexao = conexao;
	}

}