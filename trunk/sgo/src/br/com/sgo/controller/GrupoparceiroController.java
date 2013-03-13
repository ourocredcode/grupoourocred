package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.GrupoParceiroDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.GrupoParceiro;

@Resource
public class GrupoparceiroController {

	private final Result result;
	private final Validator validator;
	private final GrupoParceiroDao grupoParceiroDao;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	

	public GrupoparceiroController(Result result,Validator validator, EmpresaDao empresaDao,OrganizacaoDao organizacaoDao,GrupoParceiroDao grupoParceiroDao){
		this.grupoParceiroDao = grupoParceiroDao;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;		
		this.result = result;
		this.validator = validator;

	}	

	@Get
	@Public
	@Path("/grupoparceiro/cadastro")
	public void cadastro(){
		result.include("grupoParceiro",this.grupoParceiroDao.listaTudo("ASC","nome"));
	}

	@Post
	@Public
	@Path("/grupoparceiro/salva")
	public void salva(GrupoParceiro grupoParceiro){
		
		validator.validate(grupoParceiro);
		validator.onErrorUsePageOf(this).cadastro();

		String mensagem = "";

		try {

			grupoParceiro.setEmpresa(this.empresaDao.load(grupoParceiro.getEmpresa().getEmpresa_id()));		
			grupoParceiro.setOrganizacao(this.organizacaoDao.load(grupoParceiro.getOrganizacao().getOrganizacao_id()));

			this.grupoParceiroDao.beginTransaction();
			this.grupoParceiroDao.adiciona(grupoParceiro);
			this.grupoParceiroDao.commit();

			mensagem = "Grupo de Parceiro " + grupoParceiro.getNome() + " adicionado com sucesso";			
			
		} catch(Exception e) {
			
			this.grupoParceiroDao.rollback();

			if (e.getCause().toString().indexOf("IX_GRUPOPARCEIRO_EMPORGNOME") != -1){
				mensagem = "Erro: Tipo Parceiro " + grupoParceiro.getNome() + " já existente.";
			} else {
				mensagem = "Erro ao adicionar Grupo de Parceiro";
			}

		}
		
		this.grupoParceiroDao.clear();
		this.grupoParceiroDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

	@Get @Path("/grupoparceiro/busca.json")
	@Public
	public void grupoparceiro(Long empresa_id, Long organizacao_id, String nome){
		result.use(Results.json()).withoutRoot().from(grupoParceiroDao.buscaGrupoParceiro(empresa_id, organizacao_id, nome)).serialize();
	}

}