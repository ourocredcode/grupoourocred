package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.sgo.interceptor.Public;

@Resource
public class HomeController {

	private final Result result;
	private final Validator validator;

	public HomeController(Result result,Validator validator) {
		this.result = result;

		this.validator = validator;
	}

	@Get
	@Public
	public void login() {

	}

	@Get
	@Path("/")
	public void home() {

	}

}
