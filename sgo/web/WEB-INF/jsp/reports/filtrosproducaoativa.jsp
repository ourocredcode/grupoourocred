<%@ include file="/header.jspf"%>

<script type="text/javascript">

function validaForm(form) {

	$.each($(".select2-container"), function (i, n) {
        $(n).next().show().fadeTo(0, 0).height("0px").css("left", "auto"); 
        $(n).prepend($(n).next());
        $(n).delay(500).queue(function () {
            $(this).removeClass("validate[required]");
            $(this).dequeue();
        });
    });

	if ($(form).validate().form() == true) {

		$(form).submit();

	} else {
	
		return false;

	}

}	

</script>

<div id="content-header">
	<h1>Relat�rio Produ��o Ativa</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
	</div>
</div>

	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> DashBoard</a>
		<a href="#" class="current">Filtros Aprovados</a>
	</div>

	<c:if test="${not empty notice}">
		<c:choose>
			<c:when test="${fn:contains(notice,'Erro:')}">
					<div class="alert alert-error">
						<strong>${notice }</strong>
						<a href="#" data-dismiss="alert" class="close">�</a>
					</div>
			</c:when>
			<c:otherwise>
					<div class="alert alert-success">
						<strong>${notice }</strong>
						<a href="#" data-dismiss="alert" class="close">�</a>
					</div>
			</c:otherwise>
		</c:choose>
	</c:if>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
		<form id="filtroAprovadosForm" name="filtroAprovadosForm" action="<c:url value="/reports/producaoativa"/>" method="POST" target="_blank" >
			
			<div class="widget-box">

					<div class="widget-title">
						<span class="icon"><i class="icon-signal"></i>
						</span><h5>Filtros</h5>
						<div class="buttons"><button type="button" class="btn btn-mini" onclick="javascript:validaForm('#filtroAprovadosForm');" ><i class="icon-file icon-black"></i> Gerar Relat�rio </button></div>
					</div>

					<div class="widget-content">
						<div class="row-fluid">
							<div class="span12">
								<div class="span12 center" style="text-align: center;font-size: 7px;">					

										<div class="row-fluid">
											<div class="span3">
												<label for="empresa">Empresa</label>
												<input class="input-medium" id="empresaId" name="empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden" readonly="readonly"/>
												<input class="input-xlarge" id="empresa" name="empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" readonly="readonly" />
											</div>
							
											<div class="span3">
												<label for="organizacao">Organiza��o</label>
												<input  class="input-medium" id="organizacaoId" name="organizacao.organizacao_id"  value="${usuarioInfo.organizacao.organizacao_id }" type="hidden" readonly="readonly"/>
												<input  class="input-xlarge" id="organizacao" name="organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" readonly="readonly" />							
											</div>

											<div class="span3">								
												<label for="usuario">Supervisor</label>
												<select id="usuarioId" name="usuario.usuario_id" class="input-medium">
													<option value="">Todos</option>
													<c:forEach items="${supervisores}" var="supervisor">
														<option value="${supervisor.usuario_id}">${supervisor.apelido}</option>
													</c:forEach>
												</select>
											</div>
											
										</div>

								</div>
							</div>
						</div>
					</div>
				</div>
		</form>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>
