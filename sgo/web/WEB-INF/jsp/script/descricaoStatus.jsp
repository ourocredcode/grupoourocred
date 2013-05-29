<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/header.jspf" %>

<script type="text/javascript">

function mostra(div){

	document.getElementById("descricaoStatus").style.display = "none";

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
	<li><a href="#" onclick="mostra('descricaoStatus');">Descrição Status PN</a></li>
</ul>
</div>

<div id="descricaoStatus" style="display: inline;font-size: 16px;">

<br/>
<h2 style="color: #b5b5b5;">Descrição Status PN</h2><br/>

	<iframe src="<c:url value="/visualizaScript/descricaoStatus.pdf"/>" style="width: 75%;height: 700px"></iframe>

</div>

<%@ include file="/footer.jspf" %> 