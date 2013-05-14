<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#agencia-li-a').click(function() {
		window.location.href = '<c:url value="/agencia/cadastro" />';
	});

	$('#agenciaEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#agenciaEmpresa').val('');
						$('#agenciaEmpresaId').val('');
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
        	 $('#agenciaEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#agenciaEmpresa').val(ui.item.label);
             $('#agenciaEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#agenciaOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#agenciaEmpresaId').val() == '' ? '0' :  $('#agenciaEmpresaId').val(), 
	        		  org_nome : $('#agenciaOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#agenciaOrganizacao').val('');
         	            $('#agenciaOrganizacaoId').val('');
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
          	 $('#agenciaOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#agenciaOrganizacao').val(ui.item.label);
             $('#agenciaOrganizacaoId').val(ui.item.value);
             return false;
         }
    });
	
	$('#agenciaBanco').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/banco/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#agenciaEmpresaId').val() == '' ? '0' :  $('#agenciaEmpresaId').val(), 
	        		  organizacao_id: $('#agenciaOrganizacaoId').val() == '' ? '0' :  $('#agenciaOrganizacaoId').val(),
	        		  nome : $('#agenciaBanco').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#agenciaBanco').val('');
         	           $('#agenciaBancoId').val('');
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
          	 $('#agenciaBanco').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#agenciaBanco').val(ui.item.label);
             $('#agenciaBancoId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/agencia/cadastro" />';
	});

	$('#btnNovo').click(function() {
		document.agenciaForm.reset();
	});

	$("#agenciaIsActive").change(function(e){
		if(document.agenciaForm.agenciaIsActive.checked==true){
			document.agenciaForm.agenciaIsActive.value=true;
		}else{
			document.agenciaForm.agenciaIsActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.agenciaForm.reset();
	}
}
</script>

<div id="content-header">
	<h1>Cadastro Agência</h1>
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
				<li class="" id="agencia-li"><a href="#agencia-div" data-toggle="tab" id="agencia-li-a">Cadastro de Agência</a></li>				
			</ul>
			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="agencia-div">				

					<form id="agenciaForm" name="agenciaForm" action="<c:url value="/agencia/salva"/>" method="POST">

						<div class="row-fluid">
							<div class="span3">
								<label for="agenciaEmpresa">Empresa</label>
	      						<input class="span12" id="agenciaEmpresa" name="agencia.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" required onChange="limpaForm();" readonly="readonly">
	      						<input class="span1" id="agenciaEmpresaId" name="agencia.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden">			    					
							</div>
							<div class="span3">
								<label for="agenciaOrganizacao">Organização</label>										
	      						<input class="span12" id="agenciaOrganizacao" name="agencia.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" required onChange="limpaForm();" readonly="readonly">
	      						<input class="span1" id="agenciaOrganizacaoId" name="agencia.organizacao.organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" type="hidden">
	    					</div>
						</div>

						<div class="row-fluid">
							<div class="span2">	
								<label for="agenciaBanco">Banco</label>						
	      						<input class="span12" id="agenciaBanco" name="agencia.banco.nome" value="${agencia.banco.nome }" type="text" required onChange="limpaForm();">
	      						<input class="span1" id="agenciaBancoId" name="agencia.banco.banco_id" value="${agencia.banco.banco_id }" type="hidden">	    					
							</div>
							<div class="span2">	
								<label for="agenciaLocalidade">Localidade</label>						
	      						<input class="span12" id="agenciaLocalidade" name="agencia.localidade.nome" value="${agencia.localidade.nome }" type="text" required onChange="limpaForm();">
	      						<input class="span1" id="agenciaLocalidadeId" name="agencia.localidade.localidade_id" value="${agencia.localidade.localidade_id }" type="hidden">	    					
							</div>
							<div class="span3">
								<label for="agenciaNome">Nome</label>
								<input class="span12" type="text" id="agenciaNome" name="agencia.nome" placeholder="Nome" value="${agencia.nome }" required>
							</div>
							<div class="span3">
								<label  for="agenciaDescricao">Descrição</label>
								<input class="span12" type="text" id="agenciaDescricao" name="agencia.descricao" value="${agencia.descricao }" placeholder="Descrição" required>							
							</div>
							<div class="span1">
								<label class="control-label" for="agenciaIsActive">Ativo</label>
								<input type="checkbox" id="agenciaIsActive" name="agencia.isActive" checked="checked" value="1" >
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
