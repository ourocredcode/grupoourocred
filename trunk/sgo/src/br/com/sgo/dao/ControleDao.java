package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Controle;
import br.com.sgo.modelo.Usuario;

@Component
public class ControleDao extends Dao<Controle> {
	
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsControle;
	
	private String sqlControle = " SELECT CONTROLE.controle_id, CONTROLE.tipocontrole_id, TIPOCONTROLE.nome as tipocontrole_nome, " +
			"							  CONTROLE.contrato_id, CONTROLE.usuario_id, USUARIO.nome as usuario_nome, CONTROLE.dataatuacao, " +
			"							  CONTROLE.datachegada, CONTROLE.dataprevisao, CONTROLE.datavencimento, CONTROLE.dataprimeiraatuacao, " +
			"							  CONTROLE.dataproximaatuacao FROM ((CONTROLE (NOLOCK)" +
			"										INNER JOIN TIPOCONTROLE (NOLOCK) ON CONTROLE.tipocontrole_id = TIPOCONTROLE.tipocontrole_id) " +
			"										INNER JOIN CONTRATO (NOLOCK) ON CONTROLE.contrato_id = CONTRATO.contrato_id) " +
			"										INNER JOIN USUARIO (NOLOCK) ON CONTROLE.usuario_id = USUARIO.usuario_id ";

	public ControleDao(Session session, ConnJDBC conexao) {

		super(session, Controle.class);
		this.conexao = conexao;

	}
	
	public Controle buscaControleByContratoTipoControle(Long contrato_id, Long tipocontrole_id) {

		String sql = sqlControle;

		if (contrato_id != null)
			sql += " WHERE CONTRATO.contrato_id = ? ";
		
		if (tipocontrole_id != null)
			sql += " AND TIPOCONTROLE.tipocontrole_id = ?";

		this.conn = this.conexao.getConexao();
		
		Controle controle = null;

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1,contrato_id);
			this.stmt.setLong(2,tipocontrole_id);

			this.rsControle = this.stmt.executeQuery();

			while (rsControle.next()) {

				controle = new Controle();

				Usuario usuario = new Usuario();
				Contrato contrato = new Contrato();
				Calendar calendarAux = new GregorianCalendar();

				usuario.setUsuario_id(rsControle.getLong("usuario_id"));
				usuario.setNome(rsControle.getString("usuario_nome"));

				controle.setControle_id(rsControle.getLong("controle_id"));
				controle.setContrato(contrato);
				controle.setUsuario(usuario);

				if(rsControle.getDate("dataatuacao") != null){
					calendarAux.setTime(rsControle.getDate("dataatuacao"));
					controle.setDataAtuacao(calendarAux);
				}
				
				if(rsControle.getDate("dataChegada") != null){
					calendarAux.setTime(rsControle.getDate("dataChegada"));
					controle.setDataChegada(calendarAux);
				}
				
				if(rsControle.getDate("dataPrevisao") != null){
					calendarAux.setTime(rsControle.getDate("dataPrevisao"));
					controle.setDataPrevisao(calendarAux);
				}
				
				if(rsControle.getDate("dataPrimeiraAtuacao") != null){
					calendarAux.setTime(rsControle.getDate("dataPrimeiraAtuacao"));
					controle.setDataPrimeiraAtuacao(calendarAux);
				}
				
				if(rsControle.getDate("dataProximaAtuacao") != null){
					calendarAux.setTime(rsControle.getDate("dataProximaAtuacao"));
					controle.setDataProximaAtuacao(calendarAux);
				}
				
				if(rsControle.getDate("dataVencimento") != null){
					calendarAux.setTime(rsControle.getDate("dataVencimento"));
					controle.setDataVencimento(calendarAux);
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsControle, stmt, conn);
		return controle;

	}

}