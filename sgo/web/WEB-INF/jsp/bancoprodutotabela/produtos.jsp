<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${produtos == null}">
	<option value="">Selecione o Banco</option>
</c:if>

<c:if test="${produtos != null}">
	<option value="">Selecione o Banco</option>
	<c:forEach items="${produtos }" var="prod">
		<option value="${prod.produto_id }">${prod.nome}</option>
	</c:forEach>
</c:if>