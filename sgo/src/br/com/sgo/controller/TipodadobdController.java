package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.TipoDadoBdDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.TipoDadoBd;

@Resource
public class TipodadobdController {

	private final Result result;
	private final Validator validator;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final TipoDadoBdDao tipoDadoBdDao;

	public TipodadobdController(Result result,EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,TipoDadoBdDao tipoDadoBdDao,Validator validator){

		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.tipoDadoBdDao = tipoDadoBdDao;
		this.result = result;
		this.validator = validator;

	}	

	@Get
	@Public
	@Path("/tipodadobd/cadastro")
	public void cadastro(){
		
	}

	@Post
	@Public
	@Path("/tipodadobd/salva")
	public void salva(TipoDadoBd tipoDadoBd){
		
		validator.validate(tipoDadoBd);
		validator.onErrorUsePageOf(this).cadastro();

		String mensagem = "";

		try {

			tipoDadoBd.setEmpresa(this.empresaDao.load(tipoDadoBd.getEmpresa().getEmpresa_id()));		
			tipoDadoBd.setOrganizacao(this.organizacaoDao.load(tipoDadoBd.getOrganizacao().getOrganizacao_id()));

			this.tipoDadoBdDao.beginTransaction();
			this.tipoDadoBdDao.adiciona(tipoDadoBd);
			this.tipoDadoBdDao.commit();

			mensagem = "Tipo Dado BD " + tipoDadoBd.getNome() + " adicionado com sucesso";			
			
		} catch(Exception e) {
			
			this.tipoDadoBdDao.rollback();

			if (e.getCause().toString().indexOf("IX_TIPODADOBD_NOME") != -1){
				mensagem = "Erro: Tipo Dado Bd " + tipoDadoBd.getNome() + " já existente.";
			} else {
				mensagem = "Erro ao adicionar Tipo Dado Bd:";
			}

		}
		
		this.tipoDadoBdDao.clear();
		this.tipoDadoBdDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

	@Get @Path("/tipodadobd/busca.json")
	@Public
	public void tiposdadobd(String nome){
		result.use(Results.json()).withoutRoot().from(tipoDadoBdDao.buscaTiposDado(nome)).serialize();
	}

}