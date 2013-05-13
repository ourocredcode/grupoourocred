package br.com.sgo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
import br.com.sgo.dao.LogisticaDao;
import br.com.sgo.dao.ParceiroLocalidadeDao;
import br.com.sgo.dao.ParceiroNegocioDao;
import br.com.sgo.dao.ProdutoBancoDao;
import br.com.sgo.dao.ProdutoDao;
import br.com.sgo.dao.TabelaDao;
import br.com.sgo.dao.WorkflowEtapaDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.jasper.CheckListDataSource;
import br.com.sgo.modelo.Banco;
import br.com.sgo.modelo.Coeficiente;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Formulario;
import br.com.sgo.modelo.Logistica;
import br.com.sgo.modelo.ParceiroLocalidade;
import br.com.sgo.modelo.ParceiroNegocio;
import br.com.sgo.modelo.Produto;
import br.com.sgo.modelo.WorkflowEtapa;

@Resource
public class LogisticaController {

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
	private final LogisticaDao logisticaDao;
	private final ParceiroNegocioDao parceiroNegocioDao;
	private final ParceiroLocalidadeDao parceiroLocalidadeDao;

	private Contrato contrato;
	private Formulario formulario;
	private ParceiroNegocio parceiroNegocio;
	private ParceiroLocalidade parceiroLocalidade;
	private Collection<Banco> bancos;
	private Collection<Produto> produtos;
	private Collection<Coeficiente> coeficientes;
	private Collection<WorkflowEtapa> etapas;
	private HttpServletResponse response;

	public LogisticaController(Result result,BancoDao bancoDao,ProdutoBancoDao produtoBancoDao,ProdutoDao produtoDao,CoeficienteDao coeficienteDao,Contrato contrato,
			Formulario formulario,TabelaDao tabelaDao,ContratoDao contratoDao,FormularioDao formularioDao,WorkflowEtapaDao workFlowetapaDao,UsuarioInfo usuarioInfo,
			LogisticaDao logisticaDao,HttpServletResponse response, ParceiroNegocioDao parceiroNegocioDao,ParceiroLocalidadeDao parceiroLocalidadeDao){		

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
		this.logisticaDao = logisticaDao;
		this.workFlowetapaDao = workFlowetapaDao;
		this.response = response;
		this.parceiroNegocioDao = parceiroNegocioDao;
		this.parceiroLocalidadeDao = parceiroLocalidadeDao;

	}

	@Post
	@Path("/logistica/cadastro")
	public void cadastro(){

	}

	@Post
	@Path("/logistica/salva")
	public void salva(Logistica logistica, Long[] contrato_ids) {

		for(Long id : contrato_ids){

			Contrato c = this.contratoDao.buscaContratoById(id);

			logistica.setEmpresa(usuarioInfo.getEmpresa());
			logistica.setOrganizacao(usuarioInfo.getOrganizacao());
			logistica.setIsActive(true);
			logistica.setContrato(c);

			logisticaDao.beginTransaction();
			logisticaDao.adiciona(logistica);
			logisticaDao.commit();

		}

		result.redirectTo(ContratoController.class).status(logistica.getContrato().getContrato_id());

	}
	
	@Get
 	@Path("/checklist/{id}")
	public void checklist(Long id) {

		String caminhoJasper = "////localhost//sistemas//tomcat7//webapps//sgo//WEB-INF//_repositorio//sgo//";
		String jasper = caminhoJasper + "checklist.jasper";

		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("caminhoJasperSolicitacao", jasper);

		JasperPrint impressao = null;

		List<Formulario> forms = new ArrayList<Formulario>();
		Formulario form = this.formularioDao.load(id);

		Collection<Contrato> contratos = this.contratoDao.buscaContratoByFormulario(form.getFormulario_id());
		form.setContratos(contratos);

		parceiroNegocio = parceiroNegocioDao.buscaParceiroNegocioById(form.getParceiroNegocio().getParceiroNegocio_id());

		for(ParceiroLocalidade pl : parceiroLocalidadeDao.buscaParceiroLocalidades(parceiroNegocio.getParceiroNegocio_id())){

			if(pl.getTipoEndereco().getNome().equals("Assinatura")){
				parceiroLocalidade = pl;
			}

		}

		form.setParceiroLocalidade(parceiroLocalidade);
		form.setParceiroLocalidade(parceiroLocalidade);

		forms.add(form);

		try{

			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/pdf");

			ServletOutputStream responseOutputStream = response.getOutputStream();

			CheckListDataSource checklistDataSource;
			checklistDataSource = new CheckListDataSource(forms);

			impressao = JasperFillManager.fillReport(jasper, parametros , checklistDataSource);

			JasperExportManager.exportReportToPdfStream(impressao, responseOutputStream);

			responseOutputStream.flush();
			responseOutputStream.close();

		}catch(IOException e){
			System.out.println("Erro:" + e);
		}catch(JRException e){
			e.printStackTrace();
			System.out.println("Erro:" + e.getMessage());
		}

		result.nothing();

	}

}