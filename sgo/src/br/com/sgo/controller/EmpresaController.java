package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.EmpresaDao;

@Resource
public class EmpresaController {

	private final Result result;
	private final EmpresaDao empresaDao;

	public EmpresaController(Result result,EmpresaDao empresaDao){

		this.empresaDao = empresaDao;
		this.result = result;

	}

	@Get
	@Path("/empresa/busca.json")
	public void empresas(String n){
		result.use(Results.json()).withoutRoot().from(empresaDao.buscaEmpresas(n)).serialize();
	}

}
