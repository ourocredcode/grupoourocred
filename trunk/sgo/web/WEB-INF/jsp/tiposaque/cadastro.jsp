<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#tipoSaque-li-a').click(function() {
		window.location.href = '<c:url value="/tiposaque/cadastro" />';
	});

	$('#tipoSaqueEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#tipoSaqueEmpresa').val('');
						$('#tipoSaqueEmpresaId').val('');
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
        	 $('#tipoSaqueEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#tipoSaqueEmpresa').val(ui.item.label);
             $('#tipoSaqueEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#tipoSaqueOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#tipoSaqueEmpresaId').val() == '' ? '0' :  $('#tipoSaqueEmpresaId').val(), 
	        		  org_nome : $('#tipoSaqueOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#tipoSaqueOrganizacao').val('');
         	            $('#tipoSaqueOrganizacaoId').val('');
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
          	 $('#tipoSaqueOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#tipoSaqueOrganizacao').val(ui.item.label);
             $('#tipoSaqueOrganizacaoId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/tiposaque/cadastro" />';
	});

	$('#btnNovo').click(function() {
		document.tipoSaqueForm.reset();
	});

	$("#tipoSaqueIsActive").change(function(e){
		if(document.tipoSaqueForm.tipoSaqueIsActive.checked==true){
			document.tipoSaqueForm.tipoSaqueIsActive.value=true;
		}else{
			document.tipoSaqueForm.tipoSaqueIsActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.tipoSaqueForm.reset();
	}
}
</script>

<div id="content-header">
	<h1>Cadastro Tipo Saque</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
	</div>
</div>

	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
		<a href="#" class="current">Tipo Saque</a>
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
				<li class="" id="tiposaque-li"><a href="#tiposaque-div" data-toggle="tab" id="tiposaque-li-a">Cadastro de Tipo Saque</a></li>				
			</ul>
			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="tiposaque-div">				

					<form id="tipoSaqueForm" name="tipoSaqueForm" action="<c:url value="/tiposaque/salva"/>" method="POST">

						<div class="row-fluid">
							<div class="span3">
								<label for="tipoSaqueEmpresa">Empresa</label>
	      						<input class="span12" id="tipoSaqueEmpresa" name="tipoSaque.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" required onChange="limpaForm();" readonly="readonly">
	      						<input class="span1" id="tipoSaqueEmpresaId" name="tipoSaque.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden">			    					
							</div>
							<div class="span3">
								<label for="tipoSaqueOrganizacao">Organização</label>										
	      						<input class="span12" id="tipoSaqueOrganizacao" name="tipoSaque.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" required onChange="limpaForm();" readonly="readonly">
	      						<input class="span1" id="tipoSaqueOrganizacaoId" name="tipoSaque.organizacao.organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" type="hidden">
	    					</div>
							<div class="span3">
								<label for="tipoSaqueNome">Nome</label>
								<input class="span12" type="text" id="tipoSaqueNome" name="tipoSaque.nome" placeholder="Nome" value="${tipoSaque.nome }" required>
							</div>
							<div class="span1">
								<label for="tipoSaqueIsActive">Ativo</label>
								<input type="checkbox" id="tipoSaqueIsActive" name="tipoSaque.isActive" checked="checked" value="1" >
							</div>
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
					</form>
					
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th>Empresa</th>
								<th>Organização</th>
								<th>Nome</th>
							</tr>
						</thead>
						<tbody>	
							<c:forEach items="${tiposSaque }" var="tipoSaque">
								<tr>
									<td>${tipoSaque.empresa.nome }</td>
									<td>${tipoSaque.organizacao.nome }</td>
									<td>${tipoSaque.nome }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>
