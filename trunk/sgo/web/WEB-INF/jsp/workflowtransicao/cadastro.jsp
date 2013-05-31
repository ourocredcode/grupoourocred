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

	$('#workflowTransicaoPerfil').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/perfil/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#workflowTransicaoEmpresaId').val() == '' ? '0' :  $('#workflowTransicaoEmpresaId').val(),
	        		  organizacao_id: $('#workflowTransicaoOrganizacaoId').val() == '' ? '0' :  $('#workflowTransicaoOrganizacaoId').val(),
	        		  nome : $('#workflowTransicaoPerfil').val()},

	          success : function(data) {
	        	  if (!data || data.length == 0) {
	     	            $('#workflowTransicaoPerfil').val('');
	     	           	$('#workflowTransicaoPerfilId').val('');
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
	      	 $('#workflowTransicaoPerfil').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#workflowTransicaoPerfil').val(ui.item.label);
	         $('#workflowTransicaoPerfilId').val(ui.item.value);

	         return false;
	     }
	});

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/workflowtransicao/cadastro" />';
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});

	$("#workflowTransicaoIsActive").change(function(e){
		if(document.workflowTransicaoForm.workflowTransicaoIsActive.checked==true){
			document.workflowTransicaoForm.workflowTransicaoIsActive.value=true;
		}else{
			document.workflowTransicaoForm.workflowTransicaoIsActive.value=false;
		}
	});

});

function limpaForm(){
	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.workflowTransicaoForm.reset();
	}
}

function buscaPerfisEtapa(){

	var empresa_id = $('#workflowTransicaoEmpresaId').val();
	var organizacao_id = $('#workflowTransicaoOrganizacaoId').val();
	var workflow_id = $('#workflowTransicaoWorkflowId').val();

	$("#workflowTransicaoPerfilId").load('<c:url value="/workflowtransicao/workflowperfil" />',
			{'empresa_id': empresa_id, 'organizacao_id' : organizacao_id, 'workflow_id' : workflow_id});

}

function buscaEtapasWorkflow(){

	var empresa_id = $('#workflowTransicaoEmpresaId').val();
	var organizacao_id = $('#workflowTransicaoOrganizacaoId').val();
	var workflow_id = $('#workflowTransicaoWorkflowId').val();

	$("#workflowTransicaoWorkflowEtapaId").load('<c:url value="/workflowtransicao/workflowtransicaoetapas" />',			
			{'empresa_id': empresa_id, 'organizacao_id' : organizacao_id, 'workflow_id' : workflow_id});

}

function buscaWorkflowEtapaTransicao(){

	var empresa_id = $('#workflowTransicaoEmpresaId').val();
	var organizacao_id = $('#workflowTransicaoOrganizacaoId').val();
	var workflow_id = $('#workflowTransicaoWorkflowId').val();

	$("#workflowTransicaoWorkflowEtapaProximoId").load('<c:url value="/workflowtransicao/workflowtransicaoetapas" />',			
			{'empresa_id': empresa_id, 'organizacao_id' : organizacao_id, 'workflow_id' : workflow_id});

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
		<a href="#" class="current">Workflow Transição</a>
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

						<div class="row-fluid">
							<div class="span2">
								<label for="workflowTransicaoEmpresa">Empresa</label>
		      					<input class="span12" id="workflowTransicaoEmpresa" name="workflowTransicao.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" required onChange="limpaForm();" readonly="readonly">
		      					<input class="span1" id="workflowTransicaoEmpresaId" name="workflowTransicao.empresa.empresa_id"  value="${usuarioInfo.empresa.empresa_id }" type="hidden">
							</div>
							<div class="span2">
								<label for="workflowTransicaoOrganizacao">Organização</label>
		      					<input class="span12" id="workflowTransicaoOrganizacao" name="workflowTransicao.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" required onChange="limpaForm();" readonly="readonly">
		      					<input class="span1" id="workflowTransicaoOrganizacaoId" name="workflowTransicao.organizacao.organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" type="hidden">
							</div>

							<div class="span2">
								<label for="workflowTransicaoWorkflow">Workflow</label>
								<select id="workflowTransicaoWorkflowId" name="workflowTransicao.workflow.workflow_id" class="input-medium" onchange="buscaPerfisEtapa();">
									<c:forEach var="workflow" items="${workflows }">
									 	<option value="${workflow.workflow_id }" selected="selected"> ${workflow.nome }
									 	</option>
									</c:forEach>
								</select>
							</div>
							<div class="span2">
	      						<label for="workflowTransicaoPerfilId">Perfil</label>
	      						<select id="workflowTransicaoPerfilId" name="workflowTransicao.perfil.perfil_id" class="input-medium" onchange="buscaEtapasWorkflow();">
	      							<option value="">Selecion um Perfil...</option>
	      						</select>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span2">
	      						<label for="workflowTransicaoWorkflowEtapaId">Etapas</label>
	      						<select  class="span12" id="workflowTransicaoWorkflowEtapaId" name="workflowTransicao.workflowEtapa.workflowEtapa_id" class="input-medium" onchange="buscaWorkflowEtapaTransicao();">
	      							<option value="">Selecion uma Etapa...</option>
	      						</select>
							</div>
							<div class="span2">
	      						<label for="workflowTransicaoWorkflowEtapaProximoId">Etapas Próximo</label>
	      						<select  class="span12" id="workflowTransicaoWorkflowEtapaProximoId" name="workflowTransicao.workflowEtapaProximo.workflowEtapa_id" class="input-medium">
	      							<option value="">Selecion uma Etapa...</option>
	      						</select>
							</div>
							<!--div class="span2">
								<label for="workflowTransicaoWorkflowEtapaProximoId">Etapas Próximo</label>
								<select id="workflowTransicaoWorkflowEtapaProximoId" name="workflowTransicao.workflowEtapaProximo.workflowEtapa_id" class="input-medium">
									<c:forEach var="workflowEtapa" items="${workflowEtapas }">
									 	<option value="${workflowEtapa.workflowEtapa_id }" selected="selected"> ${workflowEtapa.nome }
									 	</option>
									</c:forEach>
								</select>
							</div>
							 -->
							<div class="span1">
								<label for="workflowTransicaoIsActive">Ativo</label>
								<input id="workflowTransicaoIsActive" name="workflowTransicao.isActive" type="checkbox" checked="checked" value="1" >
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
							<th>Worklflow Próxima</th>
							<th>Perfil</th>
							<th>Sequência</th>
							<th>Padrão</th>
							<th>Ativo</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${workflowTransicoes }" var="workflowTransicao">
							<tr>
								<td>${workflowTransicao.empresa.nome }</td>
								<td>${workflowTransicao.organizacao.nome }</td>
								<td>${workflowTransicao.workflow.nome }</td>
								<td>${workflowTransicao.workflowEtapa.nome }</td>
								<td>${workflowTransicao.workflowEtapaProximo.nome }</td>
								<td>${workflowTransicao.perfil.nome }</td>
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