package br.com.sgo.controller;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashSet;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.BancoDao;
import br.com.sgo.dao.CoeficienteDao;
import br.com.sgo.dao.ProdutoDao;
import br.com.sgo.dao.TabelaDao;
import br.com.sgo.infra.CustomDateUtil;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Banco;
import br.com.sgo.modelo.Coeficiente;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Usuario;

@Resource
public class CoeficienteController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final BancoDao bancoDao;
	private final ProdutoDao produtoDao;
	private final TabelaDao tabelaDao;
	private final CoeficienteDao coeficienteDao;
	private  Coeficiente coeficiente;
	private Collection<Coeficiente> coeficientes;
	private Collection<Banco> bancos;
	
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	private Calendar dataAtual = Calendar.getInstance();

	public CoeficienteController(Result result,UsuarioInfo usuarioInfo,BancoDao bancoDao, CoeficienteDao coeficienteDao,TabelaDao tabelaDao, ProdutoDao produtoDao, Coeficiente coeficiente,
			Empresa empresa, Organizacao organizacao, Usuario usuario){

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.coeficienteDao = coeficienteDao;
		this.coeficiente = coeficiente;
		this.tabelaDao = tabelaDao;
		this.bancoDao = bancoDao;
		this.produtoDao = produtoDao;
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();
		this.usuario = this.usuarioInfo.getUsuario();

	}

	@Get
	public void cadastro(){

		coeficientes = coeficienteDao.buscaAllCoeficientesByEmpOrg(empresa.getEmpresa_id(), organizacao.getOrganizacao_id());

		bancos = bancoDao.buscaBancosToBancoProdutoByEmpOrg(empresa.getEmpresa_id(), organizacao.getOrganizacao_id());

		result.include("coeficientes",coeficientes);
		result.include("bancos",bancos);

	}
	
	@Post
	@Path("/coeficiente/altera")
	public void altera(Coeficiente coeficiente){

		this.coeficiente = this.coeficienteDao.load(coeficiente.getCoeficiente_id());

		if (coeficiente.getPercentualMeta() != null)
			this.coeficiente.setPercentualMeta(coeficiente.getPercentualMeta());
		else if (coeficiente.getValor() != null)
			this.coeficiente.setValor(coeficiente.getValor());
		else if (coeficiente.getTabela().getTabela_id() != null)
			this.coeficiente.setTabela(tabelaDao.load(coeficiente.getTabela().getTabela_id()));

		coeficienteDao.beginTransaction();
		coeficienteDao.atualiza(this.coeficiente);
		coeficienteDao.commit();

		result.include("msg","Atualizado com Sucesso.").redirectTo(this).msg();

	}

	@Post
	@Path("/coeficiente/exclui")
	public void exclui(Coeficiente coeficiente) {

		this.coeficiente = this.coeficienteDao.load(coeficiente.getCoeficiente_id());
		this.coeficiente.setUpdated(CustomDateUtil.getTimeAtual());

		this.coeficiente.setIsActive(coeficiente.getIsActive() == null ? false : true);

		coeficienteDao.beginTransaction();
		coeficienteDao.atualiza(this.coeficiente);
		coeficienteDao.commit();

		result.include("msg","Registro removido com sucesso").forwardTo(this).cadastro();

	}

	@Post
	@Path("/coeficiente/adiciona")
	public void adiciona(Coeficiente coeficiente) {

		coeficiente.setTabela(tabelaDao.load(coeficiente.getTabela().getTabela_id()));
		coeficiente.setBanco(bancoDao.load(coeficiente.getBanco().getBanco_id()));
		
		coeficiente.setCreated(dataAtual);
		coeficiente.setUpdated(dataAtual);

		coeficiente.setCreatedBy(usuario);
		coeficiente.setUpdatedBy(usuario);
		
		this.coeficiente.setIsActive(coeficiente.getIsActive() == null ? false : true);

		coeficienteDao.beginTransaction();
		coeficienteDao.atualiza(coeficiente);
		coeficienteDao.commit();

		result.redirectTo(this).listaAdd(coeficiente.getTabela().getTabela_id());

	}

	@Post
	@Path("/coeficiente/historicoAdiciona")
	public void historicoAdiciona(Coeficiente coeficiente) {

		Long tabelaId = coeficiente.getTabela().getTabela_id();

		coeficiente.setTabela(tabelaDao.load(tabelaId));
		coeficiente.setCreated(CustomDateUtil.getTimeAtual());
		coeficiente.setUpdated(CustomDateUtil.getTimeAtual());

		coeficienteDao.beginTransaction();
		coeficienteDao.adiciona(coeficiente);
		coeficienteDao.commit();

		result.include("msg",coeficiente.getCoeficiente_id()).redirectTo(this).msg();

	}

	@Post
	@Path("/coeficiente/historico")
	public void historico(Long banco_id, Long produto_id) {

		Collection<Calendar> datas = this.coeficienteDao.buscaDatasInclusao(banco_id, produto_id);
		Collection<Integer> anos = new HashSet<Integer>();
		Collection<Integer> meses = new HashSet<Integer>();

		for (Calendar ano : datas){
			anos.add(ano.get(Calendar.YEAR));
		}

		for (Calendar mes : datas){
			meses.add(mes.get(Calendar.MONTH));
		}

		result.include("anos",anos);
		result.include("meses",meses);
		result.include("bancoNome",this.bancoDao.load(banco_id).getNome());
		result.include("produtoNome",this.produtoDao.load(produto_id).getNome());

	}
	
	@Post
	@Path("/coeficiente/cadastroAux")
	public void cadastroAux(Long banco_id, Long produto_id) {

		Collection<Calendar> datas = this.coeficienteDao.buscaDatasInclusao(banco_id, produto_id);
		Collection<Integer> anos = new HashSet<Integer>();
		Collection<Integer> meses = new HashSet<Integer>();
		//Collection<Tabela> tabelas = this.tabelaDao.buscaTabelasBanco(bancoNome, produtoNome);

		for (Calendar ano : datas){
			anos.add(ano.get(Calendar.YEAR));
		}

		for (Calendar mes : datas){
			meses.add(mes.get(Calendar.MONTH));
		}

		result.include("anos",anos);
		result.include("meses",meses);
		result.include("bancoNome",this.bancoDao.load(banco_id).getNome());
		result.include("produtoNome",this.produtoDao.load(produto_id).getNome());
		//result.include("tabelas",tabelas);

	}

	@Post
	@Path("/coeficiente/buscaCoeficienteHistorico")
	public void resultadoHistorico(Integer ano,Integer mes,String bancoNome,String produtoNome) {

		Calendar inicio = new GregorianCalendar();
		Calendar fim = new GregorianCalendar();

		inicio.set(Calendar.YEAR,ano);
		inicio.set(Calendar.MONTH, mes);
		inicio.set(Calendar.DAY_OF_MONTH,inicio.getActualMinimum(Calendar.DAY_OF_MONTH));
		inicio.set(Calendar.HOUR_OF_DAY, inicio.getActualMinimum(Calendar.HOUR_OF_DAY));
		inicio.set(Calendar.MINUTE, inicio.getActualMinimum(Calendar.MINUTE));
		inicio.set(Calendar.SECOND, inicio.getActualMinimum(Calendar.SECOND));

		fim.set(Calendar.YEAR,ano);
		fim.set(Calendar.MONTH, mes);
		fim.set(Calendar.DAY_OF_MONTH,fim.getActualMaximum(Calendar.DAY_OF_MONTH));
		fim.set(Calendar.HOUR_OF_DAY, fim.getActualMaximum(Calendar.HOUR_OF_DAY));
		fim.set(Calendar.MINUTE, fim.getActualMaximum(Calendar.MINUTE));
		fim.set(Calendar.SECOND, fim.getActualMaximum(Calendar.SECOND));

		coeficientes = coeficienteDao.buscaCoeficientesExcluidos(bancoNome, produtoNome, inicio, fim);

		result.include("coeficientes",coeficientes);

	}

	@Post
	@Path("/coeficiente/listaporbancos")
	public void listaporbancos(Long bancoId) {

		coeficientes = coeficienteDao.buscaCoeficientesByBanco(bancoId);
		result.include("coeficientes",coeficientes).redirectTo(this).lista();

	}

	@Post
	@Path("/coeficiente/listaporprodutos")
	public void listaporprodutos(Long bancoId,Long produtoId) {

		coeficientes = coeficienteDao.buscaCoeficientesByBancoProduto(bancoId,produtoId);
		result.include("coeficientes",coeficientes).redirectTo(this).lista();

	}
	
	@Post
	@Path("/coeficiente/listaportabelas")
	public void listaportabelas(Long bancoId,Long tabelaId) {

		coeficientes = coeficienteDao.buscaCoeficientesByBancoTabela(bancoId,tabelaId);
		result.include("coeficientes",coeficientes).redirectTo(this).lista();

	}
	
	@Get
	public void lista(){
		
	}
	
	@Get
	public void listaAdd(Long tabelaId) {

		coeficientes = coeficienteDao.buscaCoeficientesByTabela(tabelaId);

		result.include("coeficientes",coeficientes);

	}

	@Get
	public void msg(){

	}
	
	@Post
	@Path("/coeficiente/limpar")
	public void limpar() {

		result.redirectTo(this).cadastro();

	}
}
