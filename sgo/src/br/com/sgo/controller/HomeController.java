package br.com.sgo.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Collection;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.PerfilDao;
import br.com.sgo.dao.UsuarioDao;
import br.com.sgo.dao.UsuarioPerfilDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Perfil;
import br.com.sgo.modelo.Usuario;
import br.com.sgo.modelo.UsuarioPerfil;

@Resource
public class HomeController {

	private final Result result;
	private final UsuarioDao usuarioDao;
	private final UsuarioPerfilDao usuarioPerfilDao;
	private final PerfilDao perfilDao;
	private final UsuarioInfo usuarioInfo;
	private final Validator validator;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;

	private Usuario usuario;

	public HomeController(Result result, UsuarioDao usuarioDao, UsuarioInfo usuarioInfo, Validator validator, UsuarioPerfilDao usuarioPerfilDao,PerfilDao perfilDao,Usuario usuario,
			EmpresaDao empresaDao, OrganizacaoDao organizacaoDao) {
		this.result = result;
		this.usuarioDao = usuarioDao;
		this.usuarioPerfilDao = usuarioPerfilDao;
		this.perfilDao = perfilDao;
		this.usuarioInfo = usuarioInfo;
		this.validator = validator;
		this.usuario = usuario;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
	}

	@Get
	@Public
	public void login() {

	}
	
	@Get
	@Path("/")
	public void home() {

	}	

	@Post
	@Public
	public void login(String login, String password) {

		Usuario u = usuarioDao.find(login, password);

		if(u != null) {

			this.usuario.setUsuario_id(u.getUsuario_id());
			result.redirectTo(this).perfis(this.usuarioPerfilDao.buscaUsuarioPerfilAcesso(usuario));

		} else {

			result.include("msg","Erro").redirectTo(this).msg();

		}
			

	}

	@Get
	@Public
	public void perfis(Collection<Perfil> perfis){
		result.include("perfis",perfis);
	}

	@Post
	@Public
	@Path("/home/perfil") 
	public void perfil(UsuarioPerfil usuarioPerfil) {

		final Usuario currentUsuario = this.usuarioDao.load(usuario.getUsuario_id());

		currentUsuario.setEmpresa(empresaDao.load(usuarioPerfil.getEmpresa().getEmpresa_id()));
		currentUsuario.setOrganizacao(organizacaoDao.load(usuarioPerfil.getOrganizacao().getOrganizacao_id()));

		validator.checking(new Validations() {{
			that(currentUsuario, is(notNullValue()), "login", "invalid_login_or_password");
		}});

		validator.onErrorUsePageOf(this).login();

		usuarioInfo.login(currentUsuario);

		usuarioInfo.setPerfil(perfilDao.load(usuarioPerfil.getPerfil().getPerfil_id()));

		String tipo = usuarioInfo.getPerfil().getChave();

		result.redirectTo(MenuController.class).inicio(tipo);

	}

	public void logout() {
		usuarioInfo.logout(); 
		result.redirectTo(this).login();
	}

	@Get
	@Public
	public void msg(){

	}
	
	@Post
	@Public
	@Path("/home/empresas")
	public void empresas(Long perfil_id){

		result.include("empresas",this.usuarioPerfilDao.buscaEmpresaPerfilAcesso(perfil_id,usuario.getUsuario_id()));

	}
	
	@Post
	@Public
	@Path("/home/organizacoes")
	public void organizacoes(Long perfil_id,Long empresa_id, Long usuario_id){

		result.include("organizacoes",this.usuarioPerfilDao.buscaOrganizacaoPerfilAcesso(perfil_id, empresa_id, usuario.getUsuario_id()));

	}

}