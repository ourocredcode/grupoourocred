package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.GrupoProdutoDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.GrupoProduto;

@Resource
public class GrupoprodutoController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final GrupoProdutoDao grupoProdutoDao;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;

	public GrupoprodutoController(Result result, EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,GrupoProdutoDao grupoProdutoDao, UsuarioInfo usuarioInfo){
		
		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.grupoProdutoDao = grupoProdutoDao;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;		

	}

	@Get
	@Public
	@Path("/grupoproduto/cadastro")
	public void cadastro(){

		result.include("gruposProduto", this.grupoProdutoDao.buscaAllGruposProduto());

	}

	@Post
	@Public
	@Path("/grupoproduto/salva")
	public void salva(GrupoProduto grupoProduto){

		String mensagem = "";

		try {

			grupoProduto.setEmpresa(this.empresaDao.load(grupoProduto.getEmpresa().getEmpresa_id()));		
			grupoProduto.setOrganizacao(this.organizacaoDao.load(grupoProduto.getOrganizacao().getOrganizacao_id()));

			this.grupoProdutoDao.beginTransaction();
			this.grupoProdutoDao.adiciona(grupoProduto);
			this.grupoProdutoDao.commit();

			mensagem = "Grupo de Produto " + grupoProduto.getNome() + " adicionado com sucesso";			

		} catch(Exception e) {

			this.grupoProdutoDao.rollback();

			if (e.getCause().toString().indexOf("IX_GRUPOPRODUTO_EMPORGNOME") != -1){
				mensagem = "Erro: Grupo de Produto " + grupoProduto.getNome() + " já existente.";
			} else {
				mensagem = "Erro ao adicionar Grupo de Produto";
			}

		}

		this.grupoProdutoDao.clear();
		this.grupoProdutoDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

	@Get @Path("/grupoproduto/busca.json")
	@Public
	public void grupoproduto(String nome){
		result.use(Results.json()).withoutRoot().from(grupoProdutoDao.buscaGrupoProdutosByNome(nome)).serialize();
	}

}