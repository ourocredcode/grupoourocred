package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.MeioPagamentoDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.MeioPagamento;
import br.com.sgo.modelo.Usuario;

@Resource
public class MeiopagamentoController {

	private final Result result;
	private final MeioPagamentoDao meioPagamentoDao;

	private UsuarioInfo usuarioInfo;	
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	private Calendar dataAtual = Calendar.getInstance();	
	
	public MeiopagamentoController(Result result, UsuarioInfo usuarioInfo,Empresa empresa, Organizacao organizacao, Usuario usuario, MeioPagamentoDao meioPagamentoDao){

		this.result = result;
		this.meioPagamentoDao = meioPagamentoDao;
		this.usuarioInfo = usuarioInfo;				
		this.empresa = empresa;
		this.organizacao = organizacao;
		this.usuario = this.usuarioInfo.getUsuario();

	}	

	@Get
	@Path("/meiopagamento/cadastro")
	public void cadastro(){

		result.include("meiosPagamento", this.meioPagamentoDao.buscaAllMeioPagamento(1l, 1l));

	}

	@Post
	@Path("/meiopagamento/salva")
	public void salva(MeioPagamento meioPagamento){

		String mensagem = "";

		try {

			if (this.meioPagamentoDao.buscaMeioPagamentoByNome(1l, 1l, meioPagamento.getNome()) == null) {				

				meioPagamento.setCreated(dataAtual);
				meioPagamento.setUpdated(dataAtual);

				meioPagamento.setCreatedBy(usuario);
				meioPagamento.setUpdatedBy(usuario);

				meioPagamento.setChave(meioPagamento.getNome());
				meioPagamento.setDescricao(meioPagamento.getNome());

				empresa.setEmpresa_id(1l);
				organizacao.setOrganizacao_id(1l);

				meioPagamento.setEmpresa(empresa);
				meioPagamento.setOrganizacao(organizacao);

				meioPagamento.setIsActive(meioPagamento.getIsActive() == null ? false : true);

				this.meioPagamentoDao.beginTransaction();
				this.meioPagamentoDao.adiciona(meioPagamento);
				this.meioPagamentoDao.commit();

				mensagem = "Meio pagamento " + meioPagamento.getNome() + " adicionado com sucesso.";


			} else {

				mensagem = "Erro: Meio pagamento já cadastrado.";

			}

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar o Meio pagamento " + meioPagamento.getNome() + ".";

		} finally{

			this.meioPagamentoDao.clear();
			this.meioPagamentoDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Get 
	@Path("/meiopagamento/busca.json")
	public void tipotabela(Long empresa_id, Long organizacao_id, String nome){

		result.use(Results.json()).withoutRoot().from(meioPagamentoDao.buscaAllMeioPagamentoByEmpOrgNome(empresa_id, organizacao_id, nome)).serialize();

	}

	@Get
	public void msg() {

	}

}