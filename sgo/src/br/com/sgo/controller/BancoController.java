package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.BancoDao;
import br.com.sgo.dao.GrupoBancoDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.Banco;

@Resource
public class BancoController {

	private final Result result;
	private final BancoDao bancoDao;
	private final GrupoBancoDao grupoBancoDao;

	public BancoController(Result result, BancoDao bancoDao, GrupoBancoDao grupoBancoDao){

		this.result = result;
		this.bancoDao = bancoDao;
		this.grupoBancoDao = grupoBancoDao;

	}

	@Get
	@Path("/banco/cadastro")
	public void cadastro(){
		//result.include("workflows", this.grupoBancoDao.buscaGrupoBanco();
	}

	@Post
	@Path("/banco/salva")
	public void salva(Banco banco){

		String mensagem = "";

		try {

			this.bancoDao.beginTransaction();
			this.bancoDao.adiciona(banco);
			this.bancoDao.commit();

			mensagem = "Banco " + banco.getNome() + " adicionado com sucesso";			

		} catch(Exception e) {

			this.bancoDao.rollback();

			if (e.getCause().toString().indexOf("IX_GRUPOPRODUTO_EMPORGNOME") != -1){
				mensagem = "Erro: Banco " + banco.getNome() + " já existente.";
			} else {
				mensagem = "Erro ao adicionar o Banco";
			}

		}

		this.bancoDao.clear();
		this.bancoDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

	@Get @Path("/banco/busca.json")
	@Public
	public void grupoproduto(Long empresa_id, Long organizacao_id, String nome){
		result.use(Results.json()).withoutRoot().from(bancoDao.buscaBancos(empresa_id, organizacao_id, nome)).serialize();
	}

}