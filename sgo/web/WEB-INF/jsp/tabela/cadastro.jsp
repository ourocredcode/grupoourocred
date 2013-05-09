<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#tipotabela-li-a').click(function() {
		window.location.href = '<c:url value="/tipotabela/cadastro" />';
	});

	$('#tabela-li-a').click(function() {
		window.location.href = '<c:url value="/tabela/cadastro" />';
	});
	
	$('#tabelaEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#tabelaEmpresa').val('');
						$('#tabelaEmpresaId').val('');
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
        	 $('#tabelaEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#tabelaEmpresa').val(ui.item.label);
             $('#tabelaEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#tabelaOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#tabelaEmpresaId').val() == '' ? '0' :  $('#tabelaEmpresaId').val(), 
	        		  org_nome : $('#tabelaOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#tabelaOrganizacao').val('');
         	            $('#tabelaOrganizacaoId').val('');
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
          	 $('#tabelaOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#tabelaOrganizacao').val(ui.item.label);
             $('#tabelaOrganizacaoId').val(ui.item.value);
             return false;
         }
    });
	
	$('#tabelaBanco').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/banco/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#tabelaEmpresaId').val() == '' ? '0' :  $('#tabelaEmpresaId').val(), 
	        		  organizacao_id: $('#tabelaOrganizacaoId').val() == '' ? '0' :  $('#tabelaOrganizacaoId').val(),
	        		  nome : $('#tabelaBanco').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#tabelaBanco').val('');
         	           $('#tabelaBancoId').val('');
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
          	 $('#tabelaBanco').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#tabelaBanco').val(ui.item.label);
             $('#tabelaBancoId').val(ui.item.value);
             return false;
         }
    });
	
	$('#tabelaTipoTabela').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/tipotabela/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#tabelaEmpresaId').val() == '' ? '0' :  $('#tabelaEmpresaId').val(), 
	        		  organizacao_id : $('#tabelaOrganizacaoId').val() == '' ? '0' :  $('#tabelaOrganizacaoId').val(),
	        		  nome : $('#tabelaTipoTabela').val() },
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#tabelaTipoTabela').val('');
         	            $('#tabelaTipoTabelaId').val('');
         	        }

            	  response($.map(data, function(tipoTabela) {  
            		  return {
            			  label: tipoTabela.nome,
            			  value: tipoTabela.tipoTabela_id
                      };
                  }));  
               }
	        });
         },
         focus: function( event, ui ) {
          	 $('#tabelaTipoTabela').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#tabelaTipoTabela').val(ui.item.label);
             $('#tabelaTipoTabelaId').val(ui.item.value);
             return false;
         }
    });

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/tabela/cadastro" />';
	});

	$('#btnNovo').click(function() {
		document.tabelaForm.reset();
	});

	$("#tabelaIsActive").change(function(e){
		$(this).val( $("#tabelaIsActive:checked").length > 0 ? true : false);
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.tabelaForm.reset();
	}
}
</script>

<div id="content-header">
		<h1>Cadastro Tabela</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
		<a href="#" class="current">Tabela</a>
	</div>

<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">

			<ul id="myTab" class="nav nav-tabs">								
				<li class="" id="tipotabela-li"><a href="#tipotabela-div" data-toggle="tab" id="tipotabela-li-a">Tipo de Tabela</a></li>
				<li class="active" id="tabela-li"><a href="#tabela-div" data-toggle="tab" id="tabela-li-a">Tabela</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade" id="tipotabela-div"></div>

				<div class="tab-pane fade active in" id="tabela-div" >
				
						<div class="row25MarginTop">
						<form id="tabelaForm" name="tabelaForm" action="<c:url value="/tabela/salva"/>" method="POST">

							<div class="span3">
									<div class="control-group">
										<label class="control-label" for="tabelaEmpresa">Empresa</label>
										<div class="input-prepend">
											<span class="add-on"><i class="icon-plus-sign"></i></span>
				      						<input class="span10" id="tabelaEmpresa" name="tabela.empresa.nome" type="text" required onChange="limpaForm();">
				      						<input class="span10" id="tabelaEmpresaId" name="tabela.empresa.empresa_id" type="hidden">
				    					</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="tabelaOrganizacao">Organização</label>
										<div class="input-prepend">
											<span class="add-on"><i class="icon-plus-sign"></i></span>
				      						<input class="span10" id="tabelaOrganizacao" name="tabela.organizacao.nome" type="text" required onChange="limpaForm();">
				      						<input class="span10" id="tabelaOrganizacaoId" name="tabela.organizacao.organizacao_id" type="hidden">
				    					</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="tabelaBanco">Banco</label>
										<div class="input-prepend">
											<span class="add-on"><i class="icon-plus-sign"></i></span>
				      						<input class="span10" id="tabelaBanco" name="tabela.banco.nome" type="text" required onChange="limpaForm();">
				      						<input class="span10" id="tabelaBancoId" name="tabela.banco.banco_id" type="hidden">
				    					</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="tabelaNome">Nome</label>
										<div class="controls">
											<input  class="span10" type="text" id="tabelaNome" name="tabela.nome" placeholder="Nome" required>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="tabelaDescricao">Descrição</label>
										<div class="controls">
											<input  class="span10" type="text" id="tabelaDescricao" name="tabela.descricao" placeholder="Descrição" required>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="tabelaPrazo">Prazo</label>
										<div class="controls">
											<input  class="span10" type="text" id="tabelaPrazo" name="tabela.prazo" placeholder="Prazo" required>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="tabelaIsActive">Ativo</label>
										<div class="controls">
											<input type="checkbox" id="tabelaIsActive" name="tabela.isActive" checked="checked" value="${tabela.isActive }" >							
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
	
							<div class="span3">

								<div class="control-group">
									<label class="control-label" for="tabelaTipoTabela">Tipo Tabela</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span10" id="tabelaTipoTabela" name="tabela.tipoTabela.nome" type="text" required onChange="limpaForm();">
			      						<input class="span10" id="tabelaTipoTabelaId" name="tabela.tipoTabela.tipoTabela_id" type="hidden">
			    					</div>
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
