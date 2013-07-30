package br.com.sgo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.AgenteDao;
import br.com.sgo.dao.ContratoDao;
import br.com.sgo.dao.ControleDao;
import br.com.sgo.dao.EtapaDao;
import br.com.sgo.dao.HistoricoControleDao;
import br.com.sgo.dao.TipoControleDao;
import br.com.sgo.dao.TipoWorkflowDao;
import br.com.sgo.dao.UsuarioDao;
import br.com.sgo.dao.WorkflowDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Controle;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Etapa;
import br.com.sgo.modelo.HistoricoControle;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.TipoWorkflow;
import br.com.sgo.modelo.Usuario;

@Resource
public class ControleController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final EtapaDao workFlowetapaDao;
	private final ContratoDao contratoDao;
	private final ControleDao controleDao;
	private final HistoricoControleDao historicoControleDao;
	private final EtapaDao etapaDao;
	private final AgenteDao agenteDao;
	private final TipoControleDao tipoControleDao;
	private final TipoWorkflowDao tipoWorkflowDao;
	private final WorkflowDao workflowDao;
	private final UsuarioDao usuarioDao;

	private Contrato contrato;
	private Controle averbacao;
	private Controle boleto;
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;
	private Collection<Etapa> etapas;

	public ControleController(Result result,Contrato contrato, ContratoDao contratoDao,EtapaDao workFlowetapaDao,UsuarioInfo usuarioInfo,ControleDao controleDao,
			TipoWorkflowDao tipoWorkflowDao, EtapaDao etapaDao ,Empresa empresa,Organizacao organizacao,Usuario usuario,HistoricoControleDao historicoControleDao,
			TipoControleDao tipoControleDao,AgenteDao agenteDao,UsuarioDao usuarioDao,WorkflowDao workflowDao){		

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.contrato = contrato;
		this.contratoDao = contratoDao;
		this.workFlowetapaDao = workFlowetapaDao;
		this.controleDao = controleDao;
		this.etapaDao = etapaDao;
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();
		this.usuario = usuarioInfo.getUsuario();
		this.historicoControleDao = historicoControleDao;
		this.tipoControleDao = tipoControleDao;
		this.tipoWorkflowDao = tipoWorkflowDao;
		this.agenteDao = agenteDao;
		this.usuarioDao = usuarioDao;
		this.workflowDao = workflowDao;

	}

	@Post
	@Path("/controle/boleto")
	public void boleto(Long contrato_id){

		boleto = this.controleDao.buscaControleByContratoTipoControle(contrato_id, tipoControleDao.buscaTipoControleByEmpOrgNome(1l,1l,"Boleto").getTipoControle_id());
		
		if(boleto == null){
			boleto = new Controle();
		}

		boleto.setContrato(this.contratoDao.buscaContratoById(contrato_id));

		result.include("boleto",boleto);

		TipoWorkflow t = this.tipoWorkflowDao.buscaTipoWorkflowPorEmpresaOrganizacaoNomeExato(1l,1l, "Controle Contrato");

		result.include("etapas",this.etapaDao.buscaEtapasByEmpresaOrganizacaoTipoWorkflowDistinct(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), t.getTipoWorkflow_id()));
		result.include("agentes",this.agenteDao.buscaAgenteToControleBancoByEmpOrg(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
		result.include("atuantes",this.usuarioDao.buscaUsuariosByPerfilDepartamento(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Administrativo", "Apoio Comercial"));

	}
	
	@Post
	@Path("/controle/averbacao")
	public void averbacao(Long contrato_id){

		averbacao = this.controleDao.buscaControleByContratoTipoControle(contrato_id, tipoControleDao.buscaTipoControleByEmpOrgNome(1l,1l,"Averbacao").getTipoControle_id());

		if(averbacao == null){
			averbacao = new Controle();
		}
		
		averbacao.setContrato(this.contratoDao.buscaContratoById(contrato_id));

		result.include("averbacao",averbacao);
		
		TipoWorkflow t = this.tipoWorkflowDao.buscaTipoWorkflowPorEmpresaOrganizacaoNomeExato(1l,1l, "Controle Contrato");

		result.include("etapas",this.etapaDao.buscaEtapasByEmpresaOrganizacaoTipoWorkflowDistinct(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), t.getTipoWorkflow_id()));
		result.include("agentes",this.agenteDao.buscaAgenteToControleBancoByEmpOrg(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
		result.include("atuantes",this.usuarioDao.buscaUsuariosByPerfilDepartamento(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Administrativo", "Apoio Comercial"));

	}

	@Post
	@Path("/controle/averbacao/salva")
	public void salva_averbacao(Controle averbacao, String observacao) {

		List<String> log = new ArrayList<String>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calInicial =  new GregorianCalendar(9999, 0, 1);

		if(averbacao.getControle_id() != null){

			this.averbacao = controleDao.load(averbacao.getControle_id());
			this.averbacao.setDataAtuacao(GregorianCalendar.getInstance());
			this.averbacao.setContrato(contratoDao.load(averbacao.getContrato().getContrato_id()));
			this.averbacao.setProximoAtuante(usuarioInfo.getUsuario());

			this.averbacao.setDataProximaAtuacao(this.averbacao.getDataProximaAtuacao() == null ? calInicial : this.averbacao.getDataProximaAtuacao());
			averbacao.setDataProximaAtuacao(averbacao.getDataProximaAtuacao() == null ? calInicial : averbacao.getDataProximaAtuacao());
			this.averbacao.setDataPrevisao(this.averbacao.getDataPrevisao() == null ? calInicial : this.averbacao.getDataPrevisao());
			averbacao.setDataPrevisao(averbacao.getDataPrevisao() == null ? calInicial : averbacao.getDataPrevisao());

			if(!(this.averbacao.getDataPrevisao().compareTo(averbacao.getDataPrevisao() == null ? calInicial : averbacao.getDataPrevisao()) == 0)) {

				if (this.averbacao.getDataPrevisao().compareTo(calInicial) == 0){

					log.add("Data de próxima atuação / previsão alterada para : " + dateFormat.format(averbacao.getDataPrevisao().getTime()));
					this.averbacao.setDataPrevisao(averbacao.getDataPrevisao());

				} else if (averbacao.getDataPrevisao().compareTo(calInicial) != 0) {

					log.add("Data de próxima atuação / previsão alterada de : " + dateFormat.format(this.averbacao.getDataPrevisao().getTime()) + " para : " + dateFormat.format(averbacao.getDataPrevisao().getTime()));
					this.averbacao.setDataPrevisao(averbacao.getDataPrevisao());

				} else if (averbacao.getDataPrevisao().compareTo(calInicial) == 0) {
					log.add("Data de próxima atuação / previsão alterada de : " + dateFormat.format(this.averbacao.getDataPrevisao().getTime()) + " para : em branco.");
					this.averbacao.setDataPrevisao(null);
				}
			}

			if(!(this.averbacao.getDataProximaAtuacao().compareTo(averbacao.getDataProximaAtuacao() == null ? calInicial : averbacao.getDataProximaAtuacao()) == 0)) {

				if (this.averbacao.getDataProximaAtuacao().compareTo(calInicial) == 0){

					log.add("Data de próxima atuação alterada para : " + dateFormat.format(averbacao.getDataProximaAtuacao().getTime()));
					this.averbacao.setDataProximaAtuacao(averbacao.getDataProximaAtuacao());

				} else if (averbacao.getDataProximaAtuacao().compareTo(calInicial) != 0) {

					log.add("Data de próxima atuação alterada de : " + dateFormat.format(this.averbacao.getDataProximaAtuacao().getTime()) + " para : " + dateFormat.format(averbacao.getDataProximaAtuacao().getTime()));
					this.averbacao.setDataProximaAtuacao(averbacao.getDataProximaAtuacao());

				} else if (averbacao.getDataProximaAtuacao().compareTo(calInicial) == 0) {
					log.add("Data de próxima atuação alterada de : " + dateFormat.format(this.averbacao.getDataProximaAtuacao().getTime()) + " para : em branco.");
					this.averbacao.setDataProximaAtuacao(null);
				}
			}

			if(this.averbacao.getDataPrevisao() != null) {
				if(this.averbacao.getDataPrevisao().compareTo(calInicial) == 0)
					this.averbacao.setDataPrevisao(null);
			}

			if(this.averbacao.getDataProximaAtuacao() != null) {
				if(this.averbacao.getDataProximaAtuacao().compareTo(calInicial) == 0)
					this.averbacao.setDataProximaAtuacao(null);
			}

			if(averbacao.getEtapa().getEtapa_id() != null){

				Etapa e = this.etapaDao.buscaEtapaById(averbacao.getEtapa().getEtapa_id());

				if(this.averbacao.getEtapa() == null || (this.averbacao.getEtapa().getEtapa_id() != averbacao.getEtapa().getEtapa_id())){
					this.averbacao.setEtapa(e);
					log.add("Procedimento alterado para : " + e.getNome());
				}
			}
			
			if(averbacao.getEtapaProximo().getEtapa_id() != null){
				
				Etapa e = this.etapaDao.buscaEtapaById(averbacao.getEtapaProximo().getEtapa_id());
				
				if(this.averbacao.getEtapaProximo() == null || (this.averbacao.getEtapaProximo().getEtapa_id() != averbacao.getEtapaProximo().getEtapa_id())){
					this.averbacao.setEtapaProximo(e);
					log.add("Próximo Procedimento alterado para : " + e.getNome());
				}
			
			}

			if(averbacao.getProximoAtuante().getUsuario_id() != null){

				Usuario u = this.usuarioDao.buscaUsuarioById(averbacao.getProximoAtuante().getUsuario_id());

				if(this.averbacao.getProximoAtuante() == null || (this.averbacao.getProximoAtuante().getUsuario_id() != averbacao.getProximoAtuante().getUsuario_id()) ){
					this.averbacao.setProximoAtuante(u);
					log.add("Próximo Atuante alterado para : " + u.getNome());
				}

			}
			
			controleDao.beginTransaction();
			controleDao.atualiza(this.averbacao);
			controleDao.commit();
			
			if(!observacao.equals("")){
				log.add(observacao);
			}
			
			for(String l : log){

				HistoricoControle historico = new HistoricoControle();
				historico.setEmpresa(empresa);
				historico.setOrganizacao(organizacao);
				historico.setIsActive(true);
				historico.setCreatedBy(usuario);
				historico.setCreated(GregorianCalendar.getInstance());
				historico.setObservacao(l);
				historico.setControle(this.averbacao);
				historico.setContrato(this.averbacao.getContrato());

				this.historicoControleDao.beginTransaction();
				this.historicoControleDao.adiciona(historico);
				this.historicoControleDao.commit();

			}

		} else {

			averbacao.setDataPrimeiraAtuacao(GregorianCalendar.getInstance());
			averbacao.setDataAtuacao(GregorianCalendar.getInstance());
			averbacao.setContrato(contratoDao.load(averbacao.getContrato().getContrato_id()));
			averbacao.setProximoAtuante(usuarioInfo.getUsuario());
			averbacao.setEmpresa(empresa);
			averbacao.setOrganizacao(organizacao);
			averbacao.setCreated(GregorianCalendar.getInstance());
			averbacao.setCreatedBy(usuario);
			averbacao.setIsActive(true);
			averbacao.setTipoControle(tipoControleDao.buscaTipoControleByEmpOrgNome(1l,1l,"Averbacao"));
			averbacao.setEtapa(averbacao.getEtapa().getEtapa_id() == null ? null : averbacao.getEtapa());
			averbacao.setEtapaProximo(averbacao.getEtapaProximo().getEtapa_id() == null ? null : averbacao.getEtapaProximo());
			averbacao.setProximoAtuante(averbacao.getProximoAtuante().getUsuario_id() == null ? null : averbacao.getProximoAtuante());
			averbacao.setPerfil(usuarioInfo.getPerfil());
			averbacao.setWorkflow(this.workflowDao.buscaWorkflowPorNome(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Status Controle Averbação"));

			controleDao.beginTransaction();
			controleDao.adiciona(averbacao);
			controleDao.commit();

			if(!observacao.equals("")){
				log.add(observacao);
			}

			for(String l : log){

				HistoricoControle historico = new HistoricoControle();
				historico.setEmpresa(empresa);
				historico.setOrganizacao(organizacao);
				historico.setIsActive(true);
				historico.setCreatedBy(usuario);
				historico.setCreated(GregorianCalendar.getInstance());
				historico.setObservacao(l);
				historico.setControle(averbacao);
				historico.setContrato(averbacao.getContrato());

				this.historicoControleDao.beginTransaction();
				this.historicoControleDao.adiciona(historico);
				this.historicoControleDao.commit();

			}

		}

		result.include("msg","ok").redirectTo(this).msg();

	}
	
	@Post
	@Path("/controle/boleto/salva")
	public void salva_boleto(Controle boleto, String observacao) {

		List<String> log = new ArrayList<String>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calInicial =  new GregorianCalendar(9999, 0, 1);

		if(boleto.getControle_id() != null){

			this.boleto = controleDao.load(boleto.getControle_id());

			this.boleto.setDataAtuacao(GregorianCalendar.getInstance());

			this.boleto.setDataProximaAtuacao(this.boleto.getDataProximaAtuacao() == null ? calInicial : this.boleto.getDataProximaAtuacao());
			boleto.setDataProximaAtuacao(boleto.getDataProximaAtuacao() == null ? calInicial : boleto.getDataProximaAtuacao());
			this.boleto.setDataPrevisao(this.boleto.getDataPrevisao() == null ? calInicial : this.boleto.getDataPrevisao());
			boleto.setDataPrevisao(boleto.getDataPrevisao() == null ? calInicial : boleto.getDataPrevisao());
			this.boleto.setDataChegada(this.boleto.getDataChegada() == null ? calInicial : this.boleto.getDataChegada());
			boleto.setDataChegada(boleto.getDataChegada() == null ? calInicial : boleto.getDataChegada());
			this.boleto.setDataVencimento(this.boleto.getDataVencimento() == null ? calInicial : this.boleto.getDataVencimento());
			boleto.setDataVencimento(boleto.getDataVencimento() == null ? calInicial : boleto.getDataVencimento());

			if(!(this.boleto.getDataPrevisao().compareTo(boleto.getDataPrevisao() == null ? calInicial : boleto.getDataPrevisao()) == 0)) {

				if (this.boleto.getDataPrevisao().compareTo(calInicial) == 0){

					log.add("Data de próxima atuação / previsão alterada para : " + dateFormat.format(boleto.getDataPrevisao().getTime()));
					this.boleto.setDataPrevisao(boleto.getDataPrevisao());

				} else if (boleto.getDataPrevisao().compareTo(calInicial) != 0) {

					log.add("Data de próxima atuação / previsão alterada de : " + dateFormat.format(this.boleto.getDataPrevisao().getTime()) + " para : " + dateFormat.format(boleto.getDataPrevisao().getTime()));
					this.boleto.setDataPrevisao(boleto.getDataPrevisao());

				} else if (boleto.getDataPrevisao().compareTo(calInicial) == 0) {
					log.add("Data de próxima atuação / previsão alterada de : " + dateFormat.format(this.boleto.getDataPrevisao().getTime()) + " para : em branco.");
					this.boleto.setDataPrevisao(null);
				}
			}

			if(!(this.boleto.getDataChegada().compareTo(boleto.getDataChegada() == null ? calInicial : boleto.getDataChegada()) == 0)) {

				if (this.boleto.getDataChegada().compareTo(calInicial) == 0){

					log.add("Data de chegada alterada para : " + dateFormat.format(boleto.getDataChegada().getTime()));
					this.boleto.setDataChegada(boleto.getDataChegada());

				} else if (boleto.getDataChegada().compareTo(calInicial) != 0) {

					log.add("Data de chegada alterada de : " + dateFormat.format(this.boleto.getDataChegada().getTime()) + " para : " + dateFormat.format(boleto.getDataChegada().getTime()));
					this.boleto.setDataChegada(boleto.getDataChegada());

				} else if (boleto.getDataChegada().compareTo(calInicial) == 0) {
					log.add("Data de chegada alterada de : " + dateFormat.format(this.boleto.getDataChegada().getTime()) + " para : em branco.");
					this.boleto.setDataChegada(null);
				}
			}

			if(!(this.boleto.getDataVencimento().compareTo(boleto.getDataVencimento() == null ? calInicial : boleto.getDataVencimento()) == 0)) {

				if (this.boleto.getDataVencimento().compareTo(calInicial) == 0){

					log.add("Data de vencimento alterada para : " + dateFormat.format(boleto.getDataVencimento().getTime()));
					this.boleto.setDataVencimento(boleto.getDataVencimento());

				} else if (boleto.getDataVencimento().compareTo(calInicial) != 0) {

					log.add("Data de vencimento alterada de : " + dateFormat.format(this.boleto.getDataVencimento().getTime()) + " para : " + dateFormat.format(boleto.getDataVencimento().getTime()));
					this.boleto.setDataVencimento(boleto.getDataVencimento());

				} else if (boleto.getDataVencimento().compareTo(calInicial) == 0) {
					log.add("Data de vencimento alterada de : " + dateFormat.format(this.boleto.getDataVencimento().getTime()) + " para : em branco.");
					this.boleto.setDataVencimento(null);
				}
			}
			
			if(!(this.boleto.getDataProximaAtuacao().compareTo(boleto.getDataProximaAtuacao() == null ? calInicial : boleto.getDataProximaAtuacao()) == 0)) {

				if (this.boleto.getDataProximaAtuacao().compareTo(calInicial) == 0){

					log.add("Data de próxima atuação alterada para : " + dateFormat.format(boleto.getDataProximaAtuacao().getTime()));
					this.boleto.setDataProximaAtuacao(boleto.getDataProximaAtuacao());

				} else if (boleto.getDataProximaAtuacao().compareTo(calInicial) != 0) {

					log.add("Data de próxima atuação alterada de : " + dateFormat.format(this.boleto.getDataProximaAtuacao().getTime()) + " para : " + dateFormat.format(boleto.getDataProximaAtuacao().getTime()));
					this.boleto.setDataProximaAtuacao(boleto.getDataProximaAtuacao());

				} else if (boleto.getDataProximaAtuacao().compareTo(calInicial) == 0) {
					log.add("Data de próxima atuação alterada de : " + dateFormat.format(this.boleto.getDataProximaAtuacao().getTime()) + " para : em branco.");
					this.boleto.setDataProximaAtuacao(null);
				}
			}

			if(this.boleto.getDataChegada() != null) {
				if(this.boleto.getDataChegada().compareTo(calInicial) == 0)
					this.boleto.setDataChegada(null);
			}

			if(this.boleto.getDataPrevisao() != null) {
				if(this.boleto.getDataPrevisao().compareTo(calInicial) == 0)
					this.boleto.setDataPrevisao(null);
			}

			if(this.boleto.getDataVencimento() != null) {
				if(this.boleto.getDataVencimento().compareTo(calInicial) == 0)
					this.boleto.setDataVencimento(null);
			}

			if(this.boleto.getDataProximaAtuacao() != null) {
				if(this.boleto.getDataProximaAtuacao().compareTo(calInicial) == 0)
					this.boleto.setDataProximaAtuacao(null);
			}

			if(boleto.getEtapa().getEtapa_id() != null){

				Etapa e = this.etapaDao.buscaEtapaById(boleto.getEtapa().getEtapa_id());

				if(this.boleto.getEtapa() == null || (this.boleto.getEtapa().getEtapa_id() != boleto.getEtapa().getEtapa_id())){
					this.boleto.setEtapa(e);
					log.add("Procedimento alterado para : " + e.getNome());
				}
			}
			
			if(boleto.getEtapaProximo().getEtapa_id() != null){
				
				Etapa e = this.etapaDao.buscaEtapaById(boleto.getEtapaProximo().getEtapa_id());
				
				if(this.boleto.getEtapaProximo() == null || (this.boleto.getEtapaProximo().getEtapa_id() != boleto.getEtapaProximo().getEtapa_id())){
					this.boleto.setEtapaProximo(e);
					log.add("Próximo Procedimento alterado para : " + e.getNome());
				}
			
			}

			if(boleto.getProximoAtuante().getUsuario_id() != null){

				Usuario u = this.usuarioDao.buscaUsuarioById(boleto.getProximoAtuante().getUsuario_id());

				if(this.boleto.getProximoAtuante() == null || (this.boleto.getProximoAtuante().getUsuario_id() != boleto.getProximoAtuante().getUsuario_id()) ){
					this.boleto.setProximoAtuante(u);
					log.add("Próximo Atuante alterado para : " + u.getNome());
				}

			}

			this.boleto.setAgente(boleto.getAgente().getAgente_id() == null ? null : boleto.getAgente());

			controleDao.beginTransaction();
			controleDao.atualiza(this.boleto);
			controleDao.commit();
			
			if(!observacao.equals("")){
				log.add(observacao);
			}
			
			for(String l : log){

				HistoricoControle historico = new HistoricoControle();
				historico.setEmpresa(empresa);
				historico.setOrganizacao(organizacao);
				historico.setIsActive(true);
				historico.setCreatedBy(usuario);
				historico.setCreated(GregorianCalendar.getInstance());
				historico.setObservacao(l);
				historico.setControle(this.boleto);
				historico.setContrato(this.boleto.getContrato());

				this.historicoControleDao.beginTransaction();
				this.historicoControleDao.adiciona(historico);
				this.historicoControleDao.commit();

			}

		} else {

			boleto.setDataPrimeiraAtuacao(GregorianCalendar.getInstance());
			boleto.setDataAtuacao(GregorianCalendar.getInstance());
			boleto.setContrato(contratoDao.load(boleto.getContrato().getContrato_id()));
			boleto.setProximoAtuante(usuarioInfo.getUsuario());
			boleto.setEmpresa(empresa);
			boleto.setOrganizacao(organizacao);
			boleto.setCreated(GregorianCalendar.getInstance());
			boleto.setCreatedBy(usuario);
			boleto.setIsActive(true);
			boleto.setTipoControle(tipoControleDao.buscaTipoControleByEmpOrgNome(1l,1l,"Boleto"));
			boleto.setAgente(boleto.getAgente().getAgente_id() == null ? null : boleto.getAgente());
			boleto.setEtapa(boleto.getEtapa().getEtapa_id() == null ? null : boleto.getEtapa());
			boleto.setEtapaProximo(boleto.getEtapaProximo().getEtapa_id() == null ? null : boleto.getEtapaProximo());
			boleto.setProximoAtuante(boleto.getProximoAtuante().getUsuario_id() == null ? null : boleto.getProximoAtuante());
			boleto.setPerfil(usuarioInfo.getPerfil());
			boleto.setWorkflow(this.workflowDao.buscaWorkflowPorNome(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Status Controle Boleto"));

			controleDao.beginTransaction();
			controleDao.adiciona(boleto);
			controleDao.commit();

			if(!observacao.equals("")){
				log.add(observacao);
			}
			
			for(String l : log){

				HistoricoControle historico = new HistoricoControle();
				historico.setEmpresa(empresa);
				historico.setOrganizacao(organizacao);
				historico.setIsActive(true);
				historico.setCreatedBy(usuario);
				historico.setCreated(GregorianCalendar.getInstance());
				historico.setObservacao(l);
				historico.setControle(boleto);
				historico.setContrato(boleto.getContrato());

				this.historicoControleDao.beginTransaction();
				this.historicoControleDao.adiciona(historico);
				this.historicoControleDao.commit();

			}

		}
		
		result.include("msg","ok").redirectTo(this).msg();

	}
	
	@Get
	public void msg(){

	}

}