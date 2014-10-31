<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>



<script type="text/javascript">

$(document).ready(function() { 
	
	$("#busca_Supervisor").change(function() {   

		var supervisor_id = $("#busca_Supervisor").val();

		if(supervisor_id != '')
			$("#busca_Consultor").load('<c:url value="/contrato/consultores" />', {'supervisor_id': supervisor_id});

	});
	
});

function validaFormRepasse(form) {

	if ($(form).validate().form() === true) {
		$(form).submit();
	} else {
		return false;
	}

}

</script>


<form id="repasseForm" name="repasseForm" action="<c:url value="/contrato/repasse/salva" />" method="post" >
<input type="hidden" id="contrato.contrato_id" name="contrato.contrato_id" value="${contrato.contrato_id }" />

	<div id="divRepasse">
		<div class="row-fluid">
			<div class="span2">
				<label for="busca_Supervisor">Supervisor Repasse</label>
				<select id="busca_Supervisor" name="contrato.usuario.supervisorUsuario.nome" class="input-medium">
					<option value="">Todos</option>
					<c:forEach items="${supervisores}" var="supervisor">
						<option value="${supervisor.usuario_id}" <c:if test="${supervisor.usuario_id == contrato.usuario.supervisorUsuario.usuario_id}">selected="selected"</c:if>>${supervisor.nome}</option>
					</c:forEach>
				</select>	
			</div>
			<div class="span3">
				<label for="busca_Consultor">Consultor Repasse</label>
				<select id="busca_Consultor" name="contrato.usuario.usuario_id" class="input-xlarge">
					<option value="">Selecione um Supervisor</option>
					<c:if test="${usuarioInfo.perfil.chave == 'Gestor'}">
						<c:forEach items="${consultores }" var="consultor">
							<option value="${consultor.usuario_id}" <c:if test="${consultor.usuario_id == contrato.usuario.usuario_id}">selected="selected"</c:if>>${consultor.nome}</option>
						</c:forEach>
					</c:if>
				</select>
			</div>
			
			<div class="span2">
				<label for="percentualRepasse">Percentual Repasse</label>
				<select id="percentualRepasse" name="contrato.percentualRepasse" class="input-small">
					<option value="1.0">100%</option>
					<option value="0.9">90%</option>
					<option value="0.8">80%</option>
					<option value="0.7">70%</option>
					<option value="0.6">60%</option>
					<option value="0.5">50%</option>
					<option value="0.4">40%</option>
					<option value="0.3">30%</option>
					<option value="0.2">20%</option>
					<option value="0.1">10%</option>
					<option value="0.0">0%</option>
				</select>
			</div>
		</div>
		<div class="form-actions">
			<input value="Salva" type="button" class="btn btn-primary" onclick="javascript:validaFormRepasse('#repasseForm');" style="vertical-align: bottom;">
			<button type="button" value="Fecha" class="btn " onclick="window.location.reload(true);">Fecha</button>
		</div>
	</div>
</form>



