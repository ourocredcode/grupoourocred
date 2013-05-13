package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Logistica;
import br.com.sgo.modelo.ParceiroNegocio;
import br.com.sgo.modelo.Periodo;
import br.com.sgo.modelo.TipoLogistica;

@Component
public class LogisticaDao extends Dao<Logistica> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsLogistica;

	private final String sqlLogistica = " SELECT LOGISTICA.logistica_id, LOGISTICA.contrato_id, LOGISTICA.tipologistica_id, " +
			"									 TIPOLOGISTICA.nome as tipoLogistica_nome, " +
			"									 LOGISTICA.periodo_id, PERIODO.nome as periodo_nome , LOGISTICA.parceironegociorepresentante_id, " +
			"									 PARCEIRONEGOCIO.nome as parceiro_nome, LOGISTICA.dataassinatura" +
			"				 				FROM " +
			"									(((LOGISTICA INNER JOIN CONTRATO ON LOGISTICA.contrato_id = CONTRATO.contrato_id) INNER JOIN PERIODO ON LOGISTICA.periodo_id = PERIODO.periodo_id) " +
			"										 INNER JOIN TIPOLOGISTICA ON LOGISTICA.tipologistica_id = TIPOLOGISTICA.tipologistica_id) " +
			"										 LEFT JOIN PARCEIRONEGOCIO ON LOGISTICA.parceironegociorepresentante_id = PARCEIRONEGOCIO.parceironegocio_id  ";

	public LogisticaDao(Session session, ConnJDBC conexao) {

		super(session, Logistica.class);
		this.conexao = conexao;

	}
	
	public Collection<Logistica> buscaLogisticaByContrato(Long contrato_id) {

		String sql = sqlLogistica;

		if(contrato_id != null)
			sql += " WHERE LOGISTICA.contrato_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<Logistica> logisticas = new ArrayList<Logistica>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, contrato_id);

			this.rsLogistica = this.stmt.executeQuery();

			while (rsLogistica.next()) {
				
				Logistica logistica = new Logistica();
				Contrato contrato = new Contrato();
				ParceiroNegocio representante = new ParceiroNegocio();
				Periodo periodo = new Periodo();
				TipoLogistica tipoLogistica = new TipoLogistica();
				Calendar dataAssinatura = new GregorianCalendar();
				
				logistica.setLogistica_id(rsLogistica.getLong("logistica_id"));
				contrato.setContrato_id(rsLogistica.getLong("contrato_id"));
				representante.setParceiroNegocio_id(rsLogistica.getLong("parceironegociorepresentante_id"));
				representante.setNome(rsLogistica.getString("parceiro_nome"));
				periodo.setPeriodo_id(rsLogistica.getLong("periodo_id"));
				periodo.setNome(rsLogistica.getString("periodo_nome"));
				tipoLogistica.setTipoLogistica_id(rsLogistica.getLong("tipologistica_id"));
				tipoLogistica.setNome(rsLogistica.getString("tipoLogistica_nome"));
				dataAssinatura.setTime(rsLogistica.getDate("dataassinatura"));

				logistica.setContrato(contrato);
				logistica.setParceiroNegocioRepresentante(representante);
				logistica.setPeriodo(periodo);
				logistica.setTipoLogistica(tipoLogistica);
				logistica.setDataAssinatura(dataAssinatura);

				logisticas.add(logistica);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsLogistica, stmt, conn);
		return logisticas;
	}
	
	

}
