package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.TabelaDao;
import br.com.sgo.modelo.Tabela;

@Resource
public class TabelaController {

	private final Result result;
	private final TabelaDao tabelaDao;

	public TabelaController(Result result,TabelaDao tabelaDao){

		this.tabelaDao = tabelaDao;
		this.result = result;

	}	

	@Get
	@Path("/tabela/cadastro")
	public void cadastro(){
		

	}

	@Post
	@Path("/tabela/salva")
	public void salva(Tabela tabela){
		
		String mensagem = "";

		try {

			this.tabelaDao.beginTransaction();
			this.tabelaDao.adiciona(tabela);
			this.tabelaDao.commit();

			mensagem = "Tabela " + tabela.getNome() + " adicionado com sucesso";

		} catch(Exception e) {

			this.tabelaDao.rollback();

			if (e.getCause().toString().indexOf("IX_TABELA_EMPORGNOME") != -1){
				mensagem = "Erro: Tabela " + tabela.getNome() + " já existente.";
			} else {
				mensagem = "Erro ao adicionar Tabela:";
			}

		}

		this.tabelaDao.clear();
		this.tabelaDao.close();

		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

}