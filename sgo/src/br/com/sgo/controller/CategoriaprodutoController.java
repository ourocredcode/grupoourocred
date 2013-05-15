package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.CategoriaProdutoDao;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.CategoriaProduto;

@Resource
public class CategoriaprodutoController {

	private final Result result;
	private final Validator validator;
	private final CategoriaProdutoDao categoriaProdutoDao;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;

	public CategoriaprodutoController(Result result,Validator validator, EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,CategoriaProdutoDao categoriaProdutoDao){
		this.categoriaProdutoDao = categoriaProdutoDao;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;		
		this.result = result;
		this.validator = validator;
	}

	@Get
	@Public
	@Path("/categoriaproduto/cadastro")
	public void cadastro(){

	}

	@Post
	@Public
	@Path("/categoriaproduto/salva")
	public void salva(CategoriaProduto categoriaProduto){

		validator.validate(categoriaProduto);
		validator.onErrorUsePageOf(this).cadastro();

		String mensagem = "";

		try {

			categoriaProduto.setEmpresa(this.empresaDao.load(categoriaProduto.getEmpresa().getEmpresa_id()));		
			categoriaProduto.setOrganizacao(this.organizacaoDao.load(categoriaProduto.getOrganizacao().getOrganizacao_id()));

			this.categoriaProdutoDao.beginTransaction();
			this.categoriaProdutoDao.adiciona(categoriaProduto);
			this.categoriaProdutoDao.commit();

			mensagem = "Grupo de Produto " + categoriaProduto.getNome() + " adicionado com sucesso";			

		} catch(Exception e) {

			this.categoriaProdutoDao.rollback();

			if (e.getCause().toString().indexOf("IX_GRUPOPRODUTO_EMPORGNOME") != -1){
				mensagem = "Erro: Grupo de Produto " + categoriaProduto.getNome() + " já existente.";
			} else {
				mensagem = "Erro ao adicionar Grupo de Produto";
			}

		}

		this.categoriaProdutoDao.clear();
		this.categoriaProdutoDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

	@Get @Path("/categoriaproduto/busca.json")
	@Public
	public void categoriaproduto(Long empresa_id, Long organizacao_id, String nome){
		result.use(Results.json()).withoutRoot().from(categoriaProdutoDao.buscaCategoriaProdutos(empresa_id, organizacao_id, nome)).serialize();
	}

}