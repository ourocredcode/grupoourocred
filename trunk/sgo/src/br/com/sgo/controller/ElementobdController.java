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
import br.com.sgo.modelo.ElementoBd;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;

@Resource
public class ElementobdController {

	private final Result result;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final ElementoBdDao elementoBdDao;

	public ElementobdController(Result result,EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,ElementoBdDao elementoBdDao){

		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.elementoBdDao = elementoBdDao;
		this.result = result;

	}	

	@Get
	@Public
	@Path("/elementobd/cadastro")
	public void cadastro() {

		Empresa empresa = this.empresaDao.load(1L);
		Organizacao organizacao = this.organizacaoDao.load(1L);

		result.include("empresa",empresa);
		result.include("organizacao",organizacao);

	}

	@Post
	@Public
	@Path("/elementobd/salva")
	public void salva(ElementoBd elementoBd){

		elementoBd.setEmpresa(this.empresaDao.load(elementoBd.getEmpresa().getEmpresa_id()));
		elementoBd.setOrganizacao(this.organizacaoDao.load(elementoBd.getOrganizacao().getOrganizacao_id()));

		this.elementoBdDao.beginTransaction();
		this.elementoBdDao.adiciona(elementoBd);
		this.elementoBdDao.commit();

		this.result.nothing();

	}
	
	@Get
	@Public
	@Path("/elementobd/teste")
	public void teste() {

		Empresa empresa = this.empresaDao.load(1L);
		Organizacao organizacao = this.organizacaoDao.load(1L);

		result.include("empresa",empresa);
		result.include("organizacao",organizacao);

	}

}