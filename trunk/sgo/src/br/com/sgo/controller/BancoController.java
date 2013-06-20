package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.BancoDao;
import br.com.sgo.dao.ClassificacaoBancoDao;
import br.com.sgo.dao.GrupoBancoDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Banco;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.Usuario;

@Resource
public class BancoController {

	private final Result result;	
	private final BancoDao bancoDao;
	private final ClassificacaoBancoDao classificacaoBancoDao;
	private final GrupoBancoDao grupoBancoDao;
	
	private Banco banco;
	private Empresa empresa;
	private Organizacao organizacao;
	private final UsuarioInfo usuarioInfo;

	private Usuario usuario;
	private Calendar dataAtual = Calendar.getInstance();

	public BancoController(Result result, Usuario usuario, UsuarioInfo usuarioInfo, Empresa empresa, Organizacao organizacao
			, ClassificacaoBancoDao classificacaoBancoDao, Banco banco, BancoDao bancoDao, GrupoBancoDao grupoBancoDao){

		this.result = result;
		this.empresa = empresa;
		this.organizacao = organizacao;
		this.usuarioInfo = usuarioInfo;
		this.usuario = this.usuarioInfo.getUsuario();
		this.classificacaoBancoDao = classificacaoBancoDao;
		this.banco = banco;
		this.bancoDao = bancoDao;
		this.grupoBancoDao = grupoBancoDao;

	}

	@Get
	@Path("/banco/cadastro")
	public void cadastro(){

		result.include("gruposBanco", this.grupoBancoDao.buscaAllGrupoBanco());
		result.include("classificacaoBancos", this.classificacaoBancoDao.buscaAllClassificacaoBanco());
		result.include("bancos", this.bancoDao.buscaAllBancos());

	}

	@Post
	@Path("/banco/salva")
	public void salva(Banco banco){

		String mensagem = "";

		try {

			if (this.bancoDao.buscaBancoByEmpOrgGrupoClassificacaoNome(1l, 1l,
					banco.getGrupoBanco().getGrupoBanco_id(), banco.getClassificacaoBanco().getClassificacaoBanco_id(), banco.getNome()) == null) {				

				banco.setCreated(dataAtual);
				banco.setUpdated(dataAtual);

				banco.setCreatedBy(usuario);
				banco.setUpdatedBy(usuario);

				banco.setChave(banco.getNome());
				banco.setDescricao(banco.getNome());

				empresa.setEmpresa_id(1l);
				organizacao.setOrganizacao_id(1l);

				banco.setEmpresa(empresa);
				banco.setOrganizacao(organizacao);

				banco.setIsComprado(banco.getIsComprado() == null ? false : true);					
				banco.setIsActive(banco.getIsActive() == null ? false : true);

				this.bancoDao.beginTransaction();
				this.bancoDao.adiciona(banco);
				this.bancoDao.commit();

				mensagem = "Banco " + banco.getNome() + " adicionado com sucesso.";

			} else {

				mensagem = "Erro: Banco " + banco.getNome() + " já cadastrado.";

			}

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar o banco.";

		} finally{

			this.bancoDao.clear();
			this.bancoDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Post
	@Path("/banco/altera")
	public void altera(Banco banco) {

		String mensagem = "";

		this.banco = this.bancoDao.load(banco.getBanco_id());

		try {

			this.banco.setUpdated(dataAtual);
			this.banco.setUpdatedBy(usuario);
		
			if(banco.getIsActive() != null){
				this.banco.setIsActive(banco.getIsActive() == false ? false : true);
			}

			if(banco.getIsComprado() != null){
				this.banco.setIsComprado(banco.getIsComprado() == false ? false : true);
			}

			bancoDao.beginTransaction();		
			bancoDao.atualiza(this.banco);
			bancoDao.commit();
	
			mensagem = " Banco alterado com sucesso.";

		} catch (Exception e) {

			mensagem = "Erro: Falha ao alterar o banco.";

		} finally{

			this.bancoDao.clear();
			this.bancoDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Get 
	@Path("/banco/busca.json")
	public void grupoproduto(Long empresa_id, Long organizacao_id, String nome){

		result.use(Results.json()).withoutRoot().from(bancoDao.buscaBancos(1l, 1l, nome)).serialize();

	}

}