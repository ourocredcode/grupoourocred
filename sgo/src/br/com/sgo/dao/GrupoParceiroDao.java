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
import br.com.sgo.modelo.GrupoParceiro;

@Component
public class GrupoParceiroDao extends Dao<GrupoParceiro> {

	private Session session;
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsGrupoParceiro;

	public GrupoParceiroDao(Session session, ConnJDBC conexao) {
		super(session, GrupoParceiro.class);
		this.session = session;
		this.conexao = conexao;
	}

	public Collection<GrupoParceiro> buscaGrupoParceiro(Long empresa_id,
			Long organizacao_id, String nome) {

		String sql = "select GRUPOPARCEIRO.grupoparceiro_id, GRUPOPARCEIRO.nome from GRUPOPARCEIRO (NOLOCK) WHERE GRUPOPARCEIRO.empresa_id = ? AND GRUPOPARCEIRO.organizacao_id = ? AND GRUPOPARCEIRO.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<GrupoParceiro> grupoparceiros = new ArrayList<GrupoParceiro>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");
			this.rsGrupoParceiro = this.stmt.executeQuery();

			while (rsGrupoParceiro.next()) {
				GrupoParceiro grupoparceiro = new GrupoParceiro();

				grupoparceiro.setGrupoParceiro_id(rsGrupoParceiro
						.getLong("grupoparceiro_id"));
				grupoparceiro.setNome(rsGrupoParceiro.getString("nome"));

				grupoparceiros.add(grupoparceiro);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsGrupoParceiro, stmt, conn);

		return grupoparceiros;

	}

}