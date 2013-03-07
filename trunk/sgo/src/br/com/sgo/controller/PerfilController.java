package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.PerfilDao;
import br.com.sgo.dao.TabelaBdDao;
import br.com.sgo.dao.UsuarioDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Perfil;
import br.com.sgo.modelo.TabelaBd;

@Resource
public class PerfilController {

	private final Result result;
	private final Validator validator;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final PerfilDao perfilDao;
	private final UsuarioDao usuarioDao;

	public PerfilController(Result result,Validator validator, PerfilDao perfilDao, EmpresaDao empresaDao, OrganizacaoDao organizacaoDao, UsuarioDao usuarioDao){

		this.result = result;
		this.validator = validator;
		this.perfilDao = perfilDao;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;		
		this.usuarioDao = usuarioDao;

	}
	
	@Get
	@Public
	@Path("/perfil/cadastro")
	public void cadastro(){
		
	}

	@Post
	@Public
	@Path("/perfil/salva")
	public void salva(Perfil perfil){

		validator.validate(perfil);
		validator.onErrorUsePageOf(this).cadastro();

		String mensagem = "";

		try {

			perfil.setEmpresa(this.empresaDao.load(perfil.getEmpresa().getEmpresa_id()));		
			perfil.setOrganizacao(this.organizacaoDao.load(perfil.getOrganizacao().getOrganizacao_id()));
			perfil.setUsuario(this.usuarioDao.load(perfil.getUsuario().getUsuario_id()));
			perfil.setIsActive(perfil.getIsActive() == null ? false : true);

			this.perfilDao.beginTransaction();
			this.perfilDao.adiciona(perfil);
			this.perfilDao.commit();

			mensagem = "Perfil " + perfil.getNome() + " adicionado com sucesso";			
			
		} catch(Exception e) {

			this.perfilDao.rollback();

			if (e.getCause().toString().indexOf("IX_PERFIL_PERFILNOME") != -1){
				mensagem = "Erro: Perfil " + perfil.getNome() + " já existente.";
			} else {
				mensagem = "Erro ao adicionar Perfil:";
			}

		}

		this.perfilDao.clear();
		this.perfilDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}
	
	@Get @Path("/perfil/busca.json")
	@Public
	public void perfis(Long empresa_id, Long organizacao_id, String nome){
		result.use(Results.json()).withoutRoot().from(perfilDao.buscaPerfis(empresa_id, organizacao_id, nome)).serialize();
	}

}
