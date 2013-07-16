package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.OperacaoDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Operacao;
import br.com.sgo.modelo.Usuario;

@Resource
public class OperacaoController {

	private final Result result;
	private final OperacaoDao operacaoDao;

	private UsuarioInfo usuarioInfo;	
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	private Calendar dataAtual = Calendar.getInstance();	
	
	public OperacaoController(Result result, UsuarioInfo usuarioInfo,Empresa empresa, Organizacao organizacao, Usuario usuario, OperacaoDao operacaoDao){

		this.result = result;
		this.operacaoDao = operacaoDao;
		this.usuarioInfo = usuarioInfo;				
		this.empresa = empresa;
		this.organizacao = organizacao;
		this.usuario = this.usuarioInfo.getUsuario();

	}	

	@Get
	@Path("/operacao/cadastro")
	public void cadastro(){

		result.include("operacoes", this.operacaoDao.buscaAllOperacao(1l, 1l));

	}

	@Post
	@Path("/operacao/salva")
	public void salva(Operacao operacao){

		String mensagem = "";

		try {

			if (this.operacaoDao.buscaOperacaoByEmpOrgNome(1l, 1l, operacao.getNome()) == null) {				

				operacao.setCreated(dataAtual);
				operacao.setUpdated(dataAtual);

				operacao.setCreatedBy(usuario);
				operacao.setUpdatedBy(usuario);

				operacao.setChave(operacao.getNome());
				operacao.setDescricao(operacao.getNome());

				empresa.setEmpresa_id(1l);
				organizacao.setOrganizacao_id(1l);

				operacao.setEmpresa(empresa);
				operacao.setOrganizacao(organizacao);

				operacao.setIsActive(operacao.getIsActive() == null ? false : true);

				this.operacaoDao.beginTransaction();
				this.operacaoDao.adiciona(operacao);
				this.operacaoDao.commit();

				mensagem = "Operacao " + operacao.getNome() + " adicionado com sucesso.";


			} else {

				mensagem = "Erro: Operacao já cadastrado.";

			}

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar o Operacao " + operacao.getNome() + ".";

		} finally{

			this.operacaoDao.clear();
			this.operacaoDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Get 
	@Path("/operacao/busca.json")
	public void operacao(Long empresa_id, Long organizacao_id, String nome){

		result.use(Results.json()).withoutRoot().from(operacaoDao.buscaOperacoes(empresa_id, organizacao_id, nome)).serialize();

	}

	@Get
	public void msg() {

	}

}