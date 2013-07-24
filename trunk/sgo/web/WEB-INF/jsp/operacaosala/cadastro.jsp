<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#operacao-li-a').click(function() {
		window.location.href = '<c:url value="/operacao/cadastro" />';
	});

	$('#sala-li-a').click(function() {
		window.location.href = '<c:url value="/sala/cadastro" />';
	});

	$('#operacaosala-li-a').click(function() {
		window.location.href = '<c:url value="/operacaosala/cadastro" />';
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

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/operacaosala/cadastro" />';
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});

	$("#isActive").change(function(e){
		if(document.operacaoSalaForm.isActive.checked==true){
			document.operacaoForm.isActive.value=true;
		}else{
			document.operacaoForm.isActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.operacaoSalaForm.reset();
	}
}

</script>

<div id="content-header">
	<h1>Cadastro Operação-Salas</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
	<a href="#" class="current">Operação-Salas</a>
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
				<li class="" id="operacao-li"><a href="#operacao-div" data-toggle="tab" id="operacao-li-a">Operação</a></li>
				<li class="" id="sala-li"><a href="#sala-div" data-toggle="tab" id="sala-li-a">Sala</a></li>
				<li class="active" id="operacaosala-li"><a href="#operacaosala-div" data-toggle="tab" id="operacaosala-li-a">Operação Sala</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade" id="operacao-div"></div>
				<div class="tab-pane fade" id="sala-div"></div>

				<div class="tab-pane fade active in" id="operacaosala-div">
					<form id="operacaoSalaForm" name="operacaoSalaForm" action="<c:url value="/operacaosala/salva"/>" method="POST">
						<div class="row-fluid">
							<div class="span3">
								<label for="empresa">Empresa</label>
	      						<input class="input-xlarge" id="empresa" name="operacaoSala.empresa.nome" type="text" value="${usuarioInfo.empresa.nome }" readonly="readonly">
	      						<input class="span1" id="empresaId" name="operacaoSala.empresa.empresa_id" type="hidden" value="${usuarioInfo.empresa.empresa_id }" >
							</div>
							<div class="span3">
								<label for="organizacao">Organização</label>
	      						<input class="input-xlarge" id="organizacao" name="operacaoSala.organizacao.nome" type="text" value="${usuarioInfo.organizacao.nome }" readonly="readonly">
	      						<input class="span1" id="organizacaoId" name="operacaoSala.organizacao.organizacao_id" type="hidden" value="${usuarioInfo.organizacao.organizacao_id }">
	    					</div>						
								<div class="span2">
								<label for="operacao">Operacao</label>
								<select class="input-medim" id="operacaoId" name="operacaoSala.operacao.operacao_id" >
									<c:forEach var="operacao" items="${operacoes }">
									 	<option value="${operacao.operacao_id }" selected="selected"> ${operacao.nome }</option>
									</c:forEach>
								</select>
							</div>
								<div class="span2">
								<label for="sala">Sala</label>
								<select class="input-medim" id="salaId" name="operacaoSala.sala.sala_id" >
									<c:forEach var="sala" items="${salas }">
									 	<option value="${sala.sala_id }" selected="selected"> ${sala.nome }</option>
									</c:forEach>
								</select>
							</div>
							<div class="span1">
								<label for="isActive">Ativo</label>
								<input id="isActive" name="operacaoSala.isActive" type="checkbox" checked="checked" value="1" >
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
					<h5>Operação</h5>
				</div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty operacoes}">
						<table
							class="table table-bordered table-striped table-hover data-table"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>Empresa</th>
									<th>Organização</th>
									<th>Operação</th>
									<th>Sala</th>
									<th>Ativo</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${operacaoSalas }" var="operacaoSala">
									<tr>
										<td>${operacaoSala.empresa.nome }</td>
										<td>${operacaoSala.organizacao.nome }</td>
										<td>${operacaoSala.operacao.nome }</td>
										<td>${operacaoSala.sala.nome }</td>
										<td>
											<label class="checkbox inline">
												<input type="checkbox" id="isActiveLine" name="operacaoSala.isActive"
												<c:if test="${operacaoSala.isActive == true }"> checked="checked"</c:if>>
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