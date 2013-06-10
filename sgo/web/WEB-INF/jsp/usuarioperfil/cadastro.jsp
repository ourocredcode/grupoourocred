<%@ include file="/header.jspf"%>

<script type="text/javascript">

jQuery(function($){
	
	$('.data-table').dataTable({
		"bJQueryUI": true,
		"sPaginationType": "full_numbers",
		"sDom": '<""l>t<"F"fp>'
	});
	
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	$("span.icon input:checkbox, th input:checkbox").click(function() {
		var checkedStatus = this.checked;
		var checkbox = $(this).parents('.widget-box').find('tr td:first-child input:checkbox');		
		checkbox.each(function() {
			this.checked = checkedStatus;
			if (checkedStatus == this.checked) {
				$(this).closest('.checker > span').removeClass('checked');
			}
			if (this.checked) {
				$(this).closest('.checker > span').addClass('checked');
			}
		});
	});

	$('#usuario-li-a').click(function() {
		window.location.href = '<c:url value="/usuario/cadastro" />';
	});

	$('#usuarioperfil-li-a').click(function() {
		window.location.href = '<c:url value="/usuarioperfil/cadastro" />';
	});

	$('#usuarioorgacesso-li-a').click(function() {
		window.location.href = '<c:url value="/usuarioorgacesso/cadastro" />';
	});
	
	$('#usuarioPerfilEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#usuarioPerfilEmpresa').val('');
						$('#usuarioPerfilEmpresaId').val('');
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
        	 $('#usuarioPerfilEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {
        	 $('#usuarioPerfilEmpresa').val(ui.item.label);
             $('#usuarioPerfilEmpresaId').val(ui.item.value);
             return false;
         }
    });
	
	$('#usuarioPerfilOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#usuarioPerfilEmpresaId').val() == '' ? '0' :  $('#usuarioPerfilEmpresaId').val(), org_nome : $('#usuarioPerfilOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#usuarioPerfilOrganizacao').val('');
         	           $('#usuarioPerfilOrganizacaoId').val('');
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
          	 $('#usuarioPerfilOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#usuarioPerfilOrganizacao').val(ui.item.label);
             $('#usuarioPerfilOrganizacaoId').val(ui.item.value);
             return false;
         }
    });
	
	$('#usuarioPerfilUsuario').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/usuarios/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#usuarioPerfilEmpresaId').val() == '' ? '0' :  $('#usuarioPerfilEmpresaId').val(), 
	        		  organizacao_id: $('#usuarioPerfilOrganizacaoId').val() == '' ? '0' :  $('#usuarioPerfilOrganizacaoId').val(),
	        				  nome : $('#usuarioPerfilUsuario').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#usuarioPerfilUsuario').val('');
         	           $('#usuarioPerfilUsuarioId').val('');
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
          	 $('#usuarioPerfilUsuario').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#usuarioPerfilUsuario').val(ui.item.label);
             $('#usuarioPerfilUsuarioId').val(ui.item.value);
             return false;
         }
    });
	
	$('#usuarioPerfilPerfil').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/perfil/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#usuarioPerfilEmpresaId').val() == '' ? '0' :  $('#usuarioPerfilEmpresaId').val(), 
	        		  organizacao_id: $('#usuarioPerfilOrganizacaoId').val() == '' ? '0' :  $('#usuarioPerfilOrganizacaoId').val(),
	        		  nome : $('#usuarioPerfilPerfil').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#usuarioPerfilPerfil').val('');
         	           $('#usuarioPerfilPerfilId').val('');
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
          	 $('#usuarioPerfilPerfil').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#usuarioPerfilPerfil').val(ui.item.label);
             $('#usuarioPerfilPerfilId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/usuarioperfil/cadastro" />';
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});

	$("#usuarioPerfilIsActive").change(function(e){
		if(document.usuarioPerfilForm.usuarioPerfilIsActive.checked==true){
			document.usuarioPerfilForm.usuarioPerfilIsActive.value=true;
		}else{
			document.usuarioPerfilForm.usuarioPerfilIsActive.value=false;
		}
	});
	 
});

function limpaForm(){

	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.usuarioPerfilForm.reset();
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
				<li class="" id="usuario-li"><a href="#usuario-div" data-toggle="tab" id="usuario-li-a">Usuário</a></li>
				<li class="active" id="usuarioperfil-li"><a href="#usuarioperfil-div" data-toggle="tab" id="usuarioperfil-li-a">Usuario Perfil</a></li>
				<li class="" id="usuarioorgacesso-li"><a href="#usuarioorgacesso-div" data-toggle="tab" id="usuarioorgacesso-li-a">Usuário Organização</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade" id="usuario-div"></div>

				<div class="tab-pane fade  active in" id="usuarioperfil-div">					
						
						<form id="usuarioPerfilForm" name="usuarioPerfilForm" action="<c:url value="/usuarioperfil/salva"/>" method="POST">

							<div class="row-fluid">
								<div class="span2">
									<label for="usuarioPerfilEmpresa">Empresa</label>
		      						<input class="input-medium" id="usuarioPerfilEmpresa" name="usuarioPerfil.empresa.nome" value="${usuarioInfo.empresa.nome }"  type="text" readonly="readonly">
		      						<input class="span1" id="usuarioPerfilEmpresaId" name="usuarioPerfil.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden">
	    						</div>						
								<div class="span2">
									<label for="usuarioPerfilOrganizacao">Organização</label>
		      						<input class="input-medium" id="usuarioPerfilOrganizacao" name="usuarioPerfil.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" readonly="readonly">
		      						<input class="span1" id="usuarioPerfilOrganizacaoId" name="usuarioPerfil.organizacao.organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" type="hidden">
		    					</div>							
								<div class="span2">
									<label for="usuarioPerfilUsuario">Usuário</label>
		      						<input class="input-medium" id="usuarioPerfilUsuario" name="usuarioPerfil.usuario.nome" type="text" required onChange="limpaForm();">
		      						<input class="span1" id="usuarioPerfilUsuarioId" name="usuarioPerfil.usuario.usuario_id" type="hidden">			    					
								</div>
								<div class="span2">
									<label for="usuarioPerfilPerfil">Perfil</label>
		      						<input class="input-medium" id="usuarioPerfilPerfil" name="usuarioPerfil.perfil.nome" type="text" required onChange="limpaForm();">
		      						<input class="span1" id="usuarioPerfilPerfilId" name="usuarioPerfil.perfil.perfil_id" type="hidden">			    			
								</div>
								<div class="span1">
									<label for="usuarioPerfilIsActive">Ativo</label>
									<input type="checkbox" id="usuarioPerfilIsActive" name="usuarioPerfil.isActive" checked="checked" value="1">
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

				<div class="tab-pane fade" id="usuarioorgacesso-div"></div>

			</div>
		</div>
	</div>
</div>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<div class="widget-box">
				<div class="widget-title"><span class="icon"><i class="icon-signal"></i></span><h5>Usuário Perfil</h5></div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty usuarioPerfis}">
						<table class="table table-bordered table-striped table-hover data-table" style="font-size: 12px">
							<thead>									
								<tr>
									<th>Empresa</th>
									<th>Organização</th>
									<th>Usuário</th>
									<th>Perfil</th>								
									<th>Ativo</th>			
								</tr>
							</thead>								
							<tbody>
							<c:forEach items="${usuarioPerfis }" var="usuarioPerfil">
								<tr>
									<td>${usuarioPerfil.empresa.nome }</td>
									<td>${usuarioPerfil.organizacao.nome }</td>
									<td>${usuarioPerfil.usuario.nome }</td>
									<td>${usuarioPerfil.perfil.nome }</td>													
									<td>${usuarioPerfil.isActive }</td>
								</tr>
							</c:forEach>
							</tbody>								
						</table>
					</c:if>							
				</div>
			</div>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>
