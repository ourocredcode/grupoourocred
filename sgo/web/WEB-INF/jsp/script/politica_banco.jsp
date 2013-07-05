<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/header.jspf" %>

<script type="text/javascript">

function mostra(div){

	document.getElementById("bmg").style.display = "none";
	document.getElementById("bonsucesso").style.display = "none";
	document.getElementById("bradesco").style.display = "none";
	document.getElementById("daycoval").style.display = "none";
	document.getElementById("panamericano").style.display = "none";

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
				<li><a href="#" onclick="mostra('bmg');">BMG</a></li>
			 	<li><a href="#" onclick="mostra('bonsucesso');">BONSUCESSO</a></li>
			 	<li><a href="#" onclick="mostra('bradesco');">BRADESCO</a></li>
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
		</div>
	</div>
</div>		

<%@ include file="/footer.jspf" %> 