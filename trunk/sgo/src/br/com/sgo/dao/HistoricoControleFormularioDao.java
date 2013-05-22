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
import br.com.sgo.modelo.HistoricoControleFormulario;
import br.com.sgo.modelo.Usuario;

@Component
public class HistoricoControleFormularioDao extends Dao<HistoricoControleFormulario> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsHistoricoControleFormulario;

	public HistoricoControleFormularioDao(Session session, ConnJDBC conexao) {
		super(session, HistoricoControleFormulario.class);
		this.conexao = conexao;
	}
	
	public Collection<HistoricoControleFormulario> buscaHistoricoByFormularioControle(Long formulario_id, Long controle_id) {

		String sql = "SELECT " +
		"			HISTORICOCONTROLEFORMULARIO.historicocontroleformulario_id, " +
		"			HISTORICOCONTROLEFORMULARIO.formulario_id, HISTORICOCONTROLEFORMULARIO.createdby, USUARIO.nome as usuario_nome, " +
		"			HISTORICOCONTROLEFORMULARIO.created, " +
		"			HISTORICOCONTROLEFORMULARIO.observacao FROM (HISTORICOCONTROLEFORMULARIO " +
		"				INNER JOIN FORMULARIO ON HISTORICOCONTROLEFORMULARIO.formulario_id = FORMULARIO.formulario_id) " +
		"				INNER JOIN USUARIO ON HISTORICOCONTROLEFORMULARIO.createdby = USUARIO.usuario_id ";

		if (formulario_id != null)
			sql += " WHERE HISTORICOCONTROLEFORMULARIO.formulario_id = ? ";
		if (controle_id != null)
			sql += " AND HISTORICOCONTROLEFORMULARIO.controleformulario_id = ? ";

		this.conn = this.conexao.getConexao();
		
		Collection<HistoricoControleFormulario> historicos = new ArrayList<HistoricoControleFormulario>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, formulario_id);
			this.stmt.setLong(2, controle_id);

			this.rsHistoricoControleFormulario = this.stmt.executeQuery();

			while (rsHistoricoControleFormulario.next()) {

			try {

				HistoricoControleFormulario historicoControle = new HistoricoControleFormulario();
				Usuario createdBy = new Usuario();
				Calendar created = new GregorianCalendar();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

				historicoControle.setHistoricoControleFormulario_id(rsHistoricoControleFormulario.getLong("historicocontroleformulario_id"));
				createdBy.setUsuario_id(rsHistoricoControleFormulario.getLong("createdby"));
				createdBy.setNome(rsHistoricoControleFormulario.getString("usuario_nome"));
				created.setTime(rsHistoricoControleFormulario.getDate("created"));
				created.setTime(sdf.parse(rsHistoricoControleFormulario.getTimestamp("created").toString()));

				historicoControle.setObservacao(rsHistoricoControleFormulario.getString("observacao"));
				historicoControle.setCreatedBy(createdBy);
				historicoControle.setCreated(created);

				historicos.add(historicoControle);
				
			} catch (ParseException e) {
				e.printStackTrace();
			}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rsHistoricoControleFormulario != null || stmt != null || conn != null) {
					this.conexao.closeConnection(rsHistoricoControleFormulario, stmt, conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return historicos;
	}

}