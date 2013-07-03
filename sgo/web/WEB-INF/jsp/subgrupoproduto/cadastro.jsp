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
		window.location.href = '<c:url value="/subgrupoproduto/cadastro" />';
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});

	$("#subGrupoProdutoIsActive").change(function(e){
		if(document.subGrupoProdutoForm.subGrupoProdutoIsActive.checked==true){
			document.subGrupoProdutoForm.subGrupoProdutoIsActive.value=true;
		}else{
			document.subGrupoProdutoForm.subGrupoProdutoIsActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.subGrupoProdutoForm.reset();
	}
}
</script>

<div id="content-header">
	<h1>Cadastro Produto</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
	<a href="#" class="current">Produto</a>
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
				<li class="" id="grupoproduto-li"><a href="#grupoproduto-div" data-toggle="tab" id="grupoproduto-li-a">Grupo de Produtos</a></li>
				<li class="active" id="subgrupoproduto-li"><a href="#subgrupoproduto-div" data-toggle="tab" id="subgrupoproduto-li-a">Sub Grupo de Produtos</a></li>
			</ul>
			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade" id="produto-div"></div>
				<div class="tab-pane fade" id="grupoproduto-div"></div>

				<div class="tab-pane fade active in" id="subgrupoproduto-div">
					<form id="subGrupoProdutoForm" name="subGrupoProdutoForm" action="<c:url value="/subgrupoproduto/salva"/>" method="POST">
						<div class="row-fluid">
							<div class="span3">
								<label for="subGrupoProdutoEmpresa">Empresa</label>
	      						<input class="input-xlarge" id="subGrupoProdutoEmpresa" name="subGrupoProduto.empresa.nome" type="text" value="${usuarioInfo.empresa.nome }" readonly="readonly">
	      						<input class="span1" id="subGrupoProdutoEmpresaId" name="subGrupoProduto.empresa.empresa_id" type="hidden" value="${usuarioInfo.empresa.empresa_id }">
	    					</div>								
							<div class="span3">
								<label for="subGrupoProdutoOrganizacao">Organização</label>
	      						<input class="input-xlarge" id="subGrupoProdutoOrganizacao" name="subGrupoProduto.organizacao.nome" type="text" value="${usuarioInfo.organizacao.nome }" readonly="readonly">
	      						<input class="span1" id="subGrupoProdutoOrganizacaoId" name="subGrupoProduto.organizacao.organizacao_id" type="hidden" value="${usuarioInfo.organizacao.organizacao_id }">
	    					</div>
						</div>
						<div class="row-fluid">
							<div class="span2">
								<label for="subGrupoProdutoGrupoProduto">Grupo Produto</label>
								<select id="subGrupoProdutoGrupoProdutoId" name="subGrupoProduto.grupoProduto.grupoProduto_id" class="input-medium">
									<!-- option value="">Selecione o grupo...</option-->
									<c:forEach var="grupoProduto" items="${gruposProduto }">
									 	<option value="${grupoProduto.grupoProduto_id }" > ${grupoProduto.nome }</option>
									</c:forEach>
								</select>
							</div>
							<div class="span3">
								<label for="subGrupoProdutoNome">Nome</label>
								<input class="input-xlarge" type="text" id="subGrupoProdutoNome" name="subGrupoProduto.nome" placeholder="Nome" required>
							</div>								
							<div class="span1">
								<label for="subGrupoProdutoIsActive">Ativo</label>
								<input type="checkbox" id="subGrupoProdutoIsActive" name="subGrupoProduto.isActive" checked="checked" value="1" >							
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
					<h5>Sub Grupo Produtos</h5>
				</div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty subGrupoProdutos}">
						<table
							class="table table-bordered table-striped table-hover data-table"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>Grupo Produto</th>
									<th>Sub Grupo Produto</th>
									<th>Ativo</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${subGrupoProdutos }" var="subGrupoProduto">
									<tr>
										<td>${subGrupoProduto.grupoProduto.nome }</td>
										<td>${subGrupoProduto.nome }</td>
										<td>${subGrupoProduto.isActive}</td>
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