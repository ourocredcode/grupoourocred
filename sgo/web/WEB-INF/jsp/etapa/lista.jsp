<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<table class="table table-striped table-bordered" id="lista">
	<thead>
		<tr>
			<th>Empresa</th>
			<th>Organização</th>							
			<th>Etapa</th>
			<th>Ordem Etapa</th>
			<th>Padrão</th>
			<th>Ativo</th>								
		</tr>
	</thead>
	<tbody>	
		<c:forEach items="${etapas }" var="etapa">
			<tr>
				<td>${etapa.empresa.nome }</td>
				<td>${etapa.organizacao.nome }</td>								
				<td>${etapa.nome }</td>
				<td>${etapa.ordemEtapa }</td>
				<td>${etapa.isPadrao }</td>
				<td>${etapa.isActive }</td>									
			</tr>
		</c:forEach>
	</tbody>
</table>