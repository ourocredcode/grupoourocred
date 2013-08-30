<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#procedimentodetalhe-li-a').click(function() {
		window.location.href = '<c:url value="/procedimentodetalhe/cadastro" />';
	});

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/procedimentodetalhe/cadastro" />';
	});
	
	$('#btnNovo').click(function() {
		limpaForm();
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

	$("#isActive").change(function(e){
		if(document.procedimentoDetalheForm.isActive.checked==true){
			document.procedimentoDetalheForm.isActive.value=true;
		}else{
			document.procedimentoDetalheForm.isActive.value=false;
		}
	});

});

function limpaForm(){
	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.procedimentoDetalheForm.reset();
	}
}

function buscaBancos(){

	var procedimento_id = $('#procedimentoId').val();

	$("#bancoId").load('<c:url value="/procedimentodetalhe/bancos" />', {'procedimento_id' : procedimento_id});

}

</script>

	<div id="content-header">
		<h1>Procedimento Conferência</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
		<a href="#" class="current">Procedimento Conferência</a>
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
				<li class="active" id="procedimentodetalhe-li"><a href="#procedimentodetalhe-div" data-toggle="tab" id="procedimentodetalhe-li-a">Procedimento Detalhe</a></li>				
			</ul>

			<div id="myTabContent" class="tab-content">
				

				<div class="tab-pane fade active in" id="procedimentodetalhe-div">
				
					<form id="procedimentoDetalheForm" name="procedimentoDetalheForm" action="<c:url value="/procedimentodetalhe/salva"/>" method="POST">

						<div class="row-fluid">
							<div class="span2">
								<label for="empresa">Empresa</label>
	      						<input class="input-medium" id="empresa" name="procedimentoDetalhe.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" onChange="limpaForm();" readonly="readonly">
		      					<input class="span1" id="empresaId" name="procedimentoDetalhe.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden">
							</div>
							<div class="span2">
								<label for="organizacao">Organização</label>
		      					<input class="input-medium" id="organizacao" name="procedimentoDetalhe.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" onChange="limpaForm();" readonly="readonly">
		      					<input class="span1" id="organizacaoId" name="procedimentoDetalhe.organizacao.organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" type="hidden">
							</div>
							<div class="span2">
								<label for="procedimentoId">Procedimento</label>
								<select id="procedimentoId" name="procedimentoDetalhe.procedimento.procedimentoConferencia_id" class="input-medium" onchange="buscaBancos();">
									<c:forEach var="procedimento" items="${procedimentosConferencia }">
									 	<option value="${procedimento.procedimentoConferencia_id }" selected="selected">${procedimento.nome }</option>
									</c:forEach>
								</select>
							</div>
							<div class="span2">
	      						<label for="bancoId">Banco</label>
	      						<select id="bancoId" name="procedimentoDetalhe.banco.banco_id" class="input-medium" required>
	      							<option value="">Selecion um Banco...</option>
	      						</select>
							</div>
							<div class="span2">
								<label for="modeloProcedimentoId">Modelo Procedimento</label>
								<select id="modeloProcedimentoId" name="procedimentoDetalhe.modeloProcedimento.modeloProcedimento_id" class="input-medium">
									<c:forEach var="modeloProcedimento" items="${modelosProcedimento }">
									 	<option value="${modeloProcedimento.modeloProcedimento_id }" selected="selected">${modeloProcedimento.nome }</option>
									</c:forEach>
								</select>
							</div>
							<div class="span2">
								<label for="agenteId">Agente</label>
								<select id="agenteId" name="procedimentoDetalhe.agente.agente_id" class="input-medium">
									<c:forEach var="agente" items="${agentes }">
									 	<option value="${agente.agente_id }" selected="selected">${agente.nome }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="row-fluid">
						
							<div class="span2">
								<label for="acao">Ação</label>							
								<input class="input-mini" id="acao" name="procedimentoDetalhe.acao" value="${procedimentoDetalhe.acao }" type="text" placeholder="Ação" required>
							</div>
							<div class="span2">
								<label for="detalheprocedimento">Detalhe Procedimento</label>
								<input class="input-xxlarge" id="detalheprocedimento" name="procedimentoDetalhe.detalheProcedimento" placeholder="Detalhe Procedimento" type="text" required>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span1">
								<label for="isActive">Ativo</label>
								<input class="span1" id="isActive" name="procedimentoDetalhe.isActive" type="checkbox" checked="checked" value="1" >
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
					<h5>Procedimento Detalhe</h5>
				</div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty procedimentosDetalhe}">
						<table
							class="table table-bordered table-striped table-hover data-table"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>Empresa</th>
									<th>Organização</th>
									<th>Modelo Procedimento</th>
									<th>Agente</th>									
									<th>Procedimento</th>
									<th>Banco</th>
									<th>Ação</th>
									<th>Detalhe Procedimento</th>
									<th>Ativo</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="procedimentoDetalhe" items="${procedimentosDetalhe }">
									<tr>
										<td>${procedimentoDetalhe.empresa.nome }</td>
										<td>${procedimentoDetalhe.organizacao.nome }</td>
										<td>${procedimentoDetalhe.modeloProcedimento.nome }</td>
										<td>${procedimentoDetalhe.agente.nome }</td>
										<td>${procedimentoDetalhe.procedimento.nome }</td>
										<td>${procedimentoDetalhe.banco.nome }</td>
										<td>${procedimentoDetalhe.acao }</td>
										<td>${procedimentoDetalhe.detalheProcedimento }</td>
										<td>
											<label class="checkbox inline">
												<input type="checkbox" id="isActiveLine" name="procedimentoDetalhe.isActive"
												<c:if test="${procedimentoDetalhe.isActive == true }"> checked="checked"</c:if> onchange="altera(this,'isActiveLine','${procedimentoDetalhe.procedimentoDetalhe_id}');">
											</label>
										</td>
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