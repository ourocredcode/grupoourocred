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

				$("#perfil_id").html(response);

			}

		});
		return false;
	});
	
	$('#perfil_id').change(function() {

		var perfil_id = $("#perfil_id").val();

		$("#empresa_id").load('<c:url value="/home/empresas" />',{'perfil_id': perfil_id});

	});
	
	$('#empresa_id').change(function() {

		var empresa_id = $("#empresa_id").val();
		var perfil_id = $("#perfil_id").val();

		$("#organizacao_id").load('<c:url value="/home/organizacoes" />',{'perfil_id': perfil_id, 'empresa_id':empresa_id});

	});
	
	$('#empresa_id').change(function() {

		var empresa_id = $("#empresa_id").val();
		var perfil_id = $("#perfil_id").val();

		$("#organizacao_id").load('<c:url value="/home/organizacoes" />',{'perfil_id': perfil_id, 'empresa_id':empresa_id});

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

					<div class="control-group">
						<label class="control-label" for="inputEmail">Perfil</label>
						<select id="perfil_id">	
							<option value="">Selecione o perfil</option>
						</select>
					</div>

					<div class="control-group">
						<label class="control-label" for="inputEmail">Empresa</label>
						<div class="controls">
							<select id="empresa_id">	
								<option value="">Selecione a empresa</option>
							</select>
						</div>
					</div>

					<div class="control-group">
						<label class="control-label" for="inputEmail">Organiza&ccedil;&atilde;o</label>
						<div class="controls">
							<select id="organizacao_id">	
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
				</div>
			</div>
		</div>
	</section>
</div>

<%@ include file="/footer.jspf"%>