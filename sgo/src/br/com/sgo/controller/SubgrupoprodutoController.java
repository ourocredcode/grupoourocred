package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.GrupoProdutoDao;
import br.com.sgo.dao.SubGrupoProdutoDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.SubGrupoProduto;
import br.com.sgo.modelo.Usuario;

@Resource
public class SubgrupoprodutoController {

	private final Result result;
	private final GrupoProdutoDao grupoProdutoDao;
	private final SubGrupoProdutoDao subGrupoProdutoDao;
	
	private final UsuarioInfo usuarioInfo;
	private Empresa empresa;	
	private Organizacao organizacao;
	private Usuario usuario;	
	private Calendar dataAtual = Calendar.getInstance();

	public SubgrupoprodutoController(Result result, UsuarioInfo usuarioInfo, Empresa empresa,Organizacao organizacao, Usuario usuario, GrupoProdutoDao grupoProdutoDao, SubGrupoProdutoDao subGrupoProdutoDao){

		this.result = result;
		this.grupoProdutoDao = grupoProdutoDao;
		this.subGrupoProdutoDao = subGrupoProdutoDao;
		this.usuarioInfo = usuarioInfo;
		this.empresa = this.usuarioInfo.getEmpresa();
		this.organizacao = this.usuarioInfo.getOrganizacao();
		this.usuario = this.usuarioInfo.getUsuario();

	}

	@Get
	@Path("/subgrupoproduto/cadastro")
	public void cadastro(){

		result.include("subGrupoProdutos", this.subGrupoProdutoDao.buscaAllSubGruposProduto());
		result.include("gruposProduto", this.grupoProdutoDao.buscaAllGruposProduto());

	}

	@Post
	@Path("/subgrupoproduto/salva")
	public void salva(SubGrupoProduto subGrupoProduto){

		String mensagem = "";

		try {

			if(empresa.getNome().equals("SYSTEM") && organizacao.getNome().equals("SYSTEM")){
				
				if(this.subGrupoProdutoDao.buscaSubGrupoProdutoByNome(subGrupoProduto.getGrupoProduto().getGrupoProduto_id(), subGrupoProduto.getNome()) == null) {
	
					subGrupoProduto.setCreated(dataAtual);
					subGrupoProduto.setUpdated(dataAtual);
	
					subGrupoProduto.setCreatedBy(usuario);
					subGrupoProduto.setUpdatedBy(usuario);
	
					subGrupoProduto.setIsActive(subGrupoProduto.getIsActive() == null ? false : true);
	
					subGrupoProduto.setChave(subGrupoProduto.getNome());
					subGrupoProduto.setDescricao(subGrupoProduto.getNome());
	
					this.subGrupoProdutoDao.beginTransaction();
					this.subGrupoProdutoDao.adiciona(subGrupoProduto);
					this.subGrupoProdutoDao.commit();
	
					mensagem = "Sub Grupo adicionado com sucesso";
	
				} else {
	
					mensagem = "Erro: Sub Grupo do produto já cadastrado.";
	
				}

			} else {

				mensagem = "Erro: Sub Grupo não pode ser cadastrado nesta empresa.";

			}

		} catch(Exception e) {

			mensagem = "Erro: Falha ao adicionar o Sub Grupo do Produto.";

		} finally {

			this.subGrupoProdutoDao.clear();
			this.subGrupoProdutoDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Get 
	@Path("/subgrupoproduto/busca.json")
	public void subgrupoproduto(Long empresa_id, Long organizacao_id, String nome){

		result.use(Results.json()).withoutRoot().from(subGrupoProdutoDao.buscaSubGrupoProdutos(empresa_id, organizacao_id, nome)).serialize();

	}

}