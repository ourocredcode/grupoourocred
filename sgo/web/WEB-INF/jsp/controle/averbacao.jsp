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
				window.location.reload(true);
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

	<form id="averbacaoForm" name="averbacaoForm" action="<c:url value="/controle/averbacao/salva" />" method="post" >

		<input type="hidden" id="tipoUser" name="tipoUser" value="${consultorInfo.consultor.tipo}" />
		<input type="hidden" id="averbacao.controle_id" name="averbacao.controle_id" value="${averbacao.controle_id}" />
		<input type="hidden" id="averbacao.contrato.contrato_id" name="averbacao.contrato.contrato_id" value="${averbacao.contrato.contrato_id}" />

		<div class="control-group">
			<div class="controls">
				<label class="control-label">Última Atuação : 
				<c:if test="${not empty averbacao.controle_id }">
					 Realizada por ${averbacao.updatedBy.nome } em <fmt:formatDate pattern="dd/MM/yyyy HH:mm"  type="time" value="${averbacao.dataAtuacao.time }" />
				</c:if>
				<c:if test="${empty averbacao.controle_id }">
					 Ainda não realizado
				</c:if> 
				</label>
			</div>
		 </div>
		<div class="control-group">
			<div class="row-fluid">
				<div class="span4">
					<label class="control-label">Próxima Atuação :</label>
					<div class="controls">
						<input id="averbacaoDataProximaAtuacao" name="averbacao.dataProximaAtuacao" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${averbacao.dataProximaAtuacao.time }" />" class="input-medium" />
					</div>
				</div>
				<div class="span4">
					<label class="control-label">Próximo Procedimento :</label>
					<select id="averbacaoEtapaProximoEtapaId" name="averbacao.etapaProximo.etapa_id" value="${averbacao.etapaProximo.etapa_id }" class="input-medium">
						<option value="">Escolha...</option>
						<c:forEach var="etapa" items="${etapas }">
							<option value="${etapa.etapa_id }" <c:if test="${averbacao.etapaProximo.etapa_id == etapa.etapa_id }">selected="selected"</c:if>>${etapa.nome }</option>
						</c:forEach>
					</select>
				</div>
				<div class="span4">
					<label class="control-label">Próximo Atuante : </label>
					<div class="controls">
						<select id="averbacaoProximoAtuante" name="averbacao.proximoAtuante.usuario_id" value="${averbacao.proximoAtuante.usuario_id }"  class="input-medium" required>
							<option value="">Escolha...</option>
							<c:forEach var="atuante" items="${atuantes }">
								<option value="${atuante.usuario_id }" <c:if test="${averbacao.proximoAtuante.usuario_id == atuante.usuario_id }">selected="selected"</c:if>>${atuante.nome }</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Previsão de Liberação :</label>
			<div class="controls">
				<input id="averbacaoDataPrevisao" name="averbacao.dataPrevisao" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${averbacao.dataPrevisao.time }" />" class="input-medium" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">Procedimento : </label>
			<div class="controls">
				<select id="averbacaoEtapaEtapaId" name="averbacao.etapa.etapa_id" value="${averbacao.etapa.etapa_id }"  class="input-medium">
					<option value="">Escolha...</option>
					<c:forEach var="etapa" items="${etapas }">
						<option value="${etapa.etapa_id }" <c:if test="${averbacao.etapa.etapa_id == etapa.etapa_id }">selected="selected"</c:if>>${etapa.nome }</option>
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
		<div class="form-actions">
			<input type="button" value="Salvar" class="btn btn-primary" onclick="javascript:validaForm('#averbacaoForm');">
			<button type="button" value="Fecha" class="btn btn-primary" onclick="window.location.reload(true);">Fecha</button>
		</div>

	</form>
</div>