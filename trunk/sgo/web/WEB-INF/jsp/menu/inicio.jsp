<%@ include file="/header.jspf"%>

	<script type="text/javascript" src="<c:url value="/js/unicorn.dashboard.js"/>"></script>

	<div id="content-header">
		<h1>DashBoard - ${usuarioInfo.usuario.nome } / Equipe : ${usuarioInfo.usuario.supervisorUsuario.nome }</h1>
		
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users" href="<c:url value="/funcionario/equipe/${usuarioInfo.usuario.usuario_id }"/>"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>

	</div>

	<div id="breadcrumb">
		<a href="#" title="Dashboard" class="current"><i class="icon-home"></i> Dashboard</a>
	</div>

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">

				<div class="alert alert-info">
					Bem Vindo ao <strong>SGO - Sistema Grupo Ourocred </strong>! Boas Vendas ! Perfil : ${usuarioInfo.perfil.chave }<a href="#" data-dismiss="alert" class="close">×</a>
				</div>

				<div class="widget-box">

					<div class="widget-title">
						<span class="icon"><i class="icon-signal"></i>
						</span><h5>Vendas</h5>
						<div class="buttons"><a href="#" class="btn btn-mini"><i class="icon-refresh"></i> Update stats</a></div>
					</div>

					<div class="widget-content">
						<div class="row-fluid">
							<div class="span12">
								<div class="span12 center" style="text-align: center;">					
									<ul class="stat-boxes">
										
										<li class="popover-visits">
											<div class="left peity_bar_good"><span>1,3,10,2,1,4,${countContratos}</span>10%</div>
											<div class="right">
												<a href="#" onclick="javascript:window.location='/sgo/menu/contratos/${usuarioInfo.perfil.chave}'">
													<strong>${countContratos}</strong>
													Contratos
												</a>
											</div>
										</li>
										<li class="popover-users">
											<div class="left peity_bar_neutral"><span>1,2,10,2,4,1,5,${countClientes }</span>0%</div>
											<div class="right">
												<strong>${countClientes }</strong>
												Clientes
											</div>
										</li>
										<li class="popover-orders">
											<div class="left peity_bar_bad"><span>1030,480,200,5200,1200,250,${totalValorMeta }</span>0%</div>
											<div class="right" style="width: auto;">
												<a href="#" onclick="javascript:window.location='/sgo/menu/contratos/aprovados'">
													<strong><fmt:formatNumber type="NUMBER" value="${totalValorMeta}" minFractionDigits="2" /></strong>
													Aprovados
												</a>
											</div>
										</li>
										<li class="popover-tickets">
											<div class="left peity_line_good"><span>0,0,0,0,0,0,0</span>0%</div>
											<div class="right">
												<strong>0</strong>
												Pendentes
											</div>
										</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="container-fluid">
		<div class="row-fluid">
				
			
			<div class="span4">

				<div class="widget-box">
					<div class="widget-title">
						<span class="icon">
							<i class="icon-tag"></i>
						</span>
						<h5>Coeficientes</h5>
					</div>
					<div class="widget-content">

							<h3>Coeficientes</h3>
							<table class="table table-bordered table-striped">
							<thead>
							  <tr>
								<th>Data</th>
								<th>Banco</th>
								<th>Tabela</th>
								<th>Valor</th>
								<th>Meta</th>
							  </tr>
							</thead>
							<tbody>
								<c:forEach var="coeficiente" items="${coeficientes }">
									<tr>
										<td><fmt:formatDate value="${coeficiente.created.time}" pattern="dd/MM/yyyy" /></td>
										<td>${coeficiente.banco.nome }</td>
										<td>${coeficiente.tabela.nome }</td>
										<td><fmt:formatNumber value="${coeficiente.valor}" pattern="0.00000" /></td>
										<td><fmt:formatNumber value="${coeficiente.percentualMeta}" pattern="0%" /></td>
									</tr>
								</c:forEach>
							</tbody>
						  </table>

					</div>
				</div>
			</div>	

			<div class="span3">

				<div class="widget-box">
					<div class="widget-title">
						<span class="icon">
							<i class="icon-tag"></i>
						</span>
						<h5>Contratos e Status</h5>
					</div>
					<div class="widget-content">

							<h3>Status</h3>
							<table class="table table-bordered table-striped">
							<thead>
							  <tr>
								<th>Status</th>
								<th>Quantidade</th>
							  </tr>
							</thead>
							<tbody>
								<c:forEach items="#{mapEtapas }" var="map">
									<tr>
										<td>
										<form id="buscaStatusForm" action="<c:url value="/menu/busca/status"/>" method="POST">
											<input type="hidden" name="status" value='${map.key }' />
											<a href="#" onclick="submit();">${map.key }</a>
										</form>
										</td>
										<c:choose>
											<c:when test="${map.key == 'Aprovado' }"><td><span class="badge badge-success">${map.value }</span></td></c:when>
											<c:when test="${map.key == 'Aguardando Status' }"><td><span class="badge">${map.value }</span></td></c:when>
											<c:when test="${map.key == 'Pendente Administrativo' }"><td><span class="badge badge-important">${map.value }</span></td></c:when>
											<c:when test="${map.key == 'Pendente Agendamento' }"><td><span class="badge badge-important">${map.value }</span></td></c:when>
											<c:when test="${map.key == 'Pendente Banco' }"><td><span class="badge badge-important">${map.value }</span></td></c:when>
											<c:when test="${map.key == 'Pendente Coeficiente' }"><td><span class="badge badge-important">${map.value }</span></td></c:when>
											<c:when test="${map.key == 'Pendente Conferência' }"><td><span class="badge badge-important">${map.value }</span></td></c:when>
											<c:when test="${map.key == 'Recalcular' }"><td><span class="badge badge-important">${map.value }</span></td></c:when>
											<c:otherwise><td><span class="badge badge-info">${map.value }</span></td></c:otherwise>
										</c:choose>
									</tr>
								
								</c:forEach>
							</tbody>
						  </table>

					</div>
				</div>
			</div>

			<div class="span4">
			
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon">
							<i class="icon-tag"></i>
						</span>
						<h5>Controles</h5>
					</div>
					<div class="widget-content">
						<div class="row-fluid">
						<div class="span8">
							<ul class="site-stats">
								<li><i class="icon-arrow-right"></i><a href="/sgo/menu/contratos/boletos"><small>Boletos</small></a> </li>
								<li><i class="icon-arrow-right"></i><a href="/sgo/menu/contratos/averbacao"><small>Averbação</small></a>  </li>
							</ul>
						</div>
					</div>
				</div>

			</div>

		</div>
	</div>
</div>
<%@ include file="/footer.jspf"%>