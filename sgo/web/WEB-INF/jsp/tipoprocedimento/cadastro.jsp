<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){
	
	$('#tipoprocedimento-li-a').click(function() {
		window.location.href = '<c:url value="/tipoprocedimento/cadastro" />';
	});

	$('#procedimentoconferencia-li-a').click(function() {
		window.location.href = '<c:url value="/procedimentoconferencia/cadastro" />';
	});

	$("#tipoprocedimentoIsActive").change(function(e){
		if(document.tipoProcedimentoForm.tipoProcedimentoIsActive.checked==true){
			document.tipoProcedimentoForm.tipoProcedimentoIsActive.value=true;
		}else{
			document.tipoProcedimentoForm.tipoProcedimentoIsActive.value=false;
		}		
	});

	$('#btnNovo').click(function() {		
		limpaForm();
	});

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/tipoprocedimento/cadastro" />';
	});

});

function altera(atributo, id, valor) {

	var atributo = "tipoProcedimento." + atributo;

	var temp = "$.post( ";
	temp += "	'<c:url value='/tipoprocedimento/altera' />', ";
	temp += "	{ '" + atributo + "' : valor, 'tipoprocedimento.tipoprocedimento_id' : id }, ";
	temp += "	function(resposta) { }";
	temp += ");";

	eval(temp);

}

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.tipoProcedimentoForm.reset();
	}
}
</script>

	<div id="content-header">
		<h1>Cadastro Tipo de Procedimento</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
		<a href="#" class="current">Cadastro Tipo de Procedimento</a>
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
					<li class="active" id="tipoprocedimento-li"><a href="#tipoprocedimento-div" data-toggle="tab" id="tipoprocedimento-li-a">Tipo Procedimento</a></li>
					<li class="" id="procedimentoconferencia-li"><a href="#procedimentoconferencia-div" data-toggle="tab" id="procedimentoconferencia-li-a">Procedimento Conferencia</a></li>
				</ul>
				
				<div id="myTabContent" class="tab-content">

					<div class="tab-pane fade active in" id="tipoprocedimento-div">

						<form id="tipoProcedimentoForm" name="tipoProcedimentoForm" action="<c:url value="/tipoprocedimento/salva"/>" method="POST">
						
							<div class="row-fluid">
								<div class="span2">
									<label for="tipoProcedimentoEmpresa">Empresa</label>
									<input class="span10" id="tipoProcedimentoEmpresa" name="tipoProcedimento.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" readonly="readonly">
									<input class="span1" id="tipoProcedimentoEmpresaId" name="tipoProcedimento.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden" required/>
								</div>				
								<div class="span2">
									<label for="tipoProcedimentoOrganizacao">Organização</label>
									<input class="span10" id="tipoProcedimentoOrganizacao" name="tipoProcedimento.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" readonly="readonly">
									<input class="span1" id="tipoProcedimentoOrganizacaoId" name="tipoProcedimento.organizacao.organizacao_id"  value="${usuarioInfo.organizacao.organizacao_id }" type="hidden" required />																
								</div>
								<div class="span2">
									<label for="tipoProcedimentoNome">Nome</label>							
									<input class="span12" id="tipoProcedimentoNome" name="tipoProcedimento.nome" value="${tipoProcedimento.nome }" type="text" placeholder="Nome" required>							
								</div>
								<div class="span1">
									<label for="tipoProcedimentoIsActive">Ativo</label>							
									<input id="tipoProcedimentoIsActive" name="tipoProcedimento.isActive" type="checkbox" checked="checked" value="1" >
								</div>
							</div>
							<br>
		
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
								<div class="btn-group">
									<input type="button" value="Voltar" id="btnSalvar" onClick="history.go(-1)" class="btn btn-primary" style="width: 100px;">
								</div>
							</div>
							<br><br>		
						</form>
					</div>

					<div class="tab-pane fade" id="procedimentoconferencia-div"> </div>

				</div>
				<table class="table table-striped table-bordered">
					<thead>
						<tr>
							<th>Empresa</th>
							<th>Organização</th>
							<th>Nome</th>
						</tr>
					</thead>
					<tbody>	
						<c:forEach items="${tipoProcedimentos }" var="tipoProcedimento">
							<tr>
								<td>${tipoProcedimento.empresa.nome }</td>
								<td>${tipoProcedimento.organizacao.nome }</td>
								<td>${tipoProcedimento.nome }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>						
	</div>
</div>

<%@ include file="/footer.jspf"%>
