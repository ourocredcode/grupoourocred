<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<table class="table table-striped table-bordered">
	<thead>
		<tr>
			<th>Empresa</th>
			<th>Organização</th>
			<th>Nome</th>
		</tr>
	</thead>
	<tbody>	
		<c:forEach items="${gruposProduto }" var="grupoProduto">
			<tr>
				<td>${grupoProduto.empresa.nome }</td>
				<td>${grupoProduto.organizacao.nome }</td>
				<td>${grupoProduto.nome }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>