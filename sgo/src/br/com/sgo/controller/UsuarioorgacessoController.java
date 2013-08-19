package br.com.sgo.controller;

import java.sql.SQLException;
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
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Usuario;
import br.com.sgo.modelo.UsuarioOrgAcesso;

@Resource
public class UsuarioorgacessoController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final UsuarioOrgAcessoDao usuarioOrgAcessoDao;

	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	private Calendar dataAtual = Calendar.getInstance();

	public UsuarioorgacessoController(Result result, UsuarioInfo usuarioInfo,Empresa empresa, Organizacao organizacao, EmpresaDao empresaDao,OrganizacaoDao organizacaoDao
			, Usuario usuario, UsuarioDao usuarioDao, UsuarioOrgAcessoDao usuarioOrgAcessoDao) {

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.usuario = this.usuarioInfo.getUsuario();
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();
		this.usuarioOrgAcessoDao = usuarioOrgAcessoDao;

	}

	@Get
	@Path("/usuarioorgacesso/cadastro")
	public void cadastro() {

		result.include("usuariosOrgAcesso", this.usuarioOrgAcessoDao.buscaAllUsuarioOrgAcessoByEmpresaOrganizacao(empresa.getEmpresa_id(),organizacao.getOrganizacao_id()));

	}

	@Post
	@Path("/usuarioorgacesso/salva")
	public void salva(UsuarioOrgAcesso usuarioOrgAcesso) {

		String mensagem = "";

		try {

			if (this.usuarioOrgAcessoDao.buscaUsuarioOrgAcessoByEmpresaOrganizacaoUsuarioPerfil(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), usuarioOrgAcesso.getUsuario().getUsuario_id()) == null) {

				usuarioOrgAcesso.setCreated(dataAtual);
				usuarioOrgAcesso.setUpdated(dataAtual);

				usuarioOrgAcesso.setCreatedBy(usuario);
				usuarioOrgAcesso.setUpdatedBy(usuario);

				usuarioOrgAcesso.setIsActive(true);

				this.usuarioOrgAcessoDao.insert(usuarioOrgAcesso);

				mensagem = "Usuário adicionado com sucesso.";

			} else {

				mensagem = "Erro: Usuário já cadastrado.";

			}

		} catch (SQLException e) {

			System.out.println(e);
			System.out.println(e.getCause());

			mensagem = "Erro: Falha ao adicionar o Usuário.";

		} finally {

			this.usuarioOrgAcessoDao.clear();
			this.usuarioOrgAcessoDao.close();

		}

		result.include("notice", mensagem);
		result.redirectTo(this).cadastro();
	}

	@Post
	@Path("/usuarioorgacesso/altera")
	public void altera(Boolean isActive, Long empresa_id, Long organizacao_id, Long usuario_id) {

		String mensagem = "";

		UsuarioOrgAcesso usuarioOrgAcesso = null;

		usuarioOrgAcesso = this.usuarioOrgAcessoDao.buscaUsuarioOrgAcessoByEmpresaOrganizacaoUsuarioPerfil(empresa_id, organizacao_id, usuario_id);

		try {

			usuarioOrgAcesso.setUpdated(dataAtual);
			usuarioOrgAcesso.setUpdatedBy(usuario);
			usuarioOrgAcesso.setIsActive(isActive);

			this.usuarioOrgAcessoDao.altera(usuarioOrgAcesso);

			mensagem = "Usuário alterado com sucesso.";

		} catch (SQLException e) {

			mensagem = "Erro: ao alterar o Usuário :";

		} finally {

			this.usuarioOrgAcessoDao.clear();
			this.usuarioOrgAcessoDao.close();

		}

		result.include("notice", mensagem);
		result.redirectTo(this).cadastro();

	}

}