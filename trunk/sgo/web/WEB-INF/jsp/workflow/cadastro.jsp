<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#workflow-li-a').click(function() {
		window.location.href = '<c:url value="/workflow/cadastro" />';
	});

	$('#workflowperfilacesso-li-a').click(function() {
		window.location.href = '<c:url value="/workflowperfilacesso/cadastro" />';
	});

	$('#workflowetapa-li-a').click(function() {
		window.location.href = '<c:url value="/workflowetapa/cadastro" />';
	});

	$('#workflowetapaperfilacesso-li-a').click(function() {
		window.location.href = '<c:url value="/workflowetapaperfilacesso/cadastro" />';
	});

	$('#workflowtransicao-li-a').click(function() {
		window.location.href = '<c:url value="/workflowtransicao/cadastro" />';
	});

	$('#workflowEmpresa').autocomplete({
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
	    	 $('#workflowEmpresa').val(ui.item.label);
	         return false;
	     } ,
	     select: function( event, ui ) {
	    	 $('#workflowEmpresa').val(ui.item.label);
	         $('#workflowEmpresaId').val(ui.item.value);
	         return false;
	     }
	});

	$('#workflowOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          
	          //data : {empresa_id: $('#workflowEmpresaId').val() == '' ? '0' :  $('#workflowEmpresaId').val(), 
	        		 // org_nome: $('#workflowOrganizacao').val() == '' ? '0' :  $('#workflowOrganizacao').val(),
	        		  //nometabelabd : $('#colunaBdTabelaBd').val()},

	          data : {empresa_id: $('#workflowEmpresaId').val() == '' ? '0' :  $('#workflowEmpresaId').val(),
	        		  org_nome : $('#workflowOrganizacao').val()},

	          success : function(data) {  

	        	  if (!data || data.length == 0) {
	     	            $('#workflowOrganizacao').val('');
	     	           $('#workflowOrganizacaoId').val('');
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
	      	 $('#workflowOrganizacao').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#workflowOrganizacao').val(ui.item.label);
	         $('#workflowOrganizacaoId').val(ui.item.value);
	         return false;
	     }
	});

	$('#workflowTipoWorkflow').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/tipoworkflow/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#workflowEmpresaId').val() == '' ? '0' :  $('#workflowEmpresaId').val(), 
	        		  organizacao_id: $('#workflowOrganizacaoId').val() == '' ? '0' :  $('#workflowOrganizacaoId').val(),
	        		  nome : $('#workflowTipoWorkflow').val()},
	          success : function(data) {  

	        	  if (!data || data.length == 0) {
	     	            $('#workflowTipoWorkflow').val('');
	     	           $('#workflowTipoWorkflowId').val('');
	     	        }

	        	  response($.map(data, function(tipoWorkflow) {  
	        		  return {
	        			  label: tipoWorkflow.nome,
	        			  value: tipoWorkflow.tipoWorkflow_id
	                  };
	              }));  
	           }
	        });
	     },
	     focus: function( event, ui ) {
	      	 $('#workflowTipoWorkflow').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#workflowTipoWorkflow').val(ui.item.label);
	         $('#workflowTipoWorkflowId').val(ui.item.value);
	         return false;
	     }
	});

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/workflow/cadastro" />';
	});

	$('#btnNovo').click(function() {
		document.workflowForm.reset();
	});

	$("#workflowIsActive").change(function(e){
		$(this).val( $("#workflowIsActive:checked").length > 0 ? "1" : "0");
	});

});

function limpaForm(){
	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.workflowForm.reset();
	}
}

</script>

	<div id="content-header">
		<h1>Cadastro Perfil</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
		<a href="#" class="current">Workflow</a>
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
				<li class="active" id="workflow-li"><a href="#workflow-div" data-toggle="tab" id="workflow-li-a">Workflow</a></li>
				<li class="" id="workflowperfilacesso-li"><a href="#workflowperfilacesso-div" data-toggle="tab" id="workflowperfilacesso-li-a">Workflow Perfil Acesso</a></li>
				<li class="" id="workflowetapa-li"><a href="#workflowetapa-div" data-toggle="tab" id="workflowetapa-li-a">Workflow Etapa</a></li>
				<li class="" id="workflowetapaperfilacesso-li"><a href="#workflowetapaperfilacesso-div" data-toggle="tab" id="workflowetapaperfilacesso-li-a">Workflow Etapa Perfil Acesso</a></li>
				<li class="" id="workflowtransicao-li"><a href="#workflowtransicao-div" data-toggle="tab" id="workflowtransicao-li-a">Workflow Transicao</a></li>				
			</ul>

			<div id="myTabContent" class="tab-content">
				
				<div class="tab-pane fade active in" id="perfil-div">
					<form id="workflowForm" name="workflowForm" action="<c:url value="/workflow/salva"/>" method="POST">

						<div class="control-group">
							<label class="control-label" for="workflowEmpresa">Empresa</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="workflowEmpresa" name="workflow.empresa.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="workflowEmpresaId" name="workflow.empresa.empresa_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="workflowOrganizacao">Organização</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="workflowOrganizacao" name="workflow.organizacao.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="workflowOrganizacaoId" name="workflow.organizacao.organizacao_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="workflowTipoWorkflow">Tipo de Worflow</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="workflowTipoWorkflow" name="workflow.tipoWorkflow.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="workflowTipoWorkflowId" name="workflow.tipoWorkflow.tipoWorkflow_id" type="hidden">
	    					</div>
						</div>						
						<div class="control-group">
							<label class="control-label" for="workflowNome">Nome</label>
							<div class="controls">
								<input type="text" id="workflowNome" name="workflow.nome" placeholder="Nome" required>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="workflowIsActive">Ativo</label>
							<div class="controls">
								<input type="checkbox" id="workflowIsActive" name="workflow.isActive" checked="checked" value="1" >							
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
							<th>Empresa</th>
							<th>Organização</th>
							<th>Nome</th>								
						</tr>
					</thead>
					<tbody>	
						<c:forEach items="${workflows }" var="workflow">
							<tr>
								<td>${workflow.empresa.nome }</td>
								<td>${workflow.organizacao.nome }</td>
								<td>${workflow.nome }</td>									
							</tr>
						</c:forEach>
					</tbody>
				</table>
								
				<div class="tab-pane fade" id="workflowperfilacesso-div"></div>
				<div class="tab-pane fade" id="workflowetapa-div"></div>
				<div class="tab-pane fade" id="workflowetapaperfilacesso-div"></div>
				<div class="tab-pane fade" id="workflowtransicao-div"></div>

			</div>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>