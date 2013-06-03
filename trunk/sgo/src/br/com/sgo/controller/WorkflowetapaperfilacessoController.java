package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.PerfilDao;
import br.com.sgo.dao.WorkflowDao;
import br.com.sgo.dao.EtapaDao;
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
	private final EtapaDao workflowEtapaDao;
	private final PerfilDao perfilDao;
	
	public WorkflowetapaperfilacessoController(Result result,  UsuarioInfo usuarioInfo, WorkflowEtapaPerfilAcessoDao workflowEtapaPerfilAcessoDao, EtapaDao workflowEtapaDao
			, WorkflowDao workflowDao, PerfilDao perfilDao) {

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.workflowDao = workflowDao;
		this.workflowEtapaPerfilAcessoDao =  workflowEtapaPerfilAcessoDao;
		this.workflowEtapaDao = workflowEtapaDao;
		this.perfilDao = perfilDao;

	}

	@Get
	@Public
	@Path("/workflowetapaperfilacesso/cadastro")
	public void cadastro() {

		result.include("workflows", this.workflowDao.buscaWorkflowsByEmpresaOrganizacao(usuarioInfo.getEmpresa().getEmpresa_id(), usuarioInfo.getOrganizacao().getOrganizacao_id()));
		result.include("workflowEtapasPerfilAcesso", this.workflowEtapaPerfilAcessoDao.buscaTodosWorkflowEtapaPerfilAcesso());
		result.include("perfis", this.perfilDao.buscaPerfisToWorkflowEtapaPerfil());

	}

	@Post
	@Public
	@Path("/workflowetapaperfilacesso/workflowetapasperfil")
	public void workflowetapasperfil(Long empresa_id, Long organizacao_id, Long workflow_id){

		//TODO
		//result.include("workflowEtapas",this.workflowEtapaDao.buscaWorkflowEtapasByEmpOrgWorkflow(empresa_id, organizacao_id, workflow_id));

	}

	@Post
	@Public
	@Path("/workflowetapaperfilacesso/salva")
	public void salva(WorkflowEtapaPerfilAcesso workflowEtapaPerfilAcesso) {

		String mensagem = "";

		try {

			if (this.workflowEtapaPerfilAcessoDao.buscaWorkflowEtapaPerfilAcessoPorEmpresaOrganizacaoWorkflowEtapaPerfil(usuarioInfo.getEmpresa().getEmpresa_id(),usuarioInfo.getOrganizacao().getOrganizacao_id(),
					workflowEtapaPerfilAcesso.getEtapa().getEtapa_id(), workflowEtapaPerfilAcesso.getPerfil().getPerfil_id()) == null) {				

				workflowEtapaPerfilAcesso.setIsLeituraEscrita(workflowEtapaPerfilAcesso.getIsLeituraEscrita() == null ? false : true);
				workflowEtapaPerfilAcesso.setIsActive(workflowEtapaPerfilAcesso.getIsActive() == null ? false : true);
				workflowEtapaPerfilAcesso.setIsUpload(workflowEtapaPerfilAcesso.getIsUpload() == null ? false : true);

				this.workflowEtapaPerfilAcessoDao.insert(workflowEtapaPerfilAcesso);

				mensagem = "Perfil " + workflowEtapaPerfilAcesso.getPerfil().getNome() + " adicionado com sucesso para etapa " + workflowEtapaPerfilAcesso.getEtapa().getNome();

			} else {

				mensagem = "Erro: Perfil " + workflowEtapaPerfilAcesso.getPerfil().getNome() +" já cadastrado para a etapa " + workflowEtapaPerfilAcesso.getEtapa().getNome();

			} 

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar o Perfil para o Workflow :" + workflowEtapaPerfilAcesso.getPerfil().getNome();

		} finally{

			this.workflowEtapaDao.clear();
			this.workflowEtapaDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();
	}

	@Get
	public void msg() {

	}
}