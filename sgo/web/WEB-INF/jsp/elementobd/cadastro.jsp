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
	
	$('#elementoBdEmpresa').autocomplete({
		source: function( request, response ) {			
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#elementoBdEmpresa').val('');
						$('#elementoBdEmpresaId').val('');
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
        	 $('#elementoBdEmpresa').val(ui.item.label);
        	 $('#elementoBdOrganizacao').removeAttr("readonly");
             return false;
         } ,
         select: function( event, ui ) {
        	 $('#elementoBdEmpresa').val(ui.item.label);
             $('#elementoBdEmpresaId').val(ui.item.value);
             return false;
         }
    });
	
	$('#elementoBdOrganizacao').autocomplete({		
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#elementoBdEmpresaId').val() == '' ? '0' :  $('#elementoBdEmpresaId').val(), org_nome : $('#elementoBdOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#elementoBdOrganizacao').val('');
         	           $('#elementoBdOrganizacaoId').val('');
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
          	 $('#elementoBdOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#elementoBdOrganizacao').val(ui.item.label);
             $('#elementoBdOrganizacaoId').val(ui.item.value);
             return false;
         }
    });

	$('#elementoBdNomeColunaBd').autocomplete({
		source: function( request, response ) {
			
			if($('#elementoBdEmpresaId').val() == ''){
				alert("Empresa não preenchida");
				$('#elementoBdNomeColunaBd').val('');
				$('#elementoBdEmpresa').focus();				
				return false;
			}

			$.ajax({
	          url: "<c:url value='/elementobd/busca.json' />",
	          
	          dataType: "json",
	          
	          data : {empresa_id: $('#elementoBdEmpresaId').val() == '' ? '0' :  $('#elementoBdEmpresaId').val(), 
	        		  organizacao_id: $('#elementoBdOrganizacaoId').val() == '' ? '0' :  $('#elementoBdOrganizacaoId').val(),
	        		  nomeColunaBd : $('#elementoBdNomeColunaBd').val()},
              success : function(data) {

            	  if (!data || data.length == 0) {
         	            $('#elementoBdNomeColunaBd').val('');	       	          	
         	        }

            	  response($.map(data, function(elementobd) {  
            		  return {
            			  label: elementobd.nomeColunaBd
                      };
                  }));  
               }
	        });
         },
         focus: function( event, ui ) {
          	 $('#elementoBdNomeColunaBd').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#elementoBdNomeColunaBd').val(ui.item.label);
             $('#elementoBdNomeColunaBdId').val(ui.item.value);
             return false;
         }
    });
	
	$("#elementoBdEmpresa").blur(function() {
		var empresa_id = $("#elementoBdEmpresaId").val();
		var organizacao_id = $("#elementoBdOrganizacaoId").val();
		var nomeColunaBd = $("#elementoBdNomeColunaBd").val();
		
		$('#lista').load('<c:url value="/elementobd/lista" />', {
			'empresa_id' : empresa_id,
			'organizacao_id' : organizacao_id,
			'nomeColunaBd' : nomeColunaBd
		});
	});

	$("#elementoBdOrganizacao").blur(function() {
		var empresa_id = $("#elementoBdEmpresaId").val();
		var organizacao_id = $("#elementoBdOrganizacaoId").val();
		var nomeColunaBd = $("#elementoBdNomeColunaBd").val();
		
		$('#lista').load('<c:url value="/elementobd/lista" />', {
			'empresa_id' : empresa_id,
			'organizacao_id' : organizacao_id,
			'nomeColunaBd' : nomeColunaBd
		});
	});

	$("#elementoBdNomeColunaBd").blur(function() {
		var empresa_id = $("#elementoBdEmpresaId").val();
		var organizacao_id = $("#elementoBdOrganizacaoId").val();
		var nomeColunaBd = $("#elementoBdNomeColunaBd").val();
		if($('#elementoBdEmpresaId').val() == ''){
			alert("Empresa não preenchida");
			$('#elementoBdNomeColunaBd').val('');
			$('#elementoBdEmpresa').focus();				
			return false;
		}	
		$('#lista').load('<c:url value="/elementobd/lista" />', {
			'empresa_id' : empresa_id,
			'organizacao_id' : organizacao_id,
			'nomeColunaBd' : nomeColunaBd
		});
	});

	$("#elementoBdIsActive").change(function(e){
		if(document.elementoBdForm.elementoBdIsActive.checked==true){
			document.elementoBdForm.elementoBdIsActive.value=true;
		}else{
			document.elementoBdForm.elementoBdIsActive.value=false;
		}
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/elementobd/cadastro" />';
	});
	
});

function limpaForm(){
	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.elementoBdForm.reset();
	}
}

</script>

<div id="content-header">
	<h1>Cadastro - Elementos do banco</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
		<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
	<a href="#" class="current">Elementos do banco</a>
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
				<li class="" id="campoformulario-li"><a href="#campoformulario-div" data-toggle="tab" id="campoformulario-li-a">Campo Formulário</a></li>
				<li class="" id="tabelabd-li"><a href="#tabelabd-div" data-toggle="tab" id="tabelabd-li-a">Tabela BD</a></li>
				<li class="" id="colunabd-li"><a href="#colunabd-div" data-toggle="tab" id="colunabd-li-a">Coluna BD</a></li>	
				<li class="active" id="elementobd-li"><a href="#elementobd-div" data-toggle="tab" id="elementobd-li-a">Elemento BD</a></li>
				<li class="" id="tipodadobd-li"><a href="#tipodadobd-div" data-toggle="tab" id="tipodadobd-li-a">TipoDado BD</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">
			
				<div class="tab-pane fade" id="perfil-div"></div>
				<div class="tab-pane fade" id="perfilorgacesso-div"></div>
				<div class="tab-pane fade" id="janela-div"></div>
				<div class="tab-pane fade" id="perfiljanelaacesso-div"></div>
				<div class="tab-pane fade" id="formulariosjanela-div"></div>
				<div class="tab-pane fade" id="campoformulario-div"></div>
				<div class="tab-pane fade " id="tabelabd-div"></div>
				<div class="tab-pane fade " id="colunabd-div"></div>
				
				<div class="tab-pane fade active in" id="elementobd-div">

					<form id="elementoBdForm" name="elementoBdForm" action="<c:url value="/elementobd/salva"/>" method="POST">
						<div class="row-fluid">						
							<div class="span2">
								<label for="elementoBdEmpresa">Empresa</label>
	      						<input class="span12" id="elementoBdEmpresa" name="elementoBd.empresa.nome" type="text" value="${usuarioInfo.empresa.nome }" required onChange="limpaForm();" readonly="readonly">
	      						<input class="span1" id="elementoBdEmpresaId" name="elementoBd.empresa.empresa_id" type="hidden" value="${usuarioInfo.empresa.empresa_id }">
							</div>
							<div class="span2">
								<label for="elementoBdOrganizacao">Organização</label>
	      						<input class="span12" id="elementoBdOrganizacao" name="elementoBd.organizacao.nome" type="text" required readonly onChange="limpaForm();" value="${usuarioInfo.organizacao.nome }">
								<input class="span1" id="elementoBdOrganizacaoId" name="elementoBd.organizacao.organizacao_id" type="hidden" value="${usuarioInfo.organizacao.organizacao_id }">
							</div>
							<div class="span2">
								<label for="elementoBdNomeColunaBd">Nome Coluna BD</label>								
								<input class="span12" type="text" id="elementoBdNomeColunaBd" name="elementoBd.nomeColunaBd" placeholder="Nome da coluna BD" required readonly>
							</div>
							<div class="span2">
								<label for="elementoBd.nome">Nome</label>								
								<input class="span12" type="text" id="elementoBd.nome" name="elementoBd.nome" placeholder="Nome" required>								
							</div>
							<div class="span1">
								<label for="elementoBdIsActive">Ativo</label>
								<input type="checkbox" id="elementoBdIsActive" name="elemento.isActive" checked="checked" value="1" >
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

				<table class="table table-striped table-bordered" id="lista">
						<thead>
							<tr>
								<th>Empresa</th>
								<th>Organização</th>
								<th>Nome</th>								
							</tr>
						</thead>
						<tbody>	
							<c:forEach items="${elementosBd}" var="elementosBd">
								<tr>
									<td>${elementosBd.empresa.nome }</td>
									<td>${elementosBd.organizacao.nome }</td>
									<td>${elementosBd.nomeColunaBd }</td>									
								</tr>
							</c:forEach>
						</tbody>
					</table>

				<div class="tab-pane fade" id="tipodadobd-div"></div>							

			</div>	
			
		</div>			
	</div>
</div>

<%@ include file="/footer.jspf"%>
