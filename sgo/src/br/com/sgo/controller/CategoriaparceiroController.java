package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.CategoriaParceiroDao;
import br.com.sgo.dao.TipoParceiroDao;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.CategoriaParceiro;
import br.com.sgo.modelo.TipoParceiro;

@Resource
public class CategoriaparceiroController {

	private final Result result;
	private final Validator validator;
	private final CategoriaParceiroDao categoriaParceiroDao;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	

	public CategoriaparceiroController(Result result,Validator validator, EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,CategoriaParceiroDao categoriaParceiroDao){
		this.categoriaParceiroDao = categoriaParceiroDao;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;		
		this.result = result;
		this.validator = validator;

	}	

	@Get
	@Public
	@Path("/categoriaparceiro/cadastro")
	public void cadastro(){
		result.include("categoriaParceiro",this.categoriaParceiroDao.listaTudo("ASC","nome"));
	}

	@Post
	@Public
	@Path("/categoriaparceiro/salva")
	public void salva(CategoriaParceiro categoriaParceiro){
		
		validator.validate(categoriaParceiro);
		validator.onErrorUsePageOf(this).cadastro();

		String mensagem = "";

		try {

			categoriaParceiro.setEmpresa(this.empresaDao.load(categoriaParceiro.getEmpresa().getEmpresa_id()));		
			categoriaParceiro.setOrganizacao(this.organizacaoDao.load(categoriaParceiro.getOrganizacao().getOrganizacao_id()));

			this.categoriaParceiroDao.beginTransaction();
			this.categoriaParceiroDao.adiciona(categoriaParceiro);
			this.categoriaParceiroDao.commit();

			mensagem = "Categoria Parceiro " + categoriaParceiro.getNome() + " adicionado com sucesso";			
			
		} catch(Exception e) {
			
			this.categoriaParceiroDao.rollback();

			if (e.getCause().toString().indexOf("IX_CATEGORIAPARCEIRO_EMP_ORG_NOME") != -1){
				mensagem = "Erro: Categoria de Parceiro " + categoriaParceiro.getNome() + " já cadastrado.";
			} else {
				mensagem = "Erro ao adicionar Categoria de Parceiro.";
			}

		}
		
		this.categoriaParceiroDao.clear();
		this.categoriaParceiroDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

	@Get @Path("/categoriaparceiro/busca.json")
	@Public
	public void categoriaparceiro(Long empresa_id, Long organizacao_id, String nome){
		result.use(Results.json()).withoutRoot().from(categoriaParceiroDao.buscaCategoriaParceiro(empresa_id, organizacao_id, nome)).serialize();
	}

}