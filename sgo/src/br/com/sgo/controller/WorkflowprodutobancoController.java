package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.BancoDao;
import br.com.sgo.dao.WorkflowDao;
import br.com.sgo.dao.WorkflowProdutoBancoDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Usuario;
import br.com.sgo.modelo.WorkflowProdutoBanco;

@Resource
public class WorkflowprodutobancoController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final WorkflowProdutoBancoDao workflowProdutoBancoDao;
	private final WorkflowDao workflowDao;
	private final BancoDao bancoDao;

	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;
	private Calendar dataAtual = Calendar.getInstance();

	public WorkflowprodutobancoController(Result result, Empresa empresa, Organizacao organizacao, Usuario usuario, UsuarioInfo usuarioInfo, WorkflowDao workflowDao, WorkflowProdutoBancoDao workflowProdutoBancoDao, BancoDao bancoDao) {

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.workflowDao = workflowDao;
		this.workflowProdutoBancoDao = workflowProdutoBancoDao;
		this.bancoDao = bancoDao;
		this.empresa = this.usuarioInfo.getEmpresa();
		this.organizacao = this.usuarioInfo.getOrganizacao();
		this.usuario = this.usuarioInfo.getUsuario();

	}

	@Get
	@Path("/workflowprodutobanco/cadastro")
	public void cadastro() {

		result.include("workflowsProdutoBanco", this.workflowProdutoBancoDao.buscaAllWorkflowProdutoBancoByEmpresaOrganizacao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
		result.include("workflows", this.workflowDao.buscaWorkflowsByEmpresaOrganizacao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
		result.include("bancos", this.bancoDao.buscaAllBancos());

	}

	@Post
	@Path("/workflowprodutobanco/salva")
	public void salva(WorkflowProdutoBanco workflowProdutoBanco) {

		String mensagem = "";

		try {

			if (this.workflowProdutoBancoDao.buscaWorkflowProdutoBancoByEmpresaOrganizacaoProdutoBancoWorkflow(workflowProdutoBanco.getEmpresa().getEmpresa_id(), workflowProdutoBanco.getOrganizacao().getOrganizacao_id(),
					workflowProdutoBanco.getProduto().getProduto_id(), workflowProdutoBanco.getBanco().getBanco_id(), workflowProdutoBanco.getWorkflow().getWorkflow_id()) == null) {				

				workflowProdutoBanco.setCreated(dataAtual);
				workflowProdutoBanco.setUpdated(dataAtual);

				workflowProdutoBanco.setCreatedBy(usuario);
				workflowProdutoBanco.setUpdatedBy(usuario);

				workflowProdutoBanco.setIsActive(workflowProdutoBanco.getIsActive() == null ? false : true);
				workflowProdutoBanco.setIsWorkflow(workflowProdutoBanco.getIsWorkflow() == null ? false : true);
				
				this.workflowProdutoBancoDao.beginTransaction();
				this.workflowProdutoBancoDao.adiciona(workflowProdutoBanco);
				this.workflowProdutoBancoDao.commit();
				
				mensagem = "Workflow adicionado com sucesso.";
				
			} else {
				mensagem = "Erro: Workflow " + workflowProdutoBanco.getNome() + " já cadastrado.";
			} 

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar o workflowProdutoBanco.";

		}finally{

			this.workflowProdutoBancoDao.clear();
			this.workflowProdutoBancoDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Post
	@Path("/workflowprodutobanco/lista")
	public void lista(Long empresa_id, Long organizacao_id) {

		result.include("workflowsProdutoBanco", this.workflowProdutoBancoDao.buscaAllWorkflowProdutoBancoByEmpresaOrganizacao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));

	}

	@Get
	public void msg() {

	}
}