<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table class="table table-striped table-bordered">
	<thead>
		<tr>
			<th>Empresa</th>
			<th>Organização</th>
			<th>Nome</th>
			<th>Chave</th>
		</tr>
	</thead>
	<tbody>	
		<c:forEach items="${tiposDadosBd}" var="tipoDado">
			<tr>
				<td>${tipoDado.empresa.nome }</td>
				<td>${tipoDado.organizacao.nome }</td>
				<td>${tipoDado.nome }</td>
				<td>${tipoDado.chave }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>