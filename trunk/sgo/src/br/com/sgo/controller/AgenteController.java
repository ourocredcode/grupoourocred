package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.AgenteDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Agente;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Usuario;

@Resource
public class AgenteController {

	private final Result result;
	private final AgenteDao agenteDao;

	private Agente agente;
	private UsuarioInfo usuarioInfo;	
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	private Calendar dataAtual = Calendar.getInstance();	
	
	public AgenteController(Result result, UsuarioInfo usuarioInfo, Agente agente, Empresa empresa, Organizacao organizacao, Usuario usuario, AgenteDao agenteDao){

		this.result = result;
		this.agente= agente;
		this.agenteDao = agenteDao;
		this.usuarioInfo = usuarioInfo;				
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();
		this.usuario = this.usuarioInfo.getUsuario();

	}	

	@Get
	@Path("/agente/cadastro")
	public void cadastro(){

		result.include("agentes", this.agenteDao.buscaAllAgenteByEmpOrg(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));

	}

	@Post
	@Path("/agente/salva")
	public void salva(Agente agente){

		String mensagem = "";

		try {

			if (this.agenteDao.buscaAgenteByEmpOrgNome(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), agente.getNome()) == null) {				

				agente.setCreated(dataAtual);
				agente.setUpdated(dataAtual);

				agente.setCreatedBy(usuario);
				agente.setUpdatedBy(usuario);

				agente.setChave(agente.getNome());
				agente.setDescricao(agente.getNome());

				empresa.setEmpresa_id(empresa.getEmpresa_id());
				organizacao.setOrganizacao_id(organizacao.getOrganizacao_id());

				agente.setEmpresa(empresa);
				agente.setOrganizacao(organizacao);

				agente.setIsActive(agente.getIsActive() == null || false ? false : true);
				agente.setIsControle(agente.getIsControle() == null || false ? false : true);

				this.agenteDao.beginTransaction();
				this.agenteDao.adiciona(agente);
				this.agenteDao.commit();

				mensagem = "agente " + agente.getNome() + " adicionado com sucesso.";


			} else {

				mensagem = "Erro: agente já cadastrado.";

			}

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar o agente " + agente.getNome() + ".";

		} finally{

			this.agenteDao.clear();
			this.agenteDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}
	
	@Post
	@Path("/agente/altera")
	public void altera(Agente agente) {

		String mensagem = "";

		this.agente = this.agenteDao.load(agente.getAgente_id());

		this.agente.setUpdated(dataAtual);
		this.agente.setUpdatedBy(usuario);

		if(agente.getIsActive() != null){
			this.agente.setIsActive(agente.getIsActive() == false ? false : true);
		}

		if(agente.getIsControle() != null){
			this.agente.setIsControle(agente.getIsControle() == false ? false : true);
		}

		agenteDao.beginTransaction();		
		agenteDao.atualiza(this.agente);
		agenteDao.commit();

		mensagem = " Agente alterado com sucesso.";

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();
		
	}

	@Get 
	@Path("/agente/busca.json")
	public void agente(Long empresa_id, Long organizacao_id, String nome){

		result.use(Results.json()).withoutRoot().from(agenteDao.buscaAgentesByEmpOrgNome(empresa_id, organizacao_id, nome)).serialize();

	}

	@Get
	public void msg() {

	}

}