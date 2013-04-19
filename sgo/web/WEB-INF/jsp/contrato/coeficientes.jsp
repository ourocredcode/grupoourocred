<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${coeficientes == null}">
	<option value="">Selecione um Banco</option>
</c:if>

<c:if test="${coeficientes != null}">
	<option value="">Selecione um coeficiente...</option>

	<c:forEach var="coeficiente" items="${coeficientes}">
	<option value="${coeficiente.valor},${coeficiente.percentualMeta},${coeficiente.coeficiente_id}">${coeficiente.valor} - ${coeficiente.tabela.nome} (${coeficiente.tabela.prazo })</option>
	</c:forEach>

</c:if>









