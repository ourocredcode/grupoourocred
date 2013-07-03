<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#cidade-li-a').click(function() {
		window.location.href = '<c:url value="/cidade/cadastro" />';
	});

	$('#cidadeEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#cidadeEmpresa').val('');
						$('#cidadeEmpresaId').val('');
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
        	 $('#cidadeEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#cidadeEmpresa').val(ui.item.label);
             $('#cidadeEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#cidadeOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#cidadeEmpresaId').val() == '' ? '0' :  $('#cidadeEmpresaId').val(), org_nome : $('#cidadeOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#cidadeOrganizacao').val('');
         	            $('#cidadeOrganizacaoId').val('');
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
          	 $('#cidadeOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#cidadeOrganizacao').val(ui.item.label);
             $('#cidadeOrganizacaoId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/cidade/cadastro" />';
	});

	$('#btnNovo').click(function() {
		document.cidadeForm.reset();
	});

	$("#cidadeIsActive").change(function(e){
		if(document.cidadeForm.cidadeIsActive.checked==true){
			document.cidadeForm.cidadeIsActive.value=true;
		}else{
			document.cidadeForm.cidadeIsActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.cidadeForm.reset();
	}
}
</script>

<div id="content-header">
	<h1>Cadastro Cidade</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
	<a href="#" class="current">Cidade</a>
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
				<li class="active" id="cidade-li"><a href="#cidade-div" data-toggle="tab" id="cidade-li-a">Cidade</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="cidade-div" >

					<form id="cidadeForm" name="cidadeForm" action="<c:url value="/cidade/salva"/>" method="POST">

						<div class="row-fluid">							
							<div class="span2">
								<label for="cidadeEmpresa">Empresa</label>								
		      					<input class="span10" id="cidadeEmpresa" name="cidade.empresa.nome" type="text" value="${usuarioInfo.empresa.nome }" required onChange="limpaForm();" readonly="readonly">
		      					<input class="span1" id="cidadeEmpresaId" name="cidade.empresa.empresa_id" type="hidden" value="${usuarioInfo.empresa.empresa_id }">
	    					</div>
							<div class="span2">
								<label for="cidadeOrganizacao">Organização</label>
								<input class="span10" id="cidadeOrganizacao" name="cidade.organizacao.nome" type="text" value="${usuarioInfo.organizacao.nome }" required onChange="limpaForm();" readonly="readonly">
		      					<input class="span1" id="cidadeOrganizacaoId" name="cidade.organizacao.organizacao_id" type="hidden" value="${usuarioInfo.organizacao.organizacao_id }">
							</div>						
							<div class="span2">
								<label for="cidadeNome">Nome</label>
								<input class="span12" type="text" id="cidadeNome" name="cidade.nome" placeholder="Nome" value="${cidade.nome }" required>
							</div>
							<div class="span2">
								<label for="cidadeDescricao">Descrição</label>
								<input class="span12" type="text" id="cidadeDescricao" name="cidade.descricao" placeholder="Descrição" value="${cidade.descricao }" required>
							</div>
							<div class="span1">
								<label for="cidadeIsActive">Ativo</label>
								<input type="checkbox" id="cidadeIsActive" name="cidade.isActive" checked="checked" value="1" >
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
				</div>
			</div>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>
