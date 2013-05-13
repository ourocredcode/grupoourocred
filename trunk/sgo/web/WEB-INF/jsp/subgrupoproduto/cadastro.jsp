<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#grupoproduto-li-a').click(function() {
		window.location.href = '<c:url value="/grupoproduto/cadastro" />';
	});

	$('#subgrupoproduto-li-a').click(function() {
		window.location.href = '<c:url value="/subgrupoproduto/cadastro" />';
	});

	$('#produto-li-a').click(function() {
		window.location.href = '<c:url value="/produto/cadastro" />';
	});

	$('#subGrupoProdutoEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#subGrupoProdutoEmpresa').val('');
						$('#subGrupoProdutoEmpresaId').val('');
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
        	 $('#subGrupoProdutoEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#subGrupoProdutoEmpresa').val(ui.item.label);
             $('#subGrupoProdutoEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#subGrupoProdutoOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#subGrupoProdutoEmpresaId').val() == '' ? '0' :  $('#subGrupoProdutoEmpresaId').val(), org_nome : $('#subGrupoProdutoOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#subGrupoProdutoOrganizacao').val('');
         	            $('#subGrupoProdutoOrganizacaoId').val('');
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
          	 $('#subGrupoProdutoOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#subGrupoProdutoOrganizacao').val(ui.item.label);
             $('#subGrupoProdutoOrganizacaoId').val(ui.item.value);
             return false;
         }
    });

	$('#subGrupoProdutoGrupoProduto').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/grupoproduto/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#subGrupoProdutoEmpresaId').val() == '' ? '0' :  $('#subGrupoProdutoEmpresaId').val(), 
	        		  organizacao_id: $('#subGrupoProdutoOrganizacaoId').val() == '' ? '0' :  $('#subGrupoProdutoOrganizacaoId').val(),
	        		  nome : $('#subGrupoProdutoGrupoProduto').val()},
	          success : function(data) {  

	        	  if (!data || data.length == 0) {
	     	            $('#subGrupoProdutoGrupoProduto').val('');
	     	           $('#subGrupoProdutoGrupoProdutoId').val('');
	     	        }

	        	  response($.map(data, function(grupoproduto) {  
	        		  return {
	        			  label: grupoproduto.nome,
	        			  value: grupoproduto.grupoProduto_id
	                  };
	              }));  
	           }
	        });
	     },
	     focus: function( event, ui ) {
	      	 $('#subGrupoProdutoGrupoProduto').val(ui.item.label);
	           return false;
	       } ,
	     select: function( event, ui ) {
	         $('#subGrupoProdutoGrupoProduto').val(ui.item.label);
	         $('#subGrupoProdutoGrupoProdutoId').val(ui.item.value);
	         return false;
	     }
	});

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/subgrupoproduto/cadastro" />';
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});

	$("#subGrupoProdutoIsActive").change(function(e){
		if(document.subGrupoProdutoForm.subGrupoProdutoIsActive.checked==true){
			document.subGrupoProdutoForm.subGrupoProdutoIsActive.value=true;
		}else{
			document.subGrupoProdutoForm.subGrupoProdutoIsActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.subGrupoProdutoForm.reset();
	}
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

<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">

			<ul id="myTab" class="nav nav-tabs">
				<li class="" id="grupoproduto-li"><a href="#grupoproduto-div" data-toggle="tab" id="grupoproduto-li-a">Grupo de Produtos</a></li>
				<li class="active" id="subgrupoproduto-li"><a href="#subgrupoproduto-div" data-toggle="tab" id="subgrupoproduto-li-a">Sub Grupo de Produtos</a></li>
				<li class="" id="produto-li"><a href="#produto-div" data-toggle="tab" id="produto-li-a">Produtos</a></li>
			</ul>
			<div id="myTabContent" class="tab-content">
				
				<div class="tab-pane fade" id="grupoproduto-div"></div>

				<div class="tab-pane fade active in" id="subgrupoproduto-div">
				
					<div class="row25MarginTop">
						<div class="span3">
							<form id="subGrupoProdutoForm" name="subGrupoProdutoForm" action="<c:url value="/subgrupoproduto/salva"/>" method="POST">
								<div class="control-group">
									<label class="control-label" for="subGrupoProdutoEmpresa">Empresa</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span10" id="subGrupoProdutoEmpresa" name="subGrupoProduto.empresa.nome" type="text" required onChange="limpaForm();">
			      						<input class="span10" id="subGrupoProdutoEmpresaId" name="subGrupoProduto.empresa.empresa_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="subGrupoProdutoOrganizacao">Organização</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span10" id="subGrupoProdutoOrganizacao" name="subGrupoProduto.organizacao.nome" type="text" required onChange="limpaForm();">
			      						<input class="span10" id="subGrupoProdutoOrganizacaoId" name="subGrupoProduto.organizacao.organizacao_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="subGrupoProdutoGrupoProduto">Grupo Produto</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span10" id="subGrupoProdutoGrupoProduto" name="subGrupoProduto.grupoProduto.nome" type="text" required onChange="limpaForm();">
			      						<input class="span10" id="subGrupoProdutoGrupoProdutoId" name="subGrupoProduto.grupoProduto.grupoProduto_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="subGrupoProdutoNome">Nome</label>
									<div class="controls">
										<input type="text" id="subGrupoProdutoNome" name="subGrupoProduto.nome" placeholder="Nome" required>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="subGrupoProdutoDescricao">Descrição</label>
									<div class="controls">
										<input type="text" id="subGrupoProdutoDescricao" name="subGrupoProduto.descricao" placeholder="Descrição" required>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="subGrupoProdutoIsActive">Ativo</label>
									<div class="controls">
										<input type="checkbox" id="subGrupoProdutoIsActive" name="subGrupoProduto.isActive" checked="checked" value="1" >							
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
			
				<div class="tab-pane fade" id="produto-div"></div>
			</div>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>
