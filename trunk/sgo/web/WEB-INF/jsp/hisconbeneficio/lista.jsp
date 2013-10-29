<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table class="table table-bordered">
	<thead>
		<tr>
			<th>Imagem</th>
			<th>Data solicitação</th>
			<th>Data solicitação Adm</th>
			<th>Data Envio Adm</th>
			<th>Supervisor</th>
			<th>Consultor</th>
			<th>Cliente</th>
			<th>Nascimento</th>
			<th>Cpf</th>
			<th>Número Benefício</th>
			<th>Status Atual</th>
			<th>Posição</th>
			<c:if test="${usuarioInfo.perfil.chave != 'Consultor' }">
				<th>Quantidade</th>
			</c:if>
		</tr>
	</thead>
	<tbody>	
		<c:forEach items="${hisconsBeneficio}" var="hiscon">
			<tr <c:if test="${hiscon.countHiscons >= 3 }"> class="error"</c:if>>
				<td>
					<c:if test="${hiscon.isEnviado}">
						<a href="<c:url value="/visualizaHiscon/${hiscon.hisconBeneficio_id}"/>"><img src='<c:url  value="/img/pdf.gif" />' border="0"/></a>
					</c:if>
				</td>
				<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm" type="date" value="${hiscon.created.time}" /></td>
				<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm" type="date" value="${hiscon.dataAdm.time}" /></td>	
				<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm" type="date" value="${hiscon.dataEnvio.time}" /></td>	
				<td>${hiscon.usuario.supervisorUsuario.apelido }</td>						
				<c:if test="${usuarioInfo.perfil.chave != 'Supervisor'}">
					<td>${hiscon.usuario.apelido }</td>
				</c:if>
				<c:if test="${usuarioInfo.perfil.chave == 'Supervisor'}">
					<td>
						<select id="hisconBeneficioConsultor" name="hisconBeneficioConsultor" class="input-small" onchange="return altera('usuario.usuario_id','${hiscon.hisconBeneficio_id}', this.value);">
							<option value="">Selecione um Consultor</option>
							<c:forEach var="consultor" items="${consultores }">
								<option value="${consultor.usuario_id }" <c:if test="${consultor.usuario_id == hiscon.usuario.usuario_id}">selected</c:if>>${consultor.apelido }</option>
							</c:forEach>
						</select>
					</td>
				</c:if>									
				<td><a data-toggle="modal" onclick="showcontatos(${hiscon.parceiroBeneficio.parceiroNegocio.parceiroNegocio_id});">${hiscon.parceiroBeneficio.parceiroNegocio.nome }</a></td>
				<td><fmt:formatDate pattern="dd/MM/yyyy" type="date" value="${hiscon.parceiroBeneficio.parceiroNegocio.dataNascimento.time }" /></td>
				<td>${hiscon.parceiroBeneficio.parceiroNegocio.cpf }</td>
				<td>${hiscon.parceiroBeneficio.numeroBeneficio }</td>				
				<td>
					<select id="hisconBeneficioStatus" class="input-medium" onchange="return altera('etapa.etapa_id','${hiscon.hisconBeneficio_id}', this.value);" >
						<c:forEach var="etapa" items="${hiscon.etapas }">
							<option value="${etapa.etapa_id }" <c:if test="${etapa.etapa_id == hiscon.etapa.etapa_id}">selected</c:if>>${etapa.nome }</option>
						</c:forEach>
					</select>
				</td>
				<td>
					<select id="hisconBeneficioPosicoes" class="input-medium" onchange="return altera('etapaPosicao.etapa_id','${hiscon.hisconBeneficio_id}', this.value);" >
						<c:forEach var="etapaPosicao" items="${hiscon.posicoes }">
							<option value="${etapaPosicao.etapa_id }" <c:if test="${etapaPosicao.etapa_id == hiscon.etapaPosicao.etapa_id}">selected</c:if>>${etapaPosicao.nome }</option>
						</c:forEach>
					</select>
				</td>
				<c:if test="${usuarioInfo.perfil.chave != 'Consultor' }">
					<td style="text-align: center;">
						<a href='<c:url value="/hisconbeneficio/detalhe/${hiscon.parceiroBeneficio.parceiroBeneficio_id}"/>'><h5><small>${hiscon.countHiscons }</small></h5></a>
					</td>
				</c:if>
			</tr>
		</c:forEach>
	</tbody>
</table>

<div id="myModal" class="modal hide fade" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div> 

<script type="text/javascript">
	function showcontatos(id){

		$("#myModal").load('<c:url value="/hisconbeneficio/parceironegocio/contatos" />', {'parceironegocio_id': id});
		$('#myModal').modal('show');

	}
</script>