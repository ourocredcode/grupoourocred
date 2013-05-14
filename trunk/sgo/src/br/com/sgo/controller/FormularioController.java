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
import br.com.sgo.dao.FormularioDao;
import br.com.sgo.dao.ParceiroBeneficioDao;
import br.com.sgo.dao.ParceiroLocalidadeDao;
import br.com.sgo.dao.ParceiroNegocioDao;
import br.com.sgo.dao.PnDao;
import br.com.sgo.dao.ProdutoDao;
import br.com.sgo.dao.TabelaDao;
import br.com.sgo.dao.WorkflowDao;
import br.com.sgo.dao.WorkflowEtapaDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.jasper.FormularioDataSource;
import br.com.sgo.modelo.Banco;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Formulario;
import br.com.sgo.modelo.ParceiroBeneficio;
import br.com.sgo.modelo.ParceiroInfoBanco;
import br.com.sgo.modelo.ParceiroLocalidade;
import br.com.sgo.modelo.ParceiroNegocio;

@Resource
public class FormularioController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final ParceiroNegocioDao parceiroNegocioDao;
	private final ParceiroBeneficioDao parceiroBeneficioDao;
	private final ParceiroLocalidadeDao parceiroLocalidadeDao;
	private final BancoDao bancoDao;
	private final ProdutoDao produtoDao;
	private final TabelaDao tabelaDao;
	private final FormularioDao formularioDao;
	private final ContratoDao contratoDao;
	private final CoeficienteDao coeficienteDao;
	private final WorkflowDao workflowDao;
	private final WorkflowEtapaDao workflowEtapaDao;
	private final PnDao pnDao;

	private HttpServletResponse response;
	private Formulario formulario;
	private ParceiroNegocio parceiroNegocio;
	private ParceiroLocalidade parceiroLocalidade;
	private ParceiroInfoBanco parceiroInfoBanco;
	private ParceiroBeneficio parceiroBeneficio;
	private List<Contrato> contratos;

	public FormularioController(Result result, UsuarioInfo usuarioInfo,ParceiroNegocioDao parceiroNegocioDao,FormularioDao formularioDao,ContratoDao contratoDao,
			TabelaDao tabelaDao,CoeficienteDao coeficienteDao,PnDao pnDao,HttpServletResponse response,
			ParceiroBeneficioDao parceiroBeneficioDao,ParceiroLocalidadeDao parceiroLocalidadeDao,ParceiroNegocio parceiroNegocio,ParceiroLocalidade parceiroLocalidade,
			ParceiroInfoBanco parceiroInfoBanco,ParceiroBeneficio parceiroBeneficio,Formulario formulario,BancoDao bancoDao,ProdutoDao produtoDao,List<Contrato> contratos,
			WorkflowDao workflowDao, WorkflowEtapaDao workflowEtapaDao){		

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.response = response;
		this.parceiroNegocioDao = parceiroNegocioDao;
		this.parceiroBeneficio = parceiroBeneficio;
		this.parceiroBeneficioDao =  parceiroBeneficioDao;
		this.parceiroLocalidadeDao = parceiroLocalidadeDao;
		this.coeficienteDao = coeficienteDao;
		this.bancoDao = bancoDao;
		this.produtoDao = produtoDao;
		this.formulario = formulario;
		this.formularioDao = formularioDao;
		this.tabelaDao = tabelaDao;
		this.contratoDao = contratoDao;
		this.workflowDao = workflowDao;
		this.workflowEtapaDao = workflowEtapaDao;
		this.pnDao = pnDao;
		this.parceiroNegocio = parceiroNegocio;
		this.parceiroLocalidade = parceiroLocalidade;
		this.parceiroInfoBanco = parceiroInfoBanco;
		this.parceiroBeneficio = parceiroBeneficio;
		this.contratos = contratos;

	}

	@Get
	@Path("/formulario/cadastro")
	public void cadastro(){

		Collection<Banco> bancos = this.bancoDao.buscaBancoByGrupo("Tomadores");
		Collection<Banco> bancosRecompra = this.bancoDao.buscaBancoByGrupo("Comprados");

		this.formulario.setEmpresa(usuarioInfo.getEmpresa());
		this.formulario.setOrganizacao(usuarioInfo.getOrganizacao());
		this.formulario.setIsActive(true);
		this.formulario.setCreated(Calendar.getInstance());

		result.include("bancos",bancos);
		result.include("bancosRecompra",bancosRecompra);
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

		result.include("formulario",formulario);

	}
	
	@Post
	@Path("/formulario/cliente")
	public void cliente(String numeroBeneficio){

		ParceiroBeneficio pb = parceiroBeneficioDao.buscaParceiroBeneficioByNumeroBeneficio(numeroBeneficio);

		parceiroBeneficio.setParceiroBeneficio_id(pb.getParceiroBeneficio_id());
		parceiroBeneficio.setNumeroBeneficio(pb.getNumeroBeneficio());
		parceiroBeneficio.setParceiroNegocio(pb.getParceiroNegocio());

		parceiroNegocio = parceiroNegocioDao.load(parceiroBeneficio.getParceiroNegocio().getParceiroNegocio_id());

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

		contrato.setWorkflow(this.workflowDao.buscaWorkflowPorEmpresaOrganizacaoTipoworflowNome(usuarioInfo.getEmpresa().getEmpresa_id(),
				usuarioInfo.getOrganizacao().getOrganizacao_id(),1L,"Status Contrato"));
		contrato.setWorkflowEtapa(this.workflowEtapaDao.buscaWorkflowEtapaByNome(usuarioInfo.getEmpresa().getEmpresa_id(),
				usuarioInfo.getOrganizacao().getOrganizacao_id(),"Aguardando Status"));

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