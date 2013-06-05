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
		<c:forEach items="${workflows }" var="workflow">
			<tr>
				<td>${workflow.empresa.nome }</td>
				<td>${workflow.organizacao.nome }</td>
				<td>${workflow.nome }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>