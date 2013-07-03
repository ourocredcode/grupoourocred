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
		window.location.href = '<c:url value="/produto/cadastro" />';
	});

	$('#btnNovo').click(function() {
		document.produtoForm.reset();
	});

	$("#produtoIsActive").change(function(e){
		if(document.produtoForm.produtoIsActive.checked==true){
			document.produtoForm.produtoIsActive.value=true;
		}else{
			document.produtoForm.produtoIsActive.value=false;
		}
	});
	
	$("#produtoIsProdutoContrato").change(function(e){
		if(document.produtoForm.produtoIsProdutoContrato.checked==true){
			document.produtoForm.produtoIsProdutoContrato.value=true;
		}else{
			document.produtoForm.produtoIsProdutoContrato.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.produtoForm.reset();
	}
}

function buscaSubGrupoProduto(){

	var grupoProduto_id = $('#produtoGrupoProdutoId').val();

	$("#produtoSubGrupoProdutoId").load('<c:url value="/produto/subgrupoprodutos" />',
			{'grupoProduto_id' : grupoProduto_id});

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
				<li class="active" id="produto-li"><a href="#produto-div" data-toggle="tab" id="produto-li-a">Produtos</a></li>
				<li class="" id="grupoproduto-li"><a href="#grupoproduto-div" data-toggle="tab" id="grupoproduto-li-a">Grupo de Produtos</a></li>
				<li class="" id="subgrupoproduto-li"><a href="#subgrupoproduto-div" data-toggle="tab" id="subgrupoproduto-li-a">Sub Grupo de Produtos</a></li>
			</ul>
			
			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="produto-div" >					
					<form id="produtoForm" name="produtoForm" action="<c:url value="/produto/salva"/>" method="POST">

						<div class="row-fluid">
							<div class="span3">
								<label for="produtoEmpresa">Empresa</label>									
	      						<input class="input-xlarge" id="produtoEmpresa" name="produto.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" required readonly="readonly">
	      						<input class="span1" id="produtoEmpresaId" name="produto.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden">
	    					</div>

							<div class="span3">
								<label for="produtoOrganizacao">Organização</label>									
	      						<input class="input-xlarge" id="produtoOrganizacao" name="produto.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" required readonly="readonly">
	      						<input class="span1" id="produtoOrganizacaoId" name="produto.organizacao.organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" type="hidden">
							</div>
						</div>
						<div class="row-fluid">
							<div class="span2">
								<label for="produtoGrupoProduto">Grupo Produto</label>
								<select id="produtoGrupoProdutoId" name="produto.grupoProduto.grupoProduto_id" onchange="buscaSubGrupoProduto();" class="input-medium">
									<option value="">Selecione o grupo...</option>
									<c:forEach var="grupoProduto" items="${gruposProduto }">
									 	<option value="${grupoProduto.grupoProduto_id }" > ${grupoProduto.nome }</option>
									</c:forEach>
								</select>
							</div>
							<div class="span2">
	      						<label for="produtoSubGrupoProduto">Sub Grupo Produto</label>
	      						<select id="produtoSubGrupoProdutoId" name="produto.subGrupoProduto.subGrupoProduto_id" class="input-medium">
	      							<option value="">Selecion um Grupo Produto...</option>
	      						</select>
							</div>
							<div class="span2">
								<label for="produtoCategoriaProdutoId">Categoria Produto</label>
								<select id="produtoCategoriaProdutoId" name="produto.categoriaProduto.categoriaProduto_id" class="input-medium">
									<!--option value="">Selecione o grupo...</option-->								
									<c:forEach var="categoriaProduto" items="${categoriasProduto }">
									 	<option value="${categoriaProduto.categoriaProduto_id }" > ${categoriaProduto.nome }</option>
									</c:forEach>
								</select>
							</div>
							<div class="span2">
								<label for="produtoTipoProdutoId">Tipo Produto</label>
								<select id="produtoTipoProdutoId" name="produto.tipoProduto.tipoProduto_id" class="input-xlarge">
									<!--option value="">Selecione o grupo...</option-->
									<c:forEach var="tipoProduto" items="${tiposProduto }">
									 	<option value="${tipoProduto.tipoProduto_id }" > ${tipoProduto.nome }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span3">
								<label for="produtoNome">Nome</label>
								<input class="input-xlarge" id="produtoNome" name="produto.nome" placeholder="Nome" type="text" required>
							</div>
							<div class="span1">
								<label for="produtoIsActive">Ativo</label>
								<input type="checkbox" id="produtoIsActive" name="produto.isActive" checked="checked" value="1" >
							</div>
							<div class="span1">
								<label for="produtoIsProdutoContrato">P/C</label>
								<input type="checkbox" id="produtoIsProdutoContrato" name="produto.isProdutoContrato" checked="checked" value="1" >
							</div>
						</div>
						<div class="row-fluid">
						 	<div class="btn-group">
								<button type="submit" class="btn btn-primary" id="btnSalvar">Salvar</button>
							</div>
							<div class="btn-group">
								<button type="button" class="btn btn-primary" id="btnNovo" >Novo</button>
							</div>
							<div class="btn-group">
								<button type="button" class="btn btn-primary" id="btnSair" >Sair</button>
							</div>
						</div>				
					</form>
				</div>

				<div class="tab-pane fade" id="grupoproduto-div"></div>
				<div class="tab-pane fade" id="subgrupoproduto-div"></div>

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
					<c:if test="${not empty produtos}">
						<table
							class="table table-bordered table-striped table-hover data-table"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>Produto</th>
									<th>categoriaProduto</th>
									<th>grupoProduto</th>
									<th>subGrupoProduto</th>
									<th>tipoProduto</th>
									<th>Ativo</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${produtos }" var="produto">
									<tr>
										<td>${produto.nome }</td>
										<td>${produto.categoriaProduto.nome }</td>
										<td>${produto.grupoProduto.nome }</td>
										<td>${produto.subGrupoProduto.nome }</td>
										<td>${produto.tipoProduto.nome }</td>
										<td>${produto.isActive }</td>
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