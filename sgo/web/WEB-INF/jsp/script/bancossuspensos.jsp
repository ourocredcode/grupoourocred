<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/header.jspf" %>

<script type="text/javascript">

function mostra(div){

	document.getElementById("bancossuspensos").style.display = "inline";

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
	<li><a href="#" onclick="mostra('bancossuspensos');">Bancos Suspensos</a></li>
</ul>
</div>

<div id="bancossuspensos" style="display: inline;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">Bancos Suspensos</h2><br/>

<table id="mytable" style="width: 650px;">
	<tr>
		<th>Morada</th>
		<td>Intervenção Banco Central</td>
	</tr>
	<tr>
		<th>Cruzeiro do Sul</th>
		<td>Devolve o pagamento da carta Ted</td>
	</tr>
</table>

</div>

<%@ include file="/footer.jspf" %> 