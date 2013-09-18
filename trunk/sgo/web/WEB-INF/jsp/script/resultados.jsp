<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/header.jspf" %>

<script type="text/javascript">

function mostra(div){

	document.getElementById("janeiro2013").style.display = "none";
	document.getElementById("fevereiro2013").style.display = "none";
	document.getElementById("marco2013").style.display = "none";
	document.getElementById("abril2013").style.display = "none";
	document.getElementById("maio2013").style.display = "none";
	document.getElementById("junho2013").style.display = "none";

	if(document.getElementById(div).style.display == "none") {
		document.getElementById(div).style.display = "inline";
	} else {
		document.getElementById(div).style.display = "none";
	}

}

</script>

<div id="content-header">
	<h1>Resultados</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
	<a href="#" class="current">Resultados</a>
</div>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">

		<c:if test="${usuarioInfo.perfil.chave == 'Gestor' && usuarioInfo.organizacao.nome == 'OUROCRED MATRIZ' }">	

				<div id="menuScripts" style="float:left;min-height: 640px;font-size: 13px;margin-top: 20px;margin-right: 20px;">

				<ul>
					<li><a href="#" onclick="mostra('janeiro2013');">Janeiro 2013</a></li>
					<li><a href="#" onclick="mostra('fevereiro2013');">Fevereiro 2013</a></li>
				 	<li><a href="#" onclick="mostra('marco2013');">Março 2013</a></li>
				 	<li><a href="#" onclick="mostra('abril2013');">Abril 2013</a></li>
				 	<li><a href="#" onclick="mostra('maio2013');">Maio 2013</a></li>
				 	<li><a href="#" onclick="mostra('junho2013');">Junho 2013</a></li>
				</ul>
				</div>
				
				<div id="janeiro2013" style="display: none;font-size: 16px;">
				
				<br/>
				<h3 style="color: #b5b5b5;">Janeiro 2013</h3><br/>
				
					<iframe src="<c:url value="/visualizaScript/resultado_janeiro2013.pdf"/>" style="width: 80%;height: 750px"></iframe>
				
				</div>
				
				<div id="fevereiro2013" style="display: none;font-size: 16px;">
				
				<br/>
				<h3 style="color: #b5b5b5;">Fevereiro 2013</h3><br/>
				
					<iframe src="<c:url value="/visualizaScript/resultado_fevereiro2013.pdf"/>" style="width: 80%;height: 750px"></iframe>
				
				</div>
				
				<div id="marco2013" style="display: none;font-size: 16px;">
				
				<br/>
				<h3 style="color: #b5b5b5;">Março 2013</h3><br/>
				
					<iframe src="<c:url value="/visualizaScript/resultado_marco2013.pdf"/>" style="width: 80%;height: 750px"></iframe>
				
				</div>
				
				<div id="abril2013" style="display: none;font-size: 16px;">
				
				<br/>
				<h3 style="color: #b5b5b5;">Abril 2013</h3><br/>
				
					<iframe src="<c:url value="/visualizaScript/resultado_abril2013.pdf"/>" style="width: 80%;height: 750px"></iframe>
				
				</div>
				
				<div id="maio2013" style="display: none;font-size: 16px;">
				
				<br/>
				<h3 style="color: #b5b5b5;">Maio 2013</h3><br/>
				
					<iframe src="<c:url value="/visualizaScript/resultado_maio2013.pdf"/>" style="width: 80%;height: 750px"></iframe>
				
				</div>
				
				<div id="junho2013" style="display: none;font-size: 16px;">
				
				<br/>
				<h3 style="color: #b5b5b5;">Junho 2013</h3><br/>
				
					<iframe src="<c:url value="/visualizaScript/resultado_junho2013.pdf"/>" style="width: 80%;height: 750px"></iframe>
				
				</div>

		</c:if>

	</div>
</div>
</div>		

<%@ include file="/footer.jspf" %> 