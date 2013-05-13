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
	
	$('#perfilEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
	          success : function(data) {  

	       		  if (!data || data.length == 0) {
	       	            $('#perfilEmpresa').val('');
						$('#perfilEmpresaId').val('');
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
	    	 $('#perfilEmpresa').val(ui.item.label);
	         return false;
	     } ,
	     select: function( event, ui ) {
	    	 $('#perfilEmpresa').val(ui.item.label);
	         $('#perfilEmpresaId').val(ui.item.value);
	         return false;
	     }
	});

	$('#perfilOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#perfilEmpresaId').val() == '' ? '0' :  $('#perfilEmpresaId').val(), org_nome : $('#perfilOrganizacao').val()},
	          success : function(data) {  

	        	  if (!data || data.length == 0) {
	     	            $('#perfilOrganizacao').val('');
	     	           $('#perfilOrganizacaoId').val('');
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
	      	 $('#perfilOrganizacao').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#perfilOrganizacao').val(ui.item.label);
	         $('#perfilOrganizacaoId').val(ui.item.value);
	         return false;
	     }
	});

	$('#perfilUsuario').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/usuarios/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#perfilEmpresaId').val() == '' ? '0' :  $('#perfilEmpresaId').val(), 
	        		  organizacao_id: $('#perfilOrganizacaoId').val() == '' ? '0' :  $('#perfilOrganizacaoId').val(),
	        		  nome : $('#perfilUsuario').val()},
	          success : function(data) {  

	        	  if (!data || data.length == 0) {
	     	            $('#perfilUsuario').val('');
	     	           $('#perfilUsuarioId').val('');
	     	        }

	        	  response($.map(data, function(usuario) {  
	        		  return {
	        			  label: usuario.nome,
	        			  value: usuario.usuario_id
	                  };
	              }));  
	           }
	        });
	     },
	     focus: function( event, ui ) {
	      	 $('#perfilUsuario').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#perfilUsuario').val(ui.item.label);
	         $('#perfilUsuarioId').val(ui.item.value);
	         return false;
	     }
	});
	
	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/perfil/cadastro" />';
	});
	
	$('#btnNovo').click(function() {
		limpaForm();
	});
	
	$("#perfilIsActive").change(function(e){
		if(document.perfilForm.perfilIsActive.checked==true){
			document.perfilForm.perfilIsActive.value=true;
		}else{
			document.perfilForm.perfilIsActive.value=false;
		}
	});

});

function limpaForm(){
	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.perfilForm.reset();
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
				<li class="active" id="perfil-li"><a href="#perfil-div" data-toggle="tab" id="perfil-li-a">Perfil</a></li>
				<li class="" id="perfilorgacesso-li"><a href="#perfilorgacesso-div" data-toggle="tab" id="perfilorgacesso-li-a">Perfil Organiza��o</a></li>
				<li class="" id="janela-li"><a href="#janela-div" data-toggle="tab" id="janela-li-a">Janela</a></li>
				<li class="" id="perfiljanelaacesso-li"><a href="#perfiljanelaacesso-div" data-toggle="tab" id="perfiljanelaacesso-li-a">Janela Perfil</a></li>
				<li class="" id="formulariosjanela-li"><a href="#formulariosjanela-div" data-toggle="tab" id="formulariosjanela-li-a">Formul�rios Janela</a></li>
				<li class="" id="campoformulario-li"><a href="#campoformulario-div" data-toggle="tab" id="campoformulario-li-a">Campo Formul�rio</a></li>
				<li class="" id="tabelabd-li"><a href="#tabelabd-div" data-toggle="tab" id="tabelabd-li-a">Tabela BD</a></li>
				<li class="" id="colunabd-li"><a href="#colunabd-div" data-toggle="tab" id="colunabd-li-a">Coluna BD</a></li>	
				<li class="" id="elementobd-li"><a href="#elementobd-div" data-toggle="tab" id="elementobd-li-a">Elemento BD</a></li>
				<li class="" id="tipodadobd-li"><a href="#tipodadobd-div" data-toggle="tab" id="tipodadobd-li-a">TipoDado BD</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">
				
				<div class="tab-pane fade active in" id="perfil-div">
					<form id="perfilForm" name="perfilForm" action="<c:url value="/perfil/salva"/>" method="POST">

						<div class="control-group">
							<label class="control-label" for="perfilEmpresa">Empresa</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="perfilEmpresa" name="perfil.empresa.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="perfilEmpresaId" name="perfil.empresa.empresa_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="perfilOrganizacao">Organiza��o</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="perfilOrganizacao" name="perfil.organizacao.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="perfilOrganizacaoId" name="perfil.organizacao.organizacao_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="perfilUsuario">Supervisor Usu�rio</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="perfilUsuario" name="perfil.usuario.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="perfilUsuarioId" name="perfil.usuario.usuario_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="perfilChave">Chave</label>
							<div class="controls">
								<input type="text" id="perfilChave" name="perfil.chave" placeholder="Chave" required>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="perfilNome">Nome</label>
							<div class="controls">
								<input type="text" id="perfilNome" name="perfil.nome" placeholder="Nome" required>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="perfilIsActive">Ativo</label>
							<div class="controls">
								<input type="checkbox" id="perfilIsActive" name="perfil.isActive" checked="checked" value="1" >							
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
				
				<div class="tab-pane fade" id="perfilorgacesso-div">					

				</div>
				
				<div class="tab-pane fade" id="janela-div">					

				</div>
				
				<div class="tab-pane fade" id="perfiljanelaacesso-div">
					
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
