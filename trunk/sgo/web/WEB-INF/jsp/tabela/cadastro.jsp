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
		window.location.href = '<c:url value="/tabela/cadastro" />';
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});

	$("#tabelaIsActive").change(function(e){
		if(document.tabelaForm.tabelaIsActive.checked==true){
			document.tabelaForm.tabelaIsActive.value=true;
		}else{
			document.tabelaForm.tabelaIsActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.tabelaForm.reset();
	}
}
</script>

<div id="content-header">
	<h1>Cadastro Tabela</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
		<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
	<a href="#" class="current">Tabela</a>
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
				<li class="" id="tipotabela-li"><a href="#tipotabela-div" data-toggle="tab" id="tipotabela-li-a">Tipo Tabela</a></li>
				<li class="active" id="tabela-li"><a href="#tabela-div" data-toggle="tab" id="tabela-li-a">Tabela</a></li>
				<li class="" id="bancoproduto-li"><a href="#bancoproduto-div" data-toggle="tab" id="bancoproduto-li-a">Banco Produto</a></li>
				<li class="" id="bancoprodutotabela-li"><a href="#bancoprodutotabela-div" data-toggle="tab" id="bancoprodutotabela-li-a">Banco Produto Tabela</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade" id="tipotabela-div"></div>
				
				<div class="tab-pane fade active in" id="tabela-div" >

					<form id="tabelaForm" name="tabelaForm" action="<c:url value="/tabela/salva"/>" method="POST">
						<div class="row-fluid">
							<div class="span4">
								<label for="tabelaEmpresa">Empresa</label>
	      						<input class="input-xlarge" id="tabelaEmpresa" name="tabela.empresa.nome" type="text" value="${usuarioInfo.empresa.nome }" readonly="readonly">
	      						<input class="span1" id="tabelaEmpresaId" name="tabela.empresa.empresa_id" type="hidden" value="${usuarioInfo.empresa.empresa_id }">
							</div>
							<div class="span3">
								<label for="tabelaOrganizacao">Organização</label>
								<input class="input-xlarge" id="tabelaOrganizacao" name="tabela.organizacao.nome" type="text" value="${usuarioInfo.organizacao.nome }" readonly="readonly">
	      						<input class="span1" id="tabelaOrganizacaoId" name="tabela.organizacao.organizacao_id" type="hidden" value="${usuarioInfo.organizacao.organizacao_id }">				    				
							</div>
						</div>
						<div class="row-fluid">
							<div class="span2">
								<label for="tabelaTipoTabela">Tipo Tabela</label>
								<select class="input-medium" id="tabelaTipoTabelaId" name="tabela.tipoTabela.tipoTabela_id">
									<c:forEach var="tipoTabela" items="${tiposTabela }">
									 	<option value="${tipoTabela.tipoTabela_id }"> ${tipoTabela.nome }</option>
									</c:forEach>
								</select>
							</div>
							<div class="span3">
								<label for="tabelaNome">Nome</label>
								<input class="input-xlarge" type="text" id="tabelaNome" name="tabela.nome" placeholder="Nome" required>
							</div>
							<div class="span2">
								<label for="tabelaIsActive">Ativo</label>
								<input type="checkbox" id="tabelaIsActive" name="tabela.isActive" checked="checked" value="1" >
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
					<h5>Tabela</h5>
				</div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty tabelas}">
						<table
							class="table table-bordered table-striped table-hover data-table"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>Empresa</th>
									<th>Organização</th>
									<th>Tipo Tabela</th>
									<th>Tabela</th>
									<th>Ativo</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${tabelas }" var="tabela">
									<tr>
										<td>${tabela.empresa.nome }</td>
										<td>${tabela.organizacao.nome }</td>
										<td>${tabela.tipoTabela.nome }</td>
										<td>${tabela.nome }</td>
										<td>${tabela.isActive}</td>
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