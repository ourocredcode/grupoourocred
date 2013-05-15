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
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.HisconBeneficio;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.ParceiroBeneficio;
import br.com.sgo.modelo.ParceiroNegocio;
import br.com.sgo.modelo.Perfil;
import br.com.sgo.modelo.Usuario;
import br.com.sgo.modelo.Workflow;
import br.com.sgo.modelo.WorkflowEtapa;

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
			 ", PARCEIRONEGOCIO.nome as parceironegocio_nome, WORKFLOWETAPA.workflowetapa_id, WORKFLOWETAPA.nome AS workflowetapa_nome, HISCONBENEFICIO.created, HISCONBENEFICIO.updated " + 
			 ", HISCONBENEFICIO.dataadm, HISCONBENEFICIO.dataenvio, HISCONBENEFICIO.caminhoarquivo " +
			 ", HISCONBENEFICIO.isworkflow, HISCONBENEFICIO.isenviado, HISCONBENEFICIO.isimportado, HISCONBENEFICIO.ispadrao, WORKFLOW.workflow_id " +
			 " FROM (PERFIL (NOLOCK) INNER JOIN ((((((PARCEIRONEGOCIO (NOLOCK) INNER JOIN (HISCONBENEFICIO (NOLOCK) " +
			 " INNER JOIN PARCEIROBENEFICIO (NOLOCK) ON HISCONBENEFICIO.parceirobeneficio_id = PARCEIROBENEFICIO.parceirobeneficio_id) ON PARCEIRONEGOCIO.parceironegocio_id = PARCEIROBENEFICIO.parceironegocio_id) " +
			 " INNER JOIN EMPRESA (NOLOCK) ON HISCONBENEFICIO.empresa_id = EMPRESA.empresa_id) " +
			 " INNER JOIN WORKFLOW (NOLOCK) ON WORKFLOW.workflow_id = HISCONBENEFICIO.workflow_id) " +
			 " INNER JOIN ORGANIZACAO (NOLOCK) ON HISCONBENEFICIO.organizacao_id = ORGANIZACAO.organizacao_id) " +
			 " INNER JOIN USUARIO (NOLOCK) ON HISCONBENEFICIO.usuario_id = USUARIO.usuario_id) " +
			 " INNER JOIN USUARIOPERFIL (NOLOCK) ON USUARIO.usuario_id = USUARIOPERFIL.usuario_id) ON PERFIL.perfil_id = USUARIOPERFIL.perfil_id) " +
			 " INNER JOIN WORKFLOWETAPA (NOLOCK) ON (HISCONBENEFICIO.workflowetapa_id = WORKFLOWETAPA.workflowetapa_id) AND (HISCONBENEFICIO.workflow_id = WORKFLOWETAPA.workflow_id)";

	private String sqlHisconsExibe = " SELECT HISCONBENEFICIO.hisconbeneficio_id, HISCONBENEFICIO.empresa_id, EMPRESA.nome as empresa_nome " + 
			 ", HISCONBENEFICIO.organizacao_id, ORGANIZACAO.nome as organizacao_nome, HISCONBENEFICIO.parceirobeneficio_id , PARCEIROBENEFICIO.numerobeneficio " + 
			 ", HISCONBENEFICIO.usuario_id, USUARIO.nome as usuario_nome,PERFIL.perfil_id, PERFIL.nome as perfil_nome " +
			 ", HISCONBENEFICIO.workflow_id, PARCEIRONEGOCIO.parceironegocio_id, PARCEIRONEGOCIO.cpf " +
			 ", PARCEIRONEGOCIO.nome as parceironegocio_nome, WORKFLOWETAPA.workflowetapa_id, WORKFLOWETAPA.nome AS workflowetapa_nome, HISCONBENEFICIO.created, HISCONBENEFICIO.updated " + 
			 ", HISCONBENEFICIO.dataadm, HISCONBENEFICIO.dataenvio, HISCONBENEFICIO.caminhoarquivo " +
			 ", HISCONBENEFICIO.isworkflow, HISCONBENEFICIO.isenviado, HISCONBENEFICIO.isimportado, HISCONBENEFICIO.ispadrao " +
			 " FROM (((PERFIL (NOLOCK) INNER JOIN ((((HISCONBENEFICIO (NOLOCK) "+
			 " INNER JOIN EMPRESA (NOLOCK) ON HISCONBENEFICIO.empresa_id = EMPRESA.empresa_id) " +
			 " INNER JOIN ORGANIZACAO (NOLOCK) ON HISCONBENEFICIO.organizacao_id = ORGANIZACAO.organizacao_id) " + 
			 " INNER JOIN USUARIO (NOLOCK) ON HISCONBENEFICIO.usuario_id = USUARIO.usuario_id) " +
			 " INNER JOIN USUARIOPERFIL (NOLOCK) ON USUARIO.usuario_id = USUARIOPERFIL.usuario_id) ON (PERFIL.perfil_id = USUARIOPERFIL.perfil_id) AND (PERFIL.perfil_id = HISCONBENEFICIO.perfil_id)) " + 
			 " INNER JOIN PARCEIROBENEFICIO (NOLOCK) ON HISCONBENEFICIO.parceirobeneficio_id = PARCEIROBENEFICIO.parceirobeneficio_id) " +
			 " INNER JOIN PARCEIRONEGOCIO (NOLOCK) ON PARCEIROBENEFICIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id) " +
			 " INNER JOIN WORKFLOWETAPA (NOLOCK) ON (HISCONBENEFICIO.workflowetapa_id = WORKFLOWETAPA.workflowetapa_id) AND (HISCONBENEFICIO.workflow_id = WORKFLOWETAPA.workflow_id) ";

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

	public Collection<HisconBeneficio> buscaHisconBeneficiosByUsuarioPerfil(Empresa empresa, Organizacao organizacao,Usuario usuario) {

		String sql = sqlHisconsExibe;

		if (empresa != null)
			sql += " WHERE HISCONBENEFICIO.empresa_id = ? ";

		if (organizacao != null)
			sql += " AND HISCONBENEFICIO.organizacao_id = ? ";

		if (usuario != null)
			sql += " AND ( USUARIO.usuario_id = ? OR USUARIO.supervisor_usuario_id = ? )";

		this.conn = this.conexao.getConexao();

		Collection<HisconBeneficio> hisconsBeneficio = new ArrayList<HisconBeneficio>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa.getEmpresa_id());
			this.stmt.setLong(2, organizacao.getOrganizacao_id());
			this.stmt.setLong(3, usuario.getUsuario_id());
			this.stmt.setLong(4, usuario.getUsuario_id());

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
			sql += " AND HISCONBENEFICIO.organizacao_id = ? AND HISCONBENEFICIO.created BETWEEN getDate() -20 and getDate() "+
					" AND HISCONBENEFICIO.parceirobeneficio_id = ? GROUP BY PARCEIROBENEFICIO.numerobeneficio, PARCEIROBENEFICIO.parceirobeneficio_id, HISCONBENEFICIO.empresa_id, HISCONBENEFICIO.organizacao_id ";

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
			sql += " AND HISCONBENEFICIO.organizacao_id = ? AND HISCONBENEFICIO.workflowetapa_id = ?";
		
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

	public Collection<WorkflowEtapa> buscaWorKFlowEtapaByHisconBeneficioPerfil() {

		String sql = " SELECT WORKFLOWTRANSICAO.workflowtransicao_id, WORKFLOWTRANSICAO.empresa_id , EMPRESA.nome as empresa_nome "+
				", WORKFLOWTRANSICAO.organizacao_id, ORGANIZACAO.nome as organizacao_nome , WORKFLOWTRANSICAO.workflowetapa_id "+
				", WT1.nome as workflowetapa_nome, WORKFLOWTRANSICAO.workflowetapaproximo_id, WT2.nome as workflowetapaproximo_nome "+
				", PERFIL.perfil_id, PERFIL.nome as PERFIL_nome "+
				", WORKFLOWTRANSICAO.sequencia, WORKFLOWTRANSICAO.ispadrao, WORKFLOWTRANSICAO.isactive "+ 
				" FROM (((WORKFLOWTRANSICAO (NOLOCK) INNER JOIN PERFIL (NOLOCK) ON WORKFLOWTRANSICAO.perfil_id = PERFIL.perfil_id) "+ 
				" INNER JOIN EMPRESA (NOLOCK) ON WORKFLOWTRANSICAO.empresa_id = EMPRESA.empresa_id) " +
				" INNER JOIN ORGANIZACAO (NOLOCK) ON WORKFLOWTRANSICAO.organizacao_id = ORGANIZACAO.organizacao_id) " +
				" INNER JOIN WORKFLOWETAPA (NOLOCK) AS WT1 ON (WORKFLOWTRANSICAO.workflowetapa_id = WT1.workflowetapa_id) "+ 
				" INNER JOIN WORKFLOWETAPA (NOLOCK) AS WT2 ON (WORKFLOWTRANSICAO.workflowetapaproximo_id = WT2.workflowetapa_id) "+
				"	WHERE WORKFLOWTRANSICAO.empresa_id=1 AND WORKFLOWTRANSICAO.organizacao_id=1 "+
				" AND PERFIL.perfil_id= ? ";

		this.conn = this.conexao.getConexao();

		Collection<WorkflowEtapa> workflowsEtapa = new ArrayList<WorkflowEtapa>();

		try {

			this.stmt = conn.prepareStatement(sql);

			//this.stmt.setLong(1, hisconbeneficio_id);
			//this.stmt.setLong(2, perfil_id);

			this.rsHisconBeneficio= this.stmt.executeQuery();

			while (rsHisconBeneficio.next()) {

				WorkflowEtapa workflowEtapa = new WorkflowEtapa();

				workflowEtapa.setWorkflowEtapa_id(rsHisconBeneficio.getLong("workflowetapaproximo_id"));
				workflowEtapa.setNome(rsHisconBeneficio.getString("nome"));

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
		WorkflowEtapa workflowEtapa = new WorkflowEtapa();		
		HisconBeneficio hisconBeneficio = new HisconBeneficio();		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar created = new GregorianCalendar();

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

		workflowEtapa.setWorkflowEtapa_id(rsHisconBeneficio.getLong("workflowetapa_id"));
		workflowEtapa.setNome(rsHisconBeneficio.getString("workflowetapa_nome"));
		
		usuario.setUsuario_id(rsHisconBeneficio.getLong("usuario_id"));
		usuario.setNome(rsHisconBeneficio.getString("usuario_nome"));

		hisconBeneficio.setHisconBeneficio_id(rsHisconBeneficio.getLong("hisconbeneficio_id"));
		hisconBeneficio.setEmpresa(empresa);
		hisconBeneficio.setOrganizacao(organizacao);
		hisconBeneficio.setParceiroBeneficio(parceiroBeneficio);
		hisconBeneficio.setWorkflowEtapa(workflowEtapa);
		hisconBeneficio.setIsEnviado(rsHisconBeneficio.getBoolean("isenviado"));


		hisconBeneficio.setUsuario(usuario);

		try {

			if (rsHisconBeneficio.getDate("created") != null) {
				created.setTime(sdf.parse(rsHisconBeneficio.getTimestamp("created").toString()));
				hisconBeneficio.setCreated(created);
			}

			if (rsHisconBeneficio.getDate("updated") != null){
				created.setTime(sdf.parse(rsHisconBeneficio.getTimestamp("updated").toString()));
				hisconBeneficio.setUpdated(sdf.getCalendar());
			}

			if (rsHisconBeneficio.getDate("dataadm") != null){
				created.setTime(sdf.parse(rsHisconBeneficio.getTimestamp("dataadm").toString())); 
				hisconBeneficio.setDataAdm(sdf.getCalendar());
			}

			if(rsHisconBeneficio.getDate("dataenvio")!=null){
				created.setTime(sdf.parse(rsHisconBeneficio.getTimestamp("dataenvio").toString()));
				hisconBeneficio.setDataEnvio(sdf.getCalendar());		
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
		WorkflowEtapa workflowEtapa = new WorkflowEtapa();			
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar created = new GregorianCalendar();

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

		workflowEtapa.setWorkflowEtapa_id(rsHisconBeneficio.getLong("workflowetapa_id"));
		workflowEtapa.setNome(rsHisconBeneficio.getString("workflowetapa_nome"));
		
		usuario.setUsuario_id(rsHisconBeneficio.getLong("usuario_id"));
		usuario.setNome(rsHisconBeneficio.getString("usuario_nome"));

		hisconBeneficio.setHisconBeneficio_id(rsHisconBeneficio.getLong("hisconbeneficio_id"));
		hisconBeneficio.setEmpresa(empresa);
		hisconBeneficio.setOrganizacao(organizacao);
		hisconBeneficio.setParceiroBeneficio(parceiroBeneficio);
		hisconBeneficio.setWorkflowEtapa(workflowEtapa);
		hisconBeneficio.setPerfil(perfil);
		hisconBeneficio.setWorkflow(workflow);
		hisconBeneficio.setIsEnviado(rsHisconBeneficio.getBoolean("isenviado"));

		hisconBeneficio.setUsuario(usuario);

		try {

			if (rsHisconBeneficio.getDate("created") != null) {
				created.setTime(sdf.parse(rsHisconBeneficio.getTimestamp("created").toString()));
				hisconBeneficio.setCreated(created);
			}

			if (rsHisconBeneficio.getDate("updated") != null){
				created.setTime(sdf.parse(rsHisconBeneficio.getTimestamp("updated").toString()));
				hisconBeneficio.setUpdated(sdf.getCalendar());
			}

			if (rsHisconBeneficio.getDate("dataadm") != null){
				created.setTime(sdf.parse(rsHisconBeneficio.getTimestamp("dataadm").toString())); 
				hisconBeneficio.setDataAdm(sdf.getCalendar());
			}

			if(rsHisconBeneficio.getDate("dataenvio")!=null){
				created.setTime(sdf.parse(rsHisconBeneficio.getTimestamp("dataenvio").toString()));
				hisconBeneficio.setDataEnvio(sdf.getCalendar());		
			}

			if (rsHisconBeneficio.getString("caminhoarquivo") != null) {
				hisconBeneficio.setCaminhoArquivo(rsHisconBeneficio.getString("caminhoarquivo"));
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
}