package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.ElementoBdDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.ElementoBd;

@Resource
public class ElementobdController {

	private final Result result;
	private final ElementoBdDao elementoBdDao;


	private final UsuarioInfo usuarioInfo;
	private Calendar dataAtual = Calendar.getInstance();

	public ElementobdController(Result result, ElementoBdDao elementoBdDao, UsuarioInfo usuarioInfo){

		this.elementoBdDao = elementoBdDao;
		this.result = result;
		this.usuarioInfo = usuarioInfo;

	}	

	@Get
	@Public
	@Path("/elementobd/cadastro")
	public void cadastro(){
		result.include("elementosBd",this.elementoBdDao.buscaAllElementos());
	}

	@Post
	@Public
	@Path("/elementobd/salva")
	public void salva(ElementoBd elementoBd){

		String mensagem = "";

		try {
			if(this.elementoBdDao.buscaValidaElementosByNomeColuna(usuarioInfo.getEmpresa().getEmpresa_id(), usuarioInfo.getOrganizacao().getOrganizacao_id(), elementoBd.getNomeColunaBd()) == null) {
			
				elementoBd.setCreated(dataAtual);
				elementoBd.setUpdated(dataAtual);
	
				elementoBd.setCreatedBy(usuarioInfo.getUsuario());
				elementoBd.setUpdatedBy(usuarioInfo.getUsuario());
	
				elementoBd.setChave(elementoBd.getNome());
				elementoBd.setDescricao(elementoBd.getNome());
				elementoBd.setNomeColunaBd(elementoBd.getNome());
	
				elementoBd.setIsActive(elementoBd.getIsActive() == null ? false : true);
	
				this.elementoBdDao.beginTransaction();
				this.elementoBdDao.adiciona(elementoBd);
				this.elementoBdDao.commit();
	
				mensagem = "elemento " + elementoBd.getNome() + " adicionado com sucesso";

			} else {

				mensagem = "elemento " + elementoBd.getNome() + " já cadastrado.";

			} 

		} catch(Exception e) {

			mensagem = "Erro: Falha ao adicionar o elemento.";

		} finally{

			this.elementoBdDao.clear();
			this.elementoBdDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Get @Path("/elementobd/busca.json")
	@Public
	public void busca(Long empresa_id, Long organizacao_id, String nomeColunaBd){
		result.use(Results.json()).withoutRoot().from(elementoBdDao.buscaElementosLista(empresa_id, organizacao_id, nomeColunaBd)).serialize();
	}

	@Post @Path("/elementobd/lista")
	@Public
	public void lista(Long empresa_id, Long organizacao_id, String nomeColunaBd){
		result.include("elementosBd",this.elementoBdDao.buscaElementosLista(empresa_id, organizacao_id, nomeColunaBd));
	}

}