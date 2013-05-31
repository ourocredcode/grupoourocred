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

	$('#workflowEtapaEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
	          success : function(data) {  

	       		  if (!data || data.length == 0) {
	       	            $('#workflowEtapaEmpresa').val('');
						$('#workflowEtapaEmpresaId').val('');
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
	    	 $('#workflowEtapaEmpresa').val(ui.item.label);
	         return false;
	     } ,
	     select: function( event, ui ) {
	    	 $('#workflowEtapaEmpresa').val(ui.item.label);
	         $('#workflowEtapaEmpresaId').val(ui.item.value);
	         return false;
	     }
	});

	$('#workflowEtapaOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#workflowEtapaEmpresaId').val() == '' ? '0' :  $('#workflowEtapaEmpresaId').val(), org_nome : $('#workflowEtapaOrganizacao').val()},
	          success : function(data) {  

	        	  if (!data || data.length == 0) {
	     	            $('#workflowEtapaOrganizacao').val('');
	     	           $('#workflowEtapaOrganizacaoId').val('');
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
	      	 $('#workflowEtapaOrganizacao').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#workflowEtapaOrganizacao').val(ui.item.label);
	         $('#workflowEtapaOrganizacaoId').val(ui.item.value);
	         return false;
	     }
	});

	$('#workflowEtapaWorkflow').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/workflow/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#workflowEtapaEmpresaId').val() == '' ? '0' :  $('#workflowEtapaEmpresaId').val(), 
	        		  organizacao_id: $('#workflowEtapaOrganizacaoId').val() == '' ? '0' :  $('#workflowEtapaOrganizacaoId').val(),
	        		  nome : $('#workflowEtapaWorkflow').val()},
	          success : function(data) {  

	        	  if (!data || data.length == 0) {
	     	            $('#workflowEtapaWorkflow').val('');
	     	           $('#workflowEtapaWorkflowId').val('');
	     	        }

	        	  response($.map(data, function(workflow) {  
	        		  return {
	        			  label: workflow.nome,
	        			  value: workflow.workflow_id
	                  };
	              })); 
	           }
	        });
	     },
	     focus: function( event, ui ) {
	      	 $('#workflowEtapaWorkflow').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#workflowEtapaWorkflow').val(ui.item.label);
	         $('#workflowEtapaWorkflowId').val(ui.item.value);
	         return false;
	     }
	});
	
	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/workflowetapa/cadastro" />';
	});
	
	$('#btnNovo').click(function() {
		limpaForm();
	});
	
	$("#workflowEtapaIsActive").change(function(e){
		if(document.workflowEtapaForm.workflowEtapaIsActive.checked==true){
			document.workflowEtapaForm.workflowEtapaIsActive.value=true;
		}else{
			document.workflowEtapaForm.workflowEtapaIsActive.value=false;
		}
	});

});

function limpaForm(){
	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.workflowEtapaForm.reset();
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
		<a href="#" class="current">Workflow Etapa</a>
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
				<li class="" id="workflow-li"><a href="#workflow-div" data-toggle="tab" id="workflow-li-a">Workflow</a></li>
				<li class="" id="workflowperfilacesso-li"><a href="#workflowperfilacesso-div" data-toggle="tab" id="workflowperfilacesso-li-a">Workflow Perfil Acesso</a></li>
				<li class="active" id="workflowetapa-li"><a href="#workflowetapa-div" data-toggle="tab" id="workflowetapa-li-a">Workflow Etapa</a></li>
				<li class="" id="workflowetapaperfilacesso-li"><a href="#workflowetapaperfilacesso-div" data-toggle="tab" id="workflowetapaperfilacesso-li-a">Workflow Etapa Perfil Acesso</a></li>
				<li class="" id="workflowtransicao-li"><a href="#workflowtransicao-div" data-toggle="tab" id="workflowtransicao-li-a">Workflow Transicao</a></li>				
			</ul>

			<div id="myTabContent" class="tab-content">
				
				<div class="tab-pane fade" id="perfil-div"></div>
				<div class="tab-pane fade" id="workflowperfilacesso-div"></div>
				
				<div class="tab-pane fade active in" id="workflowetapa-div">
					
					<form id="workflowEtapaForm" name="workflowEtapaForm" action="<c:url value="/workflowetapa/salva"/>" method="POST">
					
						<div class="row-fluid">
							<div class="span2">
								<label for="workflowEtapaEmpresa">Empresa</label>
      							<input class="span12" id="workflowEtapaEmpresa" name="workflowEtapa.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" required onChange="limpaForm();" readonly="readonly">
      							<input class="span1" id="workflowEtapaEmpresaId" name="workflowEtapa.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden">	    				
							</div>
							<div class="span2">
								<label for="workflowEtapaOrganizacao">Organização</label>	
	      						<input class="span12" id="workflowEtapaOrganizacao" name="workflowEtapa.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" required onChange="limpaForm();" readonly="readonly">
	      						<input class="span1" id="workflowEtapaOrganizacaoId" name="workflowEtapa.organizacao.organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" type="hidden">
							</div>						
							<div class="span2">						
								<label for="workflowEtapaWorkflow">Worflow</label>
								<input class="span12" id="workflowEtapaWorkflow" name="workflowEtapa.workflow.nome" value="${workflowEtapa.workflow.nome }" type="text" required onChange="limpaForm();">
	      						<input class="span1" id="workflowEtapaWorkflowId" name="workflowEtapa.workflow.workflow_id" value="${workflowEtapa.workflow.workflow_id }" type="hidden">
	    					</div>
							<div class="span3">
								<label for="workflowEtapaNome">Nome</label>
								<input class="span12" id="workflowEtapaNome" name="workflowEtapa.nome" value="${workflowEtapa.nome }" type="text" placeholder="Nome" required>								
							</div>
							<div class="span1">
								<label for="workflowEtapaIsActive">Ativo</label>							
								<input type="checkbox" id="workflowEtapaIsActive" name="workflowEtapa.isActive" checked="checked" value="1" >
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
							<th>Worklflow</th>
							<th>Worklflow Etapa</th>
							<th>Padrão</th>
							<th>Ativo</th>								
						</tr>
					</thead>
					<tbody>	
						<c:forEach items="${workflowEtapas }" var="workflowEtapa">
							<tr>
								<td>${workflowEtapa.empresa.nome }</td>
								<td>${workflowEtapa.organizacao.nome }</td>
								<td>${workflowEtapa.workflow.nome }</td>
								<td>${workflowEtapa.nome }</td>
								<td>${workflowEtapa.ordemEtapa }</td>
								<td>${workflowEtapa.isPadrao }</td>
								<td>${workflowEtapa.isActive }</td>									
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<div class="tab-pane fade" id="workflowetapaperfilacesso-div"></div>
				<div class="tab-pane fade" id="workflowtransicao-div"></div>

			</div>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>