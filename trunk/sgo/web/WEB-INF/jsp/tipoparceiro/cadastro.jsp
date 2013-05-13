<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#tipoparceiro-li-a').click(function() {
		window.location.href = '<c:url value="/tipoparceiro/cadastro" />';
	});

	$('#tipoParceiroEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#tipoParceiroEmpresa').val('');
						$('#tipoParceiroEmpresaId').val('');
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
        	 $('#tipoParceiroEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#tipoParceiroEmpresa').val(ui.item.label);
             $('#tipoParceiroEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#tipoParceiroOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#tipoParceiroEmpresaId').val() == '' ? '0' :  $('#tipoParceiroEmpresaId').val(), org_nome : $('#tipoParceiroOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#tipoParceiroOrganizacao').val('');
         	            $('#tipoParceiroOrganizacaoId').val('');
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
          	 $('#tipoParceiroOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#tipoParceiroOrganizacao').val(ui.item.label);
             $('#tipoParceiroOrganizacaoId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/tipoparceiro/cadastro" />';
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});

	$("#tipoParceiroIsActive").change(function(e){
		if(document.tipoParceiroForm.tipoParceiroIsActive.checked==true){
			document.tipoParceiroForm.tipoParceiroIsActive.value=true;
		}else{
			document.tipoParceiroForm.tipoParceiroIsActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.tipoParceiroForm.reset();
	}
}
</script>

<div class="span9">

	<section id="tabs">
		<div class="bs-docs-example">

			<ul id="myTab" class="nav nav-tabs">
				<li class="" id="tipoparceiro-li"><a href="#tipoparceiro-div" data-toggle="tab" id="tipoparceiro-li-a">Tipo de Parceiro</a></li>				
			</ul>
			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="tipoparceiro-div">
				
					<div class="row25MarginTop">
						<div class="span3">
							<form id="tipoParceiroForm" name="tipoParceiroForm" action="<c:url value="/tipoparceiro/salva"/>" method="POST">
								<div class="control-group">
									<label class="control-label" for="tipoParceiroEmpresa">Empresa</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span2" id="tipoParceiroEmpresa" name="tipoParceiro.empresa.nome" type="text" required onChange="limpaForm();">
			      						<input class="span2" id="tipoParceiroEmpresaId" name="tipoParceiro.empresa.empresa_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="tipoParceiroOrganizacao">Organização</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span2" id="tipoParceiroOrganizacao" name="tipoParceiro.organizacao.nome" type="text" required onChange="limpaForm();">
			      						<input class="span2" id="tipoParceiroOrganizacaoId" name="tipoParceiro.organizacao.organizacao_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="tipoParceiroNome">Nome</label>
									<div class="controls">
										<input type="text" id="tipoParceiroNome" name="tipoParceiro.nome" placeholder="Nome" required>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="tipoParceiroDescricao">Descrição</label>
									<div class="controls">
										<input type="text" id="tipoParceiroDescricao" name="tipoParceiro.descricao" placeholder="Descrição" required>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="tipoParceiroIsActive">Ativo</label>
									<div class="controls">
										<input type="checkbox" id="tipoParceiroIsActive" name="tipoParceiro.isActive" checked="checked" value="1" >							
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
