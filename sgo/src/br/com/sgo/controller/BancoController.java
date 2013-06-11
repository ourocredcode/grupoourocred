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
	private final UsuarioInfo usuarioInfo;
	private final BancoDao bancoDao;
	private final ClassificacaoBancoDao classificacaoBancoDao;
	private final GrupoBancoDao grupoBancoDao;
	
	private Empresa empresa;
	private Organizacao organizacao;
	private Usuario usuario;

	private Calendar dataAtual = Calendar.getInstance();

	public BancoController(Result result, Empresa empresa, Organizacao organizacao, Usuario usuario, UsuarioInfo usuarioInfo, ClassificacaoBancoDao classificacaoBancoDao, BancoDao bancoDao, GrupoBancoDao grupoBancoDao){

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.empresa = this.usuarioInfo.getEmpresa();
		this.organizacao = this.usuarioInfo.getOrganizacao();
		this.usuario = this.usuarioInfo.getUsuario();
		this.classificacaoBancoDao = classificacaoBancoDao;
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
			
			if (empresa.getNome().equals("SYSTEM") && organizacao.getNome().equals("SYSTEM")){

				if (this.bancoDao.buscaBancoByEmpOrgGrupoClassificacaoNome(empresa.getEmpresa_id(), organizacao.getOrganizacao_id(),
						banco.getGrupoBanco().getGrupoBanco_id(), banco.getClassificacaoBanco().getClassificacaoBanco_id(), banco.getNome()) == null) {				

					banco.setCreated(dataAtual);
					banco.setUpdated(dataAtual);

					banco.setCreatedBy(usuario);
					banco.setUpdatedBy(usuario);

					banco.setIsActive(banco.getIsActive() == null ? false : true);

					this.bancoDao.beginTransaction();
					this.bancoDao.adiciona(banco);
					this.bancoDao.commit();

					mensagem = "Banco " + banco.getNome() + " adicionado com sucesso.";

				} else {

					mensagem = "Erro: Banco " + banco.getNome() + " já cadastrado.";

				}

			} else {

				mensagem = "Erro: Banco " + banco.getNome() + " não pode ser cadastrado nesta empresa.";

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

	@Get 
	@Path("/banco/busca.json")
	public void grupoproduto(Long empresa_id, Long organizacao_id, String nome){
		result.use(Results.json()).withoutRoot().from(bancoDao.buscaBancos(empresa_id, organizacao_id, nome)).serialize();
	}

}