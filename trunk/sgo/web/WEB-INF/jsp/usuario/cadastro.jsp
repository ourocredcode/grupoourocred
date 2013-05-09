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
	        		  nome : $('#usuarioParceiroNegocio').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#usuarioParceiroNegocio').val('');
         	           $('#usuarioParceiroNegocioId').val('');
         	        }

            	  response($.map(data, function(parceironegocio) {  
            		  return {
            			  label: parceironegocio.nome,
            			  value: parceironegocio.parceiroNegocio_id
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
             $('#usuarioNome').val(ui.item.label);             
             $('#usuarioParceiroNegocioId').val(ui.item.value);
             return false;             
         }
    });
	
	$('#usuarioSupervisorUsuario').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/usuarios/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#usuarioEmpresaId').val() == '' ? '0' :  $('#usuarioEmpresaId').val(), 
	        		  organizacao_id: $('#usuarioOrganizacaoId').val() == '' ? '0' :  $('#usuarioOrganizacaoId').val(),
	        		  nome : $('#usuarioSupervisorUsuario').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	           $('#usuarioSupervisorUsuario').val('');
         	           $('#usuarioSupervisorUsuarioId').val('');
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
          	 $('#usuarioSupervisorUsuario').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#usuarioSupervisorUsuario').val(ui.item.label);
             $('#usuarioSupervisorUsuarioId').val(ui.item.value);
             return false;
         }
    });
	
	$("#usuarioIsActive").change(function(e){
		$(this).val( $("#usuarioIsActive:checked").length > 0 ? true : false);
	});

});


function limpaForm(){
	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.usuarioForm.reset();
	}	
}


</script>

<div id="content-header">
		<h1>Cadastro Usuário</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
		<a href="#" class="current">Usuário</a>
	</div>

<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">

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
	      						<input class="span10" id="usuarioEmpresa" name="usuario.empresa.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="usuarioEmpresaId" name="usuario.empresa.empresa_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="usuarioOrganizacao">Organização</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="usuarioOrganizacao" name="usuario.organizacao.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="usuarioOrganizacaoId" name="usuario.organizacao.organizacao_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="usuarioParceiroNegocio">Perceiro de Negócios</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="usuarioParceiroNegocio" name="usuario.parceiroNegocio.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="usuarioParceiroNegocioId" name="usuario.parceiroNegocio.parceiroNegocio_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="usuarioSupervisorUsuario">Supervisor Usuário</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="usuarioSupervisorUsuario" name="usuario.usuario.nome" type="text" onChange="limpaForm();">
	      						<input class="span10" id="usuarioSupervisorUsuarioId" name="usuario.usuario.usuario_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="usuarioChave">Login</label>
							<div class="controls">
								<input type="text" id="usuarioChave" name="usuario.chave" placeholder="Login" required>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="usuarioNome">Nome</label>
							<div class="controls">
								<input type="text" id="usuarioNome" name="usuario.nome" placeholder="Nome" required readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="usuarioDescricao">Descrição</label>
							<div class="controls">
								<input type="text" id="usuarioDescricao" name="usuario.descricao" placeholder="Descricao">
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
							<div class="control-group">
							<label class="control-label" for="usuarioIsActive">Ativo</label>
							<div class="controls">
								<input type="checkbox" id="usuarioIsActive" name="usuario.isActive" checked="checked" value="${usuario.isActive }" >							
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

				<div class="tab-pane fade" id="usuarioperfil-div">					

				</div>

				<div class="tab-pane fade" id="usuarioorgacesso-div">

				</div>

			</div>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>
