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
								<th>Name</th>
								<th>Example</th>
							  </tr>
							</thead>
							<tbody>
							  <tr>
								<td>
								  Aguardando Status
								</td>
								<td>
								  <span class="badge">1</span>
								</td>
							  </tr>
							  <tr>
								<td>
								  Qualidade e Pós Venda
								</td>
								<td>
								  <span class="badge badge-success">2</span>
								</td>
							  </tr>
							  <tr>
								<td>
								  Em Assinatura
								</td>
								<td>
								  <span class="badge badge-warning">4</span>
								</td>
							  </tr>
							  <tr>
								<td>
								  Em Conferência
								</td>
								<td>
								  <span class="badge badge-important">6</span>
								</td>
							  </tr>
							  <tr>
								<td>
								  Em Análise
								</td>
								<td>
								  <span class="badge badge-info">8</span>
								</td>
							  </tr>
							  <tr>
								<td>
								  Pendentes
								</td>
								<td>
								  <span class="badge badge-inverse">10</span>
								</td>
							  </tr>
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
						<h5>Produtos</h5>
					</div>
					<div class="widget-content">

							<h3>Produtos</h3>
							<table class="table table-bordered table-striped">
							<thead>
							  <tr>
								<th>Name</th>
								<th>Example</th>
							  </tr>
							</thead>
							<tbody>
							  <tr>
								<td>
								  Margem Limpa
								</td>
								<td>
								  <span class="badge">1</span>
								</td>
							  </tr>
							  <tr>
								<td>
								  Recompra Inss
								</td>
								<td>
								  <span class="badge badge-success">2</span>
								</td>
							  </tr>
							  <tr>
								<td>
								  Recompra RMC
								</td>
								<td>
								  <span class="badge badge-warning">4</span>
								</td>
							  </tr>
							  <tr>
								<td>
								  Refinanciamento
								</td>
								<td>
								  <span class="badge badge-important">6</span>
								</td>
							  </tr>
							</tbody>
						  </table>

					</div>
				</div>

			</div>

			<div class="span6">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon">
							<i class="icon-tag"></i>
						</span>
						<h5>Controle de Boletos</h5>
						<div class="buttons"><a href="#" class="btn btn-mini"><i class="icon-search"></i> Buscar</a></div>
					</div>

					<div class="widget-content">
						<a href="/sgo/menu/contratos/boletos">Aguardando Boleto</a>
					</div>

				</div>	
			</div>
		</div>
	</div>

<%@ include file="/footer.jspf"%>