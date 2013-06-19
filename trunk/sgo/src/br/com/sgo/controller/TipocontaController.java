package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.TipoContaDao;
import br.com.sgo.modelo.TipoConta;

@Resource
public class TipocontaController {

	private final Result result;
	private final TipoContaDao tipoContaDao;

	
	public TipocontaController(Result result, TipoContaDao tipoContaDao){		
		this.result = result;
		this.tipoContaDao = tipoContaDao;
	}

	@Get
	@Path("/tipoconta/cadastro")
	public void cadastro(){

	}

	@Post
	@Path("/tipoconta/salva")
	public void salva(TipoConta tipoConta){
		
		String mensagem = "";
		
		try {
			if(this.tipoContaDao.buscaTipoContaByEmpOrgName(tipoConta.getEmpresa().getEmpresa_id(), tipoConta.getOrganizacao().getOrganizacao_id(), tipoConta.getNome()) == null) {
				
				this.tipoContaDao.beginTransaction();
				this.tipoContaDao.atualiza(tipoConta);
				this.tipoContaDao.commit();

				result.include("msg","Tipo Conta gravado com sucesso.").redirectTo(this).msg();
			} else {
				result.include("msg","Erro : Tipo de conta já existente neste endereço.").redirectTo(this).msg();
			}
		}catch(Exception e) {

			if (e.getCause().toString().indexOf("IX_TIPOCONTA_EMPORGNOME") != -1){
				mensagem = "Erro: Tipo Conta " + tipoConta.getNome() + " já existente.";
			} else {
				mensagem = "Erro ao adicionar Tipo Conta";
			}
			result.include("notice", mensagem);
			result.redirectTo(this).cadastro();
		}finally{
			this.tipoContaDao.clear();
			this.tipoContaDao.close();			
		}
	}

	@Get
	public void msg(){

	}

	@Get
	@Path("/tipoconta/busca.json")
	public void tipoconta(Long empresa_id, Long organizacao_id, String nome){
		result.use(Results.json()).withoutRoot().from(tipoContaDao.buscaTipoContaByEmpOrgName(empresa_id, organizacao_id, nome)).serialize();
	}

}