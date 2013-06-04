<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${etapas == null}">
	<option value="">Selecione um Workflow</option>
</c:if>

<c:if test="${etapas != null}">
	<option value="">Selecione a Etapa</option>
	<c:forEach items="${etapas }" var="etapa">
		<option value="${etapa.etapa_id }">${etapa.nome}</option>
	</c:forEach>
</c:if>