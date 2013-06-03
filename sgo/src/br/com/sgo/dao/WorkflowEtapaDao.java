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
	
	private final String sqlWorkflowEtapa = "SELECT WORKFLOWETAPA.empresa_id, EMPRESA.nome as empresa_nome, WORKFLOWETAPA.organizacao_id, ORGANIZACAO.nome AS organizacao_nome "+
							", WORKFLOWETAPA.etapa_id, ETAPA.nome AS etapa_nome, WORKFLOWETAPA.workflow_id, WORKFLOW.nome AS workflow_nome "+
							", WORKFLOWETAPA.isactive, WORKFLOWETAPA.isleituraescrita "+
							" FROM ((ORGANIZACAO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) INNER JOIN WORKFLOWETAPA (NOLOCK) "+
							" ON EMPRESA.empresa_id = WORKFLOWETAPA.empresa_id) ON ORGANIZACAO.organizacao_id = WORKFLOWETAPA.organizacao_id) "+ 
							" INNER JOIN WORKFLOW (NOLOCK) ON WORKFLOWETAPA.workflow_id = WORKFLOW.workflow_id) "+
							" INNER JOIN ETAPA (NOLOCK) ON WORKFLOWETAPA.etapa_id = ETAPA.etapa_id ";

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

				getWorkflowEtapa(workflowEtapas);

			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
		this.conexao.closeConnection(rsWorkflowEtapa, stmt, conn);
		
		return workflowEtapas;
	}

	public Collection<WorkflowEtapa> buscaAllWorkflowEtapaByEmpresaOrganizacao(Long empresa_id, Long organizacao_id) {
		
		String sql = sqlWorkflowEtapa;

		if (empresa_id != null)
			sql += " WHERE WORKFLOWETAPA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOWETAPA.organizacao_id = ?";

		this.conn = this.conexao.getConexao();
		
		Collection<WorkflowEtapa> workflowEtapas = new ArrayList<WorkflowEtapa>();

		try {
			
			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsWorkflowEtapa = this.stmt.executeQuery();
			
			while (rsWorkflowEtapa.next()) {

				getWorkflowEtapa(workflowEtapas);

			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
		this.conexao.closeConnection(rsWorkflowEtapa, stmt, conn);
		
		return workflowEtapas;
	}

	public Collection<WorkflowEtapa> buscaEtapaByEmpresaOrganizacaoWorkflow(Long empresa_id, Long organizacao_id, Long workflow_id) {
		
		String sql = sqlWorkflowEtapa;

		if (empresa_id != null)
			sql += " WHERE WORKFLOWETAPA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOWETAPA.organizacao_id = ?";
		if (workflow_id != null)
			sql += " AND WORKFLOWETAPA.workflow_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<WorkflowEtapa> workflowEtapas = new ArrayList<WorkflowEtapa>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, workflow_id);

			this.rsWorkflowEtapa = this.stmt.executeQuery();

			while (rsWorkflowEtapa.next()) {

				getWorkflowEtapa(workflowEtapas);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsWorkflowEtapa, stmt, conn);
		return workflowEtapas;
	}

	public WorkflowEtapa buscaWorkflowEtapaPorEmpresaOrganizacaoWorkflowEtapa(Long empresa_id, Long organizacao_id, Long workflow_id, Long etapa_id ) {
		
		String sql = sqlWorkflowEtapa;

		if (empresa_id != null)
			sql += " WHERE WORKFLOWETAPA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOWETAPA.organizacao_id = ?";
		if (workflow_id != null)
			sql += " AND WORKFLOWETAPA.workflow_id = ?";
		if (etapa_id != null)
			sql += " AND WORKFLOWETAPA.etapa_id = ?";

		this.conn = this.conexao.getConexao();
		
		WorkflowEtapa workflowEtapa = null;
		
		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, workflow_id);
			this.stmt.setLong(4, etapa_id);
			
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

		String sql = "INSERT INTO WORKFLOWETAPA (empresa_id, organizacao_id, workflow_id, etapa_id, isactive, isleituraescrita) VALUES (?,?,?,?,?,?)";

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
	
	private void getWorkflowEtapa(Collection<WorkflowEtapa> workflowEtapas) throws SQLException {

		WorkflowEtapa workflowEtapa = new WorkflowEtapa();
		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();
		Workflow workflow = new Workflow();
		Etapa etapa = new Etapa();

		empresa.setEmpresa_id(rsWorkflowEtapa.getLong("empresa_id"));
		empresa.setNome(rsWorkflowEtapa.getString("empresa_nome"));

		organizacao.setOrganizacao_id(rsWorkflowEtapa.getLong("organizacao_id"));
		organizacao.setNome(rsWorkflowEtapa.getString("organizacao_nome"));

		workflow.setWorkflow_id(rsWorkflowEtapa.getLong("workflow_id"));
		workflow.setNome(rsWorkflowEtapa.getString("workflow_nome"));

		etapa.setEtapa_id(rsWorkflowEtapa.getLong("etapa_id"));
		etapa.setNome(rsWorkflowEtapa.getString("etapa_nome"));

		workflowEtapa.setEmpresa(empresa);
		workflowEtapa.setOrganizacao(organizacao);
		workflowEtapa.setWorkflow(workflow);
		workflowEtapa.setEtapa(etapa);
		workflowEtapa.setIsActive(rsWorkflowEtapa.getBoolean("isactive")); 
		workflowEtapa.setIsLeituraEscrita(rsWorkflowEtapa.getBoolean("isleituraescrita"));

		workflowEtapas.add(workflowEtapa);

	}

}
