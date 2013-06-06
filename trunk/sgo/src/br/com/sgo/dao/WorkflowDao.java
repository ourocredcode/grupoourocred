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
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Workflow;

@Component
public class WorkflowDao extends Dao<Workflow> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsWorkflow;

	private final String sqlWorkflow = "SELECT WORKFLOW.workflow_id, WORKFLOW.nome, WORKFLOW.empresa_id, WORKFLOW.organizacao_id, WORKFLOW.tipoworkflow_id"
			+ " FROM ((WORKFLOW (NOLOCK) INNER JOIN TIPOWORKFLOW (NOLOCK) ON WORKFLOW.tipoworkflow_id = TIPOWORKFLOW.tipoworkflow_id)"
			+ " INNER JOIN EMPRESA (NOLOCK) ON WORKFLOW.empresa_id = EMPRESA.empresa_id) INNER JOIN ORGANIZACAO (NOLOCK) ON WORKFLOW.organizacao_id = ORGANIZACAO.organizacao_id ";

	private final String sqlWorkflows = "SELECT WORKFLOW.workflow_id, WORKFLOW.nome as workflow_nome, WORKFLOW.empresa_id, EMPRESA.nome as empresa_nome "+
			", WORKFLOW.organizacao_id, ORGANIZACAO.nome as organizacao_nome, WORKFLOW.tipoworkflow_id, TIPOWORKFLOW.nome as tipoworkflow_nome "+
			", WORKFLOW.ispadrao, WORKFLOW.isactive FROM (ORGANIZACAO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) "+
			" INNER JOIN WORKFLOW (NOLOCK) ON EMPRESA.empresa_id = WORKFLOW.empresa_id) ON ORGANIZACAO.organizacao_id = WORKFLOW.organizacao_id) "+ 
			" INNER JOIN TIPOWORKFLOW (NOLOCK) ON WORKFLOW.tipoworkflow_id = TIPOWORKFLOW.tipoworkflow_id ";

	
	public WorkflowDao(Session session, ConnJDBC conexao) {
		super(session, Workflow.class);
		this.conexao = conexao;
	}

	public Collection<Workflow> buscaTodosWorkflow() {
		
		String sql = sqlWorkflows;
		
		this.conn = this.conexao.getConexao();
		
		Collection<Workflow> workflows = new ArrayList<Workflow>();
		
		try {
			
			this.stmt = conn.prepareStatement(sql);
			
			this.rsWorkflow = this.stmt.executeQuery();
			
			while (rsWorkflow.next()) {

				getWorkflows(workflows);

			}
		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsWorkflow, stmt, conn);
		return workflows;

	}

	public Workflow buscaWorkflowPorEmpresaOrganizacaoTipoworflow(Long empresa_id, Long organizacao_id, Long tipoworkflow_id) {

		String sql = sqlWorkflow;

		if (empresa_id != null)
			sql += " WHERE WORKFLOW.empresa_id = ?";
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

	public Workflow buscaWorkflowPorEmpresaOrganizacaoTipoworflowNome(Long empresa_id, Long organizacao_id, Long tipoworkflow_id, String nome ) {

		String sql = sqlWorkflow;

		if (empresa_id != null)
			sql += " WHERE WORKFLOW.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOW.organizacao_id = ?";
		if (tipoworkflow_id != null)
			sql += " AND WORKFLOW.tipoworkflow_id = ?";
		if (nome != null)
			sql += " AND WORKFLOW.nome like ?";

		this.conn = this.conexao.getConexao();

		Workflow workflow = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, tipoworkflow_id);
			this.stmt.setString(4, "%" + nome + "%");

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

	public Collection<Workflow> buscaWorkflowsPorNome(Long empresa_id, Long organizacao_id, String nome) {
		
		String sql = sqlWorkflows;
		
		if (empresa_id != null)
			sql += " WHERE WORKFLOW.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOW.organizacao_id = ?";
		if (nome != null)
			sql += " AND WORKFLOW.nome like ?";
		
		this.conn = this.conexao.getConexao();

		Collection<Workflow> workflows = new ArrayList<Workflow>();

		if (workflows!=null){

			try {

				this.stmt = conn.prepareStatement(sql);

				this.stmt.setLong(1, empresa_id);				
				this.stmt.setLong(2, organizacao_id);				
				this.stmt.setString(3, "%" + nome + "%");

				this.rsWorkflow = this.stmt.executeQuery();

				while (rsWorkflow.next()) {

					getWorkflows(workflows);

				}

			} catch (SQLException e) {

				e.printStackTrace();

			}

			this.conexao.closeConnection(rsWorkflow, stmt, conn);

		}

		return workflows;
	}

	public Collection<Workflow> buscaWorkflowsToWorkflowEtapaPerfilByEmpresaOrganizacao(Long empresa_id, Long organizacao_id) {

		String sql = " SELECT DISTINCT(WORKFLOWPERFILACESSO.workflow_id), WORKFLOW.nome AS workflow_nome "+
				", WORKFLOWPERFILACESSO.empresa_id, EMPRESA.nome AS empresa_nome, WORKFLOWPERFILACESSO.organizacao_id, ORGANIZACAO.nome AS organizacao_nome "+
				" FROM ((WORKFLOWPERFILACESSO (NOLOCK) INNER JOIN WORKFLOW (NOLOCK) ON WORKFLOWPERFILACESSO.workflow_id = WORKFLOW.workflow_id) "+
				" INNER JOIN EMPRESA (NOLOCK) ON WORKFLOWPERFILACESSO.empresa_id = EMPRESA.empresa_id) "+
				" INNER JOIN ORGANIZACAO (NOLOCK) ON WORKFLOWPERFILACESSO.organizacao_id = ORGANIZACAO.organizacao_id ";

		if (empresa_id != null)
			sql += " WHERE WORKFLOWPERFILACESSO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOWPERFILACESSO.organizacao_id = ?";

		sql += " ORDER BY WORKFLOW.nome";

		this.conn = this.conexao.getConexao();

		Collection<Workflow> workflows = new ArrayList<Workflow>();

		if (workflows!=null){

			try {

				this.stmt = conn.prepareStatement(sql);

				this.stmt.setLong(1, empresa_id);				
				this.stmt.setLong(2, organizacao_id);

				this.rsWorkflow = this.stmt.executeQuery();

				while (rsWorkflow.next()) {

					getWorkflows(workflows);

				}

			} catch (SQLException e) {

				e.printStackTrace();

			}

			this.conexao.closeConnection(rsWorkflow, stmt, conn);

		}
		return workflows;
	}
	
	public Collection<Workflow> buscaWorkflowsByEmpresaOrganizacao(Long empresa_id, Long organizacao_id) {

		String sql = sqlWorkflows;

		if (empresa_id != null)
			sql += " WHERE WORKFLOW.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOW.organizacao_id = ?";

		sql += " ORDER BY WORKFLOW.empresa_id, WORKFLOW.organizacao_id, WORKFLOW.nome";

		this.conn = this.conexao.getConexao();

		Collection<Workflow> workflows = new ArrayList<Workflow>();

		if (workflows!=null){

			try {

				this.stmt = conn.prepareStatement(sql);

				this.stmt.setLong(1, empresa_id);				
				this.stmt.setLong(2, organizacao_id);

				this.rsWorkflow = this.stmt.executeQuery();

				while (rsWorkflow.next()) {

					getWorkflows(workflows);

				}

			} catch (SQLException e) {

				e.printStackTrace();

			}

			this.conexao.closeConnection(rsWorkflow, stmt, conn);

		}
		return workflows;
	}

	public Workflow buscaWorkflowPorNome(Long empresa, Long organizacao, String nome) {

		String sql = sqlWorkflow;

		if (empresa != null)
			sql += " WHERE WORKFLOW.empresa_id = ?";
		if (organizacao != null)
			sql += " AND WORKFLOW.organizacao_id = ?";
		if (nome != null)
			sql += " AND WORKFLOW.nome like ?";

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
	
	private void getWorkflows(Collection<Workflow> workflows) throws SQLException {

		Workflow workflow = new Workflow();
		Empresa e = new Empresa();
		Organizacao o = new Organizacao();

		e.setEmpresa_id(rsWorkflow.getLong("empresa_id"));
		e.setNome(rsWorkflow.getString("empresa_nome"));

		o.setOrganizacao_id(rsWorkflow.getLong("organizacao_id"));
		o.setNome(rsWorkflow.getString("organizacao_nome"));

		workflow.setWorkflow_id(rsWorkflow.getLong("workflow_id"));
		workflow.setNome(rsWorkflow.getString("workflow_nome"));

		workflow.setEmpresa(e);
		workflow.setOrganizacao(o);

		workflows.add(workflow);

	}
}