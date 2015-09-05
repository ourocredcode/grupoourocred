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
		limpaForm();
	});
	
	$("#workflowPerfilAcessoIsActive").change(function(e){
		if(document.workflowPerfilAcessoForm.workflowPerfilAcessoIsActive.checked==true){
			document.workflowPerfilAcessoForm.workflowPerfilAcessoIsActive.value=true;
		}else{
			document.workflowPerfilAcessoForm.workflowPerfilAcessoIsActive.value=false;
		}
	});
	
	$("#workflowPerfilAcessoIsLeituraEscrita").change(function(e){
		if(document.workflowPerfilAcessoForm.workflowPerfilAcessoIsLeituraEscrita.checked==true){
			document.workflowPerfilAcessoForm.workflowPerfilAcessoIsLeituraEscrita.value=true;
		}else{
			document.workflowPerfilAcessoForm.workflowPerfilAcessoIsLeituraEscrita.value=false;
		}
	});

});

function altera(linha, id) {

	var empresa_id = $('#workflowEtapaPerfilAcessoEmpresaId').val();
	var organizacao_id = $('#workflowEtapaPerfilAcessoOrganizacaoId').val();
	var workflow_id = $('#workflowEtapaPerfilAcessoWorkflowId').val();
	var perfil_id = $('#workflowPerfilAcessoPerfilId').val();

	alert(empresa_id + organizacao_id +  workflow_id +  perfil_id);

	if (window.confirm("Deseja alterar o workflow selecionado?"))
		$.post('<c:url value='/workflowperfilacesso/altera' />'
				,{'empresa_id': empresa_id, 'organizacao_id' : organizacao_id, 'workflow_id' : workflow_id, 'perfil_id' : perfil_id}
		);
	return false;
}

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
				<c:if test="${usuarioInfo.perfil.chave == 'Administrador'}">
					<li class="active" id="workflow-li"><a href="#workflow-div" data-toggle="tab" id="workflow-li-a">Workflow</a></li>
					<li class="" id="workflowperfilacesso-li"><a href="#workflowperfilacesso-div" data-toggle="tab" id="workflowperfilacesso-li-a">Workflow Perfil Acesso</a></li>
					<li class="" id="etapa-li"><a href="#etapa-div" data-toggle="tab" id="etapa-li-a">Etapa</a></li>
				</c:if>
				<c:if test="${usuarioInfo.perfil.chave == 'Gestor' || usuarioInfo.perfil.chave == 'Administrador'}">
					<li class="" id="workflowetapa-li"><a href="#workflowetapa-div" data-toggle="tab" id="workflowetapa-li-a">Workflow Etapa</a></li>
					<li class="" id="workflowetapaperfilacesso-li"><a href="#workflowetapaperfilacesso-div" data-toggle="tab" id="workflowetapaperfilacesso-li-a">Workflow Etapa Perfil Acesso</a></li>
					<li class="" id="workflowtransicao-li"><a href="#workflowtransicao-div" data-toggle="tab" id="workflowtransicao-li-a">Workflow Transicao</a></li>
				</c:if>				
			</ul>

			<div id="myTabContent" class="tab-content">
				
				<div class="tab-pane fade" id="perfil-div"></div>

				<div class="tab-pane fade active in" id="workflowperfilacesso-div">

					<form id="workflowPerfilAcessoForm" name="workflowPerfilAcessoForm" action="<c:url value="/workflowperfilacesso/salva"/>" method="POST">

						<div class="row-fluid">
							<div class="span3">
								<label for="workflowPerfilAcessoEmpresa">Empresa</label>							
      							<input class="input-xlarge" id="workflowPerfilAcessoEmpresa" name="workflowPerfilAcesso.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" required onChange="limpaForm();" readonly="readonly">
      							<input class="span1" id="workflowPerfilAcessoEmpresaId" name="workflowPerfilAcesso.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden">
							</div>
							<div class="span3">
								<label for="workflowPerfilAcessoOrganizacao">Organização</label>
	      						<input class="input-xlarge" id="workflowPerfilAcessoOrganizacao" name="workflowPerfilAcesso.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" required onChange="limpaForm();" readonly="readonly">
	      						<input class="span1" id="workflowPerfilAcessoOrganizacaoId" name="workflowPerfilAcesso.organizacao.organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" type="hidden">
	    					</div>
    					</div>
    					<div class="row-fluid">
	    					<div class="span2">
								<label for="workflowPerfilAcessoWorkflow">Workflow</label>
								<select class="input-medium" id="workflowPerfilAcessoWorkflowId" name="workflowPerfilAcesso.workflow.workflow_id" >
									<c:forEach var="workflow" items="${workflows }">
									 	<option value="${workflow.workflow_id }" selected="selected"> ${workflow.nome }
									 	</option>
									</c:forEach>
								</select>
							</div>
							<div class="span2">
								<label for="workflowPerfilAcessoPerfil">Perfil</label>
								<select class="input-medium" id="workflowPerfilAcessoPerfilId" name="workflowPerfilAcesso.perfil.perfil_id" >
									<c:forEach var="perfil" items="${perfis }">
									 	<option value="${perfil.perfil_id }" selected="selected"> ${perfil.nome }
									 	</option>
									</c:forEach>
								</select>
							</div>
							<div class="span1">
								<label for="workflowPerfilAcessoIsActive">Ativo</label>
								<input id="workflowPerfilAcessoIsActive" name="workflowPerfilAcesso.isActive" type="checkbox" checked="checked" value="1" >
							</div>
							<div class="span1">
								<label for="workflowPerfilAcessoIsLeituraEscrita">Escrita</label>
								<input id="workflowPerfilAcessoIsLeituraEscrita" name="workflowPerfilAcesso.isLeituraEscrita" type="checkbox" checked="checked" value="1" >
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

				<div class="tab-pane fade" id="etapa-div"></div>
				<div class="tab-pane fade" id="workflowetapa-div"></div>
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
					<h5>Workflow Perfil Acesso</h5>
				</div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty workflowPerfisAcesso}">
						<table
							class="table table-bordered table-striped table-hover data-table"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>Empresa</th>
									<th>Organização</th>
									<th>Workflow</th>
									<th>Perfil</th>
									<th>Ativo</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${workflowPerfisAcesso }" var="workflowPerfilAcesso">
									<tr>
										<td>${workflowPerfilAcesso.empresa.nome }</td>
										<td>${workflowPerfilAcesso.organizacao.nome }</td>
										<td>${workflowPerfilAcesso.workflow.nome }</td>
										<td>${workflowPerfilAcesso.perfil.nome }</td>
										<td>${workflowPerfilAcesso.isActive }</td>
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