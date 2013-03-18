package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.ProdutoDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.Produto;

@Resource
public class ProdutoController {

	private final Result result;
	private final ProdutoDao produtoDao;


	public ProdutoController(Result result,ProdutoDao produtoDao){
		this.produtoDao = produtoDao;
		this.result = result;
	}	

	@Get
	@Public
	@Path("/produto/cadastro")
	public void cadastro(){
		
	}

	@Post
	@Public
	@Path("/produto/salva")
	public void salva(Produto produto){

		String mensagem = "";

		try {

			this.produtoDao.beginTransaction();
			this.produtoDao.adiciona(produto);
			this.produtoDao.commit();

			mensagem = "Produto " + produto.getNome() + " adicionado com sucesso";

		} catch(Exception e) {

			this.produtoDao.rollback();

			if (e.getCause().toString().indexOf("PK_PRODUTO") != -1){
				mensagem = "Erro: Produto " + produto.getNome() + " já existente.";
			} else {
				mensagem = "Erro ao adicionar Tabela Bd:";
			}

		}

		this.produtoDao.clear();
		this.produtoDao.close();

		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}
	
	@Get @Path("/produto/busca.json")
	@Public
	public void produtos(Long empresa_id, Long organizacao_id, String nome){
		result.use(Results.json()).withoutRoot().from(produtoDao.buscaProdutos(empresa_id, organizacao_id, nome)).serialize();
	}

}