package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.FuncaoDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.Funcao;

@Resource
public class FuncaoController {

	private final Result result;
	private final Validator validator;
	private final FuncaoDao funcaoDao;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	

	public FuncaoController(Result result,Validator validator, EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,FuncaoDao funcaoDao){
		this.result = result;
		this.validator = validator;
		this.funcaoDao = funcaoDao;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;		

	}	

	@Get
	@Public
	@Path("/funcao/cadastro")
	public void cadastro(){
		result.include("funcao",this.funcaoDao.listaTudo("ASC","nome"));
	}

	@Post
	@Public
	@Path("/funcao/salva")
	public void salva(Funcao funcao){
		
		validator.validate(funcao);
		validator.onErrorUsePageOf(this).cadastro();

		String mensagem = "";

		try {

			funcao.setEmpresa(this.empresaDao.load(funcao.getEmpresa().getEmpresa_id()));		
			funcao.setOrganizacao(this.organizacaoDao.load(funcao.getOrganizacao().getOrganizacao_id()));

			this.funcaoDao.beginTransaction();
			this.funcaoDao.adiciona(funcao);
			this.funcaoDao.commit();

			mensagem = "Função " + funcao.getNome() + " adicionado com sucesso";			
			
		} catch(Exception e) {
			
			this.funcaoDao.rollback();

			if (e.getCause().toString().indexOf("IX_FUNCAO_EMPORGNOME") != -1){
				mensagem = "Erro: Funcão " + funcao.getNome() + " já existente.";
			} else {
				mensagem = "Erro ao adicionar a Funcão.";
			}

		}
		
		this.funcaoDao.clear();
		this.funcaoDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

	@Get @Path("/funcao/busca.json")
	@Public
	public void funcao(Long empresa_id, Long organizacao_id, String nome){
		result.use(Results.json()).withoutRoot().from(funcaoDao.buscaFuncoes(empresa_id, organizacao_id, nome)).serialize();
	}

}