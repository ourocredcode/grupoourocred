package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.sgo.dao.FuncionarioDao;
import br.com.sgo.dao.OrganizacaoDao;
import br.com.sgo.dao.OrganizacaoInfoDao;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.OrganizacaoInfo;
import br.com.sgo.modelo.Usuario;

@Resource
public class OrganizacaoController {

	private Calendar dataAtual = Calendar.getInstance();	
	
	private final Result result;
	private final FuncionarioDao funcionarioDao;
	private final OrganizacaoDao organizacaoDao;
	private final OrganizacaoInfoDao organizacaoInfoDao;

	private Organizacao organizacao;
	private UsuarioInfo usuarioInfo;
	private Empresa empresa;	
	private Usuario usuario;
	
	public OrganizacaoController(Result result, UsuarioInfo usuarioInfo, Empresa empresa, OrganizacaoDao organizacaoDao, Organizacao organizacao, OrganizacaoInfoDao organizacaoInfoDao, Usuario usuario, FuncionarioDao funcionarioDao){

		this.result = result;
		this.funcionarioDao = funcionarioDao;		
		this.organizacaoDao = organizacaoDao;
		this.organizacaoInfoDao = organizacaoInfoDao;
		this.usuarioInfo = usuarioInfo;
		this.empresa = usuarioInfo.getEmpresa();
		this.organizacao = usuarioInfo.getOrganizacao();
		this.usuario = this.usuarioInfo.getUsuario();

	}	
	
	
	@Get
	@Path("/organizacao/cadastro")
	public void cadastro(){

		result.include("organizacoes", this.organizacaoDao.buscaAllOrganizacaoByEmp(empresa.getEmpresa_id()));
		result.include("funcionarios", this.funcionarioDao.buscaFuncionarioToFillCombosByEmpOrg(empresa.getEmpresa_id(), organizacao.getOrganizacao_id()));

	}

	@Post
	@Path("/organizacao/salva")
	public void salva(Organizacao organizacao, OrganizacaoInfo organizacaoInfo){

		String mensagem = "";

		//try {

			//if (this.organizacaoDao.buscaOrganizacaoByEmpNome(empresa.getEmpresa_id(), organizacao.getNome()) == null) {				

				organizacao.setCreated(dataAtual);
				organizacao.setUpdated(dataAtual);

				organizacao.setCreatedBy(usuario);
				organizacao.setUpdatedBy(usuario);

				organizacao.setChave(organizacao.getNome());
				organizacao.setDescricao(organizacao.getNome());

				organizacao.setEmpresa(empresa);				

				organizacao.setIsActive(organizacao.getIsActive() == null ? false : true);

				this.organizacaoDao.beginTransaction();
				this.organizacaoDao.adiciona(organizacao);
				this.organizacaoDao.commit();

					OrganizacaoInfo oi = new OrganizacaoInfo();
	
					oi.setEmpresa(organizacao.getEmpresa());
					oi.setOrganizacao_id(organizacao.getOrganizacao_id());
					
					//f.setOperacao(funcionario.getOperacao().getOperacao_id() == null ? null : funcionario.getOperacao());
					oi.setTipoOrganizacao(organizacaoInfo.getTipoOrganizacao().getTipoOrganizacao_id()== null ? null : organizacaoInfo.getTipoOrganizacao());
					
					//oi.setTipoOrganizacao(organizacaoInfo.getTipoOrganizacao());
					oi.setLocalidade(organizacaoInfo.getLocalidade());
					oi.setCalendario(organizacaoInfo.getCalendario());
					oi.setOrganizacaoPai(organizacaoInfo.getOrganizacaoPai());
					System.out.println("organizacaoInfo.getSupervisorOrganizacao()" + organizacaoInfo.getSupervisorOrganizacao());
					oi.setSupervisorOrganizacao(organizacaoInfo.getSupervisorOrganizacao());
					oi.setChave(organizacao.getNome());
					oi.setNome(organizacao.getNome());
					oi.setDescricao(organizacao.getNome());
					oi.setNomeFantasia(organizacaoInfo.getNomeFantasia());
					oi.setDddFone1(organizacaoInfo.getDddFone1());
					oi.setTelefone1(organizacaoInfo.getTelefone1());
					oi.setDddFone2(organizacaoInfo.getDddFone2());
					oi.setTelefone2(organizacaoInfo.getTelefone2());
					oi.setDddFax(organizacaoInfo.getDddFax());
					oi.setFax(organizacaoInfo.getFax());
					oi.setEmail(organizacaoInfo.getEmail());
					oi.setIe(organizacaoInfo.getIe());
					oi.setCnpj(organizacaoInfo.getCnpj());
					oi.setContato(organizacaoInfo.getContato());					

					oi.setCreated(dataAtual);
					oi.setUpdated(dataAtual);

					oi.setCreatedBy(usuario);
					oi.setUpdatedBy(usuario);

					oi.setIsActive(organizacao.getIsActive());

					if (this.organizacaoInfoDao.buscaOrganizacaoById(organizacao.getOrganizacao_id()) == null) {						

						this.organizacaoInfoDao.beginTransaction();
						this.organizacaoInfoDao.adiciona(oi);
						this.organizacaoInfoDao.commit();

					}

				//mensagem = "Organização adicionado com sucesso.";

			//} else {

				//mensagem = "Erro: Organização já cadastrado.";

			//}

		//} catch (Exception e) {

	//		mensagem = "Erro: Falha ao adicionar a Organização.";

		//} finally{

			this.organizacaoDao.clear();
			this.organizacaoDao.close();

		//}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}
	
	@Post
	@Path("/organizacao/altera")
	public void altera(Organizacao organizacao) {

		String mensagem = "";

		this.organizacao = this.organizacaoDao.load(organizacao.getOrganizacao_id());

		this.organizacao.setUpdated(dataAtual);
		this.organizacao.setUpdatedBy(usuario);

		if(organizacao.getIsActive() != null){
			this.organizacao.setIsActive(organizacao.getIsActive() == false ? false : true);
		}

		organizacaoDao.beginTransaction();		
		organizacaoDao.atualiza(this.organizacao);
		organizacaoDao.commit();

		mensagem = " Organização alterado com sucesso.";

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Get
	@Path("/organizacao/busca.json")
	public void organizacoes(Long empresa_id, String org_nome){

		result.use(Results.json()).withoutRoot().from(organizacaoDao.buscaOrganizacoes(empresa_id, org_nome)).serialize();

	}	
	
}
