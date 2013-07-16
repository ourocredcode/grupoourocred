package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.SalaDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Sala;
import br.com.sgo.modelo.Usuario;

@Resource
public class SalaController {

	private final Result result;
	private final SalaDao salaDao;

	private UsuarioInfo usuarioInfo;	
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	private Calendar dataAtual = Calendar.getInstance();	
	
	public SalaController(Result result, UsuarioInfo usuarioInfo,Empresa empresa, Organizacao organizacao, Usuario usuario, SalaDao salaDao){

		this.result = result;
		this.salaDao = salaDao;
		this.usuarioInfo = usuarioInfo;				
		this.empresa = empresa;
		this.organizacao = organizacao;
		this.usuario = this.usuarioInfo.getUsuario();

	}	

	@Get
	@Path("/sala/cadastro")
	public void cadastro(){

		result.include("operacoes", this.salaDao.buscaAllSala(1l, 1l));

	}

	@Post
	@Path("/sala/salva")
	public void salva(Sala sala){

		String mensagem = "";

		try {

			if (this.salaDao.buscaSalaByEmpOrgNome(1l, 1l, sala.getNome()) == null) {				

				sala.setCreated(dataAtual);
				sala.setUpdated(dataAtual);

				sala.setCreatedBy(usuario);
				sala.setUpdatedBy(usuario);

				sala.setChave(sala.getNome());
				sala.setDescricao(sala.getNome());

				empresa.setEmpresa_id(1l);
				organizacao.setOrganizacao_id(1l);

				sala.setEmpresa(empresa);
				sala.setOrganizacao(organizacao);

				sala.setIsActive(sala.getIsActive() == null ? false : true);

				this.salaDao.beginTransaction();
				this.salaDao.adiciona(sala);
				this.salaDao.commit();

				mensagem = "Sala " + sala.getNome() + " adicionado com sucesso.";


			} else {

				mensagem = "Erro: Sala já cadastrado.";

			}

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar o Sala " + sala.getNome() + ".";

		} finally{

			this.salaDao.clear();
			this.salaDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Get 
	@Path("/sala/busca.json")
	public void sala(Long empresa_id, Long organizacao_id, String nome){

		result.use(Results.json()).withoutRoot().from(salaDao.buscasalas(empresa_id, organizacao_id, nome)).serialize();

	}

	@Get
	public void msg() {

	}

}