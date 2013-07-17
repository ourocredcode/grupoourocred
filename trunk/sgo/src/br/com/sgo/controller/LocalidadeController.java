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
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Localidade;
import br.com.sgo.modelo.cep.BrazilianAddressFinder;

@Resource
public class LocalidadeController {

	private final UsuarioInfo usuarioInfo;
	private final Result result;
	private final RegiaoDao regiaoDao;
	private final CidadeDao cidadeDao;
	private final PaisDao paisDao;
	private final TipoLocalidadeDao tipoLocalidadeDao;
	private final LocalidadeDao localidadeDao;
	private BrazilianAddressFinder addressFinder;
	private RestClient restfulie;

	public LocalidadeController(Result result, UsuarioInfo usuarioInfo,RegiaoDao regiaoDao,CidadeDao cidadeDao,TipoLocalidadeDao tipoLocalidadeDao,PaisDao paisDao
			,LocalidadeDao localidadeDao) {

		this.result = result;
		this.regiaoDao = regiaoDao;
		this.cidadeDao = cidadeDao;
		this.paisDao = paisDao;
		this.tipoLocalidadeDao = tipoLocalidadeDao;
		this.localidadeDao = localidadeDao;
		this.usuarioInfo = usuarioInfo;

	}

	@Post
	@Path("/localidade/salva")
	public void salva(){

	}
	
	@Post
	@Path("/localidade/busca.localidade")
	public void buscalocalidade(String enderecoCEP) {

		restfulie = Restfulie.custom();
		addressFinder = new BrazilianAddressFinder(restfulie);

		Localidade l = this.localidadeDao.buscaLocalidade(enderecoCEP);

		if(l.getLocalidade_id() == null) {

			String[] resultado = addressFinder.findAddressByZipCode(enderecoCEP).asAddressArray();

			if(resultado[0].equals("")) {

				result.redirectTo(this).cadastro(enderecoCEP);

			} else {

				l = new Localidade();

				l.setEmpresa(usuarioInfo.getEmpresa());
				l.setOrganizacao(usuarioInfo.getOrganizacao());
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
	
	@Post
	@Path("/localidade/busca.cidades")
	public void cidades(Long regiao_id) {

		result.include("cidades",this.cidadeDao.buscaCidadesByRegiao(regiao_id));

	}

}
