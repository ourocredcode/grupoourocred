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
		window.location.href = '<c:url value="/tipocontrole/cadastro" />';
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});

	$("#isActive").change(function(e){
		if(document.tipoControleForm.isActive.checked==true){
			document.tipoControleForm.isActive.value=true;
		}else{
			document.tipoControleForm.isActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.tipoControleForm.reset();
	}
}

function altera(linha, id) {

	var valor = linha.checked == true ? true : false ;

	if (window.confirm("Deseja alterar o Tipo de Controle selecionado?"))
		$.post('<c:url value="/tipocontrole/altera" />', {
			'tipoControle.tipoControle_id' : id, 'tipoControle.isActive' : valor
		});

	return false;
}

</script>

<div id="content-header">
	<h1>Cadastro Tipo Controle</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
		<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
	<a href="#" class="current">Tipo Controle</a>
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
				<li class="" id="agente-li"><a href="#agente-div" data-toggle="tab" id="agente-li-a">Agente</a></li>
				<li class="active" id="tipocontrole-li"><a href="#tipocontrole-div" data-toggle="tab" id="tipocontrole-li-a">Tipo Controle</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade" id="agente-div"></div>					

				<div class="tab-pane fade active in" id="tipocontrole-div">
					<form id="tipoControleForm" name="tipoControleForm" action="<c:url value="/tipocontrole/salva"/>" method="POST">
						<div class="row-fluid">
							<div class="span2">
								<label for="empresa">Empresa</label>
	      						<input class="input-medium" id="empresa" name="tipoControle.empresa.nome" type="text" value="${usuarioInfo.empresa.nome }" readonly="readonly">
	      						<input class="span1" id="empresaId" name="tipoControle.empresa.empresa_id" type="hidden" value="${usuarioInfo.empresa.empresa_id }" >
							</div>
							<div class="span2">
								<label for="organizacao">Organização</label>
	      						<input class="input-medium" id="organizacao" name="tipoControle.organizacao.nome" type="text" value="${usuarioInfo.organizacao.nome }" readonly="readonly">
	      						<input class="span1" id="organizacaoId" name="tipoControle.organizacao.organizacao_id" type="hidden" value="${usuarioInfo.organizacao.organizacao_id }">
	    					</div>
							<div class="span3">
								<label for="nome">Nome</label>
								<input class="input-xlarge" id="nome" name="tipoControle.nome" placeholder="Nome" type="text" required>
							</div>
							<div class="span1">
								<label for="isActive">Ativo</label>
								<input id="isActive" name="tipoControle.isActive" type="checkbox" checked="checked" value="1" >
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
					<h5>Agente</h5>
				</div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty tiposControle}">
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
								<c:forEach items="${tiposControle }" var="tipoControle">
									<tr>
										<td>${tipoControle.empresa.nome }</td>
										<td>${tipoControle.organizacao.nome }</td>
										<td>${tipoControle.nome }</td>
										<td>
											<label class="checkbox inline">
												<input type="checkbox" id="isActiveLine" name="tipoControle.isActive"
												<c:if test="${tipoControle.isActive == true }"> checked="checked"</c:if> onchange="altera(this,'isActiveLine','${tipoControle.tipoControle_id}');">
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