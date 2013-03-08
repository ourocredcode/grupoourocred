<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){
	
	$('#perfil-li-a').click(function() {
		window.location.href = '<c:url value="/perfil/cadastro" />';
	});

	$('#perfilorgacesso-li-a').click(function() {
		window.location.href = '<c:url value="/perfilorgacesso/cadastro" />';
	});
	
	$('#janela-li-a').click(function() {
		window.location.href = '<c:url value="/janela/cadastro" />';
	});
	
	$('#perfiljanelaacesso-li-a').click(function() {
		window.location.href = '<c:url value="/perfiljanelaacesso/cadastro" />';
	});
	
	$('#formulariosjanela-li-a').click(function() {
		window.location.href = '<c:url value="/formulariosjanela/cadastro" />';
	});
	
	$('#campoformulario-li-a').click(function() {
		window.location.href = '<c:url value="/campoformulario/cadastro" />';
	});

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

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/colunabd/cadastro" />';
	});
	
	$('#campoFormularioEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#campoFormularioEmpresa').val('');
						$('#campoFormularioEmpresaId').val('');
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
        	 $('#campoFormularioEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {
        	 $('#campoFormularioEmpresa').val(ui.item.label);
             $('#campoFormularioEmpresaId').val(ui.item.value);
             return false;
         }
    });
	
	$('#campoFormularioOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#campoFormularioEmpresaId').val() == '' ? '0' :  $('#campoFormularioEmpresaId').val(), org_nome : $('#campoFormularioOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#campoFormularioOrganizacao').val('');
         	           $('#campoFormularioOrganizacaoId').val('');
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
          	 $('#campoFormularioOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#campoFormularioOrganizacao').val(ui.item.label);
             $('#campoFormularioOrganizacaoId').val(ui.item.value);
             return false;
         }
    });
	
	$('#campoFormularioFormulariosJanela').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/formulariosjanela/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#campoFormularioEmpresaId').val() == '' ? '0' :  $('#campoFormularioEmpresaId').val(), 
	        		  organizacao_id: $('#campoFormularioOrganizacaoId').val() == '' ? '0' :  $('#campoFormularioOrganizacaoId').val(),
	        		  nome : $('#campoFormularioFormulariosJanela').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#campoFormularioFormulariosJanela').val('');
         	           $('#campoFormularioFormulariosJanelaId').val('');
         	        }

            	  response($.map(data, function(formulariosjanela) {  
            		  return {
            			  label: formulariosjanela.nome,
            			  value: formulariosjanela.formulariosjanela_id 
                      };
                  }));  
               }
	        });
         },
         focus: function( event, ui ) {
          	 $('#campoFormularioFormulariosJanela').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#campoFormularioFormulariosJanela').val(ui.item.label);
             $('#campoFormularioFormulariosJanelaId').val(ui.item.value);
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
				<li class="" id="perfil-li"><a href="#perfil-div" data-toggle="tab" id="perfil-li-a">Perfil</a></li>
				<li class="" id="perfilorgacesso-li"><a href="#perfilorgacesso-div" data-toggle="tab" id="perfilorgacesso-li-a">Perfil Organização</a></li>
				<li class="" id="janela-li"><a href="#janela-div" data-toggle="tab" id="janela-li-a">Janela</a></li>
				<li class="" id="perfiljanelaacesso-li"><a href="#perfiljanelaacesso-div" data-toggle="tab" id="perfiljanelaacesso-li-a">Janela Perfil</a></li>
				<li class="" id="formulariosjanela-li"><a href="#formulariosjanela-div" data-toggle="tab" id="formulariosjanela-li-a">Formulários Janela</a></li>
				<li class="active" id="campoformulario-li"><a href="#campoformulario-div" data-toggle="tab" id="campoformulario-li-a">Campo Formulário</a></li>
				<li class="" id="tabelabd-li"><a href="#tabelabd-div" data-toggle="tab" id="tabelabd-li-a">Tabela BD</a></li>
				<li class="" id="colunabd-li"><a href="#colunabd-div" data-toggle="tab" id="colunabd-li-a">Coluna BD</a></li>	
				<li class="" id="elementobd-li"><a href="#elementobd-div" data-toggle="tab" id="elementobd-li-a">Elemento BD</a></li>
				<li class="" id="tipodadobd-li"><a href="#tipodadobd-div" data-toggle="tab" id="tipodadobd-li-a">TipoDado BD</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">
			
				<div class="tab-pane fade" id="perfil-div">					

				</div>
				
				<div class="tab-pane fade" id="perfilorgacesso-div">					

				</div>
			
				<div class="tab-pane fade" id="janela-div">
					
				</div>
				
				<div class="tab-pane fade" id="perfiljanelaacesso-div">
					
				</div>
				
				<div class="tab-pane fade" id="formulariosjanela-div">
					
				</div>

				<div class="tab-pane fade active in" id="campoformulario-div">

					<form id="campoFormularioForm" name="campoFormularioForm" action="<c:url value="/campoformulario/salva"/>" method="POST">

						<div class="control-group">
							<label class="control-label" for="campoFormularioEmpresa">Empresa</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="campoFormularioEmpresa" name="campoFormulario.empresa.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="campoFormularioEmpresaId" name="campoFormulario.empresa.empresa_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="campoFormularioOrganizacao">Organização</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="campoFormularioOrganizacao" name="campoFormulario.organizacao.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="campoFormularioOrganizacaoId" name="campoFormulario.organizacao.organizacao_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="campoFormularioFormulariosJanela">Fomulário</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="campoFormularioFormulariosJanela" name="campoFormulario.formulariosJanela.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="campoFormularioFormulariosJanelaId" name="campoFormulario.formulariosJanela.formulariosjanela_id" type="hidden">
	    					</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="campoFormularioColunaBd">Coluna Bd</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="campoFormularioColunaBd" name="campoFormulario.colunaBd.nomeColunaBb" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="campoFormularioColunaBdId" name="campoFormulario.colunaBd.colunabd_id" type="hidden">
	    					</div>
						</div>
						 
						 <div class="control-group">
							<label class="control-label" for="campoFormularioChave">Chave</label>
							<div class="controls">
								<input type="text" id="campoFormularioChave" name="campoFormulario.chave" placeholder="Chave" required>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="campoFormularioNome">Nome</label>
							<div class="controls">
								<input type="text" id="campoFormularioNome" name="campoFormulario.nome" placeholder="Nome" required>
							</div>
						</div>						
						<div class="control-group">
							<label class="control-label" for="campoFormularioIsMostrado">Mostrado</label>
							<div class="controls">
								<input type="checkbox" id="campoFormularioIsMostrado" name="campoFormulario.isMostrado">							
							</div>							
						</div>
						<div class="control-group">
							<label class="control-label" for="campoFormularioIsSomenteLeitura">Somente Leitura</label>
							<div class="controls">
								<input type="checkbox" id="campoFormularioIsSomenteLeitura" name="campoFormulario.issomenteleitura">							
							</div>							
						</div>
						<div class="control-group">
							<label class="control-label" for="campoFormularioIsActive">Ativo</label>
							<div class="controls">
								<input type="checkbox" id="campoFormularioIsActive" name="campoFormulario.isactive">							
							</div>							
						</div>
						<div class="btn-toolbar">
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

				<div class="tab-pane fade" id="tabelabd-div">

				</div>
				<div class="tab-pane fade" id="colunabd-div">

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
