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

		$('#tipoDadoBdEmpresa').autocomplete({
			source: function( request, response ) {
		        $.ajax({
		          url: "<c:url value='/empresa/busca.json' />",
		          dataType: "json",
		          data : {n: request.term},
	              success : function(data) {  
	
	           		  if (!data || data.length == 0) {
	           	            $('#tipoDadoBdEmpresa').val('');
							$('#tipoDadoBdEmpresaId').val('');
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
	        	 $('#tipoDadoBdEmpresa').val(ui.item.label);
	             return false;
	         } ,
	         select: function( event, ui ) {
	
	        	 $('#tipoDadoBdEmpresa').val(ui.item.label);
	             $('#tipoDadoBdEmpresaId').val(ui.item.value);
	
	             return false;
	
	         }
	    });

		$('#tipoDadoBdOrganizacao').autocomplete({
			source: function( request, response ) {
		        $.ajax({
		          url: "<c:url value='/organizacao/busca.json' />",
		          dataType: "json",
		          data : {empresa_id: $('#tipoDadoBdEmpresaId').val() == '' ? '0' :  $('#tipoDadoBdEmpresaId').val(), org_nome : $('#tipoDadoBdOrganizacao').val()},
	              success : function(data) {  
	
	            	  if (!data || data.length == 0) {
	         	            $('#tipoDadoBdOrganizacao').val('');
	         	            $('#tipoDadoBdOrganizacaoId').val('');
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
	          	 $('#tipoDadoBdOrganizacao').val(ui.item.label);
	               return false;
	           } ,
	         select: function( event, ui ) {
	             $('#tipoDadoBdOrganizacao').val(ui.item.label);
	             $('#tipoDadoBdOrganizacaoId').val(ui.item.value);
	             return false;
	         }
	    });
	
		$('#btnSair').click(function() {
			window.location.href = '<c:url value="/tipodadobd/cadastro" />';
		});

	
		$("#tipoDadoBdEmpresa").change(function() {
			var empresa_id = $("#tipoDadoBdEmpresaId").val();
			var organizacao_id = $("#tipoDadoBdOrganizacaoId").val();
			var nome = $("#tipoDadoBdNome").val();
			
			$('#lista').load('<c:url value="/tipodadobd/lista" />', {
				'empresa_id' : empresa_id,
				'organizacao_id' : organizacao_id,
				'nome' : nome
			});
		});

		$("#tipoDadoBdOrganizacao").change(function() {
			var empresa_id = $("#tipoDadoBdEmpresaId").val();
			var organizacao_id = $("#tipoDadoBdOrganizacaoId").val();
			var nome = $("#tipoDadoBdNome").val();
			$('#lista').load('<c:url value="/tipodadobd/lista" />', {
				'empresa_id' : empresa_id,
				'organizacao_id' : organizacao_id,
				'nome' : nome
			});
		});
		
		$("#tipoDadoBdNome").change(function() {
			var empresa_id = $("#tipoDadoBdEmpresaId").val();
			var organizacao_id = $("#tipoDadoBdOrganizacaoId").val();
			var nome = $("#tipoDadoBdNome").val();
			$('#lista').load('<c:url value="/tipodadobd/lista" />', {
				'empresa_id' : empresa_id,
				'organizacao_id' : organizacao_id,
				'nome' : nome
			});
		});

	});

	function limpaForm() {

		if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
			document.colunaBdForm.reset();
		}

	}
</script>

<div class="container-fluid" id="tipodadobd-div">
	<section id="tabs">
		<ul id="myTab" class="nav nav-tabs">
			<li class="" id="perfil-li"><a href="#perfil-div" data-toggle="tab" id="perfil-li-a">Perfil</a></li>
			<li class="" id="perfilorgacesso-li"><a href="#perfilorgacesso-div" data-toggle="tab" id="perfilorgacesso-li-a">Perfil Organização</a></li>
			<li class="" id="janela-li"><a href="#janela-div" data-toggle="tab" id="janela-li-a">Janela</a></li>
			<li class="" id="perfiljanelaacesso-li"><a href="#perfiljanelaacesso-div" data-toggle="tab" id="perfiljanelaacesso-li-a">Janela Perfil</a></li>
			<li class="" id="formulariosjanela-li"><a href="#formulariosjanela-div" data-toggle="tab" id="formulariosjanela-li-a">Formulários Janela</a></li>
			<li class="" id="campoformulario-li"><a href="#campoformulario-div" data-toggle="tab" id="campoformulario-li-a">Campo Formulário</a></li>
			<li class="" id="tabelabd-li"><a href="#tabelabd-div" data-toggle="tab" id="tabelabd-li-a">Tabela BD</a></li>
			<li class="" id="colunabd-li"><a href="#colunabd-div" data-toggle="tab" id="colunabd-li-a">Coluna BD</a></li>	
			<li class="" id="elementobd-li"><a href="#elementobd-div" data-toggle="tab" id="elementobd-li-a">Elemento BD</a></li>
			<li class="active" id="tipodadobd-li"><a href="#tipodadobd-div" data-toggle="tab" id="tipodadobd-li-a">TipoDado BD</a></li>
		</ul>
	
		<div id="myTabContent" class="tab-content">
		
			<div class="tab-pane fade" id="perfil-div"></div>
			<div class="tab-pane fade" id="perfilorgacesso-div"></div>		
			<div class="tab-pane fade" id="janela-div"></div>		
			<div class="tab-pane fade" id="perfiljanelaacesso-div"></div>
			<div class="tab-pane fade" id="formulariosjanela-div"></div>			
			<div class="tab-pane fade" id="campoformulario-div"></div>	
			<div class="tab-pane fade " id="tabelabd-div"></div>
			<div class="tab-pane fade " id="colunabd-div"></div>
			<div class="tab-pane fade" id="elementobd-div"></div>
			
				<div class="tab-pane fade active in" id="tipodadobd-div">

					<form id="tipoDadoBdForm" name="tipoDadoBdForm" action="<c:url value="/tipodadobd/salva"/>" method="POST">												
						<div class="row-fluid">
							<div class="span2">
								<label class="control-label" for="tipoDadoBdEmpresa">Empresa</label>
		      					<input id="tipoDadoBdEmpresa" name="tipoDadoBd.empresa.nome" type="text" required onChange="limpaForm();">
		      					<input id="tipoDadoBdEmpresaId" name="tipoDadoBd.empresa.empresa_id" type="hidden">
							</div>
							<div class="span2">
								<label class="control-label" for="tipoDadoBdOrganizacao">Organização</label>							
	      						<input id="tipoDadoBdOrganizacao" name="tipoDadoBd.organizacao.nome" type="text" required onChange="limpaForm();">
	      						<input id="tipoDadoBdOrganizacaoId" name="tipoDadoBd.organizacao.organizacao_id" type="hidden">
							</div>
							<div class="span2">						
								<label class="control-label" for="tipoDadoBdNome">Nome Tipo Dado BD</label>							
								<input type="text" id="tipoDadoBdNome" name="tipoDadoBd.nome" placeholder="Nome do tipo de dado BD" required>							
							</div>						
							<div class="span2">						
								<label class="control-label" for="tipoDadoBdChave">Chave Tipo Dado BD</label>
								<input type="text" id="tipoDadoBdChave" name="tipoDadoBd.chave" placeholder="Chave do tipo de dado BD" required>
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
				<table class="table table-striped table-bordered" id="lista">
					<thead>
						<tr>
							<th>Empresa</th>
							<th>Organização</th>
							<th>Nome</th>
							<th>Chave</th>
						</tr>
					</thead>
					<tbody>	
						<c:forEach items="${tiposDadosBd}" var="tipoDado">
							<tr>
								<td>${tipoDado.empresa.nome }</td>
								<td>${tipoDado.organizacao.nome }</td>
								<td>${tipoDado.nome }</td>
								<td>${tipoDado.chave }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>			
		</section>
	</div>

<%@ include file="/footer.jspf"%>
