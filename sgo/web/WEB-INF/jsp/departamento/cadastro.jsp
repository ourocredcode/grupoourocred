<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#departamento-li-a').click(function() {
		window.location.href = '<c:url value="/departamento/cadastro" />';
	});

	$('#departamentoEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#departamentoEmpresa').val('');
						$('#departamentoEmpresaId').val('');
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
        	 $('#departamentoEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#departamentoEmpresa').val(ui.item.label);
             $('#departamentoEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#departamentoOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#departamentoEmpresaId').val() == '' ? '0' :  $('#departamentoEmpresaId').val(), org_nome : $('#departamentoOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#departamentoOrganizacao').val('');
         	            $('#departamentoOrganizacaoId').val('');
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
          	 $('#departamentoOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#departamentoOrganizacao').val(ui.item.label);
             $('#departamentoOrganizacaoId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/departamento/cadastro" />';
	});

	$('#btnNovo').click(function() {
		document.perfilForm.reset();
	});

	$("#departamentoIsActive").change(function(e){
		$(this).val( $("#departamentoIsActive:checked").length > 0 ? "1" : "0");
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.departamentoForm.reset();
	}
}
</script>

<div id="content-header">
		<h1>Cadastro Departamento</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
		<a href="#" class="current">Departamento</a>
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
				<li class="" id="departamento-li"><a href="#departamento-div" data-toggle="tab" id="departamento-li-a">Cadastro de Departamentos</a></li>				
			</ul>
			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="departamento-div">
				
					<div class="row25MarginTop">
						<div class="span3">
							<form id="departamentoForm" name="departamentoForm" action="<c:url value="/departamento/salva"/>" method="POST">
								<div class="control-group">
									<label class="control-label" for="departamentoEmpresa">Empresa</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span10" id="departamentoEmpresa" name="departamento.empresa.nome" type="text" required onChange="limpaForm();">
			      						<input class="span10" id="departamentoEmpresaId" name="departamento.empresa.empresa_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="departamentoOrganizacao">Organiza��o</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span10" id="departamentoOrganizacao" name="departamento.organizacao.nome" type="text" required onChange="limpaForm();">
			      						<input class="span10" id="departamentoOrganizacaoId" name="departamento.organizacao.organizacao_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="departamentoNome">Nome</label>
									<div class="controls">
										<input class="span10" type="text" id="departamentoNome" name="departamento.nome" placeholder="Nome" required>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="departamentoDescricao">Descri��o</label>
									<div class="controls">
										<input class="span10" type="text" id="departamentoDescricao" name="departamento.descricao" placeholder="Descri��o" required>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="departamentoIsActive">Ativo</label>
									<div class="controls">
										<input type="checkbox" id="departamentoIsActive" name="departamento.isActive" checked="checked" value="1" >							
									</div>
								</div>
							 	<div class="btn-group">
									<button type="submit" class="btn btn-primary" id="btnSalvar">Salvar</button>
								</div>
								<div class="btn-group">
									<button type="button" class="btn btn-primary" id="btnNovo" >Novo</button>
								</div>
								<div class="btn-group">
									<button type="button" class="btn btn-primary" id="btnSair" >Sair</button>
								</div>
							</form>
						</div>
						
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>