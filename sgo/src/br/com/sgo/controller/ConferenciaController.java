package br.com.sgo.controller;

import java.util.Collection;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.ConferenciaDao;
import br.com.sgo.dao.ContratoDao;
import br.com.sgo.dao.ProcedimentoConferenciaDao;
import br.com.sgo.dao.TipoProcedimentoDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Conferencia;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.ProcedimentoConferencia;
import br.com.sgo.modelo.TipoProcedimento;
import br.com.sgo.modelo.Usuario;

@Resource
public class ConferenciaController {

	private final Result result;
	private final ContratoDao contratoDao;
	private final ConferenciaDao conferenciaDao;
	private final UsuarioInfo usuarioInfo;
	private final TipoProcedimentoDao tipoProcedimentoDao;
	private final ProcedimentoConferenciaDao procedimentoConferenciaDao;
	private Contrato contrato;
	private Conferencia conferencia;
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;
	private Collection<ProcedimentoConferencia> procedimentos;

	public ConferenciaController(Result result,UsuarioInfo usuarioInfo,Contrato contrato,ContratoDao contratoDao,
			ConferenciaDao conferenciaDao,Conferencia conferencia,Empresa empresa,TipoProcedimentoDao tipoProcedimentoDao,ProcedimentoConferenciaDao procedimentoConferenciaDao,
			Organizacao organizacao,Usuario usuario){		

		this.usuarioInfo = usuarioInfo;
		this.result = result;
		this.contrato = contrato;
		this.contratoDao = contratoDao;
		this.conferenciaDao = conferenciaDao;
		this.tipoProcedimentoDao = tipoProcedimentoDao;
		this.procedimentoConferenciaDao = procedimentoConferenciaDao;
		this.conferencia = conferencia;
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();
		this.usuario = usuarioInfo.getUsuario();

	}
	
	@Post
	@Path("/conferencia/cadastro")
	public void cadastro(Long contrato_id) {

		Contrato contrato = this.contratoDao.load(contrato_id);
		TipoProcedimento tipoProcedimento = this.tipoProcedimentoDao.buscaTipoProcedimentoByNome("Contrato");

		conferencia = this.conferenciaDao.buscaConferenciaByEmOrTipoProcedimentoContrato(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), 
																						  tipoProcedimento.getTipoProcedimento_id(), contrato_id);
		procedimentos = this.procedimentoConferenciaDao.buscaAllProcedimentoConferencia(empresa.getEmpresa_id(), organizacao.getOrganizacao_id());

		result.include("contrato",contrato);
		result.include("conferencia",conferencia);
		result.include("procedimentos", procedimentos);

	}

}