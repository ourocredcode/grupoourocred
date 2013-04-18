<%@ include file="/header.jspf"%>

	<script type="text/javascript">

	</script>

	<div id="content-header">
		<h1>Formulário</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
		<a href="#" class="current">Formulário</a>
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

	<div id="buscaParceiroDiv">
		<form id="buscaParceiroForm" class="form-search" action="<c:url value="/formulario/cliente" />" method="POST">
			<div class="input-append">

				<input type="hidden" id="formularioEmpresaId" name="formulario.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" />
				<input type="hidden" id="formularioOrganizacaoId" name="formulario.organizacao.organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" />
				<input type="text" class="input-medium" id="formularioParceiroNegocioCpf" name="formulario.parceiroNegocio.cpf" placeholder="Benefício"/>
				<span class="add-on" onclick="submit();"><i class="icon-search"></i></span>

			</div>
		</form>
	</div>
	
	<div id="formCliente">

		<input type="text" class="input-medium" id="formularioParceiroNegocioNome" name="formulario.parceiroNegocio.nome" value="${formulario.parceiroNegocio.nome }"/>
		<input type="text" class="input-medium" id="formularioParceiroNegocioCpf" name="formulario.parceiroNegocio.cpf" placeholder="${formulario.parceiroNegocio.cpf }"/>

	</div>
	<div id="formDadosPagamento">
		
	</div>

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">

				Cadastro Formulário
						
			</div>
		</div>	
	</div>

<%@ include file="/footer.jspf"%>
