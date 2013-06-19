package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.CategoriaParceiroDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.CategoriaParceiro;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Usuario;

@Resource
public class CategoriaparceiroController {

	private final Result result;
	private final CategoriaParceiroDao categoriaParceiroDao;

	private UsuarioInfo usuarioInfo;	
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	private Calendar dataAtual = Calendar.getInstance();

	public CategoriaparceiroController(Result result, UsuarioInfo usuarioInfo,Empresa empresa, Organizacao organizacao, Usuario usuario, CategoriaParceiroDao categoriaParceiroDao){

		this.result = result;
		this.categoriaParceiroDao = categoriaParceiroDao;	
		this.usuarioInfo = usuarioInfo;				
		this.empresa = empresa;
		this.organizacao = organizacao;
		this.usuario = this.usuarioInfo.getUsuario();

	}	

	@Get
	@Path("/categoriaparceiro/cadastro")
	public void cadastro(){

		result.include("categoriasParceiro", this.categoriaParceiroDao.buscaAllCategoriaParceiroByEmpOrg(1l, 1l));

	}

	@Post
	@Path("/categoriaparceiro/salva")
	public void salva(CategoriaParceiro categoriaParceiro){
		
		String mensagem = "";

		try {

			if (this.categoriaParceiroDao.buscaCategoriaParceiroByEmpOrgNome(1l, 1l, categoriaParceiro.getNome()) == null) {				

				categoriaParceiro.setCreated(dataAtual);
				categoriaParceiro.setUpdated(dataAtual);

				categoriaParceiro.setCreatedBy(usuario);
				categoriaParceiro.setUpdatedBy(usuario);

				categoriaParceiro.setChave(categoriaParceiro.getNome());
				categoriaParceiro.setDescricao(categoriaParceiro.getNome());

				empresa.setEmpresa_id(1l);
				organizacao.setOrganizacao_id(1l);

				categoriaParceiro.setEmpresa(empresa);
				categoriaParceiro.setOrganizacao(organizacao);

				categoriaParceiro.setIsActive(categoriaParceiro.getIsActive() == null ? false : true);

				this.categoriaParceiroDao.beginTransaction();
				this.categoriaParceiroDao.adiciona(categoriaParceiro);
				this.categoriaParceiroDao.commit();

				mensagem = "Meio pagamento " + categoriaParceiro.getNome() + " adicionado com sucesso.";


			} else {

				mensagem = "Erro: Categoria Parceiro já cadastrado.";

			}

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar o Categoria Parceiro " + categoriaParceiro.getNome() + ".";

		} finally{

			this.categoriaParceiroDao.clear();
			this.categoriaParceiroDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Get
	@Path("/categoriaparceiro/busca.json")
	public void categoriaparceiro(Long empresa_id, Long organizacao_id, String nome){

		result.use(Results.json()).withoutRoot().from(categoriaParceiroDao.buscaCategoriaParceiroByEmpOrgNome(1l, 1l, nome)).serialize();

	}

}