package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.AgenciaDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Agencia;

@Resource
public class AgenciaController {

	private final Result result;
	private final AgenciaDao agenciaDao;

	private final UsuarioInfo usuarioInfo;
	private Calendar dataAtual = Calendar.getInstance();

	public AgenciaController(Result result, AgenciaDao agenciaDao, UsuarioInfo usuarioInfo){		
		this.result = result;
		this.agenciaDao = agenciaDao;
		this.usuarioInfo = usuarioInfo;
	}

	@Get
	@Path("/agencia/cadastro")
	public void cadastro(){

	}

	@Post
	@Path("/agencia/salva")
	public void salva(Agencia agencia){

		String mensagem = "";
		
		try {
			if(this.agenciaDao.buscaAgenciaByEmOrBaCa(agencia.getEmpresa().getEmpresa_id(), agencia.getOrganizacao().getOrganizacao_id(), agencia.getBanco().getBanco_id(), agencia.getCodigoAgencia()) == null) {
				
				agencia.setCreated(dataAtual);
				agencia.setUpdated(dataAtual);

				agencia.setCreatedBy(usuarioInfo.getUsuario());
				agencia.setUpdatedBy(usuarioInfo.getUsuario());

				agencia.setValue(agencia.getNome());
				agencia.setDescricao(agencia.getNome());

				agencia.setIsActive(agencia.getIsActive() == null ? false : true);
				
				this.agenciaDao.beginTransaction();
				this.agenciaDao.atualiza(agencia);
				this.agenciaDao.commit();

				mensagem = "Agência adicionado com sucesso";

			} else {

				mensagem = "Erro : Agencia já cadastrada.";

			}
		} catch(Exception e) {

			mensagem = "Erro: Falha ao adicionar a Agência.";

		} finally{

			this.agenciaDao.clear();
			this.agenciaDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();
	}

	@Get
	public void msg(){

	}

	@Get @Path("/agencia/busca.json")
	@Public
	public void agencia(Long empresa_id, Long organizacao_id, Long banco_id, String codigoagencia){
		result.use(Results.json()).withoutRoot().from(agenciaDao.buscaAgenciaByEmOrBaCa(empresa_id, organizacao_id, banco_id, codigoagencia)).serialize();
	}

}