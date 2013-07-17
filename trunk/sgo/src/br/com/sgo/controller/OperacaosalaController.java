package br.com.sgo.controller;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.OperacaoDao;
import br.com.sgo.dao.SalaDao;
import br.com.sgo.dao.OperacaoSalaDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Usuario;
import br.com.sgo.modelo.OperacaoSala;

@Resource
public class OperacaosalaController {

	private final Result result;
	private final UsuarioInfo usuarioInfo;
	private final OperacaoSalaDao operacaoSalaDao;
	private final SalaDao salaDao;
	private final OperacaoDao operacaoDao;

	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	Calendar dataAtual = GregorianCalendar.getInstance();

	public OperacaosalaController(Result result,  UsuarioInfo usuarioInfo, OperacaoSalaDao operacaoSalaDao, SalaDao salaDao, OperacaoDao operacaoDao
			,Empresa empresa,Organizacao organizacao,Usuario usuario) {

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.operacaoSalaDao =  operacaoSalaDao;
		this.salaDao = salaDao;
		this.operacaoDao = operacaoDao;
		this.empresa = this.usuarioInfo.getEmpresa();
		this.organizacao = this.usuarioInfo.getOrganizacao();
		this.usuario = this.usuarioInfo.getUsuario();

	}

	@Get
	@Path("/operacaosala/cadastro")
	public void cadastro() {

		result.include("operacoes", this.operacaoDao.buscaAllOperacao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
		result.include("salas", this.salaDao.buscaAllSala(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));		
		result.include("operacaoSalas", this.operacaoSalaDao.buscaAllOperacaoSalaByEmpresaOrganizacao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));

	}

	@Post
	@Path("/operacaosala/salva")
	public void salva(OperacaoSala operacaoSala) {

		String mensagem = "";

		try {


			if (this.operacaoSalaDao.buscaOperacaoSalaByEmpresaOrganizacaoOperacaoSala(empresa.getEmpresa_id(),organizacao.getOrganizacao_id(),
					operacaoSala.getSala().getSala_id(), operacaoSala.getOperacao().getOperacao_id()) == null) {				

				operacaoSala.setCreated(dataAtual);
				operacaoSala.setUpdated(dataAtual);

				operacaoSala.setCreatedBy(usuario);
				operacaoSala.setUpdatedBy(usuario);
				
				operacaoSala.setIsActive(operacaoSala.getIsActive() == null || operacaoSala.getIsActive() == false ? false : true);

				this.operacaoSalaDao.insert(operacaoSala);

				mensagem = "Operacao adicionado com sucesso para o Sala ";

			} else {
				
				mensagem = "Erro: Operacao já cadastrado para o Sala ";
				
			} 

		} catch (SQLException e) {

			mensagem = "Erro: ao adicionar o Operacao Sala :";

		} finally{

			this.operacaoSalaDao.clear();
			this.operacaoSalaDao.close();

		}
		
		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();
	}
	
	@Post
	@Path("/operacaosala/altera")
	public void altera(OperacaoSala operacaoSala) {

		operacaoSala = this.operacaoSalaDao.load(operacaoSala.getEmpresa().getEmpresa_id());
		operacaoSala = this.operacaoSalaDao.load(operacaoSala.getOrganizacao().getOrganizacao_id());
		operacaoSala = this.operacaoSalaDao.load(operacaoSala.getSala().getSala_id());
		operacaoSala = this.operacaoSalaDao.load(operacaoSala.getOperacao().getOperacao_id());

		String mensagem = "";

		try {

			operacaoSala.setUpdated(dataAtual);
			operacaoSala.setUpdatedBy(usuario);

			operacaoSala.setIsActive(operacaoSala.getIsActive() == null ? false : true);

			this.operacaoSalaDao.insert(operacaoSala);

			mensagem = "Operacao alterado com sucesso.";

		} catch (SQLException e) {

			mensagem = "Erro: ao alterar o Operacao Sala :";

		} finally {

			this.operacaoSalaDao.clear();
			this.operacaoSalaDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Get
	public void msg() {

	}
}