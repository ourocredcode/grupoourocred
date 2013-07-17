<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#operacao-li-a').click(function() {
		window.location.href = '<c:url value="/operacao/cadastro" />';
	});

	$('#sala-li-a').click(function() {
		window.location.href = '<c:url value="/sala/cadastro" />';
	});

	$('#operacaosala-li-a').click(function() {
		window.location.href = '<c:url value="/operacaosala/cadastro" />';
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

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/operacao/cadastro" />';
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});

	$("#isActive").change(function(e){
		if(document.operacaoForm.isActive.checked==true){
			document.operacaoForm.isActive.value=true;
		}else{
			document.operacaoForm.isActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.operacaoForm.reset();
	}
}

function altera(linha, id) {

	var valor = linha.checked == true ? true : false ;

	if (window.confirm("Deseja alterar a Operação selecionado?"))
		$.post('<c:url value="/operacao/altera" />', {
			'operacao.operacao_id' : id, 'operacao.isActive' : valor
		});

	return false;
}

$(function() {

// Invoke the plugin
	$('input, textarea').placeholder();
	// That's it, really.
	// Now display a message if the browser supports placeholder natively
	var html;
	if ($.fn.placeholder.input && $.fn.placeholder.textarea) {
		html = '<strong>Your current browser natively supports <code>placeholder</code> for <code>input</code> and <code>textarea</code> elements.</strong> The plugin wont run in this case, since its not needed. If you want to test the plugin, use an older browser ;)';
	} else if ($.fn.placeholder.input) {
		html = '<strong>Your current browser natively supports <code>placeholder</code> for <code>input</code> elements, but not for <code>textarea</code> elements.</strong> The plugin will only do its thang on the <code>textarea</code>s.';
	}
	if (html) {
		$('<p class="note">' + html + '</p>').insertAfter('form');
	}
});
</script>

<div id="content-header">
	<h1>Cadastro Operação</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
	<a href="#" class="current">Operação</a>
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
				<li class="active" id="operacao-li"><a href="#operacao-div" data-toggle="tab" id="operacao-li-a">Operação</a></li>
				<li class="" id="sala-li"><a href="#sala-div" data-toggle="tab" id="sala-li-a">Sala</a></li>
				<li class="" id="operacaosala-li"><a href="#operacaosala-div" data-toggle="tab" id="operacaosala-li-a">Operação Sala</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="operacao-div">

					<form id="operacaoForm" name="operacaoForm" action="<c:url value="/operacao/salva"/>" method="POST">
						<div class="row-fluid">
							<div class="span3">
								<label for="empresa">Empresa</label>
	      						<input class="input-xlarge" id="empresa" name="operacao.empresa.nome" type="text" value="${usuarioInfo.empresa.nome }" readonly="readonly">
	      						<input class="span1" id="empresaId" name="operacao.empresa.empresa_id" type="hidden" value="${usuarioInfo.empresa.empresa_id }" >
							</div>
							<div class="span3">
								<label for="organizacao">Organização</label>
	      						<input class="input-xlarge" id="organizacao" name="operacao.organizacao.nome" type="text" value="${usuarioInfo.organizacao.nome }" readonly="readonly">
	      						<input class="span1" id="organizacaoId" name="operacao.organizacao.organizacao_id" type="hidden" value="${usuarioInfo.organizacao.organizacao_id }">
	    					</div>
    					</div>
    					<div class="row-fluid">    					
							<div class="span3">
								<label for="nome">Nome</label>
								<input class="input-xlarge" id="nome" name="operacao.nome" placeholder="Nome" type="text" required>
							</div>
							
							<div class="span2">
								<label for="sala">Sala</label>
								<select class="input-medim" id="salaId" name="sala.sala_id" >
									<c:forEach var="sala" items="${salas }">
									 	<option value="${sala.sala_id }" selected="selected"> ${sala.nome }</option>
									</c:forEach>
								</select>
							</div>
							<div class="span3">
								<label for="isActive">Ativo</label>
								<input id="isActive" name="operacao.isActive" type="checkbox" checked="checked" value="1" >
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

				<div class="tab-pane fade" id="sala-div"></div>
				<div class="tab-pane fade" id="operacaosala-div"></div>

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
					<h5>Operação</h5>
				</div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty operacoes}">
						<table
							class="table table-bordered table-striped table-hover data-table"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>Empresa</th>
									<th>Organização</th>
									<th>Nome</th>
									<th>Ativo</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${operacoes }" var="operacao">
									<tr>
										<td>${operacao.empresa.nome }</td>
										<td>${operacao.organizacao.nome }</td>
										<td>${operacao.nome }</td>
										<td>
											<label class="checkbox inline">
												<input type="checkbox" id="isActiveLine" name="operacao.isActive"
												<c:if test="${operacao.isActive == true }"> checked="checked"</c:if> onchange="altera(this,'${operacao.operacao_id}');">
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