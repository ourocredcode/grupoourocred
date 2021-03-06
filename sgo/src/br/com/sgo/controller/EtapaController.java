package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.EtapaDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Etapa;

@Resource
public class EtapaController {

	private final Result result;	
	private final UsuarioInfo usuarioInfo;
	private final EtapaDao etapaDao;
	
	public EtapaController(Result result, UsuarioInfo usuarioInfo, EtapaDao etapaDao) {

		this.result = result;
		this.etapaDao = etapaDao;
		this.usuarioInfo = usuarioInfo;

	}

	@Get
	@Path("/etapa/cadastro")
	public void cadastro() {
		result.include("etapas", this.etapaDao.buscaEtapasByEmpresaOrganizacao(usuarioInfo.getEmpresa().getEmpresa_id(), usuarioInfo.getOrganizacao().getOrganizacao_id()));
	}

	@Post
	@Path("/etapa/salva")
	public void salva(Etapa etapa) {

		String mensagem = "";

		try {

			if (this.etapaDao.buscaEtapaByEmpresaOrganizacaoNome(etapa.getEmpresa().getEmpresa_id(), etapa.getOrganizacao().getOrganizacao_id()
					, etapa.getNome()) == null) {				

				etapa.setIsActive(etapa.getIsActive() == null ? false : true);
				
				this.etapaDao.beginTransaction();
				this.etapaDao.adiciona(etapa);
				this.etapaDao.commit();

				mensagem = "Etapa " + etapa.getNome() + " adicionado com sucesso." ;

			} else {
				
				mensagem = "Erro: Etapa " + etapa.getNome() + " já cadastrado.";
				
			} 

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar a Etapa. " ;			

			} finally{
				this.etapaDao.clear();
				this.etapaDao.close();
			}

			result.include("notice", mensagem);			
			result.redirectTo(this).cadastro();
	}
	
	@Get
	@Path("/etapa/busca.json")
	public void etapa(Long empresa_id, Long organizacao_id, String nome) {	
		result.use(Results.json()).withoutRoot().from(etapaDao.buscaEtapasByEmpresaOrganizacaoNome(empresa_id, organizacao_id, nome)).serialize();	
	}

	@Post
	@Path("/etapa/lista")
	public void lista(Long empresa_id, Long organizacao_id, String nome) {
		result.include("etapas", this.etapaDao.buscaEtapasByEmpresaOrganizacaoNome(empresa_id, organizacao_id, nome));
	}

	@Get
	public void msg() {

	}
}