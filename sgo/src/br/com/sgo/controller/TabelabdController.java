package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.TabelaBdDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.TabelaBd;

@Resource
public class TabelabdController {
	private final Result result;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;	
	private final TabelaBdDao tabelaBdDao;
	private final Validator validator;
	
	public TabelabdController(Result result, Validator validator,EmpresaDao empresaDao, OrganizacaoDao organizacaoDao, TabelaBdDao tabelaBdDao){
		this.result=result;
		this.empresaDao=empresaDao;
		this.organizacaoDao=organizacaoDao;
		this.tabelaBdDao=tabelaBdDao;
		this.validator=validator;
	}
	
	@Get
	@Public
	@Path("/tabelabd/cadastro")
	public void cadastro(){
		
	}

	@Post
	@Public
	@Path("/tabelabd/salva")
	public void salva(TabelaBd tabelaBd){

		validator.validate(tabelaBd);
		validator.onErrorUsePageOf(this).cadastro();

		String mensagem = "";

		try {

			tabelaBd.setEmpresa(this.empresaDao.load(tabelaBd.getEmpresa().getEmpresa_id()));		
			tabelaBd.setOrganizacao(this.organizacaoDao.load(tabelaBd.getOrganizacao().getOrganizacao_id()));

			this.tabelaBdDao.beginTransaction();
			this.tabelaBdDao.adiciona(tabelaBd);
			this.tabelaBdDao.commit();

			mensagem = "Tabela BD " + tabelaBd.getNome() + " adicionado com sucesso";

		} catch(Exception e) {
			if (e.getCause().toString().indexOf("IX_TABELABD_NOMETABELABD") != -1){
				mensagem = "Erro: Tabela Bd " + tabelaBd.getNome() + " já existente.";
			} else {
				mensagem = "Erro ao adicionar Tabela Bd:";
			}
		}

		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}
	
	

}