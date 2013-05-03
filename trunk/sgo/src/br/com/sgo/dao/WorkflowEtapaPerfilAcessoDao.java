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
import br.com.sgo.modelo.WorkflowEtapaPerfilAcesso;

@Component
public class WorkflowEtapaPerfilAcessoDao extends Dao<WorkflowEtapaPerfilAcesso> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsWorkflowEtapaPerfilAcesso;

	private final String sqlWorkflowEtapaPerfilAcesso = "SELECT WORKFLOWETAPAPERFILACESSO.workflowetapa_id, WORKFLOWETAPAPERFILACESSO.nome as workflowetapaperfilacesso_nome, WORKFLOWETAPAPERFILACESSO.isleituraescrita "+
							", WORKFLOWETAPAPERFILACESSO.empresa_id, EMPRESA.nome as empresa_nome "+
							", WORKFLOWETAPAPERFILACESSO.organizacao_id, ORGANIZACAO.nome as organizacao_nome "+
							", WORKFLOWETAPAPERFILACESSO.workflowetapa_id, WORKFLOWETAPA.nome as workflowetapa_nome "+
							", WORKFLOWETAPAPERFILACESSO.perfil_id, PERFIL.nome as perfil_nome "+
							" FROM ((ORGANIZACAO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) "+
							" INNER JOIN WORKFLOWETAPAPERFILACESSO (NOLOCK) ON EMPRESA.empresa_id = WORKFLOWETAPAPERFILACESSO.empresa_id) ON ORGANIZACAO.organizacao_id = WORKFLOWETAPAPERFILACESSO.organizacao_id) "+ 
							" INNER JOIN WORKFLOWETAPA (NOLOCK) ON WORKFLOWETAPAPERFILACESSO.workflowetapa_id = WORKFLOWETAPA.workflowetapa_id) "+
							" INNER JOIN PERFIL (NOLOCK) ON WORKFLOWETAPAPERFILACESSO.perfil_id = PERFIL.perfil_id ";

	public WorkflowEtapaPerfilAcessoDao(Session session, ConnJDBC conexao) {
		super(session, WorkflowEtapaPerfilAcesso.class);
		this.conexao = conexao;
	}

	public Collection<WorkflowEtapaPerfilAcesso> buscaTodosWorkflowEtapaPerfilAcesso() {

		String sql = sqlWorkflowEtapaPerfilAcesso;

		this.conn = this.conexao.getConexao();

		Collection<WorkflowEtapaPerfilAcesso> workflowEtapasPerfilAcesso = new ArrayList<WorkflowEtapaPerfilAcesso>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.rsWorkflowEtapaPerfilAcesso = this.stmt.executeQuery();

			while (rsWorkflowEtapaPerfilAcesso.next()) {

				Empresa empresa = new Empresa();
				empresa.setEmpresa_id(rsWorkflowEtapaPerfilAcesso.getLong("empresa_id"));
				empresa.setNome(rsWorkflowEtapaPerfilAcesso.getString("empresa_nome"));

				Organizacao organizacao = new Organizacao();
				organizacao.setOrganizacao_id(rsWorkflowEtapaPerfilAcesso.getLong("organizacao_id"));
				organizacao.setNome(rsWorkflowEtapaPerfilAcesso.getString("organizacao_nome"));

				WorkflowEtapa workflowEtapa = new WorkflowEtapa();
				workflowEtapa.setWorkflowEtapa_id(rsWorkflowEtapaPerfilAcesso.getLong("workflowetapa_id"));
				workflowEtapa.setNome(rsWorkflowEtapaPerfilAcesso.getString("workflowetapa_nome"));

				Perfil perfil = new Perfil();
				perfil.setPerfil_id(rsWorkflowEtapaPerfilAcesso.getLong("perfil_id"));
				perfil.setNome(rsWorkflowEtapaPerfilAcesso.getString("perfil_nome"));

				WorkflowEtapaPerfilAcesso workflowEtapaPerfilAcesso = new WorkflowEtapaPerfilAcesso();

				workflowEtapaPerfilAcesso.setEmpresa(empresa);
				workflowEtapaPerfilAcesso.setOrganizacao(organizacao);
				workflowEtapaPerfilAcesso.setWorkflowEtapa(workflowEtapa);
				workflowEtapaPerfilAcesso.setPerfil(perfil);

				workflowEtapasPerfilAcesso.add(workflowEtapaPerfilAcesso);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsWorkflowEtapaPerfilAcesso, stmt, conn);

		return workflowEtapasPerfilAcesso;
	}

	public Collection<WorkflowEtapaPerfilAcesso> buscaWorkflowEtapaPerfilAcessoPorEmpresaOrganizacaoPerfil(Long empresa_id, Long organizacao_id, Long workflowetapa_id) {
		
		String sql = sqlWorkflowEtapaPerfilAcesso;

		if (empresa_id != null)
			sql += " WHERE WORKFLOWETAPAPERFILACESSO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOWETAPAPERFILACESSO.organizacao_id = ?";
		if (workflowetapa_id != null)
			sql += " AND WORKFLOWETAPAPERFILACESSO.workflowetapa_id = ?";

		this.conn = this.conexao.getConexao();

		Collection<WorkflowEtapaPerfilAcesso> workflowEtapasPerfilAcesso = new ArrayList<WorkflowEtapaPerfilAcesso>();

		try {
			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, workflowetapa_id);

			this.rsWorkflowEtapaPerfilAcesso = this.stmt.executeQuery();
			while (rsWorkflowEtapaPerfilAcesso.next()) {

				Empresa empresa = new Empresa();
				empresa.setEmpresa_id(rsWorkflowEtapaPerfilAcesso.getLong("empresa_id"));
				empresa.setNome(rsWorkflowEtapaPerfilAcesso.getString("empresa_nome"));

				Organizacao organizacao = new Organizacao();
				organizacao.setOrganizacao_id(rsWorkflowEtapaPerfilAcesso.getLong("organizacao_id"));
				organizacao.setNome(rsWorkflowEtapaPerfilAcesso.getString("organizacao_nome"));

				WorkflowEtapa workflowEtapa = new WorkflowEtapa();
				workflowEtapa.setWorkflowEtapa_id(rsWorkflowEtapaPerfilAcesso.getLong("workflowetapa_id"));
				workflowEtapa.setNome(rsWorkflowEtapaPerfilAcesso.getString("workflowetapa_nome"));

				Perfil perfil = new Perfil();
				perfil.setPerfil_id(rsWorkflowEtapaPerfilAcesso.getLong("perfil_id"));
				perfil.setNome(rsWorkflowEtapaPerfilAcesso.getString("perfil_nome"));

				WorkflowEtapaPerfilAcesso workflowEtapaPerfilAcesso = new WorkflowEtapaPerfilAcesso();
				
				workflowEtapaPerfilAcesso.setEmpresa(empresa);
				workflowEtapaPerfilAcesso.setOrganizacao(organizacao);
				workflowEtapaPerfilAcesso.setWorkflowEtapa(workflowEtapa);
				workflowEtapaPerfilAcesso.setPerfil(perfil);

				workflowEtapasPerfilAcesso.add(workflowEtapaPerfilAcesso);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			this.conexao.closeConnection(rsWorkflowEtapaPerfilAcesso, stmt, conn);
		}
		return workflowEtapasPerfilAcesso;
	}
	
public WorkflowEtapaPerfilAcesso buscaWorkflowEtapaPerfilAcessoPorEmpresaOrganizacaoWorkflowEtapaPerfil(Long empresa_id, Long organizacao_id, Long workflowetapa_id, Long perfil_id ) {
		
		String sql = sqlWorkflowEtapaPerfilAcesso;

		if (empresa_id != null)
			sql += " WHERE WORKFLOWETAPAPERFILACESSO.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND WORKFLOWETAPAPERFILACESSO.organizacao_id = ?";
		if (workflowetapa_id != null)
			sql += " AND WORKFLOWETAPAPERFILACESSO.workflowetapa_id = ?";
		if (perfil_id != null)
			sql += " AND WORKFLOWETAPAPERFILACESSO.perfil_id = ?";

		this.conn = this.conexao.getConexao();
		
		WorkflowEtapaPerfilAcesso workflowEtapaPerfilAcesso = null;
		
		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, workflowetapa_id);
			this.stmt.setLong(4, perfil_id);
			
			this.rsWorkflowEtapaPerfilAcesso = this.stmt.executeQuery();
			
			while (rsWorkflowEtapaPerfilAcesso.next()) {
				workflowEtapaPerfilAcesso = new WorkflowEtapaPerfilAcesso();
				
				Perfil perfil = new Perfil();
				perfil.setPerfil_id(rsWorkflowEtapaPerfilAcesso.getLong("perfil_id"));
				workflowEtapaPerfilAcesso.setPerfil(perfil);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsWorkflowEtapaPerfilAcesso, stmt, conn);
		
		return workflowEtapaPerfilAcesso;
	}

	public void insert(WorkflowEtapaPerfilAcesso workflowEtapaPerfilAcesso) throws SQLException {

		String sql = "INSERT INTO WORKFLOWETAPAPERFILACESSO (empresa_id, organizacao_id, workflowetapa_id, perfil_id, isactive,isleituraescrita,isupload) VALUES (?,?,?,?,?,?,?)";

		this.conn = this.conexao.getConexao();

		try {
			this.conn.setAutoCommit(false);

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, workflowEtapaPerfilAcesso.getEmpresa().getEmpresa_id());
			this.stmt.setLong(2, workflowEtapaPerfilAcesso.getOrganizacao().getOrganizacao_id());			
			this.stmt.setLong(3, workflowEtapaPerfilAcesso.getWorkflowEtapa().getWorkflowEtapa_id());
			this.stmt.setLong(4, workflowEtapaPerfilAcesso.getPerfil().getPerfil_id());
			this.stmt.setBoolean(5, workflowEtapaPerfilAcesso.getIsActive());
			this.stmt.setBoolean(6, workflowEtapaPerfilAcesso.getIsLeituraEscrita());
			this.stmt.setBoolean(7, workflowEtapaPerfilAcesso.getIsUpload());

			this.stmt.executeUpdate();
			this.conn.commit();

		} catch (SQLException e) {
			this.conn.rollback();
			throw e;
		} finally {
			this.conn.setAutoCommit(true);
			this.conexao.closeConnection(stmt, conn);
		}
		
	}
}
