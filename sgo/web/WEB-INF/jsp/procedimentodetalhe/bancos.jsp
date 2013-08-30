<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${bancos == null}">
	<option value="">Selecione o Banco</option>
</c:if>

<c:if test="${bancos != null}">
	<option value="">Selecione o Banco</option>
	<c:forEach items="${bancos }" var="banco">
		<option value="${banco.banco_id }">${banco.nome }</option>
	</c:forEach>
</c:if>