package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.ColunaBdDao;
import br.com.sgo.dao.ElementoBdDao;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.TabelaBdDao;
import br.com.sgo.dao.TipoDadoBdDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.ColunaBd;
import br.com.sgo.modelo.ElementoBd;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.TabelaBd;
import br.com.sgo.modelo.TipoDadoBd;

@Resource
public class ColunabdController {

	private final Result result;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final ColunaBdDao colunaBdDao;
	private final TabelaBdDao tabelaBdDao;
	private final TipoDadoBdDao tipoDadoBdDao;
	private final ElementoBdDao elementoBdDao;

	public ColunabdController(Result result,EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,ColunaBdDao colunaBdDao,
			TabelaBdDao tabelaBdDao,TipoDadoBdDao tipoDadoBdDao,ElementoBdDao elementoBdDao){

		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.colunaBdDao = colunaBdDao;
		this.result = result;
		this.tabelaBdDao = tabelaBdDao;
		this.tipoDadoBdDao = tipoDadoBdDao;
		this.elementoBdDao = elementoBdDao;

	}
	
	@Get
	@Public
	@Path("/colunabd/cadastro")
	public void cadastro() {

		Empresa empresa = this.empresaDao.load(1L);
		Organizacao organizacao = this.organizacaoDao.load(1L);
		TabelaBd tabelaBd = this.tabelaBdDao.load(1L);
		TipoDadoBd tipoDadoBd = this.tipoDadoBdDao.load(1L);
		ElementoBd elementoBd = this.elementoBdDao.load(1L);

		result.include("empresa",empresa);
		result.include("organizacao",organizacao);
		result.include("tabelaBd",tabelaBd);
		result.include("tipoDadoBd",tipoDadoBd);
		result.include("elementoBd",elementoBd);

	}

	@Post
	@Public
	@Path("/colunabd/salva")
	public void salva(ColunaBd colunaBd){

		colunaBd.setEmpresa(this.empresaDao.load(colunaBd.getEmpresa().getEmpresa_id()));
		colunaBd.setOrganizacao(this.organizacaoDao.load(colunaBd.getOrganizacao().getOrganizacao_id()));
		colunaBd.setTabelaBd(this.tabelaBdDao.load(colunaBd.getTabelaBd().getTabelabd_id()));
		colunaBd.setTipoDadoBd(this.tipoDadoBdDao.load(colunaBd.getTipoDadoBd().getTipodadobd_id()));
		colunaBd.setElementoBd(this.elementoBdDao.load(colunaBd.getElementoBd().getElementobd_id()));

		this.colunaBdDao.beginTransaction();
		this.colunaBdDao.adiciona(colunaBd);
		this.colunaBdDao.commit();

		this.result.nothing();

	}

}
