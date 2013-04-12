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
import br.com.sgo.modelo.WorkflowEtapa;

@Component
public class WorkflowEtapaDao extends Dao<WorkflowEtapa> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsWorkflowEtapa;

	private final String sqlWorkflowEtapa = "SELECT WORKFLOWETAPA.workflowetapa_id, WORKFLOWETAPA.nome, WORKFLOWETAPA.empresa_id"
			+ " ,WORKFLOWETAPA.organizacao_id, WORKFLOWETAPA.workflow_id, WORKFLOWETAPA.perfil_id, WORKFLOWETAPA.ordemetapa "
			+ "FROM WORKFLOWETAPA (NOLOCK)";

	public WorkflowEtapaDao(Session session, ConnJDBC conexao) {
		super(session, WorkflowEtapa.class);
		this.conexao = conexao;
	}

	public Collection<WorkflowEtapa> buscaTodosWorkflowEtapa() {
		String sql = sqlWorkflowEtapa;

		this.conn = this.conexao.getConexao();
		Collection<WorkflowEtapa> workflowetapas = new ArrayList<WorkflowEtapa>();

		try {
			this.stmt = conn.prepareStatement(sql);
			this.rsWorkflowEtapa = this.stmt.executeQuery();
			while (rsWorkflowEtapa.next()) {
				WorkflowEtapa workflowEtapa = new WorkflowEtapa();
				workflowEtapa.setWorkflowEtapa_id(rsWorkflowEtapa
						.getLong("workflowetapa_id"));
				workflowEtapa.setNome(rsWorkflowEtapa.getString("nome"));
				workflowetapas.add(workflowEtapa);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsWorkflowEtapa, stmt, conn);
		return workflowetapas;
	}

	public WorkflowEtapa buscaWorkflowEtapaPorEmpresaOrganizacaoTipoworflow(
			Long empresa_id, Long organizacao_id, Long tipoworkflow_id) {
		String sql = sqlWorkflowEtapa;

		if (empresa_id != null)
			sql += " AND WORKFLOWETAPA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOWETAPA.organizacao_id = ?";
		if (tipoworkflow_id != null)
			sql += " AND (WORKFLOWETAPA.tipoworkflow_id = ?)";

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
				workflowetapa.setWorkflowEtapa_id(rsWorkflowEtapa
						.getLong("workflowetapa_id"));
				workflowetapa.setNome(rsWorkflowEtapa.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsWorkflowEtapa, stmt, conn);
		return workflowetapa;
	}

	public WorkflowEtapa buscaWorkflowEtapaPorNome(Long empresa,
			Long organizacao, String nome) {
		String sql = sqlWorkflowEtapa;
		if (empresa != null)
			sql += " AND WORKFLOWETAPA.empresa_id = ?";
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
				workflowetapa.setWorkflowEtapa_id(rsWorkflowEtapa
						.getLong("workflowetapa_id"));
				workflowetapa.setNome(rsWorkflowEtapa.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsWorkflowEtapa, stmt, conn);
		return workflowetapa;
	}

}