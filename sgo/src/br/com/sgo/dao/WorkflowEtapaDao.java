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
import br.com.sgo.modelo.Workflow;
import br.com.sgo.modelo.WorkflowEtapa;

@Component
public class WorkflowEtapaDao extends Dao<WorkflowEtapa> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsWorkflowEtapa;

	private final String sqlWorkflowEtapa = "SELECT WORKFLOWETAPA.empresa_id, EMPRESA.nome as empresa_nome, "
			+ " WORKFLOWETAPA.organizacao_id, ORGANIZACAO.nome as organizacao_nome, "
			+ " WORKFLOWETAPA.perfil_id, PERFIL.nome as perfil_nome, "
			+ " WORKFLOWETAPA.workflow_id, WORKFLOW.nome as workflow_nome "
			+ " FROM ((PERFIL (NOLOCK) INNER JOIN (WORKFLOW (NOLOCK) "
			+ " INNER JOIN WORKFLOWETAPA (NOLOCK) ON WORKFLOW.workflow_id = WORKFLOWETAPA.workflow_id) ON PERFIL.perfil_id = WORKFLOWETAPA.perfil_id) "
			+ " INNER JOIN EMPRESA (NOLOCK) ON WORKFLOWETAPA.empresa_id = EMPRESA.empresa_id) "
			+ " INNER JOIN ORGANIZACAO (NOLOCK) ON WORKFLOWETAPA.organizacao_id = ORGANIZACAO.organizacao_id ";

	public WorkflowEtapaDao(Session session, ConnJDBC conexao) {
		super(session, WorkflowEtapa.class);
		this.conexao = conexao;
	}

	public Collection<WorkflowEtapa> buscaTodosWorkflowEtapa() {
		
		String sql = sqlWorkflowEtapa;

		this.conn = this.conexao.getConexao();
		
		Collection<WorkflowEtapa> workflowEtapas = new ArrayList<WorkflowEtapa>();

		try {
			
			this.stmt = conn.prepareStatement(sql);
			
			this.rsWorkflowEtapa = this.stmt.executeQuery();
			
			while (rsWorkflowEtapa.next()) {

				Empresa empresa = new Empresa();
				empresa.setEmpresa_id(rsWorkflowEtapa.getLong("empresa_id"));
				empresa.setNome(rsWorkflowEtapa.getString("empresa_nome"));

				Organizacao organizacao = new Organizacao();
				organizacao.setOrganizacao_id(rsWorkflowEtapa.getLong("organizacao_id"));
				organizacao.setNome(rsWorkflowEtapa.getString("organizacao_nome"));

				Workflow workflow = new Workflow();
				workflow.setWorkflow_id(rsWorkflowEtapa.getLong("workflow_id"));
				workflow.setNome(rsWorkflowEtapa.getString("workflow_nome"));

				Etapa etapa = new Etapa();
				etapa.setEtapa_id(rsWorkflowEtapa.getLong("etapa_id"));
				etapa.setNome(rsWorkflowEtapa.getString("etapa_nome"));

				WorkflowEtapa workflowEtapa = new WorkflowEtapa();
				
				workflowEtapa.setEmpresa(empresa);
				workflowEtapa.setOrganizacao(organizacao);
				workflowEtapa.setWorkflow(workflow);
				workflowEtapa.setEtapa(etapa);

				workflowEtapas.add(workflowEtapa);
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
		this.conexao.closeConnection(rsWorkflowEtapa, stmt, conn);
		
		return workflowEtapas;
	}

	public Collection<WorkflowEtapa> buscaWorkflowEtapaPorEmpresaOrganizacaoPerfil(Long empresa_id, Long organizacao_id, Long workflow_id) {
		
		String sql = sqlWorkflowEtapa;

		if (empresa_id != null)
			sql += " WHERE WORKFLOWETAPA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOWETAPA.organizacao_id = ?";
		if (workflow_id != null)
			sql += " AND WORKFLOWETAPA.workflow_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<WorkflowEtapa> workflowPerfisAcesso = new ArrayList<WorkflowEtapa>();

		try {
			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, workflow_id);

			this.rsWorkflowEtapa = this.stmt.executeQuery();
			while (rsWorkflowEtapa.next()) {

				Empresa empresa = new Empresa();
				empresa.setEmpresa_id(rsWorkflowEtapa.getLong("empresa_id"));
				empresa.setNome(rsWorkflowEtapa.getString("empresa_nome"));

				Organizacao organizacao = new Organizacao();
				organizacao.setOrganizacao_id(rsWorkflowEtapa.getLong("organizacao_id"));
				organizacao.setNome(rsWorkflowEtapa.getString("organizacao_nome"));

				Workflow workflow = new Workflow();
				workflow.setWorkflow_id(rsWorkflowEtapa.getLong("workflow_id"));
				workflow.setNome(rsWorkflowEtapa.getString("workflow_nome"));

				Etapa etapa = new Etapa();
				etapa.setEtapa_id(rsWorkflowEtapa.getLong("etapa_id"));
				etapa.setNome(rsWorkflowEtapa.getString("etapa_nome"));

				WorkflowEtapa workflowEtapa = new WorkflowEtapa();
				
				workflowEtapa.setEmpresa(empresa);
				workflowEtapa.setOrganizacao(organizacao);
				workflowEtapa.setWorkflow(workflow);
				workflowEtapa.setEtapa(etapa);

				workflowPerfisAcesso.add(workflowEtapa);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsWorkflowEtapa, stmt, conn);
		return workflowPerfisAcesso;
	}

public WorkflowEtapa buscaWorkflowEtapaPorEmpresaOrganizacaoWorkflowPerfil(Long empresa_id, Long organizacao_id, Long workflow_id, Long perfil_id ) {
		
		String sql = sqlWorkflowEtapa;

		if (empresa_id != null)
			sql += " WHERE WORKFLOWETAPA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOWETAPA.organizacao_id = ?";
		if (workflow_id != null)
			sql += " AND WORKFLOWETAPA.workflow_id = ?";
		if (perfil_id != null)
			sql += " AND WORKFLOWETAPA.perfil_id = ?";

		this.conn = this.conexao.getConexao();
		
		WorkflowEtapa workflowEtapa = null;
		
		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, workflow_id);
			this.stmt.setLong(4, perfil_id);
			
			this.rsWorkflowEtapa = this.stmt.executeQuery();
			
			while (rsWorkflowEtapa.next()) {

				workflowEtapa = new WorkflowEtapa();				
				Etapa etapa = new Etapa();
				etapa.setEtapa_id(rsWorkflowEtapa.getLong("etapa_id"));
				workflowEtapa.setEtapa(etapa);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsWorkflowEtapa, stmt, conn);
		
		return workflowEtapa;
	}


	public void insert(WorkflowEtapa workflowEtapa) throws SQLException {

		String sql = "INSERT INTO WORKFLOWETAPA (empresa_id, organizacao_id,workflow_id, etapa_id, isactive, isleituraescrita) VALUES (?,?,?,?,?,?)";

		this.conn = this.conexao.getConexao();

		try {
			this.conn.setAutoCommit(false);

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, workflowEtapa.getEmpresa().getEmpresa_id());
			this.stmt.setLong(2, workflowEtapa.getOrganizacao().getOrganizacao_id());			
			this.stmt.setLong(3, workflowEtapa.getWorkflow().getWorkflow_id());
			this.stmt.setLong(4, workflowEtapa.getEtapa().getEtapa_id());
			this.stmt.setBoolean(5, workflowEtapa.getIsActive());
			this.stmt.setBoolean(6, workflowEtapa.getIsLeituraEscrita());

			this.stmt.executeUpdate();
			this.conn.commit();
			
		} catch (SQLException e) {
			this.conn.rollback();
			throw e;
		} finally {
			this.conn.setAutoCommit(true);
		}
		this.conexao.closeConnection(stmt, conn);
	}

}
