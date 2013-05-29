<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/header.jspf" %>

<script type="text/javascript">

function mostra(div){

	document.getElementById("cartaquitacao").style.display = "inline";

	if(document.getElementById(div).style.display == "none") {
		document.getElementById(div).style.display = "inline";
	} else {
		document.getElementById(div).style.display = "inline";
	}

}

</script>

<div id="menuScripts" style="float:left;min-height: 640px;font-size: 13px;margin-top: 20px;margin-right: 20px;">

<h2 style="color: #b5b5b5;">Escolha a opção abaixo:</h2><br/>
<ul>
	<li><a href="#" onclick="mostra('bancossuspensos');">Modelo Carta Quitação</a></li>
</ul>
</div>

<div id="cartaquitacao" style="display: inline;font-size: 16px;">

	<br/>
	<h2 style="color: #b5b5b5;">Modelo Carta Quitação</h2><br/>
	
	<table style="text-align: left; width: 750px;">
	<tr>
	<td ><b>(Cidade)</b>, <b>(Dia)</b> de <b>(Mês)</b> de <b>(Ano)</b> .</td>
	</tr>
	</table>
	<table style="text-align: left; width: 750px;margin-top: 20px">
	<tr><td>Ao Banco <b>(Nome do Banco)</b><br/><br/></td></tr>
	<tr><td>Eu, <b>(Nome Completo do Cliente)</b>, portador do CPF: <b>(CPF do Cliente)</b>, RG: <b>(RG do Cliente)</b>,</td></tr>
	<tr><td>domiciliado no endereço: <b>(Endereço Completo do Cliente)</b>, solicito por meio deste documento,</td></tr>
	<tr><td><font color="red">através do protocolo nº <b>(Quando necessário solicitado no Banco)</b></font>, meu boleto para quitação de dívida</td></tr>
	<tr><td>antecipada do(s) empréstimo(s) que possuo junto à este Banco, sob consignação do INSS mediante </td></tr>
	<tr><td>descontos mensais dos valores: <b>(Valores das Parcelas)</b>,  dos contratos de número: <b>(Número dos Contratos)</b>.</td></tr>
	<tr><td>Conforme Instrução Normativa nº 28 do INSS, de 16 de maio de 2008, desejo o  meu boleto para </td></tr> 
	<tr><td>quitação num prazo de até 5 dias úteis a partir do recebimento deste documento por parte da Instituição, assim como a liberação da minha margem em mesmo prazo após a quitação da minha dívida.</td></tr>
	</table>
	<table  style="text-align: right; width: 750px;margin-top: 20px">
	<tr><td>Sem Mais,<br/></td></tr>
	<tr><td><b>ASSINATURA DO CLIENTE</b><br/></td></tr>
	<tr><td><b>(Nome do Cliente)</b><br/></td></tr>
	</table>

</div>

<%@ include file="/footer.jspf" %> 

