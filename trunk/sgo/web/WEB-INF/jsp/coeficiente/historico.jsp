<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row-fluid">
	<div class="span12">
		Banco: ${bancoNome }
	</div>
</div>
<div class="row-fluid">
	<div class="span12">
		Produto: ${produtoNome }
	</div>
</div>

<div class="row-fluid">

	<div class="span6">
		<label for="ano">Ano:</label>
		<select id="ano" class="span12">
			<option value="0">Ano</option>
			<c:forEach items="${anos}" var="ano">
				<option value="${ano}">${ano}</option>
			</c:forEach>
		</select>
	</div>
	<div class="span6">
		<label for="mes">Mês:</label>
		<select id="mes" class="span12">
			<option value="0">Mês</option>
			<c:forEach items="${meses}" var="mes">
				<option value="${mes}">
					<c:if test="${mes == 0}">Janeiro</c:if>
					<c:if test="${mes == 1}">Fevereiro</c:if>
					<c:if test="${mes == 2}">Março</c:if>
					<c:if test="${mes == 3}">Abril</c:if>
					<c:if test="${mes == 4}">Maio</c:if>
					<c:if test="${mes == 5}">Junho</c:if>
					<c:if test="${mes == 6}">Julho</c:if>
					<c:if test="${mes == 7}">Agosto</c:if>
					<c:if test="${mes == 8}">Setembro</c:if>
					<c:if test="${mes == 9}">Outubro</c:if>
					<c:if test="${mes == 10}">Novembro</c:if>
					<c:if test="${mes == 11}">Dezembro</c:if>
				</option>
			</c:forEach>
		</select>
	</div>
</div>

<div class="row-fluid">
	<div class="span12">
		<div id="resultadoHistorico"  style="float: left;margin-top: 10px;clear: both;"></div>
	</div>
</div>

<script type="text/javascript">

jQuery(function($){

	$("#mes").change(function() {

		var ano = $("#ano").val();
		var mes = $("#mes").val();
		var bancoNome = $("#contratoBanco").val();
		var produtoNome = $("#contratoProduto").val();    

		$("#resultadoHistorico").load('<c:url value="/coeficiente/buscaCoeficienteHistorico" />',{'ano': ano, 'mes': mes ,'bancoNome': bancoNome,'produtoNome':produtoNome});

	});

}); 

</script>