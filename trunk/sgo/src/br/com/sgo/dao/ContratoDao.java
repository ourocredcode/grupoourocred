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
import br.com.sgo.infra.Dao;
import br.com.sgo.modelo.Banco;
import br.com.sgo.modelo.Coeficiente;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Etapa;
import br.com.sgo.modelo.Formulario;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.ParceiroNegocio;
import br.com.sgo.modelo.Produto;
import br.com.sgo.modelo.TipoControle;
import br.com.sgo.modelo.Usuario;
import br.com.sgo.modelo.Workflow;

@Component
public class ContratoDao extends Dao<Contrato> {

	private ConnJDBC conexao;
	private PreparedStatement stmt;
	private Connection conn;
	private ResultSet rsContrato;

	private static final String sqlContratos = " SELECT CONTRATO.empresa_id, EMPRESA.nome as empresa_nome, CONTRATO.organizacao_id, ORGANIZACAO.nome as organizacao_nome, "+
			" FORMULARIO.created,FORMULARIO.formulario_id, FORMULARIO.parceironegocio_id , CONTRATO.contrato_id,CONTRATO.formulario_id, "+
			" CONTRATO.coeficiente_id, CONTRATO.workflow_id, "+
			" CONTRATO.produto_id, CONTRATO.tabela_id, "+
			" CONTRATO.banco_id, CONTRATO.recompra_banco_id, "+
			" CONTRATO.usuario_id, "+
			" USUARIO.nome as usuario_nome, USUARIO.apelido as usuario_apelido, "+
			" USUARIO_SUPERVISOR.usuario_id as usuario_super_id, "+
			" USUARIO_SUPERVISOR.nome as usuario_super, USUARIO_SUPERVISOR.apelido as usuario_super_apelido, "+
			" CONTRATO.prazo, "+
			" CONTRATO.qtdparcelasaberto, CONTRATO.valorseguro, CONTRATO.desconto, CONTRATO.valorcontrato, "+
			" CONTRATO.valordivida, CONTRATO.valorliquido, CONTRATO.valorparcela, CONTRATO.valormeta, CONTRATO.observacao, "+
			" CONTRATO.prazo , CONTRATO.desconto , CONTRATO.qtdparcelasaberto , CONTRATO.numerobeneficio, CONTRATO.isactive, "+
			" BANCO.nome as banco_nome, BANCO_1.nome as bancoRecompra_nome , PRODUTO.nome as produto_nome, COEFICIENTE.valor, "+
			" PARCEIRONEGOCIO.nome as parceiro_nome,PARCEIRONEGOCIO.cpf as parceiro_cpf, "+
			" CONTRATO.etapa_id, WORKFLOW.workflow_id,WORKFLOW.nome as workflow_nome , "+
			" ETAPA.etapa_id, ETAPA.nome as etapa_nome "+
			" FROM " +
			" (((((((((((((((((((CONTRATO (NOLOCK) INNER JOIN ETAPA (NOLOCK) ON CONTRATO.etapa_id = ETAPA.etapa_id) "+
			" INNER JOIN WORKFLOW (NOLOCK) ON CONTRATO.workflow_id = WORKFLOW.workflow_id) "+
			" INNER JOIN USUARIO (NOLOCK) ON CONTRATO.usuario_id = USUARIO.usuario_id) "+
			" LEFT JOIN CONVENIO (NOLOCK) ON CONTRATO.convenio_id = CONVENIO.convenio_id) "+
			" LEFT JOIN MODALIDADE (NOLOCK) ON CONTRATO.modalidade_id = MODALIDADE.modalidade_id) "+
			" INNER JOIN EMPRESA (NOLOCK) ON CONTRATO.empresa_id = EMPRESA.empresa_id) "+
			" INNER JOIN ORGANIZACAO (NOLOCK) ON CONTRATO.organizacao_id = ORGANIZACAO.organizacao_id) "+
			" INNER JOIN FORMULARIO (NOLOCK) ON CONTRATO.formulario_id = FORMULARIO.formulario_id) "+
			" INNER JOIN COEFICIENTE (NOLOCK) ON CONTRATO.coeficiente_id = COEFICIENTE.coeficiente_id) "+
			" INNER JOIN PRODUTO (NOLOCK) ON CONTRATO.produto_id = PRODUTO.produto_id) "+
			" LEFT JOIN TABELA (NOLOCK) ON CONTRATO.tabela_id = TABELA.tabela_id) "+
			" INNER JOIN BANCO (NOLOCK) ON CONTRATO.banco_id = BANCO.banco_id) "+
			" LEFT JOIN SEGURO (NOLOCK) ON CONTRATO.seguro_id = SEGURO.seguro_id) "+
			" LEFT JOIN NATUREZAPROFISSIONAL (NOLOCK) ON CONTRATO.naturezaprofissional_id = NATUREZAPROFISSIONAL.naturezaprofissional_id) "+
			" LEFT JOIN TIPOSAQUE (NOLOCK) ON CONTRATO.tiposaque_id = TIPOSAQUE.tiposaque_id) "+
			" LEFT JOIN BANCO (NOLOCK) AS BANCO_1 ON CONTRATO.recompra_banco_id = BANCO_1.banco_id) "+
			" LEFT JOIN USUARIO (NOLOCK) AS USUARIO_SUPERVISOR ON USUARIO.supervisor_usuario_id = USUARIO_SUPERVISOR.usuario_id) "+
			" LEFT JOIN WORKFLOW (NOLOCK) AS WORKFLOW_1 ON CONTRATO.workflowpendencia_id = WORKFLOW_1.workflow_id) "+
			" LEFT JOIN ETAPA (NOLOCK) AS ETAPA_1 ON CONTRATO.etapapendencia_id = ETAPA_1.etapa_id) "+
			" INNER JOIN PARCEIRONEGOCIO (NOLOCK) ON FORMULARIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id ";  

	public ContratoDao(Session session, ConnJDBC conexao) {

		super(session, Contrato.class);
		this.conexao = conexao;

	}

	public Collection<Contrato> buscaContratoByUsuario(Long usuario_id, Calendar calInicio, Calendar calFim) {

		String sql = sqlContratos;
		
		if(usuario_id != null)
			sql += " WHERE ( USUARIO.usuario_id = ? OR USUARIO_SUPERVISOR.usuario_id = ? ) ";
		
		if(calInicio != null)
			sql += " AND (CONTRATO.created BETWEEN ? AND ? )";

		this.conn = this.conexao.getConexao();

		Collection<Contrato> contratos = new ArrayList<Contrato>();

		try {

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

				this.stmt.setTimestamp(curr,new Timestamp(calInicio.getTimeInMillis()));
				curr++;

				this.stmt.setTimestamp(curr,new Timestamp(calFim.getTimeInMillis()));
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
			sql += " AND (CONTRATO.created BETWEEN ? AND ? )";

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

				this.stmt.setTimestamp(curr,new Timestamp(calInicio.getTimeInMillis()));
				curr++;

				this.stmt.setTimestamp(curr,new Timestamp(calFim.getTimeInMillis()));
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

	public Collection<Contrato> buscaContratoByFiltros(Long empresa_id, Long organizacao_id, Calendar calInicio,Calendar calFim, 
			Calendar calAprovadoInicio,Calendar calAprovadoFim,String cliente, String documento, Collection<String> status,Collection<String> statusFinal,
			Collection<String> produtos,Collection<String> bancos,Collection<String> bancosComprados,Collection<Usuario> consultores) {

		String sql = sqlContratos;
		String clause = "";
		int x = 0;

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
		
		for(String statusFinalAux1 : statusFinal){

			clause = x <= 0 ? "AND" : "OR";

			if(!statusFinalAux1.equals("")){
				sql += clause + " ( ETAPA.nome like ? ) ";
				x++;
				clause = "";
			}
			
			

		}
		
		if(x == 0){
			sql += " AND ( ETAPA.NOME not in ('Aprovado','Recusado','Concluído') ) ";
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
				sql += clause + " ( USUARIO.usuario_id = ? OR USUARIO_SUPERVISOR.usuario_id = ? ) ";
				x++;
				clause = "";
			}

		}
		
		sql += " ) ";

		if(calInicio != null)
			sql += " AND (FORMULARIO.created BETWEEN ? AND ? )";
		
		if(calAprovadoInicio != null)
			sql += " AND (CONTRATO.dataStatusFinal BETWEEN ? AND ? )";

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

				if(!statusAux2.equals("")) {
					this.stmt.setString(curr, '%' + statusAux2 + '%');
					curr++;
				}

			}

			for(String statusFinalAux2 : statusFinal){

				if(!statusFinalAux2.equals("")) {
					this.stmt.setString(curr, '%' + statusFinalAux2 + '%');
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
					this.stmt.setLong(curr,u.getUsuario_id());
					curr++;
					this.stmt.setLong(curr,u.getUsuario_id());
					curr++;
				}

			}

			if(calInicio != null){

				this.stmt.setTimestamp(curr,new Timestamp(calInicio.getTimeInMillis()));
				curr++;

				this.stmt.setTimestamp(curr,new Timestamp(calFim.getTimeInMillis()));
				curr++;

			} 
			
			if(calAprovadoInicio != null){

				this.stmt.setTimestamp(curr,new Timestamp(calAprovadoInicio.getTimeInMillis()));
				curr++;

				this.stmt.setTimestamp(curr,new Timestamp(calAprovadoFim.getTimeInMillis()));
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
	
	public Collection<Contrato> buscaDatasControle(Long empresa_id, Long organizacao_id,TipoControle tipoControle,Calendar calInicio,Calendar calFim,
			Calendar previsaoInicio,Calendar previsaoFim, Calendar chegadaInicio,
			Calendar chegadaFim,Calendar vencimentoInicio, Calendar vencimentoFim, Calendar proximaAtuacaoInicio,Calendar proximaAtuacaoFim,String procedimento,
			Collection<String> bancos, Collection<String> produtos, Collection<String> bancosComprados,Collection<String> status, Collection<Usuario> consultores,String cliente,
			String documento,Collection<String> empresas) {

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
				" CONTRATO.prazo, "+
				" CONTRATO.qtdparcelasaberto, CONTRATO.valorseguro, CONTRATO.desconto, CONTRATO.valorcontrato, "+
				" CONTRATO.valordivida, CONTRATO.valorliquido, CONTRATO.valorparcela, CONTRATO.valormeta, CONTRATO.observacao, "+
				" CONTRATO.prazo , CONTRATO.desconto , CONTRATO.qtdparcelasaberto , CONTRATO.numerobeneficio, CONTRATO.isactive, "+
				" BANCO.nome as banco_nome, BANCO_1.nome as bancoRecompra_nome , PRODUTO.nome as produto_nome, COEFICIENTE.valor, "+
				" PARCEIRONEGOCIO.nome as parceiro_nome,PARCEIRONEGOCIO.cpf as parceiro_cpf, "+
				" CONTRATO.etapa_id, WORKFLOW.workflow_id,WORKFLOW.nome as workflow_nome , "+
				" ETAPA.etapa_id, ETAPA.nome as etapa_nome "+
				" FROM " +
				" (((((((((((((((((((( CONTRATO (NOLOCK) INNER JOIN ETAPA (NOLOCK) ON CONTRATO.etapa_id = ETAPA.etapa_id) "+
				" INNER JOIN WORKFLOW (NOLOCK) ON CONTRATO.workflow_id = WORKFLOW.workflow_id) "+
				" INNER JOIN USUARIO (NOLOCK) ON CONTRATO.usuario_id = USUARIO.usuario_id) "+
				" LEFT JOIN CONVENIO (NOLOCK) ON CONTRATO.convenio_id = CONVENIO.convenio_id) "+
				" LEFT JOIN MODALIDADE (NOLOCK) ON CONTRATO.modalidade_id = MODALIDADE.modalidade_id) "+
				" INNER JOIN EMPRESA (NOLOCK) ON CONTRATO.empresa_id = EMPRESA.empresa_id) "+
				" INNER JOIN ORGANIZACAO (NOLOCK) ON CONTRATO.organizacao_id = ORGANIZACAO.organizacao_id) "+
				" INNER JOIN FORMULARIO (NOLOCK) ON CONTRATO.formulario_id = FORMULARIO.formulario_id) "+
				" INNER JOIN COEFICIENTE (NOLOCK) ON CONTRATO.coeficiente_id = COEFICIENTE.coeficiente_id) "+
				" INNER JOIN PRODUTO (NOLOCK) ON CONTRATO.produto_id = PRODUTO.produto_id) "+
				" LEFT JOIN TABELA (NOLOCK) ON CONTRATO.tabela_id = TABELA.tabela_id) "+
				" INNER JOIN BANCO (NOLOCK) ON CONTRATO.banco_id = BANCO.banco_id) "+
				" LEFT JOIN SEGURO (NOLOCK) ON CONTRATO.seguro_id = SEGURO.seguro_id) "+
				" LEFT JOIN NATUREZAPROFISSIONAL (NOLOCK) ON CONTRATO.naturezaprofissional_id = NATUREZAPROFISSIONAL.naturezaprofissional_id) "+
				" LEFT JOIN TIPOSAQUE (NOLOCK) ON CONTRATO.tiposaque_id = TIPOSAQUE.tiposaque_id) "+
				" LEFT JOIN BANCO (NOLOCK) AS BANCO_1 ON CONTRATO.recompra_banco_id = BANCO_1.banco_id) "+
				" LEFT JOIN USUARIO (NOLOCK) AS USUARIO_SUPERVISOR ON USUARIO.supervisor_usuario_id = USUARIO_SUPERVISOR.usuario_id) "+
				" LEFT JOIN WORKFLOW (NOLOCK) AS WORKFLOW_1 ON CONTRATO.workflowpendencia_id = WORKFLOW_1.workflow_id) "+
				" LEFT JOIN ETAPA (NOLOCK) AS ETAPA_1 ON CONTRATO.etapapendencia_id = ETAPA_1.etapa_id)" +
				" LEFT JOIN CONTROLE (NOLOCK) AS CONTROLE ON CONTRATO.contrato_id = CONTROLE.contrato_id)  "+
				" INNER JOIN PARCEIRONEGOCIO (NOLOCK) ON FORMULARIO.parceironegocio_id = PARCEIRONEGOCIO.parceironegocio_id " ;

		String clause = "";
		int x = 0;

		sql += " WHERE CONTRATO.empresa_id = ? AND CONTRATO.organizacao_id = ? ";
		
		if(tipoControle != null)
			sql += " AND CONTROLE.tipocontrole_id = ?";

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

			if(tipoControle != null){
				this.stmt.setLong(curr, tipoControle.getTipoControle_id());
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

				if(!statusAux2.equals("")) {
					this.stmt.setString(curr, '%' + statusAux2 + '%');
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
					this.stmt.setLong(curr,u.getUsuario_id());
					curr++;
					this.stmt.setLong(curr,u.getUsuario_id());
					curr++;
				}

			}
			
			if(previsaoInicio != null){

				this.stmt.setTimestamp(curr,new Timestamp(previsaoInicio.getTimeInMillis()));
				curr++;

				this.stmt.setTimestamp(curr,new Timestamp(previsaoFim.getTimeInMillis()));
				curr++;

			} 
			
			if(chegadaInicio != null){

				this.stmt.setTimestamp(curr,new Timestamp(chegadaInicio.getTimeInMillis()));
				curr++;

				this.stmt.setTimestamp(curr,new Timestamp(chegadaFim.getTimeInMillis()));
				curr++;

			} 
			
			if(vencimentoInicio != null){

				this.stmt.setTimestamp(curr,new Timestamp(vencimentoInicio.getTimeInMillis()));
				curr++;

				this.stmt.setTimestamp(curr,new Timestamp(vencimentoInicio.getTimeInMillis()));
				curr++;

			}
			
			if(proximaAtuacaoInicio != null){

				this.stmt.setTimestamp(curr,new Timestamp(proximaAtuacaoInicio.getTimeInMillis()));
				curr++;

				this.stmt.setTimestamp(curr,new Timestamp(proximaAtuacaoInicio.getTimeInMillis()));
				curr++;

			}
			
			if(calInicio != null){

				this.stmt.setTimestamp(curr,new Timestamp(calInicio.getTimeInMillis()));
				curr++;

				this.stmt.setTimestamp(curr,new Timestamp(calFim.getTimeInMillis()));
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
	
	public HashMap<String,Integer> buscaContratosToCountEtapas(Long empresa_id , Long organizacao_id, Long usuario_id) {

		String sql = "SELECT ETAPA.nome as etapa_nome, COUNT(ETAPA.nome) as etapaCount " +
					" FROM ((CONTRATO INNER JOIN ETAPA ON CONTRATO.etapa_id = ETAPA.etapa_id) " +
					" INNER JOIN USUARIO ON CONTRATO.usuario_id = USUARIO.usuario_id) " +
					" INNER JOIN USUARIO AS USUARIO_SUPERVISOR ON USUARIO.supervisor_usuario_id = USUARIO_SUPERVISOR.usuario_id ";

			sql += " WHERE CONTRATO.empresa_id = ? ";

		if(organizacao_id != null)
			sql += " AND CONTRATO.organizacao_id = ? ";

		if(usuario_id != null)
			sql += " AND (CONTRATO.usuario_id = ? OR USUARIO_SUPERVISOR.usuario_id = ? ) ";

		
		sql +=  " AND ( ETAPA.NOME not in ('Aprovado','Recusado','Concluído') ) GROUP BY ETAPA.nome ";

		this.conn = this.conexao.getConexao();
		

		HashMap<String,Integer> map = new HashMap<String,Integer>();

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
				Integer etapaCount = rsContrato.getInt("etapaCount");

				map.put(etapa_nome,etapaCount);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.conexao.closeConnection(rsContrato, stmt, conn);
		return map;
	}
	
	public HashMap<String,Double> buscaContratosToCountEtapasStatusFinal(Long empresa_id , Long organizacao_id, Long usuario_id, Calendar calInicio, Calendar calFim) {

		String sql = " SELECT ETAPA.nome as etapa_nome, SUM(CONTRATO.valormeta) as metaCount " +
					" FROM ((CONTRATO INNER JOIN ETAPA ON CONTRATO.etapa_id = ETAPA.etapa_id) " +
					" INNER JOIN USUARIO ON CONTRATO.usuario_id = USUARIO.usuario_id) " +
					" INNER JOIN USUARIO AS USUARIO_SUPERVISOR ON USUARIO.supervisor_usuario_id = USUARIO_SUPERVISOR.usuario_id " +
					" INNER JOIN FORMULARIO ON FORMULARIO.formulario_id = CONTRATO.formulario_id ";

			sql += " WHERE CONTRATO.empresa_id = ? ";

		if(organizacao_id != null)
			sql += " AND CONTRATO.organizacao_id = ? ";

		if(usuario_id != null)
			sql += " AND (CONTRATO.usuario_id = ? OR USUARIO_SUPERVISOR.usuario_id = ? ) ";

		if(calInicio != null)
			sql += " AND (FORMULARIO.created BETWEEN ? AND ? )";
		
		sql +=  " AND ( ETAPA.NOME in ('Aprovado','Recusado','Concluído') ) GROUP BY ETAPA.nome ";

		this.conn = this.conexao.getConexao();
		

		HashMap<String,Double> map = new HashMap<String,Double>();

		try {

			this.stmt = conn.prepareStatement(sql);
			
			int curr = 1;
			
			this.stmt.setLong(curr, empresa_id);
			this.stmt.setLong(++curr, organizacao_id);
			
			if(usuario_id != null){

				this.stmt.setLong(++curr, usuario_id);
				this.stmt.setLong(++curr, usuario_id);
				
			}
			
			if(calInicio != null){

				this.stmt.setTimestamp(++curr,new Timestamp(calInicio.getTimeInMillis()));

				this.stmt.setTimestamp(++curr,new Timestamp(calFim.getTimeInMillis()));

			}

			this.rsContrato = this.stmt.executeQuery();

			while (rsContrato.next()) {

				String etapa_nome = rsContrato.getString("etapa_nome");
				Double etapaCount = rsContrato.getDouble("metaCount");

				map.put(etapa_nome,etapaCount);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.conexao.closeConnection(rsContrato, stmt, conn);
		return map;

	}

	private void getContratos(Collection<Contrato> contratos) throws SQLException {
		
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
	
	private void getContrato(Contrato contrato) throws SQLException {
		
		Calendar created = new GregorianCalendar();
		Formulario formulario = new Formulario();
		Usuario usuario = new Usuario();
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

		usuario.setUsuario_id(rsContrato.getLong("usuario_id"));
		usuario.setNome(rsContrato.getString("usuario_nome"));
		usuario.setApelido(rsContrato.getString("usuario_apelido"));

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
