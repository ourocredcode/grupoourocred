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

	$('#workflowPerfilAcessoEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
	          success : function(data) {  

	       		  if (!data || data.length == 0) {
	       	            $('#workflowPerfilAcessoEmpresa').val('');
						$('#workflowPerfilAcessoEmpresaId').val('');
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
	    	 $('#workflowPerfilAcessoEmpresa').val(ui.item.label);
	         return false;
	     } ,
	     select: function( event, ui ) {
	    	 $('#workflowPerfilAcessoEmpresa').val(ui.item.label);
	         $('#workflowPerfilAcessoEmpresaId').val(ui.item.value);
	         return false;
	     }
	});

	$('#workflowPerfilAcessoOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#workflowPerfilAcessoEmpresaId').val() == '' ? '0' :  $('#workflowPerfilAcessoEmpresaId').val(), org_nome : $('#workflowPerfilAcessoOrganizacao').val()},
	          success : function(data) {  

	        	  if (!data || data.length == 0) {
	     	            $('#workflowPerfilAcessoOrganizacao').val('');
	     	           $('#workflowPerfilAcessoOrganizacaoId').val('');
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
	      	 $('#workflowPerfilAcessoOrganizacao').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#workflowPerfilAcessoOrganizacao').val(ui.item.label);
	         $('#workflowPerfilAcessoOrganizacaoId').val(ui.item.value);
	         return false;
	     }
	});

	$('#workflowPerfilAcessoWorkflow').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/workflow/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#workflowPerfilAcessoEmpresaId').val() == '' ? '0' :  $('#workflowPerfilAcessoEmpresaId').val(), 
	        		  organizacao_id: $('#workflowPerfilAcessoOrganizacaoId').val() == '' ? '0' :  $('#workflowPerfilAcessoOrganizacaoId').val(),
	        		  nome : $('#workflowPerfilAcessoWorkflow').val()},
	          success : function(data) {  

	        	  if (!data || data.length == 0) {
	     	            $('#workflowPerfilAcessoWorkflow').val('');
	     	           $('#workflowPerfilAcessoWorkflowId').val('');
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
	      	 $('#workflowPerfilAcessoWorkflow').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#workflowPerfilAcessoWorkflow').val(ui.item.label);
	         $('#workflowPerfilAcessoWorkflowId').val(ui.item.value);
	         return false;
	     }
	});
	
	$('#workflowPerfilAcessoPerfil').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/perfil/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#workflowPerfilAcessoEmpresaId').val() == '' ? '0' :  $('#workflowPerfilAcessoEmpresaId').val(), 
	        		  organizacao_id: $('#workflowPerfilAcessoOrganizacaoId').val() == '' ? '0' :  $('#workflowPerfilAcessoOrganizacaoId').val(),
	        		  nome : $('#workflowPerfilAcessoPerfil').val()},
	          success : function(data) {  

	        	  if (!data || data.length == 0) {
	     	            $('#workflowPerfilAcessoPerfil').val('');
	     	           $('#workflowPerfilAcessoPerfilId').val('');
	     	        }

	        	  response($.map(data, function(perfil) {  
	        		  return {
	        			  label: perfil.nome,
	        			  value: perfil.perfil_id
	                  };
	              }));  
	           }
	        });
	     },
	     focus: function( event, ui ) {
	      	 $('#workflowPerfilAcessoPerfil').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#workflowPerfilAcessoPerfil').val(ui.item.label);
	         $('#workflowPerfilAcessoPerfilId').val(ui.item.value);
	         return false;
	     }
	});
	
	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/workflowperfilacesso/cadastro" />';
	});
	
	$('#btnNovo').click(function() {
		document.workflowPerfilAcessoForm.reset();
	});
	
	$("#workflowPerfilAcessoIsActive").change(function(e){
		$(this).val( $("#workflowPerfilAcessoIsActive:checked").length > 0 ? "1" : "0");
	});
	
	$("#workflowPerfilAcessoIsLeituraEscrita").change(function(e){
		$(this).val( $("#workflowPerfilAcessoIsLeituraEscrita:checked").length > 0 ? "1" : "0");
	});

});

function limpaForm(){
	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.workflowPerfilAcessoForm.reset();
	}
}

</script>

	<div id="content-header">
		<h1>Workflow Perfil Acesso</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
		<a href="#" class="current">Workflow Perfil Acesso</a>
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
				<li class="active" id="workflowperfilacesso-li"><a href="#workflowperfilacesso-div" data-toggle="tab" id="workflowperfilacesso-li-a">Workflow Perfil Acesso</a></li>
				<li class="" id="workflowetapa-li"><a href="#workflowetapa-div" data-toggle="tab" id="workflowetapa-li-a">Workflow Etapa</a></li>
				<li class="" id="workflowetapaperfilacesso-li"><a href="#workflowetapaperfilacesso-div" data-toggle="tab" id="workflowetapaperfilacesso-li-a">Workflow Etapa Perfil Acesso</a></li>
				<li class="" id="workflowtransicao-li"><a href="#workflowtransicao-div" data-toggle="tab" id="workflowtransicao-li-a">Workflow Transicao</a></li>				
			</ul>

			<div id="myTabContent" class="tab-content">
				
				<div class="tab-pane fade" id="perfil-div"></div>

				<div class="tab-pane fade active in" id="workflowperfilacesso-div">
					<form id="workflowPerfilAcessoForm" name="workflowPerfilAcessoForm" action="<c:url value="/workflowperfilacesso/salva"/>" method="POST">

						<div class="control-group">
							<label class="control-label" for="workflowPerfilAcessoEmpresa">Empresa</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="workflowPerfilAcessoEmpresa" name="workflowPerfilAcessoEmpresa.empresa.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="workflowPerfilAcessoEmpresaId" name="workflowPerfilAcessoEmpresa.empresa.empresa_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="workflowPerfilAcessoOrganizacao">Organização</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="workflowPerfilAcessoOrganizacao" name="workflowPerfilAcessoOrganizacao.organizacao.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="workflowPerfilAcessoOrganizacaoId" name="workflowPerfilAcessoOrganizacao.organizacao.organizacao_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="workflowPerfilAcessoWorkflow">WorkFlow</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="workflowPerfilAcessoWorkflow" name="workflowPerfilAcesso.workflow.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="workflowPerfilAcessoWorkflowId" name="workflowPerfilAcesso.workflow.workflow_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="workflowPerfilAcessoPerfil">Perfil</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="workflowPerfilAcessoPerfil" name="workflowPerfilAcesso.perfil.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="workflowPerfilAcessoPerfilId" name="workflowPerfilAcesso.perfil.perfil_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="workflowPerfilAcessoIsActive">Ativo</label>
							<div class="controls">
								<input type="checkbox" id="workflowPerfilAcessoIsActive" name="workflowPerfilAcesso.isActive" checked="checked" value="1" >							
							</div>							
						</div>
						<div class="control-group">
							<label class="control-label" for="workflowPerfilAcessoIsLeituraEscrita">Leitura e Escrita</label>
							<div class="controls">
								<input type="checkbox" id="workflowPerfilAcessoIsLeituraEscrita" name="workflowPerfilAcesso.isLeituraEscrita" checked="checked" value="1" >							
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
							<th>Workflow</th>
							<th>Perfil</th>								
						</tr>
					</thead>
					<tbody>	
						<c:forEach items="${workflowperfisacesso }" var="workflowperfilacesso">
							<tr>
								<td>${workflowperfilacesso.empresa.nome }</td>
								<td>${workflowperfilacesso.organizacao.nome }</td>
								<td>${workflowperfilacesso.workflow.nome }</td>
								<td>${workflowperfilacesso.perfil.nome}</td>								
							</tr>
						</c:forEach>
					</tbody>
				</table>				

				<div class="tab-pane fade" id="workflowetapa-div"></div>
				<div class="tab-pane fade" id="workflowetapaperfilacesso-div"></div>
				<div class="tab-pane fade" id="workflowtransicao-div"></div>

			</div>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>