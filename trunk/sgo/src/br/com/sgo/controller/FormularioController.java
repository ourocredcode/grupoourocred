package br.com.sgo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.BancoDao;
import br.com.sgo.dao.CoeficienteDao;
import br.com.sgo.dao.ContratoDao;
import br.com.sgo.dao.ControleFormularioDao;
import br.com.sgo.dao.EtapaDao;
import br.com.sgo.dao.FormularioDao;
import br.com.sgo.dao.HistoricoControleFormularioDao;
import br.com.sgo.dao.ParceiroBeneficioDao;
import br.com.sgo.dao.ParceiroInfoBancoDao;
import br.com.sgo.dao.ParceiroLocalidadeDao;
import br.com.sgo.dao.ParceiroNegocioDao;
import br.com.sgo.dao.PnDao;
import br.com.sgo.dao.ProdutoDao;
import br.com.sgo.dao.TabelaDao;
import br.com.sgo.dao.TipoControleDao;
import br.com.sgo.dao.WorkflowDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.jasper.FormularioDataSource;
import br.com.sgo.modelo.Banco;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.ControleFormulario;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Etapa;
import br.com.sgo.modelo.Formulario;
import br.com.sgo.modelo.HistoricoControleFormulario;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.ParceiroBeneficio;
import br.com.sgo.modelo.ParceiroInfoBanco;
import br.com.sgo.modelo.ParceiroLocalidade;
import br.com.sgo.modelo.ParceiroNegocio;
import br.com.sgo.modelo.Perfil;
import br.com.sgo.modelo.TipoControle;
import br.com.sgo.modelo.Usuario;
import br.com.sgo.modelo.Workflow;

@Resource
public class FormularioController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final ParceiroNegocioDao parceiroNegocioDao;
	private final ParceiroBeneficioDao parceiroBeneficioDao;
	private final ParceiroLocalidadeDao parceiroLocalidadeDao;
	private final ParceiroInfoBancoDao parceiroInfoBancoDao;
	private final ControleFormularioDao controleFormularioDao;
	private final HistoricoControleFormularioDao historicoControleFormularioDao;
	private final TipoControleDao tipoControleDao;
	private final BancoDao bancoDao;
	private final ProdutoDao produtoDao;
	private final TabelaDao tabelaDao;
	private final FormularioDao formularioDao;
	private final ContratoDao contratoDao;
	private final CoeficienteDao coeficienteDao;
	private final WorkflowDao workflowDao;
	private final EtapaDao etapaDao;
	private final PnDao pnDao;

	private HttpServletResponse response;
	private Formulario formulario;
	private ParceiroNegocio parceiroNegocio;
	private ParceiroLocalidade parceiroLocalidade;
	private ParceiroInfoBanco parceiroInfoBanco;
	private ParceiroBeneficio parceiroBeneficio;
	private List<Contrato> contratos;
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;
	private Perfil perfil;
	private Workflow workflow;
	private Collection<Etapa> etapas;
	private Collection<Etapa> motivos;

	public FormularioController(Result result, UsuarioInfo usuarioInfo,ParceiroNegocioDao parceiroNegocioDao,FormularioDao formularioDao,ContratoDao contratoDao,
			TabelaDao tabelaDao,CoeficienteDao coeficienteDao,PnDao pnDao,HttpServletResponse response,TipoControleDao tipoControleDao,ParceiroInfoBancoDao parceiroInfoBancoDao,
			ParceiroBeneficioDao parceiroBeneficioDao,ParceiroLocalidadeDao parceiroLocalidadeDao,ParceiroNegocio parceiroNegocio,ParceiroLocalidade parceiroLocalidade,
			ParceiroInfoBanco parceiroInfoBanco,ParceiroBeneficio parceiroBeneficio,Formulario formulario,BancoDao bancoDao,ProdutoDao produtoDao,List<Contrato> contratos,
			WorkflowDao workflowDao, EtapaDao etapaDao,ControleFormularioDao controleFormularioDao,Empresa empresa,Organizacao organizacao,Usuario usuario,
			Perfil perfil,HistoricoControleFormularioDao historicoControleFormularioDao,Workflow workflow){		

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.response = response;
		this.parceiroNegocioDao = parceiroNegocioDao;
		this.parceiroBeneficio = parceiroBeneficio;
		this.parceiroBeneficioDao =  parceiroBeneficioDao;
		this.parceiroLocalidadeDao = parceiroLocalidadeDao;
		this.controleFormularioDao = controleFormularioDao;
		this.parceiroInfoBancoDao = parceiroInfoBancoDao;
		this.historicoControleFormularioDao = historicoControleFormularioDao;
		this.coeficienteDao = coeficienteDao;
		this.tipoControleDao = tipoControleDao;
		this.bancoDao = bancoDao;
		this.produtoDao = produtoDao;
		this.formulario = formulario;
		this.formularioDao = formularioDao;
		this.tabelaDao = tabelaDao;
		this.contratoDao = contratoDao;
		this.workflowDao = workflowDao;
		this.etapaDao = etapaDao;
		this.pnDao = pnDao;
		this.parceiroNegocio = parceiroNegocio;
		this.parceiroLocalidade = parceiroLocalidade;
		this.parceiroInfoBanco = parceiroInfoBanco;
		this.parceiroBeneficio = parceiroBeneficio;
		this.contratos = contratos;
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();
		this.usuario = usuarioInfo.getUsuario();
		this.perfil = usuarioInfo.getPerfil();
		this.workflow = workflow;

	}

	@Get
	@Path("/formulario/cadastro")
	public void cadastro(){

		Collection<Banco> bancos = this.bancoDao.buscaBancoByGrupo("Tomadores");
		Collection<Banco> recompraBancos = this.bancoDao.buscaBancoByGrupo("Comprados");

		this.formulario.setEmpresa(usuarioInfo.getEmpresa());
		this.formulario.setOrganizacao(usuarioInfo.getOrganizacao());
		this.formulario.setIsActive(true);
		this.formulario.setCreated(Calendar.getInstance());

		result.include("bancos",bancos);
		result.include("recompraBancos",recompraBancos);
		result.include("contratos",contratos);
		result.include("formulario",formulario);
		result.include("parceiroLocalidade",parceiroLocalidade);
		result.include("parceiroBeneficio",parceiroBeneficio);

		
	}
	
	@Get
 	@Path("/formulario/visualiza/{id}")
	public void visualiza(Long id){

		formulario = formularioDao.load(id);
		formulario.setContratos(this.contratoDao.buscaContratoByFormulario(formulario.getFormulario_id()));

		TipoControle tp = this.tipoControleDao.buscaTipoControleByNome("Pós Venda");

		ControleFormulario posvenda = controleFormularioDao.buscaControleByContratoTipoControle(formulario.getFormulario_id(), tp.getTipoControle_id());

		if(posvenda != null) {
			
			HistoricoControleFormulario historico = new HistoricoControleFormulario();
			Collection<HistoricoControleFormulario> historicos = new ArrayList<HistoricoControleFormulario>();

			historicos.addAll(this.historicoControleFormularioDao.buscaHistoricoByFormularioControle(formulario.getFormulario_id(),posvenda.getControleFormulario_id()));

			historico.setControleFormulario(posvenda);
			historico.setCreatedBy(usuario);
			historico.setEmpresa(empresa);
			historico.setOrganizacao(organizacao);
			historico.setPerfil(perfil);
			historico.setFormulario(formulario);
			
			workflow = this.workflowDao.buscaWorkflowPorNome(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Status Pós Venda");

			etapas = etapaDao.buscaEtapasByEmpresaOrganizacaoWorkflow(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), workflow.getWorkflow_id());
			posvenda.setWorkflow(workflow);

			workflow = this.workflowDao.buscaWorkflowPorNome(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Motivos Pós Venda");

			motivos = etapaDao.buscaEtapasByEmpresaOrganizacaoWorkflow(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), workflow.getWorkflow_id());

			posvenda.setWorkflowPendencia(workflow);


			result.include("historico",historico);
			result.include("historicos",historicos);
			result.include("posvenda",posvenda);
			result.include("etapas", etapas);
			result.include("motivos", motivos);

		}

		result.include("formulario",formulario);

	}
	
	@Post
	@Path("/formulario/cliente")
	public void cliente(String numeroBeneficio){

		ParceiroBeneficio pb = parceiroBeneficioDao.buscaParceiroBeneficioByNumeroBeneficio(numeroBeneficio);
		
		if(pb != null) {
			
			parceiroBeneficio.setParceiroBeneficio_id(pb.getParceiroBeneficio_id());
			parceiroBeneficio.setNumeroBeneficio(pb.getNumeroBeneficio());
			parceiroBeneficio.setParceiroNegocio(pb.getParceiroNegocio());

			parceiroNegocio = parceiroNegocioDao.load(parceiroBeneficio.getParceiroNegocio().getParceiroNegocio_id());
			parceiroInfoBanco = parceiroInfoBancoDao.buscaParceiroInfoBancoByParceiro(parceiroBeneficio.getParceiroNegocio().getParceiroNegocio_id());

			for(ParceiroLocalidade pl : parceiroLocalidadeDao.buscaParceiroLocalidades(parceiroNegocio.getParceiroNegocio_id())){

				if(pl.getTipoEndereco().getNome().equals("Assinatura")){
					parceiroLocalidade = pl;
				}

			}

			parceiroLocalidade.setParceiroNegocio(parceiroNegocio);
			formulario.setParceiroNegocio(parceiroNegocio);
			formulario.setParceiroBeneficio(parceiroBeneficio);
			formulario.setParceiroLocalidade(parceiroLocalidade);
			formulario.setParceiroInfoBanco(parceiroInfoBanco);

			result.redirectTo(this).cadastro();
			
		} else {

			result.redirectTo(ParceironegocioController.class).cadastro();

		}

		

	}
	
	@Post
	@Path("/formulario/limpar")
	public void limpar() {

		this.formulario.setFormulario_id(null);
		this.formulario.setParceiroNegocio(null);
		this.parceiroBeneficio.setParceiroBeneficio_id(null);
		this.parceiroBeneficio.setNumeroBeneficio(null);
		this.parceiroLocalidade.setParceiroLocalidade_id(null);
		this.parceiroLocalidade.setLocalidade(null);
		this.formulario.setContratos(new ArrayList<Contrato>());

		result.redirectTo(this).cadastro();
		result.nothing();

	}
	
	@Post
	@Path("/formulario/adicionaContrato")
	public void adicionaContrato(Contrato contrato) {

		contrato.setEmpresa(usuarioInfo.getEmpresa());
		contrato.setOrganizacao(usuarioInfo.getOrganizacao());
		contrato.setUsuario(usuarioInfo.getUsuario());
		contrato.setBanco(this.bancoDao.buscaBancoById(contrato.getBanco().getBanco_id()));
		contrato.setProduto(this.produtoDao.buscaProdutoById(contrato.getProduto().getProduto_id()));
		contrato.setCoeficiente(this.coeficienteDao.buscaCoeficienteById(contrato.getCoeficiente().getCoeficiente_id()));
		contrato.setTabela(this.tabelaDao.buscaTabelasByCoeficiente(contrato.getCoeficiente().getCoeficiente_id()));
		contrato.setNumeroBeneficio(this.formulario.getParceiroBeneficio().getNumeroBeneficio());

		contrato.setWorkflow(this.workflowDao.buscaWorkflowByEmpresaOrganizacaoProdutoBanco(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), 
				contrato.getProduto().getProduto_id(), contrato.getBanco().getBanco_id()));

		contrato.setEtapa(this.etapaDao.buscaEtapaByEmpresaOrganizacaoNome(empresa.getEmpresa_id(),organizacao.getOrganizacao_id(),"Aguardando Status"));

		if(contrato.getRecompraBanco().getBanco_id() != null){
			contrato.setRecompraBanco(this.bancoDao.buscaBancoById(contrato.getRecompraBanco().getBanco_id()));
		}

		contrato.setIsActive(true);

		formulario.adicionaContrato(contrato);
		result.redirectTo(FormularioController.class).cadastro();

	}
	
	@Post
	@Path("/formulario/salva")
	public void salva() {

		this.formularioDao.beginTransaction();
		this.formularioDao.adiciona(this.formulario);
		this.formularioDao.commit();

		for(Contrato c : this.formulario.getContratos()){

			c.setFormulario(this.formulario);
			c.setRecompraBanco(c.getRecompraBanco().getBanco_id() == null ? null : c.getRecompraBanco());

			this.contratoDao.beginTransaction();
			this.contratoDao.atualiza(c);
			this.contratoDao.commit();

		}

		result.redirectTo(this).cadastro();

	}

	@Get
 	@Path("/formulario/impressao/{id}")
	public void impressao(Long id) {

		String caminhoJasper = "////localhost//sistemas//tomcat7//webapps//sgo//WEB-INF//_repositorio//sgo//";
		String jasper = caminhoJasper + "solicitacao.jasper";

		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("caminhoJasperSolicitacao", jasper);

		JasperPrint impressao = null;

		List<Formulario> forms = new ArrayList<Formulario>();
		Formulario f = this.formularioDao.load(id);

		Collection<Contrato> contratos = this.contratoDao.buscaContratoByFormulario(f.getFormulario_id());

		ParceiroNegocio parceiro =  f.getParceiroNegocio();
		Calendar formularioData = f.getCreated();
		Integer countContratos = new Integer(0);
		Integer countRecompraINSS = new Integer(0);
		Integer countMargemLimpa = new Integer(0);
		Integer countRecompraRMC = new Integer(0);
		Integer countRefinanciamento = new Integer(0);
		String cc = "";

		for (Iterator<Contrato> it  = contratos.iterator(); it.hasNext();) {

			Formulario formulario = new Formulario();
			Contrato c = (Contrato) it.next();

			cc = pnDao.buscaDetalhamento(c.getNumeroBeneficio()).getContacorrente() == null ? "0" : pnDao.buscaDetalhamento(c.getNumeroBeneficio()).getContacorrente();

			formulario.setCreated(formularioData);
			formulario.setParceiroNegocio(parceiro);
			formulario.setContratos(separaContrato(c));
			
			ParceiroBeneficio pb = parceiroBeneficioDao.buscaParceiroBeneficioByNumeroBeneficio(c.getNumeroBeneficio());

			parceiroBeneficio.setParceiroBeneficio_id(pb.getParceiroBeneficio_id());
			parceiroBeneficio.setNumeroBeneficio(pb.getNumeroBeneficio());
			parceiroBeneficio.setParceiroNegocio(pb.getParceiroNegocio());

			parceiroNegocio = parceiroNegocioDao.load(parceiroBeneficio.getParceiroNegocio().getParceiroNegocio_id());

			for(ParceiroLocalidade pl : parceiroLocalidadeDao.buscaParceiroLocalidades(parceiroNegocio.getParceiroNegocio_id())){

				if(pl.getTipoEndereco().getNome().equals("Assinatura")){
					parceiroLocalidade = pl;
				}

			}
			
			formulario.setParceiroBeneficio(parceiroBeneficio);
			formulario.setParceiroLocalidade(parceiroLocalidade);
			formulario.setParceiroLocalidade(parceiroLocalidade);
			formulario.setParceiroInfoBanco(parceiroInfoBanco);

			forms.add(formulario);
			
			countContratos +=1;

			if(c.getProduto().equals("MARGEM LIMPA") || c.getProduto().equals("AUMENTO"))
				countMargemLimpa += 1;
			if(c.getProduto().equals("RECOMPRA INSS"))
				countRecompraINSS += 1;
			if(c.getProduto().equals("RECOMPRA RMC"))
				countRecompraRMC += 1;
			if(c.getProduto().equals("REFINANCIAMENTO"))
				countRefinanciamento += 1;

		}
		
		parametros.put("countContratos", countContratos);
		parametros.put("countMargemLimpa", countMargemLimpa);
		parametros.put("countRecompraINSS", countRecompraINSS);
		parametros.put("countRecompraRMC", countRecompraRMC);
		parametros.put("countRefinanciamento", countRefinanciamento);
		parametros.put("detalhamentoCC", cc);
		
		try{
			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/pdf");
	
			ServletOutputStream responseOutputStream = response.getOutputStream();
	
			FormularioDataSource formularioDataSource;
			formularioDataSource = new FormularioDataSource(forms);
	
			impressao = JasperFillManager.fillReport(jasper, parametros , formularioDataSource);

			JasperExportManager.exportReportToPdfStream(impressao, responseOutputStream);
	
			responseOutputStream.flush();
			responseOutputStream.close();

		} catch(IOException e) {
			System.out.println("Erro:" + e);
		} catch(JRException e) {
			e.printStackTrace();
			System.out.println("Erro:" + e.getMessage());
		}

		result.nothing();

	}
	
	public static Collection<Contrato> separaContrato(Contrato contrato) {

		Collection<Contrato> contratos = new ArrayList<Contrato>();
		contratos.add(contrato);

		return contratos;
	}

}