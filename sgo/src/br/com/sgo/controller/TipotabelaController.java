package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.TipoTabelaDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.TipoTabela;

@Resource
public class TipotabelaController {

	private final Result result;
	private final TipoTabelaDao tipoTabelaDao;


	public TipotabelaController(Result result,Validator validator,TipoTabelaDao tipoTabelaDao){

		this.tipoTabelaDao = tipoTabelaDao;
		this.result = result;

	}	

	@Get
	@Path("/tipotabela/cadastro")
	public void cadastro(){

	}

	@Post
	@Path("/tipotabela/salva")
	public void salva(TipoTabela tipoTabela){
		
		String mensagem = "";

		try {

			this.tipoTabelaDao.beginTransaction();
			this.tipoTabelaDao.adiciona(tipoTabela);
			this.tipoTabelaDao.commit();

			mensagem = "Tipo de Tabela " + tipoTabela.getNome() + " adicionado com sucesso";			

		} catch(Exception e) {

			this.tipoTabelaDao.rollback();

			if (e.getCause().toString().indexOf("IX_TIPOTABELA_EMPORGNOME") != -1){
				mensagem = "Erro: Tipo de Tabela " + tipoTabela.getNome() + " já existente.";
			} else {
				mensagem = "Erro ao adicionar Tipo de Tabela";
			}

		}

		this.tipoTabelaDao.clear();
		this.tipoTabelaDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

	@Get @Path("/tipotabela/busca.json")
	@Public
	public void tipotabela(Long empresa_id, Long organizacao_id, String nome){
		result.use(Results.json()).withoutRoot().from(tipoTabelaDao.buscaTiposTabela(empresa_id, organizacao_id, nome)).serialize();
	}
}