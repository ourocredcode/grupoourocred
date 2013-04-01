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
	
	private final String sqlWorkflow= "SELECT WORKFLOW.workflow_id, WORKFLOW.nome, WORKFLOW.empresa_id" +
			", WORKFLOW.organizacao_id FROM WORKFLOW (NOLOCK)";  
	
	public WorkflowDao(Session session, ConnJDBC conexao) {
		super(session, Workflow.class);
		this.conexao = conexao;
	}

	public Collection<Workflow> buscaAllWorkflow(){
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

	public Collection<Workflow> buscaWorkflowByNome(String nome){
		String sql = sqlWorkflow;
		if(nome != null)
			sql += 	" WHERE WORKFLOW.nome like ?";
		this.conn = this.conexao.getConexao();
		Collection<Workflow> workflows = new ArrayList<Workflow>();
		try {
			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setString(1,"%"+  nome + "%");
			this.rsWorkflow = this.stmt.executeQuery();
			Workflow workflow = new Workflow();
			while (rsWorkflow.next()) {
				workflow.setWorkflow_id(rsWorkflow.getLong("workflow_id"));
				workflow.setNome(rsWorkflow.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsWorkflow, stmt, conn);
		return workflows;
	}
	
	public Workflow buscaWorkflowByEmOrNo(Long empresa, Long organizacao, String nome){
		String sql = sqlWorkflow;
		if(empresa != null)
			sql += 	" AND WORKFLOW.empresa_id = ?";
		if(organizacao != null)
			sql += 	" AND WORKFLOW.organizacao_id = ?";		
		if(nome != null)
			sql += 	" AND (WORKFLOW.nome like ?)";
		this.conn = this.conexao.getConexao();		
		Workflow workflow = null;		
		try {
			this.stmt = conn.prepareStatement(sql);			
			this.stmt.setLong(1, empresa);
			this.stmt.setLong(2, organizacao);
			this.stmt.setString(3,"%"+  nome + "%");
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