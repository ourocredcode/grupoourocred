package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.TipoProcedimentoDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.TipoProcedimento;

@Resource
public class TipoprocedimentoController {

	private final Result result;
	private final TipoProcedimentoDao tipoProcedimentoDao;
	private final UsuarioInfo usuarioInfo;
	private Calendar dataAtual = Calendar.getInstance();

	public TipoprocedimentoController(Result result, TipoProcedimentoDao tipoProcedimentoDao, UsuarioInfo usuarioInfo){

		this.result = result;
		this.tipoProcedimentoDao = tipoProcedimentoDao;
		this.usuarioInfo = usuarioInfo;

	}	

	@Get
	@Path("/tipoprocedimento/cadastro")
	public void cadastro(){
		result.include("tipoProcedimentos", this.tipoProcedimentoDao.buscaAllTipoProcedimento(usuarioInfo.getEmpresa().getEmpresa_id(), usuarioInfo.getOrganizacao().getOrganizacao_id()));
	}

	@Post
	@Path("/tipoprocedimento/salva")
	public void salva(TipoProcedimento tipoProcedimento){

		String mensagem = "";

		try {
			
			if (this.tipoProcedimentoDao.buscaTipoProcedimentosByEmOrNome(tipoProcedimento.getEmpresa().getEmpresa_id(), tipoProcedimento.getOrganizacao().getOrganizacao_id(), tipoProcedimento.getNome()) == null) {	

				tipoProcedimento.setCreated(dataAtual);
				tipoProcedimento.setUpdated(dataAtual);

				tipoProcedimento.setCreatedBy(usuarioInfo.getUsuario());
				tipoProcedimento.setUpdatedBy(usuarioInfo.getUsuario());

				tipoProcedimento.setChave(tipoProcedimento.getNome());
				tipoProcedimento.setDescricao(tipoProcedimento.getNome());
				
				tipoProcedimento.setIsActive(tipoProcedimento.getIsActive() == null ? false : true);

				this.tipoProcedimentoDao.beginTransaction();
				this.tipoProcedimentoDao.adiciona(tipoProcedimento);
				this.tipoProcedimentoDao.commit();

				mensagem = "Tipoprocedimento " + tipoProcedimento.getNome() + " adicionado com sucesso";

			} else {

				mensagem = "Erro: Tipoprocedimento " + tipoProcedimento.getNome() + " já cadastrado.";

			} 

		} catch(Exception e) {

			mensagem = "Erro: Falha ao adicionar o Tipoprocedimento.";

		} finally{

			this.tipoProcedimentoDao.clear();
			this.tipoProcedimentoDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Get
	@Path("/tipoprocedimento/busca.json")
	public void tipoProcedimento(String nome){
		result.use(Results.json()).withoutRoot().from(tipoProcedimentoDao.buscaTiposProcedimentosByNome(nome)).serialize();
	}

}