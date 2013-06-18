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
			<th>Quantidade</th>
		</tr>
	</thead>
	<tbody>	
		<c:forEach items="${hisconsBeneficio}" var="hiscon">
			<tr>
				<td>
					<c:if test="${hiscon.isEnviado}">
						<a href="<c:url value="/visualizaHiscon/${hiscon.hisconBeneficio_id}"/>"><img src='<c:url  value="/img/pdf.gif" />' border="0"/></a>
					</c:if>
				</td>
				<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm" type="date" value="${hiscon.created.time}" /></td>
				<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm" type="date" value="${hiscon.dataAdm.time}" /></td>			
				<td>${hiscon.usuario.nome }</td>									
				<td>${hiscon.parceiroBeneficio.parceiroNegocio.nome }</td>
				<td>${hiscon.parceiroBeneficio.parceiroNegocio.cpf }</td>
				<td>${hiscon.parceiroBeneficio.numeroBeneficio }</td>				
				<td>
					<select id="hisconBeneficioStatus" class="input-medium" onchange="return altera('etapa.etapa_id','${hiscon.hisconBeneficio_id}', this.value);" >
						<c:forEach var="etapa" items="${hiscon.etapas }">
							<option value="${etapa.etapa_id }" <c:if test="${etapa.etapa_id == hiscon.etapa.etapa_id}">selected</c:if>>${etapa.nome }</option>
						</c:forEach>
					</select>
				</td>
				<td>${hiscon.countHiscons }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>