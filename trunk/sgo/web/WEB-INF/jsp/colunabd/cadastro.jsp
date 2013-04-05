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
	
	$('#colunaBdEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#colunaBdEmpresa').val('');
						$('#colunaBdEmpresaId').val('');
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
        	 $('#colunaBdEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {
        	 $('#colunaBdEmpresa').val(ui.item.label);
             $('#colunaBdEmpresaId').val(ui.item.value);
             return false;
         }
    });
	
	$('#colunaBdOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#colunaBdEmpresaId').val() == '' ? '0' :  $('#colunaBdEmpresaId').val(), org_nome : $('#colunaBdOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#colunaBdOrganizacao').val('');
         	           $('#colunaBdOrganizacaoId').val('');
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
          	 $('#colunaBdOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#colunaBdOrganizacao').val(ui.item.label);
             $('#colunaBdOrganizacaoId').val(ui.item.value);
             return false;
         }
    });
	
	$('#colunaBdTabelaBd').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/tabelabd/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#colunaBdEmpresaId').val() == '' ? '0' :  $('#colunaBdEmpresaId').val(), 
	        		  organizacao_id: $('#colunaBdOrganizacaoId').val() == '' ? '0' :  $('#colunaBdOrganizacaoId').val(),
	        		  nometabelabd : $('#colunaBdTabelaBd').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#colunaBdTabelaBd').val('');
         	           $('#colunaBdTabelaBdId').val('');
         	        }

            	  response($.map(data, function(tabelabd) {  
            		  return {
            			  label: tabelabd.nomeTabelaBd,
            			  value: tabelabd.tabelaBd_id
                      };
                  }));  
               }
	        });
         },
         focus: function( event, ui ) {
          	 $('#colunaBdTabelaBd').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#colunaBdTabelaBd').val(ui.item.label);
             $('#colunaBdTabelaBdId').val(ui.item.value);
             return false;
         }
    });
	
	$('#colunaBdElementoBd').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/elementobd/busca.json' />",
	          dataType: "json",
	          data : {nomecolunabd : $('#colunaBdElementoBd').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#colunaBdElementoBd').val('');
         	           $('#colunaBdElementoBdId').val('');
         	        }

            	  response($.map(data, function(elementobd) {  
            		  return {
            			  label: elementobd.nomeColunaBd,
            			  value: elementobd.elementoBd_id
                      };
                  }));  
               }
	        });
         },
         focus: function( event, ui ) {
          	
        	 $('#colunaBdElementoBd').val(ui.item.label);
          	$('#colunaBdNomeColunaBd').val(ui.item.label);
          	
               return false;
           } ,
         select: function( event, ui ) {
             $('#colunaBdElementoBd').val(ui.item.label);
             $('#colunaBdNomeColunaBd').val(ui.item.label);
             $('#colunaBdElementoBdId').val(ui.item.value);
             return false;
         }
    });

	$('#colunaBdTipoDadoBd').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/tipodadobd/busca.json' />",
	          dataType: "json",
	          data : {nome : $('#colunaBdTipoDadoBd').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#colunaBdTipoDadoBd').val('');
         	           $('#colunaBdTipoDadoBdId').val('');
         	        }

            	  response($.map(data, function(tipodadobd) {  
            		  return {
            			  label: tipodadobd.nome,
            			  value: tipodadobd.tipoDadoBd_id
                      };
                  }));  
               }
	        });
         },
         focus: function( event, ui ) {

        	 $('#colunaBdTipoDadoBd').val(ui.item.label);

               return false;
           } ,
         select: function( event, ui ) {
             $('#colunaBdTipoDadoBd').val(ui.item.label);
             $('#colunaBdTipoDadoBdId').val(ui.item.value);
             return false;
         }
    });
	
	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/colunabd/cadastro" />';
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
				<li class="" id="janela-li"><a href="#janela-div" data-toggle="tab" id="janela-li-a">Janela</a></li>
				<li class="" id="perfiljanelaacesso-li"><a href="#perfiljanelaacesso-div" data-toggle="tab" id="perfiljanelaacesso-li-a">Janela Perfil</a></li>
				<li class="" id="formulariosjanela-li"><a href="#formulariosjanela-div" data-toggle="tab" id="formulariosjanela-li-a">Formulários Janela</a></li>
				<li class="" id="campoformulario-li"><a href="#campoformulario-div" data-toggle="tab" id="campoformulario-li-a">Campo Formulário</a></li>
				<li class="" id="tabelabd-li"><a href="#tabelabd-div" data-toggle="tab" id="tabelabd-li-a">Tabela BD</a></li>
				<li class="active" id="colunabd-li"><a href="#colunabd-div" data-toggle="tab" id="colunabd-li-a">Coluna BD</a></li>	
				<li class="" id="elementobd-li"><a href="#elementobd-div" data-toggle="tab" id="elementobd-li-a">Elemento BD</a></li>
				<li class="" id="tipodadobd-li"><a href="#tipodadobd-div" data-toggle="tab" id="tipodadobd-li-a">TipoDado BD</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">
				
				<div class="tab-pane fade" id="perfil-div">					

				</div>
				
				<div class="tab-pane fade" id="perfilorgacesso-div">					

				</div>
				
				<div class="tab-pane fade" id="janela-div">
					
				</div>
				
				<div class="tab-pane fade" id="perfiljanelaacesso-div">
					
				</div>
				
				<div class="tab-pane fade" id="formulariosjanela-div">
					
				</div>
				
				<div class="tab-pane fade" id="campoformulario-div">
					
				</div>

				<div class="tab-pane fade " id="tabelabd-div">

				</div>
				<div class="tab-pane fade active in" id="colunabd-div">
				
					<form id="colunaBdForm" name="colunaBdForm" action="<c:url value="/colunabd/salva"/>" method="POST">
						<div class="control-group">
							<label class="control-label" for="colunaBdEmpresa">Empresa</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="colunaBdEmpresa" name="colunaBd.empresa.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="colunaBdEmpresaId" name="colunaBd.empresa.empresa_id" type="hidden">
	    					</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="colunaBdOrganizacao">Organização</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="colunaBdOrganizacao" name="colunaBd.organizacao.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="colunaBdOrganizacaoId" name="colunaBd.organizacao.organizacao_id" type="hidden">
	    					</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="colunaBdTabelaBd">Tabela BD</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="colunaBdTabelaBd" name="colunaBd.tabelaBd.nomeTabelaBd" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="colunaBdTabelaBdId" name="colunaBd.tabelaBd.tabelaBd_id" type="hidden">
	    					</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="colunaBdElementoBd">Elemento BD</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="colunaBdElementoBd" name="colunaBd.elementoBd.nomecolunabd" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="colunaBdElementoBdId" name="colunaBd.elementoBd.elementoBd_id" type="hidden">
	    					</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="colunaBdTipoDadoBd">Tipo Dado BD</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span10" id="colunaBdTipoDadoBd" name="colunaBd.tipodadoBd.nome" type="text" required onChange="limpaForm();">
	      						<input class="span10" id="colunaBdTipoDadoBdId" name="colunaBd.tipoDadoBd.tipoDadoBd_id" type="hidden">
	    					</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="colunaBdNomeColunaBd">Nome Coluna BD</label>
							<div class="controls">
								<input type="text" id="colunaBdNomeColunaBd" name="colunaBd.nomeColunaBd" placeholder="Nome da coluna BD" required readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="colunaBdNome">Nome</label>
							<div class="controls">
								<input type="text" id="colunaBdNome" name="colunaBd.nome" placeholder="Nome" required>
							</div>
						</div>						
						
						<div class="btn-toolbar">
							<div class="btn-group">
								<button type="submit" class="btn btn-primary">Salvar</button>
							</div>	
							<div class="btn-group">
								<button type="button" class="btn btn-primary" id="btnSair" >Novo</button>
							</div>
						</div>

					</form>

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
