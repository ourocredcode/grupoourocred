<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${cidades == null}">
	<option value="">Selecione um UF...</option>
</c:if>

<c:if test="${cidades != null}">
	<option value="">Cidades..</option>
	<c:forEach var="cidade" items="${cidades}">
		<option value="${cidade.cidade_id }">${cidade.nome}</option>
	</c:forEach>
</c:if>









