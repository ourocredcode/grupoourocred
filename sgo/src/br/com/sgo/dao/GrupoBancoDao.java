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
import br.com.sgo.modelo.GrupoBanco;

@Component
public class GrupoBancoDao extends Dao<GrupoBanco> {

	private Session session;
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsGrupoParceiro;

	public GrupoBancoDao(Session session, ConnJDBC conexao) {
		super(session, GrupoBanco.class);
		this.session = session;
		this.conexao = conexao;
	}

	public Collection<GrupoBanco> buscaGrupoBanco(Long empresa_id,
			Long organizacao_id, String nome) {

		String sql = "select GRUPOBANCO.grupobanco_id , GRUPOBANCO.nome from GRUPOBANCO (NOLOCK) "
				+ "			WHERE GRUPOBANCO.empresa_id = ? "
				+ "		AND GRUPOBANCO.organizacao_id = ? "
				+ "		AND GRUPOBANCO.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<GrupoBanco> gruposBanco = new ArrayList<GrupoBanco>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");
			this.rsGrupoParceiro = this.stmt.executeQuery();

			while (rsGrupoParceiro.next()) {

				GrupoBanco grupoBanco = new GrupoBanco();

				grupoBanco.setGrupoBanco_id(rsGrupoParceiro
						.getLong("grupobanco_id"));
				grupoBanco.setNome(rsGrupoParceiro.getString("nome"));

				gruposBanco.add(grupoBanco);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsGrupoParceiro, stmt, conn);

		return gruposBanco;

	}

}