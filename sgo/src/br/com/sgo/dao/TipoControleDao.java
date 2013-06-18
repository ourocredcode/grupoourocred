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
import br.com.sgo.modelo.TipoControle;

@Component
public class TipoControleDao extends Dao<TipoControle> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsTipoControle;

	private final String sqlTiposControle = "SELECT TIPOCONTROLE.tipocontrole_id, TIPOCONTROLE.nome, TIPOCONTROLE.empresa_id, TIPOCONTROLE.organizacao_id FROM TIPOCONTROLE (NOLOCK)";

	public TipoControleDao(Session session, ConnJDBC conexao) {
		super(session, TipoControle.class);
		this.conexao = conexao;
	}

	public Collection<TipoControle> buscaAllTiposControle() {

		String sql = sqlTiposControle;

		this.conn = this.conexao.getConexao();

		Collection<TipoControle> tiposcontrole = new ArrayList<TipoControle>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.rsTipoControle = this.stmt.executeQuery();

			while (rsTipoControle.next()) {

				TipoControle tipoControle = new TipoControle();
				tipoControle.setTipoControle_id(rsTipoControle.getLong("tipocontrole_id"));
				tipoControle.setNome(rsTipoControle.getString("nome"));
				tiposcontrole.add(tipoControle);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsTipoControle, stmt, conn);
		return tiposcontrole;

	}

	public TipoControle buscaTipoControleByNome(String nome) {

		String sql = sqlTiposControle;

		if (nome != null)
			sql += " WHERE TIPOCONTROLE.nome like ?";

		this.conn = this.conexao.getConexao();

		TipoControle tipoControle = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setString(1, "%" + nome + "%");

			this.rsTipoControle = this.stmt.executeQuery();

			while (rsTipoControle.next()) {
				
				tipoControle = new TipoControle();
				tipoControle.setTipoControle_id(rsTipoControle.getLong("tipocontrole_id"));
				tipoControle.setNome(rsTipoControle.getString("nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsTipoControle, stmt, conn);
		return tipoControle;
	}
}