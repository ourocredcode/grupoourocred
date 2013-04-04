<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
					
<c:if test="${not empty parceiroLocalidades}">
	<div id="enderecos" style="margin-top: 15px;">
		<table class="table table-striped table-bordered" id="lista">
			<thead>
				<tr>
					<th>Cep</th>
					<th>Bairro</th>
					<th>Cidade</th>
					<th>Endereço</th>
					<th>Número</th>
					<th>Complemento</th>
					<th>Tipo</th>
					<th>Excluir</th>
				</tr>
			</thead>
			<tbody>	
				<c:forEach items="${parceiroLocalidades}" var="parceiroLocalidade">
					<tr>
						<td>${parceiroLocalidade.localidade.cep }</td>
						<td>${parceiroLocalidade.localidade.bairro }</td>
						<td>${parceiroLocalidade.localidade.cidade.nome }</td>
						<td>${parceiroLocalidade.localidade.endereco }</td>
						<td><input type="text" id="parceiroLocalidadeNumeroLista" value="${parceiroLocalidade.numero }" class="input-mini" onChange="return altera(this,'numero','${parceiroLocalidade.parceiroLocalidade_id}', this.value);"/></td>
						<td><input type="text" id="parceiroLocalidadeComplementoLista" value="${parceiroLocalidade.complemento }" class="input-mini" onChange="return altera(this,'complemento','${parceiroLocalidade.parceiroLocalidade_id}', this.value);"/></td>
						<td>
						<select id="parceiroLocalidadeTipoEnderecoLista" onChange="return altera(this,'tipoEndereco','${parceiroLocalidade.parceiroLocalidade_id}', this.value);" class="input-small">
							<option value="0" selected="selected">Selecione</option>
								<c:forEach var="tipoEndereco" items="${tiposEndereco}">
									<option value="${tipoEndereco.tipoEndereco_id}" <c:if test="${parceiroLocalidade.tipoEndereco.tipoEndereco_id eq tipoEndereco.tipoEndereco_id}">SELECTED</c:if>>${tipoEndereco.nome}</option>
								</c:forEach>
						</select>
						</td>
						<td style="text-align: center;">
							<button type="button" class="btn btn-danger btn-mini" onClick="return exclui(this,'${parceiroLocalidade.parceiroLocalidade_id}');">Excluir</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</div>
</c:if>

