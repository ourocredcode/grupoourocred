package br.com.sgo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import br.com.caelum.restfulie.RestClient;
import br.com.caelum.restfulie.Restfulie;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.BancoDao;
import br.com.sgo.dao.CidadeDao;
import br.com.sgo.dao.CoeficienteDao;
import br.com.sgo.dao.ContratoDao;
import br.com.sgo.dao.ControleFormularioDao;
import br.com.sgo.dao.EtapaDao;
import br.com.sgo.dao.FormularioDao;
import br.com.sgo.dao.HistoricoControleFormularioDao;
import br.com.sgo.dao.LocalidadeDao;
import br.com.sgo.dao.MeioPagamentoDao;
import br.com.sgo.dao.OperacaoDao;
import br.com.sgo.dao.OrganizacaoInfoDao;
import br.com.sgo.dao.PaisDao;
import br.com.sgo.dao.ParceiroBeneficioDao;
import br.com.sgo.dao.ParceiroContatoDao;
import br.com.sgo.dao.ParceiroInfoBancoDao;
import br.com.sgo.dao.ParceiroLocalidadeDao;
import br.com.sgo.dao.ParceiroNegocioDao;
import br.com.sgo.dao.PnDao;
import br.com.sgo.dao.ProdutoDao;
import br.com.sgo.dao.RegiaoDao;
import br.com.sgo.dao.TabelaDao;
import br.com.sgo.dao.TipoControleDao;
import br.com.sgo.dao.TipoEnderecoDao;
import br.com.sgo.dao.TipoLocalidadeDao;
import br.com.sgo.dao.WorkflowDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.jasper.FormularioDataSource;
import br.com.sgo.jasper.PropostaDataSource;
import br.com.sgo.modelo.Banco;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.ControleFormulario;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Etapa;
import br.com.sgo.modelo.Formulario;
import br.com.sgo.modelo.HistoricoControleFormulario;
import br.com.sgo.modelo.Localidade;
import br.com.sgo.modelo.MeioPagamento;
import br.com.sgo.modelo.Modalidade;
import br.com.sgo.modelo.NaturezaProfissional;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.OrganizacaoInfo;
import br.com.sgo.modelo.ParceiroBeneficio;
import br.com.sgo.modelo.ParceiroContato;
import br.com.sgo.modelo.ParceiroInfoBanco;
import br.com.sgo.modelo.ParceiroLocalidade;
import br.com.sgo.modelo.ParceiroNegocio;
import br.com.sgo.modelo.Perfil;
import br.com.sgo.modelo.TipoControle;
import br.com.sgo.modelo.TipoEndereco;
import br.com.sgo.modelo.Usuario;
import br.com.sgo.modelo.Workflow;
import br.com.sgo.modelo.cep.BrazilianAddressFinder;

@Resource
public class FormularioController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final ParceiroNegocioDao parceiroNegocioDao;
	private final ParceiroBeneficioDao parceiroBeneficioDao;
	private final ParceiroLocalidadeDao parceiroLocalidadeDao;
	private final ParceiroInfoBancoDao parceiroInfoBancoDao;
	private final ParceiroContatoDao parceiroContatoDao;
	private final ControleFormularioDao controleFormularioDao;
	private final HistoricoControleFormularioDao historicoControleFormularioDao;
	private final TipoControleDao tipoControleDao;
	private final BancoDao bancoDao;
	private final ProdutoDao produtoDao;
	private final FormularioDao formularioDao;
	private final ContratoDao contratoDao;
	private final CoeficienteDao coeficienteDao;
	private final WorkflowDao workflowDao;
	private final EtapaDao etapaDao;
	private final PnDao pnDao;
	private final MeioPagamentoDao meioPagamentoDao;
	private final TabelaDao tabelaDao;
	private final OperacaoDao operacaoDao;
	private final PaisDao paisDao;
	private final CidadeDao cidadeDao;
	private final RegiaoDao regiaoDao;
	private final TipoLocalidadeDao tipoLocalidadeDao;
	private final LocalidadeDao localidadeDao;
	private final TipoEnderecoDao tipoEnderecoDao;
	private final OrganizacaoInfoDao organizacaoInfoDao;

	private BrazilianAddressFinder addressFinder;
	private RestClient restfulie;
	private HttpServletResponse response;
	private Formulario formulario;
	private ParceiroNegocio parceiroNegocio;
	private ParceiroLocalidade parceiroLocalidade;
	private ParceiroInfoBanco parceiroInfoBanco;
	private ParceiroBeneficio parceiroBeneficio;
	private List<Contrato> contratos;
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;
	private Perfil perfil;
	private Workflow workflow;
	private Collection<Etapa> etapas;
	private Collection<Etapa> motivos;
	private Collection<MeioPagamento> meiosPagamento;
	
	private Calendar dataAtual = Calendar.getInstance();

	public FormularioController(Result result, UsuarioInfo usuarioInfo,ParceiroNegocioDao parceiroNegocioDao,FormularioDao formularioDao,ContratoDao contratoDao,
			CoeficienteDao coeficienteDao,PnDao pnDao,HttpServletResponse response,TipoControleDao tipoControleDao,ParceiroInfoBancoDao parceiroInfoBancoDao,
			ParceiroBeneficioDao parceiroBeneficioDao,ParceiroLocalidadeDao parceiroLocalidadeDao,ParceiroNegocio parceiroNegocio,ParceiroLocalidade parceiroLocalidade,
			ParceiroInfoBanco parceiroInfoBanco,ParceiroBeneficio parceiroBeneficio,Formulario formulario,BancoDao bancoDao,ProdutoDao produtoDao,List<Contrato> contratos,
			WorkflowDao workflowDao, EtapaDao etapaDao,ControleFormularioDao controleFormularioDao,Empresa empresa,Organizacao organizacao,Usuario usuario,
			Perfil perfil,HistoricoControleFormularioDao historicoControleFormularioDao,Workflow workflow, MeioPagamentoDao meioPagamentoDao,TabelaDao tabelaDao,
			ParceiroContatoDao parceiroContatoDao,OperacaoDao operacaoDao,PaisDao paisDao,CidadeDao cidadeDao,RegiaoDao regiaoDao,TipoLocalidadeDao tipoLocalidadeDao,
			LocalidadeDao localidadeDao,TipoEnderecoDao tipoEnderecoDao,OrganizacaoInfoDao organizacaoInfoDao){		

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.response = response;
		this.parceiroNegocioDao = parceiroNegocioDao;
		this.parceiroBeneficio = parceiroBeneficio;
		this.parceiroBeneficioDao =  parceiroBeneficioDao;
		this.parceiroLocalidadeDao = parceiroLocalidadeDao;
		this.controleFormularioDao = controleFormularioDao;
		this.parceiroInfoBancoDao = parceiroInfoBancoDao;
		this.parceiroContatoDao = parceiroContatoDao;
		this.historicoControleFormularioDao = historicoControleFormularioDao;
		this.coeficienteDao = coeficienteDao;
		this.tipoControleDao = tipoControleDao;
		this.bancoDao = bancoDao;
		this.produtoDao = produtoDao;
		this.formulario = formulario;
		this.formularioDao = formularioDao;
		this.contratoDao = contratoDao;
		this.workflowDao = workflowDao;
		this.etapaDao = etapaDao;
		this.pnDao = pnDao;
		this.meioPagamentoDao = meioPagamentoDao;
		this.tabelaDao = tabelaDao;
		this.operacaoDao = operacaoDao;
		this.paisDao = paisDao;
		this.regiaoDao = regiaoDao;
		this.cidadeDao = cidadeDao;
		this.localidadeDao = localidadeDao;
		this.tipoLocalidadeDao = tipoLocalidadeDao;
		this.tipoEnderecoDao = tipoEnderecoDao;
		this.organizacaoInfoDao = organizacaoInfoDao;
		this.parceiroNegocio = parceiroNegocio;
		this.parceiroLocalidade = parceiroLocalidade;
		this.parceiroInfoBanco = parceiroInfoBanco;
		this.parceiroBeneficio = parceiroBeneficio;
		this.contratos = contratos;
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();
		this.usuario = usuarioInfo.getUsuario();
		this.perfil = usuarioInfo.getPerfil();
		this.workflow = workflow;

	}

	@Get
	@Path("/formulario/cadastro")
	public void cadastro(){

		Collection<Banco> bancos = this.bancoDao.buscaBancosToBancoProdutoByEmpOrg(empresa.getEmpresa_id(), organizacao.getOrganizacao_id());
		Collection<Banco> recompraBancos = this.bancoDao.buscaBancoCompradoByEmpOrg(1l, 1l);

		this.formulario.setEmpresa(usuarioInfo.getEmpresa());
		this.formulario.setOrganizacao(usuarioInfo.getOrganizacao());
		this.formulario.setIsActive(true);
		this.formulario.setCreated(Calendar.getInstance());

		result.include("bancos",bancos);
		result.include("recompraBancos",recompraBancos);
		result.include("contratos",contratos);
		result.include("formulario",formulario);
		result.include("parceiroLocalidade",parceiroLocalidade);
		result.include("parceiroBeneficio",parceiroBeneficio);

		
	}
	
	@Get
 	@Path("/formulario/visualiza/{id}")
	public void visualiza(Long id){

		formulario = formularioDao.load(id);
		parceiroNegocio = parceiroNegocioDao.load(formulario.getParceiroNegocio().getParceiroNegocio_id());
		parceiroInfoBanco = parceiroInfoBancoDao.buscaParceiroInfoBancoByParceiro(formulario.getParceiroNegocio().getParceiroNegocio_id());

		formulario.setContratos(this.contratoDao.buscaContratoByFormulario(formulario.getFormulario_id()));

		result.include("bancos", this.bancoDao.buscaBancosToBancoProdutoByEmpOrg(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));

		TipoControle tp = this.tipoControleDao.buscaTipoControleByEmpOrgNome(1l,1l,"Pós Venda");

		ControleFormulario posvenda = controleFormularioDao.buscaControleByFormularioTipoControle(formulario.getFormulario_id(), tp.getTipoControle_id());

		if(posvenda != null) {
			
			HistoricoControleFormulario historico = new HistoricoControleFormulario();
			Collection<HistoricoControleFormulario> historicos = new ArrayList<HistoricoControleFormulario>();

			historicos.addAll(this.historicoControleFormularioDao.buscaHistoricoByFormularioControle(formulario.getFormulario_id(),posvenda.getControleFormulario_id()));

			historico.setControleFormulario(posvenda);
			historico.setCreatedBy(usuario);
			historico.setEmpresa(empresa);
			historico.setOrganizacao(organizacao);
			historico.setPerfil(perfil);
			historico.setFormulario(formulario);
			
			workflow = this.workflowDao.buscaWorkflowPorNome(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Status Pós Venda");

			etapas = etapaDao.buscaEtapasByEmpresaOrganizacaoWorkflow(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), workflow.getWorkflow_id());
			posvenda.setWorkflow(workflow);

			workflow = this.workflowDao.buscaWorkflowPorNome(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), "Motivos Pós Venda");

			motivos = etapaDao.buscaEtapasByEmpresaOrganizacaoWorkflow(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), workflow.getWorkflow_id());

			posvenda.setWorkflowPendencia(workflow);

			meiosPagamento = meioPagamentoDao.buscaAllMeioPagamento(1L, 1L);

			result.include("historico",historico);
			result.include("historicos",historicos);
			result.include("posvenda",posvenda);
			result.include("etapas", etapas);
			result.include("motivos", motivos);
			result.include("meiosPagamento",meiosPagamento);			

		}

		List<ParceiroContato> contatos = new ArrayList<ParceiroContato>();

		for(ParceiroContato pc : parceiroContatoDao.buscaParceiroContatos(parceiroNegocio.getParceiroNegocio_id())) {

			if(pc.getIsActive())
				contatos.add(pc);

		}

		result.include("formulario",formulario);
		result.include("parceiroContatos", contatos);

	}
	
	@Post
	@Path("/formulario/cliente")
	public void cliente(String numeroBeneficio){

		ParceiroBeneficio pb = parceiroBeneficioDao.buscaParceiroBeneficioByNumeroBeneficio(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(),numeroBeneficio);
		
		if(pb != null) {
			
			parceiroBeneficio.setParceiroBeneficio_id(pb.getParceiroBeneficio_id());
			parceiroBeneficio.setNumeroBeneficio(pb.getNumeroBeneficio());
			parceiroBeneficio.setParceiroNegocio(pb.getParceiroNegocio());
			parceiroBeneficio.setConvenio(pb.getConvenio());

			parceiroNegocio = parceiroNegocioDao.load(parceiroBeneficio.getParceiroNegocio().getParceiroNegocio_id());
			parceiroInfoBanco = parceiroInfoBancoDao.buscaParceiroInfoBancoByParceiro(parceiroBeneficio.getParceiroNegocio().getParceiroNegocio_id());

			for(ParceiroLocalidade pl : parceiroLocalidadeDao.buscaParceiroLocalidades(parceiroNegocio.getParceiroNegocio_id())){

				if(pl.getTipoEndereco().getNome().equals("Assinatura")){
					parceiroLocalidade = pl;
				}

			}

			parceiroLocalidade.setParceiroNegocio(parceiroNegocio);
			formulario.setParceiroNegocio(parceiroNegocio);
			formulario.setParceiroBeneficio(parceiroBeneficio);
			formulario.setParceiroLocalidade(parceiroLocalidade);
			formulario.setParceiroInfoBanco(parceiroInfoBanco);

			result.redirectTo(this).cadastro();
			
		} else {

			result.include("notice","Erro: Cliente não encontrado. Busque pelo CPF no campo acima. ");
			result.redirectTo(this).cadastro();

		}

		

	}
	
	@Post
	@Path("/formulario/limpar")
	public void limpar() {

		this.formulario.setFormulario_id(null);
		this.formulario.setParceiroNegocio(null);
		this.formulario.setParceiroBeneficio(null);
		this.parceiroBeneficio.setParceiroBeneficio_id(null);
		this.parceiroBeneficio.setNumeroBeneficio(null);
		this.parceiroLocalidade.setParceiroLocalidade_id(null);
		this.parceiroLocalidade.setLocalidade(null);
		this.formulario.setContratos(new ArrayList<Contrato>());
		this.formulario.setParceiroInfoBanco(null);

		result.redirectTo(this).cadastro();
		result.nothing();

	}
	
	@Post
	@Path("/formulario/adicionaContrato")
	public void adicionaContrato(Contrato contrato) {

		contrato.setEmpresa(usuarioInfo.getEmpresa());
		contrato.setOrganizacao(usuarioInfo.getOrganizacao());
		contrato.setIsActive(true);
		contrato.setCreated(GregorianCalendar.getInstance());
		contrato.setCreatedBy(usuarioInfo.getUsuario());
		contrato.setUsuario(usuarioInfo.getUsuario());
		contrato.setSupervisorCreatedBy(usuarioInfo.getUsuario().getSupervisorUsuario());
		contrato.setBanco(this.bancoDao.buscaBancoById(contrato.getBanco().getBanco_id()));
		contrato.setProduto(this.produtoDao.buscaProdutoById(contrato.getProduto().getProduto_id()));
		contrato.setCoeficiente(this.coeficienteDao.buscaCoeficienteById(contrato.getCoeficiente().getCoeficiente_id()));

		Modalidade modalidade = new Modalidade();
		modalidade.setModalidade_id(1l);

		NaturezaProfissional natureza = new NaturezaProfissional();
		natureza.setNaturezaProfissional_id(1l);

		contrato.setTabela(this.tabelaDao.buscaTabelasByCoeficiente(contrato.getCoeficiente().getCoeficiente_id()));
		contrato.setNumeroBeneficio(this.formulario.getParceiroBeneficio().getNumeroBeneficio());

		contrato.setWorkflow(this.workflowDao.buscaWorkflowByEmpresaOrganizacaoBancoProdutoConvenio(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), 
				contrato.getBanco().getBanco_id(), contrato.getProduto().getProduto_id() ,this.formulario.getParceiroBeneficio().getConvenio().getConvenio_id()));

		contrato.setConvenio(this.formulario.getParceiroBeneficio().getConvenio());

		contrato.setModalidade(modalidade);
		contrato.setNaturezaProfissional(natureza);

		contrato.setEtapa(this.etapaDao.buscaEtapaByEmpresaOrganizacaoNome(empresa.getEmpresa_id(),organizacao.getOrganizacao_id(),"Aguardando Status"));

		if(contrato.getRecompraBanco().getBanco_id() != null){
			contrato.setRecompraBanco(this.bancoDao.buscaBancoById(contrato.getRecompraBanco().getBanco_id()));
		}

		contrato.setIsRepasse(false);

		contrato.setOperacao(this.operacaoDao.buscaOperacaoByEmpOrgUsuario(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(),usuarioInfo.getUsuario().getUsuario_id()));

		formulario.adicionaContrato(contrato);
		result.redirectTo(FormularioController.class).cadastro();

	}
	
	@Post
	@Path("/formulario/salva")
	public void salva() {

		this.formularioDao.beginTransaction();
		this.formularioDao.adiciona(this.formulario);
		this.formularioDao.commit();

		for(Contrato c : this.formulario.getContratos()){

			c.setFormulario(this.formulario);
			c.setRecompraBanco(c.getRecompraBanco().getBanco_id() == null ? null : c.getRecompraBanco());
			
			if(c.getProduto().getNome().equals("MARGEM LIMPA") 
					|| c.getProduto().getNome().equals("MARGEM LIMPA PMSP")
					|| c.getProduto().getNome().equals("MARGEM LIMPA GOVRJ")
					|| c.getProduto().getNome().equals("MARGEM LIMPA SIAPE")
					|| c.getProduto().getNome().equals("RECOMPRA INSS")
					|| c.getProduto().getNome().equals("PORTABILIDADE/REFIN")
					|| c.getProduto().getNome().equals("RECOMPRA PMSP")
					|| c.getProduto().getNome().equals("RECOMPRA GOVRJ") 
					|| c.getProduto().getNome().equals("RECOMPRA SIAPE")
					|| c.getProduto().getNome().equals("RECOMPRA RMC") 
					|| c.getProduto().getNome().equals("AUMENTO") )
				c.setValorContratoLiquido(c.getValorContrato());
			
			if(c.getProduto().getNome().equals("REFINANCIAMENTO")
					|| c.getProduto().getNome().equals("REFINANCIAMENTO PMSP")
					|| c.getProduto().getNome().equals("REFINANCIAMENTO GOVRJ")
					|| c.getProduto().getNome().equals("REFINANCIAMENTO SIAPE")
					|| c.getProduto().getNome().equals("RETENÇÃO") 
					|| c.getProduto().getNome().equals("RETENÇÃO PMSP")
					|| c.getProduto().getNome().equals("RETENÇÃO GOVRJ"))
				c.setValorContratoLiquido(c.getValorLiquido());

			this.contratoDao.beginTransaction();
			this.contratoDao.atualiza(c);
			this.contratoDao.commit();

		}

		result.redirectTo(this).visualiza(this.formulario.getFormulario_id());

	}

	@Get
 	@Path("/formulario/impressao/{id}")
	public void impressao(Long id) {

		String caminhoJasper = "////localhost//sistemas//tomcat7//webapps//sgo//WEB-INF//_repositorio//sgo//";
		String jasper = caminhoJasper + "solicitacao.jasper";

		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("caminhoJasperSolicitacao", jasper);

		JasperPrint impressao = null;

		List<Formulario> forms = new ArrayList<Formulario>();
		Formulario f = this.formularioDao.load(id);

		Collection<Contrato> contratos = this.contratoDao.buscaContratoByFormulario(f.getFormulario_id());

		ParceiroNegocio parceiro =  f.getParceiroNegocio();
		Calendar formularioData = f.getCreated();
		Integer countContratos = new Integer(0);
		Integer countRecompraINSS = new Integer(0);
		Integer countMargemLimpa = new Integer(0);
		Integer countRecompraRMC = new Integer(0);
		Integer countRefinanciamento = new Integer(0);
		String cc = "";
		Boolean localidadeInsert = true;

		for (Iterator<Contrato> it  = contratos.iterator(); it.hasNext();) {

			Formulario formulario = new Formulario();
			Contrato c = (Contrato) it.next();
			
			if(!c.getEtapa().getNome().equals("Recusado")){

				if(c.getConvenio().getNome().equals("INSS"))
					cc = pnDao.buscaDetalhamento(c.getNumeroBeneficio()).getContacorrente() == null ? "0" : pnDao.buscaDetalhamento(c.getNumeroBeneficio()).getContacorrente();

				formulario.setCreated(formularioData);
				formulario.setParceiroNegocio(parceiro);
				formulario.setContratos(separaContrato(c));
				
				ParceiroBeneficio pb = parceiroBeneficioDao.buscaParceiroBeneficioByNumeroBeneficio(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(),c.getNumeroBeneficio());

				parceiroBeneficio.setParceiroBeneficio_id(pb.getParceiroBeneficio_id());
				parceiroBeneficio.setNumeroBeneficio(pb.getNumeroBeneficio());
				parceiroBeneficio.setParceiroNegocio(pb.getParceiroNegocio());

				parceiroNegocio = parceiroNegocioDao.load(parceiroBeneficio.getParceiroNegocio().getParceiroNegocio_id());

				if( ( parceiroLocalidadeDao.buscaParceiroLocalidades(parceiroNegocio.getParceiroNegocio_id() ).size() == 0 ) && localidadeInsert ){

					localidadeInsert = false;
					String cep = this.pnDao.buscaCepByParceiroNegocio(parceiroNegocio);

					Localidade l = this.localidadeDao.buscaLocalidade(cep);

					if (l.getLocalidade_id() == null) {
						
						restfulie = Restfulie.custom();
						addressFinder = new BrazilianAddressFinder(restfulie);
						String[] resultado = addressFinder.findAddressByZipCode(cep).asAddressArray();

						Empresa emp = new Empresa();
						Organizacao org = new Organizacao();

						emp.setEmpresa_id(1l);
						org.setOrganizacao_id(1l);

						l.setEmpresa(emp);
						l.setOrganizacao(org);
						l.setIsActive(true);
						l.setCep(cep);
						l.setCreated(dataAtual);
						l.setCreatedBy(usuario);
						
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

						this.localidadeDao.beginTransaction();
						this.localidadeDao.adiciona(l);
						this.localidadeDao.commit();
						
					}

					Collection<TipoEndereco> tiposEndereco = this.tipoEnderecoDao.buscaTiposEnderecoToLocalidades();

					for(TipoEndereco tipoEndereco : tiposEndereco){

						ParceiroLocalidade pl = new ParceiroLocalidade();

						pl.setEmpresa(empresa);
						pl.setOrganizacao(organizacao);
						pl.setParceiroNegocio(parceiroNegocio);
						pl.setLocalidade(l);
						pl.setTipoEndereco(tipoEndereco);
						pl.setNumero(this.pnDao.buscaNumeroByParceiroNegocio(parceiro));
						pl.setComplemento(this.pnDao.buscaComplementoByParceiroNegocio(parceiro));
						pl.setPontoReferencia(null);

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

						}
						
						if(pl.getTipoEndereco().getNome().equals("Assinatura")){

							parceiroLocalidade = pl;

						}
			
					}
					
					

				} else {

					for(ParceiroLocalidade pl : parceiroLocalidadeDao.buscaParceiroLocalidades(parceiroNegocio.getParceiroNegocio_id())){

						if(pl.getNumero() == null || pl.getNumero().equals("")){

							pl = this.parceiroLocalidadeDao.load(pl.getParceiroLocalidade_id());

							pl.setNumero(this.pnDao.buscaNumeroByParceiroNegocio(parceiro));

							this.parceiroLocalidadeDao.beginTransaction();
							this.parceiroLocalidadeDao.atualiza(pl);
							this.parceiroLocalidadeDao.commit();

						}

						if(pl.getComplemento() == null || pl.getComplemento().equals("")){

							pl = this.parceiroLocalidadeDao.load(pl.getParceiroLocalidade_id());

							pl.setComplemento(this.pnDao.buscaComplementoByParceiroNegocio(parceiro));

							this.parceiroLocalidadeDao.beginTransaction();
							this.parceiroLocalidadeDao.atualiza(pl);
							this.parceiroLocalidadeDao.commit();

						}
						
						if(pl.getTipoEndereco().getNome().equals("Assinatura")){
							parceiroLocalidade = pl;
						}

					}

				}

				countContratos +=1;

				if(c.getProduto().equals("MARGEM LIMPA") || c.getProduto().equals("MARGEM LIMPA PMSP") || c.getProduto().equals("MARGEM LIMPA SIAPE") || c.getProduto().equals("MARGEM LIMPA GOVRJ") || c.getProduto().equals("AUMENTO") )
					countMargemLimpa += 1;
				if(c.getProduto().equals("RECOMPRA INSS") ||   c.getProduto().equals("PORTABILIDADE/REFIN") || c.getProduto().equals("RECOMPRA PMSP") || c.getProduto().equals("RECOMPRA GOVRJ") || c.getProduto().equals("RECOMPRA SIAPE"))
					countRecompraINSS += 1;
				if(c.getProduto().equals("RECOMPRA RMC"))
					countRecompraRMC += 1;
				if(c.getProduto().equals("REFINANCIAMENTO") || c.getProduto().equals("REFINANCIAMENTO PMSP") || c.getProduto().equals("REFINANCIAMENTO GOVRJ") || c.getProduto().equals("REFINANCIAMENTO SIAPE"))
					countRefinanciamento += 1;

				List<ParceiroContato> contatos = new ArrayList<ParceiroContato>();

				for(ParceiroContato pc : parceiroContatoDao.buscaParceiroContatos(parceiroNegocio.getParceiroNegocio_id())) {
					contatos.add(pc);
				}

				if(contatos.size() > 0){
					formulario.setParceiroContatos(contatos);
				}

				formulario.setParceiroBeneficio(parceiroBeneficio);
				formulario.setParceiroLocalidade(parceiroLocalidade);
				formulario.setParceiroInfoBanco(parceiroInfoBanco);

				forms.add(formulario);
				
			}

		}
		
		parametros.put("countContratos", countContratos);
		parametros.put("countMargemLimpa", countMargemLimpa);
		parametros.put("countRecompraINSS", countRecompraINSS);
		parametros.put("countRecompraRMC", countRecompraRMC);
		parametros.put("countRefinanciamento", countRefinanciamento);
		parametros.put("detalhamentoCC", cc);

		String tituloFormulario = f.getOrganizacao().getDescricao() + " - GRUPO OURO CRED";

		parametros.put("tituloFormulario", tituloFormulario);

		
		try{

			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/pdf");
	
			ServletOutputStream responseOutputStream = response.getOutputStream();
	
			FormularioDataSource formularioDataSource;
			formularioDataSource = new FormularioDataSource(forms);
	
			impressao = JasperFillManager.fillReport(jasper, parametros , formularioDataSource);

			JasperExportManager.exportReportToPdfStream(impressao, responseOutputStream);
	
			responseOutputStream.flush();
			responseOutputStream.close();

		} catch(IOException e) {

			System.out.println("Erro:" + e);

		} catch(JRException e) {

			e.printStackTrace();
			System.out.println("Erro:" + e.getMessage());

		}
		
		forms = null;
		f = null;
		contratos = null;
		parceiro = null;
		formularioData = null;
		countContratos = null;
		countRecompraINSS = null;
		countMargemLimpa = null;
		countRecompraRMC = null;
		countRefinanciamento = null;
		cc = null;
		localidadeInsert = null;
		impressao = null;
		parceiroNegocio = null;
		parceiroBeneficio = null;
		parceiroLocalidade = null;
		

		result.nothing();
	}
	
	@Get
 	@Path("/formulario/proposta/{id}")
	public void proposta(Long id) {

		String caminhoJasper = "////localhost//sistemas//tomcat7//webapps//sgo//WEB-INF//_repositorio//sgo//";
		String jasper = caminhoJasper + "proposta.jasper";

		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("caminhoJasperSolicitacao", jasper);

		JasperPrint impressao = null;

		List<Formulario> forms = new ArrayList<Formulario>();
		Formulario f = this.formularioDao.load(id);

		Collection<Contrato> contratos = this.contratoDao.buscaContratoByFormulario(f.getFormulario_id());

		ParceiroNegocio parceiro =  f.getParceiroNegocio();
		Calendar formularioData = f.getCreated();
		Integer countContratos = new Integer(0);
		Integer countRecompraINSS = new Integer(0);
		Integer countMargemLimpa = new Integer(0);
		Integer countRecompraRMC = new Integer(0);
		Integer countRefinanciamento = new Integer(0);

		for (Iterator<Contrato> it  = contratos.iterator(); it.hasNext();) {

			Formulario formulario = new Formulario();
			Contrato c = (Contrato) it.next();
			
			if(!c.getEtapa().getNome().equals("Recusado")){

				formulario.setCreated(formularioData);
				formulario.setParceiroNegocio(parceiro);
				formulario.setContratos(separaContrato(c));
				
				ParceiroBeneficio pb = parceiroBeneficioDao.buscaParceiroBeneficioByNumeroBeneficio(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(),c.getNumeroBeneficio());

				parceiroBeneficio.setParceiroBeneficio_id(pb.getParceiroBeneficio_id());
				parceiroBeneficio.setNumeroBeneficio(pb.getNumeroBeneficio());
				parceiroBeneficio.setParceiroNegocio(pb.getParceiroNegocio());

				parceiroNegocio = parceiroNegocioDao.load(parceiroBeneficio.getParceiroNegocio().getParceiroNegocio_id());

				if( ( parceiroLocalidadeDao.buscaParceiroLocalidades(parceiroNegocio.getParceiroNegocio_id() ).size() != 0 ) ){

					
					for(ParceiroLocalidade pl : parceiroLocalidadeDao.buscaParceiroLocalidades(parceiroNegocio.getParceiroNegocio_id())){

						if(pl.getNumero() == null || pl.getNumero().equals("")){

							pl = this.parceiroLocalidadeDao.load(pl.getParceiroLocalidade_id());

							pl.setNumero(this.pnDao.buscaNumeroByParceiroNegocio(parceiro));

							this.parceiroLocalidadeDao.beginTransaction();
							this.parceiroLocalidadeDao.atualiza(pl);
							this.parceiroLocalidadeDao.commit();

						}

						if(pl.getComplemento() == null || pl.getComplemento().equals("")){

							pl = this.parceiroLocalidadeDao.load(pl.getParceiroLocalidade_id());

							pl.setComplemento(this.pnDao.buscaComplementoByParceiroNegocio(parceiro));

							this.parceiroLocalidadeDao.beginTransaction();
							this.parceiroLocalidadeDao.atualiza(pl);
							this.parceiroLocalidadeDao.commit();

						}

						if(pl.getTipoEndereco().getNome().equals("Assinatura")){
							parceiroLocalidade = pl;
						}

					}

				}

				countContratos +=1;

				if(c.getProduto().equals("MARGEM LIMPA") || c.getProduto().equals("MARGEM LIMPA PMSP") || c.getProduto().equals("MARGEM LIMPA SIAPE") || c.getProduto().equals("MARGEM LIMPA GOVRJ") || c.getProduto().equals("AUMENTO") )
					countMargemLimpa += 1;
				if(c.getProduto().equals("RECOMPRA INSS") || c.getProduto().equals("PORTABILIDADE/REFIN") || c.getProduto().equals("RECOMPRA PMSP") || c.getProduto().equals("RECOMPRA GOVRJ") || c.getProduto().equals("RECOMPRA SIAPE"))
					countRecompraINSS += 1;
				if(c.getProduto().equals("RECOMPRA RMC"))
					countRecompraRMC += 1;
				if(c.getProduto().equals("REFINANCIAMENTO") || c.getProduto().equals("REFINANCIAMENTO PMSP") || c.getProduto().equals("REFINANCIAMENTO GOVRJ") || c.getProduto().equals("REFINANCIAMENTO SIAPE"))
					countRefinanciamento += 1;

				List<ParceiroContato> contatos = new ArrayList<ParceiroContato>();

				for(ParceiroContato pc : parceiroContatoDao.buscaParceiroContatos(parceiroNegocio.getParceiroNegocio_id())) {

					if(pc.getIsActive())
						contatos.add(pc);

				}

				if(contatos.size() > 0){
					formulario.setParceiroContatos(contatos);
				}

				formulario.setParceiroBeneficio(parceiroBeneficio);
				formulario.setParceiroLocalidade(parceiroLocalidade);
				formulario.setParceiroInfoBanco(parceiroInfoBanco);

				forms.add(formulario);
				
			}

		}
		
		parametros.put("countContratos", countContratos);
		parametros.put("countMargemLimpa", countMargemLimpa);
		parametros.put("countRecompraINSS", countRecompraINSS);
		parametros.put("countRecompraRMC", countRecompraRMC);
		parametros.put("countRefinanciamento", countRefinanciamento);

		String tituloFormulario = " PROPOSTA DE CRÉDITO - " + f.getOrganizacao().getDescricao() + " - GRUPO OURO CRED ";

		parametros.put("tituloFormulario", tituloFormulario);

		OrganizacaoInfo info = this.organizacaoInfoDao.buscaOrganizacaoById(f.getOrganizacao().getOrganizacao_id());

		parametros.put("organizacaoEndereco", info.getDescricao());
		parametros.put("organizacaoContato", info.getDddFone1() + " " + info.getTelefone1());

		try{

			response.setHeader("Cache-Control", "no-store");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/pdf");
	
			ServletOutputStream responseOutputStream = response.getOutputStream();
	
			PropostaDataSource propostaDataSource;
			propostaDataSource = new PropostaDataSource(forms);
	
			impressao = JasperFillManager.fillReport(jasper, parametros , propostaDataSource);

			JasperExportManager.exportReportToPdfStream(impressao, responseOutputStream);
	
			responseOutputStream.flush();
			responseOutputStream.close();

		} catch(IOException e) {

			System.out.println("Erro:" + e);

		} catch(JRException e) {

			e.printStackTrace();
			System.out.println("Erro:" + e.getMessage());

		}

		forms = null;
		f = null;
		contratos = null;
		parceiro = null;
		formularioData = null;
		countContratos = null;
		countRecompraINSS = null;
		countMargemLimpa = null;
		countRecompraRMC = null;
		countRefinanciamento = null;

		impressao = null;
		parceiroNegocio = null;
		parceiroBeneficio = null;
		parceiroLocalidade = null;

		result.nothing();

	}
	
	public static Collection<Contrato> separaContrato(Contrato contrato) {

		Collection<Contrato> contratos = new ArrayList<Contrato>();
		contratos.add(contrato);

		return contratos;
	}
}