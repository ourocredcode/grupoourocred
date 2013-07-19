<%@ include file="/header.jspf"%>



<div id="content-header">
	<h1>Cadastro Senha</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
	<a href="#" class="current">Senha</a>
</div>
	
<c:if test="${not empty notice}">
	<c:choose>
		<c:when test="${fn:contains(notice,'Erro:')}">
				<div class="alert alert-error">
					<strong>${notice }</strong>
					<a href="#" data-dismiss="alert" class="close">×</a>
				</div>
		</c:when>
		<c:otherwise>
				<div class="alert alert-success">
					<strong>${notice }</strong>
					<a href="#" data-dismiss="alert" class="close">×</a>
				</div>
		</c:otherwise>
	</c:choose>
</c:if>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">

			<div id="myTabContent" class="tab-content">

				<form id="alteraSenhaForm"  action="<c:url value="/usuario/altera/senha" />" method="POST">

					<label for="usuarioChave">CPF</label>
     				<input class="input-large" id="cpf" name="cpf" type="text">

					<label for="usuarioSenha">Senha</label>
					<input class="input-large" id="senha" name="senha" type="password">

					<label for="usuarioNovaSenha">Nova Senha</label>
					<input class="input-large" id="novasenha" name="novasenha" type="password">

					<label for="usuarioConfirmaNovaSenha">Confirme Nova Senha</label>
					<input class="input-large" id="confirmanovasenha" name="confirmanovasenha" type="password">

					<div class="btn-toolbar">
						<button type="submit" class="btn btn-primary" >Salvar</button>
					</div>

				</form>
			
			</div>

		</div>		
	</div>
</div>

<script type="text/javascript">

$(document).ready(function() {
	
	$("#cpf").mask("99999999999");
	
	$("#alteraSenhaForm").validate({
		rules: {
			novasenha: {
				required: true,
				minlength: 1
			},
			confirmanovasenha: {
				required: true,
				equalTo: "#novasenha", minlength: 1
			}
		}
	});

});
</script>

<%@ include file="/footer.jspf"%>
