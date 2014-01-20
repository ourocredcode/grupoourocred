package br.com.sgo.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.ConferenciaDao;
import br.com.sgo.dao.ContratoDao;
import br.com.sgo.dao.EtapaDao;
import br.com.sgo.dao.HistoricoContratoDao;
import br.com.sgo.dao.ProcedimentoConferenciaDao;
import br.com.sgo.dao.TipoProcedimentoDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Conferencia;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Etapa;
import br.com.sgo.modelo.HistoricoContrato;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.ProcedimentoConferencia;
import br.com.sgo.modelo.TipoProcedimento;
import br.com.sgo.modelo.Usuario;

@Resource
public class ConferenciaController {

	private final Result result;
	private final ContratoDao contratoDao;
	private final EtapaDao etapaDao;
	private final ConferenciaDao conferenciaDao;
	private final UsuarioInfo usuarioInfo;
	private final TipoProcedimentoDao tipoProcedimentoDao;
	private final ProcedimentoConferenciaDao procedimentoConferenciaDao;
	private final HistoricoContratoDao historicoContratoDao;
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;
	private Collection<Conferencia> conferencias = new ArrayList<Conferencia>();

	public ConferenciaController(Result result,UsuarioInfo usuarioInfo, ContratoDao contratoDao,EtapaDao etapaDao,HistoricoContratoDao historicoContratoDao,
			ConferenciaDao conferenciaDao, Empresa empresa,TipoProcedimentoDao tipoProcedimentoDao,ProcedimentoConferenciaDao procedimentoConferenciaDao,
			Organizacao organizacao,Usuario usuario){		

		this.usuarioInfo = usuarioInfo;
		this.result = result;
		this.contratoDao = contratoDao;
		this.etapaDao = etapaDao;
		this.historicoContratoDao = historicoContratoDao;
		this.conferenciaDao = conferenciaDao;
		this.tipoProcedimentoDao = tipoProcedimentoDao;
		this.procedimentoConferenciaDao = procedimentoConferenciaDao;
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();
		this.usuario = usuarioInfo.getUsuario();

	}
	
	@Post
	@Path("/conferencia/cadastro")
	public void cadastro(Long contrato_id) {

		Contrato contrato = this.contratoDao.load(contrato_id);
		TipoProcedimento tipoProcedimento = this.tipoProcedimentoDao.buscaTipoProcedimentoByNome("Contrato");

		conferencias = this.conferenciaDao.buscaConferenciaByEmOrTipoProcedimentoContrato(
				empresa.getEmpresa_id(), 
				organizacao.getOrganizacao_id(), 
				tipoProcedimento.getTipoProcedimento_id(), 
				contrato_id);

		if(conferencias.isEmpty()) {

			conferencias = new ArrayList<Conferencia>();
			
			Collection<ProcedimentoConferencia >procedimentos = this.procedimentoConferenciaDao.buscaProcedimentoConferenciaByEmpOrgTipoProcedimento(
					empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), tipoProcedimento.getTipoProcedimento_id());

			for(ProcedimentoConferencia procedimento : procedimentos){

				Conferencia c = new Conferencia();
				c.setProcedimentoConferencia(procedimento);
				c.setContrato(contrato);
				c.setTipoProcedimento(tipoProcedimento);
				c.setEmpresa(empresa);
				c.setOrganizacao(organizacao);
				c.setCreatedBy(usuario);
				c.setCreated(GregorianCalendar.getInstance());
				c.setIsValido(true);
				c.setIsActive(true);

				conferencias.add(c);

			}
			
		}

		result.include("contrato",contrato);
		result.include("conferencias",conferencias);

	}

	@Post
	@Path("/conferencia/salva")
	public void salva(List<Conferencia> conferencias) {

		List<String> log = new ArrayList<String>();
		
		this.conferenciaDao.beginTransaction();
		this.conferenciaDao.atualiza(conferencias);
		this.conferenciaDao.commit();

		boolean valid = true;
		Long contrato_id = null;

		for(Conferencia c : conferencias){

			if(c.getIsValido() != true)
				valid = c.getIsValido();

			contrato_id = c.getContrato().getContrato_id();

		}

		Contrato c = this.contratoDao.load(contrato_id);

		if(!valid){

			Etapa e = this.etapaDao.buscaEtapaByEmpresaOrganizacaoNomeExato(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(),"Pendente Conferência");

			log.add("Status alterado de : " +c.getEtapa().getNome() + " para : " + e.getNome());
			
			c.setEtapa(e);

			contratoDao.beginTransaction();
			contratoDao.atualiza(c);
			contratoDao.commit();

			for(String lo : log){

				HistoricoContrato historico = new HistoricoContrato();
				historico.setEmpresa(empresa);
				historico.setOrganizacao(organizacao);
				historico.setIsActive(true);
				historico.setCreatedBy(usuario);
				historico.setCreated(GregorianCalendar.getInstance());
				historico.setObservacao(lo);
				historico.setContrato(c);

				this.historicoContratoDao.beginTransaction();
				this.historicoContratoDao.adiciona(historico);
				this.historicoContratoDao.commit();

			}

		}

		result.include("msg","Conferência preenchido com sucesso.").redirectTo(this).msg();

	}
	
	@Get
	public void msg(){

	}
}