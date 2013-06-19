package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.ColunaBdDao;
import br.com.sgo.dao.ElementoBdDao;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.TabelaBdDao;
import br.com.sgo.modelo.ColunaBd;

@Resource
public class ColunabdController {

	private final Result result;
	private final Validator validator;
	private final ColunaBdDao colunaBdDao;
	private final EmpresaDao empresaDao;	
	private final OrganizacaoDao organizacaoDao;
	private final TabelaBdDao tabelaBdDao;	
	private final ElementoBdDao elementoBdDao;

	public ColunabdController(Result result, Validator validator, ColunaBdDao colunaBdDao, EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,
			TabelaBdDao tabelaBdDao,ElementoBdDao elementoBdDao){

		this.result = result;
		this.validator=validator;
		this.colunaBdDao = colunaBdDao;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.tabelaBdDao = tabelaBdDao;
		this.elementoBdDao = elementoBdDao;

	}

	@Get
	@Path("/colunabd/cadastro")
	public void cadastro(){

	}

	
	@Post
	@Path("/colunabd/salva")
	public void salva(ColunaBd colunaBd){

		validator.validate(colunaBd);
		validator.onErrorUsePageOf(this).cadastro();

		String mensagem = "";

		try {

			colunaBd.setEmpresa(this.empresaDao.load(colunaBd.getEmpresa().getEmpresa_id()));		
			colunaBd.setOrganizacao(this.organizacaoDao.load(colunaBd.getOrganizacao().getOrganizacao_id()));
			colunaBd.setTabelaBd(this.tabelaBdDao.load(colunaBd.getTabelaBd().getTabelaBd_id()));
			colunaBd.setElementoBd(this.elementoBdDao.load(colunaBd.getElementoBd().getElementoBd_id()));

			this.colunaBdDao.beginTransaction();
			this.colunaBdDao.adiciona(colunaBd);
			this.colunaBdDao.commit();

			mensagem = "Coluna BD " + colunaBd.getNome() + " adicionado com sucesso";			
			
		} catch(Exception e) {
			
			this.colunaBdDao.rollback();

			if (e.getCause().toString().indexOf("IX_COLUNABD_EMP_ORG_COL_ELE") != -1){
				mensagem = "Erro: Elemento Bd " + colunaBd.getNome() + " já existente.";
			} else {
				mensagem = "Erro ao adicionar ColunaBd Bd:";
			}

		}

		this.colunaBdDao.clear();
		this.colunaBdDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

	@Get
	@Path("/colunaBd/busca.json")
	public void colunas(Long empresa_id, Long organizacao_id, Long tabelaBd_id, Long elementoBd_id){
		result.use(Results.json()).withoutRoot().from(this.colunaBdDao.buscaColunasBd(empresa_id, organizacao_id, tabelaBd_id, elementoBd_id)).serialize();
	}

}
