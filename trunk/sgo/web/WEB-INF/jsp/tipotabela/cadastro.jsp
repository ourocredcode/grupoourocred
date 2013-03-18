<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#tipotabela-li-a').click(function() {
		window.location.href = '<c:url value="/tipotabela/cadastro" />';
	});

	$('#tabela-li-a').click(function() {
		window.location.href = '<c:url value="/tabela/cadastro" />';
	});
	
	$('#tipoTabelaEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#tipoTabelaEmpresa').val('');
						$('#tipoTabelaEmpresaId').val('');
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
        	 $('#tipoTabelaEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#tipoTabelaEmpresa').val(ui.item.label);
             $('#tipoTabelaEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#tipoTabelaOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#tipoTabelaEmpresaId').val() == '' ? '0' :  $('#tipoTabelaEmpresaId').val(), org_nome : $('#tipoTabelaOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#tipoTabelaOrganizacao').val('');
         	            $('#tipoTabelaOrganizacaoId').val('');
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
          	 $('#tipoTabelaOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#tipoTabelaOrganizacao').val(ui.item.label);
             $('#tipoTabelaOrganizacaoId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/tipotabela/cadastro" />';
	});

	$('#btnNovo').click(function() {
		document.perfilForm.reset();
	});

	$("#tipoTabelaIsActive").change(function(e){
		$(this).val( $("#tipoTabelaIsActive:checked").length > 0 ? "1" : "0");
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
				<li class="active" id="tipotabela-li"><a href="#tipotabela-div" data-toggle="tab" id="tipotabela-li-a">Tipo de Tabela</a></li>
				<li class="" id="tabela-li"><a href="#tabela-div" data-toggle="tab" id="tabela-li-a">Tabela</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="tipotabela-div">
				
						<div class="row25MarginTop">
						
							<div class="span3">
								<form id="tipoTabelaForm" name="tipoTabelaForm" action="<c:url value="/tipotabela/salva"/>" method="POST">
									<div class="control-group">
										<label class="control-label" for="tipoTabelaEmpresa">Empresa</label>
										<div class="input-prepend">
											<span class="add-on"><i class="icon-plus-sign"></i></span>
				      						<input class="span2" id="tipoTabelaEmpresa" name="tipoTabela.empresa.nome" type="text" required onChange="limpaForm();">
				      						<input class="span2" id="tipoTabelaEmpresaId" name="tipoTabela.empresa.empresa_id" type="hidden">
				    					</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="tipoTabelaOrganizacao">Organização</label>
										<div class="input-prepend">
											<span class="add-on"><i class="icon-plus-sign"></i></span>
				      						<input class="span2" id="tipoTabelaOrganizacao" name="tipoTabela.organizacao.nome" type="text" required onChange="limpaForm();">
				      						<input class="span2" id="tipoTabelaOrganizacaoId" name="tipoTabela.organizacao.organizacao_id" type="hidden">
				    					</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="tipoTabelaNome">Nome</label>
										<div class="controls">
											<input type="text" id="tipoTabelaNome" name="tipoTabela.nome" placeholder="Nome" required>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="tipoTabelaDescricao">Descrição</label>
										<div class="controls">
											<input type="text" id="tipoTabelaDescricao" name="tipoTabela.descricao" placeholder="Descrição" required>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="tipoTabelaIsActive">Ativo</label>
										<div class="controls">
											<input type="checkbox" id="tipoTabelaIsActive" name="tipoTabela.isActive" checked="checked" value="1" >							
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

				<div class="tab-pane fade" id="tabela-div" ></div>

			</div>
		</div>
	</section>
</div>

<%@ include file="/footer.jspf"%>
