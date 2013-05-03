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
	
	$('#usuarioOrgAcessoEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#usuarioOrgAcessoEmpresa').val('');
						$('#usuarioOrgAcessoEmpresaId').val('');
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
        	 $('#usuarioOrgAcessoEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {
        	 $('#usuarioOrgAcessoEmpresa').val(ui.item.label);
             $('#usuarioOrgAcessoEmpresaId').val(ui.item.value);
             return false;
         }
    });
	
	$('#usuarioOrgAcessoOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#usuarioOrgAcessoEmpresaId').val() == '' ? '0' :  $('#usuarioOrgAcessoEmpresaId').val(), org_nome : $('#usuarioOrgAcessoOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#usuarioOrgAcessoOrganizacao').val('');
         	           $('#usuarioOrgAcessoOrganizacaoId').val('');
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
          	 $('#usuarioOrgAcessoOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#usuarioOrgAcessoOrganizacao').val(ui.item.label);
             $('#usuarioOrgAcessoOrganizacaoId').val(ui.item.value);
             return false;
         }
    });
	
	$('#usuarioOrgAcessoUsuario').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/usuarios/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#usuarioOrgAcessoEmpresaId').val() == '' ? '0' :  $('#usuarioOrgAcessoEmpresaId').val(), 
	        		  organizacao_id: $('#usuarioOrgAcessoOrganizacaoId').val() == '' ? '0' :  $('#usuarioOrgAcessoOrganizacaoId').val(),
	        				  nome : $('#usuarioOrgAcessoUsuario').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#usuarioOrgAcessoUsuario').val('');
         	           $('#usuarioOrgAcessoUsuarioId').val('');
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
          	 $('#usuarioOrgAcessoUsuario').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#usuarioOrgAcessoUsuario').val(ui.item.label);
             $('#usuarioOrgAcessoUsuarioId').val(ui.item.value);
             return false;
         }
    });

});

function limpaForm(){

	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.elementoBdForm.reset();
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
				<li class="" id="usuario-li"><a href="#usuario-div" data-toggle="tab" id="usuario-li-a">Usuário</a></li>
				<li class="" id="usuarioperfil-li"><a href="#usuarioperfil-div" data-toggle="tab" id="usuarioperfil-li-a">Usuario Perfil</a></li>
				<li class="active" id="usuarioorgacesso-li"><a href="#usuarioorgacesso-div" data-toggle="tab" id="usuarioorgacesso-li-a">Usuário Organização</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade" id="usuario-div">					

				</div>

				<div class="tab-pane fade" id="usuarioperfil-div">					

				</div>

				<div class="tab-pane fade active in" id="usuarioorgacesso-div">

						<form id="usuarioOrgAcessoForm" name="usuarioOrgAcessoForm" action="<c:url value="/usuarioorgacesso/salva"/>" method="POST">
						
						<div class="control-group">
							<label class="control-label" for="usuarioOrgAcessoEmpresa">Empresa</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="usuarioOrgAcessoEmpresa" name="usuarioOrgAcesso.empresa.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="usuarioOrgAcessoEmpresaId" name="usuarioOrgAcesso.empresa.empresa_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="usuarioOrgAcessoOrganizacao">Organização</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="usuarioOrgAcessoOrganizacao" name="usuarioOrgAcesso.organizacao.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="usuarioOrgAcessoOrganizacaoId" name="usuarioOrgAcesso.organizacao.organizacao_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="usuarioOrgAcessoUsuario">Usuário</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="usuarioOrgAcessoUsuario" name="usuarioOrgAcesso.usuario.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="usuarioOrgAcessoUsuarioId" name="usuarioOrgAcesso.usuario.usuario_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="usuarioOrgAcessoIsActive">Ativo</label>
							<div class="controls">
								<input type="checkbox" id="usuarioOrgAcessoIsActive" name="usuarioOrgAcesso.isActive" checked="checked" value="1">							
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

			</div>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>
