package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.BancoDao;
import br.com.sgo.dao.BancoProdutoTabelaDao;
import br.com.sgo.dao.ProdutoDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.BancoProdutoTabela;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Usuario;

@Resource
public class BancoprodutotabelaController {

	private final Result result;
	private final BancoProdutoTabelaDao bancoProdutoTabelaDao;
	private final BancoDao bancoDao;
	private final ProdutoDao produtoDao;

	private final UsuarioInfo usuarioInfo;
	private Empresa empresa;	
	private Organizacao organizacao;
	private Usuario usuario;	
	private Calendar dataAtual = Calendar.getInstance();

	public BancoprodutotabelaController(Result result,UsuarioInfo usuarioInfo, Empresa empresa,Organizacao organizacao, Usuario usuario
			, BancoDao bancoDao, ProdutoDao produtoDao, BancoProdutoTabelaDao bancoProdutoTabelaDao){

		this.result = result;
		this.bancoProdutoTabelaDao = bancoProdutoTabelaDao;
		this.bancoDao = bancoDao;
		this.produtoDao = produtoDao;
		this.usuarioInfo = usuarioInfo;
		this.empresa = this.usuarioInfo.getEmpresa();
		this.organizacao = this.usuarioInfo.getOrganizacao();
		this.usuario = this.usuarioInfo.getUsuario();

	}	

	@Get
	@Path("/bancoprodutotabela/cadastro")
	public void cadastro(){

		result.include("bancos", this.bancoDao.buscaBancoToBancoProdutoTabelaByEmpOrg(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));		

	}

	@Post
	@Path("/bancoprodutotabela/salva")
	public void salva(BancoProdutoTabela bancoProdutoTabela){
		
		String mensagem = "";

		try {

			if (this.bancoProdutoTabelaDao.buscaBancoProdutoTabelaByEmpOrgoBancoProdutoTabela(empresa.getEmpresa_id(),organizacao.getOrganizacao_id(),
					bancoProdutoTabela.getBanco().getBanco_id(), bancoProdutoTabela.getProduto().getProduto_id(), bancoProdutoTabela.getTabela().getTabela_id()) == null) {				
				
				bancoProdutoTabela.setCreated(dataAtual);
				bancoProdutoTabela.setUpdated(dataAtual);

				bancoProdutoTabela.setCreatedBy(usuario);
				bancoProdutoTabela.setUpdatedBy(usuario);
				
				bancoProdutoTabela.setIsActive(bancoProdutoTabela.getIsActive() == null ? false : true);
				
				this.bancoProdutoTabelaDao.beginTransaction();
				this.bancoProdutoTabelaDao.adiciona(bancoProdutoTabela);
				this.bancoProdutoTabelaDao.commit();

				mensagem = "Banco Produto Tabela adicionado com sucesso.";

			} else {

				mensagem = "Erro: Banco Produto Tabela já cadastrado.";

			} 

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar o Banco Produto Tabela.";

		} finally{

			this.bancoProdutoTabelaDao.clear();
			this.bancoProdutoTabelaDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();
	}

	@Post
	@Path("/bancoprodutotabela/produtos")
	public void produtos(Long empresa_id, Long organizacao_id, Long banco_id){

		result.include("produtos",this.produtoDao.buscaProdutosToBancoProdutoTabelaByEmpOrgBanco(empresa_id, organizacao_id, banco_id));

	}
	
	@Get
	public void msg() {

	}

}