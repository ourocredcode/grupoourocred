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

		$('.data-table').dataTable( {

			"aLengthMenu": [[10, 25, 50, 100, 200, -1], [10, 25, 50, 100, 200, "Todos"]],

			"iCookieDuration": 60 * 5,

			"bStateSave": true,
			
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
			
			"aoColumns": [ 
				  			/* 0 - Status */   null,
				  			/* 1 - Data */  null,
				  			/* 2 - Supervisor */ null,
				  			/* 3 - Consultor */ null,
				  			/* 4 - Cliente */    null,
				  			/* 5 - Cpf */    null,
				  			/* 6 - Banco */    null,
				  			/* 7 - Produto */    null,
				  			/* 8 - Banco Comprado */    null,
				  			/* 9 - Parcela */     null,
				  			/* 10 - Coeficiente */     { "bVisible":    false },
				  			/* 11 - Prazo */     { "bVisible":    false },
				  			/* 12 - Vl Contrato */    null,
				  			/* 13 - Vl C Liquido */    null,
				  			/* 14 - Divida */    null,
				  			/* 15 - Liquido */    null,
				  			/* 16 - Meta */    null,
				  			/* 17 - Data Chegada */     { "bVisible":    false },
				  			/* 18 - Data Previsão */     { "bVisible":    false },
				  			/* 19 - Data Vencimento */     { "bVisible":    false },
				  			/* 20 - Data Próx Atuação */     { "bVisible":    false },
				  			/* 21 - Data Últ Atuação */     { "bVisible":    false },
				  			/* 22 - Data Quitação */     { "bVisible":    false },
				  			/* 23 - Data Digitação */     { "bVisible":    false },
				  			/* 24 - Proposta Banco */     { "bVisible":    false },
				  			/* 25 - Contrato Banco */     { "bVisible":    false },
				  			/* 26 - Pós Venda */     { "bVisible":    false },
				  			/* 27 - Motivo Recusa */     { "bVisible":    false },
				  			/* 28 - Data Status Final */     { "bVisible":    false },
				  			/* 29 - Repasse */     { "bVisible":    false },
				  			/* 30 - Tabela */     { "bVisible":    false },
				  			/* 31 - QtdParcelasAberto */     { "bVisible":    false },
				  			/* 32 - Contrato ID */     { "bVisible":    false }
				  		] ,

	  		"sDom": ' T C <"clear">lfrtip',

			"oTableTools": {
				"aButtons": [
					{
						"sExtends": "xls",
						"sButtonText": "Excel"
					}
				]
			},

			"oColVis": {
				"activate": "mouseover",
				"buttonText": "Selecione Colunas",
				"aiExclude": [ 0,1,2,3,4,5,6,7,12,,13,14,15,16 ],
				
			},
			"aoColumnDefs": [{
	            "aTargets": [ 9,12,13,14,15,16 ],
	            "bUseRendered": false,
	            "fnRender": function ( o ) {
	            	return o.oSettings.fnFormatNumber( parseFloat( o.aData[ o.iDataColumn ] ).toFixed(2) ).replace(',.','*').replace('.','!').replace(',','?').replace('?','.').replace('!',',').replace('*',',');
	            }
	        }
	     ]
		} );

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

		var convenios = $("#busca_Convenio").val();
		var status = $("#busca_Status").val();
		var tipoRecusado = $("#busca_TipoRecusado").val();
		var justificativa = $("#busca_Justificativa").val();
		var tipoPagamento = $("#busca_TipoPagamento").val();
		var tipoAprovado = $("#busca_TipoAprovado").val();
		var empresas = $("#busca_Empresa").val();
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

		if(motivoPendencia == undefined)
			motivoPendencia = "";
		if(supervisor == undefined)
			supervisor = "";
		if(consultor == undefined)
			consultor = "";

		var isSupervisorApoio = consultor == supervisor ? true : false;

		if(consultor == ""){
			if(supervisor == ""){
				consultor = "";
			} else {
				consultor = supervisor;
			}
		}		
		
		if(convenios == null){
			convenios = new Array();
			convenios[0] = "";
		}
		
		if(empresas == null){
			empresas = new Array();
			empresas[0] = "";
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

			 $("#resultado").load('<c:url value="/menu/busca" />',{'informacaoSaque': informacaoSaque,'tipoAprovado': tipoAprovado,'empresas':empresas,
				 	'tipoPagamento': tipoPagamento ,'tipoRecusado': tipoRecusado,'justificativa': justificativa,'status': status, 'convenios': convenios,
					'cliente' : cliente , 'documento' : documento, 'data' : data, 'dataFim' : dataFim,
					'dataAprovadoInicio' : dataAprovadoInicio, 'dataAprovadoFim' : dataAprovadoFim, 'dataConcluidoInicio' : dataConcluidoInicio, 'dataConcluidoFim' : dataConcluidoFim, 
					'dataRecusadoInicio' : dataRecusadoInicio, 'dataRecusadoFim' : dataRecusadoFim, 'bancos' : bancos, 'produtos' : produtos, 'bancosComprados' : bancosComprados , 
					'motivoPendencia' : motivoPendencia,'isSupervisorApoio' : isSupervisorApoio ,'consultor' : consultor });
		 
		} else {

			alert("Escolha uma data ou dados do cliente.");

		}

	 }
	 
	 function buscaDatasControle(){

			var convenios = $("#busca_Convenio").val();
			var status = $("#busca_Status").val();
			var cliente = $("#busca_Cliente").val();
			var documento = $("#busca_Documento").val();
			var supervisor = $("#busca_Supervisor").val();
			var consultor = $("#busca_Consultor").val();
			var bancos = $("#busca_Banco").val();
			var produtos = $("#busca_Produto").val();
			var bancosComprados = $("#busca_BancoComprado").val();
			var empresas = $("#busca_Empresa").val();
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
			var quitacaoInicio = $("#busca_DataQuitacaoInicio").val();
			var quitacaoFim = $("#busca_DataQuitacaoFim").val();
			var assinaturaInicio = $("#busca_DataAssinaturaInicio").val();
			var assinaturaFim = $("#busca_DataAssinaturaFim").val();
			var procedimento = $("#busca_Procedimento").val();
			var proximoProcedimento = $("#busca_ProximoProcedimento").val();
			var atuante = $("#busca_Atuante").val();
			var tipoLogistica = $("#busca_TipoLogistica").val();
			var periodo = $("#busca_Periodo").val();

			var tipoControle = $("#busca_TipoControle").val();
			
			if(consultor == undefined)
				consultor = "";
			if(supervisor == undefined)
				supervisor = "";
			
			var isSupervisorApoio = consultor == supervisor ? true : false;

			if(consultor == ""){
				if(supervisor == "Todos"){
					consultor = "";
				} else {
					consultor = supervisor;
				}
			}
			
			if(convenios == null){
				convenios = new Array();
				convenios[0] = "";
			}
			
			if(empresas == null){
				empresas = new Array();
				empresas[0] = "";
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
					|| proximaAtuacaoInicio != '' || proximaAtuacaoFim != '' || quitacaoInicio != '' || quitacaoFim != '' || assinaturaInicio != '' || assinaturaFim != '' || procedimento != ''){
				
				if( (previsaoInicio != '' || previsaoFim != '' || chegadaInicio != '' || chegadaFim != '' || vencimentoInicio != '' || vencimentoFim != '' 
					|| proximaAtuacaoInicio != '' || proximaAtuacaoFim != '' ) && tipoControle == ''){

					alert("Escolha um tipo de controle.");
					tipoControle.focus();

				} else {
					
					$("#resultado").load('<c:url value="/menu/busca/controle" />',{'tipoControle': tipoControle,'data' : data, 'dataFim' : dataFim,
						'previsaoInicio': previsaoInicio,'previsaoFim': previsaoFim,
						'chegadaInicio': chegadaInicio,'chegadaFim': chegadaFim,'vencimentoInicio': vencimentoInicio,'vencimentoFim': vencimentoFim, 
						'proximaAtuacaoInicio': proximaAtuacaoInicio,'proximaAtuacaoFim': proximaAtuacaoFim,'quitacaoInicio': quitacaoInicio,'quitacaoFim': quitacaoFim,
						'assinaturaInicio': assinaturaInicio,'assinaturaFim': assinaturaFim,'bancos': bancos, 
						'produtos': produtos,'bancosComprados': bancosComprados,'status': status,'convenios': convenios,'isSupervisorApoio': isSupervisorApoio, 
						'consultor': consultor,'cliente': cliente,'documento': documento,'empresas':empresas,
						'procedimento': procedimento,'proximoProcedimento': proximoProcedimento,
						'atuante':atuante,'tipoLogistica':tipoLogistica,'periodo':periodo});
					
				}

			} else {

				alert("Escolha uma data para a busca.");

			}

	 }
	 
	 
	 function limpa(){

		 var tipoControle = $("#busca_TipoControle").val();

		 if(tipoControle == '')
		 	window.location.href = '<c:url value="/menu/contratos/${usuarioInfo.perfil.chave}" />';
		 
		 if(tipoControle != '')
			 window.location.href = '<c:url value="/menu/contratos/datascontrole" />';
	
	 }

	 </script>

	<div id="content-header">
		<h1>Busca Contratos</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Arquivos"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Usuários" href="<c:url value="/funcionario/equipe/${usuarioInfo.usuario.usuario_id }"/>"><i class="icon-user"></i></a>
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
			
			
			<div class="widget-content" style="padding: 6px;">
				<div class="row-fluid">

					<div class="span2">

						<label for="busca_Convenio">Convenio</label>
	  					<select id="busca_Convenio" name="busca_Convenio" class="input-medium" MULTIPLE >
							<option value="">Todos...</option>
							<c:forEach items="${convenios }" var="convenio">
								<option value="${convenio.convenio_id }">${convenio.nome }</option>
							</c:forEach>
						</select>

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
							<option value="">Todos</option>
							<option value="5">ATGGOLD</option>
							<option value="3">GOCX</option>
							<option value="4">GRGOLD</option>
							<option value="2">OUROCRED</option>
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
										<select id="busca_Supervisor" name="busca_Supervisor" class="input-medium">
											<option value="">Todos</option>
											<c:forEach items="${supervisores}" var="supervisor">
												<option value="${supervisor.usuario_id}">${supervisor.apelido}</option>
											</c:forEach>
										</select>
									</div>
								</c:if>
								
								<c:if test="${usuarioInfo.perfil.chave == 'Supervisor'}">
									<input id="busca_Supervisor" name="busca_Supervisor" type="hidden" value="${usuarioInfo.usuario.usuario_id }"/>
								</c:if>

							</div>
							
							<div class="row-fluid">
							
								<c:if test="${usuarioInfo.perfil.chave == 'Administrativo' || usuarioInfo.perfil.chave == 'Gestor' || usuarioInfo.perfil.chave == 'Supervisor'}">
									<div class="span6">
										<label for="busca_Consultor">Consultor</label>
										<select id="busca_Consultor" name="busca_Consultor" class="input-medium">

											<option value="">Selecione um Supervisor</option>
											<c:if test="${usuarioInfo.perfil.chave == 'Supervisor'}">
												<c:forEach items="${consultores }" var="consultor">
													<option value="${consultor.usuario_id}">${consultor.apelido}</option>
												</c:forEach>
											</c:if>
										</select>
									</div>
								</c:if>

							</div>
						
					</div>

	
					<div id="consultaBoleto" class="span7" style="display: ${buscaDatasControle};">

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
								<label class="control-label">Tipo Controle</label>
								<select id="busca_TipoControle" name="busca_Tipo" class="input-small" >
									<option value="">Tipo</option>
									<option value="1">Averbação</option>
									<option value="2">Boleto</option>
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
									<input id="busca_DataChegadaFim" name="busca_DataChegadaFim" type="text" class="input-small"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Procedimento</label>
								<select  id="busca_Procedimento" name="busca_Procedimento" class="input-medium">
									<option value=""></option>
									<c:forEach items="${procedimentos }" var="procedimento">
										<option value="${procedimento.etapa_id }">${procedimento.nome }</option>
									</c:forEach>
								</select>
							</div>
							<div class="control-group">
								<label class="control-label">Próx.Procedimento</label>
								<select  id="busca_ProximoProcedimento" name="busca_ProximoProcedimento" class="input-medium">
									<option value=""></option>
									<c:forEach items="${procedimentos }" var="procedimento">
										<option value="${procedimento.etapa_id }">${procedimento.nome }</option>
									</c:forEach>
								</select>
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
							<div class="control-group">
								<label class="control-label">Atuante</label>
								<select  id="busca_Atuante" name="busca_Atuante" class="input-medium">
									<option value=""></option>
									<c:forEach items="${atuantes }" var="atuante">
										<option value="${atuante.usuario_id }">${atuante.nome }</option>
									</c:forEach>
								</select>
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
							<div class="control-group">
								<label class="control-label">Tipo Logística</label>
								<select  id="busca_TipoLogistica" name="busca_TipoLogistica" class="input-small">
									<option value=""></option>
									<c:forEach items="${tiposLogistica }" var="tipoLogistica">
										<option value="${tipoLogistica.tipoLogistica_id }">${tipoLogistica.nome }</option>
									</c:forEach>
								</select>
							</div>
							<div class="control-group">
								<label class="control-label">Período</label>
								<select  id="busca_Periodo" name="busca_Periodo" class="input-small">
									<option value=""></option>
									<c:forEach items="${periodos }" var="periodo">
										<option value="${periodo.periodo_id }">${periodo.nome }</option>
									</c:forEach>
								</select>
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
									<select id="busca_TipoPagamento" name="busca_TipoPagamento" class="input-small" >
										<option value="">Todos</option>
										<c:forEach var="meioPagamento" items="${meiosPagamento }">
											<option value="${meioPagamento.meioPagamento_id }">${meioPagamento.nome }</option>
										</c:forEach>
										
									</select>
									
								</div>
								<div class="span4">

									<label for="busca_InformacaoSaque">Info Saque</label>
									<select id="busca_InformacaoSaque" name="busca_InformacaoSaque" class="input-medium" >
										<option value="">Todos</option>
										<c:forEach var="tipoSaque" items="${tiposSaque }">
											<option value="${tipoSaque.tipoSaque_id }">${tipoSaque.nome }</option>
										</c:forEach>
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
							<div class="span6">
								<label for="busca_Justificativa">Justificativa</label>
								<select id="busca_Justificativa" name="busca_Justificativa" class="input-medium">
									<option value="Todos">Todos</option>
									<option value="Agencia/Conta Inválida">Agencia/Conta Inválida</option>
									<option value="Aguardando Documentação">Aguardando Documentação</option>
									<option value="Aguardando Procedimento">Aguardando Procedimento</option>
									<option value="Benefício Bloqueado">Benefício Bloqueado</option>
									<option value="Cliente Cancelou Agendamento">Cliente Cancelou Agendamento</option>
									<option value="Cliente com pendência de documentos">Cliente com pendência de documentos</option>
									<option value="Cliente desistiu">Cliente desistiu</option>
									<option value="Cliente Faleceu">Cliente Faleceu</option>
									<option value="Cliente fechou por outro banco">Cliente fechou por outro banco</option>
									<option value="Cliente Inadimplente">Cliente Inadimplente</option>
									<option value="Cliente não assinou">Cliente não assinou</option>
									<option value="Cliente não retirou o boleto">Cliente não retirou o boleto</option>
									<option value="Cliente refinanciou">Cliente refinanciou</option>
									<option value="Críticas Diversas do Banco">Críticas Diversas do Banco</option>
									<option value="Dados Divergentes">Dados Divergentes</option>
									<option value="Dívida Maior que o Estimado">Dívida Maior que o Estimado</option>
									<option value="Erro de análise">Erro de análise</option>
									<option value="Erro de preenchimento">Erro de preenchimento</option>
									<option value="Família não deixou">Família não deixou</option>
									<option value="Filho não deixou">Filho não deixou</option>
									<option value="Fora da Política de Idade">Fora da Política de Idade</option>
									<option value="Junção de Parcelas">Junção de Parcelas</option>
									<option value="Margem Excedida">Margem Excedida</option>
									<option value="Operação Abaixo do Mínimo Exigido">Operação Abaixo do Mínimo Exigido</option>
									<option value="Orientação ao Cliente">Orientação ao Cliente</option>
									<option value="Parcelas Abaixo do Mínimo Exigido">Parcelas Abaixo do Mínimo Exigido</option>
									<option value="Prioridades">Prioridades</option>
									<option value="Quantidade de Contratos Excedida">Quantidade de Contratos Excedida</option>
									<option value="Recusado Banco">Recusado Banco</option>
									<option value="Recusado Pós Venda">Recusado Pós Venda</option>
									<option value="Recusado Qualidade">Recusado Qualidade</option>
									<option value="Retorno ao Cliente">Retorno ao Cliente</option>
									<option value="Risco de Fraude">Risco de Fraude</option>
									<option value="Sem contato com o cliente">Sem contato com o cliente</option>
									<option value="Sem Retorno do INSS">Sem Retorno do INSS</option>
									<option value="Solicitado Supervisor">Solicitado Supervisor</option>
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

	<div id="resultado">

	<c:if test="${not empty contratos}">
		
		
		<div class="container-fluid">
			<div class="row-fluid" style="margin-top: 1px;">
				<div class="span12" style="margin-top: 1px;">
					<table class="table table-bordered table-striped table-hover" style="width: 500px;float: right;font-size: 11px;">
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
				</div>
			</div>
		</div>	
						
	
		<div class="container-fluid">
			<div class="row-fluid" style="margin-top: 1px;">
				<div class="span12" style="margin-top: 1px;">
	
					<div class="widget-box">
						<div class="widget-title"><span class="icon"><i class="icon-signal"></i></span><h5>Contratos</h5></div>
						<div class="widget-content">
							
		
								<table id="mytable" class="table table-bordered table-hover data-table" style="font-size: 11px;">
									<thead>	
										<tr>
											<th >
												Status
											</th>
											<th >
												Data
											</th>
											<th >
												Supervisor
											</th>
											<th >
												Consultor
											</th>
											<th >
												Cliente
											</th>
											<th >
												Cpf
											</th>
											<th >
												Banco:
											</th>
											<th >
												Produto:
											</th>
											<th >
												Comprado:
											</th>
											<th >
												Parcela
											</th>
											<th >
												Coeficiente
											</th>
											<th >
												Prazo
											</th>
											<th >
												Vl Contrato
											</th>
											<th >
												Vl C Liquido
											</th>
											<th >
												Dívida
											</th>
											<th >
												Liquido
											</th>
											<th >
												Meta
											</th>
											<th >
												Chegada
											</th>
											<th >
												Previsão
											</th>
											<th >
												Vencimento
											</th>
											<th >
												Próxima Atuação
											</th>
											<th >
												Última Atuação
											</th>
											<th >
												Dt Quitação
											</th>
											<th >
												Dt Digitação
											</th>
											<th >
												Proposta Banco
											</th>
											<th >
												Contrato Banco
											</th>
											<th >
												Pós Venda
											</th>
											<th >
												Motivo
											</th>
											<th >
												Dt Status Final
											</th>
											<th >
												Repasse
											</th>
											<th >
												Tabela
											</th>
											<th >
												Parc.Aberto
											</th>
											<th >
												Contrato ID
											</th>
										</tr>
									</thead>
									<tbody>		
										<c:forEach items="${contratos}" var="contrato">
											<tr <c:if test="${contrato.formulario.posvenda.etapa.nome eq 'Pendente' || 
															  contrato.etapa.nome eq 'Pendente Administrativo' ||
															  contrato.etapa.nome eq 'Pendente Agendamento' ||
															  contrato.etapa.nome eq 'Pendente Banco' ||
															  contrato.etapa.nome eq 'Pendente Coeficiente' ||
															  contrato.etapa.nome eq 'Pendente Conferência' ||
															  contrato.etapa.nome eq 'Pendente Apoio Comercial' ||
															  contrato.etapa.nome eq 'Pendente Comercial' ||
															  contrato.etapa.nome eq 'Recalcular' }">class="error"</c:if>>
												<td >
													<a href="<c:url value="/contrato/status/${contrato.contrato_id}"/>">${contrato.etapa.nome }</a>
												</td>
												<td >
													<fmt:formatDate value="${contrato.formulario.created.time}" pattern="dd/MM/yy HH:mm" />
												</td>
												<td >
													${contrato.usuario.supervisorUsuario.apelido }
												</td>
												<td >
													${fn:substring(contrato.usuario.apelido , 0, 18)}
												</td>
												<td >
													${fn:substring(contrato.formulario.parceiroNegocio.nome, 0, 18)}
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
													${contrato.valorContrato }
												</td>
												<td >
													${contrato.valorContratoLiquido }
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
													<fmt:formatDate value="${contrato.controle.dataChegada.time }" pattern="dd/MM" />
												</td>
												<td >
													<fmt:formatDate value="${contrato.controle.dataPrevisao.time }" pattern="dd/MM" />
												</td>
												<td >
													<fmt:formatDate value="${contrato.controle.dataVencimento.time }" pattern="dd/MM" />
												</td>
												<td >
													<fmt:formatDate value="${contrato.controle.dataProximaAtuacao.time }" pattern="dd/MM" />
												</td>
												<td >
													<fmt:formatDate value="${contrato.controle.dataAtuacao.time }" pattern="dd/MM" />
												</td>
												<td >
													<fmt:formatDate value="${contrato.dataQuitacao.time }" pattern="dd/MM/yy" />
												</td>
												<td >
													<fmt:formatDate value="${contrato.dataDigitacao.time }" pattern="dd/MM/yy" />
												</td>
												<td >
													${contrato.propostaBanco }
												</td>
												<td >
													${contrato.contratoBanco }
												</td>
												<td >
													${contrato.formulario.posvenda.etapa.nome }
												</td>
												<td >
													${contrato.etapaPendencia.nome }
												</td>
												<td >
													<fmt:formatDate value="${contrato.dataStatusFinal.time}" pattern="dd/MM/yy" />
												</td>
												<td >
													<c:if test="${contrato.isRepasse }">SIM</c:if>
													<c:if test="${!contrato.isRepasse }">NÃO</c:if>
												</td>
												<td >
													${contrato.coeficiente.tabela.nome }
												</td>
												<td >
													${contrato.qtdParcelasAberto }
												</td>
												<td >
													${contrato.contrato_id }
												</td>
											</tr>
										</c:forEach>
									</tbody>	
								</table>
											
						</div>
					</div>
				</div>
			</div>
		</div>	
	
	</c:if>	
	</div>



<%@ include file="/footer.jspf"%>
