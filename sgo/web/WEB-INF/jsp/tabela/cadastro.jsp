<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#tipotabela-li-a').click(function() {
		window.location.href = '<c:url value="/tipotabela/cadastro" />';
	});

	$('#tabela-li-a').click(function() {
		window.location.href = '<c:url value="/tabela/cadastro" />';
	});
	
	$('#tabelaEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#tabelaEmpresa').val('');
						$('#tabelaEmpresaId').val('');
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
        	 $('#tabelaEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#tabelaEmpresa').val(ui.item.label);
             $('#tabelaEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#tabelaOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#tabelaEmpresaId').val() == '' ? '0' :  $('#tabelaEmpresaId').val(), org_nome : $('#tabelaOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#tabelaOrganizacao').val('');
         	            $('#tabelaOrganizacaoId').val('');
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
          	 $('#tabelaOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#tabelaOrganizacao').val(ui.item.label);
             $('#tabelaOrganizacaoId').val(ui.item.value);
             return false;
         }
    });
	
	$('#tabelaTipoTabela').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/tipotabela/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#tabelaEmpresaId').val() == '' ? '0' :  $('#tabelaEmpresaId').val(), 
	        		  organizacao_id : $('#tabelaOrganizacaoId').val() == '' ? '0' :  $('#tabelaOrganizacaoId').val(),
	        		  nome : $('#tabelaTipoTabela').val() },
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#tabelaTipoTabela').val('');
         	            $('#tabelaTipoTabelaId').val('');
         	        }

            	  response($.map(data, function(tipoTabela) {  
            		  return {
            			  label: tipoTabela.nome,
            			  value: tipoTabela.tipoTabela_id
                      };
                  }));  
               }
	        });
         },
         focus: function( event, ui ) {
          	 $('#tabelaTipoTabela').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#tabelaTipoTabela').val(ui.item.label);
             $('#tabelaTipoTabelaId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/tabela/cadastro" />';
	});

	$('#btnNovo').click(function() {
		document.tabelaForm.reset();
	});

	$("#tabelaIsActive").change(function(e){
		$(this).val( $("#tabelaIsActive:checked").length > 0 ? "1" : "0");
	});


});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.tabelaForm.reset();
	}
}
</script>

<div class="span9">

	<section id="tabs">
		<div class="bs-docs-example">

			<ul id="myTab" class="nav nav-tabs">								
				<li class="" id="tipotabela-li"><a href="#tipotabela-div" data-toggle="tab" id="tipotabela-li-a">Tipo de Tabela</a></li>
				<li class="active" id="tabela-li"><a href="#tabela-div" data-toggle="tab" id="tabela-li-a">Tabela</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade" id="tipotabela-div"></div>

				<div class="tab-pane fade active in" id="tabela-div" >
				
						<div class="row25MarginTop">
						<form id="tabelaForm" name="tabelaForm" action="<c:url value="/tabela/salva"/>" method="POST">

							<div class="span3">
									<div class="control-group">
										<label class="control-label" for="tabelaEmpresa">Empresa</label>
										<div class="input-prepend">
											<span class="add-on"><i class="icon-plus-sign"></i></span>
				      						<input class="span2" id="tabelaEmpresa" name="tabela.empresa.nome" type="text" required onChange="limpaForm();">
				      						<input class="span2" id="tabelaEmpresaId" name="tabela.empresa.empresa_id" type="hidden">
				    					</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="tabelaOrganizacao">Organização</label>
										<div class="input-prepend">
											<span class="add-on"><i class="icon-plus-sign"></i></span>
				      						<input class="span2" id="tabelaOrganizacao" name="tabela.organizacao.nome" type="text" required onChange="limpaForm();">
				      						<input class="span2" id="tabelaOrganizacaoId" name="tabela.organizacao.organizacao_id" type="hidden">
				    					</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="tabelaNome">Nome</label>
										<div class="controls">
											<input type="text" id="tabelaNome" name="tabela.nome" placeholder="Nome" required>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="tabelaDescricao">Descrição</label>
										<div class="controls">
											<input type="text" id="tabelaDescricao" name="tabela.descricao" placeholder="Descrição" required>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="tabelaIsActive">Ativo</label>
										<div class="controls">
											<input type="checkbox" id="tabelaIsActive" name="tabela.isActive" checked="checked" value="1" >							
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
	
							<div class="span3">

								<div class="control-group">
									<label class="control-label" for="tabelaTipoTabela">Tipo Tabela</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span2" id="tabelaTipoTabela" name="tabela.tipoTabela.nome" type="text" required onChange="limpaForm();">
			      						<input class="span2" id="tabelaTipoTabelaId" name="tabela.tipoTabela.tipoTabela_id" type="hidden">
			    					</div>
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
