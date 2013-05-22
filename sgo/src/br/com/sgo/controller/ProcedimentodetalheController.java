package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.ProcedimentoDetalheDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.ProcedimentoDetalhe;

@Resource
public class ProcedimentodetalheController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final ProcedimentoDetalheDao procedimentoDetalheDao;

	public ProcedimentodetalheController(Result result, ProcedimentoDetalheDao procedimentoDetalheDao, UsuarioInfo usuarioInfo){

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.procedimentoDetalheDao = procedimentoDetalheDao;

	}	

	@Get
	@Public
	@Path("/procedimentodetalhe/cadastro")
	public void cadastro(){		
		result.include("procedimentosoDetalhe", this.procedimentoDetalheDao.buscaAllProcedimentoDetalhe(usuarioInfo.getEmpresa().getEmpresa_id(), usuarioInfo.getOrganizacao().getOrganizacao_id()));
	}

	@Post
	@Public
	@Path("/procedimentobancodetalhe/salva")
	public void salva(ProcedimentoDetalhe procedimentoDetalhe){

		String mensagem = "";

		try {
			if (this.procedimentoDetalheDao.buscaProcedimentoDetalheByEmpresaOrganizacaoProcedimentoAcao(usuarioInfo.getEmpresa().getEmpresa_id()
					, usuarioInfo.getOrganizacao().getOrganizacao_id(), procedimentoDetalhe.getProcedimento().getProcedimentoConferencia_id(), 
					procedimentoDetalhe.getBanco().getBanco_id(), procedimentoDetalhe.getModeloProcedimento().getModeloProcedimento_id()
					,procedimentoDetalhe.getAgente().getAgente_id(), procedimentoDetalhe.getAcao()) == null) {

				procedimentoDetalhe.setIsActive(procedimentoDetalhe.getIsActive() == null ? false : true);

				this.procedimentoDetalheDao.beginTransaction();
				this.procedimentoDetalheDao.adiciona(procedimentoDetalhe);
				this.procedimentoDetalheDao.commit();

				mensagem = "Procedimento Detalhe" + procedimentoDetalhe.getNome() + " adicionado com sucesso";
			} else {
				mensagem = "Erro: Procedimento Detalhe " + procedimentoDetalhe.getNome() + " já cadastrado.";
			} 

		} catch(Exception e) {

			mensagem = "Erro: Falha ao adicionar o Procedimento Banco.";

		} finally{

			this.procedimentoDetalheDao.clear();
			this.procedimentoDetalheDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Get @Path("/procedimentobancodetalhe/busca.json")
	@Public
	public void procedimentobancodetalhe(long empresa_id, Long organizacao_id, Long procedimentoBanco_id, Long banco_id, Long modeloProcedimento_id, Integer acao){
		//result.use(Results.json()).withoutRoot().from(procedimentoBancoDetalheDao.buscaProcedimentoBancoDetalheByEmpresaOrganizacaoProcedimentoAcao(empresa_id, organizacao_id, procedimentoBanco_id, banco_id, modeloProcedimento_id, acao)).serialize();
	}

}