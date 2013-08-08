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
import br.com.sgo.dao.EtapaDao;
import br.com.sgo.dao.FormularioDao;
import br.com.sgo.dao.HistoricoContratoDao;
import br.com.sgo.dao.HistoricoControleFormularioDao;
import br.com.sgo.dao.TipoControleDao;
import br.com.sgo.dao.WorkflowDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.ControleFormulario;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Etapa;
import br.com.sgo.modelo.Formulario;
import br.com.sgo.modelo.HistoricoContrato;
import br.com.sgo.modelo.HistoricoControleFormulario;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Perfil;
import br.com.sgo.modelo.TipoControle;
import br.com.sgo.modelo.Usuario;
import br.com.sgo.modelo.Workflow;

@Resource
public class ControleformularioController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final EtapaDao etapaDao;
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
	private Collection<Etapa> etapas;
	private Collection<Etapa> motivos;

	public ControleformularioController(Result result,Formulario formulario, FormularioDao formularioDao,EtapaDao etapaDao,UsuarioInfo usuarioInfo,
			ControleFormularioDao controleFormularioDao,HistoricoControleFormularioDao historicoControleFormularioDao,ContratoDao contratoDao,WorkflowDao workflowDao,
			Empresa empresa,Organizacao organizacao,Usuario usuario,Perfil perfil,TipoControleDao tipoControleDao,HistoricoContratoDao historicoContratoDao){		

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.formulario = formulario;
		this.formularioDao = formularioDao;
		this.etapaDao = etapaDao;
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
		TipoControle tp = this.tipoControleDao.buscaTipoControleByEmpOrgNome(1l,1l,"Pós Venda");

		formulario.setContratos(this.contratoDao.buscaContratoByFormulario(formulario_id));

		posvenda = this.controleFormularioDao.buscaControleByFormularioTipoControle(formulario_id, tp.getTipoControle_id());

		Double countValorParcela = new Double(0.00);
		Double countValorLiquido = new Double(0.00);
		Integer countContratos = 0;

		for(Contrato c : formulario.getContratos()){
			countValorParcela += c.getValorParcela();
			countValorLiquido += c.getValorLiquido();
			countContratos += 1;
		}
		
		if(posvenda != null)
			result.include("historicos",this.historicoControleFormularioDao.buscaHistoricoByFormularioControle(formulario.getFormulario_id(),posvenda.getControleFormulario_id()));

		if(posvenda == null)
			posvenda = new ControleFormulario();

		posvenda.setFormulario(formulario);
		posvenda.setTipoControle(this.tipoControleDao.buscaTipoControleByEmpOrgNome(1l,1l,"Pós Venda"));
		posvenda.setUsuario(usuario);
		posvenda.setPerfil(perfil);

		workflow = this.workflowDao.buscaWorkflowPorNome(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Status Pós Venda");

		etapas = etapaDao.buscaEtapasByEmpresaOrganizacaoWorkflow(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), workflow.getWorkflow_id());
		posvenda.setWorkflow(workflow);

		workflow = this.workflowDao.buscaWorkflowPorNome(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Motivos Pós Venda");

		motivos = etapaDao.buscaEtapasByEmpresaOrganizacaoWorkflow(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), workflow.getWorkflow_id());

		posvenda.setWorkflowPendencia(workflow);

		result.include("posvenda",posvenda);
		result.include("formulario",formulario);
		result.include("countContratos",countContratos);
		result.include("countValorParcela",countValorParcela);
		result.include("countValorLiquido",countValorLiquido);
		result.include("etapas", etapas);
		result.include("motivos", motivos);
		

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
			this.posvenda.setEtapa(this.etapaDao.buscaEtapaById(posvenda.getEtapa().getEtapa_id()));
			this.posvenda.setEtapaPendencia(posvenda.getEtapaPendencia().getEtapa_id() == null ? null : this.etapaDao.buscaEtapaById(posvenda.getEtapaPendencia().getEtapa_id()));
			this.posvenda.setCreatedBy(posvenda.getCreatedBy());
			this.posvenda.setCreated(posvenda.getCreated());
			this.posvenda.setConfirmaPrazo(posvenda.getConfirmaPrazo());
			this.posvenda.setQuantidadeContrato(posvenda.getQuantidadeContrato());
			this.posvenda.setValorLiquido(posvenda.getValorLiquido());
			this.posvenda.setValorParcela(posvenda.getValorParcela());

			status = this.posvenda.getEtapa().getNome().equals("reprovado") || 
					this.posvenda.getEtapa().getNome().equals("pendente") ? "Recalcular" : "Em Assinatura";

			this.controleFormularioDao.beginTransaction();
			this.controleFormularioDao.atualiza(this.posvenda);
			this.controleFormularioDao.commit();

		} else {

			if(posvenda.getEtapa().getEtapa_id() != null){
				posvenda.setEtapa(this.etapaDao.buscaEtapaById(posvenda.getEtapa().getEtapa_id()));
			} else {
				posvenda.setEtapa(null);
			}

			if(posvenda.getEtapaPendencia().getEtapa_id() != null) {
				posvenda.setEtapaPendencia(this.etapaDao.buscaEtapaById(posvenda.getEtapaPendencia().getEtapa_id()));
			} else {
				posvenda.setEtapaPendencia(null);
			}

			status = posvenda.getEtapa().getNome().equals("reprovado") || 
					posvenda.getEtapa().getNome().equals("pendente") ? "Recalcular" : "Em Assinatura";

			posvenda.setEmpresa(empresa);
			posvenda.setOrganizacao(organizacao);
			posvenda.setIsActive(true);
			posvenda.setCreatedBy(usuario);
			posvenda.setCreated(GregorianCalendar.getInstance());
			
			
			System.out.println("AQUI : " + posvenda.getEtapa().getEtapa_id());

			controleFormularioDao.beginTransaction();
			controleFormularioDao.adiciona(posvenda);
			controleFormularioDao.commit();

		}

		for(Contrato c : formulario.getContratos()){

			c = this.contratoDao.load(c.getContrato_id());

			if(c.getEtapa().getNome().equals("Aguardando Pós Venda") || c.getEtapa().getNome().equals("Aguardando Qualidade"))
				c.setEtapa(this.etapaDao.buscaEtapaByEmpresaOrganizacaoNome(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), status));	

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