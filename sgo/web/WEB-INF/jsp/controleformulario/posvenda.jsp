<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div style="float: left;clear: both;">
	
	
		<div class="control-group">
		
			<div class="row-fluid">
				<div class="span8">
					<form id="posVendaForm" name="posVendaForm" action="<c:url value="/controleformulario/salvaposvenda" />" method="post">
		
						<input type="hidden" id="tipoUser" name="tipoUser" value="${usuarioInfo.perfil.chave}" />
						<input type="hidden" id="posvenda.formulario.id" name="posvenda.formulario.formulario_id" value="${posvenda.formulario.formulario_id }" />
						<input type="hidden" id="posvenda.controleFormulario_id" name="posvenda.controleFormulario_id" value="${posvenda.controleFormulario_id }" />
						<input type="hidden" id="posvenda.workflow.workflow_id" name="posvenda.workflow.workflow_id" value="${posvenda.workflow.workflow_id }" />
						<input type="hidden" id="posvenda.workflowPendencia.workflow_id" name="posvenda.workflowPendencia.workflow_id" value="${posvenda.workflowPendencia.workflow_id }" />
						<input type="hidden" id="posvenda.tipoControle.tipoControle_id" name="posvenda.tipoControle.tipoControle_id" value="${posvenda.tipoControle.tipoControle_id }" />
						<input type="hidden" id="posvenda.usuario.usuario_id" name="posvenda.usuario.usuario_id" value="${posvenda.usuario.usuario_id }" />
						<input type="hidden" id="posvenda.perfil.perfil_id" name="posvenda.perfil.perfil_id" value="${posvenda.perfil.perfil_id }" />
						
						<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th></th>
								<th></th>
								<th>Informado:</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>
									Quantidade de Contratos
								</td>
								<td>
									<input id="qtContratos" name="posvenda.quantidadeContrato" value="${posvenda.quantidadeContrato}" type="text" class="input-medium" maxlength="20" onblur="verificaValor();" />
									<input id="qtContratosInformado" value="${countContratos}" type="hidden"/>
								</td>
								<td>
									${countContratos}
								</td>
							</tr>
							<tr>
								<td>
									Valor Parcela confirmado
								</td>
								<td>
									<input id="valorParcela" name="posvenda.valorParcela" value="${posvenda.valorParcela}" type="text" class="input-medium" maxlength="20" onblur="verificaValor();"/>
									<input id="valorParcelaInformado" value="<fmt:formatNumber type="NUMBER" value="${countValorParcela}" minFractionDigits="2" groupingUsed="false" />" type="hidden"/>
								</td>
								<td>
									<fmt:formatNumber type="NUMBER" value="${countValorParcela}" minFractionDigits="2" groupingUsed="false" />
								</td>
							</tr>
								<tr>
								<td>
									Prazo confirmado:
								</td>
								<td>
									<select id="prazo" name="posvenda.confirmaPrazo" class="input-medium">
										<option value="1" <c:if test="${posvenda.confirmaPrazo}">selected</c:if>>Confirma</option>
										<option value="0" <c:if test="${!posvenda.confirmaPrazo}">selected</c:if>>Não Confirma</option>
									</select>
								</td>
								<td>
								</td>
							</tr>
							<tr>
								<td>
									Valor Líquido confirmado:
								</td>
								<td>
									<input id="valorLiquido" name="posvenda.valorLiquido" value="${posvenda.valorLiquido}" type="text" class="input-medium" maxlength="20" onblur="verificaValor();" />
									<input id="valorLiquidoInformado" value="<fmt:formatNumber type="NUMBER" value="${countValorLiquido}" minFractionDigits="2" groupingUsed="false"  />" type="hidden"/>
								</td>
								<td>
									<fmt:formatNumber type="NUMBER" value="${countValorLiquido}" minFractionDigits="2"  groupingUsed="false" />
								</td>
							</tr>	
							<tr>
								<td>
									Status:
								</td>
								<td>
									<select id="status" name="posvenda.etapa.etapa_id" class="input-medium">
										<option value="">Selecione</option>
										
										<c:forEach var="etapa" items="${etapas }">
											<option value="${etapa.etapa_id }" <c:if test="${posvenda.etapa.etapa_id == etapa.etapa_id}">selected="selected"</c:if>>${etapa.nome }</option>
										</c:forEach>
									</select>
								</td>
								<td>
									
								</td>
							</tr>
							<tr>	
								<td>
									Motivo :
								</td>
								<td>
									<select id="motivo" name="posvenda.etapaPendencia.etapa_id" class="input-medium">
										<option value="">Selecione</option>
										<c:forEach var="motivo" items="${motivos }">
											<option value="${motivo.etapa_id }" <c:if test="${posvenda.etapaPendencia.etapa_id == motivo.etapa_id}">selected</c:if>>${motivo.nome }</option>
										</c:forEach>
									</select>
								</td>
								<td>
									
								</td>
							</tr>	
							<tr>	
								<td>
									
								</td>
								<td>
									<input value="Salvar" type="button" class="btn" onclick="javascript:validaForm('#posVendaForm');">
								</td>
								<td>
									<button type="button" value="fecha" class="btn" onClick="window.location.href='/sgo/formulario/visualiza/${formulario.formulario_id}'">Fecha</button>
								</td>
							</tr>	
						</tbody>
						</table>
					</form>
				</div>
				<div class="span4">
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th>Data</th>
								<th>Responsável</th>
								<th>Observação</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${historicos }" var="historico">
							<tr>
								<td><fmt:formatDate pattern="dd/MM/yyyy"  type="time" value="${historico.created.time }" /></td>
								<td>${historico.createdBy.nome }</td>
								<td>${historico.observacao }</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>


</div>



<script type="text/javascript">

$(document).ready(function() {

		$("#valorParcela").maskMoney({symbol:"",decimal:".",thousands:""});
		$("#valorLiquido").maskMoney({symbol:"",decimal:".",thousands:""});

		$('#posVendaForm').submit(function() {
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

});

function verificaDuvida(val) {

	var duvida = document.getElementById("duvida");
	var verDuvida = document.getElementById("verDuvida");

	if(val == 'true') {
		habilita(duvida);
		verDuvida.style.color = 'red';
		duvida.style.color = 'red';
	} else {
		desabilita(duvida);
		verDuvida.style.color = 'black';
	}

}

function verificaValor() {

	var qtContratos = document.getElementById("qtContratos");
	var qtContratosInformado = document.getElementById("qtContratosInformado");
	var divqtContratosInformado = document.getElementById("divqtContratosInformado");
	
	var trocaV = /\,/g;

	if(qtContratos.value == qtContratosInformado.value || qtContratos.value == ''){
		divqtContratosInformado.style.color = 'white';
	} else {
		divqtContratosInformado.style.color = 'red';
	}

	var valorParcela = parseFloat(document.getElementById("valorParcela").value).toFixed(2);
	var valorParcelaInformado = document.getElementById("valorParcelaInformado").value.toString().replace(trocaV,".");
	var divValorParcelaInformado = document.getElementById("divvalorParcelaInformado");
	
	if(valorParcela == valorParcelaInformado || valorParcela == 0.00 || valorParcela == 'NaN'){
		divValorParcelaInformado.style.color = 'white';
	} else {
		divValorParcelaInformado.style.color = 'red';
	}
	
	var valorLiquido = parseFloat(document.getElementById("valorLiquido").value).toFixed(2);
	var valorLiquidoInformado = document.getElementById("valorLiquidoInformado").value.toString().replace(trocaV,".");
	var divValorLiquidoInformado = document.getElementById("divValorLiquidoInformado");

	if(valorLiquido == valorLiquidoInformado || valorLiquido == 0.00 || valorLiquido == 'NaN'){
		divValorLiquidoInformado.style.color = 'white';
	} else {
		divValorLiquidoInformado.style.color = 'red';
	}

}

function desabilita(campo){   
	campo.disabled = 1;
	campo.className = 'label_txt';
	campo.value= '';
}

function habilita(campo){   
	campo.disabled = 0;
	campo.className = 'required';
}

function validaForm(form) {

	if ($(form).validate().form() === true) {
		$(form).submit();
	} else {
		return false;
	}

}
</script>