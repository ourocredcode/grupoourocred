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
	
	$('#perfilJanelaAcessoEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#perfilJanelaAcessoEmpresa').val('');
						$('#perfilJanelaAcessoEmpresaId').val('');
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
        	 $('#perfilJanelaAcessoEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {
        	 $('#perfilJanelaAcessoEmpresa').val(ui.item.label);
             $('#perfilJanelaAcessoEmpresaId').val(ui.item.value);
             return false;
         }
    });
	
	$('#perfilJanelaAcessoOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#perfilJanelaAcessoEmpresaId').val() == '' ? '0' :  $('#perfilJanelaAcessoEmpresaId').val(), org_nome : $('#perfilJanelaAcessoOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#perfilJanelaAcessoOrganizacao').val('');
         	           $('#perfilJanelaAcessoOrganizacaoId').val('');
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
          	 $('#perfilJanelaAcessoOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#perfilJanelaAcessoOrganizacao').val(ui.item.label);
             $('#perfilJanelaAcessoOrganizacaoId').val(ui.item.value);
             return false;
         }
    });
	
	$('#perfilJanelaAcessoJanela').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/janela/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#perfilJanelaAcessoEmpresaId').val() == '' ? '0' :  $('#perfilJanelaAcessoEmpresaId').val(), 
	        		  organizacao_id: $('#perfilJanelaAcessoOrganizacaoId').val() == '' ? '0' :  $('#perfilJanelaAcessoOrganizacaoId').val(),
	        		  nome : $('#perfilJanelaAcessoJanela').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#perfilJanelaAcessoJanela').val('');
         	           $('#perfilJanelaAcessoJanelaId').val('');
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
          	 $('#perfilJanelaAcessoJanela').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#perfilJanelaAcessoJanela').val(ui.item.label);
             $('#perfilJanelaAcessoJanelaId').val(ui.item.value);
             return false;
         }
    });
	
	$('#perfilJanelaAcessoPerfil').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/perfil/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#perfilJanelaAcessoEmpresaId').val() == '' ? '0' :  $('#perfilJanelaAcessoEmpresaId').val(), 
	        		  organizacao_id: $('#perfilJanelaAcessoOrganizacaoId').val() == '' ? '0' :  $('#perfilJanelaAcessoOrganizacaoId').val(),
	        		  nome : $('#perfilJanelaAcessoPerfil').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#perfilJanelaAcessoPerfil').val('');
         	           $('#perfilJanelaAcessoPerfilId').val('');
         	        }

            	  response($.map(data, function(perfil) {  
            		  return {
            			  label: perfil.nome,
            			  value: perfil.perfil_id
                      };
                  }));  
               }
	        });
         },
         focus: function( event, ui ) {
          	 $('#perfilJanelaAcessoPerfil').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#perfilJanelaAcessoPerfil').val(ui.item.label);
             $('#perfilJanelaAcessoPerfilId').val(ui.item.value);
             return false;
         }
    });
	
	$("#perfilJanelaAcessoIsActive").change(function(e){
		$(this).val( $("#perfilJanelaAcessoIsActive:checked").length > 0 ? true : false);
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
				<li class="" id="perfilorgacesso-li"><a href="#perfilorgacesso-div" data-toggle="tab" id="perfilorgacesso-li-a">Perfil Organização</a></li>
				<li class="" id="janela-li"><a href="#janela-div" data-toggle="tab" id="janela-li-a">Janela</a></li>
				<li class="active" id="perfiljanelaacesso-li"><a href="#perfiljanelaacesso-div" data-toggle="tab" id="perfiljanelaacesso-li-a">Janela Perfil</a></li>
				<li class="" id="formulariosjanela-li"><a href="#formulariosjanela-div" data-toggle="tab" id="formulariosjanela-li-a">Formulários Janela</a></li>
				<li class="" id="campoformulario-li"><a href="#campoformulario-div" data-toggle="tab" id="campoformulario-li-a">Campo Formulário</a></li>
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
				
				<div class="tab-pane fade active in" id="perfiljanelaacesso-div">
					<form id="perfilJanelaAcessoForm" name="perfilJanelaAcessoForm" action="<c:url value="/perfiljanelaacesso/salva"/>" method="POST">

						<div class="control-group">
							<label class="control-label" for="perfilJanelaAcessoEmpresa">Empresa</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="perfilJanelaAcessoEmpresa" name="perfilJanelaAcesso.empresa.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="perfilJanelaAcessoEmpresaId" name="perfilJanelaAcesso.empresa.empresa_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="perfilJanelaAcessoOrganizacao">Organização</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="perfilJanelaAcessoOrganizacao" name="perfilJanelaAcesso.organizacao.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="perfilJanelaAcessoOrganizacaoId" name="perfilJanelaAcesso.organizacao.organizacao_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="perfilJanelaAcessoJanela">Janela</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="perfilJanelaAcessoJanela" name="perfilJanelaAcesso.janela.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="perfilJanelaAcessoJanelaId" name="perfilJanelaAcesso.janela.janela_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="perfilJanelaAcessoPerfil">Perfil</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="perfilJanelaAcessoPerfil" name="perfilJanelaAcesso.perfil.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="perfilJanelaAcessoPerfilId" name="perfilJanelaAcesso.perfil.perfil_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="perfilJanelaAcessoIsActive">Ativo</label>
							<div class="controls">
								<input type="checkbox" id="perfilJanelaAcessoIsActive" name="perfilJanelaAcesso.isActive" checked="checked" value="${perfilJanelaAcesso.isActive }">							
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

				<div class="tab-pane fade" id="formulariosjanela-div">

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
