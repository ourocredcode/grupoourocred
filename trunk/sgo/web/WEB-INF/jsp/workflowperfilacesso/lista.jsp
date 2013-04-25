<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table class="table table-striped table-bordered">
	<thead>
		<tr>
			<th>Empresa</th>
			<th>Organização</th>
			<th>Workflow</th>
			<th>Perfil</th>
		</tr>
	</thead>
	<tbody>	
		<c:forEach items="${workflowperfisacesso}" var="workflowperfilacesso">
			<tr>
				<td>${workflowperfilacesso.empresa.nome }</td>
				<td>${workflowperfilacesso.organizacao.nome }</td>
				<td>${workflowperfilacesso.workflow.nome }</td>
				<td>${workflowperfilacesso.perfil.nome}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>