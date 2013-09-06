<%@ include file="/header.jspf"%>

	<script type="text/javascript">
	
	jQuery(function($){ 
		
		$('.data-table').dataTable({
			
			"oLanguage": {    
				"sProcessing": "Aguarde enquanto os dados são carregados ...",    
				"sLengthMenu": "Mostrar _MENU_ registros por pagina",    
				"sZeroRecords": "Nenhum registro correspondente ao criterio encontrado",    
				"sInfoEmtpy": "Exibindo 0 a 0 de 0 registros",    
				"sInfo": "Exibindo de _START_ a _END_ de _TOTAL_ registros",    
				"sInfoFiltered": "",    
				"sSearch": "Procurar",    
				"oPaginate": {       
						"sFirst":    "Primeiro",       
						"sPrevious": "Anterior ",       
						"sNext":     " Próximo",      
						"sLast":     "Último"   
						} 
			}, 

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
		<h1>DashBoard - ${usuarioInfo.usuario.nome }</h1>
		
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Arquivos"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Usuários" href="<c:url value="/funcionario/equipe/${usuarioInfo.usuario.usuario_id }"/>"><i class="icon-user"></i></a>
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
						<div class="buttons"><a href="javascript:window.location.reload()" class="btn btn-mini"><i class="icon-refresh"></i> Atualiza Status</a></div>
					</div>

					<div class="widget-content">
						<div class="row-fluid">
							<div class="span12">
								<div class="span12 center" style="text-align: center;font-size: 7px;">					
									<ul class="stat-boxes">
										
										<li class="popover-users">
											
											<div class="right" style="width: 135px;">
												<strong><fmt:formatNumber type="NUMBER" value="${totalValorContratos}" minFractionDigits="2" /></strong>
												T. Contratos 
											</div>
										</li>
										
										<li class="popover-users">
											
											<div class="right" style="width: 135px;">
												<strong><fmt:formatNumber type="NUMBER" value="${totalContratoLiquido}" minFractionDigits="2" /></strong>
												T. C. Liquido
											</div>
										</li>
										<li class="popover-users">
											
											<div class="right" style="width: 135px;">
												<strong><fmt:formatNumber type="NUMBER" value="${totalValorLiquido}" minFractionDigits="2" /></strong>
												Vl Líquido
											</div>
										</li>
										<li class="popover-users">
											
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
			
			<c:if test="${usuarioInfo.perfil.chave == 'Gestor'}">
				<div class="span6">
	
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon">
								<i class="icon-signal"></i>
							</span>
							<h5>Performance Equipes</h5>
						</div>
						<div class="widget-content">
	
								<h5>Valores de contratos por equipes em <fmt:formatDate value="${calInicio.time}" pattern="dd/MM/yyyy" /></h5>
								<table class="table data-table">
								<thead>
								  <tr>
									<th>Supervisor</th>
									<th>Qtd.</th>
									<th>Valor Contrato</th>
									<th>Valor C. Liquido</th>
									<th>Valor Liquido
									<th>Meta</th>
								  </tr>
								</thead>
								<tbody>
									<c:forEach items="${mapEquipesCount }" var="map">
										<tr class="success">
											<td>${map.key }</td>
											<td>${map.value[1] }</td>
											<td>${map.value[2] }</td>
											<td>${map.value[3] }</td>
											<td>${map.value[4] }</td>
											<td>R$ <fmt:formatNumber type="NUMBER" value="${map.value[5]}" minFractionDigits="2" /></td>
										</tr>
									</c:forEach>
								</tbody>
							  </table>
	
						</div>
					</div>
				</div>
			</c:if>

	</div>
</div>
<%@ include file="/footer.jspf"%>