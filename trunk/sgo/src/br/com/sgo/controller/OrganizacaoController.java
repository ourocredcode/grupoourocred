package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.OrganizacaoDao;

@Resource
public class OrganizacaoController {

	private final Result result;
	private final OrganizacaoDao organizacaoDao;
	
	public OrganizacaoController(Result result, OrganizacaoDao organizacaoDao){

		this.organizacaoDao = organizacaoDao;
		this.result = result;

	}	
	
	@Get
	@Path("/organizacao/busca.json")
	public void organizacoes(Long empresa_id, String org_nome){

		result.use(Results.json()).withoutRoot().from(organizacaoDao.buscaOrganizacoes(empresa_id, org_nome)).serialize();

	}	
	
}
