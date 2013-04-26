package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
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
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.HisconBeneficio;
import br.com.sgo.modelo.ParceiroBeneficio;
import br.com.sgo.modelo.ParceiroLocalidade;

@Resource
public class HisconbeneficioController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final PerfilDao perfilDao;
	private final HisconBeneficioDao hisconBeneficioDao;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final ParceiroBeneficioDao parceiroBeneficioDao;
	private final UsuarioDao usuarioDao;
	private final WorkflowDao workflowDao;
	private final WorkflowEtapaDao workflowEtapaDao;
	private final UsuarioPerfilDao usuarioPerfilDao;
	private HisconBeneficio hisconBeneficio;

	public HisconbeneficioController(Result result, UsuarioInfo usuarioInfo, HisconBeneficioDao hisconBeneficioDao, EmpresaDao empresaDao,
			OrganizacaoDao organizacaoDao, ParceiroBeneficioDao parceiroBeneficioDao, UsuarioDao usuarioDao, WorkflowDao workflowDao, WorkflowEtapaDao workflowEtapaDao,
			UsuarioPerfilDao usuarioPerfilDao, PerfilDao perfilDao, HisconBeneficio hisconBeneficio) {
		this.result = result;
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

		result.include("hisconsBeneficio", this.hisconBeneficioDao.mostraHisconBeneficiosPorUsuarioPerfil(usuarioInfo.getUsuario().getEmpresa().getEmpresa_id(), usuarioInfo.getUsuario().getOrganizacao().getOrganizacao_id(),usuarioInfo.getPerfil().getPerfil_id(), usuarioInfo.getUsuario().getUsuario_id()));		

	}

	@Post
	@Path("/hisconbeneficio/cadastro")
	public void cadastro(Long empresa_id, Long organizacao_id, String numeroBeneficio) {

		String mensagem = "";

		try {

			ParceiroBeneficio pb = this.parceiroBeneficioDao.buscaParceiroBeneficioPorNumeroBeneficio(empresa_id, organizacao_id, numeroBeneficio);

			if (pb != null){

				this.hisconBeneficio.setEmpresa(pb.getEmpresa());
				this.hisconBeneficio.setOrganizacao(pb.getOrganizacao());
				this.hisconBeneficio.setParceiroBeneficio(pb);
				this.hisconBeneficio.setUsuario(usuarioInfo.getUsuario());

				result.include("hisconBeneficio", hisconBeneficio);

			} else {

				mensagem = "Beneficio não cadastrado.";
				result.include("notice", mensagem).redirectTo(ParceironegocioController.class).cadastro();

			}

		} catch (Exception e) {

			mensagem = "Erro ao pesquisar o número do beneficio";

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Post
	@Path("/hisconbeneficio/salva")
	public void salva() {

		String mensagem = "";

		hisconBeneficio.setIsActive(true);

		try{
			
			this.hisconBeneficioDao.beginTransaction();
			this.hisconBeneficioDao.adiciona(hisconBeneficio);
			this.hisconBeneficioDao.commit();

		} catch (Exception e) {

			mensagem = "Erro ao adicionar o workflow.";

		} finally{

			this.hisconBeneficioDao.clear();
			this.hisconBeneficioDao.close();

		}
		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

		/*try {

			if (this.hisconBeneficioDao.validaHisconBeneficioPorParceiroBeneficio(workflow.getEmpresa().getEmpresa_id(),workflow.getOrganizacao().getOrganizacao_id(),
					workflow.getTipoWorkflow().getTipoWorkflow_id(), workflow.getNome()) == null) {				
	
				this.workflowDao.beginTransaction();
				this.workflowDao.adiciona(workflow);
				this.workflowDao.commit();

				mensagem = "Workflow " + workflow.getNome() + " adicionado com sucesso.";

			} else {

				mensagem = "Workflow " + workflow.getNome() + " já cadastrado.";

			} 

		} catch (Exception e) {

			mensagem = "Erro ao adicionar o workflow " + workflow.getNome() + ".";

		} finally{

			this.workflowDao.clear();
			this.workflowDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();
		*/
		
	}

	@Get
	public void msg() {

	}

	@Get
	@Path("/hisconbeneficio/busca.json")
	@Public
	public void hisconbeneficio(Long empresa_id, Long organizacao_id) {
		// result.use(Results.json()).withoutRoot().from(agenciaDao.buscaAgenciaByEmOrBaCa(empresa_id,
		// organizacao_id, banco_id, codigoagencia)).serialize();
	}

	@Post
	@Path("/uploadHiscon")
	public void uploadHiscon(UploadedFile zip) {
		/*
		 * validator.validate(zip); validator.onErrorUsePageOf(this).consulta();
		 * 
		 * Date now = new Date(); String hora = new
		 * SimpleDateFormat("ddMMyyyyHHmm").format(now);
		 * 
		 * Collection<File> files = new ArrayList<File>();
		 * 
		 * if ( zip != null ) {
		 * 
		 * String diretorio = "////localhost//sistemas//_repositorio//hiscon//";
		 * String nomeFile = diretorio + zip.getFileName();
		 * 
		 * try {
		 * 
		 * IOUtils.copy(zip.getFile(), new FileOutputStream(new
		 * File(nomeFile))); CustomFileUtil.extraiZip(new File(nomeFile),new
		 * File(diretorio));
		 * 
		 * hiscons = this.HisconDao.buscaHisconsAdm();
		 * 
		 * for (Hiscon h : hiscons){
		 * 
		 * System.gc();
		 * 
		 * File f = new File(diretorio + h.getCliente().getBeneficio() +
		 * ".pdf");
		 * 
		 * if( f.exists() ) {
		 * 
		 * String caminhoImagemAux = diretorio + h.getCliente().getBeneficio() +
		 * "_" + hora + ".pdf"; File caminhoImagem = new File(caminhoImagemAux);
		 * 
		 * FileUtils.copyFile(f, caminhoImagem);
		 * 
		 * h.setStatus("Enviado");
		 * 
		 * h.setImagem(true); h.setCaminhoImagem(caminhoImagem.getName());
		 * 
		 * altera(h); files.add(f); } }
		 * 
		 * } catch (FileNotFoundException e) { e.printStackTrace(); } catch
		 * (IOException e) { e.printStackTrace(); } finally {
		 * 
		 * for(File f : files){ CustomFileUtil.deleteFile(f); }
		 * 
		 * } }
		 * 
		 * result.redirectTo(this).consulta();
		 */
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