package br.com.sgo.controller;

import java.util.Collection;
import java.util.GregorianCalendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.ContratoDao;
import br.com.sgo.dao.ControleFormularioDao;
import br.com.sgo.dao.FormularioDao;
import br.com.sgo.dao.HistoricoContratoDao;
import br.com.sgo.dao.HistoricoControleFormularioDao;
import br.com.sgo.dao.TipoControleDao;
import br.com.sgo.dao.WorkflowDao;
import br.com.sgo.dao.WorkflowEtapaDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.ControleFormulario;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Formulario;
import br.com.sgo.modelo.HistoricoContrato;
import br.com.sgo.modelo.HistoricoControleFormulario;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Perfil;
import br.com.sgo.modelo.Usuario;
import br.com.sgo.modelo.Workflow;
import br.com.sgo.modelo.WorkflowEtapa;

@Resource
public class ControleformularioController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final WorkflowEtapaDao workFlowetapaDao;
	private final WorkflowDao workflowDao;
	private final FormularioDao formularioDao;
	private final ControleFormularioDao controleFormularioDao;
	private final HistoricoControleFormularioDao historicoControleFormularioDao;
	private final HistoricoContratoDao historicoContratoDao;
	private final TipoControleDao tipoControleDao;
	private final ContratoDao contratoDao;

	private Formulario formulario;
	private ControleFormulario posvenda;
	private ControleFormulario qualidade;
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;
	private Perfil perfil;
	private Workflow workflow;
	private Collection<WorkflowEtapa> etapas;
	private Collection<WorkflowEtapa> motivos;

	public ControleformularioController(Result result,Formulario formulario, FormularioDao formularioDao,WorkflowEtapaDao workFlowetapaDao,UsuarioInfo usuarioInfo,
			ControleFormularioDao controleFormularioDao,HistoricoControleFormularioDao historicoControleFormularioDao,ContratoDao contratoDao,WorkflowDao workflowDao,
			Empresa empresa,Organizacao organizacao,Usuario usuario,Perfil perfil,TipoControleDao tipoControleDao,HistoricoContratoDao historicoContratoDao){		

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.formulario = formulario;
		this.formularioDao = formularioDao;
		this.workFlowetapaDao = workFlowetapaDao;
		this.workflowDao = workflowDao;
		this.controleFormularioDao = controleFormularioDao;
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();
		this.usuario = usuarioInfo.getUsuario();
		this.perfil = usuarioInfo.getPerfil();
		this.historicoControleFormularioDao = historicoControleFormularioDao;
		this.tipoControleDao = tipoControleDao;
		this.contratoDao =contratoDao;
		this.historicoContratoDao = historicoContratoDao;

	}

	@Post
	@Path("/controleformulario/posvenda")
	public void posvenda(Long formulario_id){

		formulario = formularioDao.load(formulario_id);

		formulario.setContratos(this.contratoDao.buscaContratoByFormulario(formulario_id));

		posvenda = this.controleFormularioDao.buscaControleByContratoTipoControle(
								formulario_id, 
								this.tipoControleDao.buscaTipoControleByNome("Pós Venda").getTipoControle_id());

		Double countValorParcela = new Double(0.00);
		Double countValorLiquido = new Double(0.00);
		Integer countContratos = 0;

		for(Contrato c : formulario.getContratos()){
			countValorParcela += c.getValorParcela();
			countValorLiquido += c.getValorLiquido();
			countContratos += 1;
		}

		if(posvenda == null)
			posvenda = new ControleFormulario();

		posvenda.setFormulario(formulario);
		posvenda.setTipoControle(this.tipoControleDao.buscaTipoControleByNome("Pós Venda"));
		posvenda.setUsuario(usuario);
		posvenda.setPerfil(perfil);

		workflow = this.workflowDao.buscaWorkflowPorNome(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Status Pós Venda");
		etapas = workFlowetapaDao.buscaWorKFlowEtapaByWorkFlowPerfil(workflow.getWorkflow_id(), perfil.getPerfil_id());
		posvenda.setWorkflow(workflow);

		workflow = this.workflowDao.buscaWorkflowPorNome(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Motivos Pós Venda");
		motivos = workFlowetapaDao.buscaWorKFlowEtapaByWorkFlowPerfil(workflow.getWorkflow_id(), perfil.getPerfil_id());
		posvenda.setWorkflowPendencia(workflow);

		result.include("posvenda",posvenda);
		result.include("formulario",formulario);
		result.include("countContratos",countContratos);
		result.include("countValorParcela",countValorParcela);
		result.include("countValorLiquido",countValorLiquido);
		result.include("etapas", etapas);
		result.include("motivos", motivos);
		result.include("historicos",this.historicoControleFormularioDao.buscaHistoricoByFormularioControle(formulario.getFormulario_id(),posvenda.getControleFormulario_id()));

	}
	
	@Post
	@Path("/controleformulario/qualidade")
	public void qualidade(Long formulario_id){
		
		qualidade = new ControleFormulario();

		result.include("qualidade",qualidade);

	}
	
	@Post
	@Path("/controleformulario/salvaposvenda")
	public void salvaposvenda(ControleFormulario posvenda) {

		Formulario formulario = formularioDao.load(posvenda.getFormulario().getFormulario_id());
		formulario.setContratos(this.contratoDao.buscaContratoByFormulario(formulario.getFormulario_id()));
		String status = "";

		if(posvenda.getControleFormulario_id() != null){

			this.posvenda = this.controleFormularioDao.load(posvenda.getControleFormulario_id());
			this.posvenda.setWorkflowEtapa(this.workFlowetapaDao.buscaWorkflowEtapaById(posvenda.getWorkflowEtapa().getWorkflowEtapa_id()));
			this.posvenda.setWorkflowEtapaPendencia(posvenda.getWorkflowEtapaPendencia().getWorkflowEtapa_id() == null ? null : this.workFlowetapaDao.buscaWorkflowEtapaById(posvenda.getWorkflowEtapaPendencia().getWorkflowEtapa_id()));
			this.posvenda.setCreatedBy(posvenda.getCreatedBy());
			this.posvenda.setCreated(posvenda.getCreated());
			this.posvenda.setConfirmaPrazo(posvenda.getConfirmaPrazo());
			this.posvenda.setQuantidadeContrato(posvenda.getQuantidadeContrato());
			this.posvenda.setValorLiquido(posvenda.getValorLiquido());
			this.posvenda.setValorParcela(posvenda.getValorParcela());

			status = this.posvenda.getWorkflowEtapa().getNome().equals("reprovado") || 
					this.posvenda.getWorkflowEtapa().getNome().equals("pendente") ? "Recalcular" : "Em Assinatura";

			this.controleFormularioDao.beginTransaction();
			this.controleFormularioDao.atualiza(this.posvenda);
			this.controleFormularioDao.commit();

		} else {

			posvenda.setWorkflowEtapa(this.workFlowetapaDao.buscaWorkflowEtapaById(posvenda.getWorkflowEtapa().getWorkflowEtapa_id()));
			posvenda.setWorkflowEtapaPendencia(this.workFlowetapaDao.buscaWorkflowEtapaById(posvenda.getWorkflowEtapaPendencia().getWorkflowEtapa_id()));

			status = posvenda.getWorkflowEtapa().getNome().equals("reprovado") || 
					posvenda.getWorkflowEtapa().getNome().equals("pendente") ? "Recalcular" : "Em Assinatura";

			posvenda.setEmpresa(empresa);
			posvenda.setOrganizacao(organizacao);
			posvenda.setIsActive(true);
			posvenda.setCreatedBy(usuario);
			posvenda.setCreated(GregorianCalendar.getInstance());

			controleFormularioDao.beginTransaction();
			controleFormularioDao.adiciona(posvenda);
			controleFormularioDao.commit();

		}

		for(Contrato c : formulario.getContratos()){

			c = this.contratoDao.load(c.getContrato_id());

			if(c.getWorkflowEtapa().getNome().equals("Aguardando Pós Venda") || c.getWorkflowEtapa().getNome().equals("Aguardando Qualidade"))
				c.setWorkflowEtapa(this.workFlowetapaDao.buscaWorkflowPorEmpresaOrganizacaoWorflowEtapaNome(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), 
							c.getWorkflow().getWorkflow_id(), status));	
			
			
			
			contratoDao.beginTransaction();
			contratoDao.atualiza(c);
			contratoDao.commit();

			HistoricoContrato historico = new HistoricoContrato();
			historico.setEmpresa(empresa);
			historico.setOrganizacao(organizacao);
			historico.setIsActive(true);
			historico.setCreated(GregorianCalendar.getInstance());
			historico.setCreatedBy(usuarioInfo.getUsuario());
			historico.setObservacao("Status alterado para : " + status);
			historico.setContrato(c);
			
			historicoContratoDao.beginTransaction();
			historicoContratoDao.adiciona(historico);
			historicoContratoDao.commit();

		}

		historicoContratoDao.clear();
		historicoContratoDao.close();

		result.include("msg","Controle Pós Venda preenchido com sucesso.").redirectTo(this).msg();

	}
	
	@Post
	@Path("/controleformulario/incluiComunicacao")
	public void incluiComunicacao(HistoricoControleFormulario historico) {

		historico.setCreated(GregorianCalendar.getInstance());
		historico.setIsActive(true);

		historicoControleFormularioDao.beginTransaction();
		historicoControleFormularioDao.adiciona(historico);
		historicoControleFormularioDao.commit();

		result.redirectTo(FormularioController.class).visualiza(historico.getFormulario().getFormulario_id());

	}

	@Get
	public void msg(){

	}

}