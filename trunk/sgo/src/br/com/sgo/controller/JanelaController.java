package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.JanelaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Janela;
import br.com.sgo.modelo.Organizacao;

@Resource
public class JanelaController {

	private final Result result;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final JanelaDao janelaDao;

	public JanelaController(Result result,EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,JanelaDao janelaDao){

		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.janelaDao = janelaDao;
		this.result = result;

	}
	
	@Get
	@Public
	@Path("/janela/cadastro")
	public void cadastro() {

		Empresa empresa = this.empresaDao.load(1L);
		Organizacao organizacao = this.organizacaoDao.load(1L);

		result.include("empresa",empresa);
		result.include("organizacao",organizacao);

	}

	@Post
	@Public
	@Path("/janela/salva")
	public void salva(Janela janela){

		janela.setEmpresa(this.empresaDao.load(janela.getEmpresa().getEmpresa_id()));
		janela.setOrganizacao(this.organizacaoDao.load(janela.getOrganizacao().getOrganizacao_id()));

		this.janelaDao.beginTransaction();
		this.janelaDao.adiciona(janela);
		this.janelaDao.commit();

		this.result.nothing();

	}

}
