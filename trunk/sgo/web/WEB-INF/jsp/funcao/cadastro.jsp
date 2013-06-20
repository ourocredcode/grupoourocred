<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#funcao-li-a').click(function() {
		window.location.href = '<c:url value="/funcao/cadastro" />';
	});

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/funcao/cadastro" />';
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

	$("#funcaoIsActive").change(function(e){
		if(document.funcaoForm.funcaoIsActive.checked==true){
			document.funcaoForm.funcaoIsActive.value=true;
		}else{
			document.funcaoForm.funcaoIsActive.value=false;
		}
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.funcaoForm.reset();
	}
}


function altera(linha, id) {
	var valor = linha.checked == true ? true : false;
	if (window.confirm("Deseja alterar o função selecionado?"))
		$.post('<c:url value="/funcao/altera" />', {
			'funcao.funcao_id' : id,
			'funcao.isActive' : valor
		});

	return false;
}

</script>

<div id="content-header">
	<h1>Cadastro Função</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
		<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
	<a href="#" class="current">Função</a>
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
				<li class="" id="funcao-li"><a href="#funcao-div" data-toggle="tab" id="funcao-li-a">Cadastro de Função</a></li>				
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="funcao-div">

					<form id="funcaoForm" name="funcaoForm" action="<c:url value="/funcao/salva"/>" method="POST">

						<div class="row-fluid">
							<div class="span3">
								<label for="funcaoEmpresa">Empresa</label>
	      						<input class="input-xlarge" id="funcaoEmpresa" name="funcao.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" readonly="readonly">
	      						<input id="funcaoEmpresaId" name="funcao.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden">
    						</div>						
							<div class="span3">
								<label for="funcaoOrganizacao">Organização</label>
								<input class="input-xlarge" id="funcaoOrganizacao" name="funcao.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" readonly="readonly">
		      					<input id="funcaoOrganizacaoId" name="funcao.organizacao.organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" type="hidden">
							</div>
							<div class="span3">
								<label for="funcaoNome">Nome</label>								
								<input class="input-xlarge"type="text" id="funcaoNome" name="funcao.nome" value="${funcao.nome }" placeholder="Nome" required>
							</div>
							<div class="span1">
								<label for="funcaoIsActive">Ativo</label>
								<input type="checkbox" id="funcaoIsActive" name="funcao.isActive" checked="checked" value="1" >
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
					<h5>Função</h5>
				</div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty funcoes }">
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
								<c:forEach items="${funcoes }" var="funcao">
									<tr>
										<td>${funcao.empresa.nome }</td>
										<td>${funcao.organizacao.nome }</td>
										<td>${funcao.nome }</td>
										<td>
											<label class="checkbox inline">
												<input type="checkbox" id="funcaoIsActiveLine" name="funcao.isActive"
												<c:if test="${funcao.isActive == true }"> checked="checked"</c:if> onchange="altera(this,'${funcao.funcao_id}');">
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
