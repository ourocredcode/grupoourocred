package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.ProdutoBancoDao;
import br.com.sgo.modelo.ProdutoBanco;

@Resource
public class ProdutobancoController {

	private final Result result;
	private final ProdutoBancoDao produtoBancoDao;


	public ProdutobancoController(Result result,ProdutoBancoDao produtoBancoDao){
		this.produtoBancoDao = produtoBancoDao;
		this.result = result;
	}	

	@Get
	@Path("/produtobanco/cadastro")
	public void cadastro(){
		
	}

	@Post
	@Path("/produtobanco/salva")
	public void salva(ProdutoBanco produtoBanco){
		
		String mensagem = "";

		try {

			this.produtoBancoDao.insert(produtoBanco);

			mensagem = "Produto Banco adicionado com sucesso";

		} catch(Exception e) {

			System.out.println(e);

			if (e.getMessage().indexOf("IX_PRODUTOBANCO_TABELA") != -1){
				mensagem = "Erro: Organização " + produtoBanco.getOrganizacao().getNome() + " já cadastrado para a empresa " +
						"" + produtoBanco.getEmpresa().getNome() + " e tabela " +
						"" + produtoBanco.getTabela().getNome() + ".";
			}

		} 

		this.produtoBancoDao.clear();
		this.produtoBancoDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

}