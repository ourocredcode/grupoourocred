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

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.HisconBeneficioDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.ParceiroBeneficioDao;
import br.com.sgo.dao.PerfilDao;
import br.com.sgo.dao.UsuarioDao;
import br.com.sgo.dao.UsuarioPerfilDao;
import br.com.sgo.dao.WorkflowDao;
import br.com.sgo.dao.WorkflowEtapaDao;
import br.com.sgo.infra.CustomFileUtil;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.HisconBeneficio;
import br.com.sgo.modelo.ParceiroBeneficio;
import br.com.sgo.modelo.WorkflowEtapa;

@Resource
public class HisconbeneficioController {

	private final Result result;
	private final Validator validator;
	private final UsuarioInfo usuarioInfo;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final PerfilDao perfilDao;
	private final HisconBeneficioDao hisconBeneficioDao;
	private final ParceiroBeneficioDao parceiroBeneficioDao;
	private final UsuarioDao usuarioDao;
	private final WorkflowDao workflowDao;
	private final WorkflowEtapaDao workflowEtapaDao;
	private final UsuarioPerfilDao usuarioPerfilDao;

	private HisconBeneficio hisconBeneficio;
	private Calendar dataAtual = Calendar.getInstance();
	private Collection<HisconBeneficio> hiscons;
	private Collection<WorkflowEtapa> etapas;
	private Collection<WorkflowEtapa> etapasHiscon;
	
	public HisconbeneficioController(Result result, Validator validator, UsuarioInfo usuarioInfo, HisconBeneficioDao hisconBeneficioDao, EmpresaDao empresaDao,
			OrganizacaoDao organizacaoDao, ParceiroBeneficioDao parceiroBeneficioDao, UsuarioDao usuarioDao, WorkflowDao workflowDao, WorkflowEtapaDao workflowEtapaDao,
			UsuarioPerfilDao usuarioPerfilDao, PerfilDao perfilDao, HisconBeneficio hisconBeneficio) {

		this.result = result;
		this.validator = validator;
		this.usuarioInfo = usuarioInfo;
		this.hisconBeneficioDao = hisconBeneficioDao;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.parceiroBeneficioDao = parceiroBeneficioDao;
		this.usuarioDao = usuarioDao;
		this.workflowDao = workflowDao;
		this.workflowEtapaDao = workflowEtapaDao;
		this.usuarioPerfilDao = usuarioPerfilDao;
		this.perfilDao = perfilDao;
		this.hisconBeneficio = hisconBeneficio;

	}

	@Get
	@Path("/hisconbeneficio/cadastro")
	public void cadastro() {

		Collection<HisconBeneficio> hisconsAuxiliar = this.hisconBeneficioDao.mostraHisconBeneficiosPorUsuarioPerfil(usuarioInfo.getUsuario().getEmpresa().getEmpresa_id(), usuarioInfo.getUsuario().getOrganizacao().getOrganizacao_id(),usuarioInfo.getPerfil().getPerfil_id(), usuarioInfo.getUsuario().getUsuario_id());

		hiscons  = new ArrayList<HisconBeneficio>();

		//etapasHiscon = workflowEtapaDao.buscaWorKFlowEtapaByPerfil(usuarioInfo.getUsuario().getEmpresa().getEmpresa_id(),usuarioInfo.getUsuario().getOrganizacao().getOrganizacao_id(), usuarioInfo.getPerfil().getPerfil_id());
		
		for (HisconBeneficio h : hisconsAuxiliar){

			h.setCountHiscons(this.hisconBeneficioDao.mostraCountHisconsBeneficios(usuarioInfo.getUsuario().getEmpresa().getEmpresa_id(),usuarioInfo.getUsuario().getOrganizacao().getOrganizacao_id(),h.getParceiroBeneficio().getParceiroBeneficio_id()));

			etapas = workflowEtapaDao.buscaWorKFlowEtapaByHisconPerfil(h.getHisconBeneficio_id(), usuarioInfo.getUsuario().getEmpresa().getEmpresa_id(),usuarioInfo.getUsuario().getOrganizacao().getOrganizacao_id(), usuarioInfo.getPerfil().getPerfil_id());
			
			etapasHiscon = workflowEtapaDao.buscaWorKFlowEtapaByPerfil(usuarioInfo.getUsuario().getEmpresa().getEmpresa_id(),usuarioInfo.getUsuario().getOrganizacao().getOrganizacao_id(), usuarioInfo.getPerfil().getPerfil_id());

			etapas.add(h.getWorkflowEtapa());
			hiscons.add(h);

		} 

		//result.include("hisconBeneficio",hisconBeneficio);
		result.include("hiscons", hiscons);
		result.include("etapasHiscon",etapasHiscon);
		result.include("etapas",etapas);
	}

	@Post
	@Path("/hisconbeneficio/cadastro")
	public void cadastro(Long empresa_id, Long organizacao_id, String numeroBeneficio) {

		String mensagem = "";

		ParceiroBeneficio pb = this.parceiroBeneficioDao.buscaParceiroBeneficioPorNumeroBeneficio(empresa_id, organizacao_id, numeroBeneficio);

			if (pb != null){

				HisconBeneficio hb = this.hisconBeneficioDao.mostraHisconBeneficios(pb);

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
				//result.include("notice", mensagem).redirectTo(ParceironegocioController.class).cadastro();

			}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	/*@Post
	@Path("/hisconbeneficio/cadastroteste")
	public void cadastroTeste(Long empresa_id, Long organizacao_id, String numeroBeneficio) {

		String mensagem = "";

		ParceiroBeneficio pb = this.parceiroBeneficioDao.buscaParceiroBeneficioPorNumeroBeneficio(empresa_id, organizacao_id, numeroBeneficio);

			if (pb != null){

				HisconBeneficio hb = this.hisconBeneficioDao.mostraHisconBeneficios(pb);

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
				result.include("notice", mensagem).redirectTo(ParceironegocioController.class).cadastro();

			}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();
	}*/

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

		this.hisconBeneficio = this.hisconBeneficioDao.load(hisconBeneficio.getHisconBeneficio_id());

		this.hisconBeneficioDao.mostraHisconsToUpload(usuarioInfo.getEmpresa().getEmpresa_id(), usuarioInfo.getOrganizacao().getOrganizacao_id());

		Date now = new Date();
		String hora = new SimpleDateFormat("ddMMyyyyHHmm").format(now);

		String diretorio = "////localhost//sistemas//_repositorio//hiscon//";

		String caminhoArquivo = diretorio + hisconBeneficio.getParceiroBeneficio().getNumeroBeneficio() + "_" + hora + ".pdf";

		this.hisconBeneficio.setCaminhoArquivo(caminhoArquivo);
		this.hisconBeneficio.setDataAdm(dataAtual);

		this.hisconBeneficio.setWorkflow(this.workflowDao.load(1L));
		this.hisconBeneficio.setWorkflowEtapa(this.workflowEtapaDao.load(4L));		

		this.hisconBeneficio.setIsEnviado(true);
		this.hisconBeneficio.setIsImportado(true);

		hisconBeneficioDao.beginTransaction();
		hisconBeneficioDao.atualiza(this.hisconBeneficio);
		hisconBeneficioDao.commit();

		result.nothing();

	}

	@Get
	public void msg() {

	}

	@Get
	@Path("/hisconbeneficio/busca.json")
	@Public
	public void hisconbeneficio(Long empresa_id, Long organizacao_id) {
		// result.use(Results.json()).withoutRoot().from(agenciaDao.buscaAgenciaByEmOrBaCa(empresa_id, organizacao_id, banco_id, codigoagencia)).serialize();
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

				hiscons = this.hisconBeneficioDao.mostraHisconsToUpload(usuarioInfo.getEmpresa().getEmpresa_id(), usuarioInfo.getOrganizacao().getOrganizacao_id());

				for (HisconBeneficio h : hiscons){

					System.gc();

					File f = new File(diretorio + h.getParceiroBeneficio().getNumeroBeneficio() + ".pdf");

					if( f.exists() ) {

						String caminhoImagemAux = diretorio + h.getParceiroBeneficio().getNumeroBeneficio() + "_" + hora + ".pdf";

						File caminhoImagem = new File(caminhoImagemAux);

						FileUtils.copyFile(f, caminhoImagem);

						h.setCaminhoArquivo(caminhoImagem.getName());

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

	@Post
	@Path("/hisconbeneficio/lista")
	@Public
	public void lista(String cpf) {

		// result.include("hisconsBeneficio",this.hisconBeneficioDao.mostraHisconBeneficiosPorUsuarioPerfil(usuarioInfo.getEmpresa().getEmpresa_id()
		// usuarioInfo.getOrganizacao().getOrganizacao_id(), perfil_id,
		// usuarioInfo.getUsuario().getUsuario_id());
	}
}