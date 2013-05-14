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
import br.com.sgo.modelo.HistoricoContrato;
import br.com.sgo.modelo.Usuario;

@Component
public class HistoricoContratoDao extends Dao<HistoricoContrato> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsHistoricoContrato;

	public HistoricoContratoDao(Session session, ConnJDBC conexao) {
		super(session, HistoricoContrato.class);
		this.conexao = conexao;
	}
	
	public Collection<HistoricoContrato> buscaHistoricoByContrato(Long contrato_id) {

		String sql = "SELECT " +
				"			HISTORICOCONTRATO.historicocontrato_id, " +
				"			HISTORICOCONTRATO.contrato_id, HISTORICOCONTRATO.createdby, USUARIO.nome as usuario_nome, HISTORICOCONTRATO.created, " +
				"			HISTORICOCONTRATO.observacao FROM (HISTORICOCONTRATO " +
				"				INNER JOIN CONTRATO ON HISTORICOCONTRATO.contrato_id = CONTRATO.contrato_id) " +
				"				INNER JOIN USUARIO ON HISTORICOCONTRATO.createdby = USUARIO.usuario_id";

		if (contrato_id != null)
			sql += " WHERE HISTORICOCONTRATO.contrato_id = ? ";

		this.conn = this.conexao.getConexao();
		
		Collection<HistoricoContrato> historico = new ArrayList<HistoricoContrato>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, contrato_id);

			this.rsHistoricoContrato = this.stmt.executeQuery();

			while (rsHistoricoContrato.next()) {

			try {

				HistoricoContrato historicoContrato = new HistoricoContrato();
				Usuario createdBy = new Usuario();
				Calendar created = new GregorianCalendar();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

				historicoContrato.setHistoricoContrato_id(rsHistoricoContrato.getLong("historicocontrato_id"));
				createdBy.setUsuario_id(rsHistoricoContrato.getLong("createdby"));
				createdBy.setNome(rsHistoricoContrato.getString("usuario_nome"));
				created.setTime(rsHistoricoContrato.getDate("created"));
				created.setTime(sdf.parse(rsHistoricoContrato.getTimestamp("created").toString()));

				historicoContrato.setObservacao(rsHistoricoContrato.getString("observacao"));
				historicoContrato.setCreatedBy(createdBy);
				historicoContrato.setCreated(created);

				historico.add(historicoContrato);
				
			} catch (ParseException e) {
				e.printStackTrace();
			}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rsHistoricoContrato != null || stmt != null || conn != null) {
					this.conexao.closeConnection(rsHistoricoContrato, stmt, conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return historico;
	}

}