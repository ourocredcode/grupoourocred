package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.sgo.dao.ColunaBdDao;
import br.com.sgo.dao.ElementoBdDao;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.TabelaBdDao;
import br.com.sgo.dao.TipoDadoBdDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.ColunaBd;

@Resource
public class ColunabdController {

	private final Result result;
	private final Validator validator;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final TabelaBdDao tabelaBdDao;
	private final ColunaBdDao colunaBdDao;
	private final ElementoBdDao elementoBdDao;
	private final TipoDadoBdDao tipoDadoBdDao;

	public ColunabdController(Result result, Validator validator, ColunaBdDao colunaBdDao, EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,
			TabelaBdDao tabelaBdDao,ElementoBdDao elementoBdDao, TipoDadoBdDao tipoDadoBdDao){

		this.result = result;
		this.validator=validator;
		this.colunaBdDao = colunaBdDao;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.tabelaBdDao = tabelaBdDao;
		this.elementoBdDao = elementoBdDao;
		this.tipoDadoBdDao = tipoDadoBdDao;
	}

	@Get
	@Public
	@Path("/colunabd/cadastro")
	public void cadastro(){

	}

	
	@Post
	@Public
	@Path("/colunabd/salva")
	public void salva(ColunaBd colunaBd){

		validator.validate(colunaBd);
		validator.onErrorUsePageOf(this).cadastro();

		String mensagem = "";

		try {

			colunaBd.setEmpresa(this.empresaDao.load(colunaBd.getEmpresa().getEmpresa_id()));		
			colunaBd.setOrganizacao(this.organizacaoDao.load(colunaBd.getOrganizacao().getOrganizacao_id()));
			colunaBd.setTabelaBd(this.tabelaBdDao.load(colunaBd.getTabelaBd().getTabelaBd_id()));

			this.colunaBdDao.beginTransaction();
			this.colunaBdDao.adiciona(colunaBd);
			this.colunaBdDao.commit();

			mensagem = "Coluna BD " + colunaBd.getNome() + " adicionado com sucesso";			
			
		} catch(Exception e) {
			
			this.colunaBdDao.rollback();

			if (e.getCause().toString().indexOf("IX_COLUNABD_ELEMENTOBDID") != -1){
				mensagem = "Erro: Elemento Bd " + colunaBd.getNome() + " já existente.";
			} else {
				mensagem = "Erro ao adicionar Tabela Bd:";
			}

		}

		this.colunaBdDao.clear();
		this.colunaBdDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}
}
