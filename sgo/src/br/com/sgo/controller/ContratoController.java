package br.com.sgo.controller;

import java.util.Collection;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.BancoDao;
import br.com.sgo.dao.CoeficienteDao;
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

	private Contrato contrato;
	private Formulario formulario;
	private Collection<Banco> bancos;
	private Collection<Produto> produtos;
	private Collection<Coeficiente> coeficientes;

	public ContratoController(Result result,BancoDao bancoDao,ProdutoBancoDao produtoBancoDao,ProdutoDao produtoDao,CoeficienteDao coeficienteDao,Contrato contrato,
			Formulario formulario,TabelaDao tabelaDao){		

		this.result = result;
		this.contrato = contrato;
		this.formulario = formulario;
		this.bancoDao = bancoDao;
		this.produtoBancoDao = produtoBancoDao;
		this.produtoDao = produtoDao;
		this.coeficienteDao = coeficienteDao;
		this.tabelaDao = tabelaDao;

	}

	@Post
	@Path("/contrato/cadastro")
	public void cadastro(Long id){

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