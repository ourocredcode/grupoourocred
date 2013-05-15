<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#classificacaoparceiro-li-a').click(function() {
		window.location.href = '<c:url value="/classificacaoparceiro/cadastro" />';
	});

	$('#classificacaoParceiroEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#classificacaoParceiroEmpresa').val('');
						$('#classificacaoParceiroEmpresaId').val('');
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
        	 $('#classificacaoParceiroEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#classificacaoParceiroEmpresa').val(ui.item.label);
             $('#classificacaoParceiroEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#classificacaoParceiroOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#classificacaoParceiroEmpresaId').val() == '' ? '0' :  $('#classificacaoParceiroEmpresaId').val(), org_nome : $('#classificacaoParceiroOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#classificacaoParceiroOrganizacao').val('');
         	            $('#classificacaoParceiroOrganizacaoId').val('');
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
          	 $('#classificacaoParceiroOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#classificacaoParceiroOrganizacao').val(ui.item.label);
             $('#classificacaoParceiroOrganizacaoId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/classificacaoparceiro/cadastro" />';
	});

	$('#btnNovo').click(function() {
		document.classificacaoParceiroForm.reset();
	});

	$("#classificacaoParceiroIsActive").change(function(e){
		if(document.classificacaoParceiroForm.classificacaoParceiroIsActive.checked==true){
			document.classificacaoParceiroForm.classificacaoParceiroIsActive.value=true;
		}else{
			document.classificacaoParceiroForm.classificacaoParceiroIsActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.classificacaoParceiroForm.reset();
	}
}
</script>

<div id="content-header">
	<h1>Classificação Parceiro</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
		<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
	<a href="#" class="current">Classificação Parceiro</a>
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
				<li class="" id="classificacaoparceiro-li"><a href="#classificacaoparceiro-div" data-toggle="tab" id="classificacaoparceiro-li-a">Classificacao Parceiro</a></li>				
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="classificacaoparceiro-div">			

						<form id="classificacaoParceiroForm" name="classificacaoParceiroForm" action="<c:url value="/classificacaoparceiro/salva"/>" method="POST">							

							<div class="row-fluid">
								<div class="span2">
									<label for="classificacaoParceiroEmpresa">Empresa</label>								
		      						<input class="span12" id="classificacaoParceiroEmpresa" name="classificacaoParceiro.empresa.nome" type="text" value="${usuarioInfo.empresa.nome }" required onChange="limpaForm();">
		      						<input class="span1" id="classificacaoParceiroEmpresaId" name="classificacaoParceiro.empresa.empresa_id" type="hidden" value="${usuarioInfo.empresa.empresa_id }" >
		    					</div>							
								<div class="span2">
									<label for="classificacaoParceiroOrganizacao">Organização</label>
		      						<input class="span12" id="classificacaoParceiroOrganizacao" name="classificacaoParceiro.organizacao.nome" type="text" value="${usuarioInfo.organizacao.nome }" required onChange="limpaForm();">
		      						<input class="span1" id="classificacaoParceiroOrganizacaoId" name="classificacaoParceiro.organizacao.organizacao_id" type="hidden" value="${usuarioInfo.organizacao.organizacao_id }" >
		    					</div>
								<div class="span2">
									<label for="classificacaoParceiroNome">Nome</label>
									<input class="span12" type="text" id="classificacaoParceiroNome" name="classificacaoParceiro.nome" placeholder="Nome" value="${classificacaoParceiro.nome }" required>								
								</div>
								<div class="span2">
									<label for="classificacaoParceiroDescricao">Descrição</label>
									<input class="span12" type="text" id="classificacaoParceiroDescricao" name="classificacaoParceiro.descricao" placeholder="Descrição" value="${classificacaoParceiro.descricao }" required>
								</div>
								<div class="span1">
									<label for="classificacaoParceiroIsActive">Ativo</label>
									<input type="checkbox" id="classificacaoParceiroIsActive" name="classificacaoParceiro.isActive" checked="checked" value="1" >							
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
