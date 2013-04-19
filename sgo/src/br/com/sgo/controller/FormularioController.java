package br.com.sgo.controller;

import java.util.Collection;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.BancoDao;
import br.com.sgo.dao.ParceiroBeneficioDao;
import br.com.sgo.dao.ParceiroLocalidadeDao;
import br.com.sgo.dao.ParceiroNegocioDao;
import br.com.sgo.dao.ProdutoDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Banco;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Formulario;
import br.com.sgo.modelo.ParceiroBeneficio;
import br.com.sgo.modelo.ParceiroInfoBanco;
import br.com.sgo.modelo.ParceiroLocalidade;
import br.com.sgo.modelo.ParceiroNegocio;

@Resource
public class FormularioController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final ParceiroNegocioDao parceiroNegocioDao;
	private final ParceiroBeneficioDao parceiroBeneficioDao;
	private final ParceiroLocalidadeDao parceiroLocalidadeDao;
	private final BancoDao bancoDao;
	private final ProdutoDao produtoDao;

	private Formulario formulario;
	private ParceiroNegocio parceiroNegocio;
	private ParceiroLocalidade parceiroLocalidade;
	private ParceiroInfoBanco parceiroInfoBanco;
	private ParceiroBeneficio parceiroBeneficio;
	private Collection<Banco> bancos;
	private List<Contrato> contratos;

	public FormularioController(Result result, UsuarioInfo usuarioInfo,ParceiroNegocioDao parceiroNegocioDao,
			ParceiroBeneficioDao parceiroBeneficioDao,ParceiroLocalidadeDao parceiroLocalidadeDao,ParceiroNegocio parceiroNegocio,ParceiroLocalidade parceiroLocalidade,
			ParceiroInfoBanco parceiroInfoBanco,ParceiroBeneficio parceiroBeneficio,Formulario formulario,BancoDao bancoDao,ProdutoDao produtoDao,List<Contrato> contratos){		

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.parceiroNegocioDao = parceiroNegocioDao;
		this.parceiroBeneficio = parceiroBeneficio;
		this.parceiroBeneficioDao =  parceiroBeneficioDao;
		this.parceiroLocalidadeDao = parceiroLocalidadeDao;
		this.bancoDao = bancoDao;
		this.produtoDao = produtoDao;
		this.formulario = formulario;
		this.parceiroNegocio = parceiroNegocio;
		this.parceiroLocalidade = parceiroLocalidade;
		this.parceiroInfoBanco = parceiroInfoBanco;
		this.parceiroBeneficio = parceiroBeneficio;
		this.contratos = contratos;

	}

	@Get
	@Path("/formulario/cadastro")
	public void cadastro(){

		bancos = bancoDao.listaTudo();

		result.include("bancos",bancos);
		result.include("contratos",contratos);
		result.include("formulario",formulario);
		result.include("parceiroLocalidade",parceiroLocalidade);
		result.include("parceiroBeneficio",parceiroBeneficio);

		
	}
	
	@Post
	@Path("/formulario/cliente")
	public void cliente(String numeroBeneficio){

		ParceiroBeneficio pb = parceiroBeneficioDao.buscaParceiroBeneficioByNumeroBeneficio(numeroBeneficio);

		parceiroBeneficio.setParceiroBeneficio_id(pb.getParceiroBeneficio_id());
		parceiroBeneficio.setNumeroBeneficio(pb.getNumeroBeneficio());
		parceiroBeneficio.setParceiroNegocio(pb.getParceiroNegocio());

		parceiroNegocio = parceiroNegocioDao.load(parceiroBeneficio.getParceiroNegocio().getParceiroNegocio_id());

		for(ParceiroLocalidade pl : parceiroLocalidadeDao.buscaParceiroLocalidades(parceiroNegocio.getParceiroNegocio_id())){

			if(pl.getTipoEndereco().getNome().equals("Assinatura"))
				parceiroLocalidade.setLocalidade(pl.getLocalidade());
				parceiroLocalidade.setNumero(pl.getNumero());

		}

		parceiroLocalidade.setParceiroNegocio(parceiroNegocio);
		formulario.setParceiroNegocio(parceiroNegocio);

		result.redirectTo(this).cadastro();

	}
	
	@Post
	@Path("/formulario/limpar")
	public void limpar() {

		this.formulario.setFormulario_id(null);
		this.formulario.setParceiroNegocio(null);
		this.parceiroBeneficio.setParceiroBeneficio_id(null);
		this.parceiroBeneficio.setNumeroBeneficio(null);
		this.parceiroLocalidade.setParceiroLocalidade_id(null);
		this.parceiroLocalidade.setLocalidade(null);
		this.contratos.clear();

		result.redirectTo(this).cadastro();
		result.nothing();

	}
	
	@Post
	@Path("/formulario/adicionaContrato")
	public void adicionaContrato(Contrato contrato) {

		contrato.setBanco(this.bancoDao.load(contrato.getBanco().getBanco_id()));
		contrato.setProduto(this.produtoDao.load(contrato.getProduto().getProduto_id()));

		formulario.adicionaContrato(contrato);
		result.redirectTo(FormularioController.class).cadastro();

	}

}