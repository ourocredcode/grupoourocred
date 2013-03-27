package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.AgenciaDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.Agencia;

@Resource
public class AgenciaController {

	private final Result result;
	private final AgenciaDao agenciaDao;

	public AgenciaController(Result result, AgenciaDao agenciaDao){		
		this.result = result;
		this.agenciaDao = agenciaDao;
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
			if(this.agenciaDao.buscaAgenciaByEmpOrgName(agencia.getEmpresa().getEmpresa_id(), agencia.getOrganizacao().getOrganizacao_id(), agencia.getBanco().getBanco_id(), agencia.getNome()) == null) {
				
				this.agenciaDao.beginTransaction();
				this.agenciaDao.atualiza(agencia);
				this.agenciaDao.commit();

				result.include("msg","Agencia gravado com sucesso.").redirectTo(this).msg();
			} else {
				result.include("msg","Erro : Agencia já existente neste endereço.").redirectTo(this).msg();
			}
		}catch(Exception e) {

			if (e.getCause().toString().indexOf("IX_AGENCIA_EMPORGBANNOM") != -1){
				mensagem = "Erro: Tipo Conta " + agencia.getNome() + " já existente.";
			} else {
				mensagem = "Erro ao adicionar Tipo Conta";
			}
			result.include("notice", mensagem);
			result.redirectTo(this).cadastro();
		}finally{
			this.agenciaDao.clear();
			this.agenciaDao.close();			
		}
	}

	@Get
	public void msg(){

	}

	@Get @Path("/agencia/busca.json")
	@Public
	public void agencia(Long empresa_id, Long organizacao_id, Long banco_id, String nome){
		result.use(Results.json()).withoutRoot().from(agenciaDao.buscaAgenciaByEmpOrgName(empresa_id, organizacao_id, banco_id, nome)).serialize();
	}

}