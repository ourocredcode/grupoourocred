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
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.WorkflowEtapaPerfilAcesso;

@Resource
public class WorkflowetapaperfilacessoController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final WorkflowDao workflowDao;
	private final WorkflowEtapaPerfilAcessoDao workflowEtapaPerfilAcessoDao;	
	private final PerfilDao perfilDao;
	private final EtapaDao etapaDao;

	private WorkflowEtapaPerfilAcesso workflowEtapaPerfilAcesso;	
	private Calendar dataAtual = Calendar.getInstance();

	public WorkflowetapaperfilacessoController(Result result, UsuarioInfo usuarioInfo, WorkflowEtapaPerfilAcessoDao workflowEtapaPerfilAcessoDao
			, WorkflowDao workflowDao, PerfilDao perfilDao, EtapaDao etapaDao) {

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.workflowDao = workflowDao;
		this.workflowEtapaPerfilAcessoDao =  workflowEtapaPerfilAcessoDao;		
		this.perfilDao = perfilDao;
		this.etapaDao = etapaDao;

	}

	@Post
	@Public
	@Path("/workflowetapaperfilacesso/salva")
	public void salva(WorkflowEtapaPerfilAcesso workflowEtapaPerfilAcesso) {

		String mensagem = "";

		try {

			if (this.workflowEtapaPerfilAcessoDao.buscaWorkflowEtapaPerfilAcessoPorEmpresaOrganizacaoWorkflowEtapaPerfil(usuarioInfo.getEmpresa().getEmpresa_id(),usuarioInfo.getOrganizacao().getOrganizacao_id()
					,workflowEtapaPerfilAcesso.getWorkflow().getWorkflow_id(), workflowEtapaPerfilAcesso.getEtapa().getEtapa_id(), workflowEtapaPerfilAcesso.getPerfil().getPerfil_id()) == null) {			

				this.workflowEtapaPerfilAcesso.setCreated(dataAtual);
				this.workflowEtapaPerfilAcesso.setUpdated(dataAtual);

				this.workflowEtapaPerfilAcesso.setCreatedBy(usuarioInfo.getUsuario());
				this.workflowEtapaPerfilAcesso.setUpdatedBy(usuarioInfo.getUsuario());

				workflowEtapaPerfilAcesso.setIsActive(workflowEtapaPerfilAcesso.getIsActive() == null ? false : true);				
				workflowEtapaPerfilAcesso.setIsLeituraEscrita(workflowEtapaPerfilAcesso.getIsLeituraEscrita() == null ? false : true);
				workflowEtapaPerfilAcesso.setIsUpload(workflowEtapaPerfilAcesso.getIsUpload() == null ? false : true);

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
	@Public
	@Path("/workflowetapaperfilacesso/cadastro")
	public void cadastro() {

		result.include("workflowEtapasPerfilAcesso", this.workflowEtapaPerfilAcessoDao.buscaTodosWorkflowEtapaPerfilAcesso());
		result.include("workflows", this.workflowDao.buscaWorkflowsToWorkflowEtapaPerfilByEmpresaOrganizacao(usuarioInfo.getEmpresa().getEmpresa_id(), usuarioInfo.getOrganizacao().getOrganizacao_id()));
		result.include("perfis", this.perfilDao.buscaPerfisToWorkflowEtapaPerfil());
	}

	@Post
	@Public
	@Path("/workflowetapaperfilacesso/etapas")
	public void etapas(Long empresa_id, Long organizacao_id, Long workflow_id){

		result.include("etapas", this.etapaDao.buscaEtapasByEmpresaOrganizacaoWorkflow(empresa_id, organizacao_id, workflow_id));

	}

	@Get
	public void msg() {

	}
}