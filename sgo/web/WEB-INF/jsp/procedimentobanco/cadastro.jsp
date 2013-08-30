<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#procedimentobanco-li-a').click(function() {
		window.location.href = '<c:url value="/procedimentobanco/cadastro" />';
	});

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/procedimentobanco/cadastro" />';
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
		if(document.procedimentoBancoForm.isActive.checked==true){
			document.procedimentoBancoForm.isActive.value=true;
		}else{
			document.procedimentoBancoForm.isActive.value=false;
		}
	});

});

function limpaForm(){
	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.procedimentoBancoForm.reset();
	}
}

</script>

<div id="content-header">
	<h1>Procedimentos Bancos</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i>Home</a>
	<a href="#" class="current">Procedimentos Bancos</a>
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
				<li class="active" id="procedimentobanco-li"><a href="#procedimentobanco-div" data-toggle="tab" id="procedimentobanco-li-a">Procedimentos Bancos</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade" id="modeloprocedimento-div"></div>
				<div class="tab-pane fade active in" id="procedimentobanco-div">				
						
					<form id="procedimentoBancoForm" name="procedimentoBancoForm" action="<c:url value="/procedimentobanco/salva"/>" method="POST">
						
						<c:if test="${usuarioInfo.perfil.chave == 'Administrador' }">
							<div class="row-fluid">
								<div class="span3">								
									<label for="empresa">Empresa</label>							
		      						<input class="input-xlarge" id="empresa" name="procedimentoBanco.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" required readonly="readonly">
		      						<input class="span1" id="empresaId" name="procedimentoBanco.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden">	    					
								</div>
								<div class="span3">
									<label for="organizacao">Organização</label>
		      						<input class="input-xlarge" id="organizacao" name="procedimentoBanco.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" required >
		      						<input class="span1" id="organizacaoId" name="procedimentoBanco.organizacao.organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" type="hidden">
								</div>
								<div class="span2">
									<label for="procedimentoId">Procedimento</label>
									<select id="procedimentoId" name="procedimentoBanco.procedimento.procedimentoConferencia_id" class="input-medium">
										<c:forEach var="procedimento" items="${procedimentosConferencia }">
										 	<option value="${procedimento.procedimentoConferencia_id }" selected="selected">${procedimento.nome }</option>
										</c:forEach>
									</select>
								</div>
								<div class="span2">
									<label for="bancoId">Banco</label>
									<select id="bancoId" name="procedimentoBanco.banco.banco_id" class="input-medium">
										<c:forEach var="banco" items="${bancos }">
										 	<option value="${banco.banco_id }" selected="selected">${banco.nome }</option>
										</c:forEach>
									</select>
								</div>
								<div class="span1">
									<label  for="isActive">Ativo</label>
									<input type="checkbox" id="isActive" name="procedimentoBanco.isActive" checked="checked" value="1">
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
						</c:if>

					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">				
			<div class="accordion" id="collapse-group">
				<div class="accordion-group widget-box">
					<div class="accordion-heading">
						<div class="widget-title">							
							<a data-parent="#collapse-group" href="#collapseGOne" data-toggle="collapse">
								<span class="icon"><i class="icon-magnet"></i></span><h5>Procedimentos bancos</h5>								
							</a>
						</div>
					</div>
					<div class="collapse in accordion-body" id="collapseGOne" >
						<div class="widget-content">
							<table class="table table-striped table-bordered">
								<thead>
									<tr>
										<th>Nome</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${procedimentosConferencia }" var="procedimentoConferencia">
										<tr>
											<td class="label_txt">
												<a href="<c:url value="/procedimentobanco/detalhebanco/${procedimentoConferencia.procedimentoConferencia_id}"/>">${procedimentoConferencia.nome } </a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>