<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:if test="${tabelas == null}">
	<option value="">Selecione uma Tabela</option>
</c:if>

<c:if test="${tabelas != null}">
	<option value="">Selecione um Produto</option>
	<c:forEach var="tabela" items="${tabelas}">
		<option value="${tabela.tabela_id}">${tabela.nome}</option>
	</c:forEach>
</c:if>