<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<table class="table table-striped table-bordered" id="lista">
	<thead>
		<tr>
			<th>Empresa</th>
			<th>Organização</th>
			<th>Banco</th>
			<th>Tabela</th>
			<th>Nome</th>
			<th>Inclusão</th>
			<th>Percentual</th>
			<th></th>
		</tr>
	</thead>
	<tbody>	
		<c:forEach items="${coeficientes}" var="coeficiente">
			<tr>
				<td>${coeficiente.empresa.nome }</td>
				<td>${coeficiente.organizacao.nome }</td>
				<td>${coeficiente.tabela.banco.nome }</td>
				<td>${coeficiente.tabela.nome }</td>
				<td>${coeficiente.valor }</td>
				<td>${coeficiente.percentualMeta }</td>
				<td><fmt:formatDate pattern="dd/MM/yyyy"  type="time" value="${coeficiente.created.time }" /></td>
				<td style="text-align: center;"><a href="#" onClick="return exclui(this,'${coeficiente.coeficiente_id}');">X</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>