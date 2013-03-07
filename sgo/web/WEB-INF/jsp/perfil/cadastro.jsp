<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#perfil-li-a').click(function() {
		window.location.href = '<c:url value="/perfil/cadastro" />';
	});

	$('#perfilorganizacao-li-a').click(function() {
		window.location.href = '<c:url value="/perfilorganizacao/cadastro" />';
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
		document.perfilForm.reset();
	});
	
	$("#perfilIsActive").change(function(e){
		$(this).val( $("#perfilIsActive:checked").length > 0 ? "1" : "0");
	});

});

function limpaForm(){
	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.perfilForm.reset();
	}
}


</script>

<div class="span9">

	<section id="tabs">
		<div class="bs-docs-example">

			<ul id="myTab" class="nav nav-tabs">
				<li class="active" id="perfil-li"><a href="#perfil-div" data-toggle="tab" id="perfil-li-a">Perfil</a></li>
				<li class="" id="perfilorganizacao-li"><a href="#perfilperfilorganizacao-div" data-toggle="tab" id="perfilorganizacao-li-a">Perfil Organização</a></li>
				<li class="" id="janela-li"><a href="#janela-div" data-toggle="tab" id="janela-li-a">Janela</a></li>
				<li class="" id="perfiljanelaacesso-li"><a href="#perfiljanelaacesso-div" data-toggle="tab" id="perfiljanelaacesso-li-a">Janela Perfil</a></li>
				<li class="" id="formulariosjanela-li"><a href="#formulariosjanela-div" data-toggle="tab" id="formulariosjanela-li-a">Formulários Janela</a></li>
				<li class="" id="campoformulario-li"><a href="#campoformulario-div" data-toggle="tab" id="campoformulario-li-a">Campo Formulário</a></li>
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
	      						<input class="span2" id="perfilEmpresa" name="perfil.empresa.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="perfilEmpresaId" name="perfil.empresa.empresa_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="perfilOrganizacao">Organização</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="perfilOrganizacao" name="perfil.organizacao.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="perfilOrganizacaoId" name="perfil.organizacao.organizacao_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="perfilUsuario">Supervisor Usuário</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="perfilUsuario" name="perfil.usuario.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="perfilUsuarioId" name="perfil.usuario.usuario_id" type="hidden">
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
				
				<div class="tab-pane fade" id="perfilorganizacao-div">					

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
	</section>
</div>

<%@ include file="/footer.jspf"%>
