package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.TipoWorkflowDao;
import br.com.sgo.dao.WorkflowDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Workflow;

@Resource
public class WorkflowController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final WorkflowDao workflowDao;
	private final TipoWorkflowDao tipoWorkflowDao;

	public WorkflowController(Result result,  UsuarioInfo usuarioInfo, EmpresaDao empresaDao, OrganizacaoDao organizacaoDao, WorkflowDao workflowDao, TipoWorkflowDao tipoWorkflowDao) {
		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.workflowDao = workflowDao;
		this.tipoWorkflowDao = tipoWorkflowDao;
	}

	@Get
	@Public
	@Path("/workflow/cadastro")
	public void cadastro() {
		result.include("workflows", this.workflowDao.buscaTodosWorkflow());
	}

	@Post
	@Public
	@Path("/workflow/salva")
	public void salva(Workflow workflow) {

		String mensagem = "";

		try {

			if (this.workflowDao.buscaWorkflowPorEmpresaOrganizacaoTipoworflowNome(workflow.getEmpresa().getEmpresa_id(),workflow.getOrganizacao().getOrganizacao_id(),
					workflow.getTipoWorkflow().getTipoWorkflow_id(), workflow.getNome()) == null) {				
				
				this.workflowDao.beginTransaction();
				this.workflowDao.adiciona(workflow);
				this.workflowDao.commit();
				
				mensagem = "Workflow " + workflow.getNome() + " adicionado com sucesso.";
				
			} else {
				mensagem = "Erro: Workflow " + workflow.getNome() + " já cadastrado.";
			} 

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar o workflow " + workflow.getNome() + ".";

		}finally{

			this.workflowDao.clear();
			this.workflowDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}
	
	@Get
	@Path("/workflow/busca.json")
	@Public
	public void workflow(Long empresa_id, Long organizacao_id, String nome) {	
		result.use(Results.json()).withoutRoot().from(workflowDao.buscaWorkflowsPorNome(empresa_id, organizacao_id, nome)).serialize();
	}

	@Post
	@Path("/workflow/lista")
	@Public
	public void lista(Long empresa_id, Long organizacao_id, String nome) {
		result.include("workflows", this.workflowDao.buscaWorkflowsPorNome(empresa_id, organizacao_id, nome));
	}

	@Get
	public void msg() {

	}
}