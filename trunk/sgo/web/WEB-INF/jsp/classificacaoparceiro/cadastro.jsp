<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#classificacaoparceiro-li-a').click(function() {
		window.location.href = '<c:url value="/classificacaoparceiro/cadastro" />';
	});

	$('#classificacaoParceiroEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#classificacaoParceiroEmpresa').val('');
						$('#classificacaoParceiroEmpresaId').val('');
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
        	 $('#classificacaoParceiroEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#classificacaoParceiroEmpresa').val(ui.item.label);
             $('#classificacaoParceiroEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#classificacaoParceiroOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#classificacaoParceiroEmpresaId').val() == '' ? '0' :  $('#classificacaoParceiroEmpresaId').val(), org_nome : $('#classificacaoParceiroOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#classificacaoParceiroOrganizacao').val('');
         	            $('#classificacaoParceiroOrganizacaoId').val('');
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
          	 $('#classificacaoParceiroOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#classificacaoParceiroOrganizacao').val(ui.item.label);
             $('#classificacaoParceiroOrganizacaoId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/classificacaoparceiro/cadastro" />';
	});

	$('#btnNovo').click(function() {
		document.perfilForm.reset();
	});

	$("#classificacaoParceiroIsActive").change(function(e){
		$(this).val( $("#classificacaoParceiroIsActive:checked").length > 0 ? "1" : "0");
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.classificacaoParceiroForm.reset();
	}
}
</script>

<div class="span9">

	<section id="tabs">
		<div class="bs-docs-example">

			<ul id="myTab" class="nav nav-tabs">
				<li class="" id="classificacaoparceiro-li"><a href="#classificacaoparceiro-div" data-toggle="tab" id="classificacaoparceiro-li-a">Classificacao Parceiro</a></li>				
			</ul>
			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="classificacaoparceiro-div">
				
					<div class="row25MarginTop">
						<div class="span3">
							<form id="classificacaoParceiroForm" name="classificacaoParceiroForm" action="<c:url value="/classificacaoparceiro/salva"/>" method="POST">
								<div class="control-group">
									<label class="control-label" for="classificacaoParceiroEmpresa">Empresa</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span2" id="classificacaoParceiroEmpresa" name="classificacaoParceiro.empresa.nome" type="text" required onChange="limpaForm();">
			      						<input class="span2" id="classificacaoParceiroEmpresaId" name="classificacaoParceiro.empresa.empresa_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="classificacaoParceiroOrganizacao">Organização</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span2" id="classificacaoParceiroOrganizacao" name="classificacaoParceiro.organizacao.nome" type="text" required onChange="limpaForm();">
			      						<input class="span2" id="classificacaoParceiroOrganizacaoId" name="classificacaoParceiro.organizacao.organizacao_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="classificacaoParceiroNome">Nome</label>
									<div class="controls">
										<input type="text" id="classificacaoParceiroNome" name="classificacaoParceiro.nome" placeholder="Nome" required>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="classificacaoParceiroDescricao">Descrição</label>
									<div class="controls">
										<input type="text" id="classificacaoParceiroDescricao" name="classificacaoParceiro.chave" placeholder="Descrição" required>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="classificacaoParceiroIsActive">Ativo</label>
									<div class="controls">
										<input type="checkbox" id="classificacaoParceiroIsActive" name="classificacaoParceiro.isActive" checked="checked" value="1" >							
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
