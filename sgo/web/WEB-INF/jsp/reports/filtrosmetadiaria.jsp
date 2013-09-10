<%@ include file="/header.jspf"%>

<script type="text/javascript">

</script>

<div id="content-header">
	<h1>Relatório Meta Diária</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
	</div>
</div>

	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> DashBoard</a>
		<a href="#" class="current">Filtros Aprovados</a>
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
		<form id="filtroAprovadosForm" name="filtroAprovadosForm" action="<c:url value="/reports/metadiaria"/>" method="POST">
			
			<div class="widget-box">

					<div class="widget-title">
						<span class="icon"><i class="icon-signal"></i>
						</span><h5>Filtros</h5>
						<div class="buttons"><button type="submit" class="btn btn-mini" ><i class="icon-file icon-black"></i> Gerar Relatório </button></div>
					</div>

					<div class="widget-content">
						<div class="row-fluid">
							<div class="span12">
								<div class="span12 center" style="text-align: center;font-size: 7px;">					

										<div class="row-fluid">
											<div class="span3">
												<label for="empresa">Empresa</label>
												<input class="input-medium" id="empresaId" name="empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden" readonly="readonly"/>
												<input class="input-large" id="empresa" name="empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" readonly="readonly" />
											</div>
							
											<div class="span3">
												<label for="organizacao">Organização</label>
												<input  class="input-medium" id="organizacaoId" name="organizacao.organizacao_id"  value="${usuarioInfo.organizacao.organizacao_id }" type="hidden" readonly="readonly"/>
												<input  class="input-large" id="organizacao" name="organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" readonly="readonly" />							
											</div>
											
											<div class="span2">
												<label for="ano">Ano:</label>
												<select id="ano" name="ano" class="input-medium">
													<option value="0">Ano</option>
													<option value="2011">2011</option>
													<option value="2012">2012</option>
													<option value="2013" selected="selected">2013</option>
												</select>
											</div>
											<div class="span2">
												<label for="mes">Mês:</label>
												<select id="mes" name="mes" class="input-medium">
													<option value="0">Mês</option>
													<c:forEach items="${meses}" var="mes">
														<option value="${mes}" <c:if test="${mesAtual == mes }"> selected ="selected" </c:if>>
															<c:if test="${mes == 0}">Janeiro</c:if>
															<c:if test="${mes == 1}">Fevereiro</c:if>
															<c:if test="${mes == 2}">Março</c:if>
															<c:if test="${mes == 3}">Abril</c:if>
															<c:if test="${mes == 4}">Maio</c:if>
															<c:if test="${mes == 5}">Junho</c:if>
															<c:if test="${mes == 6}">Julho</c:if>
															<c:if test="${mes == 7}">Agosto</c:if>
															<c:if test="${mes == 8}">Setembro</c:if>
															<c:if test="${mes == 9}">Outubro</c:if>
															<c:if test="${mes == 10}">Novembro</c:if>
															<c:if test="${mes == 11}">Dezembro</c:if>

													</c:forEach>
												</select>
											</div>
											
											<div class="span2">								
												<label for="usuario">Supervisor</label>
												<select id="usuarioId" name="usuario.usuario_id" class="input-medium">
													<option value="">Todos</option>
													<c:forEach items="${supervisores}" var="supervisor">
														<option value="${supervisor.usuario_id}">${supervisor.apelido}</option>
													</c:forEach>
												</select>
											</div>

										</div>

								</div>
							</div>
						</div>
					</div>
				</div>
		</form>
		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>
