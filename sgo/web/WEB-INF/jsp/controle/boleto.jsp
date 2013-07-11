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

	$('#boletoDataPrevisao').datepicker({
		dateFormat: 'dd/mm/y'
	});
	$('#boletoDataChegada').datepicker({
		dateFormat: 'dd/mm/y'
	});
	$('#boletoDataVencimento').datepicker({
		dateFormat: 'dd/mm/y'
	});
	$('#boletoDataProximaAtuacao').datepicker({
		dateFormat: 'dd/mm/y'
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

	<form id="boletoForm" name="boletoForm" action="<c:url value="/controle/boleto/salva" />" method="post" >

		<input type="hidden" id="tipoUser" name="tipoUser" value="${usuarioInfo.perfil.chave}" />
		<input type="hidden" id="boleto.controle_id" name="boleto.controle_id" value="${boleto.controle_id}" />
		<input type="hidden" id="boleto.contrato.contrato_id" name="boleto.contrato.contrato_id" value="${boleto.contrato.contrato_id}" />

			<div class="control-group">
				<label class="control-label">Última Atuação :

				<c:if test="${not empty boleto.controle_id }">
					${boleto.createdBy.nome } em <fmt:formatDate pattern="dd/MM/yyyy HH:mm"  type="time" value="${boleto.dataAtuacao.time }" />
				</c:if>
				<c:if test="${empty boleto.controle_id }">
					Ainda não realizado
				</c:if> 

				</label>
			 </div>
			 <div class="control-group"></div>
			<div class="control-group">
				<div class="row-fluid">
					<div class="span4">
						<label class="control-label">Previsão de Chegada :</label>
						<div class="controls">
							<input id="boletoDataPrevisao" name="boleto.dataPrevisao" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${boleto.dataPrevisao.time }" />" class="input-medium" />
						</div>
					</div>
					<div class="span4">
						<label class="control-label">Data de Chegada : </label>
						<div class="controls">
							<input id="boletoDataChegada" name="boleto.dataChegada" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${boleto.dataChegada.time }" />" class="input-medium" />
						</div>
					</div>
					<div class="span4">
						<label class="control-label">Data de Vencimento : </label>
						<div class="controls">
							<input id="boletoDataVencimento" name="boleto.dataVencimento" value="<fmt:formatDate pattern="dd/MM/yyyy"  value="${boleto.dataVencimento.time }" />"  class="input-medium" />
						</div>
					</div>
				</div>
			</div>
			<div class="control-group">
				<div class="row-fluid">
					<div class="span4">
						<label class="control-label">Próxima Atuação :</label>
						<input id="boletoDataProximaAtuacao" name="boleto.dataProximaAtuacao" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${boleto.dataProximaAtuacao.time }" />" class="input-medium" />
					</div>
					<div class="span4">
						<label class="control-label">Próximo Procedimento :</label>
						<select id="boletoEtapaProximoEtapaId" name="boleto.etapaProximo.etapa_id" value="${boleto.etapaProximo.etapa_id }" class="input-medium">
							<option value="">Escolha...</option>
							<c:forEach var="etapa" items="${etapas }">
								<option value="${etapa.etapa_id }" <c:if test="${boleto.etapaProximo.etapa_id == etapa.etapa_id }">selected="selected"</c:if>>${etapa.nome }</option>
							</c:forEach>
						</select>
					</div>
					<div class="span4">
						<label class="control-label">Próximo Atuante : </label>
						<div class="controls">
							<select id="boletoProximoAtuante" name="boleto.proximoAtuante.usuario_id" value="${boleto.proximoAtuante.usuario_id }"  class="input-medium">
								<option value="">Escolha...</option>
								<c:forEach var="atuante" items="${atuantes }">
									<option value="${atuante.usuario_id }" <c:if test="${boleto.proximoAtuante.usuario_id == atuante.usuario_id }">selected="selected"</c:if>>${atuante.nome }</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">Procedimento : </label>
				<div class="controls">
					<select id="boletoEtapaEtapaId" name="boleto.etapa.etapa_id" value="${boleto.etapa.etapa_id }"  class="input-medium">
						<option value="">Escolha...</option>
						<c:forEach var="etapa" items="${etapas }">
							<option value="${etapa.etapa_id }" <c:if test="${boleto.etapa.etapa_id == etapa.etapa_id }">selected="selected"</c:if>>${etapa.nome }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">Observacao : </label>
				<div class="controls">
					<textarea  style="width: 100%;" id="observacao" name="observacao" class="input-medium" rows="5" cols="120" maxlength="255"></textarea>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">Método : </label>
				<select id="boletoAgenteAgenteId" name="boleto.agente.agente_id" value="${boleto.agente.agente_id }"  class="input-medium">
					<option value="">Escolha...</option>
					<c:forEach var="agente" items="${agentes }">
						<option value="${agente.agente_id }" <c:if test="${boleto.agente.agente_id == agente.agente_id }">selected="selected"</c:if>>${agente.nome }</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-actions">
				<input type="button" value="Salvar" class="btn btn-primary" onclick="javascript:validaForm('#boletoForm');">
				<button type="button" value="Fecha" class="btn btn-primary" onclick="window.location.reload(true);">Fecha</button>
			</div>

	</form>
</div>
