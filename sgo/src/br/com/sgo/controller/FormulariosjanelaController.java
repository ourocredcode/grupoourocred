package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.FormulariosJanelaDao;
import br.com.sgo.dao.JanelaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.TabelaBdDao;
import br.com.sgo.modelo.FormulariosJanela;

@Resource
public class FormulariosjanelaController {

	private final Result result;

	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;	
	private final TabelaBdDao tabelaBdDao;
	private final JanelaDao janelaDao;
	private final FormulariosJanelaDao formulariosJanelaDao;

	public FormulariosjanelaController(Result result,EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,
										FormulariosJanelaDao formulariosJanelaDao,TabelaBdDao tabelaBdDao,JanelaDao janelaDao){

		this.result = result;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.tabelaBdDao = tabelaBdDao;
		this.janelaDao = janelaDao;
		this.formulariosJanelaDao = formulariosJanelaDao;

	}	

	@Get
	@Path("/formulariosjanela/cadastro")
	public void cadastro(){

	}

	@Post
	@Path("/formulariosjanela/salva")
	public void salva(FormulariosJanela formulariosJanela){

		String mensagem = "";

		try {

			formulariosJanela.setEmpresa(this.empresaDao.load(formulariosJanela.getEmpresa().getEmpresa_id()));		
			formulariosJanela.setOrganizacao(this.organizacaoDao.load(formulariosJanela.getOrganizacao().getOrganizacao_id()));
			formulariosJanela.setJanela(this.janelaDao.load(formulariosJanela.getJanela().getJanela_id()));
			formulariosJanela.setTabelaBd(this.tabelaBdDao.load(formulariosJanela.getTabelaBd().getTabelaBd_id()));
			formulariosJanela.setIsActive(formulariosJanela.getIsActive() == null ? false : true);
			formulariosJanela.setIsMostrado(formulariosJanela.getIsMostrado() == null ? false : true);
			formulariosJanela.setIsSomenteLeitura(formulariosJanela.getIsSomenteLeitura() == null ? false : true);

			this.formulariosJanelaDao.beginTransaction();
			this.formulariosJanelaDao.adiciona(formulariosJanela);
			this.formulariosJanelaDao.commit();

			mensagem = "Formularios Janela adicionado com sucesso";

		} catch(Exception e) {

			System.out.println(e);

			this.formulariosJanelaDao.rollback();

			if (e.getCause().toString().indexOf("IX_FORMULARIOSJANELA") != -1){
				mensagem = "Erro: Formularios Janela já existente.";
			} else {
				mensagem = "Erro ao adicionar formularios Janela.";
			}

		} 

		this.formulariosJanelaDao.clear();
		this.formulariosJanelaDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

	@Get
	@Path("/formulariosjanela/busca.json")
	public void formulariosjanela(Long empresa_id, Long organizacao_id, String nome){
		result.use(Results.json()).withoutRoot().from(formulariosJanelaDao.buscaFomulariosJanela(empresa_id, organizacao_id, nome)).serialize();
	}

}