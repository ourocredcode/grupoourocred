package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.GrupoParceiroDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.GrupoParceiro;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Usuario;

@Resource
public class GrupoparceiroController {

	private final Result result;
	private final GrupoParceiroDao grupoParceiroDao;
	private UsuarioInfo usuarioInfo;	
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	private Calendar dataAtual = Calendar.getInstance();

	public GrupoparceiroController(Result result, GrupoParceiroDao grupoParceiroDao, UsuarioInfo usuarioInfo, Empresa empresa, Organizacao organizacao, Usuario usuario){

		this.result = result;
		this.grupoParceiroDao = grupoParceiroDao;
		this.usuarioInfo = usuarioInfo;				
		this.empresa = empresa;
		this.organizacao = organizacao;
		this.usuario = this.usuarioInfo.getUsuario();

	}	

	@Get
	@Path("/grupoparceiro/cadastro")
	public void cadastro(){

		result.include("gruposParceiro", this.grupoParceiroDao.buscaAllGrupoParceiroByEmpOrg(1l, 1l));

	}

	@Post
	@Path("/grupoparceiro/salva")
	public void salva(GrupoParceiro grupoParceiro){
		
		String mensagem = "";

		try {

			if (this.grupoParceiroDao.buscaGrupoParceiroByEmpOrgNome(1l, 1l, grupoParceiro.getNome()) == null) {				

				grupoParceiro.setCreated(dataAtual);
				grupoParceiro.setUpdated(dataAtual);

				grupoParceiro.setCreatedBy(usuario);
				grupoParceiro.setUpdatedBy(usuario);

				grupoParceiro.setChave(grupoParceiro.getNome());
				grupoParceiro.setDescricao(grupoParceiro.getNome());

				empresa.setEmpresa_id(1l);
				organizacao.setOrganizacao_id(1l);

				grupoParceiro.setEmpresa(empresa);
				grupoParceiro.setOrganizacao(organizacao);

				grupoParceiro.setIsActive(grupoParceiro.getIsActive() == null ? false : true);

				this.grupoParceiroDao.beginTransaction();
				this.grupoParceiroDao.adiciona(grupoParceiro);
				this.grupoParceiroDao.commit();

				mensagem = "Grupo parceiro " + grupoParceiro.getNome() + " adicionado com sucesso.";


			} else {

				mensagem = "Erro: Grupo parceiro já cadastrado.";

			}

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar o Grupo parceiro " + grupoParceiro.getNome() + ".";

		} finally{

			this.grupoParceiroDao.clear();
			this.grupoParceiroDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Get
	@Path("/grupoparceiro/busca.json")
	public void grupoparceiro(Long empresa_id, Long organizacao_id, String nome){
		result.use(Results.json()).withoutRoot().from(grupoParceiroDao.buscaGrupoParceiroByEmpOrgNome(1l, 1l, nome)).serialize();
	}

}