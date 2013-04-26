package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.WorkflowEtapaDao;
import br.com.sgo.dao.WorkflowTransicaoDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.WorkflowTransicao;

@Resource
public class WorkflowtransicaoController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final WorkflowTransicaoDao workflowTransicaoDao;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final WorkflowEtapaDao workflowEtapaDao;

	public WorkflowtransicaoController(Result result,  UsuarioInfo usuarioInfo, WorkflowTransicaoDao workflowTransicaoDao, EmpresaDao empresaDao, OrganizacaoDao organizacaoDao, WorkflowEtapaDao workflowEtapaDao) {

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.workflowTransicaoDao = workflowTransicaoDao;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.workflowEtapaDao = workflowEtapaDao;

	}

	@Get
	@Public
	@Path("/workflowtransicao/cadastro")
	public void cadastro() {

		result.include("workflowEtapas", this.workflowEtapaDao.buscaTodosWorkflowEtapa());
		result.include("workflowTransicoes", this.workflowTransicaoDao.buscaTodosWorkflowTransicao());

	}

	@Post
	@Public
	@Path("/workflowtransicao/salva")
	public void salva(WorkflowTransicao workflowTransicao) {

		String mensagem = "";

		try {

			if (this.workflowTransicaoDao.buscaWorkflowTransicaoPorEmpresaOrganizacaoWorkflowEtapaProximo(workflowTransicao.getEmpresa().getEmpresa_id(),workflowTransicao.getOrganizacao().getOrganizacao_id(),
					workflowTransicao.getWorkflowEtapa().getWorkflowEtapa_id(), workflowTransicao.getWorkflowEtapaProximo().getWorkflowEtapa_id(), workflowTransicao.getPerfil().getPerfil_id()) == null) {				
				
				this.workflowTransicaoDao.beginTransaction();
				this.workflowTransicaoDao.adiciona(workflowTransicao);
				this.workflowTransicaoDao.commit();

				mensagem = "Etapa transição adicionado com sucesso.";				

			} else {

				mensagem = "Workflow transição já cadastrado.";

			}

		} catch (Exception e) {

				mensagem = "Erro ao adicionar Workflow :";

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

		result.include("workflowsEtapa", this.workflowEtapaDao.buscaWorkflowsPorEmpresaOrganizacaoNome(empresa_id, organizacao_id, nome));

	}

	@Get
	public void msg() {

	}
}