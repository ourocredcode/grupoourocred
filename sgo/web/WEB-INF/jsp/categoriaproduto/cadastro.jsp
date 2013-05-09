<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#categoriaproduto-li-a').click(function() {
		window.location.href = '<c:url value="/categoriaproduto/cadastro" />';
	});

	$('#categoriaProdutoEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#categoriaProdutoEmpresa').val('');
						$('#categoriaProdutoEmpresaId').val('');
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
        	 $('#categoriaProdutoEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#categoriaProdutoEmpresa').val(ui.item.label);
             $('#categoriaProdutoEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#categoriaProdutoOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#categoriaProdutoEmpresaId').val() == '' ? '0' :  $('#categoriaProdutoEmpresaId').val(), org_nome : $('#categoriaProdutoOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#categoriaProdutoOrganizacao').val('');
         	            $('#categoriaProdutoOrganizacaoId').val('');
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
          	 $('#categoriaProdutoOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#categoriaProdutoOrganizacao').val(ui.item.label);
             $('#categoriaProdutoOrganizacaoId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/categoriaproduto/cadastro" />';
	});

	$('#btnNovo').click(function() {
		document.perfilForm.reset();
	});

	$("#categoriaProdutoIsActive").change(function(e){
		$(this).val( $("#categoriaProdutoIsActive:checked").length > 0 ? true : false);
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.categoriaProdutoForm.reset();
	}
}
</script>

<div class="span9">

	<section id="tabs">
		<div class="bs-docs-example">

			<ul id="myTab" class="nav nav-tabs">
				<li class="" id="categoriaproduto-li"><a href="#categoriaproduto-div" data-toggle="tab" id="categoriaproduto-li-a">Categoria de Produtos</a></li>				
			</ul>
			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="categoriaproduto-div">
				
					<div class="row25MarginTop">
						<div class="span3">
							<form id="categoriaProdutoForm" name="categoriaProdutoForm" action="<c:url value="/categoriaproduto/salva"/>" method="POST">
								<div class="control-group">
									<label class="control-label" for="categoriaProdutoEmpresa">Empresa</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span2" id="categoriaProdutoEmpresa" name="categoriaProduto.empresa.nome" type="text" required onChange="limpaForm();">
			      						<input class="span2" id="categoriaProdutoEmpresaId" name="categoriaProduto.empresa.empresa_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="categoriaProdutoOrganizacao">Organização</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span2" id="categoriaProdutoOrganizacao" name="categoriaProduto.organizacao.nome" type="text" required onChange="limpaForm();">
			      						<input class="span2" id="categoriaProdutoOrganizacaoId" name="categoriaProduto.organizacao.organizacao_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="categoriaProdutoNome">Nome</label>
									<div class="controls">
										<input type="text" id="categoriaProdutoNome" name="categoriaProduto.nome" placeholder="Nome" required>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="categoriaProdutoDescricao">Descrição</label>
									<div class="controls">
										<input type="text" id="categoriaProdutoDescricao" name="categoriaProduto.descricao" placeholder="Descrição" required>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="categoriaProdutoIsActive">Ativo</label>
									<div class="controls">
										<input type="checkbox" id="categoriaProdutoIsActive" name="categoriaProduto.isActive" checked="checked" value="${categoriaProduto.isActive }" >							
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
