<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<table class="table table-striped table-bordered">
	<thead>
		<tr>
			<th>Empresa</th>
			<th>Organização</th>
			<th>Worklflow</th>
			<th>Worklflow Etapa</th>
			<th>Padrão</th>
			<th>Ativo</th>
		</tr>
	</thead>
	<tbody>	
		<c:forEach items="${workflowEtapas }" var="workflowEtapa">
			<tr>
				<td>${workflowEtapa.empresa.nome }</td>
				<td>${workflowEtapa.organizacao.nome }</td>
				<td>${workflowEtapa.workflow.nome }</td>
				<td>${workflowEtapa.nome }</td>
				<td>${workflowEtapa.ordemEtapa }</td>
				<td>${workflowEtapa.isPadrao }</td>
				<td>${workflowEtapa.isActive }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>