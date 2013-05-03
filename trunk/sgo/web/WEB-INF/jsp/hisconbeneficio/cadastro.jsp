<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#hisconbeneficio-li-a').click(function() {
		window.location.href = '<c:url value="/hisconbeneficio/cadastro" />';
	});

	$("#numeroBeneficio").click(function(){
		 var numeroBeneficio = $("#numeroBeneficio").val();
		$.ajax({
           type: "POST",
           url: "/sgo/hisconbeneficio/lista",           
           success: function(result) {
              $('#lista').html(result);
           }
       });
	});

	$("#hisconBeneficioIsActive").change(function(e){
		$(this).val( $("#hisconBeneficioIsActive:checked").length > 0 ? "1" : "0");
	});

	$('#btnNovo').click(function() {		
		limpaForm();
	});

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/hisconbeneficio/cadastro" />';
	});

});

function altera(atributo, id, valor) {

	var atributo = "hiscon." + atributo;

	var temp = "$.post( ";
	temp += "	'<c:url value='/hiscon/altera' />', ";
	temp += "	{ '" + atributo + "' : valor, 'hiscon.id' : id }, ";
	temp += "	function(resposta) { }";
	temp += ");";

	eval(temp);

}

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.hisconBeneficioForm.reset();
	}
}
</script>

	<div id="content-header">
		<h1>Cadastro de Hiscon</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
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

	<div id="buscaHisconBeneficioDiv" style="float: left; margin-top: 10px; margin-left: 20px;position: absolute;">
		<form id="buscaHisconBeneficioDiv" class="form-search" action="<c:url value="/hisconbeneficio/cadastroteste" />" method="POST">
			<div class="input-append">
				<input type="hidden" id="hisconBeneficioEmpresaId" name="empresa_id" value="${usuarioInfo.empresa.empresa_id }" />
				<input type="hidden" id="hisconBeneficioOrganizacaoId" name="organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" />
				<input type="text" class="input-medium" id="numeroBeneficio" name="numeroBeneficio" placeholder="Benefício"/>
				<button type="submit" class="tip-right" title="" data-original-title="Search"><i class="icon-search icon-white"></i></button>
			</div>
		</form>
	</div>
	<br>	
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">			
				<form id="hisconBeneficioForm" name="hisconBeneficioForm" action="<c:url value="/hisconbeneficio/salva"/>" method="POST">
				
					<div class="row-fluid">
						<div class="span3">
							<label for="hisconBeneficioEmpresa">Empresa</label>
							<input class="input-medium" id="hisconBeneficioEmpresaId" name="hisconBeneficio.empresa.empresa_id" value="${hisconBeneficio.empresa.empresa_id }" type="hidden" />
							<input class="input-xlarge" id="hisconBeneficioEmpresa" name="hisconBeneficio.empresa.nome" value="${hisconBeneficio.empresa.nome }" type="text" />
						</div>
		
						<div class="span3">
							<label for="hisconBeneficioOrganizacao">Organização</label>
							<input  class="input-medium" id="hisconBeneficioOrganizacaoId" name="hisconBeneficio.organizacao.organizacao_id"  value="${hisconBeneficio.organizacao.organizacao_id }" type="hidden" />
							<input  class="input-xlarge" id="hisconBeneficioOrganizacao" name="hisconBeneficio.organizacao.nome" value="${hisconBeneficio.organizacao.nome }" type="text" />							
						</div>
					</div>

					<div class="row-fluid">
						<div class="controls controls-row">
							<label for="hisconBeneficioCpf">Cpf</label>
							<input  class="input-medium" id="hisconBeneficioCpf" name="hisconBeneficio.parceiroBeneficio.parceiroNegocio.cpf" type="text" placeholder="Cpf" value="${hisconBeneficio.parceiroBeneficio.parceiroNegocio.cpf }">
						</div>
						<div class="controls controls-row">
							<label for="hisconBeneficioParceiroNegocio">Parceiro de Negócios</label>
							<input  class="input-xxlarge" id="hisconBeneficioParceiroNegocio" name="hisconBeneficio.parceiroBeneficio.parceiroNegocio.nome" type="text" placeholder="Parceiro de Negócios" value="${hisconBeneficio.parceiroBeneficio.parceiroNegocio.nome }">
							<input  class="input-medium" id="hisconBeneficioParceiroNegocioId" name="hisconBeneficio.parceiroBeneficio.parceiroNegocio.parceiroNegocio_id" type="hidden" value="${hisconBeneficio.parceiroBeneficio.parceiroNegocio.parceiroNegocio_id }">
							<input  class="input-medium" id="hisconBeneficioParceiroBeneficioId" name="hisconBeneficio.parceiroBeneficio.parceiroBeneficio_id" type="hidden" value="${hisconBeneficio.parceiroBeneficio.parceiroBeneficio_id }">
						</div>
					</div>
					<br>

					<br>
				 	<div class="btn-group">
						<input type="button" value="Voltar" id="btnSalvar" onClick="history.go(-1)" class="btn btn-primary" style="width: 100px;">
					</div>
					<div class="btn-group">
						<button type="button" class="btn btn-primary" id="btnNovo" >Limpar</button>
					</div>
					<div class="btn-group">
						<button type="button" class="btn btn-primary" id="btnSair" >Sair</button>
					</div>
					<br><br>		
									
				</form>
				
				<c:if test="${hisconBeneficio.parceiroBeneficio.parceiroNegocio.parceiroNegocio_id == null}">
				<form action="<c:url value="/uploadHiscon"/>" enctype="multipart/form-data" method="post">	
					<table id="myform">
						<tr>
							<td>
								Carregar Hiscon:
							</td>
							<td>
								<input type="file" name="zip" class="span10"/>
							</td>
							<td>
								<input type="submit" class="form_button_vertical" value="Carregar"/>	
							</td>
						</tr>
					</table>
				</form>	
				</c:if>

				<c:if test="${empty hisconBeneficio.parceiroBeneficio.parceiroNegocio.parceiroNegocio_id}">
					<td style="width: 120px;"></td>
				</c:if>
				<c:if test="${hisconBeneficio.parceiroBeneficio.parceiroNegocio.parceiroNegocio_id != null}">
					<td style="width: 200px;">
						<form action="<c:url value="/hisconbeneficio/salva"/>" method="POST">
							<button type="submit" class="btn btn-primary" id="btnSalvar" value="Solicitar Hiscon">Solicitar Hiscon</button>									
						</form>
					</td>
				</c:if>
				<br><br>
				<table class="table table-striped table-bordered" id="lista">
					<thead>
						<tr>
							<th>Imagem</th>
							<th>Data solicitação</th>
							<th>Data solicitação Adm</th>
							<th>Consultor</th>
							<th>Cliente</th>
							<th>Cpf</th>
							<th>Número Benefício</th>
							<th>Status Atual</th>
							<th>Quantidade</th>
						</tr>
					</thead>
					<tbody>	
						<c:forEach items="${hiscons}" var="hiscon">
							<tr>
								<td>${hiscon.caminhoArquivo }</td>
								<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm" type="date" value="${hiscon.created.time}" /></td>
								<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm" type="date" value="${hiscon.dataAdm.time}" /></td>
								<td>${hiscon.usuario.nome }</td>									
								<td>${hiscon.parceiroBeneficio.parceiroNegocio.nome }</td>
								<td>${hiscon.parceiroBeneficio.parceiroNegocio.cpf }</td>
								<td>${hiscon.parceiroBeneficio.numeroBeneficio }</td>
								<td>
									<select id="hisconBeneficioStatus" class="input-medium" >
										<c:forEach var="etapa" items="${etapas }">
											<option value="${etapa.workflowEtapa_id}" 
											<c:if test="${etapa.workflowEtapa_id == hisconBeneficio.workflowEtapa.workflowEtapa_id}">selected</c:if>>${etapa.nome }</option>
										</c:forEach>
									</select>
								</td>
								<td>${hiscon.countHiscons }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>						
		</div>
	</div>

<%@ include file="/footer.jspf"%>
