package br.com.sgo.controller;

import java.util.Collection;

import br.com.caelum.restfulie.RestClient;
import br.com.caelum.restfulie.Restfulie;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.CidadeDao;
import br.com.sgo.dao.DepartamentoDao;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.EstadoCivilDao;
import br.com.sgo.dao.FuncaoDao;
import br.com.sgo.dao.FuncionarioDao;
import br.com.sgo.dao.LocalidadeDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.PaisDao;
import br.com.sgo.dao.ParceiroContatoDao;
import br.com.sgo.dao.ParceiroLocalidadeDao;
import br.com.sgo.dao.ParceiroNegocioDao;
import br.com.sgo.dao.PnDao;
import br.com.sgo.dao.RegiaoDao;
import br.com.sgo.dao.SexoDao;
import br.com.sgo.dao.TipoContatoDao;
import br.com.sgo.dao.TipoEnderecoDao;
import br.com.sgo.dao.TipoLocalidadeDao;
import br.com.sgo.dao.TipoParceiroDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Funcionario;
import br.com.sgo.modelo.Localidade;
import br.com.sgo.modelo.ParceiroContato;
import br.com.sgo.modelo.ParceiroLocalidade;
import br.com.sgo.modelo.ParceiroNegocio;
import br.com.sgo.modelo.TipoEndereco;
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
	private final ParceiroLocalidadeDao parceiroLocalidadeDao;
	private final ParceiroContatoDao parceiroContatoDao;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	private final SexoDao sexoDao;
	private final EstadoCivilDao estadoCivilDao;
	private final TipoParceiroDao tipoParceiroDao;
	private final TipoEnderecoDao tipoEnderecoDao;
	private final TipoContatoDao tipoContatoDao;
	private final PaisDao paisDao;
	private final RegiaoDao regiaoDao;
	private final CidadeDao cidadeDao;
	private final TipoLocalidadeDao tipoLocalidadeDao;
	private BrazilianAddressFinder addressFinder;
	private RestClient restfulie;

	public ParceironegocioController(Result result, UsuarioInfo usuarioInfo,ParceiroNegocioDao parceiroNegocioDao,PnDao pnDao,PaisDao paisDao,RegiaoDao regiaoDao, CidadeDao cidadeDao,
			DepartamentoDao departamentoDao,FuncaoDao funcaoDao,FuncionarioDao funcionarioDao,LocalidadeDao localidadeDao,ParceiroLocalidadeDao parceiroLocalidadeDao,ParceiroContatoDao parceiroContatoDao,
			EmpresaDao empresaDao, OrganizacaoDao organizacaoDao,SexoDao sexoDao,EstadoCivilDao estadoCivilDao,TipoParceiroDao tipoParceiroDao,TipoEnderecoDao tipoEnderecoDao,
			TipoLocalidadeDao tipoLocalidadeDao,TipoContatoDao tipoContatoDao) {

		this.result = result;
		this.parceiroNegocioDao = parceiroNegocioDao;
		this.pnDao = pnDao;
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
		this.parceiroContatoDao = parceiroContatoDao;
		this.tipoParceiroDao = tipoParceiroDao;
		this.tipoEnderecoDao = tipoEnderecoDao;
		this.tipoContatoDao = tipoContatoDao;
		this.paisDao = paisDao;
		this.regiaoDao = regiaoDao;
		this.cidadeDao = cidadeDao;
		this.tipoLocalidadeDao = tipoLocalidadeDao;

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
		result.include("tiposContato",this.tipoContatoDao.buscaTiposContatos());

	}

	@Get
	@Path("/parceironegocio/cadastro/{parceironegocio_id}")
	public void cadastro(Long parceironegocio_id){

		result.include("departamentos", this.departamentoDao.buscaDepartamentos(usuarioInfo.getUsuario().getEmpresa().getEmpresa_id(), 
					usuarioInfo.getUsuario().getOrganizacao().getOrganizacao_id()));

		result.include("funcoes", this.funcaoDao.buscaFuncoes(usuarioInfo.getUsuario().getEmpresa().getEmpresa_id(), 
				usuarioInfo.getUsuario().getOrganizacao().getOrganizacao_id()));

		result.include("parceiroLocalidades",this.parceiroLocalidadeDao.buscaParceiroLocalidades(parceironegocio_id));
		result.include("parceiroContatos",this.parceiroContatoDao.buscaParceiroContatos(parceironegocio_id));
		result.include("tiposEndereco",this.tipoEnderecoDao.buscaTiposEnderecoToLocalidades());
		result.include("tiposContato",this.tipoContatoDao.buscaTiposContatos());

		result.include("sexos", this.sexoDao.buscaSexos());
		result.include("estadosCivis", this.estadoCivilDao.buscaEstadosCivis());
		result.include("tiposParceiro", this.tipoParceiroDao.buscaTiposParceiro());
		result.include("funcionario",this.funcionarioDao.buscaFuncionarioPorParceiroNegocio(parceironegocio_id));
		result.include("parceiroNegocio",this.parceiroNegocioDao.load(parceironegocio_id));

	}

	@Post
	@Path("/parceironegocio/cadastro")
	public void cadastro(String doc){

		ParceiroNegocio parceiroNegocio = this.pnDao.buscaParceiroNegocio(doc);
		ParceiroLocalidade parceiroLocalidade = this.pnDao.buscaParceiroLocalidade(parceiroNegocio);
		
		Localidade l = parceiroLocalidade.getLocalidade();

		restfulie = Restfulie.custom();
		addressFinder = new BrazilianAddressFinder(restfulie);
		String[] resultado = addressFinder.findAddressByZipCode(l.getCep()).asAddressArray();

		l.setPais(this.paisDao.buscaPais(usuarioInfo.getEmpresa().getEmpresa_id(), usuarioInfo.getOrganizacao().getOrganizacao_id(),"Brasil"));
		l.setRegiao(this.regiaoDao.buscaPorNome(resultado[4]));
		l.setCidade(this.cidadeDao.buscaPorNome(resultado[3]));
		l.setTipoLocalidade(this.tipoLocalidadeDao.buscaPorNome(resultado[0]));
		l.setEndereco(resultado[1]);
		l.setBairro(resultado[2]);

		result.include("parceiroLocalidade",parceiroLocalidade);
		result.include("localidade",parceiroLocalidade.getLocalidade());
		result.include("parceiroContatos",this.pnDao.buscaParceiroContatos(parceiroNegocio));
		result.include("parceiroInfoBanco",this.pnDao.buscaParceiroInfoBanco(parceiroNegocio));

		result.include("tiposEndereco",this.tipoEnderecoDao.buscaTiposEnderecoToLocalidades());
		result.include("tiposContato",this.tipoContatoDao.buscaTiposContatos());
		result.include("sexos", this.sexoDao.buscaSexos());
		result.include("estadosCivis", this.estadoCivilDao.buscaEstadosCivis());
		result.include("tiposParceiro", this.tipoParceiroDao.buscaTiposParceiro());

		result.include("parceiroNegocio",parceiroNegocio);

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

		if(localidade.getLocalidade_id() == null)	{

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

		}
		
		Collection<TipoEndereco> tiposEndereco = this.tipoEnderecoDao.buscaTiposEnderecoToLocalidades();

		for(TipoEndereco tipoEndereco : tiposEndereco){

			ParceiroLocalidade pl = new ParceiroLocalidade();

			pl.setEmpresa(usuarioInfo.getEmpresa());
			pl.setOrganizacao(usuarioInfo.getOrganizacao());
			pl.setParceiroNegocio(parceiroNegocio);
			pl.setLocalidade(localidade);
			pl.setTipoEndereco(tipoEndereco);
			pl.setNumero(parceiroLocalidade.getNumero());
			pl.setComplemento(parceiroLocalidade.getComplemento());
			pl.setPontoReferencia(parceiroLocalidade.getPontoReferencia());
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
	
	@Get 
	@Path("/parceironegocio/busca.json")
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

		for(TipoEndereco tipoEndereco : tiposEndereco){

			ParceiroLocalidade pl = new ParceiroLocalidade();

			pl.setEmpresa(usuarioInfo.getEmpresa());
			pl.setOrganizacao(usuarioInfo.getOrganizacao());
			pl.setParceiroNegocio(parceiroLocalidade.getParceiroNegocio());
			pl.setLocalidade(localidade);
			pl.setTipoEndereco(tipoEndereco);
			pl.setNumero(parceiroLocalidade.getNumero());
			pl.setComplemento(parceiroLocalidade.getComplemento());
			pl.setPontoReferencia(parceiroLocalidade.getPontoReferencia());
			pl.setIsActive(true);

			try{
				
				this.parceiroLocalidadeDao.beginTransaction();
				this.parceiroLocalidadeDao.adiciona(pl);
				this.parceiroLocalidadeDao.commit();
				
			} catch(Exception e) {

				this.parceiroLocalidadeDao.rollback();

				if (e.getCause().toString().indexOf("PK__PARCEIROLOCALIDADE") != -1){
					mensagem = "Erro: Parceiro Localidade " + parceiroLocalidade.getLocalidade().getCep() + " já existente.";
				} else {
					mensagem = "Erro ao adicionar Parceiro Localidade :";
				}
				
				result.include("msg",mensagem).redirectTo(this).msg();

			}

		}

		result.redirectTo(this).parceiroLocalidades(parceiroLocalidade.getParceiroNegocio().getParceiroNegocio_id());

	}

	@Post
	@Path("/parceironegocio/salvaContato")
	public void salvaContato(ParceiroContato parceiroContato){

		parceiroContato.setIsActive(true);
		parceiroContato.setEmpresa(usuarioInfo.getEmpresa());
		parceiroContato.setOrganizacao(usuarioInfo.getOrganizacao());

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
	@Path("/parceironegocio/alteraParceiroLocalidade")
	public void alteraParceiroLocalidade(ParceiroLocalidade parceiroLocalidade){

		ParceiroLocalidade pl = this.parceiroLocalidadeDao.load(parceiroLocalidade.getParceiroLocalidade_id());

		if(parceiroLocalidade.getNumero() != null)
			pl.setNumero(parceiroLocalidade.getNumero());
		if(parceiroLocalidade.getComplemento() != null)
			pl.setComplemento(parceiroLocalidade.getComplemento());
		if(parceiroLocalidade.getTipoEndereco() != null)
			pl.setTipoEndereco(this.tipoEnderecoDao.load(parceiroLocalidade.getTipoEndereco().getTipoEndereco_id()));

		if(this.parceiroLocalidadeDao.buscaParceiroLocalidade(pl.getParceiroNegocio(), pl.getLocalidade(), pl.getTipoEndereco()) == null) {

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

		if(this.parceiroContatoDao.buscaParceiroContato(pc.getTipoContato().getTipoContato_id(),pc.getNome()) == null) {

			this.parceiroContatoDao.beginTransaction();
			this.parceiroContatoDao.atualiza(pc);
			this.parceiroContatoDao.commit();

			result.include("msg","Parceiro Contato atualizado com sucesso.").redirectTo(this).msg();

		} else {

			result.include("msg","Erro : Parceiro Contato já existente.").redirectTo(this).msg();

		}

	}

	@Get
	public void msg(){

	}

}
