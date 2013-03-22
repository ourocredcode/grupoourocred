package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.EstadoCivil;

@Component
public class EstadoCivilDao extends Dao<EstadoCivil> {
	
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsEstadosCivis;

	public EstadoCivilDao(Session session,ConnJDBC conexao) {
		super(session, EstadoCivil.class);
		this.conexao = conexao;
	}
	
	public Collection<EstadoCivil> buscaEstadosCivis(){

		String sql = "select ESTADOCIVIL.estadocivil_id, ESTADOCIVIL.nome from ESTADOCIVIL (NOLOCK)";

		this.conn = this.conexao.getConexao();

		Collection<EstadoCivil> estadosCivis = new ArrayList<EstadoCivil>();

		try {

			this.stmt = conn.prepareStatement(sql);			
			
			this.rsEstadosCivis = this.stmt.executeQuery();

			while (rsEstadosCivis.next()) {
				
				EstadoCivil estadoCivil = new EstadoCivil();
				estadoCivil.setEstadoCivil_id(rsEstadosCivis.getLong("estadocivil_id"));
				estadoCivil.setNome(rsEstadosCivis.getString("nome"));
				
				estadosCivis.add(estadoCivil);
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsEstadosCivis, stmt, conn);

		return estadosCivis;

	}

}