package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Banco;
import br.com.sgo.modelo.Coeficiente;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Formulario;
import br.com.sgo.modelo.Logistica;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.ParceiroNegocio;
import br.com.sgo.modelo.Periodo;
import br.com.sgo.modelo.Produto;
import br.com.sgo.modelo.TipoLogistica;
import br.com.sgo.modelo.Usuario;
import br.com.sgo.modelo.Workflow;
import br.com.sgo.modelo.WorkflowEtapa;

@Component
public class ContratoDao extends Dao<Contrato> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsContrato;

	private static final String sqlContrato =  "SELECT CONTRATO.empresa_id, EMPRESA.nome as empresa_nome, CONTRATO.organizacao_id, ORGANIZACAO.nome as organizacao_nome,   " +
			"FORMULARIO.created,FORMULARIO.formulario_id, FORMULARIO.parceironegocio_id , CONTRATO.contrato_id,CONTRATO.formulario_id,   " +
			"CONTRATO.coeficiente_id, CONTRATO.workflow_id,  " +
			"CONTRATO.produto_id, CONTRATO.tabela_id,   " +
			"CONTRATO.banco_id, CONTRATO.recompra_banco_id,  " +
			"CONTRATO.usuario_id,  " +
			"USUARIO.nome as usuario_nome,  " +
			"SUPER.usuario_id as usuario_super_id, " +
			"SUPER.nome as usuario_super,  " +
			"CONTRATO.prazo,   " +
			"CONTRATO.qtdparcelasaberto, CONTRATO.valorseguro, CONTRATO.desconto, CONTRATO.valorcontrato,   " +
			"CONTRATO.valordivida, CONTRATO.valorliquido, CONTRATO.valorparcela, CONTRATO.valormeta, CONTRATO.observacao,   " +
			"CONTRATO.prazo , CONTRATO.desconto , CONTRATO.qtdparcelasaberto , CONTRATO.numerobeneficio, CONTRATO.isactive,   " +
			"B1.nome as banco_nome, B2.nome as bancoRecompra_nome , PRODUTO.nome as produto_nome, COEFICIENTE.valor,   " +
			"PARCEIRONEGOCIO.nome as parceiro_nome,PARCEIRONEGOCIO.cpf as parceiro_cpf,  " +
			"CONTRATO.workflowetapa_id, WORKFLOW.workflow_id,WORKFLOW.nome as workflow_nome ," +
			"WORKFLOWETAPA.nome as workflowetapa_nome, LOGISTICA.dataassinatura, LOGISTICA.logistica_id ," +
			" TIPOLOGISTICA.tipologistica_id, TIPOLOGISTICA.nome as tipologistica_nome  , PERIODO.periodo_id, PERIODO.nome as periodo_nome " +
			"FROM   " +
"((((((((((CONTRATO (NOLOCK) INNER JOIN EMPRESA (NOLOCK) ON CONTRATO.empresa_id = EMPRESA.empresa_id)  " +
				 "INNER JOIN ORGANIZACAO (NOLOCK) ON CONTRATO.organizacao_id = ORGANIZACAO.organizacao_id)" +
				 "INNER JOIN WORKFLOW (NOLOCK) ON CONTRATO.workflow_id = WORKFLOW.workflow_id )   " +
				 " LEFT JOIN LOGISTICA (NOLOCK) ON LOGISTICA.contrato_id = CONTRATO.contrato_id) " +
				 " LEFT JOIN TIPOLOGISTICA (NOLOCK) ON TIPOLOGISTICA.tipologistica_id = LOGISTICA.tipologistica_id " +
				 " LEFT JOIN PERIODO (NOLOCK) ON PERIODO.periodo_id = LOGISTICA.periodo_id " +
				 "INNER JOIN USUARIO (NOLOCK) ON CONTRATO.usuario_id = USUARIO.usuario_id)   " +
				 "INNER JOIN FORMULARIO (NOLOCK) ON CONTRATO.formulario_id = FORMULARIO.formulario_id)   " +
				 "INNER JOIN PARCEIRONEGOCIO (NOLOCK) ON FORMULARIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id)   " +
				 "INNER JOIN COEFICIENTE (NOLOCK) ON CONTRATO.coeficiente_id = COEFICIENTE.coeficiente_id)   " +
				 "INNER JOIN PRODUTO (NOLOCK) ON CONTRATO.produto_id = PRODUTO.produto_id)   " +
				 "INNER JOIN TABELA (NOLOCK) ON CONTRATO.tabela_id = TABELA.tabela_id)   " +
				 "INNER JOIN BANCO AS B1 (NOLOCK) ON CONTRATO.banco_id = B1.banco_id   " +
				 "INNER JOIN WORKFLOWETAPA (NOLOCK) ON CONTRATO.workflowetapa_id = WORKFLOWETAPA.workflowetapa_id " +
				 "INNER JOIN USUARIO as SUPER (NOLOCK) ON USUARIO.supervisor_usuario_id = SUPER.usuario_id   " +
				 "LEFT JOIN BANCO AS B2 (NOLOCK) ON CONTRATO.recompra_banco_id = B2.banco_id ";

	public ContratoDao(Session session, ConnJDBC conexao) {
		super(session, Contrato.class);
		this.conexao = conexao;
	}

	public Collection<Contrato> buscaContratoByUsuario(Long usuario_id) {

		String sql = sqlContrato;
		
		if(usuario_id != null)
			sql += " WHERE USUARIO.usuario_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<Contrato> contratos = new ArrayList<Contrato>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, usuario_id);

			this.rsContrato = this.stmt.executeQuery();

			while (rsContrato.next()) {
				getFormulario(contratos);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsContrato, stmt, conn);
		return contratos;
	}
	
	public Collection<Contrato> buscaContratoByEmpresaOrganizacao(Long empresa_id, Long organizacao_id) {

		String sql = sqlContrato;

		sql += " WHERE CONTRATO.empresa_id = ? AND CONTRATO.organizacao_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<Contrato> contratos = new ArrayList<Contrato>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			this.rsContrato = this.stmt.executeQuery();

			while (rsContrato.next()) {
				getFormulario(contratos);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsContrato, stmt, conn);
		return contratos;
	}
	
	public Collection<Contrato> buscaContratoByFormulario(Long formulario_id) {

		String sql = sqlContrato;
		
		if(formulario_id != null)
			sql += " WHERE FORMULARIO.formulario_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<Contrato> contratos = new ArrayList<Contrato>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, formulario_id);

			this.rsContrato = this.stmt.executeQuery();

			while (rsContrato.next()) {
				getFormulario(contratos);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsContrato, stmt, conn);
		return contratos;
	}

	public Contrato buscaContratoById(Long contrato_id) {

		String sql = sqlContrato;
		
		if(contrato_id != null)
			sql += " WHERE CONTRATO.contrato_id = ? ";

		this.conn = this.conexao.getConexao();

		Contrato contrato = null;

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, contrato_id);

			this.rsContrato = this.stmt.executeQuery();

			while (rsContrato.next()) {
				contrato = new Contrato();
				getFormulario(contrato);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsContrato, stmt, conn);
		return contrato;
	}

	private void getFormulario(Collection<Contrato> contratos)
			throws SQLException {
		
		Calendar created = new GregorianCalendar();
		Calendar dataAssinatura = new GregorianCalendar();
		Formulario formulario = new Formulario();
		Usuario usuario = new Usuario();
		Usuario supervisor = new Usuario();
		Contrato contrato = new Contrato();
		ParceiroNegocio parceiro = new ParceiroNegocio();
		Coeficiente coeficiente = new Coeficiente();
		Produto produto = new Produto();
		Workflow workflow = new Workflow();
		WorkflowEtapa workflowEtapa = new WorkflowEtapa();
		Logistica logistica = new Logistica();
		Periodo periodo = new Periodo();
		TipoLogistica tipoLogistica = new TipoLogistica();
		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();
		
		Banco b1 = new Banco();
		Banco b2 = new Banco();
		
		empresa.setEmpresa_id(rsContrato.getLong("empresa_id"));
		empresa.setNome(rsContrato.getString("empresa_nome"));
		
		organizacao.setOrganizacao_id(rsContrato.getLong("organizacao_id"));
		organizacao.setNome(rsContrato.getString("organizacao_nome"));

		usuario.setUsuario_id(rsContrato.getLong("usuario_id"));
		usuario.setNome(rsContrato.getString("usuario_nome"));
		supervisor.setUsuario_id(rsContrato.getLong("usuario_super_id"));
		supervisor.setNome(rsContrato.getString("usuario_super"));
		
		usuario.setSupervisorUsuario(supervisor);

		formulario.setFormulario_id(rsContrato.getLong("formulario_id"));
		created.setTime(rsContrato.getDate("created"));
		formulario.setCreated(created);

		contrato.setContrato_id(rsContrato.getLong("contrato_id"));
		
		parceiro.setParceiroNegocio_id(rsContrato.getLong("parceironegocio_id"));
		parceiro.setNome(rsContrato.getString("parceiro_nome"));
		parceiro.setCpf(rsContrato.getString("parceiro_cpf"));
		
		coeficiente.setCoeficiente_id(rsContrato.getLong("coeficiente_id"));
		coeficiente.setValor(rsContrato.getDouble("valor"));
		
		produto.setProduto_id(rsContrato.getLong("produto_id"));
		produto.setNome(rsContrato.getString("produto_nome"));
		
		b1.setBanco_id(rsContrato.getLong("banco_id"));
		b1.setNome(rsContrato.getString("banco_nome"));
		
		b2.setBanco_id(rsContrato.getLong("recompra_banco_id"));
		b2.setNome(rsContrato.getString("bancoRecompra_nome"));
		
		workflow.setWorkflow_id(rsContrato.getLong("workflow_id"));
		workflow.setNome(rsContrato.getString("workflow_nome"));

		workflowEtapa.setWorkflowEtapa_id(rsContrato.getLong("workflowetapa_id"));
		workflowEtapa.setNome(rsContrato.getString("workflowetapa_nome"));

		periodo.setPeriodo_id(rsContrato.getLong("periodo_id"));
		periodo.setNome(rsContrato.getString("periodo_nome"));

		tipoLogistica.setTipoLogistica_id(rsContrato.getLong("tipologistica_id"));
		tipoLogistica.setNome(rsContrato.getString("tipologistica_nome"));

		logistica.setLogistica_id(rsContrato.getLong("logistica_id"));
		logistica.setPeriodo(periodo);
		logistica.setTipoLogistica(tipoLogistica);

		if(rsContrato.getDate("dataassinatura") != null){
			dataAssinatura.setTime(rsContrato.getDate("dataassinatura"));
			logistica.setDataAssinatura(dataAssinatura);
		}
		
		

		formulario.setParceiroNegocio(parceiro);
		contrato.setFormulario(formulario);
		contrato.setCoeficiente(coeficiente);
		contrato.setEmpresa(empresa);
		contrato.setOrganizacao(organizacao);
		contrato.setProduto(produto);
		contrato.setUsuario(usuario);
		contrato.setBanco(b1);
		contrato.setRecompraBanco(b2);
		contrato.setWorkflowEtapa(workflowEtapa);
		contrato.setWorkflow(workflow);
		contrato.setIsActive(rsContrato.getBoolean("isactive"));
		contrato.setNumeroBeneficio(rsContrato.getString("numerobeneficio"));
		contrato.setValorContrato(rsContrato.getDouble("valorcontrato"));
		contrato.setValorDivida(rsContrato.getDouble("valordivida"));
		contrato.setValorLiquido(rsContrato.getDouble("valorliquido"));
		contrato.setValorMeta(rsContrato.getDouble("valormeta"));
		contrato.setValorParcela(rsContrato.getDouble("valorparcela"));
		contrato.setValorSeguro(rsContrato.getDouble("valorseguro"));
		contrato.setPrazo(rsContrato.getInt("prazo"));
		contrato.setQtdParcelasAberto(rsContrato.getInt("qtdparcelasaberto"));
		contrato.setLogistica(logistica);

		contratos.add(contrato);
	}
	
	private void getFormulario(Contrato contrato)
			throws SQLException {
		
		Calendar created = new GregorianCalendar();
		Calendar dataAssinatura = new GregorianCalendar();
		Formulario formulario = new Formulario();
		Usuario usuario = new Usuario();
		ParceiroNegocio parceiro = new ParceiroNegocio();
		Coeficiente coeficiente = new Coeficiente();
		Produto produto = new Produto();
		Workflow workflow = new Workflow();
		WorkflowEtapa workflowEtapa = new WorkflowEtapa();
		Logistica logistica = new Logistica();
		Periodo periodo = new Periodo();
		TipoLogistica tipoLogistica = new TipoLogistica();
		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();

		Banco b1 = new Banco();
		Banco b2 = new Banco();
		
		empresa.setEmpresa_id(rsContrato.getLong("empresa_id"));
		empresa.setNome(rsContrato.getString("empresa_nome"));
		
		organizacao.setOrganizacao_id(rsContrato.getLong("organizacao_id"));
		organizacao.setNome(rsContrato.getString("organizacao_nome"));


		usuario.setUsuario_id(rsContrato.getLong("usuario_id"));
		usuario.setNome(rsContrato.getString("usuario_nome"));

		formulario.setFormulario_id(rsContrato.getLong("formulario_id"));
		created.setTime(rsContrato.getDate("created"));
		formulario.setCreated(created);

		contrato.setContrato_id(rsContrato.getLong("contrato_id"));
		
		parceiro.setParceiroNegocio_id(rsContrato.getLong("parceironegocio_id"));
		parceiro.setNome(rsContrato.getString("parceiro_nome"));
		parceiro.setCpf(rsContrato.getString("parceiro_cpf"));
		
		coeficiente.setCoeficiente_id(rsContrato.getLong("coeficiente_id"));
		coeficiente.setValor(rsContrato.getDouble("valor"));
		
		produto.setProduto_id(rsContrato.getLong("produto_id"));
		produto.setNome(rsContrato.getString("produto_nome"));
		
		b1.setBanco_id(rsContrato.getLong("banco_id"));
		b1.setNome(rsContrato.getString("banco_nome"));
		
		b2.setBanco_id(rsContrato.getLong("recompra_banco_id"));
		b2.setNome(rsContrato.getString("bancoRecompra_nome"));
		
		workflow.setWorkflow_id(rsContrato.getLong("workflow_id"));
		workflow.setNome(rsContrato.getString("workflow_nome"));
		
		workflowEtapa.setWorkflowEtapa_id(rsContrato.getLong("workflowetapa_id"));
		workflowEtapa.setNome(rsContrato.getString("workflowetapa_nome"));

		periodo.setPeriodo_id(rsContrato.getLong("periodo_id"));
		periodo.setNome(rsContrato.getString("periodo_nome"));

		tipoLogistica.setTipoLogistica_id(rsContrato.getLong("tipologistica_id"));
		tipoLogistica.setNome(rsContrato.getString("tipologistica_nome"));

		logistica.setLogistica_id(rsContrato.getLong("logistica_id"));
		logistica.setPeriodo(periodo);
		logistica.setTipoLogistica(tipoLogistica);

		
		logistica.setLogistica_id(rsContrato.getLong("logistica_id"));

		if(rsContrato.getDate("dataassinatura") != null){
			dataAssinatura.setTime(rsContrato.getDate("dataassinatura"));
			logistica.setDataAssinatura(dataAssinatura);
		}

		formulario.setParceiroNegocio(parceiro);
		contrato.setEmpresa(empresa);
		contrato.setOrganizacao(organizacao);
		contrato.setFormulario(formulario);
		contrato.setCoeficiente(coeficiente);
		contrato.setProduto(produto);
		contrato.setUsuario(usuario);
		contrato.setBanco(b1);
		contrato.setRecompraBanco(b2);
		contrato.setWorkflowEtapa(workflowEtapa);
		contrato.setWorkflow(workflow);
		contrato.setIsActive(rsContrato.getBoolean("isactive"));
		contrato.setNumeroBeneficio(rsContrato.getString("numerobeneficio"));
		contrato.setValorContrato(rsContrato.getDouble("valorcontrato"));
		contrato.setValorDivida(rsContrato.getDouble("valordivida"));
		contrato.setValorLiquido(rsContrato.getDouble("valorliquido"));
		contrato.setValorMeta(rsContrato.getDouble("valormeta"));
		contrato.setValorParcela(rsContrato.getDouble("valorparcela"));
		contrato.setValorSeguro(rsContrato.getDouble("valorseguro"));
		contrato.setPrazo(rsContrato.getInt("prazo"));
		contrato.setQtdParcelasAberto(rsContrato.getInt("qtdparcelasaberto"));
		contrato.setLogistica(logistica);

	}

}
