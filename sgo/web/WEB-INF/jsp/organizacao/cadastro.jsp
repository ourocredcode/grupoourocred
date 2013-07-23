<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#organizacao-li-a').click(function() {
		window.location.href = '<c:url value="/organizacao/cadastro" />';
	});

	$('#organizacaoinfo-li-a').click(function() {
		window.location.href = '<c:url value="/organizacaoinfo/cadastro" />';
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
		window.location.href = '<c:url value="/organizacao/cadastro" />';
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});

	$("#isActive").change(function(e){
		if(document.organizacaoForm.isActive.checked==true){
			document.organizacaoForm.isActive.value=true;
		}else{
			document.organizacaoForm.isActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.organizacaoForm.reset();
	}
}

function altera(linha, id) {

	var valor = linha.checked == true ? true : false ;

	if (window.confirm("Deseja alterar a Organização selecionado?"))
		$.post('<c:url value="/organizacao/altera" />', {
			'organizacao.organizacao_id' : id, 'organizacao.isActive' : valor
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
	<h1>Cadastro Organização</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
	<a href="#" class="current">Organização</a>
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
				<li class="active" id="organizacao-li"><a href="#organizacao-div" data-toggle="tab" id="organizacao-li-a">Organização</a></li>				
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="organizacao-div">

					<form id="organizacaoForm" name="organizacaoForm" action="<c:url value="/organizacao/salva"/>" method="POST">
						<div class="row-fluid">
							<div class="span1">
								<label for="empresa">Empresa</label>
	      						<input class="input-xlarge" id="empresa" name="organizacao.empresa.nome" type="text" value="${usuarioInfo.empresa.nome }" readonly="readonly">
	      						<input class="span1" id="empresaId" name="organizacao.empresa.empresa_id" type="hidden" value="${usuarioInfo.empresa.empresa_id }" >
							</div>
						</div>
						<div class="row-fluid">
							<div class="span3">
								<label for="nome">Nome</label>
								<input class="input-xlarge" id="nome" name="organizacao.nome" placeholder="Nome" type="text" required>
							</div>
							<div class="span3">
								<label for="nomefantasia">Nome Fantasia</label>
								<input class="input-xlarge" id="nomefantasia" name="organizacaoInfo.nomefantasia" placeholder="Nome Fantasia" type="text" required>
							</div>
						</div>

						<div class="widget-box collapsible">
							<div class="widget-title">
								<a href="#collapseOne" data-toggle="collapse">
									<span class="icon"><i class="icon-arrow-right"></i></span>
									<h5>Informações da Organização</h5>
								</a>
							</div>
							<div class="collapse in" id="collapseOne">
								<div class="widget-content">								
									<div class="row-fluid">
										<div class="span2">
											<label for="cnpj">CNPJ</label>
											<input class="input-medium" id="cnpj" name="organizacaoInfo.cnpj" placeholder="cnpj" type="text" required>
										</div>
										<div class="span2">
											<label for="ie">Inscrição Estadual</label>
											<input class="input-medium" id="ie" name="organizacaoInfo.ie" placeholder="Inscrição Estadual" type="text" required>
										</div>
										<div class="span2">
											<label for="tipoOrganizacaoId">Tipo de Organização</label>
											<select  id="tipoOrganizacaoId" name="organizacaoInfo.tipoOrganizacao.tipoOrganizacao_id" class="input-medium">
												<option value="">Selecione o Tipo organização</option>
												<c:forEach items="${tipoOrganizacoes }" var="tipoOrganizacao">
													<option value="${tipoorganizacao.tipoOrganizacao_id }"> ${tipoOrganizacao.nome }</option>
												</c:forEach>
											</select>
										</div>
										<div class="span2">
											<label for="organizacaoPaiId">Organização Pai</label>
											<select  id="organizacaoPaiId" name="organizacaoInfo.organizacao.organizacaoPai_id" class="input-medium">
												<option value="">Selecione a organização</option>
												<c:forEach items="${organizacoes }" var="organizacao">
													<option value="${organizacao.organizacao_id }"> ${organizacao.nome }</option>
												</c:forEach>
											</select>
										</div>
										<div class="span2">
											<label for="supervisorOrganizacaoId">Supervisor Organização</label>
											<select  id="supervisorOrganizacaoId" name="organizacaoInfo.supervisorOrganizacao.funcionario_id" class="input-xlarge">
												<option value="">Selecione o supervisor</option>
												<c:forEach items="${funcionarios }" var="funcionario">
													<option value="${funcionario.funcionario_id }"> ${funcionario.nome }</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span3">
											<label for="contato">Contato</label>
											<input class="input-xlarge" id="email" name="organizacaoInfo.contato" placeholder="Contato" type="text" required>
										</div>
										<div class="span2">
											<label for="email">Email</label>
											<input class="input-xlarge" id="email" name="organizacaoInfo.email" placeholder="E-mail" type="text" required>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span1">
											<label for="dddfone1">DDD</label>
											<input class="input-mini" id="dddfone1" name="organizacaoInfo.dddfone1" placeholder="ddd" type="text" required>											
										</div>
										<div class="span2">
											<label for="telefone1">Telefone1</label>
											<input class="input-medium" id="telefone1" name="organizacaoInfo.telefone1" placeholder="telefone1" type="text" required>
										</div>									
										<div class="span1">
											<label for="dddfone2">DDD</label>
											<input class="input-mini" id="dddfone2" name="organizacaoInfo.dddfone2" placeholder="ddd" type="text" required>
										</div>
										<div class="span2">
											<label for="telefone2">Telefone2</label>
											<input class="input-medium" id="telefone2" name="organizacaoInfo.telefone2" placeholder="telefone2" type="text" required>
										</div>									
										<div class="span1">
											<label for="dddfax">DDD</label>
											<input class="input-mini" id="dddfax" name="organizacaoInfo.dddfax" placeholder="ddd" type="text" required>
										</div>
										<div class="span2">
											<label for="fax">Fax</label>
											<input class="input-medium" id="fax" name="organizacaoInfo.fax" placeholder="fax" type="text" required>
										</div>
										<div class="span1">
											<label for="isActive">Ativo</label>
											<input id="isActive" name="organizacao.isActive" type="checkbox" checked="checked" value="1" >
										</div>
									</div>
								</div>
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
					<h5>Organizacao</h5>
				</div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty organizacoes}">
						<table
							class="table table-bordered table-striped table-hover data-table"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>Empresa</th>									
									<th>Nome</th>
									<th>Ativo</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${organizacoes }" var="organizacao">
									<tr>
										<td>${organizacao.empresa.nome }</td>										
										<td>${organizacao.nome }</td>
										<td>
											<label class="checkbox inline">
												<input type="checkbox" id="isActiveLine" name="organizacao.isActive"
												<c:if test="${organizacao.isActive == true }"> checked="checked"</c:if> onchange="altera(this,'${organizacao.organizacao_id}');">
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