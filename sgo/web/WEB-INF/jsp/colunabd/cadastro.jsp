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
	
	$('#colunaBdEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#colunaBdEmpresa').val('');
						$('#colunaBdEmpresaId').val('');
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
        	 $('#colunaBdEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {
        	 $('#colunaBdEmpresa').val(ui.item.label);
             $('#colunaBdEmpresaId').val(ui.item.value);
             return false;
         }
    });
	
	$('#colunaBdOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#colunaBdEmpresaId').val() == '' ? '0' :  $('#colunaBdEmpresaId').val(), org_nome : $('#colunaBdOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#colunaBdOrganizacao').val('');
         	           $('#colunaBdOrganizacaoId').val('');
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
          	 $('#colunaBdOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#colunaBdOrganizacao').val(ui.item.label);
             $('#colunaBdOrganizacaoId').val(ui.item.value);
             return false;
         }
    });
	
	$('#colunaBdTabelaBd').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/colunabd/busca.json' />",
	          dataType: "json",
	          data : {tabelabd_id: $('#colunaBdTabelaBdId').val() == '' ? '0' :  $('#colunaBdTabelaBdId').val(), nometabelabd : $('#colunaBdTabelaBd').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#colunaBdTabelaBd').val('');
         	           $('#colunaBdTabelaBdId').val('');
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
          	 $('#colunaBdTabelaBd').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#colunaBdTabelaBd').val(ui.item.label);
             $('#colunaBdTabelaBdId').val(ui.item.value);
             return false;
         }
    });


});


function limpaCampo(campo,campoId){
	campo.value='';
	campoId.value='';
}


</script>

<div class="span9">

	<section id="tabs">
		<div class="bs-docs-example">

			<ul id="myTab" class="nav nav-tabs">
				<li class="" id="tabelabd-li"><a href="#" data-toggle="tab" id="tabelabd-li-a">Tabela BD</a></li>
				<li class="active" id="colunabd-li"><a href="#colunabd-div" data-toggle="tab" id="colunabd-li-a">Coluna BD</a></li>	
				<li class="" id="elementobd-li"><a href="#elementobd-div" data-toggle="tab" id="elementobd-li-a">Elemento BD</a></li>
				<li class="" id="tipodadobd-li"><a href="#tipodadobd-div" data-toggle="tab" id="tipodadobd-li-a">TipoDado BD</a></li>
			</ul>

				<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade " id="tabelabd-div">

				</div>
				<div class="tab-pane fade active in" id="colunabd-div">
				
					<form id="colunaBdForm" name="colunaBdForm" action="<c:url value="/colunabd/salva"/>" method="POST">
						<div class="control-group">
							<label class="control-label" for="colunaBdEmpresa">Empresa</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="colunaBdEmpresa" name="colunaBd.empresa.nome" type="text" required onChange="limpaCampo(colunaBdEmpresa,colunaBdEmpresaId);">
	      						<input class="span2" id="colunaBdEmpresaId" name="colunaBd.empresa.empresa_id" type="hidden">
	    					</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="colunaBdOrganizacao">Organização</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="colunaBdOrganizacao" name="colunaBd.organizacao.nome" type="text" required onChange="limpaCampo(colunaBdOrganizacao,colunaBdOrganizacaoId);">
	      						<input class="span2" id="colunaBdOrganizacaoId" name="colunaBd.organizacao.organizacao_id" type="hidden">
	    					</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="colunaBdTabelaBd">Tabela BD</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="colunaBdTabelaBd" name="colunaBd.tabelabd.nometabelabd" type="text" required onChange="limpaCampo(colunaBdTabelaBd,colunaBdTabelaBdId);">
	      						<input class="span2" id="colunaBdTabelaBdId" name="colunaBd.tabelabd.tabelabd_id" type="hidden">
	    					</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="colunaBdElementoBd">Elemento BD</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="colunaBdElementoBd" name="colunaBd.elementobd.nomecolunabd" type="text" required onChange="limpaCampo(colunaBdElementoBd,colunaBdElementoBdId);">
	      						<input class="span2" id="colunaBdElementoBdId" name="colunaBd.elementobd.elementobd_id" type="hidden">
	    					</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="colunaBd_TipoDadoBd">Tipo Dado BD</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="colunaBd_TipoDadoBd" name="colunaBd.tipodadobd.nome" type="text" required onChange="limpaCampo(colunaBd_TipoDadoBd,colunaBd_TipoDadoBdId);">
	      						<input class="span2" id="colunaBd_TipoDadoBdId" name="colunaBd.tipodadobd.tipodadobd_id" type="hidden">
	    					</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="colunaBd.nomecolunadb">Nome Coluna BD</label>
							<div class="controls">
								<input type="text" id="colunaBd.nomecolunadb" name="colunaBd.nomecolunadb" placeholder="Nome da coluna BD" required>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="colunaBd.nome">Nome</label>
							<div class="controls">
								<input type="text" id="colunaBd.nome" name="colunaBd.nome" placeholder="Nome" required>
							</div>
						</div>						
						
						<div class="btn-toolbar">
							<div class="btn-group">
								<button type="submit" class="btn btn-primary">Salvar</button>
							</div>	
							<div class="btn-group">
								<button type="button" class="btn btn-primary" id="btnSair" >Novo</button>
							</div>
						</div>

					</form>

				</div>
				<div class="tab-pane fade" id="elementobd-div">

				</div>
				<div class="tab-pane fade" id="tipodadobd-div">

				</div>
			</div>
		</div>
	</section>
</div>

<%@ include file="/footer.jspf"%>
