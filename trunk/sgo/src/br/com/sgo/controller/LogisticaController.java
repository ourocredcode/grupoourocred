package br.com.sgo.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
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
import br.com.sgo.dao.ContratoDao;
import br.com.sgo.dao.EtapaDao;
import br.com.sgo.dao.FormularioDao;
import br.com.sgo.dao.HistoricoContratoDao;
import br.com.sgo.dao.LogisticaDao;
import br.com.sgo.dao.ParceiroLocalidadeDao;
import br.com.sgo.dao.ParceiroNegocioDao;
import br.com.sgo.dao.ProdutoDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.jasper.CheckListDataSource;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Etapa;
import br.com.sgo.modelo.Formulario;
import br.com.sgo.modelo.HistoricoContrato;
import br.com.sgo.modelo.Logistica;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.ParceiroLocalidade;
import br.com.sgo.modelo.ParceiroNegocio;
import br.com.sgo.modelo.Usuario;

@Resource
public class LogisticaController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final ContratoDao contratoDao;
	private final FormularioDao formularioDao;
	private final LogisticaDao logisticaDao;
	private final EtapaDao etapaDao;
	private final ParceiroNegocioDao parceiroNegocioDao;
	private final ParceiroLocalidadeDao parceiroLocalidadeDao;
	private final HistoricoContratoDao historicoContratoDao;
	
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;
	private ParceiroNegocio parceiroNegocio;
	private ParceiroLocalidade parceiroLocalidade;


	private HttpServletResponse response;

	public LogisticaController(Result result,BancoDao bancoDao,ProdutoDao produtoDao,ContratoDao contratoDao,FormularioDao formularioDao,UsuarioInfo usuarioInfo,
			LogisticaDao logisticaDao,HttpServletResponse response, ParceiroNegocioDao parceiroNegocioDao,ParceiroLocalidadeDao parceiroLocalidadeDao,EtapaDao etapaDao,
			Empresa empresa, Organizacao organizacao, Usuario usuario,HistoricoContratoDao historicoContratoDao){		

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.contratoDao = contratoDao;
		this.formularioDao = formularioDao;
		this.logisticaDao = logisticaDao;
		this.etapaDao = etapaDao;
		this.response = response;
		this.parceiroNegocioDao = parceiroNegocioDao;
		this.parceiroLocalidadeDao = parceiroLocalidadeDao;
		this.historicoContratoDao = historicoContratoDao;
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();
		this.usuario = usuarioInfo.getUsuario();

	}

	@Post
	@Path("/logistica/cadastro")
	public void cadastro(){

	}

	@Post
	@Path("/logistica/salva")
	public void salva(Logistica logistica, Long[] contrato_ids) {
		
		List<String> log = new ArrayList<String>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		for(Long id : contrato_ids){

			Logistica l = new Logistica();
			Contrato c = this.contratoDao.load(id);

			l.setEmpresa(usuarioInfo.getEmpresa());
			l.setOrganizacao(usuarioInfo.getOrganizacao());
			l.setIsActive(true);
			l.setCreatedBy(usuarioInfo.getUsuario());
			l.setCreated(GregorianCalendar.getInstance());

			l.setContrato(c);
			l.setDataAssinatura(logistica.getDataAssinatura());
			l.setTipoLogistica(logistica.getTipoLogistica());
			l.setPeriodo(logistica.getPeriodo());

			log.add(" Data de Assinatura alterada para : " + dateFormat.format(l.getDataAssinatura().getTime()));

			logisticaDao.beginTransaction();
			logisticaDao.adiciona(l);
			logisticaDao.commit();
			
			
			if(c.getEtapa().getNome().equals("Aguardando Status")){

				Etapa e = this.etapaDao.buscaEtapaByEmpresaOrganizacaoNomeExato(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(),"Aguardando Qualidade");

				log.add("Status alterado de : " +c.getEtapa().getNome() + " para : " + e.getNome());

				c.setEtapa(e);

				contratoDao.beginTransaction();
				contratoDao.atualiza(c);
				contratoDao.commit();

			}

			for(String lo : log){

				HistoricoContrato historico = new HistoricoContrato();
				historico.setEmpresa(empresa);
				historico.setOrganizacao(organizacao);
				historico.setIsActive(true);
				historico.setCreatedBy(usuario);
				historico.setCreated(GregorianCalendar.getInstance());
				historico.setObservacao(lo);
				historico.setContrato(c);

				this.historicoContratoDao.beginTransaction();
				this.historicoContratoDao.adiciona(historico);
				this.historicoContratoDao.commit();

			}

		}

		result.redirectTo(ContratoController.class).status(logistica.getContrato().getContrato_id());

	}
	
	@Get
 	@Path("/logistica/checklist/{id}")
	public void checklist(Long id) {

		String caminhoJasper = "////localhost//sistemas//tomcat7//webapps//sgo//WEB-INF//_repositorio//sgo//";
		String jasper = caminhoJasper + "checklist.jasper";

		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("caminhoJasperSolicitacao", jasper);

		JasperPrint impressao = null;

		List<Formulario> forms = new ArrayList<Formulario>();
		Formulario form = this.formularioDao.load(id);

		Collection<Contrato> contratos = this.contratoDao.buscaContratoToCheckList(form.getFormulario_id());

		form.setContratos(contratos);

		parceiroNegocio = parceiroNegocioDao.buscaParceiroNegocioById(form.getParceiroNegocio().getParceiroNegocio_id());

		for(ParceiroLocalidade pl : parceiroLocalidadeDao.buscaParceiroLocalidades(parceiroNegocio.getParceiroNegocio_id())){

			if(pl.getTipoEndereco().getNome().equals("Assinatura")){
				parceiroLocalidade = pl;
			}

		}

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