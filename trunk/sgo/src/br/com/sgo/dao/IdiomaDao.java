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
import br.com.sgo.modelo.Idioma;

@Component
public class IdiomaDao extends Dao<Idioma> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsIdioma;

	private final String sqlIdioma = " SELECT IDIOMA.idioma_id, IDIOMA.nome from IDIOMA (NOLOCK) ";
			
	public IdiomaDao(Session session, ConnJDBC conexao) {

		super(session, Idioma.class);
		this.conexao = conexao;

	}

	public Collection<Idioma> buscaAllIdioma(Long empresa_id) {

		String sql = sqlIdioma;
		
		if (empresa_id != null)
			sql += " WHERE IDIOMA.empresa_id = ?";
		
		this.conn = this.conexao.getConexao();

		Collection<Idioma> idiomas = new ArrayList<Idioma>();

		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);

			this.rsIdioma = this.stmt.executeQuery();

			while (rsIdioma.next()) {
				
				Idioma idioma = new Idioma();

				idioma.setIdioma_id(rsIdioma.getLong("idioma_id"));
				idioma.setNome(rsIdioma.getString("nome"));

				idiomas.add(idioma);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsIdioma, stmt, conn);
		return idiomas;
	}
	
	public Collection<Idioma> buscaIdiomas(Long empresa_id, String nome) {

		String sql = "SELECT IDIOMA.idioma_id, IDIOMA.nome from IDIOMA (NOLOCK) WHERE IDIOMA.empresa_id = ? AND IDIOMA.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<Idioma> idiomas = new ArrayList<Idioma>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setString(2, "%" + nome + "%");

			this.rsIdioma = this.stmt.executeQuery();

			while (rsIdioma.next()) {

				Idioma idioma = new Idioma();

				idioma.setIdioma_id(rsIdioma.getLong("idioma_id"));
				idioma.setNome(rsIdioma.getString("nome"));

				idiomas.add(idioma);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsIdioma, stmt, conn);
		return idiomas;
	}

}