package br.com.sgo.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import br.com.caelum.restfulie.RestClient;
import br.com.caelum.restfulie.Restfulie;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.BancoDao;
import br.com.sgo.dao.CategoriaParceiroDao;
import br.com.sgo.dao.CidadeDao;
import br.com.sgo.dao.ClassificacaoParceiroDao;
import br.com.sgo.dao.ConvenioDao;
import br.com.sgo.dao.DepartamentoDao;
import br.com.sgo.dao.EstadoCivilDao;
import br.com.sgo.dao.FuncaoDao;
import br.com.sgo.dao.FuncionarioDao;
import br.com.sgo.dao.GrupoParceiroDao;
import br.com.sgo.dao.LocalidadeDao;
import br.com.sgo.dao.MeioPagamentoDao;
import br.com.sgo.dao.OperacaoDao;
import br.com.sgo.dao.PaisDao;
import br.com.sgo.dao.ParceiroBeneficioDao;
import br.com.sgo.dao.ParceiroContatoDao;
import br.com.sgo.dao.ParceiroInfoBancoDao;
import br.com.sgo.dao.ParceiroLocalidadeDao;
import br.com.sgo.dao.ParceiroNegocioDao;
import br.com.sgo.dao.PnDao;
import br.com.sgo.dao.RegiaoDao;
import br.com.sgo.dao.SexoDao;
import br.com.sgo.dao.TipoContatoDao;
import br.com.sgo.dao.TipoEnderecoDao;
import br.com.sgo.dao.TipoLocalidadeDao;
import br.com.sgo.dao.TipoParceiroDao;
import br.com.sgo.dao.UsuarioDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Banco;
import br.com.sgo.modelo.Convenio;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Funcionario;
import br.com.sgo.modelo.Localidade;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.ParceiroBeneficio;
import br.com.sgo.modelo.ParceiroContato;
import br.com.sgo.modelo.ParceiroInfoBanco;
import br.com.sgo.modelo.ParceiroLocalidade;
import br.com.sgo.modelo.ParceiroNegocio;
import br.com.sgo.modelo.TipoEndereco;
import br.com.sgo.modelo.TipoParceiro;
import br.com.sgo.modelo.Usuario;
import br.com.sgo.modelo.cep.BrazilianAddressFinder;

@Resource
public class ParceironegocioController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final ParceiroNegocioDao parceiroNegocioDao;
	private final PnDao pnDao;
	private final FuncionarioDao funcionarioDao;
	private final DepartamentoDao departamentoDao;
	private final FuncaoDao funcaoDao;
	private final LocalidadeDao localidadeDao;
	private final ParceiroBeneficioDao parceiroBeneficioDao;
	private final ParceiroLocalidadeDao parceiroLocalidadeDao;
	private final ParceiroContatoDao parceiroContatoDao;
	private final ParceiroInfoBancoDao parceiroInfoBancoDao;
	private final BancoDao bancoDao;
	private final ConvenioDao convenioDao;
	private final SexoDao sexoDao;
	private final EstadoCivilDao estadoCivilDao;
	private final TipoParceiroDao tipoParceiroDao;
	private final TipoEnderecoDao tipoEnderecoDao;
	private final TipoContatoDao tipoContatoDao;
	private final MeioPagamentoDao meioPagamentoDao;
	private final OperacaoDao operacaoDao;
	private final PaisDao paisDao;
	private final RegiaoDao regiaoDao;
	private final CidadeDao cidadeDao;
	private final TipoLocalidadeDao tipoLocalidadeDao;
	private final UsuarioDao usuarioDao;
	private final CategoriaParceiroDao categoriaParceiroDao;
	private final ClassificacaoParceiroDao classificacaoParceiroDao;
	private final GrupoParceiroDao grupoParceiroDao;
	private BrazilianAddressFinder addressFinder;
	private RestClient restfulie;

	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;
	private ParceiroNegocio parceiroNegocio;
	private Funcionario funcionario;

	private Calendar dataAtual = Calendar.getInstance();
	
	
	public ParceironegocioController(Result result, UsuarioInfo usuarioInfo,ParceiroNegocioDao parceiroNegocioDao,PnDao pnDao,PaisDao paisDao,RegiaoDao regiaoDao, CidadeDao cidadeDao,
			DepartamentoDao departamentoDao,FuncaoDao funcaoDao,FuncionarioDao funcionarioDao,LocalidadeDao localidadeDao,ParceiroLocalidadeDao parceiroLocalidadeDao,ParceiroContatoDao parceiroContatoDao,
			SexoDao sexoDao,EstadoCivilDao estadoCivilDao,TipoParceiroDao tipoParceiroDao,TipoEnderecoDao tipoEnderecoDao,Usuario usuario,BancoDao bancoDao,ConvenioDao convenioDao,
			TipoLocalidadeDao tipoLocalidadeDao,TipoContatoDao tipoContatoDao,ParceiroBeneficioDao parceiroBeneficioDao,UsuarioDao usuarioDao,MeioPagamentoDao meioPagamentoDao,OperacaoDao operacaoDao,
			ParceiroInfoBancoDao parceiroInfoBancoDao,Empresa empresa,Organizacao organizacao, CategoriaParceiroDao categoriaParceiroDao, ClassificacaoParceiroDao classificacaoParceiroDao, GrupoParceiroDao grupoParceiroDao,
			ParceiroNegocio parceiroNegocio,Funcionario funcionario) {

		this.result = result;
		this.parceiroNegocioDao = parceiroNegocioDao;
		this.pnDao = pnDao;
		this.departamentoDao = departamentoDao;
		this.funcaoDao = funcaoDao;
		this.usuarioInfo = usuarioInfo;
		this.funcionarioDao = funcionarioDao;
		this.localidadeDao = localidadeDao;
		this.sexoDao = sexoDao;
		this.bancoDao = bancoDao;
		this.estadoCivilDao = estadoCivilDao;
		this.parceiroLocalidadeDao = parceiroLocalidadeDao;
		this.parceiroContatoDao = parceiroContatoDao;
		this.parceiroBeneficioDao = parceiroBeneficioDao;
		this.parceiroInfoBancoDao = parceiroInfoBancoDao;
		this.tipoParceiroDao = tipoParceiroDao;
		this.tipoEnderecoDao = tipoEnderecoDao;
		this.tipoContatoDao = tipoContatoDao;
		this.meioPagamentoDao = meioPagamentoDao;
		this.operacaoDao = operacaoDao;
		this.paisDao = paisDao;
		this.regiaoDao = regiaoDao;
		this.cidadeDao = cidadeDao;
		this.usuarioDao = usuarioDao;
		this.convenioDao = convenioDao;
		this.tipoLocalidadeDao = tipoLocalidadeDao;
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();
		this.categoriaParceiroDao = categoriaParceiroDao;
		this.classificacaoParceiroDao = classificacaoParceiroDao;
		this.grupoParceiroDao = grupoParceiroDao;
		this.empresa = this.usuarioInfo.getEmpresa();
		this.organizacao = this.usuarioInfo.getOrganizacao();
		this.usuario = this.usuarioInfo.getUsuario();
		this.parceiroNegocio = parceiroNegocio;
		this.funcionario = funcionario;

	}

	@Get
	@Path("/parceironegocio/cadastro")
	public void cadastro(){

		if(this.parceiroNegocio.getParceiroNegocio_id() != null) {
			
			parceiroNegocio = this.parceiroNegocioDao.load(parceiroNegocio.getParceiroNegocio_id());

			result.include("parceiroLocalidades",this.parceiroLocalidadeDao.buscaParceiroLocalidades(parceiroNegocio.getParceiroNegocio_id()));
			result.include("parceiroInfoBanco",this.parceiroInfoBancoDao.buscaParceiroInfoBancoByParceiro(parceiroNegocio.getParceiroNegocio_id()));
			result.include("parceiroContatos",this.parceiroContatoDao.buscaParceiroContatos(parceiroNegocio.getParceiroNegocio_id()));
			result.include("parceiroBeneficios",this.parceiroBeneficioDao.buscaParceiroBeneficioByParceiroNegocio(parceiroNegocio.getParceiroNegocio_id()));
			result.include("funcionario",this.funcionarioDao.buscaFuncionarioPorParceiroNegocio(parceiroNegocio.getParceiroNegocio_id()));
			result.include("parceiroNegocio",parceiroNegocio);
			result.include("departamentos", this.departamentoDao.buscaAllDepartamento(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
			result.include("funcoes", this.funcaoDao.buscaAllFuncao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
			result.include("supervisores", this.funcionarioDao.buscaSupervisorFuncionarioByPerfil(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Supervisor"));
			result.include("tiposEndereco",this.tipoEnderecoDao.buscaTiposEnderecoToLocalidades());
			result.include("tiposContato",this.tipoContatoDao.buscaTiposContatos());
			result.include("tiposParceiro", this.tipoParceiroDao.buscaTiposParceiro());
			result.include("sexos", this.sexoDao.buscaSexos());
			result.include("estadosCivis", this.estadoCivilDao.buscaEstadosCivis());
			result.include("bancos",this.bancoDao.buscaAllBancos());
			result.include("convenios",this.convenioDao.buscaConvenioToFillComboByEmpOrg(1l, 1l));
			result.include("meiosPagamento",this.meioPagamentoDao.buscaAllMeioPagamento(1l, 1l));
			result.include("operacoes",this.operacaoDao.buscaOperacoes(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
			result.include("categoriasParceiro",this.categoriaParceiroDao.buscaAllCategoriaParceiroByEmpOrg(1l, 1l));
			result.include("classificacoesParceiro",this.classificacaoParceiroDao.buscaAllClassificacaoParceiroByEmpOrg(1l, 1l));
			result.include("gruposParceiro",this.grupoParceiroDao.buscaAllGrupoParceiroByEmpOrg(1l, 1l));
			
		}

		result.include("departamentos", this.departamentoDao.buscaAllDepartamento(empresa.getEmpresa_id(),organizacao.getOrganizacao_id()));
		result.include("funcoes", this.funcaoDao.buscaAllFuncao(empresa.getEmpresa_id(),organizacao.getOrganizacao_id()));

		Collection<Funcionario> supervisores = new ArrayList<Funcionario>();

		supervisores.addAll(this.funcionarioDao.buscaSupervisorFuncionarioByPerfil(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Supervisor"));
		supervisores.addAll(this.funcionarioDao.buscaSupervisorFuncionarioByPerfil(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Gestor"));

		result.include("supervisores",supervisores);

		result.include("sexos", this.sexoDao.buscaSexos());
		result.include("estadosCivis", this.estadoCivilDao.buscaEstadosCivis());
		result.include("tiposParceiro", this.tipoParceiroDao.buscaTiposParceiro());
		result.include("tiposContato",this.tipoContatoDao.buscaTiposContatos());
		result.include("categoriasParceiro",this.categoriaParceiroDao.buscaAllCategoriaParceiroByEmpOrg(1l, 1l));
		result.include("classificacoesParceiro",this.classificacaoParceiroDao.buscaAllClassificacaoParceiroByEmpOrg(1l, 1l));
		result.include("gruposParceiro",this.grupoParceiroDao.buscaAllGrupoParceiroByEmpOrg(1l, 1l));
		result.include("operacoes",this.operacaoDao.buscaOperacoes(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));

	}

	@Post
	@Path("/parceironegocio/cadastro")
	public void cadastro(String doc){

		ParceiroNegocio parceiroNegocio = null;
		ParceiroBeneficio parceiroBeneficio = null;

		if(!doc.equals(""))
			parceiroBeneficio = this.parceiroBeneficioDao.buscaParceiroBeneficioByNumeroBeneficio(doc);

		if( parceiroBeneficio == null ){

			if(!doc.equals(""))
				parceiroNegocio = this.parceiroNegocioDao.buscaParceiroNegocioByDocumento(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), doc);

		} else {

			parceiroNegocio = parceiroBeneficio.getParceiroNegocio();

		}

		if( parceiroNegocio == null ) {

			if(!doc.equals(""))
				parceiroNegocio = this.pnDao.buscaParceiroNegocio(doc);

			if(parceiroNegocio != null){

				ParceiroLocalidade parceiroLocalidade = this.pnDao.buscaParceiroLocalidade(parceiroNegocio);
				ParceiroInfoBanco infoBanco = this.pnDao.buscaParceiroInfoBanco(parceiroNegocio);

				infoBanco.setMeioPagamento(this.meioPagamentoDao.buscaMeioPagamentoByNome(1l, 1l, infoBanco.getMeioPagamento().getNome()));

				Banco b = this.bancoDao.buscaBancoByNome(1l, 1l, infoBanco.getBanco().getNome());

				if(b != null){
					infoBanco.setBanco(b);
				} else {
					b = new Banco();
					b.setBanco_id(7L);
					infoBanco.setBanco(b);
				}

				Localidade l = parceiroLocalidade.getLocalidade();

				if (l.getLocalidade_id() == null){

					restfulie = Restfulie.custom();
					addressFinder = new BrazilianAddressFinder(restfulie);
					String[] resultado = addressFinder.findAddressByZipCode(l.getCep()).asAddressArray();

					l.setPais(this.paisDao.buscaPais("Brasil"));
	
					if(!resultado[4].equals(""))
						l.setRegiao(this.regiaoDao.buscaPorNome(resultado[4]));
	
					if(!resultado[3].equals(""))
						l.setCidade(this.cidadeDao.buscaPorNome(resultado[3]));
	
					if(!resultado[0].equals(""))
						l.setTipoLocalidade(this.tipoLocalidadeDao.buscaPorNome(resultado[0]));
	
					if(!resultado[1].equals(""))
						l.setEndereco(resultado[1]);
	
					if(!resultado[2].equals(""))
						l.setBairro(resultado[2]);

				}

				result.include("parceiroLocalidade",parceiroLocalidade);
				result.include("localidade",parceiroLocalidade.getLocalidade());
				result.include("parceiroContatos",this.pnDao.buscaParceiroContatos(parceiroNegocio));
				result.include("parceiroBeneficios",this.pnDao.buscaParceiroBeneficios(parceiroNegocio));
				result.include("parceiroInfoBanco",infoBanco);

				TipoParceiro tipoParceiro = this.tipoParceiroDao.buscaTipoParceiro(1L,1L,"Pessoa Física");

				parceiroNegocio.setTipoParceiro(tipoParceiro);
				parceiroNegocio.setIsCliente(true);
				
				result.include("notice","Info: Cliente encontrado na Base PN. Clique em Salvar. ");

			} else {

				result.include("notice","Erro: Cliente não encontrado. Busque pelo CPF no campo acima. ");

			}

		} else {

			parceiroNegocio = this.parceiroNegocioDao.load(parceiroNegocio.getParceiroNegocio_id());

			result.include("parceiroLocalidades",this.parceiroLocalidadeDao.buscaParceiroLocalidades(parceiroNegocio.getParceiroNegocio_id()));
			result.include("parceiroInfoBanco",this.parceiroInfoBancoDao.buscaParceiroInfoBancoByParceiro(parceiroNegocio.getParceiroNegocio_id()));
			result.include("parceiroContatos",this.parceiroContatoDao.buscaParceiroContatos(parceiroNegocio.getParceiroNegocio_id()));
			result.include("parceiroBeneficios",this.parceiroBeneficioDao.buscaParceiroBeneficioByParceiroNegocio(parceiroNegocio.getParceiroNegocio_id()));
			result.include("funcionario",this.funcionarioDao.buscaFuncionarioPorParceiroNegocio(parceiroNegocio.getParceiroNegocio_id()));
			result.include("parceiroNegocio",this.parceiroNegocioDao.load(parceiroNegocio.getParceiroNegocio_id()));
			
			result.include("notice","Info: Cliente encontrado na Base SGO. Possível apenas alteração. ");

		}

		result.include("departamentos", this.departamentoDao.buscaAllDepartamento(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
		result.include("funcoes", this.funcaoDao.buscaAllFuncao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
		result.include("supervisores", this.funcionarioDao.buscaSupervisorFuncionarioByPerfil(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Supervisor"));
		result.include("tiposEndereco",this.tipoEnderecoDao.buscaTiposEnderecoToLocalidades());
		result.include("tiposContato",this.tipoContatoDao.buscaTiposContatos());
		result.include("tiposParceiro", this.tipoParceiroDao.buscaTiposParceiro());
		result.include("sexos", this.sexoDao.buscaSexos());
		result.include("estadosCivis", this.estadoCivilDao.buscaEstadosCivis());
		result.include("parceiroNegocio",parceiroNegocio);
		result.include("bancos",this.bancoDao.buscaAllBancos());
		result.include("convenios",this.convenioDao.buscaConvenioToFillComboByEmpOrg(1l, 1l));
		result.include("meiosPagamento",this.meioPagamentoDao.buscaAllMeioPagamento(1l, 1l));
		result.include("operacoes",this.operacaoDao.buscaOperacoes(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
		result.include("categoriasParceiro",this.categoriaParceiroDao.buscaAllCategoriaParceiroByEmpOrg(1l, 1l));
		result.include("classificacoesParceiro",this.classificacaoParceiroDao.buscaAllClassificacaoParceiroByEmpOrg(1l, 1l));
		result.include("gruposParceiro",this.grupoParceiroDao.buscaAllGrupoParceiroByEmpOrg(1l, 1l));

	}

	@Get
	@Path("/parceironegocio/parceiroLocalidades")
	public void parceiroLocalidades(Long parceironegocio_id){

		result.include("parceiroLocalidades",this.parceiroLocalidadeDao.buscaParceiroLocalidades(parceironegocio_id));

	}

	@Get
	@Path("/parceironegocio/parceiroContatos")
	public void parceiroContatos(Long parceironegocio_id){

		result.include("tiposContato",this.tipoContatoDao.buscaTiposContatos());
		result.include("parceiroContatos",this.parceiroContatoDao.buscaParceiroContatos(parceironegocio_id));

	}
	
	@Get
	@Path("/parceironegocio/parceiroBeneficios")
	public void parceiroBeneficios(Long parceironegocio_id){

		result.include("convenios",this.convenioDao.buscaConvenioToFillComboByEmpOrg(1l,1l));
		result.include("parceiroBeneficios",this.parceiroBeneficioDao.buscaParceiroBeneficioByParceiroNegocio(parceironegocio_id));

	}

	@Post
	@Path("/parceironegocio/salva")
	public void salva(ParceiroNegocio parceiroNegocio,Funcionario funcionario, ParceiroLocalidade parceiroLocalidade,Collection<ParceiroContato> parceiroContatos,
			Collection<ParceiroBeneficio> parceiroBeneficios,Localidade localidade, ParceiroInfoBanco parceiroInfoBanco){

		String mensagem = "";


		if(this.parceiroNegocioDao.buscaParceiroNegocioByDocumento(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), parceiroNegocio.getCpf()) == null ){

			try {

				parceiroNegocio.setCreated(dataAtual);
				parceiroNegocio.setUpdated(dataAtual);
				parceiroNegocio.setCreatedBy(usuario);
				parceiroNegocio.setUpdatedBy(usuario);				
				parceiroNegocio.setIsActive(parceiroNegocio.getIsActive() == null ? false : true);
				
				this.parceiroNegocioDao.beginTransaction();
				this.parceiroNegocioDao.adiciona(parceiroNegocio);
				this.parceiroNegocioDao.commit();

				this.parceiroNegocio.setParceiroNegocio_id(parceiroNegocio.getParceiroNegocio_id());
				this.parceiroNegocio.setCpf(parceiroNegocio.getCpf());

			} catch(Exception e) {
	
				this.parceiroNegocioDao.rollback();
	
				if (e.getCause().toString().indexOf("PK_PARCEIRONEGOCIO") != -1){

					mensagem = "Erro: Parceiro Negócio " + parceiroNegocio.getNome() + " já existente.";

				} else {

					mensagem = "Erro ao adicionar Parceiro Negócio:";

				}

			}

			if(parceiroNegocio.getIsFuncionario()){

				Funcionario f = new Funcionario();

					if(funcionario.getSupervisorFuncionario().getFuncionario_id() != null)
						f.setSupervisorFuncionario(funcionario.getSupervisorFuncionario());

					f.setEmpresa(parceiroNegocio.getEmpresa());
					f.setOrganizacao(parceiroNegocio.getOrganizacao());
					f.setDepartamento(funcionario.getDepartamento());
					f.setParceiroNegocio(parceiroNegocio);
					f.setFuncao(funcionario.getFuncao());
					f.setNome(parceiroNegocio.getNome());
					f.setChave(parceiroNegocio.getNome());
					f.setDescricao(parceiroNegocio.getNome());
					f.setApelido(funcionario.getApelido());
					f.setOperacao(funcionario.getOperacao().getOperacao_id() == null ? null : funcionario.getOperacao());
	
					f.setCreated(dataAtual);
					f.setUpdated(dataAtual);
					f.setCreatedBy(usuario);
					f.setUpdatedBy(usuario);
					
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
						mensagem = "Erro: Erro ao adicionar Perfil:";
					}
	
				}
	
				Usuario u = new Usuario();
				Funcionario func = funcionarioDao.buscaFuncionarioByEmpOrgFuncionario(funcionario.getSupervisorFuncionario().getFuncionario_id());

				//if(funcionario.getSupervisor().getParceiroNegocio_id() != null)
				//if(func != null)
				if(funcionario.getSupervisorFuncionario().getFuncionario_id() != null)
					//u.setSupervisorUsuario(this.usuarioDao.buscaUsuarioByParceiroNegocio(empresa.getEmpresa_id(),organizacao.getOrganizacao_id(), funcionario.getSupervisor().getParceiroNegocio_id()));
					u.setSupervisorUsuario(this.usuarioDao.buscaUsuarioByParceiroNegocio(empresa.getEmpresa_id(),organizacao.getOrganizacao_id(), func.getParceiroNegocio().getParceiroNegocio_id()));
	
					u.setChave(parceiroNegocio.getCpf());
					u.setEmpresa(parceiroNegocio.getEmpresa());
					u.setOrganizacao(parceiroNegocio.getOrganizacao());
					u.setParceiroNegocio(parceiroNegocio);
					u.setSenha("ouro123");
					u.setNome(parceiroNegocio.getNome());
					u.setCreated(dataAtual);
					u.setUpdated(dataAtual);
					u.setCreatedBy(usuario);
					u.setUpdatedBy(usuario);
					u.setApelido(funcionario.getApelido());
					u.setIsActive(true);

				if(u.getUsuario_id() == null) {
					this.usuarioDao.beginTransaction();
					this.usuarioDao.adiciona(u);
					this.usuarioDao.commit();
				}
	
			}
	
			if(parceiroContatos != null){
				for(ParceiroContato parceiroContato : parceiroContatos){
	
					parceiroContato.setEmpresa(empresa);
					parceiroContato.setOrganizacao(organizacao);
					parceiroContato.setParceiroNegocio(parceiroNegocio);

					parceiroContato.setCreated(dataAtual);
					parceiroContato.setUpdated(dataAtual);

					parceiroContato.setCreatedBy(usuario);
					parceiroContato.setUpdatedBy(usuario);

					parceiroContato.setIsActive(true);
		
					this.parceiroContatoDao.beginTransaction();
					this.parceiroContatoDao.adiciona(parceiroContato);
					this.parceiroContatoDao.commit();
		
				}
			}
			
			if(parceiroBeneficios != null){

				for(ParceiroBeneficio parceiroBeneficio : parceiroBeneficios){

					Convenio convenio = this.convenioDao.buscaConvenioByEmpOrgId(1L,1L,parceiroBeneficio.getConvenio().getConvenio_id());

					if(convenio == null){
						convenio = new Convenio();
						convenio.setConvenio_id(1l);
					}

					parceiroBeneficio.setEmpresa(empresa);
					parceiroBeneficio.setOrganizacao(organizacao);
					parceiroBeneficio.setParceiroNegocio(parceiroNegocio);
					parceiroBeneficio.setConvenio(convenio);

					parceiroBeneficio.setCreated(dataAtual);
					parceiroBeneficio.setUpdated(dataAtual);

					parceiroBeneficio.setCreatedBy(usuario);
					parceiroBeneficio.setUpdatedBy(usuario);

					parceiroBeneficio.setIsActive(true);

					if(this.parceiroBeneficioDao.buscaParceiroBeneficioPorNumeroBeneficio(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), parceiroBeneficio.getNumeroBeneficio()) == null) {

						this.parceiroBeneficioDao.beginTransaction();
						this.parceiroBeneficioDao.adiciona(parceiroBeneficio);
						this.parceiroBeneficioDao.commit();

					}

				}
			}

			if(! localidade.getCep().equals("") ){
				
				Localidade l = this.localidadeDao.buscaLocalidade(localidade.getCep());

				if(l.getLocalidade_id() == null)	{
		
					Empresa emp = new Empresa();
					Organizacao org = new Organizacao();
					
					emp.setEmpresa_id(1l);
					org.setOrganizacao_id(1l);

					localidade.setEmpresa(emp);
					localidade.setOrganizacao(org);
					
					localidade.setCreated(dataAtual);
					localidade.setCreatedBy(usuario);
					localidade.setIsActive(true);
		
					try {

						this.localidadeDao.beginTransaction();
						this.localidadeDao.adiciona(localidade);
						this.localidadeDao.commit();
					
					} catch(Exception e) {
		
						this.localidadeDao.rollback();
		
						if (e.getCause().toString().indexOf("PK_LOCALIDADE") != -1){
							mensagem = "Erro: Localidade " + localidade.getCep() + " já existente.";
						} else {
							mensagem = "Erro: Erro ao adicionar Localidade:";
						}

					}
		
				} else {

					localidade.setLocalidade_id(l.getLocalidade_id());

				}
		
				Collection<TipoEndereco> tiposEndereco = this.tipoEnderecoDao.buscaTiposEnderecoToLocalidades();
		
				for(TipoEndereco tipoEndereco : tiposEndereco){
		
					ParceiroLocalidade pl = new ParceiroLocalidade();
		
					pl.setEmpresa(empresa);
					pl.setOrganizacao(organizacao);
					pl.setParceiroNegocio(parceiroNegocio);
					pl.setLocalidade(localidade);
					pl.setTipoEndereco(tipoEndereco);
					pl.setNumero(parceiroLocalidade.getNumero());
					pl.setComplemento(parceiroLocalidade.getComplemento());
					pl.setPontoReferencia(parceiroLocalidade.getPontoReferencia());

					pl.setCreated(dataAtual);
					pl.setUpdated(dataAtual);

					pl.setCreatedBy(usuario);
					pl.setUpdatedBy(usuario);

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
							mensagem = "Erro: Erro ao adicionar Parceiro Localidade :";
						}
		
					}
		
				}
				
			}
			
	
			if(parceiroInfoBanco != null){
	
				parceiroInfoBanco.setEmpresa(empresa);
				parceiroInfoBanco.setOrganizacao(organizacao);
				
				parceiroInfoBanco.setCreated(dataAtual);
				parceiroInfoBanco.setUpdated(dataAtual);

				parceiroInfoBanco.setCreatedBy(usuario);
				parceiroInfoBanco.setUpdatedBy(usuario);
				
				parceiroInfoBanco.setIsActive(true);

				parceiroInfoBanco.setParceiroNegocio(parceiroNegocio);
	
				this.parceiroInfoBancoDao.beginTransaction();
				this.parceiroInfoBancoDao.adiciona(parceiroInfoBanco);
				this.parceiroInfoBancoDao.commit();
			}
	
			mensagem = "Parceiro de Negócios " + parceiroNegocio.getNome() + " adicionado com sucesso. ";	
		
		} else {
			
			mensagem = "Erro: Parceiro de Negócios " + parceiroNegocio.getNome() + " já cadastrado. ";
		}

		this.parceiroNegocioDao.clear();
		this.parceiroNegocioDao.close();

		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}
	
	@Post
	@Path("/parceironegocio/altera")
	public void altera(ParceiroNegocio parceiroNegocio, Funcionario funcionario){

		String mensagem = "Alterado Com sucesso";

		try{
			
			this.parceiroNegocio = this.parceiroNegocioDao.load(parceiroNegocio.getParceiroNegocio_id());
			
			this.parceiroNegocio.setTipoParceiro(parceiroNegocio.getTipoParceiro());
			this.parceiroNegocio.setNome(parceiroNegocio.getNome());
			this.parceiroNegocio.setCpf(parceiroNegocio.getCpf());
			this.parceiroNegocio.setRg(parceiroNegocio.getRg());
			this.parceiroNegocio.setDataNascimento(parceiroNegocio.getDataNascimento());
			this.parceiroNegocio.setSexo(parceiroNegocio.getSexo());
			this.parceiroNegocio.setEstadoCivil(parceiroNegocio.getEstadoCivil());

			this.parceiroNegocio.setUpdated(GregorianCalendar.getInstance());
			this.parceiroNegocio.setUpdatedBy(usuario);

			this.parceiroNegocioDao.beginTransaction();
			this.parceiroNegocioDao.atualiza(this.parceiroNegocio);
			this.parceiroNegocioDao.commit();

			if(this.parceiroNegocio.getIsFuncionario()){

				this.funcionario = this.funcionarioDao.load(funcionario.getFuncionario_id());

				this.funcionario.setDepartamento(funcionario.getDepartamento());

				if (this.funcionario.getOperacao().getOperacao_id() != null){
					this.funcionario.setOperacao(funcionario.getOperacao());
				}

				this.funcionario.setFuncao(funcionario.getFuncao());
				this.funcionario.setSupervisorFuncionario(funcionario.getSupervisorFuncionario());
				this.funcionario.setApelido(funcionario.getApelido());

				this.funcionario.setUpdated(GregorianCalendar.getInstance());
				this.funcionario.setUpdatedBy(usuario);

				this.funcionarioDao.beginTransaction();
				this.funcionarioDao.atualiza(this.funcionario);
				this.funcionarioDao.commit();

				Usuario usuario = this.usuarioDao.buscaUsuarioByParceiroNegocio(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), this.parceiroNegocio.getParceiroNegocio_id());

				usuario.setApelido(this.funcionario.getApelido());
				Funcionario f = funcionarioDao.buscaFuncionarioByEmpOrgFuncionario(this.funcionario.getSupervisorFuncionario().getFuncionario_id());
				//usuario.setSupervisorUsuario(this.usuarioDao.buscaUsuarioByParceiroNegocio(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), this.funcionario.getSupervisorFuncionario().getFuncionario_id()));
				usuario.setSupervisorUsuario(this.usuarioDao.buscaUsuarioByParceiroNegocio(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), f.getParceiroNegocio().getParceiroNegocio_id()));
				usuario.setUpdated(GregorianCalendar.getInstance());
				usuario.setUpdatedBy(usuario);

				this.usuarioDao.beginTransaction();
				this.usuarioDao.atualiza(usuario);
				this.usuarioDao.commit();

			}

			mensagem = " Parceiro Negócio : " + parceiroNegocio.getNome() + " atualizado com sucesso ";

		} catch(Exception e){

			mensagem = e.getMessage();

		}

		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();
	}
	
	@Get 
	@Path("/parceironegocio/busca.json")
	public void parceironegocio(Long empresa_id, Long organizacao_id, String nome){

		result.use(Results.json()).withoutRoot().from(this.parceiroNegocioDao.buscaParceiroNegocio(empresa_id, organizacao_id, nome)).serialize();

	}

	@Post
	@Path("/parceironegocio/excluiLocalidade")
	public void excluiLocalidade(ParceiroLocalidade parceiroLocalidade){

		ParceiroLocalidade pl = this.parceiroLocalidadeDao.load(parceiroLocalidade.getParceiroLocalidade_id());

		pl.setIsActive(false);

		this.parceiroLocalidadeDao.beginTransaction();
		this.parceiroLocalidadeDao.atualiza(pl);
		this.parceiroLocalidadeDao.commit();

		result.include("msg","Registro removido com sucesso").forwardTo(this).msg();

	}
	
	@Post
	@Path("/parceironegocio/excluiContato")
	public void excluiContato(ParceiroContato parceiroContato){

		ParceiroContato pc = this.parceiroContatoDao.load(parceiroContato.getParceiroContato_id());

		pc.setIsActive(false);

		this.parceiroContatoDao.beginTransaction();
		this.parceiroContatoDao.atualiza(pc);
		this.parceiroContatoDao.commit();

		result.include("msg","Registro removido com sucesso").forwardTo(this).msg();

	}
	
	@Post
	@Path("/parceironegocio/salvaLocalidade")
	public void salvaLocalidade(Localidade localidade, ParceiroLocalidade parceiroLocalidade){

		localidade.setIsActive(true);
		
		Empresa emp = new Empresa();
		Organizacao o = new Organizacao();
		
		emp.setEmpresa_id(1l);
		o.setOrganizacao_id(1l);

		localidade.setEmpresa(emp);
		localidade.setOrganizacao(o);

		String mensagem = "";
		
		if(localidade.getLocalidade_id() == null)	{
		
			try {
	
				this.localidadeDao.beginTransaction();
				this.localidadeDao.adiciona(localidade);
				this.localidadeDao.commit();
			
			} catch(Exception e) {
	
				this.localidadeDao.rollback();
	
				if (e.getCause().toString().indexOf("PK_LOCALIDADE") != -1){
					mensagem = "Erro: Localidade " + localidade.getCep() + " já existente.";
				} else {
					mensagem = "Erro ao adicionar Localidade:";
				}
	
				result.include("msg",mensagem).redirectTo(this).msg();
	
			}
		
		}

		Collection<TipoEndereco> tiposEndereco = this.tipoEnderecoDao.buscaTiposEnderecoToLocalidades();
		ParceiroNegocio parceiroNegocio = this.parceiroNegocioDao.load(parceiroLocalidade.getParceiroNegocio().getParceiroNegocio_id());

		for(TipoEndereco tipoEndereco : tiposEndereco){

			ParceiroLocalidade pl = new ParceiroLocalidade();

			pl.setEmpresa(empresa);
			pl.setOrganizacao(organizacao);
			pl.setParceiroNegocio(parceiroNegocio);
			pl.setLocalidade(localidade);
			pl.setTipoEndereco(tipoEndereco);
			pl.setNumero(parceiroLocalidade.getNumero());
			pl.setComplemento(parceiroLocalidade.getComplemento());
			pl.setPontoReferencia(parceiroLocalidade.getPontoReferencia());
			pl.setIsActive(true);

			if(this.parceiroLocalidadeDao.buscaParceiroLocalidadeNum(parceiroNegocio.getEmpresa().getEmpresa_id(),parceiroNegocio.getOrganizacao().getOrganizacao_id(), 
					parceiroNegocio.getParceiroNegocio_id(),localidade.getLocalidade_id(), tipoEndereco.getTipoEndereco_id(),pl.getNumero(), pl.getIsActive()) == null){

				try{

					this.parceiroLocalidadeDao.beginTransaction();
					this.parceiroLocalidadeDao.adiciona(pl);
					this.parceiroLocalidadeDao.commit();
					
					mensagem = " Localidade adicionado com sucesso. ";

				} catch(Exception e) {

					this.parceiroLocalidadeDao.rollback();
					mensagem = "Erro ao adicionar Parceiro Localidade :";

					result.include("msg",mensagem).redirectTo(this).msg();

				}

			} else {
				
				mensagem = "Erro : Localidade do tipo " + pl.getTipoEndereco().getNome() + " já existente. ";

			}
		}

		result.include("notice",mensagem);
		result.redirectTo(this).parceiroLocalidades(parceiroLocalidade.getParceiroNegocio().getParceiroNegocio_id());
	}

	@Post
	@Path("/parceironegocio/salvaContato")
	public void salvaContato(ParceiroContato parceiroContato){

		parceiroContato.setIsActive(true);
		parceiroContato.setEmpresa(empresa);
		parceiroContato.setOrganizacao(organizacao);

		String mensagem = "";

		try {

			this.parceiroContatoDao.beginTransaction();
			this.parceiroContatoDao.adiciona(parceiroContato);
			this.parceiroContatoDao.commit();
		
		} catch(Exception e) {

			this.parceiroContatoDao.rollback();

			if (e.getCause().toString().indexOf("PK_PARCEIROCONTATO") != -1){

				mensagem = "Erro: Contato " + parceiroContato.getNome() + " já existente.";

			} else {

				mensagem = "Erro ao adicionar Contato:";

			}

			result.include("msg",mensagem).redirectTo(this).msg();
		}

		result.redirectTo(this).parceiroContatos(parceiroContato.getParceiroNegocio().getParceiroNegocio_id());
	}
	
	@Post
	@Path("/parceironegocio/salvaBeneficio")
	public void salvaBeneficio(ParceiroBeneficio parceiroBeneficio){

		parceiroBeneficio.setIsActive(true);
		parceiroBeneficio.setEmpresa(empresa);
		parceiroBeneficio.setOrganizacao(organizacao);

		String mensagem = "";

		try {

			this.parceiroBeneficioDao.beginTransaction();
			this.parceiroBeneficioDao.adiciona(parceiroBeneficio);
			this.parceiroBeneficioDao.commit();
		
		} catch(Exception e) {

			this.parceiroBeneficioDao.rollback();

			if (e.getCause().toString().indexOf("PK_PARCEIROBENEFICIO") != -1){

				mensagem = "Erro: Contato " + parceiroBeneficio.getNumeroBeneficio() + " já existente.";

			} else {

				mensagem = "Erro ao adicionar Matrícula:";

			}

			result.include("msg",mensagem).redirectTo(this).msg();

		}

		result.redirectTo(this).parceiroBeneficios(parceiroBeneficio.getParceiroNegocio().getParceiroNegocio_id());

	}
	
	@Post
	@Path("/parceironegocio/alteraParceiroLocalidade")
	public void alteraParceiroLocalidade(ParceiroLocalidade parceiroLocalidade){

		ParceiroLocalidade pl = this.parceiroLocalidadeDao.load(parceiroLocalidade.getParceiroLocalidade_id());
		ParceiroNegocio parceiroNegocio = this.parceiroNegocioDao.load(pl.getParceiroNegocio().getParceiroNegocio_id());

		if(parceiroLocalidade.getNumero() != null)
			pl.setNumero(parceiroLocalidade.getNumero());
		
		if(parceiroLocalidade.getIsActive() != null)
			pl.setIsActive(parceiroLocalidade.getIsActive());
		
		if(parceiroLocalidade.getComplemento() != null)
			pl.setComplemento(parceiroLocalidade.getComplemento());
		
		if(parceiroLocalidade.getTipoEndereco() != null)
			pl.setTipoEndereco(this.tipoEnderecoDao.load(parceiroLocalidade.getTipoEndereco().getTipoEndereco_id()));

		if(this.parceiroLocalidadeDao.buscaParceiroLocalidadeNum(parceiroNegocio.getEmpresa().getEmpresa_id(),parceiroNegocio.getOrganizacao().getOrganizacao_id(), 
				parceiroNegocio.getParceiroNegocio_id(),pl.getLocalidade().getLocalidade_id(), pl.getTipoEndereco().getTipoEndereco_id(), pl.getNumero(), pl.getIsActive()) == null){

			this.parceiroLocalidadeDao.beginTransaction();
			this.parceiroLocalidadeDao.atualiza(pl);
			this.parceiroLocalidadeDao.commit();
			
			result.include("msg","Parceiro Localidade atualizado com sucesso.").redirectTo(this).msg();

		} else {

			result.include("msg","Erro : Parceiro Localidade já existente neste endereço.").redirectTo(this).msg();

		}

	}
	
	@Post
	@Path("/parceironegocio/alteraParceiroContato")
	public void alteraParceiroContato(ParceiroContato parceiroContato){

		ParceiroContato pc = this.parceiroContatoDao.load(parceiroContato.getParceiroContato_id());

		if(parceiroContato.getNome() != null)
			pc.setNome(parceiroContato.getNome());

		if(parceiroContato.getTipoContato() != null)
			pc.setTipoContato(this.tipoContatoDao.load(parceiroContato.getTipoContato().getTipoContato_id()));

		if(this.parceiroContatoDao.buscaParceiroContatoByTipoContatoNome(pc.getTipoContato().getTipoContato_id(),pc.getNome()) == null) {

			this.parceiroContatoDao.beginTransaction();
			this.parceiroContatoDao.atualiza(pc);
			this.parceiroContatoDao.commit();

			result.include("msg","Parceiro Contato atualizado com sucesso.").redirectTo(this).msg();

		} else {

			result.include("msg","Erro : Parceiro Contato já existente.").redirectTo(this).msg();

		}

	}
	
	@Post
	@Path("/parceironegocio/alteraParceiroBeneficio")
	public void alteraParceiroBeneficio(ParceiroBeneficio parceiroBeneficio){

		ParceiroBeneficio pb = this.parceiroBeneficioDao.load(parceiroBeneficio.getParceiroBeneficio_id());

		if(parceiroBeneficio.getNumeroBeneficio() != null)
			pb.setNumeroBeneficio(parceiroBeneficio.getNumeroBeneficio());
		
		if(parceiroBeneficio.getSenha() != null)
			pb.setSenha(parceiroBeneficio.getSenha());

		if(parceiroBeneficio.getConvenio() != null)
			pb.setConvenio(this.convenioDao.load(parceiroBeneficio.getConvenio().getConvenio_id()));

		this.parceiroBeneficioDao.beginTransaction();
		this.parceiroBeneficioDao.atualiza(pb);
		this.parceiroBeneficioDao.commit();

	}

	@Get
	public void msg(){

	}
	
	@Post
	@Path("/parceironegocio/limpar")
	public void limpar() {

		this.parceiroNegocio.setParceiroNegocio_id(null);
		this.parceiroNegocio.setCpf(null);

		result.redirectTo(this).cadastro();

	}

}
