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
import br.com.sgo.dao.ParceiroNegocioDao;
import br.com.sgo.dao.UsuarioDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Usuario;

@Resource
public class UsuarioController {

	private final Result result;
	private final Validator validator;
	private final UsuarioDao usuarioDao;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final ParceiroNegocioDao parceiroNegocioDao;

	public UsuarioController(Result result,Validator validator, UsuarioDao usuarioDao,EmpresaDao empresaDao,OrganizacaoDao organizacaoDao, ParceiroNegocioDao parceiroNegocioDao){

		this.result = result;
		this.validator = validator;
		this.usuarioDao = usuarioDao;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.parceiroNegocioDao = parceiroNegocioDao;

	}

	@Get
	@Public
	@Path("/usuario/cadastro")
	public void cadastro() {

	}

	@Post
	@Public
	@Path("/usuario/salva")
	public void salva(Usuario usuario){

		validator.validate(usuario);
		validator.onErrorUsePageOf(this).cadastro();

		String mensagem = "";

		try {
			usuario.setEmpresa(this.empresaDao.load(usuario.getEmpresa().getEmpresa_id()));
			usuario.setOrganizacao(this.organizacaoDao.load(usuario.getOrganizacao().getOrganizacao_id()));
			usuario.setParceiroNegocio(this.parceiroNegocioDao.load(usuario.getParceiroNegocio().getParceiroNegocio_id()));
	
			this.usuarioDao.beginTransaction();
			this.usuarioDao.adiciona(usuario);
			this.usuarioDao.commit();
	
			
			mensagem = "Ususário " + usuario.getNome() + " adicionado com sucesso";
	
			} catch(Exception e) {
			
			this.usuarioDao.rollback();

			if (e.getCause().toString().indexOf("IX_USUARIO_EMP_ORG_PARC") != -1){
				mensagem = "Erro: Parceiro de negócios " + usuario.getNome() + " já existente.";
			} else {
				mensagem = "Erro: Parceiro de negócios " + usuario.getParceiroNegocio().getNome() + " já cadastrado para a empresa " +
						"" +  usuario.getEmpresa().getNome() + " e organização " +
						"" + usuario.getOrganizacao().getNome() + ".";				
			}

		}

		this.usuarioDao.clear();
		this.usuarioDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

	@Get @Path("/usuarios/busca.json")
	@Public
	public void usuarios(Long empresa_id, Long organizacao_id, String nome){
		result.use(Results.json()).withoutRoot().from(usuarioDao.buscaUsuarios(empresa_id, organizacao_id, nome)).serialize();
	}	
	
	@Get
	@Public
	public void usuarioPerfil(Long usuarioId){
		result.include("usuario",this.usuarioDao.load(usuarioId));
	}

}