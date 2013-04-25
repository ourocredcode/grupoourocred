package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.HistoricoHiscon;

@Component
public class HistoricoHisconDao extends Dao<HistoricoHiscon> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsHistoricoHiscon;

	public HistoricoHisconDao(Session session, ConnJDBC conexao) {
		super(session, HistoricoHiscon.class);
		this.conexao = conexao;
	}

}