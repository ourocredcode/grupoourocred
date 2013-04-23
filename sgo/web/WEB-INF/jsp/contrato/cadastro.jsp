<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script type="text/javascript">

$(document).ready(function() {

	var contratoProduto = $("#contratoProduto").val();

	if (contratoProduto != '')
		$(verificaProduto());
	
	$("#contratoBanco").change(function() {

		var banco_id = $("#contratoBanco").val();

		$("#contratoProduto").load('<c:url value="/contrato/produtos" />',{'banco_id': banco_id});

	});

	$("#contratoProduto").change(function() {

		var produto_id = $("#contratoProduto").val();    
		var banco_id = $("#contratoBanco").val();

		$("#auxCoeficiente").load('<c:url value="/contrato/coeficientes" />',{'banco_id': banco_id,'produto_id': produto_id}); 
		
		verificaProduto();

	});
	
	$('#contratoForm').submit( function() {

		if ($('#contratoForm').validate().form() == true) {
			document.getElementById("auxCoeficiente").disabled = 1;
		}

		$('#contratoForm :input[disabled]').each( function() {               
			if (this.id != 'auxCoeficiente')
				this.disabled = false;
		});
	});
	
	$("#auxCoeficiente").change(function() {

		var auxCoeficiente = document.getElementById("auxCoeficiente");	
		var arrayCoeficiente = auxCoeficiente.value.split(',');
		var coeficiente_id = arrayCoeficiente[2];

		if(coeficiente_id != undefined){
			$("#div-prazo").load('<c:url value="/contrato/prazo" />',{'coeficiente_id': coeficiente_id});	
		}

	});

});

function calculaContrato() {

	var valorParcela = document.getElementById("valorParcela");
	var valorContrato = document.getElementById("valorContrato");
	var valorLiquido = document.getElementById("valorLiquido");
	var valorMeta = document.getElementById("valorMeta");
	var valorDivida = document.getElementById("valorDivida");
	var valorSeguro = document.getElementById("valorSeguro");
	
	var contratoProduto = document.getElementById("contratoProduto");
	var contratoProdutoNome = contratoProduto.options[contratoProduto.selectedIndex].text;

	var contratoBanco = document.getElementById("contratoBanco");
	var contratoBancoNome = contratoBanco.options[contratoBanco.selectedIndex].text;

	var desconto = document.getElementById("desconto");
	var parcelasAberto = document.getElementById("parcelasAberto");
	var auxCoeficiente = document.getElementById("auxCoeficiente");	
	var arrayCoeficiente = auxCoeficiente.value.split(',');
	var coeficiente = document.getElementById("coeficiente");
	coeficiente.value = arrayCoeficiente[2];

	if (valorParcela.value == ''){

		valorParcela.focus();
		return false;

	} else {

		var contratoValue = valorParcela.value / arrayCoeficiente[0];
		valorContrato.value = contratoValue.toFixed(2);
		valorLiquido.value = contratoValue.toFixed(2);

		if(valorContrato.value != '') {

			var liquidoValue = valorContrato.value - valorDivida.value - valorSeguro.value;
			valorLiquido.value = liquidoValue.toFixed(2);

			if(parcelasAberto.value != ''){
				var descontoValue = (valorDivida.value / (valorParcela.value * parcelasAberto.value) - 1) * 100;
				desconto.value = (descontoValue.toFixed(2) * -1);	
			}

			if(desconto.value < 0){
				alert("Desconto menor do que zero.");
				valorParcela.value = '';
				valorParcela.focus();
			}

			if(valorLiquido.value < 0){
				alert("Valor líquido menor do que zero.");
				valorDivida.value = '';
				valorDivida.focus();
			}

		} else {

			valorLiquido.value = '';

		}

		if(contratoProdutoNome != 'REFINANCIAMENTO' && contratoProdutoNome != 'RETENÇÃO' && contratoProdutoNome != 'REFIN C.E.F'){

			var metaValue = valorContrato.value * arrayCoeficiente[1];
			valorMeta.value = metaValue.toFixed(2); 

		} else {

			switch(contratoBancoNome) {

				case 'BRADESCO':
					var metaValue = valorLiquido.value * arrayCoeficiente[1];
					valorMeta.value = metaValue.toFixed(2);

				break;
			
				case 'BMG':
					var metaValue = valorLiquido.value;
					valorMeta.value = metaValue;

					break;

				case 'BGN':
					var metaValue = valorLiquido.value;
					valorMeta.value = metaValue;

					break;	

				case 'CRUZEIRO DO SUL':
					var metaValue = valorLiquido.value;
					valorMeta.value = metaValue;

					break;
				
				case 'PANAMERICANO':
					var metaValue = valorLiquido.value;
					valorMeta.value = metaValue;

					break;

				case 'BONSUCESSO':

					if(contratoProduto.value == 'REFINANCIAMENTO'){

						var metaValue = valorLiquido.value * arrayCoeficiente[1];
						valorMeta.value = metaValue.toFixed(2);

					} else {

						var metaValue = valorLiquido.value / 2;
						valorMeta.value = metaValue;

					}

					break;

				case 'CIFRA':
					var metaValue = valorLiquido.value / 2;
					valorMeta.value = metaValue.toFixed(2);

					break;

				case 'BV':

					if(parcelasAberto.value <= 17) {
						var metaValue = valorLiquido.value / 2;
						valorMeta.value =  metaValue.toFixed(2);
					} else {
						var metaValue = valorLiquido.value;
						valorMeta.value =  metaValue;
					}

					break;
					
				case 'DAYCOVAL':

					var metaValue = valorLiquido.value / 2;
					valorMeta.value = metaValue.toFixed(2);

					break;
				
				case 'C.E.F':

					var metaValue = valorLiquido.value * arrayCoeficiente[1];
					valorMeta.value = metaValue.toFixed(2);

					break;	

				default:
					alert("Escolha um Banco.");
			}
			 
		}
	}

}

function verificaProduto() {

	var valorContrato = document.getElementById("valorContrato");
	var valorLiquido = document.getElementById("valorLiquido");
	var parcelasAberto = document.getElementById("parcelasAberto");
	var bancoComprado = document.getElementById("bancoComprado");
	var valorDivida = document.getElementById("valorDivida");
	var valorSeguro = document.getElementById("valorSeguro");
	
	var contratoProduto = document.getElementById("contratoProduto");
	var contratoProdutoNome = contratoProduto.options[contratoProduto.selectedIndex].text;
	
	var contratoBanco = document.getElementById("contratoBanco");
	var observacao = document.getElementById("observacao");
	var prazo = document.getElementById("prazo");

	switch (contratoProdutoNome) {

		case 'MARGEM LIMPA':
			desabilita(bancoComprado);
			desabilita(parcelasAberto);
			desabilita(valorDivida);
			desabilita(valorSeguro);
			desabilita(valorContrato);
			desabilita(valorLiquido);
			desabilita(prazo);
			observacao.value = "";

			if(valorContrato.value != '')
				calculaContrato();

			break;

		case 'AUMENTO':
			desabilita(bancoComprado);
			desabilita(parcelasAberto);
			desabilita(valorDivida);
			desabilita(valorSeguro);
			desabilita(valorContrato);
			desabilita(valorLiquido);
			desabilita(prazo);
			observacao.value = "Enviado contrato de margem de aumento";
			
			if(valorContrato.value != '')
				calculaContrato();

			break;

		case 'RECOMPRA INSS':
			habilita(bancoComprado);
			habilita(parcelasAberto);
			habilita(valorDivida);
			desabilita(valorSeguro);
			desabilita(valorContrato);
			desabilita(valorLiquido);
			desabilita(prazo);
			observacao.value = "";

			if(valorContrato.value != '')
				calculaContrato();

			break;

		case 'RECOMPRA RMC':
			habilita(bancoComprado);
			desabilita(parcelasAberto);
			habilita(valorDivida);
			desabilita(valorSeguro);
			desabilita(valorContrato);
			desabilita(valorLiquido);
			desabilita(prazo);
			observacao.value = "";

			if(valorContrato.value != '')
				calculaContrato();

			break;

		case 'REFINANCIAMENTO':

			bancoComprado.value = contratoBanco.value;

			desabilita(bancoComprado);
			habilita(parcelasAberto);
			habilita(valorDivida);
			desabilita(valorSeguro);
			desabilita(valorContrato);
			desabilita(valorLiquido);
			desabilita(prazo);
			observacao.value = "";

			if(valorContrato.value != '')
				calculaContrato();

			break;
			
		case 'REFIN C.E.F':

			bancoComprado.value = contratoBanco.value;

			desabilita(bancoComprado);
			habilita(parcelasAberto);
			habilita(valorDivida);
			habilita(valorSeguro);
			desabilita(valorContrato);
			desabilita(valorLiquido);
			observacao.value = "";

			if(valorContrato.value != '')
				calculaContrato();

			break;	
			
		case 'RETENÇÃO':

			bancoComprado.value = contratoBanco.value;

			desabilita(bancoComprado);
			habilita(parcelasAberto);
			habilita(valorDivida);
			desabilita(valorSeguro);
			desabilita(valorContrato);
			desabilita(valorLiquido);
			desabilita(prazo);
			observacao.value = "";

			if(valorContrato.value != '')
				calculaContrato();

			break;	
			
		case 'MARGEM C.E.F':

			desabilita(bancoComprado);
			desabilita(parcelasAberto);
			desabilita(valorContrato);
			desabilita(valorLiquido);
			observacao.value = "";

			desabilita(valorDivida);
			habilita(valorSeguro);

			if(valorSeguro.value == '')
				valorSeguro.value = '0.00';

			if(valorContrato.value != '')
				calculaContrato();

			break;		

		default:
			alert("Escolha um produto.");

	}
}

function calculaValorLiquido() {

	var valorLiquido = document.getElementById("valorLiquido");
	var valorContrato = document.getElementById("valorContrato");
	var valorDivida = document.getElementById("valorDivida");
	var valorSeguro = document.getElementById("valorSeguro");

	if(valorContrato.value != '') {

		var liquidoValue = valorContrato.value - valorDivida.value - valorSeguro.value;
		valorLiquido.value = liquidoValue.toFixed(2);

		if(valorLiquido.value < 0){
			alert("Valor líquido menor do que zero.");
			valorDivida.value = '';
			valorDivida.focus();
		}

		calculaContrato();

	}else {
		valorLiquido.value = '';
	}

}

function desabilita(campo){   

	if(!campo == 'valorContrato' || campo == 'valorLiquido')
		campo.value = '';

	campo.disabled = true;
	campo.required = false;
	campo.className = 'span10';

}

function habilita(campo){   

	campo.disabled = false;
	campo.required = true;
	campo.className = 'span10';

}

function validaForm(form) {

	if ($(form).validate().form() === true) {
		$(form).submit();
	} else {
		return false;
	}

}

function preencheZero(campo) {
	if(campo != undefined){
		if(campo.value == '' || campo.value == '0.00' || campo.value == '0.0'){
			campo.value = '0.0';
		}	
	}
}

function fechar() {

	$("#contrato").empty();

}

</script>

<c:if test="${contrato == null }">
	<c:set var="url" value="/formulario/adicionaContrato"></c:set>
</c:if>
<c:if test="${contrato != null }">
	<c:set var="url" value="/contrato/alteraContrato"></c:set>
</c:if>

<div id="contrato">

	<div class="widget-box">
		<div class="widget-title"><span class="icon"><i class="icon-file"></i></span><h5>Novo Contrato</h5></div>
			<div class="widget-content padding">

				<form id="contratoForm" name="contratoForm" action="<c:url value='/formulario/adicionaContrato'/>" method="POST">
				<input type="hidden" id="contrato.contrato_id" name="contrato.contrato_id" value="${contrato.contrato_id }" />

					<div class="row-fluid">
				
						<div class="span2">

							<label for="contratoBanco">Banco:</label>
							<select id="contratoBanco" name="contrato.banco.banco_id" class="span10" required>
								<option value="">Escolha um banco</option>
								<c:forEach items="${bancos}" var="banco">
									<option value="${banco.banco_id}">${banco.nome}</option>
								</c:forEach>
							</select>

						</div>
						
						<div class="span2">

							<label for="contratoProduto">Produto:</label>
							<select id="contratoProduto" name="contrato.produto.produto_id" class="span10" required>
								<option value="">Escolha um produto</option>
							</select>

						</div>

						<div class="span2">

							<label for="auxCoeficiente">Coeficiente Cadastro:</label>
							<select class="span10" id="auxCoeficiente" name="contratoCoeficiente" onChange="calculaContrato();" required>
								<c:if test="${usuarioInfo.perfil.chave == 'C'}">
									<option value="">Selecione um produto...</option>
								</c:if>
							</select>
							<input id="coeficiente" type="hidden" name="contrato.coeficiente.coeficiente_id" value="${contrato.coeficiente.coeficiente_id}" />

						</div>
					</div>
					<div class="row-fluid">
						<div class="span2">

							<label for="bancoComprado">Banco Comprado:</label>
							<select id="bancoComprado" class="span10"  name="contrato.recompraBanco.banco_id">
								<option value="">Escolha um banco</option>
								<c:forEach items="${bancosRecompra}" var="bancoRecompra">
									<option value="${bancoRecompra.banco_id}">${bancoRecompra.nome}</option>
								</c:forEach>
							</select>

						</div>
				
						<div class="span2">
							<label for="parcelasAberto">Parcelas Aberto</label>
							<input id="parcelasAberto" name="contrato.qtdParcelasAberto" disabled="disabled" type="text" class="span10" onblur="calculaValorLiquido();" />	
						</div>
							
				
					
					</div>
					<div class="row-fluid">
						<div class="span2">
							<label for="valorContrato">Valor Contrato</label>
							<input id="valorContrato" type="text" class="span10"  name="contrato.valorContrato" disabled="disabled" onblur="calculaContrato();" required/>	
						</div>
						<div class="span2">
							<label for="valorParcela">Valor Parcela</label>
							<input id="valorParcela" type="text" class="span10"  name="contrato.valorParcela" onblur="calculaContrato();" required />	
						</div>
						<div class="span2">
							<label for="valorDivida">Valor Divida</label>
							<input id="valorDivida" type="text" class="span10"  name="contrato.valorDivida" disabled="disabled" onblur="calculaValorLiquido();" />	
						</div>
						<div class="span2">
							<label for="valorSeguro">Valor Seguro</label>
							<input id="valorSeguro" type="text" class="span10"  name="contrato.valorSeguro" disabled="disabled" onblur="calculaValorLiquido();preencheZero(this);"/>	
						</div>
					</div>
					<div class="row-fluid">
						<div class="span2">
							<div id="div-prazo">
								<label for="prazo" class="label_txt">Prazo:</label>
								<input id="prazo" class="span10"  type="text" name="contrato.prazo" value="${contrato.prazo}" disabled="disabled" required />
							</div>
						</div>
						<div class="span2">
							<label for="desconto">Desconto</label>
							<input id="desconto" type="text" class="span10"  name="contrato.desconto" disabled="disabled" onblur="calculaContrato();" />	
						</div>
						<div class="span2">
							<label for="valorLiquido">Valor Liquido</label>
							<input id="valorLiquido" type="text" class="span10"  name="contrato.valorLiquido" disabled="disabled" required />	
						</div>
						<div class="span2">
							<label for="valorMeta">Valor Meta</label>
							<input id="valorMeta" type="text" class="span10" name="contrato.valorMeta" disabled="disabled" />	
						</div>
					</div>
					<div class="row-fluid">
						<div class="span2">
							<label for="observacao">Observacao</label>
							<textarea id="observacao" name="contrato.observacao" rows="4" cols="10" maxlength="255" >
								<c:out value="${contrato.observacao}"></c:out>
							</textarea>	
						</div>
					</div>
					
					<div class="row-fluid">
						<div class="span1" style="float: left;">
							<input value="Salvar" type="button" class="btn btn-primary" onclick="javascript:validaForm('#contratoForm');">
						</div>
						<div style="float: left;">
							<input value="Cancelar" type="button" class="btn" onclick="fechar();" />
						</div>
					</div>
				</form>
			
		</div>
	</div>
</div>

<div id="cadastroAux" style="float: left;margin-left: 10px;"></div>
<div id="historico" style="float: left;margin-left: 10px;"></div>
