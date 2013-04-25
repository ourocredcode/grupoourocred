<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="/WEB-INF/lib/formattag.jar" prefix="f" %>

<c:if test="${empty contratos && empty formularios}">
	<table id="mytable" style="width:580;">
		<tr>
			<th class="titulo">
				Nenhum contrato encontrado
			</th>
		</tr>
	</table>
</c:if>

<c:if test="${not empty contratos}">
	<table id="mytable" style="width:1340px;">
		<tr>
			<th colspan="11" class="titulo">
			</th>
			<th class="titulo">
				T. Contratos
			</th>
			<th class="titulo">
				T. Dívida
			</th>
			<th class="titulo">
				T. Seguro
			</th>
			<th class="titulo">
				T. Líquido
			</th>
			<th class="titulo">
				T. Meta
			</th>
			<th class="titulo" colspan="3">
			</th>
		</tr>
		<tr>
			<th colspan="11">
			</th>
			<td>
				R$ <fmt:formatNumber type="NUMBER" value="${totalValorContratos}" minFractionDigits="2" />
			</td>
			<td>
				R$ <fmt:formatNumber type="NUMBER" value="${totalValorDivida}" minFractionDigits="2" />
			</td>
			<td>
				R$ <fmt:formatNumber type="NUMBER" value="${totalValorSeguro}" minFractionDigits="2" />
			</td>
			<td>
				R$ <fmt:formatNumber type="NUMBER" value="${totalValorLiquido}" minFractionDigits="2" />
			</td>
			<td>
				
				R$ <fmt:formatNumber type="NUMBER" value="${totalValorMeta}" minFractionDigits="2" />
			</td>
			<th colspan="3">
			</th>
		</tr>
		<tr>
			<th class="titulo" colspan="19">
				<c:choose>
					<c:when test="${countContratos > 1}">Resultado Busca Contratos: ${countContratos} contratos encontrados.</c:when>
					<c:otherwise>Resultado Busca Contratos: ${countContratos} contrato encontrado.</c:otherwise>
				</c:choose>
				Total de Clientes : ${countClientes }	
			</th>
		</tr>
		<tr>
			<th scope="col" style="width:70px">
				Data
			</th>
			<th scope="col" style="width:55px">
				Supervisor
			</th>
			<th scope="col" style="width:65px">
				Consultor
			</th>
			<th scope="col" style="width:135px">
				Cliente
			</th>
			<th scope="col" style="width:80px">
				CPF
			</th>
			<th scope="col" style="width:75px">
				Banco
			</th>
			<th scope="col" style="width:100px">
				Produto
			</th>
			<th scope="col" style="width:70px">
				Bc Comprado
			</th>
			<th scope="col" style="width:60px">
				Parcela
			</th>
			<th scope="col" style="width:50px">
				Coef.
			</th>
			<th scope="col" style="width:35px">
				Prazo
			</th>
			<th scope="col" style="width:70px">
				Contrato
			</th>
			<th scope="col" style="width:70px">
				Dívida
			</th>
			<th scope="col" style="width:70px">
				Seguro
			</th>
			<th scope="col" style="width:70px">
				Valor Líquido
			</th>
			<th scope="col" style="width:60px">
				Valor Meta
			</th>
			<th scope="col" style="width: 105px">
				Status
			</th>
			<c:choose>
				<c:when test="${buscaDataQuitacao == true && (buscaDtBoletoAtua == true || buscaDtAverbacaoAtua == true)}">
					<th scope="col" style="width: 65px">Dt Quitação</th>
					<th scope="col" style="width: 65px">Últ Atuação</th>
				</c:when>
				<c:when test="${buscaDataQuitacao == true}">
					<th scope="col" style="width: 100px">Data Quitação</th>
				</c:when>
				<c:when test="${buscaDtBoletoAtua == true || buscaDtAverbacaoAtua == true}">
					<th scope="col" style="width: 100px">Última Atuação</th>
				</c:when>
				<c:when test="${buscaDtBoletoPrevisao == true || buscaDtAverbacaoPrevisao == true }">
					<th scope="col" style="width: 100px">Previsão</th>
				</c:when>
				<c:when test="${buscaDtBoletoChegada == true}">
					<th scope="col" style="width: 100px">Chegada</th>
				</c:when>
				<c:when test="${buscaDtBoletoVencimento == true}">
					<th scope="col" style="width: 100px">Vencimento</th>
				</c:when>
				<c:when test="${buscaDtBoletoProximaAtuacao == true || buscaDtAverbacaoProximaAtuacao == true}">
					<th scope="col" style="width: 100px">Próx Atuacao</th>
				</c:when>
				<c:otherwise><th scope="col" style="width: 100px">PósVenda</th></c:otherwise>
			</c:choose>
		</tr>
		<c:forEach items="${contratos}" var="contrato">

			<c:set var="alert" value="label_txt"></c:set>

			<c:if test="${contrato.formulario.posvenda.status == 'pendente'
							|| contrato.status.status == 'Pendente Administrativo'
							|| contrato.status.status == 'Pendente Agendamento'
							|| contrato.status.status == 'Pendente Banco'
							|| contrato.status.status == 'Pendente Coeficiente'
							|| contrato.status.status == 'Pendente Conferência'
							|| contrato.status.status == 'Recalcular'}">
				<c:set var="alert" value="alert"></c:set>
			</c:if>

			<tr>
				<td class="${alert}">
					<fmt:formatDate pattern="dd/MM/yyyy HH:mm" type="time" value="${contrato.formulario.data.time }" />
				</td>
				<td class="${alert}"> 
					${contrato.formulario.consultor.supervisor }
				</td>
				<td class="${alert}"> 
					${contrato.formulario.consultor.nome }
				</td>
				<td class="${alert}">
					${fn:substring(contrato.formulario.cliente.nome, 0, 18)} ...
				</td>
				<td class="${alert}">
					<f:formatString pattern="###.###.###-##">${contrato.formulario.cliente.cpf}</f:formatString>
				</td>
				<td class="${alert}">
					${fn:substring(contrato.banco, 0, 10)} ...
				</td>
				<td class="${alert}">
					${contrato.produto }
				</td>
				<td class="${alert}">
					${contrato.bancoComprado }
				</td>
				<td class="${alert}">
					R$ <fmt:formatNumber type="NUMBER" value="${contrato.valorParcela }" minFractionDigits="2" />
				</td>
				<td class="${alert}">
					${contrato.coeficiente }
				</td>
				<td class="${alert}">
					${contrato.prazo }
				</td>
				<td class="${alert}">
					R$ <fmt:formatNumber type="NUMBER" value="${contrato.valorContrato }" minFractionDigits="2" />
				</td>
				<td class="${alert}">
					R$ <fmt:formatNumber type="NUMBER" value="${contrato.valorDivida }" minFractionDigits="2" />
				</td>
				<td class="${alert}">
					R$ <fmt:formatNumber type="NUMBER" value="${contrato.valorSeguro }" minFractionDigits="2" />
				</td>
				<td class="${alert}">
					R$ <fmt:formatNumber type="NUMBER" value="${contrato.valorLiquido }" minFractionDigits="2" />
				</td>
				<td class="${alert}">
					R$ <fmt:formatNumber type="NUMBER" value="${contrato.valorMeta }" minFractionDigits="2" />
				</td>
				<td class="${alert}">
					<a href="<c:url value="/cadastroStatus/${contrato.id}"/>">
					<c:choose>
						<c:when test="${contrato.status.status == '' || empty contrato.status || contrato.status.status == null}" >
							<c:out value="Sem status" />
						</c:when>
						<c:otherwise>
							<c:out value="${contrato.status.status }" />
						</c:otherwise>
					</c:choose>
					</a>
				</td>
			
				<c:choose>
					<c:when test="${buscaDataQuitacao == true && buscaDtAverbacaoAtua == true}">
						<td class="${alert}">
							<fmt:formatDate pattern="dd/MM/yyyy" type="time" value="${contrato.status.dataQuitacao.time }" />
						</td>
						<td class="${alert}">
							<fmt:formatDate pattern="dd/MM/yyyy" type="time" value="${contrato.averbacao.dataAtuacao.time }" />
						</td>
					</c:when>
					<c:when test="${buscaDataQuitacao == true}">
						<td class="${alert}">
							<fmt:formatDate pattern="dd/MM/yyyy" type="time" value="${contrato.status.dataQuitacao.time }" />
						</td>
					</c:when>
					<c:when test="${buscaDtBoletoAtua == true}">
						<td class="${alert}">
							<fmt:formatDate pattern="dd/MM/yyyy" type="time" value="${contrato.boleto.dataAtuacao.time }" />
						</td>	
					</c:when>
					<c:when test="${buscaDtAverbacaoAtua == true}">
						<td class="${alert}">
							<fmt:formatDate pattern="dd/MM/yyyy" type="time" value="${contrato.averbacao.dataAtuacao.time }" />
						</td>	
					</c:when>
					<c:when test="${buscaDtBoletoPrevisao == true}">
						<td class="${alert}">
							<fmt:formatDate pattern="dd/MM/yyyy" type="time" value="${contrato.boleto.dataPrevisao.time }" />
						</td>	
					</c:when>
					<c:when test="${buscaDtAverbacaoPrevisao == true}">
						<td class="${alert}">
							<fmt:formatDate pattern="dd/MM/yyyy" type="time" value="${contrato.averbacao.dataPrevisao.time }" />
						</td>	
					</c:when>
					<c:when test="${buscaDtBoletoProximaAtuacao == true}">
						<td class="${alert}">
							<fmt:formatDate pattern="dd/MM/yyyy" type="time" value="${contrato.boleto.dataProximaAtuacao.time }" />
						</td>	
					</c:when>
					<c:when test="${buscaDtAverbacaoProximaAtuacao == true}">
						<td class="${alert}">
							<fmt:formatDate pattern="dd/MM/yyyy" type="time" value="${contrato.averbacao.dataProximaAtuacao.time }" />
						</td>	
					</c:when>
					<c:when test="${buscaDtBoletoChegada == true}">
						<td class="${alert}">
							<fmt:formatDate pattern="dd/MM/yyyy" type="time" value="${contrato.boleto.dataChegada.time }" />
						</td>							
					</c:when>
					<c:when test="${buscaDtBoletoVencimento == true}">
						<td class="${alert}">
							<fmt:formatDate pattern="dd/MM/yyyy" type="time" value="${contrato.boleto.dataVencimento.time }" />
						</td>	
					</c:when>
					<c:otherwise>
						<td class="${alert}">${contrato.formulario.posvenda.status }</td>
					</c:otherwise>
				</c:choose>
				
			</tr>
		</c:forEach>
	</table>
</c:if>