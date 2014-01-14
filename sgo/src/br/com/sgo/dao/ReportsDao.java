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
import br.com.sgo.modelo.Usuario;

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

	public ResultSet statusResultSet(Long empresa_id, Long organizacao_id) {

		String sql = "SELECT " +
							 " ETAPA.nome as etapa_nome, " +
							 " ETAPA.etapa_id , " +
							 " COUNT(ETAPA.nome) as etapaCount, " +
							 " SUM(CONTRATO.valormeta) as metaCount, " +
							 " SUM(CONTRATO.valorcontrato) as contratoCount, " +
							 " SUM(CONTRATO.valorliquido) as liquidoCount, " +
					 		 " SUM(CONTRATO.valorContratoLiquido) as contLiquidoCount " +
					 " FROM ((CONTRATO INNER JOIN ETAPA ON CONTRATO.etapa_id = ETAPA.etapa_id) " +
					 " INNER JOIN USUARIO ON CONTRATO.usuario_id = USUARIO.usuario_id) " +
					 " INNER JOIN USUARIO AS USUARIO_SUPERVISOR ON USUARIO.supervisor_usuario_id = USUARIO_SUPERVISOR.usuario_id " +
					 " WHERE CONTRATO.empresa_id = ? " +
					 " AND CONTRATO.organizacao_id = ? " +
					 " AND ( ETAPA.NOME not in ('Aprovado','Recusado','Concluído') ) GROUP BY ETAPA.nome, ETAPA.etapa_id ORDER BY metaCount DESC ";

		this.conn = this.conexao.getConexao();
	
		try {
	
			this.stmt = conn.prepareStatement(sql);
			
			int curr = 1;

			if(empresa_id != null){
				this.stmt.setLong(curr, empresa_id);
				curr++;
			}

			if(organizacao_id != null){
				this.stmt.setLong(curr, organizacao_id);
				curr++;
			}

			this.rsReports = this.stmt.executeQuery();
	
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rsReports;
	
	}
	
	public ResultSet aprovadosResultSet(Empresa empresa, Organizacao organizacao, Calendar calInicio, Calendar calFim, Integer concluidoCheck) {

		String sql = " SELECT supervisor,  " +
					  " SUM(metaCount) as metaCount  ,    " + 
					  " SUM(contratoCount) as contratoCount,   " + 
					  " SUM(liquidoCount) as liquidoCount,   " +
					  " SUM(contLiquidoCount) as contLiquidoCount  from ( SELECT  " +
						 " SUPER.apelido as supervisor, " +
						 " SUM(CONTRATO.valormeta) as metaCount, " +  
						 " SUM(CONTRATO.valorcontrato) as contratoCount, " + 
						 " SUM(CONTRATO.valorliquido) as liquidoCount, " +
						 " SUM(CONTRATO.valorContratoLiquido) as contLiquidoCount " +  
					 " FROM ((( CONTRATO " + 
					 " INNER JOIN ETAPA ON CONTRATO.etapa_id = ETAPA.etapa_id) " +  
					 " INNER JOIN USUARIO ON CONTRATO.usuario_id = USUARIO.usuario_id) " + 
					 " INNER JOIN USUARIO SUPER ON SUPER.usuario_id = USUARIO.supervisor_usuario_id) " + 
					 " INNER JOIN USUARIO AS USUARIO_SUPERVISOR ON USUARIO.supervisor_usuario_id = USUARIO_SUPERVISOR.usuario_id " +
					 " WHERE CONTRATO.empresa_id = ? " +
					 " AND CONTRATO.organizacao_id = ? " +
					 " AND ( ETAPA.NOME in ('Concluído') ) ";

		 if(concluidoCheck != null)
			 sql += " AND ( CONTRATO.dataconclusao BETWEEN ? AND ? ) GROUP BY SUPER.apelido ";
		 else
			 sql += " AND ( CONTRATO.dataconclusao BETWEEN ? AND ? ) GROUP BY SUPER.apelido " +
			 		" UNION " +
					  " SELECT   " +
						  " SUPER.apelido as supervisor, " +  
						  " SUM(CONTRATO.valormeta) as metaCount, " +    
						  " SUM(CONTRATO.valorcontrato) as contratoCount, " +   
						  " SUM(CONTRATO.valorliquido) as liquidoCount, " +  
						  " SUM(CONTRATO.valorContratoLiquido) as contLiquidoCount " +    
					  " FROM ((( CONTRATO " +   
					  " INNER JOIN ETAPA ON CONTRATO.etapa_id = ETAPA.etapa_id) " +    
					  " INNER JOIN USUARIO ON CONTRATO.usuario_id = USUARIO.usuario_id) " +   
					  " INNER JOIN USUARIO SUPER ON SUPER.usuario_id = USUARIO.supervisor_usuario_id) " +   
					  " INNER JOIN USUARIO AS USUARIO_SUPERVISOR ON USUARIO.supervisor_usuario_id = USUARIO_SUPERVISOR.usuario_id " +  
					  " WHERE CONTRATO.empresa_id = ? " +  
					  " AND CONTRATO.organizacao_id = ? " +  
					  " AND ( ETAPA.NOME in ('Aprovado') ) " +
					  " AND ( CONTRATO.datastatusfinal BETWEEN ? AND ? ) GROUP BY SUPER.apelido ";

		 sql += " ) AS G1 GROUP BY supervisor ORDER BY SUM(metaCount) DESC  "; 

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
			
			if(concluidoCheck == null) {
				
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

			}
	
			this.rsReports = this.stmt.executeQuery();
	
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rsReports;
	
	}
	
	public ResultSet producaoAtivaResultSet(Empresa empresa, Organizacao organizacao, Usuario usuario) {

		String sql = " SELECT " + 
						 " USUARIO.apelido as usuario, " +
						 " SUM(CONTRATO.valormeta) as metaCount, " +
						 " SUM(CONTRATO.valorcontrato) as contratoCount, " +
						 " SUM(CONTRATO.valorliquido) as liquidoCount, " +
						 " SUM(CONTRATO.valorContratoLiquido) as contLiquidoCount " +
					 " FROM ((( CONTRATO " +
					 " INNER JOIN ETAPA ON CONTRATO.etapa_id = ETAPA.etapa_id) " +
					 " INNER JOIN USUARIO ON CONTRATO.usuario_id = USUARIO.usuario_id) " +
					 " INNER JOIN USUARIO SUPER ON SUPER.usuario_id = USUARIO.supervisor_usuario_id) " +
					 " WHERE CONTRATO.empresa_id = ? " +
					 " AND CONTRATO.organizacao_id = ? " +
					 " AND ( ETAPA.NOME not in ('Aprovado','Recusado','Concluído','Contrato Fora Planilha') ) ";

		if(usuario != null)			 
					 sql +=" AND ( USUARIO.supervisor_usuario_id = ? OR SUPER.usuario_id = ?) ";
					 
		sql += " AND USUARIO.isactive = 1 " +
					 " GROUP BY USUARIO.apelido ORDER BY metaCount DESC ";

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
			
			if(usuario != null){
				this.stmt.setLong(curr, usuario.getUsuario_id());
				curr++;
				this.stmt.setLong(curr, usuario.getUsuario_id());
				curr++;
			}

			this.rsReports = this.stmt.executeQuery();
	
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rsReports;

	}

	public ResultSet metaDiariaResultSet(Empresa empresa, Organizacao organizacao, Calendar calInicio, Calendar calFim, Usuario usuario) {

		String sql = " SELECT " +
						   " Convert(varchar(10), CONTRATO.created , 103 ) as data, " +
						  " SUPER.apelido as supervisor, " +
						  " USUARIO.apelido as usuario, " +
						  " SUM(CONTRATO.valormeta) as metaCount, " +
						  " SUM(CONTRATO.valorcontrato) as contratoCount, " +
						  " SUM(CONTRATO.valorliquido) as liquidoCount, " +
						  " SUM(CONTRATO.valorContratoLiquido) as contLiquidoCount " +
					  " FROM ((( CONTRATO " +
					  " INNER JOIN ETAPA ON CONTRATO.etapa_id = ETAPA.etapa_id) " +
					  " INNER JOIN USUARIO ON CONTRATO.usuario_id = USUARIO.usuario_id) " +
					  " INNER JOIN USUARIO SUPER ON SUPER.usuario_id = USUARIO.supervisor_usuario_id) " +
					  " WHERE CONTRATO.empresa_id = ? " +
					  " AND CONTRATO.organizacao_id = ? " +
					  " AND ( ETAPA.NOME not in ('Recusado','Contrato Fora Planilha') ) " +
					  " AND CONTRATO.created BETWEEN ? AND ? " +
					  " AND USUARIO.isactive = 1 ";
					  
		if(usuario.getUsuario_id() != null)			 
			sql +=" AND ( USUARIO.supervisor_usuario_id = ? OR SUPER.usuario_id = ?) ";  
					
					
		sql += " GROUP BY SUPER.apelido,USUARIO.apelido, Convert(varchar(10), CONTRATO.created , 103) " +
				" ORDER BY   Convert(varchar(10), CONTRATO.created , 103),SUPER.apelido,USUARIO.apelido ";


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
			
			if(usuario.getUsuario_id() != null){
				this.stmt.setLong(curr, usuario.getUsuario_id());
				curr++;
				this.stmt.setLong(curr, usuario.getUsuario_id());
				curr++;
			}

			this.rsReports = this.stmt.executeQuery();
	
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rsReports;
	
	}

}
