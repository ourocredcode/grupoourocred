package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.TipoSaqueDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.TipoSaque;

@Resource
public class TiposaqueController {

	private final Result result;	
	private final TipoSaqueDao tipoSaqueDao;
	
	private final UsuarioInfo usuarioInfo;
	private Calendar dataAtual = Calendar.getInstance();

	public TiposaqueController(Result result, TipoSaqueDao tipoSaqueDao, UsuarioInfo usuarioInfo){

		this.result = result;
		this.tipoSaqueDao = tipoSaqueDao;		
		this.usuarioInfo = usuarioInfo;

	}	

	@Post
	@Public
	@Path("/tiposaque/salva")
	public void salva(TipoSaque tipoSaque){

		String mensagem = "";

		try {

			if (this.tipoSaqueDao.buscaTipoSaqueByEmOrgNome(tipoSaque.getEmpresa().getEmpresa_id(), tipoSaque.getOrganizacao().getOrganizacao_id(), tipoSaque.getNome()) == null) {

				tipoSaque.setCreated(dataAtual);
				tipoSaque.setUpdated(dataAtual);

				tipoSaque.setCreatedBy(usuarioInfo.getUsuario());
				tipoSaque.setUpdatedBy(usuarioInfo.getUsuario());

				tipoSaque.setChave(tipoSaque.getNome());
				tipoSaque.setDescricao(tipoSaque.getNome());

				tipoSaque.setIsActive(tipoSaque.getIsActive() == null ? false : true);

				this.tipoSaqueDao.beginTransaction();
				this.tipoSaqueDao.adiciona(tipoSaque);
				this.tipoSaqueDao.commit();

				mensagem = "Tipo Saque " + tipoSaque.getNome() + " adicionado com sucesso";

			} else {

				mensagem = "Erro: Tipo Saque " + tipoSaque.getNome() + " já cadastrado.";

			} 

		} catch(Exception e) {

			mensagem = "Erro: Falha ao adicionar o Tipo Saque.";

		} finally{

			this.tipoSaqueDao.clear();
			this.tipoSaqueDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Get
	@Public
	@Path("/tiposaque/cadastro")
	public void cadastro(){
		result.include("tiposSaque",this.tipoSaqueDao.buscaAllTipoSaque());
	}

	@Get @Path("/tiposaque/busca.json")
	@Public
	public void tiposdadobd(String nome){
		result.use(Results.json()).withoutRoot().from(tipoSaqueDao.buscaTiposSaqueByNome(nome)).serialize();
	}
	
	@Post @Path("/tiposaque/lista")
	@Public
	public void lista(){
		result.include("tiposDadosBd",this.tipoSaqueDao.buscaAllTipoSaque());
	}

}