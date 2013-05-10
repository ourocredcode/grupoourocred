package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.PerfilDao;
import br.com.sgo.dao.WorkflowDao;
import br.com.sgo.dao.WorkflowPerfilAcessoDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.WorkflowPerfilAcesso;

@Resource
public class WorkflowperfilacessoController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final WorkflowPerfilAcessoDao workflowPerfilAcessoDao;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final WorkflowDao workflowDao;
	private final PerfilDao perfilDao;
	

	public WorkflowperfilacessoController(Result result,  UsuarioInfo usuarioInfo, WorkflowPerfilAcessoDao workflowPerfilAcessoDao, EmpresaDao empresaDao, OrganizacaoDao organizacaoDao, WorkflowDao workflowDao, PerfilDao perfilDao) {
		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.workflowPerfilAcessoDao =  workflowPerfilAcessoDao;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.workflowDao = workflowDao;
		this.perfilDao = perfilDao;
	}

	@Get
	@Public
	@Path("/workflowperfilacesso/cadastro")
	public void cadastro() {
		
		result.include("workflowperfisacesso", this.workflowPerfilAcessoDao.buscaTodosWorkflowPerfilAcesso());
		
	}

	@Post
	@Public
	@Path("/workflowperfilacesso/salva")
	public void salva(WorkflowPerfilAcesso workflowPerfilAcesso) {

		String mensagem = "";

		try {

			if (this.workflowPerfilAcessoDao.buscaWorkflowPerfilAcessoPorEmpresaOrganizacaoWorkflowPerfil(usuarioInfo.getEmpresa().getEmpresa_id(),usuarioInfo.getOrganizacao().getOrganizacao_id(),
					workflowPerfilAcesso.getWorkflow().getWorkflow_id(), workflowPerfilAcesso.getPerfil().getPerfil_id()) == null) {				

				workflowPerfilAcesso.setEmpresa(this.empresaDao.load(usuarioInfo.getEmpresa().getEmpresa_id()));
				workflowPerfilAcesso.setOrganizacao(this.organizacaoDao.load(usuarioInfo.getOrganizacao().getOrganizacao_id()));				
				workflowPerfilAcesso.setWorkflow(this.workflowDao.load(workflowPerfilAcesso.getWorkflow().getWorkflow_id()));
				workflowPerfilAcesso.setPerfil(this.perfilDao.load(workflowPerfilAcesso.getPerfil().getPerfil_id()));
				
				workflowPerfilAcesso.setIsActive(workflowPerfilAcesso.getIsActive() == null ? false: true);
				workflowPerfilAcesso.setIsLeituraEscrita(workflowPerfilAcesso.getIsLeituraEscrita() == null ? false: true);

				this.workflowPerfilAcessoDao.insert(workflowPerfilAcesso);

				mensagem = "Perfil " + workflowPerfilAcesso.getPerfil().getNome() + " adicionado com sucesso para o Workflow " + workflowPerfilAcesso.getWorkflow().getNome();

			} else {
				
				mensagem = "Erro: Perfil " + workflowPerfilAcesso.getPerfil().getNome() + " já cadastrado para o Workflow " + workflowPerfilAcesso.getWorkflow().getNome();
				
			} 

		} catch (Exception e) {

			mensagem = "Erro: ao adicionar o Perfil Workflow :";

		} finally{

			this.workflowPerfilAcessoDao.clear();
			this.workflowPerfilAcessoDao.close();

		}
		
		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();
	}

	@Get
	public void msg() {

	}
}