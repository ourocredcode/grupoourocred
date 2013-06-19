package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.JanelaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.PerfilDao;
import br.com.sgo.dao.PerfilJanelaAcessoDao;
import br.com.sgo.modelo.PerfilJanelaAcesso;

@Resource
public class PerfiljanelaacessoController {

	private final Result result;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final JanelaDao janelaDao;
	private final PerfilDao perfilDao;
	private final PerfilJanelaAcessoDao perfilJanelaAcessoDao;

	public PerfiljanelaacessoController(Result result,EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,JanelaDao janelaDao,PerfilDao perfilDao,
				PerfilJanelaAcessoDao perfilJanelaAcessoDao){

		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.janelaDao = janelaDao;
		this.perfilDao = perfilDao;
		this.perfilJanelaAcessoDao = perfilJanelaAcessoDao;
		this.result = result;

	}
	
	@Get
	@Path("/perfiljanelaacesso/cadastro")
	public void cadastro() {

	}

	@Post
	@Path("/perfiljanelaacesso/salva")
	public void salva(PerfilJanelaAcesso perfilJanelaAcesso){

		String mensagem = "";

		try {

			perfilJanelaAcesso.setEmpresa(this.empresaDao.load(perfilJanelaAcesso.getEmpresa().getEmpresa_id()));		
			perfilJanelaAcesso.setOrganizacao(this.organizacaoDao.load(perfilJanelaAcesso.getOrganizacao().getOrganizacao_id()));
			perfilJanelaAcesso.setJanela(this.janelaDao.load(perfilJanelaAcesso.getJanela().getJanela_id()));
			perfilJanelaAcesso.setPerfil(this.perfilDao.load(perfilJanelaAcesso.getPerfil().getPerfil_id()));
			perfilJanelaAcesso.setIsActive(perfilJanelaAcesso.getIsActive() == null ? false : true);

			this.perfilJanelaAcessoDao.insert(perfilJanelaAcesso);

			mensagem = "Perfil Janela Acesso adicionado com sucesso";

		} catch(Exception e) {

			System.out.println(e);

			if (e.getMessage().indexOf("PK_PERFILJANELAACESSO") != -1){
				mensagem = "Erro: Perfil Janela Acesso  jÃ¡ existente.";
			} else {
				mensagem = "Erro ao adicionar Perfil:";
			}

		} 

		this.perfilDao.clear();
		this.perfilDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

}
