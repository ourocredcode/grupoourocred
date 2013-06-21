package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.FuncionarioDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.ParceiroNegocioDao;
import br.com.sgo.dao.UsuarioDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Funcionario;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Usuario;

@Resource
public class FuncionarioController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final Validator validator;
	private final UsuarioDao usuarioDao;
	private final FuncionarioDao funcionarioDao;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final ParceiroNegocioDao parceiroNegocioDao;
	private Funcionario funcionario;
	private Usuario usuario;
	private Empresa empresa;
	private Organizacao organizacao;

	public FuncionarioController(Result result,UsuarioInfo usuarioInfo,Validator validator, UsuarioDao usuarioDao,EmpresaDao empresaDao,OrganizacaoDao organizacaoDao, 
			ParceiroNegocioDao parceiroNegocioDao, Usuario usuario,Empresa empresa,Organizacao organizacao,FuncionarioDao funcionarioDao,Funcionario funcionario){

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.validator = validator;
		this.usuarioDao = usuarioDao;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.parceiroNegocioDao = parceiroNegocioDao;
		this.funcionarioDao = funcionarioDao;
		this.funcionario = funcionario;
		this.usuario = usuario;
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();

	}

	@Get
	@Path("/funcionario/equipe/{supervisor_id}")
	public void equipe(Long supervisor_id) {

		if(usuarioInfo.getPerfil().equals("Consultor") || usuarioInfo.getPerfil().equals("Supervisor")) {

			result.include("supervisores", this.funcionarioDao.buscaFuncionariosByPerfil(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Supervisor"));
			result.include("funcionarios", this.funcionarioDao.buscaFuncionariosBySupervisor(empresa.getEmpresa_id(),  organizacao.getOrganizacao_id(), supervisor_id));

		} else {

			result.redirectTo(this).ramais(supervisor_id);

		}

	}
	
	@Get
	@Path("/funcionario/ramais/{supervisor_id}")
	public void ramais(Long supervisor_id) {

		result.include("funcionarios", this.funcionarioDao.buscaFuncionariosByEmpOrg(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));

	}
	
	@Post
	@Path("/funcionario/altera")
	public void altera(Funcionario funcionario){

		this.funcionario = this.funcionarioDao.load(funcionario.getFuncionario_id());
		this.usuario = this.usuarioDao.buscaUsuarioByParceiroNegocio(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), this.funcionario.getParceiroNegocio().getParceiroNegocio_id());

		if(funcionario.getApelido() != null) {

			this.funcionario.setApelido(funcionario.getApelido());
			
			this.funcionarioDao.beginTransaction();
			this.funcionarioDao.atualiza(this.funcionario);
			this.funcionarioDao.commit();

		} else if (funcionario.getSupervisor() != null) {

			this.funcionario.setSupervisor(funcionario.getSupervisor());
			this.usuario.setSupervisorUsuario(this.usuarioDao.buscaUsuarioByParceiroNegocio(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), this.funcionario.getSupervisor().getParceiroNegocio_id()));
			
			this.funcionarioDao.beginTransaction();
			this.funcionarioDao.atualiza(this.funcionario);
			this.funcionarioDao.commit();
			
			this.usuarioDao.beginTransaction();
			this.usuarioDao.atualiza(usuario);
			this.usuarioDao.commit();

		} else if (!funcionario.getIsActive()) {

			this.funcionario.setIsActive(funcionario.getIsActive());
			this.usuario.setIsActive(funcionario.getIsActive());
			
			this.funcionarioDao.beginTransaction();
			this.funcionarioDao.atualiza(this.funcionario);
			this.funcionarioDao.commit();
			
			this.usuarioDao.beginTransaction();
			this.usuarioDao.atualiza(usuario);
			this.usuarioDao.commit();

		}

		result.include("msg","Atualizado com Sucesso.").redirectTo(this).msg();

	}
	
	@Get
	public void msg(){

	}

}