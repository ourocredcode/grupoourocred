<%@ include file="/header.jspf"%>

<script type="text/javascript">


jQuery(function($){
	
	$('#loginForm').submit(function() {
		$.ajax({
			data: $(this).serialize()
			, type: $(this).attr('method')
			, url: $(this).attr('action')
			, success: function(response) {

				$("#login-li").removeClass("active");					
				$("#login-li").addClass("disabled");				
				$("#login-li-a").attr('href',"#");
	
				$("#perfil-li").removeClass("disabled");
				$("#perfil-li").addClass("active");
				$("#perfil-li-a").attr('href',"#perfil-div");
	
				$("#login-div").removeClass("tab-pane fade active in");
				$("#login-div").addClass("tab-pane fade");
				
				$("#perfil-div").removeClass("tab-pane fade");
				$("#perfil-div").addClass("tab-pane fade active in");

				$("#usuarioPerfil").html(response);

			}

		});
		return false;
	});
	
	$('#usuarioPerfil').change(function() {
		var perfil_id = $("#usuarioPerfil").val();
		$("#usuarioPerfilEmpresa").load('<c:url value="/home/empresas" />',{'perfil_id': perfil_id});
	});
	
	$('#usuarioPerfilEmpresa').change(function() {

		var empresa_id = $("#usuarioPerfilEmpresa").val();
		var perfil_id = $("#usuarioPerfil").val();

		$("#usuarioPerfilOrganizacao").load('<c:url value="/home/organizacoes" />',{'perfil_id': perfil_id, 'empresa_id':empresa_id});
	});

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/home/logout" />';
	});

}); 

</script>

<div class="span9">

	<section id="tabs">
		<div class="bs-docs-example">

			<ul id="myTab" class="nav nav-tabs">
				<li class="active" id="login-li"><a href="#login-div" data-toggle="tab" id="login-li-a">Login</a></li>
				<li class="disabled" id="perfil-li"><a href="#" data-toggle="tab" id="perfil-li-a">Perfil</a></li>	
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="login-div">

					<p></p>

					<form id="loginForm" action="<c:url value="/home/login"/>" method="POST">					

						<div class="control-group">
							<label class="control-label" for="login">Login</label>
							<div class="controls">
								<input type="text" id="login" name="login" placeholder="Digite seu CPF" required>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="password">Senha</label>
							<div class="controls">
								<input type="password" id="password" name="password" placeholder="Senha" required>
							</div>
							<div class="control-group">
								<div class="controls">
									<p></p>
									<button type="submit" class="btn btn-primary">OK</button>
								</div>
							</div>
						</div>
					</form>

				</div>

				<div class="tab-pane fade"  id="perfil-div">

					<p></p>
					<form id="usuarioPerfilForm" action="<c:url value="/menu/inicio"/>" method="POST">
						<input type="hidden" id="usuarioPerfilUsuarioId" name="usuarioPerfil.usuario.usuario_id" />
						<div class="control-group">
							<label class="control-label" for="usuarioPerfil">Perfil</label>
							<select id="usuarioPerfil" name="usuarioPerfil.perfil.perfil_id">	
								<option value="">Selecione o perfil</option>
							</select>
						</div>
	
						<div class="control-group">
							<label class="control-label" for="usuarioPerfilEmpresa">Empresa</label>
							<div class="controls">
								<select id="usuarioPerfilEmpresa" name="usuarioPerfil.empresa.empresa_id" type="hidden">	
									<option value="">Selecione a empresa</option>
								</select>
							</div>
						</div>
	
						<div class="control-group">
							<label class="control-label" for="usuarioPerfilOrganizacao">Organiza&ccedil;&atilde;o</label>
							<div class="controls">
								<select id="usuarioPerfilOrganizacao" name="usuarioPerfil.organizacao.organizacao_id">	
									<option value="">Selecione a organizacao</option>
								</select>
							</div>
	
							 <div class="btn-toolbar">
								<div class="btn-group">
									<button type="submit" class="btn btn-primary">OK</button>
								</div>	
								<div class="btn-group">
									<button type="button" class="btn btn-primary" id="btnSair" >Sair</button>
								</div>
							</div>					
						</div>
					</form>

				</div>
			</div>
		</div>
	</section>
</div>

<%@ include file="/footer.jspf"%>
