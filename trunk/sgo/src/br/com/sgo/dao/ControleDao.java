package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Agente;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Controle;
import br.com.sgo.modelo.Etapa;
import br.com.sgo.modelo.Usuario;

@Component
public class ControleDao extends Dao<Controle> {
	
	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsControle;
	
	private String sqlControle = "  SELECT CONTROLE.controle_id, CONTROLE.tipocontrole_id, TIPOCONTROLE.nome as tipocontrole_nome,  " +
						" 				CONTROLE.contrato_id, CONTROLE.createdby , USUARIO.nome as usuario_nome, CONTROLE.dataatuacao,  " +
						" 				CONTROLE.datachegada, CONTROLE.dataprevisao, CONTROLE.datavencimento, CONTROLE.dataprimeiraatuacao, " +  
						" 				CONTROLE.dataproximaatuacao, E1.etapa_id as procedimento_id , E1.nome as procedimento_nome , E2.etapa_id as proximoprocedimento_id , E2.nome as proximoprocedimento_nome,  " + 
						" 				U1.usuario_id, U1.nome, U2.usuario_id as proximoatuante_id , U2.nome as proximoatuante_nome, AGENTE.agente_id, AGENTE.nome as agente_nome" +
						"			 FROM (((((((( CONTROLE (NOLOCK) " + 
						" 					INNER JOIN TIPOCONTROLE (NOLOCK) ON CONTROLE.tipocontrole_id = TIPOCONTROLE.tipocontrole_id) " +  
						" 					INNER JOIN CONTRATO (NOLOCK) ON CONTROLE.contrato_id = CONTRATO.contrato_id ) " +  
						" 					INNER JOIN USUARIO (NOLOCK) ON CONTROLE.createdby = USUARIO.usuario_id ) " +
						" 			LEFT JOIN ETAPA AS E1 (NOLOCK) ON CONTROLE.etapa_id = E1.etapa_id ) " +
						" 			LEFT JOIN ETAPA AS E2 (NOLOCK) ON CONTROLE.etapaproximo_id = E2.etapa_id ) " +
						" 			LEFT JOIN USUARIO AS U1 (NOLOCK) ON CONTROLE.createdby = U1.usuario_id ) " +
						" 			LEFT JOIN USUARIO AS U2 (NOLOCK) ON CONTROLE.proximoatuante_id = U2.usuario_id )" +
						"			LEFT JOIN AGENTE (NOLOCK) ON CONTROLE.agente_id = AGENTE.agente_id ) ";

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
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.FFF");
				Etapa etapa = new Etapa();
				Etapa etapaProximo = new Etapa();
				Usuario proximoAtuante = new Usuario();
				Agente agente = new Agente();

				usuario.setUsuario_id(rsControle.getLong("createdby"));
				usuario.setNome(rsControle.getString("usuario_nome"));
				
				proximoAtuante.setUsuario_id(rsControle.getLong("proximoatuante_id"));
				proximoAtuante.setNome(rsControle.getString("proximoatuante_nome"));
				
				etapa.setEtapa_id(rsControle.getLong("procedimento_id"));
				etapa.setNome(rsControle.getString("procedimento_nome"));
				
				etapaProximo.setEtapa_id(rsControle.getLong("proximoprocedimento_id"));
				etapaProximo.setNome(rsControle.getString("proximoprocedimento_nome"));
				
				agente.setAgente_id(rsControle.getLong("agente_id"));
				agente.setNome(rsControle.getString("agente_nome"));

				controle.setControle_id(rsControle.getLong("controle_id"));
				controle.setContrato(contrato);
				controle.setCreatedBy(usuario);
				controle.setProximoAtuante(proximoAtuante);
				controle.setEtapa(etapa);
				controle.setEtapaProximo(etapaProximo);
				controle.setAgente(agente);

				try {
					
					if(rsControle.getDate("dataatuacao") != null){
						calendarAux.setTime(sdf.parse(rsControle.getTimestamp("dataatuacao").toString()));
						
						controle.setDataAtuacao(calendarAux);
					}
					
					if(rsControle.getDate("dataChegada") != null){
						calendarAux.setTime(sdf.parse(rsControle.getTimestamp("dataChegada").toString()));
						controle.setDataChegada(calendarAux);
					}
					
					if(rsControle.getDate("dataPrevisao") != null){
						calendarAux.setTime(sdf.parse(rsControle.getTimestamp("dataPrevisao").toString()));
						controle.setDataPrevisao(calendarAux);
					}
					
					if(rsControle.getDate("dataPrimeiraAtuacao") != null){
						calendarAux.setTime(sdf.parse(rsControle.getTimestamp("dataPrimeiraAtuacao").toString()));
						controle.setDataPrimeiraAtuacao(calendarAux);
					}
					
					if(rsControle.getDate("dataProximaAtuacao") != null){
						calendarAux.setTime(sdf.parse(rsControle.getTimestamp("dataProximaAtuacao").toString()));
						controle.setDataProximaAtuacao(calendarAux);
					}
					
					if(rsControle.getDate("dataVencimento") != null){
						calendarAux.setTime(sdf.parse(rsControle.getTimestamp("dataVencimento").toString()));
						controle.setDataVencimento(calendarAux);
					}
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
				

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsControle, stmt, conn);
		return controle;

	}

}