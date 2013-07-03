<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){
	
	$("#valor").mask("9.99999999");
	$("#percentualMeta").mask("9.999");

	$('#coeficiente-li-a').click(function() {
		window.location.href = '<c:url value="/coeficiente/cadastro" />';
	});
	
	$('.data-table').dataTable({
		"bJQueryUI": true,
		"sPaginationType": "full_numbers",
		"sDom": '<""l>t<"F"fp>'
	});

	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();

	$("span.icon input:checkbox, th input:checkbox").click(function() {
		var checkedStatus = this.checked;
		var checkbox = $(this).parents('.widget-box').find('tr td:first-child input:checkbox');		
		checkbox.each(function() {
			this.checked = checkedStatus;
			if (checkedStatus == this.checked) {
				$(this).closest('.checker > span').removeClass('checked');
			}
			if (this.checked) {
				$(this).closest('.checker > span').addClass('checked');
			}
		});
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
		
		$("input", this).attr("readonly", true);
		$("input[type='submit'],input[type='button']", this).attr("disabled", true);
		
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
		document.coeficienteForm.reset();
	});

	$("#coeficienteIsActive").change(function(e){
		if(document.coeficienteForm.coeficienteIsActive.checked==true){
			document.coeficienteForm.coeficienteIsActive.value=true;
		}else{
			document.coeficienteForm.coeficienteIsActive.value=false;
		}
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
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
		<a href="#" class="current">Coeficiente</a>
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
				<li class="active" id="produto-li"><a href="#produto-div" data-toggle="tab" id="produto-li-a">Produtos</a></li>
			</ul>
			
			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="produto-div" >					
					
					<form id="coeficienteForm" name="coeficienteForm" action="<c:url value="/coeficiente/adiciona"/>" method="POST">
			
							<div class="row-fluid">
								<div class="span2">
									<label for="coeficienteEmpresa">Empresa</label>	
									<input id="coeficienteEmpresa" name="coeficiente.empresa.nome" type="text" value="${usuarioInfo.empresa.nome }" class="input-medium">
									<input id="coeficienteEmpresaId" name="coeficiente.empresa.empresa_id" type="hidden" value="${usuarioInfo.empresa.empresa_id }">
								</div>
								<div class="span2">
									<label for="coeficienteOrganizacao">Organização</label>	
									<input id="coeficienteOrganizacao" name="coeficiente.organizacao.nome" type="text" value="${usuarioInfo.organizacao.nome }" class="input-large">
									<input id="coeficienteOrganizacaoId" name="coeficiente.organizacao.organizacao_id" type="hidden" value="${usuarioInfo.organizacao.organizacao_id }">
								</div>
							</div>
							<div class="row-fluid">	
								<div class="span2">
									<label for="banco">Banco</label>
									<select id="banco" name="coeficiente.banco.banco_id" class="span12">
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

			</div>
		</div>
	</div>
</div>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<div class="widget-box">
				<div class="widget-title">
					<span class="icon"><i class="icon-signal"></i> </span>
					<h5>Coeficientes</h5>
				</div>
				<div id="lista" class="widget-content">
					<c:if test="${not empty coeficientes}">
						<table class="table table-bordered table-striped table-hover data-table" >
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
										<td>${coeficiente.banco.nome }</td>
										<td>${coeficiente.tabela.nome }</td>
										<td>${coeficiente.valor }</td>
										<td>${coeficiente.percentualMeta }</td>
										<td><fmt:formatDate pattern="dd/MM/yyyy"  type="time" value="${coeficiente.created.time }" /></td>
										<td style="text-align:center;"><a href="#" onClick="return exclui(this,'${coeficiente.coeficiente_id}');">X</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>