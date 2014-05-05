<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	<h3 id="myModalLabel">Contatos - ${parceiroNegocio.nome }</h3>
</div>

<div class="modal-body">
	  
	<c:if test="${not empty parceiroContatos}">					
	
		<table class="table table-striped table-bordered" id="lista">
			<thead>
				<tr>
					<th>TipoContato</th>
					<th>Contato</th>
				</tr>
			</thead>
			<tbody>
	
			<c:if test="${not empty parceiroContatos}">	
				<c:forEach items="${parceiroContatos}" var="parceiroContato">
					<c:if test="${parceiroContato.isActive}">
						<tr>
							<td>
								<select id="parceiroContatoTipoContatoLista" class="input-small">
									<option value="0" selected="selected">Selecione</option>
									<c:forEach var="tipoContato" items="${tiposContato}">
										<option value="${tipoContato.tipoContato_id}" <c:if test="${parceiroContato.tipoContato.tipoContato_id eq tipoContato.tipoContato_id}">SELECTED</c:if>>${tipoContato.chave}</option>
									</c:forEach>
								</select>
							</td>
							<td><input type="text" id="parceiroContatoNomeLista" value="${parceiroContato.nome }" class="input-small" /></td>
						</tr>
					</c:if>
				</c:forEach>
			</c:if>
	
			</tbody>
		</table>
	</c:if>

</div>


