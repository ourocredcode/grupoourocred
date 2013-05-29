<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/header.jspf" %>

<script type="text/javascript">

function mostra(div){

	document.getElementById("bancosrefinanciamento").style.display = "inline";

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
	<li><a href="#" onclick="mostra('bancossuspensos');">Bancos Refinanciamento</a></li>
</ul>
</div>

<div id="bancosrefinanciamento" style="display: inline;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">Bancos Refinanciamento</h2><br/>

<table id="mytable" style="width: 650px;">
	<tr>
		<th>BMG</th>
		<td>Banco não é seguro de comprar, faz refinanciamento após quitação</td>
	</tr>
	<tr>
		<th>Bonsucesso</th>
		<td>Banco faz retenção de parcelas e refinancia neste período</td>
	</tr>
	<tr>
		<th>Cifra</th>
		<td>É Banco BMG</td>
	</tr>
	<tr>
		<th>BMC</th>
		<td>Banco Parceiro</td>
	</tr>
	<tr>
		<th>Bradesco</th>
		<td>Banco Parceiro</td>
	</tr>
	<tr>
		<th>Daycoval</th>
		<td>Procedimento complicado de quitação</td>
	</tr>
</table>

</div>

<%@ include file="/footer.jspf" %> 