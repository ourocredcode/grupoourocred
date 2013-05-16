package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.ProcedimentoConferenciaDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.ProcedimentoConferencia;

@Resource
public class ProcedimentoconferenciaController {

	private final Result result;
	private final ProcedimentoConferenciaDao procedimentoConferenciaDao;
	private Empresa empresa;
	private Organizacao organizacao; 
	private final UsuarioInfo usuarioInfo;
	private Calendar dataAtual = Calendar.getInstance();

	public ProcedimentoconferenciaController(Result result, ProcedimentoConferenciaDao procedimentoConferenciaDao, UsuarioInfo usuarioInfo
			, Empresa empresa, Organizacao organizacao){		
		this.result = result;
		this.procedimentoConferenciaDao = procedimentoConferenciaDao;
		this.usuarioInfo = usuarioInfo;
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();
	}

	@Get
	@Path("/procedimentoconferencia/cadastro")
	public void cadastro(){
		result.include("procedimentosConferencia", this.procedimentoConferenciaDao.buscaAllProcedimentoConferencia(usuarioInfo.getEmpresa().getEmpresa_id(), usuarioInfo.getOrganizacao().getOrganizacao_id()));
	}

	@Post
	@Path("/procedimentoconferencia/salva")
	public void salva(ProcedimentoConferencia procedimentoConferencia){

		String mensagem = "";

		try {

			if(this.procedimentoConferenciaDao.buscaProcedimentoConferenciaByEmOrTipoProcedimentoContrato(usuarioInfo.getEmpresa().getEmpresa_id()
					, usuarioInfo.getOrganizacao().getOrganizacao_id(), procedimentoConferencia.getTipoProcedimento().getTipoProcedimento_id()
					, procedimentoConferencia.getNome()) == null) {

				procedimentoConferencia.setCreated(dataAtual);
				procedimentoConferencia.setUpdated(dataAtual);
				
				procedimentoConferencia.setEmpresa(empresa);
				procedimentoConferencia.setOrganizacao(organizacao);

				procedimentoConferencia.setCreatedBy(usuarioInfo.getUsuario());
				procedimentoConferencia.setUpdatedBy(usuarioInfo.getUsuario());

				procedimentoConferencia.setChave(procedimentoConferencia.getNome());
				procedimentoConferencia.setDescricao(procedimentoConferencia.getNome());

				procedimentoConferencia.setIsActive(procedimentoConferencia.getIsActive() == null ? false : true);
				
				this.procedimentoConferenciaDao.beginTransaction();
				this.procedimentoConferenciaDao.atualiza(procedimentoConferencia);
				this.procedimentoConferenciaDao.commit();

				mensagem = "Procedimento coferência adicionado com sucesso";

			} else {

				mensagem = "Erro : Procedimento Conferencia já cadastrada.";

			}
		} catch(Exception e) {

			mensagem = "Erro: Falha ao adicionar a Procedimento.";

		} finally{

			this.procedimentoConferenciaDao.clear();
			this.procedimentoConferenciaDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();
	}

	@Get
	public void msg(){

	}

	@Get @Path("/procedimentoconferencia/busca.json")
	@Public
	public void procedimentoconferencia(Long empresa_id, Long organizacao_id, Long tipoProcedimento_id, String nome){
		result.use(Results.json()).withoutRoot().from(procedimentoConferenciaDao.buscaProcedimentoConferenciaTipoProcedimentoNome(empresa_id, organizacao_id, tipoProcedimento_id, nome)).serialize();
	}

}