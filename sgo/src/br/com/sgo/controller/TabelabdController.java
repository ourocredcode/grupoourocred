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
import br.com.sgo.dao.TabelaBdDao;
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
	@Path("/tabelabd/cadastro")
	public void cadastro(){
		
	}

	@Post
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

			this.tabelaBdDao.rollback();

			if (e.getCause().toString().indexOf("IX_COLUNABD_ELEMENTOBDID") != -1){
				mensagem = "Erro: Tabela Bd " + tabelaBd.getNome() + " já existente.";
			} else {
				mensagem = "Erro ao adicionar Tabela Bd:";
			}

		}

		this.tabelaBdDao.clear();
		this.tabelaBdDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}
	
	@Get
	@Path("/tabelabd/busca.json")
	public void tabelas(Long empresa_id, Long organizacao_id, String nometabelabd){
		result.use(Results.json()).withoutRoot().from(tabelaBdDao.buscaTabelas(empresa_id, organizacao_id,nometabelabd)).serialize();
	}

}