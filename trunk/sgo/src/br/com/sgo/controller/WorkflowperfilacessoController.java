package br.com.sgo.controller;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.PerfilDao;
import br.com.sgo.dao.WorkflowDao;
import br.com.sgo.dao.WorkflowPerfilAcessoDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Usuario;
import br.com.sgo.modelo.WorkflowPerfilAcesso;

@Resource
public class WorkflowperfilacessoController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final WorkflowPerfilAcessoDao workflowPerfilAcessoDao;
	private final WorkflowDao workflowDao;
	private final PerfilDao perfilDao;

	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	public WorkflowperfilacessoController(Result result,  UsuarioInfo usuarioInfo, WorkflowPerfilAcessoDao workflowPerfilAcessoDao, WorkflowDao workflowDao, PerfilDao perfilDao
			,Empresa empresa,Organizacao organizacao,Usuario usuario) {

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.workflowPerfilAcessoDao =  workflowPerfilAcessoDao;
		this.workflowDao = workflowDao;
		this.perfilDao = perfilDao;
		this.empresa = this.usuarioInfo.getEmpresa();
		this.organizacao = this.usuarioInfo.getOrganizacao();
		this.usuario = this.usuarioInfo.getUsuario();

	}

	@Get
	@Path("/workflowperfilacesso/cadastro")
	public void cadastro() {

		result.include("workflows", this.workflowDao.buscaWorkflowsByEmpresaOrganizacao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
		result.include("perfis",this.perfilDao.buscaAllPerfis(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
		result.include("workflowperfisacesso", this.workflowPerfilAcessoDao.buscaTodosWorkflowPerfilAcesso());
		
	}

	@Post
	@Path("/workflowperfilacesso/salva")
	public void salva(WorkflowPerfilAcesso workflowPerfilAcesso) {

		Calendar dataAtual = GregorianCalendar.getInstance();

		String mensagem = "";

		try {

			if (this.workflowPerfilAcessoDao.buscaWorkflowPerfilAcessoPorEmpresaOrganizacaoWorkflowPerfil(empresa.getEmpresa_id(),organizacao.getOrganizacao_id(),
					workflowPerfilAcesso.getWorkflow().getWorkflow_id(), workflowPerfilAcesso.getPerfil().getPerfil_id()) == null) {				

				workflowPerfilAcesso.setCreated(dataAtual);
				workflowPerfilAcesso.setUpdated(dataAtual);

				workflowPerfilAcesso.setCreatedBy(usuario);
				workflowPerfilAcesso.setUpdatedBy(usuario);

				workflowPerfilAcesso.setIsActive(workflowPerfilAcesso.getIsActive() == null ? false : true);
				workflowPerfilAcesso.setIsLeituraEscrita(workflowPerfilAcesso.getIsLeituraEscrita() == null ? false : true);

				this.workflowPerfilAcessoDao.insert(workflowPerfilAcesso);

				mensagem = "Perfil adicionado com sucesso para o Workflow ";

			} else {
				
				mensagem = "Erro: Perfil já cadastrado para o Workflow ";
				
			} 

		} catch (SQLException e) {

			mensagem = "Erro: ao adicionar o Perfil Workflow :";

		} finally{

			this.workflowPerfilAcessoDao.clear();
			this.workflowPerfilAcessoDao.close();

		}
		
		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();
	}

	@Get
	public void msg() {

	}
}