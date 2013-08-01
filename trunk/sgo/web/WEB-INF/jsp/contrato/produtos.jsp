<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${produtos == null}">
	<option value="">Selecione um banco...</option>
</c:if>

<c:if test="${produtos != null}">
	<option value="0">Selecione um produto...</option>
	<c:forEach var="produto" items="${produtos}">
		<option value="${produto.produto_id}" <c:if test="${contrato.produto.produto_id == produto.produto_id}">selected</c:if>>${produto.nome}</option>
	</c:forEach>
</c:if>