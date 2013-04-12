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
import br.com.sgo.modelo.Workflow;

@Component
public class WorkflowDao extends Dao<Workflow> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsWorkflow;

	private final String sqlWorkflow = "SELECT WORKFLOW.workflow_id, WORKFLOW.nome, WORKFLOW.empresa_id, WORKFLOW.organizacao_id, WORKFLOW.tipoworkflow_id"
			+ "FROM ((WORKFLOW (NOLOCK) INNER JOIN TIPOWORKFLOW (NOLOCK) ON WORKFLOW.tipoworkflow_id = TIPOWORKFLOW.tipoworkflow_id)"
			+ " INNER JOIN EMPRESA (NOLOCK) ON WORKFLOW.empresa_id = EMPRESA.empresa_id) INNER JOIN ORGANIZACAO (NOLOCK) ON WORKFLOW.organizacao_id = ORGANIZACAO.organizacao_id";

	public WorkflowDao(Session session, ConnJDBC conexao) {
		super(session, Workflow.class);
		this.conexao = conexao;
	}

	public Collection<Workflow> buscaTodosWorkflow() {
		String sql = sqlWorkflow;
		this.conn = this.conexao.getConexao();
		Collection<Workflow> workflows = new ArrayList<Workflow>();
		try {
			this.stmt = conn.prepareStatement(sql);
			this.rsWorkflow = this.stmt.executeQuery();
			while (rsWorkflow.next()) {
				Workflow workflow = new Workflow();
				workflow.setWorkflow_id(rsWorkflow.getLong("workflow_id"));
				workflow.setNome(rsWorkflow.getString("nome"));
				workflows.add(workflow);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsWorkflow, stmt, conn);
		return workflows;
	}

	public Workflow buscaWorkflowPorEmpresaOrganizacaoTipoworflow(
			Long empresa_id, Long organizacao_id, Long tipoworkflow_id) {
		String sql = sqlWorkflow;

		if (empresa_id != null)
			sql += " AND WORKFLOW.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOW.organizacao_id = ?";
		if (tipoworkflow_id != null)
			sql += " AND (WORKFLOW.tipoworkflow_id = ?)";

		this.conn = this.conexao.getConexao();
		Workflow workflow = null;
		try {
			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, tipoworkflow_id);
			this.rsWorkflow = this.stmt.executeQuery();
			while (rsWorkflow.next()) {
				workflow = new Workflow();
				workflow.setWorkflow_id(rsWorkflow.getLong("workflow_id"));
				workflow.setNome(rsWorkflow.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsWorkflow, stmt, conn);
		return workflow;
	}

	public Workflow buscaWorkflowPorNome(Long empresa, Long organizacao,
			String nome) {
		String sql = sqlWorkflow;
		if (empresa != null)
			sql += " AND WORKFLOW.empresa_id = ?";
		if (organizacao != null)
			sql += " AND WORKFLOW.organizacao_id = ?";
		if (nome != null)
			sql += " AND (WORKFLOW.nome like ?)";
		this.conn = this.conexao.getConexao();
		Workflow workflow = null;
		try {
			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa);
			this.stmt.setLong(2, organizacao);
			this.stmt.setString(3, "%" + nome + "%");
			this.rsWorkflow = this.stmt.executeQuery();
			while (rsWorkflow.next()) {
				workflow = new Workflow();
				workflow.setWorkflow_id(rsWorkflow.getLong("workflow_id"));
				workflow.setNome(rsWorkflow.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsWorkflow, stmt, conn);
		return workflow;
	}

}