package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.ElementoBdDao;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.ElementoBd;

@Resource
public class ElementobdController {

	private final Result result;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final ElementoBdDao elementoBdDao;
	private final Validator validator;

	public ElementobdController(Result result,Validator validator,EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,ElementoBdDao elementoBdDao){

		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.elementoBdDao = elementoBdDao;
		this.result = result;
		this.validator = validator;

	}	

	@Get
	@Public
	@Path("/elementobd/cadastro")
	public void cadastro(){
		
	}

	@Post
	@Public
	@Path("/elementobd/salva")
	public void salva(ElementoBd elementoBd){

		validator.validate(elementoBd);
		validator.onErrorUsePageOf(this).cadastro();

		String mensagem = "";

		try {

			elementoBd.setEmpresa(this.empresaDao.load(elementoBd.getEmpresa().getEmpresa_id()));
			elementoBd.setOrganizacao(this.organizacaoDao.load(elementoBd.getOrganizacao().getOrganizacao_id()));

			this.elementoBdDao.beginTransaction();
			this.elementoBdDao.adiciona(elementoBd);
			this.elementoBdDao.commit();

			mensagem = "Elemento BD " + elementoBd.getNome() + " adicionado com sucesso";

		} catch(Exception e) {

			this.elementoBdDao.rollback();

			if (e.getCause().toString().indexOf("IX_ELEMENTOBD_NOMECOLUNABD") != -1){
				mensagem = "Erro: Elemento Bd " + elementoBd.getNome() + " já existente.";
			} else {
				mensagem = "Erro ao adicionar Tabela Bd:";
			}

		}

		this.elementoBdDao.clear();
		this.elementoBdDao.close();

		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

	@Get @Path("/elementobd/busca.json")
	@Public
	public void empresas(String nomecolunabd){
		result.use(Results.json()).withoutRoot().from(elementoBdDao.buscaElementos(nomecolunabd)).serialize();
	}
}