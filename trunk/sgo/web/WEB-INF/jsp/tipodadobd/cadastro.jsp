<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#tabelabd-li-a').click(function() {
		window.location.href = '<c:url value="/tabelabd/cadastro" />';
	});
	$('#colunabd-li-a').click(function() {
		window.location.href = '<c:url value="/colunabd/cadastro" />';
	});
	$('#elementobd-li-a').click(function() {
		window.location.href = '<c:url value="/elementobd/cadastro" />';
	});
	$('#tipodadobd-li-a').click(function() {
		window.location.href = '<c:url value="/tipodadobd/cadastro" />';
	});
	
	$('#tipoDadoBdEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#tipoDadoBdEmpresa').val('');
						$('#tipoDadoBdEmpresaId').val('');
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
        	 $('#tipoDadoBdEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {
        	 $('#tipoDadoBdEmpresa').val(ui.item.label);
             $('#tipoDadoBdEmpresaId').val(ui.item.value);
             return false;
         }
    });
	
	$('#tipoDadoBdOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#tipoDadoBdEmpresaId').val() == '' ? '0' :  $('#tipoDadoBdEmpresaId').val(), org_nome : $('#tipoDadoBdOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#tipoDadoBdOrganizacao').val('');
         	            $('#tipoDadoBdOrganizacaoId').val('');
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
          	 $('#tipoDadoBdOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#tipoDadoBdOrganizacao').val(ui.item.label);
             $('#tipoDadoBdOrganizacaoId').val(ui.item.value);
             return false;
         }
    });
	

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/colunabd/cadastro" />';
	});

});

function limpaForm(){

	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.colunaBdForm.reset();
	}

}


</script>

<div class="span9">

	<section id="tabs">
		<div class="bs-docs-example">

			<ul id="myTab" class="nav nav-tabs">
				<li class="" id="tabelabd-li"><a href="#" data-toggle="tab" id="tabelabd-li-a">Tabela BD</a></li>
				<li class="" id="colunabd-li"><a href="#colunabd-div" data-toggle="tab" id="colunabd-li-a">Coluna BD</a></li>	
				<li class="" id="elementobd-li"><a href="#elementobd-div" data-toggle="tab" id="elementobd-li-a">Elemento BD</a></li>
				<li class="active" id="tipodadobd-li"><a href="#tipodadobd-div" data-toggle="tab" id="tipodadobd-li-a">TipoDado BD</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade " id="tabelabd-div">

				</div>
				<div class="tab-pane fade " id="colunabd-div">

				</div>
				<div class="tab-pane fade" id="elementobd-div">

				</div>
				<div class="tab-pane fade active in" id="tipodadobd-div">
				
					<form id="tipoDadoBdForm" name="tipoDadoBdForm" action="<c:url value="/tipodadobd/salva"/>" method="POST">
						<div class="control-group">
							<label class="control-label" for="tipoDadoBdEmpresa">Empresa</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="tipoDadoBdEmpresa" name="tipoDadoBd.empresa.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="tipoDadoBdEmpresaId" name="tipoDadoBd.empresa.empresa_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="tipoDadoBdOrganizacao">Organização</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="tipoDadoBdOrganizacao" name="tipoDadoBd.organizacao.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="tipoDadoBdOrganizacaoId" name="tipoDadoBd.organizacao.organizacao_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="tipoDadoBdNome">Nome Tipo Dado BD</label>
							<div class="controls">
								<input type="text" id="tipoDadoBdNome" name="tipoDadoBd.nome" placeholder="Nome do tipo de dado BD" required>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="tipoDadoBdChave">Chave Tipo Dado BD</label>
							<div class="controls">
								<input type="text" id="tipoDadoBdChave" name="tipoDadoBd.chave" placeholder="Chave do tipo de dado BD" required>
							</div>
						</div>
						
						 <div class="btn-toolbar">
								<div class="btn-group">
									<button type="submit" class="btn btn-primary">Salvar</button>
								</div>	
							</div>
					</form>

				</div>
			</div>
		</div>
	</section>
</div>

<%@ include file="/footer.jspf"%>
