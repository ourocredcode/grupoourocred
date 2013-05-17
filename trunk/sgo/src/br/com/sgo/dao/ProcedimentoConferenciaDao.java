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
import br.com.sgo.modelo.ProcedimentoConferencia;
import br.com.sgo.modelo.TipoProcedimento;

@Component
public class ProcedimentoConferenciaDao extends Dao<ProcedimentoConferencia> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsProcedimentoConferencia;

	private final String sqlProcedimentoConferencia = " SELECT PROCEDIMENTOCONFERENCIA.procedimentoconferencia_id, " +
							" PROCEDIMENTOCONFERENCIA.nome AS procedimentoconferencia_nome, PROCEDIMENTOCONFERENCIA.chave AS procedimentoconferencia_chave, "+
							" PROCEDIMENTOCONFERENCIA.empresa_id, EMPRESA.nome AS empresa_nome, "+
							" PROCEDIMENTOCONFERENCIA.organizacao_id, ORGANIZACAO.nome AS organizacao_nome, "+
							" PROCEDIMENTOCONFERENCIA.tipoprocedimento_id, TIPOPROCEDIMENTO.nome AS tipoprocedimento_nome, PROCEDIMENTOCONFERENCIA.isactive "+
							" FROM (ORGANIZACAO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) "+
							" INNER JOIN PROCEDIMENTOCONFERENCIA (NOLOCK) ON EMPRESA.empresa_id = PROCEDIMENTOCONFERENCIA.empresa_id) ON ORGANIZACAO.organizacao_id = PROCEDIMENTOCONFERENCIA.organizacao_id) "+
							" INNER JOIN TIPOPROCEDIMENTO (NOLOCK) ON PROCEDIMENTOCONFERENCIA.tipoprocedimento_id = TIPOPROCEDIMENTO.tipoprocedimento_id ";

	public ProcedimentoConferenciaDao(Session session, ConnJDBC conexao) {

		super(session, ProcedimentoConferencia.class);
		this.conexao = conexao;

	}

	public Collection<ProcedimentoConferencia> buscaAllProcedimentoConferencia(Long empresa_id, Long organizacao_id) {

		String sql = sqlProcedimentoConferencia;

		if (empresa_id != null)
			sql += " WHERE PROCEDIMENTOCONFERENCIA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND PROCEDIMENTOCONFERENCIA.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<ProcedimentoConferencia> procedimentosConferencia = new ArrayList<ProcedimentoConferencia>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsProcedimentoConferencia = this.stmt.executeQuery();

			while (rsProcedimentoConferencia.next()) {

				getProcedimentoConferencias(procedimentosConferencia);

			}
		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsProcedimentoConferencia, stmt, conn);
		return procedimentosConferencia;

	}
	
	public Collection<ProcedimentoConferencia> buscaProcedimentoConferenciaTipoProcedimentoNome(Long empresa_id, Long organizacao_id, Long tipoProcedimento_id, String nome) {

		String sql = sqlProcedimentoConferencia;

		if (empresa_id != null)
			sql += " WHERE PROCEDIMENTOCONFERENCIA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND PROCEDIMENTOCONFERENCIA.organizacao_id = ?";
		if (tipoProcedimento_id != null)
			sql += " AND PROCEDIMENTOCONFERENCIA.tipoprocedimento_id = ?";
		if (nome != null)
			sql += " AND (PROCEDIMENTOCONFERENCIA.nome like ?)";

		this.conn = this.conexao.getConexao();

		Collection<ProcedimentoConferencia> procedimentosConferencia = new ArrayList<ProcedimentoConferencia>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, tipoProcedimento_id);
			this.stmt.setString(4, "%" + nome + "%");

			this.rsProcedimentoConferencia = this.stmt.executeQuery();

			while (rsProcedimentoConferencia.next()) {

				getProcedimentoConferencias(procedimentosConferencia);

			}
		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsProcedimentoConferencia, stmt, conn);
		return procedimentosConferencia;

	}
	
	public Collection<ProcedimentoConferencia> buscaProcedimentoConferenciaTipoProcedimento(Long empresa_id, Long organizacao_id, Long tipoProcedimento_id) {

		String sql = sqlProcedimentoConferencia;

		if (empresa_id != null)
			sql += " WHERE PROCEDIMENTOCONFERENCIA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND PROCEDIMENTOCONFERENCIA.organizacao_id = ?";
		if (tipoProcedimento_id != null)
			sql += " AND PROCEDIMENTOCONFERENCIA.tipoprocedimento_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<ProcedimentoConferencia> procedimentosConferencia = new ArrayList<ProcedimentoConferencia>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, tipoProcedimento_id);

			this.rsProcedimentoConferencia = this.stmt.executeQuery();

			while (rsProcedimentoConferencia.next()) {

				getProcedimentoConferencias(procedimentosConferencia);

			}
		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsProcedimentoConferencia, stmt, conn);
		return procedimentosConferencia;

	}

	public ProcedimentoConferencia buscaProcedimentoConferenciaByEmOrTipoProcedimentoContrato(Long empresa_id, Long organizacao_id, Long tipoProcedimento_id, String nome) {

		String sql = sqlProcedimentoConferencia;

		if (empresa_id != null)
			sql += " WHERE PROCEDIMENTOCONFERENCIA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND PROCEDIMENTOCONFERENCIA.organizacao_id = ?";
		if (tipoProcedimento_id != null)
			sql += " AND PROCEDIMENTOCONFERENCIA.tipoprocedimento_id = ?";
		if (nome != null)
			sql += " AND (PROCEDIMENTOCONFERENCIA.nome like ?)";

		this.conn = this.conexao.getConexao();

		ProcedimentoConferencia procedimentoConferencia = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, tipoProcedimento_id);
			this.stmt.setString(4, "%" + nome + "%");

			this.rsProcedimentoConferencia = this.stmt.executeQuery();

			while (rsProcedimentoConferencia.next()) {

				procedimentoConferencia = new ProcedimentoConferencia();
				procedimentoConferencia.setProcedimentoConferencia_id(rsProcedimentoConferencia.getLong("procedimentoconferencia_id"));
				procedimentoConferencia.setNome(rsProcedimentoConferencia.getString("procedimentoconferencia_nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsProcedimentoConferencia, stmt, conn);
		return procedimentoConferencia;
	}

	private void getProcedimentoConferencias(Collection<ProcedimentoConferencia> procedimentosConferencia)	throws SQLException {

		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();
		TipoProcedimento tipoProcedimento = new TipoProcedimento();
		ProcedimentoConferencia procedimentoConferencia = new ProcedimentoConferencia();
		
		empresa.setEmpresa_id(rsProcedimentoConferencia.getLong("empresa_id"));
		empresa.setNome(rsProcedimentoConferencia.getString("empresa_nome"));

		organizacao.setOrganizacao_id(rsProcedimentoConferencia.getLong("organizacao_id"));
		organizacao.setNome(rsProcedimentoConferencia.getString("organizacao_nome"));

		tipoProcedimento.setTipoProcedimento_id(rsProcedimentoConferencia.getLong("tipoprocedimento_id"));
		tipoProcedimento.setNome(rsProcedimentoConferencia.getString("tipoprocedimento_nome"));

		procedimentoConferencia.setProcedimentoConferencia_id(rsProcedimentoConferencia.getLong("procedimentoconferencia_id"));
		procedimentoConferencia.setNome(rsProcedimentoConferencia.getString("procedimentoconferencia_nome"));
		procedimentoConferencia.setChave(rsProcedimentoConferencia.getString("procedimentoconferencia_chave"));
		procedimentoConferencia.setIsActive(rsProcedimentoConferencia.getBoolean("isactive"));

		procedimentoConferencia.setEmpresa(empresa);
		procedimentoConferencia.setOrganizacao(organizacao);
		procedimentoConferencia.setTipoProcedimento(tipoProcedimento);

		procedimentosConferencia.add(procedimentoConferencia);

	}

}