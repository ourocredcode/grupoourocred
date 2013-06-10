package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.UsuarioDao;
import br.com.sgo.dao.UsuarioPerfilDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.UsuarioPerfil;

@Resource
public class UsuarioperfilController {

	private final Result result;
	private final UsuarioInfo usuarioInfo; 
	private final UsuarioDao usuarioDao;
	private final UsuarioPerfilDao usuarioPerfilDao;
	
	private Empresa empresa;
	private Organizacao organizacao;

	private Calendar dataAtual = Calendar.getInstance();

	public UsuarioperfilController(Result result, Empresa empresa, Organizacao organizacao, UsuarioInfo usuarioInfo
			, UsuarioDao usuarioDao, UsuarioPerfilDao usuarioPerfilDao){

		this.result = result;
		this.empresa = empresa;
		this.organizacao = organizacao;
		this.usuarioInfo = usuarioInfo;		
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();		
		this.usuarioDao = usuarioDao;
		this.usuarioPerfilDao = usuarioPerfilDao;

	}

	@Get
	@Public
	@Path("/usuarioperfil/cadastro")
	public void cadastro() {
		
		result.include("usuarioPerfis", this.usuarioPerfilDao.buscaAllUsuarioPerfilByEmpresaOrganizacao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));

	}

	@Post
	@Public
	@Path("/usuarioperfil/salva")
	public void salva(UsuarioPerfil usuarioPerfil){

		String mensagem = "";

		try {

			if (this.usuarioPerfilDao.buscaUsuarioPerfilByEmpresaOrganizacaoUsuarioPerfil(usuarioInfo.getEmpresa().getEmpresa_id(), usuarioInfo.getOrganizacao().getOrganizacao_id()
					, usuarioPerfil.getUsuario().getUsuario_id(), usuarioPerfil.getPerfil().getPerfil_id()) == null) {

				usuarioPerfil.setCreated(dataAtual);
				usuarioPerfil.setUpdated(dataAtual);

				usuarioPerfil.setCreatedBy(usuarioPerfil.getUsuario());
				usuarioPerfil.setUpdatedBy(usuarioPerfil.getUsuario());

				usuarioPerfil.setIsActive(usuarioPerfil.getIsActive() == null ? false : true);

				this.usuarioPerfilDao.insert(usuarioPerfil);

				mensagem = "Usuário adicionado com sucesso.";

			} else {

				mensagem = "Erro: Perfil já cadastrado.";

			} 

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar o Perfil.";

		} finally{

			this.usuarioDao.clear();
			this.usuarioDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}
}