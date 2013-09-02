<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<script type="text/javascript">

$(document).ready(function() {

	$('select').select2();
	
});

</script>



<c:if test="${not empty coeficientes }">
	<table id="mytable">
		<tr>
			<td>
				<select id="coeficienteHist" size="8" class="input-large">
					<option value="">Selecione um coeficiente abaixo...</option>
					<c:forEach items="${coeficientes}" var="coeficiente">
						<fmt:formatDate value="${coeficiente.created.time}" pattern="dd/MM/yyyy" var="strDate" /> 
						<option value="${coeficiente.coeficiente_id}">${strDate} - ${coeficiente.tabela.nome} - ${coeficiente.valor}</option>
					</c:forEach>	
				</select>
			</td>
		</tr>
	</table>
</c:if>

<script type="text/javascript">
jQuery(function($){

	$("#coeficienteHist").change(function() {

		var coeficienteId = $("#coeficienteHist").val();

		alert("Selecione o coeficiente no contrato ao lado");

		$("#auxCoeficiente").load('<c:url value="/contrato/coeficienteHistorico" />',{'coeficienteId': coeficienteId});

	});
	
});
</script>