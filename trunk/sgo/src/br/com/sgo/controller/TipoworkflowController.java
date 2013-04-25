package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.TipoWorkflowDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.TipoWorkflow;

@Resource
public class TipoworkflowController {

	private final Result result;
	private final Validator validator;
	private final UsuarioInfo usuarioInfo;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final TipoWorkflowDao tipoWorkflowDao;

	public TipoworkflowController(Result result, Validator validator, UsuarioInfo usuarioInfo, EmpresaDao empresaDao, OrganizacaoDao organizacaoDao, TipoWorkflowDao tipoWorkflowDao) {
		this.result = result;
		this.validator = validator;
		this.usuarioInfo = usuarioInfo;
		this.tipoWorkflowDao = tipoWorkflowDao;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
	}

	@Get
	@Public
	@Path("/tipoworkflow/cadastro")
	public void cadastro() {
		
	}

	@Post
	@Public
	@Path("/tipoworkflow/salva")
	public void salva(TipoWorkflow tipoWorkflow) {

		validator.validate(tipoWorkflowDao);
		validator.onErrorUsePageOf(this).cadastro();

		String mensagem = "";

		try {

			tipoWorkflow.setEmpresa(this.empresaDao.load(usuarioInfo.getEmpresa().getEmpresa_id()));
			tipoWorkflow.setOrganizacao(this.organizacaoDao.load(usuarioInfo.getOrganizacao().getOrganizacao_id()));			
			tipoWorkflow.setIsActive(tipoWorkflow.getIsActive() == null ? false: true);
			
			this.tipoWorkflowDao.beginTransaction();
			this.tipoWorkflowDao.adiciona(tipoWorkflow);
			this.tipoWorkflowDao.commit();		

			mensagem = "TipoWorkflow adicionado com sucesso";

		} catch (Exception e) {

			System.out.println(e);

			if (e.getMessage().indexOf("PK_USUARIOPERFIL") != -1) {
				mensagem = "Erro: Usuário Perfil Acesso  já existente.";
			} else {
				mensagem = "Erro ao adicionar Usuário Perfil:";
			}

		} finally{
			this.tipoWorkflowDao.clear();
			this.tipoWorkflowDao.close();
			result.include("notice", mensagem);
			result.redirectTo(this).cadastro();	
		}
		
	}

	@Get
	@Path("/tipoworkflow/busca.json")
	@Public
	public void tipoworkflow(Long empresa_id, Long organizacao_id, String nome) {	
		result.use(Results.json()).withoutRoot().from(tipoWorkflowDao.buscaTipoWorkflowPorNome(empresa_id,organizacao_id, nome)).serialize();
	}

	/*
	@Get
	@Path("/tipodadobd/busca.json")
	@Public
	public void tiposdadobd(String nome) {
		result.use(Results.json()).withoutRoot().from(tipoDadoBdDao.buscaTiposDado(nome)).serialize();
	}
	*/

	@Post
	@Path("/tipoworkflow/lista")
	@Public
	public void lista(Long empresa_id, Long organizacao_id, String nome) {
		result.include("tiposWorkflow", this.tipoWorkflowDao.buscaTipoWorkflowPorNome(empresa_id, organizacao_id, nome));
	}

}