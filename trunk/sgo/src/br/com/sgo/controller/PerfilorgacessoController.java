package br.com.sgo.controller;

import java.sql.SQLException;
import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.PerfilOrgAcessoDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.PerfilOrgAcesso;
import br.com.sgo.modelo.Usuario;

@Resource
public class PerfilorgacessoController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final PerfilOrgAcessoDao perfilOrgAcessoDao;

	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	private Calendar dataAtual = Calendar.getInstance();

	public PerfilorgacessoController(Result result, UsuarioInfo usuarioInfo, PerfilOrgAcessoDao perfilOrgAcessoDao, Empresa empresa, Organizacao organizacao, Usuario usuario){

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.perfilOrgAcessoDao = perfilOrgAcessoDao;
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();
		this.usuario = this.usuarioInfo.getUsuario();

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
			if(this.perfilOrgAcessoDao.buscaUsuarioOrgAcessoByEmpOrgPerfil(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), perfilOrgAcesso.getPerfil().getPerfil_id()) == null){				
			
				perfilOrgAcesso.setCreated(dataAtual);
				perfilOrgAcesso.setUpdated(dataAtual);
		
				perfilOrgAcesso.setCreatedBy(usuario);
				perfilOrgAcesso.setUpdatedBy(usuario);
				perfilOrgAcesso.setIsActive(perfilOrgAcesso.getIsActive() == null ? false: true);
		
				this.perfilOrgAcessoDao.insert(perfilOrgAcesso);
		
				mensagem = "Perfil Acesso adicionado com sucesso";
		
			} else {
		
				mensagem = "Erro: Perfil já cadastrado.";
		
			}

		} catch (Exception e) {
	
			mensagem = "Erro: Falha ao adicionar o Perfil.";
	
		} finally {
	
			this.perfilOrgAcessoDao.clear();
			this.perfilOrgAcessoDao.close();

	}

	result.include("notice", mensagem);
	result.redirectTo(this).cadastro();

	}
	
	@Post
	@Path("/perfilorgacesso/altera")
	public void altera(Boolean isActive, Long empresa_id, Long organizacao_id, Long perfil_id){

		String mensagem = "";

		PerfilOrgAcesso perfilOrgAcesso = null;

		perfilOrgAcesso = this.perfilOrgAcessoDao.buscaUsuarioOrgAcessoByEmpOrgPerfil(empresa_id, organizacao_id, perfil_id);

		try {

			perfilOrgAcesso.setUpdated(dataAtual);
			perfilOrgAcesso.setUpdatedBy(usuario);
			perfilOrgAcesso.setIsActive(isActive);
			
			this.perfilOrgAcessoDao.altera(perfilOrgAcesso);
			
			mensagem = "Registro alterado com sucesso."; 

		} catch(SQLException e){

			mensagem = "Erro: ao alterar o Usuário :";

		} finally {

			this.perfilOrgAcessoDao.clear();
			this.perfilOrgAcessoDao.close();

		}
		
		result.include("notice", mensagem);
		result.redirectTo(this).cadastro();
	}

}
