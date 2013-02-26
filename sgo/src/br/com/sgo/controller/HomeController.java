package br.com.sgo.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.com.sgo.dao.UsuarioDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Usuario;

@Resource
public class HomeController {

	private final Result result;
	private final UsuarioDao usuarioDao;
	private final UsuarioInfo usuarioInfo;
	private final Validator validator;

	public HomeController(Result result, UsuarioDao usuarioDao, UsuarioInfo usuarioInfo, Validator validator) {
		this.result = result;
		this.usuarioDao = usuarioDao;
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

		result.redirectTo(this).home();

	}

	public void logout() {

		usuarioInfo.logout(); 
		result.redirectTo(this).login();

	}

	@Get
	@Path("/home")
	public void home() {

	}	

}
