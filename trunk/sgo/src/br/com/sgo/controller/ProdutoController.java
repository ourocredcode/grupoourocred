package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.ProdutoDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.Produto;

@Resource
public class ProdutoController {

	private final Result result;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final ProdutoDao produtoDao;
	private final Validator validator;

	public ProdutoController(Result result,Validator validator,EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,ProdutoDao produtoDao){

		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.produtoDao = produtoDao;
		this.result = result;
		this.validator = validator;

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

}