package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.PerfilDao;
import br.com.sgo.dao.UsuarioDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Perfil;

@Resource
public class PerfilController {

	private final Result result;	
	private final PerfilDao perfilDao;
	private final UsuarioInfo usuarioInfo;
	
	public PerfilController(Result result, PerfilDao perfilDao, UsuarioInfo usuarioInfo, UsuarioDao usuarioDao){

		this.result = result;
		this.perfilDao = perfilDao;
		this.usuarioInfo = usuarioInfo;

	}
	
	@Get
	@Path("/perfil/cadastro")
	public void cadastro(){
		
		result.include("perfis", this.perfilDao.buscaPerfisByEmpOrg(usuarioInfo.getEmpresa().getEmpresa_id(), usuarioInfo.getOrganizacao().getOrganizacao_id()));
		
	}

	@Post
	@Path("/perfil/salva")
	public void salva(Perfil perfil){

		Calendar dataAtual = Calendar.getInstance();

		String mensagem = "";

		try {

			if (this.perfilDao.buscaPerfilByOrgEmpNome(usuarioInfo.getEmpresa().getEmpresa_id(), usuarioInfo.getOrganizacao().getOrganizacao_id(), perfil.getNome()) == null) {				
				
				perfil.setCreated(dataAtual);
				perfil.setUpdated(dataAtual);

				perfil.setCreatedBy(usuarioInfo.getUsuario());
				perfil.setUpdatedBy(usuarioInfo.getUsuario());

				perfil.setSupervisorUsuario(perfil.getSupervisorUsuario().getUsuario_id() == null ? null : perfil.getSupervisorUsuario());

				perfil.setIsActive(perfil.getIsActive() == null ? false : true);

				this.perfilDao.beginTransaction();
				this.perfilDao.adiciona(perfil);
				this.perfilDao.commit();		

				mensagem = "Perfil adicionado com sucesso.";
				
			} else {

				mensagem = "Erro: Perfil já cadastrado.";

			} 

		} catch (Exception e) {
		
			mensagem = "Erro: Falha ao adicionar o Perfil.";

		} finally{

			this.perfilDao.clear();
			this.perfilDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();
	}

	@Get 
	@Path("/perfil/busca.json")
	public void perfil(String nome){

		result.use(Results.json()).withoutRoot().from(perfilDao.buscaAllPerfisByNome(nome)).serialize();

	}

}
