package br.com.sgo.controller;

import java.util.Collection;

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
import br.com.sgo.dao.TipoEnderecoDao;
import br.com.sgo.dao.TipoParceiroDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Funcionario;
import br.com.sgo.modelo.Localidade;
import br.com.sgo.modelo.ParceiroLocalidade;
import br.com.sgo.modelo.ParceiroNegocio;
import br.com.sgo.modelo.TipoEndereco;

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
	private final TipoEnderecoDao tipoEnderecoDao;

	public ParceironegocioController(Result result, UsuarioInfo usuarioInfo,ParceiroNegocioDao parceiroNegocioDao,
			DepartamentoDao departamentoDao,FuncaoDao funcaoDao,FuncionarioDao funcionarioDao,LocalidadeDao localidadeDao,ParceiroLocalidadeDao parceiroLocalidadeDao,
			EmpresaDao empresaDao, OrganizacaoDao organizacaoDao,SexoDao sexoDao,EstadoCivilDao estadoCivilDao,TipoParceiroDao tipoParceiroDao,TipoEnderecoDao tipoEnderecoDao) {

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
		this.tipoEnderecoDao = tipoEnderecoDao;

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
		result.include("funcionario",this.funcionarioDao.buscaFuncionarioPorParceiroNegocio(parceironegocio_id));
		result.include("parceiroNegocio",this.parceiroNegocioDao.load(parceironegocio_id));

	}
	
	@Get
	@Path("/parceironegocio/parceiroLocalidades")
	public void parceiroLocalidades(Long parceironegocio_id){
		result.include("parceiroLocalidades",this.parceiroLocalidadeDao.buscaParceiroLocalidades(parceironegocio_id));
	}

	@Post
	@Public
	@Path("/parceironegocio/salva")
	public void salva(ParceiroNegocio parceiroNegocio,Funcionario funcionario, ParceiroLocalidade parceiroLocalidade,Localidade localidade){

		String mensagem = "";
		
			try {


				this.parceiroNegocioDao.beginTransaction();
				this.parceiroNegocioDao.adiciona(parceiroNegocio);
				this.parceiroNegocioDao.commit();
			
			} catch(Exception e) {

				this.funcionarioDao.rollback();

				if (e.getCause().toString().indexOf("PK_PARCEIRONEGOCIO") != -1){
					mensagem = "Erro: Parceiro Negócio " + parceiroNegocio.getNome() + " já existente.";
				} else {
					mensagem = "Erro ao adicionar Parceiro Negócio:";
				}

			}

			if(parceiroNegocio.getIsFuncionario()){

				Funcionario f = new Funcionario();

				f.setEmpresa(parceiroNegocio.getEmpresa());
				f.setOrganizacao(parceiroNegocio.getOrganizacao());
				f.setDepartamento(funcionario.getDepartamento());
				f.setParceiroNegocio(parceiroNegocio);
				f.setFuncao(funcionario.getFuncao());
				f.setApelido(funcionario.getApelido());
				f.setIsActive(parceiroNegocio.getIsActive());
				
				try {

					this.funcionarioDao.beginTransaction();
					this.funcionarioDao.adiciona(f);
					this.funcionarioDao.commit();

				} catch(Exception e) {

					this.funcionarioDao.rollback();

					if (e.getCause().toString().indexOf("PK_FUNCIONARIO") != -1){
						mensagem = "Erro: Funcionário " + funcionario.getApelido() + " já existente.";
					} else {
						mensagem = "Erro ao adicionar Perfil:";
					}

				}

			}

		localidade.setEmpresa(this.empresaDao.load(1L));
		localidade.setOrganizacao(this.organizacaoDao.load(1L));
		localidade.setIsActive(true);
		
		try {

			this.localidadeDao.beginTransaction();
			this.localidadeDao.adiciona(localidade);
			this.localidadeDao.commit();
		
		} catch(Exception e) {

			this.localidadeDao.rollback();

			if (e.getCause().toString().indexOf("PK_LOCALIDADE") != -1){
				mensagem = "Erro: Localidade " + funcionario.getApelido() + " já existente.";
			} else {
				mensagem = "Erro ao adicionar Localidade:";
			}

		}

		Collection<TipoEndereco> tiposEndereco = this.tipoEnderecoDao.buscaTiposEnderecoToLocalidades();

		for(TipoEndereco tipoEndereco : tiposEndereco){

			ParceiroLocalidade pl = new ParceiroLocalidade();

			pl.setEmpresa(usuarioInfo.getEmpresa());
			pl.setOrganizacao(usuarioInfo.getOrganizacao());
			pl.setParceiroNegocio(parceiroNegocio);
			pl.setLocalidade(localidade);
			pl.setTipoEndereco(tipoEndereco);
			pl.setIsActive(true);

			try{
				
				this.parceiroLocalidadeDao.beginTransaction();
				this.parceiroLocalidadeDao.adiciona(pl);
				this.parceiroLocalidadeDao.commit();
				
			} catch(Exception e) {

				this.parceiroLocalidadeDao.rollback();

				if (e.getCause().toString().indexOf("PK__PARCEIROLOCALIDADE") != -1){
					mensagem = "Erro: Parceiro Localidade " + funcionario.getApelido() + " já existente.";
				} else {
					mensagem = "Erro ao adicionar Parceiro Localidade :";
				}

			}

		}

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

		localidade.setIsActive(true);

		this.localidadeDao.beginTransaction();
		this.localidadeDao.adiciona(localidade);
		this.localidadeDao.commit();

		parceiroLocalidade.setEmpresa(usuarioInfo.getEmpresa());
		parceiroLocalidade.setOrganizacao(usuarioInfo.getOrganizacao());
		parceiroLocalidade.setLocalidade(localidade);
		parceiroLocalidade.setIsActive(true);

		this.parceiroLocalidadeDao.beginTransaction();
		this.parceiroLocalidadeDao.adiciona(parceiroLocalidade);
		this.parceiroLocalidadeDao.commit();

		result.redirectTo(this).parceiroLocalidades(parceiroLocalidade.getParceiroNegocio().getParceiroNegocio_id());

	}

}
