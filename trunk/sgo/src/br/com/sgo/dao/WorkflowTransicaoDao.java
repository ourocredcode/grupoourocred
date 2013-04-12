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
import br.com.sgo.modelo.WorkflowTransicao;

@Component
public class WorkflowTransicaoDao extends Dao<WorkflowTransicao> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsWorkflowTransicao;

	private final String sqlWorkflowTransicao = "SELECT WORKFLOWTRANSICAO.workflowtransicao_id, WORKFLOWTRANSICAO.nome, WORKFLOWTRANSICAO.empresa_id"
			+ " ,WORKFLOWTRANSICAO.organizacao_id, WORKFLOWTRANSICAO.workflowetapa_id, WORKFLOWTRANSICAO.workflowetapaproximo_id, WORKFLOWTRANSICAO.sequencia "
			+ " ,WORKFLOWTRANSICAO.isactive FROM WORKFLOWTRANSICAO (NOLOCK)";

	public WorkflowTransicaoDao(Session session, ConnJDBC conexao) {
		super(session, WorkflowTransicao.class);
		this.conexao = conexao;
	}

	public Collection<WorkflowTransicao> buscaTodosWorkflowTransicao() {
		String sql = sqlWorkflowTransicao;

		this.conn = this.conexao.getConexao();
		Collection<WorkflowTransicao> workflowstransicao = new ArrayList<WorkflowTransicao>();

		try {
			this.stmt = conn.prepareStatement(sql);
			this.rsWorkflowTransicao = this.stmt.executeQuery();
			while (rsWorkflowTransicao.next()) {
				WorkflowTransicao workflowTransicao = new WorkflowTransicao();
				workflowTransicao.setWorkflowTransicao_id(rsWorkflowTransicao
						.getLong("workflowtransicao_id"));
				workflowTransicao
						.setNome(rsWorkflowTransicao.getString("nome"));
				workflowstransicao.add(workflowTransicao);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsWorkflowTransicao, stmt, conn);
		return workflowstransicao;
	}

	public WorkflowTransicao buscaWorkflowTransicaoPorEmpresaOrganizacaoWorkflowetapa(
			Long empresa_id, Long organizacao_id, Long workflowetapa_id) {
		String sql = sqlWorkflowTransicao;

		if (empresa_id != null)
			sql += " AND WORKFLOWTRANSICAO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOWTRANSICAO.organizacao_id = ?";
		if (workflowetapa_id != null)
			sql += " AND (WORKFLOWTRANSICAO.tipoworkflow_id = ?)";

		this.conn = this.conexao.getConexao();
		WorkflowTransicao workflowtransicao = null;
		try {
			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, workflowetapa_id);
			this.rsWorkflowTransicao = this.stmt.executeQuery();
			while (rsWorkflowTransicao.next()) {
				workflowtransicao = new WorkflowTransicao();
				workflowtransicao.setWorkflowTransicao_id(rsWorkflowTransicao
						.getLong("workflowtransicao_id"));
				workflowtransicao
						.setNome(rsWorkflowTransicao.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsWorkflowTransicao, stmt, conn);
		return workflowtransicao;
	}

	public WorkflowTransicao buscaWorkflowTransicaoPorEmpresaOrganizacaoWorkflowetapaWorkflowproximo(
			Long empresa_id, Long organizacao_id, Long workflowetapa_id,
			Long workflowetapaproximo_id) {
		String sql = sqlWorkflowTransicao;

		if (empresa_id != null)
			sql += " AND WORKFLOWTRANSICAO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOWTRANSICAO.organizacao_id = ?";
		if (workflowetapa_id != null)
			sql += " AND (WORKFLOWTRANSICAO.tipoworkflow_id = ?)";
		if (workflowetapaproximo_id != null)
			sql += " AND (WORKFLOWTRANSICAO.workflowetapaproximo_id = ?)";

		this.conn = this.conexao.getConexao();
		WorkflowTransicao workflowtransicao = null;
		try {
			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, workflowetapa_id);
			this.stmt.setLong(4, workflowetapaproximo_id);
			this.rsWorkflowTransicao = this.stmt.executeQuery();
			while (rsWorkflowTransicao.next()) {
				workflowtransicao = new WorkflowTransicao();
				workflowtransicao.setWorkflowTransicao_id(rsWorkflowTransicao
						.getLong("workflowtransicao_id"));
				workflowtransicao
						.setNome(rsWorkflowTransicao.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsWorkflowTransicao, stmt, conn);
		return workflowtransicao;
	}

	public WorkflowTransicao buscaWorkflowTransicaoPorNome(Long empresa,
			Long organizacao, String nome) {
		String sql = sqlWorkflowTransicao;
		if (empresa != null)
			sql += " AND WORKFLOWTRANSICAO.empresa_id = ?";
		if (organizacao != null)
			sql += " AND WORKFLOWTRANSICAO.organizacao_id = ?";
		if (nome != null)
			sql += " AND (WORKFLOWTRANSICAO.nome like ?)";
		this.conn = this.conexao.getConexao();
		WorkflowTransicao workflowtransicao = null;
		try {
			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa);
			this.stmt.setLong(2, organizacao);
			this.stmt.setString(3, "%" + nome + "%");
			this.rsWorkflowTransicao = this.stmt.executeQuery();
			while (rsWorkflowTransicao.next()) {
				workflowtransicao = new WorkflowTransicao();
				workflowtransicao.setWorkflowTransicao_id(rsWorkflowTransicao
						.getLong("workflowtransicao_id"));
				workflowtransicao
						.setNome(rsWorkflowTransicao.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsWorkflowTransicao, stmt, conn);
		return workflowtransicao;
	}

}