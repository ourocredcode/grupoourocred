<%@ include file="/header.jspf"%>

<script type="text/javascript">

jQuery(function($){

	$('#departamento-li-a').click(function() {
		window.location.href = '<c:url value="/departamento/cadastro" />';
	});

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/departamento/cadastro" />';
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});

	
	$('.data-table').dataTable({
		"bJQueryUI": true,
		"sPaginationType": "full_numbers",
		"sDom": '<""l>t<"F"fp>'
	});

	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();

	$("span.icon input:checkbox, th input:checkbox").click(function() {
		var checkedStatus = this.checked;
		var checkbox = $(this).parents('.widget-box').find('tr td:first-child input:checkbox');		
		checkbox.each(function() {
			this.checked = checkedStatus;
			if (checkedStatus == this.checked) {
				$(this).closest('.checker > span').removeClass('checked');
			}
			if (this.checked) {
				$(this).closest('.checker > span').addClass('checked');
			}
		});
	});

	$("#departamentoIsActive").change(function(e){
		if(document.departamentoForm.departamentoIsActive.checked==true){
			document.departamentoForm.departamentoIsActive.value=true;
		}else{
			document.departamentoForm.departamentoIsActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.departamentoForm.reset();
	}
}

function altera(linha, id) {
	var valor = linha.checked == true ? true : false;
	if (window.confirm("Deseja alterar o departamento selecionado?"))
		$.post('<c:url value="/departamento/altera" />', {
			'departamento.departamento_id' : id,
			'departamento.isActive' : valor
		});

	return false;
}

</script>

<div id="content-header">
	<h1>Cadastro Departamento</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
		<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
	<a href="#" class="current">Departamento</a>
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
				<li class="" id="departamento-li"><a href="#departamento-div" data-toggle="tab" id="departamento-li-a">Cadastro de Departamentos</a></li>				
			</ul>
			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="departamento-div">
														
					<form id="departamentoForm" name="departamentoForm" action="<c:url value="/departamento/salva"/>" method="POST">
						<div class="row-fluid">
							<div class="span3">
								<label for="departamentoEmpresa">Empresa</label>
		      					<input class="input-xlarge" id="departamentoEmpresa" name="departamento.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" readonly="readonly">
		      					<input class="span1" id="departamentoEmpresaId" name="departamento.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden">
	    					</div>
							<div class="span3">
								<label for="departamentoOrganizacao">Organização</label>
		      					<input class="input-xlarge" id="departamentoOrganizacao" name="departamento.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" readonly="readonly">
		      					<input class="span1" id="departamentoOrganizacaoId" name="departamento.organizacao.organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" type="hidden">
							</div>
							<div class="span3">
								<label for="departamentoNome">Nome</label>
								<input class="input-xlarge" type="text" id="departamentoNome" name="departamento.nome" value="${departamento.nome }" placeholder="Nome" required>
							</div>							
							<div class="span1">
								<label for="departamentoIsActive">Ativo</label>
								<input type="checkbox" id="departamentoIsActive" name="departamento.isActive" checked="checked" value="1" >
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
					<h5>Função</h5>
				</div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty departamentos }">
						<table
							class="table table-bordered table-striped table-hover data-table"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>Empresa</th>
									<th>Organização</th>
									<th>Nome</th>
									<th>Ativo</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${departamentos }" var="departamento">
									<tr>
										<td>${departamento.empresa.nome }</td>
										<td>${departamento.organizacao.nome }</td>
										<td>${departamento.nome }</td>
										<td>
											<label class="checkbox inline">
												<input type="checkbox" id="departamentoIsActiveLine" name="departamento.isActive"
												<c:if test="${departamento.isActive == true }"> checked="checked"</c:if> onchange="altera(this,'${departamento.departamento_id}');">
											</label>
										</td>
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