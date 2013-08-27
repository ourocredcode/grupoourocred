package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Etapa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Perfil;
import br.com.sgo.modelo.Workflow;
import br.com.sgo.modelo.WorkflowEtapaPerfilAcesso;


@Component
public class WorkflowEtapaPerfilAcessoDao extends Dao<WorkflowEtapaPerfilAcesso> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsWorkflowEtapaPerfilAcesso;

	private final String sqlWorkflowEtapaPerfilAcesso = "SELECT WORKFLOWETAPAPERFILACESSO.etapa_id, WORKFLOWETAPAPERFILACESSO.isleituraescrita, WORKFLOWETAPAPERFILACESSO.isactive "+
							 ", WORKFLOWETAPAPERFILACESSO.empresa_id, EMPRESA.nome as empresa_nome, WORKFLOWETAPAPERFILACESSO.organizacao_id "+
							 ", ORGANIZACAO.nome as organizacao_nome, WORKFLOW.workflow_id, WORKFLOW.nome as workflow_nome "+
							 ", WORKFLOWETAPAPERFILACESSO.etapa_id, ETAPA.nome as etapa_nome "+
							 ", WORKFLOWETAPAPERFILACESSO.perfil_id, PERFIL.nome as perfil_nome, WORKFLOWETAPAPERFILACESSO.isactive "+
							 " FROM ((ORGANIZACAO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) "+
							 " INNER JOIN WORKFLOWETAPAPERFILACESSO (NOLOCK) ON EMPRESA.empresa_id = WORKFLOWETAPAPERFILACESSO.empresa_id) ON ORGANIZACAO.organizacao_id = WORKFLOWETAPAPERFILACESSO.organizacao_id) "+   
							 " INNER JOIN WORKFLOW (NOLOCK) ON WORKFLOW.workflow_id = WORKFLOWETAPAPERFILACESSO.workflow_id "+
							 " INNER JOIN ETAPA (NOLOCK) ON WORKFLOWETAPAPERFILACESSO.etapa_id = ETAPA.etapa_id) "+
							 " INNER JOIN PERFIL (NOLOCK) ON WORKFLOWETAPAPERFILACESSO.perfil_id = PERFIL.perfil_id ";

	public WorkflowEtapaPerfilAcessoDao(Session session, ConnJDBC conexao) {

		super(session, WorkflowEtapaPerfilAcesso.class);
		this.conexao = conexao;

	}

	public Collection<WorkflowEtapaPerfilAcesso> buscaTodosWorkflowEtapaPerfilAcesso() {

		String sql = sqlWorkflowEtapaPerfilAcesso;
		
		sql += " ORDER BY WORKFLOW.nome, ETAPA.nome, PERFIL.nome ";

		this.conn = this.conexao.getConexao();

		Collection<WorkflowEtapaPerfilAcesso> workflowEtapasPerfilAcesso = new ArrayList<WorkflowEtapaPerfilAcesso>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.rsWorkflowEtapaPerfilAcesso = this.stmt.executeQuery();

			while (rsWorkflowEtapaPerfilAcesso.next()) {
				
				getWorkflowEtapa(workflowEtapasPerfilAcesso);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsWorkflowEtapaPerfilAcesso, stmt, conn);

		return workflowEtapasPerfilAcesso;
	}

	public Collection<WorkflowEtapaPerfilAcesso> buscaWorkflowEtapaPerfilAcessoPorEmpresaOrganizacaoPerfil(Long empresa_id, Long organizacao_id, Long workflowetapa_id) {
		
		String sql = sqlWorkflowEtapaPerfilAcesso;

		if (empresa_id != null)
			sql += " WHERE WORKFLOWETAPAPERFILACESSO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOWETAPAPERFILACESSO.organizacao_id = ?";
		if (workflowetapa_id != null)
			sql += " AND WORKFLOWETAPAPERFILACESSO.workflowetapa_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<WorkflowEtapaPerfilAcesso> workflowEtapasPerfilAcesso = new ArrayList<WorkflowEtapaPerfilAcesso>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, workflowetapa_id);

			this.rsWorkflowEtapaPerfilAcesso = this.stmt.executeQuery();

			while (rsWorkflowEtapaPerfilAcesso.next()) {

				getWorkflowEtapa(workflowEtapasPerfilAcesso);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			this.conexao.closeConnection(rsWorkflowEtapaPerfilAcesso, stmt, conn);
		}
		return workflowEtapasPerfilAcesso;
	}
	
public WorkflowEtapaPerfilAcesso buscaWorkflowEtapaPerfilAcessoPorEmpresaOrganizacaoWorkflowEtapaPerfil(Long empresa_id, Long organizacao_id, Long workflow_id, Long etapa_id, Long perfil_id) {

		String sql = sqlWorkflowEtapaPerfilAcesso;

		if (empresa_id != null)
			sql += " WHERE WORKFLOWETAPAPERFILACESSO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOWETAPAPERFILACESSO.organizacao_id = ?";
		if (workflow_id != null)
			sql += " AND WORKFLOWETAPAPERFILACESSO.workflow_id = ?";
		if (etapa_id != null)
			sql += " AND WORKFLOWETAPAPERFILACESSO.etapa_id = ?";
		if (perfil_id != null)
			sql += " AND WORKFLOWETAPAPERFILACESSO.perfil_id = ?";

		this.conn = this.conexao.getConexao();
		
		WorkflowEtapaPerfilAcesso workflowEtapaPerfilAcesso = null;
		
		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, workflow_id);
			this.stmt.setLong(4, etapa_id);
			this.stmt.setLong(5, perfil_id);
			
			this.rsWorkflowEtapaPerfilAcesso = this.stmt.executeQuery();
			
			while (rsWorkflowEtapaPerfilAcesso.next()) {

				workflowEtapaPerfilAcesso = new WorkflowEtapaPerfilAcesso();

				Perfil perfil = new Perfil();
				perfil.setPerfil_id(rsWorkflowEtapaPerfilAcesso.getLong("perfil_id"));

				workflowEtapaPerfilAcesso.setPerfil(perfil);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsWorkflowEtapaPerfilAcesso, stmt, conn);
		
		return workflowEtapaPerfilAcesso;
	}

	public void insert(WorkflowEtapaPerfilAcesso workflowEtapaPerfilAcesso) throws SQLException {

		String sql = "INSERT INTO WORKFLOWETAPAPERFILACESSO (empresa_id, organizacao_id, workflow_id, etapa_id, perfil_id, isactive, isleituraescrita,isupload) VALUES (?,?,?,?,?,?,?,?)";

		this.conn = this.conexao.getConexao();

		try {

			this.conn.setAutoCommit(false);

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, workflowEtapaPerfilAcesso.getEmpresa().getEmpresa_id());
			this.stmt.setLong(2, workflowEtapaPerfilAcesso.getOrganizacao().getOrganizacao_id());
			this.stmt.setLong(3, workflowEtapaPerfilAcesso.getWorkflow().getWorkflow_id());
			this.stmt.setLong(4, workflowEtapaPerfilAcesso.getEtapa().getEtapa_id());
			this.stmt.setLong(5, workflowEtapaPerfilAcesso.getPerfil().getPerfil_id());
			this.stmt.setBoolean(6, workflowEtapaPerfilAcesso.getIsActive());
			this.stmt.setBoolean(7, workflowEtapaPerfilAcesso.getIsLeituraEscrita());
			this.stmt.setBoolean(8, workflowEtapaPerfilAcesso.getIsUpload());

			this.stmt.executeUpdate();
			this.conn.commit();

		} catch (SQLException e) {
			
			this.conn.rollback();
			throw e;

		} finally {

			this.conn.setAutoCommit(true);
			this.conexao.closeConnection(stmt, conn);
		}
		
	}
	
	private void getWorkflowEtapa(Collection<WorkflowEtapaPerfilAcesso> workflowEtapasPerfilAcesso)throws SQLException {

		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();
		WorkflowEtapaPerfilAcesso workflowEtapaPerfilAcesso = new WorkflowEtapaPerfilAcesso();
		Workflow workflow = new Workflow();
		Etapa etapa = new Etapa();
		Perfil perfil = new Perfil();
		
		empresa.setEmpresa_id(rsWorkflowEtapaPerfilAcesso.getLong("empresa_id"));
		empresa.setNome(rsWorkflowEtapaPerfilAcesso.getString("empresa_nome"));

		organizacao.setOrganizacao_id(rsWorkflowEtapaPerfilAcesso.getLong("organizacao_id"));
		organizacao.setNome(rsWorkflowEtapaPerfilAcesso.getString("organizacao_nome"));

		workflow.setWorkflow_id(rsWorkflowEtapaPerfilAcesso.getLong("workflow_id"));
		workflow.setNome(rsWorkflowEtapaPerfilAcesso.getString("workflow_nome"));

		etapa.setEtapa_id(rsWorkflowEtapaPerfilAcesso.getLong("etapa_id"));
		etapa.setNome(rsWorkflowEtapaPerfilAcesso.getString("etapa_nome"));

		perfil.setPerfil_id(rsWorkflowEtapaPerfilAcesso.getLong("perfil_id"));
		perfil.setNome(rsWorkflowEtapaPerfilAcesso.getString("perfil_nome"));

		workflowEtapaPerfilAcesso.setEmpresa(empresa);
		workflowEtapaPerfilAcesso.setOrganizacao(organizacao);
		workflowEtapaPerfilAcesso.setEtapa(etapa);
		workflowEtapaPerfilAcesso.setWorkflow(workflow);
		workflowEtapaPerfilAcesso.setPerfil(perfil);
		workflowEtapaPerfilAcesso.setIsActive(rsWorkflowEtapaPerfilAcesso.getBoolean("isactive"));
		workflowEtapaPerfilAcesso.setIsLeituraEscrita(rsWorkflowEtapaPerfilAcesso.getBoolean("isleituraescrita"));
		

		workflowEtapasPerfilAcesso.add(workflowEtapaPerfilAcesso);
	}
}
