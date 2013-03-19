package br.com.sgo.controller;

import br.com.caelum.restfulie.RestClient;
import br.com.caelum.restfulie.Restfulie;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.CidadeDao;
import br.com.sgo.dao.RegiaoDao;
import br.com.sgo.dao.TipoLocalidadeDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.Localidade;
import br.com.sgo.modelo.cep.BrazilianAddressFinder;

@Resource
public class LocalidadeController {

	private final Result result;
	private final RegiaoDao regiaoDao;
	private final CidadeDao cidadeDao;
	private final TipoLocalidadeDao tipoLocalidadeDao;
	private BrazilianAddressFinder addressFinder;
	private RestClient restfulie;

	public LocalidadeController(Result result, UsuarioInfo usuarioInfo,RegiaoDao regiaoDao,CidadeDao cidadeDao,TipoLocalidadeDao tipoLocalidadeDao) {

		this.result = result;
		this.regiaoDao = regiaoDao;
		this.cidadeDao = cidadeDao;
		this.tipoLocalidadeDao = tipoLocalidadeDao;

	}

	@Get
	@Path("/localidade/cadastro")
	public void cadastro(){

	}

	
	@Post
	@Public
	@Path("/localidade/salva")
	public void salva(){

	}
	
	@Post
	@Public
	@Path("/localidade/busca.localidade")
	public void buscalocalidade(String enderecoCEP) {

		restfulie = Restfulie.custom();
		addressFinder = new BrazilianAddressFinder(restfulie);

		String[] resultado = addressFinder.findAddressByZipCode(enderecoCEP).asAddressArray();

		if(resultado[0].equals("")) {

			result.include("notice","ERRO").redirectTo(this).cadastro();

		} else {

			Localidade l = new Localidade();

			l.setRegiao(this.regiaoDao.buscaPorNome(resultado[4]));
			l.setCidade(this.cidadeDao.buscaPorNome(resultado[3]));
			l.setTipoLocalidade(this.tipoLocalidadeDao.buscaPorNome(resultado[0]));
			l.setEndereco1(resultado[1]);
			l.setBairro(resultado[2]);
			l.setCep(enderecoCEP);
			
			result.include("localidade",l);

		}
	}

}
