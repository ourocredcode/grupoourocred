<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#convenio-li-a').click(function() {
		window.location.href = '<c:url value="/convenio/cadastro" />';
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
		window.location.href = '<c:url value="/convenio/cadastro" />';
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});

	$("#convenioIsActive").change(function(e){
		if(document.convenioForm.convenioIsActive.checked==true){
			document.convenioForm.convenioIsActive.value=true;
		}else{
			document.convenioForm.convenioIsActive.value=false;
		}
	});

});

function altera(linha, atributo, id) {

	if(atributo=='isActiveLine'){

		var isActive = linha.checked == true ? true : false;

		if (window.confirm("Deseja alterar o Convênio selecionado?"))
			$.post('<c:url value="/convenio/altera" />', {'convenio.convenio_id' : id, 'convenio.isActive' : isActive});

	}

	return false;
}

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.convenioForm.reset();
	}
}

</script>

<div id="content-header">
	<h1>Cadastro Convênio</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
	<a href="#" class="current">Convênio</a>
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

				<div class="tab-pane fade active in" id="convenio-div">

					<form id="convenioForm" name="convenioForm" action="<c:url value="/convenio/salva"/>" method="POST">
						<div class="row-fluid">
							<div class="span3">
								<label for="convenioEmpresa">Empresa</label>
	      						<input class="input-xlarge" id="convenioEmpresa" name="convenio.empresa.nome" type="text" value="${usuarioInfo.empresa.nome }" readonly="readonly">
	      						<input class="span1" id="convenioEmpresaId" name="convenio.empresa.empresa_id" type="hidden" value="${usuarioInfo.empresa.empresa_id }" >
							</div>
							<div class="span3">
								<label for="convenioOrganizacao">Organização</label>
	      						<input class="input-xlarge" id="convenioOrganizacao" name="convenio.organizacao.nome" type="text" value="${usuarioInfo.organizacao.nome }" readonly="readonly">
	      						<input class="span1" id="convenioOrganizacaoId" name="convenio.organizacao.organizacao_id" type="hidden" value="${usuarioInfo.organizacao.organizacao_id }">
	    					</div>						
							<div class="span3">
								<label for="convenioNome">Nome</label>
								<input class="input-xlarge" id="convenioNome" name="convenio.nome" placeholder="Nome" type="text" required>
							</div>						
							<div class="span3">
								<label for="convenioIsActive">Ativo</label>
								<input id="convenioIsActive" name="convenio.isActive" type="checkbox" checked="checked" value="1" >
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
					<h5>Convênio</h5>
				</div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty convenios}">
						<table
							class="table table-bordered table-striped table-hover data-table"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>Empresa</th>
									<th>Organização</th>
									<th>Tipo Produto</th>
									<th>Ativo</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${convenios }" var="convenio">
									<tr>
										<td>${convenio.empresa.nome }</td>
										<td>${convenio.organizacao.nome }</td>
										<td>${convenio.nome }</td>										
										<td>
											<label class="checkbox inline">
												<input type="checkbox" id="isActiveLine" name="convenio.isActive"
												<c:if test="${convenio.isActive == true }"> checked="checked"</c:if> onchange="altera(this,'isActiveLine','${convenio.convenio_id}');">
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