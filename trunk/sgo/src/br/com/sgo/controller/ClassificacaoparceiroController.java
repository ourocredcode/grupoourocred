package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.ClassificacaoParceiroDao;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.modelo.ClassificacaoParceiro;

@Resource
public class ClassificacaoparceiroController {

	private final Result result;
	private final Validator validator;
	private final ClassificacaoParceiroDao classificacaoParceiroDao;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	

	public ClassificacaoparceiroController(Result result,Validator validator, EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,ClassificacaoParceiroDao classificacaoParceiroDao){
		this.classificacaoParceiroDao = classificacaoParceiroDao;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;		
		this.result = result;
		this.validator = validator;

	}	

	@Get
	@Path("/classificacaoparceiro/cadastro")
	public void cadastro(){

	}

	@Post
	@Path("/classificacaoparceiro/salva")
	public void salva(ClassificacaoParceiro classificacaoParceiro){
		
		validator.validate(classificacaoParceiro);
		validator.onErrorUsePageOf(this).cadastro();

		String mensagem = "";

		try {

			classificacaoParceiro.setEmpresa(this.empresaDao.load(classificacaoParceiro.getEmpresa().getEmpresa_id()));		
			classificacaoParceiro.setOrganizacao(this.organizacaoDao.load(classificacaoParceiro.getOrganizacao().getOrganizacao_id()));

			this.classificacaoParceiroDao.beginTransaction();
			this.classificacaoParceiroDao.adiciona(classificacaoParceiro);
			this.classificacaoParceiroDao.commit();

			mensagem = "Classificação Parceiro " + classificacaoParceiro.getNome() + " adicionado com sucesso";			
			
		} catch(Exception e) {
			
			this.classificacaoParceiroDao.rollback();

			if (e.getCause().toString().indexOf("IX_CLASSIFICACAOPARCEIRO_EMPORGNOME") != -1){
				mensagem = "Erro: Tipo Parceiro " + classificacaoParceiro.getNome() + " já existente.";
			} else {
				mensagem = "Erro ao adicionar Classificação do Parceiro";
			}

		}
		
		this.classificacaoParceiroDao.clear();
		this.classificacaoParceiroDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

	@Get
	@Path("/classificacaoparceiro/busca.json")
	public void classificacaoParceiro(Long empresa_id, Long organizacao_id, String nome){
		result.use(Results.json()).withoutRoot().from(classificacaoParceiroDao.buscaClassificacaoParceiro(empresa_id, organizacao_id, nome)).serialize();
	}

}