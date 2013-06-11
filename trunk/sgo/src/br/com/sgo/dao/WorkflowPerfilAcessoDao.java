package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Perfil;
import br.com.sgo.modelo.Workflow;
import br.com.sgo.modelo.WorkflowPerfilAcesso;

@Component
public class WorkflowPerfilAcessoDao extends Dao<WorkflowPerfilAcesso> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsWorkflowPerfilAcesso;
	
	private final String sqlWorkflowPerfilAcesso = "SELECT WORKFLOWPERFILACESSO.empresa_id, EMPRESA.nome as empresa_nome, "
			+ " WORKFLOWPERFILACESSO.organizacao_id, ORGANIZACAO.nome as organizacao_nome, "
			+ " WORKFLOWPERFILACESSO.perfil_id, PERFIL.nome as perfil_nome, "
			+ " WORKFLOWPERFILACESSO.workflow_id, WORKFLOW.nome as workflow_nome "
			+ " FROM ((PERFIL (NOLOCK) INNER JOIN (WORKFLOW (NOLOCK) "
			+ " INNER JOIN WORKFLOWPERFILACESSO (NOLOCK) ON WORKFLOW.workflow_id = WORKFLOWPERFILACESSO.workflow_id) ON PERFIL.perfil_id = WORKFLOWPERFILACESSO.perfil_id) "
			+ " INNER JOIN EMPRESA (NOLOCK) ON WORKFLOWPERFILACESSO.empresa_id = EMPRESA.empresa_id) "
			+ " INNER JOIN ORGANIZACAO (NOLOCK) ON WORKFLOWPERFILACESSO.organizacao_id = ORGANIZACAO.organizacao_id ";

	public WorkflowPerfilAcessoDao(Session session, ConnJDBC conexao) {
		super(session, WorkflowPerfilAcesso.class);
		this.conexao = conexao;
	}

	public Collection<WorkflowPerfilAcesso> buscaTodosWorkflowPerfilAcesso() {

		String sql = sqlWorkflowPerfilAcesso;

		this.conn = this.conexao.getConexao();

		Collection<WorkflowPerfilAcesso> workflowPerfisAcesso = new ArrayList<WorkflowPerfilAcesso>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.rsWorkflowPerfilAcesso = this.stmt.executeQuery();

			while (rsWorkflowPerfilAcesso.next()) {

				Empresa empresa = new Empresa();
				empresa.setEmpresa_id(rsWorkflowPerfilAcesso.getLong("empresa_id"));
				empresa.setNome(rsWorkflowPerfilAcesso.getString("empresa_nome"));

				Organizacao organizacao = new Organizacao();
				organizacao.setOrganizacao_id(rsWorkflowPerfilAcesso.getLong("organizacao_id"));
				organizacao.setNome(rsWorkflowPerfilAcesso.getString("organizacao_nome"));

				Workflow workflow = new Workflow();
				workflow.setWorkflow_id(rsWorkflowPerfilAcesso.getLong("workflow_id"));
				workflow.setNome(rsWorkflowPerfilAcesso.getString("workflow_nome"));

				Perfil perfil = new Perfil();
				perfil.setPerfil_id(rsWorkflowPerfilAcesso.getLong("perfil_id"));
				perfil.setNome(rsWorkflowPerfilAcesso.getString("perfil_nome"));

				WorkflowPerfilAcesso workflowPerfilAcesso = new WorkflowPerfilAcesso();

				workflowPerfilAcesso.setEmpresa(empresa);
				workflowPerfilAcesso.setOrganizacao(organizacao);
				workflowPerfilAcesso.setWorkflow(workflow);
				workflowPerfilAcesso.setPerfil(perfil);

				workflowPerfisAcesso.add(workflowPerfilAcesso);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsWorkflowPerfilAcesso, stmt, conn);

		return workflowPerfisAcesso;
	}

	public Collection<WorkflowPerfilAcesso> buscaWorkflowPerfilAcessoPorEmpresaOrganizacaoPerfil(Long empresa_id, Long organizacao_id, Long workflow_id) {

		String sql = sqlWorkflowPerfilAcesso;

		if (empresa_id != null)
			sql += " WHERE WORKFLOWPERFILACESSO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOWPERFILACESSO.organizacao_id = ?";
		if (workflow_id != null)
			sql += " AND WORKFLOWPERFILACESSO.workflow_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<WorkflowPerfilAcesso> workflowPerfisAcesso = new ArrayList<WorkflowPerfilAcesso>();

		try {
			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, workflow_id);

			this.rsWorkflowPerfilAcesso = this.stmt.executeQuery();
			while (rsWorkflowPerfilAcesso.next()) {

				Empresa empresa = new Empresa();
				empresa.setEmpresa_id(rsWorkflowPerfilAcesso.getLong("empresa_id"));
				empresa.setNome(rsWorkflowPerfilAcesso.getString("empresa_nome"));

				Organizacao organizacao = new Organizacao();
				organizacao.setOrganizacao_id(rsWorkflowPerfilAcesso.getLong("organizacao_id"));
				organizacao.setNome(rsWorkflowPerfilAcesso.getString("organizacao_nome"));

				Workflow workflow = new Workflow();
				workflow.setWorkflow_id(rsWorkflowPerfilAcesso.getLong("workflow_id"));
				workflow.setNome(rsWorkflowPerfilAcesso.getString("workflow_nome"));

				Perfil perfil = new Perfil();
				perfil.setPerfil_id(rsWorkflowPerfilAcesso.getLong("perfil_id"));
				perfil.setNome(rsWorkflowPerfilAcesso.getString("perfil_nome"));

				WorkflowPerfilAcesso workflowperfilacesso = new WorkflowPerfilAcesso();
				workflowperfilacesso.setEmpresa(empresa);
				workflowperfilacesso.setOrganizacao(organizacao);
				workflowperfilacesso.setWorkflow(workflow);
				workflowperfilacesso.setPerfil(perfil);

				workflowPerfisAcesso.add(workflowperfilacesso);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsWorkflowPerfilAcesso, stmt, conn);
		return workflowPerfisAcesso;
	}

public WorkflowPerfilAcesso buscaWorkflowPerfilAcessoPorEmpresaOrganizacaoWorkflowPerfil(Long empresa_id, Long organizacao_id, Long workflow_id, Long perfil_id ) {

		String sql = sqlWorkflowPerfilAcesso;

		if (empresa_id != null)
			sql += " WHERE WORKFLOWPERFILACESSO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOWPERFILACESSO.organizacao_id = ?";
		if (workflow_id != null)
			sql += " AND WORKFLOWPERFILACESSO.workflow_id = ?";
		if (perfil_id != null)
			sql += " AND WORKFLOWPERFILACESSO.perfil_id = ?";

		this.conn = this.conexao.getConexao();

		WorkflowPerfilAcesso workflowPerfilAcesso = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, workflow_id);
			this.stmt.setLong(4, perfil_id);

			this.rsWorkflowPerfilAcesso = this.stmt.executeQuery();

			while (rsWorkflowPerfilAcesso.next()) {
				workflowPerfilAcesso = new WorkflowPerfilAcesso();

				Perfil perfil = new Perfil();
				perfil.setPerfil_id(rsWorkflowPerfilAcesso.getLong("perfil_id"));

				workflowPerfilAcesso.setPerfil(perfil);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsWorkflowPerfilAcesso, stmt, conn);

		return workflowPerfilAcesso;
	}


	public void insert(WorkflowPerfilAcesso workflowPerfilAcesso) throws SQLException {

		String sql = "INSERT INTO WORKFLOWPERFILACESSO (empresa_id, organizacao_id,workflow_id, perfil_id, created, createdby," +
				"updated,updatedby, isactive, isleituraescrita) VALUES (?,?,?,?,?,?,?,?,?,?)";

		this.conn = this.conexao.getConexao();

		try {
			this.conn.setAutoCommit(false);

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, workflowPerfilAcesso.getEmpresa().getEmpresa_id());
			this.stmt.setLong(2, workflowPerfilAcesso.getOrganizacao().getOrganizacao_id());
			this.stmt.setLong(3, workflowPerfilAcesso.getWorkflow().getWorkflow_id());
			this.stmt.setLong(4, workflowPerfilAcesso.getPerfil().getPerfil_id());
			this.stmt.setTimestamp(5, new Timestamp(workflowPerfilAcesso.getCreated().getTimeInMillis()));
			this.stmt.setLong(6,workflowPerfilAcesso.getCreatedBy().getUsuario_id());
			this.stmt.setTimestamp(7, new Timestamp(workflowPerfilAcesso.getUpdated().getTimeInMillis()));
			this.stmt.setLong(8,workflowPerfilAcesso.getUpdatedBy().getUsuario_id());
			this.stmt.setBoolean(9, workflowPerfilAcesso.getIsActive());
			this.stmt.setBoolean(10, workflowPerfilAcesso.getIsLeituraEscrita());

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