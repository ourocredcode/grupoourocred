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
import br.com.sgo.modelo.WorkflowProdutoBanco;

@Component
public class WorkflowProdutoBancoDao extends Dao<WorkflowProdutoBanco> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsWorkflowProdutoBanco;

	private final String sqlWorkflowProdutoBanco = "SELECT WORKFLOWPRODUTOBANCO.workflowprodutobanco_id, WORKFLOWPRODUTOBANCO.empresa_id, WORKFLOWPRODUTOBANCO.organizacao_id, WORKFLOWPRODUTOBANCO.produto_id, WORKFLOWPRODUTOBANCO.banco_id, WORKFLOWPRODUTOBANCO.workflow_id, WORKFLOWPRODUTOBANCO.isworkflow, WORKFLOWPRODUTOBANCO.nome, WORKFLOWPRODUTOBANCO.isactive FROM WORKFLOWPRODUTOBANCO ";

	private final String sqlWorkflowsProdutoBanco = "SELECT WORKFLOWPRODUTOBANCO.workflowprodutobanco_id, WORKFLOWPRODUTOBANCO.isactive, WORKFLOWPRODUTOBANCO.isworkflow "+
							", WORKFLOWPRODUTOBANCO.empresa_id, EMPRESA.nome AS empresa_nome "+
							", WORKFLOWPRODUTOBANCO.organizacao_id, ORGANIZACAO.nome AS organizacao_nome "+
							", WORKFLOWPRODUTOBANCO.workflow_id, WORKFLOW.nome AS workflow_nome "+
							", WORKFLOWPRODUTOBANCO.produto_id, PRODUTO.nome AS produto_nome, WORKFLOWPRODUTOBANCO.banco_id, BANCO.nome AS banco_nome "+
							" FROM ((((WORKFLOWPRODUTOBANCO (NOLOCK) INNER JOIN EMPRESA (NOLOCK) ON WORKFLOWPRODUTOBANCO.empresa_id = EMPRESA.empresa_id) "+
							" INNER JOIN ORGANIZACAO (NOLOCK) ON WORKFLOWPRODUTOBANCO.organizacao_id = ORGANIZACAO.organizacao_id) "+
							" INNER JOIN BANCO (NOLOCK) ON WORKFLOWPRODUTOBANCO.banco_id = BANCO.banco_id) "+
							" INNER JOIN PRODUTO (NOLOCK) ON WORKFLOWPRODUTOBANCO.produto_id = PRODUTO.produto_id) "+
							" INNER JOIN WORKFLOW (NOLOCK) ON WORKFLOWPRODUTOBANCO.workflow_id = WORKFLOW.workflow_id ";

	public WorkflowProdutoBancoDao(Session session, ConnJDBC conexao) {

		super(session, WorkflowProdutoBanco.class);
		this.conexao = conexao;

	}

	public Collection<WorkflowProdutoBanco> buscaAllWorkflowProdutoBanco() {

		String sql = sqlWorkflowsProdutoBanco;

		this.conn = this.conexao.getConexao();

		Collection<WorkflowProdutoBanco> workflowsProdutoBanco = new ArrayList<WorkflowProdutoBanco>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.rsWorkflowProdutoBanco = this.stmt.executeQuery();

			while (rsWorkflowProdutoBanco.next()) {

				getWorkflowProdutoBanco(workflowsProdutoBanco);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsWorkflowProdutoBanco, stmt, conn);

		return workflowsProdutoBanco;
	}
	
	public Collection<WorkflowProdutoBanco> buscaAllWorkflowProdutoBancoByEmpresaOrganizacao(Long empresa_id, Long organizacao_id) {

		String sql = sqlWorkflowsProdutoBanco;

		if (empresa_id != null)
			sql += " WHERE WORKFLOWPRODUTOBANCO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOWPRODUTOBANCO.organizacao_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<WorkflowProdutoBanco> workflowsProdutoBanco = new ArrayList<WorkflowProdutoBanco>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsWorkflowProdutoBanco = this.stmt.executeQuery();

			while (rsWorkflowProdutoBanco.next()) {

				getWorkflowProdutoBanco(workflowsProdutoBanco);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsWorkflowProdutoBanco, stmt, conn);

		return workflowsProdutoBanco;
	}

	public WorkflowProdutoBanco buscaWorkflowProdutoBancoByEmpresaOrganizacaoWorkflowPerfil(Long empresa_id, Long organizacao_id, Long produto_id, Long banco_id, Long workflow_id ) {

		String sql = sqlWorkflowProdutoBanco;

		if (empresa_id != null)
			sql += " WHERE WORKFLOWPRODUTOBANCO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOWPRODUTOBANCO.organizacao_id = ?";
		if (produto_id != null)
			sql += " AND WORKFLOWPRODUTOBANCO.produto_id = ?";
		if (banco_id != null)
			sql += " AND WORKFLOWPRODUTOBANCO.banco_id = ?";
		if (workflow_id != null)
			sql += " AND WORKFLOWPRODUTOBANCO.workflow_id = ?";

		this.conn = this.conexao.getConexao();

		WorkflowProdutoBanco workflowProdutoBanco = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, produto_id);
			this.stmt.setLong(4, banco_id);
			this.stmt.setLong(5, workflow_id);
			
			this.rsWorkflowProdutoBanco = this.stmt.executeQuery();

			while (rsWorkflowProdutoBanco.next()) {

				workflowProdutoBanco = new WorkflowProdutoBanco();

				Workflow workflow = new Workflow();
				workflow.setWorkflow_id(rsWorkflowProdutoBanco.getLong("workflow_id"));

				workflowProdutoBanco.setWorkflow(workflow);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsWorkflowProdutoBanco, stmt, conn);

		return workflowProdutoBanco;
	}

	private void getWorkflowProdutoBanco(Collection<WorkflowProdutoBanco> workflowsProdutoBanco)throws SQLException {

		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();
		Workflow workflow = new Workflow();
		WorkflowProdutoBanco workflowProdutoBanco = new WorkflowProdutoBanco();
		
		empresa.setEmpresa_id(rsWorkflowProdutoBanco.getLong("empresa_id"));
		empresa.setNome(rsWorkflowProdutoBanco.getString("empresa_nome"));

		organizacao.setOrganizacao_id(rsWorkflowProdutoBanco.getLong("organizacao_id"));
		organizacao.setNome(rsWorkflowProdutoBanco.getString("organizacao_nome"));

		workflow.setWorkflow_id(rsWorkflowProdutoBanco.getLong("workflow_id"));
		workflow.setNome(rsWorkflowProdutoBanco.getString("workflow_nome"));

		workflowProdutoBanco.setEmpresa(empresa);
		workflowProdutoBanco.setOrganizacao(organizacao);
		workflowProdutoBanco.setWorkflow(workflow);

		workflowsProdutoBanco.add(workflowProdutoBanco);

	}
	
}