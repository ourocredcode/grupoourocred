package br.com.sgo.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.BancoDao;
import br.com.sgo.dao.CoeficienteDao;
import br.com.sgo.dao.ConferenciaDao;
import br.com.sgo.dao.ContratoDao;
import br.com.sgo.dao.ControleDao;
import br.com.sgo.dao.EtapaDao;
import br.com.sgo.dao.FormularioDao;
import br.com.sgo.dao.HistoricoContratoDao;
import br.com.sgo.dao.HistoricoControleDao;
import br.com.sgo.dao.LogisticaDao;
import br.com.sgo.dao.ParceiroBeneficioDao;
import br.com.sgo.dao.ParceiroInfoBancoDao;
import br.com.sgo.dao.ParceiroLocalidadeDao;
import br.com.sgo.dao.ParceiroNegocioDao;
import br.com.sgo.dao.PeriodoDao;
import br.com.sgo.dao.ProdutoBancoDao;
import br.com.sgo.dao.ProdutoDao;
import br.com.sgo.dao.TabelaDao;
import br.com.sgo.dao.TipoControleDao;
import br.com.sgo.dao.TipoLogisticaDao;
import br.com.sgo.dao.TipoProcedimentoDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Banco;
import br.com.sgo.modelo.Coeficiente;
import br.com.sgo.modelo.Conferencia;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Controle;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Etapa;
import br.com.sgo.modelo.Formulario;
import br.com.sgo.modelo.HistoricoContrato;
import br.com.sgo.modelo.HistoricoControle;
import br.com.sgo.modelo.Logistica;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.ParceiroBeneficio;
import br.com.sgo.modelo.ParceiroInfoBanco;
import br.com.sgo.modelo.ParceiroLocalidade;
import br.com.sgo.modelo.ParceiroNegocio;
import br.com.sgo.modelo.Periodo;
import br.com.sgo.modelo.Produto;
import br.com.sgo.modelo.Tabela;
import br.com.sgo.modelo.TipoLogistica;
import br.com.sgo.modelo.TipoProcedimento;
import br.com.sgo.modelo.Usuario;

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
	private final EtapaDao etapaDao;
	private final PeriodoDao periodoDao;
	private final TipoLogisticaDao tipoLogisticaDao;
	private final TipoControleDao tipoControleDao;
	private final TipoProcedimentoDao tipoProcedimentoDao;
	private final LogisticaDao logisticaDao;
	private final HistoricoContratoDao historicoContratoDao;
	private final HistoricoControleDao historicoControleDao;
	private final ControleDao controleDao;
	private final ConferenciaDao conferenciaDao;
	private final ParceiroBeneficioDao parceiroBeneficioDao;
	private final ParceiroNegocioDao parceiroNegocioDao;
	private final ParceiroInfoBancoDao parceiroInfoBancoDao;
	private final ParceiroLocalidadeDao parceiroLocalidadeDao;

	private Contrato contrato;
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;
	private Formulario formulario;
	private ParceiroNegocio parceiroNegocio;
	private ParceiroLocalidade parceiroLocalidade;
	private ParceiroInfoBanco parceiroInfoBanco;
	private ParceiroBeneficio parceiroBeneficio;
	private Collection<Banco> bancos;
	private Controle boleto;
	private Controle averbacao;
	private Collection<Conferencia> conferencias;
	private Collection<Produto> produtos;
	private Collection<Coeficiente> coeficientes = new ArrayList<Coeficiente>();;
	private Collection<Etapa> etapas;
	private Collection<Periodo> periodos;
	private Collection<TipoLogistica> tiposLogistica;
	private Collection<Logistica> logisticas;
	private Collection<Contrato> contratos;
	private Collection<HistoricoContrato> historico;
	private Collection<HistoricoControle> historicoControleBoleto;
	private Collection<HistoricoControle> historicoControleAverbacao;

	public ContratoController(Result result,BancoDao bancoDao,ProdutoBancoDao produtoBancoDao,ProdutoDao produtoDao,CoeficienteDao coeficienteDao,Contrato contrato,
			Formulario formulario,TabelaDao tabelaDao,ContratoDao contratoDao,FormularioDao formularioDao,EtapaDao etapaDao,UsuarioInfo usuarioInfo,
			PeriodoDao periodoDao,TipoLogisticaDao tipoLogisticaDao,LogisticaDao logisticaDao,Empresa empresa,Organizacao organizacao,Usuario usuario,
			ParceiroNegocio parceiroNegocio, ParceiroLocalidade parceiroLocalidade, ParceiroInfoBanco parceiroInfoBanco, ParceiroBeneficio parceiroBeneficio,
			HistoricoContratoDao historicoContratoDao, HistoricoControleDao historicoControleDao,Controle boleto,  Controle averbacao, Collection<Conferencia> conferencias ,
			ControleDao controleDao, ParceiroBeneficioDao parceiroBeneficioDao,TipoControleDao tipoControleDao,ParceiroNegocioDao parceiroNegocioDao,
			ParceiroInfoBancoDao parceiroInfoBancoDao,ParceiroLocalidadeDao parceiroLocalidadeDao,ConferenciaDao conferenciaDao,TipoProcedimentoDao tipoProcedimentoDao){		

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.contrato = contrato;
		this.formulario = formulario;
		this.parceiroInfoBanco = parceiroInfoBanco;
		this.parceiroBeneficio = parceiroBeneficio;
		this.parceiroNegocio = parceiroNegocio;
		this.parceiroLocalidade = parceiroLocalidade;
		this.bancoDao = bancoDao;
		this.contratoDao = contratoDao;
		this.produtoBancoDao = produtoBancoDao;
		this.produtoDao = produtoDao;
		this.coeficienteDao = coeficienteDao;
		this.formularioDao = formularioDao;
		this.tabelaDao = tabelaDao;
		this.periodoDao = periodoDao;
		this.tipoControleDao = tipoControleDao;
		this.etapaDao = etapaDao;
		this.tipoLogisticaDao = tipoLogisticaDao;
		this.logisticaDao = logisticaDao;
		this.historicoContratoDao = historicoContratoDao;
		this.historicoControleDao = historicoControleDao;
		this.parceiroBeneficioDao = parceiroBeneficioDao;
		this.parceiroNegocioDao = parceiroNegocioDao;
		this.parceiroInfoBancoDao = parceiroInfoBancoDao;
		this.parceiroLocalidadeDao = parceiroLocalidadeDao;
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();
		this.usuario = usuarioInfo.getUsuario();
		this.boleto = boleto;
		this.averbacao = averbacao;
		this.conferencias = conferencias;
		this.controleDao = controleDao;
		this.conferenciaDao = conferenciaDao;
		this.tipoProcedimentoDao = tipoProcedimentoDao;

	}

	@Post
	@Path("/contrato/cadastro")
	public void cadastro(Long id){

		Collection<Banco> bancos = this.bancoDao.buscaBancoByGrupo("Tomadores");
		Collection<Banco> recompraBancos = this.bancoDao.buscaBancoByGrupo("Comprados");

		contrato = contratoDao.load(id);

		Coeficiente coeficienteContrato = new Coeficiente();
		coeficienteContrato.setValor(contrato.getCoeficiente().getValor());

		coeficientes = coeficienteDao.buscaCoeficientesByBancoProduto(contrato.getBanco().getBanco_id(), contrato.getProduto().getProduto_id());

		for(Coeficiente c : coeficientes){
			if(c.getValor().compareTo(coeficienteContrato.getValor()) == 0) {
				coeficienteContrato.setCoeficiente_id(c.getCoeficiente_id());
				coeficienteContrato.setTabela(c.getTabela());
				coeficienteContrato.setPercentualMeta(c.getPercentualMeta());
			}
		}

		coeficientes.add(coeficienteContrato);

		contrato = contratoDao.load(id);

		Banco banco = bancoDao.buscaBancoById(contrato.getBanco().getBanco_id());
		Collection<Produto> produtos = produtoDao.buscaProdutosByBanco(banco.getBanco_id());

		result.include("bancos",bancos);
		result.include("recompraBancos",recompraBancos);
		result.include("contrato",contrato);
		result.include("banco",banco);
		result.include("produtos",produtos);
		result.include("coeficientes",coeficientes);

	}
	
	@Get
 	@Path("/contrato/status/{id}")
	public void status(Long id){

		contrato = contratoDao.load(id);
		formulario = formularioDao.buscaFormularioByContrato(id);

		etapas = etapaDao.buscaEtapaByContratoPerfil(id, usuarioInfo.getPerfil().getPerfil_id());
		etapas.add(contrato.getEtapa());
		
		periodos = periodoDao.buscaAllPeriodos();
		historico = historicoContratoDao.buscaHistoricoByContrato(id);
		
		tiposLogistica = tipoLogisticaDao.buscaAllTipoLogistica();
		logisticas = logisticaDao.buscaLogisticaByContrato(id);
		
		boleto = this.controleDao.buscaControleByContratoTipoControle(id, tipoControleDao.buscaTipoControleByNome("Boleto").getTipoControle_id());
		averbacao = this.controleDao.buscaControleByContratoTipoControle(id, tipoControleDao.buscaTipoControleByNome("Averbacao").getTipoControle_id());

		TipoProcedimento tipoProcedimento = this.tipoProcedimentoDao.buscaTipoProcedimentoByNome("Contrato");
		
		conferencias = this.conferenciaDao.buscaConferenciaByEmOrTipoProcedimentoContrato(contrato.getEmpresa().getEmpresa_id(), contrato.getOrganizacao().getOrganizacao_id(), 
				tipoProcedimento.getTipoProcedimento_id(), contrato.getContrato_id());

		contratos = contratoDao.buscaContratoByFormulario(formulario.getFormulario_id());

		if(boleto != null)
			historicoControleBoleto = this.historicoControleDao.buscaHistoricoByContratoControle(contrato.getContrato_id(), boleto.getControle_id());

		if(averbacao != null)
			historicoControleAverbacao = this.historicoControleDao.buscaHistoricoByContratoControle(contrato.getContrato_id(), averbacao.getControle_id());
		
		parceiroBeneficio = parceiroBeneficioDao.buscaParceiroBeneficioByNumeroBeneficio(contrato.getNumeroBeneficio());
		
		parceiroNegocio = parceiroNegocioDao.load(parceiroBeneficio.getParceiroNegocio().getParceiroNegocio_id());
		parceiroInfoBanco = parceiroInfoBancoDao.buscaParceiroInfoBancoByParceiro(parceiroBeneficio.getParceiroNegocio().getParceiroNegocio_id());

		for(ParceiroLocalidade pl : parceiroLocalidadeDao.buscaParceiroLocalidades(parceiroNegocio.getParceiroNegocio_id())){

			if(pl.getTipoEndereco().getNome().equals("Assinatura")){
				parceiroLocalidade = pl;
			}

		}

		formulario.setParceiroNegocio(parceiroNegocio);
		formulario.setParceiroBeneficio(parceiroBeneficio);
		formulario.setParceiroLocalidade(parceiroLocalidade);
		formulario.setParceiroInfoBanco(parceiroInfoBanco);

		result.include("formulario",formulario);
		result.include("contrato",contrato);
		result.include("etapas",etapas);
		result.include("periodos", periodos);
		result.include("tiposLogistica", tiposLogistica);
		result.include("logisticas",logisticas);
		result.include("contratos",contratos);
		result.include("historico",historico);
		result.include("boleto",boleto);
		result.include("averbacao",averbacao);
		result.include("conferencias",conferencias);
		result.include("historicoControleBoleto",historicoControleBoleto);
		result.include("historicoControleAverbacao",historicoControleAverbacao);

	}
	
	@Post
	@Path("/contrato/altera")
	public void altera(Contrato contrato) {

		List<String> log = new ArrayList<String>();

		this.contrato = this.contratoDao.load(contrato.getContrato_id());
		contrato.setCoeficiente(coeficienteDao.load(contrato.getCoeficiente().getCoeficiente_id()));

		if(this.contrato.getBanco().getBanco_id() != contrato.getBanco().getBanco_id()){
			log.add("Banco alterado de : " + this.contrato.getBanco().getNome() + " para: " + contrato.getBanco().getNome());
			this.contrato.setBanco(contrato.getBanco() == null ? null : contrato.getBanco());
		}

		if(this.contrato.getProduto().getProduto_id() != contrato.getProduto().getProduto_id()) {
			log.add("Produto alterado: " + this.contrato.getProduto().getNome() + " para: " + contrato.getProduto().getNome());
			this.contrato.setProduto(contrato.getProduto() == null ? null : contrato.getProduto());
		}

		if(this.contrato.getQtdParcelasAberto() != contrato.getQtdParcelasAberto()){
			log.add("Parcelas Aberto alterado: " + this.contrato.getQtdParcelasAberto() + " para : " + contrato.getQtdParcelasAberto());
			this.contrato.setQtdParcelasAberto(contrato.getQtdParcelasAberto() == null ? null : contrato.getQtdParcelasAberto());
		}

		if(this.contrato.getPrazo() != contrato.getPrazo()) {
			log.add("Prazo alterado de : " + this.contrato.getPrazo() + " para : " + contrato.getPrazo());
			this.contrato.setPrazo(contrato.getPrazo() == null ? null : contrato.getPrazo());
		}		

		if(this.contrato.getCoeficiente().getCoeficiente_id() != contrato.getCoeficiente().getCoeficiente_id()){
			log.add("Coeficiente alterado de : " + this.contrato.getCoeficiente().getValor() + " para : " + contrato.getCoeficiente().getValor());
			this.contrato.setCoeficiente(contrato.getCoeficiente() == null ? null : contrato.getCoeficiente());
		}

		if(this.contrato.getValorContrato().compareTo(contrato.getValorContrato()) != 0){
			log.add("Valor contrato alterado de : " + this.contrato.getValorContrato() + " para : " + contrato.getValorContrato());
			this.contrato.setValorContrato(contrato.getValorContrato() == null ? null : contrato.getValorContrato());
		}

		if(this.contrato.getValorLiquido().compareTo(contrato.getValorLiquido()) != 0) {
			log.add("Valor Liquido alterado de : " + this.contrato.getValorLiquido() + " para: " + contrato.getValorLiquido());
			this.contrato.setValorLiquido(contrato.getValorLiquido() == null ? null : contrato.getValorLiquido());
		}

		if(this.contrato.getValorParcela().compareTo(contrato.getValorParcela()) != 0) {
			log.add("Valor Parcela alterado de : " + this.contrato.getValorParcela() + " para: " + contrato.getValorParcela());
			this.contrato.setValorParcela(contrato.getValorParcela() == null ? null : contrato.getValorParcela());
		}

		if(this.contrato.getValorMeta().compareTo(contrato.getValorMeta()) != 0) {
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

		this.contratoDao.beginTransaction();
		this.contratoDao.atualiza(this.contrato);
		this.contratoDao.commit();
		
		for(String l : log){

			HistoricoContrato historico = new HistoricoContrato();
			historico.setEmpresa(empresa);
			historico.setOrganizacao(organizacao);
			historico.setIsActive(true);
			historico.setCreatedBy(usuario);
			historico.setCreated(GregorianCalendar.getInstance());
			historico.setObservacao(l);
			historico.setContrato(contrato);

			this.historicoContratoDao.beginTransaction();
			this.historicoContratoDao.adiciona(historico);
			this.historicoContratoDao.commit();

		}
		
		result.redirectTo(this).status(this.contrato.getContrato_id());

	}
	
	@Post
	@Path("/contrato/altera/status")
	public void alteraStatus(Contrato contrato) {

		List<String> log = new ArrayList<String>();

		this.contrato = this.contratoDao.load(contrato.getContrato_id());

		if(!(this.contrato.getEtapa().getEtapa_id() == contrato.getEtapa().getEtapa_id())){
			log.add("Status alterado de : " + this.contrato.getEtapa().getNome() + " para : " + contrato.getEtapa().getNome());
			this.contrato.setEtapa(contrato.getEtapa() == null ? null : contrato.getEtapa());
		}

		this.contratoDao.beginTransaction();
		this.contratoDao.atualiza(this.contrato);
		this.contratoDao.commit();

		for(String l : log){

			HistoricoContrato historico = new HistoricoContrato();
			historico.setEmpresa(empresa);
			historico.setOrganizacao(organizacao);
			historico.setIsActive(true);
			historico.setCreatedBy(usuario);
			historico.setCreated(GregorianCalendar.getInstance());
			historico.setObservacao(l);
			historico.setContrato(contrato);

			this.historicoContratoDao.beginTransaction();
			this.historicoContratoDao.adiciona(historico);
			this.historicoContratoDao.commit();

		}

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
	
	@Post
 	@Path("/contrato/coeficienteHistorico")
	public void coeficientes(Long coeficienteId) {

		coeficientes.add(coeficienteDao.load(coeficienteId));
		result.include("coeficientes",coeficientes);

	}

}