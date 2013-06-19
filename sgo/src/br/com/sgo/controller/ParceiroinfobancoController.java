package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.ParceiroInfoBancoDao;
import br.com.sgo.modelo.ParceiroInfoBanco;

@Resource
public class ParceiroinfobancoController {

	private final Result result;
	private final ParceiroInfoBancoDao parceiroInfoBancoDao;

	public ParceiroinfobancoController(Result result, ParceiroInfoBancoDao parceiroInfoBancoDao){		
		this.result = result;
		this.parceiroInfoBancoDao = parceiroInfoBancoDao;
	}

	@Get
	@Path("/parceiroinfobanco/cadastro")
	public void cadastro(){

	}

	@Get
	public void msg(){

	}

	@Post
	@Path("/parceiroinfobanco/salva")
	public void salva(ParceiroInfoBanco parceiroInfoBanco){

		String mensagem = "";
		
		try {
			if(this.parceiroInfoBancoDao.buscaParceiroInfoBancoByEmOrPaBaAgCb(parceiroInfoBanco.getEmpresa().getEmpresa_id(), parceiroInfoBanco.getOrganizacao().getOrganizacao_id()
					, parceiroInfoBanco.getParceiroNegocio().getParceiroNegocio_id(), parceiroInfoBanco.getBanco().getBanco_id(), parceiroInfoBanco.getAgencia().getAgencia_id()
					, parceiroInfoBanco.getContaBancaria().getContaBancaria_id()) == null) {
				
				this.parceiroInfoBancoDao.beginTransaction();
				this.parceiroInfoBancoDao.atualiza(parceiroInfoBanco);
				this.parceiroInfoBancoDao.commit();

				result.include("msg","ParceiroInfoBanco gravado com sucesso.").redirectTo(this).msg();
			} else {
				result.include("msg","Erro : ParceiroInfoBanco já existente neste endereço.").redirectTo(this).msg();
			}
		}catch(Exception e) {

			if (e.getCause().toString().indexOf("IX_PARCEIROINFOBANCO_ALLID") != -1){
				mensagem = "Erro: Informações bancárias já cadastradas para este parceiro.";
			} else {
				mensagem = "Erro ao adicionar Informações bancárias para este parceiro";
			}
			result.include("notice", mensagem);
			result.redirectTo(this).cadastro();
		}finally{
			this.parceiroInfoBancoDao.clear();
			this.parceiroInfoBancoDao.close();			
		}
	}
	
	@Get
	@Path("/parceiroinfobanco/busca.json")
	public void parceiroinfobanco(Long empresa, Long organizacao, Long parceironegocio, Long banco, Long agencia, Long contabancaria){
		result.use(Results.json()).withoutRoot().from(parceiroInfoBancoDao.buscaParceiroInfoBancoByEmOrPaBaAgCb(empresa, organizacao, parceironegocio, banco, agencia, contabancaria)).serialize();
	}

}