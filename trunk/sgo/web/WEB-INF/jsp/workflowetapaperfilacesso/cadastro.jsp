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

	$('#workflowEtapaPerfilAcessoWorkflowEtapa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/workflowetapa/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#workflowEtapaPerfilAcessoEmpresaId').val() == '' ? '0' :  $('#workflowEtapaPerfilAcessoEmpresaId').val(), 
	        		  organizacao_id: $('#workflowEtapaPerfilAcessoOrganizacaoId').val() == '' ? '0' :  $('#workflowEtapaPerfilAcessoOrganizacaoId').val(),
	        		  nome : $('#workflowEtapaPerfilAcessoWorkflowEtapa').val()},
	          success : function(data) {  

	        	  if (!data || data.length == 0) {
	     	            $('#workflowEtapaPerfilAcessoWorkflowEtapa').val('');
	     	           $('#workflowEtapaPerfilAcessoWorkflowEtapaId').val('');
	     	        }

	        	  response($.map(data, function(workflowEtapa) {  
	        		  return {
	        			  label: workflowEtapa.nome,
	        			  value: workflowEtapa.workflowEtapa_id
	                  };
	              }));  
	           }
	        });
	     },
	     focus: function( event, ui ) {
	      	 $('#workflowEtapaPerfilAcessoWorkflowEtapa').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#workflowEtapaPerfilAcessoWorkflowEtapa').val(ui.item.label);
	         $('#workflowEtapaPerfilAcessoWorkflowEtapaId').val(ui.item.value);
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
		document.workflowEtapaPerfilAcessoForm.reset();
	});
	
	$("#workflowEtapaPerfilAcessoIsActive").change(function(e){
		$(this).val( $("#workflowEtapaPerfilAcessoIsActive:checked").length > 0 ? "1" : "0");
	});
	
	$("#workflowEtapaPerfilAcessoIsLeituraEscrita").change(function(e){
		$(this).val( $("#workflowEtapaPerfilAcessoIsLeituraEscrita:checked").length > 0 ? "1" : "0");
	});

});

function limpaForm(){
	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.workflowEtapaPerfilAcessoForm.reset();
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
		<a href="#" class="current">Workflow Etapa Perfil Acesso</a>
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
				<li class="active" id="workflowetapaperfilacesso-li"><a href="#workflowetapaperfilacesso-div" data-toggle="tab" id="workflowetapaperfilacesso-li-a">Workflow Etapa Perfil Acesso</a></li>
				<li class="" id="workflowtransicao-li"><a href="#workflowtransicao-div" data-toggle="tab" id="workflowtransicao-li-a">Workflow Transicao</a></li>				
			</ul>

			<div id="myTabContent" class="tab-content">
				
				<div class="tab-pane fade" id="perfil-div"></div>

				<div class="tab-pane fade" id="workflowperfilacesso-div"></div>
				<div class="tab-pane fade" id="workflowetapa-div"></div>
				
				<div class="tab-pane fade active in" id="workflowetapaperfilacesso-div">
					<form id="workflowEtapaPerfilAcessoForm" name="workflowEtapaPerfilAcessoForm" action="<c:url value="/workflowetapaperfilacesso/salva"/>" method="POST">

						<div class="control-group">
							<label class="control-label" for="workflowEtapaPerfilAcessoEmpresa">Empresa</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="workflowEtapaPerfilAcessoEmpresa" name="workflowEtapaPerfilAcessoEmpresa.empresa.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="workflowEtapaPerfilAcessoEmpresaId" name="workflowEtapaPerfilAcessoEmpresa.empresa.empresa_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="workflowEtapaPerfilAcessoOrganizacao">Organiza��o</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="workflowEtapaPerfilAcessoOrganizacao" name="workflowEtapaPerfilAcessoOrganizacao.organizacao.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="workflowEtapaPerfilAcessoOrganizacaoId" name="workflowEtapaPerfilAcessoOrganizacao.organizacao.organizacao_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="workflowEtapaPerfilAcessoWorkflowEtapa">WorkFlow Etapa</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="workflowEtapaPerfilAcessoWorkflowEtapa" name="workflowEtapaPerfilAcesso.workflowEtapa.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="workflowEtapaPerfilAcessoWorkflowEtapaId" name="workflowEtapaPerfilAcesso.workflowEtapa.workflowEtapa_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="workflowEtapaPerfilAcessoPerfil">Perfil</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="workflowEtapaPerfilAcessoPerfil" name="workflowEtapaPerfilAcesso.perfil.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="workflowEtapaPerfilAcessoPerfilId" name="workflowEtapaPerfilAcesso.perfil.perfil_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="workflowEtapaPerfilAcessoIsActive">Ativo</label>
							<div class="controls">
								<input type="checkbox" id="workflowEtapaPerfilAcessoIsActive" name="workflowEtapaPerfilAcesso.isActive" checked="checked" value="1" >							
							</div>							
						</div>
						<div class="control-group">
							<label class="control-label" for="workflowEtapaPerfilAcessoIsLeituraEscrita">Leitura e Escrita</label>
							<div class="controls">
								<input type="checkbox" id="workflowEtapaPerfilAcessoIsLeituraEscrita" name="workflowEtapaPerfilAcesso.isLeituraEscrita" checked="checked" value="1" >							
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
							<th>Worklflow</th>
							<th>Worklflow Etapa</th>
							<th>Leitura e Escrita</th>
							<th>Ativo</th>
						</tr>
					</thead>
					<tbody>	
						<c:forEach items="${workflowEtapasPerfilAcesso }" var="workflowEtapaPerfilAcesso">
							<tr>
								<td>${workflowEtapaPerfilAcesso.empresa.nome }</td>
								<td>${workflowEtapaPerfilAcesso.organizacao.nome }</td>
								<td>${workflowEtapaPerfilAcesso.workflowEtapa.nome }</td>
								<td>${workflowEtapaPerfilAcesso.perfil.nome }</td>								
								<td>${workflowEtapaPerfilAcesso.isLeituraEscrita }</td>
								<td>${workflowEtapaPerfilAcesso.isActive }</td>									
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<div class="tab-pane fade" id="workflowtransicao-div"></div>

			</div>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>