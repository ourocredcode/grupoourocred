package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.UsuarioDao;
import br.com.sgo.dao.UsuarioOrgAcessoDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.UsuarioOrgAcesso;

@Resource
public class UsuarioorgacessoController {

	private final Result result;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final UsuarioDao usuarioDao;
	private final UsuarioOrgAcessoDao usuarioOrgAcessoDao;

	public UsuarioorgacessoController(Result result,EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,UsuarioDao usuarioDao,UsuarioOrgAcessoDao usuarioOrgAcessoDao){

		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.usuarioDao = usuarioDao;
		this.usuarioOrgAcessoDao = usuarioOrgAcessoDao;
		this.result = result;

	}

	@Get
	@Public
	@Path("/usuarioorgacesso/cadastro")
	public void cadastro() {

	}

	@Post
	@Public
	@Path("/usuarioorgacesso/salva")
	public void salva(UsuarioOrgAcesso usuarioOrgAcesso){

		String mensagem = "";

		try {

			usuarioOrgAcesso.setEmpresa(this.empresaDao.load(usuarioOrgAcesso.getEmpresa().getEmpresa_id()));		
			usuarioOrgAcesso.setOrganizacao(this.organizacaoDao.load(usuarioOrgAcesso.getOrganizacao().getOrganizacao_id()));
			usuarioOrgAcesso.setUsuario(this.usuarioDao.load(usuarioOrgAcesso.getUsuario().getUsuario_id()));

			usuarioOrgAcesso.setIsActive(usuarioOrgAcesso.getIsActive() == null ? false : true);

			this.usuarioOrgAcessoDao.insert(usuarioOrgAcesso);

			mensagem = "Usuário Perfil adicionado com sucesso";

		} catch(Exception e) {

			System.out.println(e);

			if (e.getMessage().indexOf("PK_USUARIOORGACESSO") != -1){
				mensagem = "Erro: Usuário Perfil Acesso  já existente.";
			} else {
				mensagem = "Erro ao adicionar Usuário Perfil:";
			}

		} 

		this.usuarioOrgAcessoDao.clear();
		this.usuarioOrgAcessoDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

}