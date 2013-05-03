package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.HisconBeneficio;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.ParceiroBeneficio;
import br.com.sgo.modelo.ParceiroNegocio;
import br.com.sgo.modelo.Usuario;
import br.com.sgo.modelo.Workflow;

@Component
public class HisconBeneficioDao extends Dao<HisconBeneficio> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsHisconBeneficio;
	private String sqlHisconBeneficio = "SELECT HISCONBENEFICIO.hisconbeneficio_id, HISCONBENEFICIO.empresa_id, HISCONBENEFICIO.organizacao_id, HISCONBENEFICIO.parceirobeneficio_id"
			+ ", HISCONBENEFICIO.usuario_id, HISCONBENEFICIO.workflowetapa_id, HISCONBENEFICIO.workflow_id, HISCONBENEFICIO.workflowposicao_id, HISCONBENEFICIO.isactive"
			+ ", HISCONBENEFICIO.isworkflow, HISCONBENEFICIO.isenviado, HISCONBENEFICIO.isimportado, HISCONBENEFICIO.ispadrao FROM HISCONBENEFICIO(NOLOCK)";

	private String sqlHisconsBeneficio = "SELECT HISCONBENEFICIO.hisconbeneficio_id, HISCONBENEFICIO.empresa_id, EMPRESA.nome as empresa_nome "
			+ ", HISCONBENEFICIO.organizacao_id, ORGANIZACAO.nome as organizacao_nome, HISCONBENEFICIO.parceirobeneficio_id , PARCEIROBENEFICIO.numerobeneficio "
			+ ", HISCONBENEFICIO.usuario_id, USUARIO.nome as usuario_nome,PERFIL.perfil_id, PERFIL.nome "
			+ ", HISCONBENEFICIO.workflow_id, WORKFLOWETAPA.nome AS workflowetapa_nome, PARCEIRONEGOCIO.parceironegocio_id, PARCEIRONEGOCIO.cpf "
			+ ", PARCEIRONEGOCIO.nome as parceironegocio_nome, HISCONBENEFICIO.created, HISCONBENEFICIO.updated "
			+ ", HISCONBENEFICIO.dataadm, HISCONBENEFICIO.dataenvio, HISCONBENEFICIO.caminhoarquivo "
			+ ", HISCONBENEFICIO.isworkflow, HISCONBENEFICIO.isenviado, HISCONBENEFICIO.isimportado, HISCONBENEFICIO.ispadrao "
			+ " FROM (PERFIL (NOLOCK) INNER JOIN (((((PARCEIRONEGOCIO (NOLOCK) INNER JOIN (HISCONBENEFICIO (NOLOCK) "
			+ " INNER JOIN PARCEIROBENEFICIO (NOLOCK) ON HISCONBENEFICIO.parceirobeneficio_id = PARCEIROBENEFICIO.parceirobeneficio_id) ON PARCEIRONEGOCIO.parceironegocio_id = PARCEIROBENEFICIO.parceironegocio_id) "
			+ " INNER JOIN EMPRESA (NOLOCK) ON HISCONBENEFICIO.empresa_id = EMPRESA.empresa_id) "
			+ " INNER JOIN ORGANIZACAO (NOLOCK) ON HISCONBENEFICIO.organizacao_id = ORGANIZACAO.organizacao_id) "
			+ " INNER JOIN USUARIO (NOLOCK) ON HISCONBENEFICIO.usuario_id = USUARIO.usuario_id) "
			+ " INNER JOIN USUARIOPERFIL (NOLOCK) ON USUARIO.usuario_id = USUARIOPERFIL.usuario_id) ON PERFIL.perfil_id = USUARIOPERFIL.perfil_id) "
			+ " INNER JOIN WORKFLOWETAPA (NOLOCK) ON (HISCONBENEFICIO.workflowetapa_id = WORKFLOWETAPA.workflowetapa_id) AND (HISCONBENEFICIO.workflow_id = WORKFLOWETAPA.workflow_id)";

	private String sqlHisconsExibe = "SELECT HISCONBENEFICIO.hisconbeneficio_id, HISCONBENEFICIO.empresa_id, EMPRESA.nome as empresa_nome " + 
			 ", HISCONBENEFICIO.organizacao_id, ORGANIZACAO.nome as organizacao_nome, HISCONBENEFICIO.parceirobeneficio_id , PARCEIROBENEFICIO.numerobeneficio " + 
			 ", HISCONBENEFICIO.usuario_id, USUARIO.nome as usuario_nome,PERFIL.perfil_id, PERFIL.nome " +
			 ", HISCONBENEFICIO.workflow_id, PARCEIRONEGOCIO.parceironegocio_id, PARCEIRONEGOCIO.cpf " +
			 ", PARCEIRONEGOCIO.nome as parceironegocio_nome, HISCONBENEFICIO.created, HISCONBENEFICIO.updated " + 
			 ", HISCONBENEFICIO.dataadm, HISCONBENEFICIO.dataenvio, HISCONBENEFICIO.caminhoarquivo " +
			 ", HISCONBENEFICIO.isworkflow, HISCONBENEFICIO.isenviado, HISCONBENEFICIO.isimportado, HISCONBENEFICIO.ispadrao " +
				" FROM ((PERFIL (NOLOCK) INNER JOIN ((((HISCONBENEFICIO (NOLOCK) " +
				" INNER JOIN EMPRESA (NOLOCK) ON HISCONBENEFICIO.empresa_id = EMPRESA.empresa_id) " + 
				" INNER JOIN ORGANIZACAO (NOLOCK) ON HISCONBENEFICIO.organizacao_id = ORGANIZACAO.organizacao_id) " + 
				" INNER JOIN USUARIO (NOLOCK) ON HISCONBENEFICIO.usuario_id = USUARIO.usuario_id) " +
				" INNER JOIN USUARIOPERFIL (NOLOCK) ON USUARIO.usuario_id = USUARIOPERFIL.usuario_id) ON (PERFIL.perfil_id = HISCONBENEFICIO.perfil_id) AND (PERFIL.perfil_id = USUARIOPERFIL.perfil_id)) " + 
				" INNER JOIN PARCEIROBENEFICIO (NOLOCK) ON HISCONBENEFICIO.parceirobeneficio_id = PARCEIROBENEFICIO.parceirobeneficio_id) " +
				" INNER JOIN PARCEIRONEGOCIO (NOLOCK) ON PARCEIROBENEFICIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id";
	
	private String sqlCountHiscons = "SELECT COUNT(HISCONBENEFICIO.parceirobeneficio_id) AS quantidade_beneficio , PARCEIROBENEFICIO.parceirobeneficio_id, PARCEIROBENEFICIO.numerobeneficio, HISCONBENEFICIO.empresa_id, HISCONBENEFICIO.organizacao_id "+
			" FROM ((PERFIL (NOLOCK) INNER JOIN ((((HISCONBENEFICIO (NOLOCK) "+
			" INNER JOIN EMPRESA (NOLOCK) ON HISCONBENEFICIO.empresa_id = EMPRESA.empresa_id) "+ 
			" INNER JOIN ORGANIZACAO (NOLOCK) ON HISCONBENEFICIO.organizacao_id = ORGANIZACAO.organizacao_id) "+
			" INNER JOIN USUARIO (NOLOCK) ON HISCONBENEFICIO.usuario_id = USUARIO.usuario_id) "+
			" INNER JOIN USUARIOPERFIL (NOLOCK) ON USUARIO.usuario_id = USUARIOPERFIL.usuario_id) ON (PERFIL.perfil_id = HISCONBENEFICIO.perfil_id) AND (PERFIL.perfil_id = USUARIOPERFIL.perfil_id)) "+ 
			" INNER JOIN PARCEIROBENEFICIO (NOLOCK) ON HISCONBENEFICIO.parceirobeneficio_id = PARCEIROBENEFICIO.parceirobeneficio_id) "+
			" INNER JOIN PARCEIRONEGOCIO (NOLOCK) ON PARCEIROBENEFICIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id ";
			
	private String sqlHisconsToUpload = " SELECT HISCONBENEFICIO.parceirobeneficio_id, PARCEIROBENEFICIO.numerobeneficio " +
			", HISCONBENEFICIO.empresa_id, HISCONBENEFICIO.organizacao_id, HISCONBENEFICIO.hisconbeneficio_id "+
			" FROM ((PERFIL (NOLOCK) INNER JOIN ((((HISCONBENEFICIO (NOLOCK) "+
			" INNER JOIN EMPRESA (NOLOCK) ON HISCONBENEFICIO.empresa_id = EMPRESA.empresa_id) "+ 
			" INNER JOIN ORGANIZACAO (NOLOCK) ON HISCONBENEFICIO.organizacao_id = ORGANIZACAO.organizacao_id) "+
			" INNER JOIN USUARIO (NOLOCK) ON HISCONBENEFICIO.usuario_id = USUARIO.usuario_id) "+
			" INNER JOIN USUARIOPERFIL (NOLOCK) ON USUARIO.usuario_id = USUARIOPERFIL.usuario_id) ON (PERFIL.perfil_id = HISCONBENEFICIO.perfil_id) AND (PERFIL.perfil_id = USUARIOPERFIL.perfil_id)) "+ 
			" INNER JOIN PARCEIROBENEFICIO (NOLOCK) ON HISCONBENEFICIO.parceirobeneficio_id = PARCEIROBENEFICIO.parceirobeneficio_id) "+
			" INNER JOIN PARCEIRONEGOCIO (NOLOCK) ON PARCEIROBENEFICIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id ";

	public HisconBeneficioDao(Session session, ConnJDBC conexao) {
		super(session, HisconBeneficio.class);
		this.conexao = conexao;
	}

	public HisconBeneficio validaHisconBeneficioPorParceiroBeneficioUsuarioWorkflow(Long empresa_id, Long organizacao_id, Long parceirobeneficio_id, Long usuario_id, Long workflow_id) {

		String sql = sqlHisconBeneficio;

		if (empresa_id != null)
			sql += " WHERE HISCONBENEFICIO.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND HISCONBENEFICIO.organizacao_id = ? ";
		if (parceirobeneficio_id != null)
			sql += " AND HISCONBENEFICIO.parceirobeneficio_id = ? ";
		if (usuario_id != null)
			sql += " AND HISCONBENEFICIO.usuario_id = ? ";
		if (workflow_id != null)
			sql += " AND HISCONBENEFICIO.workflow_id = ? ";

		this.conn = this.conexao.getConexao();

		HisconBeneficio hisconbeneficio = new HisconBeneficio();

		try {
			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, hisconbeneficio.getEmpresa().getEmpresa_id());
			this.stmt.setLong(2, hisconbeneficio.getOrganizacao().getOrganizacao_id());
			this.stmt.setLong(3, hisconbeneficio.getParceiroBeneficio().getParceiroBeneficio_id());
			this.stmt.setLong(4, hisconbeneficio.getUsuario().getUsuario_id());
			this.stmt.setLong(5, hisconbeneficio.getWorkflow().getWorkflow_id());

			this.rsHisconBeneficio = this.stmt.executeQuery();
			
			while (rsHisconBeneficio.next()) {
				
				hisconbeneficio.setHisconBeneficio_id(rsHisconBeneficio.getLong("hisconbeneficio_id"));
				
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			try {
				if (rsHisconBeneficio != null || stmt != null || conn != null) {

					this.conexao.closeConnection(rsHisconBeneficio, stmt, conn);

				}
			} catch (Exception e) {

				e.printStackTrace();

			}
		}

		return hisconbeneficio;
	}

	public HisconBeneficio validaHisconBeneficioPorParceiroBeneficioUsuario(Long empresa_id, Long organizacao_id, Long parceirobeneficio_id, Long usuario_id) {

		String sql = sqlHisconBeneficio;

		if (empresa_id != null)
			sql += " WHERE HISCONBENEFICIO.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND HISCONBENEFICIO.organizacao_id = ? ";
		if (parceirobeneficio_id != null)
			sql += " AND HISCONBENEFICIO.parceirobeneficio_id = ? ";
		if (usuario_id != null)
			sql += " AND HISCONBENEFICIO.usuario_id = ? ";

		this.conn = this.conexao.getConexao();

		HisconBeneficio hisconbeneficio = new HisconBeneficio();

		try {

			this.stmt = conn.prepareStatement(sql);
			
			this.stmt.setLong(1, hisconbeneficio.getEmpresa().getEmpresa_id());
			this.stmt.setLong(2, hisconbeneficio.getOrganizacao().getOrganizacao_id());
			this.stmt.setLong(3, hisconbeneficio.getParceiroBeneficio().getParceiroBeneficio_id());
			this.stmt.setLong(4, hisconbeneficio.getUsuario().getUsuario_id());
			
			this.rsHisconBeneficio = this.stmt.executeQuery();
			
			while (rsHisconBeneficio.next()) {

				hisconbeneficio.setHisconBeneficio_id(rsHisconBeneficio.getLong("hisconbeneficio_id"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rsHisconBeneficio != null || stmt != null || conn != null) {
					this.conexao.closeConnection(rsHisconBeneficio, stmt, conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return hisconbeneficio;
	}

	public HisconBeneficio validaHisconBeneficioPorParceiroBeneficio(Long empresa_id, Long organizacao_id, Long parceirobeneficio_id) {

		String sql = sqlHisconBeneficio;

		if (empresa_id != null)
			sql += " WHERE HISCONBENEFICIO.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND HISCONBENEFICIO.organizacao_id = ? ";
		if (parceirobeneficio_id != null)
			sql += " AND HISCONBENEFICIO.parceirobeneficio_id = ? ";

		this.conn = this.conexao.getConexao();

		HisconBeneficio hisconbeneficio = new HisconBeneficio();

		try {
			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, hisconbeneficio.getEmpresa().getEmpresa_id());
			this.stmt.setLong(2, hisconbeneficio.getOrganizacao().getOrganizacao_id());
			this.stmt.setLong(3, hisconbeneficio.getParceiroBeneficio().getParceiroBeneficio_id());

			this.rsHisconBeneficio = this.stmt.executeQuery();

			while (rsHisconBeneficio.next()) {

				hisconbeneficio.setHisconBeneficio_id(rsHisconBeneficio.getLong("hisconbeneficio_id"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			try {
				if (rsHisconBeneficio != null || stmt != null || conn != null) {

					this.conexao.closeConnection(rsHisconBeneficio, stmt, conn);

				}

			} catch (Exception e) {

				e.printStackTrace();

			}
		}

		return hisconbeneficio;
	}

	public HisconBeneficio mostraHisconBeneficios(ParceiroBeneficio parceiroBeneficio) {

		String sql = sqlHisconsBeneficio;

		if (parceiroBeneficio.getEmpresa().getEmpresa_id() != null)
			sql += " WHERE HISCONBENEFICIO.empresa_id = ? ";
		if (parceiroBeneficio.getOrganizacao().getOrganizacao_id() != null)
			sql += " AND HISCONBENEFICIO.organizacao_id = ? ";
		if (parceiroBeneficio.getParceiroBeneficio_id() != null)
			sql += " AND HISCONBENEFICIO.parceirobeneficio_id = ? ";

		this.conn = this.conexao.getConexao();

		HisconBeneficio hisconBeneficio = null;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, parceiroBeneficio.getEmpresa().getEmpresa_id());
			this.stmt.setLong(2, parceiroBeneficio.getOrganizacao().getOrganizacao_id());
			this.stmt.setLong(3, parceiroBeneficio.getParceiroBeneficio_id());

			this.rsHisconBeneficio = this.stmt.executeQuery();

			while (rsHisconBeneficio.next()) {

				hisconBeneficio = new HisconBeneficio();
				parceiroBeneficio.setParceiroBeneficio_id(rsHisconBeneficio.getLong("parceirobeneficio_id"));				
				hisconBeneficio.setParceiroBeneficio(parceiroBeneficio);
				Usuario usuario = new Usuario();
				usuario.setUsuario_id(rsHisconBeneficio.getLong("usuario_id"));
				usuario.setNome(rsHisconBeneficio.getString("usuario_nome"));
				hisconBeneficio.setUsuario(usuario);

				hisconBeneficio.setIsEnviado(rsHisconBeneficio.getBoolean("isenviado"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rsHisconBeneficio != null || stmt != null || conn != null) {
					this.conexao.closeConnection(rsHisconBeneficio, stmt, conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return hisconBeneficio;
	}
	
	public Collection<HisconBeneficio> mostraHisconBeneficios(Long empresa_id, Long organizacao_id, Long parceirobeneficio_id) {

		String sql = sqlHisconsBeneficio;

		if (empresa_id != null)
			sql += " WHERE HISCONBENEFICIO.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND HISCONBENEFICIO.organizacao_id = ? ";
		if (parceirobeneficio_id != null)
			sql += " AND HISCONBENEFICIO.parceirobeneficio_id = ? ";

		this.conn = this.conexao.getConexao();
		Collection<HisconBeneficio> hisconsBeneficio = new ArrayList<HisconBeneficio>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, parceirobeneficio_id);

			this.rsHisconBeneficio = this.stmt.executeQuery();

			while (rsHisconBeneficio.next()) {

				Empresa empresa = new Empresa();
				empresa.setEmpresa_id(rsHisconBeneficio.getLong("empresa_id"));
				empresa.setNome(rsHisconBeneficio.getString("empresa_nome"));

				Organizacao organizacao = new Organizacao();
				organizacao.setOrganizacao_id(rsHisconBeneficio.getLong("organizacao_id"));
				organizacao.setNome(rsHisconBeneficio.getString("organizacao_nome"));

				ParceiroBeneficio parceirobeneficio = new ParceiroBeneficio();
				parceirobeneficio.setParceiroBeneficio_id(rsHisconBeneficio.getLong("parceirobeneficio_id"));
				parceirobeneficio.setNumeroBeneficio(rsHisconBeneficio.getString("numerobeneficio"));

				ParceiroNegocio parceiro = new ParceiroNegocio();
				parceiro.setParceiroNegocio_id(rsHisconBeneficio.getLong("parceironegocio_id"));
				parceiro.setNome(rsHisconBeneficio.getString("parceironegocio_nome"));
				parceiro.setCpf(rsHisconBeneficio.getString("cpf"));

				parceirobeneficio.setParceiroNegocio(parceiro);

				Usuario usuario = new Usuario();
				usuario.setUsuario_id(rsHisconBeneficio.getLong("usuario_id"));
				usuario.setNome(rsHisconBeneficio.getString("usuario_nome"));

				Workflow workflow = new Workflow();
				workflow.setWorkflow_id(rsHisconBeneficio.getLong("workflow_id"));
				workflow.setNome(rsHisconBeneficio.getString("workflow_nome"));

				HisconBeneficio hisconbeneficio = new HisconBeneficio();
				hisconbeneficio.setEmpresa(empresa);
				hisconbeneficio.setOrganizacao(organizacao);
				hisconbeneficio.setParceiroBeneficio(parceirobeneficio);
				hisconbeneficio.setUsuario(usuario);
				hisconbeneficio.setWorkflow(workflow);

				Calendar cal = new GregorianCalendar();
				cal.setTime(rsHisconBeneficio.getDate("created"));
				//hisconbeneficio.setCreated(cal);

				cal.setTime(rsHisconBeneficio.getDate("updated"));
				hisconbeneficio.setUpdated(cal);

				cal.setTime(rsHisconBeneficio.getDate("dataadm"));
				hisconbeneficio.setDataAdm(cal);

				cal.setTime(rsHisconBeneficio.getDate("dataenvio"));
				hisconbeneficio.setDataEnvio(cal);

				hisconbeneficio.setCaminhoArquivo(rsHisconBeneficio.getString("caminhoarquivo"));

				hisconsBeneficio.add(hisconbeneficio);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rsHisconBeneficio != null || stmt != null || conn != null) {
					this.conexao.closeConnection(rsHisconBeneficio, stmt, conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return hisconsBeneficio;
	}

	public Collection<HisconBeneficio> mostraHisconBeneficiosPorUsuarioPerfil(Long empresa_id, Long organizacao_id, Long perfil_id,Long usuario_id) {

		String sql = sqlHisconsExibe;

		if (empresa_id != null)
			sql += " WHERE HISCONBENEFICIO.empresa_id = ? ";

		if (organizacao_id != null)
			sql += " AND HISCONBENEFICIO.organizacao_id = ? ";

		if (perfil_id != null)
			sql += " AND PERFIL.perfil_id = ? ";

		if (usuario_id != null)
			sql += " AND HISCONBENEFICIO.usuario_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<HisconBeneficio> hisconsBeneficio = new ArrayList<HisconBeneficio>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, perfil_id);
			this.stmt.setLong(4, usuario_id);

			this.rsHisconBeneficio = this.stmt.executeQuery();

			while (rsHisconBeneficio.next()) {

				Empresa empresa = new Empresa();
				empresa.setEmpresa_id(rsHisconBeneficio.getLong("empresa_id"));
				empresa.setNome(rsHisconBeneficio.getString("empresa_nome"));

				Organizacao organizacao = new Organizacao();
				organizacao.setOrganizacao_id(rsHisconBeneficio.getLong("organizacao_id"));
				organizacao.setNome(rsHisconBeneficio.getString("organizacao_nome"));

				ParceiroBeneficio parceiroBeneficio = new ParceiroBeneficio();

				ParceiroNegocio parceiro = new ParceiroNegocio();
				parceiro.setParceiroNegocio_id(rsHisconBeneficio.getLong("parceironegocio_id"));
				parceiro.setNome(rsHisconBeneficio.getString("parceironegocio_nome"));
				parceiro.setCpf(rsHisconBeneficio.getString("cpf"));

				parceiroBeneficio.setParceiroNegocio(parceiro);

				parceiroBeneficio.setParceiroBeneficio_id(rsHisconBeneficio.getLong("parceirobeneficio_id"));
				parceiroBeneficio.setNumeroBeneficio(rsHisconBeneficio.getString("numerobeneficio"));

				Usuario usuario = new Usuario();
				usuario.setUsuario_id(rsHisconBeneficio.getLong("usuario_id"));
				usuario.setNome(rsHisconBeneficio.getString("usuario_nome"));

				//WorkflowEtapa workflowEtapa = new WorkflowEtapa();
				//workflowEtapa.setWorkflowEtapa_id(rsHisconBeneficio.getLong("workflow_id"));
				//workflowEtapa.setNome(rsHisconBeneficio.getString("workflowetapa_nome"));

				HisconBeneficio hisconBeneficio = new HisconBeneficio();

				hisconBeneficio.setEmpresa(empresa);
				hisconBeneficio.setOrganizacao(organizacao);
				hisconBeneficio.setParceiroBeneficio(parceiroBeneficio);

				hisconBeneficio.setUsuario(usuario);
				//hisconBeneficio.setWorkflowEtapa(workflowEtapa);

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

				Calendar created = new GregorianCalendar();
				
				try {
					
					if (rsHisconBeneficio.getDate("created") != null) {
						created.setTime(sdf.parse(rsHisconBeneficio.getTimestamp("created").toString()));
						hisconBeneficio.setCreated(created);
					}

					if (rsHisconBeneficio.getDate("updated") != null){
						created.setTime(sdf.parse(rsHisconBeneficio.getTimestamp("updated").toString()));
						hisconBeneficio.setUpdated(sdf.getCalendar());
					}

					if (rsHisconBeneficio.getDate("dataadm") != null){
						created.setTime(sdf.parse(rsHisconBeneficio.getTimestamp("dataadm").toString())); 
						hisconBeneficio.setDataAdm(sdf.getCalendar());
					}

					if(rsHisconBeneficio.getDate("dataenvio")!=null){
						created.setTime(sdf.parse(rsHisconBeneficio.getTimestamp("dataenvio").toString()));
						hisconBeneficio.setDataEnvio(sdf.getCalendar());		
					}

					if (rsHisconBeneficio.getString("caminhoarquivo") != null) {
						hisconBeneficio.setCaminhoArquivo(rsHisconBeneficio.getString("caminhoarquivo"));
					}
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
				

				hisconsBeneficio.add(hisconBeneficio);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rsHisconBeneficio != null || stmt != null || conn != null) {
					this.conexao.closeConnection(rsHisconBeneficio, stmt, conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return hisconsBeneficio;
	}

	public Integer mostraCountHisconsBeneficios(Long empresa_id, Long organizacao_id, Long parceirobeneficio_id) {

		String sql = sqlCountHiscons;

		if (empresa_id != null)
			sql += " WHERE HISCONBENEFICIO.empresa_id = ? ";

		if (organizacao_id != null)
			sql += " AND HISCONBENEFICIO.organizacao_id = ? AND HISCONBENEFICIO.created BETWEEN getDate() -20 and getDate() "+
					" AND HISCONBENEFICIO.parceirobeneficio_id = ? GROUP BY PARCEIROBENEFICIO.numerobeneficio, PARCEIROBENEFICIO.parceirobeneficio_id, HISCONBENEFICIO.empresa_id, HISCONBENEFICIO.organizacao_id ";

		this.conn = this.conexao.getConexao();

		Integer count = 0;

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, parceirobeneficio_id);

			this.rsHisconBeneficio = this.stmt.executeQuery();

			while (rsHisconBeneficio.next()) {

				 count = rsHisconBeneficio.getInt("quantidade_beneficio");

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			try {
				if (rsHisconBeneficio != null || stmt != null || conn != null) {

					this.conexao.closeConnection(rsHisconBeneficio, stmt, conn);

				}

			} catch (Exception e) {

				e.printStackTrace();

			}
		}

		return count;

	}

	public Collection<HisconBeneficio> mostraHisconBeneficiosPorPerfil(Long empresa_id, Long organizacao_id, Long perfil_id) {

		String sql = sqlHisconsBeneficio;

		if (empresa_id != null)
			sql += " WHERE HISCONBENEFICIO.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND HISCONBENEFICIO.organizacao_id = ? ";
		if (perfil_id != null)
			sql += " AND PERFIL.perfil_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<HisconBeneficio> hisconsBeneficio = new ArrayList<HisconBeneficio>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);
			this.stmt.setLong(3, perfil_id);

			this.rsHisconBeneficio = this.stmt.executeQuery();

			while (rsHisconBeneficio.next()) {

				Empresa empresa = new Empresa();
				empresa.setEmpresa_id(rsHisconBeneficio.getLong("empresa_id"));
				empresa.setNome(rsHisconBeneficio.getString("empresa_nome"));

				Organizacao organizacao = new Organizacao();
				organizacao.setOrganizacao_id(rsHisconBeneficio.getLong("organizacao_id"));
				organizacao.setNome(rsHisconBeneficio.getString("organizacao_nome"));

				ParceiroBeneficio parceirobeneficio = new ParceiroBeneficio();
				parceirobeneficio.setParceiroBeneficio_id(rsHisconBeneficio.getLong("parceirobeneficio_id"));
				parceirobeneficio.setNumeroBeneficio(rsHisconBeneficio.getString("numerobeneficio"));

				ParceiroNegocio parceiro = new ParceiroNegocio();				
				parceiro.setParceiroNegocio_id(rsHisconBeneficio.getLong("parceironegocio_id"));
				parceiro.setNome(rsHisconBeneficio.getString("parceironegocio_nome"));
				parceiro.setCpf(rsHisconBeneficio.getString("cpf"));

				parceirobeneficio.setParceiroNegocio(parceiro);

				Usuario usuario = new Usuario();
				usuario.setUsuario_id(rsHisconBeneficio.getLong("usuario_id"));
				usuario.setNome(rsHisconBeneficio.getString("usuario_nome"));

				Workflow workflow = new Workflow();
				workflow.setWorkflow_id(rsHisconBeneficio.getLong("workflow_id"));
				workflow.setNome(rsHisconBeneficio.getString("workflow_nome"));

				HisconBeneficio hisconbeneficio = new HisconBeneficio();
				hisconbeneficio.setEmpresa(empresa);
				hisconbeneficio.setOrganizacao(organizacao);
				hisconbeneficio.setParceiroBeneficio(parceirobeneficio);
				hisconbeneficio.setUsuario(usuario);
				hisconbeneficio.setWorkflow(workflow);

				Calendar cal = new GregorianCalendar();
				SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy hh:mm");
				formater.format(rsHisconBeneficio.getDate("created").getTime());

				cal.setTime(rsHisconBeneficio.getDate("created"));
				hisconbeneficio.setCreated(cal);

				cal.setTime(rsHisconBeneficio.getDate("updated"));
				hisconbeneficio.setUpdated(cal);

				cal.setTime(rsHisconBeneficio.getDate("dataadm"));
				hisconbeneficio.setDataAdm(cal);

				cal.setTime(rsHisconBeneficio.getDate("dataenvio"));
				hisconbeneficio.setDataEnvio(cal);

				hisconbeneficio.setCaminhoArquivo(rsHisconBeneficio.getString("caminhoarquivo"));

				hisconsBeneficio.add(hisconbeneficio);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rsHisconBeneficio != null || stmt != null || conn != null) {
					this.conexao.closeConnection(rsHisconBeneficio, stmt, conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return hisconsBeneficio;

	}

	public Collection<HisconBeneficio> mostraHisconsToUpload(Long empresa_id, Long organizacao_id) {

		String sql = sqlHisconsToUpload;

		if (empresa_id != null)
			sql += " WHERE HISCONBENEFICIO.empresa_id = ? ";
		if (organizacao_id != null)
			sql += " AND HISCONBENEFICIO.organizacao_id = ? AND HISCONBENEFICIO.workflowetapa_id = 2";
		
		this.conn = this.conexao.getConexao();

		Collection<HisconBeneficio> hisconsBeneficio = new ArrayList<HisconBeneficio>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);			

			this.rsHisconBeneficio = this.stmt.executeQuery();

			while (rsHisconBeneficio.next()) {

				Empresa empresa = new Empresa();
				empresa.setEmpresa_id(rsHisconBeneficio.getLong("empresa_id"));

				Organizacao organizacao = new Organizacao();
				organizacao.setOrganizacao_id(rsHisconBeneficio.getLong("organizacao_id"));

				ParceiroBeneficio parceirobeneficio = new ParceiroBeneficio();
				parceirobeneficio.setParceiroBeneficio_id(rsHisconBeneficio.getLong("parceirobeneficio_id"));
				parceirobeneficio.setNumeroBeneficio(rsHisconBeneficio.getString("numerobeneficio"));

				HisconBeneficio hisconbeneficio = new HisconBeneficio();
				hisconbeneficio.setEmpresa(empresa);
				hisconbeneficio.setOrganizacao(organizacao);
				hisconbeneficio.setParceiroBeneficio(parceirobeneficio);
				hisconbeneficio.setHisconBeneficio_id(rsHisconBeneficio.getLong("hisconbeneficio_id"));

				hisconsBeneficio.add(hisconbeneficio);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rsHisconBeneficio != null || stmt != null || conn != null) {
					this.conexao.closeConnection(rsHisconBeneficio, stmt, conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return hisconsBeneficio;

	}
	
	
	
}