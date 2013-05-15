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
import br.com.sgo.modelo.HistoricoControle;
import br.com.sgo.modelo.Usuario;

@Component
public class HistoricoControleDao extends Dao<HistoricoControle> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsHistoricoControle;

	public HistoricoControleDao(Session session, ConnJDBC conexao) {
		super(session, HistoricoControle.class);
		this.conexao = conexao;
	}
	
	public Collection<HistoricoControle> buscaHistoricoByContratoControle(Long contrato_id, Long controle_id) {

		String sql = "SELECT " +
				"			HISTORICOCONTROLE.historicocontrole_id, " +
				"			HISTORICOCONTROLE.contrato_id, HISTORICOCONTROLE.createdby, USUARIO.nome as usuario_nome, HISTORICOCONTROLE.created, " +
				"			HISTORICOCONTROLE.observacao FROM (HISTORICOCONTROLE " +
				"				INNER JOIN CONTRATO ON HISTORICOCONTROLE.contrato_id = CONTRATO.contrato_id) " +
				"				INNER JOIN USUARIO ON HISTORICOCONTROLE.createdby = USUARIO.usuario_id";

		if (contrato_id != null)
			sql += " WHERE HISTORICOCONTROLE.contrato_id = ? ";
		if (controle_id != null)
			sql += " AND HISTORICOCONTROLE.controle_id = ? ";

		this.conn = this.conexao.getConexao();
		
		Collection<HistoricoControle> historico = new ArrayList<HistoricoControle>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, contrato_id);
			this.stmt.setLong(2, controle_id);

			this.rsHistoricoControle = this.stmt.executeQuery();

			while (rsHistoricoControle.next()) {

			try {

				HistoricoControle historicoControle = new HistoricoControle();
				Usuario createdBy = new Usuario();
				Calendar created = new GregorianCalendar();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

				historicoControle.setHistoricoControle_id(rsHistoricoControle.getLong("historicocontrole_id"));
				createdBy.setUsuario_id(rsHistoricoControle.getLong("createdby"));
				createdBy.setNome(rsHistoricoControle.getString("usuario_nome"));
				created.setTime(rsHistoricoControle.getDate("created"));
				created.setTime(sdf.parse(rsHistoricoControle.getTimestamp("created").toString()));

				historicoControle.setObservacao(rsHistoricoControle.getString("observacao"));
				historicoControle.setCreatedBy(createdBy);
				historicoControle.setCreated(created);

				historico.add(historicoControle);
				
			} catch (ParseException e) {
				e.printStackTrace();
			}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rsHistoricoControle != null || stmt != null || conn != null) {
					this.conexao.closeConnection(rsHistoricoControle, stmt, conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return historico;
	}

}