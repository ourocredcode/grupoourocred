package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Funcionario;

@Component
public class FuncionarioDao extends Dao<Funcionario> {

	private Session session;
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsUsuarios;


	public FuncionarioDao(Session session, ConnJDBC conexao) {
		super(session, Funcionario.class);
		this.session = session;
		this.conexao = conexao;
	}

}
