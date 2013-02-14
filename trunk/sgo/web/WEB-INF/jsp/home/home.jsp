<%@ include file="/header.jspf" %>

<script type="text/javascript">

	$(document).ready(function() {

		$("#busca_Produto").dropdownchecklist({ firstItemChecksAll: true, emptyText: "Selecione..",width: 110,explicitClose:'...fechar'});
		$("#busca_Banco").dropdownchecklist({ firstItemChecksAll: true, emptyText: "Selecione..",width: 110,explicitClose:'...fechar'});
		$("#busca_BancoComprado").dropdownchecklist({ firstItemChecksAll: true, emptyText: "Selecione..",width: 110,maxDropHeight: 150, explicitClose:'...fechar'});
		$("#busca_Status").dropdownchecklist({ firstItemChecksAll: true, emptyText: "Selecione..",width: 110,maxDropHeight: 150, explicitClose:'...fechar'});

		$("#busca_Data").mask("99/99/9999");
		$("#busca_DataFim").mask("99/99/9999");
		$("#busca_DataAprovadoInicio").mask("99/99/9999");
		$("#busca_DataAprovadoFim").mask("99/99/9999");
		$("#busca_DataConcluidoInicio").mask("99/99/9999");
		$("#busca_DataConcluidoFim").mask("99/99/9999");
		$("#busca_DataRecusadoInicio").mask("99/99/9999");
		$("#busca_DataRecusadoFim").mask("99/99/9999");
		$("#busca_DataQuitacaoInicio").mask("99/99/9999");
		$("#busca_DataQuitacaoFim").mask("99/99/9999");
		$("#busca_DataAgendadoInicio").mask("99/99/9999");
		$("#busca_DataAgendadoFim").mask("99/99/9999");
	
		//BOLETOS e AVERBACAO
		$("#busca_DataPrevisaoInicio").mask("99/99/9999");
		$("#busca_DataPrevisaoFim").mask("99/99/9999");
		$("#busca_DataProximaAtuacaoInicio").mask("99/99/9999");
		$("#busca_DataProximaAtuacaoFim").mask("99/99/9999");
	
		$("#busca_DataChegadaInicio").mask("99/99/9999");
		$("#busca_DataChegadaFim").mask("99/99/9999");
		$("#busca_DataVencimentoInicio").mask("99/99/9999");
		$("#busca_DataVencimentoFim").mask("99/99/9999");
	
		$(verificaTipoStatus);
		$.historyInit(pageload);

		$("#busca_Supervisor").change(function() {   
	
			var supervisor = $("#busca_Supervisor").val();
	
			$("#busca_Consultor").load('<c:url value="/controle/consultores" />',   
				{'supervisor': supervisor});
	
			$('#busca_Consultor').attr("disabled", false); 
	
		});
	
		$("#busca_Status").change(function() {   
			verificaTipoStatus();
		});
	
		$("#busca_Consultor").change(function() {   
			var hash = this.value;
			 $.historyLoad(hash); 
		});
		
		$('#busca_Data').focus( function() {
			$(this).calendario({
				target:'#busca_Data',
				top:0,
				left:100
			});
	
		});
	
		$('#busca_DataFim').focus( function() {
			$(this).calendario({
				target:'#busca_DataFim',
				top:0,
				left:100
			});
		});
	
		$('#busca_DataAprovadoInicio').focus( function() {
	
			$(this).calendario({
				target:'#busca_DataAprovadoInicio',
				top:0,
				left:100
			});
	
		});
	
		$('#busca_DataAprovadoFim').focus( function() {
			$(this).calendario({
				target:'#busca_DataAprovadoFim',
				top:0,
				left:100
			});
	
		});
		
		$('#busca_DataConcluidoInicio').focus( function() {
			
			$(this).calendario({
				target:'#busca_DataConcluidoInicio',
				top:0,
				left:100
			});
	
		});
	
		$('#busca_DataConcluidoFim').focus( function() {
			$(this).calendario({
				target:'#busca_DataConcluidoFim',
				top:0,
				left:100
			});
	
		});
	
		$('#busca_DataRecusadoInicio').focus( function() {
			$(this).calendario({
				target:'#busca_DataRecusadoInicio',
				top:0,
				left:100
			});
	
		});
	
		$('#busca_DataRecusadoFim').focus( function() {
			$(this).calendario({
				target:'#busca_DataRecusadoFim',
				top:0,
				left:100
			});
	
		});
		
		$('#busca_DataQuitacaoInicio').focus( function() {
			$(this).calendario({
				target:'#busca_DataQuitacaoInicio',
				top:0,
				left:100
			});
	
		});
	
		$('#busca_DataQuitacaoFim').focus( function() {
			$(this).calendario({
				target:'#busca_DataQuitacaoFim',
				top:0,
				left:100
			});
	
		});
		
		$('#busca_DataAssinaturaInicio').focus( function() {
			$(this).calendario({
				target:'#busca_DataAssinaturaInicio',
				top:0,
				left:100
			});
	
		});
	
		$('#busca_DataAssinaturaFim').focus( function() {
			$(this).calendario({
				target:'#busca_DataAssinaturaFim',
				top:0,
				left:100
			});
	
		});
		
		$('#busca_DataPrevisaoInicio').focus( function() {
			$(this).calendario({
				target:'#busca_DataPrevisaoInicio',
				top:0,
				left:100
			});
	
		});
		
		$('#busca_DataPrevisaoFim').focus( function() {
			$(this).calendario({
				target:'#busca_DataPrevisaoFim',
				top:0,
				left:100
			});
	
		});
		
		$('#busca_DataChegadaInicio').focus( function() {
			$(this).calendario({
				target:'#busca_DataChegadaInicio',
				top:0,
				left:100
			});
	
		});
		
		$('#busca_DataChegadaFim').focus( function() {
			$(this).calendario({
				target:'#busca_DataChegadaFim',
				top:0,
				left:100
			});
	
		});
		
		
		$('#busca_DataVencimentoInicio').focus( function() {
			$(this).calendario({
				target:'#busca_DataVencimentoInicio',
				top:0,
				left:100
			});
	
		});
		
		$('#busca_DataVencimentoFim').focus( function() {
			$(this).calendario({
				target:'#busca_DataVencimentoFim',
				top:0,
				left:100
			});
	
		});
		
		$('#busca_DataAgendadoInicio').focus( function() {
			$(this).calendario({
				target:'#busca_DataAgendadoInicio',
				top:0,
				left:100
			});
	
		});
	
		$('#busca_DataAgendadoFim').focus( function() {
			$(this).calendario({
				target:'#busca_DataAgendadoFim',
				top:0,
				left:100
			});
	
		});
		
		$('#busca_DataProximaAtuacaoInicio').focus( function() {
			$(this).calendario({
				target:'#busca_DataProximaAtuacaoInicio',
				top:0,
				left:100
			});
	
		});
		
		$('#busca_DataProximaAtuacaoFim').focus( function() {
			$(this).calendario({
				target:'#busca_DataProximaAtuacaoFim',
				top:0,
				left:100
			});
	
		});
	
		$('#loading').ajaxStart(function() {
			 $(this).show();
			 $('#resultado').hide();
			 }).ajaxStop(function() {
			 $(this).hide();
			 $('#resultado').fadeIn('slow');
		});
	
	});
	
	function buscaPropostaContrato(){
		
		var propostaBanco = $("#busca_PropostaBanco").val();
		var contratoBanco = $("#busca_ContratoBanco").val();
	
		if(propostaBanco == '' && contratoBanco == ''){
			alert('Pesquise por um dos campos acima.');
			$("#busca_PropostaBanco").focus();
		} else {
			$("#resultado").load('<c:url value="/controle/propostaContrato" />',{'propostaBanco': propostaBanco,'contratoBanco': contratoBanco });	
		}
	
	}

	function verificaTipoStatus() {
	
		var statusPendente = -1;
		var statusDatasBoleto = -1;
	
		if($("#busca_Status").val() != null){

			var statusPendente = $("#busca_Status").val().toString().indexOf("Pendente");

			var arrayStatus = $("#busca_Status").val();

			var statusDatasBoleto = jQuery.inArray("Aguardando Apoio comercial", arrayStatus) >= 0 
					|| jQuery.inArray("Aguardando Quitação", arrayStatus) >= 0 
					|| jQuery.inArray("Aguardando Boleto", arrayStatus) >= 0
					|| jQuery.inArray("Contrato Fora Planilha", arrayStatus) >= 0
					|| jQuery.inArray("Aguardando Qualidade", arrayStatus) >= 0
					|| jQuery.inArray("Aguardando Pós Venda", arrayStatus) >= 0
					|| jQuery.inArray("Aguardando Remarcação", arrayStatus) >= 0
					|| jQuery.inArray("Em Análise", arrayStatus) >= 0
					|| jQuery.inArray("Em Assinatura", arrayStatus) >= 0
					|| jQuery.inArray("Em Conferência", arrayStatus) >= 0
					|| jQuery.inArray("Pendente Conferência", arrayStatus) >= 0 ? 0 : -1;

		}
	
		if($("#busca_Status").val() == 'Enviado DataPrev' || $("#busca_Status").val() == 'Quitado'){
			
			$("#busca_DataAssinaturaInicio").val('');
			$("#busca_DataAssinaturaFim").val('');
			$("#busca_DataAgendadoInicio").val('');
			$("#busca_DataAgendadoFim").val('');

			$("#divAverbacao").show();
			$("#divEnviadoDataPrev").show();

			$("#divContratosStatusFinal").hide();
			$("#divAssinatura").hide();
			$("#divPropostaContrato").hide();
			$("#divPendente").hide();
			$("#divBoleto").hide();
			$("#divAgendado").hide();
			
		}
	
		if(statusPendente >= 0){
			
			$("#busca_DataAssinaturaInicio").val('');
			$("#busca_DataAssinaturaFim").val('');
			$("#busca_DataPrevisaoInicio").val('');
			$("#busca_DataPrevisaoFim").val('');
			$("#busca_DataProximaAtuacaoInicio").val('');
			$("#busca_DataProximaAtuacaoFim").val('');
			$("#busca_DataChegadaInicio").val('');
			$("#busca_DataChegadaFim").val('');
			$("#busca_DataVencimentoInicio").val('');
			$("#busca_DataVencimentoFim").val('');
			$("#busca_DataAgendadoInicio").val('');
			$("#busca_DataAgendadoFim").val('');
			
			$("#divContratosStatusFinal").hide();
			$("#divEnviadoDataPrev").hide();
			$("#divAssinatura").hide();
			$("#divPropostaContrato").hide();
			$("#divPendente").show();
			$("#divBoleto").hide();
			$("#divAverbacao").hide();
			$("#divAgendado").hide();
			
		}
	
		if(statusDatasBoleto>= 0){

			$("#busca_DataQuitacaoInicio").val('');
			$("#busca_DataQuitacaoFim").val('');
			$("#busca_DataAgendadoInicio").val('');
			$("#busca_DataAgendadoFim").val('');
			
			$("#divContratosStatusFinal").hide();
			$("#divEnviadoDataPrev").hide();
			$("#divAssinatura").hide();
			$("#divPropostaContrato").hide();
			$("#divPendente").hide();
			$("#divAverbacao").show();
			$("#divBoleto").show();
			$("#divAgendado").hide();
			
		}
		
		if($("#busca_Status").val() == 'Agendado'){

			$("#busca_DataAssinaturaInicio").val('');
			$("#busca_DataAssinaturaFim").val('');
			$("#busca_DataQuitacaoInicio").val('');
			$("#busca_DataQuitacaoFim").val('');
			$("#busca_DataPrevisaoInicio").val('');
			$("#busca_DataPrevisaoFim").val('');
			$("#busca_DataProximaAtuacaoInicio").val('');
			$("#busca_DataProximaAtuacaoFim").val('');
			$("#busca_DataChegadaInicio").val('');
			$("#busca_DataChegadaFim").val('');
			$("#busca_DataVencimentoInicio").val('');
			$("#busca_DataVencimentoFim").val('');

			$("#divContratosStatusFinal").hide();
			$("#divEnviadoDataPrev").hide();
			$("#divAssinatura").hide();
			$("#divPropostaContrato").hide();
			$("#divPendente").hide();
			$("#divBoleto").hide();
			$("#divAverbacao").hide();
			$("#divAgendado").show();
		
		}
	
		if($("#busca_Status").val() == 'Em Assinatura'){

			$("#busca_DataQuitacaoInicio").val('');
			$("#busca_DataQuitacaoFim").val('');
			$("#busca_DataPrevisaoInicio").val('');
			$("#busca_DataPrevisaoFim").val('');
			$("#busca_DataProximaAtuacaoInicio").val('');
			$("#busca_DataProximaAtuacaoFim").val('');
			$("#busca_DataChegadaInicio").val('');
			$("#busca_DataChegadaFim").val('');
			$("#busca_DataVencimentoInicio").val('');
			$("#busca_DataVencimentoFim").val('');
			$("#busca_DataAgendadoInicio").val('');
			$("#busca_DataAgendadoFim").val('');
			
			$("#divContratosStatusFinal").hide();
			$("#divEnviadoDataPrev").hide();
			$("#divAssinatura").show();
			$("#divPropostaContrato").hide();
			$("#divPendente").hide();
			$("#divBoleto").show();
			$("#divAverbacao").show();
			$("#divAgendado").hide();
			
		}
	
		if($("#busca_Status").val() != 'Em Assinatura' && $("#busca_Status").val() != 'Enviado DataPrev' && $("#busca_Status").val() != 'Agendado' 
				&& $("#busca_Status").val() != 'Quitado' && (statusPendente == -1) && (statusDatasBoleto == -1)){

			$("#busca_DataAssinaturaInicio").val('');
			$("#busca_DataAssinaturaFim").val('');
			$("#busca_DataQuitacaoInicio").val('');
			$("#busca_DataQuitacaoFim").val('');
			$("#busca_DataAgendadoInicio").val('');
			$("#busca_DataAgendadoFim").val('');
			$("#busca_DataAssinaturaFim").val('');
			$("#busca_DataPrevisaoInicio").val('');
			$("#busca_DataPrevisaoFim").val('');
			$("#busca_DataProximaAtuacaoInicio").val('');
			$("#busca_DataProximaAtuacaoFim").val('');
			$("#busca_DataChegadaInicio").val('');
			$("#busca_DataChegadaFim").val('');
			$("#busca_DataVencimentoInicio").val('');
			$("#busca_DataVencimentoFim").val('');
			
			$("#divEnviadoDataPrev").hide();
			$("#divContratosStatusFinal").show();
			$("#divAssinatura").hide();
			$("#divPropostaContrato").hide();
			$("#divPendente").hide();
			$("#divBoleto").hide();
			$("#divAverbacao").hide();
			$("#divAgendado").hide();
			
			
		}
		
		if($("#busca_PropostaBanco").val() != '' || $("#busca_ContratoBanco").val() != ''){
			
			$("#busca_DataAssinaturaInicio").val('');
			$("#busca_DataAssinaturaFim").val('');
			$("#busca_DataQuitacaoInicio").val('');
			$("#busca_DataQuitacaoFim").val('');
			$("#busca_DataAgendadoInicio").val('');
			$("#busca_DataAgendadoFim").val('');
			$("#busca_DataAssinaturaFim").val('');
			$("#busca_DataPrevisaoInicio").val('');
			$("#busca_DataPrevisaoFim").val('');
			$("#busca_DataProximaAtuacaoInicio").val('');
			$("#busca_DataProximaAtuacaoFim").val('');
			$("#busca_DataChegadaInicio").val('');
			$("#busca_DataChegadaFim").val('');
			$("#busca_DataVencimentoInicio").val('');
			$("#busca_DataVencimentoFim").val('');
			
			$("#divEnviadoDataPrev").hide();
			$("#divContratosStatusFinal").hide();
			$("#divAssinatura").hide();
			$("#divPropostaContrato").show();
			$("#divPendente").hide();
			$("#divAverbacao").hide();
			$("#divBoleto").hide();
			$("#divAgendado").hide();
			
		}
	}

	function mudaTipoBusca(tipo) {
	
		if(tipo == 'propostaContrato') {
			$("#divContratosStatusFinal").hide();
			$("#divEnviadoDataPrev").hide();
			$("#divAgendado").hide();
			$("#divAssinatura").hide();
			$("#divPropostaContrato").show();
		}
		
		if(tipo == 'aprovadoRecusado') {
			$("#divContratosStatusFinal").show();
			$("#divEnviadoDataPrev").hide();
			$("#divAgendado").hide();
			$("#divAssinatura").hide();
			$("#divPropostaContrato").hide();
		}
	
	}

	function buscaDatasControle(){

		var status = $("#busca_Status").val();
		var cliente = $("#busca_Cliente").val();
		var documento = $("#busca_Documento").val();
		var supervisor = $("#busca_Supervisor").val();
		var consultor = $("#busca_Consultor").val();
		var previsaoInicio = $("#busca_DataPrevisaoInicio").val();
		var previsaoFim = $("#busca_DataPrevisaoFim").val();
		var chegadaInicio = $("#busca_DataChegadaInicio").val();
		var chegadaFim = $("#busca_DataChegadaFim").val();
		var vencimentoInicio = $("#busca_DataVencimentoInicio").val();
		var vencimentoFim = $("#busca_DataVencimentoFim").val();
		var proximaAtuacaoInicio = $("#busca_DataProximaAtuacaoInicio").val();
		var proximaAtuacaoFim = $("#busca_DataProximaAtuacaoFim").val();
		var procedimento = $("#busca_Procedimento").val();
		var bancos = $("#busca_Banco").val();
		var produtos = $("#busca_Produto").val();
		var bancosComprados = $("#busca_BancoComprado").val();
		var empresa = $("#busca_Empresa").val();

		var tipoBusca = '';
		
		if(consultor == undefined)
			consultor = "";
		if(supervisor == undefined)
			supervisor = "";
		if(empresa == undefined)
			empresa = "";

		if(status != null){

			var arrayStatus = $("#busca_Status").val();

			var statusDatasBoleto = jQuery.inArray("Aguardando Apoio comercial", arrayStatus) >= 0 
					|| jQuery.inArray("Aguardando Quitação", arrayStatus) >= 0 
					|| jQuery.inArray("Aguardando Boleto", arrayStatus) >= 0
					|| jQuery.inArray("Contrato Fora Planilha", arrayStatus) >= 0
					|| jQuery.inArray("Aguardando Qualidade", arrayStatus) >= 0
					|| jQuery.inArray("Aguardando Pós Venda", arrayStatus) >= 0
					|| jQuery.inArray("Aguardando Remarcação", arrayStatus) >= 0
					|| jQuery.inArray("Em Análise", arrayStatus) >= 0
					|| jQuery.inArray("Em Assinatura", arrayStatus) >= 0
					|| jQuery.inArray("Em Conferência", arrayStatus) >= 0
					|| jQuery.inArray("Pendente Conferência", arrayStatus) >= 0 ? 0 : -1;

			if(statusDatasBoleto == 0)
				tipoBusca = "boleto";
			else
				tipoBusca = "";

			if(status == "Enviado DataPrev")
				tipoBusca = "averbacao";

		}

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
			$("#resultado").load('<c:url value="/controle/datasControle" />',{'tipoBusca': tipoBusca,'previsaoInicio': previsaoInicio,'previsaoFim': previsaoFim,
									'chegadaInicio': chegadaInicio,'chegadaFim': chegadaFim,'vencimentoInicio': vencimentoInicio,'vencimentoFim': vencimentoFim, 
									'proximaAtuacaoInicio': proximaAtuacaoInicio,'proximaAtuacaoFim': proximaAtuacaoFim,'procedimento': procedimento,'bancos': bancos, 
									'produtos': produtos,'bancosComprados': bancosComprados,'status': status,'consultor': consultor,'cliente': cliente,'documento': documento,'empresa':empresa});	
		}
	
	}
	
	function buscaContratos(hash){

		$(verificaTipoStatus);

		var status = $("#busca_Status").val();
		var tipoRecusado = $("#busca_TipoRecusado").val();
		var justificativa = $("#busca_Justificativa").val();
		var tipoPagamento = $("#busca_TipoPagamento").val();
		var tipoAprovado = $("#busca_TipoAprovado").val();
		var empresa = $("#busca_Empresa").val();
		var informacaoSaque = $("#busca_InformacaoSaque").val();
		var consultor = $("#busca_Consultor").val();
		var supervisor = $("#busca_Supervisor").val();
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
		var dataQuitacaoInicio = $("#busca_DataQuitacaoInicio").val();
		var dataQuitacaoFim = $("#busca_DataQuitacaoFim").val();
		var dataAgendadoInicio = $("#busca_DataAgendadoInicio").val();
		var dataAgendadoFim = $("#busca_DataAgendadoFim").val();
		var dataAssinaturaInicio = $("#busca_DataAssinaturaInicio").val();
		var dataAssinaturaFim = $("#busca_DataAssinaturaFim").val();
		var previsaoInicio = $("#busca_DataPrevisaoInicio").val();
		var previsaoFim = $("#busca_DataPrevisaoFim").val();
		var chegadaInicio = $("#busca_DataChegadaInicio").val();
		var chegadaFim = $("#busca_DataChegadaFim").val();
		var vencimentoInicio = $("#busca_DataVencimentoInicio").val();
		var vencimentoFim = $("#busca_DataVencimentoFim").val();
		var proximaAtuacaoInicio = $("#busca_DataProximaAtuacaoInicio").val();
		var proximaAtuacaoFim = $("#busca_DataProximaAtuacaoFim").val();
		var bancos = $("#busca_Banco").val();
		var produtos = $("#busca_Produto").val();
		var bancosComprados = $("#busca_BancoComprado").val();
		var motivoPendencia = $("#busca_Pendente").val();

		if(supervisor == undefined)
			supervisor = "";
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
		if(dataQuitacaoInicio == undefined || dataQuitacaoInicio == '__/__/____')
			dataQuitacaoInicio = "";
		if(dataQuitacaoFim == undefined || dataQuitacaoFim == '__/__/____')
			dataQuitacaoFim = "";
		if(dataAssinaturaInicio == undefined || dataAssinaturaInicio == '__/__/____')
			dataAssinaturaInicio = "";
		if(dataAssinaturaFim == undefined || dataAssinaturaFim == '__/__/____')
			dataAssinaturaFim = "";
		if(dataAgendadoInicio == undefined || dataAgendadoInicio == '__/__/____')
			dataAgendadoInicio = "";
		if(dataAgendadoFim == undefined || dataAgendadoFim == '__/__/____')
			dataAgendadoFim = "";
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

		if(hash){
			consultor = hash;
		} else {
			consultor = $("#busca_Consultor").val();
		}
		
		if(consultor == undefined)
			consultor = "";

		if(consultor == ""){
			if(supervisor == "Todos"){
				consultor = "";
			} else {
				consultor = supervisor;
			}
		}

		if($("#check_Contratos").is(":checked")){
			
			var buscaOk = false;
			var buscaDatasControleOK = false;

			if( previsaoInicio != "" || previsaoFim != "" || chegadaInicio != "" || chegadaFim != "" || vencimentoInicio != "" || vencimentoFim != "" || proximaAtuacaoInicio != "" || proximaAtuacaoFim != "")
				buscaDatasControleOK = true;

			if( data != "" || dataAprovadoInicio != "" || dataConcluidoInicio != "" || dataRecusadoInicio != "" || dataQuitacaoInicio != "" 
					|| dataAgendadoInicio != "" || dataAssinaturaInicio != "") {
				buscaOk = true;
			}

			if((!buscaDatasControleOK && !buscaOk) && (cliente != "" || documento!= ""))
				buscaOk = true;	

			if(buscaDatasControleOK)
				$(buscaDatasControle);

			if(buscaOk){
				$("#resultado").load('<c:url value="/controle/contratos" />',
				        {'informacaoSaque': informacaoSaque,'tipoAprovado': tipoAprovado,'empresa':empresa,'tipoPagamento': tipoPagamento ,'tipoRecusado': tipoRecusado,'justificativa': justificativa,'status': status, 
						'consultor' : consultor, 'cliente' : cliente , 'documento' : documento, 'data' : data, 'dataFim' : dataFim,
						'dataAprovadoInicio' : dataAprovadoInicio, 'dataAprovadoFim' : dataAprovadoFim, 'dataConcluidoInicio' : dataConcluidoInicio, 'dataConcluidoFim' : dataConcluidoFim, 
						'dataRecusadoInicio' : dataRecusadoInicio, 'dataRecusadoFim' : dataRecusadoFim,'dataQuitacaoInicio' : dataQuitacaoInicio, 'dataQuitacaoFim' : dataQuitacaoFim,
						'dataAgendadoInicio' : dataAgendadoInicio, 'dataAgendadoFim' : dataAgendadoFim, 'dataAssinaturaInicio' : dataAssinaturaInicio, 'dataAssinaturaFim' : dataAssinaturaFim,
						'bancos' : bancos, 'produtos' : produtos, 'bancosComprados' : bancosComprados , 'motivoPendencia' : motivoPendencia});
			}

		} else {

			if(data != "" || dataFim != "") {
				$("#resultado").load('<c:url value="/controle/solicitacoes" />',
				        {'data': data, 'dataFim' : dataFim});	
			}

		}

	}

	function buscaByCalendar(){

		$(buscaDatasControle);
		$(buscaContratos(null));

	}
</script>

<c:if test="${consultorInfo.consultor.tipo == 'S' || consultorInfo.consultor.tipo == 'C'}">
	<script>
		function pageload(hash) {
			if(hash){		
				$(buscaDatasControle);
				$(buscaContratos(hash));
			} else {
				$(buscaDatasControle);
				$(buscaContratos(null));
			}
		}
	</script>
</c:if>

<c:if test="${consultorInfo.consultor.tipo != 'S' && consultorInfo.consultor.tipo != 'C'}">
	<script>
		function pageload(hash) {
			if(hash){
				$("#busca_Consultor").append('<option value="' + hash + '" selected="selected">' + hash + '</option>');
				$('#busca_Consultor').attr("disabled", true); 
				$(buscaDatasControle);
				$(buscaContratos(hash));
			} else {
				$(buscaDatasControle);
				$(buscaContratos(null));
			}
		}
	</script>
</c:if>

<c:if test="${consultorInfo.consultor.tipo != 'A'}">

	<div style="float: left;">
		<table id="buscaProduto" style="width: 170px;height:140px;">
			<tr>
				<th style="text-align: left;">
					Status:
				</th>
				
				<th style="width: 110px;text-align: left;">
					Banco
				</th>
			</tr>
			<tr>	
				<td>
					<select id="busca_Status" name="busca_Status" class="label_txt" MULTIPLE onchange="buscaContratos();"   >
						<option value="">Todos...</option>
						<option value="Contrato Fora Planilha">Contrato Fora Planilha</option>
						<option value="Agendado">Agendado</option>
						<option value="Aguardando Agendamento">Aguardando Agendamento</option>
						<option value="Aguardando Apoio comercial">Aguardando Apoio comercial</option>
						<option value="Aguardando Boleto">Aguardando Boleto</option>
						<option value="Aguardando Digitação">Aguardando Digitação</option>
						<option value="Aguardando Pós Venda">Aguardando Pós Venda</option>
						<option value="Aguardando Qualidade">Aguardando Qualidade</option>
						<option value="Aguardando Quitação">Aguardando Quitação</option>
						<option value="Aguardando Saldo">Aguardando Saldo</option>
						<option value="Aguardando Remarcação">Aguardando Remarcação</option>
						<option value="Digitado">Digitado</option>
						<option value="Em Análise">Em Análise</option>
						<option value="Em Assinatura">Em Assinatura</option>
						<option value="Em Conferência">Em Conferência</option>
						<option value="Enviado DataPrev">Enviado DataPrev</option>
						<option value="Pendente Administrativo">Pendente Administrativo</option>
						<option value="Pendente Agendamento">Pendente Agendamento</option>
						<option value="Pendente Banco">Pendente Banco</option>
						<option value="Pendente Coeficiente">Pendente Coeficiente</option>
						<option value="Pendente Conferência">Pendente Conferência</option>
						<option value="Quitado">Quitado</option>
						<option value="Recalcular">Recalcular</option>
					</select>
				</td>
				<td>
					<select id="busca_Banco" name="busca_Banco"  class="label_txt" MULTIPLE onchange="buscaContratos();">
	            		<option value="">Todos...</option>
	            		<c:forEach var="banco" items="${bancos }">
	            			<option value="${banco.nome }">${banco.nome }</option>
	            		</c:forEach>
	        		</select>
				</td>
			</tr>
			<tr>
				<th style="width: 60px;text-align: left;">
					Empresa
				</th>
				<th style="text-align: left;">
					Produto:
				</th>
			</tr>
			<tr>
				<td>
					<select id="busca_Empresa" name="busca_Empresa" class="label_txt" onchange="buscaContratos();" style="width:100px" >
						<option value="Todos">Todos</option>
						<option value="ATGGOLD">ATGGOLD</option>
						<option value="GOCX">GOCX</option>
						<option value="GRGOLD">GRGOLD</option>
						<option value="OUROCRED">OUROCRED</option>
					</select>
				</td>
				
				<td>
					<select id="busca_Produto" name="busca_Produto"  class="label_txt" MULTIPLE onchange="buscaContratos();">
	            		<option value="">Todos...</option>
	            		<c:forEach var="nomeProduto" items="${nomesProdutos }">
	            			<option value="${nomeProduto }">${nomeProduto }</option>
	            		</c:forEach>
	        		</select>
				</td>
			</tr>
			<tr>
				<th style="text-align: left;">
					
				</th>
				<th style="text-align: left;">
					Banco comprado:
				</th>
			</tr>
			<tr>
				<td></td>
				<td>
					<select id="busca_BancoComprado" name="busca_BancoComprado"  class="label_txt" MULTIPLE onchange="buscaContratos();">
						<option value="">Selecione um banco</option>
						<option value="BANCO DO BRASIL">BANCO DO BRASIL</option>
						<option value="BANCRED">BANCRED</option>
						<option value="BANRRISUL">BANRRISUL</option>
						<option value="BBM">BBM</option>
						<option value="BCV">BCV</option>
						<option value="BGN">BGN</option>
						<option value="BMC">BMC</option>
						<option value="BMG">BMG</option>
						<option value="BONSUCESSO">BONSUCESSO</option>
						<option value="BRADESCO">BRADESCO</option>
						<option value="BV">BV</option>
						<option value="BVA">BVA</option>
						<option value="C.E.F.">C.E.F.</option>
						<option value="CACIQUE">CACIQUE</option>
						<option value="CIFRA">CIFRA</option>
						<option value="CITIBANK">CITIBANK</option>
						<option value="CREFISA">CREFISA</option>
						<option value="CRUZEIRO DO SUL">CRUZEIRO DO SUL</option>
						<option value="DAYCOVAL">DAYCOVAL</option>
						<option value="FIBRA">FIBRA</option>
						<option value="FICSA">FICSA</option>
						<option value="HSBC">HSBC</option>
						<option value="IBI">IBI</option>
						<option value="INDUSTRIAL">INDUSTRIAL</option>
						<option value="INTERMÉDIUM">INTERMÉDIUM</option>
						<option value="ITAÚ">ITAÚ</option>
						<option value="LECCA">LECCA</option>
						<option value="MAXIMA">MAXIMA</option>
						<option value="MERCANTIL">MERCANTIL</option>
						<option value="ORIGINAL">ORIGINAL</option>
						<option value="PANAMERICANO">PANAMERICANO</option>
						<option value="PARANÁ">PARANÁ</option>
						<option value="PINE">PINE</option>
						<option value="RURAL">RURAL</option>
						<option value="SAFRA">SAFRA</option>
						<option value="SANTANDER">SANTANDER</option>
						<option value="SEMEAR">SEMEAR</option>
						<option value="SCHAHIN">SCHAHIN</option>
						<option value="SOFISA">SOFISA</option>
						<option value="SUL FINANCEIRA">SUL FINANCEIRA</option>
						<option value="UNIBANCO">UNIBANCO</option>
					</select>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="myformBusca" style="float: left">

		<table id="myform" style="width: 370px;height:140px;">
			<tr>
				<th colspan="5" style="text-align: left;">
					Nome Cliente:
				</th>
			</tr>
			<tr>	
				<td colspan="5" valign="top">
					<input id="busca_Cliente" name="busca_Cliente" class="label_txt" type="text" onblur="buscaContratos();"/>
				</td>
			</tr>
			<tr>
				<th scope="col" colspan="5" style="text-align: left;">
					Número Documento (Benefício/Cpf):
				</th>
			</tr>
			<tr>
				<td colspan="5" valign="top">
					<input id="busca_Documento" name="busca_Documento" class="label_txt" type="text" onblur="buscaContratos();"/>
				</td>
			</tr>
			<tr>
				<th colspan="2" style="width: 90px;text-align: left;">
					Data Início:
				</th>
				<th style="width: 70px;text-align: left;">
					Data Fim:
				</th>

				<c:if test="${consultorInfo.consultor.tipo != 'S' && consultorInfo.consultor.tipo != 'C'}">
					<th style="width: 100px;text-align: left;">
						Supervisor:
					</th>
					<th style="width: 110px;text-align: left;">
						Consultor:
					</th>
				</c:if>

				<c:if test="${consultorInfo.consultor.tipo == 'S'}">
					<th style="width: 100px;text-align: left;">
						Consultor:
					</th>
					<th style="width: 110px;text-align: left;">
			
					</th>
				</c:if>
				<c:if test="${consultorInfo.consultor.tipo == 'C'}">
					<th style="width: 100px;text-align: left;">
					
					</th>
					<th style="width: 110px;text-align: left;">
					
					</th>
				</c:if>

			</tr>
			<tr>
				<td valign="top" colspan="2" >
					<input id="busca_Data" name="busca_Data" type="text" maxlength="10" class="label_txt" onchange="buscaContratos();"/>
				</td>
				<td valign="top">
					<input id="busca_DataFim" name="busca_DataFim" type="text" maxlength="10" class="label_txt" onchange="buscaContratos();"/>
				</td>

				<c:if test="${consultorInfo.consultor.tipo != 'S' && consultorInfo.consultor.tipo != 'C'}">
					<td valign="top" >
						<select id="busca_Supervisor" name="busca_Supervisor" class="label_txt" onchange="buscaContratos();">
							<option value="Todos">Todos</option>
							<c:forEach items="${supervisores}" var="supervisor">
								<option value="${supervisor.login}">${supervisor.nome}</option>
							</c:forEach>
						</select>
					</td>
					<td valign="top" >
						<select id="busca_Consultor" name="busca_Consultor" class="label_txt" onchange="buscaContratos();">
							<option value="">Selecione um Supervisor</option>
						</select>
					</td>
				</c:if>

				<c:if test="${consultorInfo.consultor.tipo == 'S'}">
					<td valign="top" >
						<select id="busca_Consultor" name="busca_Consultor" class="label_txt" onchange="buscaContratos();">
							<option value="">Todos</option>
							<c:forEach items="${consultores}" var="consultor">
								<option value="${consultor.login}">${consultor.nome}</option>
							</c:forEach>
						</select>
					</td>
				</c:if>
				<c:if test="${consultorInfo.consultor.tipo == 'C'}">
					<td style="background-color: #99CCFF;width: 80px">
					</td>
				</c:if>

			</tr>
			<tr>
				<td style="text-align: right;">
					<input type="checkbox" id="check_Contratos" name="check_Contratos" 
						<c:if test="${consultorInfo.consultor.tipo != 'S' && consultorInfo.consultor.tipo != 'C'}">checked="checked"</c:if> />
				</td>
				<td colspan="3"> 
					Buscar por Contratos
				</td>
			</tr>
		</table>
	</div>

	<div id="divEnviadoDataPrev" style="display: none;float: left">
		<table id="myform" style="width: 180px;height:140px;">
			<tr>
				<th scope="col" colspan="2" style="text-align: left;">
					Filtrar Data de Quitação
				</th>
			</tr>
			<tr>
				<th scope="col" style="width: 90px;text-align: left;">
					Data Início
				</th>
				<th scope="col" style="width: 90px;text-align: left;">
					Data Fim
				</th>
			</tr>
			<tr>
				<td>
					<input id="busca_DataQuitacaoInicio" name="busca_DataQuitacaoInicio" type="text" maxlength="10" class="label_txt" onchange="buscaContratos();"/>
				</td>
				<td>
					<input id="busca_DataQuitacaoFim" name="busca_DataQuitacaoFim" type="text" maxlength="10" class="label_txt" onchange="buscaContratos();"/>
				</td>
			</tr>
			<tr>
				<td style="height: 75px;"></td>
			</tr>
		</table>
	</div>

	<div id="divAgendado" style="display: none;float: left">
		<table id="myform" style="width: 200px;height:140px;">
			<tr>
				<th scope="col" colspan="2" style="text-align: left;">
					Filtrar Data de Agendamento
				</th>
			</tr>
			<tr>
				<th scope="col" style="width: 100px;text-align: left;">
					Data Início
				</th>
				<th scope="col" style="width: 100px;text-align: left;">
					Data Fim
				</th>
			</tr>
			<tr>
				<td>
					<input id="busca_DataAgendadoInicio" name="busca_DataAgendadoInicio" type="text" maxlength="10" class="label_txt" onchange="buscaContratos();"/>
				</td>
				<td>
					<input id="busca_DataAgendadoFim" name="busca_DataAgendadoFim" type="text" maxlength="10" class="label_txt" onchange="buscaContratos();"/>
				</td>
			</tr>
			<tr>
				<td style="height: 75px;"></td>
			</tr>
		</table>
	</div>
	
	<div id="divPendente" style="display: none;float: left">
		<table id="myform" style="width: 200px;height:140px;">
			<tr>
				<th scope="col" colspan="2" style="text-align: left;">
					Motivo de Pendência
				</th>
			</tr>
			<tr>
				<td>
					<select id="busca_Pendente" name="busca_Pendente" class="label_txt" onchange="buscaContratos();">
						<option value="">Selecione</option>
						<option value="Agencia/Conta Inválida">Agencia/Conta Inválida</option>
						<option value="Cliente Inadimplente">Cliente Inadimplente</option>
						<option value="Cliente Cancelou Agendamento">Cliente Cancelou Agendamento</option>
						<option value="Parcelas Abaixo do Mínimo Exigido">Parcelas Abaixo do Mínimo Exigido</option>
						<option value="Fora da Política de Idade">Fora da Política de Idade</option>
						<option value="Margem Excedida">Margem Excedida</option>
						<option value="Parcelas Abaixo do Mínimo Exigido">Parcelas Abaixo do Mínimo Exigido</option>
						<option value="Operação Abaixo do Mínimo Exigido">Operação Abaixo do Mínimo Exigido</option>
						<option value="Operação Acima do Máximo Exigido">Operação Acima do Máximo Exigido</option>
						<option value="Quantidade de Contratos Excedida">Quantidade de Contratos Excedida</option>
						<option value="Recusado Banco">Recusado Banco</option>
						<option value="Risco de Fraude">Risco de Fraude</option>
						<option value="Solicitado Supervisor">Solicitado Supervisor</option>
					</select>
				</td>
			</tr>
			<tr>
				<td style="height: 90px;"></td>
			</tr>
		</table>
	</div>
	
	<div id="divAverbacao" style="display: none;float: left">
		<table id="myform" style="width: 300px;height:140px;">
			<tr>
				<th scope="col" style="width: 75px;text-align: left;">
					Início Previsão
				</th>
				<th scope="col" style="width: 75px;text-align: left;">
					Fim Previsão
				</th>
				<th scope="col" style="width: 75px;text-align: left;">
					Início Próx. Atuação
				</th>
				<th scope="col" style="width: 75px;text-align: left;">
					Fim Próx. Atuação
				</th>
			</tr>
			<tr>
				<td>
					<input id="busca_DataPrevisaoInicio" name="busca_DataPrevisaoInicio" type="text" maxlength="10" class="label_txt" onchange="buscaDatasControle();"/>
				</td>
				<td>
					<input id="busca_DataPrevisaoFim" name="busca_DataPrevisaoFim" type="text" maxlength="10" class="label_txt" onchange="buscaDatasControle();"/>
				</td>
				<td>
					<input id="busca_DataProximaAtuacaoInicio" name="busca_DataProximaAtuacaoInicio" type="text" maxlength="10" class="label_txt" onchange="buscaDatasControle();"/>
				</td>
				<td>
					<input id="busca_DataProximaAtuacaoFim" name="busca_DataProximaAtuacaoFim" type="text" maxlength="10" class="label_txt" onchange="buscaDatasControle();"/>
				</td>
			</tr>
			<tr>
				<th scope="col" style="width: 100px;text-align: left;" colspan="2">
					Filtro Procedimento:
				</th>
			</tr>
			<tr>
				<td colspan="2">
					<select id="busca_Procedimento" name="busca_Procedimento" class="label_txt" onchange="buscaDatasControle();" >
						<option value="" <c:if test="${boleto.procedimento == ''}">selected</c:if>>Todos</option>
						<option value="ADM/Banco">ADM/Banco</option>
						<option value="Correios">Correios</option>
						<option value="Consulta Reclamação">Consulta Reclamação</option>
						<option value="Criação de e-mail">Criação de e-mail</option>
						<option value="E-mail Banco">E-mail Banco</option>
						<option value="Ligação BACEN">Ligação BACEN</option>
						<option value="Ligação Banco SAC">Ligação Banco SAC</option>
						<option value="Ligação Banco Ouvidoria">Ligação Banco Ouvidoria</option>
						<option value="Pessoalmente Banco">Pessoalmente Banco</option>
						<option value="PROCON">PROCON</option>
						<option value="Site Banco">Site Banco</option>
						<option value="Reclamação BACEN">Reclamação BACEN</option>
						<option value="Reclame Aqui">Reclame Aqui</option>
					</select>
				</td>
			</tr>
			<tr>
				<td style="height: 20px;"></td>
			</tr>
		</table>
	</div>

	<div id="divBoleto" style="display: none;float: left">
		<table id="myform" style="width: 200px;height:140px;">
			<tr>
				<th scope="col" style="width: 100px;text-align: left;">
					Início Chegada
				</th>
				<th scope="col" style="width: 100px;text-align: left;">
					Fim Chegada
				</th>
			</tr>
			<tr>
				<td>
					<input id="busca_DataChegadaInicio" name="busca_DataChegadaInicio" type="text" maxlength="10" class="label_txt" onchange="buscaDatasControle();"/>
				</td>
				<td>
					<input id="busca_DataChegadaFim" name="busca_DataChegadaFim" type="text" maxlength="10" class="label_txt" onchange="buscaDatasControle();"/>
				</td>
			</tr>
			<tr>
				<th scope="col" style="width: 100px;text-align: left;">
					Início Vencimento
				</th>
				<th scope="col" style="width: 100px;text-align: left;">
					Fim Vencimento
				</th>
			</tr>
			<tr>
				<td>
					<input id="busca_DataVencimentoInicio" name="busca_DataVencimentoInicio" type="text" maxlength="10" class="label_txt" onchange="buscaDatasControle();"/>
				</td>
				<td>
					<input id="busca_DataVencimentoFim" name="busca_DataVencimentoFim" type="text" maxlength="10" class="label_txt" onchange="buscaDatasControle();"/>
				</td>
			</tr>
			<tr>
				<td style="height: 20px;"></td>
			</tr>
		</table>
	</div>
	
	<div id="divAssinatura" style="display: none;float: left">
		<table id="myform" style="width: 200px;height:140px;">
			<tr>
				<th scope="col" colspan="2" style="text-align: left;">
					Filtrar Data de Assinatura
				</th>
			</tr>
			<tr>
				<th scope="col" style="width: 100px;text-align: left;">
					Data Início
				</th>
				<th scope="col" style="width: 100px;text-align: left;">
					Data Fim
				</th>
			</tr>
			<tr>
				<td>
					<input id="busca_DataAssinaturaInicio" name="busca_DataAssinaturaInicio" type="text" maxlength="10" class="label_txt" onchange="buscaContratos();"/>
				</td>
				<td>
					<input id="busca_DataAssinaturaFim" name="busca_DataAssinaturaFim" type="text" maxlength="10" class="label_txt" onchange="buscaContratos();"/>
				</td>
			</tr>
			<tr>
				<td style="height: 75px;"></td>
			</tr>
		</table>
	</div>
	
	<div id="divPropostaContrato" style="display: none;float: left">
		<table id="myform" style="width: 220px;height:140px;">
			<tr>
				<th scope="col" colspan="2" style="text-align: left;">
					Filtrar Proposta/Contrato do Banco
				</th>
			</tr>
			<tr>
				<th scope="col" style="width: 110px;text-align: left;">
					Proposta Banco
				</th>
			</tr>
			<tr>
				<td>
					<input id="busca_PropostaBanco" name="busca_PropostaBanco" type="text" maxlength="10" class="label_txt"/>
				</td>
			</tr>
			<tr>
				<th scope="col" style="width: 110px;text-align: left;">
					Contrato Banco
				</th>
			</tr>
			<tr>	
				<td>
					<input id="busca_ContratoBanco" name="busca_ContratoBanco" type="text" maxlength="10" class="label_txt"/>
				</td>
			</tr>
			<tr>
				<td> 
					<a href="#" onClick="mudaTipoBusca('aprovadoRecusado')">Busca Aprovado/Recusado</a>
				</td>
				<td style="height: 35px;" align="right">
					<input id="btt_ContratoBanco" type="button" value="Buscar" class="label_txt" style="width: 50px" onclick="buscaPropostaContrato();"/>
				</td>
			</tr>
		</table>
	</div>

	<div id="divContratosStatusFinal" style="display: block;float: left;" >
		<table id="myform" style="width: 300px;height:140px;">
			<tr>
				<th scope="col" style="width: 100px;text-align: left;">
					Data Início Aprovado
				</th>
				<th scope="col" style="width: 100px;text-align: left;">
					Data Fim Aprovado
				</th>
				<th scope="col" style="width: 100px;text-align: left;">
					Tipo Aprovado
				</th>
			</tr>
			<tr>
				<td>
					<input id="busca_DataAprovadoInicio" name="busca_DataAprovadoInicio" type="text" maxlength="10" class="label_txt" onchange="buscaContratos();"/>
				</td>
				<td>
					<input id="busca_DataAprovadoFim" name="busca_DataAprovadoFim" type="text" maxlength="10" class="label_txt" onchange="buscaContratos();"/>
				</td>
				<td>
					<select id="busca_TipoAprovado" name="busca_TipoAprovado" class="label_txt" onchange="buscaContratos();" style="width:100px" >
						<option value="Todos">Todos</option>
						<option value="Aprovado">Aprovado</option>
						<option value="Concluído">Concluído</option>
					</select>
				</td>
			</tr>
			<tr>
				<th scope="col" style="width: 135px;text-align: left;">
					Data Início Concluído
				</th>
				<th scope="col" style="width: 100px;text-align: left;">
					Data Fim Concluído
				</th>
			</tr>
			<tr>
				<td>
					<input id="busca_DataConcluidoInicio" name="busca_DataConcluidoInicio" type="text" maxlength="10" class="label_txt" onchange="buscaContratos();"/>
				</td>
				<td>
					<input id="busca_DataConcluidoFim" name="busca_DataConcluidoFim" type="text" maxlength="10" class="label_txt" onchange="buscaContratos();"/>
				</td>

			</tr>
			<tr>	
				<th scope="col" style="width: 135px;text-align: left;">
					Tipo Pagamento
				</th>
				<th scope="col" style="width: 100px;text-align: left;">
					Informacao Saque
				</th>
			</tr>
			<tr>	
				<td>
					<select id="busca_TipoPagamento" name="busca_TipoPagamento" class="label_txt" onchange="buscaContratos();" style="width:100px" >
						<option value="Todos">Todos</option>
						<option value="OP">OP</option>
						<option value="TED">TED</option>
					</select>
				</td>
				<td style="width: 100px">
					<select id="busca_InformacaoSaque" name="busca_InformacaoSaque" class="label_txt" onchange="buscaContratos();" style="width:100px" >
						<option value="Todos">Todos</option>
						<option value="Aguardando Saque">Aguardando Saque</option>
						<option value="Saque Efetuado">Saque Efetuado</option>
					</select>
				</td>
			</tr>
			<tr>
				<td> 
					<a href="#" onClick="mudaTipoBusca('propostaContrato')">Proposta/Contrato</a>
				</td>
			</tr>
		</table>

		<table id="myform" style="width: 200px;height:140px;">
			<tr>
				<th style="width: 100px;text-align: left;">
					Início Recusado
				</th>
				<th style="width: 100px;text-align: left;">
					Fim Recusado
				</th>
			</tr>
			<tr>	
				<td>
					<input id="busca_DataRecusadoInicio" name="busca_DataRecusadoInicio" type="text" maxlength="10" class="label_txt" onchange="buscaContratos();"/>
				</td>
				<td>
					<input id="busca_DataRecusadoFim" name="busca_DataRecusadoFim" type="text" maxlength="10" class="label_txt" onchange="buscaContratos();"/>
				</td>
			</tr>
			<tr>
				<th scope="col" style="text-align: left;">
					Tipo Recusado
				</th>
				<th scope="col" style="text-align: left;">
					Filtro Justificativa
				</th>
			</tr>
			<tr>
				<td>
					<select id="busca_TipoRecusado" name="busca_TipoRecusado" class="label_txt" onchange="buscaContratos();" style="width:85px">
						<option value="Todos">Todos</option>
						<option value="false">Dentro da Planilha</option>
						<option value="true">Fora da Planilha</option>
					</select>
				</td>
				<td>
					<select id="busca_Justificativa" name="busca_Justificativa" class="label_txt" onchange="buscaContratos();" style="width:85px">
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
				</td>
			</tr>
			<tr>
				<td style="height: 50px;"></td>
			</tr>
		</table>
	</div>	

	<div id="loading" style="display:none;color:#1b5790; font-weight:bold;float:left;clear: both;margin-left: 600px;">CARREGANDO...</div>

</c:if>

<%@ include file="/footer.jspf" %> 