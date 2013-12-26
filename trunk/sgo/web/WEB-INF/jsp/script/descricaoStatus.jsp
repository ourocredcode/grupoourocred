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

<div id="content-header">
	<h1>Status PN</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
	<a href="#" class="current">Status PN</a>
</div>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">

			<div id="menuScripts" style="float:left;min-height: 640px;font-size: 13px;margin-top: 20px;margin-right: 20px;">

			</div>
			
			<div id="descricaoStatus" style="display: inline;font-size: 16px;">
			
				<c:if test="${ usuarioInfo.organizacao.nome == 'OUROCRED MATRIZ' }">
				
					<iframe src="<c:url value="/visualizaScript/descricaoStatus.pdf"/>" style="width: 95%;height: 780px"></iframe>
				
				</c:if>
				
				<c:if test="${ usuarioInfo.organizacao.nome == 'OUROCRED RIBEIRAO' }">
				
					<iframe src="<c:url value="/visualizaScript/descricaoStatusRIBPRETO.pdf"/>" style="width: 95%;height: 780px"></iframe>
				
				</c:if>
			
			</div>
		</div>
	</div>
</div>			

<%@ include file="/footer.jspf" %> 