package br.com.sgo.controller;

import java.util.Collection;

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
import br.com.sgo.dao.WorkflowEtapaDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Banco;
import br.com.sgo.modelo.Coeficiente;
import br.com.sgo.modelo.Contrato;
import br.com.sgo.modelo.Formulario;
import br.com.sgo.modelo.Logistica;
import br.com.sgo.modelo.Produto;
import br.com.sgo.modelo.WorkflowEtapa;

@Resource
public class LogisticaController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final BancoDao bancoDao;
	private final ProdutoBancoDao produtoBancoDao;
	private final ProdutoDao produtoDao;
	private final CoeficienteDao coeficienteDao;
	private final TabelaDao tabelaDao;
	private final ContratoDao contratoDao;
	private final FormularioDao formularioDao;
	private final WorkflowEtapaDao workFlowetapaDao;
	

	private Contrato contrato;
	private Formulario formulario;
	private Collection<Banco> bancos;
	private Collection<Produto> produtos;
	private Collection<Coeficiente> coeficientes;
	private Collection<WorkflowEtapa> etapas;

	public LogisticaController(Result result,BancoDao bancoDao,ProdutoBancoDao produtoBancoDao,ProdutoDao produtoDao,CoeficienteDao coeficienteDao,Contrato contrato,
			Formulario formulario,TabelaDao tabelaDao,ContratoDao contratoDao,FormularioDao formularioDao,WorkflowEtapaDao workFlowetapaDao,UsuarioInfo usuarioInfo){		

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.contrato = contrato;
		this.formulario = formulario;
		this.bancoDao = bancoDao;
		this.contratoDao = contratoDao;
		this.produtoBancoDao = produtoBancoDao;
		this.produtoDao = produtoDao;
		this.coeficienteDao = coeficienteDao;
		this.formularioDao = formularioDao;
		this.tabelaDao = tabelaDao;
		this.workFlowetapaDao = workFlowetapaDao;

	}

	@Post
	@Path("/logistica/cadastro")
	public void cadastro(){

	}

	@Post
	@Path("/logistica/salva")
	public void salva(Logistica logistica) {

		System.out.println(logistica.getDataAssinatura().getTime());
		System.out.println(logistica.getTipoLogistica().getNome());
		System.out.println(logistica.getPeriodo());

	}

}