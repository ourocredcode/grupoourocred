<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#categoriaproduto-li-a').click(function() {
		window.location.href = '<c:url value="/categoriaproduto/cadastro" />';
	});

	$('#categoriaProdutoEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#categoriaProdutoEmpresa').val('');
						$('#categoriaProdutoEmpresaId').val('');
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
        	 $('#categoriaProdutoEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#categoriaProdutoEmpresa').val(ui.item.label);
             $('#categoriaProdutoEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#categoriaProdutoOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#categoriaProdutoEmpresaId').val() == '' ? '0' :  $('#categoriaProdutoEmpresaId').val(), org_nome : $('#categoriaProdutoOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#categoriaProdutoOrganizacao').val('');
         	            $('#categoriaProdutoOrganizacaoId').val('');
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
          	 $('#categoriaProdutoOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#categoriaProdutoOrganizacao').val(ui.item.label);
             $('#categoriaProdutoOrganizacaoId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/categoriaproduto/cadastro" />';
	});

	$('#btnNovo').click(function() {
		document.categoriaProdutoForm.reset();
	});

	$("#categoriaProdutoIsActive").change(function(e){
		if(document.categoriaProdutoForm.categoriaProdutoIsActive.checked==true){
			document.categoriaProdutoForm.categoriaProdutoIsActive.value=true;
		}else{
			document.categoriaProdutoForm.categoriaProdutoIsActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.categoriaProdutoForm.reset();
	}
}
</script>

<div id="content-header">
	<h1>Cadastro Categoria Produto</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
	</div>
</div>

	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
		<a href="#" class="current">Categoria Produto</a>
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
				<li class="" id="categoriaproduto-li"><a href="#categoriaproduto-div" data-toggle="tab" id="categoriaproduto-li-a">Categoria de Produtos</a></li>				
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="categoriaproduto-div">
					<form id="categoriaProdutoForm" name="categoriaProdutoForm" action="<c:url value="/categoriaproduto/salva"/>" method="POST">
					
						<div class="row-fluid">							
							<div class="span2">
								<label for="categoriaProdutoEmpresa">Empresa</label>
	      						<input class="span12" id="categoriaProdutoEmpresa" name="categoriaProduto.empresa.nome" type="text" value="${usuarioInfo.empresa.nome }" required onChange="limpaForm();" readonly="readonly">
	      						<input class="span1" id="categoriaProdutoEmpresaId" name="categoriaProduto.empresa.empresa_id" type="hidden" value="${usuarioInfo.empresa.empresa_id }">
	    					</div>
							<div class="span2">
								<label for="categoriaProdutoOrganizacao">Organização</label>
								<input class="span12" id="categoriaProdutoOrganizacao" name="categoriaProduto.organizacao.nome" type="text" value="${usuarioInfo.organizacao.nome }" required onChange="limpaForm();" readonly="readonly">
		      					<input class="span1" id="categoriaProdutoOrganizacaoId" name="categoriaProduto.organizacao.organizacao_id" type="hidden" value="${usuarioInfo.organizacao.organizacao_id }" >
	    					</div>
							<div class="span2">
								<label for="categoriaProdutoNome">Nome</label>
								<input class="span12" type="text" id="categoriaProdutoNome" name="categoriaProduto.nome" placeholder="Nome" value="${categoriaProduto.nome }" required>
							</div>
							<div class="span2">
								<label for="categoriaProdutoDescricao">Descrição</label>
								<input class="span12" type="text" id="categoriaProdutoDescricao" name="categoriaProduto.descricao" placeholder="Descrição" value="${categoriaProduto.descricao }" required>
							</div>
							<div class="span1">
								<label for="categoriaProdutoIsActive">Ativo</label>
								<input type="checkbox" id="categoriaProdutoIsActive" name="categoriaProduto.isActive" checked="checked" value="1" >
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
