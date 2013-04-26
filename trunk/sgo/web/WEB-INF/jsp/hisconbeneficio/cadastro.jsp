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
		document.perfilForm.reset();
	});

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/hisconbeneficio/cadastro" />';
	});

});

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

	<div id="buscaHisconBeneficioDiv">
		<form id="buscaHisconBeneficioForm" class="form-search" action="<c:url value="/hisconbeneficio/cadastro" />" method="POST">
			
					<div class="row-fluid">
						<div class="span2">
							<label for="hisconBeneficioEmpresa">Empresa</label>
							<input class="input-medium" id="hisconBeneficioEmpresa" name="hisconBeneficio.empresa.nome" value="${hisconBeneficio.empresa.nome }" type="text" />
							<input class="input-medium" id="hisconBeneficioEmpresaId" name="hisconBeneficio.empresa.empresa_id" value="${hisconBeneficio.empresa.empresa_id }" type="hidden" />
						</div>

						<div class="span2">
							<label for="hisconBeneficioOrganizacao">Organização</label>
							<input  class="input-medium" id="hisconBeneficioOrganizacao" name="hisconBeneficio.organizacao.nome" value="${hisconBeneficio.organizacao.nome }" type="text" />
							<input  class="input-medium" id="hisconBeneficioOrganizacaoId" name="hisconBeneficio.organizacao.organizacao_id"  value="${hisconBeneficio.organizacao.organizacao_id }" type="hidden" />
						</div>
					</div>
					
			<div class="input-append">
				<input type="hidden" id="hisconBeneficioEmpresaId" name="empresa_id" value="${usuarioInfo.empresa.empresa_id }" />
				<input type="hidden" id="hisconBeneficioOrganizacaoId" name="organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" />
				<input type="text" class="input-medium" id="numeroBeneficio" name="numeroBeneficio" placeholder="Benefício"/>
				<button class="add-on" onclick="submit();"><i class="icon-search"></i></button>
			</div>

		</form>
	</div>

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">			
				<form id="hisconBeneficioForm" name="hisconBeneficioForm" action="<c:url value="/hisconbeneficio/salva"/>" method="POST">

					<div class="row-fluid">
						<div class="controls controls-row">
							<label for="hisconBeneficioCpf">Cpf</label>
							<input  class="input-medium" id="hisconBeneficioCpf" name="hisconBeneficio.parceiroBeneficio.parceiroNegocio.cpf" type="text" placeholder="Cpf" value="${hisconBeneficio.parceiroBeneficio.parceiroNegocio.cpf }">
						</div>
						<div class="span2">
							<label for="hisconBeneficioParceiroNegocio">Parceiro de Negócios</label>
							<input  class="input-medium" id="hisconBeneficioParceiroNegocio" name="hisconBeneficio.parceiroBeneficio.parceiroNegocio.nome" type="text" placeholder="Parceiro de Negócios" value="${hisconBeneficio.parceiroBeneficio.parceiroNegocio.nome }">
							<input  class="input-medium" id="hisconBeneficioParceiroNegocioId" name="hisconBeneficio.parceiroBeneficio.parceiroNegocio.parceiroNegocio_id" type="hidden" value="${hisconBeneficio.parceiroBeneficio.parceiroNegocio.parceiroNegocio_id }">
							<input  class="input-medium" id="hisconBeneficioParceiroNegocioId" name="hisconBeneficio.parceiroBeneficio.parceiroBeneficio_id" type="hidden" value="${hisconBeneficio.parceiroBeneficio.parceiroBeneficio_id }">
						</div>

					</div>
					
					<div class="control-group">
						<label class="control-label" for="hisconBeneficioIsActive">Ativo</label>
						<div class="controls">
							<input type="checkbox" id="hisconBeneficioIsActive" name="hisconBeneficio.isActive" checked="checked" value="1" >							
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
							</tr>
						</thead>
						<tbody>	
							<c:forEach items="${hisconsBeneficio}" var="hiscon">
								<tr>
									<td>${hiscon.caminhoArquivo }</td>
									<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm" type="time" value="${hiscon.created.time}" /></td>
									<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm" type="time" value="${hiscon.dataAdm.time}" /></td>
									<td>${hiscon.usuario.nome }</td>									
									<td>${hiscon.parceiroBeneficio.parceiroNegocio.nome }</td>
									<td>${hiscon.parceiroBeneficio.parceiroNegocio.cpf }</td>
									<td>${hiscon.parceiroBeneficio.numeroBeneficio }</td>
									
									<div class="controls controls-row">
									<label for="parceiroNegocioTipoParceiroId">Tipo Parceiro</label>
									<select id="parceiroNegocioTipoParceiroId" name="parceiroNegocio.tipoParceiro.tipoParceiro_id" class="input-medium">
									
									</select>
								</div>
									<td>${hiscon.workflowEtapa.nome }
										<c:forEach var="tipoParceiro" items="${tiposParceiro }">
								 			<option value="${tipoParceiro.tipoParceiro_id }" <c:if test="${parceiroNegocio.tipoParceiro.tipoParceiro_id eq tipoParceiro.tipoParceiro_id }"> selected="selected"</c:if>>${tipoParceiro.nome }</option>
										</c:forEach>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>				
				</form>
			</div>						
		</div>
	</div>

<%@ include file="/footer.jspf"%>
