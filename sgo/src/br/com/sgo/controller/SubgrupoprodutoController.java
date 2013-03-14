package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.SubGrupoProdutoDao;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.SubGrupoProduto;

@Resource
public class SubgrupoprodutoController {

	private final Result result;
	private final Validator validator;
	private final SubGrupoProdutoDao subGrupoProdutoDao;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;

	public SubgrupoprodutoController(Result result,Validator validator, EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,SubGrupoProdutoDao subGrupoProdutoDao){
		this.subGrupoProdutoDao = subGrupoProdutoDao;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;		
		this.result = result;
		this.validator = validator;
	}

	@Get
	@Public
	@Path("/subgrupoproduto/cadastro")
	public void cadastro(){
		result.include("subGrupoProduto",this.subGrupoProdutoDao.listaTudo("ASC","nome"));
	}

	@Post
	@Public
	@Path("/subgrupoproduto/salva")
	public void salva(SubGrupoProduto subGrupoProduto){

		validator.validate(subGrupoProduto);
		validator.onErrorUsePageOf(this).cadastro();

		String mensagem = "";

		try {

			subGrupoProduto.setEmpresa(this.empresaDao.load(subGrupoProduto.getEmpresa().getEmpresa_id()));		
			subGrupoProduto.setOrganizacao(this.organizacaoDao.load(subGrupoProduto.getOrganizacao().getOrganizacao_id()));

			this.subGrupoProdutoDao.beginTransaction();
			this.subGrupoProdutoDao.adiciona(subGrupoProduto);
			this.subGrupoProdutoDao.commit();

			mensagem = "Grupo de Produto " + subGrupoProduto.getNome() + " adicionado com sucesso";			

		} catch(Exception e) {

			this.subGrupoProdutoDao.rollback();

			if (e.getCause().toString().indexOf("IX_SUBGRUPOPRODUTO") != -1){
				mensagem = "Erro: SubGrupo de Produto " + subGrupoProduto.getNome() + " já existente.";
			} else {
				mensagem = "Erro ao adicionar SubGrupo de Produto";
			}

		}

		this.subGrupoProdutoDao.clear();
		this.subGrupoProdutoDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

	@Get @Path("/subgrupoproduto/busca.json")
	@Public
	public void subgrupoproduto(Long empresa_id, Long organizacao_id, String nome){
		result.use(Results.json()).withoutRoot().from(subGrupoProdutoDao.buscaSubGrupoProdutos(empresa_id, organizacao_id, nome)).serialize();
	}

}