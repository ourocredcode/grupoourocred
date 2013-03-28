package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.ContaBancariaDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.ContaBancaria;

@Resource
public class ContabancariaController {

	private final Result result;
	private final ContaBancariaDao contaBancariaDao;

	public ContabancariaController(Result result, ContaBancariaDao contaBancariaDao){		
		this.result = result;
		this.contaBancariaDao = contaBancariaDao;
	}

	@Get
	@Path("/contabancaria/cadastro")
	public void cadastro(){

	}

	@Post
	@Path("/contabancaria/salva")
	public void salva(ContaBancaria contaBancaria){

		String mensagem = "";
		
		try {
			if(this.contaBancariaDao.buscaContaBancariaByEmpOrgAgeTipNum(contaBancaria.getEmpresa().getEmpresa_id(), contaBancaria.getOrganizacao().getOrganizacao_id(), contaBancaria.getAgencia().getAgencia_id(), contaBancaria.getTipoConta().getTipoConta_id(), contaBancaria.getNumeroconta()) == null) {
				
				this.contaBancariaDao.beginTransaction();
				this.contaBancariaDao.atualiza(contaBancaria);
				this.contaBancariaDao.commit();

				result.include("msg","Conta bancária gravado com sucesso.").redirectTo(this).msg();
			} else {
				result.include("msg","Erro : Conta bancária já existente neste.").redirectTo(this).msg();
			}
		}catch(Exception e) {

			if (e.getCause().toString().indexOf("IX_CONTABANCARIA_EMPORGAGNC") != -1){
				mensagem = "Erro:  Conta bancária já cadastrado.";
			} else {
				mensagem = "Erro ao adicionar Conta bancária";
			}
			result.include("notice", mensagem);
			result.redirectTo(this).cadastro();
		}finally{
			this.contaBancariaDao.clear();
			this.contaBancariaDao.close();			
		}
	}

	@Get
	public void msg(){

	}

	@Get @Path("/contabancaria/busca.json")
	@Public
	public void contabancaria(Long empresa_id, Long organizacao_id, Long banco_id, Long tipoconta_id, String numeroconta){
		result.use(Results.json()).withoutRoot().from(contaBancariaDao.buscaContaBancariaByEmpOrgAgeTipNum(empresa_id, organizacao_id, banco_id, tipoconta_id, numeroconta)).serialize();
	}

}