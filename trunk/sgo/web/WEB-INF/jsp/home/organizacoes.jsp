<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${organizacoes == null}">
	<option value="">Selecione a organizacao</option>
</c:if>

<c:if test="${organizacoes != null}">
	<option value="">Selecione a organizacao</option>

	<c:forEach var="organizacao" items="${organizacoes}">
	<option value="${organizacao.organizacao_id}">${organizacao.nome}</option>
	</c:forEach>

</c:if>