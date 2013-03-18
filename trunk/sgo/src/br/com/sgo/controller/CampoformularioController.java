package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.CampoFormularioDao;
import br.com.sgo.dao.ColunaBdDao;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.FormulariosJanelaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.CampoFormulario;

@Resource
public class CampoformularioController {

	private final Result result;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;	
	private final FormulariosJanelaDao formulariosJanelaDao;
	private final CampoFormularioDao campoFormularioDao;
	private final ColunaBdDao colunaBdDao;	

	public CampoformularioController(Result result, EmpresaDao empresaDao, OrganizacaoDao organizacaoDao,FormulariosJanelaDao formulariosJanelaDao,
			ColunaBdDao colunaBdDao,CampoFormularioDao campoFormularioDao){

		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.formulariosJanelaDao = formulariosJanelaDao;
		this.colunaBdDao = colunaBdDao;
		this.campoFormularioDao = campoFormularioDao;
		this.result = result;

	}	

	@Get
	@Public
	@Path("/campoformulario/cadastro")
	public void cadastro(){

	}

	@Post
	@Public
	@Path("/campoformulario/salva")
	public void salva(CampoFormulario campoFormulario){
		
		String mensagem = "";

		try {

			campoFormulario.setEmpresa(this.empresaDao.load(campoFormulario.getEmpresa().getEmpresa_id()));		
			campoFormulario.setOrganizacao(this.organizacaoDao.load(campoFormulario.getOrganizacao().getOrganizacao_id()));
			campoFormulario.setFormulariosJanela(this.formulariosJanelaDao.load(campoFormulario.getFormulariosJanela().getFormulariosjanela_id()));
			campoFormulario.setColunaBd(this.colunaBdDao.load(campoFormulario.getColunaBd().getColunabd_id()));

			campoFormulario.setIsActive(campoFormulario.getIsActive() == null ? false : true);
			campoFormulario.setIsMostrado(campoFormulario.getIsMostrado() == null ? false : true);
			campoFormulario.setIsSomenteLeitura(campoFormulario.getIsSomenteLeitura() == null ? false : true);

			this.campoFormularioDao.beginTransaction();
			this.campoFormularioDao.adiciona(campoFormulario);
			this.campoFormularioDao.commit();

			mensagem = "Campo Formulário adicionado com sucesso";

		} catch(Exception e) {

			System.out.println(e);

			this.campoFormularioDao.rollback();

			if (e.getCause().toString().indexOf("IX_CAMPOFORMULARIO") != -1){
				mensagem = "Erro: Campo Formulário já existente.";
			} else {
				mensagem = "Erro ao adicionar campo formulário.";
			}

		} 

		this.formulariosJanelaDao.clear();
		this.formulariosJanelaDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

}