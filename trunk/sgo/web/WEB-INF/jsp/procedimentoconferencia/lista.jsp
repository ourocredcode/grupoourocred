<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table class="table table-striped table-bordered">
	<thead>
		<tr>
			<th>Empresa</th>
			<th>Organização</th>
			<th>Tipo Procedimento</th>
			<th>Nome</th>
		</tr>
	</thead>
	<tbody>	
		<c:forEach items="${procedimentosConferencia }" var="procedimentoConferencia">
			<tr>
				<td>${procedimentoConferencia.empresa.nome }</td>
				<td>${procedimentoConferencia.organizacao.nome }</td>
				<td>${procedimentoConferencia.tipoProcedimento.nome }</td>								
				<td>${procedimentoConferencia.nome }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>