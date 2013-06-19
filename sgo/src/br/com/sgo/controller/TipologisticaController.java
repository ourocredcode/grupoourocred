package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.TipoLogisticaDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.TipoLogistica;

@Resource
public class TipologisticaController {

	private final Result result;
	private final TipoLogisticaDao tipoLogisticaDao;

	private final UsuarioInfo usuarioInfo;
	private Calendar dataAtual = Calendar.getInstance();

	public TipologisticaController(Result result, TipoLogisticaDao tipoLogisticaDao, UsuarioInfo usuarioInfo){		
		this.result = result;
		this.tipoLogisticaDao = tipoLogisticaDao;
		this.usuarioInfo = usuarioInfo;
	}

	@Get
	@Path("/tipologistica/cadastro")
	public void cadastro(){
		result.include("tiposLogistica", this.tipoLogisticaDao.buscaAllTipoLogistica());
	}

	@Post
	@Path("/tipologistica/salva")
	public void salva(TipoLogistica tipoLogistica){

		String mensagem = "";
		
		try {

			if(this.tipoLogisticaDao.buscaTipoLogisticaByNome(usuarioInfo.getEmpresa().getEmpresa_id(), usuarioInfo.getOrganizacao().getOrganizacao_id(), tipoLogistica.getNome()) == null) {

				tipoLogistica.setCreated(dataAtual);
				tipoLogistica.setUpdated(dataAtual);

				tipoLogistica.setCreatedBy(usuarioInfo.getUsuario());
				tipoLogistica.setUpdatedBy(usuarioInfo.getUsuario());

				tipoLogistica.setChave(tipoLogistica.getNome());
				tipoLogistica.setDescricao(tipoLogistica.getNome());

				tipoLogistica.setIsActive(tipoLogistica.getIsActive() == null ? false : true);

				this.tipoLogisticaDao.beginTransaction();
				this.tipoLogisticaDao.atualiza(tipoLogistica);
				this.tipoLogisticaDao.commit();

				mensagem = "Tipo de Logística " + tipoLogistica.getNome() + " adicionado com sucesso";

			} else {

				mensagem = "Erro: Tipo Logística " + tipoLogistica.getNome() + " já cadastrado.";

			} 

		} catch(Exception e) {

			mensagem = "Erro: Falha ao adicionar o Tipo Logística.";

		} finally{

			this.tipoLogisticaDao.clear();
			this.tipoLogisticaDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();
	}

	@Get
	public void msg(){

	}

	@Get
	@Path("/tipologistica/busca.json")
	public void tipoLogistica(Long empresa_id, Long organizacao_id, Long banco_id, String nome){
		result.use(Results.json()).withoutRoot().from(tipoLogisticaDao.buscaTipoLogisticaByEmOrgNome(empresa_id, organizacao_id, nome)).serialize();
	}

}