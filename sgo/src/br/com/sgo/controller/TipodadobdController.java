package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.ElementoBdDao;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.TipoDadoBd;

@Resource
public class TipodadobdController {

	private final Result result;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final ElementoBdDao elementoBdDao;

	public TipodadobdController(Result result,EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,ElementoBdDao elementoBdDao){

		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.elementoBdDao = elementoBdDao;
		this.result = result;

	}	

	@Get
	@Public
	@Path("/tipodadobd/cadastro")
	public void cadastro(){
		
	}

	@Post
	@Public
	@Path("/tipodadobd/salva")
	public void salva(TipoDadoBd tipoDadoBd){

	}

}