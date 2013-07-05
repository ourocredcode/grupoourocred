<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/header.jspf" %>

<script type="text/javascript">

function mostra(div){

	document.getElementById("bbm").style.display = "none";
	document.getElementById("bva").style.display = "none";
	document.getElementById("cacique").style.display = "none";
	document.getElementById("fibra").style.display = "none";
	document.getElementById("ficsa").style.display = "none";
	document.getElementById("intermedium").style.display = "none";
	document.getElementById("mercantil").style.display = "none";
	document.getElementById("parana").style.display = "none";
	document.getElementById("pine").style.display = "none";
	document.getElementById("rural").style.display = "none";
	document.getElementById("safra").style.display = "none";
	document.getElementById("schahin").style.display = "none";
	
	
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
	<li><a href="#" onclick="mostra('bbm');">BBM</a></li>
 	<li><a href="#" onclick="mostra('bva');">BVA</a></li>
 	<li><a href="#" onclick="mostra('cacique');">Cacique</a></li>
	<li><a href="#" onclick="mostra('fibra');">Fibra</a></li>
 	<li><a href="#" onclick="mostra('ficsa');">Ficsa</a></li>
 	<li><a href="#" onclick="mostra('intermedium');">Intermedium</a></li>
 	<li><a href="#" onclick="mostra('mercantil');">Mercantil</a></li>
 	<li><a href="#" onclick="mostra('parana');">Paraná</a></li>
 	<li><a href="#" onclick="mostra('pine');">Pine</a></li>
 	<li><a href="#" onclick="mostra('rural');">Rural</a></li>
 	<li><a href="#" onclick="mostra('safra');">Safra</a></li>
 	<li><a href="#" onclick="mostra('schahin');">Schahin</a></li>
 	
</ul>
</div>

<div id="bbm" style="display: none;font-size: 16px;">

	<br/>
	<h2 style="color: #b5b5b5;">BBM</h2><br/>
	
	<table id="mytable" style="width: 850px;">
		<tr>
			<th style="width: 100px;">Modelo Quitação</th>
			<th>Processo</th>
		</tr>
		<tr>
			<td>Carta</td>
			<td>Envio de carta comum ao Banco</td>
		</tr>
	</table>
	
	<table id="mytable" style="width: 850px;margin-top: 20px;">
		<tr>
			<th class="titulo" colspan="3">Passo a passo:</th>
		</tr>
		<tr>
			<th style="width: 100px;"><b>Ação 01</b></th>
			<th style="width: 100px;"><b>Operação</b></th>
			<td>Preencher a carta de acordo com modelo e enviar junto com o contrato para assinatura;</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th><b>Operação</b></th>
			<td>Cliente vai assinar a carta e devolver juntamente com os contratos</td>
		</tr>
		<tr>
			<th><b>Ação 03</b></th>
			<th><b>Apoio Comercial</b></th>
			<td>Após recepção do contrato juntamente com a carta no departamento, vamos enviar ao Banco</td>
		</tr>
		<tr>
			<th><b>Ação 04</b></th>
			<th><b>Apoio Comercial</b></th>
			<td>Confirmar recebimento da carta junto ao Banco</td>
		</tr>
		<tr>
			<th><b>Ação 05</b></th>
			<th><b>Apoio Comercial</b></th>
			<td>Solicitação de protocolo de previsão</td>
		</tr>
		<tr>
			<th><b>Ação 06</b></th>
			<th><b>Apoio Comercial</b></th>
			<td>Acompanhamento para chegada do boleto</td>
		</tr>
	</table>

</div>

<div id="bva" style="display: none;font-size: 16px;">

	<br/>
	<h2 style="color: #b5b5b5;">BVA</h2><br/>
	
	<table id="mytable" style="width: 850px;">
		<tr>
			<th style="width: 100px;">Modelo Quitação</th>
			<th>Processo</th>
		</tr>
		<tr>
			<td>Carta</td>
			<td>Envio de carta comum ao Banco</td>
		</tr>
	</table>
	
	<table id="mytable" style="width: 850px;margin-top: 20px;">
		<tr>
			<th class="titulo" colspan="3">Passo a passo:</th>
		</tr>
		<tr>
			<th style="width: 100px;"><b>Ação 01</b></th>
			<th style="width: 100px;"><b>Operação</b></th>
			<td>Preencher a carta de acordo com modelo e enviar junto com o contrato para assinatura</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th><b>Operação</b></th>
			<td>Cliente vai assinar a carta e devolver juntamente com os contratos</td>
		</tr>
		<tr>
			<th><b>Ação 03</b></th>
			<th><b>Apoio Comercial</b></th>
			<td>Após recepção do contrato juntamente com a carta no departamento, vamos enviar ao Banco</td>
		</tr>
		<tr>
			<th><b>Ação 04</b></th>
			<th><b>Apoio Comercial</b></th>
			<td>Confirmar recebimento da carta junto ao Banco</td>
		</tr>
		<tr>
			<th><b>Ação 05</b></th>
			<th><b>Apoio Comercial</b></th>
			<td>Solicitação de protocolo de previsão</td>
		</tr>
		<tr>
			<th><b>Ação 06</b></th>
			<th><b>Apoio Comercial</b></th>
			<td>Acompanhamento para chegada do boleto</td>
		</tr>
	</table>

</div>

<div id="cacique" style="display: none;font-size: 16px;">
	<br/>
	<h2 style="color: #b5b5b5;">Cacique</h2><br/>
	
	<table id="mytable" style="width: 850px;">
		<tr>
			<th style="width: 100px;">Modelo Quitação</th>
			<th>Processo</th>
		</tr>
		<tr>
			<td>Carta</td>
			<td>Envio de carta comum ao Banco</td>
		</tr>
	</table>
	
	<table id="mytable" style="width: 850px;margin-top: 20px;">
		<tr>
			<th class="titulo" colspan="3">Passo a passo:</th>
		</tr>
		<tr>
			<th style="width: 100px;"><b>Ação 01</b></th>
			<th style="width: 100px;"><b>Operação</b></th>
			<td>Preencher a carta de acordo com modelo e enviar junto com o contrato para assinatura</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th><b>Operação</b></th>
			<td>Cliente vai assinar a carta e devolver juntamente com os contratos</td>
		</tr>
		<tr>
			<th><b>Ação 03</b></th>
			<th><b>Apoio Comercial</b></th>
			<td>Após recepção do contrato juntamente com a carta no departamento, vamos enviar ao Banco</td>
		</tr>
		<tr>
			<th><b>Ação 04</b></th>
			<th><b>Apoio Comercial</b></th>
			<td>Confirmar recebimento da carta junto ao Banco</td>
		</tr>
		<tr>
			<th><b>Ação 05</b></th>
			<th><b>Apoio Comercial</b></th>
			<td>Solicitação de protocolo de previsão</td>
		</tr>
		<tr>
			<th><b>Ação 06</b></th>
			<th><b>Apoio Comercial</b></th>
			<td>Acompanhamento para chegada do boleto</td>
		</tr>
	</table>
</div>

<div id="fibra" style="display: none;font-size: 16px;">
	
	<br/>
	<h2 style="color: #b5b5b5;">Fibra</h2><br/>
	
	<table id="mytable" style="width: 850px;">
		<tr>
			<th style="width: 100px;">Modelo Quitação</th>
			<th>Processo</th>
		</tr>
		<tr>
			<td>Carta</td>
			<td>Envio de carta reconhecida Firma por autenticidade ao Banco juntamente com protocolo</td>
		</tr>
	</table>
	
	<table id="mytable" style="width: 850px;margin-top: 20px;">
		<tr>
			<th class="titulo" colspan="3">Passo a passo:</th>
		</tr>
		<tr>
			<th style="width: 100px;"><b>Ação 01</b></th>
			<th style="width: 100px;"><b>Operação</b></th>
			<td>Envio de carta reconhecida Firma por autenticidade ao Banco juntamente com protocolo</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th><b>Operação</b></th>
			<td>Preencher a carta de acordo com modelo com protocolo retirado no Banco e valor da parcela e enviar junto com o contrato para assinatura</td>
		</tr>
		<tr>
			<th><b>Ação 03</b></th>
			<th><b>Operação</b></th>
			<td>Após assinatura do contrato, orientar o cliente a reconhecer firma por autenticidade e anexar cópias dos documentos para enviar ao Banco via correios como carta registrada</td>
		</tr>
		<tr>
			<th><b>Ação 04</b></th>
			<th><b>Operação</b></th>
			<td>Solicitar o número de AR</td>
		</tr>
		<tr>
			<th><b>Ação 05</b></th>
			<th><b>Operação</b></th>
			<td>Solicitação de protocolo de previsão</td>
		</tr>
		<tr>
			<th><b>Ação 06</b></th>
			<th><b>Apoio Comercial</b></th>
			<td>Setor Apoio comercial vai fazer acompanhamento para chegada do boleto</td>
		</tr>
	</table>

</div>

<div id="ficsa" style="display: none;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">Ficsa</h2><br/>

	<table id="mytable" style="width: 850px;">
		<tr>
			<th style="width: 100px;">Modelo Quitação</th>
			<th>Processo</th>
		</tr>
		<tr>
			<td>Carta</td>
			<td>Envio de carta reconhecida Firma por autenticidade ao Banco</td>
		</tr>
	</table>
	
	<table id="mytable" style="width: 850px;margin-top: 20px;">
		<tr>
			<th class="titulo" colspan="3">Passo a passo:</th>
		</tr>
		<tr>
			<th style="width: 100px;"><b>Ação 01</b></th>
			<th style="width: 100px;"><b>Operação</b></th>
			<td>Preencher a carta de acordo com modelo  e enviar junto com o contrato para assinatura</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th><b>Operação</b></th>
			<td>Após assinatura do contrato, orientar o cliente a reconhecer firma por autenticidade e anexar cópias dos documentos para enviar ao Banco via correios como carta registrada</td>
		</tr>
		<tr>
			<th><b>Ação 03</b></th>
			<th><b>Operação</b></th>
			<td>solicitar o número de AR</td>
		</tr>
		<tr>
			<th><b>Ação 04</b></th>
			<th><b>Operação</b></th>
			<td>Incluir número de AR no sistema</td>
		</tr>
		<tr>
			<th><b>Ação 05</b></th>
			<th><b>Apoio Comercial</b></th>
			<td>Setor Apoio comercial vai fazer acompanhamento para chegada do boleto</td>
		</tr>
	</table>

</div>

<div id="intermedium" style="display: none;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">Intermedium</h2><br/>

	<table id="mytable" style="width: 850px;">
		<tr>
			<th style="width: 100px;">Modelo Quitação</th>
			<th>Processo</th>
		</tr>
		<tr>
			<td>Carta</td>
			<td>Envio de carta reconhecida Firma por autenticidade ao Banco</td>
		</tr>
	</table>
	
	<table id="mytable" style="width: 850px;margin-top: 20px;">
		<tr>
			<th class="titulo" colspan="3">Passo a passo:</th>
		</tr>
		<tr>
			<th style="width: 100px;"><b>Ação 01</b></th>
			<th style="width: 100px;"><b>Operação</b></th>
			<td>Preencher a carta de acordo com modelo  e enviar junto com o contrato para assinatura</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th><b>Operação</b></th>
			<td>Após assinatura do contrato, orientar o cliente a reconhecer firma por autenticidade e anexar cópias dos documentos para enviar ao Banco via correios como carta registrada</td>
		</tr>
		<tr>
			<th><b>Ação 03</b></th>
			<th><b>Operação</b></th>
			<td>solicitar o número de AR</td>
		</tr>
		<tr>
			<th><b>Ação 04</b></th>
			<th><b>Operação</b></th>
			<td>Incluir número de AR no sistema</td>
		</tr>
		<tr>
			<th><b>Ação 05</b></th>
			<th><b>Apoio Comercial</b></th>
			<td>Setor Apoio comercial vai fazer acompanhamento para chegada do boleto</td>
		</tr>
	</table>

</div>

<div id="mercantil" style="display: none;font-size: 16px;">
	<br/>
	<h2 style="color: #b5b5b5;">Mercantil</h2><br/>
	
	<table id="mytable" style="width: 850px;">
		<tr>
			<th style="width: 100px;">Modelo Quitação</th>
			<th>Processo</th>
		</tr>
		<tr>
			<td>Formulario</td>
			<td>Envio do formulario e o comprovante de endereço reconhecida Firma por autenticidade ao Banco com protocolo.</td>
		</tr>
	</table>
	
	<table id="mytable" style="width: 850px;margin-top: 20px;">
		<tr>
			<th class="titulo" colspan="3">Passo a passo:</th>
		</tr>
		<tr>
			<th style="width: 100px;"><b>Ação 01</b></th>
			<th style="width: 100px;"><b>Operação</b></th>
			<td>Ligar no banco solicitando saldo devedor e protocolo.</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th><b>Operação</b></th>
			<td>Preencher o formulário, com protocolo enviado por e-mail</td>
		</tr>
		<tr>
			<th><b>Ação 03</b></th>
			<th><b>Operação</b></th>
			<td>Após assinatura do contrato, orientar o cliente a reconhecer firma do formulário e do comprovante de endereço por autenticidade e anexar cópias dos documentos para enviar ao Banco via correios como carta registrada</td>
		</tr>
		<tr>
			<th><b>Ação 04</b></th>
			<th><b>Operação</b></th>
			<td>Solicitar o número de AR</td>
		</tr>
		<tr>
			<th><b>Ação 05</b></th>
			<th><b>Operação</b></th>
			<td>Solicitação de protocolo de previsão</td>
		</tr>
		<tr>
			<th><b>Ação 06</b></th>
			<th><b>Apoio Comercial</b></th>
			<td>Setor Apoio comercial vai fazer acompanhamento para chegada do boleto</td>
		</tr>
	</table>

</div>

<div id="parana" style="display: none;font-size: 16px;">

	<br/>
	<h2 style="color: #b5b5b5;">Parana</h2><br/>

	<table id="mytable" style="width: 850px;">
		<tr>
			<th style="width: 100px;">Modelo Quitação</th>
			<th>Processo</th>
		</tr>
		<tr>
			<td>Carta</td>
			<td>Preencher formulário e enviar ao Banco</td>
		</tr>
	</table>
	
	<table id="mytable" style="width: 850px;margin-top: 20px;">
		<tr>
			<th class="titulo" colspan="3">Passo a passo:</th>
		</tr>
		<tr>
			<th style="width: 100px;"><b>Ação 01</b></th>
			<th style="width: 100px;"><b>Operação</b></th>
			<td>Entrar no site do Banco antes de enviar o motoboy e preencher formulário para solicitação de boleto</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th><b>Operação</b></th>
			<td>Enviar o formulário junto com os contratos</td>
		</tr>
		<tr>
			<th><b>Ação 03</b></th>
			<th><b>Operação</b></th>
			<td>Cliente precisa reconhecer firma por autenticidade e enviar ao endereço como carta registrada que vai estar no formulário</td>
		</tr>
		<tr>
			<th><b>Ação 04</b></th>
			<th><b>Operação</b></th>
			<td>Solicitar o número de AR e entrar em contato com o Banco para acompanhar</td>
		</tr>
		<tr>
			<th><b>Ação 05</b></th>
			<th><b>Apoio Comercial</b></th>
			<td>Reclamação a cada final de prazo</td>
		</tr>
	</table>

</div>

<div id="pine" style="display: none;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">Pine</h2><br/>


	<table id="mytable" style="width: 850px;">
		<tr>
			<th style="width: 100px;">Modelo Quitação</th>
			<th>Processo</th>
		</tr>
		<tr>
			<td>Carta</td>
			<td>Envio de carta reconhecida Firma por autenticidade ao Banco</td>
		</tr>
	</table>
	
	<table id="mytable" style="width: 850px;margin-top: 20px;">
		<tr>
			<th class="titulo" colspan="3">Passo a passo:</th>
		</tr>
		<tr>
			<th style="width: 100px;"><b>Ação 01</b></th>
			<th style="width: 100px;"><b>Operação</b></th>
			<td>Preencher a carta de acordo com modelo  e enviar junto com o contrato para Assinatura</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th><b>Operação</b></th>
			<td>Após assinatura do contrato, orientar o cliente a reconhecer firma por autenticidade e anexar cópias dos documentos para enviar ao Banco via correios como carta registrada</td>
		</tr>
		<tr>
			<th><b>Ação 03</b></th>
			<th><b>Operação</b></th>
			<td>solicitar o número de AR</td>
		</tr>
		<tr>
			<th><b>Ação 04</b></th>
			<th><b>Operação</b></th>
			<td>Incluir número de AR no sistema</td>
		</tr>
		<tr>
			<th><b>Ação 05</b></th>
			<th><b>Apoio Comercial</b></th>
			<td>Setor Apoio comercial vai fazer acompanhamento para chegada do boleto</td>
		</tr>
	</table>
</div>


<div id="rural" style="display: none;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">Rural</h2><br/>


<table id="mytable" style="width: 850px;">
		<tr>
			<th style="width: 100px;">Modelo Quitação</th>
			<th>Processo</th>
		</tr>
		<tr>
			<td>Carta</td>
			<td>Envio de carta, documentos autenticados e extrato de pagamento autenticado</td>
		</tr>
	</table>
	
	<table id="mytable" style="width: 850px;margin-top: 20px;">
		<tr>
			<th class="titulo" colspan="3">Passo a passo:</th>
		</tr>
		<tr>
			<th style="width: 100px;"><b>Ação 01</b></th>
			<th style="width: 100px;"><b>Operação</b></th>
			<td>Enviar a carta junto com o contrato</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th><b>Operação</b></th>
			<td>Solicitar ao cliente que reconheça firma por autenticidade nos documentos: RG, CPF, Comp. De residência e detalhamento de crédito</td>
		</tr>
		<tr>
			<th><b>Ação 03</b></th>
			<th><b>Operação</b></th>
			<td>Postar nos correios como carta registrada ao endereço do Banco e enviar uma cópia simples para Ourocred</td>
		</tr>
		<tr>
			<th><b>Ação 04</b></th>
			<th><b>Operação</b></th>
			<td>Solicitar o número de AR</td>
		</tr>
		<tr>
			<th><b>Ação 05</b></th>
			<th><b>Operação</b></th>
			<td>Incluir número de AR no sistema</td>
		</tr>
		<tr>
			<th><b>Ação 06</b></th>
			<th><b>Apoio Comercial</b></th>
			<td>Setor Apoio comercial vai fazer acompanhamento para chegada do boleto</td>
		</tr>
	</table>
	
<br/>

</div>

<div id="safra" style="display: none;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">Safra</h2><br/>

	<table id="mytable" style="width: 850px;">
		<tr>
			<th style="width: 100px;">Modelo Quitação</th>
			<th>Processo</th>
		</tr>
		<tr>
			<td>Carta</td>
			<td>Envio de carta ao Banco juntamente com os documentos autenticados</td>
		</tr>
	</table>
	
	<table id="mytable" style="width: 850px;margin-top: 20px;">
		<tr>
			<th class="titulo" colspan="3">Passo a passo:</th>
		</tr>
		<tr>
			<th style="width: 100px;"><b>Ação 01</b></th>
			<th style="width: 100px;"><b>Operação</b></th>
			<td>Enviar a carta junto com o contrato</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th><b>Operação</b></th>
			<td>Solicitar ao cliente que reconheça firma por autenticidade nos documentos: RG, CPF, Comp. De residência</td>
		</tr>
		<tr>
			<th><b>Ação 03</b></th>
			<th><b>Operação</b></th>
			<td>Postar nos correios como carta registrada ao endereço do Banco e enviar uma cópia simples para Ourocred</td>
		</tr>
		<tr>
			<th><b>Ação 04</b></th>
			<th><b>Operação</b></th>
			<td>Solicitar o número de AR</td>
		</tr>
		<tr>
			<th><b>Ação 05</b></th>
			<th><b>Operação</b></th>
			<td>Incluir número de AR no sistema</td>
		</tr>
		<tr>
			<th><b>Ação 06</b></th>
			<th><b>Apoio Comercial</b></th>
			<td>Setor Apoio comercial vai fazer acompanhamento para chegada do boleto</td>
		</tr>
	</table>
</div>

<div id="schahin" style="display: none;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">Schahin</h2><br/>

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
			<td>Liga no Banco Fazer a solicitação do Saldo Devedor</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th><b>Operação</b></th>
			<td>Cliente terá que se dirigir no endereço rua Consolação 2717 térreo cerqueira Cesar – SP – (Levar copia RG, CPF, Comprovante de Endereço e Protocolo da ligação)</td>
		</tr>
		<tr>
			<th><b>Ação 03</b></th>
			<th><b>Operação</b></th>
			<td>Cliente recebera o Boleto em casa no prazo de 15 dias</td>
		</tr>
	</table>
	
	<br/><br/>
	
	<table id="mytable" style="width: 850px;">
		<tr>
			<th style="width: 100px;">Modelo Quitação</th>
			<th>Processo</th>
		</tr>
		<tr>
			<td>Carta</td>
			<td>Enviar a carta reconhecida firma juntamente do protocolo de solicitação</td>
		</tr>
	</table>
	
	<table id="mytable" style="width: 850px;margin-top: 20px;">
		<tr>
			<th class="titulo" colspan="3">Passo a passo:</th>
		</tr>
		<tr>
			<th style="width: 100px;"><b>Ação 01</b></th>
			<th style="width: 100px;"><b>Operação</b></th>
			<td>Liga no Banco Fazer a solicitação do Saldo devedor e  pegar protocolo</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th><b>Operação</b></th>
			<td>Cliente terá que fazer uma carta de próprio punho solicitando o saldo devedor, mencionando numero de protocolo.</td>
		</tr>
		<tr>
			<th><b>Ação 03</b></th>
			<th><b>Operação</b></th>
			<td>Após assinatura do contrato, orientar o cliente a reconhecer firma por da carta  e anexar cópias dos documentos para enviar ao Banco via correios como carta registrada</td>
		</tr>
		<tr>
			<th><b>Ação 04</b></th>
			<th><b>Operação</b></th>
			<td>Solicitar o número de AR</td>
		</tr>
		<tr>
			<th><b>Ação 05</b></th>
			<th><b>Operação</b></th>
			<td>Solicitação de protocolo de previsão</td>
		</tr>
		<tr>
			<th><b>Ação 06</b></th>
			<th><b>Apoio Comercial</b></th>
			<td>Setor Apoio comercial vai fazer acompanhamento para chegada do boleto</td>
		</tr>
	</table>

</div>



<%@ include file="/footer.jspf" %> 