package br.com.sgo.controller;

import java.util.Calendar;
import java.util.Collection;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.CategoriaProdutoDao;
import br.com.sgo.dao.GrupoProdutoDao;
import br.com.sgo.dao.ProdutoDao;
import br.com.sgo.dao.SubGrupoProdutoDao;
import br.com.sgo.dao.TipoProdutoDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Produto;
import br.com.sgo.modelo.Usuario;

@Resource
public class ProdutoController {

	private final Result result;
	private final ProdutoDao produtoDao;	
	private final SubGrupoProdutoDao subGrupoProdutoDao;
	private final GrupoProdutoDao grupoProdutoDao;
	private final CategoriaProdutoDao categoriaProdutoDao;
	private final TipoProdutoDao tipoProdutoDao;
	private final UsuarioInfo usuarioInfo;

	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;
	
	private Calendar dataAtual = Calendar.getInstance();

	private Collection<Produto> produtos;

	public ProdutoController(Result result, Empresa empresa, Organizacao organizacao, Usuario usuario, UsuarioInfo usuarioInfo
			, ProdutoDao produtoDao, GrupoProdutoDao grupoProdutoDao, SubGrupoProdutoDao subGrupoProdutoDao
			,CategoriaProdutoDao categoriaProdutoDao, TipoProdutoDao tipoProdutoDao){

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.empresa = this.usuarioInfo.getEmpresa();
		this.organizacao = this.usuarioInfo.getOrganizacao();
		this.usuario = this.usuarioInfo.getUsuario();
		this.produtoDao = produtoDao;
		this.grupoProdutoDao = grupoProdutoDao;
		this.subGrupoProdutoDao = subGrupoProdutoDao;
		this.categoriaProdutoDao = categoriaProdutoDao;
		this.tipoProdutoDao = tipoProdutoDao;

	}	

	@Get
	@Path("/produto/cadastro")
	public void cadastro(){

		result.include("produtos",this.produtoDao.buscaAllProdutosByEmpOrg(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
		result.include("gruposProduto",this.grupoProdutoDao.buscaAllGruposProduto());
		result.include("tiposProduto",this.tipoProdutoDao.buscaAllTiposProdutoByEmpresaOrganizacao());
		result.include("categoriasProduto",this.categoriaProdutoDao.buscaAllCategoriaProduto());

	}

	@Post
	@Path("/produto/salva")
	public void salva(Produto produto){
		
		String mensagem = "";

		try {
			
			if(this.produtoDao.buscaProdutoByEmpresaOrgCategoriaGrupoSubGrupoTipoNome(usuarioInfo.getEmpresa().getEmpresa_id(), usuarioInfo.getOrganizacao().getOrganizacao_id(), produto.getCategoriaProduto().getCategoriaProduto_id()
					, produto.getGrupoProduto().getGrupoProduto_id(), produto.getSubGrupoProduto().getSubGrupoProduto_id(), produto.getTipoProduto().getTipoProduto_id(), produto.getNome()) == null) {

				produto.setCreated(dataAtual);
				produto.setUpdated(dataAtual);
	
				produto.setCreatedBy(usuario);
				produto.setUpdatedBy(usuario);

				produto.setIsActive(produto.getIsActive() == null ? false : true);
				produto.setIsProdutoContrato(produto.getIsProdutoContrato() == null ? false : true);
				
				produto.setChave(produto.getNome());
				produto.setDescricao(produto.getNome());
	
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
	public void produtos(Long empresa_id, Long organizacao_id, String nome){

		result.use(Results.json()).withoutRoot().from(produtoDao.buscaProdutos(empresa_id, organizacao_id, nome)).serialize();

	}
	
	@Post
	@Path("/produto/subgrupoprodutos")
	public void subgrupoprodutos(Long grupoProduto_id){

		result.include("subGrupoProdutos",this.subGrupoProdutoDao.buscaSubGrupoProdutoByGrupoProduto(grupoProduto_id));

	}

	@Get
	public void msg() {

	}

}