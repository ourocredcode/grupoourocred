package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.BancoDao;
import br.com.sgo.dao.CategoriaParceiroDao;
import br.com.sgo.dao.ClassificacaoParceiroDao;
import br.com.sgo.dao.DepartamentoDao;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.FuncaoDao;
import br.com.sgo.dao.GrupoParceiroDao;
import br.com.sgo.dao.IdiomaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.ParceiroNegocioDao;
import br.com.sgo.dao.TipoParceiroDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.ParceiroNegocio;

@Resource
public class ParceironegocioController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final ParceiroNegocioDao parceiroNegocioDao;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final GrupoParceiroDao grupoParceiroDao;	
	private final CategoriaParceiroDao categoriaParceiroDao;	
	private final ClassificacaoParceiroDao classificacaoParceiroDao;		
	private final TipoParceiroDao tipoParceiroDao;
	private final BancoDao bancoDao;
	private final IdiomaDao idiomaDao;
	private final DepartamentoDao departamentoDao;
	private final FuncaoDao funcaoDao;
	

	public ParceironegocioController(Result result, UsuarioInfo usuarioInfo,ParceiroNegocioDao parceiroNegocioDao, EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,GrupoParceiroDao grupoParceiroDao
			,CategoriaParceiroDao categoriaParceiroDao,ClassificacaoParceiroDao classificacaoParceiroDao,TipoParceiroDao tipoParceiroDao,
			BancoDao bancoDao,IdiomaDao idiomaDao,DepartamentoDao departamentoDao,FuncaoDao funcaoDao) {

		this.result = result;
		this.parceiroNegocioDao = parceiroNegocioDao;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.grupoParceiroDao= grupoParceiroDao;	
		this.categoriaParceiroDao= categoriaParceiroDao;	
		this.classificacaoParceiroDao = classificacaoParceiroDao;		
		this.tipoParceiroDao = tipoParceiroDao;
		this.bancoDao = bancoDao;
		this.idiomaDao = idiomaDao;
		this.departamentoDao = departamentoDao;
		this.funcaoDao = funcaoDao;
		this.usuarioInfo = usuarioInfo;

	}

	@Get
	@Path("/parceironegocio/cadastro")
	public void cadastro(){

		result.include("departamentos", this.departamentoDao.buscaDepartamentos(usuarioInfo.getUsuario().getEmpresa().getEmpresa_id(), 
					usuarioInfo.getUsuario().getOrganizacao().getOrganizacao_id()));
		result.include("funcoes", this.funcaoDao.buscaFuncoes(usuarioInfo.getUsuario().getEmpresa().getEmpresa_id(), 
				usuarioInfo.getUsuario().getOrganizacao().getOrganizacao_id()));

	}

	
	@Post
	@Public
	@Path("/parceironegocio/salva")
	public void salva(ParceiroNegocio parceiroNegocio){

		

		String mensagem = "";

		try {

			parceiroNegocio.setEmpresa(this.empresaDao.load(parceiroNegocio.getEmpresa().getEmpresa_id()));		
			parceiroNegocio.setOrganizacao(this.organizacaoDao.load(parceiroNegocio.getOrganizacao().getOrganizacao_id()));			

			this.parceiroNegocioDao.beginTransaction();
			this.parceiroNegocioDao.adiciona(parceiroNegocio);
			this.parceiroNegocioDao.commit();

			mensagem = "Parceiro de Negócios " + parceiroNegocio.getNome() + " adicionado com sucesso";			
			
		} catch(Exception e) {
			
			this.parceiroNegocioDao.rollback();

			if (e.getCause().toString().indexOf("IX_COLUNABD_ELEMENTOBDID") != -1){
				mensagem = "Erro: Parceiro de negócios " + parceiroNegocio.getNome() + " já existente.";
			} else {
				mensagem = "Erro ao adicionar o Parceiro de negócios.";
			}

		}

		this.parceiroNegocioDao.clear();
		this.parceiroNegocioDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}
	
	@Get @Path("/parceironegocio/busca.json")
	@Public
	public void parceironegocio(Long empresa_id, Long organizacao_id, String nome){
		result.use(Results.json()).withoutRoot().from(this.parceiroNegocioDao.buscaParceiroNegocio(empresa_id, organizacao_id, nome)).serialize();
	}
}
