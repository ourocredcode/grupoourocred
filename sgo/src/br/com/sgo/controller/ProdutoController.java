package br.com.sgo.controller;

import java.util.Calendar;
import java.util.Collection;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.CategoriaProdutoDao;
import br.com.sgo.dao.GrupoProdutoDao;
import br.com.sgo.dao.ProdutoDao;
import br.com.sgo.dao.SubGrupoProdutoDao;
import br.com.sgo.dao.TipoProdutoDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Produto;

@Resource
public class ProdutoController {

	private final Result result;
	private final ProdutoDao produtoDao;	
	private final SubGrupoProdutoDao subGrupoProdutoDao;
	private final GrupoProdutoDao grupoProdutoDao;
	private final CategoriaProdutoDao categoriaProdutoDao;
	private final TipoProdutoDao tipoProdutoDao;
	private final UsuarioInfo usuarioInfo;
	private final Validator validator;
	
	private Produto produto;
	private Calendar dataAtual = Calendar.getInstance();

	private Collection<Produto> produtos;

	public ProdutoController(Result result, Validator validator, UsuarioInfo usuarioInfo, ProdutoDao produtoDao, GrupoProdutoDao grupoProdutoDao, SubGrupoProdutoDao subGrupoProdutoDao
			,CategoriaProdutoDao categoriaProdutoDao, TipoProdutoDao tipoProdutoDao){

		this.result = result;
		this.validator = validator;
		this.usuarioInfo = usuarioInfo;
		this.produtoDao = produtoDao;
		this.grupoProdutoDao = grupoProdutoDao;
		this.subGrupoProdutoDao = subGrupoProdutoDao;
		this.categoriaProdutoDao = categoriaProdutoDao;
		this.tipoProdutoDao = tipoProdutoDao;

	}	

	@Get
	@Path("/produto/cadastro")
	public void cadastro(){

		result.include("gruposProduto",this.grupoProdutoDao.buscaAllGruposProduto());
		result.include("tiposProduto",this.tipoProdutoDao.buscaAllTiposProdutoByEmpresaOrganizacao());
		result.include("categoriasProduto",this.categoriaProdutoDao.buscaAllCategoriaProduto());

	}

	@Post
	@Public
	@Path("/produto/salva")
	public void salva(Produto produto){
		
		validator.validate(produto);
		validator.onErrorUsePageOf(this).cadastro();
		
		String mensagem = "";

		try {
			
			if(this.produtoDao.buscaProdutoByEmpresaOrgCategoriaGrupoSubGrupoTipoNome(usuarioInfo.getEmpresa().getEmpresa_id(), usuarioInfo.getOrganizacao().getOrganizacao_id()
					, produto.getCategoriaProduto().getCategoriaProduto_id(), produto.getGrupoProduto().getGrupoProduto_id(), produto.getSubGrupoProduto().getSubGrupoProduto_id()
					, produto.getTipoProduto().getTipoProduto_id(), produto.getNome()) == null) {

				this.produto.setCreated(dataAtual);
				this.produto.setUpdated(dataAtual);
	
				this.produto.setCreatedBy(usuarioInfo.getUsuario());
				this.produto.setUpdatedBy(usuarioInfo.getUsuario());
				
				this.produto.setChave(produto.getNome());
				this.produto.setDescricao(produto.getNome());
	
				this.produtoDao.beginTransaction();
				this.produtoDao.adiciona(produto);
				this.produtoDao.commit();

				mensagem = "Produto " + produto.getNome() + " adicionado com sucesso";

			} else {
	
				mensagem = "Erro: Produto " + produto.getNome() + " já cadastrado.";
	
			} 
	
			} catch(Exception e) {
		
				mensagem = "Erro: Falha ao adicionar o Produto.";
		
			} finally{
		
				this.produtoDao.clear();
				this.produtoDao.close();
		
			}
		
			result.include("notice", mensagem);			
			result.redirectTo(this).cadastro();

	}
	
	@Post
	@Path("/produto/produtos")
	public void produtos(Long bancoId) {

		produtos = produtoDao.buscaProdutosByBanco(bancoId);
		result.include("produtos", produtos);
		result.include("gruposProduto",this.grupoProdutoDao.buscaAllGruposProduto());

	}
	
	@Get @Path("/produto/busca.json")
	@Public
	public void produtos(Long empresa_id, Long organizacao_id, String nome){
		result.use(Results.json()).withoutRoot().from(produtoDao.buscaProdutos(empresa_id, organizacao_id, nome)).serialize();
	}
	
	@Post
	@Path("/produto/subgrupoprodutos")
	public void subgrupoprodutos(Long grupoProduto_id){

		result.include("subGrupoProdutos",this.subGrupoProdutoDao.buscaSubGrupoProdutoByGrupoProduto(grupoProduto_id));

	}

}