<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#tipologistica-li-a').click(function() {
		window.location.href = '<c:url value="/tipologistica/cadastro" />';
	});

	$('#tipologisticaperfil-li-a').click(function() {
		window.location.href = '<c:url value="/tipologisticaperfil/cadastro" />';
	});

	$('#tipoLogisticaPerfilEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#tipoLogisticaPerfilEmpresa').val('');
						$('#tipoLogisticaPerfilEmpresaId').val('');
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
        	 $('#tipoLogisticaPerfilEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {
        	 $('#tipoLogisticaPerfilEmpresa').val(ui.item.label);
             $('#tipoLogisticaPerfilEmpresaId').val(ui.item.value);
             return false;
         }
    });
	
	$('#tipoLogisticaPerfilOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#tipoLogisticaPerfilEmpresaId').val() == '' ? '0' :  $('#tipoLogisticaPerfilEmpresaId').val(), org_nome : $('#tipoLogisticaPerfilOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#tipoLogisticaPerfilOrganizacao').val('');
         	           $('#tipoLogisticaPerfilOrganizacaoId').val('');
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
          	 $('#tipoLogisticaPerfilOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#tipoLogisticaPerfilOrganizacao').val(ui.item.label);
             $('#tipoLogisticaPerfilOrganizacaoId').val(ui.item.value);
             return false;
         }
    });
	
	$('#tipoLogisticaPerfilTipoLogistica').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/tipologistica/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#tipoLogisticaPerfilEmpresaId').val() == '' ? '0' :  $('#tipoLogisticaPerfilEmpresaId').val(), 
	        		  organizacao_id: $('#tipoLogisticaPerfilOrganizacaoId').val() == '' ? '0' :  $('#tipoLogisticaPerfilOrganizacaoId').val(),
	        				  nome : $('#tipoLogisticaPerfilTipoLogistica').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#tipoLogisticaPerfilTipoLogistica').val('');
         	           $('#tipoLogisticaPerfilTipoLogisticaId').val('');
         	        }

            	  response($.map(data, function(tipoLogistica) {  
            		  return {
            			  label: tipoLogistica.nome,
            			  value: tipoLogistica.tipoLogistica_id
                      };
                  }));  
               }
	        });
         },
         focus: function( event, ui ) {
          	 $('#tipoLogisticaPerfilTipoLogistica').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#tipoLogisticaPerfilTipoLogistica').val(ui.item.label);
             $('#tipoLogisticaPerfilTipoLogisticaId').val(ui.item.value);
             return false;
         }
    });
	
	$('#tipoLogisticaPerfilPerfil').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/perfil/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#tipoLogisticaPerfilEmpresaId').val() == '' ? '0' :  $('#tipoLogisticaPerfilEmpresaId').val(), 
	        		  organizacao_id: $('#tipoLogisticaPerfilOrganizacaoId').val() == '' ? '0' :  $('#tipoLogisticaPerfilOrganizacaoId').val(),
	        		  nome : $('#tipoLogisticaPerfilPerfil').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#tipoLogisticaPerfilPerfil').val('');
         	           $('#tipoLogisticaPerfilPerfilId').val('');
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
          	 $('#tipoLogisticaPerfilPerfil').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#tipoLogisticaPerfilPerfil').val(ui.item.label);
             $('#tipoLogisticaPerfilPerfilId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/tipologistica/cadastro" />';
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});

	$("#workflowEtapaPerfilAcessoIsLeituraEscrita").change(function(e){
		if(document.workflowEtapaPerfilAcessoForm.workflowEtapaPerfilAcessoIsLeituraEscrita.checked==true){
			document.workflowEtapaPerfilAcessoForm.workflowEtapaPerfilAcessoIsLeituraEscrita.value=true;
		}else{
			document.workflowEtapaPerfilAcessoForm.workflowEtapaPerfilAcessoIsLeituraEscrita.value=false;
		}
	});

	$("#tipoLogisticaPerfilIsActive").change(function(e){
		if(document.tipoLogisticaPerfilForm.tipoLogisticaPerfilIsActive.checked==true){
			document.tipoLogisticaPerfilForm.tipoLogisticaPerfilIsActive.value=true;
		}else{
			document.tipoLogisticaPerfilForm.tipoLogisticaPerfilIsActive.value=false;
		}
	});

});

function limpaForm(){

	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.tipoLogisticaPerfilForm.reset();
	}

}


</script>

<div id="content-header">
		<h1>Cadastro Tipo de Logistica Perfil</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
		</div>
	</div>

	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
		<a href="#" class="current">Tipo de Logistica Peril</a>
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
				<li class="" id="tipologistica-li"><a href="#tipologistica-div" data-toggle="tab" id="tipologistica-li-a">Tipo de Logística</a></li>
				<li class="active" id="tipologisticaperfil-li"><a href="#tipologisticaperfil-div" data-toggle="tab" id="tipologisticaperfil-li-a">Tipo de Logística Perfil</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade" id="tipologistica-div"></div>
				<div class="tab-pane fade active in" id="tipologisticaperfil-div">					
					
					<form id="tipoLogisticaPerfilForm" name="tipoLogisticaPerfilForm" action="<c:url value="/tipologisticaperfil/salva"/>" method="POST">

						<div class="row-fluid">
							<div class="span2">
								<label for="tipoLogisticaPerfilEmpresa">Empresa</label>
	      						<input class="span12" id="tipoLogisticaPerfilEmpresa" name="tipoLogisticaPerfil.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" required onChange="limpaForm();" >
	      						<input class="span10" id="tipoLogisticaPerfilEmpresaId" name="tipoLogisticaPerfil.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="text">
	    					</div>
							<div class="span2">
								<label for="tipoLogisticaPerfilOrganizacao">Organização</label>
								<input class="span12" id="tipoLogisticaPerfilOrganizacao" name="tipoLogisticaPerfil.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="tipoLogisticaPerfilOrganizacaoId" name="tipoLogisticaPerfil.organizacao.organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" type="text">
	    					</div>
						</div>
						<div class="row-fluid">
							<div class="span2">
								<label for="tipoLogisticaPerfilTipoLogistica">Tipo Logistica</label>								
		      					<input class="span12" id="tipoLogisticaPerfilTipoLogistica" name="tipoLogisticaPerfil.tipoLogistica.nome" type="text" required onChange="limpaForm();">
		      					<input class="span10" id="tipoLogisticaPerfilTipoLogisticaId" name="tipoLogisticaPerfil.tipoLogistica.tipoLogistica_id" type="text">
	    					</div>
							<div class="span2">
								<label for="tipoLogisticaPerfilPerfil">Perfil</label>								
		      					<input class="span12" id="tipoLogisticaPerfilPerfil" name="tipoLogisticaPerfil.perfil.nome" type="text" required onChange="limpaForm();">
		      					<input class="span10" id="tipoLogisticaPerfilPerfilId" name="tipoLogisticaPerfil.perfil.perfil_id" type="text">
							</div>
							<div class="span1">
								<label for="tipoLogisticaPerfilPerfilIsLeituraEscrita">Escrita</label>
								<input id="tipoLogisticaPerfilPerfilIsLeituraEscrita" name="tipoLogisticaPerfil.isLeituraEscrita" type="checkbox" checked="checked" value="1" >
							</div>
							<div class="span1">
								<label for="tipoLogisticaPerfilIsActive">Ativo</label>
								<input type="checkbox" id="tipoLogisticaPerfilIsActive" name="tipoLogisticaPerfil.isActive" checked="checked" value="1">
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
			</div>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>
