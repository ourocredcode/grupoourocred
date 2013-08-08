<%@ include file="/header.jspf" %> 



	<div id="content-header">
		<h1>Histórico Cliente</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users" href="<c:url value="/funcionario/equipe/${usuarioInfo.usuario.usuario_id }"/>"><i class="icon-user"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="<c:url value="/menu/inicio/${usuarioInfo.perfil.chave}" />" title="Dashboard" class="tip-bottom"><i class="icon-home"></i> Dashboard</a>
		<a href="javascript:history.go(-1)">Status Contrato</a>
		<a href="#" class="current">Status</a>
	</div>

	<c:if test="${not empty notice}">
		<c:choose>
			<c:when test="${fn:contains(notice,'Erro:')}">
					<div class="alert alert-error">
						<strong>${notice }</strong>
						<a href="#" data-dismiss="alert" class="close">×</a>
					</div>
			</c:when>
			<c:otherwise>
					<div class="alert alert-success">
						<strong>${notice }</strong>
						<a href="#" data-dismiss="alert" class="close">×</a>
					</div>
			</c:otherwise>
		</c:choose>
	</c:if>
	
	
	
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon">
							<i class="icon-align-justify"></i>									
						</span>
						<h5>Hiscons</h5>
					</div>
					<div class="widget-content padding">
							
							<c:if test="${not empty hisconsBeneficio }">

										<table class="table table-striped table-bordered">
										<thead>
											<tr>
												<th>Imagem</th>
												<th>Data solicitação</th>
												<th>Data solicitação Adm</th>
												
												<c:if test="${usuarioInfo.perfil.chave != 'Consultor' }">
													<th>Consultor</th>
												</c:if>
												
												<th>Cliente</th>
												<th>Cpf</th>
												<th>Número Benefício</th>
												<th>Status Atual</th>
											</tr>
										</thead>
										<tbody>	
											<c:forEach items="${hisconsBeneficio }" var="hiscon">
												<tr>
													<td>
														<c:if test="${hiscon.isEnviado}">
															<a href="<c:url value="/visualizaHiscon/${hiscon.hisconBeneficio_id}"/>"><img src='<c:url  value="/img/pdf.gif" />' border="0"/></a>
														</c:if>
													</td>
													<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm" type="date" value="${hiscon.created.time}" /></td>
													<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm" type="date" value="${hiscon.dataAdm.time}" /></td>			

													<c:if test="${usuarioInfo.perfil.chave != 'Consultor' }">
														<td>${hiscon.usuario.nome }</td>
													</c:if>									

													<td>${hiscon.parceiroBeneficio.parceiroNegocio.nome }</td>
													<td>${hiscon.parceiroBeneficio.parceiroNegocio.cpf }</td>
													<td>${hiscon.parceiroBeneficio.numeroBeneficio }</td>				
													<td>${hiscon.etapa.nome }</td>
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

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon">
							<i class="icon-align-justify"></i>									
						</span>
						<h5>Dados Contrato</h5>
					</div>
					<div class="widget-content padding">

						<div class="row-fluid">
							<div id="divContrato" style="float: none;clear:both;">
	
								<table class="table table-striped table-bordered">
									<c:if test="${not empty contratos}">
									<thead>	
										<tr>
											<th scope="col">
												Data
											</th>
											<th scope="col">
												Status
											</th>
											<th scope="col">
												Banco
											</th>
											<th scope="col">
												Produto
											</th>
											<th scope="col">
												Banco Comprado
											</th>
											<th scope="col">
												Parcela Aberto
											</th>
											<th scope="col">
												Contrato:
											</th>
											<th scope="col">
												Parcel:
											</th>
											<th scope="col">
												Prazo:
											</th>
											<th scope="col">
												Dívida
											</th>
											<th scope="col">
												Seguro
											</th>
											<th scope="col">
												Desconto
											</th>
											<th scope="col">
												Valor Liquido
											</th>
											<th scope="col">
												Coeficiente
											</th>
											<th scope="col">
												Observação
											</th>
										</tr>
									</thead>
									<tbody>		
										<c:forEach var="contrato" items="${contratos }">
											<tr>
												<td>
													<fmt:formatDate pattern="dd/MM/yyyy" value="${contrato.created.time }" />
												</td>
												<td>
													<a href="<c:url value="/contrato/status/${contrato.contrato_id}"/>">${contrato.etapa.nome }</a>
												</td>
												<td>
													${contrato.produto.nome }
												</td>
												<td>
													${contrato.recompraBanco.nome }
												</td>
												<td>
													${contrato.qtdParcelasAberto }
												</td>
												<td>
													${contrato.valorContrato }
												</td>
												<td>
													${contrato.valorParcela }
												</td>
												<td>
													${contrato.prazo }
												</td>
												<td>
													${contrato.valorDivida }
												</td>
												<td>
													${contrato.valorSeguro }
												</td>
												<td>
													${contrato.desconto }
												</td>
												<td>
													${contrato.valorLiquido }
												</td>
												<td>
													<fmt:formatNumber type="number" pattern="#.#####" value="${contrato.coeficiente.valor }" />
												</td>
												<td class="label_txt" style="text-align: center">
													<c:if test="${not empty contrato.observacao}">
														<a href="#" onclick="return showObs('${contrato.observacao}');" style="border: 0"><img src="../img/lupa.gif" border="0"/></a>
													</c:if>
												</td>
											</tr>
										</c:forEach>
									</tbody>	
									</c:if>
								</table>
							</div>
						
						</div>
						
					</div>
				</div>						
			</div>
		</div>
	</div>
	
	
		

<%@ include file="/footer.jspf" %>