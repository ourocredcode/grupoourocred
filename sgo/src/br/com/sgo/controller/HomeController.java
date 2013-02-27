package br.com.sgo.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.ArrayList;
import java.util.List;

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
import br.com.sgo.modelo.Usuario;
import br.com.sgo.modelo.UsuarioPerfil;

@Resource
public class HomeController {

	private final Result result;
	private final UsuarioDao usuarioDao;
	private final UsuarioPerfilDao usuarioPerfilDao;
	private final UsuarioInfo usuarioInfo;
	private final Validator validator;

	private List<UsuarioPerfil> usuarioPerfis = new ArrayList<UsuarioPerfil>();

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

	@Post
	@Public
	public void login(String login, String password) {

		final Usuario currentUsuario = usuarioDao.find(login, password);

		validator.checking(new Validations() {{
			that(currentUsuario, is(notNullValue()), "login", "invalid_login_or_password");
		}});

		validator.onErrorUsePageOf(this).login();

		usuarioInfo.login(currentUsuario);

		//this.usuarioPerfilDao.buscaPerfilPorUsuario(usuarioInfo.getUsuario())
		
		String perfis = "ADMINISTRADOR";
		
		result.include("perfis",perfis);

		result.include("msg","OK").redirectTo(this).msg();

	}

	public void logout() {
		usuarioInfo.logout(); 
		result.redirectTo(this).login();
	}

	@Get
	@Path("/home")
	public void home() {

	}	
	
	@Get
	public void msg(){

	}

}