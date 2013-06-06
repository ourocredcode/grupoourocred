<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#produto-li-a').click(function() {
		window.location.href = '<c:url value="/produto/cadastro" />';
	});

	$('#grupoproduto-li-a').click(function() {
		window.location.href = '<c:url value="/grupoproduto/cadastro" />';
	});

	$('#subgrupoproduto-li-a').click(function() {
		window.location.href = '<c:url value="/subgrupoproduto/cadastro" />';
	});

	$('#produtoEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#produtoEmpresa').val('');
						$('#produtoEmpresaId').val('');
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
        	 $('#produtoEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#produtoEmpresa').val(ui.item.label);
             $('#produtoEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#produtoOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#produtoEmpresaId').val() == '' ? '0' :  $('#produtoEmpresaId').val(), org_nome : $('#produtoOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#produtoOrganizacao').val('');
         	            $('#produtoOrganizacaoId').val('');
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
          	 $('#produtoOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#produtoOrganizacao').val(ui.item.label);
             $('#produtoOrganizacaoId').val(ui.item.value);
             return false;
         }
    });
	
	$('#produtoGrupoProduto').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/grupoproduto/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#produtoEmpresaId').val() == '' ? '0' :  $('#produtoEmpresaId').val(), 
	        		  organizacao_id : $('#produtoOrganizacaoId').val() == '' ? '0' :  $('#produtoOrganizacaoId').val(),
	        		  nome : $('#produtoGrupoProduto').val() },
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#produtoGrupoProduto').val('');
         	            $('#produtoGrupoProdutoId').val('');
         	        }

            	  response($.map(data, function(grupoProduto) {  
            		  return {
            			  label: grupoProduto.nome,
            			  value: grupoProduto.grupoProduto_id
                      };
                  }));  
               }
	        });
         },
         focus: function( event, ui ) {
          	 $('#produtoGrupoProduto').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#produtoGrupoProduto').val(ui.item.label);
             $('#produtoGrupoProdutoId').val(ui.item.value);
             return false;
         }
    });
	
	$('#produtoSubGrupoProduto').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/subgrupoproduto/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#produtoEmpresaId').val() == '' ? '0' :  $('#produtoEmpresaId').val(), 
	        		  organizacao_id : $('#produtoOrganizacaoId').val() == ''  ? '0' :  $('#produtoOrganizacaoId').val(),
	        		  nome : $('#produtoSubGrupoProduto').val() },
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#produtoSubGrupoProduto').val('');
         	            $('#produtoSubGrupoProdutoId').val('');
         	        }

            	  response($.map(data, function(subGrupoProduto) {  
            		  return {
            			  label: subGrupoProduto.nome,
            			  value: subGrupoProduto.subGrupoProduto_id
                      };
                  }));  
               }
	        });
         },
         focus: function( event, ui ) {
          	 $('#produtoSubGrupoProduto').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#produtoSubGrupoProduto').val(ui.item.label);
             $('#produtoSubGrupoProdutoId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/produto/cadastro" />';
	});

	$('#btnNovo').click(function() {
		document.produtoForm.reset();
	});

	$("#produtoIsActive").change(function(e){
		if(document.produtoForm.produtoIsActive.checked==true){
			document.produtoForm.produtoIsActive.value=true;
		}else{
			document.produtoForm.produtoIsActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.produtoForm.reset();
	}
}

function buscaSubGrupoProduto(){

	var grupoProduto_id = $('#produtoGrupoProdutoId').val();

	$("#produtoSubGrupoProdutoId").load('<c:url value="/produto/subgrupoprodutos" />',
			{'grupoProduto_id' : grupoProduto_id});

}

</script>

<div id="content-header">
		<h1>Cadastro Produto</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
		<a href="#" class="current">Produto</a>
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
				<li class="active" id="produto-li"><a href="#produto-div" data-toggle="tab" id="produto-li-a">Produtos</a></li>
				<li class="" id="grupoproduto-li"><a href="#grupoproduto-div" data-toggle="tab" id="grupoproduto-li-a">Grupo de Produtos</a></li>
				<li class="" id="subgrupoproduto-li"><a href="#subgrupoproduto-div" data-toggle="tab" id="subgrupoproduto-li-a">Sub Grupo de Produtos</a></li>
			</ul>
			
			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="produto-div" >					
					<form id="produtoForm" name="produtoForm" action="<c:url value="/produto/salva"/>" method="POST">

						<div class="row-fluid">
							<div class="span3">
								<label for="produtoEmpresa">Empresa</label>									
	      						<input class="input-xlarge" id="produtoEmpresa" name="produto.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" required readonly="readonly">
	      						<input class="span1" id="produtoEmpresaId" name="produto.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden">
	    					</div>

							<div class="span3">
								<label for="produtoOrganizacao">Organização</label>									
	      						<input class="input-xlarge" id="produtoOrganizacao" name="produto.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" required readonly="readonly">
	      						<input class="span1" id="produtoOrganizacaoId" name="produto.organizacao.organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" type="hidden">
							</div>
						</div>
						<div class="row-fluid">
							<div class="span2">
								<label for="produtoGrupoProdutoId">Grupo Produto</label>
								<select id="produtoGrupoProdutoId" name="produto.grupoProduto.grupoProduto_id" onchange="buscaSubGrupoProduto();" class="input-medium">
									<option value="">Selecione o grupo...</option>
									<c:forEach var="grupoProduto" items="${gruposProduto }">
									 	<option value="${grupoProduto.grupoProduto_id }" > ${grupoProduto.nome }</option>
									</c:forEach>
								</select>
							</div>
							<div class="span2">
	      						<label for="produtoSubGrupoProdutoId">Sub Grupo Produto</label>
	      						<select id="produtoSubGrupoProdutoId" name="produto.subGrupoProduto.subGrupoProduto_id" class="input-medium">
	      							<option value="">Selecion um Grupo Produto...</option>
	      						</select>
							</div>
							<div class="span2">
								<label for="produtoCategoriaProdutoId">Categoria Produto</label>
								<select id="produtoCategoriaProdutoId" name="produto.categoriaProduto.categoriaProduto_id" class="input-medium">
									<!--option value="">Selecione o grupo...</option-->								
									<c:forEach var="categoriaProduto" items="${categoriasProduto }">
									 	<option value="${categoriaProduto.categoriaProduto_id }" > ${categoriaProduto.nome }</option>
									</c:forEach>
								</select>
							</div>
							<div class="span2">
								<label for="produtoTipoProdutoId">Tipo Produto</label>
								<select id="produtoTipoProdutoId" name="produto.tipoProduto.tipoProduto_id" class="input-medium">
									<!--option value="">Selecione o grupo...</option-->
									<c:forEach var="tipoProduto" items="${tiposProduto }">
									 	<option value="${tipoProduto.tipoProduto_id }" > ${tipoProduto.nome }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span3">
								<label for="produtoNome">Nome</label>
								<input class="input-xlarge" id="produtoNome" name="produto.nome" placeholder="Nome" type="text" required>
							</div>
							<div class="span3">
								<label for="produtoDescricao">Descrição</label>
								<input class="input-xlarge" id="produtoDescricao" name="produto.descricao" placeholder="Descrição" type="text" required>
							</div>
							<div class="span1">
								<label for="produtoIsActive">Ativo</label>
								<input type="checkbox" id="produtoIsActive" name="produto.isActive" checked="checked" value="1" >
							</div>
						</div>
						<div class="row-fluid">
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

				<div class="tab-pane fade" id="grupoproduto-div"></div>
				<div class="tab-pane fade" id="subgrupoproduto-div"></div>
			</div>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>
