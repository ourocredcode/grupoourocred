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
import br.com.sgo.modelo.WorkflowEtapa;
import br.com.sgo.modelo.WorkflowTransicao;

@Component
public class WorkflowTransicaoDao extends Dao<WorkflowTransicao> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsWorkflowTransicao;

	private final String sqlWorkflowTransicao = "SELECT WORKFLOWTRANSICAO.workflowtransicao_id, WORKFLOWTRANSICAO.empresa_id " +
			 " ,WORKFLOWTRANSICAO.organizacao_id, WORKFLOWTRANSICAO.workflowetapa_id, WORKFLOWTRANSICAO.workflowetapaproximo_id" +
			 ", WORKFLOWTRANSICAO.perfil_id, WORKFLOWTRANSICAO.sequencia, WORKFLOWTRANSICAO.isactive FROM WORKFLOWTRANSICAO (NOLOCK) ";

	/*private final String sqlWorkflowTransicoes = "SELECT WORKFLOWTRANSICAO.workflowtransicao_id, WORKFLOWTRANSICAO.empresa_id " +
			", EMPRESA.nome as empresa_nome, WORKFLOWTRANSICAO.organizacao_id, ORGANIZACAO.nome as organizacao_nome " +
			", WORKFLOWTRANSICAO.workflowetapa_id, WT1.nome as workflowetapa_nome, WORKFLOWTRANSICAO.workflowetapaproximo_id, WT2.nome as workflowetapaproximo_nome " +
			", WORKFLOWTRANSICAO.sequencia, WORKFLOWTRANSICAO.ispadrao, WORKFLOWTRANSICAO.isactive " +
			" FROM ((WORKFLOWTRANSICAO INNER JOIN EMPRESA ON WORKFLOWTRANSICAO.empresa_id = EMPRESA.empresa_id) " +
			" INNER JOIN ORGANIZACAO ON WORKFLOWTRANSICAO.organizacao_id = ORGANIZACAO.organizacao_id) " +
			" INNER JOIN WORKFLOWETAPA AS WT1 ON (WORKFLOWTRANSICAO.workflowetapa_id = WT1.workflowetapa_id) " + 
			" INNER JOIN WORKFLOWETAPA AS WT2 ON (WORKFLOWTRANSICAO.workflowetapaproximo_id = WT2.workflowetapa_id) "; 
	*/
	private final String sqlWorkflowTransicoes = "SELECT WORKFLOWTRANSICAO.workflowtransicao_id, WORKFLOWTRANSICAO.empresa_id , EMPRESA.nome as empresa_nome "+
	", WORKFLOWTRANSICAO.organizacao_id, ORGANIZACAO.nome as organizacao_nome , WORKFLOWTRANSICAO.workflowetapa_id "+
	", WT1.nome as workflowetapa_nome, WORKFLOWTRANSICAO.workflowetapaproximo_id, WT2.nome as workflowetapaproximo_nome, PERFIL.perfil_id, PERFIL.nome as PERFIL_nome "+  
	", WORKFLOWTRANSICAO.sequencia, WORKFLOWTRANSICAO.ispadrao, WORKFLOWTRANSICAO.isactive "+
	" FROM (((WORKFLOWTRANSICAO (NOLOCK) INNER JOIN PERFIL (NOLOCK) ON WORKFLOWTRANSICAO.perfil_id = PERFIL.perfil_id) "+ 
	" INNER JOIN EMPRESA (NOLOCK) ON WORKFLOWTRANSICAO.empresa_id = EMPRESA.empresa_id) "+
	" INNER JOIN ORGANIZACAO (NOLOCK) ON WORKFLOWTRANSICAO.organizacao_id = ORGANIZACAO.organizacao_id) "+
	" INNER JOIN WORKFLOWETAPA (NOLOCK) AS WT1 ON (WORKFLOWTRANSICAO.workflowetapa_id = WT1.workflowetapa_id) "+   
	" INNER JOIN WORKFLOWETAPA (NOLOCK) AS WT2 ON (WORKFLOWTRANSICAO.workflowetapaproximo_id = WT2.workflowetapa_id)";
	
	public WorkflowTransicaoDao(Session session, ConnJDBC conexao) {
		super(session, WorkflowTransicao.class);
		this.conexao = conexao;
	}

	public Collection<WorkflowTransicao> buscaTodosWorkflowTransicao() {

		String sql = sqlWorkflowTransicoes;

		this.conn = this.conexao.getConexao();
		
		Collection<WorkflowTransicao> workflowTransicoes = new ArrayList<WorkflowTransicao>();

		try {
			
			this.stmt = conn.prepareStatement(sql);
			
			this.rsWorkflowTransicao = this.stmt.executeQuery();
			
			while (rsWorkflowTransicao.next()) {
				
				WorkflowTransicao workflowTransicao = new WorkflowTransicao();			
				

				Empresa e = new Empresa();
				Organizacao o = new Organizacao();
				WorkflowEtapa workflowEtapa = new WorkflowEtapa();
				WorkflowEtapa workflowEtapaProximo = new WorkflowEtapa();
				Perfil perfil = new Perfil();

				e.setEmpresa_id(rsWorkflowTransicao.getLong("empresa_id"));
				e.setNome(rsWorkflowTransicao.getString("empresa_nome"));

				o.setOrganizacao_id(rsWorkflowTransicao.getLong("organizacao_id"));
				o.setNome(rsWorkflowTransicao.getString("organizacao_nome"));

				workflowEtapa.setWorkflowEtapa_id(rsWorkflowTransicao.getLong("workflowetapa_id"));
				workflowEtapa.setNome(rsWorkflowTransicao.getString("workflowetapa_nome"));

				workflowEtapaProximo.setWorkflowEtapa_id(rsWorkflowTransicao.getLong("workflowetapaproximo_id"));
				workflowEtapaProximo.setNome(rsWorkflowTransicao.getString("workflowetapaproximo_nome"));

				perfil.setPerfil_id(rsWorkflowTransicao.getLong("perfil_id"));
				perfil.setNome(rsWorkflowTransicao.getString("perfil_nome"));
				
				workflowTransicao.setEmpresa(e);
				workflowTransicao.setOrganizacao(o);
				workflowTransicao.setWorkflowEtapa(workflowEtapa);
				workflowTransicao.setWorkflowEtapaProximo(workflowEtapaProximo);
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

	public WorkflowTransicao buscaWorkflowTransicaoPorEmpresaOrganizacaoWorkflowetapa(Long empresa_id, Long organizacao_id, Long workflowetapa_id) {
		
		String sql = sqlWorkflowTransicao;

		if (empresa_id != null)
			sql += " AND WORKFLOWTRANSICAO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOWTRANSICAO.organizacao_id = ?";
		if (workflowetapa_id != null)
			sql += " AND (WORKFLOWTRANSICAO.workflowetapa_id = ?)";

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
				workflowtransicao.setWorkflowTransicao_id(rsWorkflowTransicao.getLong("workflowtransicao_id"));
				workflowtransicao.setNome(rsWorkflowTransicao.getString("nome"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsWorkflowTransicao, stmt, conn);
		return workflowtransicao;
	}

	public WorkflowTransicao buscaWorkflowTransicaoPorEmpresaOrganizacaoWorkflowEtapaProximo(Long empresa_id, Long organizacao_id, Long workflowEtapa_id, Long workflowEtapaProximo_id, Long perfil_id) {

		String sql = sqlWorkflowTransicao;

		if (empresa_id != null)
			sql += " WHERE WORKFLOWTRANSICAO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOWTRANSICAO.organizacao_id = ?";
		if (workflowEtapa_id != null)
			sql += " AND (WORKFLOWTRANSICAO.workflowEtapa_id = ?)";
		if (workflowEtapaProximo_id != null)
			sql += " AND (WORKFLOWTRANSICAO.workflowetapaproximo_id = ?)";
		if (perfil_id != null)
			sql += " AND (WORKFLOWTRANSICAO.perfil_id = ?)";

		this.conn = this.conexao.getConexao();

		WorkflowTransicao workflowtransicao = null;

		try {
			
			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, workflowEtapa_id);
			this.stmt.setLong(4, workflowEtapaProximo_id);
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