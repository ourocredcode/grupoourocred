package br.com.sgo.controller;

import java.util.Collection;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.BancoDao;
import br.com.sgo.dao.ModeloProcedimentoDao;
import br.com.sgo.dao.ProcedimentoBancoDao;
import br.com.sgo.dao.ProcedimentoConferenciaDao;
import br.com.sgo.dao.ProcedimentoDetalheDao;
import br.com.sgo.dao.TipoProcedimentoDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Banco;
import br.com.sgo.modelo.ModeloProcedimento;
import br.com.sgo.modelo.ProcedimentoBanco;
import br.com.sgo.modelo.ProcedimentoDetalhe;

@Resource
public class ProcedimentobancoController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final ProcedimentoBancoDao procedimentoBancoDao;
	private final ProcedimentoConferenciaDao procedimentoConferenciaDao;
	private final TipoProcedimentoDao tipoProcedimentoDao;
	private final BancoDao bancoDao;
	private final ModeloProcedimentoDao modeloProcedimentoDao;
	private final ProcedimentoDetalheDao procedimentoDetalheDao;

	public ProcedimentobancoController(Result result, ProcedimentoBancoDao procedimentoBancoDao,ProcedimentoConferenciaDao procedimentoConferenciaDao, UsuarioInfo usuarioInfo,
			TipoProcedimentoDao tipoProcedimentoDao, BancoDao bancoDao, ModeloProcedimentoDao modeloProcedimentoDao, ProcedimentoDetalheDao procedimentoDetalheDao){

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.procedimentoBancoDao = procedimentoBancoDao;
		this.procedimentoConferenciaDao = procedimentoConferenciaDao;
		this.tipoProcedimentoDao = tipoProcedimentoDao;
		this.bancoDao = bancoDao;
		this.modeloProcedimentoDao = modeloProcedimentoDao;
		this.procedimentoDetalheDao = procedimentoDetalheDao;

	}	

	@Get
	@Public
	@Path("/procedimentobanco/cadastro")
	public void cadastro(){
		result.include("procedimentosConferencia", this.procedimentoConferenciaDao.buscaProcedimentoConferenciaByEmpOrgTipoProcedimento(usuarioInfo.getEmpresa().getEmpresa_id(), 
					usuarioInfo.getOrganizacao().getOrganizacao_id(),this.tipoProcedimentoDao.buscaTipoProcedimentoByNome("Banco").getTipoProcedimento_id()));
	}
	
	@Get
	@Path("/procedimentobanco/detalhebanco/{id}")
	public void detalhebanco(Long id){		
		Collection<Banco> bancos = this.bancoDao.buscaBancoByProcedimento(id);
		result.include("bancos", bancos);		
	}

	@Get
	@Path("/procedimentobanco/detalhemodelo/{id}")
	public void detalhemodelo(Long id){

		Banco banco = this.bancoDao.load(id);

		Collection<ModeloProcedimento> modelosProcedimento = this.modeloProcedimentoDao.buscaModeloProcedimentoByBanco(id);
		result.include("modelosProcedimento", modelosProcedimento);
		result.include("banco",banco);
		
	}
	
	@Get
	@Path("/procedimentobanco/detalheprocedimento/{procedimento_id}/{banco_id}")
	public void detalheprocedimento(Long procedimento_id, Long banco_id){
		Collection<ProcedimentoDetalhe> procedimentosDetalhes = this.procedimentoDetalheDao.buscaProcedimentoDetalheByBancoModeloProcedimento(procedimento_id,banco_id);
		result.include("procedimentosDetalhes", procedimentosDetalhes);
	}

	@Post
	@Public
	@Path("/procedimentobanco/salva")
	public void salva(ProcedimentoBanco procedimentoBanco){

		String mensagem = "";

		try {

			if (this.procedimentoBancoDao.buscaProcedimentoBancoByEmpOrgProcedimentoBanco(usuarioInfo.getEmpresa().getEmpresa_id(), usuarioInfo.getOrganizacao().getOrganizacao_id()
					, procedimentoBanco.getProcedimento().getProcedimentoConferencia_id(), procedimentoBanco.getBanco().getBanco_id()) == null) {	

				procedimentoBanco.setIsActive(procedimentoBanco.getIsActive() == null ? false : true);

				this.procedimentoBancoDao.beginTransaction();
				this.procedimentoBancoDao.adiciona(procedimentoBanco);
				this.procedimentoBancoDao.commit();

				mensagem = "Procedimento Banco " + procedimentoBanco.getNome() + " adicionado com sucesso";
			} else {
				mensagem = "Erro: Procedimento Banco " + procedimentoBanco.getNome() + " já cadastrado.";
			} 

		} catch(Exception e) {

			mensagem = "Erro: Falha ao adicionar o Procedimento Banco.";

		} finally{

			this.procedimentoBancoDao.clear();
			this.procedimentoBancoDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Get @Path("/procedimentobanco/busca.json")
	@Public
	public void procedimentobanco(Long empresa_id, Long organizacao_id){
		result.use(Results.json()).withoutRoot().from(procedimentoBancoDao.buscaAllProcedimentoBanco(empresa_id, organizacao_id)).serialize();
	}

}