<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#agencia-li-a').click(function() {
		window.location.href = '<c:url value="/agencia/cadastro" />';
	});

	$('#agenciaEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#agenciaEmpresa').val('');
						$('#agenciaEmpresaId').val('');
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
        	 $('#agenciaEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#agenciaEmpresa').val(ui.item.label);
             $('#agenciaEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#agenciaOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#agenciaEmpresaId').val() == '' ? '0' :  $('#agenciaEmpresaId').val(), organizacao_id : $('#agenciaOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#agenciaOrganizacao').val('');
         	            $('#agenciaOrganizacaoId').val('');
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
          	 $('#agenciaOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#agenciaOrganizacao').val(ui.item.label);
             $('#agenciaOrganizacaoId').val(ui.item.value);
             return false;
         }
    });
	
	$('#agenciaBanco').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/banco/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#agenciaEmpresaId').val() == '' ? '0' :  $('#agenciaEmpresaId').val(), 
	        		  organizacao_id: $('#agenciaOrganizacaoId').val() == '' ? '0' :  $('#agenciaOrganizacaoId').val(),
	        		  nome : $('#agenciaBanco').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#agenciaBanco').val('');
         	           $('#agenciaBancoId').val('');
         	        }

            	  response($.map(data, function(banco) {  
            		  return {
            			  label: banco.nome,
            			  value: banco.banco_id
                      };
                  }));  
               }
	        });
         },
         focus: function( event, ui ) {
          	 $('#agenciaBanco').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#agenciaBanco').val(ui.item.label);
             $('#agenciaBancoId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/agencia/cadastro" />';
	});

	$('#btnNovo').click(function() {
		document.perfilForm.reset();
	});

	$("#agenciaIsActive").change(function(e){
		$(this).val( $("#agenciaIsActive:checked").length > 0 ? "1" : "0");
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.agenciaForm.reset();
	}
}
</script>

<div class="span9">

	<section id="tabs">
		<div class="bs-docs-example">

			<ul id="myTab" class="nav nav-tabs">
				<li class="" id="agencia-li"><a href="#agencia-div" data-toggle="tab" id="agencia-li-a">Cadastro de Banco</a></li>				
			</ul>
			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="agencia-div">
				
					<div class="row25MarginTop">
						<div class="span3">
							<form id="agenciaForm" name="agenciaForm" action="<c:url value="/agencia/salva"/>" method="POST">
								<div class="control-group">
									<label class="control-label" for="agenciaEmpresa">Empresa</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span2" id="agenciaEmpresa" name="agencia.empresa.nome" type="text" required onChange="limpaForm();">
			      						<input class="span2" id="agenciaEmpresaId" name="agencia.empresa.empresa_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="agenciaOrganizacao">Organização</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span2" id="agenciaOrganizacao" name="agencia.organizacao.nome" type="text" required onChange="limpaForm();">
			      						<input class="span2" id="agenciaOrganizacaoId" name="agencia.organizacao.organizacao_id" type="text">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="agenciaBanco">Banco</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span2" id="agenciaBanco" name="agencia.banco.nome" type="text" required onChange="limpaForm();">
			      						<input class="span2" id="agenciaBancoId" name="agencia.banco.banco_id" type="text">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="agenciaNome">Nome</label>
									<div class="controls">
										<input type="text" id="agenciaNome" name="agencia.nome" placeholder="Nome" required>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="agenciaDescricao">Descrição</label>
									<div class="controls">
										<input type="text" id="agenciaDescricao" name="agencia.descricao" placeholder="Descrição" required>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="agenciaIsActive">Ativo</label>
									<div class="controls">
										<input type="checkbox" id="agenciaIsActive" name="agencia.isActive" checked="checked" value="1" >							
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
