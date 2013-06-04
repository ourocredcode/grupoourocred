package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.PerfilDao;
import br.com.sgo.dao.WorkflowDao;
import br.com.sgo.dao.EtapaDao;
import br.com.sgo.dao.WorkflowTransicaoDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.WorkflowTransicao;

@Resource
public class WorkflowtransicaoController {

	private final Result result;
	
	private final WorkflowTransicaoDao workflowTransicaoDao;
	private final WorkflowDao workflowDao;
	private final EtapaDao workflowEtapaDao;
	private final PerfilDao perfilDao;
	private final UsuarioInfo usuarioInfo;

	public WorkflowtransicaoController(Result result, UsuarioInfo usuarioInfo, WorkflowDao workflowDao, WorkflowTransicaoDao workflowTransicaoDao, EtapaDao workflowEtapaDao, PerfilDao perfilDao) {

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.workflowDao = workflowDao;
		this.workflowTransicaoDao = workflowTransicaoDao;
		this.workflowEtapaDao = workflowEtapaDao;
		this.perfilDao = perfilDao;

	}

	@Get @Path("/workflowtransicao/cadastro")
	@Public
	public void cadastro() {

		result.include("workflows", this.workflowDao.buscaWorkflowsByEmpresaOrganizacao(usuarioInfo.getEmpresa().getEmpresa_id(), usuarioInfo.getOrganizacao().getOrganizacao_id()));
		result.include("workflowTransicoes", this.workflowTransicaoDao.buscaTodosWorkflowTransicao());

	}

	@Post @Path("/workflowtransicao/salva")
	@Public
	public void salva(WorkflowTransicao workflowTransicao) {

		String mensagem = "";
		
		try {
			

			if (this.workflowTransicaoDao.buscaWorkflowTransicaoPorEmpresaOrganizacaoWorkflowEtapaProximo(workflowTransicao.getEmpresa().getEmpresa_id(),workflowTransicao.getOrganizacao().getOrganizacao_id(),
					workflowTransicao.getEtapa().getEtapa_id(), workflowTransicao.getEtapaProximo().getEtapa_id(), workflowTransicao.getPerfil().getPerfil_id()) == null) {				

				workflowTransicao.setIsActive(workflowTransicao.getIsActive() == null ? false : true);
				
				this.workflowTransicaoDao.beginTransaction();
				this.workflowTransicaoDao.adiciona(workflowTransicao);
				this.workflowTransicaoDao.commit();

				mensagem = "Etapa transição adicionado com sucesso.";				

			} else {

				mensagem = "Erro: Workflow transição já cadastrado.";

			}

		} catch (Exception e) {

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
	@Public
	public void workflowTransicao(Long empresa_id, Long organizacao_id, Long workflowetapa_id, Long workflowetapaproximo_id, Long perfil_id) {	

		result.use(Results.json()).withoutRoot().from(workflowTransicaoDao.buscaWorkflowTransicaoPorEmpresaOrganizacaoWorkflowEtapaProximo(empresa_id, organizacao_id, workflowetapa_id, workflowetapaproximo_id, perfil_id)).serialize();

	}

	@Post
	@Path("/workflowtransicao/lista")
	@Public
	public void lista(Long empresa_id, Long organizacao_id, String nome) {

		result.include("workflowsEtapa", this.workflowEtapaDao.buscaEtapaByEmpresaOrganizacaoNome(empresa_id, organizacao_id, nome));

	}

	@Post
	@Public
	@Path("/workflowtransicao/workflowperfil")
	public void workflowperfil(Long empresa_id, Long organizacao_id, Long workflow_id){

		result.include("perfis",this.perfilDao.buscaPerfisToWorkflowTransicaoByWorkflow(empresa_id, organizacao_id, workflow_id));

	}

	@Post
	@Public
	@Path("/workflowtransicao/workflowtransicaoetapas")
	public void workflowtransicaoetapas(Long empresa_id, Long organizacao_id, Long workflow_id){

		//TODO
		//result.include("workflowEtapas",this.workflowEtapaDao.buscaEtapasToWorkflowEtapaPerfilByEmpresaOrganizacaoWorkflow(empresa_id, organizacao_id, workflow_id));

	}

	@Get
	public void msg() {

	}
}