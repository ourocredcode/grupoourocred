package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.ModeloProcedimentoDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.ModeloProcedimento;
import br.com.sgo.modelo.Usuario;

@Resource
public class ModeloprocedimentoController {

	private final Result result;
	private final ModeloProcedimentoDao modeloProcedimentoDao;

	private ModeloProcedimento modeloProcedimento;
	private UsuarioInfo usuarioInfo;	
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	private Calendar dataAtual = Calendar.getInstance();	
	
	public ModeloprocedimentoController(Result result, UsuarioInfo usuarioInfo, ModeloProcedimento modeloProcedimento, Empresa empresa, Organizacao organizacao, Usuario usuario, ModeloProcedimentoDao modeloProcedimentoDao){

		this.result = result;
		this.modeloProcedimento= modeloProcedimento;
		this.modeloProcedimentoDao = modeloProcedimentoDao;
		this.usuarioInfo = usuarioInfo;				
		this.empresa = empresa;
		this.organizacao = organizacao;
		this.usuario = this.usuarioInfo.getUsuario();

	}	

	@Get
	@Path("/modeloprocedimento/cadastro")
	public void cadastro(){

		result.include("modelosProcedimento", this.modeloProcedimentoDao.buscaAllModeloProcedimentoByEmpOrg(1L, 1L));

	}

	@Post
	@Path("/modeloprocedimento/salva")
	public void salva(ModeloProcedimento modeloProcedimento){

		String mensagem = "";

		try {

			if (this.modeloProcedimentoDao.buscaModeloProcedimentoByEmpOrgId(1L, 1L, modeloProcedimento.getNome()) == null) {				

				modeloProcedimento.setCreated(dataAtual);
				modeloProcedimento.setUpdated(dataAtual);

				modeloProcedimento.setCreatedBy(usuario);
				modeloProcedimento.setUpdatedBy(usuario);

				modeloProcedimento.setChave(modeloProcedimento.getNome());
				modeloProcedimento.setDescricao(modeloProcedimento.getNome());

				empresa.setEmpresa_id(1l);
				organizacao.setOrganizacao_id(1l);
				
				modeloProcedimento.setEmpresa(empresa);
				modeloProcedimento.setOrganizacao(organizacao);

				modeloProcedimento.setIsActive(modeloProcedimento.getIsActive() == null || false ? false : true);

				this.modeloProcedimentoDao.beginTransaction();
				this.modeloProcedimentoDao.adiciona(modeloProcedimento);
				this.modeloProcedimentoDao.commit();

				mensagem = "modeloProcedimento " + modeloProcedimento.getNome() + " adicionado com sucesso.";


			} else {

				mensagem = "Erro: Modelo Procedimento já cadastrado.";

			}

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar o Modelo Procedimento " + modeloProcedimento.getNome() + ".";

		} finally{

			this.modeloProcedimentoDao.clear();
			this.modeloProcedimentoDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Post
	@Path("/modeloprocedimento/altera")
	public void altera(ModeloProcedimento modeloProcedimento) {

		String mensagem = "";

		this.modeloProcedimento = this.modeloProcedimentoDao.load(modeloProcedimento.getModeloProcedimento_id());

		this.modeloProcedimento.setUpdated(dataAtual);
		this.modeloProcedimento.setUpdatedBy(usuario);

		if(modeloProcedimento.getIsActive() != null){
			this.modeloProcedimento.setIsActive(modeloProcedimento.getIsActive() == false ? false : true);
		}

		modeloProcedimentoDao.beginTransaction();		
		modeloProcedimentoDao.atualiza(this.modeloProcedimento);
		modeloProcedimentoDao.commit();

		mensagem = " Modelo Procedimento alterado com sucesso.";

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();
		
	}

	@Get 
	@Path("/modeloprocedimento/busca.json")
	public void modeloProcedimento(Long empresa_id, Long organizacao_id, String nome){

		result.use(Results.json()).withoutRoot().from(modeloProcedimentoDao.buscaModeloProcedimentos(empresa_id, organizacao_id, nome)).serialize();

	}

	@Get
	public void msg() {

	}

}