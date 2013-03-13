<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#grupoparceiro-li-a').click(function() {
		window.location.href = '<c:url value="/grupoparceiro/cadastro" />';
	});

	$('#grupoParceiroEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#grupoParceiroEmpresa').val('');
						$('#grupoParceiroEmpresaId').val('');
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
        	 $('#grupoParceiroEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#grupoParceiroEmpresa').val(ui.item.label);
             $('#grupoParceiroEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#grupoParceiroOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#grupoParceiroEmpresaId').val() == '' ? '0' :  $('#grupoParceiroEmpresaId').val(), org_nome : $('#grupoParceiroOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#grupoParceiroOrganizacao').val('');
         	            $('#grupoParceiroOrganizacaoId').val('');
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
          	 $('#grupoParceiroOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#grupoParceiroOrganizacao').val(ui.item.label);
             $('#grupoParceiroOrganizacaoId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/grupoparceiro/cadastro" />';
	});

	$('#btnNovo').click(function() {
		document.perfilForm.reset();
	});

	$("#grupoParceiroIsActive").change(function(e){
		$(this).val( $("#grupoParceiroIsActive:checked").length > 0 ? "1" : "0");
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.grupoParceiroForm.reset();
	}
}
</script>

<div class="span9">

	<section id="tabs">
		<div class="bs-docs-example">

			<ul id="myTab" class="nav nav-tabs">
				<li class="" id="grupoparceiro-li"><a href="#grupoparceiro-div" data-toggle="tab" id="grupoparceiro-li-a">Grupo de Parceiro</a></li>				
			</ul>
			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="grupoparceiro-div">
				
					<div class="row25MarginTop">
						<div class="span3">
							<form id="grupoParceiroForm" name="grupoParceiroForm" action="<c:url value="/grupoparceiro/salva"/>" method="POST">
								<div class="control-group">
									<label class="control-label" for="grupoParceiroEmpresa">Empresa</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span2" id="grupoParceiroEmpresa" name="grupoParceiro.empresa.nome" type="text" required onChange="limpaForm();">
			      						<input class="span2" id="grupoParceiroEmpresaId" name="grupoParceiro.empresa.empresa_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="grupoParceiroOrganizacao">Organização</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span2" id="grupoParceiroOrganizacao" name="grupoParceiro.organizacao.nome" type="text" required onChange="limpaForm();">
			      						<input class="span2" id="grupoParceiroOrganizacaoId" name="grupoParceiro.organizacao.organizacao_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="grupoParceiroNome">Nome</label>
									<div class="controls">
										<input type="text" id="grupoParceiroNome" name="grupoParceiro.nome" placeholder="Nome" required>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="grupoParceiroDescricao">Descrição</label>
									<div class="controls">
										<input type="text" id="grupoParceiroDescricao" name="grupoParceiro.chave" placeholder="Descrição" required>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="grupoParceiroIsActive">Ativo</label>
									<div class="controls">
										<input type="checkbox" id="grupoParceiroIsActive" name="grupoParceiro.isActive" checked="checked" value="1" >							
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
	</section>
</div>

<%@ include file="/footer.jspf"%>
