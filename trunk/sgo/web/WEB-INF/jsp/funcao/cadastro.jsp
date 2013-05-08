<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#funcao-li-a').click(function() {
		window.location.href = '<c:url value="/funcao/cadastro" />';
	});

	$('#funcaoEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#funcaoEmpresa').val('');
						$('#funcaoEmpresaId').val('');
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
        	 $('#funcaoEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#funcaoEmpresa').val(ui.item.label);
             $('#funcaoEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#funcaoOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#funcaoEmpresaId').val() == '' ? '0' :  $('#funcaoEmpresaId').val(), org_nome : $('#funcaoOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#funcaoOrganizacao').val('');
         	            $('#funcaoOrganizacaoId').val('');
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
          	 $('#funcaoOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#funcaoOrganizacao').val(ui.item.label);
             $('#funcaoOrganizacaoId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/funcao/cadastro" />';
	});

	$('#btnNovo').click(function() {
		document.perfilForm.reset();
	});

	$("#funcaoIsActive").change(function(e){
		$(this).val( $("#funcaoIsActive:checked").length > 0 ? "1" : "0");
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.funcaoForm.reset();
	}
}
</script>

<div id="content-header">
		<h1>Cadastro Função</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
		<a href="#" class="current">Função</a>
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
				<li class="" id="funcao-li"><a href="#funcao-div" data-toggle="tab" id="funcao-li-a">Cadastro de Função</a></li>				
			</ul>
			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="funcao-div">
				
					<div class="row25MarginTop">
						<div class="span3">
							<form id="funcaoForm" name="funcaoForm" action="<c:url value="/funcao/salva"/>" method="POST">
								<div class="control-group">
									<label class="control-label" for="funcaoEmpresa">Empresa</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span10" id="funcaoEmpresa" name="funcao.empresa.nome" type="text" required onChange="limpaForm();">
			      						<input class="span10" id="funcaoEmpresaId" name="funcao.empresa.empresa_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="funcaoOrganizacao">Organização</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span10" id="funcaoOrganizacao" name="funcao.organizacao.nome" type="text" required onChange="limpaForm();">
			      						<input class="span10" id="funcaoOrganizacaoId" name="funcao.organizacao.organizacao_id" type="hidden">
			    					</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="funcaoNome">Nome</label>
									<div class="controls">
										<input class="span10"type="text" id="funcaoNome" name="funcao.nome" placeholder="Nome" required>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="funcaoDescricao">Descrição</label>
									<div class="controls">
										<input  class="span10" type="text" id="funcaoDescricao" name="funcao.descricao" placeholder="Descrição" required>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="funcaoIsActive">Ativo</label>
									<div class="controls">
										<input type="checkbox" id="funcaoIsActive" name="funcao.isActive" checked="checked" value="1" >							
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
	</div>
</div>

<%@ include file="/footer.jspf"%>
