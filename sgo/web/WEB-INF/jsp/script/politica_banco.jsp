<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/header.jspf" %>

<script type="text/javascript">

function mostra(div){

	document.getElementById("banrisul").style.display = "none";
	document.getElementById("banrisul_siape").style.display = "none";
	document.getElementById("bgn").style.display = "none";
	document.getElementById("bgn_siape").style.display = "none";
	document.getElementById("bmg").style.display = "none";
	document.getElementById("bmg_siape").style.display = "none";
	document.getElementById("bonsucesso").style.display = "none";
	document.getElementById("bonsucesso_siape").style.display = "none";
	document.getElementById("bradesco").style.display = "none";
	document.getElementById("daycoval").style.display = "none";
	document.getElementById("daycoval_siape").style.display = "none";
	document.getElementById("panamericano").style.display = "none";
	document.getElementById("panamericano_siape").style.display = "none";
	document.getElementById("sabemi_siape").style.display = "none";
	document.getElementById("safra").style.display = "none";

	if(document.getElementById(div).style.display == "none") {
		document.getElementById(div).style.display = "inline";
	} else {
		document.getElementById(div).style.display = "none";
	}

}

</script>

<div id="content-header">
	<h1>Políticas Bancos</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
	<a href="#" class="current">Políticas de Bancos</a>
</div>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">



			<div id="menuScripts" style="float:left;min-height: 640px;font-size: 13px;margin-top: 20px;margin-right: 20px;">

			<ul>
				<li><a href="#" onclick="mostra('banrisul');">BANRISUL</a></li>
				<li><a href="#" onclick="mostra('banrisul_siape');">BANRISUL (SIAPE)</a></li>
				<li><a href="#" onclick="mostra('bgn');">BGN</a></li>
				<li><a href="#" onclick="mostra('bgn_siape');">BGN (SIAPE)</a></li>
				<li><a href="#" onclick="mostra('bmg');">BMG</a></li>
				<li><a href="#" onclick="mostra('bmg_siape');">BMG (SIAPE)</a></li>
			 	<li><a href="#" onclick="mostra('bonsucesso');">BONSUCESSO</a></li>
			 	<li><a href="#" onclick="mostra('bonsucesso_siape');">BONSUCESSO (SIAPE)</a></li>
			 	<li><a href="#" onclick="mostra('bradesco');">BRADESCO</a></li>
			 	<li><a href="#" onclick="mostra('daycoval');">DAYCOVAL</a></li>
			 	<li><a href="#" onclick="mostra('daycoval_siape');">DAYCOVAL (SIAPE)</a></li>
			 	<li><a href="#" onclick="mostra('panamericano');">PANAMERICANO</a></li>
			 	<li><a href="#" onclick="mostra('panamericano_siape');">PANAMERICANO (SIAPE)</a></li>
			 	<li><a href="#" onclick="mostra('sabemi_siape');">SABEMI (SIAPE)</a></li>
			 	<li><a href="#" onclick="mostra('safra');">SAFRA</a></li>
			</ul>
			</div>
			
			<div id="banrisul" style="display: none;font-size: 16px;">
			
			<br/>
			
			<h2 style="color: #b5b5b5;">BANRISUL</h2><br/>
			
				<iframe src="<c:url value="/visualizaScript/politicas_banrisul.pdf"/>" style="width: 70%;height: 800px"></iframe>
			
			</div>
			
			<div id="banrisul_siape" style="display: none;font-size: 16px;">
			
			<br/>
			
			<h2 style="color: #b5b5b5;">BANRISUL</h2><br/>
			
				<iframe src="<c:url value="/visualizaScript/politicas_banrisul_siape.pdf"/>" style="width: 70%;height: 800px"></iframe>
			
			</div>
			
			<div id="bgn" style="display: none;font-size: 16px;">
			
			<br/>
			
			<h2 style="color: #b5b5b5;">BGN</h2><br/>
			
				<iframe src="<c:url value="/visualizaScript/politicas_bgn.pdf"/>" style="width: 70%;height: 800px"></iframe>
			
			</div>
			
			<div id="bgn_siape" style="display: none;font-size: 16px;">
			
			<br/>
			
			<h2 style="color: #b5b5b5;">BGN (SIAPE)</h2><br/>
			
				<iframe src="<c:url value="/visualizaScript/politicas_bgn_siape.pdf"/>" style="width: 70%;height: 800px"></iframe>
			
			</div>
			
			<div id="bmg" style="display: none;font-size: 16px;">
			
			<br/>
			<h2 style="color: #b5b5b5;">BMG</h2><br/>
			
				<iframe src="<c:url value="/visualizaScript/politicas_bmg.pdf"/>" style="width: 70%;height: 800px"></iframe>
			
			</div>
			
			<div id="bmg_siape" style="display: none;font-size: 16px;">
			
			<br/>
			<h2 style="color: #b5b5b5;">BMG (SIAPE)</h2><br/>
			
				<iframe src="<c:url value="/visualizaScript/politicas_bmg_siape.pdf"/>" style="width: 70%;height: 800px"></iframe>
			
			</div>
			
			<div id="bonsucesso" style="display: none;font-size: 16px;">
			
			<br/>
			<h2 style="color: #b5b5b5;">BONSUCESSO</h2><br/>
			
				<iframe src="<c:url value="/visualizaScript/politicas_bonsucesso.pdf"/>" style="width: 70%;height: 800px"></iframe>
			
			</div>
			
			<div id="bonsucesso_siape" style="display: none;font-size: 16px;">
			
			<br/>
			<h2 style="color: #b5b5b5;">BONSUCESSO (SIAPE)</h2><br/>
			
				<iframe src="<c:url value="/visualizaScript/politicas_bonsucesso_siape.pdf"/>" style="width: 70%;height: 800px"></iframe>
			
			</div>
			
			<div id="bradesco" style="display: none;font-size: 16px;">
			
			<br/>
			<h2 style="color: #b5b5b5;">BRADESCO</h2><br/> 
			
				<iframe src="<c:url value="/visualizaScript/politicas_bradesco.pdf"/>" style="width: 70%;height: 600px"></iframe>
			
			</div>

			<div id="daycoval" style="display: none;font-size: 16px;">
			
			<br/>
			<h2 style="color: #b5b5b5;">DAYCOVAL</h2><br/> 
			
				<iframe src="<c:url value="/visualizaScript/politicas_daycoval.pdf"/>" style="width: 70%;height: 600px"></iframe>
			
			</div>
			
			<div id="daycoval_siape" style="display: none;font-size: 16px;">
			
			<br/>
			<h2 style="color: #b5b5b5;">DAYCOVAL</h2><br/> 
			
				<iframe src="<c:url value="/visualizaScript/politicas_daycoval_siape.pdf"/>" style="width: 70%;height: 600px"></iframe>
			
			</div>
			
			<div id="panamericano" style="display: none;font-size: 16px;">
			
			<br/>
			<h2 style="color: #b5b5b5;">PANAMERICANO</h2><br/> 
			
				<iframe src="<c:url value="/visualizaScript/politicas_panamericano.pdf"/>" style="width: 70%;height: 600px"></iframe>
			
			</div>
			
			<div id="panamericano_siape" style="display: none;font-size: 16px;">
			
			<br/>
			<h2 style="color: #b5b5b5;">PANAMERICANO (SIAPE)</h2><br/> 
			
				<iframe src="<c:url value="/visualizaScript/politicas_panamericano_siape.pdf"/>" style="width: 70%;height: 600px"></iframe>
			
			</div>
			
			<div id="sabemi_siape" style="display: none;font-size: 16px;">
			
			<br/>
			<h2 style="color: #b5b5b5;">SABEMI (SIAPE)</h2><br/> 
			
				<iframe src="<c:url value="/visualizaScript/politicas_sabemi_siape.pdf"/>" style="width: 70%;height: 600px"></iframe>
			
			</div>
			
			
			<div id="safra" style="display: none;font-size: 16px;">
			
			<br/>
			<h2 style="color: #b5b5b5;">SAFRA</h2><br/> 
			
				<iframe src="<c:url value="/visualizaScript/politicas_safra.pdf"/>" style="width: 70%;height: 600px"></iframe>
			
			</div>
		</div>
	</div>
</div>		

<%@ include file="/footer.jspf" %> 