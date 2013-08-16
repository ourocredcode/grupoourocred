package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.CustomDateUtil;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;

@Component
public class ReportsDao extends Dao<Contrato> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsReports;

	public ReportsDao(Session session, ConnJDBC conexao) {

		super(session, Contrato.class);
		this.conexao = conexao;

	}

	public ResultSet statusResultSet() {

		String sql = "SELECT " +
							 " ETAPA.nome as etapa_nome, " +
							 " ETAPA.etapa_id , " +
							 " COUNT(ETAPA.nome) as etapaCount, " +
							 " SUM(CONTRATO.valormeta) as metaCount, " +
							 " SUM(CONTRATO.valorcontrato) as contratoCount, " +
					 		 " SUM(CONTRATO.valorContratoLiquido) as contLiquidoCount " +
					 " FROM ((CONTRATO INNER JOIN ETAPA ON CONTRATO.etapa_id = ETAPA.etapa_id) " +
					 " INNER JOIN USUARIO ON CONTRATO.usuario_id = USUARIO.usuario_id) " +
					 " INNER JOIN USUARIO AS USUARIO_SUPERVISOR ON USUARIO.supervisor_usuario_id = USUARIO_SUPERVISOR.usuario_id " +
					 " WHERE CONTRATO.empresa_id = 2 " +
					 " AND CONTRATO.organizacao_id = 2 " +
					 " AND ( ETAPA.NOME not in ('Aprovado','Recusado','Concluído') ) GROUP BY ETAPA.nome, ETAPA.etapa_id ORDER BY metaCount DESC ";

		this.conn = this.conexao.getConexao();
	
		try {
	
			this.stmt = conn.prepareStatement(sql);
	
			this.rsReports = this.stmt.executeQuery();
	
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rsReports;
	
	}
	
	public ResultSet aprovadosResultSet(Empresa empresa, Organizacao organizacao, Calendar calInicio, Calendar calFim) {

		String sql = " SELECT  " +
						 " SUPER.apelido as supervisor, " +
						 " SUM(CONTRATO.valormeta) as metaCount, " +  
						 " SUM(CONTRATO.valorcontrato) as contratoCount, " + 
						 " SUM(CONTRATO.valorContratoLiquido) as contLiquidoCount " +  
					 " FROM ((( CONTRATO " + 
					 " INNER JOIN ETAPA ON CONTRATO.etapa_id = ETAPA.etapa_id) " +  
					 " INNER JOIN USUARIO ON CONTRATO.usuario_id = USUARIO.usuario_id) " + 
					 " INNER JOIN USUARIO SUPER ON SUPER.usuario_id = USUARIO.supervisor_usuario_id) " + 
					 " INNER JOIN USUARIO AS USUARIO_SUPERVISOR ON USUARIO.supervisor_usuario_id = USUARIO_SUPERVISOR.usuario_id " +
					 " WHERE CONTRATO.empresa_id = ? " +
					 " AND CONTRATO.organizacao_id = ? " +
					 " AND ( ETAPA.NOME in ('Aprovado','Concluído') ) " +
					 " AND ( CONTRATO.datastatusfinal BETWEEN ? AND ? ) " +
					 " GROUP BY SUPER.apelido ORDER BY metaCount DESC  "; 

		this.conn = this.conexao.getConexao();
	
		try {
	
			this.stmt = conn.prepareStatement(sql);
			
			int curr = 1;

			if(empresa != null){
				this.stmt.setLong(curr, empresa.getEmpresa_id());
				curr++;
			}

			if(organizacao != null){
				this.stmt.setLong(curr, organizacao.getOrganizacao_id());
				curr++;
			}
			
			if(calInicio != null){

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarInicio(calInicio).getTimeInMillis()));
				curr++;

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarFim(calFim).getTimeInMillis()));
				curr++;

			}
	
			this.rsReports = this.stmt.executeQuery();
	
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rsReports;
	
	}

}
