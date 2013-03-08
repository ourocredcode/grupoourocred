package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.PerfilDao;
import br.com.sgo.dao.PerfilOrgAcessoDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.PerfilOrgAcesso;

@Resource
public class PerfilorgacessoController {

	private final Result result;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;	
	private final PerfilDao perfilDao;
	private final PerfilOrgAcessoDao perfilOrgAcessoDao;

	public PerfilorgacessoController(Result result, EmpresaDao empresaDao, OrganizacaoDao organizacaoDao, PerfilDao perfilDao, PerfilOrgAcessoDao perfilOrgAcessoDao){

		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;		
		this.perfilDao = perfilDao;
		this.perfilOrgAcessoDao = perfilOrgAcessoDao;
		this.result = result;

	}
	
	@Get
	@Public
	@Path("/perfilorgacesso/cadastro")
	public void cadastro() {

	}

	@Post
	@Public
	@Path("/perfilorgacesso/salva")
	public void salva(PerfilOrgAcesso perfilOrgAcesso){

		String mensagem = "";

		try {

			perfilOrgAcesso.setEmpresa(this.empresaDao.load(perfilOrgAcesso.getEmpresa().getEmpresa_id()));		
			perfilOrgAcesso.setOrganizacao(this.organizacaoDao.load(perfilOrgAcesso.getOrganizacao().getOrganizacao_id()));			
			perfilOrgAcesso.setPerfil(this.perfilDao.load(perfilOrgAcesso.getPerfil().getPerfil_id()));
			perfilOrgAcesso.setIsActive(perfilOrgAcesso.getIsActive() == null ? false : true);

			this.perfilOrgAcessoDao.insert(perfilOrgAcesso);

			mensagem = "Perfil Janela Acesso adicionado com sucesso";

		} catch(Exception e) {

			if (e.getMessage().indexOf("PK_PERFILORGACESSO_1") != -1){
				mensagem = "Erro: Organização já existente para este perfil.";
			} else {
				mensagem = "Erro ao adicionar a Organização para o perfil:";
			}

		} 

		this.perfilDao.clear();
		this.perfilDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

}
