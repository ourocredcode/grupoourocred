package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.ConvenioDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Convenio;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Usuario;

@Resource
public class ConvenioController {

	private final Result result;
	private final ConvenioDao convenioDao;

	private UsuarioInfo usuarioInfo;	
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;
	private Convenio convenio;
	
	private Calendar dataAtual = Calendar.getInstance();	
	
	public ConvenioController(Result result, UsuarioInfo usuarioInfo,Empresa empresa, Organizacao organizacao, Usuario usuario, ConvenioDao convenioDao){

		this.result = result;
		this.convenioDao = convenioDao;
		this.usuarioInfo = usuarioInfo;				
		this.empresa = empresa;
		this.organizacao = organizacao;
		this.usuario = this.usuarioInfo.getUsuario();

	}	

	@Get
	@Path("/convenio/cadastro")
	public void cadastro(){

		result.include("convenios", this.convenioDao.buscaAllConvenio(1l, 1l));

	}

	@Post
	@Path("/convenio/salva")
	public void salva(Convenio convenio){

		String mensagem = "";

		try {

			if (this.convenioDao.buscaConvenioByEmpOrgNome(1l, 1l, convenio.getNome()) == null) {				

				convenio.setCreated(dataAtual);
				convenio.setUpdated(dataAtual);

				convenio.setCreatedBy(usuario);
				convenio.setUpdatedBy(usuario);

				convenio.setChave(convenio.getNome());
				convenio.setDescricao(convenio.getNome());

				empresa.setEmpresa_id(1l);
				organizacao.setOrganizacao_id(1l);

				convenio.setEmpresa(empresa);
				convenio.setOrganizacao(organizacao);

				convenio.setIsActive(convenio.getIsActive() == null ? false : true);

				this.convenioDao.beginTransaction();
				this.convenioDao.adiciona(convenio);
				this.convenioDao.commit();

				mensagem = "Convênio " + convenio.getNome() + " adicionado com sucesso.";


			} else {

				mensagem = "Erro: Convênio já cadastrado.";

			}

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar o Convênio " + convenio.getNome() + ".";

		} finally {

			this.convenioDao.clear();
			this.convenioDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}
	
	@Post
	@Path("/convenio/altera")
	public void altera(Convenio convenio) {

		String mensagem = "";

		this.convenio = this.convenioDao.load(convenio.getConvenio_id());

		this.convenio.setUpdated(dataAtual);
		this.convenio.setUpdatedBy(usuario);

		this.convenio.setIsActive(convenio.getIsActive() == null || convenio.getIsActive() == false ? false : true);

		convenioDao.beginTransaction();		
		convenioDao.atualiza(this.convenio);
		convenioDao.commit();

		mensagem = " Convênio adicionado com sucesso.";

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();
		
	}

	@Get 
	@Path("/convenio/busca.json")
	public void convenio(Long empresa_id, Long organizacao_id, String nome){

		result.use(Results.json()).withoutRoot().from(convenioDao.buscaConveniosByEmpOrgNome(empresa_id, organizacao_id, nome)).serialize();

	}

	@Get
	public void msg() {

	}

}