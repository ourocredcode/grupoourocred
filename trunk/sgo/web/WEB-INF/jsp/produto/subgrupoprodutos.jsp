<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${subGrupoProdutos == null}">
	<option value="">Selecione um Grupo Produto</option>
</c:if>

<c:if test="${subGrupoProdutos != null}">
	<option value="">Selecione um Grupo Produto...</option>
	<c:forEach items="${subGrupoProdutos} " var="subGrupoProduto">
		<option value="${subGrupoProduto.subGrupoProduto_id } ">${subGrupoProduto.nome}</option>
	</c:forEach>
</c:if>