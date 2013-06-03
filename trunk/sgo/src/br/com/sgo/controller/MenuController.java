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
import br.com.sgo.dao.ContratoDao;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.MenuDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.PerfilDao;
import br.com.sgo.dao.ProdutoDao;
import br.com.sgo.dao.UsuarioDao;
import br.com.sgo.dao.WorkflowDao;
import br.com.sgo.dao.EtapaDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Menu;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.ParceiroNegocio;
import br.com.sgo.modelo.Produto;
import br.com.sgo.modelo.Usuario;
import br.com.sgo.modelo.Etapa;

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
	private final ProdutoDao produtoDao;
	private final BancoDao bancoDao;
	private final UsuarioInfo usuarioInfo;
	private Set<Contrato> contratos = new LinkedHashSet<Contrato>();
	private Collection<Etapa> etapas = new ArrayList<Etapa>();
	private Collection<Produto> produtos = new ArrayList<Produto>();

	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	public MenuController(Result result,Validator validator, EmpresaDao empresaDao, OrganizacaoDao organizacaoDao,MenuDao menuDao,UsuarioInfo usuarioInfo,
			UsuarioDao usuarioDao,ContratoDao contratoDao,PerfilDao perfilDao,EtapaDao etapaDao,WorkflowDao workflowDao,ProdutoDao produtoDao,
			BancoDao bancoDao,Empresa empresa,Organizacao organizacao,Usuario usuario){

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
		this.produtoDao = produtoDao;
		this.bancoDao = bancoDao;
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();
		this.usuario = usuarioInfo.getUsuario();

	}
	
	@Get
	@Path("/menu/inicio/{tipo}") 
	public void inicio(String tipo) {

		usuarioDao.refresh(usuarioInfo.getUsuario());
		perfilDao.refresh(usuarioInfo.getPerfil());

		if(tipo.equals("Supervisor") || tipo.equals("Consultor"))
			contratos.addAll(this.contratoDao.buscaContratoByUsuario(usuarioInfo.getUsuario().getUsuario_id()));

		if(tipo.equals("Administrativo"))
			contratos.addAll(this.contratoDao.buscaContratoByEmpresaOrganizacao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));

		contador();

	}
	
	@Get
	@Path("/menu/contratos/{tipo}") 
	public void contratos(String tipo) {

		usuarioDao.refresh(usuarioInfo.getUsuario());
		perfilDao.refresh(usuarioInfo.getPerfil());

		if(tipo.equals("Supervisor") || tipo.equals("Consultor"))
			contratos.addAll(this.contratoDao.buscaContratoByUsuario(usuarioInfo.getUsuario().getUsuario_id()));

		if(tipo.equals("Administrativo"))
			contratos.addAll(this.contratoDao.buscaContratoByEmpresaOrganizacao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));

		result.include("bancos",this.bancoDao.buscaBancoByGrupo("Tomadores"));
		result.include("bancosComprados",this.bancoDao.buscaBancoByGrupo("Comprados"));

		result.include("etapas",this.etapaDao.buscaEtapasByEmpresaOrganizacaoWorkflow(empresa.getEmpresa_id(),organizacao.getOrganizacao_id(),
				this.workflowDao.buscaWorkflowPorNome(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Status Contrato").getWorkflow_id()));

		result.include("produtos",this.produtoDao.buscaProdutosByEmpOrg(empresa.getEmpresa_id(),organizacao.getOrganizacao_id()));

		contador();

	}
	
	@Post
	@Path("/menu/busca") 
	public void busca(String informacaoSaque,String tipoAprovado,String empresa,String tipoPagamento,String tipoRecusado,String justificativa, Collection<String> status,
			String cliente, String documento,String data, String dataFim,String dataAprovadoInicio, String dataAprovadoFim,String dataConcluidoInicio, String dataConcluidoFim,
			String dataRecusadoInicio, String dataRecusadoFim,Collection<String> bancos, Collection<String> produtos, Collection<String> bancosComprados, String motivoPendencia) {

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

		contratos.addAll(this.contratoDao.buscaContratoByFiltros(this.empresa.getEmpresa_id(), this.organizacao.getOrganizacao_id(), calInicio, calFim, cliente, documento, status,
				produtos, bancos, bancosComprados));

		result.include("contratos",contratos);		

	}

	@Get
	@Public
	@Path("/menu/cadastro")
	public void cadastro() {

	}

	@Post
	@Public
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

	@Get @Path("/menu/busca.json")
	@Public
	public void menus(Long empresa_id, Long organizacao_id, String nome){
		result.use(Results.json()).withoutRoot().from(menuDao.buscaMenus(empresa_id, organizacao_id, nome)).serialize();
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
		result.include("totalValorMeta",totalValorMeta);
		result.include("totalValorDivida",totalValorDivida);
		result.include("totalValorSeguro",totalValorSeguro);
		result.include("totalValorLiquido",totalValorLiquido);
		result.include("countContratos",countContratos);
		result.include("countClientes",countClientes.size());

	}

}
