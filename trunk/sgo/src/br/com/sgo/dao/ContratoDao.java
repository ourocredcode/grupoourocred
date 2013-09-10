package br.com.sgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.sgo.infra.ConnJDBC;
import br.com.sgo.infra.CustomDateUtil;
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Banco;
import br.com.sgo.modelo.Coeficiente;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Controle;
import br.com.sgo.modelo.ControleFormulario;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Etapa;
import br.com.sgo.modelo.Formulario;
import br.com.sgo.modelo.Logistica;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.ParceiroNegocio;
import br.com.sgo.modelo.Periodo;
import br.com.sgo.modelo.Produto;
import br.com.sgo.modelo.Usuario;
import br.com.sgo.modelo.Workflow;

@Component
public class ContratoDao extends Dao<Contrato> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsContrato;

	private static final String sqlContratos = " SELECT CONTRATO.empresa_id, EMPRESA.nome as empresa_nome, CONTRATO.organizacao_id, ORGANIZACAO.nome as organizacao_nome, "+
			" FORMULARIO.created,CONTRATO.created as contratoCreated ,FORMULARIO.formulario_id, FORMULARIO.parceironegocio_id , CONTRATO.contrato_id,CONTRATO.formulario_id, "+
			" CONTRATO.coeficiente_id, CONTRATO.workflow_id, "+
			" CONTRATO.produto_id, CONTRATO.tabela_id, "+
			" CONTRATO.banco_id, CONTRATO.recompra_banco_id, "+
			" CONTRATO.usuario_id, "+
			" USUARIO.nome as usuario_nome, USUARIO.apelido as usuario_apelido,  USUARIO.chave as usuario_chave , "+
			" USUARIO_SUPERVISOR.usuario_id as usuario_super_id, "+
			" USUARIO_SUPERVISOR.nome as usuario_super, USUARIO_SUPERVISOR.apelido as usuario_super_apelido, "+
			" CONTRATO.prazo, "+
			" CONTRATO.qtdparcelasaberto, CONTRATO.valorseguro, CONTRATO.desconto, CONTRATO.valorcontrato, "+
			" CONTRATO.valordivida, CONTRATO.valorliquido, CONTRATO.valorparcela, CONTRATO.valormeta, CONTRATO.observacao, "+
			" CONTRATO.prazo , CONTRATO.desconto , CONTRATO.qtdparcelasaberto , CONTRATO.numerobeneficio, CONTRATO.isactive, "+
			" BANCO.nome as banco_nome, BANCO_1.nome as bancoRecompra_nome , PRODUTO.nome as produto_nome, COEFICIENTE.valor, "+
			" PARCEIRONEGOCIO.nome as parceiro_nome,PARCEIRONEGOCIO.cpf as parceiro_cpf, "+
			" CONTRATO.etapa_id, WORKFLOW.workflow_id,WORKFLOW.nome as workflow_nome , "+
			" ETAPA.etapa_id, ETAPA.nome as etapa_nome, ETAPACONTROLE.etapa_id as etapacontrole_id, ETAPACONTROLE.nome as etapacontrole_nome  "+
			" FROM " +
			" (((((((((((((((((( CONTRATO (NOLOCK) INNER JOIN ETAPA (NOLOCK) ON CONTRATO.etapa_id = ETAPA.etapa_id) "+
			" INNER JOIN WORKFLOW (NOLOCK) ON CONTRATO.workflow_id = WORKFLOW.workflow_id) "+
			" INNER JOIN USUARIO (NOLOCK) ON CONTRATO.usuario_id = USUARIO.usuario_id) "+
			" INNER JOIN EMPRESA (NOLOCK) ON CONTRATO.empresa_id = EMPRESA.empresa_id) "+
			" INNER JOIN ORGANIZACAO (NOLOCK) ON CONTRATO.organizacao_id = ORGANIZACAO.organizacao_id) "+
			" INNER JOIN FORMULARIO (NOLOCK) ON CONTRATO.formulario_id = FORMULARIO.formulario_id)" +
			" LEFT JOIN CONTROLEFORMULARIO (NOLOCK) ON CONTROLEFORMULARIO.formulario_id = FORMULARIO.formulario_id)" +
			" LEFT JOIN ETAPA AS ETAPACONTROLE (NOLOCK) ON CONTROLEFORMULARIO.etapa_id = ETAPACONTROLE.etapa_id) "+
			" INNER JOIN COEFICIENTE (NOLOCK) ON CONTRATO.coeficiente_id = COEFICIENTE.coeficiente_id) "+
			" INNER JOIN PRODUTO (NOLOCK) ON CONTRATO.produto_id = PRODUTO.produto_id) "+
			" LEFT JOIN TABELA (NOLOCK) ON CONTRATO.tabela_id = TABELA.tabela_id) "+
			" INNER JOIN BANCO (NOLOCK) ON CONTRATO.banco_id = BANCO.banco_id) "+
			" LEFT JOIN TIPOSAQUE (NOLOCK) ON CONTRATO.tiposaque_id = TIPOSAQUE.tiposaque_id) "+
			" LEFT JOIN BANCO (NOLOCK) AS BANCO_1 ON CONTRATO.recompra_banco_id = BANCO_1.banco_id) "+
			" LEFT JOIN USUARIO (NOLOCK) AS USUARIO_SUPERVISOR ON USUARIO.supervisor_usuario_id = USUARIO_SUPERVISOR.usuario_id) "+
			" LEFT JOIN WORKFLOW (NOLOCK) AS WORKFLOW_1 ON CONTRATO.workflowpendencia_id = WORKFLOW_1.workflow_id) "+
			" LEFT JOIN ETAPA (NOLOCK) AS ETAPA_1 ON CONTRATO.etapapendencia_id = ETAPA_1.etapa_id) "+
			" INNER JOIN PARCEIRONEGOCIO (NOLOCK) ON FORMULARIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id) " +
			" INNER JOIN PARCEIROBENEFICIO (NOLOCK ) ON PARCEIROBENEFICIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id AND PARCEIROBENEFICIO.numerobeneficio = CONTRATO.numerobeneficio " ;  

	public ContratoDao(Session session, ConnJDBC conexao) {

		super(session, Contrato.class);
		this.conexao = conexao;

	}

	public Collection<Contrato> buscaContratoByUsuario(Long usuario_id, Calendar calInicio, Calendar calFim) {

		String sql = sqlContratos;

		if(usuario_id != null)
			sql += " WHERE ( USUARIO.usuario_id = ? OR USUARIO_SUPERVISOR.usuario_id = ? ) AND ( ETAPA.NOME not in ('Recusado') ) ";

		if(calInicio != null)
			sql += " AND ( FORMULARIO.created BETWEEN ? AND ? )";

		this.conn = this.conexao.getConexao();

		Collection<Contrato> contratos = new ArrayList<Contrato>();

		try {

			//System.out.println("buscaContratoByUsuario INICIO");
			//System.out.println(sql);
			//System.out.println(calInicio.getTime());
			//System.out.println(calFim.getTime());
			//System.out.println(usuario_id);
			//System.out.println("buscaContratoByUsuario FIM");

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, usuario_id);
			this.stmt.setLong(2, usuario_id);

			int curr = 1;

			if(usuario_id != null){
				this.stmt.setLong(curr, usuario_id);
				curr++;
				this.stmt.setLong(curr, usuario_id);
				curr++;
			}

			if(calInicio != null){

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarInicio(calInicio).getTimeInMillis()));
				curr++;

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarFim(calFim).getTimeInMillis()));
				curr++;

			} 

			this.rsContrato = this.stmt.executeQuery();

			while (rsContrato.next()) {

				getContratos(contratos);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsContrato, stmt, conn);
		return contratos;
	}

	public Collection<Contrato> buscaContratoByEmpresaOrganizacao(Long empresa_id, Long organizacao_id,Calendar calInicio,Calendar calFim) {

		String sql = sqlContratos;

		sql += " WHERE CONTRATO.empresa_id = ? AND CONTRATO.organizacao_id = ? ";

		if(calInicio != null)
			sql += " AND ( FORMULARIO.created BETWEEN ? AND ? ) AND ( ETAPA.NOME not in ('Recusado') ) ";

		this.conn = this.conexao.getConexao();

		Collection<Contrato> contratos = new ArrayList<Contrato>();

		try {
				
			int curr = 1;

			this.stmt = conn.prepareStatement(sql);

			if(empresa_id != null){
				this.stmt.setLong(curr, empresa_id);
				curr++;
			}

			if(organizacao_id != null){
				this.stmt.setLong(curr, organizacao_id);
				curr++;
			}
			
			if(calInicio != null){

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarInicio(calInicio).getTimeInMillis()));
				curr++;

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarFim(calFim).getTimeInMillis()));
				curr++;

			} 

			this.rsContrato = this.stmt.executeQuery();

			while (rsContrato.next()) {

				getContratos(contratos);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsContrato, stmt, conn);
		return contratos;
	}
	
	public Collection<Contrato> buscaContratoByEmpresaOrganizacaoUsuarioStatus(Long empresa_id, Long organizacao_id, Collection<Usuario> consultores , String status) {

		String sql = sqlContratos;

		String clause = "";
		int x = 0;

		if(empresa_id != null)
			sql += " WHERE EMPRESA.empresa_id = ? ";
		if(organizacao_id != null)
			sql += " AND ORGANIZACAO.organizacao_id = ? ";
		if(!status.equals(""))
			sql += " AND ETAPA.nome like ? ";

		sql += " AND ( 1=1 ";

		for(Usuario u : consultores){
			clause = x <= 0 ? "AND" : "OR";

			if(u != null) {
				sql += clause + " ( USUARIO.usuario_id = ? OR USUARIO_SUPERVISOR.usuario_id = ? ) ";
				x++;
				clause = "";
			}
				

		}
		
		sql += " ) ";

		this.conn = this.conexao.getConexao();

		Collection<Contrato> contratos = new ArrayList<Contrato>();

		try {

			this.stmt = conn.prepareStatement(sql);
			int curr = 1;

			if(empresa_id != null){
				this.stmt.setLong(curr, empresa_id);
				curr++;
			}

			if(organizacao_id != null){
				this.stmt.setLong(curr, organizacao_id);
				curr++;
			}
			
			if(!status.equals("")){
				this.stmt.setLong(curr, organizacao_id);
				curr++;
			}

			for(Usuario u : consultores){

				if(u != null) {
					this.stmt.setLong(curr,u.getUsuario_id());
					curr++;
					this.stmt.setLong(curr,u.getUsuario_id());
					curr++;
				}

			}

			this.rsContrato = this.stmt.executeQuery();

			while (rsContrato.next()) {
				getContratos(contratos);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsContrato, stmt, conn);
		return contratos;

	}
	
	public Collection<Contrato> buscaContratoByFormulario(Long formulario_id) {

		String sql = sqlContratos;
		
		if(formulario_id != null)
			sql += " WHERE FORMULARIO.formulario_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<Contrato> contratos = new ArrayList<Contrato>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, formulario_id);

			this.rsContrato = this.stmt.executeQuery();

			while (rsContrato.next()) {

				getContratos(contratos);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsContrato, stmt, conn);
		return contratos;
	}
	
	public Collection<Contrato> buscaContratoByParceiroNegocio(Long parceiro_id) {

		String sql = sqlContratos;
		
		if(parceiro_id != null)
			sql += " WHERE FORMULARIO.parceironegocio_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<Contrato> contratos = new ArrayList<Contrato>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, parceiro_id);

			this.rsContrato = this.stmt.executeQuery();

			while (rsContrato.next()) {

				getContratos(contratos);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsContrato, stmt, conn);
		return contratos;
	}
	
	public Collection<Contrato> buscaContratoToCheckList(Long formulario_id) {

		String sql = " SELECT CONTRATO.empresa_id, EMPRESA.nome as empresa_nome, CONTRATO.organizacao_id, ORGANIZACAO.nome as organizacao_nome, " + 
					 " FORMULARIO.created,FORMULARIO.formulario_id, FORMULARIO.parceironegocio_id , CONTRATO.contrato_id,CONTRATO.formulario_id, " +
					 " CONTRATO.coeficiente_id, CONTRATO.workflow_id, " + 
					 " CONTRATO.produto_id, CONTRATO.tabela_id, " + 
					 " CONTRATO.banco_id, CONTRATO.recompra_banco_id, " + 
					 " CONTRATO.usuario_id, " + 
					 " USUARIO.nome as usuario_nome, USUARIO.apelido as usuario_apelido, " + 
					 " USUARIO_SUPERVISOR.usuario_id as usuario_super_id, " + 
					 " USUARIO_SUPERVISOR.nome as usuario_super, USUARIO_SUPERVISOR.apelido as usuario_super_apelido, " + 
					 " CONTRATO.prazo, " +  
					 " CONTRATO.qtdparcelasaberto, CONTRATO.valorseguro, CONTRATO.desconto, CONTRATO.valorcontrato, " + 
					 " CONTRATO.valordivida, CONTRATO.valorliquido, CONTRATO.valorparcela, CONTRATO.valormeta, CONTRATO.observacao, " + 
					 " CONTRATO.prazo , CONTRATO.desconto , CONTRATO.qtdparcelasaberto , CONTRATO.numerobeneficio, CONTRATO.isactive, " + 
					 " PARCEIRONEGOCIO.nome as parceiro_nome,PARCEIRONEGOCIO.cpf as parceiro_cpf, " + 
					 " CONTRATO.etapa_id, WORKFLOW.workflow_id,WORKFLOW.nome as workflow_nome , " +  
					 " ETAPA.etapa_id, ETAPA.nome as etapa_nome, " + 
					 " LOGISTICA.logistica_id, LOGISTICA.dataassinatura, LOGISTICA.periodo_id , PERIODO.nome as periodo_nome " + 
					 " FROM " +  
					 " (((((((((( CONTRATO (NOLOCK) " + 
					 " INNER JOIN ETAPA (NOLOCK) ON CONTRATO.etapa_id = ETAPA.etapa_id) " + 
					 " INNER JOIN WORKFLOW (NOLOCK) ON CONTRATO.workflow_id = WORKFLOW.workflow_id) " + 
					 " INNER JOIN USUARIO (NOLOCK) ON CONTRATO.usuario_id = USUARIO.usuario_id) " + 
					 " INNER JOIN EMPRESA (NOLOCK) ON CONTRATO.empresa_id = EMPRESA.empresa_id) " + 
					 " INNER JOIN ORGANIZACAO (NOLOCK) ON CONTRATO.organizacao_id = ORGANIZACAO.organizacao_id) " + 
					 " INNER JOIN FORMULARIO (NOLOCK) ON CONTRATO.formulario_id = FORMULARIO.formulario_id) " + 
					 " LEFT JOIN USUARIO (NOLOCK) AS USUARIO_SUPERVISOR ON USUARIO.supervisor_usuario_id = USUARIO_SUPERVISOR.usuario_id) " + 
					 " INNER JOIN PARCEIRONEGOCIO (NOLOCK) ON FORMULARIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id) " +  
					 " INNER JOIN PARCEIROBENEFICIO (NOLOCK ) ON PARCEIROBENEFICIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id AND PARCEIROBENEFICIO.numerobeneficio = CONTRATO.numerobeneficio) " +
					 " LEFT JOIN LOGISTICA (NOLOCK) ON LOGISTICA.logistica_id = ( SELECT max(LOGISTICA.logistica_id) FROM LOGISTICA (NOLOCK) WHERE LOGISTICA.contrato_id = CONTRATO.contrato_id ) ) " +
					 " LEFT JOIN PERIODO (NOLOCK) ON LOGISTICA.periodo_id = PERIODO.periodo_id  ";
		
		if(formulario_id != null)
			sql += " WHERE FORMULARIO.formulario_id = ? ";

		this.conn = this.conexao.getConexao();

		Collection<Contrato> contratos = new ArrayList<Contrato>();

		try {

			this.stmt = conn.prepareStatement(sql);
			this.stmt.setLong(1, formulario_id);

			this.rsContrato = this.stmt.executeQuery();

			while (rsContrato.next()) {

				Calendar created = new GregorianCalendar();
				Formulario formulario = new Formulario();
				Usuario usuario = new Usuario();
				Usuario supervisor = new Usuario();
				Contrato contrato = new Contrato();
				ParceiroNegocio parceiro = new ParceiroNegocio();
				Workflow workflow = new Workflow();
				Etapa etapa = new Etapa();
				Empresa empresa = new Empresa();
				Organizacao organizacao = new Organizacao();
				Logistica logistica = new Logistica();
				Periodo periodo = new Periodo();

				empresa.setEmpresa_id(rsContrato.getLong("empresa_id"));
				empresa.setNome(rsContrato.getString("empresa_nome"));
				
				organizacao.setOrganizacao_id(rsContrato.getLong("organizacao_id"));
				organizacao.setNome(rsContrato.getString("organizacao_nome"));

				usuario.setUsuario_id(rsContrato.getLong("usuario_id"));
				usuario.setNome(rsContrato.getString("usuario_nome"));
				usuario.setApelido(rsContrato.getString("usuario_apelido"));
				supervisor.setUsuario_id(rsContrato.getLong("usuario_super_id"));
				
				supervisor.setNome(rsContrato.getString("usuario_super"));
				supervisor.setApelido(rsContrato.getString("usuario_super_apelido"));

				usuario.setSupervisorUsuario(supervisor);

				formulario.setFormulario_id(rsContrato.getLong("formulario_id"));
				created.setTime(rsContrato.getDate("created"));
				formulario.setCreated(created);
				
				contrato.setContrato_id(rsContrato.getLong("contrato_id"));

				parceiro.setParceiroNegocio_id(rsContrato.getLong("parceironegocio_id"));
				parceiro.setNome(rsContrato.getString("parceiro_nome"));
				parceiro.setCpf(rsContrato.getString("parceiro_cpf"));
				
				workflow.setWorkflow_id(rsContrato.getLong("workflow_id"));
				workflow.setNome(rsContrato.getString("workflow_nome"));

				etapa.setEtapa_id(rsContrato.getLong("etapa_id"));
				etapa.setNome(rsContrato.getString("etapa_nome"));
				
				logistica.setLogistica_id(rsContrato.getLong("logistica_id"));
				
				if(logistica.getLogistica_id() != null){

					Calendar dataAssinatura = new GregorianCalendar();
					dataAssinatura.setTime(rsContrato.getDate("dataassinatura"));
					logistica.setDataAssinatura(dataAssinatura);
					periodo.setPeriodo_id(rsContrato.getLong("periodo_id"));
					periodo.setNome(rsContrato.getString("periodo_nome"));
					logistica.setPeriodo(periodo);

					contrato.setLogistica(logistica);

				}
				

				formulario.setParceiroNegocio(parceiro);

				contrato.setFormulario(formulario);

				contrato.setEmpresa(empresa);
				contrato.setOrganizacao(organizacao);

				contrato.setUsuario(usuario);

				contrato.setEtapa(etapa);
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

				contratos.add(contrato);
			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsContrato, stmt, conn);
		return contratos;
	}

	public Contrato buscaContratoById(Long contrato_id) {

		String sql = sqlContratos;
		
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
				getContrato(contrato);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsContrato, stmt, conn);
		return contrato;
	}

	public Collection<Contrato> buscaContratosByProposta(Long empresa_id, Long organizacao_id, String proposta, String contrato, Usuario usuario) {

		String sql = sqlContratos;

		if(empresa_id != null)
			sql += " WHERE EMPRESA.empresa_id = ? ";
		if(organizacao_id != null)
			sql += " AND ORGANIZACAO.organizacao_id = ? ";
		if(!proposta.equals(""))
			sql += " AND CONTRATO.propostabanco like ?  ";
		if(!contrato.equals(""))
			sql += " AND CONTRATO.contratobanco like ? ";
		if(usuario != null)
			sql += " AND ( USUARIO.usuario_id = ? OR USUARIO_SUPERVISOR.usuario_id = ? ) ";

		this.conn = this.conexao.getConexao();

		 Collection<Contrato> contratos = new ArrayList<Contrato>();

		try {

			this.stmt = conn.prepareStatement(sql);

			//System.out.println(sql);
			
			int curr = 1;

			if(empresa_id != null){
				this.stmt.setLong(curr, empresa_id);
				curr++;
			}

			if(organizacao_id != null){
				this.stmt.setLong(curr, organizacao_id);
				curr++;
			}
			
			if(!proposta.equals("")){
				this.stmt.setString(curr,"%" + proposta + "%");
				curr++;
			}

			if(!contrato.equals("")){
				this.stmt.setString(curr,"%" + contrato + "%");
				curr++;
			}

			if(usuario != null) {
				this.stmt.setLong(curr,usuario.getUsuario_id());
				curr++;
				this.stmt.setLong(curr,usuario.getUsuario_id());
				curr++;
			}

			this.rsContrato = this.stmt.executeQuery();

			while (rsContrato.next()) {

				getContratos(contratos);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsContrato, stmt, conn);
		return contratos;
	}

	public Collection<Contrato> buscaContratoByFiltros(Long empresa_id, Long organizacao_id, Calendar calInicio,Calendar calFim, 
			Calendar calStatusFinalInicio,Calendar calStatusFinalFim,Calendar calConclusaoInicio,Calendar calConclusaoFim,
			String cliente, String documento, Collection<Long> convenios,Collection<String> status,Collection<String> statusFinal,Collection<String> justificativas,
			Collection<String> produtos,Collection<String> bancos,Collection<String> bancosComprados,Collection<Usuario> consultores,Boolean isSupervisorApoio,
			Long tipoPagamento, Long informacaoSaque,Collection<Long> empresas) {

		String sql = sqlContratos;
		String clause = "";
		int x = 0;
		Boolean comboSearch = false;

		sql += " WHERE CONTRATO.empresa_id = ? AND CONTRATO.organizacao_id = ? ";

		if(!cliente.equals(""))
			sql += " AND PARCEIRONEGOCIO.nome like ? ";

		if(!documento.equals(""))
			sql += " AND ( PARCEIRONEGOCIO.cpf like ? OR PARCEIROBENEFICIO.numerobeneficio like ? ) ";

		if(informacaoSaque != null)
			sql += " AND ( CONTRATO.tiposaque_id = ? ) ";
		
		if(tipoPagamento != null)
			sql += " AND ( CONTRATO.meiopagamento_id = ? ) ";

		sql += " AND ( 1=1 ";

		for(String statusAux1 : status){

			clause = x <= 0 ? "AND" : "OR";

			if(!statusAux1.equals("")){
				sql += clause + " ( ETAPA.nome like ? ) ";
				x++;
				clause = "";
			}

		}

		if ( x == 0 ){

			sql += " AND ( 1=1 ";

			if(x == 0 && cliente.equals("") && documento.equals("") && calStatusFinalInicio == null && calConclusaoInicio == null){
				sql += " AND ( ETAPA.NOME not in ('Aprovado','Recusado','Concluído') ) ";
			}

			sql += " ) ";

		} else {

			if(calStatusFinalInicio != null || calConclusaoInicio != null)
				comboSearch = true;

		}
		
		sql += " ) ";

		x = 0;
		sql += " AND ( 1=1 ";

		for(Long convenioAux : convenios){

			clause = x <= 0 ? "AND" : "OR";

			if(convenioAux != null) {
				sql += clause + " ( CONTRATO.convenio_id = ? ) ";
				x++;
				clause = "";
			}

		}

		sql += " ) ";
		
		
		x = 0;
		sql += " AND ( 1=1 ";

		for(Long empresaAux : empresas){

			clause = x <= 0 ? "AND" : "OR";

			if(empresaAux != null) {
				sql += clause + " ( CONTRATO.organizacaodigitacao_id = ? ) ";
				x++;
				clause = "";
			}

		}

		sql += " ) ";

		x = 0;
		sql += " AND ( 1=1 ";

		for(String produtosAux1 : produtos){

			clause = x <= 0 ? "AND" : "OR";

			if(!produtosAux1.equals("")) {
				sql += clause + " ( PRODUTO.nome like ? ) ";
				x++;
				clause = "";
			}

		}

		sql += " ) ";
		x = 0;
		sql += " AND ( 1=1 ";

		for(String bancosAux1 : bancos){

			clause = x <= 0 ? "AND" : "OR";

			if(!bancosAux1.equals("")){
				sql += clause + " ( BANCO.nome like ? ) ";
				x++;
				clause = "";
			}

		}

		sql += " ) ";
		x = 0;
		sql += " AND ( 1=1 ";

		for(String bancosCompradosAux1 : bancosComprados){
			clause = x <= 0 ? "AND" : "OR";

			if(!bancosCompradosAux1.equals("")) {
				sql += clause + " ( BANCO_1.nome like ? ) ";
				x++;
				clause = "";
			}

		}
		
		sql += " ) ";
		
		x = 0;
		sql += " AND ( 1=1 ";

		for(String justificativaAux1 : justificativas){

			clause = x <= 0 ? "AND" : "OR";

			if(!justificativaAux1.equals("")) {
				sql += clause + " ( ETAPA_1.nome like ? ) ";
				x++;
				clause = "";
			}

		}

		sql += " ) ";
		
		x = 0;
		sql += " AND ( 1=1 ";

		for(Usuario u : consultores){

			clause = x <= 0 ? "AND" : "OR";

			if(u != null) {

				if(isSupervisorApoio)
					sql += clause + " ( USUARIO.usuario_id = ? ) ";
				else
					sql += clause + " ( USUARIO.usuario_id = ? OR USUARIO_SUPERVISOR.usuario_id = ? ) ";

				x++;
				clause = "";
			}

		}
		
		sql += " ) ";

		sql += " AND ( 1=1  ";
		
		if(calInicio != null)
			sql += " AND (FORMULARIO.created BETWEEN ? AND ? )";
		
		if(calStatusFinalInicio != null) {

			if(comboSearch){

				sql += " ) OR ( ( CONTRATO.datastatusfinal BETWEEN ? AND ? ) ";

				x=0;
				
				sql += " AND ( 1=1 ";

				for(String statusFinalAux1 : statusFinal){

					clause = x <= 0 ? " AND " : "OR";

					if(!statusFinalAux1.equals("")){
						sql += clause + " ( ETAPA.nome like ? ) ";
						x++;
						clause = "";
					}

				}
				
				sql += " ) ";
				
				x = 0;
				sql += " AND ( 1=1 ";

				for(Usuario u : consultores){

					clause = x <= 0 ? "AND" : "OR";

					if(u != null) {

						if(isSupervisorApoio)
							sql += clause + " ( USUARIO.usuario_id = ? ) ";
						else
							sql += clause + " ( USUARIO.usuario_id = ? OR USUARIO_SUPERVISOR.usuario_id = ? ) ";

						x++;
						clause = "";
					}

				}
				
				sql += " ) ";
				
				

			} else {

				sql += " AND (  CONTRATO.datastatusfinal BETWEEN ? AND ? )";
				
				x=0;

				sql += " AND ( 1=1 ";

				for(String statusFinalAux1 : statusFinal){

					clause = x <= 0 ? " AND " : "OR";

					if(!statusFinalAux1.equals("")){
						sql += clause + " ( ETAPA.nome like ? ) ";
						x++;
						clause = "";
					}

				}
				
				sql += " ) ";

			}
				

		}

		if(calConclusaoInicio != null) {

			if(comboSearch) {

				sql += " ) OR ( (CONTRATO.dataconclusao BETWEEN ? AND ? )";
				
				x=0;
				
				sql += " AND ( 1=1 ";

				for(String statusFinalAux1 : statusFinal){

					clause = x <= 0 ? " AND " : "OR";

					if(!statusFinalAux1.equals("")){
						sql += clause + " ( ETAPA.nome like ? ) ";
						x++;
						clause = "";
					}

				}
				
				sql += " ) ";
				
				x = 0;
				sql += " AND ( 1=1 ";

				for(Usuario u : consultores){

					clause = x <= 0 ? "AND" : "OR";

					if(u != null) {

						if(isSupervisorApoio)
							sql += clause + " ( USUARIO.usuario_id = ? ) ";
						else
							sql += clause + " ( USUARIO.usuario_id = ? OR USUARIO_SUPERVISOR.usuario_id = ? ) ";

						x++;
						clause = "";
					}

				}
				
				sql += " ) ";

			} else {

				sql += " AND ( CONTRATO.dataconclusao BETWEEN ? AND ? )";
				
				x=0;
				
				sql += " AND ( 1=1 ";

				for(String statusFinalAux1 : statusFinal){

					clause = x <= 0 ? " AND " : "OR";

					if(!statusFinalAux1.equals("")){
						sql += clause + " ( ETAPA.nome like ? ) ";
						x++;
						clause = "";
					}

				}

				sql += " ) ";

			}

		}

		sql += " ) ";

		this.conn = this.conexao.getConexao();

		Collection<Contrato> contratos = new ArrayList<Contrato>();

		try {

			this.stmt = conn.prepareStatement(sql);

			//System.out.println(sql);

			int curr = 1;

			if(empresa_id != null){

				this.stmt.setLong(curr, empresa_id);
				curr++;
			}
			
			if(organizacao_id != null){

				this.stmt.setLong(curr, organizacao_id);
				curr++;
			}
			
			if(!cliente.equals("")){
				this.stmt.setString(curr, '%' + cliente + '%');
				curr++;
			}
			
			if(!documento.equals("")){
				this.stmt.setString(curr, '%' + documento + '%');
				curr++;
				this.stmt.setString(curr, '%' + documento + '%');
				curr++;
			}
			
			if(informacaoSaque != null) {

				this.stmt.setLong(curr, informacaoSaque);
				curr++;

			}
			
			if(tipoPagamento != null) {

				this.stmt.setLong(curr, tipoPagamento);
				curr++;

			}

			for(String statusAux2 : status){

				if(!statusAux2.equals("")) {
					this.stmt.setString(curr, '%' + statusAux2 + '%');
					curr++;
				}

			}

			for(Long convenioAux2 : convenios){

				if(convenioAux2 != null ) {
					this.stmt.setLong(curr, convenioAux2);
					curr++;
				}

			}
			
			for(Long empresaAux2 : empresas){

				if(empresaAux2 != null ) {
					this.stmt.setLong(curr, empresaAux2);
					curr++;
				}

			}
			
			for(String produtosAux2 : produtos){

				if(!produtosAux2.equals("")) {
					this.stmt.setString(curr, '%' + produtosAux2 + '%');
					curr++;
				}

			}

			for(String bancosAux2 : bancos){

				if(!bancosAux2.equals("")) {
					this.stmt.setString(curr, '%' + bancosAux2 + '%');
					curr++;
				}

			}

			for(String bancosCompradosAux2 : bancosComprados){

				if(!bancosCompradosAux2.equals("")) {
					this.stmt.setString(curr, '%' + bancosCompradosAux2 + '%');
					curr++;
				}

			}
			
			for(String justificativaAux2 : justificativas){

				if(!justificativaAux2.equals("")) {
					this.stmt.setString(curr,justificativaAux2);
					curr++;
				}

			}

			for(Usuario u : consultores){

				if(u != null) {

					if(isSupervisorApoio) {

						this.stmt.setLong(curr,u.getUsuario_id());
						curr++;

					} else {

						this.stmt.setLong(curr,u.getUsuario_id());
						curr++;
						this.stmt.setLong(curr,u.getUsuario_id());
						curr++;

					}

				}

			}

			if(calInicio != null){

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarInicio(calInicio).getTimeInMillis()));
				curr++;
				
				

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarFim(calFim).getTimeInMillis()));
				curr++;

			} 
			
			if(calStatusFinalInicio != null){

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarInicio(calStatusFinalInicio).getTimeInMillis()));
				curr++;

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarFim(calStatusFinalFim).getTimeInMillis()));
				curr++;
				
				for(String statusFinalAux2 : statusFinal){

					if(!statusFinalAux2.equals("")) {
						this.stmt.setString(curr, '%' + statusFinalAux2 + '%');
						curr++;
					}

				}

			}
			
			if(calConclusaoInicio != null){

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarInicio(calConclusaoInicio).getTimeInMillis()));
				curr++;
				
				//System.out.println(CustomDateUtil.getCalendarInicio(calConclusaoInicio).getTime());

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarFim(calConclusaoFim).getTimeInMillis()));
				curr++;
				
				//System.out.println(CustomDateUtil.getCalendarFim(calConclusaoFim).getTime());
				
				for(String statusFinalAux2 : statusFinal){

					if(!statusFinalAux2.equals("")) {
						this.stmt.setString(curr, '%' + statusFinalAux2 + '%');
						curr++;
					}

				}

			}

			
			
			if(comboSearch) {
				
				for(Usuario u : consultores){

					if(u != null) {

						if(isSupervisorApoio) {

							this.stmt.setLong(curr,u.getUsuario_id());
							curr++;

						} else {

							this.stmt.setLong(curr,u.getUsuario_id());
							curr++;
							this.stmt.setLong(curr,u.getUsuario_id());
							curr++;

						}

					}

				}
				
			}

			this.rsContrato = this.stmt.executeQuery();

			while (rsContrato.next()) {

				getContratos(contratos);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsContrato, stmt, conn);
		return contratos;

	}

	public Collection<Contrato> buscaDatasControle(Long empresa_id, Long organizacao_id,Long tipoControle,Calendar calInicio,Calendar calFim,
			Calendar previsaoInicio,Calendar previsaoFim, Calendar chegadaInicio,
			Calendar chegadaFim,Calendar vencimentoInicio, Calendar vencimentoFim, Calendar proximaAtuacaoInicio,Calendar proximaAtuacaoFim,
			Calendar quitacaoInicio,Calendar quitacaoFim,Calendar assinaturaInicio,Calendar assinaturaFim,
			Collection<String> bancos, Collection<String> produtos, Collection<String> bancosComprados,Collection<String> status,Collection<Long> convenios, 
			Collection<Usuario> consultores,Boolean isSupervisorApoio, String cliente, String documento,Collection<Long> empresas, Long procedimento, Long proximoProcedimento, 
			Long atuante) {

		String sql = " SELECT CONTRATO.empresa_id, EMPRESA.nome as empresa_nome, CONTRATO.organizacao_id, ORGANIZACAO.nome as organizacao_nome, "+
				" FORMULARIO.created,FORMULARIO.formulario_id, FORMULARIO.parceironegocio_id , CONTRATO.contrato_id,CONTRATO.formulario_id, "+
				" CONTRATO.coeficiente_id, CONTRATO.workflow_id, "+
				" CONTRATO.produto_id, CONTRATO.tabela_id, "+
				" CONTRATO.banco_id, CONTRATO.recompra_banco_id, "+
				" CONTRATO.usuario_id, "+
				" USUARIO.nome as usuario_nome," +
				" USUARIO.apelido as usuario_apelido, "+
				" USUARIO_SUPERVISOR.usuario_id as usuario_super_id, "+
				" USUARIO_SUPERVISOR.nome as usuario_super," +
				" USUARIO_SUPERVISOR.apelido as usuario_super_apelido, "+
				" CONTRATO.prazo, CONTRATO.dataquitacao, "+
				" CONTRATO.qtdparcelasaberto, CONTRATO.valorseguro, CONTRATO.desconto, CONTRATO.valorcontrato, "+
				" CONTRATO.valordivida, CONTRATO.valorliquido, CONTRATO.valorparcela, CONTRATO.valormeta, CONTRATO.observacao, "+
				" CONTRATO.prazo , CONTRATO.desconto , CONTRATO.qtdparcelasaberto , CONTRATO.numerobeneficio, CONTRATO.isactive, "+
				" BANCO.nome as banco_nome, BANCO_1.nome as bancoRecompra_nome , PRODUTO.nome as produto_nome, COEFICIENTE.valor, "+
				" PARCEIRONEGOCIO.nome as parceiro_nome,PARCEIRONEGOCIO.cpf as parceiro_cpf, "+
				" CONTRATO.etapa_id, WORKFLOW.workflow_id,WORKFLOW.nome as workflow_nome , "+
				" ETAPA.etapa_id, ETAPA.nome as etapa_nome, CONTROLE.controle_id, CONTROLE.tipocontrole_id, CONTROLE.dataatuacao, CONTROLE.datachegada," +
				" CONTROLE.dataprevisao,CONTROLE.datavencimento," +
				" CONTROLE.dataprimeiraatuacao, CONTROLE.dataproximaatuacao "+
				" FROM " +
				"  CONTRATO (NOLOCK) INNER JOIN ETAPA (NOLOCK) ON CONTRATO.etapa_id = ETAPA.etapa_id "+
				" INNER JOIN WORKFLOW (NOLOCK) ON CONTRATO.workflow_id = WORKFLOW.workflow_id "+
				" INNER JOIN USUARIO (NOLOCK) ON CONTRATO.usuario_id = USUARIO.usuario_id "+
				" LEFT JOIN CONVENIO (NOLOCK) ON CONTRATO.convenio_id = CONVENIO.convenio_id "+
				" LEFT JOIN MODALIDADE (NOLOCK) ON CONTRATO.modalidade_id = MODALIDADE.modalidade_id "+
				" INNER JOIN EMPRESA (NOLOCK) ON CONTRATO.empresa_id = EMPRESA.empresa_id "+
				" INNER JOIN ORGANIZACAO (NOLOCK) ON CONTRATO.organizacao_id = ORGANIZACAO.organizacao_id "+
				" INNER JOIN FORMULARIO (NOLOCK) ON CONTRATO.formulario_id = FORMULARIO.formulario_id "+
				" INNER JOIN COEFICIENTE (NOLOCK) ON CONTRATO.coeficiente_id = COEFICIENTE.coeficiente_id "+
				" INNER JOIN PRODUTO (NOLOCK) ON CONTRATO.produto_id = PRODUTO.produto_id "+
				" LEFT JOIN TABELA (NOLOCK) ON CONTRATO.tabela_id = TABELA.tabela_id "+
				" INNER JOIN BANCO (NOLOCK) ON CONTRATO.banco_id = BANCO.banco_id "+
				" LEFT JOIN LOGISTICA (NOLOCK) ON LOGISTICA.logistica_id = ( SELECT max(LOGISTICA.logistica_id) FROM LOGISTICA WHERE LOGISTICA.contrato_id = CONTRATO.contrato_id ) "+
				" LEFT JOIN NATUREZAPROFISSIONAL (NOLOCK) ON CONTRATO.naturezaprofissional_id = NATUREZAPROFISSIONAL.naturezaprofissional_id "+
				" LEFT JOIN TIPOSAQUE (NOLOCK) ON CONTRATO.tiposaque_id = TIPOSAQUE.tiposaque_id "+
				" LEFT JOIN BANCO (NOLOCK) AS BANCO_1 ON CONTRATO.recompra_banco_id = BANCO_1.banco_id "+
				" LEFT JOIN USUARIO (NOLOCK) AS USUARIO_SUPERVISOR ON USUARIO.supervisor_usuario_id = USUARIO_SUPERVISOR.usuario_id "+
				" LEFT JOIN WORKFLOW (NOLOCK) AS WORKFLOW_1 ON CONTRATO.workflowpendencia_id = WORKFLOW_1.workflow_id "+
				" LEFT JOIN ETAPA (NOLOCK) AS ETAPA_1 ON CONTRATO.etapapendencia_id = ETAPA_1.etapa_id " +
				" INNER JOIN PARCEIRONEGOCIO (NOLOCK) ON FORMULARIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id " ;

		String clause = "";
		int x = 0;
		
		if(tipoControle != null)
			sql += " LEFT JOIN CONTROLE (NOLOCK) AS CONTROLE ON CONTRATO.contrato_id = CONTROLE.contrato_id AND CONTROLE.tipocontrole_id = ?  ";

		sql += " WHERE CONTRATO.empresa_id = ? AND CONTRATO.organizacao_id = ? ";

		if(!cliente.equals(""))
			sql += " AND PARCEIRONEGOCIO.nome like ? ";

		if(!documento.equals(""))
			sql += " AND ( PARCEIRONEGOCIO.cpf like ? OR PARCEIROBENEFICIO.numerobeneficio like ? ) ";

		sql += " AND ( 1=1 ";

		for(String statusAux1 : status){

			clause = x <= 0 ? "AND" : "OR";

			if(!statusAux1.equals("")){
				sql += clause + " ( ETAPA.nome like ? ) ";
				x++;
				clause = "";
			}

		}

		sql += " ) ";
		
		x = 0;
		sql += " AND ( 1=1 ";

		for(Long convenioAux1 : convenios){

			clause = x <= 0 ? "AND" : "OR";

			if(convenioAux1 != null) {
				sql += clause + " ( CONTRATO.convenio_id = ? ) ";
				x++;
				clause = "";
			}

		}

		sql += " ) ";
		
		
		x = 0;
		sql += " AND ( 1=1 ";

		for(Long empresasAux1 : empresas){

			clause = x <= 0 ? "AND" : "OR";

			if(empresasAux1 != null) {
				sql += clause + " ( CONTRATO.organizacaodigitacao_id = ? ) ";
				x++;
				clause = "";
			}

		}

		sql += " ) ";
		
		
		x = 0;
		sql += " AND ( 1=1 ";

		for(String produtosAux1 : produtos){

			clause = x <= 0 ? "AND" : "OR";

			if(!produtosAux1.equals("")) {
				sql += clause + " ( PRODUTO.nome like ? ) ";
				x++;
				clause = "";
			}

		}

		sql += " ) ";
		
		x = 0;
		sql += " AND ( 1=1 ";

		for(String bancosAux1 : bancos){

			clause = x <= 0 ? "AND" : "OR";

			if(!bancosAux1.equals("")){
				sql += clause + " ( BANCO.nome like ? ) ";
				x++;
				clause = "";
			}

		}

		sql += " ) ";
		x = 0;
		sql += " AND ( 1=1 ";

		for(String bancosCompradosAux1 : bancosComprados){
			clause = x <= 0 ? "AND" : "OR";

			if(!bancosCompradosAux1.equals("")) {
				sql += clause + " ( BANCO_1.nome like ? ) ";
				x++;
				clause = "";
			}

		}
		
		sql += " ) ";
		x = 0;
		sql += " AND ( 1=1 ";

		for(Usuario u : consultores){
			clause = x <= 0 ? "AND" : "OR";

			if(u != null) {
				
				if(isSupervisorApoio)
					sql += clause + " ( USUARIO.usuario_id = ? ) ";
				else
					sql += clause + " ( USUARIO.usuario_id = ? OR USUARIO_SUPERVISOR.usuario_id = ? ) ";

				x++;
				clause = "";
			}

		}
		
		sql += " ) ";
		
		if(previsaoInicio != null)
			sql += " AND (CONTROLE.dataprevisao BETWEEN ? AND ? )";
		
		if(chegadaInicio != null)
			sql += " AND (CONTROLE.datachegada BETWEEN ? AND ? )";
		
		if(vencimentoInicio != null)
			sql += " AND (CONTROLE.datavencimento BETWEEN ? AND ? )";
		
		if(proximaAtuacaoInicio != null)
			sql += " AND (CONTROLE.dataproximaatuacao BETWEEN ? AND ? )";
		
		if(calInicio != null)
			sql += " AND (FORMULARIO.created BETWEEN ? AND ? )";
		
		if(quitacaoInicio != null)
			sql += " AND (CONTRATO.dataquitacao BETWEEN ? AND ? )";
		
		if(assinaturaInicio != null)
			sql += " AND (LOGISTICA.dataassinatura BETWEEN ? AND ? )";
		
		if(procedimento != null)
			sql += " AND ( CONTROLE.etapa_id = ? ) ";
		
		if(proximoProcedimento != null)
			sql += "  AND ( CONTROLE.etapaproximo_id = ? ) ";
		
		if(atuante != null)
			sql += "  AND ( CONTROLE.proximoatuante_id = ? ) ";

		this.conn = this.conexao.getConexao();

		Collection<Contrato> contratos = new ArrayList<Contrato>();

		try {

			this.stmt = conn.prepareStatement(sql);

			//System.out.println(sql);

			int curr = 1;
			
			if(tipoControle != null){
				
				//System.out.println("tipoControle " + tipoControle);
				
				this.stmt.setLong(curr, tipoControle);
				curr++;
			}

			if(empresa_id != null){
				
				//System.out.println("empresa_id : " + empresa_id);

				this.stmt.setLong(curr, empresa_id);
				curr++;
			}
			
			if(organizacao_id != null){
				
				//System.out.println("organizacao_id : " + organizacao_id);
				
				this.stmt.setLong(curr, organizacao_id);
				curr++;
			}

			if(!cliente.equals("")){
				this.stmt.setString(curr, '%' + cliente + '%');
				curr++;
			}
			
			if(!documento.equals("")){
				this.stmt.setString(curr, '%' + documento + '%');
				curr++;
				this.stmt.setString(curr, '%' + documento + '%');
				curr++;
			}

			for(String statusAux2 : status){
				
				//System.out.println(" status : " + statusAux2);

				if(!statusAux2.equals("")) {
					this.stmt.setString(curr, '%' + statusAux2 + '%');
					curr++;
				}

			}
			
			for(Long conveniosAux2 : convenios){

				if(conveniosAux2 != null) {
					this.stmt.setLong(curr, conveniosAux2);
					curr++;
				}

			}
			
			
			for(Long empresasAux2 : empresas){

				if(empresasAux2 != null) {
					this.stmt.setLong(curr, empresasAux2);
					curr++;
				}

			}
			
			for(String produtosAux2 : produtos){

				if(!produtosAux2.equals("")) {
					this.stmt.setString(curr, '%' + produtosAux2 + '%');
					curr++;
				}

			}

			for(String bancosAux2 : bancos){

				if(!bancosAux2.equals("")) {
					this.stmt.setString(curr, '%' + bancosAux2 + '%');
					curr++;
				}

			}

			for(String bancosCompradosAux2 : bancosComprados){

				if(!bancosCompradosAux2.equals("")) {
					this.stmt.setString(curr, '%' + bancosCompradosAux2 + '%');
					curr++;
				}

			}
			
			for(Usuario u : consultores){

				if(u != null) {
					
					if(isSupervisorApoio) {
					
						this.stmt.setLong(curr,u.getUsuario_id());
						curr++;
						
					} else {
						
						this.stmt.setLong(curr,u.getUsuario_id());
						curr++;
						this.stmt.setLong(curr,u.getUsuario_id());
						curr++;

					}

				}

			}
			
			if(previsaoInicio != null){

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarInicio(previsaoInicio).getTimeInMillis()));
				curr++;

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarFim(previsaoFim).getTimeInMillis()));
				curr++;

			} 
			
			if(chegadaInicio != null){

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarInicio(chegadaInicio).getTimeInMillis()));
				curr++;

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarFim(chegadaFim).getTimeInMillis()));
				curr++;

			} 
			
			if(vencimentoInicio != null){

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarInicio(vencimentoInicio).getTimeInMillis()));
				curr++;

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarFim(vencimentoFim).getTimeInMillis()));
				curr++;

			}
			
			if(proximaAtuacaoInicio != null){

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarInicio(proximaAtuacaoInicio).getTimeInMillis()));
				curr++;

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarFim(proximaAtuacaoFim).getTimeInMillis()));
				curr++;

			}
			
			if(calInicio != null){

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarInicio(calInicio).getTimeInMillis()));
				curr++;

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarFim(calFim).getTimeInMillis()));
				curr++;

			} 
			
			if(quitacaoInicio != null){

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarInicio(quitacaoInicio).getTimeInMillis()));
				curr++;

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarFim(quitacaoFim).getTimeInMillis()));
				curr++;

			} 
			
			if(assinaturaInicio != null){

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarInicio(assinaturaInicio).getTimeInMillis()));
				curr++;

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarFim(assinaturaFim).getTimeInMillis()));
				curr++;

			} 
			
			if(procedimento != null) {
				
				//System.out.println("procedimento " + procedimento );

				this.stmt.setLong(curr, procedimento);
				curr++;

			}
			
			if(proximoProcedimento != null) {
				
				//System.out.println("proximoProcedimento " + proximoProcedimento );

				this.stmt.setLong(curr, proximoProcedimento);
				curr++;

				
			}
			
			if(atuante != null) {
				
				//System.out.println(" atuante " + atuante );
				
				this.stmt.setLong(curr, atuante);
				curr++;

				
			}

			this.rsContrato = this.stmt.executeQuery();

			while (rsContrato.next()) {

				Calendar created = new GregorianCalendar();
				Formulario formulario = new Formulario();
				Usuario usuario = new Usuario();
				Usuario supervisor = new Usuario();
				Contrato contrato = new Contrato();
				ParceiroNegocio parceiro = new ParceiroNegocio();
				Coeficiente coeficiente = new Coeficiente();
				Produto produto = new Produto();
				Workflow workflow = new Workflow();
				Etapa etapa = new Etapa();
				Empresa empresa = new Empresa();
				Organizacao organizacao = new Organizacao();
				Controle controle = new Controle();
				
				Banco b1 = new Banco();
				Banco b2 = new Banco();
				
				empresa.setEmpresa_id(rsContrato.getLong("empresa_id"));
				empresa.setNome(rsContrato.getString("empresa_nome"));
				
				organizacao.setOrganizacao_id(rsContrato.getLong("organizacao_id"));
				organizacao.setNome(rsContrato.getString("organizacao_nome"));

				usuario.setUsuario_id(rsContrato.getLong("usuario_id"));
				usuario.setNome(rsContrato.getString("usuario_nome"));
				usuario.setApelido(rsContrato.getString("usuario_apelido"));
				supervisor.setUsuario_id(rsContrato.getLong("usuario_super_id"));
				
				supervisor.setNome(rsContrato.getString("usuario_super"));
				supervisor.setApelido(rsContrato.getString("usuario_super_apelido"));

				usuario.setSupervisorUsuario(supervisor);

				formulario.setFormulario_id(rsContrato.getLong("formulario_id"));
				created.setTime(rsContrato.getDate("created"));
				formulario.setCreated(created);

				controle.setControle_id(rsContrato.getLong("controle_id"));

				if(rsContrato.getDate("dataatuacao") != null) {
					Calendar dataatuacao = new GregorianCalendar();
					
					dataatuacao.setTime(rsContrato.getDate("dataatuacao"));
					controle.setDataAtuacao(dataatuacao);
				}
				
				if(rsContrato.getDate("datachegada") != null) {
					
					Calendar datachegada = new GregorianCalendar();
					
					datachegada.setTime(rsContrato.getDate("datachegada"));
					controle.setDataChegada(datachegada);
				}
				
				
				if(rsContrato.getDate("dataprevisao") != null) {
					
					Calendar dataprevisao = new GregorianCalendar();
					
					dataprevisao.setTime(rsContrato.getDate("dataprevisao"));
					controle.setDataPrevisao(dataprevisao);
				}
				
				if(rsContrato.getDate("dataprimeiraatuacao") != null) {
					
					Calendar dataprimeiraatuacao = new GregorianCalendar();
					
					dataprimeiraatuacao.setTime(rsContrato.getDate("dataprimeiraatuacao"));
					controle.setDataPrimeiraAtuacao(dataprimeiraatuacao);
				}
				
				if(rsContrato.getDate("dataproximaatuacao") != null) {
					
					Calendar dataproximaatuacao = new GregorianCalendar();
					
					dataproximaatuacao.setTime(rsContrato.getDate("dataproximaatuacao"));
					controle.setDataProximaAtuacao(dataproximaatuacao);
				}
				
				if(rsContrato.getDate("datavencimento") != null) {

					Calendar datavencimento = new GregorianCalendar();

					datavencimento.setTime(rsContrato.getDate("datavencimento"));
					controle.setDataVencimento(datavencimento);

				}
				
				if(rsContrato.getDate("dataquitacao") != null) {

					Calendar dataQuitacao = new GregorianCalendar();

					dataQuitacao.setTime(rsContrato.getDate("dataquitacao"));
					contrato.setDataQuitacao(dataQuitacao);

				}

				contrato.setControle(controle);

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

				etapa.setEtapa_id(rsContrato.getLong("etapa_id"));
				etapa.setNome(rsContrato.getString("etapa_nome"));

				formulario.setParceiroNegocio(parceiro);
				contrato.setFormulario(formulario);
				contrato.setCoeficiente(coeficiente);
				contrato.setEmpresa(empresa);
				contrato.setOrganizacao(organizacao);
				contrato.setProduto(produto);
				contrato.setUsuario(usuario);
				contrato.setBanco(b1);
				contrato.setRecompraBanco(b2);
				contrato.setEtapa(etapa);
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

				contratos.add(contrato);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		this.conexao.closeConnection(rsContrato, stmt, conn);
		return contratos;

	}

	public HashMap<String,Object[]> buscaContratosToCountEtapas(Long empresa_id , Long organizacao_id, Long usuario_id) {

		String sql = " SELECT " +
				"		 ETAPA.nome as etapa_nome," +
				"		 ETAPA.etapa_id , " +
				"		 COUNT(ETAPA.nome) as etapaCount, " +
				"		 SUM(CONTRATO.valormeta) as metaCount, " +
				"		 SUM(CONTRATO.valorcontrato) as contratoCount," +
				" 		 SUM(CONTRATO.valorContratoLiquido) as contLiquidoCount " +
				" FROM ((CONTRATO (NOLOCK) INNER JOIN ETAPA (NOLOCK) ON CONTRATO.etapa_id = ETAPA.etapa_id) " +
				" INNER JOIN USUARIO (NOLOCK) ON CONTRATO.usuario_id = USUARIO.usuario_id) " +
				" INNER JOIN USUARIO AS USUARIO_SUPERVISOR (NOLOCK) ON USUARIO.supervisor_usuario_id = USUARIO_SUPERVISOR.usuario_id ";

			sql += " WHERE CONTRATO.empresa_id = ? ";

		if(organizacao_id != null)
			sql += " AND CONTRATO.organizacao_id = ? ";

		if(usuario_id != null)
			sql += " AND (CONTRATO.usuario_id = ? OR USUARIO_SUPERVISOR.usuario_id = ? ) ";

		sql +=  " AND ( ETAPA.NOME not in ('Aprovado','Recusado','Concluído') ) GROUP BY ETAPA.nome, ETAPA.etapa_id ORDER BY ETAPA.nome ASC ";

		this.conn = this.conexao.getConexao();

		HashMap<String,Object[]> map = new HashMap<String,Object[]>();

		try {

			this.stmt = conn.prepareStatement(sql);

			this.stmt.setLong(1, empresa_id);
			this.stmt.setLong(2, organizacao_id);

			if(usuario_id != null){

				this.stmt.setLong(3, usuario_id);
				this.stmt.setLong(4, usuario_id);

			}

			this.rsContrato = this.stmt.executeQuery();

			while (rsContrato.next()) {

				String etapa_nome = rsContrato.getString("etapa_nome");
				Long etapa_id = rsContrato.getLong("etapa_id");
				Double etapaCount = rsContrato.getDouble("etapaCount");
				Double contratoCount = rsContrato.getDouble("contratoCount");
				Double contLiquidoCount = rsContrato.getDouble("contLiquidoCount");
				Double metaCount = rsContrato.getDouble("metaCount");

				Object[] values = new Object[5];

				values[0] = etapa_id;
				values[1] = etapaCount;
				values[2] = contratoCount;
				values[3] = contLiquidoCount;
				values[4] = metaCount;

				map.put(etapa_nome,values);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsContrato, stmt, conn);

		return map;

	}

	public HashMap<String,Object[]> buscaContratosToCountEquipes(Long empresa_id , Long organizacao_id, Calendar dia1,Calendar dia2) {

		String sql = " SELECT " +
						 " USUARIO_SUPERVISOR.usuario_id, " +
						 " USUARIO_SUPERVISOR.apelido, " +
						 " COUNT(CONTRATO.contrato_id) as qtdContrato," +
						 " SUM(CONTRATO.valormeta) as metaCount, " +  
						 " SUM(CONTRATO.valorcontrato) as contratoCount, " + 
				 		 " SUM(CONTRATO.valorContratoLiquido) as contratoLiquidoCount, " +  
				 		 " SUM(CONTRATO.valorLiquido) as liquidoCount " + 
				 " FROM ((CONTRATO (NOLOCK) INNER JOIN ETAPA (NOLOCK) ON CONTRATO.etapa_id = ETAPA.etapa_id) " +  
				 " INNER JOIN USUARIO (NOLOCK) ON CONTRATO.usuario_id = USUARIO.usuario_id) " +  
				 " INNER JOIN USUARIO AS USUARIO_SUPERVISOR (NOLOCK) ON USUARIO.supervisor_usuario_id = USUARIO_SUPERVISOR.usuario_id WHERE ";

		if(empresa_id != null)
			sql += " CONTRATO.empresa_id = ? AND ";

		if(organizacao_id != null)
			sql += "  CONTRATO.organizacao_id = ? AND ";

		if(dia1 != null)
			sql += "  ( CONTRATO.created BETWEEN ? AND ? ) AND ";

		sql +=  "  ( ETAPA.nome not like 'Recusado' ) GROUP BY USUARIO_SUPERVISOR.usuario_id, USUARIO_SUPERVISOR.apelido ORDER BY metaCount DESC ";

		this.conn = this.conexao.getConexao();

		HashMap<String,Object[]> map = new HashMap<String,Object[]>();

		try {

			int curr = 1;

			this.stmt = conn.prepareStatement(sql);


			if(empresa_id != null){
				this.stmt.setLong(curr, empresa_id);
				curr++;
			}

			if(organizacao_id != null){
				this.stmt.setLong(curr, organizacao_id);
				curr++;
			}
			
			if(dia1 != null){

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarInicio(dia1).getTimeInMillis()));
				curr++;

				this.stmt.setTimestamp(curr,new Timestamp(CustomDateUtil.getCalendarFim(dia2).getTimeInMillis()));
				curr++;

			} 

			this.rsContrato = this.stmt.executeQuery();

			while (rsContrato.next()) {

				String supervisor_apelido  = rsContrato.getString("apelido");
				Long supervisor_id = rsContrato.getLong("usuario_id");

				Integer qtdContrato = rsContrato.getInt("qtdContrato");
				Double contratoCount = rsContrato.getDouble("contratoCount");
				Double contratoLiquidoCount = rsContrato.getDouble("contratoLiquidoCount");
				Double liquidoCount = rsContrato.getDouble("liquidoCount");
				Double metaCount = rsContrato.getDouble("metaCount");

				Object[] values = new Object[6];

				values[0] = supervisor_id;
				values[1] = qtdContrato;
				values[2] = contratoCount;
				values[3] = contratoLiquidoCount;
				values[4] = liquidoCount;
				values[5] = metaCount;

				map.put(supervisor_apelido,values);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsContrato, stmt, conn);

		return map;

	}
	
	public HashMap<String,Object[]> buscaContratosToCountEtapasStatusFinal(Long empresa_id , Long organizacao_id, Long usuario_id, Calendar calInicio, Calendar calFim) {

		String sql = " SELECT " +
					"		 ETAPA.nome as etapa_nome," +
					"		 ETAPA.etapa_id , " +
					"		 COUNT(ETAPA.nome) as etapaCount, " +
					"		 SUM(CONTRATO.valormeta) as metaCount, " +
					"		 SUM(CONTRATO.valorcontrato) as contratoCount," +
					" 		 SUM(CONTRATO.valorContratoLiquido) as contLiquidoCount " +
					" FROM (( CONTRATO INNER JOIN ETAPA ON CONTRATO.etapa_id = ETAPA.etapa_id) " +
					" LEFT JOIN USUARIO ON CONTRATO.usuario_id = USUARIO.usuario_id) " +
					" LEFT JOIN USUARIO AS USUARIO_SUPERVISOR ON USUARIO.supervisor_usuario_id = USUARIO_SUPERVISOR.usuario_id " +
					" INNER JOIN FORMULARIO ON FORMULARIO.formulario_id = CONTRATO.formulario_id ";

			sql += " WHERE CONTRATO.empresa_id = ? ";

		if(organizacao_id != null)
			sql += " AND CONTRATO.organizacao_id = ? ";

		if(usuario_id != null)
			sql += " AND (CONTRATO.usuario_id = ? OR USUARIO_SUPERVISOR.usuario_id = ? ) ";

		if(calInicio != null)
			sql += " AND ( CONTRATO.datastatusfinal BETWEEN ? AND ? )";
		
		sql +=  " AND ( ETAPA.NOME in ('Aprovado','Recusado') ) GROUP BY ETAPA.nome, ETAPA.etapa_id ";

		this.conn = this.conexao.getConexao();
		

		HashMap<String,Object[]> map = new HashMap<String,Object[]>();

		try {

			this.stmt = conn.prepareStatement(sql);

			//System.out.println( " CONSULTA DASHBOARD " + sql);

			int curr = 1;
			
			this.stmt.setLong(curr, empresa_id);
			this.stmt.setLong(++curr, organizacao_id);
			
			if(usuario_id != null){

				this.stmt.setLong(++curr, usuario_id);
				this.stmt.setLong(++curr, usuario_id);
				
			}
			
			if(calInicio != null){

				this.stmt.setTimestamp(++curr,new Timestamp(CustomDateUtil.getCalendarInicio(calInicio).getTimeInMillis()));
				
				//System.out.println(" CONSULTA DASHBOARD " + calInicio.getTime());

				this.stmt.setTimestamp(++curr,new Timestamp(CustomDateUtil.getCalendarFim(calFim).getTimeInMillis()));
				
				//System.out.println(" CONSULTA DASHBOARD " + CustomDateUtil.getCalendarFim(calFim).getTime());

			}

			this.rsContrato = this.stmt.executeQuery();

			while (rsContrato.next()) {

				String etapa_nome = rsContrato.getString("etapa_nome");
				Long etapa_id = rsContrato.getLong("etapa_id");
				Double etapaCount = rsContrato.getDouble("etapaCount");
				Double contratoCount = rsContrato.getDouble("contratoCount");
				Double contLiquidoCount = rsContrato.getDouble("contLiquidoCount");
				Double metaCount = rsContrato.getDouble("metaCount");

				Object[] values = new Object[5];

				values[0] = etapa_id;
				values[1] = etapaCount;
				values[2] = contratoCount;
				values[3] = contLiquidoCount;
				values[4] = metaCount;

				map.put(etapa_nome,values);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsContrato, stmt, conn);
		return map;

	}
	
	
	public HashMap<String,Object[]> buscaContratosToCountEtapasConcluído(Long empresa_id , Long organizacao_id, Long usuario_id, Calendar calInicio, Calendar calFim) {

		String sql = " SELECT " +
					"		 ETAPA.nome as etapa_nome," +
					"		 ETAPA.etapa_id , " +
					"		 COUNT(ETAPA.nome) as etapaCount, " +
					"		 SUM(CONTRATO.valormeta) as metaCount, " +
					"		 SUM(CONTRATO.valorcontrato) as contratoCount," +
					" 		 SUM(CONTRATO.valorContratoLiquido) as contLiquidoCount " +
					" FROM (( CONTRATO INNER JOIN ETAPA ON CONTRATO.etapa_id = ETAPA.etapa_id) " +
					" LEFT JOIN USUARIO ON CONTRATO.usuario_id = USUARIO.usuario_id) " +
					" LEFT JOIN USUARIO AS USUARIO_SUPERVISOR ON USUARIO.supervisor_usuario_id = USUARIO_SUPERVISOR.usuario_id " +
					" INNER JOIN FORMULARIO ON FORMULARIO.formulario_id = CONTRATO.formulario_id ";

			sql += " WHERE CONTRATO.empresa_id = ? ";

		if(organizacao_id != null)
			sql += " AND CONTRATO.organizacao_id = ? ";

		if(usuario_id != null)
			sql += " AND (CONTRATO.usuario_id = ? OR USUARIO_SUPERVISOR.usuario_id = ? ) ";

		if(calInicio != null)
			sql += " AND ( CONTRATO.dataconclusao BETWEEN ? AND ? )";
		
		sql +=  " AND ( ETAPA.NOME in ('Concluído') ) GROUP BY ETAPA.nome, ETAPA.etapa_id ";

		this.conn = this.conexao.getConexao();
		

		HashMap<String,Object[]> map = new HashMap<String,Object[]>();

		try {

			this.stmt = conn.prepareStatement(sql);

			//System.out.println( " CONSULTA DASHBOARD CONCLUIDO " + sql);

			int curr = 1;
			
			this.stmt.setLong(curr, empresa_id);
			this.stmt.setLong(++curr, organizacao_id);
			
			if(usuario_id != null){

				this.stmt.setLong(++curr, usuario_id);
				this.stmt.setLong(++curr, usuario_id);
				
			}
			
			if(calInicio != null){

				this.stmt.setTimestamp(++curr,new Timestamp(CustomDateUtil.getCalendarInicio(calInicio).getTimeInMillis()));
				
				//System.out.println( " CONSULTA DASHBOARD CONCLUIDO " + CustomDateUtil.getCalendarInicio(calInicio).getTime());

				this.stmt.setTimestamp(++curr,new Timestamp(CustomDateUtil.getCalendarFim(calFim).getTimeInMillis()));
				
				//System.out.println( " CONSULTA DASHBOARD CONCLUIDO " + CustomDateUtil.getCalendarFim(calFim).getTime());

			}

			this.rsContrato = this.stmt.executeQuery();

			while (rsContrato.next()) {

				String etapa_nome = rsContrato.getString("etapa_nome");
				Long etapa_id = rsContrato.getLong("etapa_id");
				Double etapaCount = rsContrato.getDouble("etapaCount");
				Double contratoCount = rsContrato.getDouble("contratoCount");
				Double contLiquidoCount = rsContrato.getDouble("contLiquidoCount");
				Double metaCount = rsContrato.getDouble("metaCount");

				Object[] values = new Object[5];

				values[0] = etapa_id;
				values[1] = etapaCount;
				values[2] = contratoCount;
				values[3] = contLiquidoCount;
				values[4] = metaCount;

				map.put(etapa_nome,values);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsContrato, stmt, conn);
		return map;

	}

	private void getContratos(Collection<Contrato> contratos) throws SQLException {
		
		Calendar created = new GregorianCalendar();
		Calendar contratoCreated = new GregorianCalendar();
		Formulario formulario = new Formulario();
		Usuario usuario = new Usuario();
		Usuario supervisor = new Usuario();
		Contrato contrato = new Contrato();
		ParceiroNegocio parceiro = new ParceiroNegocio();
		Coeficiente coeficiente = new Coeficiente();
		Produto produto = new Produto();
		Workflow workflow = new Workflow();
		Etapa etapa = new Etapa();
		Etapa etapacontrole = new Etapa();
		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();
		ControleFormulario posvenda = new ControleFormulario();

		Banco b1 = new Banco();
		Banco b2 = new Banco();
		
		empresa.setEmpresa_id(rsContrato.getLong("empresa_id"));
		empresa.setNome(rsContrato.getString("empresa_nome"));
		
		organizacao.setOrganizacao_id(rsContrato.getLong("organizacao_id"));
		organizacao.setNome(rsContrato.getString("organizacao_nome"));

		usuario.setUsuario_id(rsContrato.getLong("usuario_id"));
		usuario.setNome(rsContrato.getString("usuario_nome"));
		usuario.setApelido(rsContrato.getString("usuario_apelido"));
		usuario.setChave(rsContrato.getString("usuario_chave"));
		supervisor.setUsuario_id(rsContrato.getLong("usuario_super_id"));

		supervisor.setNome(rsContrato.getString("usuario_super"));
		supervisor.setApelido(rsContrato.getString("usuario_super_apelido"));

		usuario.setSupervisorUsuario(supervisor);

		formulario.setFormulario_id(rsContrato.getLong("formulario_id"));

		created.setTime(rsContrato.getDate("created"));
		formulario.setCreated(created);
		contratoCreated.setTime(rsContrato.getDate("contratoCreated"));
		contrato.setCreated(contratoCreated);

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

		etapa.setEtapa_id(rsContrato.getLong("etapa_id"));
		etapa.setNome(rsContrato.getString("etapa_nome"));

		etapacontrole.setEtapa_id(rsContrato.getLong("etapacontrole_id"));
		etapacontrole.setNome(rsContrato.getString("etapacontrole_nome"));

		posvenda.setEtapa(etapacontrole);

		formulario.setPosvenda(posvenda);
		formulario.setParceiroNegocio(parceiro);

		contrato.setFormulario(formulario);
		contrato.setCoeficiente(coeficiente);
		contrato.setEmpresa(empresa);
		contrato.setOrganizacao(organizacao);
		contrato.setProduto(produto);
		contrato.setUsuario(usuario);
		contrato.setBanco(b1);
		contrato.setRecompraBanco(b2);
		contrato.setEtapa(etapa);
		contrato.setWorkflow(workflow);
		contrato.setIsActive(rsContrato.getBoolean("isactive"));
		contrato.setNumeroBeneficio(rsContrato.getString("numerobeneficio"));
		contrato.setValorContrato(rsContrato.getDouble("valorcontrato"));
		contrato.setValorDivida(rsContrato.getDouble("valordivida"));
		contrato.setDesconto(rsContrato.getDouble("desconto"));
		contrato.setValorLiquido(rsContrato.getDouble("valorliquido"));
		contrato.setValorMeta(rsContrato.getDouble("valormeta"));
		contrato.setValorParcela(rsContrato.getDouble("valorparcela"));
		contrato.setValorSeguro(rsContrato.getDouble("valorseguro"));
		contrato.setPrazo(rsContrato.getInt("prazo"));
		contrato.setObservacao(rsContrato.getString("observacao"));
		contrato.setQtdParcelasAberto(rsContrato.getInt("qtdparcelasaberto"));

		contratos.add(contrato);

	}
	
	private void getContrato(Contrato contrato) throws SQLException {
		
		Calendar created = new GregorianCalendar();
		Calendar contratoCreated = new GregorianCalendar();
		Formulario formulario = new Formulario();
		Usuario usuario = new Usuario();
		Usuario supervisor = new Usuario();
		ParceiroNegocio parceiro = new ParceiroNegocio();
		Coeficiente coeficiente = new Coeficiente();
		Produto produto = new Produto();
		Workflow workflow = new Workflow();
		Etapa etapa = new Etapa();
		Empresa empresa = new Empresa();
		Organizacao organizacao = new Organizacao();

		Banco b1 = new Banco();
		Banco b2 = new Banco();
		
		empresa.setEmpresa_id(rsContrato.getLong("empresa_id"));
		empresa.setNome(rsContrato.getString("empresa_nome"));
		
		organizacao.setOrganizacao_id(rsContrato.getLong("organizacao_id"));
		organizacao.setNome(rsContrato.getString("organizacao_nome"));

		supervisor.setUsuario_id(rsContrato.getLong("usuario_super_id"));
		supervisor.setNome(rsContrato.getString("usuario_super"));

		usuario.setUsuario_id(rsContrato.getLong("usuario_id"));
		usuario.setNome(rsContrato.getString("usuario_nome"));
		usuario.setApelido(rsContrato.getString("usuario_apelido"));
		usuario.setSupervisorUsuario(supervisor);

		formulario.setFormulario_id(rsContrato.getLong("formulario_id"));
		created.setTime(rsContrato.getDate("created"));
		formulario.setCreated(created);
		
		contratoCreated.setTime(rsContrato.getDate("contratoCreated"));
		contrato.setCreated(contratoCreated);

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
		
		etapa.setEtapa_id(rsContrato.getLong("etapa_id"));
		etapa.setNome(rsContrato.getString("etapa_nome"));

		formulario.setParceiroNegocio(parceiro);
		contrato.setEmpresa(empresa);
		contrato.setOrganizacao(organizacao);
		contrato.setFormulario(formulario);
		contrato.setCoeficiente(coeficiente);
		contrato.setProduto(produto);
		contrato.setUsuario(usuario);
		contrato.setBanco(b1);
		contrato.setRecompraBanco(b2);
		contrato.setEtapa(etapa);
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

	}

}
