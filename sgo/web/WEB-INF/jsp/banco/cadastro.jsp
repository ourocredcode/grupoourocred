<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#grupobanco-li-a').click(function() {
		window.location.href = '<c:url value="/grupobanco/cadastro" />';
	});

	$('#banco-li-a').click(function() {
		window.location.href = '<c:url value="/banco/cadastro" />';
	});
	
	$('#bancoEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#bancoEmpresa').val('');
						$('#bancoEmpresaId').val('');
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
        	 $('#bancoEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#bancoEmpresa').val(ui.item.label);
             $('#bancoEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#bancoOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#bancoEmpresaId').val() == '' ? '0' :  $('#bancoEmpresaId').val(), org_nome : $('#bancoOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#bancoOrganizacao').val('');
         	            $('#bancoOrganizacaoId').val('');
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
          	 $('#bancoOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#bancoOrganizacao').val(ui.item.label);
             $('#bancoOrganizacaoId').val(ui.item.value);
             return false;
         }
    });
	
	$('#bancoGrupoBanco').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/grupobanco/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#bancoEmpresaId').val() == '' ? '0' :  $('#bancoEmpresaId').val(), 
	        		  organizacao_id : $('#bancoOrganizacaoId').val() == '' ? '0' :  $('#bancoOrganizacaoId').val(),
	        		  nome : $('#bancoGrupoBanco').val() },
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#bancoGrupoBanco').val('');
         	            $('#bancoGrupoBancoId').val('');
         	        }

            	  response($.map(data, function(grupoBanco) {  
            		  return {
            			  label: grupoBanco.nome,
            			  value: grupoBanco.grupoBanco_id
                      };
                  }));  
               }
	        });
         },
         focus: function( event, ui ) {
          	 $('#bancoGrupoBanco').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#bancoGrupoBanco').val(ui.item.label);
             $('#bancoGrupoBancoId').val(ui.item.value);
             return false;
         }
    });
	
	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/banco/cadastro" />';
	});

	$('#btnNovo').click(function() {
		document.perfilForm.reset();
	});

	$("#bancoIsActive").change(function(e){
		$(this).val( $("#bancoIsActive:checked").length > 0 ? "1" : "0");
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.bancoForm.reset();
	}
}
</script>

<div id="content-header">
		<h1>Cadastro Banco</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
		<a href="#" class="current">Banco</a>
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
				<li class="" id="grupobanco-li"><a href="#grupobanco-div" data-toggle="tab" id="grupobanco-li-a">Grupo de Bancos</a></li>
				<li class="active" id="banco-li"><a href="#banco-div" data-toggle="tab" id="banco-li-a">Banco</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade" id="grupobanco-div">
				
				
				</div>

				<div class="tab-pane fade active in" id="banco-div" >

						<div class="row25MarginTop">
						<form id="bancoForm" name="bancoForm" action="<c:url value="/banco/salva"/>" method="POST">

							<div class="span3">
									<div class="control-group">
										<label class="control-label" for="bancoEmpresa">Empresa</label>
										<div class="input-prepend">
											<span class="add-on"><i class="icon-plus-sign"></i></span>
				      						<input class="span10" id="bancoEmpresa" name="banco.empresa.nome" type="text" required onChange="limpaForm();">
				      						<input class="span10" id="bancoEmpresaId" name="banco.empresa.empresa_id" type="hidden">
				    					</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="bancoOrganizacao">Organização</label>
										<div class="input-prepend">
											<span class="add-on"><i class="icon-plus-sign"></i></span>
				      						<input class="span10" id="bancoOrganizacao" name="banco.organizacao.nome" type="text" required onChange="limpaForm();">
				      						<input class="span10" id="bancoOrganizacaoId" name="banco.organizacao.organizacao_id" type="hidden">
				    					</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="bancoNome">Nome</label>
										<div class="controls">
											<input type="text" id="bancoNome" name="banco.nome" placeholder="Nome" required>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="bancoDescricao">Descrição</label>
										<div class="controls">
											<input type="text" id="bancoDescricao" name="banco.descricao" placeholder="Descrição" required>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="bancoIsActive">Ativo</label>
										<div class="controls">
											<input type="checkbox" id="bancoIsActive" name="banco.isActive" checked="checked" value="1" >							
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
								
							</div>
	
							<div class="span3">

									<div class="control-group">
										<label class="control-label" for="bancoGrupoBanco">Grupo banco</label>
										<div class="input-prepend">
											<span class="add-on"><i class="icon-plus-sign"></i></span>
				      						<input class="span10" id="bancoGrupoBanco" name="banco.grupoBanco.nome" type="text" required onChange="limpaForm();">
				      						<input class="span10" id="bancoGrupoBancoId" name="banco.grupoBanco.grupoBanco_id" type="hidden">
				    					</div>
									</div>

							</div>

						</form>
						
					</div>
					
				</div>
	
			</div>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>
