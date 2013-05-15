package br.com.sgo.controller;

import java.util.LinkedHashSet;
import java.util.Set;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.ContratoDao;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.MenuDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.PerfilDao;
import br.com.sgo.dao.UsuarioDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Menu;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Usuario;

@Resource
public class MenuController {

	private final Result result;
	private final Validator validator;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final MenuDao menuDao;
	private final ContratoDao contratoDao;
	private final UsuarioDao usuarioDao;
	private final PerfilDao perfilDao;
	private final UsuarioInfo usuarioInfo;
	private Set<Contrato> contratos = new LinkedHashSet<Contrato>();
	
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	public MenuController(Result result,Validator validator, EmpresaDao empresaDao, OrganizacaoDao organizacaoDao,MenuDao menuDao,UsuarioInfo usuarioInfo,
			UsuarioDao usuarioDao,ContratoDao contratoDao,PerfilDao perfilDao,Empresa empresa,Organizacao organizacao,Usuario usuario){

		this.empresaDao = empresaDao;
		this.usuarioDao = usuarioDao;
		this.perfilDao = perfilDao;
		this.usuarioInfo = usuarioInfo;
		this.organizacaoDao = organizacaoDao;
		this.menuDao = menuDao;
		this.result = result;
		this.validator = validator;
		this.contratoDao = contratoDao;
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();
		this.usuario = usuarioInfo.getUsuario();

	}
	
	@Get
	@Path("/menu/inicio/{tipo}") 
	public void inicio(String tipo) {

		usuarioDao.refresh(usuarioInfo.getUsuario());
		perfilDao.refresh(usuarioInfo.getPerfil());

		if(tipo.equals("Supervisor") || tipo.equals("Consultor"))
			contratos.addAll(this.contratoDao.buscaContratoByUsuario(usuarioInfo.getUsuario().getUsuario_id()));

		if(tipo.equals("Administrativo"))
			contratos.addAll(this.contratoDao.buscaContratoByEmpresaOrganizacao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));

		result.include("contratos", contratos);

	}

	@Get
	@Public
	@Path("/menu/cadastro")
	public void cadastro() {

	}

	@Post
	@Public
	@Path("/menu/salva")
	public void salva(Menu menu){

		validator.validate(menu);
		validator.onErrorUsePageOf(this).cadastro();

		String mensagem = "";

			try {

				menu.setEmpresa(this.empresaDao.load(menu.getEmpresa().getEmpresa_id()));		
				menu.setOrganizacao(this.organizacaoDao.load(menu.getOrganizacao().getOrganizacao_id()));
				menu.setIsActive(menu.getIsActive() == null ? false : true);
				
				this.menuDao.beginTransaction();
				this.menuDao.adiciona(menu);
				this.menuDao.commit();

				mensagem = "Menu " + menu.getNome() + " adicionada com sucesso";			
				
			} catch(Exception e) {
				
				this.menuDao.rollback();
	
				if (e.getCause().toString().indexOf("IX_JANELA_EMP_ORG_NOME") != -1){
					mensagem = "Erro: Menu " + menu.getNome() + " já existente.";
				} else {
					mensagem = "Erro ao adicionar Menu:";
				}
	
			}

		this.menuDao.clear();
		this.menuDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

	@Get @Path("/menu/busca.json")
	@Public
	public void menus(Long empresa_id, Long organizacao_id, String nome){
		result.use(Results.json()).withoutRoot().from(menuDao.buscaMenus(empresa_id, organizacao_id, nome)).serialize();
	}

}
