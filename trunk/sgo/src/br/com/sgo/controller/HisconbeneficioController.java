package br.com.sgo.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.com.sgo.dao.HisconBeneficioDao;
import br.com.sgo.dao.ParceiroBeneficioDao;
import br.com.sgo.dao.WorkflowDao;
import br.com.sgo.dao.WorkflowEtapaDao;
import br.com.sgo.infra.CustomFileUtil;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.HisconBeneficio;
import br.com.sgo.modelo.ParceiroBeneficio;
import br.com.sgo.modelo.WorkflowEtapa;

@Resource
public class HisconbeneficioController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final HisconBeneficioDao hisconBeneficioDao;
	private final ParceiroBeneficioDao parceiroBeneficioDao;
	private final WorkflowDao workflowDao;
	private final WorkflowEtapaDao workflowEtapaDao;

	private HisconBeneficio hisconBeneficio;
	private Calendar dataAtual = Calendar.getInstance();
	private Collection<HisconBeneficio> hiscons;
	private HttpServletResponse response;

	public HisconbeneficioController(Result result, UsuarioInfo usuarioInfo, HisconBeneficioDao hisconBeneficioDao, 
			ParceiroBeneficioDao parceiroBeneficioDao, WorkflowDao workflowDao, WorkflowEtapaDao workflowEtapaDao,HisconBeneficio hisconBeneficio,HttpServletResponse response) {

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.hisconBeneficioDao = hisconBeneficioDao;
		this.parceiroBeneficioDao = parceiroBeneficioDao;
		this.workflowDao = workflowDao;
		this.workflowEtapaDao = workflowEtapaDao;
		this.hisconBeneficio = hisconBeneficio;
		this.response = response;

	}

	@Get
	@Path("/hisconbeneficio/cadastro")
	public void cadastro() {

		this.hisconBeneficio = new HisconBeneficio();

		Collection<HisconBeneficio> hisconsAuxiliar = new ArrayList<HisconBeneficio>();

		WorkflowEtapa etapaAguardandoAdm = this.workflowEtapaDao.buscaWorkflowEtapaByNome(
				usuarioInfo.getEmpresa().getEmpresa_id(), 
				usuarioInfo.getOrganizacao().getOrganizacao_id(), 
				"Aguardando Adm");

		if(usuarioInfo.getPerfil().getNome().equals("Administrativo")){

			hisconsAuxiliar =
					this.hisconBeneficioDao.buscaHisconsToUpload(usuarioInfo.getEmpresa().getEmpresa_id(), 
							usuarioInfo.getOrganizacao().getOrganizacao_id(),etapaAguardandoAdm.getWorkflowEtapa_id());

		} else {

			hisconsAuxiliar = 
					this.hisconBeneficioDao.buscaHisconBeneficiosByUsuarioPerfil(
								usuarioInfo.getEmpresa(), 
								usuarioInfo.getOrganizacao(),
								usuarioInfo.getUsuario());
			
		}
		

		hiscons  = new ArrayList<HisconBeneficio>();

		for (HisconBeneficio h : hisconsAuxiliar){

			h.setCountHiscons(this.hisconBeneficioDao.buscaCountHisconsBeneficios(usuarioInfo.getEmpresa().getEmpresa_id(),usuarioInfo.getOrganizacao().getOrganizacao_id(),h.getParceiroBeneficio().getParceiroBeneficio_id()));

			h.setWorkflowEtapas(workflowEtapaDao.buscaWorKFlowEtapaByHisconPerfil(
						usuarioInfo.getEmpresa().getEmpresa_id(),
						usuarioInfo.getOrganizacao().getOrganizacao_id(),
						usuarioInfo.getPerfil().getPerfil_id(),
						h.getHisconBeneficio_id()));

			h.getWorkflowEtapas().add(h.getWorkflowEtapa());

			hiscons.add(h);

		} 

		result.include("hiscons", hiscons);

	}

	@Post
	@Path("/hisconbeneficio/cadastro")
	public void cadastro(Long empresa_id, Long organizacao_id, String numeroBeneficio) {

		String mensagem = "";
		
		ParceiroBeneficio pb = this.parceiroBeneficioDao.buscaParceiroBeneficioPorNumeroBeneficio(empresa_id, organizacao_id, numeroBeneficio);

			if (pb != null){

				HisconBeneficio hb = this.hisconBeneficioDao.buscaHisconBeneficioByParceiroBeneficio(pb.getEmpresa().getEmpresa_id(),
						pb.getOrganizacao().getOrganizacao_id(),pb.getParceiroBeneficio_id());

				if (hb != null){

					if (hb.getIsEnviado()){

						this.hisconBeneficio.setEmpresa(pb.getEmpresa());
						this.hisconBeneficio.setOrganizacao(pb.getOrganizacao());
						this.hisconBeneficio.setParceiroBeneficio(pb);
						this.hisconBeneficio.setUsuario(usuarioInfo.getUsuario());
	
						result.include("hisconBeneficio", hisconBeneficio);

					} else {

						mensagem = "Erro: Hiscon em aberto solicitado por " + hb.getUsuario().getNome() + " em ";
						result.include("notice", mensagem);

					}

				}else {

					this.hisconBeneficio.setEmpresa(pb.getEmpresa());
					this.hisconBeneficio.setOrganizacao(pb.getOrganizacao());
					this.hisconBeneficio.setParceiroBeneficio(pb);
					this.hisconBeneficio.setUsuario(usuarioInfo.getUsuario());

					result.include("hisconBeneficio", hisconBeneficio);

				}

			} else {

				mensagem = "Erro: Beneficio não cadastrado.";

			}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Post
	@Path("/hisconbeneficio/salva")
	public void salva() {

		String mensagem = "";

		this.hisconBeneficio.setCreated(dataAtual);
		this.hisconBeneficio.setUpdated(dataAtual);

		this.hisconBeneficio.setCreatedBy(usuarioInfo.getUsuario());
		this.hisconBeneficio.setUpdatedBy(usuarioInfo.getUsuario());
		this.hisconBeneficio.setPerfil(usuarioInfo.getPerfil());

		this.hisconBeneficio.setIsActive(true);
		this.hisconBeneficio.setIsWorkflow(true);
		this.hisconBeneficio.setIsEnviado(false);
		this.hisconBeneficio.setIsImportado(false);
		this.hisconBeneficio.setIsPadrao(false);

		this.hisconBeneficio.setWorkflow(this.workflowDao.load(2L));
		this.hisconBeneficio.setWorkflowEtapa(this.workflowEtapaDao.load(22L));

		this.hisconBeneficio.setWorkflowPosicao(this.workflowDao.load(3L));
		this.hisconBeneficio.setWorkflowPosicaoEtapa(this.workflowEtapaDao.load(27L));

		try {

			this.hisconBeneficioDao.beginTransaction();
			this.hisconBeneficioDao.adiciona(hisconBeneficio);
			this.hisconBeneficioDao.commit();

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar o workflow.";

		} finally{

			this.hisconBeneficioDao.clear();
			this.hisconBeneficioDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Post
	@Path("/hisconbeneficio/altera")
	public void altera(HisconBeneficio hisconBeneficio) {

		this.hisconBeneficio = this.hisconBeneficioDao.buscaHisconBeneficioById(hisconBeneficio.getHisconBeneficio_id());

		if(hisconBeneficio.getWorkflowEtapa() != null){

			hisconBeneficio.setWorkflowEtapa(this.workflowEtapaDao.buscaWorkflowEtapaById(hisconBeneficio.getWorkflowEtapa().getWorkflowEtapa_id()));

			if(!this.hisconBeneficio.getWorkflowEtapa().getNome().equals("Aguardando Adm") && !this.hisconBeneficio.getWorkflowEtapa().getNome().equals("Enviado"))
				this.hisconBeneficio.setDataAdm(hisconBeneficio.getWorkflowEtapa().getNome().equals("Aguardando Adm") ? GregorianCalendar.getInstance() : null);

			this.hisconBeneficio.setWorkflowEtapa(hisconBeneficio.getWorkflowEtapa());
			this.hisconBeneficio.setIsEnviado(hisconBeneficio.getWorkflowEtapa().getNome().equals("Enviado") || hisconBeneficio.getWorkflowEtapa().getNome().equals("Desconsiderado") ? true : false);

		}

		if (hisconBeneficio.getWorkflowPosicaoEtapa() != null)
			this.hisconBeneficio.setWorkflowPosicaoEtapa(hisconBeneficio.getWorkflowPosicaoEtapa() == null ? null : hisconBeneficio.getWorkflowPosicaoEtapa());

		if(hisconBeneficio.getUsuario() != null)
			this.hisconBeneficio.setUsuario(hisconBeneficio.getUsuario());

		if(this.hisconBeneficio.getDataEnvio() == null)
			this.hisconBeneficio.setDataEnvio(this.hisconBeneficio.getIsEnviado() == false ? null :GregorianCalendar.getInstance());

		if(hisconBeneficio.getCaminhoArquivo() != null){
			this.hisconBeneficio.setCaminhoArquivo(hisconBeneficio.getCaminhoArquivo());
		}
		
		if(hisconBeneficio.getIsEnviado()){

			WorkflowEtapa etapaEnviado = this.workflowEtapaDao.buscaWorkflowEtapaByNome(
					usuarioInfo.getEmpresa().getEmpresa_id(), 
					usuarioInfo.getOrganizacao().getOrganizacao_id(), 
					"Enviado");

			this.hisconBeneficio.setWorkflowEtapa(etapaEnviado);

			this.hisconBeneficio.setIsEnviado(true);

		}

		this.hisconBeneficio.setIsActive(true);

		hisconBeneficioDao.beginTransaction();
		hisconBeneficioDao.atualiza(this.hisconBeneficio);
		hisconBeneficioDao.commit();

		result.nothing();

	}

	@Get
	public void msg() {

	}

	@Post
	@Path("/uploadHiscon")
	public void uploadHiscon(UploadedFile zip) {		

		Date now = new Date();
		String hora = new SimpleDateFormat("ddMMyyyyHHmm").format(now);

		Collection<File> files = new ArrayList<File>();

		if ( zip != null ) {

			String diretorio = "////localhost//sistemas//_repositorio//hiscon//";

			String nomeFile = diretorio + zip.getFileName();

			try {

				IOUtils.copy(zip.getFile(), new FileOutputStream(new File(nomeFile)));

				CustomFileUtil.extraiZip(new File(nomeFile),new File(diretorio));

				WorkflowEtapa etapaAguardandoAdm = this.workflowEtapaDao.buscaWorkflowEtapaByNome(
						usuarioInfo.getEmpresa().getEmpresa_id(), 
						usuarioInfo.getOrganizacao().getOrganizacao_id(), 
						"Aguardando Adm");

				hiscons = this.hisconBeneficioDao.buscaHisconsToUpload(usuarioInfo.getEmpresa().getEmpresa_id(), 
																		usuarioInfo.getOrganizacao().getOrganizacao_id(),
																		etapaAguardandoAdm.getWorkflowEtapa_id());

				for (HisconBeneficio h : hiscons){

					System.gc();

					File f = new File(diretorio + h.getParceiroBeneficio().getNumeroBeneficio() + ".pdf");

					if( f.exists() ) {

						String caminhoImagemAux = diretorio + h.getParceiroBeneficio().getNumeroBeneficio() + "_" + hora + ".pdf";

						File caminhoImagem = new File(caminhoImagemAux);

						FileUtils.copyFile(f, caminhoImagem);

						h.setCaminhoArquivo(caminhoImagem.getName());
						h.setIsEnviado(true);

						altera(h);
						files.add(f);

					}
				}

			} catch (FileNotFoundException e) {

				e.printStackTrace();

			} catch (IOException e) {

				e.printStackTrace();

			} finally {

				for(File f : files){

					CustomFileUtil.deleteFile(f);

				}

			}
		}

		result.redirectTo(this).cadastro();

	}
	
	@Get
	@Path("/visualizaHiscon/{id}")
	public void visualiza(Long id) {

		hisconBeneficio = this.hisconBeneficioDao.buscaHisconBeneficioById(id);

		String diretorio = "////localhost//sistemas//_repositorio//hiscon//";
		String nomeFile = diretorio + hisconBeneficio.getCaminhoArquivo();

		File pdf = new File(nomeFile);

		try {

            byte[] arquivo = null;

            File dir = new File(pdf.getPath());

            try {
                arquivo = CustomFileUtil.fileToByte(dir);
            } catch (Exception e) {
                e.printStackTrace();
            }

            response.reset();
            response.setContentType("application/pdf");
            response.setDateHeader("Expires", 0);
            response.setContentLength(arquivo.length);
            response.getOutputStream().write(arquivo, 0, arquivo.length);

            ServletOutputStream responseOutputStream = response.getOutputStream();

            responseOutputStream.flush();
			responseOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

		result.nothing();
	}

}