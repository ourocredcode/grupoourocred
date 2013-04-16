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

	$('#grupoProdutoEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#grupoProdutoEmpresa').val('');
						$('#grupoProdutoEmpresaId').val('');
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
        	 $('#grupoProdutoEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#grupoProdutoEmpresa').val(ui.item.label);
             $('#grupoProdutoEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#grupoProdutoOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#grupoProdutoEmpresaId').val() == '' ? '0' :  $('#grupoProdutoEmpresaId').val(), org_nome : $('#grupoProdutoOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#grupoProdutoOrganizacao').val('');
         	            $('#grupoProdutoOrganizacaoId').val('');
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
          	 $('#grupoProdutoOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#grupoProdutoOrganizacao').val(ui.item.label);
             $('#grupoProdutoOrganizacaoId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/grupoproduto/cadastro" />';
	});

	$('#btnNovo').click(function() {
		document.perfilForm.reset();
	});

	$("#grupoProdutoIsActive").change(function(e){
		$(this).val( $("#grupoProdutoIsActive:checked").length > 0 ? "1" : "0");
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.grupoProdutoForm.reset();
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
				<li class="active" id="grupoproduto-li"><a href="#grupoproduto-div" data-toggle="tab" id="grupoproduto-li-a">Grupo de Produtos</a></li>
				<li class="" id="subgrupoproduto-li"><a href="#subgrupoproduto-div" data-toggle="tab" id="subgrupoproduto-li-a">Sub Grupo de Produtos</a></li>
				<li class="" id="produto-li"><a href="#produto-div" data-toggle="tab" id="produto-li-a">Produtos</a></li>
			</ul>
			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="grupoproduto-div">
				
					<div class="row25MarginTop">
						<div class="span3">
							<form id="grupoProdutoForm" name="grupoProdutoForm" action="<c:url value="/grupoproduto/salva"/>" method="POST">
								<div class="control-group">
									<label class="control-label" for="grupoProdutoEmpresa">Empresa</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span10" id="grupoProdutoEmpresa" name="grupoProduto.empresa.nome" type="text" required onChange="limpaForm();">
			      						<input class="span10" id="grupoProdutoEmpresaId" name="grupoProduto.empresa.empresa_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="grupoProdutoOrganizacao">Organização</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span10" id="grupoProdutoOrganizacao" name="grupoProduto.organizacao.nome" type="text" required onChange="limpaForm();">
			      						<input class="span10" id="grupoProdutoOrganizacaoId" name="grupoProduto.organizacao.organizacao_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="grupoProdutoNome">Nome</label>
									<div class="controls">
										<input type="text" id="grupoProdutoNome" name="grupoProduto.nome" placeholder="Nome" required>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="grupoProdutoDescricao">Descrição</label>
									<div class="controls">
										<input type="text" id="grupoProdutoDescricao" name="grupoProduto.descricao" placeholder="Descrição" required>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="grupoProdutoIsActive">Ativo</label>
									<div class="controls">
										<input type="checkbox" id="grupoProdutoIsActive" name="grupoProduto.isActive" checked="checked" value="1" >							
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

				<div class="tab-pane fade" id="subgrupoproduto-div" ></div>
				<div class="tab-pane fade" id="produto-div" ></div>
	
			</div>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>
