package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.sgo.dao.CampoFormularioDao;
import br.com.sgo.dao.EmpresaDao;
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
	private final TipoDadoBdDao tipoDadoBdDao;
	private final CampoFormularioDao campoFormularioDao;

	public FormulariosjanelaController(Result result,EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,TipoDadoBdDao tipoDadoBdDao,Validator validator,
									CampoFormularioDao campoFormularioDao){

		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.tipoDadoBdDao = tipoDadoBdDao;
		this.campoFormularioDao = campoFormularioDao;
		this.result = result;
		this.validator = validator;

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

}