<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){
	
	$("#valor").mask("9.99999999");
	$("#percentualMeta").mask("9.999");

	$('#coeficiente-li-a').click(function() {
		window.location.href = '<c:url value="/coeficiente/cadastro" />';
	});
	
	$('#coeficienteEmpresa').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/empresa/busca.json' />",
	          dataType: "json",
	          data : {n: request.term},
              success : function(data) {  

           		  if (!data || data.length == 0) {
           	            $('#coeficienteEmpresa').val('');
						$('#coeficienteEmpresaId').val('');
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
        	 $('#coeficienteEmpresa').val(ui.item.label);
             return false;
         } ,
         select: function( event, ui ) {

        	 $('#coeficienteEmpresa').val(ui.item.label);
             $('#coeficienteEmpresaId').val(ui.item.value);

             return false;

         }
    });

	$('#coeficienteOrganizacao').autocomplete({
		source: function( request, response ) {
	        $.ajax({
	          url: "<c:url value='/organizacao/busca.json' />",
	          dataType: "json",
	          data : {empresa_id: $('#coeficienteEmpresaId').val() == '' ? '0' :  $('#coeficienteEmpresaId').val(), org_nome : $('#coeficienteOrganizacao').val()},
              success : function(data) {  

            	  if (!data || data.length == 0) {
         	            $('#coeficienteOrganizacao').val('');
         	            $('#coeficienteOrganizacaoId').val('');
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
          	 $('#coeficienteOrganizacao').val(ui.item.label);
               return false;
           } ,
         select: function( event, ui ) {
             $('#coeficienteOrganizacao').val(ui.item.label);
             $('#coeficienteOrganizacaoId').val(ui.item.value);
             return false;
         }
    });

	$("#banco").change(function() {   

		var bancoId = $("#banco").val();

		$("#lista").load('<c:url value="/coeficiente/listaporbancos" />',   
	        {'bancoId': bancoId}, function() {  

        	$("#tabela").load('<c:url value="/tabela/tabelas" />',   
	    		        {'bancoId': bancoId});

	     });

	});
	
	$("#tabela").change(function() {   

		var tabelaId = $("#tabela").val();  
		var bancoId = $("#banco").val();

		$("#lista").load('<c:url value="/coeficiente/listaportabelas" />',   
		        {'bancoId': bancoId, 'tabelaId' : tabelaId});
	});
	
	$('#coeficienteForm').submit(function() {
		$.ajax({
			data: $(this).serialize()
			, type: $(this).attr('method')
			, url: $(this).attr('action')
			, success: function(response) {
				$('#lista').html(response);
			}
		});
	return false;
	
	});

	$('#btnNovo').click(function() {
		document.perfilForm.reset();
	});

	$("#coeficienteIsActive").change(function(e){
		$(this).val( $("#coeficienteIsActive:checked").length > 0 ? true : false);
	});

});

function exclui(linha, id) {
	if (window.confirm("Deseja realmente excluir o coeficiente selecionado?"))
		$.post('<c:url value='/coeficiente/exclui' />'
		, {'coeficiente.coeficiente_id' : id}
		, function(resposta) { excluiLinha(linha, resposta); });

	return false;
}

function excluiLinha(linha, resposta) {
	
	var objTR = linha.parentNode.parentNode;
	var objTable = objTR.parentNode;

	objTable.deleteRow(objTR.rowIndex - 1);

	return false;
}

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.coeficienteForm.reset();
	}
}
</script>

<div id="content-header">
		<h1>Cadastro Coeficiente</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
		<a href="#" class="current">Coeficiente</a>
	</div>

<div class="container-fluid">

		<div class="row-fluid">

			<div class="span12">

				<div class="tab-pane fade active in" id="coeficiente-div" >

					<div class="row25MarginTop">
						<form id="coeficienteForm" name="coeficienteForm" action="<c:url value="/coeficiente/adiciona"/>" method="POST">

							<div class="row-fluid">
								<div class="span2">
									<label for="coeficienteEmpresa">Empresa</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span10" id="coeficienteEmpresa" name="coeficiente.empresa.nome" type="text" required onChange="limpaForm();">
			      						<input class="span10" id="coeficienteEmpresaId" name="coeficiente.empresa.empresa_id" type="hidden">
			    					</div>
								</div>
								<div class="span2">
									<label for="coeficienteOrganizacao">Organização</label>
									<div class="input-prepend">
										<span class="add-on"><i class="icon-plus-sign"></i></span>
			      						<input class="span10" id="coeficienteOrganizacao" name="coeficiente.organizacao.nome" type="text" required onChange="limpaForm();">
			      						<input class="span10" id="coeficienteOrganizacaoId" name="coeficiente.organizacao.organizacao_id" type="hidden">
			    					</div>
								</div>
								<div class="span2">
									<label for="banco">Banco</label>
									<select id="banco" name="banco" class="span12">
										<option value="">Selecione</option>
										<c:forEach var="banco" items="${bancos }">
										 	<option value="${banco.banco_id }" >${banco.nome }</option>
										</c:forEach>
									</select>
								</div>
								<div class="span2">
									<label for="tabela">Tabela</label>
									<select id="tabela" name="coeficiente.tabela.tabela_id" class="span12">
										<option value="">Selecione um banco...</option>
									</select>
								</div>
								<div class="span2">
									<label for="valor">Valor</label>
									<input class="span10" type="text" id="valor" name="coeficiente.valor" placeholder="Valor" required>
								</div>
								<div class="span2">
									<label for="percentualMeta">Percentual Meta</label>
									<input class="span10" type="text" id="percentualMeta" name="coeficiente.percentualMeta" placeholder="Percentual" required>
								</div>
							</div>

						 	<div class="btn-group">
								<button type="submit" class="btn btn-primary" id="btnSalvar">Salvar</button>
							</div>
							<div class="btn-group">
								<button type="button" class="btn btn-primary" id="btnNovo" >Novo</button>
							</div>

						</form>						
					</div>
					
					<br/>
					
					<table class="table table-striped table-bordered" id="lista">
					<thead>
						<tr>
							<th>Empresa</th>
							<th>Organização</th>
							<th>Banco</th>
							<th>Tabela</th>
							<th>Valor</th>
							<th>Percentual</th>
							<th>Inclusão</th>
							<th></th>
						</tr>
					</thead>
					<tbody>	
						<c:forEach items="${coeficientes}" var="coeficiente">
							<tr>
								<td>${coeficiente.empresa.nome }</td>
								<td>${coeficiente.organizacao.nome }</td>
								<td>${coeficiente.tabela.banco.nome }</td>
								<td>${coeficiente.tabela.nome }</td>
								<td>${coeficiente.valor }</td>
								<td>${coeficiente.percentualMeta }</td>
								<td><fmt:formatDate pattern="dd/MM/yyyy"  type="time" value="${coeficiente.created.time }" /></td>
								<td style="text-align:center;"><a href="#" onClick="return exclui(this,'${coeficiente.coeficiente_id}');">X</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
					
				</div>
	
			</div>
		</div>
</div>


<%@ include file="/footer.jspf"%>
