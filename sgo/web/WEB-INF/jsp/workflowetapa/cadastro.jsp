<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#workflow-li-a').click(function() {
		window.location.href = '<c:url value="/workflow/cadastro" />';
	});

	$('#workflowperfilacesso-li-a').click(function() {
		window.location.href = '<c:url value="/workflowperfilacesso/cadastro" />';
	});

	$('#etapa-li-a').click(function() {
		window.location.href = '<c:url value="/etapa/cadastro" />';
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
	
	$('#workflowEtapaEtapa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/etapa/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#workflowEtapaEmpresaId').val() == '' ? '0' :  $('#workflowEtapaEmpresaId').val(), 
	        		  organizacao_id: $('#workflowEtapaOrganizacaoId').val() == '' ? '0' :  $('#workflowEtapaOrganizacaoId').val(),
	        		  nome : $('#workflowEtapaEtapa').val()},
	          success : function(data) {  

	        	  if (!data || data.length == 0) {
	     	            $('#workflowEtapaEtapa').val('');
	     	           $('#workflowEtapaEtapaId').val('');
	     	        }

	        	  response($.map(data, function(etapa) {  
	        		  return {
	        			  label: etapa.nome,
	        			  value: etapa.etapa_id
	                  };
	              })); 
	           }
	        });
	     },
	     focus: function( event, ui ) {
	      	 $('#workflowEtapaEtapa').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#workflowEtapaEtapa').val(ui.item.label);
	         $('#workflowEtapaEtapaId').val(ui.item.value);
	         return false;
	     }
	});
	
	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/etapa/cadastro" />';
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
	
	$("#workflowEtapaIsLeituraEscrita").change(function(e){
		if(document.workflowEtapaForm.workflowEtapaIsLeituraEscrita.checked==true){
			document.workflowEtapaForm.workflowEtapaIsLeituraEscrita.value=true;
		}else{
			document.workflowEtapaForm.workflowEtapaIsLeituraEscrita.value=false;
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
				<li class="" id="etapa-li"><a href="#etapa-div" data-toggle="tab" id="etapa-li-a">Etapa</a></li>
				<li class="active" id="workflowetapa-li"><a href="#workflowetapa-div" data-toggle="tab" id="workflowetapa-li-a">Workflow Etapa</a></li>
				<li class="" id="workflowetapaperfilacesso-li"><a href="#workflowetapaperfilacesso-div" data-toggle="tab" id="workflowetapaperfilacesso-li-a">Workflow Etapa Perfil Acesso</a></li>
				<li class="" id="workflowtransicao-li"><a href="#workflowtransicao-div" data-toggle="tab" id="workflowtransicao-li-a">Workflow Transicao</a></li>				
			</ul>

			<div id="myTabContent" class="tab-content">
				
				<div class="tab-pane fade" id="perfil-div"></div>
				<div class="tab-pane fade" id="workflowperfilacesso-div"></div>
				<div class="tab-pane fade" id="etapa-div"></div>
				
				<div class="tab-pane fade active in" id="workflowetapa-div">
					
					<form id="workflowEtapaForm" name="workflowEtapaForm" action="<c:url value="/workflowetapa/salva"/>" method="POST">
					
						<div class="row-fluid">
							<div class="span3">
								<label for="workflowEtapaEmpresa">Empresa</label>
      							<input class="input-xlarge" id="workflowEtapaEmpresa" name="workflowEtapa.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" required readonly="readonly">
      							<input class="span1" id="workflowEtapaEmpresaId" name="workflowEtapa.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden">	    				
							</div>
							<div class="span3">
								<label for="workflowEtapaOrganizacao">Organização</label>	
	      						<input class="input-xlarge" id="workflowEtapaOrganizacao" name="workflowEtapa.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" required readonly="readonly">
	      						<input class="span1" id="workflowEtapaOrganizacaoId" name="workflowEtapa.organizacao.organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" type="hidden">
							</div>
						</div>
						<div class="row-fluid">
							<div class="span3">
								<label for="workflowEtapaWorkflow">Workflow</label>
								<select class="input-xlarge" id="workflowEtapaWorkflowId" name="workflowEtapa.workflow.workflow_id" >
									<c:forEach var="workflow" items="${workflows }">
									 	<option value="${workflow.workflow_id }" selected="selected"> ${workflow.nome }</option>
									</c:forEach>
								</select>
							</div>
							<div class="span3">
								<label for="workflowEtapaEtapa">Etapas</label>
								<select class="input-xlarge" id="workflowEtapaEtapaId" name="workflowEtapa.etapa.etapa_id" >
									<c:forEach var="etapa" items="${etapas }">
									 	<option value="${etapa.etapa_id }" selected="selected"> ${etapa.nome }</option>
									</c:forEach>
								</select>
							</div>
							<div class="span1">
								<label for="workflowEtapaIsActive">Ativo</label>							
								<input type="checkbox" id="workflowEtapaIsActive" name="workflowEtapa.isActive" checked="checked" value="1" >
							</div>
							<div class="span1">
								<label for="workflowEtapaIsLeituraEscrita">R/W</label>							
								<input type="checkbox" id="workflowEtapaIsLeituraEscrita" name="workflowEtapa.isLeituraEscrita" checked="checked" value="1" >
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

				<div class="tab-pane fade" id="workflowetapaperfilacesso-div"></div>
				<div class="tab-pane fade" id="workflowtransicao-div"></div>

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
					<h5>Workflow</h5>
				</div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty workflowEtapas}">
						<table
							class="table table-bordered table-striped table-hover data-table"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>Empresa</th>
									<th>Organização</th>
									<th>Worklflow</th>
									<th>Etapa</th>
									<th>Ativo</th>
									<th>R/W</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${workflowEtapas }" var="workflowEtapa">
									<tr>
										<td>${workflowEtapa.empresa.nome }</td>
										<td>${workflowEtapa.organizacao.nome }</td>
										<td>${workflowEtapa.workflow.nome }</td>
										<td>${workflowEtapa.etapa.nome }</td>
										<td>${workflowEtapa.isActive }</td>
										<td>${workflowEtapa.isLeituraEscrita }</td>
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