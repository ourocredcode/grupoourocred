package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.PerfilDao;
import br.com.sgo.dao.WorkflowEtapaDao;
import br.com.sgo.dao.WorkflowEtapaPerfilAcessoDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.WorkflowEtapaPerfilAcesso;

@Resource
public class WorkflowetapaperfilacessoController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final WorkflowEtapaPerfilAcessoDao workflowEtapaPerfilAcessoDao;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final WorkflowEtapaDao workflowEtapaDao;
	private final PerfilDao perfilDao;
	
	
	public WorkflowetapaperfilacessoController(Result result,  UsuarioInfo usuarioInfo, WorkflowEtapaPerfilAcessoDao workflowEtapaPerfilAcessoDao, EmpresaDao empresaDao
			, OrganizacaoDao organizacaoDao, WorkflowEtapaDao workflowEtapaDao, PerfilDao perfilDao) {
		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.workflowEtapaPerfilAcessoDao =  workflowEtapaPerfilAcessoDao;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.workflowEtapaDao = workflowEtapaDao;
		this.perfilDao = perfilDao;
	}

	@Get
	@Public
	@Path("/workflowetapaperfilacesso/cadastro")
	public void cadastro() {
		result.include("workflowEtapasPerfilAcesso", this.workflowEtapaPerfilAcessoDao.buscaTodosWorkflowEtapaPerfilAcesso());
	}

	@Post
	@Public
	@Path("/workflowetapaperfilacesso/salva")
	public void salva(WorkflowEtapaPerfilAcesso workflowEtapaPerfilAcesso) {

		String mensagem = "";

		try {

			if (this.workflowEtapaPerfilAcessoDao.buscaWorkflowEtapaPerfilAcessoPorEmpresaOrganizacaoWorkflowEtapaPerfil(usuarioInfo.getEmpresa().getEmpresa_id(),usuarioInfo.getOrganizacao().getOrganizacao_id(),
					workflowEtapaPerfilAcesso.getWorkflowEtapa().getWorkflowEtapa_id(), workflowEtapaPerfilAcesso.getPerfil().getPerfil_id()) == null) {				

				workflowEtapaPerfilAcesso.setEmpresa(this.empresaDao.load(usuarioInfo.getEmpresa().getEmpresa_id()));
				workflowEtapaPerfilAcesso.setOrganizacao(this.organizacaoDao.load(usuarioInfo.getOrganizacao().getOrganizacao_id()));				
				workflowEtapaPerfilAcesso.setWorkflowEtapa(this.workflowEtapaDao.load(workflowEtapaPerfilAcesso.getWorkflowEtapa().getWorkflowEtapa_id()));
				workflowEtapaPerfilAcesso.setPerfil(this.perfilDao.load(workflowEtapaPerfilAcesso.getPerfil().getPerfil_id()));
				
				workflowEtapaPerfilAcesso.setIsLeituraEscrita(workflowEtapaPerfilAcesso.getIsLeituraEscrita() == null ? false: true);
				workflowEtapaPerfilAcesso.setIsActive(workflowEtapaPerfilAcesso.getIsActive() == null ? false: true);				

				this.workflowEtapaPerfilAcessoDao.insert(workflowEtapaPerfilAcesso);

				mensagem = "Perfil " + workflowEtapaPerfilAcesso.getPerfil().getNome() + " adicionado com sucesso para etapa " + workflowEtapaPerfilAcesso.getWorkflowEtapa().getNome();
				
			} else {
				
				mensagem = "Perfil " + workflowEtapaPerfilAcesso.getPerfil().getNome() +" já cadastrado para a etapa " + workflowEtapaPerfilAcesso.getWorkflowEtapa().getNome();
				
			} 

		} catch (Exception e) {

			mensagem = "Erro ao adicionar o Perfil para o Workflow :" + workflowEtapaPerfilAcesso.getPerfil().getNome();

		}finally{

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