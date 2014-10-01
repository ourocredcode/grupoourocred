<%@ include file="/header.jspf" %> 

<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">

<script type="text/javascript">

$(document).ready(function() {

	// ARRUMAR LOGISTICA $('select').select2();

	$("#dataStatusFinal").mask("99/99/9999");
	$("#logisticaDataAssinatura").mask("99/99/9999");
	$("#dataConcluido").mask("99/99/9999");
	$("#dataDigitacao").mask("99/99/9999");
	$("#dataQuitacao").mask("99/99/9999");
	$("#dataAgendado").mask("99/99/9999");
	$("#telefoneRes").mask("(99) 9999-9999?9");
	$("#telefoneCel").mask("(99) 9999-9999?9");
	$("#valorQuitacao").maskMoney({symbol:"",decimal:".",thousands:""});
	
	jQuery.extend( jQuery.fn.dataTableExt.oSort, {
	    "date-euro-pre": function ( a ) {
	        if ($.trim(a) != '') {
	            var frDatea = $.trim(a).split(' ');
	            var frTimea = frDatea[1].split(':');
	            var frDatea2 = frDatea[0].split('/');
	            var x = (frDatea2[2] + frDatea2[1] + frDatea2[0] + frTimea[0] + frTimea[1] + frTimea[2]) * 1;
	        } else {
	            var x = 10000000000000; // = l'an 1000 ...
	        }
	         
	        return x;
	    },
	 
	    "date-euro-asc": function ( a, b ) {
	        return a - b;
	    },
	 
	    "date-euro-desc": function ( a, b ) {
	        return b - a;
	    }
	} );
	
	$('.data-table').dataTable({
		"bJQueryUI": true,
		"sPaginationType": "full_numbers",
		"sDom": '<""l>t<"F"fp>',
		"bFilter": false,
		"aaSorting": [],
		"aoColumns": [
            { "sType": "date-euro" },
            null,
            null
        ]
	});
	
	

	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();

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

	var justificativa = document.getElementById("justificativa");
	var dataQuitacao = document.getElementById("dataQuitacao");
	var dataDigitacao = document.getElementById("dataDigitacao");
	var dataAgendado = document.getElementById("dataAgendado");
	var dataStatusFinal = document.getElementById("dataStatusFinal");
	var dataConcluido = document.getElementById("dataConcluido");
	var propostaBanco = document.getElementById("propostaBanco");
	var contratoBanco = document.getElementById("contratoBanco");
	var organizacaoDigitacao = document.getElementById("organizacaoDigitacao");
	var informacaoSaque = document.getElementById("informacaoSaque");
	var meioPagamento = document.getElementById("meioPagamento");
	var valorQuitacao = document.getElementById("valorQuitacao");

	if(dataStatusFinal != undefined) {
		if( dataStatusFinal.value == "")
			desabilita(dataStatusFinal);
	}
	
	if(dataConcluido != undefined) {
		if( dataConcluido.value == "")
			desabilita(dataConcluido);
	}
	
	if(dataQuitacao != undefined) {
		if( dataQuitacao.value == "")
			desabilita(dataQuitacao);
	}
	
	if(dataDigitacao != undefined) {
		if( dataDigitacao.value == "")
			desabilita(dataDigitacao);
	}
	
	if(dataAgendado != undefined) {
		if( dataAgendado.value == "")
			desabilita(dataAgendado);
	}

	if(justificativa != undefined) {
		if(justificativa.value == "")
			desabilita(justificativa);	
	}
	
	if(propostaBanco != undefined) {
		if(propostaBanco.value == "")
			desabilita(propostaBanco);	
	}
	
	if(contratoBanco != undefined) {
		if(contratoBanco.value == "")
			desabilita(contratoBanco);	
	}
	
	if(organizacaoDigitacao != undefined) {
		if(organizacaoDigitacao.value == "")
			desabilita(organizacaoDigitacao);	
	}
	
	if(informacaoSaque != undefined) {
		if(informacaoSaque.value == "")
			desabilita(informacaoSaque);	
	}

	if(meioPagamento != undefined) {
		if(meioPagamento.value == "")
			desabilita(meioPagamento);	
	}

	if(valorQuitacao != undefined) {
		if(valorQuitacao.value == "" || valorQuitacao.value == 0.0){
			valorQuitacao.value = '';
			desabilita(valorQuitacao);
		}
	}

    $("#contratoStatusForm").submit(function() {
    	$("input", this).prop("readonly", true);
		$("input[type='submit'],input[type='button']", this).prop("disabled", true);
        return true;
    });

    $("textarea[maxlength]").keypress(function(event){
		var key = event.which;

		if(key >= 33 || key == 13) {

			var maxLength = $(this).prop("maxlength");
			var length = this.value.length;

			if(length >= maxLength) {
				event.preventDefault();
			};

		};
	});

	$("textarea[maxlength]").keyup(function(event){

		var length = this.value.length;
		var btt = document.getElementById("comunicacaoBtt");

		if(length > 0){
			btt.style.display = "inline";
		};
		if(length == 0){
			btt.style.display = "none";
		};
	});

	$('#dataDigitacao').datepicker({
		dateFormat: 'dd/mm/y'
	});
	$('#dataQuitacao').datepicker({
		dateFormat: 'dd/mm/y'
	});
	$('#dataStatusFinal').datepicker({
		dateFormat: 'dd/mm/y'
	});
	$('#dataConcluido').datepicker({
		dateFormat: 'dd/mm/y'
	});
	$('#dataConcluido').datepicker({
		dateFormat: 'dd/mm/y'
	});
	
	$('#logisticaDataAssinatura').datepicker({
		dateFormat: 'dd/mm/y'
	});	

	$('#horaAssinatura .time').timepicker({
        'showDuration': true,
        'minTime': '9:00am',
        'maxTime': '6:00pm',
        'timeFormat': 'H:i'
    });

	$('#horaAssinatura').datepair();

	
	$("#busca_Supervisor").change(function() {   
		
		var supervisor_id = $("#busca_Supervisor").val();

		if(supervisor_id != '')
			$("#busca_Consultor").load('<c:url value="/contrato/consultores" />', {'supervisor_id': supervisor_id});
		else
			$('#busca_Consultor option').remove();

	});
	
	$('#reload').click(function() {
	    location.reload();
	});
	
});

function verificaStatus() {

	var contratoStatus = document.getElementById("contratoStatus");
	var status = contratoStatus.options[contratoStatus.selectedIndex].text;

	var justificativa = document.getElementById("justificativa");
	var dataAgendado = document.getElementById("dataAgendado");
	var dataStatusFinal = document.getElementById("dataStatusFinal");
	var dataConcluido = document.getElementById("dataConcluido");
	var dataQuitacao = document.getElementById("dataQuitacao");
	var dataDigitacao = document.getElementById("dataDigitacao");
    var meioPagamento = document.getElementById("meioPagamento");
	var propostaBanco = document.getElementById("propostaBanco");
	var contratoBanco = document.getElementById("contratoBanco");
	var organizacaoDigitacao = document.getElementById("organizacaoDigitacao");
	var contratoProduto = $("#contratoProduto").val();
	var valorQuitacao = document.getElementById("valorQuitacao");

	if(status == 'Aprovado') {

		habilita(dataStatusFinal);

		$("#dataStatusFinal").val(getCurrentDate());

		habilita(meioPagamento);

	}

	if(status == 'Concluído') {
		habilita(dataConcluido);
		$("#dataConcluido").val(dataStatusFinal.value);
	}

	if(status == 'Recusado'){
		habilita(dataStatusFinal);
		$("#dataStatusFinal").val(getCurrentDate());
		habilita(justificativa);

	}
	
	if(status == 'Pendente Banco'){
		$("#dataQuitacao").val('');
		desabilita(dataQuitacao);
		habilita(justificativa);
	}
	
	if(status == 'Pendente Administrativo'){
		habilita(justificativa);
	}

	if(status == 'Pendente Agendamento'){
		habilita(justificativa);
	}

	if(status == 'Pendente Coeficiente'){
		habilita(justificativa);
	}
	
	if(status == 'Pendente Apoio Comercial'){
		habilita(justificativa);
	}
	
	if(status == 'Pendente Comercial'){
		habilita(justificativa);
	}
	
	if(status != 'Aprovado' && status != 'Recusado' && status != 'Concluído') {
		desabilita(dataStatusFinal);
		$("#dataStatusFinal").val("");
	}

	if(status != 'Recusado' && status != 'Pendente Banco' && status != 'Pendente Administrativo' && status != 'Pendente Agendamento' && status != 'Pendente Coeficiente' && status != 'Pendente Apoio Comercial' && status != 'Pendente Comercial')
		desabilita(justificativa);

	if(status == 'Enviado DataPrev' || status == 'Quitado' || status == 'Aguardando Integração'){

		if(contratoProduto == 'MARGEM LIMPA' 
				|| contratoProduto == 'MARGEM LIMPA PMSP' 
				|| contratoProduto == 'MARGEM LIMPA PMRP' 
				|| contratoProduto == 'MARGEM LIMPA GOVRJ'
				|| contratoProduto == 'MARGEM LIMPA SIAPE'
				|| contratoProduto == 'REFINANCIAMENTO' 
				|| contratoProduto == 'REFINANCIAMENTO GOVRJ'
				|| contratoProduto == 'REFINANCIAMENTO PMSP' 
				|| contratoProduto == 'REFINANCIAMENTO PMRP' 
				|| contratoProduto == 'REFINANCIAMENTO SIAPE'
				|| contratoProduto == 'RETENÇÃO' || contratoProduto == 'RETENÇÃO PMSP')
			desabilita(dataQuitacao);
		else
			habilita(dataQuitacao);
	}

	if(status == 'Pendente Banco'){
		$("#dataQuitacao").val('');
		desabilita(dataQuitacao);
	}

	if(status == 'Agendado') {
		habilita(dataAgendado);
	} else {
		if(dataAgendado.value == ''){
			desabilita(dataAgendado);
		}
	}
	
	if(status == 'Aguardando Quitação') {
		habilita(valorQuitacao);
	} else {
		if(valorQuitacao.value == ''){
			desabilita(valorQuitacao);
		}
	}
	
	if(status == 'Quitado') {
		habilita(valorQuitacao);
	} else {
		if(valorQuitacao.value == ''){
			desabilita(valorQuitacao);
		}
	}

	if(status == 'Digitado'){

		habilita(contratoBanco);
		habilita(propostaBanco);
		habilita(organizacaoDigitacao);
		habilita(dataDigitacao);
		$("#dataDigitacao").val(getCurrentDate());

	} else {

		if(contratoBanco.value == '')
			desabilita(contratoBanco);
		if(propostaBanco.value == '')
			desabilita(propostaBanco);
		if(organizacaoDigitacao.value == '')
			desabilita(organizacaoDigitacao);
		if(dataDigitacao.value == '')
			desabilita(dataDigitacao);

	}

	if(status == 'Contrato Fora Planilha'){

		if(bairro.value == '')
			desabilita(bairro);

		if(cidade.value == '')
			desabilita(cidade);

		if(endereco.value == '')
			desabilita(endereco);

		if(numero.value == '')
			desabilita(numero);

	}

	if(status == 'Aguardando Qualidade'){
		habilita(bairro);
		habilita(cidade);
		habilita(endereco);
		habilita(numero);
	}

}

function showObs(value){

	alert(value);

	return false;

}

function mostra(id){

	$('#divContrato').load('<c:url value="/contrato/cadastro"/>',{'id' : id});
	
	return false;
}

function repasse(id){
	$('#divRepasse').load('<c:url value="/contrato/repasse"/>',{'id' : id});
}

function conferencia(contrato_id){

	$('#divConferencia').load('<c:url value="/conferencia/cadastro"/>',{'contrato_id' : contrato_id});
	
	return false;
}

function boleto(contrato_id){

	$('#divBoleto').load('<c:url value="/controle/boleto"/>',{'contrato_id': contrato_id});
	
	return false;

}

function averbacao(contrato_id){

	$('#divAverbacao').load('<c:url value="/controle/averbacao"/>',{'contrato_id': contrato_id});

	return false;

}

function desabilita(campo){   

	$("#" + campo.id).select2("disable");
	
	campo.disabled = true;
	campo.required = false;
	campo.className = 'span10';

}

function habilita(campo){

	$("#" + campo.id).select2("enable");
	
	campo.disabled = false;
	campo.required = true;
	campo.className = 'span10';

}

function validaForm(form) {
	
	$.each($(".select2-container"), function (i, n) {
        $(n).next().show().fadeTo(0, 0).height("0px").css("left", "auto"); // make the original select visible for validation engine and hidden for us
        $(n).prepend($(n).next());
        $(n).delay(500).queue(function () {
            $(this).removeClass("validate[required]"); //remove the class name from select2 container(div), so that validation engine dose not validate it
            $(this).dequeue();
        });
    });

	if ($(form).validate().form() === true) {

		var contratoStatus = document.getElementById("contratoStatus");
		var status = contratoStatus.options[contratoStatus.selectedIndex].text;

		var justificativa = document.getElementById("justificativa");
		var dataAgendado = document.getElementById("dataAgendado");
		var dataStatusFinal = document.getElementById("dataStatusFinal");
		var dataConcluido = document.getElementById("dataConcluido");
		var dataQuitacao = document.getElementById("dataQuitacao");
		var dataDigitacao = document.getElementById("dataDigitacao");
	    var meioPagamento = document.getElementById("meioPagamento");
		var propostaBanco = document.getElementById("propostaBanco");
		var contratoBanco = document.getElementById("contratoBanco");
		var organizacaoDigitacao = document.getElementById("organizacaoDigitacao");
		var valorQuitacao = document.getElementById("valorQuitacao");
		var contratoProduto = document.getElementById("contratoProduto").value;

		if(status == 'Aprovado') {

			if( dataStatusFinal.value == '' || meioPagamento.value == '' ) {

				alert(" Data Final / Meio Pagamento obrigatórios. ");
				return false;
				
			}
				
		}

		if(status == 'Concluído') {

			if(dataConcluido.value == ''){
				alert("Data Conclusão obrigatória. ");
				return false;
			}	
			
		}

		if(status == 'Recusado'){
			if( dataStatusFinal.value == '' || justificativa.value == ''){
				alert(" Data Final obrigatória. ");
				return false;
			}
		}
		
		if( status == 'Pendente Banco' && status == 'Pendente Administrativo' && status == 'Pendente Agendamento' && status == 'Pendente Coeficiente' && status == 'Pendente Apoio Comercial' && status == 'Pendente Comercial'){

			if( justificativa.value == ''){
				alert(" Justificativa obrigatória. ");
				return false;
			}

		}

		if(status == 'Enviado DataPrev' || status == 'Quitado'){

			if(contratoProduto != 'MARGEM LIMPA' 
					&& contratoProduto != 'MARGEM LIMPA PMSP'
					&& contratoProduto != 'MARGEM LIMPA PMRP'
					&& contratoProduto != 'MARGEM LIMPA SIAPE'
					&& contratoProduto != 'MARGEM LIMPA GOVRJ'
					&& contratoProduto != 'REFINANCIAMENTO' 
					&& contratoProduto != 'REFINANCIAMENTO PMSP'
					&& contratoProduto != 'REFINANCIAMENTO PMRP'
					&& contratoProduto != 'REFINANCIAMENTO SIAPE'
					&& contratoProduto != 'REFINANCIAMENTO GOVRJ'
					&& contratoProduto != 'RETENÇÃO' 
					&& contratoProduto != 'RETENÇÃO PMSP') {
				
				if(dataQuitacao.value == ''){
					alert("Data Quitação obrigatória");
					return false;
				}
				
			}
				
		}

		if(status == 'Agendado') {
			if(dataAgendado.value == ''){
				alert("Data Agendamento obrigatória");
				return false;
			}
		} 
		
		if(status == 'Quitado') {
			if(valorQuitacao.value == ''){
				alert("Valor Quitação obrigatório");
				return false;
			}
		} 
		

		if(status == 'Digitado'){

			if(contratoBanco.value == '' || propostaBanco.value == '' || organizacaoDigitacao.value == '' || dataDigitacao.value == '') {
				alert("Valores Contrato / Proposta/ Organizacao / Data Digitação obrigatórias. ");
				return false;
			}

		} 

		$(form).submit();

	} else {
		return false;
	}

};


function verificaPeriodo(value) {

	var logisticaPeriodoId = document.getElementById("logisticaPeriodoId");
	var logisticaPeriodo = logisticaPeriodoId.options[logisticaPeriodoId.selectedIndex].text;
	var horaInicio = document.getElementById("logisticaHoraAssinaturaInicio");
	var horaFim = document.getElementById("logisticaHoraAssinaturaFim");
	var btt_salvalogistica = document.getElementById("btt_salvalogistica");

	if(logisticaPeriodo == "Restrito"){

		horaInicio.disabled = false;
		horaInicio.required = true;

		horaFim.disabled = false;
		horaFim.required = true;
		
		btt_salvalogistica.disabled = true;

	} else {

		horaInicio.disabled = true;
		horaInicio.required = false;

		horaFim.disabled = true;
		horaFim.required = false;
		
		btt_salvalogistica.disabled = false;

		horaInicio.value = "";
		horaFim.value = "";
	}

}

function habilitaSalva(value){

	var btt_salvalogistica = document.getElementById("btt_salvalogistica");

	if(value != ""){

		btt_salvalogistica.disabled = false;

	} else {

		btt_salvalogistica.disabled = true;

	}

}

function verificaPagamento() {

	var meioPagamentoId = document.getElementById("meioPagamento");
	var meioPagamento = meioPagamentoId.options[meioPagamentoId.selectedIndex].text;
	var informacaoSaque = document.getElementById("informacaoSaque");

	if(meioPagamento == "OP"){
		habilita(informacaoSaque);
	} else {
		desabilita(informacaoSaque);
		informacaoSaque.value = "";
	}

}



function openPopup(url) {
	 window.open(url, "popup_id", "scrollbars,resizable,width=650,height=750");
 return false;
}

window.onload = function() { 
  var txts = document.getElementsByTagName('TEXTAREA'); 

  for(var i = 0, l = txts.length; i < l; i++) {
    if(/^[0-9]+$/.test(txts[i].getAttribute("maxlength"))) { 
      var func = function() { 
        var len = parseInt(this.getAttribute("maxlength"), 10); 

        if(this.value.length > len) { 
          alert('Tamanho do campo excedido : ' + len); 
          this.value = this.value.substr(0, len); 
          return false; 
        } 
      };

      txts[i].onkeyup = func;
      txts[i].onblur = func;
    } 
  } 
}



</script>

	<div id="content-header">
		<h1>Formulário</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users" href="<c:url value="/funcionario/equipe/${usuarioInfo.usuario.usuario_id }"/>"><i class="icon-user"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="<c:url value="/menu/inicio/${usuarioInfo.perfil.chave}" />" title="Dashboard" class="tip-bottom"><i class="icon-home"></i> Dashboard</a>
		<a href="javascript:history.go(-1)">Contratos</a>
		<a href="#" class="current">Status</a>
		<a href="javascript:window.location='/sgo/formulario/visualiza/${formulario.formulario_id}'" class="current">Formulário</a>
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
						<h5>Dados Consultor</h5>
						<c:if test="${usuarioInfo.perfil.chave == 'Gestor' }">
							<div class="buttons"><a href="#" class="btn btn-mini" onclick="repasse('${contrato.contrato_id}');"><i class="icon-refresh"></i> Altera Consultor</a></div>
						</c:if>
					</div>
					<div class="widget-content padding">
						<div class="row-fluid">
							<div id="divRepasse">
								<div class="span2">
									<label for="contratoUsuarioSupervisorNome">Supervisor</label>
									<input id="contratoUsuarioSupervisorNome"  name="contrato.usuario.supervisorUsuario.nome" value="${contrato.usuario.supervisorUsuario.nome }" type="text" class="input-medium" />	
								</div>
								<div class="span3">
									<label for="contratoUsuarioNome">Consultor</label>
									<input id="contratoUsuarioNome"  name="contrato.usuario.nome" value="${contrato.usuario.nome }" type="text" class="input-large" />
								</div>
								<div class="span2">
									<label for="contratoUsuarioApelido">Codinome</label>
									<input id="contratoUsuarioApelido"  name="contrato.usuario.apelido" value="${contrato.usuario.apelido }" type="text" class="input-medium" />
								</div>
								<div class="span2">
									<label for="contratoUsuarioChave">CPF</label>
									<input id="contratoUsuarioChave"  name="contrato.usuario.chave" value="${contrato.usuario.chave }" type="text" class="input-medium" />
								</div>
								<div class="span2">
									<c:if test="${contrato.isRepasse }">
										 <input type="checkbox" name="contrato.isRepasse" checked="checked" />Repasse
									</c:if>
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
			<div class="span12">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon">
							<i class="icon-align-justify"></i>									
						</span>

						<h5>Dados Cliente</h5>

						<div class="buttons">
							<form id="buscaParceiroForm" action="<c:url value="/parceironegocio/cadastro" />" method="post">
								<input id="doc" name="doc" type="hidden" value="${formulario.parceiroNegocio.cpf }" />
								<button type="submit" class="btn btn-mini" ><i class="icon-user icon-black"></i> Dados Cliente</button>
							</form>
						</div>
						<div class="buttons">
							<a href="<c:url value="/contrato/cliente/detalhamento/${formulario.parceiroBeneficio.numeroBeneficio }"/>" onclick="javascript:return openPopup(this.href);" class="btn btn-mini"><i class="icon-search"></i> Detalhamento </a>
						</div>
						
						<c:if test="${usuarioInfo.perfil.chave == 'Administrativo' || usuarioInfo.perfil.chave == 'Gestor' }">
							<div class="buttons">
								<form id="buscaHistoricoCliente" action="<c:url value="/contrato/cliente/historico" />" method="post">
									<input id="doc" name="doc" type="hidden" value="${formulario.parceiroNegocio.cpf }" />
									<button type="submit" class="btn btn-mini" ><i class="icon-align-justify icon-black"></i> Histórico </button>
								</form>
							</div>
						</c:if>

					</div>
					<div class="widget-content padding">
						<div class="row-fluid">
							<div id="formCliente">
								<div class="span2">
									<label for="formularioParceiroNegocioNome">Nome</label>
									<input type="text" class="input-medium" id="formularioParceiroNegocioNome" name="formulario.parceiroNegocio.nome" value="${formulario.parceiroNegocio.nome }"/>	
								</div>
								<div class="span2">
									<label for="formularioParceiroNegocioCpf">Cpf</label>
									<input type="text" class="input-medium" id="formularioParceiroNegocioCpf" name="formulario.parceiroNegocio.cpf" value="${formulario.parceiroNegocio.cpf }" />
								</div>
								<div class="span2">
									<label for="parceiroBeneficioNumeroBeneficio">Beneficio</label>
									<input type="text" class="input-medium" id="parceiroBeneficioNumeroBeneficio" name="parceiroBeneficio.numeroBeneficio" value="${formulario.parceiroBeneficio.numeroBeneficio }" />
								</div>
								<div class="span2">
									<label for="formularioParceiroNegocioDataNascimento">Dt Nascimento</label>
									<input type="text" class="input-medium" id="formularioParceiroNegocioDataNascimento" name="formulario.parceiroNegocio.dataNascimento"  placeholder="Nasc." 
									value="<fmt:formatDate pattern="dd/MM/yyyy" value="${formulario.parceiroNegocio.dataNascimento.time }" />">
								</div>
								<div class="span2">
									<label for="parceiroLocalidadeLocalidadeCep">CEP</label>
									<input  class="input-medium" id="parceiroLocalidadeLocalidadeCep" name="parceiroLocalidade.localidade.cep" type="text" value="${formulario.parceiroLocalidade.localidade.cep }" />
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div id="formDadosPagamento">
								<div class="span2">
									<label for="Banco">Banco</label>
									<input type="text" class="input-medium" id="Banco" name="Banco" value="${formulario.parceiroInfoBanco.banco.nome }"/>
									<input type="hidden" class="span1" id="BancoId" name="BancoId" value="${formulario.parceiroInfoBanco.banco.banco_id }"/>
								</div>
								<div class="span2">
									<label for="Agencia">Agencia</label>
									<input type="text" class="input-medium" id="Agencia" name="Agencia" value="${formulario.parceiroInfoBanco.agenciaNumero }" />
								</div>
								<div class="span2">
									<label for="Conta">Conta</label>
									<input type="text" class="input-medium" id="Conta" name="Conta" value="${formulario.parceiroInfoBanco.contaCorrente }" />
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
						<c:choose>
							<c:when test="${usuarioInfo.perfil.chave == 'Consultor' && (contrato.etapa.nome == 'Recalcular' || contrato.etapa.nome == 'Contrato Fora Planilha')}">
								<div class="buttons"><a href="#" class="btn btn-mini" onclick="return mostra('${contrato.contrato_id}');"><i class="icon-refresh"></i> Altera Contrato</a></div>
							</c:when>
							<c:when test="${usuarioInfo.perfil.chave == 'Gestor'}">
								<div class="buttons"><a href="#" class="btn btn-mini" onclick="return mostra('${contrato.contrato_id}');"><i class="icon-refresh"></i> Altera Contrato</a></div>
							</c:when>
							<c:when test="${(usuarioInfo.perfil.chave == 'Supervisor' && contrato.etapa.nome != 'Concluído' && contrato.etapa.nome != 'Aprovado' && 
								 contrato.etapa.nome != 'Recusado' && contrato.etapa.nome != 'Aguardando Quitação'  &&  contrato.etapa.nome != 'Enviado DataPrev' &&  contrato.etapa.nome != 'Digitado')}">
								<div class="buttons"><a href="#" class="btn btn-mini" onclick="return mostra('${contrato.contrato_id}');"><i class="icon-refresh"></i> Altera Contrato</a></div>
							</c:when>
							<c:when test="${usuarioInfo.perfil.chave == 'Administrativo' && contrato.etapa.nome != 'Concluído'}">
								<div class="buttons"><a href="#" class="btn btn-mini" onclick="return mostra('${contrato.contrato_id}');"><i class="icon-refresh"></i> Altera Contrato</a></div>
							</c:when>
						</c:choose>
					</div>
					<div class="widget-content padding">

						<div class="row-fluid">
							<div id="divContrato" style="float: none;clear:both;">
	
								<table class="table table-striped table-bordered">
									<c:if test="${not empty contrato}">
									<thead>	
										<tr>
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
										<tr>
											<td>
												${contrato.banco.nome }
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
													<a href="#myModalObservacao" role="button" data-toggle="modal"><i class="icon-search"></i></a>
												</c:if>
												<c:if test="${empty contrato.observacao}">
													<a href="#myModalObservacao" role="button" data-toggle="modal"><i class="icon-pencil"></i></a>
												</c:if>
											</td>
										</tr>
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
	
	<div id="myModalObservacao" class="modal hide fade" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	    <h3 id="myModalLabel">Altera Observação</h3>
	  </div>

	  <form action="<c:url value='/contrato/observacao/salva'/>"  method="post">

	 	 <input id="contratoId" name="contrato.contrato_id" value="${contrato.contrato_id }" type="hidden" />

		  <div class="modal-body">
			<div style="float: left;">
	  			<div class="control-group">
					<label for="observacao">Observacao</label>
					<textarea id="observacao" name="contrato.observacao" rows="4" cols="4" maxlength="255" class="span5"><c:out value="${contrato.observacao}"></c:out></textarea>	
				</div>
			</div>	
		  </div>
		  <div class="modal-footer">
		    <button class="btn" data-dismiss="modal" aria-hidden="true">Fecha</button>
		    <c:if test="${usuarioInfo.perfil.chave == 'Consultor' || usuarioInfo.perfil.chave == 'Supervisor'}">
		    	<button class="btn btn-primary" type="submit" id="btt_salvalogistica">Salva</button>
		    </c:if>
		  </div>
		</form>
	</div>
	
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon">
							<i class="icon-align-justify"></i>									
						</span>
						<h5>Status</h5>
					</div>
					<div class="widget-content padding">

					<form id="contratoStatusForm" name="contratoStatusForm" action="<c:url value="/contrato/altera/status"/>" method="POST">
					<input id="contratoProduto" type="hidden" name="contratoStatus.contrato.produto" value="${contrato.produto.nome }" />
					<input id="contratoId" type="hidden" name="contrato.contrato_id" value="${contrato.contrato_id }" />	

						<div class="row-fluid"> 
	
							<div class="span2">
								<label for="dataAgendado">Data Agendado</label>
								<input id="dataAgendado" type="text" name="contrato.dataAgendado" value="<fmt:formatDate pattern="dd/MM/yyyy"  type="time" value="${contrato.dataAgendado.time}" />" class="input-medium"/>
							</div>
							
							<div class="span2">
								<label for="valorQuitacao">Valor Quitação</label>
								<input id="valorQuitacao" type="text" name="contrato.valorQuitacao" value="${contrato.valorQuitacao}" class="input-medium" />
							</div>
							
							<div class="span2">
								<label for="dataQuitacao">Data Quitação</label>
								<input id="dataQuitacao" type="text" name="contrato.dataQuitacao" value="<fmt:formatDate pattern="dd/MM/yyyy"  type="time" value="${contrato.dataQuitacao.time}" />" class="input-medium"/>
							</div>
						</div>
						
						<div class="row-fluid"> 
							
							<div class="span2">
								<label for="meioPagamento">Meio Pagamento</label>	
								<select id="meioPagamento" name="contrato.meioPagamento.meioPagamento_id" class="input-medium" onchange="verificaPagamento();">
									<option value="">Selecione</option>
									<c:forEach var="meioPagamento" items="${meiosPagamento }">
										<option value="${meioPagamento.meioPagamento_id }" <c:if test="${meioPagamento.meioPagamento_id == contrato.meioPagamento.meioPagamento_id }">selected="selected" </c:if>>${meioPagamento.nome }</option>
									</c:forEach>
								</select>
							</div>

							<div class="span2">
								<label for="informacaoSaque">Informação Saque</label>	
								<select id="informacaoSaque" name="contrato.tipoSaque.tipoSaque_id" class="input-medium" >
									<option value="">Selecione</option>
									<c:forEach var="tipoSaque" items="${tiposSaque }">
										<option value="${tipoSaque.tipoSaque_id }" <c:if test="${tipoSaque.tipoSaque_id == contrato.tipoSaque.tipoSaque_id }">selected="selected" </c:if>>${tipoSaque.nome }</option>
									</c:forEach>
								</select>
							</div>

						</div>
						
						<div class="row-fluid">
	
							<div class="span2">
								<label for="dataDigitacao">Data Digitacao</label>
								<input id="dataDigitacao" type="text" name="contrato.dataDigitacao" value="<fmt:formatDate pattern="dd/MM/yyyy"  type="time" value="${contrato.dataDigitacao.time}" />" class="input-medium" />
							</div>
							<div class="span2">	
								<label for="contratoBanco">Contrato Banco</label>	
								<input id="contratoBanco" type="text" name="contrato.contratoBanco" value="${contrato.contratoBanco}" class="input-medium" />
							</div>
							<div class="span2">	
								<label for="propostaBanco">Proposta Banco</label>	
								<input id="propostaBanco" type="text" name="contrato.propostaBanco" value="${contrato.propostaBanco}" class="input-medium" />
							</div>
							<div class="span2">	
								<label for="organizacaoDigitacao">Organização Digitação</label>	
								<select id="organizacaoDigitacao" name="contrato.organizacaoDigitacao.organizacao_id" class="input-medium" >
									<option value="">Selecione</option>
									<c:forEach var="organizacao" items="${organizacoes }">
										<option value="${organizacao.organizacao_id }" <c:if test="${organizacao.organizacao_id == contrato.organizacaoDigitacao.organizacao_id }">selected="selected" </c:if>>${organizacao.nome }</option>
									</c:forEach>
								</select>
							</div>
						</div>
					
					<div class="row-fluid">

						<div class="span2">
							<label for="contratoStatus">Status Contrato</label>
							<select id="contratoStatus" name="contrato.etapa.etapa_id" class="input-medium" onchange="verificaStatus();">
								<c:forEach var="etapa" items="${etapas }">
									<option value="${etapa.etapa_id}" 
									<c:if test="${etapa.etapa_id == contrato.etapa.etapa_id}">selected</c:if>>${etapa.nome }</option>
								</c:forEach>
							</select>
						</div>
					
						<div class="span2">
							<label for="dataStatusFinal">Data Aprova/Recusa</label>
							<input id="dataStatusFinal" type="text" name="contrato.dataStatusFinal" value="<fmt:formatDate pattern="dd/MM/yyyy"  type="time" value="${contrato.dataStatusFinal.time}" />" class="input-medium"/>
						</div>
					
						<div class="span2">
							<label for="dataConcluido">Data Conclusão</label>
							<input id="dataConcluido" type="text" name="contrato.dataConcluido" value="<fmt:formatDate pattern="dd/MM/yyyy"  type="time" value="${contrato.dataConcluido.time}" />" class="input-medium"/>
						</div>
					
						<div class="span2">
							<label for="justificativa">Justificativa</label>
							<select id="justificativa" name="contrato.etapaPendencia.etapa_id" class="input-medium">

								<option value="" selected="selected">Selecione</option>
								<c:forEach var="justificativa" items="${justificativas }">
									<option value="${justificativa.etapa_id}" 
									<c:if test="${justificativa.etapa_id == contrato.etapaPendencia.etapa_id}">selected</c:if>>${justificativa.nome }</option>
								</c:forEach>

							</select>
						</div>
					</div>

					<div class="form-actions">

							<c:choose>
								<c:when test="${ usuarioInfo.perfil.chave == 'Consultor' && (contrato.etapa.nome == 'Aguardando Status' || contrato.etapa.nome == 'Recalcular' || contrato.etapa.nome == 'Contrato Fora Planilha' || contrato.etapa.nome == 'Pendente Apoio Comercial') }">
									<div class="span1" style="float: left;">
										<input value="Salva" type="button" class="btn btn-primary" onclick="javascript:validaForm('#contratoStatusForm');" >
									</div>	
								</c:when>
								<c:when test="${usuarioInfo.perfil.chave == 'Supervisor' && contrato.etapa.nome != 'Concluído' }">
									<div class="span1" style="float: left;">
										<input value="Salva" type="button" class="btn btn-primary" onclick="javascript:validaForm('#contratoStatusForm');" >
									</div>	
								</c:when>
								<c:when test="${usuarioInfo.perfil.chave == 'Administrativo' && contrato.etapa.nome != 'Concluído'}">
									<div class="span1" style="float: left;">
										<input value="Salva" type="button" class="btn btn-primary" onclick="javascript:validaForm('#contratoStatusForm');" >
									</div>	
								</c:when>
								<c:when test="${usuarioInfo.perfil.chave == 'Gestor'}">
									<div class="span1" style="float: left;">
										<input value="Salva" type="button" class="btn btn-primary" onclick="javascript:validaForm('#contratoStatusForm');" >
									</div>	
								</c:when>
							</c:choose>

						<div class="span1" style="float: left;">
							<input value="Formulário" type="button" class="btn" onclick="javascript:window.location='/sgo/formulario/visualiza/${formulario.formulario_id}'">
						</div>
					</div>	
				  </form>	
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
						<h5>Histórico</h5>
					</div>
					<div class="widget-content">
					
					<form id="contratoHistoricoForm" name="contratoHistoricoForm" action="<c:url value="/contrato/inclui/historico" />" method="post">

						<input type="hidden" id="historicoContrato.contrato.contrato_id" name="historicoContrato.contrato.contrato_id" value="${contrato.contrato_id}" />

						<textarea  style="width: 100%;" id="historicoContrato.observacao" name="historicoContrato.observacao" class="label_txt" rows="5" cols="120" maxlength="255"><c:out value="${historicoContrato.observacao}" /></textarea>

						<button id="comunicacaoBtt" type="submit" class="btn" style="display: none;">Salvar</button>

					</form>
					
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
						<h5>Histórico</h5>
					</div>
					<div class="widget-content">
						<table class="table table-bordered table-striped table-hover data-table" id="histTable">
							<c:if test="${not empty historico}">
							<thead>	
								<tr>
									<th scope="col">
										Data
									</th>
									<th scope="col">
										Usuario
									</th>
									<th scope="col">
										Observacao
									</th>
								</tr>
							</thead>
							<tbody>		
								<c:forEach var="historico" items="${historico }">
									<tr>
										<td>
											<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss"  type="time" value="${historico.created.time }" />
										</td>
										<td>
											${historico.createdBy.nome }
										</td>
										<td>
											${historico.observacao }
										</td>
									</tr>
								</c:forEach>
							</tbody>	
							</c:if>
							<c:if test="${empty historico}">
							<thead>	
								<tr>
									<th scope="col">
										Data
									</th>
									<th scope="col">
										Usuario
									</th>
									<th scope="col">
										Observacao
									</th>
								</tr>
							</thead>
							<tbody>		
								<tr>
									<td>
									</td>
									<td>
									</td>
									<td>
										<i> Nenhuma alteração realizada </i>
									</td>
								</tr>
							</tbody>	
							</c:if>
						</table>
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
						<h5>Logística</h5>
					</div>
					<div class="widget-content padding">
						<div class="row-fluid"> 

							<div id="logisticasDiv">
							
								<c:if test="${not empty formulario.parceiroLocalidade.complemento && not empty formulario.parceiroLocalidade.pontoReferencia }">
								
									<div class="alert alert-success">
									  <button type="button" class="close" data-dismiss="alert">&times;</button>

									  <h6>Endereço cadastrado corretamente!</h6>

									  <b>Endereço de assinatura : </b> <c:out value="${formulario.parceiroLocalidade.localidade.tipoLocalidade.nome }" /> <c:out value="${formulario.parceiroLocalidade.localidade.endereco }" /> - <c:out value="${formulario.parceiroLocalidade.numero }" /> / CEP : - <c:out value="${formulario.parceiroLocalidade.localidade.cep }" /> <br/>
									  <b>Complemento : </b> <c:out value="${formulario.parceiroLocalidade.complemento }" /> <br/>
									  <b>Ponto de Referência : </b>  <c:out value="${formulario.parceiroLocalidade.pontoReferencia }" />

									</div>
								
								</c:if>

								<c:if test="${empty formulario.parceiroLocalidade.complemento || empty formulario.parceiroLocalidade.pontoReferencia }">
								
									<div class="alert alert-error">
									  <button type="button" class="close" data-dismiss="alert">&times;</button>

									  <h6>Atenção! Cadastro de endereço incompleto!</h6>
									  Cliente sem ponto de referência e / ou complemento no seu endereço de assinatura. Clique no botão (Dados do Cliente) abaixo para completar o cadastro.

									  <br/><br/>
									  Após realizar a alteração, clique <a id="reload" href="#">aqui</a> para carregar esta página novamente.

									</div>
								
								</c:if>

								<c:if test="${not empty logisticas }">
									<table class="table table-striped table-bordered" id="lista">
										<thead>
											<tr>
												<th>Data Assinatura</th>
												<th>Tipo Logística</th>
												<th>Período</th>
											</tr>
										</thead>
										<tbody>	
											<c:forEach items="${logisticas}" var="logistica">
												<tr>
													<td><fmt:formatDate pattern="dd/MM/yyyy"  type="time" value="${logistica.dataAssinatura.time }" /></td>
													<td>${logistica.tipoLogistica.nome }</td>
													<td>${logistica.periodo.nome }</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</c:if>
							</div>

							<div class="row-fluid"> 
								
								
								<c:if test="${not empty formulario.parceiroLocalidade.complemento && not empty formulario.parceiroLocalidade.pontoReferencia }">
									<div class="span2">
										<a href="#myModal" role="button" class="btn" data-toggle="modal">Nova Logística</a>
									</div>
								</c:if>
								<c:if test="${empty formulario.parceiroLocalidade.complemento || empty formulario.parceiroLocalidade.pontoReferencia }">
									<div class="span2">
										<div class="buttons">
											<form id="buscaParceiroForm" action="<c:url value="/parceironegocio/cadastro" />" method="post">
												<input id="doc" name="doc" type="hidden" value="${formulario.parceiroNegocio.cpf }" />
												<button type="submit" class="btn" ><i class="icon-user icon-black"></i> Dados do Cliente</button>
											</form>
										</div>
										
									</div>
								</c:if>
								
								<div class="span1">
									<c:if test="${not empty logisticas && not empty formulario.parceiroLocalidade }">

										<form action="<c:url value="/logistica/checklist/${formulario.formulario_id}"/>" target="_blank">
											<input type="submit" value="CheckList" class="btn"/>
										</form>

									</c:if>
								</div>
								
							</div>

							<div id="myModal" class="modal hide fade" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
							  <div class="modal-header">
							    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
							    <h3 id="myModalLabel">Cadastro Logística</h3>
							  </div>
							  <form action="<c:url value='/logistica/salva'/>"  method="post">

							  <input id="logisticaContratoId" name="logistica.contrato.contrato_id" value="${contrato.contrato_id }" type="hidden" />

								  <div class="modal-body">
									<div style="float: left;">
							  			<div class="control-group">
											<label class="control-label">Data Assinatura :</label>
											<div class="controls">
												<input id="logisticaDataAssinatura" name="logistica.dataAssinatura" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${logistica.dataAssinatura.time }" />" class="input-medium" />
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">Tipo Logística :</label>
											<div class="controls">
												<select id="logisticaTipoLogisticaId" name="logistica.tipoLogistica.tipoLogistica_id" class="selectTipoLogistica">
													<c:forEach var="tipoLogistica" items="${tiposLogistica }">
														<c:if test="${tipoLogistica.nome != 'Representante' }">
															<option value="${tipoLogistica.tipoLogistica_id }">${tipoLogistica.nome }</option>
														</c:if>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">Período : </label>
											<div class="controls">
												<select id="logisticaPeriodoId" name="logistica.periodo.periodo_id" class="selectPeriodoLogistica" onchange="verificaPeriodo(this.value);">
													<c:forEach var="periodo" items="${periodos }">
														<option value="${periodo.periodo_id }" <c:if test="${periodo.nome eq 'Comercial' }">selected="selected"	</c:if> >${periodo.nome }</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div id="horaAssinaturaDiv" class="control-group">
											<label class="control-label">Hora Inicio : </label>
											<div class="controls">
												<p id="horaAssinatura">
													<input id="logisticaHoraAssinaturaInicio" name="logistica.horaAssinaturaInicio.time"  value="<fmt:formatDate pattern="HH:mm" value="${ logistica.horaAssinaturaInicio.time }"  />"  disabled="disabled" class="input-small time start" onchange="habilitaSalva(this.value);" /> até
													<input id="logisticaHoraAssinaturaFim" name="logistica.horaAssinaturaFim.time" value="<fmt:formatDate pattern="HH:mm" value="${ logistica.horaAssinaturaInicio.time }"  />"  disabled="disabled" class="input-small time end" />
												</p>
											</div>
										</div>
									</div>	
									<div style="float: left;margin-left: 10px;">
										<div class="control-group">
											<label class="control-label">Incluir Contratos do mesmo Formulário: </label>
											<div class="controls">
												<select id="contrato_ids[]" name="contrato_ids[]" multiple="multiple" style="width: 300px">
													<c:forEach var="contrato" items="${contratos }">
														<option value="${contrato.contrato_id }" selected="selected">${contrato.produto.nome } - ${contrato.etapa.nome }</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
								  </div>
								  <div class="modal-footer">
								    <button class="btn" data-dismiss="modal" aria-hidden="true">Fecha</button>
								    <button class="btn btn-primary" type="submit" id="btt_salvalogistica">Salva</button>
								  </div>
							</form>
							</div>
		
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
					<div class="widget-title">
						<span class="icon">
							<i class="icon-align-justify"></i>									
						</span>
						<h5>Conferência Contrato </h5>
						<c:if test="${usuarioInfo.perfil.chave == 'Administrativo' }">
							<div class="buttons"><a href="#" class="btn btn-mini" onclick="javascript:return conferencia('${contrato.contrato_id}');"><i class="icon-refresh"></i> Conferencia</a></div>
						</c:if>
					</div>
					<div class="widget-content padding">
						<div class="row-fluid">
							<div id="divConferencia" style="margin-left: 50px">
								<c:if test="${not empty conferencias }">
									<table class="table table-striped table-bordered" id="conferencias">
										<thead>
											<tr>
												<th>Data</th>
 												<th>Conferente</th>
 												<th>Procedimento</th>
												<th>Observação</th>
											</tr>
											<c:forEach var="conferencia" items="${conferencias }">
												<tr>
													<td style="text-align: center;"><fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${conferencia.created.time }" /></td>	
													<td style="text-align: center;"><c:out value="${conferencia.createdBy.nome }"></c:out></td>
													<td style="text-align: center;"><c:out value="${conferencia.procedimentoConferencia.nome }"></c:out></td>
													<td style="text-align: center;"><c:if test="${conferencia.isValido }"> OK </c:if> ${conferencia.observacao }</td>
												</tr>
											</c:forEach>
										</thead>
									</table>
								</c:if>
								<c:if test="${empty conferencias }">
									
									Conferência não realizada
								
								</c:if>
							</div> 
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
					<div class="widget-title"><span class="icon">
						<i class="icon-signal"></i></span><h5>Controle Boleto</h5>
						<c:if test="${ ( usuarioInfo.perfil.chave == 'Consultor' ) && contrato.etapa.nome != 'Aguardando Apoio Comercial' }">
							<div class="buttons" style="position: absolute;margin-left: 180px;"><a href="#" class="btn btn-mini" onclick="javascript:return boleto('${contrato.contrato_id}');"><i class="icon-refresh"></i> Alterar</a></div>
						</c:if>
						<c:if test="${ usuarioInfo.perfil.chave == 'Gestor' || usuarioInfo.perfil.chave == 'Administrativo' || usuarioInfo.perfil.chave == 'Supervisor' }">
							<div class="buttons" style="position: absolute;margin-left: 180px;"><a href="#" class="btn btn-mini" onclick="javascript:return boleto('${contrato.contrato_id}');"><i class="icon-refresh"></i> Alterar</a></div>
						</c:if>
					</div>	
					<div class="widget-content padding">
						<div class="row-fluid">
							<div class="span6">
								<div id="divBoleto" style="margin-left: 50px">	
									<div class="control-group">

										<div class="controls">
										<c:if test="${not empty boleto.controle_id }">

											<c:if test="${not empty boleto.createdBy }">
												<label>Primeira atuação por ${boleto.createdBy.nome } em <fmt:formatDate pattern="dd/MM/yyyy HH:mm"  type="time" value="${boleto.dataPrimeiraAtuacao.time }" /></label>
											</c:if>

											<c:if test="${not empty boleto.updatedBy.nome }">
												<label>Atualizado por ${boleto.updatedBy.nome } em <fmt:formatDate pattern="dd/MM/yyyy HH:mm"  type="time" value="${boleto.dataAtuacao.time }" /></label>
											</c:if>

											<br/>
											<div class="control-group">
												<label class="control-label">Previsão de Chegada : <fmt:formatDate pattern="dd/MM/yyyy" value="${boleto.dataPrevisao.time }" /></label>
											</div>
											<div class="control-group">
												<label class="control-label">Próxima Atuação : <fmt:formatDate pattern="dd/MM/yyyy" value="${boleto.dataProximaAtuacao.time }" /></label>
											</div>
											<div class="control-group">
												<label class="control-label">Data de Chegada : <fmt:formatDate pattern="dd/MM/yyyy" value="${boleto.dataChegada.time }" /></label>
											</div>
											<div class="control-group">
												<label class="control-label">Data de Vencimento : <fmt:formatDate pattern="dd/MM/yyyy"  value="${boleto.dataVencimento.time }" /></label>
											</div>
											
											
										</c:if>
										<c:if test="${empty boleto.controle_id }">
											Ainda não realizado
										</c:if> 
										</div>
									 </div>
								</div>
							</div>
							<div class="span6">
								<c:if test="${not empty historicoControleBoleto }">
									<table class="table table-bordered table-striped table-hover data-table" id="lista">
										<thead>
											<tr>
												<th>Data</th>
												<th>Responsável</th>
												<th>Observação</th>
											</tr>
										</thead>
										<tbody>	
											<c:forEach items="${historicoControleBoleto}" var="historico">
												<tr>
													<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss"  type="time" value="${historico.created.time }" /></td>
													
													<td>${historico.createdBy.nome }</td>
													<td>${historico.observacao }</td>
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
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-title"><span class="icon">
						<i class="icon-signal"></i></span><h5>Controle Averbacao</h5>
						<c:if test="${usuarioInfo.perfil.chave != 'Consultor'}">
							<div class="buttons" style="position: absolute;margin-left: 180px;"><a href="#" class="btn btn-mini" onclick="javascript:return averbacao('${contrato.contrato_id}');"><i class="icon-refresh"></i> Alterar</a></div>
						</c:if>
					</div>	
					<div class="widget-content padding">
						<div class="row-fluid">
							<div class="span6">
								<div id="divAverbacao" style="margin-left: 50px">	
									<div class="control-group">
										<div class="controls">
										<c:if test="${not empty averbacao.controle_id }">
											
											<c:if test="${not empty averbacao.createdBy }">
												<label>Primeira atuação por ${averbacao.createdBy.nome } em <fmt:formatDate pattern="dd/MM/yyyy HH:mm"  type="time" value="${averbacao.dataPrimeiraAtuacao.time }" /></label>
											</c:if>

											<c:if test="${not empty averbacao.updatedBy.nome }">
												<label>Atualizado por ${averbacao.updatedBy.nome } em <fmt:formatDate pattern="dd/MM/yyyy HH:mm"  type="time" value="${averbacao.dataAtuacao.time }" /></label>
											</c:if>
											
											<br/>
											<div class="control-group">
												<label class="control-label">Próxima Atuação : <fmt:formatDate pattern="dd/MM/yyyy" value="${averbacao.dataProximaAtuacao.time }" /></label>
											</div>
											<div class="control-group">
												<label class="control-label">Previsão de Liberação : <fmt:formatDate pattern="dd/MM/yyyy" value="${averbacao.dataPrevisao.time }" /></label>
											</div>

										</c:if>
										<c:if test="${empty averbacao.controle_id }">
											Ainda não realizado
										</c:if> 
									</div>
									</div>
									
								</div>
							</div>
							<div class="span6">
								<c:if test="${not empty historicoControleAverbacao }">
									<table class="table table-bordered table-striped table-hover data-table" id="lista">
										<thead>
											<tr>
												<th>Data</th>
												<th>Responsável</th>
												<th>Observação</th>
											</tr>
										</thead>
										<tbody>	
											<c:forEach items="${historicoControleAverbacao}" var="historico">
												<tr>
													<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss"  type="time" value="${historico.created.time }" /></td>
													<td>${historico.createdBy.nome }</td>
													<td>${historico.observacao }</td>
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

<%@ include file="/footer.jspf" %>