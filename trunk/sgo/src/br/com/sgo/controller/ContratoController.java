package br.com.sgo.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.BancoDao;
import br.com.sgo.dao.CoeficienteDao;
import br.com.sgo.dao.ContratoDao;
import br.com.sgo.dao.FormularioDao;
import br.com.sgo.dao.PeriodoDao;
import br.com.sgo.dao.ProdutoBancoDao;
import br.com.sgo.dao.ProdutoDao;
import br.com.sgo.dao.TabelaDao;
import br.com.sgo.dao.WorkflowEtapaDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Banco;
import br.com.sgo.modelo.Coeficiente;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Formulario;
import br.com.sgo.modelo.Periodo;
import br.com.sgo.modelo.Produto;
import br.com.sgo.modelo.Tabela;
import br.com.sgo.modelo.TipoLogistica;
import br.com.sgo.modelo.WorkflowEtapa;

@Resource
public class ContratoController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final BancoDao bancoDao;
	private final ProdutoBancoDao produtoBancoDao;
	private final ProdutoDao produtoDao;
	private final CoeficienteDao coeficienteDao;
	private final TabelaDao tabelaDao;
	private final ContratoDao contratoDao;
	private final FormularioDao formularioDao;
	private final WorkflowEtapaDao workFlowetapaDao;
	private final PeriodoDao periodoDao;

	private Contrato contrato;
	private Formulario formulario;
	private Collection<Banco> bancos;
	private Collection<Produto> produtos;
	private Collection<Coeficiente> coeficientes;
	private Collection<WorkflowEtapa> etapas;
	private Collection<Periodo> periodos;
	private Collection<TipoLogistica> tiposLogistica;

	public ContratoController(Result result,BancoDao bancoDao,ProdutoBancoDao produtoBancoDao,ProdutoDao produtoDao,CoeficienteDao coeficienteDao,Contrato contrato,
			Formulario formulario,TabelaDao tabelaDao,ContratoDao contratoDao,FormularioDao formularioDao,WorkflowEtapaDao workFlowetapaDao,UsuarioInfo usuarioInfo,
			PeriodoDao periodoDao){		

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.contrato = contrato;
		this.formulario = formulario;
		this.bancoDao = bancoDao;
		this.contratoDao = contratoDao;
		this.produtoBancoDao = produtoBancoDao;
		this.produtoDao = produtoDao;
		this.coeficienteDao = coeficienteDao;
		this.formularioDao = formularioDao;
		this.tabelaDao = tabelaDao;
		this.periodoDao = periodoDao;
		this.workFlowetapaDao = workFlowetapaDao;

	}

	@Post
	@Path("/contrato/cadastro")
	public void cadastro(Long id){

	}
	
	@Get
 	@Path("/contrato/status/{id}")
	public void status(Long id){

		contrato = contratoDao.load(id);
		formulario = formularioDao.buscaFormularioByContrato(id);
		etapas = workFlowetapaDao.buscaWorKFlowEtapaByContratoPerfil(id, usuarioInfo.getPerfil().getPerfil_id());
		periodos = periodoDao.buscaAllPeriodos();
		etapas.add(contrato.getWorkflowEtapa());

		result.include("formulario",formulario);
		result.include("contrato",contrato);
		result.include("etapas",etapas);
		result.include("periodos", periodos);

	}
	
	@Post
	@Path("/alteraContrato")
	public void alteraContrato(Contrato contrato) {

		List<String> log = new ArrayList<String>();

		this.contrato = this.contratoDao.load(contrato.getContrato_id());

		if(!this.contrato.getBanco().equals(contrato.getBanco())){
			log.add("Banco alterado de : " + this.contrato.getBanco() + " para: " + contrato.getBanco());
			this.contrato.setBanco(contrato.getBanco() == null ? null : contrato.getBanco());
		}

		if(!this.contrato.getBanco().equals(contrato.getBanco())) {
			log.add("Banco Comprado alterado de : " + this.contrato.getBanco().getNome() + " para: " + contrato.getBanco().getNome());
			this.contrato.setBanco(contrato.getBanco() == null ? null : contrato.getBanco());
		}

		if(!this.contrato.getProduto().equals(contrato.getProduto())) {
			log.add("Produto alterado: " + this.contrato.getProduto() + " para: " + contrato.getProduto());
			this.contrato.setProduto(contrato.getProduto() == null ? null : contrato.getProduto());
		}

		if(!(this.contrato.getQtdParcelasAberto() == contrato.getQtdParcelasAberto())){
			log.add("Parcelas Aberto alterado: " + this.contrato.getQtdParcelasAberto() + " para : " + contrato.getQtdParcelasAberto());
			this.contrato.setQtdParcelasAberto(contrato.getQtdParcelasAberto() == null ? null : contrato.getQtdParcelasAberto());
		}

		if(!(this.contrato.getPrazo() == contrato.getPrazo())) {
			log.add("Prazo alterado de : " + this.contrato.getPrazo() + " para : " + contrato.getPrazo());
			this.contrato.setPrazo(contrato.getPrazo() == null ? null : contrato.getPrazo());
		}		

		if(!(this.contrato.getCoeficiente() == contrato.getCoeficiente())){
			log.add("Coeficiente alterado de : " + this.contrato.getCoeficiente() + " para : " + contrato.getCoeficiente());
			this.contrato.setCoeficiente(contrato.getCoeficiente() == null ? null : contrato.getCoeficiente());
		}

		if(!( this.contrato.getValorContrato().compareTo(contrato.getValorContrato()) == 0)){
			log.add("Valor contrato alterado de : " + this.contrato.getValorContrato() + " para : " + contrato.getValorContrato());
			this.contrato.setValorContrato(contrato.getValorContrato() == null ? null : contrato.getValorContrato());
		}

		if(! (this.contrato.getValorLiquido().compareTo(contrato.getValorLiquido()) == 0)) {
			log.add("Valor Liquido alterado de : " + this.contrato.getValorLiquido() + " para: " + contrato.getValorLiquido());
			this.contrato.setValorLiquido(contrato.getValorLiquido() == null ? null : contrato.getValorLiquido());
		}

		if(! (this.contrato.getValorParcela().compareTo(contrato.getValorParcela()) == 0)) {
			log.add("Valor Parcela alterado de : " + this.contrato.getValorParcela() + " para: " + contrato.getValorParcela());
			this.contrato.setValorParcela(contrato.getValorParcela() == null ? null : contrato.getValorParcela());
		}

		if(! (this.contrato.getValorMeta().compareTo(contrato.getValorMeta()) == 0)) {
			log.add("Valor Meta Alterado de : " + this.contrato.getValorMeta() + " para : " + contrato.getValorMeta());
			this.contrato.setValorMeta(contrato.getValorMeta() == null ? null : contrato.getValorMeta());
		}

		if(!(this.contrato.getValorDivida() == null && contrato.getValorDivida() == null)){
			if(!(this.contrato.getValorDivida().compareTo(contrato.getValorDivida() == null ? null : contrato.getValorDivida()) == 0)){
				log.add("Valor Divida alterado de : " + this.contrato.getValorDivida() + " para : " + contrato.getValorDivida());
				this.contrato.setValorDivida(contrato.getValorDivida() == null ? null : contrato.getValorDivida());
			}
		}

		if(!(this.contrato.getValorSeguro() == null && contrato.getValorSeguro() == null)){
			if(!(this.contrato.getValorSeguro().compareTo(contrato.getValorSeguro() == null ? null : contrato.getValorSeguro()) == 0)){
				log.add("Valor Seguro alterado de : " + this.contrato.getValorSeguro() + " para : " + contrato.getValorSeguro());
				this.contrato.setValorSeguro(contrato.getValorSeguro() == null ? null : contrato.getValorSeguro());
			}
		}

		if(!(this.contrato.getDesconto() == null && contrato.getDesconto() == null)) {
			if(!(this.contrato.getDesconto().compareTo(contrato.getDesconto() == null ? null : contrato.getDesconto()) == 0 )) {
				log.add("Valor Desconto alterado de : " + this.contrato.getDesconto() + " para : " + contrato.getDesconto());
				this.contrato.setDesconto(contrato.getDesconto() == null ? null : contrato.getDesconto());
			}
		}
		
		if(!(this.contrato.getWorkflowEtapa() == contrato.getWorkflowEtapa())){
			log.add("Status alterado de : " + this.contrato.getWorkflowEtapa().getNome() + " para : " + contrato.getWorkflowEtapa().getNome());
			this.contrato.setWorkflowEtapa(contrato.getWorkflowEtapa() == null ? null : contrato.getWorkflowEtapa());
		}

		this.contratoDao.atualiza(this.contrato);
		result.redirectTo(this).status(this.contrato.getContrato_id());

	}

	@Post
	@Path("/contrato/produtos")
	public void produtos(Long banco_id) {

		produtos = banco_id == null ? null : produtoDao.buscaProdutosByBanco(banco_id);
		result.include("produtos",produtos);

	}

	@Post
 	@Path("/contrato/coeficientes")
	public void coeficientes(Long banco_id,Long produto_id) {

		coeficientes = produto_id == null ? null : this.coeficienteDao.buscaCoeficientesByBancoProduto(banco_id,produto_id);
		result.include("coeficientes",coeficientes);

	}
	
	@Post
 	@Path("/contrato/prazo")
	public void prazo(Long coeficiente_id) {

		Tabela t = tabelaDao.buscaTabelasByCoeficiente(coeficiente_id);

		contrato.setPrazo(t.getPrazo());

		result.include("contrato",contrato);

	}
	
	@Post
	@Path("/contrato/salva")
	public void salva(Formulario formulario) {	

	}

}