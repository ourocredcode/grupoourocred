package br.com.sgo.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
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
import br.com.sgo.dao.ContratoDao;
import br.com.sgo.dao.EtapaDao;
import br.com.sgo.dao.HisconBeneficioDao;
import br.com.sgo.dao.ParceiroBeneficioDao;
import br.com.sgo.dao.ParceiroContatoDao;
import br.com.sgo.dao.ParceiroNegocioDao;
import br.com.sgo.dao.TipoContatoDao;
import br.com.sgo.dao.UsuarioDao;
import br.com.sgo.dao.WorkflowDao;
import br.com.sgo.infra.CustomFileUtil;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Etapa;
import br.com.sgo.modelo.HisconBeneficio;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.ParceiroBeneficio;
import br.com.sgo.modelo.Usuario;
import br.com.sgo.modelo.Workflow;

@Resource
public class HisconbeneficioController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final HisconBeneficioDao hisconBeneficioDao;
	private final ParceiroBeneficioDao parceiroBeneficioDao;
	private final WorkflowDao workflowDao;
	private final EtapaDao etapaDao;
	private final UsuarioDao usuarioDao;
	private final ContratoDao contratoDao;
	private final TipoContatoDao tipoContatoDao;
	private final ParceiroContatoDao parceiroContatoDao;
	private final ParceiroNegocioDao parceiroNegocioDao;

	private HisconBeneficio hisconBeneficio;
	private Calendar dataAtual = Calendar.getInstance();
	private Collection<HisconBeneficio> hiscons;
	private Collection<Usuario> consultoresAux = new ArrayList<Usuario>();
	private Collection<Usuario> consultores;
	private HttpServletResponse response;
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	public HisconbeneficioController(Result result, UsuarioInfo usuarioInfo,Empresa empresa, Organizacao organizacao, Usuario usuario,
			UsuarioDao usuarioDao ,HisconBeneficioDao hisconBeneficioDao, TipoContatoDao tipoContatoDao, ParceiroContatoDao parceiroContatoDao,
			ParceiroNegocioDao parceiroNegocioDao,ContratoDao contratoDao,
			ParceiroBeneficioDao parceiroBeneficioDao, WorkflowDao workflowDao, EtapaDao etapaDao,HisconBeneficio hisconBeneficio,HttpServletResponse response) {

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.hisconBeneficioDao = hisconBeneficioDao;
		this.parceiroBeneficioDao = parceiroBeneficioDao;
		this.usuarioDao = usuarioDao;
		this.workflowDao = workflowDao;
		this.etapaDao = etapaDao;
		this.hisconBeneficio = hisconBeneficio;
		this.tipoContatoDao = tipoContatoDao;
		this.parceiroContatoDao = parceiroContatoDao;
		this.parceiroNegocioDao = parceiroNegocioDao;
		this.contratoDao  = contratoDao;
		this.response = response;
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();
		this.usuario = usuarioInfo.getUsuario();

	}

	@Get
	@Path("/hisconbeneficio/cadastro")
	public void cadastro() {

		Calendar c1 = new GregorianCalendar();
		Calendar c2 = new GregorianCalendar();

		c1.add(Calendar.DAY_OF_MONTH, -5);

		Collection<HisconBeneficio> hisconsAuxiliar = new ArrayList<HisconBeneficio>();

		Etapa etapaAguardandoAdm = this.etapaDao.buscaEtapaByEmpresaOrganizacaoNome(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(),"Aguardando Adm");

		if(usuarioInfo.getPerfil().getNome().equals("Hiscon")){

			hisconsAuxiliar = this.hisconBeneficioDao.buscaHisconsToUpload(empresa.getEmpresa_id(),organizacao.getOrganizacao_id(),etapaAguardandoAdm.getEtapa_id());

		} else {

			hisconsAuxiliar = this.hisconBeneficioDao.buscaHisconBeneficiosByUsuarioPerfil( usuarioInfo.getEmpresa(),  usuarioInfo.getOrganizacao(), usuarioInfo.getUsuario(), c1, c2);

		}
		

		hiscons  = new ArrayList<HisconBeneficio>();

		for (HisconBeneficio h : hisconsAuxiliar){

			//h.setCountHiscons(this.hisconBeneficioDao.buscaCountHisconsBeneficios(usuarioInfo.getEmpresa().getEmpresa_id(),usuarioInfo.getOrganizacao().getOrganizacao_id(),h.getParceiroBeneficio().getParceiroBeneficio_id()));
			h.setCountHiscons(0);
			
			h.setEtapas(etapaDao.buscaEtapaByHisconPerfil(
						usuarioInfo.getEmpresa().getEmpresa_id(),
						usuarioInfo.getOrganizacao().getOrganizacao_id(),
						usuarioInfo.getPerfil().getPerfil_id(),
						h.getHisconBeneficio_id()));
			
			h.getEtapas().add(h.getEtapa());
			
			h.setPosicoes(etapaDao.buscaPosicaoByHisconPerfil(
					usuarioInfo.getEmpresa().getEmpresa_id(),
					usuarioInfo.getOrganizacao().getOrganizacao_id(),
					usuarioInfo.getPerfil().getPerfil_id(),
					h.getHisconBeneficio_id()));

			h.getPosicoes().add(h.getEtapaPosicao());

			hiscons.add(h);

		} 

		result.include("hiscons", hiscons);

	}

	@Post
	@Path("/hisconbeneficio/cadastro")
	public void cadastro(Long empresa_id, Long organizacao_id, String numeroBeneficio) {

		String mensagem = "";

		ParceiroBeneficio pb = this.parceiroBeneficioDao.buscaParceiroBeneficioPorNumeroBeneficio(empresa_id, organizacao_id, numeroBeneficio);
		Boolean aguardandoAverbacao = false;

		//Regra inserida em 06/11/2014 - Evitar solicitação de hiscon com contratos aguardando averbação
		if(organizacao.getNome().equals("OUROCRED MATRIZ") 
				|| organizacao.getNome().equals("OUROCRED RJ") 
				|| organizacao.getNome().equals("USECRED")
				|| organizacao.getNome().equals("XV NOVEMBRO")){

			Collection<Contrato> verificaContratos = this.contratoDao.buscaContratosByNumeroBeneficio(empresa_id,organizacao_id,numeroBeneficio);

			for(Contrato c : verificaContratos){

				if((usuarioInfo.getPerfil().getNome().equals("Consultor") || usuarioInfo.getPerfil().getNome().equals("Supervisor")) 
						&&  ( c.getEtapa().getNome().equals("Aguardando Integração") 
						|| c.getEtapa().getNome().equals("Enviado DataPrev")
						|| c.getEtapa().getNome().equals("Quitado")
						|| c.getEtapa().getNome().equals("Enviado DataPrev Refin")
						|| c.getEtapa().getNome().equals("Pendente ADM")
						|| c.getEtapa().getNome().equals("Pendente Banco")
						|| c.getEtapa().getNome().equals("Pendente Inadimplência"))){
					aguardandoAverbacao = true;
				}

			}
			
		}

		if (pb != null){

			//HisconBeneficio hb = this.hisconBeneficioDao.buscaHisconBeneficioByParceiroBeneficio(pb.getEmpresa().getEmpresa_id(),pb.getOrganizacao().getOrganizacao_id(),pb.getParceiroBeneficio_id());

				if (this.hisconBeneficioDao.buscaHisconBeneficioByParceiroBeneficioToCheckStatusHiscon
					(pb.getEmpresa().getEmpresa_id(), pb.getOrganizacao().getOrganizacao_id(), pb.getParceiroBeneficio_id()) == null){

					if(!aguardandoAverbacao) {
					
						this.hisconBeneficio.setEmpresa(pb.getEmpresa());
						this.hisconBeneficio.setOrganizacao(pb.getOrganizacao());
						this.hisconBeneficio.setParceiroBeneficio(pb);
						this.hisconBeneficio.setUsuario(usuarioInfo.getUsuario());

						result.include("hisconBeneficio", hisconBeneficio);
						
					} else {

						mensagem = "Erro: Cliente esta em período de averbação de contrato. Não é possível fazer nova solicitação de hiscon.";
						result.include("notice", mensagem);

						this.hisconBeneficio.setHisconBeneficio_id(null);
						this.hisconBeneficio.setParceiroBeneficio(null);
						this.hisconBeneficio.setCreated(null);
						this.hisconBeneficio = new HisconBeneficio();

					}

				} else {

					mensagem = "Erro: Hiscon já esta com solicitação em andamento.";
					result.include("notice", mensagem);

					this.hisconBeneficio.setHisconBeneficio_id(null);
					this.hisconBeneficio.setParceiroBeneficio(null);
					this.hisconBeneficio.setCreated(null);
					this.hisconBeneficio = new HisconBeneficio();

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

		Long empresa_id = this.hisconBeneficio.getParceiroBeneficio().getEmpresa().getEmpresa_id();
		Long organizacao_id = this.hisconBeneficio.getParceiroBeneficio().getOrganizacao().getOrganizacao_id();

		this.hisconBeneficio.setWorkflow(this.workflowDao.buscaWorkflowPorNome(empresa_id, organizacao_id, "Status Hiscon"));
		this.hisconBeneficio.setEtapa(this.etapaDao.buscaEtapaByEmpresaOrganizacaoNome(empresa_id, organizacao_id, "Aguardando Supervisor"));

		this.hisconBeneficio.setWorkflowPosicao(this.workflowDao.buscaWorkflowPorNome(empresa_id, organizacao_id, "Posicao Hiscon"));
		this.hisconBeneficio.setEtapaPosicao(this.etapaDao.buscaEtapaByEmpresaOrganizacaoNome(empresa_id, organizacao_id, "Aguardando Posição"));

		try {

			this.hisconBeneficioDao.beginTransaction();
			this.hisconBeneficioDao.adiciona(hisconBeneficio);
			this.hisconBeneficioDao.commit();

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar o hiscon.";

		} finally{

			this.hisconBeneficioDao.clear();
			this.hisconBeneficioDao.close();

		}
		
		this.hisconBeneficio.setHisconBeneficio_id(null);
		this.hisconBeneficio.setParceiroBeneficio(null);
		this.hisconBeneficio.setCreated(null);
		this.hisconBeneficio = new HisconBeneficio();

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Post
	@Path("/hisconbeneficio/altera")
	public void altera(HisconBeneficio hisconBeneficio) {

		this.hisconBeneficio = this.hisconBeneficioDao.load(hisconBeneficio.getHisconBeneficio_id());

		if(hisconBeneficio.getEtapa() != null){

			hisconBeneficio.setEtapa(this.etapaDao.buscaEtapaById(hisconBeneficio.getEtapa().getEtapa_id()));

			if(!this.hisconBeneficio.getEtapa().getNome().equals("Aguardando Adm") && !this.hisconBeneficio.getEtapa().getNome().equals("Enviado"))
				this.hisconBeneficio.setDataAdm(hisconBeneficio.getEtapa().getNome().equals("Aguardando Adm") ? GregorianCalendar.getInstance() : null);

			this.hisconBeneficio.setEtapa(hisconBeneficio.getEtapa());
			this.hisconBeneficio.setIsEnviado(hisconBeneficio.getEtapa().getNome().equals("Enviado") ? true : false);

		}

		if(hisconBeneficio.getEtapaPosicao() != null){

			hisconBeneficio.setEtapaPosicao(this.etapaDao.buscaEtapaById(hisconBeneficio.getEtapaPosicao().getEtapa_id()));

			this.hisconBeneficio.setEtapaPosicao(hisconBeneficio.getEtapaPosicao());

		}
			

		if(hisconBeneficio.getUsuario() != null)
			this.hisconBeneficio.setUsuario(hisconBeneficio.getUsuario());

		if(this.hisconBeneficio.getDataEnvio() == null)
			this.hisconBeneficio.setDataEnvio(this.hisconBeneficio.getIsEnviado() == false ? null :GregorianCalendar.getInstance());

		if(hisconBeneficio.getCaminhoArquivo() != null)
			this.hisconBeneficio.setCaminhoArquivo(hisconBeneficio.getCaminhoArquivo());

		if(hisconBeneficio.getIsEnviado()){

			Etapa etapaEnviado = this.etapaDao.buscaEtapaByEmpresaOrganizacaoNomeExato(usuarioInfo.getEmpresa().getEmpresa_id(), usuarioInfo.getOrganizacao().getOrganizacao_id(), "Enviado");

			this.hisconBeneficio.setEtapa(etapaEnviado);
			this.hisconBeneficio.setDataEnvio(GregorianCalendar.getInstance());
			this.hisconBeneficio.setIsEnviado(true);

		}

		this.hisconBeneficio.setIsActive(true);
		this.hisconBeneficio.setUpdated(GregorianCalendar.getInstance());
		this.hisconBeneficio.setUpdatedBy(usuario);

		hisconBeneficioDao.beginTransaction();
		hisconBeneficioDao.atualiza(this.hisconBeneficio);
		hisconBeneficioDao.commit();

		result.nothing();


	}

	@Get
	public void msg() {

	}
	
	@Get
	@Path("/hisconbeneficio/hiscons/{tipo}")
	public void hiscons(String tipo) {

		Workflow w = this.workflowDao.buscaWorkflowPorNome(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Status Hiscon");
		Workflow posicao = this.workflowDao.buscaWorkflowPorNome(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Posicao Hiscon");
		
		Collection<Usuario> supervisores = new ArrayList<Usuario>();

		result.include("etapas",this.etapaDao.buscaEtapasByEmpresaOrganizacaoWorkflow(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), w.getWorkflow_id()));
		result.include("posicoes",this.etapaDao.buscaEtapasByEmpresaOrganizacaoWorkflow(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), posicao.getWorkflow_id()));

		supervisores.addAll(this.usuarioDao.buscaUsuariosByPerfilDepartamento(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Supervisor", "Comercial"));
		supervisores.addAll(this.usuarioDao.buscaUsuariosByPerfilDepartamento(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Gestor", "Administrativo"));
		supervisores.addAll(this.usuarioDao.buscaUsuariosByPerfilDepartamento(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Gestor", "Apoio Comercial"));

		result.include("supervisores", supervisores);

		if(tipo.equals("Supervisor")){

			result.include("consultores",this.usuarioDao.buscaUsuariosBySupervisor(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), this.usuario.getUsuario_id()));

		}

	}
	
	@Post
	@Path("/hisconbeneficio/lista")
	public void lista(Collection<String> status,Collection<String> posicao,String cliente,String documento,String dataInicio, String dataFim, Long consultor) {

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

		
		Calendar calendarInicio = new GregorianCalendar();
		Calendar calendarFim = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		try {

			if(dataFim.equals(""))
				dataFim = dataInicio;

			if(dataInicio.equals("")) {
				calendarInicio = null;
				calendarFim = null;
			} else {
				calendarInicio.setTime(sdf.parse(dataInicio));
				calendarFim.setTime(sdf.parse(dataFim));
				calendarInicio.set(Calendar.HOUR_OF_DAY,calendarInicio.getActualMinimum(Calendar.HOUR_OF_DAY));
				calendarFim.set(Calendar.HOUR_OF_DAY,calendarFim.getActualMaximum(Calendar.HOUR_OF_DAY));
			}	

		} catch (ParseException e) {
			e.printStackTrace();
		}

		Collection<HisconBeneficio> hisconsAux = new ArrayList<HisconBeneficio>();

		hisconsAux = this.hisconBeneficioDao.buscaHisconsByFiltro(empresa, organizacao,consultoresAux,status,posicao,cliente,documento,calendarInicio,calendarFim);

		hiscons = new ArrayList<HisconBeneficio>();

		for(HisconBeneficio h : hisconsAux){

			//h.setCountHiscons(this.hisconBeneficioDao.buscaCountHisconsBeneficios(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), h.getParceiroBeneficio().getParceiroBeneficio_id()));
			h.setCountHiscons(0);

			h.setEtapas(etapaDao.buscaEtapaByHisconPerfil(
					usuarioInfo.getEmpresa().getEmpresa_id(),
					usuarioInfo.getOrganizacao().getOrganizacao_id(),
					usuarioInfo.getPerfil().getPerfil_id(),
					h.getHisconBeneficio_id()));

			h.getEtapas().add(h.getEtapa());

			h.setPosicoes(etapaDao.buscaPosicaoByHisconPerfil(
					usuarioInfo.getEmpresa().getEmpresa_id(),
					usuarioInfo.getOrganizacao().getOrganizacao_id(),
					usuarioInfo.getPerfil().getPerfil_id(),
					h.getHisconBeneficio_id()));

			h.getPosicoes().add(h.getEtapaPosicao());

			hiscons.add(h);

		}

		if(usuarioInfo.getPerfil().getChave().equals("Supervisor")){

			consultores = this.usuarioDao.buscaUsuariosBySupervisor(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), usuarioInfo.getUsuario().getUsuario_id());
			consultores.add(usuarioInfo.getUsuario());

			result.include("consultores",consultores);

		}

		Collection<Usuario> supervisores = new ArrayList<Usuario>();

		supervisores.addAll(this.usuarioDao.buscaUsuariosByPerfilDepartamento(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Supervisor", "Comercial"));
		supervisores.addAll(this.usuarioDao.buscaUsuariosByPerfilDepartamento(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Gestor", "Administrativo"));
		supervisores.addAll(this.usuarioDao.buscaUsuariosByPerfilDepartamento(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Gestor", "Apoio Comercial"));

		result.include("supervisoresLista", supervisores);

		result.include("hisconsBeneficio",hiscons);

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

				Etapa etapaAguardandoAdm = this.etapaDao.buscaEtapaByEmpresaOrganizacaoNome(empresa.getEmpresa_id(),organizacao.getOrganizacao_id(), "Aguardando Adm");

				hiscons = this.hisconBeneficioDao.buscaHisconsToUpload(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), etapaAguardandoAdm.getEtapa_id());

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
	
	@Post
 	@Path("/hisconbeneficio/consultores")
	public void consultores(Long supervisor_id) {

		consultores = this.usuarioDao.buscaUsuariosBySupervisor(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), supervisor_id);

		result.include("consultores",consultores);

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
	
	@Post
	@Path("/hisconbeneficio/limpar")
	public void limpar() {

		this.hisconBeneficio.setHisconBeneficio_id(null);
		this.hisconBeneficio.setParceiroBeneficio(null);
		this.hisconBeneficio.setCreated(null);
		this.hisconBeneficio = null;

		result.redirectTo(this).cadastro();
		result.nothing();

	}
	
	@Get
	@Path("/hisconbeneficio/detalhe/{parceirobeneficio_id}")
	public void detalhe(Long parceirobeneficio_id) {

		hiscons = this.hisconBeneficioDao.buscaHisconsBeneficioByParceiroBeneficio(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), parceirobeneficio_id);

		result.include("hiscons",hiscons);

	}
	
	@Post
	@Path("/hisconbeneficio/parceironegocio/contatos")
	public void contatos(Long parceironegocio_id) {

		result.include("parceiroNegocio",this.parceiroNegocioDao.load(parceironegocio_id));
		result.include("tiposContato",this.tipoContatoDao.buscaTiposContatos());
		result.include("parceiroContatos",this.parceiroContatoDao.buscaParceiroContatos(parceironegocio_id));

	}

}