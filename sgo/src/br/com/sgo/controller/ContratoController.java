package br.com.sgo.controller;

import java.util.Collection;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.BancoDao;
import br.com.sgo.dao.CoeficienteDao;
import br.com.sgo.dao.ContratoDao;
import br.com.sgo.dao.FormularioDao;
import br.com.sgo.dao.ProdutoBancoDao;
import br.com.sgo.dao.ProdutoDao;
import br.com.sgo.dao.TabelaDao;
import br.com.sgo.modelo.Banco;
import br.com.sgo.modelo.Coeficiente;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Formulario;
import br.com.sgo.modelo.Produto;
import br.com.sgo.modelo.Tabela;

@Resource
public class ContratoController {

	private final Result result;
	private final BancoDao bancoDao;
	private final ProdutoBancoDao produtoBancoDao;
	private final ProdutoDao produtoDao;
	private final CoeficienteDao coeficienteDao;
	private final TabelaDao tabelaDao;
	private final ContratoDao contratoDao;
	private final FormularioDao formularioDao;

	private Contrato contrato;
	private Formulario formulario;
	private Collection<Banco> bancos;
	private Collection<Produto> produtos;
	private Collection<Coeficiente> coeficientes;

	public ContratoController(Result result,BancoDao bancoDao,ProdutoBancoDao produtoBancoDao,ProdutoDao produtoDao,CoeficienteDao coeficienteDao,Contrato contrato,
			Formulario formulario,TabelaDao tabelaDao,ContratoDao contratoDao,FormularioDao formularioDao){		

		this.result = result;
		this.contrato = contrato;
		this.formulario = formulario;
		this.bancoDao = bancoDao;
		this.contratoDao = contratoDao;
		this.produtoBancoDao = produtoBancoDao;
		this.produtoDao = produtoDao;
		this.coeficienteDao = coeficienteDao;
		this.formularioDao = formularioDao;
		this.tabelaDao = tabelaDao;

	}

	@Post
	@Path("/contrato/cadastro")
	public void cadastro(Long id){

	}
	
	@Get
 	@Path("/contrato/status/{id}")
	public void status(Long id){

		formulario = formularioDao.load(id);
		formulario.setContratos(this.contratoDao.buscaContratoByFormulario(formulario.getFormulario_id()));

		result.include("formulario",formulario);

	}
	
	@Post
	@Path("/contrato/produtos")
	public void produtos(Long banco_id) {

		produtos = banco_id == null ? null : produtoDao.buscaProdutosByBanco(banco_id);
		result.include("produtos",produtos);

	}
	
	@Post
 	@Path("/contrato/coeficientes")
	public void coeficientes(Long banco_id,Long produto_id) {

		coeficientes = produto_id == null ? null : this.coeficienteDao.buscaCoeficientesByBancoProduto(banco_id,produto_id);
		result.include("coeficientes",coeficientes);

	}
	
	@Post
 	@Path("/contrato/prazo")
	public void prazo(Long coeficiente_id) {

		Tabela t = tabelaDao.buscaTabelasByCoeficiente(coeficiente_id);

		contrato.setPrazo(t.getPrazo());

		result.include("contrato",contrato);

	}
	
	@Post
	@Path("/contrato/salva")
	public void salva(Formulario formulario) {	

	}

}