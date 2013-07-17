package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.OperacaoDao;
import br.com.sgo.dao.OperacaoSalaDao;
import br.com.sgo.dao.SalaDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Operacao;
import br.com.sgo.modelo.OperacaoSala;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Sala;
import br.com.sgo.modelo.Usuario;

@Resource
public class OperacaoController {

	private final Result result;
	private final OperacaoDao operacaoDao;
	private final OperacaoSalaDao operacaoSalaDao;
	private final SalaDao salaDao;

	private Operacao operacao;
	private UsuarioInfo usuarioInfo;
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	private Calendar dataAtual = Calendar.getInstance();	
	
	public OperacaoController(Result result, UsuarioInfo usuarioInfo,Empresa empresa, Organizacao organizacao, Usuario usuario, OperacaoDao operacaoDao
			, OperacaoSalaDao operacaoSalaDao, SalaDao salaDao, Operacao operacao){

		this.result = result;
		this.operacao = operacao;
		this.operacaoDao = operacaoDao;
		this.operacaoSalaDao = operacaoSalaDao;
		this.salaDao = salaDao;
		this.usuarioInfo = usuarioInfo;
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();
		this.usuario = this.usuarioInfo.getUsuario();

	}	

	@Get
	@Path("/operacao/cadastro")
	public void cadastro(){

		result.include("operacoes", this.operacaoDao.buscaAllOperacao(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));
		result.include("salas", this.salaDao.buscaAllSala(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));

	}

	@Post
	@Path("/operacao/salva")
	public void salva(Operacao operacao, Sala sala){

		String mensagem = "";

		try {

			if (this.operacaoDao.buscaOperacaoByEmpOrgNome(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(), operacao.getNome()) == null) {				

				operacao.setCreated(dataAtual);
				operacao.setUpdated(dataAtual);

				operacao.setCreatedBy(usuario);
				operacao.setUpdatedBy(usuario);

				operacao.setChave(operacao.getNome());
				operacao.setDescricao(operacao.getNome());

				operacao.setEmpresa(empresa);
				operacao.setOrganizacao(organizacao);

				operacao.setIsActive(operacao.getIsActive() == null ? false : true);

				this.operacaoDao.beginTransaction();
				this.operacaoDao.adiciona(operacao);
				this.operacaoDao.commit();

					OperacaoSala os = new OperacaoSala();

					os.setEmpresa(operacao.getEmpresa());
					os.setOrganizacao(operacao.getOrganizacao());
					os.setSala(sala);
					os.setOperacao(operacao);
				
					os.setCreated(dataAtual);
					os.setUpdated(dataAtual);

					os.setCreatedBy(usuario);
					os.setUpdatedBy(usuario);

					os.setIsActive(operacao.getIsActive());

					if (this.operacaoSalaDao.buscaOperacaoSalaByEmpresaOrganizacaoOperacaoSala(empresa.getEmpresa_id(),organizacao.getOrganizacao_id(),
							os.getSala().getSala_id(), os.getOperacao().getOperacao_id()) == null) {						

						this.operacaoSalaDao.insert(os);

					} 

				mensagem = "Operacao " + operacao.getNome() + " adicionado com sucesso.";

			} else {

				mensagem = "Erro: Operacao já cadastrado para a sala.";

			}

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar o Operacao " + operacao.getNome() + ".";

		} finally{

			this.operacaoDao.clear();
			this.operacaoDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}
	
	@Post
	@Path("/operacao/altera")
	public void altera(Operacao operacao) {

		String mensagem = "";

		this.operacao = this.operacaoDao.load(operacao.getOperacao_id());

		this.operacao.setUpdated(dataAtual);
		this.operacao.setUpdatedBy(usuario);

		if(operacao.getIsActive() != null){
			this.operacao.setIsActive(operacao.getIsActive() == false ? false : true);
		}

		operacaoDao.beginTransaction();		
		operacaoDao.atualiza(this.operacao);
		operacaoDao.commit();

		mensagem = " Operação alterado com sucesso.";

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();
		
	}


	@Get 
	@Path("/operacao/busca.json")
	public void operacao(Long empresa_id, Long organizacao_id, String nome){

		result.use(Results.json()).withoutRoot().from(operacaoDao.buscaOperacoes(empresa_id, organizacao_id, nome)).serialize();

	}

	@Get
	public void msg() {

	}

}