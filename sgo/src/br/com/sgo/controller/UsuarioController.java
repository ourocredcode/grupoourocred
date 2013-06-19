package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.UsuarioDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Usuario;

@Resource
public class UsuarioController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final UsuarioDao usuarioDao;
	private Empresa empresa;
	private Organizacao organizacao;

	private Calendar dataAtual = Calendar.getInstance();

	public UsuarioController(Result result,UsuarioInfo usuarioInfo, UsuarioDao usuarioDao, Empresa empresa,Organizacao organizacao){

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.usuarioDao = usuarioDao;
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();

	}

	@Get
	@Path("/usuario/cadastro")
	public void cadastro() {
		
		result.include("usuarios", this.usuarioDao.buscaAllUsuariosByEmpresaOrganizacao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));

	}

	@Post
	@Path("/usuario/salva")
	public void salva(Usuario usuario){

		String mensagem = "";

		try {

			if (this.usuarioDao.buscaUsuarioByEmpresaOrganizacaoChave(usuarioInfo.getEmpresa().getEmpresa_id(), usuarioInfo.getOrganizacao().getOrganizacao_id(), usuario.getChave()) == null) {

				usuario.setCreated(dataAtual);
				usuario.setUpdated(dataAtual);

				usuario.setCreatedBy(usuario);
				usuario.setUpdatedBy(usuario);

				usuario.setParceiroNegocio(usuario.getParceiroNegocio().getParceiroNegocio_id() == null ? null : usuario.getParceiroNegocio());
				usuario.setSupervisorUsuario(usuario.getSupervisorUsuario().getUsuario_id() == null ? null : usuario.getSupervisorUsuario());

				usuario.setIsActive(usuario.getIsActive() == null ? false : true);

				this.usuarioDao.beginTransaction();
				this.usuarioDao.adiciona(usuario);
				this.usuarioDao.commit();

				mensagem = "Usuário adicionado com sucesso.";

			} else {

				mensagem = "Erro: Usuário já cadastrado.";

			} 

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar o Usuário.";

		} finally{

			this.usuarioDao.clear();
			this.usuarioDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Get 
	@Path("/usuarios/busca.json")
	public void usuarios(Long empresa_id, Long organizacao_id, String nome){
		
		result.use(Results.json()).withoutRoot().from(usuarioDao.buscaUsuarios(empresa_id, organizacao_id, nome)).serialize();

	}	
	
	@Get
	public void usuarioPerfil(Long usuarioId){
		result.include("usuario",this.usuarioDao.load(usuarioId));
	}

}