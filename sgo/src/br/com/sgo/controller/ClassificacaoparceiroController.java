package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.ClassificacaoParceiroDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.ClassificacaoParceiro;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Usuario;

@Resource
public class ClassificacaoparceiroController {

	private final Result result;
	private final ClassificacaoParceiroDao classificacaoParceiroDao;

	private UsuarioInfo usuarioInfo;	
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	private Calendar dataAtual = Calendar.getInstance();

	public ClassificacaoparceiroController(Result result, ClassificacaoParceiroDao classificacaoParceiroDao, UsuarioInfo usuarioInfo,
			Empresa empresa, Organizacao organizacao, Usuario usuario){

		this.result = result;
		this.classificacaoParceiroDao = classificacaoParceiroDao;
		this.usuarioInfo = usuarioInfo;				
		this.empresa = empresa;
		this.organizacao = organizacao;
		this.usuario = this.usuarioInfo.getUsuario();

	}	

	@Get
	@Path("/classificacaoparceiro/cadastro")
	public void cadastro(){

		result.include("classificacoesParceiro", this.classificacaoParceiroDao.buscaAllClassificacaoParceiroByEmpOrg(1l, 1l));

	}

	@Post
	@Path("/classificacaoparceiro/salva")
	public void salva(ClassificacaoParceiro classificacaoParceiro){

		String mensagem = "";

		try {

			if (this.classificacaoParceiroDao.buscaClassificacaoParceiroByEmpOrgNome(1l, 1l, classificacaoParceiro.getNome()) == null) {				

				classificacaoParceiro.setCreated(dataAtual);
				classificacaoParceiro.setUpdated(dataAtual);

				classificacaoParceiro.setCreatedBy(usuario);
				classificacaoParceiro.setUpdatedBy(usuario);

				classificacaoParceiro.setChave(classificacaoParceiro.getNome());
				classificacaoParceiro.setDescricao(classificacaoParceiro.getNome());

				empresa.setEmpresa_id(1l);
				organizacao.setOrganizacao_id(1l);

				classificacaoParceiro.setEmpresa(empresa);
				classificacaoParceiro.setOrganizacao(organizacao);

				classificacaoParceiro.setIsActive(classificacaoParceiro.getIsActive() == null ? false : true);

				this.classificacaoParceiroDao.beginTransaction();
				this.classificacaoParceiroDao.adiciona(classificacaoParceiro);
				this.classificacaoParceiroDao.commit();

				mensagem = "Classificação Parceiro " + classificacaoParceiro.getNome() + " adicionado com sucesso.";


			} else {

				mensagem = "Erro: Classificação Parceiro já cadastrado.";

			}

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar o Classificação Parceiro " + classificacaoParceiro.getNome() + ".";

		} finally{

			this.classificacaoParceiroDao.clear();
			this.classificacaoParceiroDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Get
	@Path("/classificacaoparceiro/busca.json")
	public void classificacaoParceiro(Long empresa_id, Long organizacao_id, String nome){

		result.use(Results.json()).withoutRoot().from(classificacaoParceiroDao.buscaClassificacoesParceiro(1l, 1l, nome)).serialize();

	}

}