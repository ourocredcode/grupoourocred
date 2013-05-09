<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){
	
	$('#perfil-li-a').click(function() {
		window.location.href = '<c:url value="/perfil/cadastro" />';
	});

	$('#perfilorganizacao-li-a').click(function() {
		window.location.href = '<c:url value="/perfil/cadastro" />';
	});
	
	$('#janela-li-a').click(function() {
		window.location.href = '<c:url value="/janela/cadastro" />';
	});
	
	$('#formulariosjanela-li-a').click(function() {
		window.location.href = '<c:url value="/formulariosjanela/cadastro" />';
	});
	
	$('#perfiljanelaacesso-li-a').click(function() {
		window.location.href = '<c:url value="/perfiljanelaacesso/cadastro" />';
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
	
	$('#formulariosJanelaEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#formulariosJanelaEmpresa').val('');
						$('#formulariosJanelaEmpresaId').val('');
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
        	 $('#formulariosJanelaEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {
        	 $('#formulariosJanelaEmpresa').val(ui.item.label);
             $('#formulariosJanelaEmpresaId').val(ui.item.value);
             return false;
         }
    });
	
	$('#formulariosJanelaOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#formulariosJanelaEmpresaId').val() == '' ? '0' :  $('#formulariosJanelaEmpresaId').val(), org_nome : $('#formulariosJanelaOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#formulariosJanelaOrganizacao').val('');
         	           $('#formulariosJanelaOrganizacaoId').val('');
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
          	 $('#formulariosJanelaOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#formulariosJanelaOrganizacao').val(ui.item.label);
             $('#formulariosJanelaOrganizacaoId').val(ui.item.value);
             return false;
         }
    });
	
	$('#formulariosJanelaJanela').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/janela/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#formulariosJanelaEmpresaId').val() == '' ? '0' :  $('#formulariosJanelaEmpresaId').val(), 
	        		  organizacao_id: $('#formulariosJanelaOrganizacaoId').val() == '' ? '0' :  $('#formulariosJanelaOrganizacaoId').val(),
	        		  nome : $('#formulariosJanelaJanela').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#formulariosJanelaJanela').val('');
         	           $('#formulariosJanelaJanelaId').val('');
         	        }

            	  response($.map(data, function(janela) {  
            		  return {
            			  label: janela.nome,
            			  value: janela.janela_id
                      };
                  }));  
               }
	        });
         },
         focus: function( event, ui ) {
          	 $('#formulariosJanelaJanela').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#formulariosJanelaJanela').val(ui.item.label);
             $('#formulariosJanelaJanelaId').val(ui.item.value);
             return false;
         }
    });
	
	$('#formulariosJanelaTabelaBd').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/tabelabd/busca.json' />",
	          dataType: "json",
	          data : {empresa_id : $('#formulariosJanelaEmpresaId').val() == '' ? '0' :  $('#formulariosJanelaEmpresaId').val(),
	        		  organizacao_id: $('#formulariosJanelaOrganizacaoId').val() == '' ? '0' :  $('#formulariosJanelaOrganizacaoId').val(),
	        				  nometabelabd : $('#formulariosJanelaTabelaBd').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#formulariosJanelaTabelaBd').val('');
         	           $('#formulariosJanelaTabelaBdId').val('');
         	        }

            	  response($.map(data, function(tabelabd) {  
            		  return {
            			  label: tabelabd.nomeTabelaBd,
            			  value: tabelabd.tabelaBd_id
                      };
                  }));  
               }
	        });
         },
         focus: function( event, ui ) {
          	
        	 $('#formulariosJanelaTabelaBd').val(ui.item.label);
          	$('#formulariosJanelaNome').val(ui.item.label);
          	
               return false;
           } ,
         select: function( event, ui ) {
             $('#formulariosJanelaTabelaBd').val(ui.item.label);
             $('#formulariosJanelaNome').val(ui.item.label);
             $('#formulariosJanelaTabelaBdId').val(ui.item.value);
             return false;
         }
    });
	
	$("#formulariosJanelaIsMostrado").change(function(e){
		$(this).val( $("#formulariosJanelaIsMostrado:checked").length > 0 ? true : false);
	});
	
	$("#formulariosJanelaIsSomenteLeitura").change(function(e){
		$(this).val( $("#formulariosJanelaIsSomenteLeitura:checked").length > 0 ? true : false);
	});
	
	$("#formulariosJanelaIsActive").change(function(e){
		$(this).val( $("#formulariosJanelaIsActive:checked").length > 0 ? true : false);
	});

});

function limpaForm(){

	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.colunaBdForm.reset();
	}

}


</script>

<div id="content-header">
		<h1>Cadastro Perfil</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
		<a href="#" class="current">Perfil</a>
	</div>

<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">

			<ul id="myTab" class="nav nav-tabs">
				<li class="" id="perfil-li"><a href="#perfil-div" data-toggle="tab" id="perfil-li-a">Perfil</a></li>
				<li class="" id="perfilorganizacao-li"><a href="#perfilperfilorganizacao-div" data-toggle="tab" id="perfilorganizacao-li-a">Perfil Organização</a></li>
				<li class="" id="janela-li"><a href="#janela-div" data-toggle="tab" id="janela-li-a">Janela</a></li>
				<li class="" id="perfiljanelaacesso-li"><a href="#perfiljanelaacesso-div" data-toggle="tab" id="perfiljanelaacesso-li-a">Janela Perfil</a></li>
				<li class="active" id="formulariosjanela-li"><a href="#formulariosjanela-div" data-toggle="tab" id="formulariosjanela-li-a">Formulários Janela</a></li>
				<li class="" id="campoformulario-li"><a href="#campoformulario-div" data-toggle="tab" id="campoformulario-li-a">Campo Formulário</a></li>
				<li class="" id="tabelabd-li"><a href="#tabelabd-div" data-toggle="tab" id="tabelabd-li-a">Tabela BD</a></li>
				<li class="" id="colunabd-li"><a href="#colunabd-div" data-toggle="tab" id="colunabd-li-a">Coluna BD</a></li>	
				<li class="" id="elementobd-li"><a href="#elementobd-div" data-toggle="tab" id="elementobd-li-a">Elemento BD</a></li>
				<li class="" id="tipodadobd-li"><a href="#tipodadobd-div" data-toggle="tab" id="tipodadobd-li-a">TipoDado BD</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">
				
				<div class="tab-pane fade" id="perfil-div">					

				</div>
				
				<div class="tab-pane fade" id="perfilorganizacao-div">					

				</div>
			
				<div class="tab-pane fade" id="janela-div">
					
				</div>
				
				<div class="tab-pane fade" id="perfiljanelaacesso-div">
					
				</div>
				
				<div class="tab-pane fade active in" id="formulariosjanela-div">
					
					<form id="formulariosJanelaForm" name="formulariosJanelaForm" action="<c:url value="/formulariosjanela/salva"/>" method="POST">

						<div class="control-group">
							<label class="control-label" for="formulariosJanelaEmpresa">Empresa</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="formulariosJanelaEmpresa" name="formulariosJanela.empresa.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="formulariosJanelaEmpresaId" name="formulariosJanela.empresa.empresa_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="formulariosJanelaOrganizacao">Organização</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="formulariosJanelaOrganizacao" name="formulariosJanela.organizacao.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="formulariosJanelaOrganizacaoId" name="formulariosJanela.organizacao.organizacao_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="formulariosJanelaJanela">Janela</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="formulariosJanelaJanela" name="formulariosJanela.janela.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="formulariosJanelaJanelaId" name="formulariosJanela.janela.janela_id" type="hidden">
	    					</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="formulariosJanelaTabelaBd">Tabela Bd</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="formulariosJanelaTabelaBd" name="formulariosJanela.tabelaBd.nomeTabelaBd" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="formulariosJanelaTabelaBdId" name="formulariosJanela.tabelaBd.tabelaBd_id" type="hidden">
	    					</div>
						</div>						
						
						<div class="control-group">
							<label class="control-label" for="formularioJanelaChave">Chave</label>
							<div class="controls">
								<input type="text" id="formulariosJanelaChave" name="formulariosJanela.chave" placeholder="Chave" required>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="formulariosJanelaNome">Nome</label>
							<div class="controls">
								<input type="text" id="formulariosJanelaNome" name="formulariosJanela.nome" placeholder="Nome" required readonly="readonly">
							</div>
						</div>						
						<div class="control-group">
							<label class="control-label" for="formulariosJanelaIsMostrado">Mostrado</label>
							<div class="controls">
								<input type="checkbox" id="formulariosJanelaIsMostrado" name="formulariosJanela.isMostrado" checked="checked" value="${formulariosJanela.isMostrado }">							
							</div>							
						</div>
						<div class="control-group">
							<label class="control-label" for="formulariosJanelaIsSomenteLeitura">Leitura</label>
							<div class="controls">
								<input type="checkbox" id="formulariosJanelaIsSomenteLeitura" name="formulariosJanela.isSomenteLeitura" checked="checked" value="${formulariosJanela.isSomenteLeitura }">							
							</div>							
						</div>
						<div class="control-group">
							<label class="control-label" for="formulariosJanelaIsActive">Ativo</label>
							<div class="controls">
								<input type="checkbox" id="formulariosJanelaIsActive" name="formulariosJanela.isActive" checked="checked" value="${formulariosJanela.isActive }">							
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

				<div class="tab-pane fade" id="campoformulario-div">

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
	</div>
</div>

<%@ include file="/footer.jspf"%>
