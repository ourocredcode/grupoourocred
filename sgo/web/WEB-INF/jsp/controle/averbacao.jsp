<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript">

$(document).ready(function() {

	$("#averbacaoDataPrevisao").mask("99/99/9999");
	$("#averbacaoDataProximaAtuacao").mask("99/99/9999");

	$('#averbacaoForm').submit(function() {

		$("input", this).attr("readonly", true);
		$("input[type='submit'],input[type='button']", this).attr("disabled", true);

		$.ajax({
			data: $(this).serialize()
			, type: $(this).attr('method')
			, url: $(this).attr('action')
			, success: function(response) {
				alert(response);
			}
		});
		return false;
	});
	
	$("textarea[maxlength]").keypress(function(event){
		var key = event.which;

		if(key >= 33 || key == 13) {

			var maxLength = $(this).attr("maxlength");
			var length = this.value.length;

			if(length >= maxLength) {
				event.preventDefault();
			};

		};
	});
		
	$('#averbacaoDataProximaAtuacao').datepicker({
		dateFormat: 'dd/mm/y'
	});
	$('#averbacaoDataPrevisao').datepicker({
		dateFormat: 'dd/mm/y'
	});

});

function validaForm(form) {

	if ($(form).validate().form() === true) {
		$(form).submit();
	} else {
		return false;
	}

}

function buscaByCalendar(){
	return false;
}

</script>	
	

<div style="float: left;clear: both;">

	<form id="averbacaoForm" name="averbacaoForm" action="<c:url value="/controle/averbacao/salva" />" method="post" class="form-horizontal">

		<input type="hidden" id="tipoUser" name="tipoUser" value="${consultorInfo.consultor.tipo}" />
		<input type="hidden" id="averbacao.controle_id" name="averbacao.controle_id" value="${averbacao.controle_id}" />
		<input type="hidden" id="averbacao.contrato.contrato_id" name="averbacao.contrato.contrato_id" value="${averbacao.contrato.contrato_id}" />

		<div class="control-group">
			<label class="control-label">Última Atuação :</label>
			<div class="controls">
			<c:if test="${not empty averbacao.controle_id }">
				realizada por ${averbacao.createdBy.nome } em <fmt:formatDate pattern="dd/MM/yyyy HH:mm"  type="time" value="${averbacao.dataAtuacao.time }" />
			</c:if>
			<c:if test="${empty averbacao.controle_id }">
				Ainda não realizado
			</c:if> 
			</div>
		 </div>
		<div class="control-group">
			<label class="control-label">Próxima Atuação :</label>
			<div class="controls">
				<input id="averbacaoDataProximaAtuacao" name="averbacao.dataProximaAtuacao" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${averbacao.dataProximaAtuacao.time }" />" class="input-medium" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Previsão de Chegada :</label>
			<div class="controls">
				<input id="averbacaoDataPrevisao" name="averbacao.dataPrevisao" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${averbacao.dataPrevisao.time }" />" class="input-medium" />
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label">Observacao : </label>
			<div class="controls">
				<textarea  style="width: 100%;" id="observacao" name="observacao" class="input-medium" rows="5" cols="120" maxlength="255"></textarea>
			</div>
		</div>
		<div class="form-actions">
			<input type="button" value="Salvar" class="btn btn-primary" onclick="javascript:validaForm('#averbacaoForm');">
			<button type="button" value="Fecha" class="btn btn-primary" onclick="window.location.reload(true);">Fecha</button>
		</div>

	</form>
</div>