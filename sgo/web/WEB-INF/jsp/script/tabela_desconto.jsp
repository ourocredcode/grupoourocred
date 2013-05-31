<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/header.jspf" %>

<script type="text/javascript">

function mostra(div){

	document.getElementById("bancosbons").style.display = "none";
	document.getElementById("bancosbonsBGN").style.display = "none";
	document.getElementById("bancosmediosBV").style.display = "none";
	document.getElementById("bancosmediosBB_CEF").style.display = "none";
	document.getElementById("bancosmedios").style.display = "none";
	document.getElementById("bancosruins").style.display = "none";

	if(document.getElementById(div).style.display == "none") {
		document.getElementById(div).style.display = "inline";
	} else {
		document.getElementById(div).style.display = "none";
	}

}

</script>

<div id="menuScripts" style="float:left;min-height: 640px;font-size: 13px;margin-top: 20px;margin-right: 20px;">
<h2 style="color: #b5b5b5;">Escolha o Script Abaixo:</h2><br/>
<ul>
	<li><a href="#" onclick="mostra('bancosbons');">Bancos Bons</a></li>
	<li><a href="#" onclick="mostra('bancosbonsBGN');">Bancos Bons BGN</a></li>
 	<li><a href="#" onclick="mostra('bancosmediosBV');">Bancos Médios BV</a></li>
 	<li><a href="#" onclick="mostra('bancosmediosBB_CEF');">Bancos Médios Banco Brasil / C.E.F.</a></li>
 	<li><a href="#" onclick="mostra('bancosmedios');">Bancos Médios</a></li>
 	<li><a href="#" onclick="mostra('bancosruins');">Bancos Ruins</a></li>
</ul>
</div>

<div id="bancosbons" style="display: none;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">Bancos Bons</h2><br/>

	<iframe src="<c:url value="/visualizaScript/tabelaDescontos_bancosBons.pdf"/>" style="width: 70%;height: 800px"></iframe>

</div>

<div id="bancosmediosBV" style="display: none;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">Bancos Médios BV</h2><br/>

	<iframe src="<c:url value="/visualizaScript/tabelaDescontos_bancosMediosBv.pdf"/>" style="width: 70%;height: 800px"></iframe>

</div>

<div id="bancosmediosBB_CEF" style="display: none;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">Bancos Médios Banco Brasil / C.E.F.</h2><br/>

	<iframe src="<c:url value="/visualizaScript/tabelaDescontos_bancosMedios_BB_CEF.pdf"/>" style="width: 70%;height: 800px"></iframe>

</div>

<div id="bancosbonsBGN" style="display: none;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">Bancos Médios BGN</h2><br/>

	<iframe src="<c:url value="/visualizaScript/tabelaDescontos_bancosBonsBGN.pdf"/>" style="width: 70%;height: 800px"></iframe>

</div>

<div id="bancosmedios" style="display: none;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">Bancos Médios</h2><br/> 

	<iframe src="<c:url value="/visualizaScript/tabelaDescontos_bancosMedios.pdf"/>" style="width: 70%;height: 800px"></iframe>

</div>

<div id="bancosruins" style="display: none;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">Bancos Ruins</h2><br/> 

	<iframe src="<c:url value="/visualizaScript/tabelaDescontos_bancosRuins.pdf"/>" style="width: 70%;height: 800px"></iframe>

</div>

<%@ include file="/footer.jspf" %> 