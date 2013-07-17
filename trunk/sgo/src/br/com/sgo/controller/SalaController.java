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

	private Sala sala;
	private UsuarioInfo usuarioInfo;	
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	private Calendar dataAtual = Calendar.getInstance();	
	
	public SalaController(Result result, UsuarioInfo usuarioInfo,Empresa empresa, Organizacao organizacao, Usuario usuario, SalaDao salaDao, Sala sala){

		this.result = result;
		this.sala = sala;
		this.salaDao = salaDao;
		this.usuarioInfo = usuarioInfo;				
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();
		this.usuario = this.usuarioInfo.getUsuario();

	}	

	@Get
	@Path("/sala/cadastro")
	public void cadastro(){

		result.include("salas", this.salaDao.buscaAllSala(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));

	}

	@Post
	@Path("/sala/salva")
	public void salva(Sala sala){

		String mensagem = "";

		try {

			if (this.salaDao.buscaSalaByEmpOrgNome(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), sala.getNome()) == null) {				

				sala.setCreated(dataAtual);
				sala.setUpdated(dataAtual);

				sala.setCreatedBy(usuario);
				sala.setUpdatedBy(usuario);

				sala.setChave(sala.getNome());
				sala.setDescricao(sala.getNome());

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

	@Post
	@Path("/sala/altera")
	public void altera(Sala sala) {

		String mensagem = "";

		this.sala = this.salaDao.load(sala.getSala_id());

		this.sala.setUpdated(dataAtual);
		this.sala.setUpdatedBy(usuario);

		if(sala.getIsActive() != null){
			this.sala.setIsActive(sala.getIsActive() == false ? false : true);
		}

		salaDao.beginTransaction();		
		salaDao.atualiza(this.sala);
		salaDao.commit();

		mensagem = " Sala alterado com sucesso.";

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();
		
	}

	@Get 
	@Path("/sala/busca.json")
	public void sala(Long empresa_id, Long organizacao_id, String nome){

		result.use(Results.json()).withoutRoot().from(salaDao.buscaSalas(empresa_id, organizacao_id, nome)).serialize();

	}

	@Get
	public void msg() {

	}

}