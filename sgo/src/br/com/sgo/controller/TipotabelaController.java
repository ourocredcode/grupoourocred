package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.TipoTabelaDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.TipoTabela;
import br.com.sgo.modelo.Usuario;

@Resource
public class TipotabelaController {

	private final Result result;
	private final TipoTabelaDao tipoTabelaDao;

	private UsuarioInfo usuarioInfo;	
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	private Calendar dataAtual = Calendar.getInstance();	
	
	public TipotabelaController(Result result, UsuarioInfo usuarioInfo,Empresa empresa, Organizacao organizacao, Usuario usuario, TipoTabelaDao tipoTabelaDao){

		this.result = result;
		this.tipoTabelaDao = tipoTabelaDao;
		this.usuarioInfo = usuarioInfo;				
		this.empresa = empresa;
		this.organizacao = organizacao;
		this.usuario = this.usuarioInfo.getUsuario();

	}	

	@Get
	@Path("/tipotabela/cadastro")
	public void cadastro(){

		result.include("tiposTabela", this.tipoTabelaDao.buscaAllTipoTabela(1l, 1l));

	}

	@Post
	@Path("/tipotabela/salva")
	public void salva(TipoTabela tipoTabela){

		String mensagem = "";

		try {

			if (this.tipoTabelaDao.buscaTipoTabelaByEmpOrgNome(1l, 1l, tipoTabela.getNome()) == null) {				

				tipoTabela.setCreated(dataAtual);
				tipoTabela.setUpdated(dataAtual);

				tipoTabela.setCreatedBy(usuario);
				tipoTabela.setUpdatedBy(usuario);

				tipoTabela.setChave(tipoTabela.getNome());
				tipoTabela.setDescricao(tipoTabela.getNome());

				empresa.setEmpresa_id(1l);
				organizacao.setOrganizacao_id(1l);

				tipoTabela.setEmpresa(empresa);
				tipoTabela.setOrganizacao(organizacao);

				tipoTabela.setIsActive(tipoTabela.getIsActive() == null ? false : true);

				this.tipoTabelaDao.beginTransaction();
				this.tipoTabelaDao.adiciona(tipoTabela);
				this.tipoTabelaDao.commit();

				mensagem = "Tipo Tabela " + tipoTabela.getNome() + " adicionado com sucesso.";


			} else {

				mensagem = "Erro: Tipo Tabela já cadastrado.";

			}

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar o Tipo Tabela " + tipoTabela.getNome() + ".";

		} finally{

			this.tipoTabelaDao.clear();
			this.tipoTabelaDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Get 
	@Path("/tipotabela/busca.json")
	public void tipotabela(Long empresa_id, Long organizacao_id, String nome){

		result.use(Results.json()).withoutRoot().from(tipoTabelaDao.buscaTiposTabela(empresa_id, organizacao_id, nome)).serialize();

	}

	@Get
	public void msg() {

	}

}