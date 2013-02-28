package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.TabelaBdDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.TabelaBd;

@Resource
public class TabelabdController {
	private final Result result;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;	
	private final TabelaBdDao tabelaBdDao;
	
	public TabelabdController(Result result, EmpresaDao empresaDao, OrganizacaoDao organizacaoDao, TabelaBdDao tabelaBdDao){
		this.result=result;
		this.empresaDao=empresaDao;
		this.organizacaoDao=organizacaoDao;
		this.tabelaBdDao=tabelaBdDao;		
	}
	
	@Get
	@Public
	@Path("/tabelabd/cadastro")
	public void cadastro() {

		Empresa empresa = this.empresaDao.load(1L);
		Organizacao organizacao = this.organizacaoDao.load(1L);
		
		result.include("empresa",empresa);
		result.include("organizacao",organizacao);
	}

	@Post
	@Public
	@Path("/tabelabd/salva")
	public void salva(TabelaBd tabelaBd){

		tabelaBd.setEmpresa(this.empresaDao.load(tabelaBd.getEmpresa().getEmpresa_id()));
		tabelaBd.setOrganizacao(this.organizacaoDao.load(tabelaBd.getOrganizacao().getOrganizacao_id()));		

		this.tabelaBdDao.beginTransaction();
		this.tabelaBdDao.adiciona(tabelaBd);
		this.tabelaBdDao.commit();

		this.result.nothing();
	}
	
	@Get
	@Public
	@Path("/tabelabd/configuracao")
	public void configuracao(){
		
	}

}