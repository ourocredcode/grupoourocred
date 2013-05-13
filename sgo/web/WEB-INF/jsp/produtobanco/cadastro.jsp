<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#produtobanco-li-a').click(function() {
		window.location.href = '<c:url value="/produtobanco/cadastro" />';
	});
	
	$('#produtoBancoEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#produtoBancoEmpresa').val('');
						$('#produtoBancoEmpresaId').val('');
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
        	 $('#produtoBancoEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#produtoBancoEmpresa').val(ui.item.label);
             $('#produtoBancoEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#produtoBancoOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#produtoBancoEmpresaId').val() == '' ? '0' :  $('#produtoBancoEmpresaId').val(), org_nome : $('#produtoBancoOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#produtoBancoOrganizacao').val('');
         	            $('#produtoBancoOrganizacaoId').val('');
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
          	 $('#produtoBancoOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#produtoBancoOrganizacao').val(ui.item.label);
             $('#produtoBancoOrganizacaoId').val(ui.item.value);
             return false;
         }
    });
	
	$('#produtoBancoBanco').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/banco/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#produtoBancoEmpresaId').val() == '' ? '0' :  $('#produtoBancoEmpresaId').val(), 
	        		  organizacao_id : $('#produtoBancoOrganizacaoId').val() == '' ? '0' :  $('#produtoBancoOrganizacaoId').val(),
	        		  nome : $('#produtoBancoBanco').val() },
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#produtoBancoBanco').val('');
         	            $('#produtoBancoBancoId').val('');
         	        }

            	  response($.map(data, function(banco) {  
            		  return {
            			  label: banco.nome,
            			  value: banco.banco_id
                      };
                  }));  
               }
	        });
         },
         focus: function( event, ui ) {
          	 $('#produtoBancoBanco').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#produtoBancoBanco').val(ui.item.label);
             $('#produtoBancoBancoId').val(ui.item.value);
             return false;
         }
    });
	
	$('#produtoBancoProduto').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/produto/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#produtoBancoEmpresaId').val() == '' ? '0' :  $('#produtoBancoEmpresaId').val(), 
	        		  organizacao_id : $('#produtoBancoOrganizacaoId').val() == '' ? '0' :  $('#produtoBancoOrganizacaoId').val(),
	        		  nome : $('#produtoBancoProduto').val() },
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#produtoBancoProduto').val('');
         	            $('#produtoBancoProdutoId').val('');
         	        }

            	  response($.map(data, function(produto) {  
            		  return {
            			  label: produto.nome,
            			  value: produto.produto_id
                      };
                  }));  
               }
	        });
         },
         focus: function( event, ui ) {
          	 $('#produtoBancoProduto').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#produtoBancoProduto').val(ui.item.label);
             $('#produtoBancoProdutoId').val(ui.item.value);
             return false;
         }
    });
	
	$('#produtoBancoTabela').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/tabela/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#produtoBancoEmpresaId').val() == '' ? '0' :  $('#produtoBancoEmpresaId').val(), 
	        		  organizacao_id : $('#produtoBancoOrganizacaoId').val() == '' ? '0' :  $('#produtoBancoOrganizacaoId').val(),
	        		  nome : $('#produtoBancoTabela').val() },
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#produtoBancoTabela').val('');
         	            $('#produtoBancoTabelaId').val('');
         	        }

            	  response($.map(data, function(tabela) {  
            		  return {
            			  label: tabela.nome,
            			  value: tabela.tabela_id
                      };
                  }));  
               }
	        });
         },
         focus: function( event, ui ) {
          	 $('#produtoBancoTabela').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#produtoBancoTabela').val(ui.item.label);
             $('#produtoBancoTabelaId').val(ui.item.value);
             return false;
         }
    });

	$("#produtoBancoIsActive").change(function(e){
		if(document.produtoBancoForm.produtoBancoIsActive.checked==true){
			document.produtoBancoForm.produtoBancoIsActive.value=true;
		}else{
			document.produtoBancoForm.produtoBancoIsActive.value=false;
		}
	});

	$('#btnNovo').click(function() {		
		limpaForm();
	});

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/produtobanco/cadastro" />';
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.produtoForm.reset();
	}
}
</script>

<div id="content-header">
		<h1>Cadastro Produto Banco</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
		<a href="#" class="current">Produto Banco</a>
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
				<li class="active" id="produtobanco-li"><a href="#produtobanco-div" data-toggle="tab" id="produtobanco-li-a">Produto Banco</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="produtobanco-div">
					
					<div class="row25MarginTop">
						<form id="produtoBancoForm" name="produtoBancoForm" action="<c:url value="/produtobanco/salva"/>" method="POST">

							<div class="span3">
								<div class="control-group">
									<label class="control-label" for="produtoBancoEmpresa">Empresa</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span10" id="produtoBancoEmpresa" name="produtoBanco.empresa.nome" type="text" required onChange="limpaForm();">
			      						<input class="span10" id="produtoBancoEmpresaId" name="produtoBanco.empresa.empresa_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="produtoBancoOrganizacao">Organização</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span10" id="produtoBancoOrganizacao" name="produtoBanco.organizacao.nome" type="text" required onChange="limpaForm();">
			      						<input class="span10" id="produtoBancoOrganizacaoId" name="produtoBanco.organizacao.organizacao_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="produtoBancoBanco">Banco</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span10" id="produtoBancoBanco" name="produtoBanco.banco.nome" type="text" required onChange="limpaForm();">
			      						<input class="span10" id="produtoBancoBancoId" name="produtoBanco.banco.banco_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="produtoBancoProduto">Produto</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span10" id="produtoBancoProduto" name="produtoBanco.produto.nome" type="text" required onChange="limpaForm();">
			      						<input class="span10" id="produtoBancoProdutoId" name="produtoBanco.produto.produto_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="produtoBancoTabela">Tabela</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span10" id="produtoBancoTabela" name="produtoBanco.tabela.nome" type="text" required onChange="limpaForm();">
			      						<input class="span10" id="produtoBancoTabelaId" name="produtoBanco.tabela.tabela_id" type="hidden">
			    					</div>
								</div>								
								<div class="control-group">
									<label class="control-label" for="produtoBancoIsActive">Ativo</label>
									<div class="controls">
										<input type="checkbox" id="produtoBancoIsActive" name="produtoBanco.isActive" checked="checked" value="1" >							
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
								
							</div>

						</form>
						
					</div>
				

				</div>

			</div>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>
