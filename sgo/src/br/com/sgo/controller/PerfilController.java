package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.PerfilDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Perfil;

@Resource
public class PerfilController {

	private final Result result;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final PerfilDao perfilDao;

	public PerfilController(Result result,EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,PerfilDao perfilDao){

		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.perfilDao = perfilDao;
		this.result = result;

	}
	
	@Get
	@Public
	@Path("/perfil/cadastro")
	public void cadastro() {

		Empresa empresa = this.empresaDao.load(1L);
		Organizacao organizacao = this.organizacaoDao.load(1L);

		result.include("empresa",empresa);
		result.include("organizacao",organizacao);

	}

	@Post
	@Public
	@Path("/perfil/salva")
	public void salva(Perfil perfil){

		perfil.setEmpresa(this.empresaDao.load(perfil.getEmpresa().getEmpresa_id()));
		perfil.setOrganizacao(this.organizacaoDao.load(perfil.getOrganizacao().getOrganizacao_id()));

		this.perfilDao.beginTransaction();
		this.perfilDao.adiciona(perfil);
		this.perfilDao.commit();

		this.result.nothing();

	}

}
