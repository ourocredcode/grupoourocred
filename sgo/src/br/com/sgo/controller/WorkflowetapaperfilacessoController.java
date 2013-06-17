package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.EtapaDao;
import br.com.sgo.dao.PerfilDao;
import br.com.sgo.dao.WorkflowDao;
import br.com.sgo.dao.WorkflowEtapaPerfilAcessoDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Usuario;
import br.com.sgo.modelo.WorkflowEtapaPerfilAcesso;

@Resource
public class WorkflowetapaperfilacessoController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final WorkflowDao workflowDao;
	private final WorkflowEtapaPerfilAcessoDao workflowEtapaPerfilAcessoDao;	
	private final PerfilDao perfilDao;
	private final EtapaDao etapaDao;

	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	public WorkflowetapaperfilacessoController(Result result, UsuarioInfo usuarioInfo, WorkflowEtapaPerfilAcessoDao workflowEtapaPerfilAcessoDao
			, WorkflowDao workflowDao, PerfilDao perfilDao, EtapaDao etapaDao, Empresa empresa,Organizacao organizacao,Usuario usuario) {

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.workflowDao = workflowDao;
		this.workflowEtapaPerfilAcessoDao =  workflowEtapaPerfilAcessoDao;	
		this.perfilDao = perfilDao;
		this.etapaDao = etapaDao;
		this.empresa = this.usuarioInfo.getEmpresa();
		this.organizacao = this.usuarioInfo.getOrganizacao();
		this.usuario = this.usuarioInfo.getUsuario();

	}

	@Post
	@Path("/workflowetapaperfilacesso/salva")
	public void salva(WorkflowEtapaPerfilAcesso workflowEtapaPerfilAcesso) {

		String mensagem = "";
		Calendar dataAtual = Calendar.getInstance();

		try {

			if (this.workflowEtapaPerfilAcessoDao.buscaWorkflowEtapaPerfilAcessoPorEmpresaOrganizacaoWorkflowEtapaPerfil(empresa.getEmpresa_id(),organizacao.getOrganizacao_id()
					,workflowEtapaPerfilAcesso.getWorkflow().getWorkflow_id(), workflowEtapaPerfilAcesso.getEtapa().getEtapa_id(), workflowEtapaPerfilAcesso.getPerfil().getPerfil_id()) == null) {			

				workflowEtapaPerfilAcesso.setCreated(dataAtual);
				workflowEtapaPerfilAcesso.setUpdated(dataAtual);

				workflowEtapaPerfilAcesso.setCreatedBy(usuario);
				workflowEtapaPerfilAcesso.setUpdatedBy(usuario);

				workflowEtapaPerfilAcesso.setIsActive(workflowEtapaPerfilAcesso.getIsActive() == null || workflowEtapaPerfilAcesso.getIsActive() == false ? false : true);
				workflowEtapaPerfilAcesso.setIsLeituraEscrita(workflowEtapaPerfilAcesso.getIsLeituraEscrita() == null || workflowEtapaPerfilAcesso.getIsUpload() == false ? false : true);
				workflowEtapaPerfilAcesso.setIsUpload(workflowEtapaPerfilAcesso.getIsUpload() == null || workflowEtapaPerfilAcesso.getIsUpload() == false ? false : true);

				this.workflowEtapaPerfilAcessoDao.insert(workflowEtapaPerfilAcesso);

				mensagem = "Perfil adicionado com sucesso."; 

			} else {

				mensagem = "Erro: Perfil já cadastrado para a etapa.";

			} 

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar o Perfil para o Workflow."; 

		} finally{

			this.workflowEtapaPerfilAcessoDao.clear();
			this.workflowEtapaPerfilAcessoDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Get
	@Path("/workflowetapaperfilacesso/cadastro")
	public void cadastro() {

		result.include("workflowEtapasPerfilAcesso", this.workflowEtapaPerfilAcessoDao.buscaTodosWorkflowEtapaPerfilAcesso());
		result.include("workflows", this.workflowDao.buscaWorkflowsToWorkflowEtapaPerfilByEmpresaOrganizacao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
		result.include("perfis", this.perfilDao.buscaPerfisToWorkflowEtapaPerfil());
	}

	@Post
	@Path("/workflowetapaperfilacesso/etapas")
	public void etapas(Long empresa_id, Long organizacao_id, Long workflow_id){

		result.include("etapas", this.etapaDao.buscaEtapasByEmpresaOrganizacaoWorkflow(empresa_id, organizacao_id, workflow_id));

	}

	@Get
	public void msg() {

	}
}