<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#tipoconta-li-a').click(function() {
		window.location.href = '<c:url value="/tipoconta/cadastro" />';
	});

	$('#tipoContaEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#tipoContaEmpresa').val('');
						$('#tipoContaEmpresaId').val('');
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
        	 $('#tipoContaEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#tipoContaEmpresa').val(ui.item.label);
             $('#tipoContaEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#tipoContaOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#tipoContaEmpresaId').val() == '' ? '0' :  $('#tipoContaEmpresaId').val(), org_nome : $('#tipoContaOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#tipoContaOrganizacao').val('');
         	            $('#tipoContaOrganizacaoId').val('');
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
          	 $('#tipoContaOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#tipoContaOrganizacao').val(ui.item.label);
             $('#tipoContaOrganizacaoId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/tipoconta/cadastro" />';
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});

	$("#tipoContaIsActive").change(function(e){
		if(document.tipoContaForm.tipoContaIsActive.checked==true){
			document.tipoContaForm.tipoContaIsActive.value=true;
		}else{
			document.tipoContaForm.tipoContaIsActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.tipocontaForm.reset();
	}
}
</script>

<div class="span9">

	<section id="tabs">
		<div class="bs-docs-example">

			<ul id="myTab" class="nav nav-tabs">
				<li class="" id="tipoconta-li"><a href="#tipoconta-div" data-toggle="tab" id="tipoconta-li-a">Cadastro de Tipo de Contas</a></li>				
			</ul>
			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="tipoconta-div">
				
					<div class="row25MarginTop">
						<div class="span3">
							<form id="tipoContaForm" name="tipoContaForm" action="<c:url value="/tipoconta/salva"/>" method="POST">
								<div class="control-group">
									<label class="control-label" for="tipoContaEmpresa">Empresa</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span2" id="tipoContaEmpresa" name="tipoConta.empresa.nome" type="text" required onChange="limpaForm();">
			      						<input class="span2" id="tipoContaEmpresaId" name="tipoConta.empresa.empresa_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="tipoContaOrganizacao">Organização</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span2" id="tipoContaOrganizacao" name="tipoConta.organizacao.nome" type="text" required onChange="limpaForm();">
			      						<input class="span2" id="tipoContaOrganizacaoId" name="tipoConta.organizacao.organizacao_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="tipoContaNome">Nome</label>
									<div class="controls">
										<input type="text" id="tipoContaNome" name="tipoConta.nome" placeholder="Nome" required>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="tipoContaDescricao">Descrição</label>
									<div class="controls">
										<input type="text" id="tipoContaDescricao" name="tipoConta.descricao" placeholder="Descrição" required>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="tipoContaIsActive">Ativo</label>
									<div class="controls">
										<input type="checkbox" id="tipoContaIsActive" name="tipoConta.isActive" checked="checked" value="1" >							
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
