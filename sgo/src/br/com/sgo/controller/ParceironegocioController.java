package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.DepartamentoDao;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.EstadoCivilDao;
import br.com.sgo.dao.FuncaoDao;
import br.com.sgo.dao.FuncionarioDao;
import br.com.sgo.dao.LocalidadeDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.ParceiroLocalidadeDao;
import br.com.sgo.dao.ParceiroNegocioDao;
import br.com.sgo.dao.SexoDao;
import br.com.sgo.dao.TipoParceiroDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Funcionario;
import br.com.sgo.modelo.Localidade;
import br.com.sgo.modelo.ParceiroLocalidade;
import br.com.sgo.modelo.ParceiroNegocio;

@Resource
public class ParceironegocioController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final ParceiroNegocioDao parceiroNegocioDao;
	private final FuncionarioDao funcionarioDao;
	private final DepartamentoDao departamentoDao;
	private final FuncaoDao funcaoDao;
	private final LocalidadeDao localidadeDao;
	private final ParceiroLocalidadeDao parceiroLocalidadeDao;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final SexoDao sexoDao;
	private final EstadoCivilDao estadoCivilDao;
	private final TipoParceiroDao tipoParceiroDao;

	public ParceironegocioController(Result result, UsuarioInfo usuarioInfo,ParceiroNegocioDao parceiroNegocioDao,
			DepartamentoDao departamentoDao,FuncaoDao funcaoDao,FuncionarioDao funcionarioDao,LocalidadeDao localidadeDao,ParceiroLocalidadeDao parceiroLocalidadeDao,
			EmpresaDao empresaDao, OrganizacaoDao organizacaoDao,SexoDao sexoDao,EstadoCivilDao estadoCivilDao,TipoParceiroDao tipoParceiroDao) {

		this.result = result;
		this.parceiroNegocioDao = parceiroNegocioDao;
		this.departamentoDao = departamentoDao;
		this.funcaoDao = funcaoDao;
		this.usuarioInfo = usuarioInfo;
		this.funcionarioDao = funcionarioDao;
		this.localidadeDao = localidadeDao;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;
		this.sexoDao = sexoDao;
		this.estadoCivilDao = estadoCivilDao;
		this.parceiroLocalidadeDao = parceiroLocalidadeDao;
		this.tipoParceiroDao = tipoParceiroDao;

	}

	@Get
	@Path("/parceironegocio/cadastro")
	public void cadastro(){

		result.include("departamentos", this.departamentoDao.buscaDepartamentos(usuarioInfo.getUsuario().getEmpresa().getEmpresa_id(), 
					usuarioInfo.getUsuario().getOrganizacao().getOrganizacao_id()));
		result.include("funcoes", this.funcaoDao.buscaFuncoes(usuarioInfo.getUsuario().getEmpresa().getEmpresa_id(), 
				usuarioInfo.getUsuario().getOrganizacao().getOrganizacao_id()));

		result.include("sexos", this.sexoDao.buscaSexos());
		result.include("estadosCivis", this.estadoCivilDao.buscaEstadosCivis());
		result.include("tiposParceiro", this.tipoParceiroDao.buscaTiposParceiro());

	}

	@Get
	@Path("/parceironegocio/cadastro/{parceironegocio_id}")
	public void cadastro(Long parceironegocio_id){

		result.include("departamentos", this.departamentoDao.buscaDepartamentos(usuarioInfo.getUsuario().getEmpresa().getEmpresa_id(), 
					usuarioInfo.getUsuario().getOrganizacao().getOrganizacao_id()));
		result.include("funcoes", this.funcaoDao.buscaFuncoes(usuarioInfo.getUsuario().getEmpresa().getEmpresa_id(), 
				usuarioInfo.getUsuario().getOrganizacao().getOrganizacao_id()));

		result.include("parceiroLocalidades",this.parceiroLocalidadeDao.buscaParceiroLocalidades(parceironegocio_id));

		result.include("sexos", this.sexoDao.buscaSexos());
		result.include("estadosCivis", this.estadoCivilDao.buscaEstadosCivis());
		result.include("tiposParceiro", this.tipoParceiroDao.buscaTiposParceiro());
		
		result.include("parceiroNegocio",this.parceiroNegocioDao.load(parceironegocio_id));

	}

	@Post
	@Public
	@Path("/parceironegocio/salva")
	public void salva(ParceiroNegocio parceiroNegocio,Funcionario funcionario, ParceiroLocalidade parceiroLocalidade,Localidade localidade){

		String mensagem = "";

			this.parceiroNegocioDao.beginTransaction();
			this.parceiroNegocioDao.adiciona(parceiroNegocio);
			this.parceiroNegocioDao.commit();

			if(parceiroNegocio.getIsFuncionario()){

				Funcionario f = new Funcionario();

				f.setEmpresa(parceiroNegocio.getEmpresa());
				f.setOrganizacao(parceiroNegocio.getOrganizacao());
				f.setDepartamento(funcionario.getDepartamento());
				f.setParceiroNegocio(parceiroNegocio);
				f.setFuncao(funcionario.getFuncao());
				f.setApelido(funcionario.getApelido());
				f.setIsActive(parceiroNegocio.getIsActive());

				this.funcionarioDao.beginTransaction();
				this.funcionarioDao.adiciona(f);
				this.funcionarioDao.commit();

			}

		localidade.setEmpresa(this.empresaDao.load(1L));
		localidade.setOrganizacao(this.organizacaoDao.load(1L));
		localidade.setIsActive(true);

		this.localidadeDao.beginTransaction();
		this.localidadeDao.adiciona(localidade);
		this.localidadeDao.commit();

		parceiroLocalidade.setEmpresa(usuarioInfo.getEmpresa());
		parceiroLocalidade.setOrganizacao(usuarioInfo.getOrganizacao());
		parceiroLocalidade.setParceiroNegocio(parceiroNegocio);
		parceiroLocalidade.setLocalidade(localidade);
		parceiroLocalidade.setIsActive(true);

		this.parceiroLocalidadeDao.beginTransaction();
		this.parceiroLocalidadeDao.adiciona(parceiroLocalidade);
		this.parceiroLocalidadeDao.commit();

		mensagem = "Parceiro de Negócios " + parceiroNegocio.getNome() + " adicionado com sucesso";			

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

	@Post
	@Path("/parceironegocio/busca.funcionario")
	public void buscaFuncionario(String doc){

		result.redirectTo(this).cadastro(this.parceiroNegocioDao.buscaParceiroNegocioDocumento(
				usuarioInfo.getEmpresa().getEmpresa_id(), usuarioInfo.getOrganizacao().getOrganizacao_id(), doc).getParceiroNegocio_id());

	}
	
	@Post
	@Path("/parceironegocio/salvaLocalidade")
	public void salvaLocalidade(Localidade localidade, ParceiroLocalidade parceiroLocalidade){

		System.out.println(localidade.getCep());
		System.out.println(localidade.getEndereco());
		System.out.println(localidade.getBairro());
		System.out.println(localidade.getCidade().getCidade_id());
		System.out.println(localidade.getRegiao().getRegiao_id());
		System.out.println(parceiroLocalidade.getComplemento());
		System.out.println(parceiroLocalidade.getNumero());

		result.nothing();

	}

}
