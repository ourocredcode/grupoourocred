<%@ include file="/header.jspf"%>

<link rel="stylesheet" href="<c:url value="/css/select2.css"/>" />
<script type="text/javascript" src="<c:url value="/js/select2.js"/>"></script>

<script type="text/javascript">
jQuery(function($){

	$('#empresa-li-a').click(function() {
		window.location.href = '<c:url value="/empresa/cadastro" />';
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
		window.location.href = '<c:url value="/empresa/cadastro" />';
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});

	$("#isActive").change(function(e){
		if(document.empresaForm.isActive.checked==true){
			document.empresaForm.isActive.value=true;
		}else{
			document.empresaForm.isActive.value=false;
		}
	});
	
	 $('#loading').ajaxStart(function() {
		 $(this).show();
		 $('#resultado').hide();
		 }).ajaxStop(function() {
		 $(this).hide();
		 $('#resultado').fadeIn('fast');
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.empresaForm.reset();
	}
}

function altera(linha, id) {

	var valor = linha.checked == true ? true : false ;

	if (window.confirm("Deseja alterar a Organização selecionado?"))
		$.post('<c:url value="/empresa/altera" />', {
			'empresa.empresa_id' : id, 'empresa.isActive' : valor
		});

	return false;
}

</script>

<div id="content-header">
	<h1>Cadastro Empresa</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
	<a href="#" class="current">Empresa</a>
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
				<li class="active" id="empresa-li"><a href="#empresa-div" data-toggle="tab" id="empresa-li-a">Empresa</a></li>				
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="empresa-div">

					<form id="empresaForm" name="empresaForm" action="<c:url value="/empresa/salva"/>" method="POST">					
						<div class="row-fluid">
							<div class="span3">
								<label for="nome">Nome</label>
								<input class="input-xlarge" id="nome" name="empresa.nome" placeholder="Nome" type="text" required>
							</div>
							<div class="span3">
								<label for="descricao">Descrição</label>
								<input class="input-xlarge" id="descricao" name="empresa.descricao" placeholder="Descrição" type="text" required>
							</div>
							<div class="span2">
								<label for="idiomaId">Idioma</label>
								<select  id="idiomaId" name="idioma.idioma_id" class="input-medium">
									<option value="">Selecione o Idioma</option>
									<c:forEach items="${idiomas }" var="idioma">
										<option value="${idioma.idioma_id }"> ${idioma.nome }</option>
									</c:forEach>
								</select>
							</div>
							<div class="span1">
								<label for="isActive">Ativo</label>
								<input id="isActive" name="empresa.isActive" type="checkbox" checked="checked" value="1" >
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
					<h5>Organizacao</h5>
				</div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty empresas}">
						<table
							class="table table-bordered table-striped table-hover data-table"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>Empresa</th>
									<th>Ativo</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${empresas }" var="empresa">
									<tr>
										<td>${empresa.nome }</td>
										<td>
											<label class="checkbox inline">
												<input type="checkbox" id="isActiveLine" name="empresa.isActive"
												<c:if test="${empresa.isActive == true }"> checked="checked"</c:if> onchange="altera(this,'${empresa.empresa_id}');">
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