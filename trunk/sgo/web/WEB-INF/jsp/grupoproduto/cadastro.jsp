<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#produto-li-a').click(function() {
		window.location.href = '<c:url value="/produto/cadastro" />';
	});

	$('#grupoproduto-li-a').click(function() {
		window.location.href = '<c:url value="/grupoproduto/cadastro" />';
	});

	$('#subgrupoproduto-li-a').click(function() {
		window.location.href = '<c:url value="/subgrupoproduto/cadastro" />';
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
		window.location.href = '<c:url value="/grupoproduto/cadastro" />';
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});

	$("#grupoProdutoIsActive").change(function(e){
		if(document.grupoProdutoForm.grupoProdutoIsActive.checked==true){
			document.grupoProdutoForm.grupoProdutoIsActive.value=true;
		}else{
			document.grupoProdutoForm.grupoProdutoIsActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.grupoProdutoForm.reset();
	}
}
</script>

<div id="content-header">
		<h1>Cadastro Produto</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
		<a href="#" class="current">Grupo Produto</a>
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
				<li class="" id="produto-li"><a href="#produto-div" data-toggle="tab" id="produto-li-a">Produtos</a></li>
				<li class="active" id="grupoproduto-li"><a href="#grupoproduto-div" data-toggle="tab" id="grupoproduto-li-a">Grupo de Produtos</a></li>
				<li class="" id="subgrupoproduto-li"><a href="#subgrupoproduto-div" data-toggle="tab" id="subgrupoproduto-li-a">Sub Grupo de Produtos</a></li>
			</ul>
			
			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade" id="produto-div" ></div>

				<div class="tab-pane fade active in" id="grupoproduto-div">				
					<form id="grupoProdutoForm" name="grupoProdutoForm" action="<c:url value="/grupoproduto/salva"/>" method="POST">
						<div class="row-fluid">
							<div class="span3">
								<label for="grupoProdutoEmpresa">Empresa</label>
	      						<input class="input-xlarge" id="grupoProdutoEmpresa" name="grupoProduto.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" required onChange="limpaForm();" readonly="readonly">
	      						<input class="span1" id="grupoProdutoEmpresaId" name="grupoProduto.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden">
	    					</div>
							<div class="span3">
								<label for="grupoProdutoOrganizacao">Organização</label>
	      						<input class="input-xlarge" id="grupoProdutoOrganizacao" name="grupoProduto.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" required onChange="limpaForm();" readonly="readonly">
	      						<input class="span1" id="grupoProdutoOrganizacaoId" name="grupoProduto.organizacao.organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" type="hidden">
	    					</div>
						</div>
						<div class="row-fluid">
							<div class="span3">
								<label for="grupoProdutoNome">Nome</label>
								<input class="input-xlarge" type="text" id="grupoProdutoNome" name="grupoProduto.nome" placeholder="Nome" required>
							</div>
							<div class="span1">
								<label for="grupoProdutoIsActive">Ativo</label>								
								<input id="grupoProdutoIsActive" name="grupoProduto.isActive" type="checkbox" checked="checked" value="1" >
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
				
				<div class="tab-pane fade" id="subgrupoproduto-div" ></div>
				
				<table class="table table-striped table-bordered">
					<thead>
						
					</thead>
					<tbody>	
						
					</tbody>
				</table>
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
					<h5>Produtos</h5>
				</div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty gruposProduto}">
						<table
							class="table table-bordered table-striped table-hover data-table"
							style="font-size: 12px">
							<thead>
								<tr>
							<th>Empresa</th>
							<th>Organização</th>
							<th>Nome</th>
						</tr>
							</thead>
							<tbody>
								<c:forEach items="${gruposProduto }" var="grupoProduto">
							<tr>
								<td>${grupoProduto.empresa.nome }</td>
								<td>${grupoProduto.organizacao.nome }</td>
								<td>${grupoProduto.nome }</td>
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