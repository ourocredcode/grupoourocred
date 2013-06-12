package br.com.sgo.controller;

import java.util.Calendar;
import java.util.Collection;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.TabelaDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Tabela;
import br.com.sgo.modelo.Usuario;

@Resource
public class TabelaController {

	private final Result result;
	private final TabelaDao tabelaDao;
	private Collection<Tabela> tabelas;
	
	private UsuarioInfo usuarioInfo;	
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	private Calendar dataAtual = Calendar.getInstance();

	public TabelaController(Result result,TabelaDao tabelaDao, UsuarioInfo usuarioInfo, Empresa empresa, Organizacao organizacao, Usuario usuario){

		this.tabelaDao = tabelaDao;
		this.result = result;
		this.usuarioInfo = usuarioInfo;				
		this.empresa = this.usuarioInfo.getEmpresa();
		this.organizacao = this.usuarioInfo.getOrganizacao();
		this.usuario = this.usuarioInfo.getUsuario();

	}	

	@Get
	@Path("/tabela/cadastro")
	public void cadastro(){

		//result.include("tiposTabela", this.tabelaDao.buscaAllTabela(usuarioInfo.getEmpresa().getEmpresa_id(), usuarioInfo.getOrganizacao().getOrganizacao_id()));

	}

	@Post
	@Path("/tabela/salva")
	public void salva(Tabela tabela){
		
		String mensagem = "";


			try {
				
				if(empresa.getNome().equals("SYSTEM") && organizacao.getNome().equals("SYSTEM")){

					//if (this.tabelaDao.buscaTipoTabelaByEmpOrgNome(1l, 1l, tabela.getNome()) == null) {				
		
						tabela.setCreated(dataAtual);
						tabela.setUpdated(dataAtual);
		
						tabela.setCreatedBy(usuario);
						tabela.setUpdatedBy(usuario);
		
						tabela.setChave(tabela.getNome());
						tabela.setDescricao(tabela.getNome());
						
						tabela.setIsActive(tabela.getIsActive() == null ? false : true);
		
						this.tabelaDao.beginTransaction();
						this.tabelaDao.adiciona(tabela);
						this.tabelaDao.commit();
		
						mensagem = "Tabela adicionado com sucesso.";
		
					//} else {

					//	mensagem = "Erro: Tipo Tabela " + tabela.getNome() + " já cadastrado.";

					//}

				} else {
					
					mensagem = "Erro: Tipo Tabela não pode ser cadastrado nesta empresa..";

				}

			} catch (Exception e) {

				mensagem = "Erro: Falha ao adicionar o Tipo Tabela " + tabela.getNome() + ".";

			} finally{

				this.tabelaDao.clear();
				this.tabelaDao.close();

			}

			result.include("notice", mensagem);			
			result.redirectTo(this).cadastro();

	}
	
	@Post
	@Path("/tabela/tabelas")
	public void tabelas(Long bancoId) {

		tabelas = tabelaDao.buscaTabelasByBanco(bancoId);
		result.include("tabelas",tabelas);

	}
	
	@Get @Path("/tabela/busca.json")
	@Public
	public void tabelas(Long empresa_id, Long organizacao_id, String nome){
		result.use(Results.json()).withoutRoot().from(tabelaDao.buscaTabelas(empresa_id, organizacao_id, nome)).serialize();
	}

}