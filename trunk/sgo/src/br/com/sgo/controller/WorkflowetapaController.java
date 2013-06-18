package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.EtapaDao;
import br.com.sgo.dao.WorkflowDao;
import br.com.sgo.dao.WorkflowEtapaDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.WorkflowEtapa;

@Resource
public class WorkflowetapaController {

	private final Result result;	
	private final UsuarioInfo usuarioInfo;
	private final EtapaDao etapaDao;
	private final WorkflowEtapaDao workflowEtapaDao;
	private final WorkflowDao workflowDao;
	private Empresa empresa;
	private Organizacao organizacao;

	public WorkflowetapaController(Result result, Empresa empresa, Organizacao organizacao,UsuarioInfo usuarioInfo, EtapaDao etapaDao, WorkflowEtapaDao workflowEtapaDao, 
			WorkflowDao workflowDao) {

		this.result = result;
		this.workflowDao = workflowDao;
		this.workflowEtapaDao = workflowEtapaDao;
		this.etapaDao = etapaDao;
		this.usuarioInfo = usuarioInfo;
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();

	}

	@Get
	@Path("/workflowetapa/cadastro")
	public void cadastro() {

		result.include("workflowEtapas", this.workflowEtapaDao.buscaAllWorkflowEtapaByEmpresaOrganizacao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
		result.include("workflows", this.workflowDao.buscaWorkflowsByEmpresaOrganizacao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
		result.include("etapas",this.etapaDao.buscaEtapasByEmpresaOrganizacao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));

	}

	@Post
	@Path("/workflowetapa/salva")
	public void salva(WorkflowEtapa workflowEtapa) {

		String mensagem = "";

		try {

			if (this.workflowEtapaDao.buscaWorkflowEtapaPorEmpresaOrganizacaoWorkflowEtapa(usuarioInfo.getEmpresa().getEmpresa_id()
					, usuarioInfo.getOrganizacao().getOrganizacao_id(), workflowEtapa.getWorkflow().getWorkflow_id(), workflowEtapa.getEtapa().getEtapa_id()) == null) {				

				workflowEtapa.setEmpresa(this.empresa);
				workflowEtapa.setOrganizacao(this.organizacao);

				workflowEtapa.setIsActive(workflowEtapa.getIsActive() == null || workflowEtapa.getIsActive() == false ? false : true);
				workflowEtapa.setIsLeituraEscrita(workflowEtapa.getIsLeituraEscrita() == null || workflowEtapa.getIsLeituraEscrita() == false ? false : true);

				this.workflowEtapaDao.insert(workflowEtapa);

				mensagem = "Etapa adicionado com sucesso para o workflow. " ;

			} else {

				mensagem = "Erro: Etapa já cadastrado para o workflow. " ;

			} 

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar a Etapa do Workflow. ";			

		} finally{

			this.workflowEtapaDao.clear();
			this.workflowEtapaDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();
	}
	
	@Get
	@Path("/workflowetapa/busca.json")
	public void workflowEtapa(Long empresa_id, Long organizacao_id, String nome) {	
		result.include("workflowEtapas", this.workflowEtapaDao.buscaAllWorkflowEtapaByEmpresaOrganizacao(usuarioInfo.getEmpresa().getEmpresa_id(), usuarioInfo.getOrganizacao().getOrganizacao_id()));	
	}

	@Post
	@Path("/workflowetapa/lista")
	public void lista(Long empresa_id, Long organizacao_id) {

		result.include("workflowEtapas", this.workflowEtapaDao.buscaAllWorkflowEtapaByEmpresaOrganizacao(usuarioInfo.getEmpresa().getEmpresa_id(), usuarioInfo.getOrganizacao().getOrganizacao_id()));

	}
	
	@Get
	public void msg() {

	}
}