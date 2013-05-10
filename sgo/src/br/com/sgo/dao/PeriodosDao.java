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
import br.com.sgo.modelo.Periodos;

@Component
public class PeriodosDao extends Dao<Periodos> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsPeriodos;

	private final String sqlPeriodos = "SELECT PERIODOS.periodos_id, PERIODOS.nome, PERIODOS.empresa_id, PERIODOS.organizacao_id FROM PERIODOS (NOLOCK)";

	public PeriodosDao(Session session, ConnJDBC conexao) {
		super(session, Periodos.class);
		this.conexao = conexao;
	}

	public Collection<Periodos> buscaAllPeriodos() {

		String sql = sqlPeriodos;

		this.conn = this.conexao.getConexao();

		Collection<Periodos> periodos = new ArrayList<Periodos>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.rsPeriodos = this.stmt.executeQuery();

			while (rsPeriodos.next()) {

				Periodos periodo = new Periodos();
				periodo.setPeriodos_id(rsPeriodos.getLong("periodo_id"));
				periodo.setNome(rsPeriodos.getString("nome"));
				periodos.add(periodo);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsPeriodos, stmt, conn);
		return periodos;
	}

	public Periodos buscaPeriodosByEmOrBanNome(Long empresa_id, Long organizacao_id, String nome) {

		String sql = sqlPeriodos;

		if (empresa_id != null)
			sql += " WHERE PERIODOS.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND PERIODOS.organizacao_id = ?";
		if (nome != null)
			sql += " AND PERIODOS.nome like ?";

		this.conn = this.conexao.getConexao();

		Periodos periodo = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);		
			this.stmt.setString(3, "%" + nome + "%");

			this.rsPeriodos = this.stmt.executeQuery();

			while (rsPeriodos.next()) {

				periodo = new Periodos();
				periodo.setPeriodos_id(rsPeriodos.getLong("periodo_id"));
				periodo.setNome(rsPeriodos.getString("nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsPeriodos, stmt, conn);
		return periodo;
	}

}