package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.PeriodoDao;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Periodo;

@Resource
public class PeriodoController {

	private final Result result;
	private final PeriodoDao periodoDao;

	public PeriodoController(Result result, PeriodoDao periodoDao){

		this.result = result;
		this.periodoDao = periodoDao;

	}	

	@Get
	@Path("/periodo/cadastro")
	public void cadastro(){
		result.include("periodos", this.periodoDao.buscaAllPeriodos());
	}

	@Post
	@Path("/periodo/salva")
	public void salva(Periodo periodo){

		String mensagem = "";

		try {
			
			if (this.periodoDao.buscaPeriodosByEmOrNome(periodo.getEmpresa().getEmpresa_id(), periodo.getOrganizacao().getOrganizacao_id(), periodo.getNome()) == null) {	

				periodo.setIsActive(periodo.getIsActive() == null ? false : true);

				this.periodoDao.beginTransaction();
				this.periodoDao.adiciona(periodo);
				this.periodoDao.commit();

				mensagem = "Período " + periodo.getNome() + " adicionado com sucesso";
			} else {
				mensagem = "Erro: Workflow " + periodo.getNome() + " já cadastrado.";
			} 

		} catch(Exception e) {

			mensagem = "Erro: Falha ao adicionar o workflow.";

		} finally{

			this.periodoDao.clear();
			this.periodoDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Get
	@Path("/periodo/busca.json")
	public void periodo(Empresa empresa, Organizacao organizacao, String nome){
		result.use(Results.json()).withoutRoot().from(periodoDao.buscaPeriodosPorNome(empresa, organizacao, nome)).serialize();
	}

}