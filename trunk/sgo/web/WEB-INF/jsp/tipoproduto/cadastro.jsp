<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#tipoproduto-li-a').click(function() {
		window.location.href = '<c:url value="/tipoproduto/cadastro" />';
	});

	$('#tipoProdutoEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#tipoProdutoEmpresa').val('');
						$('#tipoProdutoEmpresaId').val('');
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
        	 $('#tipoProdutoEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#tipoProdutoEmpresa').val(ui.item.label);
             $('#tipoProdutoEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#tipoProdutoOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#tipoProdutoEmpresaId').val() == '' ? '0' :  $('#tipoProdutoEmpresaId').val(), org_nome : $('#tipoProdutoOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#tipoProdutoOrganizacao').val('');
         	            $('#tipoProdutoOrganizacaoId').val('');
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
          	 $('#tipoProdutoOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#tipoProdutoOrganizacao').val(ui.item.label);
             $('#tipoProdutoOrganizacaoId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/tipoproduto/cadastro" />';
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});

	$("#tipoProdutoIsActive").change(function(e){
		if(document.tipoProdutoForm.tipoProdutoIsActive.checked==true){
			document.tipoProdutoForm.tipoProdutoIsActive.value=true;
		}else{
			document.tipoProdutoForm.tipoProdutoIsActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.tipoProdutoForm.reset();
	}
}
</script>

<div class="span9">

	<section id="tabs">
		<div class="bs-docs-example">

			<ul id="myTab" class="nav nav-tabs">
				<li class="" id="tipoproduto-li"><a href="#tipoproduto-div" data-toggle="tab" id="tipoproduto-li-a">Tipo de Produto</a></li>				
			</ul>
			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="tipoproduto-div">
				
					<div class="row25MarginTop">
						<div class="span3">
							<form id="tipoProdutoForm" name="tipoProdutoForm" action="<c:url value="/tipoproduto/salva"/>" method="POST">
								<div class="control-group">
									<label class="control-label" for="tipoProdutoEmpresa">Empresa</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span2" id="tipoProdutoEmpresa" name="tipoProduto.empresa.nome" type="text" required onChange="limpaForm();">
			      						<input class="span2" id="tipoProdutoEmpresaId" name="tipoProduto.empresa.empresa_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="tipoProdutoOrganizacao">Organização</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span2" id="tipoProdutoOrganizacao" name="tipoProduto.organizacao.nome" type="text" required onChange="limpaForm();">
			      						<input class="span2" id="tipoProdutoOrganizacaoId" name="tipoProduto.organizacao.organizacao_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="tipoProdutoNome">Nome</label>
									<div class="controls">
										<input type="text" id="tipoProdutoNome" name="tipoProduto.nome" placeholder="Nome" required>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="tipoProdutoDescricao">Descrição</label>
									<div class="controls">
										<input type="text" id="tipoProdutoDescricao" name="tipoProduto.descricao" placeholder="Descrição" required>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="tipoProdutoIsActive">Ativo</label>
									<div class="controls">
										<input type="checkbox" id="tipoProdutoIsActive" name="tipoProduto.isActive" checked="checked" value="1" >							
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
