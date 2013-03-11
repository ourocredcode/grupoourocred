<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#usuario-li-a').click(function() {
		window.location.href = '<c:url value="/usuario/cadastro" />';
	});

	$('#usuarioperfil-li-a').click(function() {
		window.location.href = '<c:url value="/usuarioperfil/cadastro" />';
	});

	$('#usuarioorgacesso-li-a').click(function() {
		window.location.href = '<c:url value="/usuarioorgacesso/cadastro" />';
	});

});

function limpaForm(){

	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.elementoBdForm.reset();
	}

}


</script>

<div class="span9">

	<section id="tabs">
		<div class="bs-docs-example">

			<ul id="myTab" class="nav nav-tabs">
				<li class="" id="usuario-li"><a href="#usuario-div" data-toggle="tab" id="usuario-li-a">Usuário</a></li>
				<li class="active" id="usuarioperfil-li"><a href="#usuarioperfil-div" data-toggle="tab" id="usuarioperfil-li-a">Usuario Perfil</a></li>
				<li class="" id="usuarioorgacesso-li"><a href="#usuarioorgacesso-div" data-toggle="tab" id="usuarioorgacesso-li-a">Usuário Organização</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade" id="usuario-div">					

					

				</div>

				<div class="tab-pane fade  active in" id="usuarioperfil-div">					
						
						<form id="perfilJanelaAcessoForm" name="usuarioPerfilForm" action="<c:url value="/perfiljanelaacesso/salva"/>" method="POST">

						<div class="control-group">
							<label class="control-label" for="usuarioPerfilFormEmpresa">Empresa</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="usuarioPerfilFormEmpresa" name="usuarioPerfil.empresa.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="usuarioPerfilFormEmpresaId" name="usuarioPerfil.empresa.empresa_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="usuarioPerfilOrganizacao">Organização</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="usuarioPerfilOrganizacao" name="usuarioPerfil.organizacao.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="usuarioPerfilOrganizacaoId" name="usuarioPerfil.organizacao.organizacao_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="perfilJanelaAcessoJanela">Usuário</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="usuarioPerfilUsuario" name="usuarioPerfil.usuario.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="usuarioPerfilUsuarioId" name="usuarioPerfil.usuario.usuario_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="usuarioPerfilPerfil">Perfil</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="usuarioPerfilPerfil" name="perfil.perfil.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="usuarioPerfilPerfilId" name="perfil.perfil.perfil_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="usuarioPerfilIsActive">Ativo</label>
							<div class="controls">
								<input type="checkbox" id="usuarioPerfilIsActive" name="usuarioPerfil.isActive" checked="checked" value="1">							
							</div>							
						</div>
						<div class="btn-toolbar">
							<div class="btn-group">
								<button type="submit" class="btn btn-primary" id="btnSalvar">Salvar</button>
							</div>	
							<div class="btn-group">
								<button type="button" class="btn btn-primary" id="btnNovo" >Novo</button>
							</div>
							<div class="btn-group">
								<button type="button" class="btn btn-primary" id="btnSair" >Sair</button>
							</div>
						</div>

					</form>

				</div>

				<div class="tab-pane fade" id="usuarioorgacesso-div">

				</div>

			</div>
		</div>
	</section>
</div>

<%@ include file="/footer.jspf"%>
