package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.TipoWorkflowDao;
import br.com.sgo.dao.WorkflowDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Usuario;
import br.com.sgo.modelo.Workflow;

@Resource
public class WorkflowController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final TipoWorkflowDao tipoWorkflowDao;
	private final WorkflowDao workflowDao;

	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;
	private Workflow workflow;
	private Calendar dataAtual = Calendar.getInstance();

	public WorkflowController(Result result, Empresa empresa, Organizacao organizacao, Usuario usuario, UsuarioInfo usuarioInfo, Workflow workflow, WorkflowDao workflowDao, TipoWorkflowDao tipoWorkflowDao) {

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.workflow = workflow;
		this.workflowDao = workflowDao;
		this.tipoWorkflowDao = tipoWorkflowDao;
		this.empresa = this.usuarioInfo.getEmpresa();
		this.organizacao = this.usuarioInfo.getOrganizacao();
		this.usuario = this.usuarioInfo.getUsuario();

	}

	@Get
	@Path("/workflow/cadastro")
	public void cadastro() {

		result.include("workflows", this.workflowDao.buscaTodosWorkflow(empresa.getEmpresa_id(),organizacao.getOrganizacao_id()));
		result.include("tiposWorkflow", this.tipoWorkflowDao.buscaTodosTipoWorkflow());

	}

	@Post
	@Path("/workflow/salva")
	public void salva(Workflow workflow) {

		String mensagem = "";

		try {

			if (this.workflowDao.buscaWorkflowPorEmpresaOrganizacaoTipoworflowNome(empresa.getEmpresa_id(),organizacao.getOrganizacao_id(),
					workflow.getTipoWorkflow().getTipoWorkflow_id(), workflow.getNome()) == null) {				
				
				workflow.setCreated(dataAtual);
				workflow.setUpdated(dataAtual);

				workflow.setCreatedBy(usuario);
				workflow.setUpdatedBy(usuario);
				
				this.workflow.setIsActive(workflow.getIsActive() == null || workflow.getIsActive() == false ? false : true);
				
				this.workflowDao.beginTransaction();
				this.workflowDao.adiciona(workflow);
				this.workflowDao.commit();
				
				mensagem = "Workflow " + workflow.getNome() + " adicionado com sucesso.";
				
			} else {
				mensagem = "Erro: Workflow " + workflow.getNome() + " já cadastrado.";
			} 

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar o workflow " + workflow.getNome() + ".";

		} finally{

			this.workflowDao.clear();
			this.workflowDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Post
	@Path("/workflow/altera")
	public void altera(Workflow workflow) {

		String mensagem = "";

		this.workflow = this.workflowDao.load(workflow.getWorkflow_id());

		this.workflow.setUpdated(dataAtual);
		this.workflow.setUpdatedBy(usuario);

		this.workflow.setIsActive(workflow.getIsActive() == null || workflow.getIsActive() == false ? false : true);

		workflowDao.beginTransaction();		
		workflowDao.atualiza(this.workflow);
		workflowDao.commit();

		mensagem = " Workflow adicionado com sucesso.";

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();
		
	}

	@Get
	@Path("/workflow/busca.json")
	public void workflow(Long empresa_id, Long organizacao_id, String nome) {

		result.use(Results.json()).withoutRoot().from(workflowDao.buscaWorkflowsPorNome(empresa_id, organizacao_id, nome)).serialize();

	}
	
	@Post
	@Path("/workflow/lista")
	public void lista(Long empresa_id, Long organizacao_id, String nome) {

		result.include("workflows", this.workflowDao.buscaWorkflowsPorNome(empresa_id, organizacao_id, nome));

	}

	@Get
	public void msg() {

	}
}