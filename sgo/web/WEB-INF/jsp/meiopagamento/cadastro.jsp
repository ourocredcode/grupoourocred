<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#meiopagamento-li-a').click(function() {
		window.location.href = '<c:url value="/meiopagamento/cadastro" />';
	});

	$('#parceiroinfobanco-li-a').click(function() {
		window.location.href = '<c:url value="/parceiroinfobanco/cadastro" />';
	});

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/meiopagamento/cadastro" />';
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

	$("#meioPagamentoIsActive").change(function(e){
		if(document.meioPagamentoForm.meioPagamentoIsActive.checked==true){
			document.meioPagamentoForm.meioPagamentoIsActive.value=true;
		}else{
			document.meioPagamentoForm.meioPagamentoIsActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.meioPagamentoForm.reset();
	}
}
</script>

<div id="content-header">
	<h1>Cadastro Meio Pagamento</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
		<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
	<a href="#" class="current">Meio Pagamento</a>
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
				<li class="active" id="meiopagamento-li"><a href="#meiopagamento-div" data-toggle="tab" id="meiopagamento-li-a">Cadastro Meio Pagamento</a></li>
				<li class="" id="parceiroinfobanco-li"><a href="#parceiroinfobanco-div" data-toggle="tab" id="parceiroinfobanco-li-a">Informações Bancárias Parceiros</a></li>				
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="meiopagamento-div">														
					<form id="meioPagamentoForm" name="meioPagamentoForm" action="<c:url value="/meiopagamento/salva"/>" method="POST">
						<div class="row-fluid">
							<div class="span3">
								<label for="meioPagamentoEmpresa">Empresa</label>
		      					<input class="input-xlarge" id="meioPagamentoEmpresa" name="meioPagamento.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" readonly="readonly">
		      					<input class="span1" id="meioPagamentoEmpresaId" name="meioPagamento.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden">
	    					</div>
							<div class="span3">
								<label for="meioPagamentoOrganizacao">Organização</label>
		      					<input class="input-xlarge" id="meioPagamentoOrganizacao" name="meioPagamento.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" readonly="readonly">
		      					<input class="span1" id="meioPagamentoOrganizacaoId" name="meioPagamento.organizacao.organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" type="hidden">
							</div>
							<div class="span3">
								<label for="meioPagamentoNome">Nome</label>
								<input class="input-xlarge" type="text" id="meioPagamentoNome" name="meioPagamento.nome" value="${meioPagamento.nome }" placeholder="Nome" required>
							</div>
							<div class="span1">
								<label for="meioPagamentoIsActive">Ativo</label>
								<input type="checkbox" id="meioPagamentoIsActive" name="meioPagamento.isActive" checked="checked" value="1" >
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

				<div class="tab-pane fade" id="parceiroinfobanco-div"></div>

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
					<h5>Meio Pagamento</h5>
				</div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty meiosPagamento}">
						<table
							class="table table-bordered table-striped table-hover data-table"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>Empresa</th>
									<th>Organizacao</th>
									<th>Meio Pagamento</th>
									<th>Ativo</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${meiosPagamento }" var="meioPagamento">
									<tr>
										<td>${meioPagamento.empresa.nome }</td>
										<td>${meioPagamento.organizacao.nome }</td>
										<td>${meioPagamento.nome }</td>
										<td>${bancoProduto.isActive}</td>
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
