package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.EmpresaDao;
import br.com.sgo.dao.EmpresaInfoDao;
import br.com.sgo.dao.IdiomaDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.EmpresaInfo;
import br.com.sgo.modelo.Usuario;

@Resource
public class EmpresaController {

	private final Result result;
	private final EmpresaInfoDao empresaInfoDao;
	private final EmpresaDao empresaDao;
	private final IdiomaDao idiomaDao;

	private Calendar dataAtual = Calendar.getInstance();

	private UsuarioInfo usuarioInfo;
	private Empresa empresa;
	private Usuario usuario;

	public EmpresaController(Result result, UsuarioInfo usuarioInfo, Empresa empresa, EmpresaDao empresaDao, EmpresaInfoDao empresaInfoDao
			,IdiomaDao idiomaDao , Usuario usuario){

		this.result = result;
		this.idiomaDao = idiomaDao;
		this.empresaInfoDao = empresaInfoDao;
		this.empresaDao = empresaDao;
		this.usuarioInfo = usuarioInfo;
		this.empresa = usuarioInfo.getEmpresa();		
		this.usuario = usuarioInfo.getUsuario();

	}

	@Get
	@Path("/empresa/cadastro")
	public void cadastro(){

		result.include("empresas", this.empresaDao.buscaAllEmpresa());
		result.include("idiomas", this.idiomaDao.buscaAllIdioma(1l));

	}

	@Post
	@Path("/empresa/salva")
	public void salva(Empresa empresa, EmpresaInfo empresaInfo){

		String mensagem = "";

		try {

			if (this.empresaDao.buscaEmpresaByNome(empresa.getNome()) == null) {				

				empresa.setCreated(dataAtual);
				empresa.setUpdated(dataAtual);

				empresa.setCreatedBy(usuario);
				empresa.setUpdatedBy(usuario);

				empresa.setChave(empresa.getNome());
				empresa.setDescricao(empresa.getNome());
	

				empresa.setIsActive(empresa.getIsActive() == null ? false : true);

				this.empresaDao.beginTransaction();
				this.empresaDao.adiciona(empresa);
				this.empresaDao.commit();

					EmpresaInfo ei = new EmpresaInfo();

					ei.setEmpresa_id(empresa.getEmpresa_id());
					//ei.setCalendario(empresaInfo.getCalendario().getCalendario_id() == null ? null : empresaInfo.getCalendario());
					ei.setChave(empresa.getNome());
					ei.setNome(empresa.getNome());
					ei.setDescricao(empresa.getNome());
					
					ei.setCreated(dataAtual);
					ei.setUpdated(dataAtual);

					ei.setCreatedBy(usuario);
					ei.setUpdatedBy(usuario);

					ei.setIsActive(empresa.getIsActive());

					if (this.empresaInfoDao.buscaEmpresaById(empresa.getEmpresa_id()) == null) {						

						this.empresaInfoDao.beginTransaction();
						this.empresaInfoDao.adiciona(ei);
						this.empresaInfoDao.commit();

					}

				mensagem = "Empresa adicionado com sucesso.";

			} else {

				mensagem = "Erro: Empresa já cadastrada.";

			}

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar a Empresa.";

		} finally{

			this.empresaDao.clear();
			this.empresaDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}
	
	@Post
	@Path("/empresa/altera")
	public void altera(Empresa empresa) {

		String mensagem = "";

		this.empresa = this.empresaDao.load(empresa.getEmpresa_id());

		this.empresa.setUpdated(dataAtual);
		this.empresa.setUpdatedBy(usuario);

		if(empresa.getIsActive() != null){

			this.empresa.setIsActive(empresa.getIsActive() == false ? false : true);

		}

		empresaDao.beginTransaction();		
		empresaDao.atualiza(this.empresa);
		empresaDao.commit();

		mensagem = " Empresa alterada com sucesso.";

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Get
	@Path("/empresa/busca.json")
	public void empresas(String n){

		result.use(Results.json()).withoutRoot().from(empresaDao.buscaEmpresas(n)).serialize();

	}

}
