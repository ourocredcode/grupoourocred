package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.BancoDao;
import br.com.sgo.dao.WorkflowDao;
import br.com.sgo.dao.BancoProdutoDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Usuario;
import br.com.sgo.modelo.BancoProduto;

@Resource
public class BancoprodutoController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final BancoProdutoDao bancoProdutoDao;
	private final WorkflowDao workflowDao;
	private final BancoDao bancoDao;

	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;
	private Calendar dataAtual = Calendar.getInstance();

	public BancoprodutoController(Result result, Empresa empresa, Organizacao organizacao, Usuario usuario, UsuarioInfo usuarioInfo, WorkflowDao workflowDao, BancoProdutoDao bancoProdutoDao, BancoDao bancoDao) {

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.workflowDao = workflowDao;
		this.bancoProdutoDao = bancoProdutoDao;
		this.bancoDao = bancoDao;
		this.empresa = this.usuarioInfo.getEmpresa();
		this.organizacao = this.usuarioInfo.getOrganizacao();
		this.usuario = this.usuarioInfo.getUsuario();

	}

	@Get
	@Path("/bancoproduto/cadastro")
	public void cadastro() {

		result.include("bancoProdutos", this.bancoProdutoDao.buscaAllBancoProdutoByEmpresaOrganizacao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
		result.include("workflows", this.workflowDao.buscaWorkflowsByEmpresaOrganizacao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
		result.include("bancos", this.bancoDao.buscaAllBancos());

	}

	@Post
	@Path("/bancoproduto/salva")
	public void salva(BancoProduto bancoProduto) {

		String mensagem = "";

		try {

			if (this.bancoProdutoDao.buscaBancoProdutoByEmpresaOrganizacaoProdutoBancoWorkflow(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(),
					bancoProduto.getProduto().getProduto_id(), bancoProduto.getBanco().getBanco_id(), bancoProduto.getWorkflow().getWorkflow_id()) == null) {				

				bancoProduto.setCreated(dataAtual);
				bancoProduto.setUpdated(dataAtual);

				bancoProduto.setCreatedBy(usuario);
				bancoProduto.setUpdatedBy(usuario);

				bancoProduto.setIsActive(bancoProduto.getIsActive() == null ? false : true);
				bancoProduto.setIsWorkflow(bancoProduto.getIsWorkflow() == null ? false : true);
				
				this.bancoProdutoDao.beginTransaction();
				this.bancoProdutoDao.adiciona(bancoProduto);
				this.bancoProdutoDao.commit();
				
				mensagem = "Banco Produto adicionado com sucesso.";
				
			} else {
				mensagem = "Erro: Banco Produto já cadastrado.";
			} 

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar o Produto.";

		} finally{

			this.bancoProdutoDao.clear();
			this.bancoProdutoDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Post
	@Path("/bancoproduto/lista")
	public void lista(Long empresa_id, Long organizacao_id) {

		result.include("workflowsProdutoBanco", this.bancoProdutoDao.buscaAllBancoProdutoByEmpresaOrganizacao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));

	}

	@Get
	public void msg() {

	}
}