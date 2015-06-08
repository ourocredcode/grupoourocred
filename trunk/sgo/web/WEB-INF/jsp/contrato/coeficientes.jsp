<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:if test="${coeficientes == null}">
	<option value="">Selecione um Banco</option>
</c:if>

<c:if test="${coeficientes != null}">
	<option value="">Selecione um coeficiente...</option>

	<c:forEach var="coeficiente" items="${coeficientes}">
	<option value="${coeficiente.valor},${coeficiente.percentualMeta},${coeficiente.coeficiente_id},${coeficiente.tabela.tabela_id},${coeficiente.tabela.nome}">${coeficiente.valor} - ${coeficiente.tabela.nome} (<fmt:formatNumber value="${coeficiente.percentualMeta * 100}" maxFractionDigits="0"/>%)</option>
	</c:forEach>

</c:if>









