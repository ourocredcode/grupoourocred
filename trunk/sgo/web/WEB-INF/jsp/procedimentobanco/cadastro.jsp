<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#procedimentobanco-li-a').click(function() {
		window.location.href = '<c:url value="/procedimentobanco/cadastro" />';
	});
		
	$('#procedimentoBancoEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
	          success : function(data) {  

	       		  if (!data || data.length == 0) {
	       	            $('#procedimentoBancoEmpresa').val('');
						$('#procedimentoBancoEmpresaId').val('');
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
	    	 $('#procedimentoBancoEmpresa').val(ui.item.label);
	         return false;
	     } ,
	     select: function( event, ui ) {
	    	 $('#procedimentoBancoEmpresa').val(ui.item.label);
	         $('#procedimentoBancoEmpresaId').val(ui.item.value);
	         return false;
	     }
	});

	$('#procedimentoBancoOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#procedimentoBancoEmpresaId').val() == '' ? '0' :  $('#procedimentoBancoEmpresaId').val(), org_nome : $('#procedimentoBancoOrganizacao').val()},
	          success : function(data) {  

	        	  if (!data || data.length == 0) {
	     	            $('#procedimentoBancoOrganizacao').val('');
	     	           $('#procedimentoBancoOrganizacaoId').val('');
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
	      	 $('#procedimentoBancoOrganizacao').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#procedimentoBancoOrganizacao').val(ui.item.label);
	         $('#procedimentoBancoOrganizacaoId').val(ui.item.value);
	         return false;
	     }
	});

	$('#procedimentoBancoProcedimentoConferencia').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/procedimento/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#procedimentoBancoEmpresaId').val() == '' ? '0' :  $('#procedimentoBancoEmpresaId').val(), 
	        		  organizacao_id: $('#procedimentoBancoOrganizacaoId').val() == '' ? '0' :  $('#procedimentoBancoOrganizacaoId').val(),
	        		  nome : $('#procedimentoBancoProcedimentoConferencia').val()},
	          success : function(data) {  

	        	  if (!data || data.length == 0) {
	     	            $('#procedimentoBancoProcedimentoConferencia').val('');
	     	           $('#procedimentoBancoProcedimentoConferenciaId').val('');
	     	        }

	        	  response($.map(data, function(procedimentoConferencia) {  
	        		  return {
	        			  label: procedimentoConferencia.nome,
	        			  value: procedimentoConferencia.procedimentoconferencia_id
	                  };
	              }));  
	           }
	        });
	     },
	     focus: function( event, ui ) {
	      	 $('#procedimentoBancoProcedimentoConferencia').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#procedimentoBancoProcedimentoConferencia').val(ui.item.label);
	         $('#procedimentoBancoProcedimentoConferenciaId').val(ui.item.value);
	         return false;
	     }
	});

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/procedimentobanco/cadastro" />';
	});
	
	$('#btnNovo').click(function() {
		limpaForm();
	});

	$("#procedimentoBancoIsActive").change(function(e){
		if(document.procedimentoBancoForm.procedimentoBancoIsActive.checked==true){
			document.procedimentoBancoForm.procedimentoBancoIsActive.value=true;
		}else{
			document.procedimentoBancoForm.procedimentoBancoIsActive.value=false;
		}
	});

});

function limpaForm(){
	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.procedimentoBancoForm.reset();
	}
}

</script>

	<div id="content-header">
		<h1>Procedimentos Bancos</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
		<a href="#" class="current">Procedimentos Bancos</a>
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
					<li class="active" id="procedimentobanco-li"><a href="#procedimentobanco-div" data-toggle="tab" id="procedimentobanco-li-a">Procedimento Banco</a></li>
				</ul>
				
				<div class="accordion" id="collapse-group">
                 	<div class="accordion-group widget-box">
	                   	<div class="accordion-heading">
	                       	<div class="widget-title">
	                           	<a data-parent="#collapse-group" href="#collapseGOne" data-toggle="collapse">
	                              	<span class="icon"><i class="icon-magnet"></i></span><h5>Procedimentos Bancos</h5>
	                          	</a>
	                      	</div>
	                   	</div>
	                   	<div class="collapse in accordion-body" id="collapseGOne">
	                       	<div class="widget-content">
	                          	<table class="table table-striped table-bordered">
									<thead>
										<tr>
											<th>Nome</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${procedimentosConferencia }" var="procedimentoConferencia">
											<tr>
												<td class="label_txt">
													<a href="<c:url value="/procedimentobanco/detalhebanco/${procedimentoConferencia.procedimentoConferencia_id}"/>">${procedimentoConferencia.nome }</a>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
	                      	</div>
	                  	</div>
              		</div>
             	</div> 

				

		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>