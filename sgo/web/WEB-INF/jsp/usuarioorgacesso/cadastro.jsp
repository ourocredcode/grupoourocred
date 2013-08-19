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
	
	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/usuarioorgacesso/cadastro" />';
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});
	
	$("#isActive").change(function(e){
		if(document.usuarioOrgAcessoForm.isActive.checked==true){
			document.usuarioOrgAcessoForm.isActive.value=true;
		}else{
			document.usuarioOrgAcessoForm.isActive.value=false;
		}
	});
	
});

function altera(linha, empresa, organizacao, usuario) {
	var emp = empresa;
	var org = organizacao;
	var usu = usuario;

	var valor = linha.checked == true ? true : false;

	if (window.confirm("Deseja alterar o dado selecionado?"))
		$.post('<c:url value='/usuarioorgacesso/altera' />'
			,{'empresa_id': emp, 'organizacao_id' : org, 'usuario_id' : usu, 'isActive' : valor}
		);

	return false;
}

function limpaForm(){
	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.usuarioOrgAcessoForm.reset();
	}
}

</script>

<div id="content-header">
	<h1>Cadastro Usuário</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
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
				<li class="" id="usuarioperfil-li"><a href="#usuarioperfil-div" data-toggle="tab" id="usuarioperfil-li-a">Usuario Perfil</a></li>
				<li class="active" id="usuarioorgacesso-li"><a href="#usuarioorgacesso-div" data-toggle="tab" id="usuarioorgacesso-li-a">Usuário Organização</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade" id="usuario-div"></div>
				<div class="tab-pane fade" id="usuarioperfil-div"></div>

				<div class="tab-pane fade active in" id="usuarioorgacesso-div">
					<form id="usuarioOrgAcessoForm" name="usuarioOrgAcessoForm" action="<c:url value="/usuarioorgacesso/salva"/>" method="POST">
					
						<div class="row-fluid">
							<div class="span2">
								<label for="usuarioOrgAcessoEmpresa">Empresa</label>
      							<input class="input-medium" id="usuarioOrgAcessoEmpresa" name="usuarioOrgAcesso.empresa.nome" type="text" value="${usuarioInfo.empresa.nome }">
      							<input class="span1" id="usuarioOrgAcessoEmpresaId" name="usuarioOrgAcesso.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden">
    						</div>						
							<div class="span2">
								<label for="usuarioOrgAcessoOrganizacao">Organização</label>
	      						<input class="input-medium" id="usuarioOrgAcessoOrganizacao" name="usuarioOrgAcesso.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" >
	      						<input class="span1" id="usuarioOrgAcessoOrganizacaoId" name="usuarioOrgAcesso.organizacao.organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" type="hidden">
							</div>
							<div class="span2">
								<label for="usuarioOrgAcessoUsuario">Usuário</label>
	      						<input class="input-medium" id="usuarioOrgAcessoUsuario" name="usuarioOrgAcesso.usuario.nome" value="${usuarioOrgAcesso.usuario.nome }" type="text" required>
	      						<input class="span1" id="usuarioOrgAcessoUsuarioId" name="usuarioOrgAcesso.usuario.usuario_id" value="${usuarioOrgAcesso.usuario.usuario_id }" type="hidden">
	    					</div>
							<div class="span1">
								<label for="isActive">Ativo</label>
								<input type="checkbox" id="isActive" name="usuarioOrgAcesso.isActive" checked="checked" value="1">							
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

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<div class="widget-box">
				<div class="widget-title"><span class="icon"><i class="icon-signal"></i></span><h5>Usuário Organização</h5></div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty usuariosOrgAcesso}">
						<table id="myTable" class="table table-bordered table-striped table-hover data-table" style="font-size: 12px">
							<thead>
								<tr>
									<th>Empresa</th>
									<th>Organização</th>
									<th>Usuário</th>
									<th>Login</th>
									<th>Ativo</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${usuariosOrgAcesso }" var="usuarioOrgAcesso">
								<tr>
									<td>${usuarioOrgAcesso.empresa.nome }</td>									
									<td>${usuarioOrgAcesso.organizacao.nome } </td>
									<td>${usuarioOrgAcesso.usuario.nome }</td>
									<td>${usuarioOrgAcesso.usuario.chave }</td>
									<td>
										<label class="checkbox inline">
											<input type="checkbox" id="isActiveLine" name="usuarioOrgAcesso.isActive"
											<c:if test="${usuarioOrgAcesso.isActive == true }"> checked="checked"</c:if> onchange="altera(this, '${usuarioOrgAcesso.empresa.empresa_id }', '${usuarioOrgAcesso.organizacao.organizacao_id }', '${usuarioOrgAcesso.usuario.usuario_id }');">
										</label>
									</td>							
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
