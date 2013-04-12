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
	private String sqlHisconBeneficio =	"SELECT HISCONBENEFICIO.hisconbeneficio_id, HISCONBENEFICIO.empresa_id, HISCONBENEFICIO.organizacao_id, HISCONBENEFICIO.parceirobeneficio_id" +
			", HISCONBENEFICIO.usuario_id, HISCONBENEFICIO.workflowetapa_id, HISCONBENEFICIO.workflow_id, HISCONBENEFICIO.workflowposicao_id, HISCONBENEFICIO.isactive" +
			", HISCONBENEFICIO.isworkflow, HISCONBENEFICIO.isenviado, HISCONBENEFICIO.isimportado, HISCONBENEFICIO.ispadrao FROM HISCONBENEFICIO(NOLOCK)";

	private String sqlHisconsBeneficio = "SELECT HISCONBENEFICIO.hisconbeneficio_id, HISCONBENEFICIO.empresa_id, EMPRESA.nome as empresa_nome" +
			", HISCONBENEFICIO.organizacao_id, ORGANIZACAO.nome as organizacao_nome, HISCONBENEFICIO.parceirobeneficio_id, PARCEIROBENEFICIO.numerobeneficio" +
			", HISCONBENEFICIO.usuario_id, USUARIO.nome as usuario_nome" +
			", HISCONBENEFICIO.workflow_id, WORKFLOW.nome as workflow_nome, PARCEIRONEGOCIO.parceironegocio_id, PARCEIRONEGOCIO.cpf" +
			", PARCEIRONEGOCIO.nome as parceironegocio_nome, HISCONBENEFICIO.created, HISCONBENEFICIO.updated" +
			", HISCONBENEFICIO.dataadm, HISCONBENEFICIO.dataenvio, HISCONBENEFICIO.caminhoarquivo" +
			", HISCONBENEFICIO.isworkflow, HISCONBENEFICIO.isenviado, HISCONBENEFICIO.isimportado, HISCONBENEFICIO.ispadrao" +			
			"FROM (((((HISCONBENEFICIO INNER JOIN EMPRESA ON HISCONBENEFICIO.empresa_id = EMPRESA.empresa_id)" + 
			"INNER JOIN ORGANIZACAO ON HISCONBENEFICIO.organizacao_id = ORGANIZACAO.organizacao_id)" +
			"INNER JOIN PARCEIROBENEFICIO ON HISCONBENEFICIO.parceirobeneficio_id = PARCEIROBENEFICIO.parceirobeneficio_id)" + 
			"INNER JOIN USUARIO ON HISCONBENEFICIO.usuario_id = USUARIO.usuario_id)" +
			"INNER JOIN WORKFLOW ON HISCONBENEFICIO.workflow_id = WORKFLOW.workflow_id)" +
			"INNER JOIN PARCEIRONEGOCIO ON PARCEIROBENEFICIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id";

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
		}
		this.conexao.closeConnection(rsHisconBeneficio, stmt, conn);
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
		}
		this.conexao.closeConnection(rsHisconBeneficio, stmt, conn);
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
		}
		this.conexao.closeConnection(rsHisconBeneficio, stmt, conn);
		return hisconbeneficio;
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
		Collection<HisconBeneficio> hisconbeneficios = new ArrayList<HisconBeneficio>();

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
				//hisconbeneficio.setCreated(rsHisconBeneficio.getString("created"));
				//hisconbeneficio.setUpdated(rsHisconBeneficio.getString("updated"));
				//hisconbeneficio.setDataAdm(rsHisconBeneficio.getString("dataadm"));
				//hisconbeneficio.setDataEnvio(rsHisconBeneficio.getString("dataenvio"));
				hisconbeneficio.setCaminhoArquivo(rsHisconBeneficio.getString("caminhoarquivo"));

				hisconbeneficios.add(hisconbeneficio);				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsHisconBeneficio, stmt, conn);
		return hisconbeneficios;
	}

}