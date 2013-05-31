<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${workflowEtapas == null}">
	<option value="">Selecione a Etapa</option>
</c:if>

<c:if test="${workflowEtapas != null}">
	<option value="">Selecione a Etapa</option>
	<c:forEach items="${workflowEtapas }" var="workflowEtapa">
		<option value="${workflowEtapa.workflowEtapa_id }">${workflowEtapa.nome}</option>
	</c:forEach>
</c:if>