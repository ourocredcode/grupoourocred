<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/header.jspf" %>

<script type="text/javascript">

function mostra(div){

	document.getElementById("bradesco").style.display = "none";
	document.getElementById("bv").style.display = "none";

	if(document.getElementById(div).style.display == "none") {
		document.getElementById(div).style.display = "inline";
	} else {
		document.getElementById(div).style.display = "none";
	}

}

</script>

<div id="menuScripts" style="float:left;min-height: 640px;font-size: 13px;margin-top: 20px;margin-right: 20px;">
<h2 style="color: #b5b5b5;">Coeficientes:</h2><br/>
<ul>
	<li><a href="#" onclick="mostra('bradesco');">Bradesco</a></li>
 	<li><a href="#" onclick="mostra('bv');">BV</a></li>
</ul>
</div>

<div id="bv" style="display: none;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">BV</h2><br/>

	<iframe src="<c:url value="/visualizaScript/coeficientes_BV.pdf"/>" style="width: 70%;height: 800px"></iframe>


</div>

<div id="bradesco" style="display: none;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">BRADESCO</h2><br/>

	<iframe src="<c:url value="/visualizaScript/coeficientes_Bradesco.pdf"/>" style="width: 70%;height: 800px"></iframe>

</div>

<%@ include file="/footer.jspf" %> 