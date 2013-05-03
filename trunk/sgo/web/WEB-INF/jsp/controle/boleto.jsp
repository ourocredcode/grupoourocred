<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript">

$(document).ready(function() {

	$("#boletoDataPrevisao").mask("99/99/9999");
	$("#boletoDataChegada").mask("99/99/9999");
	$("#boletoDataVencimento").mask("99/99/9999");
	$("#boletoDataProximaAtuacao").mask("99/99/9999");

	$('#boletoForm').submit(function() {

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
	
	$('#boletoDataPrevisao').focus( function() {
		$(this).calendario({
			target:'#boletoDataPrevisao',
			top:0,
			left:100
		});
	});
	
	$('#boletoDataChegada').focus( function() {
		$(this).calendario({
			target:'#boletoDataChegada',
			top:0,
			left:100
		});
	});
	
	$('#boletoDataVencimento').focus( function() {
		$(this).calendario({
			target:'#boletoDataVencimento',
			top:0,
			left:100
		});
	});
	
	$('#boletoDataProximaAtuacao').focus( function() {
		$(this).calendario({
			target:'#boletoDataProximaAtuacao',
			top:0,
			left:100
		});
	});

});

function buscaByCalendar(){
	return false;
}

function validaForm(form) {

	if ($(form).validate().form() === true) {
		$(form).submit();
	} else {
		return false;
	}

}

</script>	
	

<div style="float: left;clear: both;">

	<form id="boletoForm" name="boletoForm" action="<c:url value="/boleto/salva" />" method="post" class="form-horizontal">

		<input type="hidden" id="tipoUser" name="tipoUser" value="${usuarioInfo.perfil.chave}" />
		<input type="hidden" id="boleto.id" name="boleto.id" value="${boleto.controle_id}" />
		<input type="hidden" id="boleto.contrato.id" name="boleto.contrato.id" value="${contrato.contrato_id}" />
		
			<div class="control-group">
				<label class="control-label">Última Atuação :</label>
				<div class="controls">
				<c:if test="${not empty boleto.controle_id }">
					realizada por ${boleto.usuario.nome } em <fmt:formatDate pattern="dd/MM/yyyy HH:mm"  type="time" value="${boleto.dataAtuacao.time }" />
				</c:if>
				<c:if test="${empty boleto.controle_id }">
					Ainda não realizado
				</c:if> 
				</div>
			 </div>
			<div class="control-group">
				<label class="control-label">Previsão de Chegada :</label>
				<div class="controls">
					<input id="boletoDataPrevisao" name="boleto.dataPrevisao" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${boleto.dataPrevisao.time }" />" class="input-medium" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">Próxima Atuação :</label>
				<div class="controls">
					<input id="boletoDataProximaAtuacao" name="boleto.dataProximaAtuacao" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${boleto.dataProximaAtuacao.time }" />" class="input-medium" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">Data de Chegada : </label>
				<div class="controls">
					<input id="boletoDataChegada" name="boleto.dataChegada" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${boleto.dataChegada.time }" />" class="input-medium" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">Data de Vencimento : </label>
				<div class="controls">
					<input id="boletoDataVencimento" name="boleto.dataVencimento" value="<fmt:formatDate pattern="dd/MM/yyyy"  value="${boleto.dataVencimento.time }" />"  class="input-medium" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">Observacao : </label>
				<div class="controls">
					<textarea  style="width: 100%;" id="observacao" name="observacao" class="input-medium" rows="5" cols="120" maxlength="255"></textarea>
				</div>
			</div>
			<div class="form-actions">
				<input type="button" value="Salvar" class="btn btn-primary" onclick="javascript:validaForm('#boletoForm');">
				<button type="button" value="Fecha" class="btn btn-primary" onclick="window.location.reload(true);">Fecha</button>
			</div>

	</form>
</div>
