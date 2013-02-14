package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.ColunaBdDao;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.ColunaBd;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;

@Resource
public class ColunaBdController {

	private final Result result;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final ColunaBdDao colunaBdDao;

	public ColunaBdController(Result result,EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,ColunaBdDao colunaBdDao){

		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.colunaBdDao = colunaBdDao;
		this.result = result;

	}
	
	@Get
	@Public
	@Path("/colunabd/cadastro")
	public void cadastro() {

		Empresa empresa = this.empresaDao.load(1L);
		Organizacao organizacao = this.organizacaoDao.load(1L);

		result.include("empresa",empresa);
		result.include("organizacao",organizacao);

	}

	@Post
	@Public
	@Path("/elementobd/salva")
	public void salva(ColunaBd colunaBd){

		colunaBd.setEmpresa(this.empresaDao.load(colunaBd.getEmpresa().getEmpresa_id()));
		colunaBd.setOrganizacao(this.organizacaoDao.load(colunaBd.getOrganizacao().getOrganizacao_id()));

		this.colunaBdDao.beginTransaction();
		this.colunaBdDao.adiciona(colunaBd);
		this.colunaBdDao.commit();

		this.result.nothing();

	}

}
