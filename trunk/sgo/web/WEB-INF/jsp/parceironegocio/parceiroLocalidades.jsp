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
					<th>Ação</th>
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
						<td>${parceiroLocalidade.numero }</td>
						<td>${parceiroLocalidade.complemento }</td>
						<td>
							<c:choose>
								<c:when test="${parceiroLocalidade.isAssinatura}"> Assinatura! </c:when>
								<c:when test="${parceiroLocalidade.isResidencial}"> Residencial! </c:when>
								<c:otherwise> Nem um nem outro! </c:otherwise>
							</c:choose>
						</td>
						<td>
							<button type="button" class="btn btn-primary">Alterar</button>
							
						</td>
						<td>
							<button type="button" class="btn btn-primary">Excluir</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</div>
</c:if>

