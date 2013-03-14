package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.TipoProdutoDao;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.TipoProduto;

@Resource
public class TipoprodutoController {

	private final Result result;
	private final Validator validator;
	private final TipoProdutoDao tipoProdutoDao;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;

	public TipoprodutoController(Result result,Validator validator, EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,TipoProdutoDao tipoProdutoDao){
		this.tipoProdutoDao = tipoProdutoDao;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;		
		this.result = result;
		this.validator = validator;
	}

	@Get
	@Public
	@Path("/tipoproduto/cadastro")
	public void cadastro(){
		result.include("tipoProduto",this.tipoProdutoDao.listaTudo("ASC","nome"));
	}

	@Post
	@Public
	@Path("/tipoproduto/salva")
	public void salva(TipoProduto tipoProduto){

		validator.validate(tipoProduto);
		validator.onErrorUsePageOf(this).cadastro();

		String mensagem = "";

		try {

			tipoProduto.setEmpresa(this.empresaDao.load(tipoProduto.getEmpresa().getEmpresa_id()));		
			tipoProduto.setOrganizacao(this.organizacaoDao.load(tipoProduto.getOrganizacao().getOrganizacao_id()));

			this.tipoProdutoDao.beginTransaction();
			this.tipoProdutoDao.adiciona(tipoProduto);
			this.tipoProdutoDao.commit();

			mensagem = "Tipo de Produto " + tipoProduto.getNome() + " adicionado com sucesso";			

		} catch(Exception e) {

			this.tipoProdutoDao.rollback();

			if (e.getCause().toString().indexOf("IX_TIPOPRODUTO_EMPORGNOME") != -1){
				mensagem = "Erro: Tipo de Produto " + tipoProduto.getNome() + " já existente.";
			} else {
				mensagem = "Erro ao adicionar Tipo de Produto";
			}

		}

		this.tipoProdutoDao.clear();
		this.tipoProdutoDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

	@Get @Path("/tipoproduto/busca.json")
	@Public
	public void tipoproduto(Long empresa_id, Long organizacao_id, String nome){
		result.use(Results.json()).withoutRoot().from(tipoProdutoDao.buscaTipoProdutos(empresa_id, organizacao_id, nome)).serialize();
	}

}