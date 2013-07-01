package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.TipoControleDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.TipoControle;
import br.com.sgo.modelo.Usuario;

@Resource
public class TipocontroleController {

	private final Result result;
	private final TipoControleDao tipoControleDao;

	private TipoControle tipoControle;
	private UsuarioInfo usuarioInfo;	
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	private Calendar dataAtual = Calendar.getInstance();	
	
	public TipocontroleController(Result result, UsuarioInfo usuarioInfo, TipoControle tipoControle, Empresa empresa, Organizacao organizacao, Usuario usuario, TipoControleDao tipoControleDao){

		this.result = result;
		this.tipoControle= tipoControle;
		this.tipoControleDao = tipoControleDao;
		this.usuarioInfo = usuarioInfo;				
		this.empresa = empresa;
		this.organizacao = organizacao;
		this.usuario = this.usuarioInfo.getUsuario();

	}	

	@Get
	@Path("/tipocontrole/cadastro")
	public void cadastro(){

		result.include("tiposControle", this.tipoControleDao.buscaAllTipoControleByEmpOrg(1L, 1L));

	}

	@Post
	@Path("/tipocontrole/salva")
	public void salva(TipoControle tipoControle){

		String mensagem = "";

		try {

			if (this.tipoControleDao.buscaTipoControleByEmpOrgNome(1L, 1L, tipoControle.getNome()) == null) {				

				tipoControle.setCreated(dataAtual);
				tipoControle.setUpdated(dataAtual);

				tipoControle.setCreatedBy(usuario);
				tipoControle.setUpdatedBy(usuario);

				tipoControle.setChave(tipoControle.getNome());
				tipoControle.setDescricao(tipoControle.getNome());

				empresa.setEmpresa_id(1l);
				organizacao.setOrganizacao_id(1l);
				
				tipoControle.setEmpresa(empresa);
				tipoControle.setOrganizacao(organizacao);

				tipoControle.setIsActive(tipoControle.getIsActive() == null || false ? false : true);

				this.tipoControleDao.beginTransaction();
				this.tipoControleDao.adiciona(tipoControle);
				this.tipoControleDao.commit();

				mensagem = "tipoControle " + tipoControle.getNome() + " adicionado com sucesso.";


			} else {

				mensagem = "Erro: tipoControle já cadastrado.";

			}

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar o tipoControle " + tipoControle.getNome() + ".";

		} finally{

			this.tipoControleDao.clear();
			this.tipoControleDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Post
	@Path("/tipocontrole/altera")
	public void altera(TipoControle tipoControle) {

		String mensagem = "";

		this.tipoControle = this.tipoControleDao.load(tipoControle.getTipoControle_id());

		this.tipoControle.setUpdated(dataAtual);
		this.tipoControle.setUpdatedBy(usuario);

		if(tipoControle.getIsActive() != null){
			this.tipoControle.setIsActive(tipoControle.getIsActive() == false ? false : true);
		}

		tipoControleDao.beginTransaction();		
		tipoControleDao.atualiza(this.tipoControle);
		tipoControleDao.commit();

		mensagem = " Tipo Controle alterado com sucesso.";

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();
		
	}

	@Get 
	@Path("/tipocontrole/busca.json")
	public void tipoControle(Long empresa_id, Long organizacao_id, String nome){

		result.use(Results.json()).withoutRoot().from(tipoControleDao.buscaTiposControleByEmpOrgNome(empresa_id, organizacao_id, nome)).serialize();

	}

	@Get
	public void msg() {

	}

}