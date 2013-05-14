package br.com.sgo.controller;

import java.util.Calendar;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.sgo.dao.TipoLogisticaPerfilDao;
import br.com.sgo.interceptor.Public;
import br.com.sgo.interceptor.UsuarioInfo;
import br.com.sgo.modelo.TipoLogisticaPerfil;

@Resource
public class TipologisticaperfilController {

	private final Result result;
	private final TipoLogisticaPerfilDao tipoLogisticaPerfilDao;
	private final UsuarioInfo usuarioInfo;
	private Calendar dataAtual = Calendar.getInstance();

	public TipologisticaperfilController(Result result,  UsuarioInfo usuarioInfo, TipoLogisticaPerfilDao tipoLogisticaPerfilDao) {

		this.result = result;
		this.usuarioInfo = usuarioInfo;
		this.tipoLogisticaPerfilDao =  tipoLogisticaPerfilDao;

	}

	@Get
	@Public
	@Path("/tipologisticaperfil/cadastro")
	public void cadastro() {
		result.include("tiposLogisticaPerfil", this.tipoLogisticaPerfilDao.buscaTodosTipoLogisticaPerfil());
	}

	@Post
	@Public
	@Path("/tipologisticaperfil/salva")
	public void salva(TipoLogisticaPerfil tipoLogisticaPerfil) {

		String mensagem = "";

		try {

			if (this.tipoLogisticaPerfilDao.buscaTipoLogisticaPerfilPorEmpresaOrganizacaoWorkflowPerfil(usuarioInfo.getEmpresa().getEmpresa_id()
					,usuarioInfo.getOrganizacao().getOrganizacao_id(), tipoLogisticaPerfil.getTipoLogistica().getTipoLogistica_id(), tipoLogisticaPerfil.getPerfil().getPerfil_id()) == null) {

				tipoLogisticaPerfil.setCreated(dataAtual);
				tipoLogisticaPerfil.setUpdated(dataAtual);

				tipoLogisticaPerfil.setCreatedBy(usuarioInfo.getUsuario());
				tipoLogisticaPerfil.setUpdatedBy(usuarioInfo.getUsuario());
				
				tipoLogisticaPerfil.setIsLeituraEscrita(tipoLogisticaPerfil.getIsLeituraEscrita() == null ? false : true);
				tipoLogisticaPerfil.setIsActive(tipoLogisticaPerfil.getIsActive() == null ? false : true);

				this.tipoLogisticaPerfilDao.insert(tipoLogisticaPerfil);

				mensagem = "Perfil " + tipoLogisticaPerfil.getPerfil().getNome() + " adicionado com sucesso.";
				
			} else {
				
				mensagem = "Erro: Perfil " + tipoLogisticaPerfil.getPerfil().getNome() +" já cadastrado.";
				
			} 

		} catch (Exception e) {

			mensagem = "Erro: Falha ao adicionar o Perfil o Tipo de Logistica:" + tipoLogisticaPerfil.getPerfil().getNome();

		} finally{

			this.tipoLogisticaPerfilDao.clear();
			this.tipoLogisticaPerfilDao.close();

		}

		result.include("notice", mensagem);			
		result.redirectTo(this).cadastro();

	}

	@Get
	public void msg() {

	}
}