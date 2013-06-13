<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#tipotabela-li-a').click(function() {
		window.location.href = '<c:url value="/tipotabela/cadastro" />';
	});
	
	$('#tabela-li-a').click(function() {
		window.location.href = '<c:url value="/tabela/cadastro" />';
	});

	$('#bancoproduto-li-a').click(function() {
		window.location.href = '<c:url value="/bancoproduto/cadastro" />';
	});

	$('#bancoprodutotabela-li-a').click(function() {
		window.location.href = '<c:url value="/bancoprodutotabela/cadastro" />';
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
		window.location.href = '<c:url value="/tipotabela/cadastro" />';
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});

	$("#tipoTabelaIsActive").change(function(e){
		if(document.tipoTabelaForm.tipoTabelaIsActive.checked==true){
			document.tipoTabelaForm.tipoTabelaIsActive.value=true;
		}else{
			document.tipoTabelaForm.tipoTabelaIsActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.tipoTabelaForm.reset();
	}
}
</script>

<div id="content-header">
	<h1>Cadastro Tipo Tabela</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
		<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
	<a href="#" class="current">Tipo Tabela</a>
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
				<li class="active" id="tipotabela-li"><a href="#tipotabela-div" data-toggle="tab" id="tipotabela-li-a">Tipo Tabela</a></li>
				<li class="" id="tabela-li"><a href="#tabela-div" data-toggle="tab" id="tabela-li-a">Tabela</a></li>
				<li class="" id="bancoproduto-li"><a href="#bancoproduto-div" data-toggle="tab" id="bancoproduto-li-a">Banco Produto</a></li>
				<li class="" id="bancoprodutotabela-li"><a href="#bancoprodutotabela-div" data-toggle="tab" id="bancoprodutotabela-li-a">Banco Produto Tabela</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="tipotabela-div">

					<form id="tipoTabelaForm" name="tipoTabelaForm" action="<c:url value="/tipotabela/salva"/>" method="POST">
						<div class="row-fluid">
							<div class="span3">
								<label for="tipoTabelaEmpresa">Empresa</label>
	      						<input class="input-xlarge" id="tipoTabelaEmpresa" name="tipoTabela.empresa.nome" type="text" value="${usuarioInfo.empresa.nome }" readonly="readonly">
	      						<input class="span1" id="tipoTabelaEmpresaId" name="tipoTabela.empresa.empresa_id" type="hidden" value="${usuarioInfo.empresa.empresa_id }" >
							</div>
							<div class="span3">
								<label for="tipoTabelaOrganizacao">Organização</label>
	      						<input class="input-xlarge" id="tipoTabelaOrganizacao" name="tipoTabela.organizacao.nome" type="text" value="${usuarioInfo.organizacao.nome }" readonly="readonly">
	      						<input class="span1" id="tipoTabelaOrganizacaoId" name="tipoTabela.organizacao.organizacao_id" type="hidden" value="${usuarioInfo.organizacao.organizacao_id }">
	    					</div>						
							<div class="span3">
								<label for="tipoTabelaNome">Nome</label>
								<input class="input-xlarge" id="tipoTabelaNome" name="tipoTabela.nome" placeholder="Nome" type="text" required>
							</div>						
							<div class="span3">
								<label for="tipoTabelaIsActive">Ativo</label>
								<input id="tipoTabelaIsActive" name="tipoTabela.isActive" type="checkbox" checked="checked" value="1" >
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

				<div class="tab-pane fade" id="tabela-div"></div>
				<div class="tab-pane fade" id="bancoproduto-div"></div>
				<div class="tab-pane fade" id="bancoprodutotabela-div"></div>

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
					<h5>Tipo Tabela</h5>
				</div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty tiposTabela}">
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
								<c:forEach items="${tiposTabela }" var="tipoTabela">
									<tr>
										<td>${tipoTabela.empresa.nome }</td>
										<td>${tipoTabela.organizacao.nome }</td>
										<td>${tipoTabela.nome }</td>
										<td>${tipoTabela.isActive }</td>
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