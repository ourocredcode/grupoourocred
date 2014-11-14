<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
					
<c:if test="${not empty parceiroLocalidades}">
	<div id="enderecos" style="margin-top: 15px;">
		<table class="table table-striped table-bordered" id="lista">
			<thead>
				<tr>
					<th>Cep</th>
					<th>Bairro</th>
					<th>Cidade</th>
					<th>Tipo</th>
					<th>Endereço</th>
					<th>Número</th>
					<th>Complemento</th>
					<th>Ponto Referência</th>
					<th>Tipo</th>
					<!-- 
					<th>Excluir</th>
					 -->
				</tr>
			</thead>
			<tbody>	
				<c:forEach items="${parceiroLocalidades}" var="parceiroLocalidade">
					<tr>
						<td>${parceiroLocalidade.localidade.cep }</td>
						<td>${parceiroLocalidade.localidade.bairro }</td>
						<td>${parceiroLocalidade.localidade.cidade.nome }</td>
						<td>${parceiroLocalidade.localidade.tipoLocalidade.nome }</td>
						<td>${parceiroLocalidade.localidade.endereco }</td>
						<td><input type="text" id="parceiroLocalidadeNumeroLista" value="${parceiroLocalidade.numero }" class="input-small" onChange="return altera(this,'numero','${parceiroLocalidade.parceiroLocalidade_id}', this.value);"/></td>
						<td><input type="text" id="parceiroLocalidadeComplementoLista" value="${parceiroLocalidade.complemento }"  maxlength="50" class="input-small" onChange="return altera(this,'complemento','${parceiroLocalidade.parceiroLocalidade_id}', this.value);"/></td>
						<td><input type="text" id="parceiroLocalidadePontoReferenciaLista" value="${parceiroLocalidade.pontoReferencia }" maxlength="50" class="input-medium" onChange="return altera(this,'pontoReferencia','${parceiroLocalidade.parceiroLocalidade_id}', this.value);"/></td>
						<td>${parceiroLocalidade.tipoEndereco.nome }</td>
						<!-- 
						<td>
						<select id="parceiroLocalidadeTipoEnderecoLista" onChange="return alteraDESATIVADO(this,'tipoEndereco','${parceiroLocalidade.parceiroLocalidade_id}', this.value);" class="input-small">
							<option value="0" selected="selected">Selecione</option>
								<c:forEach var="tipoEndereco" items="${tiposEndereco}">
									<option value="${tipoEndereco.tipoEndereco_id}" <c:if test="${parceiroLocalidade.tipoEndereco.tipoEndereco_id eq tipoEndereco.tipoEndereco_id}">SELECTED</c:if>>${tipoEndereco.nome}</option>
								</c:forEach>
						</select>
						</td>
						
						<td style="text-align: center;">
							<button type="button" class="btn btn-danger btn-mini" onClick="return excluiDESATIVADO(this,'${parceiroLocalidade.parceiroLocalidade_id}');">Excluir</button>
						</td>
						 -->
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</c:if>

