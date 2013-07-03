<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#grupoparceiro-li-a').click(function() {
		window.location.href = '<c:url value="/grupoparceiro/cadastro" />';
	});

	
	
	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/grupoparceiro/cadastro" />';
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});

	$("#grupoParceiroIsActive").change(function(e){
		if(document.grupoParceiroForm.grupoParceiroIsActive.checked==true){
			document.grupoParceiroForm.grupoParceiroIsActive.value=true;
		}else{
			document.grupoParceiroForm.grupoParceiroIsActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.grupoParceiroForm.reset();
	}
}
</script>

<div id="content-header">
	<h1>Grupo Parceiro</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
	<a href="#" class="current">Grupo Parceiro</a>
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
				<li class="" id="grupoparceiro-li"><a href="#grupoparceiro-div" data-toggle="tab" id="grupoparceiro-li-a">Grupo de Parceiro</a></li>				
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="grupoparceiro-div">
					<form id="grupoParceiroForm" name="grupoParceiroForm" action="<c:url value="/grupoparceiro/salva"/>" method="POST">
						<div class="row-fluid">
							<div class="span3">
								<label for="grupoParceiroEmpresa">Empresa</label>
	      						<input class="input-xlarge" id="grupoParceiroEmpresa" name="grupoParceiro.empresa.nome" type="text" value="${usuarioInfo.empresa.nome }" readonly="readonly">
	      						<input class="span1" id="grupoParceiroEmpresaId" name="grupoParceiro.empresa.empresa_id" type="hidden" value="${usuarioInfo.empresa.empresa_id }">
	    					</div>
							<div class="span3">
								<label for="grupoParceiroOrganizacao">Organização</label>
	     						<input class="input-xlarge" id="grupoParceiroOrganizacao" name="grupoParceiro.organizacao.nome" type="text" value="${usuarioInfo.organizacao.nome }" readonly="readonly">
	      						<input class="span1" id="grupoParceiroOrganizacaoId" name="grupoParceiro.organizacao.organizacao_id" type="hidden" value="${usuarioInfo.organizacao.organizacao_id }">
							</div>
							<div class="span3">
								<label for="grupoParceiroNome">Nome</label>
								<input class="input-xlarge" type="text" id="grupoParceiroNome" name="grupoParceiro.nome" placeholder="Nome" required>
							</div>
							<div class="span1">
								<label for="grupoParceiroIsActive">Ativo</label>
								<input type="checkbox" id="grupoParceiroIsActive" name="grupoParceiro.isActive" checked="checked" value="1" >
							</div>
						</div>
					 	<div class="btn-group">
							<button type="submit" class="btn btn-primary" id="btnSalvar">Salvar</button>
						</div>
						<div class="btn-group">
							<button type="button" class="btn btn-primary" id="btnNovo" >Novo</button>
						</div>
						<div class="btn-group">
							<button type="button" class="btn btn-primary" id="btnSair" >Sair</button>
						</div>
					</form>						
				</div>
			</div>
		</div>
	</div>
</div>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<div class="widget-box">
				<div class="widget-title">
					<span class="icon"><i class="icon-signal"></i> </span>
					<h5>Grupo Parceiro</h5>
				</div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty gruposParceiro}">
						<table
							class="table table-bordered table-striped table-hover data-table"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>Empresa</th>
									<th>organização</th>
									<th>Nome</th>
									<th>Ativo</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${gruposParceiro }" var="grupoParceiro">
									<tr>
										<td>${grupoParceiro.empresa.nome }</td>
										<td>${grupoParceiro.organizacao.nome }</td>
										<td>${grupoParceiro.nome }</td>
										<td>${grupoParceiro.isActive}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>
