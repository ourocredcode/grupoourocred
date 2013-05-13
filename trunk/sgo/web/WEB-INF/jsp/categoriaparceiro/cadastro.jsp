<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#categoriaparceiro-li-a').click(function() {
		window.location.href = '<c:url value="/categoriaparceiro/cadastro" />';
	});

	$('#categoriaParceiroEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#categoriaParceiroEmpresa').val('');
						$('#categoriaParceiroEmpresaId').val('');
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
        	 $('#categoriaParceiroEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#categoriaParceiroEmpresa').val(ui.item.label);
             $('#categoriaParceiroEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#categoriaParceiroOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#categoriaParceiroEmpresaId').val() == '' ? '0' :  $('#categoriaParceiroEmpresaId').val(), org_nome : $('#categoriaParceiroOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#categoriaParceiroOrganizacao').val('');
         	            $('#categoriaParceiroOrganizacaoId').val('');
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
          	 $('#categoriaParceiroOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#categoriaParceiroOrganizacao').val(ui.item.label);
             $('#categoriaParceiroOrganizacaoId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/categoriaparceiro/cadastro" />';
	});

	$('#btnNovo').click(function() {
		document.categoriaParceiroForm.reset();
	});

	$("#categoriaParceiroIsActive").change(function(e){
		if(document.categoriaParceiroForm.categoriaParceiroIsActive.checked==true){
			document.categoriaParceiroForm.categoriaParceiroIsActive.value=true;
		}else{
			document.categoriaParceiroForm.categoriaParceiroIsActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.categoriaParceiroForm.reset();
	}
}
</script>

<div class="span9">

	<section id="tabs">
		<div class="bs-docs-example">

			<ul id="myTab" class="nav nav-tabs">
				<li class="" id="categoriaparceiro-li"><a href="#categoriaparceiro-div" data-toggle="tab" id="categoriaparceiro-li-a">Categoria Parceiro</a></li>				
			</ul>
			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="categoriaparceiro-div">
				
					<div class="row25MarginTop">
						<div class="span3">
							<form id="categoriaParceiroForm" name="categoriaParceiroForm" action="<c:url value="/categoriaparceiro/salva"/>" method="POST">
								<div class="control-group">
									<label class="control-label" for="categoriaParceiroEmpresa">Empresa</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span2" id="categoriaParceiroEmpresa" name="categoriaParceiro.empresa.nome" type="text" required onChange="limpaForm();">
			      						<input class="span2" id="categoriaParceiroEmpresaId" name="categoriaParceiro.empresa.empresa_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="categoriaParceiroOrganizacao">Organização</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span2" id="categoriaParceiroOrganizacao" name="categoriaParceiro.organizacao.nome" type="text" required onChange="limpaForm();">
			      						<input class="span2" id="categoriaParceiroOrganizacaoId" name="categoriaParceiro.organizacao.organizacao_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="categoriaParceiroNome">Nome</label>
									<div class="controls">
										<input type="text" id="categoriaParceiroNome" name="categoriaParceiro.nome" placeholder="Nome" required>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="categoriaParceiroDescricao">Descrição</label>
									<div class="controls">
										<input type="text" id="categoriaParceiroDescricao" name="categoriaParceiro.descricao" placeholder="Descrição" required>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="categoriaParceiroIsActive">Ativo</label>
									<div class="controls">
										<input type="checkbox" id="categoriaParceiroIsActive" name="categoriaParceiro.isActive" checked="checked" value="1" >							
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
