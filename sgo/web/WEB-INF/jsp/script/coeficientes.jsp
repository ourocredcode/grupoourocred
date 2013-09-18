<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/header.jspf" %>

<script type="text/javascript">

function mostra(div){

	document.getElementById("bradesco").style.display = "none";
	document.getElementById("bv").style.display = "none";
	document.getElementById("panamericano").style.display = "none";
	document.getElementById("safra").style.display = "none";

	if(document.getElementById(div).style.display == "none") {
		document.getElementById(div).style.display = "inline";
	} else {
		document.getElementById(div).style.display = "none";
	}

}

</script>


<div id="content-header">
	<h1>Coeficientes</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
	<a href="#" class="current">Coeficientes</a>
</div>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
		
		<c:if test="${usuarioInfo.perfil.chave == 'Gestor' && usuarioInfo.organizacao.nome == 'OUROCRED MATRIZ' }">	

			<div id="menuScripts" style="float:left;min-height: 640px;font-size: 13px;margin-top: 20px;margin-right: 20px;">

			<ul>
				<li><a href="#" onclick="mostra('bradesco');">Bradesco</a></li>
			 	<li><a href="#" onclick="mostra('bv');">BV</a></li>
			 	<li><a href="#" onclick="mostra('panamericano');">Panamericano</a></li>
			 	<li><a href="#" onclick="mostra('safra');">Safra</a></li>
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
			
			<div id="panamericano" style="display: none;font-size: 16px;">
			
			<br/>
			<h2 style="color: #b5b5b5;">PANAMERICANO</h2><br/>
			
				<iframe src="<c:url value="/visualizaScript/coeficientes_Panamericano.pdf"/>" style="width: 70%;height: 800px"></iframe>
			
			</div>
			
			<div id="safra" style="display: none;font-size: 16px;">
			
			<br/>
			<h2 style="color: #b5b5b5;">SAFRA</h2><br/>
			
				<iframe src="<c:url value="/visualizaScript/coeficientes_Safra.pdf"/>" style="width: 70%;height: 800px"></iframe>
			
			</div>

		</c:if>
	
		</div>
	</div>
</div>			


<%@ include file="/footer.jspf" %> 