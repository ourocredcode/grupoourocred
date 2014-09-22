<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/header.jspf" %>

<script type="text/javascript">

function mostra(div){

	document.getElementById("analiseBI").style.display = "none";

	if(document.getElementById(div).style.display == "none") {
		document.getElementById(div).style.display = "inline";
	} else {
		document.getElementById(div).style.display = "none";
	}

}

</script>

<div id="content-header">
	<h1>BI - Análise de Vendas</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
	<a href="#" class="current">BI - Análise de Vendas</a>
</div>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">

			<div id="analiseBI" style="display: inline;font-size: 16px;">
			
				<c:if test="${ usuarioInfo.organizacao.nome == 'OUROCRED MATRIZ' }">

					<img src="<c:url value="/visualizaScript/analiseBI.png"/>" style="width: 100%;height: 1780px" />
				
				</c:if>
				
				<c:if test="${ usuarioInfo.organizacao.nome == 'OUROCRED RJ' }">

					<img src="<c:url value="/visualizaScript/analiseBI_RJ.png"/>" style="width: 100%;height: 1780px" />
				
				</c:if>

			</div>
		</div>
	</div>
</div>			

<%@ include file="/footer.jspf" %> 