package br.com.sgo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.BancoDao;
import br.com.sgo.dao.BancoProdutoTabelaDao;
import br.com.sgo.dao.CoeficienteDao;
import br.com.sgo.dao.ConferenciaDao;
import br.com.sgo.dao.ContratoDao;
import br.com.sgo.dao.ControleDao;
import br.com.sgo.dao.EtapaDao;
import br.com.sgo.dao.FormularioDao;
import br.com.sgo.dao.HisconBeneficioDao;
import br.com.sgo.dao.HistoricoContratoDao;
import br.com.sgo.dao.HistoricoControleDao;
import br.com.sgo.dao.LogisticaDao;
import br.com.sgo.dao.MeioPagamentoDao;
import br.com.sgo.dao.OperacaoDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.ParceiroBeneficioDao;
import br.com.sgo.dao.ParceiroInfoBancoDao;
import br.com.sgo.dao.ParceiroLocalidadeDao;
import br.com.sgo.dao.ParceiroNegocioDao;
import br.com.sgo.dao.PeriodoDao;
import br.com.sgo.dao.PnDao;
import br.com.sgo.dao.ProdutoDao;
import br.com.sgo.dao.TipoControleDao;
import br.com.sgo.dao.TipoLogisticaDao;
import br.com.sgo.dao.TipoProcedimentoDao;
import br.com.sgo.dao.TipoSaqueDao;
import br.com.sgo.dao.TipoWorkflowDao;
import br.com.sgo.dao.UsuarioDao;
import br.com.sgo.dao.WorkflowDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Banco;
import br.com.sgo.modelo.Coeficiente;
import br.com.sgo.modelo.Conferencia;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Controle;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Etapa;
import br.com.sgo.modelo.Formulario;
import br.com.sgo.modelo.HisconBeneficio;
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
import br.com.sgo.modelo.TipoLogistica;
import br.com.sgo.modelo.TipoProcedimento;
import br.com.sgo.modelo.TipoWorkflow;
import br.com.sgo.modelo.Usuario;

@Resource
public class ContratoController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final OrganizacaoDao organizacaoDao;
	private final BancoDao bancoDao;
	private final BancoProdutoTabelaDao bancoProdutoTabelaDao;
	private final ProdutoDao produtoDao;
	private final CoeficienteDao coeficienteDao;
	private final TipoSaqueDao tipoSaqueDao;

	private final ContratoDao contratoDao;
	private final FormularioDao formularioDao;
	private final EtapaDao etapaDao;
	private final PeriodoDao periodoDao;
	private final TipoLogisticaDao tipoLogisticaDao;
	private final TipoControleDao tipoControleDao;
	private final TipoProcedimentoDao tipoProcedimentoDao;
	private final LogisticaDao logisticaDao;
	private final UsuarioDao usuarioDao;
	private final HistoricoContratoDao historicoContratoDao;
	private final HistoricoControleDao historicoControleDao;
	private final HisconBeneficioDao hisconBeneficioDao;
	private final ControleDao controleDao;
	private final ConferenciaDao conferenciaDao;
	private final ParceiroBeneficioDao parceiroBeneficioDao;
	private final ParceiroNegocioDao parceiroNegocioDao;
	private final ParceiroInfoBancoDao parceiroInfoBancoDao;
	private final ParceiroLocalidadeDao parceiroLocalidadeDao;
	private final MeioPagamentoDao meioPagamentoDao;
	private final OperacaoDao operacaoDao;
	private final WorkflowDao workflowDao;
	private final PnDao pnDao;
	private final TipoWorkflowDao tipoWorkflowDao;

	private Contrato contrato;
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;
	private Formulario formulario;
	private ParceiroNegocio parceiroNegocio;
	private ParceiroLocalidade parceiroLocalidade;
	private ParceiroInfoBanco parceiroInfoBanco;
	private ParceiroBeneficio parceiroBeneficio;
	private Controle boleto;
	private Controle averbacao;
	private Collection<Conferencia> conferencias;
	private Collection<Coeficiente> coeficientes = new ArrayList<Coeficiente>();
	private Collection<Etapa> etapas;
	private Collection<Periodo> periodos;
	private Collection<TipoLogistica> tiposLogistica;
	private Collection<Logistica> logisticas;
	private Collection<HistoricoContrato> historico = new ArrayList<HistoricoContrato>();
	private Collection<Usuario> consultores = new ArrayList<Usuario>();
	private Collection<HistoricoControle> historicoControleBoleto;
	private Collection<HistoricoControle> historicoControleAverbacao;

	public ContratoController(Result result,BancoDao bancoDao,OrganizacaoDao organizacaoDao, ProdutoDao produtoDao,CoeficienteDao coeficienteDao,Contrato contrato,
			Formulario formulario,ContratoDao contratoDao,FormularioDao formularioDao,EtapaDao etapaDao,UsuarioInfo usuarioInfo,
			PeriodoDao periodoDao,TipoLogisticaDao tipoLogisticaDao,LogisticaDao logisticaDao,Empresa empresa,Organizacao organizacao,Usuario usuario,
			ParceiroNegocio parceiroNegocio, ParceiroLocalidade parceiroLocalidade, ParceiroInfoBanco parceiroInfoBanco, ParceiroBeneficio parceiroBeneficio,
			HistoricoContratoDao historicoContratoDao, HistoricoControleDao historicoControleDao,Controle boleto,  Controle averbacao, Collection<Conferencia> conferencias ,
			ControleDao controleDao, ParceiroBeneficioDao parceiroBeneficioDao,TipoControleDao tipoControleDao,ParceiroNegocioDao parceiroNegocioDao,
			ParceiroInfoBancoDao parceiroInfoBancoDao,ParceiroLocalidadeDao parceiroLocalidadeDao,ConferenciaDao conferenciaDao,TipoProcedimentoDao tipoProcedimentoDao
			,MeioPagamentoDao meioPagamentoDao, BancoProdutoTabelaDao bancoProdutoTabelaDao,UsuarioDao usuarioDao,HisconBeneficioDao hisconBeneficioDao,
			PnDao pnDao,TipoSaqueDao tipoSaqueDao,OperacaoDao operacaoDao,TipoWorkflowDao tipoWorkflowDao,WorkflowDao workflowDao){		

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.contrato = contrato;
		this.formulario = formulario;
		this.parceiroInfoBanco = parceiroInfoBanco;
		this.parceiroBeneficio = parceiroBeneficio;
		this.parceiroNegocio = parceiroNegocio;
		this.parceiroLocalidade = parceiroLocalidade;
		this.organizacaoDao = organizacaoDao;
		this.bancoDao = bancoDao;
		this.bancoProdutoTabelaDao = bancoProdutoTabelaDao;
		this.contratoDao = contratoDao;
		this.produtoDao = produtoDao;
		this.coeficienteDao = coeficienteDao;
		this.formularioDao = formularioDao;
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
		this.tipoWorkflowDao = tipoWorkflowDao;
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();
		this.usuario = usuarioInfo.getUsuario();
		this.boleto = boleto;
		this.averbacao = averbacao;
		this.conferencias = conferencias;
		this.controleDao = controleDao;
		this.conferenciaDao = conferenciaDao;
		this.tipoProcedimentoDao = tipoProcedimentoDao;
		this.meioPagamentoDao = meioPagamentoDao;
		this.usuarioDao = usuarioDao;
		this.hisconBeneficioDao = hisconBeneficioDao;
		this.pnDao = pnDao;
		this.tipoSaqueDao = tipoSaqueDao;
		this.operacaoDao = operacaoDao;
		this.workflowDao = workflowDao;

	}

	@Post
	@Path("/contrato/cadastro")
	public void cadastro(Long id){

		result.include("bancos", this.bancoDao.buscaBancosToBancoProdutoByEmpOrg(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
		Collection<Banco> recompraBancos = this.bancoDao.buscaBancoCompradoByEmpOrg(1l, 1l);

		contrato = contratoDao.load(id);

		coeficientes = coeficienteDao.buscaCoeficientesByBancoProduto(contrato.getBanco().getBanco_id(), contrato.getProduto().getProduto_id());
		
		Coeficiente coeficienteContrato = new Coeficiente();
		coeficienteContrato.setValor(contrato.getCoeficiente().getValor());
		coeficienteContrato.setCoeficiente_id(contrato.getCoeficiente().getCoeficiente_id());
		coeficienteContrato.setTabela(contrato.getCoeficiente().getTabela());
		coeficienteContrato.setPercentualMeta(contrato.getCoeficiente().getPercentualMeta());

		/*
		for(Coeficiente c : coeficientes){
			if(c.getValor().compareTo(coeficienteContrato.getValor()) == 0) {
				coeficienteContrato.setCoeficiente_id(c.getCoeficiente_id());
				coeficienteContrato.setTabela(c.getTabela());
				coeficienteContrato.setPercentualMeta(c.getPercentualMeta());
			}
		}
		*/

		coeficientes.add(coeficienteContrato);

		contrato = contratoDao.load(id);

		Banco banco = bancoDao.buscaBancoById(contrato.getBanco().getBanco_id());
		Collection<Produto> produtos = produtoDao.buscaProdutoBancoByEmpOrgBancoConvenio(empresa.getEmpresa_id(),organizacao.getOrganizacao_id(),banco.getBanco_id(),contrato.getConvenio().getConvenio_id());

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

		if(usuarioInfo.getPerfil().getChave().equals("Gestor")){

			etapas = etapaDao.buscaEtapasByEmpresaOrganizacaoWorkflow(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), contrato.getWorkflow().getWorkflow_id());

		} else {
			
			etapas = etapaDao.buscaEtapaByContratoPerfil(id, usuarioInfo.getPerfil().getPerfil_id());
			etapas.add(contrato.getEtapa());

		}

		periodos = periodoDao.buscaAllPeriodos();
		historico = historicoContratoDao.buscaHistoricoByContrato(id) ;

		tiposLogistica = tipoLogisticaDao.buscaAllTipoLogistica();
		logisticas = logisticaDao.buscaLogisticaByContrato(id);


		boleto = this.controleDao.buscaControleByContratoTipoControle(id, tipoControleDao.buscaTipoControleByEmpOrgNome(1l,1l,"Boleto").getTipoControle_id());
		averbacao = this.controleDao.buscaControleByContratoTipoControle(id, tipoControleDao.buscaTipoControleByEmpOrgNome(1l,1l,"Averbacao").getTipoControle_id());

		TipoProcedimento tipoProcedimento = this.tipoProcedimentoDao.buscaTipoProcedimentoByNome("Contrato");
		
		conferencias = this.conferenciaDao.buscaConferenciaByEmOrTipoProcedimentoContrato(contrato.getEmpresa().getEmpresa_id(), contrato.getOrganizacao().getOrganizacao_id(), 
				tipoProcedimento.getTipoProcedimento_id(), contrato.getContrato_id());

		Collection<Contrato> contratosLogistica = contratoDao.buscaContratoParaLogisticaByFormulario(formulario.getFormulario_id());

		if(boleto != null)
			historicoControleBoleto = this.historicoControleDao.buscaHistoricoByContratoControle(contrato.getContrato_id(), boleto.getControle_id());

		if(averbacao != null)
			historicoControleAverbacao = this.historicoControleDao.buscaHistoricoByContratoControle(contrato.getContrato_id(), averbacao.getControle_id());
		
		parceiroBeneficio = parceiroBeneficioDao.buscaParceiroBeneficioByNumeroBeneficio(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(),contrato.getNumeroBeneficio());
		
		parceiroNegocio = parceiroNegocioDao.load(parceiroBeneficio.getParceiroNegocio().getParceiroNegocio_id());
		parceiroInfoBanco = parceiroInfoBancoDao.buscaParceiroInfoBancoByParceiro(parceiroBeneficio.getParceiroNegocio().getParceiroNegocio_id());

		for(ParceiroLocalidade pl : parceiroLocalidadeDao.buscaParceiroLocalidades(parceiroNegocio.getParceiroNegocio_id())){

			if(pl.getTipoEndereco().getNome().equals("Assinatura") && pl.getIsActive()){
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

		TipoWorkflow tipoWf = tipoWorkflowDao.buscaTipoWorkflowPorEmpresaOrganizacaoNomeExato(1l, 1l, "Contrato Pendência");
		result.include("justificativas",this.etapaDao.buscaEtapasByEmpresaOrganizacaoTipoWorkflowDistinct(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), tipoWf.getTipoWorkflow_id()));
		
		result.include("periodos", periodos);
		result.include("tiposLogistica", tiposLogistica);
		result.include("logisticas",logisticas);
		result.include("contratosLogistica",contratosLogistica);
		result.include("historico",historico);
		result.include("boleto",boleto);
		result.include("averbacao",averbacao);
		result.include("conferencias",conferencias);
		result.include("historicoControleBoleto",historicoControleBoleto);
		result.include("historicoControleAverbacao",historicoControleAverbacao);
		result.include("organizacoes", this.organizacaoDao.buscaOrganizacoesByEmpresa(empresa.getEmpresa_id()));
		result.include("hisconsBeneficio", this.hisconBeneficioDao.buscaHisconsBeneficioByParceiroBeneficioToStatusContrato(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(),parceiroBeneficio.getParceiroBeneficio_id()));
		result.include("meiosPagamento",this.meioPagamentoDao.buscaAllMeioPagamento(1L, 1L));
		result.include("tiposSaque",this.tipoSaqueDao.buscaAllTipoSaque());

	}
	
	@Post
	@Path("/contrato/altera")
	public void altera(Contrato contrato) {

		List<String> log = new ArrayList<String>();

		this.contrato = this.contratoDao.load(contrato.getContrato_id());

		if(contrato.getCoeficiente().getCoeficiente_id() != null){

			Coeficiente coefAux = coeficienteDao.load(contrato.getCoeficiente().getCoeficiente_id());
			
			if(this.contrato.getCoeficiente().getCoeficiente_id() != contrato.getCoeficiente().getCoeficiente_id()) {

				log.add("Coeficiente alterado de : " + this.contrato.getCoeficiente().getValor() + " para: " + coefAux.getValor());
				this.contrato.setCoeficiente(coefAux);

			}

		}

		if(contrato.getBanco().getBanco_id() != null) {

			if(this.contrato.getBanco().getBanco_id() != contrato.getBanco().getBanco_id()) {

				this.contrato.setBanco(bancoDao.load(contrato.getBanco().getBanco_id()));
				log.add("Banco alterado para : " + this.contrato.getBanco().getNome());

			}

		}

		if(contrato.getProduto().getProduto_id() != null) {

			if(this.contrato.getProduto().getProduto_id() != contrato.getProduto().getProduto_id() ) {

				this.contrato.setProduto(produtoDao.load(contrato.getProduto().getProduto_id()));
				log.add("Produto alterado para : " + this.contrato.getProduto().getNome());

			}

		}

		if(contrato.getRecompraBanco().getBanco_id() == null){

			if(this.contrato.getRecompraBanco() != null){

				log.add(" Banco recompra excluído. ");
				this.contrato.setRecompraBanco(null);

			} else {

				this.contrato.setRecompraBanco(null);

			}

		} else {

			if(this.contrato.getRecompraBanco() == null || this.contrato.getRecompraBanco().getBanco_id() != contrato.getRecompraBanco().getBanco_id()) {

				this.contrato.setRecompraBanco(bancoDao.load(contrato.getRecompraBanco().getBanco_id()));
				log.add("Banco recompra alterado para : " + this.contrato.getRecompraBanco().getNome());

			}

		}

		if(this.contrato.getQtdParcelasAberto() != contrato.getQtdParcelasAberto()){
			log.add("Parcelas Aberto alterado: " + this.contrato.getQtdParcelasAberto() + " para : " + contrato.getQtdParcelasAberto());
			this.contrato.setQtdParcelasAberto(contrato.getQtdParcelasAberto() == null ? null : contrato.getQtdParcelasAberto());
		}

		if(this.contrato.getPrazo() != contrato.getPrazo()) {
			log.add("Prazo alterado de : " + this.contrato.getPrazo() + " para : " + contrato.getPrazo());
			this.contrato.setPrazo(contrato.getPrazo() == null ? null : contrato.getPrazo());
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

		if(this.contrato.getProduto().getNome().equals("MARGEM LIMPA") 
				|| this.contrato.getProduto().getNome().equals("MARGEM LIMPA PMSP")
				|| this.contrato.getProduto().getNome().equals("MARGEM LIMPA PMRP")
				|| this.contrato.getProduto().getNome().equals("MARGEM LIMPA SIAPE")
				|| this.contrato.getProduto().getNome().equals("MARGEM LIMPA GOVRJ")
				|| this.contrato.getProduto().getNome().equals("DEBITO")
				|| this.contrato.getProduto().getNome().equals("RECOMPRA INSS")  
				|| this.contrato.getProduto().getNome().equals("PORTABILIDADE/REFIN")
				|| this.contrato.getProduto().getNome().equals("RECOMPRA PMSP") 
				|| this.contrato.getProduto().getNome().equals("RECOMPRA PMRP") 
				|| this.contrato.getProduto().getNome().equals("RECOMPRA GOVRJ")
				|| this.contrato.getProduto().getNome().equals("RECOMPRA SIAPE")
				|| this.contrato.getProduto().getNome().equals("RECOMPRA RMC")
				|| this.contrato.getProduto().getNome().equals("AUMENTO"))
			this.contrato.setValorContratoLiquido(this.contrato.getValorContrato());

		if(this.contrato.getProduto().getNome().equals("REFINANCIAMENTO") 
				|| this.contrato.getProduto().getNome().equals("REFINANCIAMENTO PMSP")
				|| this.contrato.getProduto().getNome().equals("REFINANCIAMENTO PMRP")
				|| this.contrato.getProduto().getNome().equals("REFINANCIAMENTO GOVRJ")
				|| this.contrato.getProduto().getNome().equals("REFINANCIAMENTO SIAPE")
				|| this.contrato.getProduto().getNome().equals("RETENÇÃO") 
				|| this.contrato.getProduto().getNome().equals("RETENÇÃO PMSP")
				|| this.contrato.getProduto().getNome().equals("RETENÇÃO GOVRJ"))
			this.contrato.setValorContratoLiquido(this.contrato.getValorLiquido());

		if(this.contrato.getProduto().getNome().equals("CARTAO CREDITO"))
			this.contrato.setValorContratoLiquido(0.0);

		this.contrato.setWorkflow(this.workflowDao.buscaWorkflowByEmpresaOrganizacaoBancoProdutoConvenio(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), 
				contrato.getBanco().getBanco_id(), contrato.getProduto().getProduto_id() ,this.contrato.getConvenio().getConvenio_id()));

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
		Calendar calInicial =  new GregorianCalendar(9999, 0, 1);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		this.contrato = this.contratoDao.load(contrato.getContrato_id());

		contrato.setEtapa(this.etapaDao.buscaEtapaById(contrato.getEtapa().getEtapa_id()));

		if(!(this.contrato.getEtapa().getEtapa_id() == contrato.getEtapa().getEtapa_id())){
			log.add("Status alterado para : " + contrato.getEtapa().getNome());
			this.contrato.setEtapa(contrato.getEtapa() == null ? null : contrato.getEtapa());
		}

		if(contrato.getEtapaPendencia() != null){

			contrato.setEtapaPendencia(this.etapaDao.load(contrato.getEtapaPendencia().getEtapa_id()));

			if(this.contrato.getEtapaPendencia() != null){
				if(this.contrato.getEtapaPendencia().getEtapa_id() != contrato.getEtapaPendencia().getEtapa_id()){
					log.add("Justificativa alterada para : " + contrato.getEtapaPendencia().getNome() );
					this.contrato.setEtapaPendencia(contrato.getEtapaPendencia());
				}
					
			}

			if(this.contrato.getEtapaPendencia() == null){
				log.add("Justificativa alterada para : " + contrato.getEtapaPendencia().getNome() );
				this.contrato.setEtapaPendencia(contrato.getEtapaPendencia());
			}

		}

		this.contrato.setSupervisorStatusFinal(this.contrato.getEtapa().getNome().equals("Concluído") ? this.contrato.getUsuario().getSupervisorUsuario() : null);

		this.contrato.setDataDigitacao(this.contrato.getDataDigitacao() == null ? calInicial : this.contrato.getDataDigitacao());
		contrato.setDataDigitacao(contrato.getDataDigitacao() == null ? calInicial : contrato.getDataDigitacao());

		if(!(this.contrato.getDataDigitacao().compareTo(contrato.getDataDigitacao() == null ? calInicial : contrato.getDataDigitacao()) == 0)) {
			if (this.contrato.getDataDigitacao().compareTo(calInicial) == 0){

				log.add("Data de digitação alterado para : " + dateFormat.format(contrato.getDataDigitacao().getTime()));
				this.contrato.setDataDigitacao(contrato.getDataDigitacao());

			} else if (contrato.getDataDigitacao().compareTo(calInicial) != 0) {

				log.add("Data de digitação alterado de : " + dateFormat.format(this.contrato.getDataDigitacao().getTime()) + " para: " + dateFormat.format(contrato.getDataDigitacao().getTime()));
				this.contrato.setDataDigitacao(contrato.getDataDigitacao());

			} else if (contrato.getDataDigitacao().compareTo(calInicial) == 0) {
				log.add("Data de digitação alterado de : " + dateFormat.format(this.contrato.getDataDigitacao().getTime()) + " para: em branco.");
				this.contrato.setDataDigitacao(null);
			}
		}
		
		if(this.contrato.getDataDigitacao() != null) {
			if(this.contrato.getDataDigitacao().compareTo(calInicial) == 0)
				this.contrato.setDataDigitacao(null);
		}
		


		
		this.contrato.setDataAgendado(this.contrato.getDataAgendado() == null ? calInicial : this.contrato.getDataAgendado());
		contrato.setDataAgendado(contrato.getDataAgendado() == null ? calInicial : contrato.getDataAgendado());

		if(!(this.contrato.getDataAgendado().compareTo(contrato.getDataAgendado() == null ? calInicial : contrato.getDataAgendado()) == 0)) {
			if (this.contrato.getDataAgendado().compareTo(calInicial) == 0){

				log.add("Data de agendamento alterado para : " + dateFormat.format(contrato.getDataAgendado().getTime()));
				this.contrato.setDataAgendado(contrato.getDataAgendado());

			} else if (contrato.getDataAgendado().compareTo(calInicial) != 0) {

				log.add("Data de agendamento alterado de : " + dateFormat.format(this.contrato.getDataAgendado().getTime()) + " para: " + dateFormat.format(contrato.getDataAgendado().getTime()));
				this.contrato.setDataAgendado(contrato.getDataAgendado());

			} else if (contrato.getDataAgendado().compareTo(calInicial) == 0) {
				log.add("Data de agendamento alterado de : " + dateFormat.format(this.contrato.getDataAgendado().getTime()) + " para: em branco.");
				this.contrato.setDataAgendado(null);
			}
		}
		
		if(this.contrato.getDataAgendado() != null) {
			if(this.contrato.getDataAgendado().compareTo(calInicial) == 0)
				this.contrato.setDataAgendado(null);
		}

		
		this.contrato.setDataQuitacao(this.contrato.getDataQuitacao() == null ? calInicial : this.contrato.getDataQuitacao());
		contrato.setDataQuitacao(contrato.getDataQuitacao() == null ? calInicial : contrato.getDataQuitacao());

		if(!(this.contrato.getDataQuitacao().compareTo(contrato.getDataQuitacao() == null ? calInicial : contrato.getDataQuitacao()) == 0)) {
			if (this.contrato.getDataQuitacao().compareTo(calInicial) == 0){

				log.add("Data de quitação alterado para : " + dateFormat.format(contrato.getDataQuitacao().getTime()));
				this.contrato.setDataQuitacao(contrato.getDataQuitacao());

			} else if (contrato.getDataQuitacao().compareTo(calInicial) != 0) {

				log.add("Data de quitação alterado de : " + dateFormat.format(this.contrato.getDataQuitacao().getTime()) + " para: " + dateFormat.format(contrato.getDataQuitacao().getTime()));
				this.contrato.setDataQuitacao(contrato.getDataQuitacao());

			} else if (contrato.getDataQuitacao().compareTo(calInicial) == 0) {
				log.add("Data de quitação alterado de : " + dateFormat.format(this.contrato.getDataQuitacao().getTime()) + " para: em branco.");
				this.contrato.setDataQuitacao(null);
			}
		}

		if(this.contrato.getDataQuitacao() != null) {
			if(this.contrato.getDataQuitacao().compareTo(calInicial) == 0)
				this.contrato.setDataQuitacao(null);
		}
		
		this.contrato.setDataStatusFinal(this.contrato.getDataStatusFinal() == null ? calInicial : this.contrato.getDataStatusFinal());
		contrato.setDataStatusFinal(contrato.getDataStatusFinal() == null ? calInicial : contrato.getDataStatusFinal());

		if(!(this.contrato.getDataStatusFinal().compareTo(contrato.getDataStatusFinal()) == 0)) {
			if (this.contrato.getDataStatusFinal().compareTo(calInicial) == 0) {

				log.add("Data de Aprovação/Recusa alterado para : " + dateFormat.format(contrato.getDataStatusFinal().getTime()));
				this.contrato.setDataStatusFinal(contrato.getDataStatusFinal());

			} else if(contrato.getDataStatusFinal().compareTo(calInicial) != 0) {

				log.add("Data de Aprovação/Recusa alterado de : " + dateFormat.format(this.contrato.getDataStatusFinal().getTime()) + " para: " + dateFormat.format(contrato.getDataStatusFinal().getTime()));
				this.contrato.setDataStatusFinal(contrato.getDataStatusFinal());

			} else if(contrato.getDataStatusFinal().compareTo(calInicial) == 0) {

				log.add("Data de Aprovação/Recusa alterado de : " + dateFormat.format(this.contrato.getDataStatusFinal().getTime()) + " para: em branco.");
				this.contrato.setDataStatusFinal(null);

			}	
		}

		if(this.contrato.getDataStatusFinal() != null) {
			if(this.contrato.getDataStatusFinal().compareTo(calInicial) == 0)
				this.contrato.setDataStatusFinal(null);
		}
		
		this.contrato.setDataConcluido(this.contrato.getDataConcluido() == null ? calInicial : this.contrato.getDataConcluido());
		contrato.setDataConcluido(contrato.getDataConcluido() == null ? calInicial : contrato.getDataConcluido());
		
		if(!(this.contrato.getDataConcluido().compareTo(contrato.getDataConcluido()) == 0)) {
			if (this.contrato.getDataConcluido().compareTo(calInicial) == 0) {

				log.add("Data de Conclusão alterado para : " + dateFormat.format(contrato.getDataConcluido().getTime()));
				this.contrato.setDataConcluido(contrato.getDataConcluido());

			} else if(contrato.getDataConcluido().compareTo(calInicial) != 0) {

				log.add("Data de Conclusão alterado de : " + dateFormat.format(this.contrato.getDataConcluido().getTime()) + " para: " + dateFormat.format(contrato.getDataConcluido().getTime()));
				this.contrato.setDataConcluido(contrato.getDataConcluido());

			} else if(contrato.getDataConcluido().compareTo(calInicial) == 0) {

				log.add("Data de Conclusão alterado de : " + dateFormat.format(this.contrato.getDataConcluido().getTime()) + " para: em branco.");
				this.contrato.setDataConcluido(null);

			}	
		}
		
		if(this.contrato.getDataConcluido() != null) {
			if(this.contrato.getDataConcluido().compareTo(calInicial) == 0)
				this.contrato.setDataConcluido(null);
		}
		
		if(!this.contrato.getPropostaBanco().equals(contrato.getPropostaBanco())) {
			log.add("Proposta Banco alterada para : " + contrato.getPropostaBanco() );
			this.contrato.setPropostaBanco(contrato.getPropostaBanco());
		}

		if(!this.contrato.getContratoBanco().equals(contrato.getContratoBanco())) {
			log.add("Contrato Banco alterada para : " + contrato.getContratoBanco() );
			this.contrato.setContratoBanco(contrato.getContratoBanco());
		}

		if(contrato.getOrganizacaoDigitacao() != null){

			contrato.setOrganizacaoDigitacao(this.organizacaoDao.load(contrato.getOrganizacaoDigitacao().getOrganizacao_id()));

			if(this.contrato.getOrganizacaoDigitacao() != null){
				if(this.contrato.getOrganizacaoDigitacao().getOrganizacao_id() != contrato.getOrganizacaoDigitacao().getOrganizacao_id()) {

					log.add("Organização Digitado alterado de : " + this.contrato.getOrganizacaoDigitacao().getNome() + " para : " + contrato.getOrganizacaoDigitacao().getNome() );
					this.contrato.setOrganizacaoDigitacao(contrato.getOrganizacaoDigitacao());

				}
					
			}

			if(this.contrato.getOrganizacaoDigitacao() == null){
				log.add("Organização Digitado alterado para : " + contrato.getOrganizacaoDigitacao().getNome() );
				this.contrato.setOrganizacaoDigitacao(contrato.getOrganizacaoDigitacao());
			}

		}

		if(!(this.contrato.getValorQuitacao() == null && contrato.getValorQuitacao() == null)){
			if(!(this.contrato.getValorQuitacao().compareTo(contrato.getValorQuitacao() == null ? null : contrato.getValorQuitacao()) == 0)){
				log.add("Valor Quitação alterado de : " + this.contrato.getValorQuitacao() + " para : " + contrato.getValorQuitacao());
				this.contrato.setValorQuitacao(contrato.getValorQuitacao() == null ? null : contrato.getValorQuitacao());
			}
		}
		
		if(contrato.getMeioPagamento() != null){

			contrato.setMeioPagamento(this.meioPagamentoDao.load(contrato.getMeioPagamento().getMeioPagamento_id()));

			if(this.contrato.getMeioPagamento() != null){
				if(this.contrato.getMeioPagamento().getMeioPagamento_id() != contrato.getMeioPagamento().getMeioPagamento_id())
					log.add("Meio Pagamento alterado de : " + this.contrato.getMeioPagamento().getNome() + " para : " + contrato.getMeioPagamento().getNome() );
					this.contrato.setMeioPagamento(contrato.getMeioPagamento());
			}

			if(this.contrato.getMeioPagamento() == null){
				log.add("Meio Pagamento alterado para : " + contrato.getMeioPagamento().getNome() );
				this.contrato.setMeioPagamento(contrato.getMeioPagamento());
			}

		}
		
		
		if(contrato.getTipoSaque() != null){

			contrato.setTipoSaque(this.tipoSaqueDao.load(contrato.getTipoSaque().getTipoSaque_id()));

			if(this.contrato.getTipoSaque() != null){
				if(this.contrato.getTipoSaque().getTipoSaque_id() != contrato.getTipoSaque().getTipoSaque_id())
					log.add("Tipo Saque alterado de : " + this.contrato.getTipoSaque().getNome() + " para : " + contrato.getTipoSaque().getNome() );
					this.contrato.setTipoSaque(contrato.getTipoSaque());
			}

			if(this.contrato.getTipoSaque() == null){
				log.add("Tipo Saque alterado para : " + contrato.getTipoSaque().getNome() );
				this.contrato.setTipoSaque(contrato.getTipoSaque());
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
	@Path("/contrato/produtos")
	public void produtos(Long empresa_id, Long organizacao_id, Long banco_id, Long convenio_id) {

		result.include("produtos",this.produtoDao.buscaProdutoBancoByEmpOrgBancoConvenio(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), banco_id, convenio_id));

	}
	
	@Post
	@Path("/contrato/repasse")
	public void repasse(Long id) {
		
		contrato = this.contratoDao.buscaContratoById(id);

		result.include("supervisores", this.usuarioDao.buscaUsuariosByPerfilDepartamento(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Supervisor", "Comercial"));
		result.include("consultores", this.usuarioDao.buscaUsuariosBySupervisor(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), contrato.getUsuario().getSupervisorUsuario().getUsuario_id()));
		result.include("contrato",contrato);

	}
	
	@Post
	@Path("/contrato/repasse/salva")
	public void repasse(Contrato contrato) {

		this.contrato = this.contratoDao.load(contrato.getContrato_id());
		List<String> log = new ArrayList<String>();
		Usuario created = this.usuarioDao.load(this.contrato.getCreatedBy().getUsuario_id());
		Usuario usuarioAtual = this.usuarioDao.load(this.contrato.getUsuario().getUsuario_id());
		Usuario usuarioRepasse = this.usuarioDao.load(contrato.getUsuario().getUsuario_id());

		if(this.contrato.getUsuario().getUsuario_id() != contrato.getUsuario().getUsuario_id()) {

			this.contrato.setUsuario(contrato.getUsuario());
			this.contrato.setIsRepasse(true);
			this.contrato.setPercentualRepasse(contrato.getPercentualRepasse());

			log.add("Usuário alterado de : " + usuarioAtual.getApelido() + " para : " + usuarioRepasse.getApelido() + " ( Contrato criado por :  " + created.getApelido() + ")");
			log.add("Status atual : " + this.contrato.getEtapa().getNome() + " - Regra repasse utilizada : " + contrato.getPercentualRepasse()*100 + " %");

		}
		
		this.contrato.setOperacao(this.operacaoDao.buscaOperacaoByEmpOrgUsuario(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(),contrato.getUsuario().getUsuario_id()));

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

		this.result.redirectTo(this).status(contrato.getContrato_id());

	}
	
	@Post
	@Path("/contrato/cliente/historico")
	public void historico(String doc) {

		ParceiroNegocio parceiroNegocio = this.parceiroNegocioDao.buscaParceiroNegocioByDocumento(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), doc);

		Collection<HisconBeneficio> hisconsBeneficio = new ArrayList<HisconBeneficio>();
		Collection<Contrato> contratos = new ArrayList<Contrato>();

		Collection<ParceiroBeneficio> parceiroBeneficios = this.parceiroBeneficioDao.buscaParceiroBeneficioByParceiroNegocio(parceiroNegocio.getParceiroNegocio_id());

		for(ParceiroBeneficio pb : parceiroBeneficios){

			hisconsBeneficio.addAll(this.hisconBeneficioDao.buscaHisconsBeneficioByParceiroBeneficio(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), pb.getParceiroBeneficio_id()));
			contratos.addAll(this.contratoDao.buscaContratoByParceiroNegocio(parceiroNegocio.getParceiroNegocio_id()));

		}

		result.include("contratos",contratos);
		result.include("hisconsBeneficio",hisconsBeneficio);

	}

	@Post
	@Path("/contrato/inclui/historico")
	public void incluihistorico(HistoricoContrato historicoContrato) {

        historicoContrato.setEmpresa(empresa);
        historicoContrato.setOrganizacao(organizacao);
        historicoContrato.setPerfil(usuarioInfo.getPerfil());
        historicoContrato.setCreatedBy(usuario);
        historicoContrato.setIsActive(true);

		historicoContrato.setCreated(GregorianCalendar.getInstance());

		historicoContratoDao.beginTransaction();
		historicoContratoDao.adiciona(historicoContrato);
		historicoContratoDao.commit();

		result.redirectTo(ContratoController.class).status(historicoContrato.getContrato().getContrato_id());

	}
	
	@Post
 	@Path("/contrato/consultores")
	public void consultores(Long supervisor_id) {

		consultores = this.usuarioDao.buscaUsuariosBySupervisor(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), supervisor_id);
		result.include("consultores",consultores);

	}

	@Post
 	@Path("/contrato/coeficientes")
	public void coeficientes(Long banco_id,Long produto_id) {

		coeficientes = produto_id == null ? null : this.coeficienteDao.buscaCoeficientesByBancoProduto(banco_id,produto_id);
		result.include("coeficientes",coeficientes);

	}

	@Post
 	@Path("/contrato/prazo")
	public void prazo(Long banco_id,Long produto_id,Long tabela_id) {

		contrato.setPrazo(bancoProdutoTabelaDao.buscaPrazoByEmpOrgBancoProdutoTabela(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(),banco_id,produto_id, tabela_id));
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
	
	@Get
	@Path("/contrato/cliente/detalhamento/{beneficio}")
	public void detalhamento(String beneficio) {

		result.include("detalhamento",this.pnDao.buscaDetalhamento(beneficio));

	}
	
	
	@Post
	@Path("/contrato/observacao/salva")
	public void salva(Contrato contrato) {

		List<String> log = new ArrayList<String>();
		this.contrato = this.contratoDao.load(contrato.getContrato_id());

		if(!contrato.getObservacao().equals("")){

			if(this.contrato.getObservacao() != null)
				log.add(" Observação do Contrato alterada de :" + this.contrato.getObservacao());

			this.contrato.setObservacao(contrato.getObservacao().trim());

			this.contratoDao.beginTransaction();
			this.contratoDao.atualiza(this.contrato);
			this.contratoDao.commit();

			log.add(" Observação do Contrato alterada para :" + this.contrato.getObservacao());

			for(String lo : log){

				HistoricoContrato historico = new HistoricoContrato();
				historico.setEmpresa(empresa);
				historico.setOrganizacao(organizacao);
				historico.setIsActive(true);
				historico.setCreatedBy(usuario);
				historico.setCreated(GregorianCalendar.getInstance());
				historico.setObservacao(lo);
				historico.setContrato(contrato);

				this.historicoContratoDao.beginTransaction();
				this.historicoContratoDao.adiciona(historico);
				this.historicoContratoDao.commit();

			}

		}

		result.redirectTo(ContratoController.class).status(contrato.getContrato_id());

	}
	
	
}