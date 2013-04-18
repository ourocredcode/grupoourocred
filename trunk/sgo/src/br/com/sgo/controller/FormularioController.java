package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.ParceiroNegocioDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Formulario;
import br.com.sgo.modelo.ParceiroBeneficio;

@Resource
public class FormularioController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final ParceiroNegocioDao parceiroNegocioDao;
	private Formulario formulario;
	private ParceiroBeneficio parceiroBeneficio;
	

	public FormularioController(Result result, UsuarioInfo usuarioInfo,Formulario formulario,ParceiroNegocioDao parceiroNegocioDao,ParceiroBeneficio parceiroBeneficio){		

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.parceiroNegocioDao = parceiroNegocioDao;
		this.parceiroBeneficio = parceiroBeneficio;
		this.formulario = formulario;

	}

	@Get
	@Path("/formulario/cadastro")
	public void cadastro(){

		result.include("formulario",formulario);

	}
	
	@Post
	@Path("/formulario/cliente")
	public void cliente(Formulario formulario){

		parceiroNegocioDao.buscaParceiroNegocioDocumento(formulario.getEmpresa().getEmpresa_id(), 
				formulario.getOrganizacao().getOrganizacao_id(), formulario.getParceiroNegocio().getCpf());

		result.include("formulario",formulario);

		result.redirectTo(this).cadastro();

	}

}