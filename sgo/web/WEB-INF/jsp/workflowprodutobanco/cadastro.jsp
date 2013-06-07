<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#workflowProdutoBancoprodutobanco-li-a').click(function() {
		window.location.href = '<c:url value="/workflowprodutobanco/cadastro" />';
	});

	$('#workflowProdutoBancoEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
	          success : function(data) {  

	       		  if (!data || data.length == 0) {
	       	            $('#perfilEmpresa').val('');
						$('#perfilEmpresaId').val('');
	       	        }

	        	  response($.map(data, function(empresa) {  
	        		  return {
	                      label: empresa.nome,
	                      value: empresa.empresa_id
	                  };
	              }));  
	           }
	        });
	     } ,
	     focus: function( event, ui ) {
	    	 $('#workflowProdutoBancoEmpresa').val(ui.item.label);
	         return false;
	     } ,
	     select: function( event, ui ) {
	    	 $('#workflowProdutoBancoEmpresa').val(ui.item.label);
	         $('#workflowProdutoBancoEmpresaId').val(ui.item.value);
	         return false;
	     }
	});

	$('#workflowProdutoBancoOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",

	          data : {empresa_id: $('#workflowProdutoBancoEmpresaId').val() == '' ? '0' :  $('#workflowProdutoBancoEmpresaId').val(),
	        		  org_nome : $('#workflowProdutoBancoOrganizacao').val()},

	          success : function(data) {  

	        	  if (!data || data.length == 0) {
	     	            $('#workflowProdutoBancoOrganizacao').val('');
	     	           $('#workflowProdutoBancoOrganizacaoId').val('');
	     	        }

	        	  response($.map(data, function(organizacao) {  
	        		  return {
	        			  label: organizacao.nome,
	        			  value: organizacao.organizacao_id
	                  };
	              }));  
	           }
	        });
	     },
	     focus: function( event, ui ) {
	      	 $('#workflowProdutoBancoOrganizacao').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#workflowProdutoBancoOrganizacao').val(ui.item.label);
	         $('#workflowProdutoBancoOrganizacaoId').val(ui.item.value);
	         return false;
	     }
	});

	$('#workflowProdutoBancoProduto').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/produto/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#workflowProdutoBancoEmpresaId').val() == '' ? '0' :  $('#workflowProdutoBancoEmpresaId').val(), 
	        		  organizacao_id: $('#workflowProdutoBancoOrganizacaoId').val() == '' ? '0' :  $('#workflowProdutoBancoOrganizacaoId').val(),
	        		  nome : $('#workflowProdutoBancoProduto').val()},
	          success : function(data) {  

	        	  if (!data || data.length == 0) {
	     	            $('#workflowProdutoBancoProduto').val('');
	     	           $('#workflowProdutoBancoProdutoId').val('');
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
	      	 $('#workflowProdutoBancoProduto').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#workflowProdutoBancoProduto').val(ui.item.label);
	         $('#workflowProdutoBancoProdutoId').val(ui.item.value);
	         return false;
	     }
	});

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/workflowprodutobanco/cadastro" />';
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});

	$("#workflowProdutoBancoIsActive").change(function(e){
		if(document.workflowProdutoBancoForm.workflowProdutoBancoIsActive.checked==true){
			document.workflowProdutoBancoForm.workflowProdutoBancoIsActive.value=true;
		}else{
			document.workflowProdutoBancoForm.workflowProdutoBancoIsActive.value=false;
		}
	});
	
	$("#workflowProdutoBancoIsWorkflow").change(function(e){
		if(document.workflowProdutoBancoForm.workflowProdutoBancoIsWorkflow.checked==true){
			document.workflowProdutoBancoForm.workflowProdutoBancoIsWorkflow.value=true;
		}else{
			document.workflowProdutoBancoForm.workflowProdutoBancoIsWorkflow.value=false;
		}
	});

});

function limpaForm(){
	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.workflowProdutoBancoForm.reset();
	}
}

</script>

	<div id="content-header">
		<h1>Cadastro Workflow Produto Banco</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
		<a href="#" class="current">Workflow Produto Banco</a>
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
				<li class="active" id="workflowprodutobanco-li"><a href="#workflowprodutobanco-div" data-toggle="tab" id="workflowprodutobanco-li-a">Workflow Produto Banco</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">
				<div class="tab-pane fade active in" id="perfil-div">

					<form id="workflowProdutoBancoForm" name="workflowProdutoBancoForm" action="<c:url value="/workflowprodutobanco/salva"/>" method="POST">

						<div class="row-fluid">
							<div class="span3">
								<label for="workflowProdutoBancoEmpresa">Empresa</label>							
	      						<input class="input-xlarge" id="workflowProdutoBancoEmpresa" name="workflowProdutoBanco.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" required onChange="limpaForm();" readonly="readonly">
	      						<input class="span1" id="workflowProdutoBancoEmpresaId" name="workflowProdutoBanco.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden">	    					
							</div>
							<div class="span3">
								<label for="workflowProdutoBancoOrganizacao">Organização</label>
	      						<input class="input-xlarge" id="workflowProdutoBancoOrganizacao" name="workflowProdutoBanco.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" required onChange="limpaForm();" readonly="readonly">
	      						<input class="span1" id="workflowProdutoBancoOrganizacaoId" name="workflowProdutoBanco.organizacao.organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" type="hidden">
							</div>
						</div>
						<div class="row-fluid">
							<div class="span3">
								<label for="workflowProdutoBancoProduto">Produto</label>
	      						<input class="input-xlarge" id="workflowProdutoBancoProduto" name="workflowProdutoBanco.produto.nome" value="${workflowProdutoBanco.produto.nome }" type="text" required>
	      						<input class="span1" id="workflowProdutoBancoProdutoId" name="workflowProdutoBanco.produto.produto_id" value="${workflowProdutoBanco.produto.produto_id }" type="hidden">
							
							</div>
							<div class="span3">
								<label for="workflowProdutoBancoBanco">Banco</label>
								<select class="input-medim" id="workflowProdutoBancoBancoId" name="workflowProdutoBanco.banco.banco_id" >
									<c:forEach var="banco" items="${bancos }">
									 	<option value="${banco.banco_id }" selected="selected"> ${banco.nome }
									 	</option>
									</c:forEach>
								</select>
							</div>
							<div class="span3">
								<label for="workflowProdutoBancoWorkflow">Worflow</label>
								<select class="input-medim" id="workflowProdutoBancoWorkflowId" name="workflowProdutoBanco.workflow.workflow_id" >
									<c:forEach var="workflow" items="${workflows }">
									 	<option value="${workflow.workflow_id }" selected="selected"> ${workflow.nome }
									 	</option>
									</c:forEach>
								</select>
							</div>
							<div class="span1">
								<label for="workflowProdutoBancoIsActive">Ativo</label>							
								<input id="workflowProdutoBancoIsActive" name="workflowProdutoBanco.isActive" type="checkbox" checked="checked" value="1" >
							</div>
							<div class="span1">
								<label for="workflowProdutoBancoIsWorkflow">Workflow</label>							
								<input id="workflowProdutoBancoIsWorkflow" name="workflowProdutoBanco.isWorkflow" type="checkbox" checked="checked" value="1" >
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
				
				<table class="table table-striped table-bordered" id="lista">
					<thead>
						<tr>
							<th>Banco</th>
							<th>Produto</th>
							<th>Workflow</th>								
						</tr>
					</thead>
					<tbody>	
						<c:forEach items="${workflowsProdutoBanco }" var="workflowProdutoBanco">
							<tr>
								<td>${workflowProdutoBanco.banco.nome }</td>
								<td>${workflowProdutoBanco.produto.nome }</td>
								<td>${workflowProdutoBanco.workflow.nome }</td>									
							</tr>
						</c:forEach>
					</tbody>
				</table>							
								
			</div>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>