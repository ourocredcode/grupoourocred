<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript">

$(document).ready(function() {

	var tipoUser = document.getElementById("tipoUser").value;
	var conferenciaCopiaRg = document.getElementById("conferenciacopiaRg");

	if(conferenciaCopiaRg.value != ""){
		habilita(conferenciaCopiaRg);
		$("input:radio[name='conferencia.copiaRg']:checked").prop('disabled',true);
	}

	$("input:radio").click(function() {

		var conferenciaCopiaRg = document.getElementById("conferenciacopiaRg");
		var conferenciaCopiaCpf = document.getElementById("conferenciacopiaCpf");

		if($("input:radio[name='conferencia.copiaRg']:checked").val() == "Problema"){
			habilita(conferenciaCopiaRg);
			$("input:radio[name='conferencia.copiaRg']:checked").prop('disabled',true); 
		} else {
			desabilita(conferenciaCopiaRg);
			$("input:radio[name='conferencia.copiaRg']").prop('disabled',false);
		}
		
		if($("input:radio[name='conferencia.copiaCpf']:checked").val() == "Problema"){
			habilita(conferenciaCopiaCpf);
			$("input:radio[name='conferencia.copiaCpf']:checked").prop('disabled',true); 
		} else {
			desabilita(conferenciaCopiaCpf);
			$("input:radio[name='conferencia.copiaCpf']").prop('disabled',false);
		}

	});
});


function desabilita(campo){   
	campo.disabled = 1;
	campo.className = 'label_txt';
	campo.value= '';
}

function habilita(campo){   
	campo.disabled = 0;
	campo.className = 'required';
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

		<input type="hidden" id="tipoUser" name="tipoUser" value="${usuarioInfo.perfil.chave}" />
		<input type="hidden" id="conferencia.contrato.contrato_id" name="conferencia.contrato.contrato_id" value="${contrato.contrato_id}" />
		<input type="hidden" id="conferencia.conferencia_id" name="conferencia.conferencia_id" value="${conferencia.conferencia_id}" />

		<table class="table table-striped table-bordered" id="lista">
			<thead>
				<tr>
					<th>OK</th>
					<th>Problema</th>
					<th>Observação</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="procedimento" items="${procedimentos }">
					<c:set var="name" scope="session" value="conferencia.${procedimento.chave }"/>
					<c:set var="nameId" scope="session" value="conferencia${procedimento.chave }"/>
					<tr>
						<td>
							<input type="radio" id="${name }" name="${name }" value="OK" />
						</td>
						<td>
							<input type="radio" id="${name }" name="${name }" value="Problema" />
						</td>
						<td>
							<input type="text" id="${nameId }" name="${name }" disabled="disabled" />
						</td>
					</tr>
				</c:forEach>
				<tr>
					<th colspan="1">
					</th>
					<th style="text-align: center">
						<input type="button" value="Salvar" class="form_button" onclick="javascript:validaForm('#conferenciaForm');">
					</th>
					<th style="text-align: center">
						<button type="button" value="Fecha" class="form_button" onclick="window.location.reload(true);">Fecha</button>
					</th>
				</tr>
			</tbody>
		</table>
	</form>
</div>



