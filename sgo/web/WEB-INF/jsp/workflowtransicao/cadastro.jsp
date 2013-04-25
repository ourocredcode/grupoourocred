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

	$('#workflowTransicaoEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
	          success : function(data) {  

	       		  if (!data || data.length == 0) {
	       	            $('#workflowTransicaoEmpresa').val('');
						$('#workflowTransicaoEmpresaId').val('');
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
	    	 $('#workflowTransicaoEmpresa').val(ui.item.label);
	         return false;
	     } ,
	     select: function( event, ui ) {
	    	 $('#workflowTransicaoEmpresa').val(ui.item.label);
	         $('#workflowTransicaoEmpresaId').val(ui.item.value);
	         return false;
	     }
	});

	$('#workflowTransicaoOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#workflowTransicaoEmpresaId').val() == '' ? '0' :  $('#workflowTransicaoEmpresaId').val(), org_nome : $('#workflowTransicaoOrganizacao').val()},
	          success : function(data) {  

	        	  if (!data || data.length == 0) {
	     	            $('#workflowTransicaoOrganizacao').val('');
	     	           $('#workflowTransicaoOrganizacaoId').val('');
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
	      	 $('#workflowTransicaoOrganizacao').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#workflowTransicaoOrganizacao').val(ui.item.label);
	         $('#workflowTransicaoOrganizacaoId').val(ui.item.value);
	         return false;
	     }
	});

	$('#workflowTransicaoWorkflow').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/workflow/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#workflowTransicaoEmpresaId').val() == '' ? '0' :  $('#workflowTransicaoEmpresaId').val(), 
	        		  organizacao_id: $('#workflowTransicaoOrganizacaoId').val() == '' ? '0' :  $('#workflowTransicaoOrganizacaoId').val(),
	        		  nome : $('#workflowTransicaoWorkflow').val()},
	          success : function(data) {  

	        	  if (!data || data.length == 0) {
	     	            $('#workflowTransicaoWorkflow').val('');
	     	           $('#workflowTransicaoWorkflowId').val('');
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
	      	 $('#workflowTransicaoWorkflow').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#workflowTransicaoWorkflow').val(ui.item.label);
	         $('#workflowTransicaoWorkflowId').val(ui.item.value);
	         return false;
	     }
	});
	
	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/workflowetapa/cadastro" />';
	});
	
	$('#btnNovo').click(function() {
		document.workflowTransicaoForm.reset();
	});
	
	$("#workflowTransicaoIsActive").change(function(e){
		$(this).val( $("#workflowTransicaoIsActive:checked").length > 0 ? "1" : "0");
	});

});

function limpaForm(){
	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.workflowTransicaoForm.reset();
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
						<a href="#" data-dismiss="alert" class="close">�</a>
					</div>
			</c:when>
			<c:otherwise>
					<div class="alert alert-success">
						<strong>${notice }</strong>
						<a href="#" data-dismiss="alert" class="close">�</a>
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
				<li class="" id="workflowetapa-li"><a href="#workflowetapa-div" data-toggle="tab" id="workflowetapa-li-a">Workflow Etapa</a></li>
				<li class="" id="workflowetapaperfilacesso-li"><a href="#workflowetapaperfilacesso-div" data-toggle="tab" id="workflowetapaperfilacesso-li-a">Workflow Etapa Perfil Acesso</a></li>
				<li class="active" id="workflowtransicao-li"><a href="#workflowtransicao-div" data-toggle="tab" id="workflowtransicao-li-a">Workflow Transicao</a></li>				
			</ul>

			<div id="myTabContent" class="tab-content">
				
				<div class="tab-pane fade" id="perfil-div"></div>
				<div class="tab-pane fade" id="workflowperfilacesso-div"></div>
				<div class="tab-pane fade" id="workflowetapa-div"></div>
				<div class="tab-pane fade" id="workflowetapaperfilacesso-div"></div>
				
				<div class="tab-pane fade active in" id="workflowtransicao-div">
					<form id="workflowTransicaoForm" name="workflowTransicaoForm" action="<c:url value="/workflowtransicao/salva"/>" method="POST">
						<div class="control-group">
							<label class="control-label" for="workflowTransicaoEmpresa">Empresa</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="workflowTransicaoEmpresa" name="workflowTransicao.empresa.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="workflowTransicaoEmpresaId" name="workflowTransicao.empresa.empresa_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="workflowTransicaoOrganizacao">Organiza��o</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="workflowTransicaoOrganizacao" name="workflowTransicao.organizacao.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="workflowTransicaoOrganizacaoId" name="workflowTransicao.organizacao.organizacao_id" type="hidden">
	    					</div>
						</div>

						<div class="controls controls-row">
							<label for="workflowTransicaoWorkflowEtapaId">Etapas</label>
							<select id="workflowTransicaoWorkflowEtapaId" name="workflowTransicao.workflowEtapa.workflowEtapa_id" class="input-medium">
								<c:forEach var="workflowEtapa" items="${workflowEtapas }">
								 	<option value="${workflowEtapa.workflowEtapa_id }" selected="selected"> ${workflowEtapa.nome }
								 	</option>
								</c:forEach>
							</select>
						</div>

						<div class="controls controls-row">
							<label for="workflowTransicaoWorkflowEtapaProximoId">Etapas Pr�ximo</label>
							<select id="workflowTransicaoWorkflowEtapaProximoId" name="workflowTransicao.workflowEtapaProximo.workflowEtapa_id" class="input-medium">
								<c:forEach var="workflowEtapa" items="${workflowEtapas }">
								 	<option value="${workflowEtapa.workflowEtapa_id }" selected="selected"> ${workflowEtapa.nome }
								 	</option>
								</c:forEach>
							</select>
						</div>

						<div class="control-group">
							<label class="control-label" for="workflowTransicaoIsActive">Ativo</label>
							<div class="controls">
								<input type="checkbox" id="workflowTransicaoIsActive" name="workflowTransicao.isActive" checked="checked" value="1" >							
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
							<th>Organiza��o</th>
							<th>Worklflow Etapa</th>
							<th>Worklflow Pr�xima</th>			
							<th>Sequ�ncia</th>
							<th>Padr�o</th>
							<th>Ativo</th>								
						</tr>
					</thead>
					<tbody>	
						<c:forEach items="${workflowTransicoes }" var="workflowTransicao">
							<tr>
								<td>${workflowTransicao.empresa.nome }</td>
								<td>${workflowTransicao.organizacao.nome }</td>
								<td>${workflowTransicao.workflowEtapa.nome }</td>
								<td>${workflowTransicao.workflowEtapaProximo.nome }</td>
								<td>${workflowTransicao.sequencia }</td>
								<td>${workflowTransicao.isPadrao }</td>
								<td>${workflowTransicao.isActive }</td>									
							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>