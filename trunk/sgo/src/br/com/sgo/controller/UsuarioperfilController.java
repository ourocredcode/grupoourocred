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
import br.com.sgo.modelo.Usuario;

@Resource
public class UsuarioperfilController {

	private final Result result;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final UsuarioDao usuarioDao;

	public UsuarioperfilController(Result result,EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,UsuarioDao usuarioDao){

		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.usuarioDao = usuarioDao;
		this.result = result;

	}

	@Get
	@Public
	@Path("/usuarioperfil/cadastro")
	public void cadastro() {

	}

	@Post
	@Public
	@Path("/usuarioperfil/salva")
	public void salva(Usuario usuario){

	}
}