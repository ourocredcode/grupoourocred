package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.GrupoBancoDao;
import br.com.sgo.modelo.GrupoBanco;

@Resource
public class GrupobancoController {

	private final Result result; 
	private final GrupoBancoDao grupoBancoDao;

	public GrupobancoController(Result result, GrupoBancoDao grupoBancoDao){

		this.grupoBancoDao = grupoBancoDao;
		this.result = result;

	}

	@Get
	@Path("/grupobanco/cadastro")
	public void cadastro(){

		result.include("gruposBanco", this.grupoBancoDao.buscaAllGrupoBanco());

	}

	@Post
	@Path("/grupobanco/salva")
	public void salva(GrupoBanco grupoBanco){

		String mensagem = "";

		try {

			this.grupoBancoDao.beginTransaction();
			this.grupoBancoDao.adiciona(grupoBanco);
			this.grupoBancoDao.commit();

			mensagem = "Grupo de Banco " + grupoBanco.getNome() + " adicionado com sucesso";			

		} catch(Exception e) {

			this.grupoBancoDao.rollback();

			if (e.getCause().toString().indexOf("IX_GRUPOPRODUTO_EMPORGNOME") != -1){
				mensagem = "Erro: Grupo de Banco " + grupoBanco.getNome() + " já existente.";
			} else {
				mensagem = "Erro ao adicionar Grupo de Banco";
			}

		}

		this.grupoBancoDao.clear();
		this.grupoBancoDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

	@Get
	@Path("/grupobanco/busca.json")
	public void grupoproduto(Long empresa_id, Long organizacao_id, String nome){
		result.use(Results.json()).withoutRoot().from(grupoBancoDao.buscaGrupoBanco(empresa_id, organizacao_id, nome)).serialize();
	}

}