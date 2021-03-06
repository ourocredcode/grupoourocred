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
import br.com.sgo.modelo.Etapa;
import br.com.sgo.modelo.Organizacao;

@Component
public class EtapaDao extends Dao<Etapa> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsEtapa;

	private final String sqlEtapa = " SELECT ETAPA.etapa_id, ETAPA.nome as etapa_nome, ETAPA.empresa_id  ,ETAPA.organizacao_id, ETAPA.ordemetapa, ETAPA.ispadrao, ETAPA.isactive," +
			"						  EMPRESA.nome as empresa_nome, ORGANIZACAO.nome as organizacao_nome, EMPRESA.empresa_id, ORGANIZACAO.organizacao_id " +
			"							FROM ETAPA (NOLOCK) " +
			"							INNER JOIN EMPRESA (NOLOCK) ON EMPRESA.empresa_id = ETAPA.empresa_id " +
			"							INNER JOIN ORGANIZACAO (NOLOCK) ON ORGANIZACAO.organizacao_id = ETAPA.organizacao_id  ";

	private final String sqlEtapas = " SELECT DISTINCT ETAPA.etapa_id, ETAPA.nome AS etapa_nome, ETAPA.empresa_id " +
									 ", EMPRESA.NOME AS empresa_nome, ETAPA.organizacao_id, ORGANIZACAO.nome as organizacao_nome " + 
									 ", ETAPA.isactive, ETAPA.ispadrao, ETAPA.ordemetapa  " +
										"FROM ((((ETAPA (NOLOCK)  " +
										"INNER JOIN WORKFLOWETAPA (NOLOCK) ON WORKFLOWETAPA.etapa_id = ETAPA.etapa_id) " +
										"INNER JOIN WORKFLOW (NOLOCK) ON WORKFLOWETAPA.workflow_id = WORKFLOW.workflow_id) " +
										"INNER JOIN TIPOWORKFLOW (NOLOCk) ON WORKFLOW.tipoworkflow_id = TIPOWORKFLOW.tipoworkflow_id) " +
										"INNER JOIN EMPRESA (NOLOCK) ON ETAPA.empresa_id = EMPRESA.empresa_id) " +  
										"INNER JOIN ORGANIZACAO (NOLOCK) ON ETAPA.organizacao_id = ORGANIZACAO.organizacao_id ";

	public EtapaDao(Session session, ConnJDBC conexao) {
		super(session, Etapa.class);
		this.conexao = conexao;
	}
	
	public Etapa buscaEtapaById(Long etapa_id) {

		String sql = sqlEtapa;

		if (etapa_id != null)
			sql += " WHERE ETAPA.etapa_id = ? ";
		
		sql += " ORDER BY ETAPA.nome ";

		this.conn = this.conexao.getConexao();
		
		Etapa etapa = null;
		
		try {
			
			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, etapa_id);

			this.rsEtapa = this.stmt.executeQuery();
			
			while (rsEtapa.next()) {

				etapa = new Etapa();
				etapa.setEtapa_id(rsEtapa.getLong("etapa_id"));
				etapa.setNome(rsEtapa.getString("etapa_nome"));
			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsEtapa, stmt, conn);
		return etapa;

	}

	public Collection<Etapa> buscaTodosEtapa() {

		String sql = sqlEtapas;
		
		sql += " ORDER BY ETAPA.nome ";

		this.conn = this.conexao.getConexao();

		Collection<Etapa> etapas = new ArrayList<Etapa>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.rsEtapa = this.stmt.executeQuery();

			while (rsEtapa.next()) {

				getEtapa(etapas);	

			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		this.conexao.closeConnection(rsEtapa, stmt, conn);
		return etapas;
	}

	public Collection<Etapa> buscaEtapasByEmpresaOrganizacao(Long empresa_id, Long organizacao_id) {

		String sql = sqlEtapa;

		if (empresa_id != null)
			sql += " WHERE ETAPA.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND ETAPA.organizacao_id = ? ";
		
		sql += " ORDER BY ETAPA.nome ";

		this.conn = this.conexao.getConexao();

		Collection<Etapa> etapas = new ArrayList<Etapa>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsEtapa = this.stmt.executeQuery();

			while (rsEtapa.next()) {

				getEtapa(etapas);	

			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		this.conexao.closeConnection(rsEtapa, stmt, conn);
		return etapas;
	}
	
	public Etapa buscaEtapaByEmpresaOrganizacaoNome(Long empresa_id, Long organizacao_id, String nome ) {

		String sql = sqlEtapa;

		if (empresa_id != null)
			sql += " WHERE ETAPA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND ETAPA.organizacao_id = ?";
		if (nome != null)
			sql += " AND ETAPA.nome like ?";

		sql += " ORDER BY ETAPA.nome ";
		
		this.conn = this.conexao.getConexao();

		Etapa etapa = null;
		
		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3, "%" + nome + "%");

			this.rsEtapa = this.stmt.executeQuery();
			
			while (rsEtapa.next()) {
				etapa = new Etapa();
				etapa.setEtapa_id(rsEtapa.getLong("etapa_id"));
				etapa.setNome(rsEtapa.getString("etapa_nome"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsEtapa, stmt, conn);
		return etapa;
	}
	
	public Etapa buscaEtapaByEmpresaOrganizacaoNomeExato(Long empresa_id, Long organizacao_id, String nome ) {

		String sql = sqlEtapa;

		if (empresa_id != null)
			sql += " WHERE ETAPA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND ETAPA.organizacao_id = ?";
		if (nome != null)
			sql += " AND ETAPA.nome = ?";

		sql += " ORDER BY ETAPA.nome ";
		
		this.conn = this.conexao.getConexao();

		Etapa etapa = null;
		
		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setString(3,nome);

			this.rsEtapa = this.stmt.executeQuery();
			
			while (rsEtapa.next()) {
				etapa = new Etapa();
				etapa.setEtapa_id(rsEtapa.getLong("etapa_id"));
				etapa.setNome(rsEtapa.getString("etapa_nome"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsEtapa, stmt, conn);
		return etapa;
	}
	
	public Collection<Etapa> buscaEtapasByEmpresaOrganizacaoNome(Long empresa, Long organizacao, String nome) {

		String sql = sqlEtapas;

		if (empresa != null)
			sql += " WHERE ETAPA.empresa_id = ?";
		if (organizacao != null)
			sql += " AND ETAPA.organizacao_id = ?";
		if (nome != null)
			sql += " AND (ETAPA.nome like ?)";
		
		sql += " ORDER BY ETAPA.nome ";

		this.conn = this.conexao.getConexao();
		
		Collection<Etapa> etapas =  new ArrayList<Etapa>();

		try {
			
			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa);
			this.stmt.setLong(2, organizacao);
			this.stmt.setString(3, "%" + nome + "%");
			
			this.rsEtapa = this.stmt.executeQuery();
			
			while (rsEtapa.next()) {

				Etapa etapa = new Etapa();
				etapa.setEtapa_id(rsEtapa.getLong("etapa_id"));
				etapa.setNome(rsEtapa.getString("etapa_nome"));
				
				etapas.add(etapa);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsEtapa, stmt, conn);
		return etapas;
	}

	public Collection<Etapa> buscaEtapasByEmpresaOrganizacaoWorkflow(Long empresa_id, Long organizacao_id, Long workflow_id) {
		
		String sql = sqlEtapas;
		
		if (empresa_id != null)
			sql += " WHERE ETAPA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND ETAPA.organizacao_id = ?";
		if (workflow_id != null)
			sql += " AND WORKFLOWETAPA.workflow_id = ?";
		
		sql += " ORDER BY ETAPA.nome ";
		
		this.conn = this.conexao.getConexao();
		
		Collection<Etapa> etapas =  new ArrayList<Etapa>();

		try {
			
			this.stmt = conn.prepareStatement(sql);
			
			//System.out.println(sql);
			//System.out.println(empresa_id);
			//System.out.println(organizacao_id);
			//System.out.println(workflow_id);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, workflow_id);

			this.rsEtapa = this.stmt.executeQuery();
			
			while (rsEtapa.next()) {

				getEtapa(etapas);

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

		this.conexao.closeConnection(rsEtapa, stmt, conn);
		return etapas;
	}
	
	public Collection<Etapa> buscaEtapasByEmpresaOrganizacaoTipoWorkflow(Long empresa_id, Long organizacao_id, Long tipoworkflow_id) {
		
		String sql = sqlEtapas;
		
		if (empresa_id != null)
			sql += " WHERE ETAPA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND ETAPA.organizacao_id = ?";
		if (tipoworkflow_id != null)
			sql += " AND TIPOWORKFLOW.tipoworkflow_id = ?";
		
		sql += " ORDER BY ETAPA.nome ";
		
		this.conn = this.conexao.getConexao();
		
		Collection<Etapa> etapas =  new ArrayList<Etapa>();

		try {
			
			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, tipoworkflow_id);

			this.rsEtapa = this.stmt.executeQuery();
			
			while (rsEtapa.next()) {

				getEtapa(etapas);

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

		this.conexao.closeConnection(rsEtapa, stmt, conn);
		return etapas;
	}
	
public Collection<Etapa> buscaEtapasByEmpresaOrganizacaoTipoWorkflowDistinct(Long empresa_id, Long organizacao_id, Long tipoworkflow_id) {
		
		String sql = " SELECT DISTINCT ETAPA.etapa_id, ETAPA.nome AS etapa_nome, ETAPA.empresa_id  " +
					 " ,EMPRESA.nome AS empresa_nome, ETAPA.organizacao_id, ORGANIZACAO.nome as organizacao_nome   " +
					 " , ETAPA.isactive, ETAPA.ispadrao, ETAPA.ordemetapa " +   
					 "		FROM ((((ETAPA (NOLOCK)   "  +
					 " INNER JOIN WORKFLOWETAPA (NOLOCK) ON WORKFLOWETAPA.etapa_id = ETAPA.etapa_id AND WORKFLOWETAPA.isactive = 1 ) " +  
					 " INNER JOIN WORKFLOW (NOLOCK) ON WORKFLOWETAPA.workflow_id = WORKFLOW.workflow_id)   " +
					 " INNER JOIN TIPOWORKFLOW (NOLOCk) ON WORKFLOW.tipoworkflow_id = TIPOWORKFLOW.tipoworkflow_id) " +  
					 " INNER JOIN EMPRESA (NOLOCK) ON ETAPA.empresa_id = EMPRESA.empresa_id) " +    
					 " INNER JOIN ORGANIZACAO (NOLOCK) ON ETAPA.organizacao_id = ORGANIZACAO.organizacao_id  ";
		
		if (empresa_id != null)
			sql += " WHERE ETAPA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND ETAPA.organizacao_id = ?";
		if (tipoworkflow_id != null)
			sql += " AND TIPOWORKFLOW.tipoworkflow_id = ?";
		
		sql += " ORDER BY ETAPA.nome ";
		
		this.conn = this.conexao.getConexao();
		
		Collection<Etapa> etapas =  new ArrayList<Etapa>();

		try {
			
			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, tipoworkflow_id);

			this.rsEtapa = this.stmt.executeQuery();
			
			while (rsEtapa.next()) {

				getEtapa(etapas);

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

		this.conexao.closeConnection(rsEtapa, stmt, conn);
		return etapas;
	}

	public Collection<Etapa> buscaEtapasToTransicaoByWorkflowPerfil(Long empresa_id, Long organizacao_id, Long workflow_id, Long perfil_id) {
		
		String sql = "SELECT WORKFLOWETAPAPERFILACESSO.empresa_id, EMPRESA.nome AS empresa_nome, WORKFLOWETAPAPERFILACESSO.organizacao_id "+
					", ORGANIZACAO.nome AS organizacao_nome, WORKFLOWETAPAPERFILACESSO.etapa_id, WORKFLOWETAPA.nome AS etapa_nome "+
					", WORKFLOWETAPAPERFILACESSO.isactive, WORKFLOWETAPAPERFILACESSO.isleituraescrita, WORKFLOWETAPAPERFILACESSO.isupload "+
					", WORKFLOWETAPAPERFILACESSO.perfil_id, PERFIL.nome AS perfil_nome, WORKFLOWETAPAPERFILACESSO.workflow_id, WORKFLOW.nome AS workflow_nome "+
					" FROM ((WORKFLOW (NOLOCK) INNER JOIN (ORGANIZACAO (NOLOCK) INNER JOIN (EMPRESA (NOLOCK) "+
					" INNER JOIN WORKFLOWETAPAPERFILACESSO (NOLOCK) ON EMPRESA.empresa_id = WORKFLOWETAPAPERFILACESSO.empresa_id) "+ 
					" ON ORGANIZACAO.organizacao_id = WORKFLOWETAPAPERFETAPAnizacao_id) "+
					" ON WORKFLOW.workflow_id = WORKFLOWETAPAPERFILACESSO.workflow_id) INNER JOIN WORKFLOWETAPA (NOLOCK) "+
					" ON WORKFLOWETAPAPERFILACESSO.etapa_id = WORKFLOWETAPA.etapa_id) INNER JOIN PERFIL (NOLOCK) "+ 
					" ON WORKFLOWETAPAPERFILACESSO.perfil_id = PERFIL.perfil_id ";
		
		if (empresa_id != null)
			sql += " WHERE ETAPA.empresa_id = ?";
		if (organizacao_id != null)
			sql += " AND ETAPA.organizacao_id = ?";
		if (workflow_id != null)
			sql += " AND ETAPA.workflow_id = ?";
		if (perfil_id != null)
			sql += " AND WORKFLOWETAPAPERFILACESSO.perfil_id = ?";
		
		sql += " ORDER BY ETAPA.nome ";
		
		this.conn = this.conexao.getConexao();
		
		Collection<Etapa> etapas =  new ArrayList<Etapa>();

		try {
			
			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, workflow_id);
			this.stmt.setLong(4, perfil_id);

			this.rsEtapa = this.stmt.executeQuery();

			while (rsEtapa.next()) {

				Etapa etapa = new Etapa();
				etapa.setEtapa_id(rsEtapa.getLong("etapa_id"));
				etapa.setNome(rsEtapa.getString("etapa_nome"));
				etapas.add(etapa);

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

		this.conexao.closeConnection(rsEtapa, stmt, conn);
		return etapas;
	}

	
	
	

	public Collection<Etapa> buscaEtapaByContratoPerfil(Long contrato_id, Long perfil_id) {

		String sql = "SELECT " +
				"		WORKFLOWTRANSICAO.etapaproximo_id, ETAPA.nome as etapa_nome FROM " +
				"		(CONTRATO (NOLOCK) " +
				"			INNER JOIN (ETAPA (NOLOCK) " +
				"			INNER JOIN WORKFLOWTRANSICAO (NOLOCK) " +
				"					ON ETAPA.etapa_id = WORKFLOWTRANSICAO.etapaproximo_id) " +
				"					ON ( CONTRATO.etapa_id = WORKFLOWTRANSICAO.etapa_id " +
				"					 AND CONTRATO.workflow_id = WORKFLOWTRANSICAO.workflow_id AND WORKFLOWTRANSICAO.isactive = 1 ) " +
				"			INNER JOIN PERFIL (NOLOCK) ON WORKFLOWTRANSICAO.perfil_id = PERFIL.perfil_id) " +
				"		WHERE CONTRATO.contrato_id = ? AND PERFIL.perfil_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<Etapa> workflowsEtapa = new ArrayList<Etapa>();

		try {

			this.stmt = conn.prepareStatement(sql);
			
			//System.out.println(sql);

			this.stmt.setLong(1, contrato_id);
			this.stmt.setLong(2, perfil_id);

			this.rsEtapa = this.stmt.executeQuery();

			while (rsEtapa.next()) {

				Etapa etapa = new Etapa();

				etapa.setEtapa_id(rsEtapa.getLong("etapaproximo_id"));
				etapa.setNome(rsEtapa.getString("etapa_nome"));

				workflowsEtapa.add(etapa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsEtapa, stmt, conn);

		return workflowsEtapa;

	}

	public Collection<Etapa> buscaEtapaByWorkFlowPerfil(Long workflow_id,Long perfil_id) {

		String sql = "SELECT DISTINCT WORKFLOWTRANSICAO.etapaproximo_id, ETAPA.nome as etapa_nome  " +
							" FROM ((( ETAPA " +
						" INNER JOIN WORKFLOWETAPA (NOLOCK) ON WORKFLOWETAPA.etapa_id = ETAPA.etapa_id) " +    
						" INNER JOIN WORKFLOWTRANSICAO  ON ETAPA.etapa_id = WORKFLOWTRANSICAO.etapaproximo_id) " +   
						" INNER JOIN PERFIL ON WORKFLOWTRANSICAO.perfil_id = PERFIL.perfil_id) " +   
						" INNER JOIN WORKFLOW ON WORKFLOWETAPA.workflow_id = WORKFLOW.workflow_id " +  
					" WHERE WORKFLOW.workflow_id = ? AND PERFIL.perfil_id = ? "; 

		this.conn = this.conexao.getConexao();

		Collection<Etapa> workflowsEtapa = new ArrayList<Etapa>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, workflow_id);
			this.stmt.setLong(2, perfil_id);

			this.rsEtapa = this.stmt.executeQuery();

			while (rsEtapa.next()) {

				Etapa etapa = new Etapa();

				etapa.setEtapa_id(rsEtapa.getLong("etapaproximo_id"));
				etapa.setNome(rsEtapa.getString("etapa_nome"));

				workflowsEtapa.add(etapa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsEtapa, stmt, conn);

		return workflowsEtapa;

	}

	public Collection<Etapa> buscaEtapaByHisconPerfil(Long empresa_id, Long organizacao_id, Long perfil_id, Long hisconbeneficio_id) {

		String sql = " SELECT HISCONBENEFICIO.hisconbeneficio_id, HISCONBENEFICIO.empresa_id, HISCONBENEFICIO.organizacao_id, ETAPA.nome as etapa_nome," +
				" WORKFLOWTRANSICAO.etapaproximo_id " +
				" FROM ETAPA (NOLOCK) INNER JOIN (((HISCONBENEFICIO (NOLOCK) " +
				" INNER JOIN EMPRESA (NOLOCK) ON HISCONBENEFICIO.empresa_id = EMPRESA.empresa_id) " +
				" INNER JOIN ORGANIZACAO (NOLOCK) ON HISCONBENEFICIO.organizacao_id = ORGANIZACAO.organizacao_id) " +
				" INNER JOIN (PERFIL (NOLOCK) INNER JOIN WORKFLOWTRANSICAO (NOLOCK) ON PERFIL.perfil_id = WORKFLOWTRANSICAO.perfil_id) " +
				" ON HISCONBENEFICIO.etapa_id = WORKFLOWTRANSICAO.etapa_id) " +
				" ON ETAPA.etapa_id = WORKFLOWTRANSICAO.etapaproximo_id " +
				" WHERE HISCONBENEFICIO.hisconbeneficio_id = ? AND HISCONBENEFICIO.empresa_id = ? AND HISCONBENEFICIO.organizacao_id = ? AND PERFIL.perfil_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<Etapa> workflowsEtapa = new ArrayList<Etapa>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, hisconbeneficio_id);
			this.stmt.setLong(2, empresa_id);
			this.stmt.setLong(3, organizacao_id);
			this.stmt.setLong(4, perfil_id);

			this.rsEtapa = this.stmt.executeQuery();

			while (rsEtapa.next()) {

				Etapa etapa = new Etapa();

				etapa.setEtapa_id(rsEtapa.getLong("etapaproximo_id"));
				etapa.setNome(rsEtapa.getString("etapa_nome"));

				workflowsEtapa.add(etapa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsEtapa, stmt, conn);

		return workflowsEtapa;

	}
	
	public Collection<Etapa> buscaPosicaoByHisconPerfil(Long empresa_id, Long organizacao_id, Long perfil_id, Long hisconbeneficio_id) {

		String sql = " SELECT HISCONBENEFICIO.hisconbeneficio_id, HISCONBENEFICIO.empresa_id, HISCONBENEFICIO.organizacao_id, ETAPA.nome as etapa_nome," +
				" WORKFLOWTRANSICAO.etapaproximo_id " +
				" FROM ETAPA (NOLOCK) INNER JOIN (((HISCONBENEFICIO (NOLOCK) " +
				" INNER JOIN EMPRESA (NOLOCK) ON HISCONBENEFICIO.empresa_id = EMPRESA.empresa_id) " +
				" INNER JOIN ORGANIZACAO (NOLOCK) ON HISCONBENEFICIO.organizacao_id = ORGANIZACAO.organizacao_id) " +
				" INNER JOIN (PERFIL (NOLOCK) INNER JOIN WORKFLOWTRANSICAO (NOLOCK) ON PERFIL.perfil_id = WORKFLOWTRANSICAO.perfil_id) " +
				" ON HISCONBENEFICIO.etapaposicao_id = WORKFLOWTRANSICAO.etapa_id) " +
				" ON ETAPA.etapa_id = WORKFLOWTRANSICAO.etapaproximo_id " +
				" WHERE HISCONBENEFICIO.hisconbeneficio_id = ? AND HISCONBENEFICIO.empresa_id = ? AND HISCONBENEFICIO.organizacao_id = ? AND PERFIL.perfil_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<Etapa> workflowsEtapa = new ArrayList<Etapa>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, hisconbeneficio_id);
			this.stmt.setLong(2, empresa_id);
			this.stmt.setLong(3, organizacao_id);
			this.stmt.setLong(4, perfil_id);

			this.rsEtapa = this.stmt.executeQuery();

			while (rsEtapa.next()) {

				Etapa etapa = new Etapa();

				etapa.setEtapa_id(rsEtapa.getLong("etapaproximo_id"));
				etapa.setNome(rsEtapa.getString("etapa_nome"));

				workflowsEtapa.add(etapa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsEtapa, stmt, conn);

		return workflowsEtapa;

	}
	
	public Collection<Etapa> buscaEtapaByPerfil(Long empresa_id, Long organizacao_id, Long perfil_id) {

		String sql = "SELECT HISCONBENEFICIO.hisconbeneficio_id, HISCONBENEFICIO.empresa_id, HISCONBENEFICIO.organizacao_id, ETAPA.nome as etapa_nome " +
				" FROM ETAPA (NOLOCK) INNER JOIN (((HISCONBENEFICIO (NOLOCK) " +
				" INNER JOIN EMPRESA (NOLOCK) ON HISCONBENEFICIO.empresa_id = EMPRESA.empresa_id) " +
				" INNER JOIN ORGANIZACAO (NOLOCK) ON HISCONBENEFICIO.organizacao_id = ORGANIZACAO.organizacao_id) " +
				" INNER JOIN (PERFIL (NOLOCK) INNER JOIN WORKFLOWTRANSICAO (NOLOCK) ON PERFIL.perfil_id = WORKFLOWTRANSICAO.perfil_id) " +
				" ON HISCONBENEFICIO.etapa_id = WORKFLOWTRANSICAO.etapa_id) " +
				" ON ETAPA.etapa_id = WORKFLOWTRANSICAO.etapaproximo_id " +
				" WHERE HISCONBENEFICIO.empresa_id = ? AND HISCONBENEFICIO.organizacao_id = ? AND PERFIL.perfil_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<Etapa> workflowsEtapa = new ArrayList<Etapa>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, perfil_id);

			this.rsEtapa = this.stmt.executeQuery();

			while (rsEtapa.next()) {

				Etapa etapa = new Etapa();

				etapa.setEtapa_id(rsEtapa.getLong("etapaproximo_id"));
				etapa.setNome(rsEtapa.getString("etapa_nome"));

				workflowsEtapa.add(etapa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsEtapa, stmt, conn);

		return workflowsEtapa;

	}
	
	public Collection<Etapa> buscaEtapaByHisconBeneficioPerfil(Long empresa_id, Long organizacao_id, Long perfil_id) {

		String sql = " SELECT WORKFLOWTRANSICAO.workflowtransicao_id, WORKFLOWTRANSICAO.empresa_id , EMPRESA.nome as empresa_nome "+
				", WORKFLOWTRANSICAO.organizacao_id, ORGANIZACAO.nome as organizacao_nome , WORKFLOWTRANSICAO.etapa_id "+
				", WT1.nome as etapa_nome, WORKFLOWTRANSICAO.etapaproximo_id, WT2.nome as etapaproximo_nome "+
				", PERFIL.perfil_id, PERFIL.nome as PERFIL_nome "+
				", WORKFLOWTRANSICAO.sequencia, WORKFLOWTRANSICAO.ispadrao, WORKFLOWTRANSICAO.isactive "+ 
				" FROM (((WORKFLOWTRANSICAO (NOLOCK) INNER JOIN PERFIL (NOLOCK) ON WORKFLOWTRANSICAO.perfil_id = PERFIL.perfil_id) "+ 
				" INNER JOIN EMPRESA (NOLOCK) ON WORKFLOWTRANSICAO.empresa_id = EMPRESA.empresa_id) " +
				" INNER JOIN ORGANIZACAO (NOLOCK) ON WORKFLOWTRANSICAO.organizacao_id = ORGANIZACAO.organizacao_id) " +
				" INNER JOIN ETAPA (NOLOCK) AS WT1 ON (WORKFLOWTRANSICAO.etapa_id = WT1.etapa_id) "+ 
				" INNER JOIN ETAPA (NOLOCK) AS WT2 ON (WORKFLOWTRANSICAO.etapaproximo_id = WT2.etapa_id) "+
				"	WHERE WORKFLOWTRANSICAO.empresa_id = ? AND WORKFLOWTRANSICAO.organizacao_id = ? "+
				" AND PERFIL.perfil_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<Etapa> workflowsEtapa = new ArrayList<Etapa>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, perfil_id);

			this.rsEtapa = this.stmt.executeQuery();

			while (rsEtapa.next()) {

				Etapa etapa = new Etapa();

				etapa.setEtapa_id(rsEtapa.getLong("etapaproximo_id"));
				etapa.setNome(rsEtapa.getString("etapaproximo_nome"));

				workflowsEtapa.add(etapa);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsEtapa, stmt, conn);

		return workflowsEtapa;

	}	
	
	private void getEtapa(Collection<Etapa> etapas)	throws SQLException {

		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();
		Etapa etapa = new Etapa();

		empresa.setEmpresa_id(rsEtapa.getLong("empresa_id"));
		empresa.setNome(rsEtapa.getString("empresa_nome"));

		organizacao.setOrganizacao_id(rsEtapa.getLong("organizacao_id"));
		organizacao.setNome(rsEtapa.getString("organizacao_nome"));

		etapa.setEmpresa(empresa);
		etapa.setOrganizacao(organizacao);
		etapa.setEtapa_id(rsEtapa.getLong("etapa_id"));
		etapa.setNome(rsEtapa.getString("etapa_nome"));
		etapa.setIsPadrao(rsEtapa.getBoolean("ispadrao"));
		etapa.setIsActive(rsEtapa.getBoolean("isactive"));

		etapas.add(etapa);

	}

}