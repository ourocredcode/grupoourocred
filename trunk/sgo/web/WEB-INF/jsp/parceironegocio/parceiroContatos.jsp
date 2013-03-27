<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty parceiroContatos}">					
	<table class="table table-striped table-bordered" id="lista">
		<thead>
			<tr>
				<th>TipoContato</th>
				<th>Contato</th>
				<th>Excluir</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty parceiroContatos}">	
				<c:forEach items="${parceiroContatos}" var="parceiroContato">
					<tr>
						<td>
							<select id="parceiroContatoTipoContatoLista" onChange="return altera(this,'tipoContato','${parceiroContato.parceiroContato_id}', this.value);" class="input-small">
								<option value="0" selected="selected">Selecione</option>
								<c:forEach var="tipoContato" items="${tiposContato}">
									<option value="${tipoContato.tipoContato_id}" <c:if test="${parceiroContato.tipoContato.tipoContato_id eq tipoContato.tipoContato_id}">SELECTED</c:if>>${tipoContato.chave}</option>
								</c:forEach>
							</select>
						</td>
						<td><input type="text" id="parceiroContatoNomeLista" value="${parceiroContato.nome }" class="input-small" onChange="return altera(this,'nome','${parceiroContato.parceiroContato_id}', this.value);"/></td>
						<td style="text-align: center;">
							<button type="button" class="btn btn-danger btn-mini" onClick="return exclui(this,'${parceiroContato.parceiroContato_id}');">Excluir</button>
						</td>
					</tr>
				</c:forEach>
			</c:if>
			
			<tr>
				<td>
					<select id="parceiroContatoTipoContatoNovo" class="input-small">
						<option value="0" selected="selected">Selecione</option>
						<c:forEach var="tipoContato" items="${tiposContato}">
							<option value="${tipoContato.tipoContato_id}" >${tipoContato.chave}</option>
						</c:forEach>
					</select>
				</td>
				<td><input type="text" id="parceiroContatoNomeNovo" value="${parceiroContato.nome }" class="input-small"/></td>
				<td style="text-align: center;">
					<button type="button" class="btn btn-mini" id="bttParceiroContatoNovo" onClick="return salvaContato();">Novo</button>
				</td>
			</tr>
			
		</tbody>
	</table>
</c:if>
