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
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.PerfilOrgAcesso;

@Resource
public class PerfilorgacessoController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;	
	private final PerfilDao perfilDao;
	private final PerfilOrgAcessoDao perfilOrgAcessoDao;

	public PerfilorgacessoController(Result result, UsuarioInfo usuarioInfo, EmpresaDao empresaDao, OrganizacaoDao organizacaoDao, PerfilDao perfilDao, PerfilOrgAcessoDao perfilOrgAcessoDao){

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.perfilDao = perfilDao;
		this.perfilOrgAcessoDao = perfilOrgAcessoDao;

	}
	
	@Get
	@Path("/perfilorgacesso/cadastro")
	public void cadastro() {
		
		result.include("perfisOrgAcesso", this.perfilOrgAcessoDao.buscaAllPerfilOrgAcessoByEmpOrg(usuarioInfo.getEmpresa().getEmpresa_id(), usuarioInfo.getOrganizacao().getOrganizacao_id()));

	}

	@Post
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
			
			if (e.getMessage().indexOf("IX_PERFILORGACESSO_EMPORGPER") != -1){
				mensagem = "Erro: Perfil " + perfilOrgAcesso.getPerfil().getNome() + " já cadastrado.";
			} else {
				mensagem = "Erro: Organização " + perfilOrgAcesso.getOrganizacao().getNome() + " já cadastrado para a empresa " +
						"" + perfilOrgAcesso.getEmpresa().getNome() + " e perfil " +
						"" + perfilOrgAcesso.getPerfil().getNome() + ".";
			}

		} 

		this.perfilDao.clear();
		this.perfilDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

}
