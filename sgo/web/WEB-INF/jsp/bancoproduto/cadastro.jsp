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

	$('#bancoProdutoProduto').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/produto/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#bancoProdutoEmpresaId').val() == '' ? '0' :  $('#bancoProdutoEmpresaId').val(),
	        		  organizacao_id: $('#bancoProdutoOrganizacaoId').val() == '' ? '0' :  $('#bancoProdutoOrganizacaoId').val(),
	        		  nome : $('#bancoProdutoProduto').val()},
	          success : function(data) {

	        	  if (!data || data.length == 0) {
	     	            $('#bancoProdutoProduto').val('');
	     	           $('#bancoProdutoProdutoId').val('');
	     	        }

	        	  response($.map(data, function(produto) {
	        		  return {
	        			  label: produto.nome,
	        			  value: produto.produto_id
	                  };
	              }));
	           }
	        });
	     },
	     focus: function( event, ui ) {
	      	 $('#bancoProdutoProduto').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#bancoProdutoProduto').val(ui.item.label);
	         $('#bancoProdutoProdutoId').val(ui.item.value);
	         return false;
	     }
	});

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/bancoproduto/cadastro" />';
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});

	$("#bancoProdutoIsActive").change(function(e){
		if(document.bancoProdutoForm.bancoProdutoIsActive.checked==true){
			document.bancoProdutoForm.bancoProdutoIsActive.value=true;
		}else{
			document.bancoProdutoForm.bancoProdutoIsActive.value=false;
		}
	});

	$("#bancoProdutoIsWorkflow").change(function(e){
		if(document.bancoProdutoForm.bancoProdutoIsWorkflow.checked==true){
			document.bancoProdutoForm.bancoProdutoIsWorkflow.value=true;
		}else{
			document.bancoProdutoForm.bancoProdutoIsWorkflow.value=false;
		}
	});

});

function altera(linha, atributo, id) {

	alert(linha.checked);

	if(atributo == 'isWorkflowLine'){

		var isWorkflow = linha.checked == true ? true : false;
		alert(isWorkflow);
		if (window.confirm("Deseja alterar o Banco do Produto selecionado?"))
			$.post('<c:url value="/bancoproduto/altera" />', {
				'bancoProduto.bancoProduto_id' : id, 'bancoProduto.isWorkflow' : isWorkflow
			});

	}
	
	if(atributo=='isActiveLine'){

		var isActive = linha.checked == true ? true : false;
		alert(isActive);
		if (window.confirm("Deseja alterar o Banco do Produto selecionado?"))
			$.post('<c:url value="/bancoproduto/altera" />', {'bancoProduto.bancoProduto_id' : id, 'bancoProduto.isActive' : isActive});

	}
	return false;
}

function limpaForm(){
	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.bancoProdutoForm.reset();
	}
}

</script>

	<div id="content-header">
		<h1>Cadastro Banco Produto</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
	</div>

	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
		<a href="#" class="current">Banco Produto</a>
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
				<li class="" id="tabela-li"><a href="#tabela-div" data-toggle="tab" id="tabela-li-a">Tabela</a></li>
				<li class="active" id="bancoproduto-li"><a href="#bancoproduto-div" data-toggle="tab" id="bancoproduto-li-a">Banco Produto</a></li>
				<li class="" id="bancoprodutotabela-li"><a href="#bancoprodutotabela-div" data-toggle="tab" id="bancoprodutotabela-li-a">Banco Produto Tabela</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade" id="tipotabela-div"></div>
				<div class="tab-pane fade" id="tabela-div"></div>

				<div class="tab-pane fade active in" id="bancoproduto-div">

					<form id="bancoProdutoForm" name="bancoProdutoForm" action="<c:url value="/bancoproduto/salva"/>" method="POST">

						<div class="row-fluid">
							<div class="span3">
								<label for="bancoProdutoEmpresa">Empresa</label>
	      						<input class="input-xlarge" id="bancoProdutoEmpresa" name="bancoProduto.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" readonly="readonly">
	      						<input class="span1" id="bancoProdutoEmpresaId" name="bancoProduto.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden">
							</div>
							<div class="span3">
								<label for="bancoProdutoOrganizacao">Organização</label>
	      						<input class="input-xlarge" id="bancoProdutoOrganizacao" name="bancoProduto.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" readonly="readonly">
	      						<input class="span1" id="bancoProdutoOrganizacaoId" name="bancoProduto.organizacao.organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" type="hidden">
							</div>
						</div>
						<div class="row-fluid">
							<div class="span2">
								<label for="bancoProdutoBanco">Banco</label>
								<select class="input-medium" id="bancoProdutoBancoId" name="bancoProduto.banco.banco_id" required>
									<option value=""></option>
									<c:forEach var="banco" items="${bancos }">
									 	<option value="${banco.banco_id }">${banco.nome }</option>
									</c:forEach>
								</select>
							</div>
							<div class="span3">
								<label for="bancoProdutoProduto">Produto</label>
	      						<input class="input-xlarge" id="bancoProdutoProduto" name="bancoProduto.produto.nome" value="${bancoProduto.produto.nome }" type="text" required>
	      						<input class="span1" id="bancoProdutoProdutoId" name="bancoProduto.produto.produto_id" value="${bancoProduto.produto.produto_id }" type="hidden">
							</div>
							<div class="span3">
								<label for="bancoProdutoWorkflow">Worflow</label>
								<select class="input-xlarge" id="bancoProdutoWorkflowId" name="bancoProduto.workflow.workflow_id" required>
									<option value=""></option>
									<c:forEach var="workflow" items="${workflows }">
									 	<option value="${workflow.workflow_id }">${workflow.nome }</option>
									</c:forEach>
								</select>
							</div>
							<div class="span1">
								<label for="bancoProdutoIsActive">Ativo</label>
								<input class="span1" id="bancoProdutoIsActive" name="bancoProduto.isActive" type="checkbox" checked="checked" value="1" >
							</div>
							<div class="span1">
								<label for="bancoProdutoIsWorkflow">Workflow</label>
								<input class="span1" id="bancoProdutoIsWorkflow" name="bancoProduto.isWorkflow" type="checkbox" checked="checked" value="1" >
							</div>
						</div>
						<div class="btn-toolbar">
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
					<h5>Banco Produto</h5>
				</div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty bancoProdutos}">
						<table
							class="table table-bordered table-striped table-hover data-table"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>Banco</th>
									<th>Produto</th>
									<th>Workflow</th>
									<th>IsWorkflow</th>
									<th>Ativo</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${bancoProdutos }" var="bancoProduto">
									<tr>
										<td>${bancoProduto.banco.nome }</td>
										<td>${bancoProduto.produto.nome }</td>
										<td>${bancoProduto.workflow.nome }</td>
										<td>
											<label class="checkbox inline">
												<input type="checkbox" id="isWorkflowLine" name="bancoProduto.isWorkflow"
												<c:if test="${bancoProduto.isWorkflow == true }"> checked="checked"</c:if> onchange="altera(this,'isWorkflowLine','${bancoProduto.bancoProduto_id}');">
											</label>
										</td>
										<td>
											<label class="checkbox inline">
												<input type="checkbox" id="isActiveLine" name="bancoProduto.isActive"
												<c:if test="${bancoProduto.isActive == true }"> checked="checked"</c:if> onchange="altera(this,'isActiveLine','${bancoProduto.bancoProduto_id}');">
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