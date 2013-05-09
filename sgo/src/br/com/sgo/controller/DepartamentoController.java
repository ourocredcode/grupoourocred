package br.com.sgo.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.DepartamentoDao;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.modelo.Departamento;

@Resource
public class DepartamentoController {

	private final Result result;
	private final Validator validator;
	private final DepartamentoDao departamentoDao;
	private final EmpresaDao empresaDao;
	private final OrganizacaoDao organizacaoDao;
	

	public DepartamentoController(Result result, Validator validator, EmpresaDao empresaDao, OrganizacaoDao organizacaoDao, DepartamentoDao departamentoDao){
		this.departamentoDao = departamentoDao;
		this.empresaDao = empresaDao;
		this.organizacaoDao = organizacaoDao;		
		this.result = result;
		this.validator = validator;

	}	

	@Get
	@Public
	@Path("/departamento/cadastro")
	public void cadastro(){
		//result.include("departamento",this.departamentoDao.listaTudo("ASC","nome"));
	}

	@Post
	@Public
	@Path("/departamento/salva")
	public void salva(Departamento departamento){
		
		validator.validate(departamento);
		validator.onErrorUsePageOf(this).cadastro();

		String mensagem = "";

		try {

			departamento.setEmpresa(this.empresaDao.load(departamento.getEmpresa().getEmpresa_id()));		
			departamento.setOrganizacao(this.organizacaoDao.load(departamento.getOrganizacao().getOrganizacao_id()));			
			departamento.setIsActive(departamento.getIsActive() == null ? false : true);

			this.departamentoDao.beginTransaction();
			this.departamentoDao.adiciona(departamento);
			this.departamentoDao.commit();

			mensagem = "Grupo de Parceiro " + departamento.getNome() + " adicionado com sucesso";			

		} catch(Exception e) {
			
			this.departamentoDao.rollback();

			if (e.getCause().toString().indexOf("IX_DEPARTAMENTO_EMPORGNOME") != -1){
				mensagem = "Erro: Tipo Parceiro " + departamento.getNome() + " já existente.";
			} else {
				mensagem = "Erro ao adicionar Departamento";
			}

		}

		this.departamentoDao.clear();
		this.departamentoDao.close();
		result.include("notice",mensagem);
		result.redirectTo(this).cadastro();

	}

	@Get @Path("/departamento/busca.json")
	@Public
	public void departamento(Long empresa_id, Long organizacao_id, String nome){
		result.use(Results.json()).withoutRoot().from(departamentoDao.buscaDepartamentos(empresa_id, organizacao_id, nome)).serialize();
	}

}