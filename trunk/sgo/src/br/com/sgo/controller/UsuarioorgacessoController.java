package br.com.sgo.controller;

import java.util.Calendar;

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
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.UsuarioOrgAcesso;

@Resource
public class UsuarioorgacessoController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final UsuarioOrgAcessoDao usuarioOrgAcessoDao;
	
	private Empresa empresa;
	private Organizacao organizacao;

	private Calendar dataAtual = Calendar.getInstance();

	public UsuarioorgacessoController(Result result, UsuarioInfo usuarioInfo, Empresa empresa, Organizacao organizacao, EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,UsuarioDao usuarioDao,UsuarioOrgAcessoDao usuarioOrgAcessoDao){

		this.usuarioInfo = usuarioInfo;
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();
		this.usuarioOrgAcessoDao = usuarioOrgAcessoDao;
		this.result = result;

	}

	@Get
	@Public
	@Path("/usuarioorgacesso/cadastro")
	public void cadastro() {

		result.include("usuariosOrgAcesso", this.usuarioOrgAcessoDao.buscaAllUsuarioOrgAcessoByEmpresaOrganizacao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));

	}

	@Post
	@Public
	@Path("/usuarioorgacesso/salva")
	public void salva(UsuarioOrgAcesso usuarioOrgAcesso){

		String mensagem = "";

		try {

			if (this.usuarioOrgAcessoDao.buscaUsuarioOrgAcessoByEmpresaOrganizacaoUsuarioPerfil(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()
					, usuarioOrgAcesso.getUsuario().getUsuario_id()) == null) {

				usuarioOrgAcesso.setCreated(dataAtual);
				usuarioOrgAcesso.setUpdated(dataAtual);
	
				usuarioOrgAcesso.setCreatedBy(usuarioOrgAcesso.getUsuario());
				usuarioOrgAcesso.setUpdatedBy(usuarioOrgAcesso.getUsuario());
	
				usuarioOrgAcesso.setIsActive(usuarioOrgAcesso.getIsActive() == null ? false : true);
	
				this.usuarioOrgAcessoDao.insert(usuarioOrgAcesso);
	
				mensagem = "Usuário adicionado com sucesso.";

			} else {

				mensagem = "Erro: Usuário já cadastrado.";

			} 

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar o Usuário.";

		} finally{

			this.usuarioOrgAcessoDao.clear();
			this.usuarioOrgAcessoDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

}