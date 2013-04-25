<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:if test="${empty formularios && empty contratos}">
	<table id="mytable" style="width:633;">
		<tr>
			<th class="titulo">
				Nenhuma solicitação encontrada
			</th>
		</tr>
	</table>
</c:if>

<c:if test="${not empty formularios}">
	<table id="mytable" style="width:780;">
	<tr>
		<th class="titulo" colspan="12">
			Resultado Busca Formulários:
		</th>
	</tr>
	<tr>
		<th scope="col" style="width:80;">
			Data
		</th>
		<th scope="col" style="width:120;">
			Consultor
		</th>
		<th scope="col" style="width:400;">
			Nome Cliente
		</th>
		<th scope="col" style="width:80;">
		</th>
		<th scope="col" style="width:100;text-align: center">
			Pós Venda
		</th>
	</tr>	
	<c:forEach items="${formularios}" var="formulario">
		<c:if test="${formulario.posvenda.status == 'pendente' || formulario.posvenda.status == 'reprovado'}">
			<c:set var="alert" value="alert"></c:set>
		</c:if>
		<c:if test="${formulario.posvenda.status == 'aprovado' || empty formulario.posvenda.status}">
			<c:set var="alert" value="label_txt"></c:set>
		</c:if>
		<tr>
			<td class="${alert}"><fmt:formatDate pattern="dd/MM/yyyy" type="time" value="${formulario.data.time }" /></td>
			<td class="${alert}">${formulario.consultor.nome }</td>
			<td class="${alert}">${formulario.cliente.nome }</td>
			<td class="${alert}" style="text-align: center"><a href="<c:url value="/visualiza/${formulario.id}"/>">Visualizar</a></td>
			<td class="${alert}" style="text-align: center">${formulario.posvenda.status }</td>
		</tr>
	</c:forEach>
	</table>
	<br/>
</c:if>