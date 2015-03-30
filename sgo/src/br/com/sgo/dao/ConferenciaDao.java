package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Conferencia;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.ProcedimentoConferencia;
import br.com.sgo.modelo.TipoProcedimento;
import br.com.sgo.modelo.Usuario;

@Component
public class ConferenciaDao extends Dao<Conferencia> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsConferencia;

	private final String sqlConferencia = " SELECT CONFERENCIA.conferencia_id, CONFERENCIA.empresa_id, EMPRESA.nome AS empresa_nome, "+
					" CONFERENCIA.organizacao_id, ORGANIZACAO.nome AS organizacao_nome, CONFERENCIA.observacao,CONFERENCIA.isvalido, CONFERENCIA.isactive, "+
					" CONFERENCIA.contrato_id, CONFERENCIA.procedimentoconferencia_id, CONFERENCIA.created, CONFERENCIA.updated, " +
					" PROCEDIMENTOCONFERENCIA.nome as procedimentoconferencia_nome, " +
					" CONFERENCIA.tipoprocedimento_id, TIPOPROCEDIMENTO.nome as tipoprocedimento_nome,USUARIO.usuario_id, " +
				    " USUARIO.nome as usuario_nome, UPDATED.nome as usuario_nome_updated, UPDATED.usuario_id as usuario_id_updated "+
					" FROM ((((ORGANIZACAO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) "+
					" INNER JOIN CONFERENCIA (NOLOCK) ON EMPRESA.empresa_id = CONFERENCIA.empresa_id) ON ORGANIZACAO.organizacao_id = CONFERENCIA.organizacao_id) "+
					" INNER JOIN PROCEDIMENTOCONFERENCIA (NOLOCK) ON CONFERENCIA.procedimentoconferencia_id = PROCEDIMENTOCONFERENCIA.procedimentoconferencia_id) " +
					" INNER JOIN TIPOPROCEDIMENTO (NOLOCK) ON CONFERENCIA.tipoprocedimento_id = TIPOPROCEDIMENTO.tipoprocedimento_id) "+
					" INNER JOIN CONTRATO (NOLOCK) ON CONFERENCIA.contrato_id = CONTRATO.contrato_id)" +
					" INNER JOIN USUARIO (NOLOCK) ON USUARIO.usuario_id = CONFERENCIA.createdby " + 
					" LEFT JOIN USUARIO AS UPDATED (NOLOCK) ON UPDATED.usuario_id = CONFERENCIA.updatedby ";

	public ConferenciaDao(Session session, ConnJDBC conexao) {

		super(session, Conferencia.class);
		this.conexao = conexao;

	}

	public Collection<Conferencia> buscaAllConferencia(Long empresa_id, Long organizacao_id) {

		String sql = sqlConferencia;

		if (empresa_id != null)
			sql += " WHERE CONFERENCIA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND CONFERENCIA.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<Conferencia> conferencias = new ArrayList<Conferencia>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.rsConferencia = this.stmt.executeQuery();

			while (rsConferencia.next()) {

				getConferencias(conferencias);

			}
		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsConferencia, stmt, conn);
		return conferencias;

	}

	public Collection<Conferencia> buscaConferenciaByEmOrTipoProcedimentoContrato(Long empresa_id, Long organizacao_id, Long tipoProcedimento_id, Long contrato_id) {

		String sql = sqlConferencia;

		if (empresa_id != null)
			sql += " WHERE CONFERENCIA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND CONFERENCIA.organizacao_id = ?";
		if (tipoProcedimento_id != null)
			sql += " AND CONFERENCIA.tipoprocedimento_id = ?";
		if (contrato_id != null)
			sql += " AND CONFERENCIA.contrato_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<Conferencia> conferencias = new ArrayList<Conferencia>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, tipoProcedimento_id);
			this.stmt.setLong(4, contrato_id);

			this.rsConferencia = this.stmt.executeQuery();

			while (rsConferencia.next()) {

				getConferencias(conferencias);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsConferencia, stmt, conn);
		return conferencias;
	}

	private void getConferencias(Collection<Conferencia> conferencias)	throws SQLException {

		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();
		Conferencia conferencia = new Conferencia();
		Contrato contrato = new Contrato();
		ProcedimentoConferencia procedimentoConferencia = new ProcedimentoConferencia();
		TipoProcedimento tipoProcedimento = new TipoProcedimento();
		Usuario usuario = new Usuario();
		Usuario updatedBy = new Usuario();
		Calendar created = new GregorianCalendar();
		Calendar udpated = new GregorianCalendar();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

		empresa.setEmpresa_id(rsConferencia.getLong("empresa_id"));
		empresa.setNome(rsConferencia.getString("empresa_nome"));

		organizacao.setOrganizacao_id(rsConferencia.getLong("organizacao_id"));
		organizacao.setNome(rsConferencia.getString("organizacao_nome"));

		usuario.setUsuario_id(rsConferencia.getLong("usuario_id"));
		usuario.setNome(rsConferencia.getString("usuario_nome"));

		updatedBy.setUsuario_id(rsConferencia.getLong("usuario_id_updated"));
		updatedBy.setNome(rsConferencia.getString("usuario_nome_updated"));

		contrato.setContrato_id(rsConferencia.getLong("contrato_id"));

		procedimentoConferencia.setProcedimentoConferencia_id(rsConferencia.getLong("procedimentoconferencia_id"));
		procedimentoConferencia.setNome(rsConferencia.getString("procedimentoconferencia_nome"));

		tipoProcedimento.setTipoProcedimento_id(rsConferencia.getLong("tipoprocedimento_id"));
		tipoProcedimento.setNome(rsConferencia.getString("tipoprocedimento_nome"));

		conferencia.setConferencia_id(rsConferencia.getLong("conferencia_id"));
		conferencia.setObservacao(rsConferencia.getString("observacao"));
		conferencia.setIsValido(rsConferencia.getBoolean("isvalido"));
		conferencia.setIsActive(rsConferencia.getBoolean("isactive"));

		try {

			if(rsConferencia.getDate("created") != null) {
				created.setTime(format.parse(rsConferencia.getTimestamp("created").toString()));
				conferencia.setCreated(created);
			}	

			if(rsConferencia.getDate("updated") != null) {
				udpated.setTime(format.parse(rsConferencia.getTimestamp("updated").toString()));
				conferencia.setUpdated(udpated);
			} else {
				conferencia.setUpdated(GregorianCalendar.getInstance());
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		conferencia.setEmpresa(empresa);
		conferencia.setOrganizacao(organizacao);
		conferencia.setContrato(contrato);
		conferencia.setProcedimentoConferencia(procedimentoConferencia);
		conferencia.setTipoProcedimento(tipoProcedimento);
		conferencia.setCreatedBy(usuario);
		conferencia.setUpdatedBy(updatedBy);

		conferencias.add(conferencia);

	}

}