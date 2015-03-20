package br.com.sgo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
import br.com.sgo.dao.ConvenioDao;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.EtapaDao;
import br.com.sgo.dao.LogisticaDao;
import br.com.sgo.dao.MeioPagamentoDao;
import br.com.sgo.dao.MenuDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.PerfilDao;
import br.com.sgo.dao.PeriodoDao;
import br.com.sgo.dao.ProdutoDao;
import br.com.sgo.dao.RegiaoDao;
import br.com.sgo.dao.TipoControleDao;
import br.com.sgo.dao.TipoLogisticaDao;
import br.com.sgo.dao.TipoSaqueDao;
import br.com.sgo.dao.TipoWorkflowDao;
import br.com.sgo.dao.UsuarioDao;
import br.com.sgo.dao.UsuarioPerfilDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Etapa;
import br.com.sgo.modelo.Logistica;
import br.com.sgo.modelo.Menu;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.ParceiroNegocio;
import br.com.sgo.modelo.Perfil;
import br.com.sgo.modelo.Periodo;
import br.com.sgo.modelo.Regiao;
import br.com.sgo.modelo.TipoLogistica;
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
	private final UsuarioPerfilDao usuarioPerfilDao;
	private final EtapaDao etapaDao;
	private final TipoWorkflowDao tipoWorkflowDao;
	private final PeriodoDao periodoDao;
	private final TipoLogisticaDao tipoLogisticaDao;
	private final ProdutoDao produtoDao;
	private final BancoDao bancoDao;
	private final TipoControleDao tipoControleDao;
	private final CoeficienteDao coeficienteDao;
	private final ConvenioDao convenioDao;
	private final TipoSaqueDao tipoSaqueDao;
	private final MeioPagamentoDao meioPagamentoDao;
	private final LogisticaDao logisticaDao;
	private final RegiaoDao regiaoDao;
	private final UsuarioInfo usuarioInfo;
	private Set<Contrato> contratos = new LinkedHashSet<Contrato>();
	private Set<Logistica> logisticas = new LinkedHashSet<Logistica>();
	private Collection<Usuario> consultores = new ArrayList<Usuario>();
	private Collection<Usuario> consultoresAux = new ArrayList<Usuario>();
	private Collection<Periodo> periodos;
	private Collection<TipoLogistica> tiposLogistica;
	private Collection<Regiao> regioes;
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	public MenuController(Result result,Validator validator, EmpresaDao empresaDao, OrganizacaoDao organizacaoDao,MenuDao menuDao,UsuarioInfo usuarioInfo,CoeficienteDao coeficienteDao,
			UsuarioDao usuarioDao,ContratoDao contratoDao,PerfilDao perfilDao,EtapaDao etapaDao,TipoWorkflowDao tipoWorkflowDao, ProdutoDao produtoDao,TipoSaqueDao tipoSaqueDao,
			ConvenioDao convenioDao,PeriodoDao periodoDao,TipoLogisticaDao tipoLogisticaDao,UsuarioPerfilDao usuarioPerfilDao,LogisticaDao logisticaDao,
			MeioPagamentoDao meioPagamentoDao,TipoControleDao tipoControleDao,BancoDao bancoDao,Empresa empresa,Organizacao organizacao,Usuario usuario,RegiaoDao regiaoDao){

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
		this.tipoWorkflowDao = tipoWorkflowDao;
		this.produtoDao = produtoDao;
		this.tipoSaqueDao = tipoSaqueDao;
		this.periodoDao = periodoDao;
		this.tipoLogisticaDao = tipoLogisticaDao;
		this.meioPagamentoDao = meioPagamentoDao;
		this.coeficienteDao = coeficienteDao;
		this.bancoDao = bancoDao;
		this.convenioDao = convenioDao;
		this.tipoControleDao = tipoControleDao;
		this.usuarioPerfilDao = usuarioPerfilDao;
		this.regiaoDao = regiaoDao;
		this.logisticaDao = logisticaDao;
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();
		this.usuario = usuarioInfo.getUsuario();

	}
	
	@Get
	@Path("/menu/inicio/{perfil}") 
	public void inicio(String perfil) {

		//Date d1 = new GregorianCalendar().getTime();
		//Calendar dataInicial = Calendar.getInstance(); 
		//dataInicial.setTime(d1);    
		
		usuarioDao.refresh(usuarioInfo.getUsuario());
		perfilDao.refresh(usuarioInfo.getPerfil());

		Calendar dia1 = new GregorianCalendar();
		Calendar dia2 = new GregorianCalendar();

		dia1.set(GregorianCalendar.HOUR_OF_DAY,dia1.getActualMinimum(GregorianCalendar.HOUR_OF_DAY));
		dia1.set(GregorianCalendar.MINUTE,dia1.getActualMinimum(GregorianCalendar.MINUTE));
		dia1.set(GregorianCalendar.SECOND,dia1.getActualMinimum(GregorianCalendar.SECOND));
		dia2.set(GregorianCalendar.HOUR_OF_DAY, dia2.getActualMaximum(GregorianCalendar.HOUR));
		dia2.set(GregorianCalendar.MINUTE, dia2.getActualMaximum(GregorianCalendar.MINUTE));
		dia2.set(GregorianCalendar.SECOND, dia2.getActualMaximum(GregorianCalendar.SECOND));
		
		Calendar mes1 = new GregorianCalendar();
		Calendar mes2 = new GregorianCalendar();

		mes1.set(GregorianCalendar.DAY_OF_MONTH, mes1.getActualMinimum(GregorianCalendar.DAY_OF_MONTH));
		mes1.set(GregorianCalendar.HOUR_OF_DAY,mes1.getActualMinimum(GregorianCalendar.HOUR_OF_DAY));
		mes1.set(GregorianCalendar.MINUTE,mes1.getActualMinimum(GregorianCalendar.MINUTE));
		mes1.set(GregorianCalendar.SECOND,mes1.getActualMinimum(GregorianCalendar.SECOND));
		mes2.set(GregorianCalendar.DAY_OF_MONTH, mes2.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
		mes2.set(GregorianCalendar.HOUR_OF_DAY, mes2.getActualMaximum(GregorianCalendar.HOUR));
		mes2.set(GregorianCalendar.MINUTE, mes2.getActualMaximum(GregorianCalendar.MINUTE));
		mes2.set(GregorianCalendar.SECOND, mes2.getActualMaximum(GregorianCalendar.SECOND));

		Long empresa_id = empresa.getEmpresa_id();
		Long organizacao_id = organizacao.getOrganizacao_id();
		String cliente = "";
		String documento = "";
		Collection<Long> convenios = new ArrayList<Long>();
		Collection<Long> empresas = new ArrayList<Long>();
		Collection<String> status = new ArrayList<String>();
		Collection<String> statusFinal = new ArrayList<String>();
		Collection<String> justificativas = new ArrayList<String>();
		Collection<String> produtos = new ArrayList<String>();
		Collection<String> bancos = new ArrayList<String>();
		Collection<String> bancosComprados = new ArrayList<String>();
		Collection<Usuario> consultores = new ArrayList<Usuario>();
		HashMap<String,Object[]> contratosStatusFinal = new HashMap<String,Object[]>();

		Calendar calAprovadoInicio = null;
		Calendar calAprovadoFim = null;
		Calendar calConcluidoInicio = null;
		Calendar calConcluidoFim = null;

		if(perfil.equals("Consultor")){

			contratos.addAll(this.contratoDao.buscaContratoByUsuario(usuarioInfo.getUsuario().getUsuario_id(),dia1,dia2));

			result.include("mapEtapas", this.contratoDao.buscaContratosToCountEtapas(empresa_id, organizacao_id, usuarioInfo.getUsuario().getUsuario_id()));

			contratosStatusFinal.putAll(this.contratoDao.buscaContratosToCountEtapasStatusFinal(empresa_id, organizacao_id, usuarioInfo.getUsuario().getUsuario_id(), mes1, mes2));
			contratosStatusFinal.putAll(this.contratoDao.buscaContratosToCountEtapasConcluído(empresa_id, organizacao_id, usuarioInfo.getUsuario().getUsuario_id(), mes1, mes2));

			result.include("mapEtapasFinal",contratosStatusFinal);

		}
		
		if(perfil.equals("Supervisor")){

			contratos.addAll(this.contratoDao.buscaContratoByUsuario(usuarioInfo.getUsuario().getUsuario_id(),dia1,dia2));

			result.include("mapEtapas", this.contratoDao.buscaContratosToCountEtapas(empresa_id, organizacao_id, usuarioInfo.getUsuario().getUsuario_id()));

			contratosStatusFinal.putAll(this.contratoDao.buscaContratosToCountEtapasStatusFinal(empresa_id, organizacao_id, usuarioInfo.getUsuario().getUsuario_id(), mes1, mes2));
			contratosStatusFinal.putAll(this.contratoDao.buscaContratosToCountEtapasConcluído(empresa_id, organizacao_id, usuarioInfo.getUsuario().getUsuario_id(), mes1, mes2));

			result.include("mapEtapasFinal",contratosStatusFinal);
			result.include("mapConsultoresCount",this.contratoDao.buscaContratosToCountConsultores(empresa_id, organizacao_id,usuarioInfo.getUsuario().getUsuario_id(), dia1, dia2));

		}

		if(perfil.equals("Administrativo")){

			contratos.addAll(this.contratoDao.buscaContratoByEmpresaOrganizacao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(),dia1,dia2));
			result.include("mapEtapas", this.contratoDao.buscaContratosToCountEtapas(empresa_id, organizacao_id, null));
			
			contratosStatusFinal.putAll(this.contratoDao.buscaContratosToCountEtapasStatusFinal(empresa_id, organizacao_id, null, mes1, mes2));
			contratosStatusFinal.putAll(this.contratoDao.buscaContratosToCountEtapasConcluído(empresa_id, organizacao_id, null, mes1, mes2));

			result.include("mapEtapasFinal",contratosStatusFinal);

		}

		if(perfil.equals("Gestor")){

			contratos.addAll(this.contratoDao.buscaContratoByFiltros(empresa_id,organizacao_id,dia1,dia2,calAprovadoInicio,calAprovadoFim,calConcluidoInicio,calConcluidoFim, cliente, documento, convenios,status,statusFinal,justificativas, produtos, bancos, bancosComprados, consultores,false,null,null,empresas));
			result.include("mapEtapas", this.contratoDao.buscaContratosToCountEtapas(empresa_id, organizacao_id, null));

			contratosStatusFinal.putAll(this.contratoDao.buscaContratosToCountEtapasStatusFinal(empresa_id, organizacao_id, null, mes1, mes2));
			contratosStatusFinal.putAll(this.contratoDao.buscaContratosToCountEtapasConcluído(empresa_id, organizacao_id, null, mes1, mes2));

			result.include("mapEtapasFinal",contratosStatusFinal);
			result.include("mapEquipesCount",this.contratoDao.buscaContratosToCountEquipes(empresa_id, organizacao_id, dia1, dia2));

		}

		Collection<Perfil> perfis = this.usuarioPerfilDao.buscaUsuarioPerfilAcesso(usuario);
		result.include("perfis",perfis);

		result.include("calInicio", dia1);
		result.include("calFim", dia2);
		
		result.include("calMesInicio", mes1);
		result.include("calMesFim", mes2);

		contadorSeparado();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		System.out.println(" Acesso : " + usuarioInfo.getUsuario().getNome() + " em :  " + sdf.format(Calendar.getInstance().getTime()) );

	}

	@Get
	@Path("/menu/contratos/{tipo}") 
	public void contratos(String tipo) {

		usuarioDao.refresh(usuarioInfo.getUsuario());
		perfilDao.refresh(usuarioInfo.getPerfil());

		Calendar c1 = new GregorianCalendar();
		Calendar c2 = new GregorianCalendar();
		c1.set(GregorianCalendar.HOUR_OF_DAY, c1.getActualMinimum(GregorianCalendar.HOUR_OF_DAY));
		c1.set(GregorianCalendar.MINUTE, c1.getActualMinimum(GregorianCalendar.MINUTE));
		c1.set(GregorianCalendar.SECOND, c1.getActualMinimum(GregorianCalendar.SECOND));
		c2.set(GregorianCalendar.HOUR_OF_DAY, c2.getActualMaximum(GregorianCalendar.HOUR_OF_DAY));
		c2.set(GregorianCalendar.MINUTE, c2.getActualMaximum(GregorianCalendar.MINUTE));
		c2.set(GregorianCalendar.SECOND, c2.getActualMaximum(GregorianCalendar.SECOND));

		Long empresa_id = empresa.getEmpresa_id();
		Long organizacao_id = organizacao.getOrganizacao_id();
		String cliente = "";
		String documento = "";
		Collection<Long> convenios = new ArrayList<Long>();
		Collection<Long> empresas = new ArrayList<Long>();
		Collection<String> status = new ArrayList<String>();
		Collection<String> statusFinal = new ArrayList<String>();
		Collection<String> justificativas = new ArrayList<String>();
		Collection<String> produtos = new ArrayList<String>();
		Collection<String> bancos = new ArrayList<String>();
		Collection<String> bancosComprados = new ArrayList<String>();
		Collection<Usuario> consultores = new ArrayList<Usuario>();
		Calendar calAprovadoInicio = null;
		Calendar calAprovadoFim = null;
		Calendar calConcluidoInicio = null;
		Calendar calConcluidoFim = null;
		
		if(tipo.equals("Supervisor")){

			result.include("consultores",this.usuarioDao.buscaUsuariosBySupervisor(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), this.usuario.getUsuario_id()));

		}

		if(tipo.equals("Supervisor") || tipo.equals("Consultor")){

			contratos.addAll(this.contratoDao.buscaContratoByUsuario(usuarioInfo.getUsuario().getUsuario_id(),c1,c2));

			result.include("function","buscaContratos();");
			result.include("buscaDatasControle","none");
			result.include("buscaAprovado","block");

		}
	
		if(tipo.equals("Administrativo")  || tipo.equals("Administrador") ) {

			contratos.addAll(this.contratoDao.buscaContratoByEmpresaOrganizacao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(),c1,c2));

			result.include("function","buscaContratos();");
			result.include("buscaDatasControle","none");
			result.include("buscaAprovado","block");
			
		}
			
		
		if(tipo.equals("Gestor")){

			if(usuarioInfo.getPerfil().getNome().equals("Supervisor") || usuarioInfo.getPerfil().getNome().equals("Consultor"))
				consultores.add(usuario);

			contratos.addAll(this.contratoDao.buscaContratoByFiltros(empresa_id,organizacao_id,c1,c2,calAprovadoInicio,calAprovadoFim,calConcluidoInicio,calConcluidoFim, cliente, documento, convenios,status,statusFinal,justificativas, produtos, bancos, bancosComprados, consultores,false,null,null,empresas));

			result.include("function","buscaContratos();");
			result.include("buscaDatasControle","none");
			result.include("buscaAprovado","block");
		}

		if(tipo.equals("aprovados")){

			if(usuarioInfo.getPerfil().getNome().equals("Supervisor") || usuarioInfo.getPerfil().getNome().equals("Consultor"))
				consultores.add(usuario);

			c1.set(GregorianCalendar.DAY_OF_MONTH, c1.getActualMinimum(GregorianCalendar.DAY_OF_MONTH));
			c1.set(GregorianCalendar.HOUR, c1.getActualMinimum(GregorianCalendar.HOUR));
			c1.set(GregorianCalendar.MINUTE, c1.getActualMinimum(GregorianCalendar.MINUTE));
			c1.set(GregorianCalendar.SECOND, c1.getActualMinimum(GregorianCalendar.SECOND));
			c2.set(GregorianCalendar.DAY_OF_MONTH, c2.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
			c2.set(GregorianCalendar.HOUR, c2.getActualMaximum(GregorianCalendar.HOUR));
			c2.set(GregorianCalendar.MINUTE, c2.getActualMaximum(GregorianCalendar.MINUTE));
			c2.set(GregorianCalendar.SECOND, c2.getActualMaximum(GregorianCalendar.SECOND));

			statusFinal.add("Aprovado");

			contratos.addAll(this.contratoDao.buscaContratoByFiltros(empresa_id,organizacao_id,null,null,c1,c2,null,null, cliente, documento, convenios,status,statusFinal,justificativas, produtos, bancos, bancosComprados, consultores,false,null,null,empresas));

			result.include("function","buscaContratos();");
			result.include("buscaDatasControle","none");
			result.include("buscaAprovado","block");

		}
		
		if(tipo.equals("concluidos")){

			if(usuarioInfo.getPerfil().getNome().equals("Supervisor") || usuarioInfo.getPerfil().getNome().equals("Consultor"))
				consultores.add(usuario);

			c1.set(GregorianCalendar.DAY_OF_MONTH, c1.getActualMinimum(GregorianCalendar.DAY_OF_MONTH));
			c1.set(GregorianCalendar.HOUR, c1.getActualMinimum(GregorianCalendar.HOUR));
			c1.set(GregorianCalendar.MINUTE, c1.getActualMinimum(GregorianCalendar.MINUTE));
			c1.set(GregorianCalendar.SECOND, c1.getActualMinimum(GregorianCalendar.SECOND));
			c2.set(GregorianCalendar.DAY_OF_MONTH, c2.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
			c2.set(GregorianCalendar.HOUR, c2.getActualMaximum(GregorianCalendar.HOUR));
			c2.set(GregorianCalendar.MINUTE, c2.getActualMaximum(GregorianCalendar.MINUTE));
			c2.set(GregorianCalendar.SECOND, c2.getActualMaximum(GregorianCalendar.SECOND));

			statusFinal.add("Concluído");

			contratos.addAll(this.contratoDao.buscaContratoByFiltros(empresa_id,organizacao_id,null,null,null,null,c1,c2, cliente, documento, convenios,status,statusFinal,justificativas, produtos, bancos, bancosComprados, consultores,false,null,null,empresas));

			result.include("function","buscaContratos();");
			result.include("buscaDatasControle","none");
			result.include("buscaAprovado","block");

		}
		
		if(tipo.equals("recusados")){

			if(usuarioInfo.getPerfil().getNome().equals("Supervisor") || usuarioInfo.getPerfil().getNome().equals("Consultor"))
				consultores.add(usuario);

			c1.set(GregorianCalendar.DAY_OF_MONTH, c1.getActualMinimum(GregorianCalendar.DAY_OF_MONTH));
			c1.set(GregorianCalendar.HOUR, c1.getActualMinimum(GregorianCalendar.HOUR));
			c1.set(GregorianCalendar.MINUTE, c1.getActualMinimum(GregorianCalendar.MINUTE));
			c1.set(GregorianCalendar.SECOND, c1.getActualMinimum(GregorianCalendar.SECOND));
			c2.set(GregorianCalendar.DAY_OF_MONTH, c2.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
			c2.set(GregorianCalendar.HOUR, c2.getActualMaximum(GregorianCalendar.HOUR));
			c2.set(GregorianCalendar.MINUTE, c2.getActualMaximum(GregorianCalendar.MINUTE));
			c2.set(GregorianCalendar.SECOND, c2.getActualMaximum(GregorianCalendar.SECOND));

			statusFinal.add("Recusado");

			contratos.addAll(this.contratoDao.buscaContratoByFiltros(empresa_id,organizacao_id,null,null,c1,c2,null,null, cliente, documento, convenios,status,statusFinal,justificativas, produtos, bancos, bancosComprados, consultores,false,null,null,empresas));

			result.include("function","buscaContratos();");
			result.include("buscaDatasControle","none");
			result.include("buscaAprovado","block");

		}

		if(tipo.equals("datascontrole")){

			if(usuarioInfo.getPerfil().getNome().equals("Supervisor"))
				result.include("consultores",this.usuarioDao.buscaUsuariosBySupervisor(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), this.usuario.getUsuario_id()));

			result.include("tipobusca","datascontrole");
			result.include("function","buscaDatasControle();");
			result.include("buscaDatasControle","block");
			result.include("buscaAprovado","none");

		} 

		if(tipo.equals("proposta")){
			result.redirectTo(this).buscaproposta();
		}
		
		if(tipo.equals("logistica")){
			result.redirectTo(this).buscalogistica();;
		}

		TipoWorkflow tw;

		tw = this.tipoWorkflowDao.buscaTipoWorkflowPorEmpresaOrganizacaoNomeExato(1l, 1l, "Contrato");
		result.include("etapas",this.etapaDao.buscaEtapasByEmpresaOrganizacaoTipoWorkflow(empresa.getEmpresa_id(),organizacao.getOrganizacao_id(),tw.getTipoWorkflow_id()));

		
		tw = this.tipoWorkflowDao.buscaTipoWorkflowPorEmpresaOrganizacaoNomeExato(1l, 1l, "Controle Contrato");
		result.include("procedimentos",this.etapaDao.buscaEtapasByEmpresaOrganizacaoTipoWorkflow(empresa.getEmpresa_id(),organizacao.getOrganizacao_id(),tw.getTipoWorkflow_id()));

		result.include("bancos",this.bancoDao.buscaBancosToBancoProdutoByEmpOrg(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
		result.include("bancosComprado",this.bancoDao.buscaBancoCompradoByEmpOrg(1l, 1l));

		Collection<Usuario> atuantes = new ArrayList<Usuario>();		
		atuantes.addAll(this.usuarioDao.buscaUsuariosByPerfilDepartamento(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Administrativo", "Apoio Comercial"));
		atuantes.addAll(this.usuarioDao.buscaUsuariosByPerfilDepartamento(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Gestor", "Apoio Comercial"));

		result.include("atuantes",atuantes);		 
		result.include("produtos",this.produtoDao.buscaProdutosByEmpOrg(empresa.getEmpresa_id(),organizacao.getOrganizacao_id()));

		Collection<Usuario> supervisores = new ArrayList<Usuario>();

		supervisores.addAll(this.usuarioDao.buscaUsuariosByPerfilDepartamento(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Supervisor", "Comercial"));
		supervisores.addAll(this.usuarioDao.buscaUsuariosByPerfilDepartamento(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Gestor", "Comercial"));
		supervisores.addAll(this.usuarioDao.buscaUsuariosByPerfilDepartamento(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Gestor", "Retenção"));
		supervisores.addAll(this.usuarioDao.buscaUsuariosByPerfilDepartamento(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Supervisor", "Retenção"));

		result.include("supervisores",supervisores);

		result.include("tiposSaque",this.tipoSaqueDao.buscaAllTipoSaque());
		result.include("meiosPagamento",this.meioPagamentoDao.buscaAllMeioPagamento(1l, 1l));
		result.include("convenios",this.convenioDao.buscaConvenioToFillComboByEmpOrg(1l, 1l));
		result.include("contratos",contratos);
		
		periodos = periodoDao.buscaAllPeriodos();
		tiposLogistica = tipoLogisticaDao.buscaAllTipoLogistica();
		
		result.include("periodos", periodos);
		result.include("tiposLogistica", tiposLogistica);
		

		contador();

	}

	@Get
	@Path("/menu/contratos/busca/{tipo}/{id}") 
	public void contratos(String tipo,Long id) {

		usuarioDao.refresh(usuarioInfo.getUsuario());
		perfilDao.refresh(usuarioInfo.getPerfil());

		Long empresa_id = empresa.getEmpresa_id();
		Long organizacao_id = organizacao.getOrganizacao_id();
		String cliente = "";
		String documento = "";
		Collection<Long> convenios = new ArrayList<Long>();
		Collection<Long> empresas = new ArrayList<Long>();
		Collection<String> status = new ArrayList<String>();
		Collection<String> statusFinal = new ArrayList<String>();
		Collection<String> justificativas = new ArrayList<String>();
		Collection<String> produtos = new ArrayList<String>();
		Collection<String> bancos = new ArrayList<String>();
		Collection<String> bancosComprados = new ArrayList<String>();
		Collection<Usuario> consultores = new ArrayList<Usuario>();
		Calendar calAprovadoInicio = null;
		Calendar calAprovadoFim = null;
		Calendar calConcluidoInicio = null;
		Calendar calConcluidoFim = null;

		Calendar dia1 = new GregorianCalendar();
		Calendar dia2 = new GregorianCalendar();

		dia1.set(GregorianCalendar.HOUR_OF_DAY,dia1.getActualMinimum(GregorianCalendar.HOUR_OF_DAY));
		dia1.set(GregorianCalendar.MINUTE,dia1.getActualMinimum(GregorianCalendar.MINUTE));
		dia1.set(GregorianCalendar.SECOND,dia1.getActualMinimum(GregorianCalendar.SECOND));
		dia2.set(GregorianCalendar.HOUR_OF_DAY, dia2.getActualMaximum(GregorianCalendar.HOUR));
		dia2.set(GregorianCalendar.MINUTE, dia2.getActualMaximum(GregorianCalendar.MINUTE));
		dia2.set(GregorianCalendar.SECOND, dia2.getActualMaximum(GregorianCalendar.SECOND));

		if(tipo.equals("etapas")){
			
			if(usuarioInfo.getPerfil().getNome().equals("Supervisor") || usuarioInfo.getPerfil().getNome().equals("Consultor"))
				consultores.add(usuario);

			Etapa e = this.etapaDao.buscaEtapaById(id);
			status.add(e.getNome());

			dia1 = null;
			dia2 = null;

			if(e.getNome().equals("Enviado DataPrev")){

				contratos.addAll(this.contratoDao.buscaContratoByFiltros(empresa_id,organizacao_id,dia1,dia2,calAprovadoInicio,calAprovadoFim,calConcluidoInicio,calConcluidoFim, cliente, documento, convenios,status,statusFinal,justificativas, produtos, bancos, bancosComprados, consultores,false,null,null,empresas));

				result.include("tipobusca","datascontrole");
				result.include("function","buscaDatasControle();");
				result.include("buscaDatasControle","block");
				result.include("buscaAprovado","none");

			} else if(e.getNome().equals("Aguardando Apoio Comercial") || e.getNome().equals("Aguardando Boleto")){

				contratos.addAll(this.contratoDao.buscaContratoByFiltros(empresa_id,organizacao_id,dia1,dia2,calAprovadoInicio,calAprovadoFim,calConcluidoInicio,calConcluidoFim, cliente, documento, convenios,status,statusFinal,justificativas, produtos, bancos, bancosComprados, consultores,false,null,null,empresas));

				result.include("tipobusca","datascontrole");
				result.include("function","buscaDatasControle();");
				result.include("buscaDatasControle","block");
				result.include("buscaAprovado","none");

			} else {

				contratos.addAll(this.contratoDao.buscaContratoByFiltros(empresa_id,organizacao_id,dia1,dia2,calAprovadoInicio,calAprovadoFim,calConcluidoInicio,calConcluidoFim, cliente, documento, convenios,status,statusFinal,justificativas, produtos, bancos, bancosComprados, consultores,false,null,null,empresas));

				result.include("function","buscaContratos();");
				result.include("buscaDatasControle","none");
				result.include("buscaAprovado","block");

			}

		}
		
		if(tipo.equals("supervisores")){

			if(usuarioInfo.getPerfil().getNome().equals("Supervisor") || usuarioInfo.getPerfil().getNome().equals("Consultor"))
				consultores.add(usuario);
			else
				consultores.add(this.usuarioDao.load(id));

			contratos.addAll(this.contratoDao.buscaContratoByFiltros(empresa_id,organizacao_id,dia1,dia2,calAprovadoInicio,calAprovadoFim,calConcluidoInicio,calConcluidoFim, cliente, documento, convenios,status,statusFinal,justificativas, produtos, bancos, bancosComprados, consultores,false,null,null,empresas));

			result.include("function","buscaContratos();");
			result.include("buscaDatasControle","none");
			result.include("buscaAprovado","block");

		}
		
		if(tipo.equals("consultores")){

			consultores.add(this.usuarioDao.load(id));

			contratos.addAll(this.contratoDao.buscaContratoByFiltros(empresa_id,organizacao_id,dia1,dia2,calAprovadoInicio,calAprovadoFim,calConcluidoInicio,calConcluidoFim, cliente, documento, convenios,status,statusFinal,justificativas, produtos, bancos, bancosComprados, consultores,false,null,null,empresas));

			result.include("function","buscaContratos();");
			result.include("buscaDatasControle","none");
			result.include("buscaAprovado","block");

		}

		TipoWorkflow tw;

		tw = this.tipoWorkflowDao.buscaTipoWorkflowPorEmpresaOrganizacaoNomeExato(1l, 1l, "Contrato");
		result.include("etapas",this.etapaDao.buscaEtapasByEmpresaOrganizacaoTipoWorkflow(empresa.getEmpresa_id(),organizacao.getOrganizacao_id(),tw.getTipoWorkflow_id()));

		tw = this.tipoWorkflowDao.buscaTipoWorkflowPorEmpresaOrganizacaoNomeExato(1l, 1l, "Controle Contrato");
		result.include("procedimentos",this.etapaDao.buscaEtapasByEmpresaOrganizacaoTipoWorkflow(empresa.getEmpresa_id(),organizacao.getOrganizacao_id(),tw.getTipoWorkflow_id()));

		result.include("bancos",this.bancoDao.buscaBancosToBancoProdutoByEmpOrg(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
		result.include("bancosComprado",this.bancoDao.buscaBancoCompradoByEmpOrg(1l, 1l));
		result.include("atuantes",this.usuarioDao.buscaUsuariosByPerfilDepartamento(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Administrativo", "Apoio Comercial"));		 
		result.include("produtos",this.produtoDao.buscaProdutosByEmpOrg(empresa.getEmpresa_id(),organizacao.getOrganizacao_id()));
		result.include("supervisores", this.usuarioDao.buscaUsuariosByPerfilDepartamento(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Supervisor", "Comercial"));
		result.include("tiposSaque",this.tipoSaqueDao.buscaAllTipoSaque());
		result.include("meiosPagamento",this.meioPagamentoDao.buscaAllMeioPagamento(1l, 1l));
		
		periodos = periodoDao.buscaAllPeriodos();
		tiposLogistica = tipoLogisticaDao.buscaAllTipoLogistica();
		
		result.include("periodos", periodos);
		result.include("tiposLogistica", tiposLogistica);

		result.include("contratos",contratos);

		contador();

	}
	
	@Post
	@Path("/menu/busca") 
	public void busca(Long informacaoSaque,String tipoAprovado,Collection<Long> empresas,Long tipoPagamento,String tipoRecusado,String justificativa, Collection<String> status,
			Collection<Long> convenios,String cliente, String documento,String data, String dataFim,String dataAprovadoInicio, String dataAprovadoFim,String dataConcluidoInicio, 
			String dataConcluidoFim, String dataRecusadoInicio, String dataRecusadoFim,Collection<String> bancos, Collection<String> produtos, Collection<String> bancosComprados, 
			String motivoPendencia, Boolean isSupervisorApoio ,Long consultor) {

		Calendar calInicio = new GregorianCalendar();
		Calendar calFim = new GregorianCalendar();
		Calendar calAprovadoInicio = new GregorianCalendar();
		Calendar calAprovadoFim = new GregorianCalendar();
		Calendar calConcluidoInicio = new GregorianCalendar();
		Calendar calConcluidoFim = new GregorianCalendar();
		Calendar calRecusadoInicio = new GregorianCalendar();
		Calendar calRecusadoFim = new GregorianCalendar();
		Calendar calStatusFinalInicio = null;
		Calendar calStatusFinalFim = null;

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		
		Collection<String> statusFinal = new ArrayList<String>();
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

				calStatusFinalInicio = calAprovadoInicio;
				calStatusFinalFim = calAprovadoFim;

			}

			if(calConcluidoInicio != null) {
				statusFinal.add("Concluído");
			}

			if(calRecusadoInicio != null) {

				statusFinal.add("Recusado");

				calStatusFinalInicio = calRecusadoInicio;
				calStatusFinalFim = calRecusadoFim;

			}

			if(!justificativa.equals("Todos")){
				justificativas.add(justificativa);
			}
			
			/*
			if(calRecusadoInicio != null) {
				if(!justificativa.equals("Todos")){
					justificativas.add(justificativa);
				}
			}
			*/

		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(usuarioInfo.getPerfil().getChave().equals("Consultor") || usuarioInfo.getPerfil().getChave().equals("Supervisor")){

			Usuario u = new Usuario();

			if(consultor != null) {
				u = this.usuarioDao.load(consultor);
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
				calStatusFinalInicio, calStatusFinalFim ,calConcluidoInicio, calConcluidoFim,cliente, documento, convenios,status,statusFinal,justificativas,
				produtos, bancos, bancosComprados,consultoresAux,isSupervisorApoio,tipoPagamento,informacaoSaque,empresas));

		contador();

		result.include("contratos",contratos);		

	}

	@Post
	@Path("/menu/busca/controle")
	public void busca(Long tipoControle,String data, String dataFim,String previsaoInicio,String previsaoFim, String chegadaInicio,String chegadaFim,String vencimentoInicio,
							String vencimentoFim, String proximaAtuacaoInicio,String proximaAtuacaoFim , String quitacaoInicio,String quitacaoFim , String assinaturaInicio,String assinaturaFim ,
							Collection<String> bancos, 
							Collection<String> produtos, Collection<String> bancosComprados,Collection<String> status,Collection<Long> convenios,
							Boolean isSupervisorApoio, Long consultor,String cliente, String documento,
							Collection<Long> empresas,Long procedimento , Long proximoProcedimento, Long atuante, Long tipoLogistica, Long periodo) {

		Calendar calInicio = new GregorianCalendar();
		Calendar calFim = new GregorianCalendar();
		Calendar calPrevisaoInicio = new GregorianCalendar();
		Calendar calPrevisaoFim = new GregorianCalendar();
		Calendar calChegadaInicio = new GregorianCalendar();
		Calendar calChegadaFim = new GregorianCalendar();
		Calendar calVencimentoInicio = new GregorianCalendar();
		Calendar calVencimentoFim = new GregorianCalendar();
		Calendar calProximaAtuacaoInicio = new GregorianCalendar();
		Calendar calProximaAtuacaoFim = new GregorianCalendar();
		Calendar calQuitacaoInicio = new GregorianCalendar();
		Calendar calQuitacaoFim = new GregorianCalendar();
		Calendar calAssinaturaInicio = new GregorianCalendar();
		Calendar calAssinaturaFim = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");

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

			if(previsaoFim.equals(""))
				previsaoFim = previsaoInicio;
	
			if(previsaoInicio.equals("")) {
				calPrevisaoInicio = null;
				calPrevisaoFim = null;
			} else {
				calPrevisaoInicio.setTime(sdf.parse(previsaoInicio));
				calPrevisaoFim.setTime(sdf.parse(previsaoFim));

			}
			
			if(chegadaFim.equals(""))
				chegadaFim = chegadaInicio;
	
			if(chegadaInicio.equals("")) {
				calChegadaInicio = null;
				calChegadaFim = null;
			} else {
				calChegadaInicio.setTime(sdf.parse(chegadaInicio));
				calChegadaFim.setTime(sdf.parse(chegadaFim));

			}
			
			if(vencimentoFim.equals(""))
				vencimentoFim = vencimentoInicio;
	
			if(vencimentoInicio.equals("")) {
				calVencimentoInicio = null;
				calVencimentoFim = null;
			} else {
				calVencimentoInicio.setTime(sdf.parse(vencimentoInicio));
				calVencimentoFim.setTime(sdf.parse(vencimentoFim));

			}
			
			if(proximaAtuacaoFim.equals(""))
				proximaAtuacaoFim = proximaAtuacaoInicio;
	
			if(proximaAtuacaoInicio.equals("")) {
				calProximaAtuacaoInicio = null;
				calProximaAtuacaoFim = null;
			} else {
				calProximaAtuacaoInicio.setTime(sdf.parse(proximaAtuacaoInicio));
				calProximaAtuacaoFim.setTime(sdf.parse(proximaAtuacaoFim));

			}
			
			if(quitacaoFim.equals(""))
				quitacaoFim = quitacaoInicio;
	
			if(quitacaoInicio.equals("")) {
				calQuitacaoInicio = null;
				calQuitacaoFim = null;
			} else {
				calQuitacaoInicio.setTime(sdf.parse(quitacaoInicio));
				calQuitacaoFim.setTime(sdf.parse(quitacaoFim));

			}
			
			if(assinaturaFim.equals(""))
				assinaturaFim = assinaturaInicio;
	
			if(assinaturaInicio.equals("")) {
				calAssinaturaInicio = null;
				calAssinaturaFim = null;
			} else {
				calAssinaturaInicio.setTime(sdf.parse(assinaturaInicio));
				calAssinaturaFim.setTime(sdf.parse(assinaturaFim));

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

		if(tipoControle == null){

			Boolean buscaEnviadoDataPrev = false;
			Boolean buscaAguardandoBoleto = false;

			tipoControle = 2l;

			for(String s : status){

				if(s.equals("Enviado DataPrev"))
					buscaEnviadoDataPrev = true;

				if(s.equals("Aguardando Boleto") || s.equals("Aguardando Apoio Comercial"))
					buscaAguardandoBoleto = true;

			}

			if(buscaEnviadoDataPrev)
				tipoControle = 1l;

			if(buscaAguardandoBoleto)
				tipoControle = 2l;

		}

		contratos.clear();

		contratos.addAll(this.contratoDao.buscaDatasControle(this.empresa.getEmpresa_id(),this.organizacao.getOrganizacao_id(),tipoControle,
				calInicio, calFim, calPrevisaoInicio,calPrevisaoFim,calChegadaInicio,calChegadaFim,calVencimentoInicio,calVencimentoFim,
				calProximaAtuacaoInicio,calProximaAtuacaoFim,calQuitacaoInicio,calQuitacaoFim,calAssinaturaInicio,calAssinaturaFim,bancos,produtos,bancosComprados,
				status,convenios,consultoresAux,isSupervisorApoio,cliente,documento,empresas,procedimento,proximoProcedimento, atuante, tipoLogistica, periodo));

		contador();

	}
	
	@Post
	@Path("/menu/contrato/propostaContrato")
	public void busca(String propostaBanco, String contratoBanco,String digitacaoInicio,String digitacaoFim, Collection<Long> empresas , Collection<Long> bancos,
			Collection<String> status) {

		contratos.clear();
		
		Calendar calDigitacaoInicio = new GregorianCalendar();
		Calendar calDigitacaoFim = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		
		try {
			
			if(digitacaoFim.equals(""))
				digitacaoFim = digitacaoInicio;

			if(digitacaoInicio.equals("")) {

				calDigitacaoInicio = null;
				calDigitacaoFim = null;

			} else {

				calDigitacaoInicio.setTime(sdf.parse(digitacaoInicio));
				calDigitacaoFim.setTime(sdf.parse(digitacaoFim));

				calDigitacaoInicio.set(Calendar.HOUR_OF_DAY,calDigitacaoInicio.getActualMinimum(Calendar.HOUR_OF_DAY));
				calDigitacaoFim.set(Calendar.HOUR_OF_DAY,calDigitacaoFim.getActualMaximum(Calendar.HOUR_OF_DAY));

			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Usuario u = null;

		if(usuarioInfo.getPerfil().getNome().equals("Supervisor") || usuarioInfo.getPerfil().getNome().equals("Consultor"))
			u = usuarioInfo.getUsuario();

		contratos.addAll(this.contratoDao.buscaContratosByProposta(empresa.getEmpresa_id(),organizacao.getOrganizacao_id(),propostaBanco, contratoBanco, u,
				calDigitacaoInicio,calDigitacaoFim,empresas,bancos,status));

		contador();
	}
	
	@Post
	@Path("/menu/contrato/logistica")
	public void logisticas(String assinaturaInicio,String assinaturaFim, Collection<Long> bancos, Collection<String> status, Long consultor, Boolean isSupervisorApoio,
			Long tipoLogistica, Long periodo, Long regiao) {
		
		logisticas.clear();

		Calendar calAssinaturaInicio = new GregorianCalendar();
		Calendar calAssinaturaFim = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		
		try {
			
			if(assinaturaFim.equals(""))
				assinaturaFim = assinaturaInicio;

			if(assinaturaInicio.equals("")) {

				assinaturaInicio = null;
				assinaturaFim = null;

			} else {

				calAssinaturaInicio.setTime(sdf.parse(assinaturaInicio));
				calAssinaturaFim.setTime(sdf.parse(assinaturaFim));

				calAssinaturaInicio.set(Calendar.HOUR_OF_DAY,calAssinaturaInicio.getActualMinimum(Calendar.HOUR_OF_DAY));
				calAssinaturaFim.set(Calendar.HOUR_OF_DAY,calAssinaturaFim.getActualMaximum(Calendar.HOUR_OF_DAY));

			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Usuario u = null;

		if(usuarioInfo.getPerfil().getChave().equals("Consultor") || usuarioInfo.getPerfil().getChave().equals("Supervisor")){

			u = new Usuario();

			if(consultor != null) {
				u = this.usuarioDao.load(consultor);
			} else {
				u = this.usuarioDao.load(usuario.getUsuario_id());
			}

			consultoresAux.add(u);

		} else {

			if(consultor != null){

				consultoresAux.add(this.usuarioDao.load(consultor));

			}

		}

		logisticas.addAll(this.logisticaDao.buscaLogisticaByDataAssinatura(empresa.getEmpresa_id(),organizacao.getOrganizacao_id(), consultoresAux,isSupervisorApoio,
				calAssinaturaInicio,calAssinaturaFim,bancos,status,tipoLogistica,periodo, regiao));
		
		result.include("logisticas",logisticas);

	}
	
	@Get
	public void buscaproposta() {

		TipoWorkflow tw;

		tw = this.tipoWorkflowDao.buscaTipoWorkflowPorEmpresaOrganizacaoNomeExato(1l, 1l, "Contrato");
		result.include("etapas",this.etapaDao.buscaEtapasByEmpresaOrganizacaoTipoWorkflow(empresa.getEmpresa_id(),organizacao.getOrganizacao_id(),tw.getTipoWorkflow_id()));
		
		result.include("bancos",this.bancoDao.buscaBancosToBancoProdutoByEmpOrg(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));

	}
	
	@Get
	public void buscalogistica() {

		TipoWorkflow tw;

		Collection<Usuario> supervisores = new ArrayList<Usuario>();

		tw = this.tipoWorkflowDao.buscaTipoWorkflowPorEmpresaOrganizacaoNomeExato(1l, 1l, "Contrato");
		result.include("etapas",this.etapaDao.buscaEtapasByEmpresaOrganizacaoTipoWorkflow(empresa.getEmpresa_id(),organizacao.getOrganizacao_id(),tw.getTipoWorkflow_id()));

		result.include("bancos",this.bancoDao.buscaBancosToBancoProdutoByEmpOrg(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));

		supervisores.addAll(this.usuarioDao.buscaUsuariosByPerfilDepartamento(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Supervisor", "Comercial"));
		supervisores.addAll(this.usuarioDao.buscaUsuariosByPerfilDepartamento(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Gestor", "Comercial"));

		result.include("supervisores",supervisores);
		
		periodos = periodoDao.buscaAllPeriodos();
		tiposLogistica = tipoLogisticaDao.buscaAllTipoLogistica();
		
		result.include("periodos", periodos);
		result.include("tiposLogistica", tiposLogistica);
		
		regioes = new ArrayList<Regiao>();
		
		regioes.add(this.regiaoDao.buscaPorNome("SP"));
		regioes.add(this.regiaoDao.buscaPorNome("RJ"));
		
		result.include("regioes", regioes);
		
		

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
		Set<String> countClientesByCpf = new HashSet<String>();
		Double totalContratoLiquido = 0.0;

		for(Contrato cs : contratos){
			countClientes.add(cs.getFormulario().getParceiroNegocio());
			countClientesByCpf.add(cs.getFormulario().getParceiroNegocio().getCpf());

			totalValorContratos += cs.getValorContrato();
			totalValorMeta += cs.getValorMeta();
			totalValorDivida += cs.getValorDivida();
			totalValorSeguro += cs.getValorSeguro();
			totalValorLiquido += cs.getValorLiquido();
			countContratos += 1;
			
			if(cs.getProduto().getNome().equals("MARGEM LIMPA") 
					|| cs.getProduto().getNome().equals("MARGEM LIMPA PMSP")
					|| cs.getProduto().getNome().equals("MARGEM LIMPA PMRP")
					|| cs.getProduto().getNome().equals("MARGEM LIMPA SIAPE")
					|| cs.getProduto().getNome().equals("MARGEM LIMPA GOVRJ")
					|| cs.getProduto().getNome().equals("MARGEM LIMPA GOVSP")
					|| cs.getProduto().getNome().equals("DEBITO")
					|| cs.getProduto().getNome().equals("RECOMPRA INSS")  
					|| cs.getProduto().getNome().equals("PORTABILIDADE/REFIN")
					|| cs.getProduto().getNome().equals("RECOMPRA PMSP")
					|| cs.getProduto().getNome().equals("RECOMPRA PMRP")
					|| cs.getProduto().getNome().equals("RECOMPRA GOVRJ") 
					|| cs.getProduto().getNome().equals("RECOMPRA GOVSP") 
					|| cs.getProduto().getNome().equals("RECOMPRA SIAPE")
					|| cs.getProduto().getNome().equals("RECOMPRA RMC")
					|| cs.getProduto().getNome().equals("AUMENTO"))
				totalContratoLiquido += cs.getValorContrato();

			if(cs.getProduto().getNome().equals("REFINANCIAMENTO")
					|| cs.getProduto().getNome().equals("REFINANCIAMENTO PMSP")
					|| cs.getProduto().getNome().equals("REFINANCIAMENTO PMRP")
					|| cs.getProduto().getNome().equals("REFINANCIAMENTO GOVRJ")
					|| cs.getProduto().getNome().equals("REFINANCIAMENTO GOVSP")
					|| cs.getProduto().getNome().equals("REFINANCIAMENTO SIAPE")
					|| cs.getProduto().getNome().equals("RETENÇÃO") 
					|| cs.getProduto().getNome().equals("RETENÇÃO PMSP")
					|| cs.getProduto().getNome().equals("RETENÇÃO GOVRJ"))
				totalContratoLiquido += cs.getValorLiquido();

		}

		result.include("contratos",contratos);
		result.include("totalValorContratos",totalValorContratos);
		result.include("totalContratoLiquido",totalContratoLiquido);
		result.include("totalValorDivida",totalValorDivida);
		result.include("totalValorSeguro",totalValorSeguro);
		result.include("totalValorLiquido",totalValorLiquido);
		result.include("totalValorMeta",totalValorMeta);
		result.include("countContratos",countContratos);
		result.include("countClientes",countClientes.size());
		result.include("countClientesByCpf",countClientesByCpf.size());

	}
	
	private void contadorSeparado() {

		Double totalValorContratos = 0.0;
		Double totalValorMeta = 0.0;
		Double totalValorDivida = 0.0;
		Double totalValorSeguro = 0.0;
		Double totalValorLiquido = 0.0;
		Double totalContratoLiquido = 0.0;
		
		Integer countContratos = 0;
		Set<ParceiroNegocio> countClientes = new HashSet<ParceiroNegocio>();
		Set<String> countClientesByCpf = new HashSet<String>();

		for(Contrato cs : contratos){

			countClientes.add(cs.getFormulario().getParceiroNegocio());
			countClientesByCpf.add(cs.getFormulario().getParceiroNegocio().getCpf());
			totalValorContratos += cs.getValorContrato();
			
			totalValorDivida += cs.getValorDivida();
			totalValorSeguro += cs.getValorSeguro();
			totalValorLiquido += cs.getValorLiquido();
			totalValorMeta += cs.getValorMeta();

			if(cs.getProduto().getNome().equals("MARGEM LIMPA") 
					|| cs.getProduto().getNome().equals("MARGEM LIMPA PMSP")
					|| cs.getProduto().getNome().equals("MARGEM LIMPA PMRP")
					|| cs.getProduto().getNome().equals("MARGEM LIMPA GOVRJ")
					|| cs.getProduto().getNome().equals("MARGEM LIMPA GOVSP")
					|| cs.getProduto().getNome().equals("MARGEM LIMPA SIAPE")
					|| cs.getProduto().getNome().equals("DEBITO")
					|| cs.getProduto().getNome().equals("RECOMPRA INSS") 
					|| cs.getProduto().getNome().equals("PORTABILIDADE/REFIN")
					|| cs.getProduto().getNome().equals("RECOMPRA PMSP")
					|| cs.getProduto().getNome().equals("RECOMPRA PMRP")
					|| cs.getProduto().getNome().equals("RECOMPRA GOVRJ")
					|| cs.getProduto().getNome().equals("RECOMPRA GOVSP")
					|| cs.getProduto().getNome().equals("RECOMPRA SIAPE")
					|| cs.getProduto().getNome().equals("RECOMPRA RMC")
					|| cs.getProduto().getNome().equals("AUMENTO"))
				totalContratoLiquido += cs.getValorContrato();
			
			if(cs.getProduto().getNome().equals("REFINANCIAMENTO") 
					|| cs.getProduto().getNome().equals("REFINANCIAMENTO PMSP")
					|| cs.getProduto().getNome().equals("REFINANCIAMENTO PMRP")
					|| cs.getProduto().getNome().equals("REFINANCIAMENTO GOVRJ")
					|| cs.getProduto().getNome().equals("REFINANCIAMENTO GOVSP")
					|| cs.getProduto().getNome().equals("REFINANCIAMENTO SIAPE")
					|| cs.getProduto().getNome().equals("RETENÇÃO")
					|| cs.getProduto().getNome().equals("RETENÇÃO PMSP")
					|| cs.getProduto().getNome().equals("RETENÇÃO GOVRJ"))
				totalContratoLiquido += cs.getValorLiquido();
				

			countContratos += 1;

		}

		result.include("contratos",contratos);
		result.include("totalValorContratos",totalValorContratos);
		result.include("totalContratoLiquido",totalContratoLiquido);
		result.include("totalValorDivida",totalValorDivida);
		result.include("totalValorSeguro",totalValorSeguro);
		result.include("totalValorLiquido",totalValorLiquido);
		result.include("totalValorMeta",totalValorMeta);
		result.include("countContratos",countContratos);
		result.include("countClientes",countClientes.size());
		result.include("countClientesByCpf",countClientesByCpf.size());

	}

}
