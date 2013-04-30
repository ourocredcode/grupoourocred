<%@ include file="/header.jspf"%>

<script type="text/javascript" src="<c:url value="../js/unicorn.dashboard.js"/>"></script>

	<div id="content-header">
		<h1>DashBoard</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
		<a href="#" class="current">Dashboard</a>
	</div>
	
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">

				<div class="alert alert-info">
					Welcome in the <strong>Unicorn Admin Theme</strong>. Don't forget to check all the pages!
					<a href="#" data-dismiss="alert" class="close">×</a>
				</div>

				<div class="widget-box">
					<div class="widget-title"><span class="icon"><i class="icon-signal"></i></span><h5>Vendas</h5><div class="buttons"><a href="#" class="btn btn-mini"><i class="icon-refresh"></i> Update stats</a></div></div>
					<div class="widget-content">
						<div class="row-fluid">
						<div class="span4">

						</div>
						<div class="span8">

						</div>	
						</div>							
					</div>
				</div>

				<div class="widget-box">
					<div class="widget-title"><span class="icon"><i class="icon-signal"></i></span><h5>Contratos</h5></div>
					<div class="widget-content">
						<div class="row-fluid">
						<div class="span12">
							<c:if test="${not empty contratos}">
								<table class="table table-striped table-bordered" style="font-size: 12px">
									<thead>	
										<tr>
											<th colspan="13">
												Total de contratos 
											</th>
										</tr>
										<tr>
											<th scope="col">
												Data
											</th>
											<th scope="col">
												Supervisor
											</th>
											<th scope="col">
												Consultor
											</th>
											<th scope="col">
												Cliente
											</th>
											<th scope="col">
												Cpf
											</th>
											<th scope="col">
												Banco:
											</th>
											<th scope="col">
												Produto:
											</th>
											<th scope="col">
												Banco Comprado:
											</th>
											<th scope="col">
												Parcela
											</th>
											<th scope="col">
												Coeficiente
											</th>
											<th scope="col">
												Prazo
											</th>
											<th scope="col">
												Dívida
											</th>
											<th scope="col">
												Liquido
											</th>
											<th scope="col">
												Meta
											</th>
											<th scope="col">
												Status
											</th>
											<th scope="col">
												Pós Venda
											</th>
										</tr>
									</thead>
									<tbody>		
										<c:forEach items="${contratos}" var="contrato">
											<tr>
												<td class="label_txt">
													<fmt:formatDate value="${contrato.formulario.created.time}" pattern="dd/MM/yyyy" />
												</td>
												<td class="label_txt">
													SUPERVISOR
												</td>
												<td class="label_txt">
													CONSULTOR
												</td>
												<td class="label_txt">
													${contrato.formulario.parceiroNegocio.nome }
												</td>
												<td class="label_txt">
													${contrato.formulario.parceiroNegocio.cpf }
												</td>
												<td class="label_txt">
													${contrato.banco.nome }
												</td>
												<td class="label_txt">
													${contrato.produto.nome }
												</td>
												<td class="label_txt">
													${contrato.recompraBanco.nome }
												</td>
												<td class="label_txt">
													${contrato.valorParcela }
												</td>
												<td class="label_txt">
													<fmt:formatNumber type="number" pattern="#.#####" value="${contrato.coeficiente.valor }" />
												</td>
												<td class="label_txt">
													${contrato.prazo }
												</td>
												<td class="label_txt">
													${contrato.valorDivida }
												</td>
												<td class="label_txt">
													${contrato.valorLiquido }
												</td>
												<td class="label_txt">
													${contrato.valorMeta }
												</td>
												<td class="label_txt">
													<a href="<c:url value="/contrato/status/${contrato.contrato_id}"/>">STATUS</a>
												</td>
												<td class="label_txt">
													PÓS VENDA
												</td>
											</tr>
										</c:forEach>
									</tbody>	
								</table>
							</c:if>
						</div>
						</div>							
					</div>
				</div>
			</div>
		</div>
	</div>

<%@ include file="/footer.jspf"%>