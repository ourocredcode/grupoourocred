<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){
	
	$('#perfil-li-a').click(function() {
		window.location.href = '<c:url value="/perfil/cadastro" />';
	});

	$('#perfilorganizacao-li-a').click(function() {
		window.location.href = '<c:url value="/perfil/cadastro" />';
	});
	
	$('#janela-li-a').click(function() {
		window.location.href = '<c:url value="/janela/cadastro" />';
	});
	
	$('#perfiljanelaacesso-li-a').click(function() {
		window.location.href = '<c:url value="/perfiljanelaacesso/cadastro" />';
	});
	
	$('#formulariosjanela-li-a').click(function() {
		window.location.href = '<c:url value="/formulariosjanela/cadastro" />';
	});
	
	$('#campoformulario-li-a').click(function() {
		window.location.href = '<c:url value="/campoformulario/cadastro" />';
	});

	$('#tabelabd-li-a').click(function() {
		window.location.href = '<c:url value="/tabelabd/cadastro" />';
	});
	$('#colunabd-li-a').click(function() {
		window.location.href = '<c:url value="/colunabd/cadastro" />';
	});
	$('#elementobd-li-a').click(function() {
		window.location.href = '<c:url value="/elementobd/cadastro" />';
	});
	$('#tipodadobd-li-a').click(function() {
		window.location.href = '<c:url value="/tipodadobd/cadastro" />';
	});

	$('#tabelaBdEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#tabelaBdEmpresa').val('');
						$('#tabelaBdEmpresaId').val('');
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
        	 $('#tabelaBdEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {
        	 $('#tabelaBdEmpresa').val(ui.item.label);
             $('#tabelaBdEmpresaId').val(ui.item.value);
             return false;
         }
    });
	
	$('#tabelaBdOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#tabelaBdEmpresaId').val() == '' ? '0' :  $('#tabelaBdEmpresaId').val(), org_nome : $('#tabelaBdOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#tabelaBdOrganizacao').val('');
         	           $('#tabelaBdOrganizacaoId').val('');
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
          	 $('#tabelaBdOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#tabelaBdOrganizacao').val(ui.item.label);
             $('#tabelaBdOrganizacaoId').val(ui.item.value);
             return false;
         }
    });
});

function limpaForm(){
	document.tabelaBdForm.reset();
}


</script>

<div class="span9">

	<section id="tabs">
		<div class="bs-docs-example">

			<ul id="myTab" class="nav nav-tabs">
				<li class="" id="perfil-li"><a href="#perfil-div" data-toggle="tab" id="perfil-li-a">Perfil</a></li>
				<li class="" id="perfilorganizacao-li"><a href="#perfilperfilorganizacao-div" data-toggle="tab" id="perfilorganizacao-li-a">Perfil Organização</a></li>
				<li class="" id="janela-li"><a href="#janela-div" data-toggle="tab" id="janela-li-a">Janela</a></li>
				<li class="" id="perfiljanelaacesso-li"><a href="#perfiljanelaacesso-div" data-toggle="tab" id="perfiljanelaacesso-li-a">Janela Perfil</a></li>
				<li class="" id="formulariosjanela-li"><a href="#formulariosjanela-div" data-toggle="tab" id="formulariosjanela-li-a">Formulários Janela</a></li>
				<li class="" id="campoformulario-li"><a href="#campoformulario-div" data-toggle="tab" id="campoformulario-li-a">Campo Formulário</a></li>
				<li class="active" id="tabelabd-li"><a href="#" data-toggle="tab" id="tabelabd-li-a">Tabela BD</a></li>
				<li class="" id="colunabd-li"><a href="#colunabd-div" data-toggle="tab" id="colunabd-li-a">Coluna BD</a></li>	
				<li class="" id="elementobd-li"><a href="#elementobd-div" data-toggle="tab" id="elementobd-li-a">Elemento BD</a></li>
				<li class="" id="tipodadobd-li"><a href="#tipodadobd-div" data-toggle="tab" id="tipodadobd-li-a">TipoDado BD</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade" id="janela-div">
					
				</div>
				
				<div class="tab-pane fade" id="perfiljanelaacesso-div">
					
				</div>
				
				<div class="tab-pane fade" id="formulariosjanela-div">
					
				</div>
				
				<div class="tab-pane fade" id="campoformulario-div">
					
				</div>

				<div class="tab-pane fade active in" id="tabelabd-div">
					
					<form id="tabelaBdForm" name="tabelaBdForm" action="<c:url value="/tabelabd/salva"/>" method="POST">
						<div class="control-group">
							<label class="control-label" for="tabelaBdEmpresa">Empresa</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="tabelaBdEmpresa" name="tabelaBd.empresa.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="tabelaBdEmpresaId" name="tabelaBd.empresa.empresa_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="tabelaBdOrganizacao">Organização</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="tabelaBdOrganizacao" name="tabelaBd.organizacao.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="tabelaBdOrganizacaoId" name="tabelaBd.organizacao.organizacao_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="tabelaBdNomeTabelaBd">Nome Tabela BD</label>
							<div class="controls">
								<input type="text" id="tabelaBdNomeTabelaBd" name="tabelaBd.nomeTabelaBd" placeholder="Nome da tabela BD" required>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="tabelaBd.nome">Nome</label>
							<div class="controls">
								<input type="text" id="tabelaBd.nome" name="tabelaBd.nome" placeholder="Nome" required>
							</div>
						</div>
						
						 <div class="btn-toolbar">
								<div class="btn-group">
									<button type="submit" class="btn btn-primary">Salvar</button>
								</div>	
							</div>
					</form>
					
				</div>
				<div class="tab-pane fade" id="colunabd-div">

				</div>
				<div class="tab-pane fade" id="elementobd-div">

				</div>
				<div class="tab-pane fade" id="tipodadobd-div">

				</div>
			</div>
		</div>
	</section>
</div>

<%@ include file="/footer.jspf"%>
