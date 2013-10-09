<%@ include file="/header.jspf"%>


<div id="content-header">
	<h1>Detalhe Hiscons</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users" href="<c:url value="/funcionario/equipe/${usuarioInfo.usuario.usuario_id }"/>"><i class="icon-user"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
		<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="<c:url value="/menu/inicio/${usuarioInfo.perfil.chave}" />" title="Dashboard" class="tip-bottom"><i class="icon-home"></i> Dashboard</a>
	<a href="javascript:history.go(-1)">Hiscons</a>
	<a href="#" class="current">Detalhe</a>
</div>


<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">

			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th>Imagem</th>
						<th>Data solicitação</th>
						<th>Data solicitação Adm</th>
						<th>Consultor</th>
						<th>Cliente</th>
						<th>Nascimento</th>
						<th>Cpf</th>
						<th>Número Benefício</th>
						<th>Status Atual</th>
						<th>Posição</th>
					</tr>
				</thead>
				<tbody>	
					<c:forEach items="${hiscons }" var="hiscon">
						<tr>
							<td>
								<c:if test="${hiscon.isEnviado}">
									<a href="<c:url value="/visualizaHiscon/${hiscon.hisconBeneficio_id}"/>"><img src='<c:url  value="/img/pdf.gif" />' border="0"/></a>
								</c:if>
							</td>
							<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm" type="date" value="${hiscon.created.time}" /></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm" type="date" value="${hiscon.dataAdm.time}" /></td>			
							<td>${hiscon.usuario.nome }</td>									
							<td>${hiscon.parceiroBeneficio.parceiroNegocio.nome }</td>
							<td><fmt:formatDate pattern="dd/MM/yyyy" type="date" value="${hiscon.parceiroBeneficio.parceiroNegocio.dataNascimento.time }" /></td>
							<td>${hiscon.parceiroBeneficio.parceiroNegocio.cpf }</td>
							<td>${hiscon.parceiroBeneficio.numeroBeneficio }</td>				
							<td>${hiscon.etapa.nome }</td>			
							<td>${hiscon.etapaPosicao.nome }</td>		
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>	


<%@ include file="/footer.jspf"%>		