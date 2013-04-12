package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.TipoLocalidade;

@Component
public class TipoLocalidadeDao extends Dao<TipoLocalidade> {

	private Session session;
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsTiposLocalidade;

	public TipoLocalidadeDao(Session session, ConnJDBC conexao) {
		super(session, TipoLocalidade.class);
		this.session = session;
		this.conexao = conexao;
	}

	public TipoLocalidade buscaPorNome(String nome) {

		String sql = "select TIPOLOCALIDADE.tipolocalidade_id, TIPOLOCALIDADE.nome from TIPOLOCALIDADE (NOLOCK) where TIPOLOCALIDADE.nome like ?";

		this.conn = this.conexao.getConexao();
		TipoLocalidade tipoLocalidade = new TipoLocalidade();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setString(1, "%" + nome + "%");
			this.rsTiposLocalidade = this.stmt.executeQuery();

			while (rsTiposLocalidade.next()) {

				tipoLocalidade.setTipoLocalidade_id(rsTiposLocalidade
						.getLong("tipolocalidade_id"));
				tipoLocalidade.setNome(rsTiposLocalidade.getString("nome"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsTiposLocalidade, stmt, conn);

		return tipoLocalidade;

	}

}
