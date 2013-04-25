<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table class="table table-striped table-bordered">
	<thead>
		<tr>
			<th>Imagem</th>
			<th>Data solicitação</th>
			<th>Data solicitação Adm</th>
			<th>Consultor</th>
			<th>Cliente</th>
			<th>Cpf</th>
			<th>Número Benefício</th>
			<th>Status Atual</th>
		</tr>
	</thead>
	<tbody>	
		<c:forEach items="${hisconsBeneficio}" var="hiscon">
			<tr>
				<td>${hiscon.caminhoArquivo }</td>
				<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm" type="time" value="${hiscon.created.time}" /></td>
				<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm" type="time" value="${hiscon.dataAdm.time}" /></td>			
				<td>${hiscon.usuario.nome }</td>									
				<td>${hiscon.parceiroBeneficio.parceiroNegocio.nome }</td>
				<td>${hiscon.parceiroBeneficio.parceiroNegocio.cpf }</td>
				<td>${hiscon.parceiroBeneficio.numeroBeneficio }</td>				
				<td>${hiscon.workflowEtapa.nome }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>