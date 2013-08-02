package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.BancoDao;
import br.com.sgo.dao.BancoProdutoDao;
import br.com.sgo.dao.ConvenioDao;
import br.com.sgo.dao.WorkflowDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.BancoProduto;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Usuario;

@Resource
public class BancoprodutoController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final BancoProdutoDao bancoProdutoDao;
	private final WorkflowDao workflowDao;
	private final BancoDao bancoDao;
	private final ConvenioDao convenioDao;

	private BancoProduto bancoProduto;
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;
	private Calendar dataAtual = Calendar.getInstance();

	public BancoprodutoController(Result result, BancoProduto bancoProduto, Empresa empresa, Organizacao organizacao, Usuario usuario, UsuarioInfo usuarioInfo
			, WorkflowDao workflowDao, BancoProdutoDao bancoProdutoDao, BancoDao bancoDao, ConvenioDao convenioDao) {

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.workflowDao = workflowDao;
		this.bancoProdutoDao = bancoProdutoDao;
		this.bancoDao = bancoDao;
		this.empresa = this.usuarioInfo.getEmpresa();
		this.organizacao = this.usuarioInfo.getOrganizacao();
		this.usuario = this.usuarioInfo.getUsuario();
		this.bancoProduto = bancoProduto;
		this.convenioDao = convenioDao;

	}

	@Get
	@Path("/bancoproduto/cadastro")
	public void cadastro() {

		result.include("bancoProdutos", this.bancoProdutoDao.buscaAllBancoProdutoByEmpresaOrganizacao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
		result.include("bancos", this.bancoDao.buscaAllBancos());
		result.include("workflows", this.workflowDao.buscaWorkflowsByEmpresaOrganizacao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
		result.include("convenios", this.convenioDao.buscaConvenioToFillComboByEmpOrg(1l,1l));

	}

	@Post
	@Path("/bancoproduto/salva")
	public void salva(BancoProduto bancoProduto) {

		String mensagem = "";

		try {

			if (this.bancoProdutoDao.buscaBancoProdutoByEmpresaOrganizacaoProdutoBancoWorkflow(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(),
					bancoProduto.getProduto().getProduto_id(), bancoProduto.getBanco().getBanco_id(), bancoProduto.getWorkflow().getWorkflow_id(), bancoProduto.getConvenio().getConvenio_id()) == null) {				

				bancoProduto.setCreated(dataAtual);
				bancoProduto.setUpdated(dataAtual);

				bancoProduto.setCreatedBy(usuario);
				bancoProduto.setUpdatedBy(usuario);

				bancoProduto.setIsConvenio(bancoProduto.getIsConvenio() == null ? false : true);
				bancoProduto.setIsWorkflow(bancoProduto.getIsWorkflow() == null ? false : true);
				bancoProduto.setIsActive(bancoProduto.getIsActive() == null ? false : true);

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
	@Path("/bancoproduto/altera")
	public void altera(BancoProduto bancoProduto) {

		String mensagem = "";

		this.bancoProduto = this.bancoProdutoDao.load(bancoProduto.getBancoProduto_id());

		this.bancoProduto.setUpdated(dataAtual);
		this.bancoProduto.setUpdatedBy(usuario);
		
		if(bancoProduto.getIsConvenio() != null){
			this.bancoProduto.setIsConvenio(bancoProduto.getIsConvenio() == false ? false : true);
		}

		if(bancoProduto.getIsWorkflow() != null){
			this.bancoProduto.setIsWorkflow(bancoProduto.getIsWorkflow() == false ? false : true);
		}

		if(bancoProduto.getIsActive() != null){
			this.bancoProduto.setIsActive(bancoProduto.getIsActive() == false ? false : true);
		}

		bancoProdutoDao.beginTransaction();		
		bancoProdutoDao.atualiza(this.bancoProduto);
		bancoProdutoDao.commit();

		mensagem = " Banco Produto alterado com sucesso.";

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Post
	@Path("/bancoproduto/lista")
	public void lista(Long empresa_id, Long organizacao_id) {

		result.include("bancoProdutos", this.bancoProdutoDao.buscaAllBancoProdutoByEmpresaOrganizacao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));

	}

	@Get
	public void msg() {

	}
}