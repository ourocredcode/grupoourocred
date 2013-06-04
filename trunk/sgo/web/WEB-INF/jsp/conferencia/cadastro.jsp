<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript">

$(document).ready(function() {

	var elementos = document.forms[0].elements;

	for (var x = 0; x < elementos.length; x++) {

		if (elementos[x].name.indexOf("observacao") != -1) {

			var observacao = document.getElementById(elementos[x].id);

			if(observacao.value != ""){
				habilita(observacao);
			}
		}
	}
	
	$("input:radio").click(function() {

		var countRadios = 0;
		var elementos = document.forms["conferenciaForm"].elements;

		for (var x = 0; x < elementos.length; x++) {

			if (elementos[x].name.indexOf("isValido") != -1) {
				countRadios += 1;
			}

		}

		for (var x = 0; x < countRadios/2; x++) {

			var confRadio = "conferencias[" + x + "].isValido";
			var confObs = "conferencias[" + x + "].observacao";
			var observacao = document.getElementById(confObs);

			if($("input:radio[name='" + confRadio +"']:checked").val() == 0){
				habilita(observacao);
				$("input:radio[name='" + confRadio +"']:checked").prop('disabled',true);

			} else {
				desabilita(observacao);
				$("input:radio[name='" + confRadio +"']").prop('disabled',false);
			}
		}

	});
	
	$('#conferenciaForm').submit(function() {
		$("input", this).prop("readonly", true);
		$("input[type='submit'],input[type='button']", this).prop("disabled", true);
		$.ajax({
			data: $(this).serialize()
			, type: $(this).prop('method')
			, url: $(this).prop('action')
			, success: function(response) {
				alert(response);
			}
		});

		return false;

	});

	

});

function desabilita(campo){   

	campo.disabled = true;
	campo.required = false;
	campo.className = 'input-xxlarge';

}

function habilita(campo){

	campo.disabled = false;
	campo.required = true;
	campo.className = 'input-xxlarge';

}

function validaForm(form) {

	if ($(form).validate().form() === true) {
		$(form).submit();
	} else {
		return false;
	}

}

</script>

<div style="float: left;margin-top: 10px;margin-bottom: 20px;">
	<form id="conferenciaForm" name="conferenciaForm" action="<c:url value="/conferencia/salva" />" method="post">

		<table class="table table-striped table-bordered" id="lista">
			<thead>
				<tr>
					<th></th>
					<th>OK</th>
					<th>Problema</th>
					<th>Observação</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="conferencia" items="${conferencias }" varStatus="status">
					<input type="hidden" id="conferencia.tipoProcedimento.tipoProcedimento_id" name="conferencias[${status.index}].tipoProcedimento.tipoProcedimento_id" value="${conferencia.tipoProcedimento.tipoProcedimento_id }" />
					<input type="hidden" id="conferencia.procedimentoConferencia.procedimentoConferencia_id" name="conferencias[${status.index}].procedimentoConferencia.procedimentoConferencia_id" value="${conferencia.procedimentoConferencia.procedimentoConferencia_id }" />
					<input type="hidden" id="conferencia.contrato.contrato_id" name="conferencias[${status.index}].contrato.contrato_id" value="${conferencia.contrato.contrato_id }" />
					<input type="hidden" id="conferencia.empresa.empresa_id" name="conferencias[${status.index}].empresa.empresa_id" value="${conferencia.empresa.empresa_id }" />
					<input type="hidden" id="conferencia.organizacao.organizacao_id" name="conferencias[${status.index}].organizacao.organizacao_id" value="${conferencia.organizacao.organizacao_id }" />
					<input type="hidden" id="conferencia.createdBy.usuario_id" name="conferencias[${status.index}].createdBy.usuario_id" value="${conferencia.createdBy.usuario_id }" />
					<input type="hidden" id="conferencia.created.time" name="conferencias[${status.index}].created.time" value="<fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${conferencia.created.time }" />" />
					<input type="hidden" id="conferencia.isActive" name="conferencias[${status.index}].isActive" value="1" />
					<input type="hidden" id="conferencia.conferencia_id" name="conferencias[${status.index}].conferencia_id" value="${conferencia.conferencia_id}" />
					<tr>
						<td>
							<c:out value="${conferencia.procedimentoConferencia.nome }"></c:out>
						</td>
						<td>
							<input type="radio" id="conferenciasIsValido" name="conferencias[${status.index}].isValido" value="1" <c:if test="${conferencia.isValido == true }">checked="checked"</c:if> />
						</td>
						<td>
							<input type="radio" id="conferenciasIsValido" name="conferencias[${status.index}].isValido" value="0" <c:if test="${conferencia.isValido != true }">checked="checked"</c:if> />
						</td>
						<td>
							<input type="text" id="conferencias[${status.index}].observacao" name="conferencias[${status.index}].observacao" disabled="disabled" class="input-xxlarge" value="${conferencia.observacao }" />
						</td>
					</tr>
				</c:forEach>
				<tr>
					<th style="text-align: center">
						<input type="button" value="Salvar" class="btn btn_primary" onclick="javascript:validaForm('#conferenciaForm');">
					</th>
					<th style="text-align: center">
						<button type="button" value="Fecha" class="btn" onclick="window.location.reload(true);">Fecha</button>
					</th>
					<th colspan="2">
					</th>
				</tr>
			</tbody>
		</table>
	</form>
</div>



