<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#cidade-li-a').click(function() {
		window.location.href = '<c:url value="/cidade/cadastro" />';
	});

	$('#cidadeEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#cidadeEmpresa').val('');
						$('#cidadeEmpresaId').val('');
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
        	 $('#cidadeEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#cidadeEmpresa').val(ui.item.label);
             $('#cidadeEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#cidadeOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#cidadeEmpresaId').val() == '' ? '0' :  $('#cidadeEmpresaId').val(), org_nome : $('#cidadeOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#cidadeOrganizacao').val('');
         	            $('#cidadeOrganizacaoId').val('');
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
          	 $('#cidadeOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#cidadeOrganizacao').val(ui.item.label);
             $('#cidadeOrganizacaoId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/cidade/cadastro" />';
	});

	$('#btnNovo').click(function() {
		document.cidadeForm.reset();
	});

	$("#cidadeIsActive").change(function(e){
		if(document.cidadeForm.cidadeIsActive.checked==true){
			document.cidadeForm.cidadeIsActive.value=true;
		}else{
			document.cidadeForm.cidadeIsActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.cidadeForm.reset();
	}
}
</script>

<div class="span9">

	<section id="tabs">
		<div class="bs-docs-example">

			<ul id="myTab" class="nav nav-tabs">				
				<li class="active" id="cidade-li"><a href="#cidade-div" data-toggle="tab" id="cidade-li-a">Banco</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">
							
				<div class="tab-pane fade active in" id="cidade-div" >

						<div class="row25MarginTop">
						<form id="cidadeForm" name="cidadeForm" action="<c:url value="/cidade/salva"/>" method="POST">

							<div class="span3">
									<div class="control-group">
										<label class="control-label" for="cidadeEmpresa">Empresa</label>
										<div class="input-prepend">
											<span class="add-on"><i class="icon-plus-sign"></i></span>
				      						<input class="span2" id="cidadeEmpresa" name="cidade.empresa.nome" type="text" required onChange="limpaForm();">
				      						<input class="span2" id="cidadeEmpresaId" name="cidade.empresa.empresa_id" type="hidden">
				    					</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="cidadeOrganizacao">Organização</label>
										<div class="input-prepend">
											<span class="add-on"><i class="icon-plus-sign"></i></span>
				      						<input class="span2" id="cidadeOrganizacao" name="cidade.organizacao.nome" type="text" required onChange="limpaForm();">
				      						<input class="span2" id="cidadeOrganizacaoId" name="cidade.organizacao.organizacao_id" type="hidden">
				    					</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="cidadeNome">Nome</label>
										<div class="controls">
											<input type="text" id="cidadeNome" name="cidade.nome" placeholder="Nome" required>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="cidadeDescricao">Descrição</label>
										<div class="controls">
											<input type="text" id="cidadeDescricao" name="cidade.descricao" placeholder="Descrição" required>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="cidadeIsActive">Ativo</label>
										<div class="controls">
											<input type="checkbox" id="cidadeIsActive" name="cidade.isActive" checked="checked" value="1" >							
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
							
						</form>
						
					</div>
					
				</div>
	
			</div>
		</div>
	</section>
</div>

<%@ include file="/footer.jspf"%>
