package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.CidadeDao;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.PaisDao;
import br.com.sgo.dao.RegiaoDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.Cidade;

@Resource
public class CidadeController {

	private final Result result;
	private final Validator validator;
	private final CidadeDao cidadeDao;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final PaisDao paisDao;
	private final RegiaoDao regiaoDao;
	
	public CidadeController(Result result,Validator validator,EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,CidadeDao cidadeDao, PaisDao paisDao, RegiaoDao regiaoDao){

		this.result = result;
		this.validator = validator;
		this.cidadeDao = cidadeDao;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;		
		this.paisDao = paisDao;
		this.regiaoDao = regiaoDao;
		
	}	

	@Get
	@Public
	@Path("/cidade/cadastro")
	public void cadastro(){

	}

	@Post
	@Public
	@Path("/cidade/salva")
	public void salva(Cidade cidade){

		validator.validate(cidade);
		validator.onErrorUsePageOf(this).cadastro();

		String mensagem = "";

		try {

			cidade.setEmpresa(this.empresaDao.load(cidade.getEmpresa().getEmpresa_id()));
			cidade.setOrganizacao(this.organizacaoDao.load(cidade.getOrganizacao().getOrganizacao_id()));
			cidade.setPais(this.paisDao.load(cidade.getPais().getPais_id()));
			cidade.setRegiao(this.regiaoDao.load(cidade.getRegiao().getRegiao_id()));

			this.cidadeDao.beginTransaction();
			this.cidadeDao.adiciona(cidade);
			this.cidadeDao.commit();

			mensagem = "Cidade " + cidade.getNome() + " adicionado com sucesso";

		} catch(Exception e) {

			this.cidadeDao.rollback();

			if (e.getCause().toString().indexOf("PK_CIDADE") != -1){
				mensagem = "Erro: Cidade " + cidade.getNome() + " já existente.";
			} else {
				mensagem = "Erro ao adicionar a cidade.";
			}

		}

		this.cidadeDao.clear();
		this.cidadeDao.close();

		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

	@Get @Path("/cidade/busca.json")
	@Public
	public void busca(String nome){
		result.use(Results.json()).withoutRoot().from(cidadeDao.buscaPorNome(nome)).serialize();
	}

}