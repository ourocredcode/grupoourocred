package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.DepartamentoDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Departamento;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Funcao;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Usuario;

@Resource
public class DepartamentoController {

	private final Result result;	
	private final DepartamentoDao departamentoDao;	

	private Departamento departamento;

	private UsuarioInfo usuarioInfo;	
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	private Calendar dataAtual = Calendar.getInstance();

	public DepartamentoController(Result result, UsuarioInfo usuarioInfo,Empresa empresa, Organizacao organizacao, Usuario usuario
			,Departamento departamento, DepartamentoDao departamentoDao){

		this.result = result;
		this.departamento = departamento;
		this.departamentoDao = departamentoDao;
		this.usuarioInfo = usuarioInfo;				
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();
		this.usuario = this.usuarioInfo.getUsuario();

	}	

	@Get
	@Path("/departamento/cadastro")
	public void cadastro(){

		result.include("departamentos", this.departamentoDao.buscaAllDepartamento(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));

	}

	@Post
	@Path("/departamento/salva")
	public void salva(Departamento departamento){
		

		String mensagem = "";

		try {

			if (this.departamentoDao.buscaDepartamentoByEmpOrgNome(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), departamento.getNome()) == null) {				

				departamento.setCreated(dataAtual);
				departamento.setUpdated(dataAtual);

				departamento.setCreatedBy(usuario);
				departamento.setUpdatedBy(usuario);

				departamento.setChave(departamento.getNome());
				departamento.setDescricao(departamento.getNome());

				departamento.setEmpresa(empresa);
				departamento.setOrganizacao(organizacao);

				departamento.setIsActive(departamento.getIsActive() == null ? false : true);

				this.departamentoDao.beginTransaction();
				this.departamentoDao.adiciona(departamento);
				this.departamentoDao.commit();

				mensagem = "Departamento " + departamento.getNome() + " adicionado com sucesso.";


			} else {

				mensagem = "Erro: Departamento já cadastrado.";

			}

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar a Departamento " + departamento.getNome() + ".";

		} finally{

			this.departamentoDao.clear();
			this.departamentoDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}
	
	@Post
	@Path("/departamento/altera")
	public void altera(Funcao departamento) {

		String mensagem = "";

		this.departamento = this.departamentoDao.load(departamento.getFuncao_id());

		this.departamento.setUpdated(dataAtual);
		this.departamento.setUpdatedBy(usuario);

		this.departamento.setIsActive(departamento.getIsActive() == null || departamento.getIsActive() == false ? false : true);

		departamentoDao.beginTransaction();		
		departamentoDao.atualiza(this.departamento);
		departamentoDao.commit();

		mensagem = " Departamento alteterada com sucesso.";

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Get
	@Path("/departamento/busca.json")
	public void departamento(Long empresa_id, Long organizacao_id, String nome){

		result.use(Results.json()).withoutRoot().from(departamentoDao.buscaDepartamentos(empresa_id, organizacao_id, nome)).serialize();

	}

}