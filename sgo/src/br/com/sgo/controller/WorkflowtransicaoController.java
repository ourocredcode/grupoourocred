package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.EtapaDao;
import br.com.sgo.dao.PerfilDao;
import br.com.sgo.dao.WorkflowDao;
import br.com.sgo.dao.WorkflowEtapaPerfilAcessoDao;
import br.com.sgo.dao.WorkflowTransicaoDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Usuario;
import br.com.sgo.modelo.WorkflowEtapaPerfilAcesso;
import br.com.sgo.modelo.WorkflowTransicao;

@Resource
public class WorkflowtransicaoController {

	private final Result result;
	
	private final WorkflowTransicaoDao workflowTransicaoDao;
	private final WorkflowEtapaPerfilAcessoDao workflowEtapaPerfilAcessoDao;
	private final WorkflowDao workflowDao;
	private final EtapaDao etapaDao;
	private final PerfilDao perfilDao;
	private final UsuarioInfo usuarioInfo;
	
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	public WorkflowtransicaoController(Result result, UsuarioInfo usuarioInfo, WorkflowDao workflowDao, WorkflowTransicaoDao workflowTransicaoDao, 
			WorkflowEtapaPerfilAcessoDao workflowEtapaPerfilAcessoDao, EtapaDao etapaDao, 
			PerfilDao perfilDao, Empresa empresa, Organizacao organizacao, Usuario usuario) {

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.workflowDao = workflowDao;
		this.workflowTransicaoDao = workflowTransicaoDao;
		this.etapaDao = etapaDao;
		this.perfilDao = perfilDao;
		this.workflowEtapaPerfilAcessoDao = workflowEtapaPerfilAcessoDao;
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();
		this.usuario = usuarioInfo.getUsuario();

	}

	@Get 
	@Path("/workflowtransicao/cadastro")
	public void cadastro() {

		result.include("workflows", this.workflowDao.buscaWorkflowsByEmpresaOrganizacao(usuarioInfo.getEmpresa().getEmpresa_id(), usuarioInfo.getOrganizacao().getOrganizacao_id()));
		result.include("workflowTransicoes", this.workflowTransicaoDao.buscaTodosWorkflowTransicao(empresa.getEmpresa_id(),organizacao.getOrganizacao_id()));

	}

	@Post 
	@Path("/workflowtransicao/salva")
	public void salva(WorkflowTransicao workflowTransicao) {

		Calendar dataAtual = Calendar.getInstance();

		String mensagem = "";

		try {

			if (this.workflowTransicaoDao.buscaWorkflowTransicaoPorEmpresaOrganizacaoEtapasPerfilWorkFlow(workflowTransicao.getEmpresa().getEmpresa_id(),workflowTransicao.getOrganizacao().getOrganizacao_id(),
					workflowTransicao.getEtapa().getEtapa_id(), workflowTransicao.getEtapaProximo().getEtapa_id(), workflowTransicao.getPerfil().getPerfil_id(), workflowTransicao.getWorkflow().getWorkflow_id()) == null) {				

				workflowTransicao.setCreated(dataAtual);
				workflowTransicao.setUpdated(dataAtual);
				workflowTransicao.setCreatedBy(usuarioInfo.getUsuario());
				workflowTransicao.setUpdatedBy(usuarioInfo.getUsuario());

				workflowTransicao.setIsActive(workflowTransicao.getIsActive() == null || workflowTransicao.getIsActive() == false ? false : true);

				this.workflowTransicaoDao.beginTransaction();
				this.workflowTransicaoDao.adiciona(workflowTransicao);
				this.workflowTransicaoDao.commit();

				WorkflowEtapaPerfilAcesso workflowetapaperfilAcesso = new WorkflowEtapaPerfilAcesso();

				workflowetapaperfilAcesso.setEmpresa(empresa);
				workflowetapaperfilAcesso.setOrganizacao(organizacao);
				workflowetapaperfilAcesso.setWorkflow(workflowTransicao.getWorkflow());
				workflowetapaperfilAcesso.setEtapa(workflowTransicao.getEtapa());
				workflowetapaperfilAcesso.setPerfil(workflowTransicao.getPerfil());
				workflowetapaperfilAcesso.setCreated(dataAtual);
				workflowetapaperfilAcesso.setCreatedBy(usuario);
				workflowetapaperfilAcesso.setIsActive(true);
				workflowetapaperfilAcesso.setIsLeituraEscrita(true);
				workflowetapaperfilAcesso.setIsUpload(false);

				if(this.workflowEtapaPerfilAcessoDao.
						buscaWorkflowEtapaPerfilAcessoPorEmpresaOrganizacaoWorkflowEtapaPerfil(
									empresa.getEmpresa_id(), 
									organizacao.getOrganizacao_id(), 
									workflowetapaperfilAcesso.getWorkflow().getWorkflow_id(), 
									workflowetapaperfilAcesso.getEtapa().getEtapa_id(), 
									workflowetapaperfilAcesso.getPerfil().getPerfil_id()) == null) {


					workflowEtapaPerfilAcessoDao.insert(workflowetapaperfilAcesso);


				}

				mensagem = "Etapa transição adicionado com sucesso.";				

			} else {

				mensagem = "Erro: Workflow transição já cadastrado.";

			}

		} catch (Exception e) {

				System.out.println(e);

				mensagem = "Erro: falha ao adicionar Workflow :";

		} finally{

			this.workflowTransicaoDao.clear();
			this.workflowTransicaoDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}
	
	@Get
	@Path("/workflowtransicao/busca.json")
	public void workflowTransicao(Long empresa_id, Long organizacao_id, Long workflowetapa_id, Long workflowetapaproximo_id, Long perfil_id) {	

		result.use(Results.json()).withoutRoot().from(workflowTransicaoDao.buscaWorkflowTransicaoPorEmpresaOrganizacaoEtapaProximo(empresa_id, organizacao_id, workflowetapa_id, workflowetapaproximo_id, perfil_id)).serialize();

	}

	@Post
	@Path("/workflowtransicao/lista")
	public void lista(Long empresa_id, Long organizacao_id, String nome) {

		result.include("etapa", this.etapaDao.buscaEtapaByEmpresaOrganizacaoNome(empresa_id, organizacao_id, nome));

	}

	@Post
	@Path("/workflowtransicao/workflowperfil")
	public void workflowperfil(Long empresa_id, Long organizacao_id, Long workflow_id){

		result.include("perfis",this.perfilDao.buscaPerfisToWorkflowTransicaoByWorkflow(empresa_id, organizacao_id, workflow_id));

	}

	@Post
	@Path("/workflowtransicao/workflowtransicaoetapas")
	public void workflowtransicaoetapas(Long empresa_id, Long organizacao_id, Long workflow_id){

		result.include("etapas",this.etapaDao.buscaEtapasByEmpresaOrganizacaoWorkflow(empresa_id, organizacao_id, workflow_id));

	}

	@Get
	public void msg() {

	}
}