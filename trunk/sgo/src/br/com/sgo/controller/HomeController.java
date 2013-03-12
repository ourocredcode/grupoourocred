package br.com.sgo.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Collection;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.com.sgo.dao.UsuarioDao;
import br.com.sgo.dao.UsuarioPerfilDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Perfil;
import br.com.sgo.modelo.Usuario;

@Resource
public class HomeController {

	private final Result result;
	private final UsuarioDao usuarioDao;
	private final UsuarioPerfilDao usuarioPerfilDao;
	private final UsuarioInfo usuarioInfo;
	private final Validator validator;

	public HomeController(Result result, UsuarioDao usuarioDao, UsuarioInfo usuarioInfo, Validator validator, UsuarioPerfilDao usuarioPerfilDao) {
		this.result = result;
		this.usuarioDao = usuarioDao;
		this.usuarioPerfilDao = usuarioPerfilDao;
		this.usuarioInfo = usuarioInfo;
		this.validator = validator;
	}

	@Get
	@Public
	public void login() {

	}
	
	@Get
	@Path("/home")
	public void home() {

	}	

	@Post
	@Public
	public void login(String login, String password) {

		Long usuario_id = usuarioDao.find(login, password).getUsuario_id();

		final Usuario currentUsuario = usuarioDao.load(usuario_id);

		validator.checking(new Validations() {{
			that(currentUsuario, is(notNullValue()), "login", "invalid_login_or_password");
		}});

		validator.onErrorUsePageOf(this).login();

		usuarioInfo.login(currentUsuario);

		result.redirectTo(this).perfis(this.usuarioPerfilDao.buscaUsuarioPerfilAcesso(usuarioInfo.getUsuario()));

	}

	@Get
	public void perfis(Collection<Perfil> perfis){
		result.include("perfis",perfis);
	}

	public void logout() {
		usuarioInfo.logout(); 
		result.redirectTo(this).login();
	}

	@Get
	public void msg(){

	}
	
	@Post
	@Path("/home/empresas")
	public void empresas(Long perfil_id){

		result.include("empresas",this.usuarioPerfilDao.buscaEmpresaPerfilAcesso(perfil_id,usuarioInfo.getUsuario().getUsuario_id()) );

	}
	
	@Post
	@Path("/home/organizacoes")
	public void organizacoes(Long perfil_id,Long empresa_id){

		result.include("organizacoes",this.usuarioPerfilDao.buscaOrganizacaoPerfilAcesso(perfil_id, empresa_id, usuarioInfo.getUsuario().getUsuario_id()));

	}

}