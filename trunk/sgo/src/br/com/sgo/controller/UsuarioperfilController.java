package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.PerfilDao;
import br.com.sgo.dao.UsuarioDao;
import br.com.sgo.dao.UsuarioPerfilDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.UsuarioPerfil;

@Resource
public class UsuarioperfilController {

	private final Result result;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final UsuarioDao usuarioDao;
	private final PerfilDao perfilDao;
	private final UsuarioPerfilDao usuarioPerfildao;

	public UsuarioperfilController(Result result,EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,UsuarioDao usuarioDao, PerfilDao perfilDao, UsuarioPerfilDao usuarioPerfildao){

		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.usuarioDao = usuarioDao;
		this.perfilDao = perfilDao;
		this.usuarioPerfildao = usuarioPerfildao;
		this.result = result;

	}

	@Get
	@Public
	@Path("/usuarioperfil/cadastro")
	public void cadastro() {

	}

	@Post
	@Public
	@Path("/usuarioperfil/salva")
	public void salva(UsuarioPerfil usuarioPerfil){

		String mensagem = "";

		try {

			usuarioPerfil.setEmpresa(this.empresaDao.load(usuarioPerfil.getEmpresa().getEmpresa_id()));		
			usuarioPerfil.setOrganizacao(this.organizacaoDao.load(usuarioPerfil.getOrganizacao().getOrganizacao_id()));
			usuarioPerfil.setUsuario(this.usuarioDao.load(usuarioPerfil.getUsuario().getUsuario_id()));
			usuarioPerfil.setPerfil(this.perfilDao.load(usuarioPerfil.getPerfil().getPerfil_id()));

			usuarioPerfil.setIsActive(usuarioPerfil.getIsActive() == null ? false : true);

			this.usuarioPerfildao.insert(usuarioPerfil);

			mensagem = "Usuário Perfil adicionado com sucesso";

		} catch(Exception e) {

			if (e.getMessage().indexOf("PK_USUARIOPERFIL") != -1){
				mensagem = "Erro: Usuário Perfil Acesso  já existente.";
			} else {
				mensagem = "Erro ao adicionar Usuário Perfil:";
			}

		} 

		this.perfilDao.clear();
		this.perfilDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();
	}

}