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
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Periodo;

@Component
public class PeriodoDao extends Dao<Periodo> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsPeriodos;

	private final String sqlPeriodos = "SELECT PERIODO.periodos_id, PERIODO.nome, PERIODO.empresa_id, PERIODO.organizacao_id FROM PERIODO (NOLOCK)";

	public PeriodoDao(Session session, ConnJDBC conexao) {
		super(session, Periodo.class);
		this.conexao = conexao;
	}

	public Collection<Periodo> buscaAllPeriodos() {

		String sql = sqlPeriodos;

		this.conn = this.conexao.getConexao();

		Collection<Periodo> periodos = new ArrayList<Periodo>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.rsPeriodos = this.stmt.executeQuery();

			while (rsPeriodos.next()) {

				Periodo periodo = new Periodo();
				periodo.setPeriodo_id(rsPeriodos.getLong("periodo_id"));
				periodo.setNome(rsPeriodos.getString("nome"));
				periodos.add(periodo);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsPeriodos, stmt, conn);
		return periodos;
	}

	public Periodo buscaPeriodosByEmOrBanNome(Empresa empresa, Organizacao organizacao, String nome) {

		String sql = sqlPeriodos;

		if (empresa != null)
			sql += " WHERE PERIODO.empresa_id = ?";
		if (organizacao != null)
			sql += " AND PERIODO.organizacao_id = ?";
		if (nome != null)
			sql += " AND PERIODO.nome like ?";

		this.conn = this.conexao.getConexao();

		Periodo periodo = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa.getEmpresa_id());
			this.stmt.setLong(2, organizacao.getOrganizacao_id());		
			this.stmt.setString(3, "%" + nome + "%");

			this.rsPeriodos = this.stmt.executeQuery();

			while (rsPeriodos.next()) {

				periodo = new Periodo();
				periodo.setPeriodo_id(rsPeriodos.getLong("periodo_id"));
				periodo.setNome(rsPeriodos.getString("nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsPeriodos, stmt, conn);
		return periodo;
	}

}