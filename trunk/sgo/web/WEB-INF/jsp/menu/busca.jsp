<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="/WEB-INF/lib/formattag.jar" prefix="f" %>

<script type="text/javascript">

$(document).ready(function() {
	
	
	$('.data-table').dataTable({
		"bJQueryUI": true,
		"sPaginationType": "full_numbers",
		"sDom": '<""l>t<"F"fp>'
	});
	
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	$("span.icon input:checkbox, th input:checkbox").click(function() {
		var checkedStatus = this.checked;
		var checkbox = $(this).parents('.widget-box').find('tr td:first-child input:checkbox');		
		checkbox.each(function() {
			this.checked = checkedStatus;
			if (checkedStatus == this.checked) {
				$(this).closest('.checker > span').removeClass('checked');
			}
			if (this.checked) {
				$(this).closest('.checker > span').addClass('checked');
			}
		});
	});
	
	
	
});

</script>


<c:if test="${not empty contratos}">
	
	<table class="table table-bordered table-striped table-hover" style="width: 500px;float: right;">
		<tr>
			<th >Total Contrato</th>
			<th >Total C. Líquido</th>
			<th >Total Dívida</th>
			<th >Total Líquido</th>
			<th >Total Meta</th>
		</tr>
		<tr>
			<td>R$ <fmt:formatNumber type="NUMBER" value="${totalValorContratos}" minFractionDigits="2" /></td>
			<td>R$ <fmt:formatNumber type="NUMBER" value="${totalContratoLiquido}" minFractionDigits="2" /></td>
			<td>R$ <fmt:formatNumber type="NUMBER" value="${totalValorDivida}" minFractionDigits="2" /></td>
			<td>R$ <fmt:formatNumber type="NUMBER" value="${totalValorLiquido}" minFractionDigits="2" /></td>
			<td>R$ <fmt:formatNumber type="NUMBER" value="${totalValorMeta}" minFractionDigits="2" /></td>
		</tr>
	</table>
	
	<table class="table table-bordered table-striped table-hover data-table"> 
		<thead>	
			<tr>
				<th >
					Data
				</th>
				<th >
					Supervisor
				</th>
				<th >
					Consultor
				</th>
				<th >
					Cliente
				</th>
				<th >
					Cpf
				</th>
				<th >
					Banco:
				</th>
				<th >
					Produto:
				</th>
				<th >
					Comprado:
				</th>
				<th >
					Parcela
				</th>
				<th >
					Coeficiente
				</th>
				<th >
					Prazo
				</th>
				<th >
					Vl Contrato
				</th>
				<th >
					Vl C Liquido
				</th>
				<th >
					Dívida
				</th>
				<th >
					Liquido
				</th>
				<th >
					Meta
				</th>
				<th >
					Status
				</th>
			</tr>
		</thead>
		<tbody>		
			<c:forEach items="${contratos}" var="contrato">
				<tr>
					<td >
						<fmt:formatDate value="${contrato.formulario.created.time}" pattern="dd/MM" />
					</td>
					<td >
						${contrato.usuario.supervisorUsuario.apelido }
					</td>
					<td >
						${fn:substring(contrato.usuario.apelido , 0, 18)} ...
					</td>
					<td >
						${fn:substring(contrato.formulario.parceiroNegocio.nome, 0, 18)} ...
					</td>
					<td >
						${contrato.formulario.parceiroNegocio.cpf }
					</td>
					<td >
						${contrato.banco.nome }
					</td>
					<td >
						${contrato.produto.nome }
					</td>
					<td >
						${contrato.recompraBanco.nome }
					</td>
					<td >
						${contrato.valorParcela }
					</td>
					<td >
						<fmt:formatNumber type="number" pattern="#.#####" value="${contrato.coeficiente.valor }" />
					</td>
					<td >
						${contrato.prazo }
					</td>
					<td >
						${contrato.valorContrato }
					</td>
					<td class="${alert}">
						<c:choose>
							<c:when test="${contrato.produto.nome eq 'MARGEM LIMPA' || contrato.produto.nome eq 'RECOMPRA RMC' || contrato.produto.nome eq 'RECOMPRA INSS'}">
								<fmt:formatNumber type="NUMBER" value="${contrato.valorContrato }" minFractionDigits="2" />
							</c:when>
							<c:when test="${contrato.produto.nome eq 'RETENÇÃO' || contrato.produto.nome eq 'REFINANCIAMENTO'}">
								<fmt:formatNumber type="NUMBER" value="${contrato.valorLiquido }" minFractionDigits="2" />
							</c:when>
						</c:choose>
					</td>
					<td >
						${contrato.valorDivida }
					</td>
					<td >
						${contrato.valorLiquido }
					</td>
					<td >
						${contrato.valorMeta }
					</td>
					<td >
						<a href="<c:url value="/contrato/status/${contrato.contrato_id}"/>">${contrato.etapa.nome }</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>	
	</table>
</c:if>