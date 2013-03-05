package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.CampoFormulario;

@Component
public class CampoFormularioDao extends Dao<CampoFormulario> {

	private Session session;	
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsCamposFormulario;

	public CampoFormularioDao(Session session, ConnJDBC conexao) {
		super(session, CampoFormulario.class);
		this.session = session;
		this.conexao = conexao;
	}


}
