<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#usuario-li-a').click(function() {
		window.location.href = '<c:url value="/usuario/cadastro" />';
	});

	$('#usuarioperfil-li-a').click(function() {
		window.location.href = '<c:url value="/usuarioperfil/cadastro" />';
	});

	$('#usuarioorgacesso-li-a').click(function() {
		window.location.href = '<c:url value="/usuarioorgacesso/cadastro" />';
	});

});

$('#usuarioEmpresa').autocomplete({
	source: function( request, response ) {
        $.ajax({
          url: "<c:url value='/empresa/busca.json' />",
          dataType: "json",
          data : {n: request.term},
          success : function(data) {  

       		  if (!data || data.length == 0) {
       	            $('#usuarioEmpresa').val('');
					$('#usuarioEmpresaId').val('');
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
    	 $('#usuarioEmpresa').val(ui.item.label);
         return false;
     } ,
     select: function( event, ui ) {
    	 $('#usuarioEmpresa').val(ui.item.label);
         $('#usuarioEmpresaId').val(ui.item.value);
         return false;
     }
});

$('#usuarioOrganizacao').autocomplete({
	source: function( request, response ) {
        $.ajax({
          url: "<c:url value='/organizacao/busca.json' />",
          dataType: "json",
          data : {empresa_id: $('#usuarioEmpresaId').val() == '' ? '0' :  $('#usuarioEmpresaId').val(), org_nome : $('#usuarioOrganizacao').val()},
          success : function(data) {  

        	  if (!data || data.length == 0) {
     	            $('#usuarioOrganizacao').val('');
     	           $('#usuarioOrganizacaoId').val('');
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
      	 $('#usuarioOrganizacao').val(ui.item.label);
           return false;
       } ,
     select: function( event, ui ) {
         $('#usuarioOrganizacao').val(ui.item.label);
         $('#usuarioOrganizacaoId').val(ui.item.value);
         return false;
     }
});

$('#usuarioParceiroNegocio').autocomplete({
	source: function( request, response ) {
        $.ajax({
          url: "<c:url value='/parceironegocio/busca.json' />",
          dataType: "json",
          data : {empresa_id: $('#usuarioEmpresaId').val() == '' ? '0' :  $('#usuarioEmpresaId').val(), 
        		  organizacao_id: $('#usuarioOrganizacaoId').val() == '' ? '0' :  $('#usuarioOrganizacaoId').val(),
        		  nome: $('#usuarioParceiroNegocio').val()},
          success : function(data) {  

        	  if (!data || data.length == 0) {
     	            $('#usuarioParceiroNegocio').val('');
     	           $('#usuarioParceiroNegocioId').val('');
     	        }

        	  response($.map(data, function(parceironegocio) {  
        		  return {
        			  label: parceironegocio.nome,
        			  value: parceironegocio.parceironegocio_id
                  };
              }));  
           }
        });
     },
     focus: function( event, ui ) {
      	 $('#usuarioParceiroNegocio').val(ui.item.label);
           return false;
       } ,
     select: function( event, ui ) {
         $('#usuarioParceiroNegocio').val(ui.item.label);
         $('#usuarioParceiroNegocioId').val(ui.item.value);
         return false;
     }
});

$('#usuarioElementoBd').autocomplete({
	source: function( request, response ) {
        $.ajax({
          url: "<c:url value='/elementobd/busca.json' />",
          dataType: "json",
          data : {nomecolunabd : $('#colunaBdElementoBd').val()},
          success : function(data) {  

        	  if (!data || data.length == 0) {
     	            $('#colunaBdElementoBd').val('');
     	           $('#colunaBdElementoBdId').val('');
     	        }

        	  response($.map(data, function(elementobd) {  
        		  return {
        			  label: elementobd.nomeColunaBd,
        			  value: elementobd.elementoBd_id
                  };
              }));  
           }
        });
     },
     focus: function( event, ui ) {
      	
    	 $('#colunaBdElementoBd').val(ui.item.label);
      	$('#colunaBdNomeColunaBd').val(ui.item.label);
      	
           return false;
       } ,
     select: function( event, ui ) {
         $('#colunaBdElementoBd').val(ui.item.label);
         $('#colunaBdNomeColunaBd').val(ui.item.label);
         $('#colunaBdElementoBdId').val(ui.item.value);
         return false;
     }
});

function limpaForm(){

	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.elementoBdForm.reset();
	}

}


</script>

<div class="span9">

	<section id="tabs">
		<div class="bs-docs-example">

			<ul id="myTab" class="nav nav-tabs">
				<li class="active" id="usuario-li"><a href="#usuario-div" data-toggle="tab" id="usuario-li-a">Usuário</a></li>
				<li class="" id="usuarioperfil-li"><a href="#usuarioperfil-div" data-toggle="tab" id="usuarioperfil-li-a">Usuario Perfil</a></li>
				<li class="" id="usuarioorgacesso-li"><a href="#usuarioorgacesso-div" data-toggle="tab" id="usuarioorgacesso-li-a">Usuário Organização</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="usuario-div">					

					<form id="usuarioForm" name="usuarioForm" action="<c:url value="/usuario/salva"/>" method="POST">
						<div class="control-group">
							<label class="control-label" for="usuarioEmpresa">Empresa</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="usuarioEmpresa" name="usuario.empresa.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="usuarioEmpresaId" name="usuario.empresa.empresa_id" type="hidden">
	    					</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="usuarioOrganizacao">Organização</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="usuarioOrganizacao" name="usuario.organizacao.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="usuarioOrganizacaoId" name="usuario.organizacao.organizacao_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="usuarioParceiroNegocio">Perceiro de Negócios</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="usuarioParceiroNegocio" name="usuario.parceironegocio.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="usuarioParceiroNegocioId" name="usuario.parceironegocio.parceironegocio_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="usuarioUsuario">Supervisor Usuário</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="usuarioUsuario" name="usuario.usuario.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="usuarioUsuarioId" name="usuario.usuario.usuario_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="usuarioNome">Nome</label>
							<div class="controls">
								<input type="text" id="usuarioNome" name="usuario.nome" placeholder="Nome" required>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="usuarioDescricao">Descrição</label>
							<div class="controls">
								<input type="text" id="usuarioDescricao" name="usuario.descricao" placeholder="Descricao" required>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="usuarioSenha">Senha</label>
							<div class="controls">
								<input type="password" id="usuarioSenha" name="usuario.senha" placeholder="Senha" required>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="usuarioEmail">Email</label>
							<div class="controls">
								<input type="text" id="usuarioEmail" name="usuario.email" placeholder="E-mail" required>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="usuarioTelefone">Telefone</label>
							<div class="controls">
								<input type="text" id="usuarioTelefone" name="usuario.telefone" placeholder="Telefone" required>
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

				<div class="tab-pane fade" id="usuarioperfil-div">					

				</div>

				<div class="tab-pane fade" id="usuarioorgacesso-div">

				</div>

			</div>
		</div>
	</section>
</div>

<%@ include file="/footer.jspf"%>
