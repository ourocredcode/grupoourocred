<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#grupobanco-li-a').click(function() {
		window.location.href = '<c:url value="/grupobanco/cadastro" />';
	});

	$('#banco-li-a').click(function() {
		window.location.href = '<c:url value="/banco/cadastro" />';
	});
	
	$('#grupoBancoEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#grupoBancoEmpresa').val('');
						$('#grupoBancoEmpresaId').val('');
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
        	 $('#grupoBancoEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#grupoBancoEmpresa').val(ui.item.label);
             $('#grupoBancoEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#grupoBancoOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#grupoBancoEmpresaId').val() == '' ? '0' :  $('#grupoBancoEmpresaId').val(), org_nome : $('#grupoBancoOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#grupoBancoOrganizacao').val('');
         	            $('#grupoBancoOrganizacaoId').val('');
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
          	 $('#grupoBancoOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#grupoBancoOrganizacao').val(ui.item.label);
             $('#grupoBancoOrganizacaoId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/grupobanco/cadastro" />';
	});

	$('#btnNovo').click(function() {
		document.perfilForm.reset();
	});

	$("#grupoBancoIsActive").change(function(e){
		$(this).val( $("#grupoBancoIsActive:checked").length > 0 ? "1" : "0");
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.produtoForm.reset();
	}
}
</script>

<div class="span9">

	<section id="tabs">
		<div class="bs-docs-example">

			<ul id="myTab" class="nav nav-tabs">								
				<li class="active" id="grupobanco-li"><a href="#grupobanco-div" data-toggle="tab" id="grupobanco-li-a">Grupo de Bancos</a></li>
				<li class="" id="banco-li"><a href="#banco-div" data-toggle="tab" id="banco-li-a">Banco</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="grupobanco-div">
				
						<div class="row25MarginTop">
						<div class="span3">
							<form id="grupoBancoForm" name="grupoBancoForm" action="<c:url value="/grupobanco/salva"/>" method="POST">
								<div class="control-group">
									<label class="control-label" for="grupoBancoEmpresa">Empresa</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span2" id="grupoBancoEmpresa" name="grupoBanco.empresa.nome" type="text" required onChange="limpaForm();">
			      						<input class="span2" id="grupoBancoEmpresaId" name="grupoBanco.empresa.empresa_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="grupoBancoOrganizacao">Organização</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span2" id="grupoBancoOrganizacao" name="grupoBanco.organizacao.nome" type="text" required onChange="limpaForm();">
			      						<input class="span2" id="grupoBancoOrganizacaoId" name="grupoBanco.organizacao.organizacao_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="grupoBancoNome">Nome</label>
									<div class="controls">
										<input type="text" id="grupoBancoNome" name="grupoBanco.nome" placeholder="Nome" required>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="grupoBancoDescricao">Descrição</label>
									<div class="controls">
										<input type="text" id="grupoBancoDescricao" name="grupoBanco.descricao" placeholder="Descrição" required>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="grupoBancoIsActive">Ativo</label>
									<div class="controls">
										<input type="checkbox" id="grupoBancoIsActive" name="grupoBanco.isActive" checked="checked" value="1" >							
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

				<div class="tab-pane fade " id="banco-div" >

						
					
				</div>
	
			</div>
		</div>
	</section>
</div>

<%@ include file="/footer.jspf"%>
