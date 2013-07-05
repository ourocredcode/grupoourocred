<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/header.jspf" %>

<script type="text/javascript">

function mostra(div){

	document.getElementById("bgn").style.display = "none";
	document.getElementById("citibank").style.display = "none";
	document.getElementById("hsbc").style.display = "none";
	document.getElementById("ibi").style.display = "none";
	document.getElementById("panamericano").style.display = "none";
	document.getElementById("sulfinanceira").style.display = "none";

	if(document.getElementById(div).style.display == "none") {
		document.getElementById(div).style.display = "inline";
	} else {
		document.getElementById(div).style.display = "none";
	}

}

</script>

<div id="menuScripts" style="float:left;min-height: 640px;font-size: 13px;margin-top: 20px;margin-right: 20px;">
<h2 style="color: #b5b5b5;">Escolha a opção abaixo:</h2><br/>
<ul>
	<li><a href="#" onclick="mostra('bgn');">BGN</a></li>
 	<li><a href="#" onclick="mostra('citibank');">Citibank</a></li>
 	<li><a href="#" onclick="mostra('hsbc');">HSBC</a></li>
 	<li><a href="#" onclick="mostra('ibi');">IBI</a></li>
 	<li><a href="#" onclick="mostra('panamericano');">Panamericano</a></li>
 	<li><a href="#" onclick="mostra('sulfinanceira');">Sul Financeira</a></li>
</ul>
</div>

<div id="bgn" style="display: none;font-size: 16px;">

	<br/>
	<h2 style="color: #b5b5b5;">BGN</h2><br/>
	
	<table id="mytable" style="width: 850px;">
		<tr>
			<th style="width: 100px;">Modelo Quitação</th>
			<th>Processo</th>
		</tr>
		<tr>
			<td>Pessoalmente</td>
			<td>Solicitar protocolo, após ir pessoalmente até agência determinada p/ Banco</td>
		</tr>
	</table>
	
	<table id="mytable" style="width: 850px;margin-top: 20px;">
		<tr>
			<th class="titulo" colspan="3">Passo a passo:</th>
		</tr>
		<tr>
			<th style="width: 100px;"><b>Ação 01</b></th>
			<th style="width: 100px;"><b>Operação</b></th>
			<td>Orientar o cliente que será necessário o mesmo ir até o Banco antes de enviar para assinatura</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th><b>Operação</b></th>
			<td>Após a assinatura do contrato, entrar em contato com o BGN solicitando protocolo e endereço da agência onde o cliente vai solicitar o boleto.</td>
		</tr>
		<tr>
			<th><b>Ação 03</b></th>
			<th><b>Operação</b></th>
			<td>Ligar para o cliente passar o número de protocolo e "Validade do protocolo" e passar o endereço onde ele precisa ir para solicitar o boleto.</td>
		</tr>
		<tr>
			<th><b>Ação 04</b></th>
			<th><b>Operação</b></th>
			<td>Confirmar se o cliente foi até o Banco e solicitar informações do que ocorreu.</td>
		</tr>
		<tr>
			<th><b>Ação 05</b></th>
			<th><b>Operação</b></th>
			<td>Orientar o cliente a retornar ao Banco no prazo retirar o boleto.</td>
		</tr>
	</table>

</div>

<div id="citibank" style="display: none;font-size: 16px;">

	<br/>
	<h2 style="color: #b5b5b5;">Citibank</h2><br/>

	<table id="mytable" style="width: 850px;">
		<tr>
			<th style="width: 100px;">Modelo Quitação</th>
			<th>Processo</th>
		</tr>
		<tr>
			<td>Pessoalmente</td>
			<td>Cliente precisa ir até agência solicitar o boleto</td>
		</tr>
	</table>
	
	<table id="mytable" style="width: 850px;margin-top: 20px;">
		<tr>
			<th class="titulo" colspan="3">Passo a passo:</th>
		</tr>
		<tr>
			<th style="width: 100px;"><b>Ação 01</b></th>
			<th style="width: 100px;"><b>Operação</b></th>
			<td>Orientar o cliente que será necessário o mesmo ir até o Banco antes de enviar para assinatura.</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th><b>Operação</b></th>
			<td>Após a assinatura do contrato, solicitar ao cliente ir até a agência fazer a solicitação.</td>
		</tr>
		<tr>
			<th><b>Ação 03</b></th>
			<th><b>Operação</b></th>
			<td>Confirmar se o cliente foi até o Banco e solicitar informações do que ocorreu.</td>
		</tr>
		<tr>
			<th><b>Ação 04</b></th>
			<th><b>Operação</b></th>
			<td>Orientar o cliente a retornar ao Banco no prazo retirar o boleto.</td>
		</tr>
	</table>

</div>

<div id="hsbc" style="display: none;font-size: 16px;">
	<br/>
	<h2 style="color: #b5b5b5;">HSBC</h2><br/>
	
	<table id="mytable" style="width: 850px;">
		<tr>
			<th style="width: 100px;">Modelo Quitação</th>
			<th>Processo</th>
		</tr>
		<tr>
			<td>Pessoalmente</td>
			<td>Cliente precisa ir até agência solicitar o boleto</td>
		</tr>
	</table>
	
	<table id="mytable" style="width: 850px;margin-top: 20px;">
		<tr>
			<th class="titulo" colspan="3">Passo a passo:</th>
		</tr>
		<tr>
			<th style="width: 100px;"><b>Ação 01</b></th>
			<th style="width: 100px;"><b>Operação</b></th>
			<td>Orientar o cliente que será necessário o mesmo ir até o Banco antes de enviar para assinatura.</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th><b>Operação</b></th>
			<td>Após a assinatura do contrato, solicitar ao cliente ir até a agência fazer a solicitação e retirar o boleto.</td>
		</tr>
		<tr>
			<th><b>Ação 03</b></th>
			<th><b>Operação</b></th>
			<td>Confirmar se o cliente foi até o Banco e solicitar que ele envie o boleto por fax ou e-mail.</td>
		</tr>
	</table>

</div>

<div id="ibi" style="display: none;font-size: 16px;">
	<br/>
	<h2 style="color: #b5b5b5;">IBI</h2><br/>
	
	
	<table id="mytable" style="width: 850px;">
		<tr>
			<th style="width: 100px;">Modelo Quitação</th>
			<th>Processo</th>
		</tr>
		<tr>
			<td>Pessoalmente</td>
			<td>Cliente precisa ir até agência solicitar o boleto</td>
		</tr>
	</table>
	
	<table id="mytable" style="width: 850px;margin-top: 20px;">
		<tr>
			<th class="titulo" colspan="3">Passo a passo:</th>
		</tr>
		<tr>
			<th style="width: 100px;"><b>Ação 01</b></th>
			<th style="width: 100px;"><b>Operação</b></th>
			<td>Orientar o cliente que será necessário o mesmo ir até o Banco antes de enviar para assinatura.</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th><b>Operação</b></th>
			<td>Após a assinatura do contrato, solicitar ao cliente ir até a agência fazer a solicitação e retirar o boleto.</td>
		</tr>
		<tr>
			<th><b>Ação 03</b></th>
			<th><b>Operação</b></th>
			<td>Confirmar se o cliente foi até o Banco e solicitar que ele envie o boleto por fax ou e-mail.</td>
		</tr>
	</table>

</div>

<div id="panamericano" style="display: none;font-size: 16px;">
	<br/>
	<h2 style="color: #b5b5b5;">Panamericano</h2><br/>
	
	<table id="mytable" style="width: 850px;">
		<tr>
			<th style="width: 100px;">Modelo Quitação</th>
			<th >Processo</th>
		</tr>
		<tr>
			<td>Pessoalmente</td>
			<td>Cliente precisa ir até agência solicitar o boleto</td>
		</tr>
	</table>
	
	<table id="mytable" style="width: 850px;margin-top: 20px;">
		<tr>
			<th class="titulo" colspan="3">Passo a passo:</th>
		</tr>
		<tr>
			<th style="width: 100px;"><b>Ação 01</b></th>
			<th style="width: 100px;"><b>Operação</b></th>
			<td>Orientar o cliente que será necessário o mesmo ir até o Banco antes de enviar para assinatura.</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th><b>Operação</b></th>
			<td>Após a assinatura do contrato, solicitar ao cliente ir até a agência retirar carta, preencher, ir até o cartório reconhecer firma por autenticidade e devolver a carta no Panamericano.</td>
		</tr>
		<tr>
			<th><b>Ação 03</b></th>
			<th><b>Operação</b></th>
			<td>Confirmar se o cliente foi até o Banco e solicitar informações do que ocorreu.</td>
		</tr>
		<tr>
			<th><b>Ação 04</b></th>
			<th><b>Operação</b></th>
			<td>Orientar o cliente a retornar ao Banco no prazo retirar o boleto.</td>
		</tr>
	</table>

</div>

<div id="sulfinanceira" style="display: none;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">Sul Financeira</h2><br/>

	<table id="mytable" style="width: 850px;">
		<tr>
			<th style="width: 100px;">Modelo Quitação</th>
			<th>Processo</th>
		</tr>
		<tr>
			<td>Pessoalmente</td>
			<td>Cliente precisa ir até agência solicitar o boleto</td>
		</tr>
	</table>
	
	<table id="mytable" style="width: 850px;margin-top: 20px;">
		<tr>
			<th class="titulo" colspan="3">Passo a passo:</th>
		</tr>
		<tr>
			<th style="width: 100px;"><b>Ação 01</b></th>
			<th style="width: 100px;"><b>Operação</b></th>
			<td>Orientar o cliente que será necessário o mesmo ir até o Banco antes de enviar para assinatura.</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th><b>Operação</b></th>
			<td>Após a assinatura do contrato, solicitar ao cliente ir até a agência fazer a solicitação.</td>
		</tr>
		<tr>
			<th><b>Ação 03</b></th>
			<th><b>Operação</b></th>
			<td>Confirmar se o cliente foi até o Banco e solicitar informações do que ocorreu.</td>
		</tr>
		<tr>
			<th><b>Ação 04</b></th>
			<th><b>Operação</b></th>
			<td>Orientar o cliente a retornar ao Banco no prazo retirar o boleto.</td>
		</tr>
	</table>

</div>

<%@ include file="/footer.jspf" %> 