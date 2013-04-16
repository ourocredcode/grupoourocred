<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${produtos == null}">
	<option value="0">Selecione um Banco</option>
</c:if>

<c:if test="${produtos != null}">
	<option value="0">Selecione um banco...</option>
	<c:forEach var="produto" items="${produtos}">
		<option value="${produto.produto_id}">${produto.nome}</option>
	</c:forEach>
</c:if>