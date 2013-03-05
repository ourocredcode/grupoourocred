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
	
	$('#elementoBdEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#elementoBdEmpresa').val('');
						$('#elementoBdEmpresaId').val('');
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
        	 $('#elementoBdEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {
        	 $('#elementoBdEmpresa').val(ui.item.label);
             $('#elementoBdEmpresaId').val(ui.item.value);
             return false;
         }
    });
	
	$('#elementoBdOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#elementoBdEmpresaId').val() == '' ? '0' :  $('#elementoBdEmpresaId').val(), org_nome : $('#elementoBdOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#elementoBdOrganizacao').val('');
         	           $('#elementoBdOrganizacaoId').val('');
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
          	 $('#elementoBdOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#elementoBdOrganizacao').val(ui.item.label);
             $('#elementoBdOrganizacaoId').val(ui.item.value);
             return false;
         }
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
				<li class="active" id="elementobd-li"><a href="#elementobd-div" data-toggle="tab" id="elementobd-li-a">Elemento BD</a></li>
				<li class="" id="tipodadobd-li"><a href="#tipodadobd-div" data-toggle="tab" id="tipodadobd-li-a">TipoDado BD</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade " id="tabelabd-div">

				</div>
				<div class="tab-pane fade " id="colunabd-div">

				</div>
				<div class="tab-pane fade active in" id="elementobd-div">

					<form id="elementoBdForm" name="elementoBdForm" action="<c:url value="/elementobd/salva"/>" method="POST">
						<div class="control-group">
							<label class="control-label" for="elementoBdEmpresa">Empresa</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="elementoBdEmpresa" name="elementoBd.empresa.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="elementoBdEmpresaId" name="elementoBd.empresa.empresa_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="elementoBdOrganizacao">Organização</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="elementoBdOrganizacao" name="elementoBd.organizacao.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="elementoBdOrganizacaoId" name="elementoBd.organizacao.organizacao_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="elementoBdNomeColunaBd">Nome Coluna BD</label>
							<div class="controls">
								<input type="text" id="elementoBdNomeColunaBd" name="elementoBd.nomeColunaBd" placeholder="Nome da coluna BD" required>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="elementoBd.nome">Nome</label>
							<div class="controls">
								<input type="text" id="elementoBd.nome" name="elementoBd.nome" placeholder="Nome" required>
							</div>
						</div>
						
						 <div class="btn-toolbar">
								<div class="btn-group">
									<button type="submit" class="btn btn-primary">Salvar</button>
								</div>	
							</div>
					</form>

				</div>
				<div class="tab-pane fade" id="tipodadobd-div">

				</div>
			</div>
		</div>
	</section>
</div>

<%@ include file="/footer.jspf"%>
