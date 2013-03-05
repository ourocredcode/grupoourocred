package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.CampoFormularioDao;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.FormulariosJanelaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.TipoDadoBdDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.FormulariosJanela;

@Resource
public class FormulariosjanelaController {

	private final Result result;
	private final Validator validator;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;	
	private final FormulariosJanelaDao formulariosJanelaDao;

	public FormulariosjanelaController(Result result,Validator validator,EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,
										FormulariosJanelaDao formulariosJanelaDao){

		this.result = result;
		this.validator = validator;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.formulariosJanelaDao = formulariosJanelaDao;

	}	

	@Get
	@Public
	@Path("/formulariosjanela/cadastro")
	public void cadastro(){

	}

	@Post
	@Public
	@Path("/formulariosjanela/salva")
	public void salva(FormulariosJanela formulariosJanela){

	}
	
	@Get @Path("/formulariosjanela/busca.json")
	@Public
	public void formulariosjanela(Long empresa_id, Long organizacao_id, String nome){
		result.use(Results.json()).withoutRoot().from(formulariosJanelaDao.buscaFomulariosJanela(empresa_id, organizacao_id, nome)).serialize();
	}

}