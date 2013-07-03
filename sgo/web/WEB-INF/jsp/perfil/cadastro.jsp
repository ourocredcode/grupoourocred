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

	$('#perfilSupervisorUsuario').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/usuarios/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#perfilEmpresaId').val() == '' ? '0' :  $('#perfilEmpresaId').val(), 
	        		  organizacao_id: $('#perfilOrganizacaoId').val() == '' ? '0' :  $('#perfilOrganizacaoId').val(),
	        		  nome : $('#perfilSupervisorUsuario').val()},
	          success : function(data) {  

	        	  if (!data || data.length == 0) {
	     	            $('#perfilSupervisorUsuario').val('');
	     	           $('#perfilSupervisorUsuarioId').val('');
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
	      	 $('#perfilSupervisorUsuario').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#perfilSupervisorUsuario').val(ui.item.label);
	         $('#perfilSupervisorUsuarioId').val(ui.item.value);
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
	
	alert(perfilFomr.perfil.supervisorPerfil.usuario_id().val);

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
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
		<a href="#" class="current">Perfil</a>
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
				<li class="active" id="perfil-li"><a href="#perfil-div" data-toggle="tab" id="perfil-li-a">Perfil</a></li>
				<li class="" id="perfilorgacesso-li"><a href="#perfilorgacesso-div" data-toggle="tab" id="perfilorgacesso-li-a">Perfil Organização</a></li>
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

						<div class="row-fluid">
							<div class="span3">							
								<label for="perfilEmpresa">Empresa</label>							
	      						<input class="input-xlarge" id="perfilEmpresa" name="perfil.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" required readonly="readonly">
	      						<input class="span1" id="perfilEmpresaId" name="perfil.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden">	    					
							</div>
							<div class="span3">
								<label for="perfilOrganizacao">Organização</label>
	      						<input class="input-xlarge" id="perfilOrganizacao" name="perfil.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" required readonly="readonly">
	      						<input class="span1" id="perfilOrganizacaoId" name="perfil.organizacao.organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" type="hidden">
							</div>
						</div>
						<div class="row-fluid">	
							<div class="span2">
								<label for="perfilSupervisorUsuario">Supervisor Usuário</label>
	      						<input class="input-medium" id="perfilSupervisorUsuario" name="perfil.supervisorUsuario.nome" value="${perfil.supervisorUsuario.nome }" type="text">
	      						<input class="span2" id="perfilSupervisorUsuarioId" name="perfil.supervisorUsuario.usuario_id" value="${perfil.supervisorUsuario.usuario_id }" type="hidden">
							</div>
							<div class="span3">
								<label for="perfilChave">Chave</label>							
								<input class="input-xlarge" id="perfilChave" name="perfil.chave" value="${perfil.chave }" type="text" placeholder="Chave" required>							
							</div>
							<div class="span3">
								<label for="perfilNome">Nome</label>							
								<input class="input-xlarge" id="perfilNome" name="perfil.nome" value="${perfil.Nome }" type="text" placeholder="Nome" required>							
							</div>
							<div class="span1">
								<label for="perfilIsActive">Ativo</label>							
								<input id="perfilIsActive" name="perfil.isActive" type="checkbox" checked="checked" value="1" >
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

				<div class="tab-pane fade" id="perfilorgacesso-div"></div>				
				<div class="tab-pane fade" id="janela-div"></div>				
				<div class="tab-pane fade" id="perfiljanelaacesso-div"></div>
				<div class="tab-pane fade" id="formulariosjanela-div"></div>
				<div class="tab-pane fade" id="campoformulario-div"></div>
				<div class="tab-pane fade" id="tabelabd-div"></div>
				<div class="tab-pane fade" id="colunabd-div"></div>
				<div class="tab-pane fade" id="elementobd-div"></div>
				<div class="tab-pane fade" id="tipodadobd-div"></div>

			</div>
		</div>
	</div>
</div>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<div class="widget-box">
				<div class="widget-title">
					<span class="icon"><i class="icon-signal"></i></span>
					<h5>Perfil</h5>
				</div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty perfis}">
						<table
							class="table table-bordered table-striped table-hover data-table"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>Empresa</th>
									<th>Organização</th>
									<th>Perfil</th>
									<th>Supervisor Usuário</th>
									<th>Ativo</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${perfis }" var="perfil">
									<tr>
										<td>${perfil.empresa.nome }</td>
										<td>${perfil.organizacao.nome }</td>
										<td>${perfil.nome }</td>
										<td>${perfil.supervisorUsuario.nome }</td>
										<td>${perfil.isActive }</td>
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
