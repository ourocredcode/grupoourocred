package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.EtapaDao;
import br.com.sgo.dao.WorkflowDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.WorkflowEtapa;

@Resource
public class WorkflowetapaController {

	private final Result result;	
	private final UsuarioInfo usuarioInfo;
	private final EtapaDao workflowEtapaDao;
	private final WorkflowDao workflowDao;
	
	public WorkflowetapaController(Result result, UsuarioInfo usuarioInfo, EtapaDao workflowEtapaDao, WorkflowDao workflowDao) {

		this.result = result;
		this.workflowEtapaDao = workflowEtapaDao;
		this.workflowDao = workflowDao;
		this.usuarioInfo = usuarioInfo;

	}

	@Get
	@Public
	@Path("/workflowetapa/cadastro")
	public void cadastro() {
		result.include("workflows", this.workflowDao.buscaWorkflowsByEmpresaOrganizacao(usuarioInfo.getEmpresa().getEmpresa_id(), usuarioInfo.getOrganizacao().getOrganizacao_id()));
		result.include("workflowEtapas", this.workflowEtapaDao.buscaTodosEtapa());
	}

	@Post
	@Public
	@Path("/workflowetapa/salva")
	public void salva(WorkflowEtapa workflowEtapa) {

		String mensagem = "";

		try {
			//TODO
			if (this.workflowEtapaDao.buscaEtapaByNome(workflowEtapa.getEmpresa().getEmpresa_id(), workflowEtapa.getOrganizacao().getOrganizacao_id()
					, workflowEtapa.getNome()) == null) {				

				workflowEtapa.setIsActive(workflowEtapa.getIsActive() == null ? false : true);
				
				this.workflowEtapaDao.beginTransaction();
				//TODO
				//this.workflowEtapaDao.adiciona(workflowEtapa);
				this.workflowEtapaDao.commit();

				mensagem = "Etapa " + workflowEtapa.getNome() + " adicionado com sucesso para o workflow " + workflowEtapa.getWorkflow().getNome();

			} else {
				
				mensagem = "Erro: Etapa " + workflowEtapa.getNome() + " já cadastrado para o workflow " + workflowEtapa.getWorkflow().getNome();
				
			} 

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar a Etapa do Workflow " + workflowEtapa.getWorkflow().getNome();			

			} finally{
				this.workflowEtapaDao.clear();
				this.workflowEtapaDao.close();
			}

			result.include("notice", mensagem);			
			result.redirectTo(this).cadastro();
	}
	
	@Get
	@Path("/workflowetapa/busca.json")
	@Public
	public void workflowEtapa(Long empresa_id, Long organizacao_id, String nome) {	
		//TODO
		//result.use(Results.json()).withoutRoot().from(workflowEtapaDao.buscaWorkflowEtapasByNome(empresa_id, organizacao_id, nome)).serialize();	
	}

	@Post
	@Path("/workflowetapa/lista")
	@Public
	public void lista(Long empresa_id, Long organizacao_id, String nome) {
		//TODO
		//result.include("workflowEtapas", this.workflowEtapaDao.buscaWorkflowEtapasByNome(empresa_id, organizacao_id, nome));
	}

	@Get
	public void msg() {

	}
}