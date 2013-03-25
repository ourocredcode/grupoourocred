<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table class="table table-striped table-bordered">
	<thead>
		<tr>
			<th>Empresa</th>
			<th>Organização</th>
			<th>ColunaBd</th>
		</tr>
	</thead>
	<tbody>	
		<c:forEach items="${elementosBd}" var="elementoBd">
			<tr>
				<td>${elementoBd.empresa.nome }</td>
				<td>${elementoBd.organizacao.nome }</td>
				<td>${elementoBd.nomeColunaBd }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>