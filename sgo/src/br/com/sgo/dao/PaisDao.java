package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Pais;

@Component
public class PaisDao extends Dao<Pais> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsPaises;

	public PaisDao(Session session, ConnJDBC conexao) {
		super(session, Pais.class);
		this.conexao = conexao;
	}

	public Pais buscaPais(String nome) {

		String sql = "select PAIS.pais_id,PAIS.nome from PAIS (NOLOCK) WHERE PAIS.nome like ? ";

		this.conn = this.conexao.getConexao();

		Pais pais = new Pais();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setString(1, "%" + nome + "%");

			this.rsPaises = this.stmt.executeQuery();

			while (rsPaises.next()) {

				pais.setPais_id(rsPaises.getLong("pais_id"));
				pais.setNome(rsPaises.getString("nome"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsPaises, stmt, conn);

		return pais;

	}

}