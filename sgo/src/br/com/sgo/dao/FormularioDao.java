package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Formulario;

@Component
public class FormularioDao extends Dao<Formulario> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsFormulario;

	public FormularioDao(Session session, ConnJDBC conexao) {
		super(session, Formulario.class);
		this.conexao = conexao;
	}

}
