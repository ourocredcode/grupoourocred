package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.JanelaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.Janela;

@Resource
public class JanelaController {

	private final Result result;
	private final Validator validator;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final JanelaDao janelaDao;

	public JanelaController(Result result,Validator validator,EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,JanelaDao janelaDao){

		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.janelaDao = janelaDao;
		this.result = result;
		this.validator = validator;

	}
	
	@Get
	@Public
	@Path("/janela/cadastro")
	public void cadastro() {

	}

	@Post
	@Public
	@Path("/janela/salva")
	public void salva(Janela janela){

		validator.validate(janela);
		validator.onErrorUsePageOf(this).cadastro();

		String mensagem = "";

			try {

				janela.setEmpresa(this.empresaDao.load(janela.getEmpresa().getEmpresa_id()));		
				janela.setOrganizacao(this.organizacaoDao.load(janela.getOrganizacao().getOrganizacao_id()));
	
				this.janelaDao.beginTransaction();
				this.janelaDao.adiciona(janela);
				this.janelaDao.commit();

				mensagem = "Janela " + janela.getNome() + " adicionada com sucesso";			
				
			} catch(Exception e) {
				
				this.janelaDao.rollback();
	
				if (e.getCause().toString().indexOf("IX_JANELA_NOME") != -1){
					mensagem = "Erro: Janela " + janela.getNome() + " já existente.";
				} else {
					mensagem = "Erro ao adicionar Janela:";
				}
	
			}

		this.janelaDao.clear();
		this.janelaDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

	@Get @Path("/janela/busca.json")
	@Public
	public void janelas(Long empresa_id, Long organizacao_id, String nome){
		result.use(Results.json()).withoutRoot().from(janelaDao.buscaTabelas(empresa_id, organizacao_id, nome)).serialize();
	}

}
