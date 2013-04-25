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
import br.com.sgo.modelo.WorkflowEtapa;

@Component
public class WorkflowEtapaDao extends Dao<WorkflowEtapa> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsWorkflowEtapa;

	private final String sqlWorkflowEtapa = "SELECT WORKFLOWETAPA.workflowetapa_id, WORKFLOWETAPA.nome, WORKFLOWETAPA.empresa_id " +
			" ,WORKFLOWETAPA.organizacao_id, WORKFLOWETAPA.workflow_id, WORKFLOWETAPA.ordemetapa, WORKFLOWETAPA.ispadrao" +
			", WORKFLOWETAPA.isactive FROM WORKFLOWETAPA (NOLOCK) ";
	
	private final String sqlWorkflowEtapas = "SELECT WORKFLOWETAPA.workflowetapa_id, WORKFLOWETAPA.nome as workflowetapa_nome " +
			", WORKFLOWETAPA.ordemetapa, WORKFLOWETAPA.ispadrao, WORKFLOWETAPA.isactive "+
	", WORKFLOWETAPA.workflow_id, WORKFLOW.nome as workflow_nome, WORKFLOWETAPA.empresa_id, EMPRESA.nome as empresa_nome " +
	", WORKFLOWETAPA.organizacao_id, ORGANIZACAO.nome as organizacao_nome FROM (ORGANIZACAO (NOLOCK) INNER JOIN (WORKFLOW (NOLOCK) "+
	" INNER JOIN WORKFLOWETAPA (NOLOCK) ON WORKFLOW.workflow_id = WORKFLOWETAPA.workflow_id) ON ORGANIZACAO.organizacao_id = WORKFLOWETAPA.organizacao_id) "+ 
	" INNER JOIN EMPRESA (NOLOCK) ON WORKFLOWETAPA.empresa_id = EMPRESA.empresa_id ";

	public WorkflowEtapaDao(Session session, ConnJDBC conexao) {
		super(session, WorkflowEtapa.class);
		this.conexao = conexao;
	}

	public Collection<WorkflowEtapa> buscaTodosWorkflowEtapa() {

		String sql = sqlWorkflowEtapas;

		this.conn = this.conexao.getConexao();

		Collection<WorkflowEtapa> workflowEtapas = new ArrayList<WorkflowEtapa>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.rsWorkflowEtapa = this.stmt.executeQuery();

			while (rsWorkflowEtapa.next()) {

				WorkflowEtapa workflowEtapa = new WorkflowEtapa();

				Empresa e = new Empresa();
				Organizacao o = new Organizacao();
				Workflow workflow = new Workflow();

				e.setEmpresa_id(rsWorkflowEtapa.getLong("empresa_id"));
				e.setNome(rsWorkflowEtapa.getString("empresa_nome"));

				o.setOrganizacao_id(rsWorkflowEtapa.getLong("organizacao_id"));
				o.setNome(rsWorkflowEtapa.getString("organizacao_nome"));

				workflow.setWorkflow_id(rsWorkflowEtapa.getLong("workflow_id"));
				workflow.setNome(rsWorkflowEtapa.getString("workflow_nome"));

				workflowEtapa.setEmpresa(e);
				workflowEtapa.setOrganizacao(o);
				workflowEtapa.setWorkflow(workflow);

				workflowEtapa.setWorkflowEtapa_id(rsWorkflowEtapa.getLong("workflowetapa_id"));
				workflowEtapa.setNome(rsWorkflowEtapa.getString("workflowetapa_nome"));
				workflowEtapa.setOrdemEtapa(rsWorkflowEtapa.getInt("ordemetapa"));
				workflowEtapa.setIsPadrao(rsWorkflowEtapa.getBoolean("ispadrao"));
				workflowEtapa.setIsActive(rsWorkflowEtapa.getBoolean("isactive"));

				workflowEtapas.add(workflowEtapa);

			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		this.conexao.closeConnection(rsWorkflowEtapa, stmt, conn);
		return workflowEtapas;
	}

	public WorkflowEtapa buscaWorkflowEtapaPorEmpresaOrganizacaoTipoworflow(Long empresa_id, Long organizacao_id, Long tipoworkflow_id) {
		
		String sql = sqlWorkflowEtapa;

		if (empresa_id != null)
			sql += " WHERE WORKFLOWETAPA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOWETAPA.organizacao_id = ?";
		if (tipoworkflow_id != null)
			sql += " AND (WORKFLOWETAPA.workflowetapa_id = ?)";

		this.conn = this.conexao.getConexao();
		WorkflowEtapa workflowetapa = null;
		try {
			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, tipoworkflow_id);
			
			this.rsWorkflowEtapa = this.stmt.executeQuery();
			while (rsWorkflowEtapa.next()) {
				workflowetapa = new WorkflowEtapa();
				workflowetapa.setWorkflowEtapa_id(rsWorkflowEtapa.getLong("workflowetapa_id"));
				workflowetapa.setNome(rsWorkflowEtapa.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsWorkflowEtapa, stmt, conn);
		return workflowetapa;
	}

	public WorkflowEtapa buscaWorkflowPorEmpresaOrganizacaoWorflowEtapaNome(Long empresa_id, Long organizacao_id, Long workflow_id, String nome ) {
		
		String sql = sqlWorkflowEtapa;

		if (empresa_id != null)
			sql += " WHERE WORKFLOWETAPA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOWETAPA.organizacao_id = ?";
		if (workflow_id != null)
			sql += " AND WORKFLOWETAPA.workflow_id = ?";
		if (nome != null)
			sql += " AND WORKFLOWETAPA.nome like ?";

		this.conn = this.conexao.getConexao();
		
		WorkflowEtapa workflowEtapa = null;
		
		try {
			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, workflow_id);
			this.stmt.setString(4, "%" + nome + "%");
			
			this.rsWorkflowEtapa = this.stmt.executeQuery();
			
			while (rsWorkflowEtapa.next()) {
				workflowEtapa = new WorkflowEtapa();
				workflowEtapa.setWorkflowEtapa_id(rsWorkflowEtapa.getLong("workflowetapa_id"));
				workflowEtapa.setNome(rsWorkflowEtapa.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsWorkflowEtapa, stmt, conn);
		return workflowEtapa;
	}

	public WorkflowEtapa buscaWorkflowEtapaPorNome(Long empresa, Long organizacao, String nome) {
		String sql = sqlWorkflowEtapa;
		
		if (empresa != null)
			sql += " WHERE WORKFLOWETAPA.empresa_id = ?";
		if (organizacao != null)
			sql += " AND WORKFLOWETAPA.organizacao_id = ?";
		if (nome != null)
			sql += " AND (WORKFLOWETAPA.nome like ?)";
		
		this.conn = this.conexao.getConexao();
		
		WorkflowEtapa workflowetapa = null;
		
		try {
			
			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa);
			this.stmt.setLong(2, organizacao);
			this.stmt.setString(3, "%" + nome + "%");
			
			this.rsWorkflowEtapa = this.stmt.executeQuery();
			
			while (rsWorkflowEtapa.next()) {
				workflowetapa = new WorkflowEtapa();
				workflowetapa.setWorkflowEtapa_id(rsWorkflowEtapa.getLong("workflowetapa_id"));
				workflowetapa.setNome(rsWorkflowEtapa.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsWorkflowEtapa, stmt, conn);
		return workflowetapa;
	}
	
	public Collection<WorkflowEtapa> buscaWorkflowsPorEmpresaOrganizacaoNome(Long empresa_id, Long organizacao_id, String nome) {
		
		String sql = sqlWorkflowEtapa;
		
		if (empresa_id != null)
			sql += " WHERE WORKFLOWETAPA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOWETAPA.organizacao_id = ?";
		if (nome != null)
			sql += " AND WORKFLOWETAPA.nome like ?";
		
		this.conn = this.conexao.getConexao();

		Collection<WorkflowEtapa> workflowsEtapa = new ArrayList<WorkflowEtapa>();
		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");
			
			this.rsWorkflowEtapa = this.stmt.executeQuery();

			while (rsWorkflowEtapa.next()) {
				WorkflowEtapa workflowEtapa = new WorkflowEtapa();

				workflowEtapa.setWorkflowEtapa_id(rsWorkflowEtapa.getLong("workflowetapa_id"));
				workflowEtapa.setNome(rsWorkflowEtapa.getString("nome"));

				workflowsEtapa.add(workflowEtapa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsWorkflowEtapa, stmt, conn);

		return workflowsEtapa;

	}

}