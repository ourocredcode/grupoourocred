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
	<table class="table table-bordered table-striped table-hover data-table" style="font-size: 12px">
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
					Banco Comprado:
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
				<th >
					Pós Venda
				</th>
			</tr>
		</thead>
		<tbody>		
			<c:forEach items="${contratos}" var="contrato">
				<tr>
					<td >
						<fmt:formatDate value="${contrato.formulario.created.time}" pattern="dd/MM/yyyy" />
					</td>
					<td >
						${contrato.usuario.supervisorUsuario.nome }
					</td>
					<td >
						${contrato.usuario.nome }
					</td>
					<td >
						${contrato.formulario.parceiroNegocio.nome }
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
					<td >
						PÓS VENDA
					</td>
				</tr>
			</c:forEach>
		</tbody>	
	</table>
</c:if>