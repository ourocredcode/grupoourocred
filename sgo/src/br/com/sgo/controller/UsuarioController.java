package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.UsuarioDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Usuario;

@Resource
public class UsuarioController {

	private final Result result;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final UsuarioDao usuarioDao;

	public UsuarioController(Result result,EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,UsuarioDao usuarioDao){

		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.usuarioDao = usuarioDao;
		this.result = result;


	}
	
	@Get
	@Public
	@Path("/usuario/cadastro")
	public void cadastro() {

		Empresa empresa = this.empresaDao.load(1L);
		Organizacao organizacao = this.organizacaoDao.load(1L);

		result.include("empresa",empresa);
		result.include("organizacao",organizacao);

	}

	@Post
	@Public
	@Path("/usuario/salva")
	public void salva(Usuario usuario){

		usuario.setEmpresa(this.empresaDao.load(usuario.getEmpresa().getEmpresa_id()));
		usuario.setOrganizacao(this.organizacaoDao.load(usuario.getOrganizacao().getOrganizacao_id()));

		this.usuarioDao.beginTransaction();
		this.usuarioDao.adiciona(usuario);
		this.usuarioDao.commit();

		this.result.nothing();

	}

}