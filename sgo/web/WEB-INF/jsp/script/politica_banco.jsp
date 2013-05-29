<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/header.jspf" %>

<script type="text/javascript">

function mostra(div){

	document.getElementById("bmg").style.display = "none";
	document.getElementById("bonsucesso").style.display = "none";
	document.getElementById("bradesco").style.display = "none";
	document.getElementById("bv").style.display = "none";
	document.getElementById("daycoval").style.display = "none";
	document.getElementById("panamericano").style.display = "none";

	if(document.getElementById(div).style.display == "none") {
		document.getElementById(div).style.display = "inline";
	} else {
		document.getElementById(div).style.display = "none";
	}

}

</script>

<div id="menuScripts" style="float:left;min-height: 640px;font-size: 13px;margin-top: 20px;margin-right: 20px;">
<h2 style="color: #b5b5b5;">POLÍTICAS:</h2><br/>
<ul>
	<li><a href="#" onclick="mostra('bmg');">BMG</a></li>
 	<li><a href="#" onclick="mostra('bonsucesso');">BONSUCESSO</a></li>
 	<li><a href="#" onclick="mostra('bradesco');">BRADESCO</a></li>
 	<!--  
 	<li><a href="#" onclick="mostra('bv');">BV</a></li>
 	-->
 	<li><a href="#" onclick="mostra('daycoval');">DAYCOVAL</a></li>
 	<li><a href="#" onclick="mostra('panamericano');">PANAMERICANO</a></li>
</ul>
</div>

<div id="bmg" style="display: none;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">BMG</h2><br/>

	<iframe src="<c:url value="/visualizaScript/politicas_bmg.pdf"/>" style="width: 70%;height: 800px"></iframe>

</div>

<div id="bonsucesso" style="display: none;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">BONSUCESSO</h2><br/>

	<iframe src="<c:url value="/visualizaScript/politicas_bonsucesso.pdf"/>" style="width: 70%;height: 800px"></iframe>

</div>

<div id="bradesco" style="display: none;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">BRADESCO</h2><br/> 

	<iframe src="<c:url value="/visualizaScript/politicas_bradesco.pdf"/>" style="width: 70%;height: 600px"></iframe>

</div>

<div id="bv" style="display: none;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">BV</h2><br/> 

	<iframe src="<c:url value="/visualizaScript/politicas_bv.pdf"/>" style="width: 70%;height: 600px"></iframe>

</div>

<div id="daycoval" style="display: none;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">DAYCOVAL</h2><br/> 

	<iframe src="<c:url value="/visualizaScript/politicas_daycoval.pdf"/>" style="width: 70%;height: 600px"></iframe>

</div>

<div id="panamericano" style="display: none;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">PANAMERICANO</h2><br/> 

	<iframe src="<c:url value="/visualizaScript/politicas_panamericano.pdf"/>" style="width: 70%;height: 600px"></iframe>

</div>

<%@ include file="/footer.jspf" %> 