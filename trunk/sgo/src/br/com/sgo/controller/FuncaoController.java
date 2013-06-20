package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.FuncaoDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Funcao;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Usuario;

@Resource
public class FuncaoController {

	private final Result result;
	private final FuncaoDao funcaoDao;
	
	private Funcao funcao;
	
	private UsuarioInfo usuarioInfo;	
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	private Calendar dataAtual = Calendar.getInstance();

	public FuncaoController(Result result, UsuarioInfo usuarioInfo,Empresa empresa, Organizacao organizacao, Usuario usuario, Funcao funcao, FuncaoDao funcaoDao){

		this.result = result;
		this.funcao = funcao;
		this.funcaoDao = funcaoDao;
		this.usuarioInfo = usuarioInfo;				
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();
		this.usuario = this.usuarioInfo.getUsuario();		

	}	

	@Get
	@Path("/funcao/cadastro")
	public void cadastro(){

		result.include("funcoes", this.funcaoDao.buscaAllFuncao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));

	}

	@Post
	@Path("/funcao/salva")
	public void salva(Funcao funcao){
		
		String mensagem = "";

		try {

			if (this.funcaoDao.buscaFuncaoByEmpOrgNome(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), funcao.getNome()) == null) {				

				funcao.setCreated(dataAtual);
				funcao.setUpdated(dataAtual);

				funcao.setCreatedBy(usuario);
				funcao.setUpdatedBy(usuario);

				funcao.setChave(funcao.getNome());
				funcao.setDescricao(funcao.getNome());

				funcao.setEmpresa(empresa);
				funcao.setOrganizacao(organizacao);

				funcao.setIsActive(funcao.getIsActive() == null ? false : true);

				this.funcaoDao.beginTransaction();
				this.funcaoDao.adiciona(funcao);
				this.funcaoDao.commit();

				mensagem = "Função " + funcao.getNome() + " adicionado com sucesso.";


			} else {

				mensagem = "Erro: Função já cadastrado.";

			}

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar a Função " + funcao.getNome() + ".";

		} finally{

			this.funcaoDao.clear();
			this.funcaoDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Post
	@Path("/funcao/altera")
	public void altera(Funcao funcao) {

		String mensagem = "";

		this.funcao = this.funcaoDao.load(funcao.getFuncao_id());

		this.funcao.setUpdated(dataAtual);
		this.funcao.setUpdatedBy(usuario);

		this.funcao.setIsActive(funcao.getIsActive() == null || funcao.getIsActive() == false ? false : true);

		funcaoDao.beginTransaction();		
		funcaoDao.atualiza(this.funcao);
		funcaoDao.commit();

		mensagem = " Função alteterada com sucesso.";

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();
		
	}
	
	@Get
	@Path("/funcao/busca.json")
	public void funcao(Long empresa_id, Long organizacao_id, String nome){

		result.use(Results.json()).withoutRoot().from(funcaoDao.buscaFuncoes(empresa_id, organizacao_id, nome)).serialize();

	}

}