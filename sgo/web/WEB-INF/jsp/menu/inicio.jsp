<%@ include file="/header.jspf"%>

	<script type="text/javascript">
	
	jQuery(function($){ 
		
		$('.data-table').dataTable({
			"bJQueryUI": true,
			"sPaginationType": "full_numbers",
			"sDom": '<""l>t<"F"fp>',
			"bFilter": false,
			"aoColumnDefs": [{
				                "aTargets": [ 1 ],
				                "bUseRendered": false,
				                "fnRender": function ( o ) {
				                    return  o.oSettings.fnFormatNumber( parseFloat( o.aData[ o.iDataColumn ] ) ) ;
				                }
				            },
			                 {
			                     "aTargets": [ 2,3,4 ],
			                     "bUseRendered": false,
			                     "fnRender": function ( o ) {
			                         return 'R$ ' + o.oSettings.fnFormatNumber( parseFloat( o.aData[ o.iDataColumn ] ).toFixed(2) ).replace(',','.').replace(',','.').replace('..',',').replace(',.',',') ;
			                     }
			                 }
			             ]
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
		
		jQuery.fn.dataTableExt.oSort['formatted-num-asc'] = function(a,b) {
			
			/* Remove any formatting */
			var x = a.match(/\d/) ? a.replace( /[^\d\-\.]/g, "" ) : 0;
			var y = b.match(/\d/) ? b.replace( /[^\d\-\.]/g, "" ) : 0;

			/* Parse and return */
			return parseFloat(x) - parseFloat(y);
			};

		jQuery.fn.dataTableExt.oSort['formatted-num-desc'] = function(a,b) {
			var x = a.match(/\d/) ? a.replace( /[^\d\-\.]/g, "" ) : 0;
			var y = b.match(/\d/) ? b.replace( /[^\d\-\.]/g, "" ) : 0;

			return parseFloat(y) - parseFloat(x);
		};
		
		
		
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
						</span><h5>Vendas - <fmt:formatDate value="${calInicio.time}" pattern="dd/MM/yyyy" /></h5>
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
		
			<div class="span6">

				<div class="widget-box">
					<div class="widget-title">
						<span class="icon">
							<i class="icon-tag"></i>
						</span>
						<h5>Contratos e Status</h5>
					</div>
					<div class="widget-content">
							
							<h5>Status Aprovados e Recusados <fmt:formatDate value="${calMesInicio.time}" pattern="dd/MM" /> até <fmt:formatDate value="${calMesFim.time}" pattern="dd/MM" /></h5>
							<table class="table table-bordered table-striped">
							<thead>
							  <tr>
								<th>Status</th>
								<th>Quantidade</th>
								<th>Contrato</th>
								<th>C. Líquido</th>
								<th>Meta</th>
							  </tr>
							</thead>
							<tbody>
								<c:forEach items="#{mapEtapasFinal }" var="map">
									<tr>
										<c:choose>
											<c:when test="${map.key == 'Aprovado' }">
													<tr class="success">
														<td><a href="<c:url value="/menu/contratos/aprovados" />">${map.key }</a></td>
														<td><fmt:formatNumber value="${map.value[1] }" type="number" maxFractionDigits="0" /></td>
														<td><fmt:formatNumber value="${map.value[2] }" type="currency" /></td>
														<td><fmt:formatNumber value="${map.value[3] }" type="currency" /></td>
														<td><fmt:formatNumber value="${map.value[4] }" type="currency" /></td>
													</tr>
											</c:when>
											<c:when test="${map.key == 'Concluído' }">
													<tr class="success">
														<td><a href="<c:url value="/menu/contratos/concluidos" />">${map.key }</a></td>
														<td><fmt:formatNumber value="${map.value[1] }" type="number" maxFractionDigits="0" /></td>
														<td><fmt:formatNumber value="${map.value[2] }" type="currency" /></td>
														<td><fmt:formatNumber value="${map.value[3] }" type="currency" /></td>
														<td><fmt:formatNumber value="${map.value[4] }" type="currency" /></td>
													</tr>
											</c:when>
											<c:when test="${map.key == 'Recusado' }">
													<tr class="error">
														<td><a href="<c:url value="/menu/contratos/recusados" />">${map.key }</a></td>
														<td><fmt:formatNumber value="${map.value[1] }" type="number" maxFractionDigits="0" /></td>
														<td><fmt:formatNumber value="${map.value[2] }" type="currency" /></td>
														<td><fmt:formatNumber value="${map.value[3] }" type="currency" /></td>
														<td><fmt:formatNumber value="${map.value[4] }" type="currency" /></td>
													</tr>
											</c:when>
										</c:choose>
									</tr>
								</c:forEach>
							</tbody>
						  </table>

							<h5>Status Geral</h5>
							<table class="table data-table">
							<thead>
							  <tr>
								<th>Status</th>
								<th>Quantidade</th>
								<th>Contrato</th>
								<th>C. Líquido</th>
								<th>Meta</th>
							  </tr>
							</thead>
							<tbody>
								<c:forEach items="#{mapEtapas }" var="map">
									
									<c:choose>
										<c:when test="${map.key == 'Aguardando Status' }">
												<tr class="info">
													<td><a href="<c:url value="/menu/contratos/etapas/${map.value[0]}" />">${map.key }</a></td>
													<td>${map.value[1] }</td>
													<td>${map.value[2] }</td>
													<td>${map.value[3] }</td>
													<td>${map.value[4] }</td>
												</tr>
										</c:when>
										<c:when test="${map.key == 'Pendente Administrativo' }">
												<tr class="error">
													<td><a href="<c:url value="/menu/contratos/etapas/${map.value[0]}" />">${map.key }</a></td>
													<td>${map.value[1] }</td>
													<td>${map.value[2] }</td>
													<td>${map.value[3] }</td>
													<td>${map.value[4] }</td>
												</tr>
										</c:when>
										<c:when test="${map.key == 'Pendente Agendamento' }">
												<tr class="error">
													<td><a href="<c:url value="/menu/contratos/etapas/${map.value[0]}" />">${map.key }</a></td>
													<td>${map.value[1] }</td>
													<td>${map.value[2] }</td>
													<td>${map.value[3] }</td>
													<td>${map.value[4] }</td>
												</tr>
										</c:when>
										<c:when test="${map.key == 'Pendente Banco' }">
												<tr class="error">
													<td><a href="<c:url value="/menu/contratos/etapas/${map.value[0]}" />">${map.key }</a></td>
													<td>${map.value[1] }</td>
													<td>${map.value[2] }</td>
													<td>${map.value[3] }</td>
													<td>${map.value[4] }</td>
												</tr>
										</c:when>
										<c:when test="${map.key == 'Pendente Coeficiente' }">
												<tr class="error">
													<td><a href="<c:url value="/menu/contratos/etapas/${map.value[0]}" />">${map.key }</a></td>
													<td>${map.value[1] }</td>
													<td>${map.value[2] }</td>
													<td>${map.value[3] }</td>
													<td>${map.value[4] }</td>
												</tr>
										</c:when>
										<c:when test="${map.key == 'Pendente Conferência' }">
												<tr class="error">
													<td><a href="<c:url value="/menu/contratos/etapas/${map.value[0]}" />">${map.key }</a></td>
													<td>${map.value[1] }</td>
													<td>${map.value[2] }</td>
													<td>${map.value[3] }</td>
													<td>${map.value[4] }</td>
												</tr>
										</c:when>
										<c:when test="${map.key == 'Recalcular' }">
												<tr class="error">
													<td><a href="<c:url value="/menu/contratos/etapas/${map.value[0]}" />">${map.key }</a></td>
													<td>${map.value[1] }</td>
													<td>${map.value[2] }</td>
													<td>${map.value[3] }</td>
													<td>${map.value[4] }</td>
												</tr>
										</c:when>
										<c:otherwise>
												<tr class="info">
													<td><a href="<c:url value="/menu/contratos/etapas/${map.value[0]}" />">${map.key }</a></td>
													<td>${map.value[1] }</td>
													<td>${map.value[2] }</td>
													<td>${map.value[3] }</td>
													<td>${map.value[4] }</td>
												</tr>
										</c:otherwise>
									</c:choose>
								
								</c:forEach>
							</tbody>
						  </table>

					</div>
				</div>
			</div>

	</div>
</div>
<%@ include file="/footer.jspf"%>