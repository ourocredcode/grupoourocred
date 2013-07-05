<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/header.jspf" %>

<script type="text/javascript">

function mostra(div){

	document.getElementById("bancobrasil").style.display = "none";
	document.getElementById("cef").style.display = "none";

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
	<li><a href="#" onclick="mostra('bancobrasil');">Banco do Brasil</a></li>
 	<li><a href="#" onclick="mostra('cef');">Caixa Econômica</a></li>
</ul>
</div>

<div id="bancobrasil" style="display: none;font-size: 16px;">

	<br/>
	<h2 style="color: #b5b5b5;">Banco Brasil</h2><br/>
	
	<table id="mytable" style="width: 850px;">
		<tr>
			<th style="width: 100px;">Modelo Quitação</th>
			<th>Processo</th>
		</tr>
		<tr>
			<td>Boca de Caixa</td>
			<td>Agendamento com o cliente, pagamento em boca de caixa juntamente com o cliente</td>
		</tr>
	</table>
	
	<table id="mytable" style="width: 850px;margin-top: 20px;">
		<tr>
			<th class="titulo" colspan="3">Passo a passo:</th>
		</tr>
		<tr>
			<th style="width: 100px;"><b>Ação 01</b></th>
			<th style="width: 100px;"><b>Operação</b></th>
			<td>Mediante fechamento do contrato, solicitar o EXTRATO DE OPERAÇÃO e EXTRATO DA CONTA CORRENTE ATUALIZADO</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th style="width: 100px;"><b>Quitador</b></th>
			<td>Após recebimento dos contratos e aprovação pela análise será feito agendamento pelo o Quitador com o cliente</td>
		</tr>
		<tr>
			<th><b>Ação 03</b></th>
			<th style="width: 100px;"><b>Quitador</b></th>
			<td>Na data agendada com o cliente será feito a quitação</td>
		</tr>
		<tr>
			<th><b>Ação 04</b></th>
			<th style="width: 100px;"><b>Apoio Comercial</b></th>
			<td>Controle de averbação</td>
		</tr>
	</table>

</div>

<div id="cef" style="display: none;font-size: 16px;">
	<br/>
	<h2 style="color: #b5b5b5;">Caixa Econômica</h2><br/>
	
	<table id="mytable" style="width: 850px;">
		<tr>
			<th style="width: 100px;">Modelo Quitação</th>
			<th>Processo</th>
		</tr>
		<tr>
			<td>Boca de Caixa</td>
			<td>Agendamento com o cliente, pagamento em boca de caixa juntamente com o cliente</td>
		</tr>
	</table>
	
	<table id="mytable" style="width: 850px;margin-top: 20px;">
		<tr>
			<th class="titulo" colspan="3">Passo a passo:</th>
		</tr>
		<tr>
			<th style="width: 100px;"><b>Ação 01</b></th>
			<th style="width: 100px;"><b>Operação</b></th>
			<td>Orientar cliente que deverá ir até agência com Quitador</td>
		</tr>
		<tr>
			<th><b>Ação 02</b></th>
			<th style="width: 100px;"><b>Quitador</b></th>
			<td>Após recebimento dos contratos e aprovação pela análise será feito agendamento pelo o Quitador com o cliente</td>
		</tr>
		<tr>
			<th><b>Ação 03</b></th>
			<th style="width: 100px;"><b>Quitador</b></th>
			<td>Na data agendada com o cliente será feito a quitação</td>
		</tr>
		<tr>
			<th><b>Ação 04</b></th>
			<th style="width: 100px;"><b>Apoio Comercial</b></th>
			<td>Controle de averbação</td>
		</tr>
	</table>
</div>

<%@ include file="/footer.jspf" %> 