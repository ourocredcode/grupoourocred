<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#menu-li-a').click(function() {
		window.location.href = '<c:url value="/menu/cadastro" />';
	});
	
	$('#menuEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
	          success : function(data) {  

	       		  if (!data || data.length == 0) {
	       	            $('#menuEmpresa').val('');
						$('#menuEmpresaId').val('');
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
	    	 $('#menuEmpresa').val(ui.item.label);
	         return false;
	     } ,
	     select: function( event, ui ) {
	    	 $('#menuEmpresa').val(ui.item.label);
	         $('#menuEmpresaId').val(ui.item.value);
	         return false;
	     }
	});

	$('#menuOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#menuEmpresaId').val() == '' ? '0' :  $('#menuEmpresaId').val(), org_nome : $('#menuOrganizacao').val()},
	          success : function(data) {  

	        	  if (!data || data.length == 0) {
	     	            $('#menuOrganizacao').val('');
	     	           $('#menuOrganizacaoId').val('');
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
	      	 $('#menuOrganizacao').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#menuOrganizacao').val(ui.item.label);
	         $('#menuOrganizacaoId').val(ui.item.value);
	         return false;
	     }
	});

	$('#menuJanela').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/janela/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#menuEmpresaId').val() == '' ? '0' :  $('#menuEmpresaId').val(), 
	        		  organizacao_id: $('#perfilOrganizacaoId').val() == '' ? '0' :  $('#menuOrganizacaoId').val(),
	        		  nome : $('#menuJanela').val()},
	          success : function(data) {  

	        	  if (!data || data.length == 0) {
	     	            $('#menuJanela').val('');
	     	           $('#menuJanelaId').val('');
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
	      	 $('#menuJanela').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#menuJanela').val(ui.item.label);
	         $('#menuJanelaId').val(ui.item.value);
	         return false;
	     }
	});
	
	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/menu/cadastro" />';
	});
	
	$('#btnNovo').click(function() {
		document.menuForm.reset();
	});
	
	$("#menuIsActive").change(function(e){
		if(document.menuForm.menuIsActive.checked==true){
			document.menuForm.menuIsActive.value=true;
		}else{
			document.menuForm.menuIsActive.value=false;
		}
	});

});

function limpaForm(){
	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.menuForm.reset();
	}
}


</script>

<div id="content-header">
	<h1>Cadastro Menu</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
	<a href="#" class="current">Menu</a>
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
				<li class="active" id="menu-li"><a href="#menu-div" data-toggle="tab" id="menu-li-a">Cadastro de Menus</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">
				
				<div class="tab-pane fade active in" id="menu-div">
					
					<form id="menuForm" name="menuForm" action="<c:url value="/menu/salva"/>" method="POST">

						<div class="control-group">
							<label class="control-label" for="menuEmpresa">Empresa</label>
      						<input id="menuEmpresa" name="menu.empresa.nome" type="text" required onChange="limpaForm();" class="input-large">
      						<input id="menuEmpresaId" name="menu.empresa.empresa_id" type="hidden">
						</div>
						<div class="control-group">
							<label class="control-label" for="menuOrganizacao">Organização</label>
							<input id="menuOrganizacao" name="menu.organizacao.nome" type="text" required onChange="limpaForm();" class="input-large">
							<input id="menuOrganizacaoId" name="menu.organizacao.organizacao_id" type="hidden">
						</div>
						<div class="control-group">
							<label class="control-label" for="menuNome">Nome</label>
							<div class="controls">
								<input type="text" id="menuNome" name="menu.nome" placeholder="Nome" required class="input-large">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="menuDescricao">Descrição</label>
							<div class="controls">
								<input type="text" id="menuDescricao" name="menu.descricao" placeholder="Descrição" required class="input-large">
							</div>
						</div>
		
						<div class="control-group">
							<label class="control-label" for="menuIsActive">Ativo</label>
							<div class="controls">
								<input type="checkbox" id="menuIsActive" name="menu.isActive" checked="checked" value="1" >							
							</div>							
						</div>				

						<div class="control-group">
							<label class="control-label" for="menuAcao">Ação</label>
							<div class="controls">
								<input type="text" id="menuAcao" name="menu.acao" placeholder="Ação" required class="input-large">							
							</div>							
						</div>
						<div class="control-group">
							<label class="control-label" for="menuJanela">Janela</label>
							<input id="menuJanela" name="menu.janela.nome" type="text" required onChange="limpaForm();" class="input-large">
							<input id="menuJanelaId" name="menu.janela.janela_id" type="hidden">
						</div>
						<div class="control-group">
							<label class="control-label" for="menuIsSomenteLeitura">Somente Leitura</label>
							<div class="controls">
								<input type="checkbox" id="menuIsSomenteLeitura" name="menu.IsSomenteLeitura" checked="checked" value="1" >							
							</div>							
						
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
