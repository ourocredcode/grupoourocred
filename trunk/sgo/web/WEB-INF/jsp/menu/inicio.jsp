<%@ include file="/header.jspf"%>

	<script type="text/javascript">
	
	jQuery(function($){ 
		
		$('.data-table').dataTable({
			"bJQueryUI": true,
			"sPaginationType": "full_numbers",
			"sDom": '<""l>t<"F"fp>',
			"bFilter": false
		});

		$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
		
		$('select').select2();

		$("span.icon input:checkbox, th input:checkbox").click(function() {
			var checkedStatus = this.checked;
			var checkbox = $(this).parents('.widget-box').find('tr td:first-child input:checkbox');		
			checkbox.each(function() {
				this.checked = checkedStatus;
				if (checkedStatus == this.checked) {
					$(this).closest('.checker > span').removeClass('checked');
				}
				if (this.checked) {
					$(this).closest('.checker > span').addClass('checked');
				}
			});
		});
		
		
		
	});

	</script>

	<script type="text/javascript" src="<c:url value="/js/unicorn.dashboard.js"/>"></script>
	
	

	<div id="content-header">
		<h1>DashBoard - ${usuarioInfo.usuario.nome } </h1>
		
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
					Bem Vindo ao <strong>SGO - Sistema Grupo Ourocred </strong>! Boas Vendas !<a href="#" data-dismiss="alert" class="close">×</a>
				</div>

				<div class="widget-box">

					<div class="widget-title">
						<span class="icon"><i class="icon-signal"></i>
						</span><h5>Vendas - <fmt:formatDate value="${calInicio.time}" pattern="dd/MM" /> até <fmt:formatDate value="${calFim.time}" pattern="dd/MM" /></h5>
						<div class="buttons"><a href="#" class="btn btn-mini"><i class="icon-refresh"></i> Update stats</a></div>
					</div>

					<div class="widget-content">
						<div class="row-fluid">
							<div class="span12">
								<div class="span12 center" style="text-align: center;font-size: 8px;">					
									<ul class="stat-boxes">
										
										<li class="popover-users">
											<div class="left peity_bar_neutral"><span>1,2,10,2,4,1,5,10</span>0%</div>
											<div class="right" style="width: 135px;">
												<strong><fmt:formatNumber type="NUMBER" value="${totalValorContratos}" minFractionDigits="2" /></strong>
												T. Contratos 
											</div>
										</li>
										
										<li class="popover-users">
											<div class="left peity_bar_neutral"><span>1,2,10,2,4,1,5,10</span>0%</div>
											<div class="right" style="width: 135px;">
												<strong><fmt:formatNumber type="NUMBER" value="${totalContratoLiquido}" minFractionDigits="2" /></strong>
												T. C. Liquido
											</div>
										</li>
										<li class="popover-users">
											<div class="left peity_bar_neutral"><span>1,2,10,2,4,1,5,10</span>0%</div>
											<div class="right" style="width: 135px;">
												<strong><fmt:formatNumber type="NUMBER" value="${totalValorLiquido}" minFractionDigits="2" /></strong>
												Vl Líquido
											</div>
										</li>
										<li class="popover-users">
											<div class="left peity_bar_neutral"><span>1,2,10,2,4,1,5,10</span>0%</div>
											<div class="right" style="width: 135px;">
												<strong><fmt:formatNumber type="NUMBER" value="${totalValorMeta}" minFractionDigits="2" /></strong>
												Vl Meta
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
		
			<div class="span3">

				<div class="widget-box">
					<div class="widget-title">
						<span class="icon">
							<i class="icon-tag"></i>
						</span>
						<h5>Contratos e Status</h5>
					</div>
					<div class="widget-content">
							
							<h5>Status Final <fmt:formatDate value="${calInicio.time}" pattern="dd/MM" /> até <fmt:formatDate value="${calFim.time}" pattern="dd/MM" /></h5>
							<table class="table table-bordered table-striped">
							<thead>
							  <tr>
								<th>Status</th>
								<th>Total</th>
							  </tr>
							</thead>
							<tbody>
								<c:forEach items="#{mapEtapasFinal }" var="map">
									<tr>
										<c:choose>
											<c:when test="${map.key == 'Aprovado' }"><td><a href="#" onclick="javascript:window.location='/sgo/menu/contratos/aprovados'">${map.key }</a></td></c:when>
											<c:otherwise><td>${map.key }</td></c:otherwise>
										</c:choose>
										<c:choose>
											<c:when test="${map.key == 'Concluído' }"><td><span class="badge badge-success">R$ <fmt:formatNumber type="NUMBER" value="${map.value }" minFractionDigits="2" /></span></td></c:when>
											<c:when test="${map.key == 'Aprovado' }"><td><span class="badge badge-info">R$ <fmt:formatNumber type="NUMBER" value="${map.value }" minFractionDigits="2" /></span></td></c:when>
											<c:when test="${map.key == 'Recusado' }"><td><span class="badge badge-important">R$ <fmt:formatNumber type="NUMBER" value="${map.value }" minFractionDigits="2" /></span></td></c:when>
											<c:otherwise><td><span class="badge badge-info">R$ <fmt:formatNumber type="NUMBER" value="${map.value }" minFractionDigits="2" /></span></td></c:otherwise>
										</c:choose>
									</tr>
								
								</c:forEach>
							</tbody>
						  </table>

							<h5>Status</h5>
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
										<c:choose>
											<c:when test="${map.key == 'Aguardando Status' }"><td>${map.key }</td></c:when>
											<c:when test="${map.key == 'Pendente Administrativo' }"><td>${map.key }</td></c:when>
											<c:when test="${map.key == 'Pendente Agendamento' }"><td>${map.key }</td></c:when>
											<c:when test="${map.key == 'Pendente Banco' }"><td>${map.key }</td></c:when>
											<c:when test="${map.key == 'Pendente Coeficiente' }"><td>${map.key }</td></c:when>
											<c:when test="${map.key == 'Pendente Conferência' }"><td>${map.key }</td></c:when>
											<c:when test="${map.key == 'Recalcular' }"><td>${map.key }</td></c:when>
											<c:otherwise><td>${map.key }</td></c:otherwise>
										</c:choose>
										<c:choose>
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
						<span class="icon"><i class="icon-signal"></i> </span>
						<h5>Coeficientes</h5>
					</div>
					<div class="widget-content">

							<h5>Coeficientes</h5>
							<table class="table table-bordered table-striped table-hover data-table" >
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