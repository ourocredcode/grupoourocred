<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#tipoprocedimento-li-a').click(function() {
		window.location.href = '<c:url value="/tipoprocedimento/cadastro" />';
	});

	$('#procedimentoconferencia-li-a').click(function() {
		window.location.href = '<c:url value="/procedimentoconferencia/cadastro" />';
	});

	$('#procedimentoConferenciaEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
	          success : function(data) {  

	       		  if (!data || data.length == 0) {
	       	            $('#procedimentoConferenciaEmpresa').val('');
						$('#procedimentoConferenciaEmpresaId').val('');
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
	    	 $('#procedimentoConferenciaEmpresa').val(ui.item.label);
	         return false;
	     } ,
	     select: function( event, ui ) {
	    	 $('#procedimentoConferenciaEmpresa').val(ui.item.label);
	         $('#procedimentoConferenciaEmpresaId').val(ui.item.value);
	         return false;
	     }
	});

	$('#procedimentoConferenciaOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#procedimentoConferenciaEmpresaId').val() == '' ? '0' :  $('#procedimentoConferenciaEmpresaId').val(), org_nome : $('#procedimentoConferenciaOrganizacao').val()},
	          success : function(data) {  

	        	  if (!data || data.length == 0) {
	     	            $('#procedimentoConferenciaOrganizacao').val('');
	     	           $('#procedimentoConferenciaOrganizacaoId').val('');
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
	      	 $('#procedimentoConferenciaOrganizacao').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#procedimentoConferenciaOrganizacao').val(ui.item.label);
	         $('#procedimentoConferenciaOrganizacaoId').val(ui.item.value);
	         return false;
	     }
	});

	$('#procedimentoConferenciaTipoProcedimento').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/tipoprocedimento/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#procedimentoConferenciaEmpresaId').val() == '' ? '0' :  $('#procedimentoConferenciaEmpresaId').val(), 
	        		  organizacao_id: $('#procedimentoConferenciaOrganizacaoId').val() == '' ? '0' :  $('#procedimentoConferenciaOrganizacaoId').val(),
	        		  nome : $('#procedimentoConferenciaTipoProcedimento').val()},
	          success : function(data) {  

	        	  if (!data || data.length == 0) {
	     	            $('#procedimentoConferenciaTipoProcedimento').val('');
	     	           $('#procedimentoConferenciaTipoProcedimentoId').val('');
	     	        }

	        	  response($.map(data, function(tipoProcedimento) {  
	        		  return {
	        			  label: tipoProcedimento.nome,
	        			  value: tipoProcedimento.tipoProcedimento_id
	                  };
	              }));  
	           }
	        });
	     },
	     focus: function( event, ui ) {
	      	 $('#procedimentoConferenciaTipoProcedimento').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#procedimentoConferenciaTipoProcedimento').val(ui.item.label);
	         $('#procedimentoConferenciaTipoProcedimentoId').val(ui.item.value);
	         return false;
	     }
	});

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/procedimentoconferencia/cadastro" />';
	});
	
	$('#btnNovo').click(function() {
		limpaForm();
	});

	$("#procedimentoConferenciaIsActive").change(function(e){
		if(document.procedimentoConferenciaForm.procedimentoConferenciaIsActive.checked==true){
			document.procedimentoConferenciaForm.procedimentoConferenciaIsActive.value=true;
		}else{
			document.procedimentoConferenciaForm.procedimentoConferenciaIsActive.value=false;
		}
	});

});

function limpaForm(){
	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.procedimentoConferenciaForm.reset();
	}
}

</script>

	<div id="content-header">
		<h1>Procedimento Conferência</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
		<a href="#" class="current">Procedimento Conferência</a>
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
					<li class="" id="tipoprocedimento-li"><a href="#tipoprocedimento-div" data-toggle="tab" id="tipoprocedimento-li-a">Tipo Procedimento</a></li>
					<li class="active" id="procedimentoconferencia-li"><a href="#procedimentoconferencia-div" data-toggle="tab" id="procedimentoconferencia-li-a">Procedimento Conferencia</a></li>
				</ul>

				<div id="myTabContent" class="tab-content">
					
					<div class="tab-pane fade" id="tipoprocedimento-div"></div>
					<div class="tab-pane fade active in" id="procedimentoconferencia-div">
					
						<form id="procedimentoConferenciaForm" name="procedimentoConferenciaForm" action="<c:url value="/procedimentoconferencia/salva"/>" method="POST">
	
							<div class="row-fluid">
	
								<div class="span2">
									<label for="procedimentoConferenciaEmpresa">Empresa</label>
		      						<input class="span10" id="procedimentoConferenciaEmpresa" name="procedimentoConferenciaEmpresa.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" required onChange="limpaForm();" readonly="readonly">
			      					<input class="span1" id="procedimentoConferenciaEmpresaId" name="procedimentoConferenciaEmpresa.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden">
								</div>
								<div class="span2">
									<label for="procedimentoConferenciaOrganizacao">Organização</label>
			      					<input class="span10" id="procedimentoConferenciaOrganizacao" name="procedimentoConferenciaOrganizacao.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" required onChange="limpaForm();" readonly="readonly">
			      					<input class="span1" id="procedimentoConferenciaOrganizacaoId" name="procedimentoConferenciaOrganizacao.organizacao.organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" type="hidden">
								</div>							
								<div class="span2">
									<label for="procedimentoConferenciaTipoProcedimento">Tipo Procedimento</label>
			      					<input class="span10" id="procedimentoConferenciaTipoProcedimento" name="procedimentoConferencia.tipoProcedimento.nome" value="${procedimentoConferencia.tipoProcedimento.nome }" type="text" placeholder="Tipo Procedimento" required onChange="limpaForm();">
			      					<input class="span1" id="procedimentoConferenciaTipoProcedimentoId" name="procedimentoConferencia.tipoProcedimento.tipoProcedimento_id" value="${procedimentoConferencia.tipoProcedimento.tipoProcedimento_id }" type="hidden">
								</div>
								<div class="span2">
									<label for="procedimentoConferenciaNome">Nome</label>							
									<input class="span12" id="procedimentoConferenciaNome" name="procedimentoConferencia.nome" value="${procedimentoConferencia.nome }" type="text" placeholder="Nome" value="${procedimentoConferencia }" required>
									<input class="span1" id="procedimentoConferenciaNomeId" name="procedimentoConferencia.procedimentoConferencia_id" value="4" type="text" >
								</div>
								<div class="span1">
									<label for="procedimentoConferenciaIsActive">Ativo</label>
									<input id="procedimentoConferenciaIsActive" name="procedimentoConferencia.isActive" type="checkbox" checked="checked" value="1" >
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
								<div class="btn-group">
									<input type="button" value="Voltar" id="btnSalvar" onClick="history.go(-1)" class="btn btn-primary" style="width: 100px;">
								</div>
							</div>
						</form>
					</div>
				</div>

				<table class="table table-striped table-bordered">
					<thead>
						<tr>
							<th>Empresa</th>
							<th>Organização</th>
							<th>Tipo Procedimento</th>
							<th>Nome</th>
						</tr>
					</thead>
					<tbody>	
						<c:forEach items="${procedimentosConferencia }" var="procedimentoConferencia">
							<tr>
								<td>${procedimentoConferencia.empresa.nome }</td>
								<td>${procedimentoConferencia.organizacao.nome }</td>
								<td>${procedimentoConferencia.tipoProcedimento.nome }</td>								
								<td>${procedimentoConferencia.nome }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>