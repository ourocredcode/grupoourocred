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

	$('#workflowEtapaPerfilAcessoEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
	          success : function(data) {  

	       		  if (!data || data.length == 0) {
	       	            $('#workflowEtapaPerfilAcessoEmpresa').val('');
						$('#workflowEtapaPerfilAcessoEmpresaId').val('');
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
	    	 $('#workflowEtapaPerfilAcessoEmpresa').val(ui.item.label);
	         return false;
	     } ,
	     select: function( event, ui ) {
	    	 $('#workflowEtapaPerfilAcessoEmpresa').val(ui.item.label);
	         $('#workflowEtapaPerfilAcessoEmpresaId').val(ui.item.value);
	         return false;
	     }
	});

	$('#workflowEtapaPerfilAcessoOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#workflowEtapaPerfilAcessoEmpresaId').val() == '' ? '0' :  $('#workflowEtapaPerfilAcessoEmpresaId').val(), org_nome : $('#workflowEtapaPerfilAcessoOrganizacao').val()},
	          success : function(data) {  

	        	  if (!data || data.length == 0) {
	     	            $('#workflowEtapaPerfilAcessoOrganizacao').val('');
	     	           $('#workflowEtapaPerfilAcessoOrganizacaoId').val('');
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
	      	 $('#workflowEtapaPerfilAcessoOrganizacao').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#workflowEtapaPerfilAcessoOrganizacao').val(ui.item.label);
	         $('#workflowEtapaPerfilAcessoOrganizacaoId').val(ui.item.value);
	         return false;
	     }
	});
	
	$('#workflowEtapaPerfilAcessoWorkflow').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/workflow/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#workflowEtapaPerfilAcessoEmpresaId').val() == '' ? '0' :  $('#workflowEtapaPerfilAcessoEmpresaId').val(), 
	        		  organizacao_id: $('#workflowEtapaPerfilAcessoOrganizacaoId').val() == '' ? '0' :  $('#workflowEtapaPerfilAcessoOrganizacaoId').val(),
	        		  nome : $('#workflowEtapaPerfilAcessoWorkflow').val()},
	          success : function(data) {  

	        	

	        	  if (!data || data.length == 0) {
	     	            $('#workflowEtapaPerfilAcessoWorkflow').val('');
	     	           $('#workflowEtapaPerfilAcessoWorkflowId').val('');
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
	      	 $('#workflowEtapaPerfilAcessoWorkflow').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {

	    	 $('#workflowEtapaPerfilAcessoWorkflow').val(ui.item.label);
	         $('#workflowEtapaPerfilAcessoWorkflowId').val(ui.item.value);
	         
	         return false;
	     }
	});

	$('#workflowEtapaPerfilAcessoEtapa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/etapa/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#workflowEtapaPerfilAcessoEmpresaId').val() == '' ? '0' :  $('#workflowEtapaPerfilAcessoEmpresaId').val(), 
	        		  organizacao_id: $('#workflowEtapaPerfilAcessoOrganizacaoId').val() == '' ? '0' :  $('#workflowEtapaPerfilAcessoOrganizacaoId').val(),
	        		  nome : $('#workflowEtapaPerfilAcessoEtapa').val()},
	          success : function(data) {  

	        	  if (!data || data.length == 0) {
	     	            $('#workflowEtapaPerfilAcessoEtapa').val('');
	     	           $('#workflowEtapaPerfilAcessoEtapaId').val('');
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
	      	 $('#workflowEtapaPerfilAcessoEtapa').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#workflowEtapaPerfilAcessoEtapa').val(ui.item.label);
	         $('#workflowEtapaPerfilAcessoEtapaId').val(ui.item.value);
	         return false;
	     }
	});
	
	$('#workflowEtapaPerfilAcessoPerfil').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/perfil/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#workflowEtapaPerfilAcessoEmpresaId').val() == '' ? '0' :  $('#workflowEtapaPerfilAcessoEmpresaId').val(), 
	        		  organizacao_id: $('#workflowEtapaPerfilAcessoOrganizacaoId').val() == '' ? '0' :  $('#workflowEtapaPerfilAcessoOrganizacaoId').val(),
	        		  nome : $('#workflowEtapaPerfilAcessoPerfil').val()},
	          success : function(data) {  

	        	  if (!data || data.length == 0) {
	     	            $('#workflowEtapaPerfilAcessoPerfil').val('');
	     	           $('#workflowEtapaPerfilAcessoPerfilId').val('');
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
	      	 $('#workflowEtapaPerfilAcessoPerfil').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#workflowEtapaPerfilAcessoPerfil').val(ui.item.label);
	         $('#workflowEtapaPerfilAcessoPerfilId').val(ui.item.value);
	         return false;
	     }
	});

	
	
	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/workflowetapaperfilacesso/cadastro" />';
	});
	
	$('#btnNovo').click(function() {
		limpaForm();
	});

	$("#workflowEtapaPerfilAcessoIsActive").change(function(e){
		if(document.workflowEtapaPerfilAcessoForm.workflowEtapaPerfilAcessoIsActive.checked==true){
			document.workflowEtapaPerfilAcessoForm.workflowEtapaPerfilAcessoIsActive.value=true;
		}else{
			document.workflowEtapaPerfilAcessoForm.workflowEtapaPerfilAcessoIsActive.value=false;
		}
	});

	$("#workflowEtapaPerfilAcessoIsLeituraEscrita").change(function(e){
		if(document.workflowEtapaPerfilAcessoForm.workflowEtapaPerfilAcessoIsLeituraEscrita.checked==true){
			document.workflowEtapaPerfilAcessoForm.workflowEtapaPerfilAcessoIsLeituraEscrita.value=true;
		}else{
			document.workflowEtapaPerfilAcessoForm.workflowEtapaPerfilAcessoIsLeituraEscrita.value=false;
		}
	});

	$("#workflowEtapaPerfilAcessoIsUpload").change(function(e){
		if(document.workflowEtapaPerfilAcessoForm.workflowEtapaPerfilAcessoIsUpload.checked==true){
			document.workflowEtapaPerfilAcessoForm.workflowEtapaPerfilAcessoIsUpload.value=true;
		}else{
			document.workflowEtapaPerfilAcessoForm.workflowEtapaPerfilAcessoIsUpload.value=false;
		}
	});
	
});

function limpaForm(){
	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.workflowEtapaPerfilAcessoForm.reset();
	}
}

function buscaEtapas(){

	var empresa_id = $('#workflowEtapaPerfilAcessoEmpresaId').val();
	var organizacao_id = $('#workflowEtapaPerfilAcessoOrganizacaoId').val();
	var workflow_id = $('#workflowEtapaPerfilAcessoWorkflowId').val();

	$("#workflowEtapaPerfilAcessoEtapaId").load('<c:url value="/workflowetapaperfilacesso/etapas" />',
			{'empresa_id': empresa_id, 'organizacao_id' : organizacao_id, 'workflow_id' : workflow_id});

}

</script>

	<div id="content-header">
		<h1>Workflow Perfil Acesso</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
		<a href="#" class="current">Workflow Etapa Perfil Acesso</a>
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
				<li class="" id="workflowetapa-li"><a href="#workflowetapa-div" data-toggle="tab" id="workflowetapa-li-a">Workflow Etapa</a></li>
				<li class="active" id="workflowetapaperfilacesso-li"><a href="#workflowetapaperfilacesso-div" data-toggle="tab" id="workflowetapaperfilacesso-li-a">Workflow Etapa Perfil Acesso</a></li>
				<li class="" id="workflowtransicao-li"><a href="#workflowtransicao-div" data-toggle="tab" id="workflowtransicao-li-a">Workflow Transicao</a></li>				
			</ul>

			<div id="myTabContent" class="tab-content">
				
				<div class="tab-pane fade" id="perfil-div"></div>
				<div class="tab-pane fade" id="workflowperfilacesso-div"></div>
				<div class="tab-pane fade" id="etapa-div"></div>
				<div class="tab-pane fade" id="workflowetapa-div"></div>
				
				<div class="tab-pane fade active in" id="workflowetapaperfilacesso-div">
				
					<form id="workflowEtapaPerfilAcessoForm" name="workflowEtapaPerfilAcessoForm" action="<c:url value="/workflowetapaperfilacesso/salva"/>" method="POST">

						<div class="row-fluid">
							<div class="span2">
								<label for="workflowEtapaPerfilAcessoEmpresa">Empresa</label>
	      						<input class="input-medium" id="workflowEtapaPerfilAcessoEmpresa" name="workflowEtapaPerfilAcessoEmpresa.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" required onChange="limpaForm();" readonly="readonly">
		      					<input id="workflowEtapaPerfilAcessoEmpresaId" name="workflowEtapaPerfilAcessoEmpresa.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden">
							</div>
							<div class="span2">
								<label for="workflowEtapaPerfilAcessoOrganizacao">Organização</label>
		      					<input class="input-medium" id="workflowEtapaPerfilAcessoOrganizacao" name="workflowEtapaPerfilAcessoOrganizacao.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" required onChange="limpaForm();" readonly="readonly">
		      					<input id="workflowEtapaPerfilAcessoOrganizacaoId" name="workflowEtapaPerfilAcessoOrganizacao.organizacao.organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" type="hidden">
							</div>
						</div>
						<div class="row-fluid">
							<div class="span2">
								<label for="workflowEtapaPerfilAcessoWorkflowId">Workflow</label>
								<select id="workflowEtapaPerfilAcessoWorkflowId" name="workflowEtapaPerfilAcesso.workflow.workflow_id" onchange="buscaEtapas();" class="input-medium">
									<c:forEach var="workflow" items="${workflows }">
									 	<option value="${workflow.workflow_id }" selected="selected"> ${workflow.nome }
									 	</option>
									</c:forEach>
								</select>
							</div>
							<div class="span2">
	      						<label for="workflowEtapaPerfilAcessoEtapaId">Etapa</label>
	      						<select id="workflowEtapaPerfilAcessoEtapaId" name="workflowEtapaPerfilAcesso.etapa.etapa_id" class="input-medium">
	      							<option value="">Selecion um Workflow...</option>
	      						</select>
							</div>
							<div class="span2">
								<label for="workflowEtapaPerfilAcessoPerfilId">Perfil</label>
								<select id="workflowEtapaPerfilAcessoPerfilId" name="workflowEtapaPerfilAcesso.perfil.perfil_id" class="input-medium">
									<c:forEach var="perfil" items="${perfis }">
									 	<option value="${perfil.perfil_id }" selected="selected"> ${perfil.nome }
									 	</option>
									</c:forEach>
								</select>
							</div>
							<div class="span1">
								<label for="workflowEtapaPerfilAcessoIsActive">Ativo</label>
								<input id="workflowEtapaPerfilAcessoIsActive" name="workflowEtapaPerfilAcesso.isActive" type="checkbox" checked="checked" value="1">
							</div>
							<div class="span1">
								<label for="workflowEtapaPerfilAcessoIsLeituraEscrita">Escrita</label>
								<input id="workflowEtapaPerfilAcessoIsLeituraEscrita" name="workflowEtapaPerfilAcesso.isLeituraEscrita" type="checkbox" checked="checked" value="1">
							</div>
							<div class="span1">
								<label for="workflowEtapaPerfilAcessoIsUpload">Upload</label>
								<input id="workflowEtapaPerfilAcessoIsUpload" name="workflowEtapaPerfilAcesso.isUpload" type="checkbox" value="0" >
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
					<h5>Workflow Etapa Perfil Acesso</h5>
				</div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty workflowEtapasPerfilAcesso}">
						<table
							class="table table-bordered table-striped table-hover data-table"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>Worklflow</th>
									<th>Worklflow Etapa</th>
									<th>Perfil</th>
									<th>Leitura e Escrita</th>
									<th>Ativo</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${workflowEtapasPerfilAcesso }" var="workflowEtapaPerfilAcesso">
									<tr>
										<td>${workflowEtapaPerfilAcesso.workflow.nome }</td>
										<td>${workflowEtapaPerfilAcesso.etapa.nome }</td>
										<td>${workflowEtapaPerfilAcesso.perfil.nome }</td>
										<td>${workflowEtapaPerfilAcesso.isLeituraEscrita }</td>
										<td>${workflowEtapaPerfilAcesso.isActive }</td>
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