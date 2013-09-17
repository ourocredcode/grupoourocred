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
import br.com.sgo.modelo.Perfil;
import br.com.sgo.modelo.Workflow;
import br.com.sgo.modelo.Etapa;
import br.com.sgo.modelo.WorkflowTransicao;

@Component
public class WorkflowTransicaoDao extends Dao<WorkflowTransicao> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsWorkflowTransicao;

	private final String sqlWorkflowTransicao = "SELECT WORKFLOWTRANSICAO.workflowtransicao_id, WORKFLOWTRANSICAO.empresa_id " +
			 " ,WORKFLOWTRANSICAO.organizacao_id, WORKFLOWTRANSICAO.etapa_id, WORKFLOWTRANSICAO.etapaproximo_id" +
			 ", WORKFLOWTRANSICAO.perfil_id, WORKFLOWTRANSICAO.sequencia, WORKFLOWTRANSICAO.isactive FROM WORKFLOWTRANSICAO (NOLOCK) ";

	private final String sqlWorkflowTransicoes = "SELECT WORKFLOWTRANSICAO.workflowtransicao_id, WORKFLOWTRANSICAO.empresa_id , WORKFLOW.workflow_id, WORKFLOW.nome as workflow_nome " +
									", EMPRESA.nome as empresa_nome, WORKFLOWTRANSICAO.organizacao_id, ORGANIZACAO.nome as organizacao_nome , WORKFLOWTRANSICAO.etapa_id "+
									", WT1.nome as etapa_nome, WORKFLOWTRANSICAO.etapaproximo_id, WT2.nome as etapaproximo_nome, PERFIL.perfil_id, PERFIL.nome as PERFIL_nome "+
									", WORKFLOWTRANSICAO.sequencia, WORKFLOWTRANSICAO.ispadrao, WORKFLOWTRANSICAO.isactive "+
									" FROM (((WORKFLOWTRANSICAO (NOLOCK) INNER JOIN PERFIL (NOLOCK) ON WORKFLOWTRANSICAO.perfil_id = PERFIL.perfil_id) "+
									" INNER JOIN EMPRESA (NOLOCK) ON WORKFLOWTRANSICAO.empresa_id = EMPRESA.empresa_id) "+
									" INNER JOIN ORGANIZACAO (NOLOCK) ON WORKFLOWTRANSICAO.organizacao_id = ORGANIZACAO.organizacao_id) "+
									" INNER JOIN WORKFLOW (NOLOCK) ON (WORKFLOW.workflow_id = WORKFLOWTRANSICAO.workflow_id) "+
									" INNER JOIN ETAPA (NOLOCK) AS WT1 ON (WORKFLOWTRANSICAO.etapa_id = WT1.etapa_id) "+
									" INNER JOIN ETAPA (NOLOCK) AS WT2 ON (WORKFLOWTRANSICAO.etapaproximo_id = WT2.etapa_id) ";
	
	public WorkflowTransicaoDao(Session session, ConnJDBC conexao) {
		super(session, WorkflowTransicao.class);
		this.conexao = conexao;
	}

	public Collection<WorkflowTransicao> buscaTodosWorkflowTransicao(Long empresa_id, Long organizacao_id) {

		String sql = sqlWorkflowTransicoes;
		
		if (empresa_id != null)
			sql += " WHERE WORKFLOWTRANSICAO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOWTRANSICAO.organizacao_id = ?";

		this.conn = this.conexao.getConexao();
		
		Collection<WorkflowTransicao> workflowTransicoes = new ArrayList<WorkflowTransicao>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsWorkflowTransicao = this.stmt.executeQuery();
			
			while (rsWorkflowTransicao.next()) {
				
				WorkflowTransicao workflowTransicao = new WorkflowTransicao();			
				

				Empresa e = new Empresa();
				Organizacao o = new Organizacao();
				Workflow workflow = new Workflow(); 
				Etapa etapa = new Etapa();
				Etapa etapaProximo = new Etapa();
				Perfil perfil = new Perfil();

				e.setEmpresa_id(rsWorkflowTransicao.getLong("empresa_id"));
				e.setNome(rsWorkflowTransicao.getString("empresa_nome"));

				o.setOrganizacao_id(rsWorkflowTransicao.getLong("organizacao_id"));
				o.setNome(rsWorkflowTransicao.getString("organizacao_nome"));

				workflow.setWorkflow_id(rsWorkflowTransicao.getLong("workflow_id"));
				workflow.setNome(rsWorkflowTransicao.getString("workflow_nome"));
				
				etapa.setEtapa_id(rsWorkflowTransicao.getLong("etapa_id"));
				etapa.setNome(rsWorkflowTransicao.getString("etapa_nome"));

				etapaProximo.setEtapa_id(rsWorkflowTransicao.getLong("etapaproximo_id"));
				etapaProximo.setNome(rsWorkflowTransicao.getString("etapaproximo_nome"));

				perfil.setPerfil_id(rsWorkflowTransicao.getLong("perfil_id"));
				perfil.setNome(rsWorkflowTransicao.getString("perfil_nome"));
				
				workflowTransicao.setEmpresa(e);
				workflowTransicao.setOrganizacao(o);
				workflowTransicao.setWorkflow(workflow);
				workflowTransicao.setEtapa(etapa);
				workflowTransicao.setEtapaProximo(etapaProximo);
				workflowTransicao.setPerfil(perfil);
				
				workflowTransicao.setSequencia(rsWorkflowTransicao.getInt("sequencia"));
				workflowTransicao.setIsPadrao(rsWorkflowTransicao.getBoolean("ispadrao"));
				workflowTransicao.setIsActive(rsWorkflowTransicao.getBoolean("isactive"));

				workflowTransicoes.add(workflowTransicao);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsWorkflowTransicao, stmt, conn);

		return workflowTransicoes;

	}

	public WorkflowTransicao buscaWorkflowTransicaoPorEmpresaOrganizacaoEtapa(Long empresa_id, Long organizacao_id, Long etapa_id) {
		
		String sql = sqlWorkflowTransicao;

		if (empresa_id != null)
			sql += " AND WORKFLOWTRANSICAO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOWTRANSICAO.organizacao_id = ?";
		if (etapa_id != null)
			sql += " AND (WORKFLOWTRANSICAO.etapa_id = ?)";

		this.conn = this.conexao.getConexao();
		WorkflowTransicao workflowtransicao = null;
		try {
			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, etapa_id);
			this.rsWorkflowTransicao = this.stmt.executeQuery();
			while (rsWorkflowTransicao.next()) {
				workflowtransicao = new WorkflowTransicao();
				workflowtransicao.setWorkflowTransicao_id(rsWorkflowTransicao.getLong("workflowtransicao_id"));
				workflowtransicao.setNome(rsWorkflowTransicao.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsWorkflowTransicao, stmt, conn);
		return workflowtransicao;
	}

	public WorkflowTransicao buscaWorkflowTransicaoPorEmpresaOrganizacaoEtapaProximo(Long empresa_id, Long organizacao_id, Long etapa_id, Long etapaProximo_id, Long perfil_id) {

		String sql = sqlWorkflowTransicao;

		if (empresa_id != null)
			sql += " WHERE WORKFLOWTRANSICAO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOWTRANSICAO.organizacao_id = ?";
		if (etapa_id != null)
			sql += " AND (WORKFLOWTRANSICAO.etapa_id = ?)";
		if (etapaProximo_id != null)
			sql += " AND (WORKFLOWTRANSICAO.etapaproximo_id = ?)";
		if (perfil_id != null)
			sql += " AND (WORKFLOWTRANSICAO.perfil_id = ?)";

		this.conn = this.conexao.getConexao();

		WorkflowTransicao workflowtransicao = null;

		try {
			
			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, etapa_id);
			this.stmt.setLong(4, etapaProximo_id);
			this.stmt.setLong(5, perfil_id);

			this.rsWorkflowTransicao = this.stmt.executeQuery();

			while (rsWorkflowTransicao.next()) {

				workflowtransicao = new WorkflowTransicao();
				workflowtransicao.setWorkflowTransicao_id(rsWorkflowTransicao.getLong("workflowtransicao_id"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}
		
		this.conexao.closeConnection(rsWorkflowTransicao, stmt, conn);
		
		return workflowtransicao;

	}
	
	public WorkflowTransicao buscaWorkflowTransicaoPorEmpresaOrganizacaoEtapasPerfilWorkFlow(Long empresa_id, Long organizacao_id, Long etapa_id, 
			Long etapaProximo_id, Long perfil_id, Long workflow_id) {

		String sql = sqlWorkflowTransicao;

		if (empresa_id != null)
			sql += " WHERE WORKFLOWTRANSICAO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOWTRANSICAO.organizacao_id = ?";
		if (etapa_id != null)
			sql += " AND (WORKFLOWTRANSICAO.etapa_id = ?)";
		if (etapaProximo_id != null)
			sql += " AND (WORKFLOWTRANSICAO.etapaproximo_id = ?)";
		if (perfil_id != null)
			sql += " AND (WORKFLOWTRANSICAO.perfil_id = ?)";
		if (workflow_id != null)
			sql += " AND (WORKFLOWTRANSICAO.workflow_id = ?)";

		this.conn = this.conexao.getConexao();

		WorkflowTransicao workflowtransicao = null;

		try {
			
			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, etapa_id);
			this.stmt.setLong(4, etapaProximo_id);
			this.stmt.setLong(5, perfil_id);
			this.stmt.setLong(6, workflow_id);

			this.rsWorkflowTransicao = this.stmt.executeQuery();

			while (rsWorkflowTransicao.next()) {

				workflowtransicao = new WorkflowTransicao();
				workflowtransicao.setWorkflowTransicao_id(rsWorkflowTransicao.getLong("workflowtransicao_id"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}
		
		this.conexao.closeConnection(rsWorkflowTransicao, stmt, conn);
		
		return workflowtransicao;

	}

	public WorkflowTransicao buscaWorkflowTransicaoPorNome(Long empresa, Long organizacao, String nome) {

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
				workflowtransicao.setWorkflowTransicao_id(rsWorkflowTransicao.getLong("workflowtransicao_id"));
				workflowtransicao.setNome(rsWorkflowTransicao.getString("nome"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsWorkflowTransicao, stmt, conn);
		return workflowtransicao;

	}

}