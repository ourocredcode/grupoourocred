package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.GrupoProdutoDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.GrupoProduto;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Usuario;

@Resource
public class GrupoprodutoController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final GrupoProdutoDao grupoProdutoDao;
	
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;
	
	private Calendar dataAtual = Calendar.getInstance();

	public GrupoprodutoController(Result result, Empresa empresa,Organizacao organizacao, Usuario usuario, GrupoProdutoDao grupoProdutoDao, UsuarioInfo usuarioInfo){
		
		this.result = result;		
		this.usuarioInfo = usuarioInfo;
		this.empresa = this.usuarioInfo.getEmpresa();
		this.organizacao = this.usuarioInfo.getOrganizacao();
		this.usuario = this.usuarioInfo.getUsuario();
		this.grupoProdutoDao = grupoProdutoDao;

	}

	@Get
	@Path("/grupoproduto/cadastro")
	public void cadastro(){

		result.include("gruposProduto", this.grupoProdutoDao.buscaAllGruposProduto());

	}

	@Post
	@Path("/grupoproduto/salva")
	public void salva(GrupoProduto grupoProduto){

		String mensagem = "";

		try {

			if(empresa.getNome().equals("SYSTEM") && organizacao.getNome().equals("SYSTEM")){
				
				if(this.grupoProdutoDao.buscaGrupoProdutoByNome(grupoProduto.getNome()) == null) {
	
					grupoProduto.setCreated(dataAtual);
					grupoProduto.setUpdated(dataAtual);
	
					grupoProduto.setCreatedBy(usuario);
					grupoProduto.setUpdatedBy(usuario);
	
					grupoProduto.setIsActive(grupoProduto.getIsActive() == null ? false : true);
	
					grupoProduto.setChave(grupoProduto.getNome());
					grupoProduto.setDescricao(grupoProduto.getNome());
	
					this.grupoProdutoDao.beginTransaction();
					this.grupoProdutoDao.adiciona(grupoProduto);
					this.grupoProdutoDao.commit();
	
					mensagem = "Grupo adicionado com sucesso";
	
				} else {
	
					mensagem = "Erro: Grupo do produto já cadastrado.";
	
				}

			} else {

				mensagem = "Erro: Grupo não pode ser cadastrado nesta empresa.";

			}

		} catch(Exception e) {

			mensagem = "Erro: Falha ao adicionar o Grupo do Produto.";

		} finally {

			this.grupoProdutoDao.clear();
			this.grupoProdutoDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Get @Path("/grupoproduto/busca.json")
	public void grupoproduto(String nome){
		result.use(Results.json()).withoutRoot().from(grupoProdutoDao.buscaGrupoProdutosByNome(nome)).serialize();
	}
	
	@Get
	public void msg() {

	}

}