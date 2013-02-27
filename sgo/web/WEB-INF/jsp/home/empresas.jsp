<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${empresas == null}">
	<option value="">Selecione a empresa</option>
</c:if>

<c:if test="${empresas != null}">

	<option value="">Selecione a empresa</option>

	<c:forEach var="empresa" items="${empresas}">
	<option value="${empresa.empresa_id}">${empresa.nome}</option>
	</c:forEach>

</c:if>