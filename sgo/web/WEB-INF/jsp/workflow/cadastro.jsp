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
		limpaForm();
	});

	$("#workflowIsActive").change(function(e){		
		if(document.workflowForm.workflowIsActive.checked==true){
			document.workflowForm.workflowIsActive.value=true;
		}else{
			document.workflowForm.workflowIsActive.value=false;
		}
	});
});

function altera(linha, id) {

	var valor = linha.checked == true ? true : false ;

	if (window.confirm("Deseja alterar o workflow selecionado?"))
		$.post('<c:url value="/workflow/altera" />', {
			'workflow.workflow_id' : id, 'workflow.isActive' : valor
		});

	return false;
}

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.workflowForm.reset();
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
				<div class="tab-pane fade active in" id="perfil-div">

					<form id="workflowForm" name="workflowForm" action="<c:url value="/workflow/salva"/>" method="POST">

						<div class="row-fluid">
							<div class="span3">
								<label for="workflowEmpresa">Empresa</label>							
	      						<input class="input-xlarge" id="workflowEmpresa" name="workflow.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" required onChange="limpaForm();" readonly="readonly">
	      						<input class="span1" id="workflowEmpresaId" name="workflow.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden">	    					
							</div>
							<div class="span3">
								<label for="workflowOrganizacao">Organização</label>
	      						<input class="input-xlarge" id="workflowOrganizacao" name="workflow.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" required onChange="limpaForm();" readonly="readonly">
	      						<input class="span1" id="workflowOrganizacaoId" name="workflow.organizacao.organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" type="hidden">
							</div>
						</div>
						<div class="row-fluid">
							<div class="span2">
								<label for="workflowTipoWorkflow">Tipo de Worflow</label>
								<select class="input-medim" id="workflowTipoWorkflowId" name="workflow.tipoWorkflow.tipoWorkflow_id" >
									<c:forEach var="tipoWorkflow" items="${tiposWorkflow }">
									 	<option value="${tipoWorkflow.tipoWorkflow_id }" selected="selected"> ${tipoWorkflow.nome }
									 	</option>
									</c:forEach>
								</select>
							</div>
							<div class="span3">
								<label for="workflowNome">Nome</label>							
								<input class="input-xlarge" id="workflowNome" name="workflow.nome" value="${workflow.nome }" type="text" placeholder="Nome" required>							
							</div>							
							<div class="span1">								
								<label for="workflowIsActive">Ativo</label>							
								<input id="workflowIsActive" name="workflow.isActive" type="checkbox" checked="checked" value="1" >
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

				<div class="tab-pane fade" id="workflowperfilacesso-div"></div>
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
					<h5>Workflow</h5>
				</div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty workflows}">
						<table
							class="table table-bordered table-striped table-hover data-table"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>Empresa</th>
									<th>Organização</th>
									<th>Nome</th>
									<th>Ativo</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${workflows }" var="workflow">
									<tr>
										<td>${workflow.empresa.nome }</td>
										<td>${workflow.organizacao.nome }</td>
										<td>${workflow.nome }</td>
										<td>
											<label class="checkbox inline">
												<input type="checkbox" id="workflowIsActiveLine" name="workflow.isActive"
												<c:if test="${workflow.isActive == true }"> checked="checked"</c:if> onchange="altera(this,'${workflow.workflow_id}');">
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