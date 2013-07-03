<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#tipologistica-li-a').click(function() {
		window.location.href = '<c:url value="/tipologistica/cadastro" />';
	});

	$('#tipologisticaperfil-li-a').click(function() {
		window.location.href = '<c:url value="/tipologisticaperfil/cadastro" />';
	});

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/tipologistica/cadastro" />';
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});

	$("#tipoLogisticaIsActive").change(function(e){
		if(document.tipoLogisticaForm.tipoLogisticaIsActive.checked==true){
			document.tipoLogisticaForm.tipoLogisticaIsActive.value=true;
		}else{
			document.tipoLogisticaForm.tipoLogisticaIsActive.value=false;
		}
	});

});

function limpaForm(){

	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.tipoLogisticaForm.reset();
	}

}


</script>

<div id="content-header">
		<h1>Cadastro Tipos de Logísticas</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
		<a href="#" class="current">Tipos de Logísticas</a>
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

			<ul id="myTab" class="nav nav-tabs">
				<li class="active" id="tipologistica-li"><a href="#tipologistica-div" data-toggle="tab" id="tipologistica-li-a">Tipo Logística</a></li>
				<li class="" id="tipologisticaperfil-li"><a href="#tipologisticaperfil-div" data-toggle="tab" id="tipologisticaperfil-li-a">Tipo Logística Perfil</a></li>				
			</ul>

			<div id="myTabContent" class="tab-content">				
				<div class="tab-pane fade  active in" id="tipologistica-div">			

					<form id="tipoLogisticaForm" name="tipoLogisticaForm" action="<c:url value="/tipologistica/salva"/>" method="POST">

						<div class="row-fluid">
							<div class="span3">
								<label for="tipoLogisticaEmpresa">Empresa</label>
								<input class="input-medium" id="tipoLogisticaEmpresaId" name="tipoLogistica.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden" required/>
								<input class="input-xlarge" id="tipoLogisticaEmpresa" name="tipoLogistica.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" />
							</div>

							<div class="span3">
								<label for="tipoLogisticaOrganizacao">Organização</label>
								<input  class="input-medium" id="tipoLogisticaOrganizacaoId" name="tipoLogistica.organizacao.organizacao_id"  value="${usuarioInfo.organizacao.organizacao_id }" type="hidden" required />
								<input  class="input-xlarge" id="tipoLogisticaOrganizacao" name="tipoLogistica.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" />							
							</div>
						</div>

						<div class="row-fluid">
							<div class="span5">
								<label for="tipoLogisticaNome">Nome</label>							
								<input class="input-xxlarge" id="tipoLogisticaNome" name="tipoLogistica.nome" value="${tipoLogistica.nome }" type="text" placeholder="Nome" required>							
							</div>
							<div class="span1">
								<label for="tipoLogisticaIsActive">Ativo</label>							
								<input type="checkbox" id="tipoLogisticaIsActive" name="tipoLogistica.isActive" checked="checked" value="1">
							</div>
						</div>
						<br>

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
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th>Empresa</th>
								<th>Organização</th>
								<th>Nome</th>
							</tr>
						</thead>
						<tbody>	
							<c:forEach items="${tiposLogistica }" var="tipoLogistica">
								<tr>
									<td>${tipoLogistica.empresa.nome }</td>
									<td>${tipoLogistica.organizacao.nome }</td>
									<td>${tipoLogistica.nome }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

				</div>

				<div class="tab-pane fade" id="tipologisticaperfil-div"></div>

			</div>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>
