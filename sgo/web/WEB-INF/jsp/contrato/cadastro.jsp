<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script type="text/javascript">

$(document).ready(function() {

	$('select').select2();

	$("#valorContrato").maskMoney({symbol:"",decimal:".",thousands:""});
	$("#valorParcela").maskMoney({symbol:"",decimal:".",thousands:""});
	$("#valorDivida").maskMoney({symbol:"",decimal:".",thousands:""});
	$("#valorSeguro").maskMoney({symbol:"",decimal:".",thousands:""});
	$("#valorLiquido").maskMoney({symbol:"",decimal:".",thousands:""});

	var contratoProduto = $("#contratoProduto").val();

	if (contratoProduto != '')
		$(verificaProduto());
	
	$("#contratoBanco").change(function() {

		var banco_id = $("#contratoBanco").val();

		$("#contratoProduto").select2('val','');
		$("#auxCoeficiente").select2('val','');

		$("#contratoProduto").load('<c:url value="/contrato/produtos" />',{'banco_id': banco_id});

	});

	$("#contratoProduto").change(function() {

		var produto_id = $("#contratoProduto").val();    
		var banco_id = $("#contratoBanco").val();

		$("#auxCoeficiente").select2('val','');
		$("#bancoComprado").select2('val','');

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
		var tabela_id = arrayCoeficiente[3];

		if(tabela_id != undefined){
			$("#div-prazo").load('<c:url value="/contrato/prazo" />',{'tabela_id': tabela_id});	
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
				alert("Valor l�quido menor do que zero.");
				valorDivida.value = '';
				valorDivida.focus();
			}

		} else {

			valorLiquido.value = '';

		}

		if(contratoProdutoNome != 'REFINANCIAMENTO' && contratoProdutoNome != 'RETEN��O' && contratoProdutoNome != 'REFIN C.E.F'){

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
	
	var bancoComprado = document.getElementById("bancoComprado");
	var parcelasAberto = document.getElementById("parcelasAberto");
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

			$("#bancoComprado").select2().select2('val',contratoBanco.value);

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
			
		case 'REFIN C.E.F':

			$("#bancoComprado").select2().select2('val',contratoBanco.value);

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
			
		case 'RETEN��O':

			$("#bancoComprado").select2().select2('val',contratoBanco.value);

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
			alert("Valor l�quido menor do que zero.");
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

	if(campo.id == 'bancoComprado')
		$("#bancoComprado").select2("disable");
	
	campo.disabled = true;
	campo.required = false;
	campo.className = 'span10';	
	

}

function habilita(campo){
	
	if(campo.id == 'bancoComprado')
		$("#bancoComprado").select2("enable");

	campo.disabled = false;
	campo.required = true;
	campo.className = 'span10';	

}

function validaForm(form) {

	$.each($(".select2-container"), function (i, n) {
        $(n).next().show().fadeTo(0, 0).height("0px").css("left", "auto"); // make the original select visible for validation engine and hidden for us
        $(n).prepend($(n).next());
        $(n).delay(500).queue(function () {
            $(this).removeClass("validate[required]"); //remove the class name from select2 container(div), so that validation engine dose not validate it
            $(this).dequeue();
        });
    });

	if ($(form).validate().form() === true) {

		var contratoProduto = document.getElementById("contratoProduto");
		var contratoBanco = document.getElementById("contratoBanco");
		var coeficiente = document.getElementById("coeficiente");
		var contratoProdutoNome = contratoProduto.options[contratoProduto.selectedIndex].text;
		var bancoComprado = document.getElementById("bancoComprado");
		var parcelasAberto = document.getElementById("parcelasAberto");
		var valorDivida = document.getElementById("valorDivida");
		var valorParcela = document.getElementById("valorParcela");
		
		if(contratoProdutoNome == 'RECOMPRA INSS'){
			if(bancoComprado.value == '' || parcelasAberto == '' || valorDivida == ''){
				alert(" Banco Comprado / Parcela Aberto / D�vida s�o campos obrigat�rios ");
				bancoComprado.focus();
				
				return false;
			}
		}

		if( contratoProduto.value == '' || contratoBanco.value == '' || coeficiente.value == '' || valorParcela == '') {

			alert("Banco / Produto / Coeficiente s�o obrigat�rios");

			return false;

		} 

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

	var contratoId = document.getElementById("contratoId");

	if(contratoId.value != '')
		window.location.href = "<c:url value='/contrato/status/" + contratoId.value + "' />";
	else
		window.location.href = "<c:url value='/formulario/cadastro' />";				

}

function historicoCoeficiente() {

	var produto_id = $("#contratoProduto").val();   
	var banco_id = $("#contratoBanco").val();

	$("#historico").load('<c:url value="/coeficiente/historico" />',{'banco_id': banco_id,'produto_id': produto_id});

}

</script>

<c:if test="${contrato.contrato_id  == null }">
	<c:set var="url" value="/formulario/adicionaContrato"></c:set>
	<c:set var="titulo" value="Novo Contrato"></c:set>
</c:if>
<c:if test="${contrato.contrato_id != null }">
	<c:set var="url" value="/contrato/altera"></c:set>
	<c:set var="titulo" value="Altera Contrato"></c:set>
</c:if>

<div id="contrato">
	<div class="row-fluid">
		<div class="span8">
			<div class="widget-box">
				<div class="widget-title"><span class="icon"><i class="icon-file"></i></span><h5>${titulo }</h5></div>
				<div class="widget-content padding">

				<form id="contratoForm" name="contratoForm" action="<c:url value="${url }"  />" method="POST">
				<input type="hidden" id="contratoId" name="contrato.contrato_id" value="${contrato.contrato_id }" />

					<div class="row-fluid">
				
						<div class="span3">
							<label for="contratoBanco">Banco:</label>
							<select id="contratoBanco" name="contrato.banco.banco_id" class="span12" required>
								<option value="">Escolha um banco</option>
								<c:forEach items="${bancos}" var="banco">
									<option value="${banco.banco_id}" <c:if test="${contrato.banco.banco_id == banco.banco_id }">selected</c:if>>${banco.nome}</option>
								</c:forEach>
							</select>
						</div>						
						<div class="span3" id="selProduto" >

							<label for="contratoProduto">Produto:</label>
							<select class="span12" id="contratoProduto" name="contrato.produto.produto_id" required>
								<option value="">Escolha um produto</option>
								<c:if test="${not empty contrato.contrato_id}">
									<c:forEach items="${produtos}" var="produto">
										<option value="${produto.produto_id}" <c:if test="${contrato.produto.produto_id eq produto.produto_id}">SELECTED</c:if>>${produto.nome}</option>
									</c:forEach>
								</c:if>
							</select>

						</div>

						<div class="span3">

							<label for="auxCoeficiente">Coeficiente Cadastro:</label>
							<select id="auxCoeficiente" name="contratoCoeficiente" onChange="calculaContrato();" class="span12" required="required">
								<c:if test="${empty contrato.contrato_id}">
									<option value="">Selecione um produto...</option>
								</c:if>
								<c:if test="${not empty contrato.contrato_id}">
									<c:forEach items="${coeficientes}" var="coeficiente">
										<option value="${coeficiente.valor},${coeficiente.percentualMeta},${coeficiente.coeficiente_id},${coeficiente.tabela.tabela_id}" 
										<c:if test="${contrato.coeficiente.coeficiente_id eq coeficiente.coeficiente_id}">selected="selected"</c:if>>${coeficiente.valor} - ${coeficiente.tabela.nome}</option>
									</c:forEach>
								</c:if>
							</select>

							<input id="coeficiente" type="hidden" name="contrato.coeficiente.coeficiente_id" value="${contrato.coeficiente.coeficiente_id }" />
							<input id="tabela" type="hidden" name="contrato.coeficiente.tabela.tabela_id" value="${contrato.coeficiente.tabela.tabela_id }" />

						</div>
						
						<div class="span3">

							<label for="bancoComprado">Banco Comprado:</label>
							<select id="bancoComprado" class="span10"  name="contrato.recompraBanco.banco_id">
								<option value="">Escolha um banco</option>
								<c:forEach items="${recompraBancos}" var="recompraBanco">
									<option value="${recompraBanco.banco_id}" <c:if test="${contrato.recompraBanco.banco_id eq recompraBanco.banco_id}">SELECTED</c:if>>${recompraBanco.nome}</option>
								</c:forEach>
							</select>

						</div>
					</div>

					<div class="row-fluid">
						<div class="span3">
							<label for="valorContrato">Valor Contrato</label>
							<input id="valorContrato" type="text" class="span10" value="${contrato.valorContrato }"  name="contrato.valorContrato" disabled="disabled" onblur="calculaContrato();" required/>	
						</div>
						<div class="span3">
							<label for="valorParcela">Valor Parcela</label>
							<input id="valorParcela" type="text" class="span10"  value="${contrato.valorParcela }" name="contrato.valorParcela" onblur="calculaContrato();" required="required" />	
						</div>
						<div class="span3">
							<label for="parcelasAberto">Parcelas Aberto</label>
							<input id="parcelasAberto" name="contrato.qtdParcelasAberto" value="${contrato.qtdParcelasAberto }" disabled="disabled" type="text" class="span10" onblur="calculaValorLiquido();" />	
						</div>
						<div class="span3">
							<label for="valorDivida">Valor Divida</label>
							<input id="valorDivida" type="text" class="span10"  value="${contrato.valorDivida }" name="contrato.valorDivida" disabled="disabled" onblur="calculaValorLiquido();" />	
						</div>
						<div>
							<input id="valorSeguro" type="hidden" class="span10"  value="${contrato.valorSeguro }" name="contrato.valorSeguro" disabled="disabled" onblur="calculaValorLiquido();preencheZero(this);"/>	
						</div>
					</div>
					<div class="row-fluid">
						<div class="span3">
							<div id="div-prazo">
								<label for="prazo">Prazo:</label>
								<input id="prazo" class="span10"  type="text" value="${contrato.prazo }" name="contrato.prazo"  disabled="disabled" required />
							</div>
						</div>
						<div class="span3">
							<label for="desconto">Desconto</label>
							<input id="desconto" type="text" class="span10"  value="${contrato.desconto }" name="contrato.desconto" disabled="disabled" onblur="calculaContrato();" />	
						</div>
						<div class="span3">
							<label for="valorLiquido">Valor Liquido</label>
							<input id="valorLiquido" type="text" class="span10" value="${contrato.valorLiquido }" name="contrato.valorLiquido" disabled="disabled" required />	
						</div>
						<div class="span3">
							<label for="valorMeta">Valor Meta</label>
							<input id="valorMeta" type="text" class="span10" value="${contrato.valorMeta }" name="contrato.valorMeta" disabled="disabled" />	
						</div>
					</div>
					<div class="row-fluid">
						<div>
							<label for="observacao">Observacao</label>
							<textarea id="observacao" name="contrato.observacao" rows="4" cols="8" maxlength="255" class="span12">
								<c:out value="${contrato.observacao}"></c:out>
							</textarea>	
						</div>
					</div>
					
					<div class="row-fluid">
						<div class="span2" style="float: left;">
							<input value="Salvar" type="button" class="btn btn-primary" onclick="javascript:validaForm('#contratoForm');">
						</div>
						<div class="span2" style="float: left;">
							<input value="Cancelar" type="button" class="btn" onclick="fechar();" />
						</div>
						<div class="span3" style="float: left;">
							<input value="Hist�rico Coeficiente" type="button" class="btn" onclick="historicoCoeficiente();" />
						</div>
					</div>
				</form>
				</div>
			</div>			
		</div>
		<div class="span3">
			<div class="widget-box">
				<div class="widget-title"><span class="icon"><i class="icon-file"></i></span><h5>Historico Coeficiente</h5></div>
				<div class="widget-content padding">
					<div class="row-fluid">
						<div class="span12">
							<div id="historico" style="float: left;margin-left: 10px;"></div>
							<div id="cadastroAux" style="float: left;margin-left: 10px;"></div>
						</div>
					</div>			
				</div>
			</div>
		</div>
	</div>
</div>



