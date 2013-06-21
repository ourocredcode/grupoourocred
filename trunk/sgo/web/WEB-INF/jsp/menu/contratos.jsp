<%@ include file="/header.jspf"%>

	<script type="text/javascript">
 
	 $(document).ready(function() {

		$("#busca_Data").mask("99/99/99");
		$("#busca_DataFim").mask("99/99/99");
		$("#busca_DataAprovadoInicio").mask("99/99/9999");
		$("#busca_DataAprovadoFim").mask("99/99/9999");
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
	
			if(data != '' || dataFim != '' || dataAprovadoInicio != '' || dataAprovadoFim != '' || dataConcluidoInicio != '' || dataConcluidoFim != '' 
						  || dataRecusadoInicio != '' || dataRecusadoFim != ''|| cliente != '' || documento != '' ){
				$(buscaContratos);
			}

		$('#busca_Data').datepicker({
			dateFormat: 'dd/mm/y'
		});
		$('#busca_DataFim').datepicker({
			dateFormat: 'dd/mm/y'
		});
		$('#busca_DataAprovadoInicio').datepicker({
			dateFormat: 'dd/mm/y'
		});
		$('#busca_DataAprovadoFim').datepicker({
			dateFormat: 'dd/mm/y'
		});
		$('#busca_DataPrevisaoInicio').datepicker({
			dateFormat: 'dd/mm/y'
		});
		$('#busca_DataPrevisaoFim').datepicker({
			dateFormat: 'dd/mm/y'
		});
		$('#busca_DataProximaAtuacaoInicio').datepicker({
			dateFormat: 'dd/mm/y'
		});
		$('#busca_DataProximaAtuacaoFim').datepicker({
			dateFormat: 'dd/mm/y'
		});
		$('#busca_DataChegadaInicio').datepicker({
			dateFormat: 'dd/mm/y'
		});
		$('#busca_DataChegadaFim').datepicker({
			dateFormat: 'dd/mm/y'
		});
		$('#busca_DataVencimentoInicio').datepicker({
			dateFormat: 'dd/mm/y'
		});
		$('#busca_DataVencimentoFim').datepicker({
			dateFormat: 'dd/mm/y'
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
			var previsaoInicio = $("#busca_DataPrevisaoInicio").val();
			var previsaoFim = $("#busca_DataPrevisaoFim").val();
			var chegadaInicio = $("#busca_DataChegadaInicio").val();
			var chegadaFim = $("#busca_DataChegadaFim").val();
			var vencimentoInicio = $("#busca_DataVencimentoInicio").val();
			var vencimentoFim = $("#busca_DataVencimentoFim").val();
			var proximaAtuacaoInicio = $("#busca_DataProximaAtuacaoInicio").val();
			var proximaAtuacaoFim = $("#busca_DataProximaAtuacaoFim").val();
			var procedimento = $("#busca_Procedimento").val();

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

			if(previsaoInicio != '' || previsaoFim != '' || chegadaInicio != '' || chegadaFim != '' || vencimentoInicio != '' || vencimentoFim != '' 
					|| proximaAtuacaoInicio != '' || proximaAtuacaoFim != '' || procedimento != ''){
				$("#resultado").load('<c:url value="/menu/busca/controle" />',{'tipoBusca': tipoBusca,'previsaoInicio': previsaoInicio,'previsaoFim': previsaoFim,
										'chegadaInicio': chegadaInicio,'chegadaFim': chegadaFim,'vencimentoInicio': vencimentoInicio,'vencimentoFim': vencimentoFim, 
										'proximaAtuacaoInicio': proximaAtuacaoInicio,'proximaAtuacaoFim': proximaAtuacaoFim,'procedimento': procedimento,'bancos': bancos, 
										'produtos': produtos,'bancosComprados': bancosComprados,'status': status,'consultor': consultor,'cliente': cliente,'documento': documento,'empresa':empresa});

			} else {

				alert("Escolha uma data para a busca.");

			}
		
	 }

	 </script>

	<div id="content-header">
		<h1>Busca Contratos - ${fn:toUpperCase(tipobusca)}  </h1>
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
				<div class="buttons"><a href="javascript:${function }" class="btn btn-mini"><i class="icon-refresh"></i> Busca</a></div>
			</div>
			
			<input type="hidden" id="busca_Tipo" name="busca_Tipo" value="${tipobusca }"/>
			
			
			<div class="widget-content" style="padding: 8px;">
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
		
							<div class="span6">								
								<label for="busca_Supervisor">Supervisor</label>
								<select id="busca_Supervisor" name="busca_Supervisor" class="input-small">
									<option value="">Todos</option>
									<c:forEach items="${supervisores}" var="supervisor">
										<option value="${supervisor.usuario_id}">${supervisor.nome}</option>
									</c:forEach>
								</select>
							</div>
							<div class="span6">
								<label for="busca_Consultor">Consultor</label>
								<select id="busca_Consultor" name="busca_Consultor" class="input-small">
									<option value="">Selecione um Supervisor</option>
								</select>
							</div>

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
	
	

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">

				<div class="widget-box">
					<div class="widget-title"><span class="icon"><i class="icon-signal"></i></span><h5>Contratos</h5></div>
					<div id="resultado" class="widget-content">
						<c:if test="${not empty contratos}">
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
