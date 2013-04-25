<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<table class="table table-striped table-bordered">
	<thead>
		<tr>
			<th>Empresa</th>
			<th>Organização</th>
			<th>Worklflow Etapa</th>
			<th>Worklflow Próxima</th>			
			<th>Sequência</th>
			<th>Padrão</th>
			<th>Ativo</th>
		</tr>
	</thead>
	<tbody>	
		<c:forEach items="${workflowTransicoes }" var="workflowTransicao">
			<tr>
				<td>${workflowTransicao.empresa.nome }</td>
				<td>${workflowTransicao.organizacao.nome }</td>
				<td>${workflowTransicao.workflowEtapa.nome }</td>
				<td>${workflowTransicao.workflowEtapaProximo.nome }</td>
				<td>${workflowTransicao.sequencia }</td>
				<td>${workflowTransicao.isPadrao }</td>
				<td>${workflowTransicao.isActive }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>