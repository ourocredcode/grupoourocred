<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#perfil-li-a').click(function() {
		window.location.href = '<c:url value="/perfil/cadastro" />';
	});

	$('#perfilorgacesso-li-a').click(function() {
		window.location.href = '<c:url value="/perfilorgacesso/cadastro" />';
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

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/colunabd/cadastro" />';
	});
	
	$('#janelaEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#janelaEmpresa').val('');
						$('#janelaEmpresaId').val('');
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
        	 $('#janelaEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {
        	 $('#janelaEmpresa').val(ui.item.label);
             $('#janelaEmpresaId').val(ui.item.value);
             return false;
         }
    });
	
	$('#janelaOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#janelaEmpresaId').val() == '' ? '0' :  $('#janelaEmpresaId').val(), org_nome : $('#janelaOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#janelaOrganizacao').val('');
         	           $('#janelaOrganizacaoId').val('');
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
          	 $('#janelaOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#janelaOrganizacao').val(ui.item.label);
             $('#janelaOrganizacaoId').val(ui.item.value);
             return false;
         }
    });

	$("#menuIsActive").change(function(e){
		$(this).val( $("#menuIsActive:checked").length > 0 ? true : false);
	});

});

function limpaForm(){

	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.colunaBdForm.reset();
	}

}


</script>

<div id="content-header">
		<h1>Cadastro Perfil</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
		<a href="#" class="current">Perfil</a>
	</div>

<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">

			<ul id="myTab" class="nav nav-tabs">
				<li class="" id="perfil-li"><a href="#perfil-div" data-toggle="tab" id="perfil-li-a">Perfil</a></li>
				<li class="" id="perfilorgacesso-li"><a href="#perfilorgacesso-div" data-toggle="tab" id="perfilorgacesso-li-a">Perfil Organização</a></li>
				<li class="active" id="janela-li"><a href="#janela-div" data-toggle="tab" id="janela-li-a">Janela</a></li>
				<li class="" id="perfiljanelaacesso-li"><a href="#perfiljanelaacesso-div" data-toggle="tab" id="perfiljanelaacesso-li-a">Janela Perfil</a></li>
				<li class="" id="formulariosjanela-li"><a href="#formulariosjanela-div" data-toggle="tab" id="formulariosjanela-li-a">Formulários Janela</a></li>
				<li class="" id="campoformulario-li"><a href="#campoformulario-div" data-toggle="tab" id="campoformulario-li-a">Campo Formulário</a></li>
				<li class="" id="tabelabd-li"><a href="#tabelabd-div" data-toggle="tab" id="tabelabd-li-a">Tabela BD</a></li>
				<li class="" id="colunabd-li"><a href="#colunabd-div" data-toggle="tab" id="colunabd-li-a">Coluna BD</a></li>	
				<li class="" id="elementobd-li"><a href="#elementobd-div" data-toggle="tab" id="elementobd-li-a">Elemento BD</a></li>
				<li class="" id="tipodadobd-li"><a href="#tipodadobd-div" data-toggle="tab" id="tipodadobd-li-a">TipoDado BD</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">
			
				<div class="tab-pane fade" id="perfil-div">					

				</div>
				
				<div class="tab-pane fade" id="perfilorgacesso-div">					

				</div>
			
				<div class="tab-pane fade active in" id="janela-div">
					
					<form id="janelaForm" name="janelaForm" action="<c:url value="/janela/salva"/>" method="POST">

						<div class="control-group">
							<label class="control-label" for="janelaEmpresa">Empresa</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="janelaEmpresa" name="janela.empresa.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="janelaEmpresaId" name="janela.empresa.empresa_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="janelaOrganizacao">Organização</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="janelaOrganizacao" name="janela.organizacao.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="janelaOrganizacaoId" name="janela.organizacao.organizacao_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="janelaChave">Chave</label>
							<div class="controls">
								<input type="text" id="janelaChave" name="janela.chave" placeholder="Chave" required>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="janelaNome">Nome</label>
							<div class="controls">
								<input type="text" id="janelaNome" name="janela.nome" placeholder="Nome" required>
							</div>
						</div>						
						<div class="control-group">
							<label class="control-label" for="janelaIsActive">Ativo</label>
							<div class="controls">
								<input type="checkbox" id="janelaIsActive" name="janela.isActive" value="${janela.isActive }">							
							</div>							
						</div>
						<div class="btn-toolbar">
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
				
				<div class="tab-pane fade" id="perfiljanelaacesso-div">
					
				</div>

				<div class="tab-pane fade" id="formulariosjanela-div">

				</div>

				<div class="tab-pane fade" id="campoformulario-div">

				</div>

				<div class="tab-pane fade" id="tabelabd-div">

				</div>
				<div class="tab-pane fade" id="colunabd-div">

				</div>
				<div class="tab-pane fade" id="elementobd-div">

				</div>
				<div class="tab-pane fade" id="tipodadobd-div">

				</div>
			</div>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>
