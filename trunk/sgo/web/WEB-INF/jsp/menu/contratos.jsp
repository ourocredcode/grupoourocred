<%@ include file="/header.jspf"%>

	<script type="text/javascript">
 
	 $(document).ready(function() {

		$("#busca_Data").mask("99/99/9999");
		$("#busca_DataFim").mask("99/99/9999");
		$("#busca_DataAprovadoInicio").mask("99/99/9999");
		$("#busca_DataAprovadoFim").mask("99/99/9999");
		$("#busca_DataRecusadoInicio").mask("99/99/9999");
		$("#busca_DataRecusadoFim").mask("99/99/9999");
		$("#busca_DataConcluidoInicio").mask("99/99/9999");
		$("#busca_DataConcluidoFim").mask("99/99/9999");


		//BOLETOS e AVERBACAO
		$("#busca_DataPrevisaoInicio").mask("99/99/9999");
		$("#busca_DataPrevisaoFim").mask("99/99/9999");
		$("#busca_DataProximaAtuacaoInicio").mask("99/99/9999");
		$("#busca_DataProximaAtuacaoFim").mask("99/99/9999");
	
		$("#busca_DataChegadaInicio").mask("99/99/9999");
		$("#busca_DataChegadaFim").mask("99/99/9999");
		$("#busca_DataVencimentoInicio").mask("99/99/9999");
		$("#busca_DataVencimentoFim").mask("99/99/9999");
		
		$("#busca_DataQuitacaoInicio").mask("99/99/9999");
		$("#busca_DataQuitacaoFim").mask("99/99/9999");
		$("#busca_DataAssinaturaInicio").mask("99/99/9999");
		$("#busca_DataAssinaturaFim").mask("99/99/9999");

			var cliente = $("#busca_Cliente").val();
			var documento = $("#busca_Documento").val();
			var data = $("#busca_Data").val();
			var dataFim = $("#busca_DataFim").val();
			var dataAprovadoInicio = $("#busca_DataAprovadoInicio").val();
			var dataAprovadoFim = $("#busca_DataAprovadoFim").val();
	 		var dataConcluidoInicio = $("#busca_DataConcluidoInicio").val();
			var dataConcluidoFim = $("#busca_DataConcluidoFim").val();
			var dataRecusadoInicio = $("#busca_DataRecusadoInicio").val();
			var dataRecusadoFim = $("#busca_DataRecusadoFim").val();
			var previsaoInicio = $("#busca_DataPrevisaoInicio").val();
			var previsaoFim = $("#busca_DataPrevisaoFim").val();
			var chegadaInicio = $("#busca_DataChegadaInicio").val();
			var chegadaFim = $("#busca_DataChegadaFim").val();
			var vencimentoInicio = $("#busca_DataVencimentoInicio").val();
			var vencimentoFim = $("#busca_DataVencimentoFim").val();
			var proximaAtuacaoInicio = $("#busca_DataProximaAtuacaoInicio").val();
			var proximaAtuacaoFim = $("#busca_DataProximaAtuacaoFim").val();
			var quitacaoInicio = $("#busca_DataQuitacaoInicio").val();
			var quitacaoFim = $("#busca_DataQuitacaoFim").val();
			var assinaturaInicio = $("#busca_DataAssinaturaInicio").val();
			var assinaturaFim = $("#busca_DataAssinaturaFim").val();
			
			if(dataAprovadoInicio == undefined || dataAprovadoInicio == '__/__/____')
				dataAprovadoInicio = "";
			if(dataAprovadoFim == undefined || dataAprovadoFim == '__/__/____')
				dataAprovadoFim = "";
			if(dataConcluidoInicio == undefined || dataConcluidoInicio == '__/__/____')
				dataConcluidoInicio = "";
			if(dataConcluidoFim == undefined || dataConcluidoFim == '__/__/____')
				dataConcluidoFim = "";
			if(dataRecusadoInicio == undefined || dataRecusadoInicio == '__/__/____')
				dataRecusadoInicio = "";
			if(dataRecusadoFim == undefined || dataRecusadoFim == '__/__/____')
				dataRecusadoFim = "";
			if(previsaoInicio == undefined || previsaoInicio == '__/__/____')
				previsaoInicio = "";
			if(previsaoFim == undefined || previsaoFim == '__/__/____')
				previsaoFim = "";
			if(chegadaInicio == undefined || chegadaInicio == '__/__/____')
				chegadaInicio = "";
			if(chegadaFim == undefined || chegadaFim == '__/__/____')
				chegadaFim = "";
			if(vencimentoInicio == undefined || vencimentoInicio == '__/__/____')
				vencimentoInicio = "";
			if(vencimentoFim == undefined || vencimentoFim == '__/__/____')
				vencimentoFim = "";
			if(proximaAtuacaoInicio == undefined || proximaAtuacaoInicio == '__/__/____')
				proximaAtuacaoInicio = "";
			if(proximaAtuacaoFim == undefined || proximaAtuacaoFim == '__/__/____')
				proximaAtuacaoFim = "";
			if(quitacaoInicio == undefined || quitacaoInicio == '__/__/____')
				quitacaoInicio = "";
			if(quitacaoFim == undefined || quitacaoFim == '__/__/____')
				quitacaoFim = "";
			if(assinaturaInicio == undefined || assinaturaInicio == '__/__/____')
				assinaturaInicio = "";
			if(assinaturaFim == undefined || assinaturaFim == '__/__/____')
				assinaturaFim = "";
			
			if(previsaoInicio != '' || previsaoFim != '' || chegadaInicio != '' || chegadaFim != '' || vencimentoInicio != '' || vencimentoFim != '' 
				|| proximaAtuacaoInicio != '' || proximaAtuacaoFim != '' || quitacaoInicio != '' || quitacaoFim != '' || assinaturaInicio != '' || assinaturaFim != ''){
				$(buscaDatasControle);
			} else if (data != '' || dataFim != '' || dataAprovadoInicio != '' || dataAprovadoFim != '' || dataConcluidoInicio != '' || dataConcluidoFim != '' 
				  || dataRecusadoInicio != '' || dataRecusadoFim != ''|| cliente != '' || documento != '' ) {
				$(buscaContratos);
			}			

		$('#busca_Data').datepicker({
			dateFormat: 'dd/mm/yy'
		});
		$('#busca_DataFim').datepicker({
			dateFormat: 'dd/mm/yy'
		});
		$('#busca_DataAprovadoInicio').datepicker({
			dateFormat: 'dd/mm/yy'
		});
		$('#busca_DataAprovadoFim').datepicker({
			dateFormat: 'dd/mm/yy'
		});
		$('#busca_DataConcluidoInicio').datepicker({
			dateFormat: 'dd/mm/yy'
		});
		$('#busca_DataConcluidoFim').datepicker({
			dateFormat: 'dd/mm/yy'
		});
		$('#busca_DataRecusadoInicio').datepicker({
			dateFormat: 'dd/mm/yy'
		});
		$('#busca_DataRecusadoFim').datepicker({
			dateFormat: 'dd/mm/yy'
		});
		$('#busca_DataPrevisaoInicio').datepicker({
			dateFormat: 'dd/mm/yy'
		});
		$('#busca_DataPrevisaoFim').datepicker({
			dateFormat: 'dd/mm/yy'
		});
		$('#busca_DataProximaAtuacaoInicio').datepicker({
			dateFormat: 'dd/mm/yy'
		});
		$('#busca_DataProximaAtuacaoFim').datepicker({
			dateFormat: 'dd/mm/yy'
		});
		$('#busca_DataChegadaInicio').datepicker({
			dateFormat: 'dd/mm/yy'
		});
		$('#busca_DataChegadaFim').datepicker({
			dateFormat: 'dd/mm/yy'
		});
		$('#busca_DataVencimentoInicio').datepicker({
			dateFormat: 'dd/mm/yy'
		});
		$('#busca_DataVencimentoFim').datepicker({
			dateFormat: 'dd/mm/yy'
		});	
		$('#busca_DataQuitacaoInicio').datepicker({
			dateFormat: 'dd/mm/yy'
		});
		$('#busca_DataQuitacaoFim').datepicker({
			dateFormat: 'dd/mm/yy'
		});	
		$('#busca_DataAssinaturaInicio').datepicker({
			dateFormat: 'dd/mm/yy'
		});
		$('#busca_DataAssinaturaFim').datepicker({
			dateFormat: 'dd/mm/yy'
		});	

		$('.data-table').dataTable({
			"bJQueryUI": true,
			"sPaginationType": "full_numbers",
			"sDom": '<""l>t<"F"fp>'
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
		
		$("#busca_Supervisor").change(function() {   
			
			var supervisor_id = $("#busca_Supervisor").val();

			if(supervisor_id != '')
				$("#busca_Consultor").load('<c:url value="/menu/consultores" />', {'supervisor_id': supervisor_id});
			else
				$('#busca_Consultor option').remove();

		});
		
		$('#loading').ajaxStart(function() {
			 $(this).show();
			 $('#resultado').hide();
			 }).ajaxStop(function() {
			 $(this).hide();
			 $('#resultado').fadeIn('fast');
		});

	 });

	 function buscaContratos(){

		var status = $("#busca_Status").val();
		var tipoRecusado = $("#busca_TipoRecusado").val();
		var justificativa = $("#busca_Justificativa").val();
		var tipoPagamento = $("#busca_TipoPagamento").val();
		var tipoAprovado = $("#busca_TipoAprovado").val();
		var empresa = $("#busca_Empresa").val();
		var informacaoSaque = $("#busca_InformacaoSaque").val();
		var cliente = $("#busca_Cliente").val();
		var documento = $("#busca_Documento").val();
		var data = $("#busca_Data").val();
		var dataFim = $("#busca_DataFim").val();
		var dataAprovadoInicio = $("#busca_DataAprovadoInicio").val();
		var dataAprovadoFim = $("#busca_DataAprovadoFim").val();
 		var dataConcluidoInicio = $("#busca_DataConcluidoInicio").val();
		var dataConcluidoFim = $("#busca_DataConcluidoFim").val();
		var dataRecusadoInicio = $("#busca_DataRecusadoInicio").val();
		var dataRecusadoFim = $("#busca_DataRecusadoFim").val();
		var bancos = $("#busca_Banco").val();
		var produtos = $("#busca_Produto").val();
		var bancosComprados = $("#busca_BancoComprado").val();
		var motivoPendencia = $("#busca_Pendente").val();
		var consultor = $("#busca_Consultor").val();
		var supervisor = $("#busca_Supervisor").val();
		
		if(dataAprovadoInicio == undefined || dataAprovadoInicio == '__/__/____')
			dataAprovadoInicio = "";
		if(dataAprovadoFim == undefined || dataAprovadoFim == '__/__/____')
			dataAprovadoFim = "";
		if(dataConcluidoInicio == undefined || dataConcluidoInicio == '__/__/____')
			dataConcluidoInicio = "";
		if(dataConcluidoFim == undefined || dataConcluidoFim == '__/__/____')
			dataConcluidoFim = "";
		if(dataRecusadoInicio == undefined || dataRecusadoInicio == '__/__/____')
			dataRecusadoInicio = "";
		if(dataRecusadoFim == undefined || dataRecusadoFim == '__/__/____')
			dataRecusadoFim = "";
		
		if(tipoRecusado == undefined)
			tipoRecusado = "";
		if(justificativa == undefined)
			justificativa = "";
		if(tipoPagamento == undefined)
			tipoPagamento = "";
		if(tipoAprovado == undefined)
			tipoAprovado = "";
		if(empresa == undefined)
			empresa = "";
		if(informacaoSaque == undefined)
			informacaoSaque = "";
		if(motivoPendencia == undefined)
			motivoPendencia = "";
		if(supervisor == undefined)
			supervisor = "";
		if(consultor == undefined)
			consultor = "";

		if(consultor == ""){
			if(supervisor == "Todos"){
				consultor = "";
			} else {
				consultor = supervisor;
			}
		}
		 
		if(status == null){
			status = new Array();
			status[0] = "";
		}
		
		if(produtos == null){
			produtos = new Array();
			produtos[0] = "";
		}
		
		if(bancos == null){
			bancos = new Array();
			bancos[0] = "";
		}
		
		if(bancosComprados == null){
			bancosComprados = new Array();
			bancosComprados[0] = "";
		}

		if(data != '' || dataFim != '' || dataAprovadoInicio != '' || dataAprovadoFim != '' || dataConcluidoInicio != '' || dataConcluidoFim != '' 
			  || dataRecusadoInicio != '' || dataRecusadoFim != ''|| cliente != '' || documento != '' ){

			 $("#resultado").load('<c:url value="/menu/busca" />',{'informacaoSaque': informacaoSaque,'tipoAprovado': tipoAprovado,'empresa':empresa,
				 	'tipoPagamento': tipoPagamento ,'tipoRecusado': tipoRecusado,'justificativa': justificativa,'status': status, 
					'cliente' : cliente , 'documento' : documento, 'data' : data, 'dataFim' : dataFim,
					'dataAprovadoInicio' : dataAprovadoInicio, 'dataAprovadoFim' : dataAprovadoFim, 'dataConcluidoInicio' : dataConcluidoInicio, 'dataConcluidoFim' : dataConcluidoFim, 
					'dataRecusadoInicio' : dataRecusadoInicio, 'dataRecusadoFim' : dataRecusadoFim, 'bancos' : bancos, 'produtos' : produtos, 'bancosComprados' : bancosComprados , 
					'motivoPendencia' : motivoPendencia, 'consultor' : consultor});
		 
		} else {

			alert("Escolha uma data ou dados do cliente.");

		}

	 }
	 
	 function buscaDatasControle(){

			var status = $("#busca_Status").val();
			var cliente = $("#busca_Cliente").val();
			var documento = $("#busca_Documento").val();
			var supervisor = $("#busca_Supervisor").val();
			var consultor = $("#busca_Consultor").val();
			var bancos = $("#busca_Banco").val();
			var produtos = $("#busca_Produto").val();
			var bancosComprados = $("#busca_BancoComprado").val();
			var empresa = $("#busca_Empresa").val();
			var data = $("#busca_Data").val();
			var dataFim = $("#busca_DataFim").val();
			var previsaoInicio = $("#busca_DataPrevisaoInicio").val();
			var previsaoFim = $("#busca_DataPrevisaoFim").val();
			var chegadaInicio = $("#busca_DataChegadaInicio").val();
			var chegadaFim = $("#busca_DataChegadaFim").val();
			var vencimentoInicio = $("#busca_DataVencimentoInicio").val();
			var vencimentoFim = $("#busca_DataVencimentoFim").val();
			var proximaAtuacaoInicio = $("#busca_DataProximaAtuacaoInicio").val();
			var proximaAtuacaoFim = $("#busca_DataProximaAtuacaoFim").val();
			var procedimento = $("#busca_Procedimento").val();
			var quitacaoInicio = $("#busca_DataQuitacaoInicio").val();
			var quitacaoFim = $("#busca_DataQuitacaoFim").val();
			var assinaturaInicio = $("#busca_DataAssinaturaInicio").val();
			var assinaturaFim = $("#busca_DataAssinaturaFim").val();

			var tipoBusca = $("#busca_Tipo").val();
			
			if(consultor == undefined)
				consultor = "";
			if(supervisor == undefined)
				supervisor = "";
			if(empresa == undefined)
				empresa = "";

			if(consultor == ""){
				if(supervisor == "Todos"){
					consultor = "";
				} else {
					consultor = supervisor;
				}
			}

			if(status == null){
				status = new Array();
				status[0] = "";
			}
			
			if(produtos == null){
				produtos = new Array();
				produtos[0] = "";
			}
			
			if(bancos == null){
				bancos = new Array();
				bancos[0] = "";
			}
			
			if(bancosComprados == null){
				bancosComprados = new Array();
				bancosComprados[0] = "";
			}

			if(data != '' || dataFim != '' || previsaoInicio != '' || previsaoFim != '' || chegadaInicio != '' || chegadaFim != '' || vencimentoInicio != '' || vencimentoFim != '' 
					|| proximaAtuacaoInicio != '' || proximaAtuacaoFim != '' || quitacaoInicio != '' || quitacaoFim != '' || procedimento != ''){
				$("#resultado").load('<c:url value="/menu/busca/controle" />',{'tipoBusca': tipoBusca,'data' : data, 'dataFim' : dataFim,
										'previsaoInicio': previsaoInicio,'previsaoFim': previsaoFim,
										'chegadaInicio': chegadaInicio,'chegadaFim': chegadaFim,'vencimentoInicio': vencimentoInicio,'vencimentoFim': vencimentoFim, 
										'proximaAtuacaoInicio': proximaAtuacaoInicio,'proximaAtuacaoFim': proximaAtuacaoFim,'quitacaoInicio': quitacaoInicio,'quitacaoFim': quitacaoFim,
										'assinaturaInicio': assinaturaInicio,'assinaturaFim': assinaturaFim,'procedimento': procedimento,'bancos': bancos, 
										'produtos': produtos,'bancosComprados': bancosComprados,'status': status,'consultor': consultor,'cliente': cliente,'documento': documento,'empresa':empresa});

			} else {

				alert("Escolha uma data para a busca.");

			}
		
	 }
	 
	 
	 function limpa(){
		 window.location.href = '<c:url value="/menu/contratos/${usuarioInfo.perfil.chave}" />';
	 }

	 </script>

	<div id="content-header">
		<h1>Busca Contratos ${fn:toUpperCase(tipobusca)}  </h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users" href="<c:url value="/funcionario/equipe/${usuarioInfo.usuario.usuario_id }"/>"><i class="icon-user"></i></a>
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
			<div class="widget-title"><span class="icon">
				<i class="icon-signal"></i></span><h5>Filtros</h5>
				<div class="buttons"><a href="javascript:${function }" class="btn btn-mini"><i class="icon-search"></i> Busca</a></div>
				<div class="buttons"><a href="javascript:limpa();" class="btn btn-mini"><i class="icon-trash"></i> Limpa</a></div>
			</div>
			
			<input type="hidden" id="busca_Tipo" name="busca_Tipo" value="${tipobusca }"/>
			
			
			<div class="widget-content" style="padding: 6px;">
				<div class="row-fluid">

					<div class="span2">

						<label for="busca_Status">Status</label>
	  					<select id="busca_Status" name="busca_Status" class="input-medium" MULTIPLE >
							<option value="">Todos...</option>
							<c:forEach items="${etapas }" var="etapa">
								<option value="${etapa.nome }">${etapa.nome }</option>
							</c:forEach>
						</select>
						
						<label for="busca_Produto">Produto</label>
						<select id="busca_Produto" name="busca_Produto" class="input-medium" MULTIPLE >
							<option value="Todos">Todos</option>
							<c:forEach items="${produtos }" var="produto">
								<option value="${produto.nome }">${produto.nome }</option>
							</c:forEach>
						</select>

						<label for="busca_Empresa">Empresa</label>
						<select id="busca_Empresa" name="busca_Empresa" class="input-medium" MULTIPLE>
							<option value="Todos">Todos</option>
							<option value="ATGGOLD">ATGGOLD</option>
							<option value="GOCX">GOCX</option>
							<option value="GRGOLD">GRGOLD</option>
							<option value="OUROCRED">OUROCRED</option>
						</select>

						<label for="busca_Banco">Banco</label>
						<select id="busca_Banco" name="busca_Banco"  class="input-medium" MULTIPLE>
							<option value="">Selecione um banco</option>
							<c:forEach items="${bancos }" var="banco">
								<option value="${banco.nome }">${banco.nome }</option>
							</c:forEach>
						</select>
						<label for="busca_BancoComprado">Banco Comprado</label>
						<select id="busca_BancoComprado" name="busca_BancoComprado"  class="input-medium" MULTIPLE>
							<option value="">Selecione um banco</option>
							<c:forEach items="${bancosComprado }" var="bancoComprado">
								<option value="${bancoComprado.nome }">${bancoComprado.nome }</option>
							</c:forEach>
						</select>
	  				</div>

	 				<div class="span3">
	
						<label for="busca_Cliente">Cliente</label>
						<input id="busca_Cliente" name="busca_Cliente" class="input-large" type="text" />
		
						<label for="busca_Documento">Documento</label>
						<input id="busca_Documento" name="busca_Documento" class="input-large" type="text" />
						
						<div class="row-fluid">
		
							<div class="span6">								
								<label for="busca_Data">Data Início</label>
								<input id="busca_Data" name="busca_Data"  class="input-small" type="text" />
							</div>
							<div class="span6">
								<label for="busca_DataFim">Data Fim</label>
								<input id="busca_DataFim" name="busca_DataFim" class="input-small" type="text"  />
							</div>
		
						</div>
						
						
							<div class="row-fluid">
								
								<c:if test="${usuarioInfo.perfil.chave == 'Administrativo' || usuarioInfo.perfil.chave == 'Gestor'}">
									<div class="span6">								
										<label for="busca_Supervisor">Supervisor</label>
										<select id="busca_Supervisor" name="busca_Supervisor" class="input-xlarge">
											<option value="">Todos</option>
											<c:forEach items="${supervisores}" var="supervisor">
												<option value="${supervisor.usuario_id}">${supervisor.nome}</option>
											</c:forEach>
										</select>
									</div>
								</c:if>

							</div>
							
							<div class="row-fluid">
							
								<c:if test="${usuarioInfo.perfil.chave == 'Administrativo' || usuarioInfo.perfil.chave == 'Gestor' || usuarioInfo.perfil.chave == 'Supervisor'}">
									<div class="span6">
										<label for="busca_Consultor">Consultor</label>
										<select id="busca_Consultor" name="busca_Consultor" class="input-xlarge">

											<option value="">Selecione um Supervisor</option>
											<c:if test="${usuarioInfo.perfil.chave == 'Supervisor'}">
												<c:forEach items="${consultores }" var="consultor">
													<option value="${consultor.usuario_id}">${consultor.nome}</option>
												</c:forEach>
											</c:if>
										</select>
									</div>
								</c:if>

							</div>
						
					</div>

	
					<div id="consultaBoleto" class="span7" style="display: ${buscaBoleto};">

						<div class="span2">
							<div class="control-group">
								<label class="control-label">Prev Início</label>
								<div class="controls">
									<input id="busca_DataPrevisaoInicio" name="busca_DataPrevisaoInicio" type="text" class="input-small"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Chegada Início</label>
								<div class="controls">
									<input id="busca_DataChegadaInicio" name="busca_DataChegadaInicio" type="text" class="input-small"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Procedimento</label>
								<select  id="busca_Procedimento" name="busca_Procedimento" class="input-medium">
									<option value="">Apoio Agregado</option>
									<option value="">Apoio Agregado</option>
									<option value="">Apoio Agregado</option>
									<option value="">Apoio Agregado</option>
									<option value="">Apoio Agregado</option>
									<option value="">Apoio Agregado</option>
								</select>
							</div>
						</div>
						<div class="span2">
							<div class="control-group">
								<label class="control-label">Prev Fim</label>
								<div class="controls">
									<input id="busca_DataPrevisaoFim" name="busca_DataPrevisaoFim" type="text" class="input-small"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Chegada Fim</label>
								<div class="controls">
									<input id="busca_DataChegadaInicio" name="busca_DataChegadaInicio" type="text" class="input-small"/>
								</div>
							</div>
						</div>
						<div class="span2">
							<div class="control-group">
								<label class="control-label">Prox Atua Início</label>
								<div class="controls">
									<input id="busca_DataProximaAtuacaoInicio" name="busca_DataProximaAtuacaoInicio" type="text" class="input-small"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Venc. Início</label>
								<div class="controls">
									<input id="busca_DataVencimentoInicio" name="busca_DataVencimentoInicio" type="text" class="input-small"/>
								</div>
							</div>
						</div>
						<div class="span2">
							<div class="control-group">
								<label class="control-label">Prox Atua Fim</label>
								<div class="controls">
									<input id="busca_DataProximaAtuacaoFim" name="busca_DataProximaAtuacaoFim" type="text" class="input-small"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Venc. Fim</label>
								<div class="controls">
									<input id="busca_DataVencimentoFim" name="busca_DataVencimentoFim" type="text" class="input-small"/>
								</div>
							</div>
						</div>
						<div class="span2">
							<div class="control-group">
								<label class="control-label">Quitação Início</label>
								<div class="controls">
									<input id="busca_DataQuitacaoInicio" name="busca_DataQuitacaoInicio" type="text" class="input-small"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Assinatura Início</label>
								<div class="controls">
									<input id="busca_DataAssinaturaInicio" name="busca_DataAssinaturaInicio" type="text" class="input-small"/>
								</div>
							</div>
						</div>
						<div class="span2">
							<div class="control-group">
								<label class="control-label">Quitação Fim</label>
								<div class="controls">
									<input id="busca_DataQuitacaoFim" name="busca_DataQuitacaoFim" type="text" class="input-small"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Assinatura Fim</label>
								<div class="controls">
									<input id="busca_DataAssinaturaFim" name="busca_DataAssinaturaFim" type="text" class="input-small"/>
								</div>
							</div>
						</div>
					</div>

			
					<div class="span4"  style="display: ${buscaAprovado};">

							<div class="row-fluid">
			
								<div class="span4">								
									<label for="busca_DataAprovadoInicio">Aprovado Inicio</label>
									<input id="busca_DataAprovadoInicio" name="busca_DataAprovadoInicio"  class="input-small" type="text" />
								</div>
								<div class="span4">
									<label for="busca_DataAprovadoFim">Aprovado Fim</label>
									<input id="busca_DataAprovadoFim" name="busca_DataAprovadoFim" class="input-small" type="text"  />
								</div>
								<div class="span4">
									<label for="busca_TipoAprovado">Tipo Aprovado</label>
									<select id="busca_TipoAprovado" name="busca_TipoAprovado" class="input-small">
										<option value="Todos">Todos</option>
										<option value="Aprovado">Aprovado</option>
										<option value="Concluído">Concluído</option>
									</select>
								</div>
			
							</div>
							
							<div class="row-fluid">
			
								<div class="span4">								
									<label for="busca_DataConcluidoInicio">Concluído Início</label>
									<input id="busca_DataConcluidoInicio" name="busca_DataConcluidoInicio"  class="input-small" type="text" />
								</div>
								<div class="span4">
									<label for="busca_DataConcluidoFim">Concluído Fim</label>
									<input id="busca_DataConcluidoFim" name="busca_DataConcluidoFim" class="input-small" type="text"  />
								</div>
			
							</div>
							
							<div class="row-fluid">
			
								<div class="span4">								
									
									<label for="busca_TipoPagamento">Tipo Pagamento</label>
									<select id="busca_TipoPagamento" name="busca_TipoPagamento" style="width:100px" >
										<option value="Todos">Todos</option>
										<option value="OP">OP</option>
										<option value="TED">TED</option>
									</select>
									
								</div>
								<div class="span4">
									
									<label for="busca_InformacaoSaque">Info Saque</label>
									<select id="busca_InformacaoSaque" name="busca_InformacaoSaque" style="width:100px" >
										<option value="Todos">Todos</option>
										<option value="Aguardando Saque">Aguardando Saque</option>
										<option value="Saque Efetuado">Saque Efetuado</option>
									</select>
									
								</div>
			
							</div>

					</div>
					
					<div class="span3"  style="display: ${buscaAprovado};">
					
						<div class="row-fluid">
		
							<div class="span5">								
								<label for="busca_DataRecusadoInicio">Recusado Início</label>
								<input id="busca_DataRecusadoInicio" name="busca_DataRecusadoInicio"  class="input-small" type="text" />
							</div>
							<div class="span5">
								<label for="busca_DataRecusadoFim">Recusado Fim</label>
								<input id="busca_DataRecusadoFim" name="busca_DataRecusadoFim" class="input-small" type="text"  />
							</div>
		
						</div>
						
						<div class="row-fluid">
		
							<div class="span5">
								<label for="busca_TipoRecusado">Tipo Recusa</label>								
								<select id="busca_TipoRecusado" name="busca_TipoRecusado" class="input-small" >
									<option value="Todos">Todos</option>
									<option value="false">Dentro da Planilha</option>
									<option value="true">Fora da Planilha</option>
								</select>
							</div>
							<div class="span5">
								<label for="busca_Justificativa">Justificativa</label>
								<select id="busca_Justificativa" name="busca_Justificativa" class="input-small">
									<option value="Todos">Todos</option>
									<option value="Cliente com pendência de documentos">Cliente com pendência de documentos</option>
									<option value="Cliente desistiu">Cliente desistiu</option>
									<option value="Cliente fechou por outro banco">Cliente fechou por outro banco</option>
									<option value="Cliente inadimplente">Cliente inadimplente</option>
									<option value="Cliente não assinou">Cliente não assinou</option>
									<option value="Cliente não retirou o boleto">Cliente não retirou o boleto</option>
									<option value="Cliente refinanciou">Cliente refinanciou</option>
									<option value="Dívida maior que o previsto">Dívida maior que o previsto</option>
									<option value="Erro de análise">Erro de análise</option>
									<option value="Erro de preenchimento">Erro de preenchimento</option>
									<option value="Família não deixou">Família não deixou</option>
									<option value="Filho não deixou">Filho não deixou</option>
									<option value="Junção de Parcelas">Junção de Parcelas</option>
									<option value="Operador trocou de equipe">Operador trocou de equipe</option>
									<option value="Recusado Banco">Recusado Banco</option>
									<option value="Recusado Qualidade">Recusado Qualidade</option>
									<option value="Recusado Pós Venda">Recusado Pós Venda</option>
								</select>
							</div>
						</div>
					</div>
				</div>
			</div>
						

			<!-- FIM -->
			</div>
		</div>
		</div>
		</div>
	
	<div id="loading" style="display:none;color:#1b5790; font-weight:bold;float:left;clear: both;margin-left: 600px;margin-top: 1px;">CARREGANDO...</div>

	<div class="container-fluid">
		<div class="row-fluid" style="margin-top: 1px;">
			<div class="span12" style="margin-top: 1px;">

				<div class="widget-box">
					<div class="widget-title"><span class="icon"><i class="icon-signal"></i></span><h5>Contratos</h5></div>
					<div id="resultado" class="widget-content">
						<c:if test="${not empty contratos}">
							
							<table class="table table-bordered table-striped table-hover" style="width: 500px;float: right;">
								<tr>
									<th >Total Contrato</th>
									<th >Total C. Líquido</th>
									<th >Total Dívida</th>
									<th >Total Líquido</th>
									<th >Total Meta</th>
								</tr>
								<tr>
									<td>R$ <fmt:formatNumber type="NUMBER" value="${totalValorContratos}" minFractionDigits="2" /></td>
									<td>R$ <fmt:formatNumber type="NUMBER" value="${totalContratoLiquido}" minFractionDigits="2" /></td>
									<td>R$ <fmt:formatNumber type="NUMBER" value="${totalValorDivida}" minFractionDigits="2" /></td>
									<td>R$ <fmt:formatNumber type="NUMBER" value="${totalValorLiquido}" minFractionDigits="2" /></td>
									<td>R$ <fmt:formatNumber type="NUMBER" value="${totalValorMeta}" minFractionDigits="2" /></td>
								</tr>
							</table>
							
							
							<table id="mytable" class="table table-bordered table-striped table-hover data-table">
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
												<fmt:formatDate value="${contrato.formulario.created.time}" pattern="dd/MM" />
											</td>
											<td >
												${contrato.usuario.supervisorUsuario.apelido }
											</td>
											<td >
												${contrato.usuario.apelido }
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
