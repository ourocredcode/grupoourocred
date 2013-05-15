<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#categoriaparceiro-li-a').click(function() {
		window.location.href = '<c:url value="/categoriaparceiro/cadastro" />';
	});

	$('#categoriaParceiroEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#categoriaParceiroEmpresa').val('');
						$('#categoriaParceiroEmpresaId').val('');
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
        	 $('#categoriaParceiroEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#categoriaParceiroEmpresa').val(ui.item.label);
             $('#categoriaParceiroEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#categoriaParceiroOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#categoriaParceiroEmpresaId').val() == '' ? '0' :  $('#categoriaParceiroEmpresaId').val(), org_nome : $('#categoriaParceiroOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#categoriaParceiroOrganizacao').val('');
         	            $('#categoriaParceiroOrganizacaoId').val('');
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
          	 $('#categoriaParceiroOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#categoriaParceiroOrganizacao').val(ui.item.label);
             $('#categoriaParceiroOrganizacaoId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/categoriaparceiro/cadastro" />';
	});

	$('#btnNovo').click(function() {
		document.categoriaParceiroForm.reset();
	});

	$("#categoriaParceiroIsActive").change(function(e){
		if(document.categoriaParceiroForm.categoriaParceiroIsActive.checked==true){
			document.categoriaParceiroForm.categoriaParceiroIsActive.value=true;
		}else{
			document.categoriaParceiroForm.categoriaParceiroIsActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.categoriaParceiroForm.reset();
	}
}
</script>

<div id="content-header">
	<h1>Cadastro Categoria Parceiro</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
		<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
	</div>
</div>

	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
		<a href="#" class="current">Categoria Parceiro</a>
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
				<li class="" id="categoriaparceiro-li"><a href="#categoriaparceiro-div" data-toggle="tab" id="categoriaparceiro-li-a">Categoria Parceiro</a></li>				
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="categoriaparceiro-div">

					<form id="categoriaParceiroForm" name="categoriaParceiroForm" action="<c:url value="/categoriaparceiro/salva"/>" method="POST">
						
						<div class="row-fluid">
							<div class="span2">
								<label for="categoriaParceiroEmpresa">Empresa</label>
								<input class="span10" id="categoriaParceiroEmpresa" name="categoriaParceiro.empresa.nome" type="text" value="${usuarioInfo.empresa.nome }" required onChange="limpaForm();" readonly="readonly">
	      						<input class="span1" id="categoriaParceiroEmpresaId" name="categoriaParceiro.empresa.empresa_id" type="hidden" value="${usuarioInfo.empresa.empresa_id }">
    						</div>
							<div class="span2">
								<label for="categoriaParceiroOrganizacao">Organização</label>
								<input class="span10" id="categoriaParceiroOrganizacao" name="categoriaParceiro.organizacao.nome" type="text" value="${usuarioInfo.organizacao.nome }" required onChange="limpaForm();" readonly="readonly">
		      					<input class="span1" id="categoriaParceiroOrganizacaoId" name="categoriaParceiro.organizacao.organizacao_id" type="hidden" value="${usuarioInfo.organizacao.organizacao_id }">
	    					</div>
							<div class="span2">
								<label for="categoriaParceiroNome">Nome</label>
								<input class="span12" type="text" id="categoriaParceiroNome" name="categoriaParceiro.nome" placeholder="Nome" value="${categoriaParceiro.nome }" required>
							</div>
							<div class="span2">
								<label for="categoriaParceiroDescricao">Descrição</label>
								<input class="span12" type="text" id="categoriaParceiroDescricao" name="categoriaParceiro.descricao" placeholder="Descrição" value="${categoriaParceiro.descricao }" required>
							</div>
							<div class="span1">
								<label for="categoriaParceiroIsActive">Ativo</label>
								<input type="checkbox" id="categoriaParceiroIsActive" name="categoriaParceiro.isActive" checked="checked" value="1" >							
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
	</section>
</div>

<%@ include file="/footer.jspf"%>
