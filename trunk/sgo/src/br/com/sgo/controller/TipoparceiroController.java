package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.TipoParceiroDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.TipoParceiro;

@Resource
public class TipoparceiroController {

	private final Result result;
	private final Validator validator;
	private final TipoParceiroDao tipoParceiroDao;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	

	public TipoparceiroController(Result result,Validator validator, EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,TipoParceiroDao tipoParceiroDao){
		this.tipoParceiroDao = tipoParceiroDao;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;		
		this.result = result;
		this.validator = validator;

	}	

	@Get
	@Public
	@Path("/tipoparceiro/cadastro")
	public void cadastro(){
		result.include("tipoParceiro",this.tipoParceiroDao.listaTudo("ASC","nome"));
	}

	@Post
	@Public
	@Path("/tipoparceiro/salva")
	public void salva(TipoParceiro tipoParceiro){
		
		validator.validate(tipoParceiro);
		validator.onErrorUsePageOf(this).cadastro();

		String mensagem = "";

		try {

			tipoParceiro.setEmpresa(this.empresaDao.load(tipoParceiro.getEmpresa().getEmpresa_id()));		
			tipoParceiro.setOrganizacao(this.organizacaoDao.load(tipoParceiro.getOrganizacao().getOrganizacao_id()));
			tipoParceiro.setIsActive(tipoParceiro.getIsActive() == null ? false : true);
			
			this.tipoParceiroDao.beginTransaction();
			this.tipoParceiroDao.adiciona(tipoParceiro);
			this.tipoParceiroDao.commit();

			mensagem = "Tipo Dado BD " + tipoParceiro.getNome() + " adicionado com sucesso";			
			
		} catch(Exception e) {
			
			this.tipoParceiroDao.rollback();

			if (e.getCause().toString().indexOf("IX_TIPOPARCEIRO_EMP_ORG_NOME") != -1){
				mensagem = "Erro: Tipo Parceiro " + tipoParceiro.getNome() + " já existente.";
			} else {
				mensagem = "Erro ao adicionar Tipo Parceiro";
			}

		}
		
		this.tipoParceiroDao.clear();
		this.tipoParceiroDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

	@Get @Path("/tipoparceiro/busca.json")
	@Public
	public void tipoparceiro(Long empresa_id, Long organizacao_id, String nome){
		result.use(Results.json()).withoutRoot().from(tipoParceiroDao.buscaTipoParceiro(empresa_id, organizacao_id, nome)).serialize();
	}

}