<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#agente-li-a').click(function() {
		window.location.href = '<c:url value="/agente/cadastro" />';
	});
	
	$('#tipocontrole-li-a').click(function() {
		window.location.href = '<c:url value="/tipocontrole/cadastro" />';
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
		window.location.href = '<c:url value="/agente/cadastro" />';
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});
	
	$("#isControle").change(function(e){
		if(document.agenteForm.isControle.checked==true){
			document.agenteForm.isControle.value=true;
		}else{
			document.agenteForm.isControle.value=false;
		}
	})

	$("#isActive").change(function(e){
		if(document.agenteForm.isActive.checked==true){
			document.agenteForm.isActive.value=true;
		}else{
			document.agenteForm.isActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.agenteForm.reset();
	}
}

function altera(linha, atributo, id) {

	if(atributo == 'isControleLine'){

		var isControle = linha.checked == true ? true : false;

		if (window.confirm("Deseja alterar o agente selecionado?"))
			$.post('<c:url value="/agente/altera" />', {
				'agente.agente_id' : id, 'agente.isControle' : isControle
			});

	}
	
	if(atributo=='isActiveLine'){

		var isActive = linha.checked == true ? true : false;

		if (window.confirm("Deseja alterar o agente selecionado?"))
			$.post('<c:url value="/agente/altera" />', {'agente.agente_id' : id, 'agente.isActive' : isActive});

	}

	return false;
}

</script>

<div id="content-header">
	<h1>Cadastro Agente</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
	<a href="#" class="current">Agente</a>
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
				<li class="active" id="agente-li"><a href="#agente-div" data-toggle="tab" id="agente-li-a">Agente</a></li>
				<li class="" id="tipocontrole-li"><a href="#tipocontrole-div" data-toggle="tab" id="tipocontrole-li-a">Tipo Controle</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="agente-div">

					<form id="agenteForm" name="agenteForm" action="<c:url value="/agente/salva"/>" method="POST">
						<div class="row-fluid">
							<div class="span2">
								<label for="empresa">Empresa</label>
	      						<input class="input-medium" id="empresa" name="agente.empresa.nome" type="text" value="${usuarioInfo.empresa.nome }" readonly="readonly">
	      						<input class="span1" id="empresaId" name="agente.empresa.empresa_id" type="hidden" value="${usuarioInfo.empresa.empresa_id }" >
							</div>
							<div class="span2">
								<label for="organizacao">Organização</label>
	      						<input class="input-medium" id="organizacao" name="agente.organizacao.nome" type="text" value="${usuarioInfo.organizacao.nome }" readonly="readonly">
	      						<input class="span1" id="organizacaoId" name="agente.organizacao.organizacao_id" type="hidden" value="${usuarioInfo.organizacao.organizacao_id }">
	    					</div>
							<div class="span3">
								<label for="nome">Nome</label>
								<input class="input-xlarge" id="nome" name="agente.nome" placeholder="Nome" type="text" required>
							</div>
							<div class="span1">
								<label for="isControle">Controle</label>
								<input id="isControle" name="agente.isControle" type="checkbox" value="1" >
							</div>					
							<div class="span1">
								<label for="isActive">Ativo</label>
								<input id="isActive" name="agente.isActive" type="checkbox" checked="checked" value="1" >
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

				<div class="tab-pane fade" id="tipocontrole-div"></div>

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
					<h5>Agente</h5>
				</div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty agentes}">
						<table
							class="table table-bordered table-striped table-hover data-table"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>Empresa</th>
									<th>Organização</th>
									<th>Agente</th>
									<th>Controle</th>
									<th>Ativo</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${agentes }" var="agente">
									<tr>
										<td>${agente.empresa.nome }</td>
										<td>${agente.organizacao.nome }</td>
										<td>${agente.nome }</td>
										<td>
											<label class="checkbox inline">
												<input type="checkbox" id="isControleLine" name="agente.isControle"
												<c:if test="${agente.isControle == true }"> checked="checked"</c:if> onchange="altera(this,'isControleLine','${agente.agente_id}');">
											</label>
										</td>
										<td>
											<label class="checkbox inline">
												<input type="checkbox" id="isActiveLine" name="agente.isActive"
												<c:if test="${agente.isActive == true }"> checked="checked"</c:if> onchange="altera(this,'isActiveLine','${agente.agente_id}');">
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