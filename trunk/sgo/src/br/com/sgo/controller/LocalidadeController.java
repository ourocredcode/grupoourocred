package br.com.sgo.controller;

import br.com.caelum.restfulie.RestClient;
import br.com.caelum.restfulie.Restfulie;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.CidadeDao;
import br.com.sgo.dao.LocalidadeDao;
import br.com.sgo.dao.PaisDao;
import br.com.sgo.dao.RegiaoDao;
import br.com.sgo.dao.TipoLocalidadeDao;
import br.com.sgo.modelo.Empresa;
import br.com.sgo.modelo.Localidade;
import br.com.sgo.modelo.Organizacao;
import br.com.sgo.modelo.cep.BrazilianAddressFinder;

@Resource
public class LocalidadeController {

	private final Result result;
	private final RegiaoDao regiaoDao;
	private final CidadeDao cidadeDao;
	private final PaisDao paisDao;
	private final TipoLocalidadeDao tipoLocalidadeDao;
	private final LocalidadeDao localidadeDao;
	private BrazilianAddressFinder addressFinder;
	private RestClient restfulie;

	public LocalidadeController(Result result, RegiaoDao regiaoDao, CidadeDao cidadeDao, TipoLocalidadeDao tipoLocalidadeDao, PaisDao paisDao, LocalidadeDao localidadeDao) {

		this.result = result;
		this.regiaoDao = regiaoDao;
		this.cidadeDao = cidadeDao;
		this.paisDao = paisDao;
		this.tipoLocalidadeDao = tipoLocalidadeDao;
		this.localidadeDao = localidadeDao;

	}

	@Post
	@Path("/localidade/salva")
	public void salva(){

	}
	
	@Post
	@Path("/localidade/busca.localidade")
	public void buscalocalidade(String enderecoCEP) {

		Localidade l = this.localidadeDao.buscaLocalidade(enderecoCEP);

		if(l.getLocalidade_id() == null) {

			restfulie = Restfulie.custom();
			addressFinder = new BrazilianAddressFinder(restfulie);
			String[] resultado = addressFinder.findAddressByZipCode(enderecoCEP).asAddressArray();

			if(resultado[0].equals("")) {

				result.redirectTo(this).cadastro(enderecoCEP);

			} else {

				l = new Localidade();

				Empresa emp = new Empresa();
				Organizacao org = new Organizacao();

				emp.setEmpresa_id(1l);
				org.setOrganizacao_id(1l);

				l.setEmpresa(emp);
				l.setOrganizacao(org);

				l.setIsActive(true);
				l.setPais(this.paisDao.buscaPais("Brasil"));
				l.setRegiao(this.regiaoDao.buscaPorNome(resultado[4]));
				l.setCidade(this.cidadeDao.buscaPorNome(resultado[3]));
				l.setTipoLocalidade(this.tipoLocalidadeDao.buscaPorNome(resultado[0]));
				l.setEndereco(resultado[1]);
				l.setBairro(resultado[2]);
				l.setCep(enderecoCEP);

				result.include("localidade",l);

			}

		} else {				

				result.include("localidade",l);

		}
	}
	
	@Post
	@Path("/localidade/organizacao/busca.localidade")
	public void buscalocalidadeOrg(String enderecoCEP) {

		Localidade l = this.localidadeDao.buscaLocalidade(enderecoCEP);

		if(l.getLocalidade_id() == null) {

			restfulie = Restfulie.custom();
			addressFinder = new BrazilianAddressFinder(restfulie);

			String[] resultado = addressFinder.findAddressByZipCode(enderecoCEP).asAddressArray();

			if(resultado[0].equals("")) {

				result.redirectTo(this).cadastroorg(enderecoCEP);

			} else {

				l = new Localidade();

				Empresa emp = new Empresa();
				Organizacao org = new Organizacao();

				emp.setEmpresa_id(1l);
				org.setOrganizacao_id(1l);

				l.setEmpresa(emp);
				l.setOrganizacao(org);

				l.setIsActive(true);
				l.setPais(this.paisDao.buscaPais("Brasil"));
				l.setRegiao(this.regiaoDao.buscaPorNome(resultado[4]));
				l.setCidade(this.cidadeDao.buscaPorNome(resultado[3]));
				l.setTipoLocalidade(this.tipoLocalidadeDao.buscaPorNome(resultado[0]));
				l.setEndereco(resultado[1]);
				l.setBairro(resultado[2]);
				l.setCep(enderecoCEP);

				result.include("localidade",l);

			}

		} else {				

				result.include("localidade",l);

		}
	}
	
	@Get
	@Path("/localidade/cadastro")
	public void cadastro(String cep) {

		Localidade localidade = new Localidade();
		localidade.setCep(cep);

		result.include("regioes",this.regiaoDao.listaTudo("ASC","nome"));
		result.include("tiposLocalidade",this.tipoLocalidadeDao.listaTudo("ASC","nome"));
		result.include("localidade",localidade);

	}
	
	@Get
	@Path("/localidade/cadastroorg")
	public void cadastroorg(String cep) {

		Localidade localidade = new Localidade();
		localidade.setCep(cep);

		result.include("regioes",this.regiaoDao.listaTudo("ASC","nome"));
		result.include("tiposLocalidade",this.tipoLocalidadeDao.listaTudo("ASC","nome"));
		result.include("localidade",localidade);

	}

	@Post
	@Path("/localidade/busca.cidades")
	public void cidades(Long regiao_id) {

		result.include("cidades",this.cidadeDao.buscaCidadesByRegiao(regiao_id));

	}

}
