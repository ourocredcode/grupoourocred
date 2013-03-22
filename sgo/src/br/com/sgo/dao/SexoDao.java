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
import br.com.sgo.modelo.Sexo;

@Component
public class SexoDao extends Dao<Sexo> {
	
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsSexo;

	public SexoDao(Session session, ConnJDBC conexao) {
		super(session, Sexo.class);
		this.conexao = conexao;
	}
	
	public Collection<Sexo> buscaSexos(){

		String sql = "select SEXO.sexo_id, SEXO.nome from SEXO (NOLOCK)";

		this.conn = this.conexao.getConexao();

		Collection<Sexo> sexos = new ArrayList<Sexo>();

		try {

			this.stmt = conn.prepareStatement(sql);			
			
			this.rsSexo = this.stmt.executeQuery();

			while (rsSexo.next()) {
				
				Sexo sexo = new Sexo();
				sexo.setSexo_id(rsSexo.getLong("sexo_id"));
				sexo.setNome(rsSexo.getString("nome"));
				
				sexos.add(sexo);
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsSexo, stmt, conn);

		return sexos;

	}

}