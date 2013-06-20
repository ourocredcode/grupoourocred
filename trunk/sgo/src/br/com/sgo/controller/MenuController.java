package br.com.sgo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.BancoDao;
import br.com.sgo.dao.CoeficienteDao;
import br.com.sgo.dao.ContratoDao;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.EtapaDao;
import br.com.sgo.dao.MenuDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.PerfilDao;
import br.com.sgo.dao.ProdutoDao;
import br.com.sgo.dao.TipoControleDao;
import br.com.sgo.dao.TipoWorkflowDao;
import br.com.sgo.dao.UsuarioDao;
import br.com.sgo.dao.WorkflowDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Coeficiente;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Etapa;
import br.com.sgo.modelo.Menu;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.ParceiroNegocio;
import br.com.sgo.modelo.Produto;
import br.com.sgo.modelo.TipoControle;
import br.com.sgo.modelo.TipoWorkflow;
import br.com.sgo.modelo.Usuario;

@Resource
public class MenuController {

	private final Result result;
	private final Validator validator;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final MenuDao menuDao;
	private final ContratoDao contratoDao;
	private final UsuarioDao usuarioDao;
	private final PerfilDao perfilDao;
	private final EtapaDao etapaDao;
	private final WorkflowDao workflowDao;
	private final TipoWorkflowDao tipoWorkflowDao;
	private final ProdutoDao produtoDao;
	private final BancoDao bancoDao;
	private final TipoControleDao tipoControleDao;
	private final CoeficienteDao coeficienteDao;
	private final UsuarioInfo usuarioInfo;
	
	private Set<Contrato> contratos = new LinkedHashSet<Contrato>();
	private Collection<Etapa> etapas = new ArrayList<Etapa>();
	private Collection<Produto> produtos = new ArrayList<Produto>();
	private Collection<Usuario> consultores = new ArrayList<Usuario>();
	private Collection<Usuario> supervisores = new ArrayList<Usuario>();
	private Collection<Usuario> consultoresAux = new ArrayList<Usuario>();
	private Collection<Coeficiente> coeficientes = new ArrayList<Coeficiente>();

	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	public MenuController(Result result,Validator validator, EmpresaDao empresaDao, OrganizacaoDao organizacaoDao,MenuDao menuDao,UsuarioInfo usuarioInfo,CoeficienteDao coeficienteDao,
			UsuarioDao usuarioDao,ContratoDao contratoDao,PerfilDao perfilDao,EtapaDao etapaDao,WorkflowDao workflowDao,TipoWorkflowDao tipoWorkflowDao, ProdutoDao produtoDao,
			TipoControleDao tipoControleDao,BancoDao bancoDao,Empresa empresa,Organizacao organizacao,Usuario usuario){

		this.empresaDao = empresaDao;
		this.usuarioDao = usuarioDao;
		this.perfilDao = perfilDao;
		this.usuarioInfo = usuarioInfo;
		this.organizacaoDao = organizacaoDao;
		this.menuDao = menuDao;
		this.result = result;
		this.validator = validator;
		this.contratoDao = contratoDao;
		this.etapaDao = etapaDao;
		this.workflowDao = workflowDao;
		this.tipoWorkflowDao = tipoWorkflowDao;
		this.produtoDao = produtoDao;
		this.coeficienteDao = coeficienteDao;
		this.bancoDao = bancoDao;
		this.tipoControleDao = tipoControleDao;
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();
		this.usuario = usuarioInfo.getUsuario();

	}
	
	@Get
	@Path("/menu/inicio/{perfil}") 
	public void inicio(String perfil) {

		usuarioDao.refresh(usuarioInfo.getUsuario());
		perfilDao.refresh(usuarioInfo.getPerfil());

		Calendar c1 = new GregorianCalendar();
		Calendar c2 = new GregorianCalendar();
		c1.set(GregorianCalendar.DAY_OF_MONTH, c1.getActualMinimum(GregorianCalendar.DAY_OF_MONTH));
		c2.set(GregorianCalendar.DAY_OF_MONTH, c2.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));

		Long empresa_id = empresa.getEmpresa_id();
		Long organizacao_id = organizacao.getOrganizacao_id();
		String cliente = "";
		String documento = "";
		Collection<String> status = new ArrayList<String>();
		Collection<String> produtos = new ArrayList<String>();
		Collection<String> bancos = new ArrayList<String>();
		Collection<String> bancosComprados = new ArrayList<String>();
		Collection<Usuario> consultores = new ArrayList<Usuario>();
		Calendar calAprovadoInicio = null;
		Calendar calAprovadoFim = null;

		if(perfil.equals("Supervisor") || perfil.equals("Consultor")){
			contratos.addAll(this.contratoDao.buscaContratoByUsuario(usuarioInfo.getUsuario().getUsuario_id()));
			result.include("mapEtapas", this.contratoDao.buscaContratosToCountEtapas(empresa_id, organizacao_id, usuarioInfo.getUsuario().getUsuario_id()));
		}

		if(perfil.equals("Administrativo")){
			contratos.addAll(this.contratoDao.buscaContratoByEmpresaOrganizacao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
			result.include("mapEtapas", this.contratoDao.buscaContratosToCountEtapas(empresa_id, organizacao_id, null));
		}

		if(perfil.equals("Gestor")){
			contratos.addAll(this.contratoDao.buscaContratoByFiltros(empresa_id,organizacao_id,c1,c2,calAprovadoInicio,calAprovadoFim, cliente, documento, status, produtos, bancos, bancosComprados, consultores));
			result.include("mapEtapas", this.contratoDao.buscaContratosToCountEtapas(empresa_id, organizacao_id, null));
		}

		result.include("coeficientes", this.coeficienteDao.buscaCoeficientesByEmpOrg(empresa_id, organizacao_id));

		contadorSeparado();

	}
	
	@Get
	@Path("/menu/contratos/{tipo}") 
	public void contratos(String tipo) {

		usuarioDao.refresh(usuarioInfo.getUsuario());
		perfilDao.refresh(usuarioInfo.getPerfil());
		
		Calendar c1 = new GregorianCalendar();
		Calendar c2 = new GregorianCalendar();
		c1.set(GregorianCalendar.DAY_OF_MONTH, c1.getActualMinimum(GregorianCalendar.DAY_OF_MONTH));
		c2.set(GregorianCalendar.DAY_OF_MONTH, c2.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));

		Long empresa_id = empresa.getEmpresa_id();
		Long organizacao_id = organizacao.getOrganizacao_id();
		String cliente = "";
		String documento = "";
		Collection<String> status = new ArrayList<String>();
		Collection<String> produtos = new ArrayList<String>();
		Collection<String> bancos = new ArrayList<String>();
		Collection<String> bancosComprados = new ArrayList<String>();
		Collection<Usuario> consultores = new ArrayList<Usuario>();
		Calendar calAprovadoInicio = null;
		Calendar calAprovadoFim = null;

		if(tipo.equals("Supervisor") || tipo.equals("Consultor")){
			contratos.addAll(this.contratoDao.buscaContratoByUsuario(usuarioInfo.getUsuario().getUsuario_id()));
			
			
			result.include("function","buscaContratos();");
			result.include("buscaBoleto","none");
			result.include("buscaAprovado","block");
		}
	
		if(tipo.equals("Administrativo")) {
			contratos.addAll(this.contratoDao.buscaContratoByEmpresaOrganizacao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
			
			
			result.include("function","buscaContratos();");
			result.include("buscaBoleto","none");
			result.include("buscaAprovado","block");
			
		}
			
		
		if(tipo.equals("Gestor")){
			contratos.addAll(this.contratoDao.buscaContratoByFiltros(empresa_id,organizacao_id,c1,c2,calAprovadoInicio,calAprovadoFim, cliente, documento, status, produtos, bancos, bancosComprados, consultores));
			
			result.include("function","buscaContratos();");
			result.include("buscaBoleto","none");
			result.include("buscaAprovado","block");
		}

		if(tipo.equals("aprovados")){
			status.add("Aprovado");
			contratos.addAll(this.contratoDao.buscaContratoByFiltros(empresa_id,organizacao_id,c1,c2,calAprovadoInicio,calAprovadoFim, cliente, documento, status, produtos, bancos, bancosComprados, consultores));
			
			result.include("function","buscaContratos();");
			result.include("buscaBoleto","none");
			result.include("buscaAprovado","block");
		}

		if(tipo.equals("boletos")){
			result.include("tipobusca","boleto");
			result.include("function","buscaDatasControle();");
			result.include("buscaBoleto","block");
			result.include("buscaAprovado","none");
		} 
		
		if(tipo.equals("averbacao")) {
			result.include("tipobusca","averbacao");
			result.include("function","buscaDatasControle();");
			result.include("buscaBoleto","block");
			result.include("buscaAprovado","none");
		}

		TipoWorkflow tw = this.tipoWorkflowDao.buscaTipoWorkflowPorEmpresaOrganizacaoNome(1l, 1l, "Contrato");

		result.include("bancos",this.bancoDao.buscaBancosToBancoProdutoByEmpOrg(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
		result.include("bancosComprado",this.bancoDao.buscaBancoCompradoByEmpOrg(1l, 1l));
		result.include("etapas",this.etapaDao.buscaEtapasByEmpresaOrganizacaoTipoWorkflow(empresa.getEmpresa_id(),organizacao.getOrganizacao_id(),tw.getTipoWorkflow_id()));		 
		result.include("produtos",this.produtoDao.buscaProdutosByEmpOrg(empresa.getEmpresa_id(),organizacao.getOrganizacao_id()));
		result.include("supervisores", this.usuarioDao.buscaUsuariosByPerfil(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Supervisor"));

		contador();

	}
	
	@Post
	@Path("/menu/busca") 
	public void busca(String informacaoSaque,String tipoAprovado,String empresa,String tipoPagamento,String tipoRecusado,String justificativa, Collection<String> status,
			String cliente, String documento,String data, String dataFim,String dataAprovadoInicio, String dataAprovadoFim,String dataConcluidoInicio, String dataConcluidoFim,
			String dataRecusadoInicio, String dataRecusadoFim,Collection<String> bancos, Collection<String> produtos, Collection<String> bancosComprados, String motivoPendencia,
			Long consultor) {

		Calendar calInicio = new GregorianCalendar();
		Calendar calFim = new GregorianCalendar();
		Calendar calAprovadoInicio = new GregorianCalendar();
		Calendar calAprovadoFim = new GregorianCalendar();
		Calendar calConcluidoInicio = new GregorianCalendar();
		Calendar calConcluidoFim = new GregorianCalendar();
		Calendar calRecusadoInicio = new GregorianCalendar();
		Calendar calRecusadoFim = new GregorianCalendar();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/y");
		
		Collection<String> statusFinal = new ArrayList<String>();
		Collection<String> tiposPagamento = new ArrayList<String>();
		Collection<String> empresas = new ArrayList<String>();
		Collection<String> informacoesSaque = new ArrayList<String>();
		Collection<String> justificativas = new ArrayList<String>();
		
		try {

			if(dataFim.equals(""))
				dataFim = data;

			if(data.equals("")) {

				calInicio = null;
				calFim = null;

			} else {

				calInicio.setTime(sdf.parse(data));
				calFim.setTime(sdf.parse(dataFim));

				calInicio.set(Calendar.HOUR_OF_DAY,calInicio.getActualMinimum(Calendar.HOUR_OF_DAY));
				calFim.set(Calendar.HOUR_OF_DAY,calInicio.getActualMaximum(Calendar.HOUR_OF_DAY));

			}

			if(dataAprovadoFim.equals(""))
				dataAprovadoFim = dataAprovadoInicio;

			if(dataAprovadoInicio.equals("")) {
				calAprovadoInicio = null;
				calAprovadoFim = null;
			} else {
				calAprovadoInicio.setTime(sdf.parse(dataAprovadoInicio));
				calAprovadoFim.setTime(sdf.parse(dataAprovadoFim));
			}

			if(dataConcluidoFim.equals(""))
				dataConcluidoFim = dataConcluidoInicio;

			if(dataConcluidoInicio.equals("")) {
				calConcluidoInicio = null;
				calConcluidoFim = null;
			} else {
				calConcluidoInicio.setTime(sdf.parse(dataConcluidoInicio));
				calConcluidoFim.setTime(sdf.parse(dataConcluidoFim));
			}

			if(dataRecusadoFim.equals(""))
				dataRecusadoFim = dataRecusadoInicio;

			if(dataRecusadoInicio.equals("")) {
				calRecusadoInicio = null;
				calRecusadoFim = null;
			} else {
				calRecusadoInicio.setTime(sdf.parse(dataRecusadoInicio));
				calRecusadoFim.setTime(sdf.parse(dataRecusadoFim));
			}

			if(calAprovadoInicio != null) {
				if(tipoAprovado.equals("Todos")){
					statusFinal.add("Aprovado");
					statusFinal.add("Concluído");
				} else {
					statusFinal.add(tipoAprovado);
				}
			}

			if(calConcluidoInicio != null) {
				statusFinal.add("Concluído");
			}

			if(calRecusadoInicio != null) {
				statusFinal.add("Recusado");
			}
			
			if(tipoPagamento.equals("Todos")){
				tiposPagamento.add("");
			} else {
				tiposPagamento.add(tipoPagamento);
			}

			if(informacaoSaque.equals("Todos")){
				informacoesSaque.add("");
			} else {
				informacoesSaque.add(informacaoSaque);
			}
			
			if(empresa.equals("Todos")){
				empresas.add("");
			} else {
				empresas.add(empresa);
			}

			if(calRecusadoInicio != null) {
				if(!justificativa.equals("Todos")){
					justificativas.add(justificativa);
				}
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(usuarioInfo.getPerfil().getChave().equals("Consultor") || usuarioInfo.getPerfil().getChave().equals("Supervisor")){

			Usuario u = new Usuario();

			if(consultor != null) {
				u = usuarioInfo.getUsuario();
			} else {
				u = this.usuarioDao.load(usuario.getUsuario_id());
			}

			consultoresAux.add(u);

		} else {

			if(consultor != null){

				consultoresAux.add(this.usuarioDao.load(consultor));

				//TODO : VERIFICA PERFIL SUPERVISOR INATIVO
				//consultoresAux.addAll(this.consultorDao.buscaLoginsSupervisoresInativos());

			}

		}

		contratos.addAll(this.contratoDao.buscaContratoByFiltros(this.empresa.getEmpresa_id(), this.organizacao.getOrganizacao_id(), calInicio, calFim, 
				calAprovadoInicio, calAprovadoFim ,cliente, documento, status,
				produtos, bancos, bancosComprados,consultoresAux));

		result.include("contratos",contratos);		

	}

	@Post
	@Path("/menu/busca/controle")
	public void busca(String tipoBusca,String previsaoInicio,String previsaoFim, String chegadaInicio,String chegadaFim,String vencimentoInicio,String vencimentoFim,
							String proximaAtuacaoInicio,String proximaAtuacaoFim , String procedimento ,Collection<String> bancos, Collection<String> produtos, 
							Collection<String> bancosComprados,Collection<String> status,Long consultor,String cliente, String documento,String empresa) {

		Calendar calPrevisaoInicio = new GregorianCalendar();
		Calendar calPrevisaoFim = new GregorianCalendar();
		Calendar calChegadaInicio = new GregorianCalendar();
		Calendar calChegadaFim = new GregorianCalendar();
		Calendar calVencimentoInicio = new GregorianCalendar();
		Calendar calVencimentoFim = new GregorianCalendar();
		Calendar calProximaAtuacaoInicio = new GregorianCalendar();
		Calendar calProximaAtuacaoFim = new GregorianCalendar();
		Collection<String> empresas = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/y");

		Boolean isDataNUll = true;

		try {

		if(previsaoFim.equals(""))
			previsaoFim = previsaoInicio;

		if(previsaoInicio.equals("")) {
			calPrevisaoInicio = null;
			calPrevisaoFim = null;
		} else {
			calPrevisaoInicio.setTime(sdf.parse(previsaoInicio));
			calPrevisaoFim.setTime(sdf.parse(previsaoFim));

			isDataNUll = false;
			
			if(tipoBusca.equals("boleto"))
				result.include("buscaDtBoletoPrevisao",true);
			if(tipoBusca.equals("averbacao"))
				result.include("buscaDtAverbacaoPrevisao",true);
		}
		
		if(chegadaFim.equals(""))
			chegadaFim = chegadaInicio;

		if(chegadaInicio.equals("")) {
			calChegadaInicio = null;
			calChegadaFim = null;
		} else {
			calChegadaInicio.setTime(sdf.parse(chegadaInicio));
			calChegadaFim.setTime(sdf.parse(chegadaFim));
			
			isDataNUll = false;

			result.include("buscaDtBoletoChegada",true);

		}
		
		if(vencimentoFim.equals(""))
			vencimentoFim = vencimentoInicio;

		if(vencimentoInicio.equals("")) {
			calVencimentoInicio = null;
			calVencimentoFim = null;
		} else {
			calVencimentoInicio.setTime(sdf.parse(vencimentoInicio));
			calVencimentoFim.setTime(sdf.parse(vencimentoFim));
			
			isDataNUll = false;

			result.include("buscaDtBoletoVencimento",true);
				
		}
		
		if(proximaAtuacaoFim.equals(""))
			proximaAtuacaoFim = proximaAtuacaoInicio;

		if(proximaAtuacaoInicio.equals("")) {
			calProximaAtuacaoInicio = null;
			calProximaAtuacaoFim = null;
		} else {
			calProximaAtuacaoInicio.setTime(sdf.parse(proximaAtuacaoInicio));
			calProximaAtuacaoFim.setTime(sdf.parse(proximaAtuacaoFim));
			
			isDataNUll = false;
			
			if(tipoBusca.equals("boleto"))
				result.include("buscaDtBoletoProximaAtuacao",true);
			if(tipoBusca.equals("averbacao"))			
				result.include("buscaDtAverbacaoProximaAtuacao",true);
		}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		if(usuarioInfo.getPerfil().getChave().equals("Consultor") || usuarioInfo.getPerfil().getChave().equals("Supervisor")){

			Usuario u = new Usuario();

			if(consultor != null) {
				u = usuarioInfo.getUsuario();
			} else {
				u = this.usuarioDao.load(usuario.getUsuario_id());
			}

			consultoresAux.add(u);

		} else {

			if(consultor != null){

				consultoresAux.add(this.usuarioDao.load(consultor));

				//TODO : VERIFICA PERFIL SUPERVISOR INATIVO
				//consultoresAux.addAll(this.consultorDao.buscaLoginsSupervisoresInativos());

			}

		}

		if(isDataNUll){
			if(tipoBusca.equals("boleto"))
				result.include("buscaDtBoletoAtua",true);
			
			if(tipoBusca.equals("averbacao"))
				result.include("buscaDtAverbacaoAtua",true);
			
		}
		
		if(empresa.equals("Todos")){
			empresas.add("");
		} else {
			empresas.add(empresa);
		}
		
		TipoControle tipoControle = this.tipoControleDao.buscaTipoControleByNome(tipoBusca);

		contratos.clear();

		contratos.addAll(this.contratoDao.buscaDatasControle(this.empresa.getEmpresa_id(),this.organizacao.getOrganizacao_id(),tipoControle,calPrevisaoInicio, 
				calPrevisaoFim,calChegadaInicio,calChegadaFim,calVencimentoInicio,calVencimentoFim,
				calProximaAtuacaoInicio,calProximaAtuacaoFim,procedimento,bancos,produtos,bancosComprados,status,consultoresAux,
				cliente,documento,empresas));

		contador();
	}

	@Get
	@Path("/menu/cadastro")
	public void cadastro() {

	}

	@Post
	@Path("/menu/salva")
	public void salva(Menu menu){

		validator.validate(menu);
		validator.onErrorUsePageOf(this).cadastro();

		String mensagem = "";

			try {

				menu.setEmpresa(this.empresaDao.load(menu.getEmpresa().getEmpresa_id()));		
				menu.setOrganizacao(this.organizacaoDao.load(menu.getOrganizacao().getOrganizacao_id()));
				menu.setIsActive(menu.getIsActive() == null ? false : true);
				
				this.menuDao.beginTransaction();
				this.menuDao.adiciona(menu);
				this.menuDao.commit();

				mensagem = "Menu " + menu.getNome() + " adicionada com sucesso";			
				
			} catch(Exception e) {
				
				this.menuDao.rollback();
	
				if (e.getCause().toString().indexOf("IX_JANELA_EMP_ORG_NOME") != -1){
					mensagem = "Erro: Menu " + menu.getNome() + " já existente.";
				} else {
					mensagem = "Erro ao adicionar Menu:";
				}
	
			}

		this.menuDao.clear();
		this.menuDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

	@Get 
	@Path("/menu/busca.json")
	public void menus(Long empresa_id, Long organizacao_id, String nome){
		result.use(Results.json()).withoutRoot().from(menuDao.buscaMenus(empresa_id, organizacao_id, nome)).serialize();
	}
	
	@Post
 	@Path("/menu/consultores")
	public void consultores(Long supervisor_id) {

		consultores = this.usuarioDao.buscaUsuariosBySupervisor(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), supervisor_id);

		result.include("consultores",consultores);

	}

	private void contador() {

		Double totalValorContratos = 0.0;
		Double totalValorMeta = 0.0;
		Double totalValorDivida = 0.0;
		Double totalValorSeguro = 0.0;
		Double totalValorLiquido = 0.0;
		Integer countContratos = 0;
		Set<ParceiroNegocio> countClientes = new HashSet<ParceiroNegocio>();

		for(Contrato cs : contratos){
			countClientes.add(cs.getFormulario().getParceiroNegocio());
			totalValorContratos += cs.getValorContrato();
			totalValorMeta += cs.getValorMeta();
			totalValorDivida += cs.getValorDivida();
			totalValorSeguro += cs.getValorSeguro();
			totalValorLiquido += cs.getValorLiquido();
			countContratos += 1;
		}

		result.include("contratos",contratos);
		result.include("totalValorContratos",totalValorContratos);

		result.include("totalValorDivida",totalValorDivida);
		result.include("totalValorSeguro",totalValorSeguro);
		result.include("totalValorLiquido",totalValorLiquido);
		result.include("totalValorMeta",totalValorMeta);
		result.include("countContratos",countContratos);
		result.include("countClientes",countClientes.size());

	}
	
	private void contadorSeparado() {

		Double totalValorContratos = 0.0;
		Double totalValorMeta = 0.0;
		Double totalValorDivida = 0.0;
		Double totalValorSeguro = 0.0;
		Double totalValorLiquido = 0.0;
		Integer countContratos = 0;
		Set<ParceiroNegocio> countClientes = new HashSet<ParceiroNegocio>();
		Etapa aprovado = this.etapaDao.buscaEtapaByEmpresaOrganizacaoNomeExato(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Aprovado");

		for(Contrato cs : contratos){

			countClientes.add(cs.getFormulario().getParceiroNegocio());
			totalValorContratos += cs.getValorContrato();
			
			totalValorDivida += cs.getValorDivida();
			totalValorSeguro += cs.getValorSeguro();
			totalValorLiquido += cs.getValorLiquido();

			if(cs.getEtapa().getEtapa_id().compareTo(aprovado.getEtapa_id()) == 0){
				totalValorMeta += cs.getValorMeta();
			}
				

			countContratos += 1;

		}

		result.include("contratos",contratos);
		result.include("totalValorContratos",totalValorContratos);

		result.include("totalValorDivida",totalValorDivida);
		result.include("totalValorSeguro",totalValorSeguro);
		result.include("totalValorLiquido",totalValorLiquido);
		result.include("totalValorMeta",totalValorMeta);
		result.include("countContratos",countContratos);
		result.include("countClientes",countClientes.size());

	}

}
