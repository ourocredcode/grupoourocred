<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

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

<table class="table table-bordered table-striped table-hover data-table">
	<thead>
		<tr>
			<th>Empresa</th>
			<th>Organização</th>
			<th>Banco</th>
			<th>Tabela</th>
			<th>Valor</th>
			<th>Percentual</th>
			<th>Comissao</th>
			<th>Inclusão</th>
			<th></th>
		</tr>
	</thead>
	<tbody>	
		<c:forEach items="${coeficientes}" var="coeficiente">
			<tr>
				<td>${coeficiente.empresa.nome }</td>
				<td>${coeficiente.organizacao.nome }</td>
				<td>${coeficiente.banco.nome }</td>
				<td>${coeficiente.tabela.nome }</td>
				<td>${coeficiente.valor }</td>
				<td>${coeficiente.percentualMeta }</td>
				<td>${coeficiente.percentualComissao }</td>
				<td><fmt:formatDate pattern="dd/MM/yyyy"  type="time" value="${coeficiente.created.time }" /></td>
				<td style="text-align:center;"><a href="#" onClick="return exclui(this,'${coeficiente.coeficiente_id}');">X</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>