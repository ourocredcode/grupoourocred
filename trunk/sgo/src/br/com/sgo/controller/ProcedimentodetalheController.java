package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.AgenteDao;
import br.com.sgo.dao.BancoDao;
import br.com.sgo.dao.ModeloProcedimentoDao;
import br.com.sgo.dao.ProcedimentoConferenciaDao;
import br.com.sgo.dao.ProcedimentoDetalheDao;
import br.com.sgo.dao.TipoProcedimentoDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.ProcedimentoDetalhe;
import br.com.sgo.modelo.Usuario;

@Resource
public class ProcedimentodetalheController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final ProcedimentoDetalheDao procedimentoDetalheDao;
	private final ProcedimentoConferenciaDao procedimentoConferenciaDao;
	private final TipoProcedimentoDao tipoProcedimentoDao;
	private final BancoDao bancoDao;
	private final ModeloProcedimentoDao modeloProcedimentoDao;
	private final AgenteDao agenteDao;
	
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	private Calendar dataAtual = Calendar.getInstance();

	public ProcedimentodetalheController(Result result, ProcedimentoDetalheDao procedimentoDetalheDao, UsuarioInfo usuarioInfo, ProcedimentoConferenciaDao procedimentoConferenciaDao
			,TipoProcedimentoDao tipoProcedimentoDao, BancoDao bancoDao, Empresa empresa, Organizacao organizacao, Usuario usuario, ModeloProcedimentoDao modeloProcedimentoDao, AgenteDao agenteDao){

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.procedimentoConferenciaDao = procedimentoConferenciaDao;
		this.procedimentoDetalheDao = procedimentoDetalheDao;
		this.tipoProcedimentoDao = tipoProcedimentoDao;
		this.modeloProcedimentoDao  =modeloProcedimentoDao;
		this.bancoDao = bancoDao;
		this.agenteDao = agenteDao;
		this.usuario = this.usuarioInfo.getUsuario();
		this.empresa = this.usuarioInfo.getEmpresa();
		this.organizacao = this.usuarioInfo.getOrganizacao();

	}

	@Get
	@Path("/procedimentodetalhe/cadastro")
	public void cadastro(){

		result.include("procedimentosDetalhe", this.procedimentoDetalheDao.buscaAllProcedimentoDetalhe(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
		result.include("procedimentosConferencia", this.procedimentoConferenciaDao.buscaProcedimentoConferenciaToFillCombo(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), this.tipoProcedimentoDao.buscaTipoProcedimentoByNome("Banco").getTipoProcedimento_id()));
		result.include("modelosProcedimento", this.modeloProcedimentoDao.buscaAllModeloProcedimentoByEmpOrg(1L, 1L));
		result.include("agentes", this.agenteDao.buscaAllAgenteByEmpOrg(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));

	}

	@Post
	@Path("/procedimentodetalhe/salva")
	public void salva(ProcedimentoDetalhe procedimentoDetalhe){

		String mensagem = "";

		try {

			if (this.procedimentoDetalheDao.buscaProcedimentoDetalheByEmpresaOrganizacaoProcedimentoAcao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()
					, procedimentoDetalhe.getProcedimento().getProcedimentoConferencia_id(), procedimentoDetalhe.getBanco().getBanco_id(), procedimentoDetalhe.getModeloProcedimento().getModeloProcedimento_id()
					,procedimentoDetalhe.getAgente().getAgente_id(), procedimentoDetalhe.getAcao()) == null) {

				procedimentoDetalhe.setCreated(dataAtual);
				procedimentoDetalhe.setUpdated(dataAtual);
				
				procedimentoDetalhe.setCreatedBy(usuario);
				procedimentoDetalhe.setUpdatedBy(usuario);
				
				procedimentoDetalhe.setIsActive(procedimentoDetalhe.getIsActive() == null ? false : true);

				this.procedimentoDetalheDao.beginTransaction();
				this.procedimentoDetalheDao.adiciona(procedimentoDetalhe);
				this.procedimentoDetalheDao.commit();

				mensagem = "Procedimento Detalhe" + procedimentoDetalhe.getNome() + " adicionado com sucesso";

			} else {

				mensagem = "Erro: Procedimento Detalhe já cadastrado.";

			} 

		} catch(Exception e) {

			mensagem = "Erro: Falha ao adicionar o Procedimento.";

		} finally{

			this.procedimentoDetalheDao.clear();
			this.procedimentoDetalheDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Post
	@Path("/procedimentodetalhe/bancos")
	public void bancos(Long procedimento_id){

		result.include("bancos", this.bancoDao.buscaBancoByProcedimento(procedimento_id));

	}

	@Get
	@Path("/procedimentobancodetalhe/busca.json")
	public void procedimentobancodetalhe(long empresa_id, Long organizacao_id, Long procedimentoBanco_id, Long banco_id, Long modeloProcedimento_id, Integer acao){

		//result.use(Results.json()).withoutRoot().from(procedimentoBancoDetalheDao.buscaProcedimentoBancoDetalheByEmpresaOrganizacaoProcedimentoAcao(empresa_id, organizacao_id, procedimentoBanco_id, banco_id, modeloProcedimento_id, acao)).serialize();

	}

	@Get
	public void msg() {

	}

}