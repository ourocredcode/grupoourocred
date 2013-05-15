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
	
	$('#campoFormularioColunaBd').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/colunaBd/busca.json' />",
	          dataType: "json",
	          data : {empresa_id : $('#campoFormularioEmpresaId').val() == '' ? '0' :  $('#campoFormularioEmpresaId').val(),
	        		  organizacao_id: $('#campoFormularioOrganizacaoId').val() == '' ? '0' :  $('#campoFormularioOrganizacaoId').val(),
	        				  nomeColunaBd : $('#campoFormularioColunaBd').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#campoFormularioColunaBd').val('');
         	           $('#campoFormularioColunaBdId').val('');
         	        }

            	  response($.map(data, function(colunabd) {  
            		  return {
            			  label: colunabd.nomeColunaBd,
            			  value: colunabd.colunabd_id
                      };
                  }));  
               }
	        });
         },
         focus: function( event, ui ) {

			$('#campoFormularioColunaBd').val(ui.item.label);
          	$('#campoFormularioColunaBdId').val(ui.item.label);

               return false;
           } ,
         select: function( event, ui ) {
             $('#campoFormularioColunaBd').val(ui.item.label);
             $('#campoFormularioNome').val(ui.item.label);
             $('#campoFormularioColunaBdId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/campoformulario/cadastro" />';
	});

	$('#btnNovo').click(function() {
		document.campoFormularioForm.reset();
	});

	$("#campoFormularioIsMostrado").change(function(e){
		if(document.campoFormularioForm.campoFormularioIsMostrado.checked==true){
			document.campoFormularioForm.campoFormularioIsMostrado.value=true;
		}else{
			document.campoFormularioForm.campoFormularioIsMostrado.value=false;
		}
	});
	
	$("#campoFormularioIsSomenteLeitura").change(function(e){
		if(document.campoFormularioForm.campoFormularioIsSomenteLeitura.checked==true){
			document.campoFormularioForm.campoFormularioIsSomenteLeitura.value=true;
		}else{
			document.campoFormularioForm.campoFormularioIsSomenteLeitura.value=false;
		}
	});
	
	$("#campoFormularioIsActive").change(function(e){
		if(document.campoFormularioForm.campoFormularioIsActive.checked==true){
			document.campoFormularioForm.campoFormularioIsActive.value=true;
		}else{
			document.campoFormularioForm.campoFormularioIsActive.value=false;
		}
	});
});

function limpaForm(){

	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.campoFormularioForm.reset();
	}

}


</script>

<div id="content-header">
	<h1>Cadastro Campo Formulário</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
		<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
	<a href="#" class="current">Campo Formulário</a>
</div>

<c:if test="${not empty notice}">
	<c:choose>
		<c:when test="${fn:contains(notice,'Erro:')}">
				<div class="alert alert-error">
					<strong>${notice }</strong>
					<a href="#" data-dismiss="alert" class="close">×</a>
				</div>
		</c:when>
		<c:otherwise>
				<div class="alert alert-success">
					<strong>${notice }</strong>
					<a href="#" data-dismiss="alert" class="close">×</a>
				</div>
		</c:otherwise>
	</c:choose>
</c:if>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">

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
			
				<div class="tab-pane fade" id="perfil-div"></div>
				<div class="tab-pane fade" id="perfilorgacesso-div"></div>
				<div class="tab-pane fade" id="janela-div"></div>
				<div class="tab-pane fade" id="perfiljanelaacesso-div"></div>
				<div class="tab-pane fade" id="formulariosjanela-div"></div>

				<div class="tab-pane fade active in" id="campoformulario-div">
					<form id="campoFormularioForm" name="campoFormularioForm" action="<c:url value="/campoformulario/salva"/>" method="POST">

						<div class="row-fluid">
							<div class="span2">
								<label for="campoFormularioEmpresa">Empresa</label>							
	      						<input class="span10" id="campoFormularioEmpresa" name="campoFormulario.empresa.nome" type="text" value="${usuarioInfo.empresa.nome }" required onChange="limpaForm();" readonly="readonly">
	      						<input class="span1" id="campoFormularioEmpresaId" name="campoFormulario.empresa.empresa_id" type="hidden" value="${usuarioInfo.empresa.empresa_id }">
	    					</div>					
							<div class="span2">
								<label for="campoFormularioOrganizacao">Organização</label>							
	      						<input class="span10" id="campoFormularioOrganizacao" name="campoFormulario.organizacao.nome" type="text" value="${usuarioInfo.organizacao.nome }" required onChange="limpaForm();" readonly="readonly">
	      						<input class="span1" id="campoFormularioOrganizacaoId" name="campoFormulario.organizacao.organizacao_id" type="hidden" value="${usuarioInfo.organizacao.organizacao_id }">
	    					</div>
	    					<div class="span2">
								<label for="campoFormularioFormulariosJanela">Fomulário</label>
	      						<input class="span10" id="campoFormularioFormulariosJanela" name="campoFormulario.formulariosJanela.nome" type="text" value="${campoFormulario.formulariosJanela.nome }" required onChange="limpaForm();">
	      						<input class="span1" id="campoFormularioFormulariosJanelaId" name="campoFormulario.formulariosJanela.formulariosjanela_id" type="hidden" value="${campoFormulario.formulariosJanela.formulariosjanela_id }">
	    					</div>
							<div class="span2">
								<label for="campoFormularioColunaBd">Coluna Bd</label>
	      						<input class="span10" id="campoFormularioColunaBd" name="campoFormulario.colunaBd.nomeColunaBb" type="text" value="${campoFormulario.colunaBd.nomeColunaBb }" required onChange="limpaForm();">
	      						<input class="span1" id="campoFormularioColunaBdId" name="campoFormulario.colunaBd.colunabd_id" type="hidden" value="${campoFormulario.colunaBd.colunabd_id }" >
	    					</div>
    					</div>
    					<div class="row-fluid">
							 <div class="span2">
								<label for="campoFormularioChave">Chave</label>
								<input class="span12" type="text" id="campoFormularioChave" name="campoFormulario.chave" placeholder="Chave" value="${campoFormulario.chave }" required>
							</div>
							 <div class="span2">
								<label for="campoFormularioNome">Nome</label>
								<input class="span12" type="text" id="campoFormularioNome" name="campoFormulario.nome" placeholder="Nome" value="${campoFormulario.nome }" required readonly="readonly">
							</div>						
							<div class="span1">
								<label for="campoFormularioIsMostrado">Mostrado</label>
								<input type="checkbox" id="campoFormularioIsMostrado" name="campoFormulario.isMostrado" checked="checked" value="1">
							</div>
							<div class="span1">
								<label for="campoFormularioIsSomenteLeitura">Leitura</label>
								<input type="checkbox" id="campoFormularioIsSomenteLeitura" name="campoFormulario.isSomenteLeitura" checked="checked" value="1">
							</div>
							<div class="span1">
								<label for="campoFormularioIsActive">Ativo</label>
								<input type="checkbox" id="campoFormularioIsActive" name="campoFormulario.isActive" checked="checked" value="1">
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

				<div class="tab-pane fade" id="tabelabd-div"></div>
				<div class="tab-pane fade" id="colunabd-div"></div>
				<div class="tab-pane fade" id="tipodadobd-div"></div>

			</div>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>
