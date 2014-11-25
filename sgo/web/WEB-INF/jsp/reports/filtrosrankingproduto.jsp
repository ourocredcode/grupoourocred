<%@ include file="/header.jspf"%>

<script type="text/javascript">

$(document).ready(function() {
	
	$("#data").mask("99/99/9999");
	$("#dataFim").mask("99/99/9999");
	
	$('#data').datepicker({
		dateFormat: 'dd/mm/yy'
	});
	$('#dataFim').datepicker({
		dateFormat: 'dd/mm/yy'
	});

	$("#supervisor_id").change(function() {   
		
		var supervisor_id = $("#supervisor_id").val();
	
		if(supervisor_id != '')
			$("#consultor_id").load('<c:url value="/reports/consultores" />', {'supervisor_id': supervisor_id});
		else
			$('#consultor_id option').remove();
	
	});

});

</script>

<div id="content-header">
	<h1>Relatório Ranking Produto</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
	</div>
</div>

	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> DashBoard</a>
		<a href="#" class="current">Filtros Ranking Produto</a>
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
		<form id="filtroAprovadosForm" name="filtroAprovadosForm" action="<c:url value="/reports/rankingproduto"/>" method="POST" target="_blank">
			
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
											<div class="span2">
												<label for="empresa">Empresa</label>
												<input class="input-medium" id="empresaId" name="empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden" readonly="readonly"/>
												<input class="input-medium" id="empresa" name="empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" readonly="readonly" />
											</div>
							
											<div class="span2">
												<label for="organizacao">Organização</label>
												<input  class="input-medium" id="organizacaoId" name="organizacao.organizacao_id"  value="${usuarioInfo.organizacao.organizacao_id }" type="hidden" readonly="readonly"/>
												<input  class="input-medium" id="organizacao" name="organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" readonly="readonly" />							
											</div>
										
										</div>	
										
										<br/>
										
										<div class="row-fluid">	
											
											<div class="span1">								
												<label for="data">Data Início</label>
												<input id="data" name="data"  class="input-mini" type="text" required="required"/>
											</div>

											<div class="span1">
												<label for="dataFim">Data Fim</label>
												<input id="dataFim" name="dataFim" class="input-mini" type="text"  />
											</div>
											
											<div class="span2">								
												<label for="produto_id">Produto</label>
												<select id="produto_id" name="produto_id" class="input-medium">
													<c:forEach items="${produtos}" var="produto">
														<option value="${produto.produto_id}">${produto.nome}</option>
													</c:forEach>
												</select>
											</div>

											<div class="span2">								
												<label for="supervisor_id">Supervisor</label>
												<select id="supervisor_id" name="supervisor_id" class="input-medium">
													<option value="">Todos</option>
													<c:forEach items="${supervisores}" var="supervisor">
														<option value="${supervisor.usuario_id}">${supervisor.apelido}</option>
													</c:forEach>
												</select>
											</div>

											<div class="span2">
												<label for="consultor_id">Consultor</label>
												<select id="consultor_id" name="consultor_id" class="input-medium">
		
													<option value="">Selecione um Supervisor</option>

												</select>
											</div>
											
											<div class="span1">
												<label for="concluidoCheckBox">Apenas Concluídos</label>
												<input id="concluidoCheckBox" name="concluidoCheck" checked="checked" value="1" type="checkbox">
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
