<%@ include file="/header.jspf"%>

<script type="text/javascript">
	
</script>

<div id="content-header">
	<h1>DashBoard</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users" href="<c:url value=""/>"><i class="icon-user"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
		<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="<c:url value="/menu/inicio/${usuarioInfo.perfil.chave}" />" title="Dashboard" class="tip-bottom"><i class="icon-home"></i> Dashboard</a>
	<a href="#" class="current">Contratos</a>
</div>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<div class="widget-box">
				<div class="widget-title">
					<span class="icon"> <i class="icon-signal"></i></span>
					<h5>Filtros</h5>
					<div class="buttons">
						<a href="javascript:${function }" class="btn btn-mini"><i class="icon-refresh"></i> Busca</a>
					</div>
				</div>

				<input type="hidden" id="busca_Tipo" name="busca_Tipo" value="${tipobusca }" />


				<div class="widget-content" style="padding: 8px;">
					<div class="row-fluid">
						<div class="span2"></div>
						<div class="span3"></div>
					</div>
				</div>

			</div>
		</div>
	</div>
</div>



<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">

				<div class="widget-box">
					<div class="widget-title"><span class="icon"><i class="icon-signal"></i></span><h5>Contratos</h5></div>
					<div id="resultado" class="widget-content">
						<c:if test="${not empty contratos}">
							<table class="table table-bordered table-striped table-hover data-table">
								<thead>	
									<tr>
										<th>
											Data
										</th>
										<th>
											Supervisor
										</th>
										<th>
											Consultor
										</th>
										<th>
											Cliente
										</th>
										<th>
											Cpf
										</th>
										<th>
											Banco:
										</th>
										<th>
											Produto:
										</th>
										<th>
											Banco Comprado:
										</th>
										<th>
											Parcela
										</th>
										<th>
											Coeficiente
										</th>
										<th>
											Prazo
										</th>
										<th>
											Dívida
										</th>
										<th>
											Liquido
										</th>
										<th>
											Meta
										</th>
										<th>
											Status
										</th>
										<th>
											Pós Venda
										</th>
									</tr>
								</thead>
								<tbody>		
									<c:forEach items="${contratos}" var="contrato">
										<tr>
											<td>
												<fmt:formatDate value="${contrato.formulario.created.time}" pattern="dd/MM/yyyy" />
											</td>
											<td >
												${contrato.usuario.supervisorUsuario.nome }
											</td>
											<td >
												${contrato.usuario.nome }
											</td>
											<td >
												${contrato.formulario.parceiroNegocio.nome }
											</td>
											<td >
												${contrato.formulario.parceiroNegocio.cpf }
											</td>
											<td >
												${contrato.banco.nome }
											</td>
											<td >
												${contrato.produto.nome }
											</td>
											<td >
												${contrato.recompraBanco.nome }
											</td>
											<td >
												${contrato.valorParcela }
											</td>
											<td >
												<fmt:formatNumber type="number" pattern="#.#####" value="${contrato.coeficiente.valor }" />
											</td>
											<td >
												${contrato.prazo }
											</td>
											<td >
												${contrato.valorDivida }
											</td>
											<td >
												${contrato.valorLiquido }
											</td>
											<td >
												${contrato.valorMeta }
											</td>
											<td >
												<a href="<c:url value="/contrato/status/${contrato.contrato_id}"/>">${contrato.etapa.nome }</a>
											</td>
											<td >
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
<%@ include file="/footer.jspf"%>
