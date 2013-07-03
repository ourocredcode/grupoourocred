<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#periodo-li-a').click(function() {
		window.location.href = '<c:url value="/periodo/cadastro" />';
	});

	$("#periodoIsActive").change(function(e){
		if(document.periodoForm.periodoIsActive.checked==true){
			document.periodoForm.periodoIsActive.value=true;
		}else{
			document.periodoForm.periodoIsActive.value=false;
		}		
	});

	$('#btnNovo').click(function() {		
		limpaForm();
	});

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/periodo/cadastro" />';
	});

});

function altera(atributo, id, valor) {

	var atributo = "periodo." + atributo;

	var temp = "$.post( ";
	temp += "	'<c:url value='/periodo/altera' />', ";
	temp += "	{ '" + atributo + "' : valor, 'periodo.periodo_id' : id }, ";
	temp += "	function(resposta) { }";
	temp += ");";

	eval(temp);

}

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.periodoForm.reset();
	}
}
</script>

	<div id="content-header">
		<h1>Cadastro de Hiscon</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
		<a href="#" class="current">Cadastro de Hiscon</a>
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
				<form id="periodoForm" name="periodoForm" action="<c:url value="/periodo/salva"/>" method="POST">
				
					<div class="row-fluid">
						<div class="span3">
							<label for="periodoEmpresa">Empresa</label>
							<input class="input-medium" id="periodoEmpresaId" name="periodo.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden" required/>
							<input class="input-xlarge" id="periodoEmpresa" name="periodo.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" />
						</div>
		
						<div class="span3">
							<label for="periodoOrganizacao">Organização</label>
							<input  class="input-medium" id="periodoOrganizacaoId" name="periodo.organizacao.organizacao_id"  value="${usuarioInfo.organizacao.organizacao_id }" type="hidden" required />
							<input  class="input-xlarge" id="periodoOrganizacao" name="periodo.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" />							
						</div>
					</div>
					<div class="row-fluid">
						<div class="span5">
							<label for="periodoNome">Nome</label>							
							<input class="input-xxlarge" id="periodoNome" name="periodo.nome" value="${periodo.nome }" type="text" placeholder="Nome" required>							
						</div>
						<div class="span1">
							<label for="periodoIsActive">Ativo</label>							
							<input id="periodoIsActive" name="periodo.isActive" type="checkbox" checked="checked" value="1" >
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

				<table class="table table-striped table-bordered">
					<thead>
						<tr>
							<th>Empresa</th>
							<th>Organização</th>
							<th>Nome</th>
						</tr>
					</thead>
					<tbody>	
						<c:forEach items="${periodos }" var="periodo">
							<tr>
								<td>${periodo.empresa.nome }</td>
								<td>${periodo.organizacao.nome }</td>
								<td>${periodo.nome }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>						
		</div>
	</div>

<%@ include file="/footer.jspf"%>
