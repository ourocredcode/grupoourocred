package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Etapa;
import br.com.sgo.modelo.HisconBeneficio;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.ParceiroBeneficio;
import br.com.sgo.modelo.ParceiroNegocio;
import br.com.sgo.modelo.Perfil;
import br.com.sgo.modelo.Usuario;
import br.com.sgo.modelo.Workflow;

@Component
public class HisconBeneficioDao extends Dao<HisconBeneficio> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsHisconBeneficio;

	private String sqlHisconsBeneficio = "SELECT HISCONBENEFICIO.hisconbeneficio_id, HISCONBENEFICIO.empresa_id, EMPRESA.nome as empresa_nome " + 
			 ", HISCONBENEFICIO.organizacao_id, ORGANIZACAO.nome as organizacao_nome, HISCONBENEFICIO.parceirobeneficio_id , PARCEIROBENEFICIO.numerobeneficio " + 
			 ", HISCONBENEFICIO.usuario_id, USUARIO.nome as usuario_nome,PERFIL.perfil_id, PERFIL.nome as perfil_nome " +
			 ", HISCONBENEFICIO.workflow_id, PARCEIRONEGOCIO.parceironegocio_id, PARCEIRONEGOCIO.cpf " +
			 ", PARCEIRONEGOCIO.nome as parceironegocio_nome, ETAPA.etapa_id, ETAPA.nome AS etapa_nome, HISCONBENEFICIO.created, HISCONBENEFICIO.updated " + 
			 ", HISCONBENEFICIO.dataadm, HISCONBENEFICIO.dataenvio, HISCONBENEFICIO.caminhoarquivo " +
			 ", HISCONBENEFICIO.isworkflow, HISCONBENEFICIO.isenviado, HISCONBENEFICIO.isimportado, HISCONBENEFICIO.ispadrao, WORKFLOW.workflow_id " +
			 " FROM (PERFIL (NOLOCK) INNER JOIN ((((((PARCEIRONEGOCIO (NOLOCK) INNER JOIN (HISCONBENEFICIO (NOLOCK) " +
			 " INNER JOIN PARCEIROBENEFICIO (NOLOCK) ON HISCONBENEFICIO.parceirobeneficio_id = PARCEIROBENEFICIO.parceirobeneficio_id) ON PARCEIRONEGOCIO.parceironegocio_id = PARCEIROBENEFICIO.parceironegocio_id) " +
			 " INNER JOIN EMPRESA (NOLOCK) ON HISCONBENEFICIO.empresa_id = EMPRESA.empresa_id) " +
			 " INNER JOIN WORKFLOW (NOLOCK) ON WORKFLOW.workflow_id = HISCONBENEFICIO.workflow_id) " +
			 " INNER JOIN ORGANIZACAO (NOLOCK) ON HISCONBENEFICIO.organizacao_id = ORGANIZACAO.organizacao_id) " +
			 " INNER JOIN USUARIO (NOLOCK) ON HISCONBENEFICIO.usuario_id = USUARIO.usuario_id) " +
			 " INNER JOIN USUARIOPERFIL (NOLOCK) ON USUARIO.usuario_id = USUARIOPERFIL.usuario_id) ON PERFIL.perfil_id = USUARIOPERFIL.perfil_id) " +
			 " INNER JOIN ETAPA (NOLOCK) ON (HISCONBENEFICIO.etapa_id = ETAPA.etapa_id)";

	private String sqlHisconsExibe = " SELECT HISCONBENEFICIO.hisconbeneficio_id, HISCONBENEFICIO.empresa_id, EMPRESA.nome as empresa_nome   " + 
			", HISCONBENEFICIO.organizacao_id, ORGANIZACAO.nome as organizacao_nome, HISCONBENEFICIO.parceirobeneficio_id , PARCEIROBENEFICIO.numerobeneficio " +     
			", HISCONBENEFICIO.usuario_id, USUARIO.nome as usuario_nome,PERFIL.perfil_id, PERFIL.nome as perfil_nome " +    
			", HISCONBENEFICIO.workflow_id, PARCEIRONEGOCIO.parceironegocio_id, PARCEIRONEGOCIO.cpf " +    
			", PARCEIRONEGOCIO.nome as parceironegocio_nome, ETAPA.etapa_id, ETAPA.nome AS etapa_nome, HISCONBENEFICIO.created, HISCONBENEFICIO.updated " +     
			", HISCONBENEFICIO.dataadm, HISCONBENEFICIO.dataenvio, HISCONBENEFICIO.caminhoarquivo " +    
			", HISCONBENEFICIO.isworkflow, HISCONBENEFICIO.isenviado, HISCONBENEFICIO.isimportado, HISCONBENEFICIO.ispadrao" +
			", POSICAO.etapa_id as posicaoEtapa_id , POSICAO.nome as posicao_nome   " +    
			"FROM (((PERFIL (NOLOCK) INNER JOIN (((((HISCONBENEFICIO (NOLOCK) " +   
			"INNER JOIN EMPRESA (NOLOCK) ON HISCONBENEFICIO.empresa_id = EMPRESA.empresa_id) " +    
			"INNER JOIN ORGANIZACAO (NOLOCK) ON HISCONBENEFICIO.organizacao_id = ORGANIZACAO.organizacao_id) " +       
			"INNER JOIN USUARIO (NOLOCK) ON HISCONBENEFICIO.usuario_id = USUARIO.usuario_id)" +
			"INNER JOIN USUARIO as SUPER (NOLOCK) ON USUARIO.supervisor_usuario_id = SUPER.usuario_id)  " +   
			"INNER JOIN USUARIOPERFIL (NOLOCK) ON USUARIO.usuario_id = USUARIOPERFIL.usuario_id) ON (PERFIL.perfil_id = USUARIOPERFIL.perfil_id) AND (PERFIL.perfil_id = HISCONBENEFICIO.perfil_id)) " +     
			"INNER JOIN PARCEIROBENEFICIO (NOLOCK) ON HISCONBENEFICIO.parceirobeneficio_id = PARCEIROBENEFICIO.parceirobeneficio_id) " +    
			"INNER JOIN PARCEIRONEGOCIO (NOLOCK) ON PARCEIROBENEFICIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id) " +    
			"INNER JOIN ETAPA (NOLOCK) ON (HISCONBENEFICIO.etapa_id = ETAPA.etapa_id) " +
			"LEFT JOIN ETAPA AS POSICAO (NOLOCK) ON (HISCONBENEFICIO.etapaposicao_id = POSICAO.etapa_id) ";  

	private String sqlCountHiscons = "SELECT COUNT(HISCONBENEFICIO.parceirobeneficio_id) AS quantidade_beneficio , PARCEIROBENEFICIO.parceirobeneficio_id, PARCEIROBENEFICIO.numerobeneficio, HISCONBENEFICIO.empresa_id, HISCONBENEFICIO.organizacao_id "+
			" FROM ((PERFIL (NOLOCK) INNER JOIN ((((HISCONBENEFICIO (NOLOCK) "+
			" INNER JOIN EMPRESA (NOLOCK) ON HISCONBENEFICIO.empresa_id = EMPRESA.empresa_id) "+ 
			" INNER JOIN ORGANIZACAO (NOLOCK) ON HISCONBENEFICIO.organizacao_id = ORGANIZACAO.organizacao_id) "+
			" INNER JOIN USUARIO (NOLOCK) ON HISCONBENEFICIO.usuario_id = USUARIO.usuario_id) "+
			" INNER JOIN USUARIOPERFIL (NOLOCK) ON USUARIO.usuario_id = USUARIOPERFIL.usuario_id) ON (PERFIL.perfil_id = HISCONBENEFICIO.perfil_id) AND (PERFIL.perfil_id = USUARIOPERFIL.perfil_id)) "+ 
			" INNER JOIN PARCEIROBENEFICIO (NOLOCK) ON HISCONBENEFICIO.parceirobeneficio_id = PARCEIROBENEFICIO.parceirobeneficio_id) "+
			" INNER JOIN PARCEIRONEGOCIO (NOLOCK) ON PARCEIROBENEFICIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id ";
			
	
	public HisconBeneficioDao(Session session, ConnJDBC conexao) {
		super(session, HisconBeneficio.class);
		this.conexao = conexao;
	}

	public HisconBeneficio validaHisconBeneficioPorParceiroBeneficioUsuarioWorkflow(Long empresa_id, Long organizacao_id, Long parceirobeneficio_id, Long usuario_id, Long workflow_id) {

		String sql = sqlHisconsExibe;

		if (empresa_id != null)
			sql += " WHERE HISCONBENEFICIO.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND HISCONBENEFICIO.organizacao_id = ? ";
		if (parceirobeneficio_id != null)
			sql += " AND HISCONBENEFICIO.parceirobeneficio_id = ? ";
		if (usuario_id != null)
			sql += " AND HISCONBENEFICIO.usuario_id = ? ";
		if (workflow_id != null)
			sql += " AND HISCONBENEFICIO.workflow_id = ? ";

		this.conn = this.conexao.getConexao();

		HisconBeneficio hisconbeneficio = new HisconBeneficio();

		try {
			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, hisconbeneficio.getEmpresa().getEmpresa_id());
			this.stmt.setLong(2, hisconbeneficio.getOrganizacao().getOrganizacao_id());
			this.stmt.setLong(3, hisconbeneficio.getParceiroBeneficio().getParceiroBeneficio_id());
			this.stmt.setLong(4, hisconbeneficio.getUsuario().getUsuario_id());
			this.stmt.setLong(5, hisconbeneficio.getWorkflow().getWorkflow_id());

			this.rsHisconBeneficio = this.stmt.executeQuery();
			
			while (rsHisconBeneficio.next()) {
				
				hisconbeneficio.setHisconBeneficio_id(rsHisconBeneficio.getLong("hisconbeneficio_id"));
				
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			try {
				if (rsHisconBeneficio != null || stmt != null || conn != null) {

					this.conexao.closeConnection(rsHisconBeneficio, stmt, conn);

				}
			} catch (Exception e) {

				e.printStackTrace();

			}
		}

		return hisconbeneficio;
	}

	public HisconBeneficio validaHisconBeneficioPorParceiroBeneficioUsuario(Long empresa_id, Long organizacao_id, Long parceirobeneficio_id, Long usuario_id) {

		String sql = sqlHisconsExibe;

		if (empresa_id != null)
			sql += " WHERE HISCONBENEFICIO.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND HISCONBENEFICIO.organizacao_id = ? ";
		if (parceirobeneficio_id != null)
			sql += " AND HISCONBENEFICIO.parceirobeneficio_id = ? ";
		if (usuario_id != null)
			sql += " AND HISCONBENEFICIO.usuario_id = ? ";

		this.conn = this.conexao.getConexao();

		HisconBeneficio hisconbeneficio = new HisconBeneficio();

		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, hisconbeneficio.getEmpresa().getEmpresa_id());
			this.stmt.setLong(2, hisconbeneficio.getOrganizacao().getOrganizacao_id());
			this.stmt.setLong(3, hisconbeneficio.getParceiroBeneficio().getParceiroBeneficio_id());
			this.stmt.setLong(4, hisconbeneficio.getUsuario().getUsuario_id());
			
			this.rsHisconBeneficio = this.stmt.executeQuery();
			
			while (rsHisconBeneficio.next()) {

				hisconbeneficio.setHisconBeneficio_id(rsHisconBeneficio.getLong("hisconbeneficio_id"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rsHisconBeneficio != null || stmt != null || conn != null) {
					this.conexao.closeConnection(rsHisconBeneficio, stmt, conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return hisconbeneficio;
	}

	public HisconBeneficio buscaHisconBeneficioByParceiroBeneficio(Long empresa_id, Long organizacao_id, Long parceirobeneficio_id) {

		String sql = sqlHisconsExibe;

		if (empresa_id != null)
			sql += " WHERE HISCONBENEFICIO.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND HISCONBENEFICIO.organizacao_id = ? ";
		if (parceirobeneficio_id != null)
			sql += " AND HISCONBENEFICIO.parceirobeneficio_id = ? ";

		this.conn = this.conexao.getConexao();

		HisconBeneficio hisconbeneficio = null;

		try {
			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, parceirobeneficio_id);

			this.rsHisconBeneficio = this.stmt.executeQuery();

			while (rsHisconBeneficio.next()) {
				
				hisconbeneficio = new HisconBeneficio();

				getHisconsBeneficio(hisconbeneficio);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			try {
				if (rsHisconBeneficio != null || stmt != null || conn != null) {

					this.conexao.closeConnection(rsHisconBeneficio, stmt, conn);

				}

			} catch (Exception e) {

				e.printStackTrace();

			}
		}

		return hisconbeneficio;
	}
	
	public Collection<HisconBeneficio> buscaHisconsBeneficioByParceiroBeneficio(Long empresa_id, Long organizacao_id, Long parceirobeneficio_id) {

		String sql = sqlHisconsExibe;

		if (empresa_id != null)
			sql += " WHERE HISCONBENEFICIO.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND HISCONBENEFICIO.organizacao_id = ? ";
		if (parceirobeneficio_id != null)
			sql += " AND HISCONBENEFICIO.parceirobeneficio_id = ? ORDER BY HISCONBENEFICIO.created DESC ";

		this.conn = this.conexao.getConexao();

		Collection<HisconBeneficio> hisconsbeneficio = new ArrayList<HisconBeneficio>();

		try {
			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, parceirobeneficio_id);

			this.rsHisconBeneficio = this.stmt.executeQuery();

			while (rsHisconBeneficio.next()) {

				getHisconsBeneficio(hisconsbeneficio);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			try {
				if (rsHisconBeneficio != null || stmt != null || conn != null) {

					this.conexao.closeConnection(rsHisconBeneficio, stmt, conn);

				}

			} catch (Exception e) {

				e.printStackTrace();

			}
		}

		return hisconsbeneficio;
	}

	public HisconBeneficio buscaHisconBeneficioById(Long hisconBeneficio_id) {

		String sql = sqlHisconsBeneficio;

		if (hisconBeneficio_id != null)
			sql += " WHERE HISCONBENEFICIO.hisconBeneficio_id = ? ";

		this.conn = this.conexao.getConexao();
		
		HisconBeneficio hisconBeneficio = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, hisconBeneficio_id);

			this.rsHisconBeneficio = this.stmt.executeQuery();

			while (rsHisconBeneficio.next()) {
				
				hisconBeneficio = new HisconBeneficio();

				getHisconsBeneficio(hisconBeneficio);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rsHisconBeneficio != null || stmt != null || conn != null) {
					this.conexao.closeConnection(rsHisconBeneficio, stmt, conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return hisconBeneficio;
	}

	public Collection<HisconBeneficio> buscaHisconBeneficiosByUsuarioPerfil(Empresa empresa, Organizacao organizacao,Usuario usuario, Calendar c1 , Calendar c2) {

		String sql = sqlHisconsExibe;

		if (empresa != null)
			sql += " WHERE HISCONBENEFICIO.empresa_id = ? ";

		if (organizacao != null)
			sql += " AND HISCONBENEFICIO.organizacao_id = ? ";

		if (usuario != null)
			sql += " AND ( USUARIO.usuario_id = ? OR USUARIO.supervisor_usuario_id = ? )";
		
		if(c1 != null)
			sql += " AND (HISCONBENEFICIO.created BETWEEN ? AND ? )";
		
		sql += " ORDER BY HISCONBENEFICIO.hisconbeneficio_id desc ";

		this.conn = this.conexao.getConexao();

		Collection<HisconBeneficio> hisconsBeneficio = new ArrayList<HisconBeneficio>();

		try {
			
			System.out.println(sql);
			System.out.println(c1.getTime());
			System.out.println(c2.getTime());

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa.getEmpresa_id());
			this.stmt.setLong(2, organizacao.getOrganizacao_id());
			this.stmt.setLong(3, usuario.getUsuario_id());
			this.stmt.setLong(4, usuario.getUsuario_id());
			
			if(c1 != null){
				this.stmt.setTimestamp(5,new Timestamp(c1.getTimeInMillis()));
				this.stmt.setTimestamp(6,new Timestamp(c2.getTimeInMillis()));
			}

			this.rsHisconBeneficio = this.stmt.executeQuery();

			while (rsHisconBeneficio.next()) {

				getHisconsBeneficio(hisconsBeneficio);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rsHisconBeneficio != null || stmt != null || conn != null) {
					this.conexao.closeConnection(rsHisconBeneficio, stmt, conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return hisconsBeneficio;
	}
	
	public Collection<HisconBeneficio> buscaHisconsByFiltro(Empresa empresa, Organizacao organizacao,Collection<Usuario> consultores, Collection<String> status, 
			Collection<String> posicoes, String cliente,String documento,Calendar calendarInicio,Calendar calendarFim) {

		String sql = sqlHisconsExibe;
		String clause = "";
		int x = 0;

		if (empresa != null)
			sql += " WHERE HISCONBENEFICIO.empresa_id = ? ";

		if (organizacao != null)
			sql += " AND HISCONBENEFICIO.organizacao_id = ? ";
		
		if(!cliente.equals(""))
			sql += " AND PARCEIRONEGOCIO.nome like ? ";

		if(!documento.equals(""))
			sql += " AND ( PARCEIRONEGOCIO.cpf like ? OR PARCEIROBENEFICIO.numerobeneficio like ? ) ";
		
		sql += " AND ( 1=1 ";

		for(String statusAux1 : status){

			clause = x <= 0 ? "AND" : "OR";

			if(!statusAux1.equals("")){
				sql += clause + " ( ETAPA.nome like ? ) ";
				x++;
				clause = "";
			}

		}

		sql += " ) ";
		
		x = 0;
		sql += " AND ( 1=1 ";

		for(String posicoesAux1 : posicoes){

			clause = x <= 0 ? "AND" : "OR";

			if(!posicoesAux1.equals("")){
				sql += clause + " ( POSICAO.nome like ? ) ";
				x++;
				clause = "";
			}

		}

		sql += " ) ";

		x = 0;
		sql += " AND ( 1=1 ";

		for(Usuario u : consultores){
			clause = x <= 0 ? "AND" : "OR";

			if(u != null) {
				sql += clause + " ( USUARIO.usuario_id = ? OR SUPER.usuario_id = ? ) ";
				x++;
				clause = "";
			}

		}
		
		sql += " ) ";
		
		if(calendarInicio != null)
			sql += " AND (HISCONBENEFICIO.created BETWEEN ? AND ? )";

		this.conn = this.conexao.getConexao();

		Collection<HisconBeneficio> hisconsBeneficio = new ArrayList<HisconBeneficio>();

		try {

			this.stmt = conn.prepareStatement(sql);

			System.out.println(sql);
			System.out.println(calendarInicio.getTime());
			System.out.println(calendarFim.getTime());

			int curr = 1;

			if(empresa != null){
				this.stmt.setLong(curr, empresa.getEmpresa_id());
				curr++;
			}

			if(organizacao != null){
				this.stmt.setLong(curr, organizacao.getOrganizacao_id());
				curr++;
			}
			
			if(!cliente.equals("")){
				this.stmt.setString(curr, '%' + cliente + '%');
				curr++;
			}
			
			if(!documento.equals("")){
				this.stmt.setString(curr, '%' + documento + '%');
				curr++;
				this.stmt.setString(curr, '%' + documento + '%');
				curr++;
			}
			
			for(String statusAux2 : status){

				if(!statusAux2.equals("")) {
					this.stmt.setString(curr, '%' + statusAux2 + '%');
					curr++;
				}

			}

			for(Usuario u : consultores){

				if(u != null) {
					this.stmt.setLong(curr,u.getUsuario_id());
					curr++;
					this.stmt.setLong(curr,u.getUsuario_id());
					curr++;
				}

			}
			
			if(calendarInicio != null) {
				
				this.stmt.setTimestamp(curr,new Timestamp(calendarInicio.getTimeInMillis()));
				curr++;

				this.stmt.setTimestamp(curr,new Timestamp(calendarFim.getTimeInMillis()));
				curr++;
				
			}

			this.rsHisconBeneficio = this.stmt.executeQuery();

			while (rsHisconBeneficio.next()) {

				getHisconsBeneficio(hisconsBeneficio);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rsHisconBeneficio != null || stmt != null || conn != null) {
					this.conexao.closeConnection(rsHisconBeneficio, stmt, conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return hisconsBeneficio;

	}

	public Integer buscaCountHisconsBeneficios(Long empresa_id, Long organizacao_id, Long parceirobeneficio_id) {

		String sql = sqlCountHiscons;

		if (empresa_id != null)
			sql += " WHERE HISCONBENEFICIO.empresa_id = ? ";

		if (organizacao_id != null)
			sql += "  AND HISCONBENEFICIO.organizacao_id = ? AND HISCONBENEFICIO.created BETWEEN getDate() -20 and getDate() " +
					" AND HISCONBENEFICIO.parceirobeneficio_id = ? GROUP BY PARCEIROBENEFICIO.numerobeneficio, PARCEIROBENEFICIO.parceirobeneficio_id, " +
					" HISCONBENEFICIO.empresa_id, HISCONBENEFICIO.organizacao_id ";

		this.conn = this.conexao.getConexao();

		Integer count = 0;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, parceirobeneficio_id);

			this.rsHisconBeneficio = this.stmt.executeQuery();

			while (rsHisconBeneficio.next()) {

				 count = rsHisconBeneficio.getInt("quantidade_beneficio");

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			try {
				if (rsHisconBeneficio != null || stmt != null || conn != null) {

					this.conexao.closeConnection(rsHisconBeneficio, stmt, conn);

				}

			} catch (Exception e) {

				e.printStackTrace();

			}
		}

		return count;

	}

	public Collection<HisconBeneficio> buscaHisconsToUpload(Long empresa_id, Long organizacao_id, Long etapa_id) {

		String sql = sqlHisconsExibe;

		if (empresa_id != null)
			sql += " WHERE HISCONBENEFICIO.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND HISCONBENEFICIO.organizacao_id = ? AND HISCONBENEFICIO.etapa_id = ? ORDER BY HISCONBENEFICIO.hisconbeneficio_id desc ";
		
		this.conn = this.conexao.getConexao();

		Collection<HisconBeneficio> hisconsBeneficio = new ArrayList<HisconBeneficio>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, etapa_id);	

			this.rsHisconBeneficio = this.stmt.executeQuery();

			while (rsHisconBeneficio.next()) {

				getHisconsBeneficio(hisconsBeneficio);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rsHisconBeneficio != null || stmt != null || conn != null) {
					this.conexao.closeConnection(rsHisconBeneficio, stmt, conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return hisconsBeneficio;

	}

	
	//TODO : Consulta incorreta.
	public Collection<Etapa> buscaWorKFlowEtapaByHisconBeneficioPerfil() {

		String sql = " SELECT WORKFLOWTRANSICAO.workflowtransicao_id, WORKFLOWTRANSICAO.empresa_id , EMPRESA.nome as empresa_nome "+
				", WORKFLOWTRANSICAO.organizacao_id, ORGANIZACAO.nome as organizacao_nome , WORKFLOWTRANSICAO.etapa_id "+
				", WT1.nome as etapa_nome, WORKFLOWTRANSICAO.workflowetapaproximo_id, WT2.nome as workflowetapaproximo_nome "+
				", PERFIL.perfil_id, PERFIL.nome as PERFIL_nome "+
				", WORKFLOWTRANSICAO.sequencia, WORKFLOWTRANSICAO.ispadrao, WORKFLOWTRANSICAO.isactive "+ 
				" FROM (((WORKFLOWTRANSICAO (NOLOCK) INNER JOIN PERFIL (NOLOCK) ON WORKFLOWTRANSICAO.perfil_id = PERFIL.perfil_id) "+ 
				" INNER JOIN EMPRESA (NOLOCK) ON WORKFLOWTRANSICAO.empresa_id = EMPRESA.empresa_id) " +
				" INNER JOIN ORGANIZACAO (NOLOCK) ON WORKFLOWTRANSICAO.organizacao_id = ORGANIZACAO.organizacao_id) " +
				" INNER JOIN ETAPA (NOLOCK) AS WT1 ON (WORKFLOWTRANSICAO.etapa_id = WT1.etapa_id) "+ 
				" INNER JOIN ETAPA (NOLOCK) AS WT2 ON (WORKFLOWTRANSICAO.workflowetapaproximo_id = WT2.etapa_id) "+
				" WHERE WORKFLOWTRANSICAO.empresa_id=1 AND WORKFLOWTRANSICAO.organizacao_id=1 "+
				" AND PERFIL.perfil_id= ? ";

		this.conn = this.conexao.getConexao();

		Collection<Etapa> workflowsEtapa = new ArrayList<Etapa>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.rsHisconBeneficio= this.stmt.executeQuery();

			while (rsHisconBeneficio.next()) {

				Etapa workflowEtapa = new Etapa();

				workflowEtapa.setEtapa_id(rsHisconBeneficio.getLong("workflowetapaproximo_id"));
				workflowEtapa.setNome(rsHisconBeneficio.getString("etapa_nome"));

				workflowsEtapa.add(workflowEtapa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsHisconBeneficio, stmt, conn);

		return workflowsEtapa;

	}	

	private void getHisconsBeneficio(Collection<HisconBeneficio> hisconsBeneficio) throws SQLException {

		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();
		ParceiroBeneficio parceiroBeneficio = new ParceiroBeneficio();
		ParceiroNegocio parceiro = new ParceiroNegocio();
		Usuario usuario = new Usuario();
		Etapa etapa = new Etapa();		
		HisconBeneficio hisconBeneficio = new HisconBeneficio();		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.FFF");

		empresa.setEmpresa_id(rsHisconBeneficio.getLong("empresa_id"));
		empresa.setNome(rsHisconBeneficio.getString("empresa_nome"));

		organizacao.setOrganizacao_id(rsHisconBeneficio.getLong("organizacao_id"));
		organizacao.setNome(rsHisconBeneficio.getString("organizacao_nome"));

		parceiro.setParceiroNegocio_id(rsHisconBeneficio.getLong("parceironegocio_id"));
		parceiro.setNome(rsHisconBeneficio.getString("parceironegocio_nome"));
		parceiro.setCpf(rsHisconBeneficio.getString("cpf"));

		parceiroBeneficio.setParceiroNegocio(parceiro);

		parceiroBeneficio.setParceiroBeneficio_id(rsHisconBeneficio.getLong("parceirobeneficio_id"));
		parceiroBeneficio.setNumeroBeneficio(rsHisconBeneficio.getString("numerobeneficio"));

		etapa.setEtapa_id(rsHisconBeneficio.getLong("etapa_id"));
		etapa.setNome(rsHisconBeneficio.getString("etapa_nome"));
		
		usuario.setUsuario_id(rsHisconBeneficio.getLong("usuario_id"));
		usuario.setNome(rsHisconBeneficio.getString("usuario_nome"));

		hisconBeneficio.setHisconBeneficio_id(rsHisconBeneficio.getLong("hisconbeneficio_id"));
		hisconBeneficio.setEmpresa(empresa);
		hisconBeneficio.setOrganizacao(organizacao);
		hisconBeneficio.setParceiroBeneficio(parceiroBeneficio);
		hisconBeneficio.setEtapa(etapa);
		hisconBeneficio.setIsEnviado(rsHisconBeneficio.getBoolean("isenviado"));


		hisconBeneficio.setUsuario(usuario);

		try {

			if (rsHisconBeneficio.getDate("created") != null) {
				Calendar created = new GregorianCalendar();
				created.setTime(sdf.parse(rsHisconBeneficio.getTimestamp("created").toString()));
				hisconBeneficio.setCreated(created);
			}

			if (rsHisconBeneficio.getDate("updated") != null){
				Calendar updated = new GregorianCalendar();
				updated.setTime(sdf.parse(rsHisconBeneficio.getTimestamp("updated").toString()));
				hisconBeneficio.setUpdated(updated);
			}

			if (rsHisconBeneficio.getDate("dataadm") != null){
				Calendar dataadm = new GregorianCalendar();
				dataadm.setTime(sdf.parse(rsHisconBeneficio.getTimestamp("dataadm").toString())); 
				hisconBeneficio.setDataAdm(dataadm);
			}

			if(rsHisconBeneficio.getDate("dataenvio")!=null){
				Calendar dataenvio = new GregorianCalendar();
				dataenvio.setTime(sdf.parse(rsHisconBeneficio.getTimestamp("dataenvio").toString()));
				hisconBeneficio.setDataEnvio(dataenvio);		
			}

			if (rsHisconBeneficio.getString("caminhoarquivo") != null) {
				hisconBeneficio.setCaminhoArquivo(rsHisconBeneficio.getString("caminhoarquivo"));
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}

		hisconsBeneficio.add(hisconBeneficio);

	}
	
	private void getHisconsBeneficio(HisconBeneficio hisconBeneficio) throws SQLException {

		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();
		ParceiroBeneficio parceiroBeneficio = new ParceiroBeneficio();
		ParceiroNegocio parceiro = new ParceiroNegocio();
		Usuario usuario = new Usuario();
		Perfil perfil = new Perfil();
		Workflow workflow = new Workflow();
		Etapa etapa = new Etapa();			
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.FFF");
		

		empresa.setEmpresa_id(rsHisconBeneficio.getLong("empresa_id"));
		empresa.setNome(rsHisconBeneficio.getString("empresa_nome"));

		organizacao.setOrganizacao_id(rsHisconBeneficio.getLong("organizacao_id"));
		organizacao.setNome(rsHisconBeneficio.getString("organizacao_nome"));
		
		perfil.setPerfil_id(rsHisconBeneficio.getLong("perfil_id"));
		perfil.setNome(rsHisconBeneficio.getString("perfil_nome"));

		workflow.setWorkflow_id(rsHisconBeneficio.getLong("workflow_id"));

		parceiro.setParceiroNegocio_id(rsHisconBeneficio.getLong("parceironegocio_id"));
		parceiro.setNome(rsHisconBeneficio.getString("parceironegocio_nome"));
		parceiro.setCpf(rsHisconBeneficio.getString("cpf"));

		parceiroBeneficio.setParceiroNegocio(parceiro);

		parceiroBeneficio.setParceiroBeneficio_id(rsHisconBeneficio.getLong("parceirobeneficio_id"));
		parceiroBeneficio.setNumeroBeneficio(rsHisconBeneficio.getString("numerobeneficio"));

		etapa.setEtapa_id(rsHisconBeneficio.getLong("etapa_id"));
		etapa.setNome(rsHisconBeneficio.getString("etapa_nome"));
		
		usuario.setUsuario_id(rsHisconBeneficio.getLong("usuario_id"));
		usuario.setNome(rsHisconBeneficio.getString("usuario_nome"));

		hisconBeneficio.setHisconBeneficio_id(rsHisconBeneficio.getLong("hisconbeneficio_id"));
		hisconBeneficio.setEmpresa(empresa);
		hisconBeneficio.setOrganizacao(organizacao);
		hisconBeneficio.setParceiroBeneficio(parceiroBeneficio);
		hisconBeneficio.setEtapa(etapa);
		hisconBeneficio.setPerfil(perfil);
		hisconBeneficio.setWorkflow(workflow);
		hisconBeneficio.setIsEnviado(rsHisconBeneficio.getBoolean("isenviado"));

		hisconBeneficio.setUsuario(usuario);

		try {

			if (rsHisconBeneficio.getDate("created") != null) {
				Calendar created = new GregorianCalendar();
				created.setTime(sdf.parse(rsHisconBeneficio.getTimestamp("created").toString()));
				hisconBeneficio.setCreated(created);
			}

			if (rsHisconBeneficio.getDate("updated") != null){
				Calendar updated = new GregorianCalendar();
				updated.setTime(sdf.parse(rsHisconBeneficio.getTimestamp("updated").toString()));
				hisconBeneficio.setUpdated(updated);
			}

			if (rsHisconBeneficio.getDate("dataadm") != null){
				Calendar dataadm = new GregorianCalendar();
				dataadm.setTime(sdf.parse(rsHisconBeneficio.getTimestamp("dataadm").toString())); 
				hisconBeneficio.setDataAdm(dataadm);
			}

			if(rsHisconBeneficio.getDate("dataenvio")!=null){
				Calendar dataenvio = new GregorianCalendar();
				dataenvio.setTime(sdf.parse(rsHisconBeneficio.getTimestamp("dataenvio").toString()));
				hisconBeneficio.setDataEnvio(dataenvio);		
			}

			if (rsHisconBeneficio.getString("caminhoarquivo") != null) {
				hisconBeneficio.setCaminhoArquivo(rsHisconBeneficio.getString("caminhoarquivo"));
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
}