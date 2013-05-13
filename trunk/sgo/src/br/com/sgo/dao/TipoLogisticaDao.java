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
import br.com.sgo.modelo.TipoLogistica;

@Component
public class TipoLogisticaDao extends Dao<TipoLogistica> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsTipoLogistica;

	private final String sqlTipoLogisticas = "SELECT TIPOLOGISTICA.tipologistica_id, TIPOLOGISTICA.nome AS tipologistica_nome, "+
							" TIPOLOGISTICA.empresa_id, EMPRESA.nome AS empresa_nome, TIPOLOGISTICA.organizacao_id, ORGANIZACAO.nome AS organizacao_nome "+
							" FROM (TIPOLOGISTICA (NOLOCK) INNER JOIN EMPRESA (NOLOCK) ON TIPOLOGISTICA.empresa_id = EMPRESA.empresa_id) "+
							" INNER JOIN ORGANIZACAO (NOLOCK) ON TIPOLOGISTICA.organizacao_id = ORGANIZACAO.organizacao_id ";

	public TipoLogisticaDao(Session session, ConnJDBC conexao) {
		super(session, TipoLogistica.class);
		this.conexao = conexao;
	}

	public Collection<TipoLogistica> buscaAllTipoLogistica() {

		String sql = sqlTipoLogisticas;

		this.conn = this.conexao.getConexao();

		Collection<TipoLogistica> tiposLogistica = new ArrayList<TipoLogistica>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.rsTipoLogistica = this.stmt.executeQuery();

			while (rsTipoLogistica.next()) {

				getTiposLogistica(tiposLogistica);

			}
		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsTipoLogistica, stmt, conn);
		return tiposLogistica;
	}

	public Collection<TipoLogistica> buscaTipoLogisticaByEmOrgNome(Long empresa_id, Long organizacao_id, String nome) {

		String sql = sqlTipoLogisticas;

		if (empresa_id != null)
			sql += " WHERE TIPOLOGISTICA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND TIPOLOGISTICA.organizacao_id = ?";
		if (nome != null)
			sql += " AND TIPOLOGISTICA.nome like ?";

		this.conn = this.conexao.getConexao();

		Collection<TipoLogistica> tiposLogistica = new ArrayList<TipoLogistica>();

		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);				
			this.stmt.setLong(2, organizacao_id);				
			this.stmt.setString(3, "%" + nome + "%");
			
			this.rsTipoLogistica = this.stmt.executeQuery();

			while (rsTipoLogistica.next()) {

				getTiposLogistica(tiposLogistica);

			}
		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsTipoLogistica, stmt, conn);
		return tiposLogistica;
	}

	public TipoLogistica buscaTipoLogisticaByEmOrBaCa(Empresa empresa, Organizacao organizacao, TipoLogistica tipoLogistica) {

		String sql = sqlTipoLogisticas;

		if (empresa != null)
			sql += " WHERE TIPOLOGISTICA.empresa_id = ?";
		if (organizacao != null)
			sql += " AND TIPOLOGISTICA.organizacao_id = ?";
		if (tipoLogistica != null)
			sql += " AND TIPOLOGISTICA.tipologistica_id = ?";

		this.conn = this.conexao.getConexao();

		TipoLogistica tipoLog = null;
		
		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa.getEmpresa_id());
			this.stmt.setLong(2, organizacao.getOrganizacao_id());
			this.stmt.setLong(3, tipoLogistica.getTipoLogistica_id());

			this.rsTipoLogistica = this.stmt.executeQuery();

			while (rsTipoLogistica.next()) {

				tipoLog = new TipoLogistica();
				tipoLog.setTipoLogistica_id(rsTipoLogistica.getLong("tipologistica_id"));
				tipoLog.setNome(rsTipoLogistica.getString("nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsTipoLogistica, stmt, conn);
		return tipoLog;
	}
	
	private void getTiposLogistica(Collection<TipoLogistica> tiposLogistica) throws SQLException {

		TipoLogistica tipoLogistica = new TipoLogistica();

		Empresa e = new Empresa();
		Organizacao o = new Organizacao();

		e.setEmpresa_id(rsTipoLogistica.getLong("empresa_id"));
		e.setNome(rsTipoLogistica.getString("empresa_nome"));

		o.setOrganizacao_id(rsTipoLogistica.getLong("organizacao_id"));
		o.setNome(rsTipoLogistica.getString("organizacao_nome"));

		tipoLogistica.setTipoLogistica_id(rsTipoLogistica.getLong("tipologistica_id"));
		tipoLogistica.setNome(rsTipoLogistica.getString("tipologistica_nome"));
		
		tipoLogistica.setEmpresa(e);
		tipoLogistica.setOrganizacao(o);

		tiposLogistica.add(tipoLogistica);

	}

}