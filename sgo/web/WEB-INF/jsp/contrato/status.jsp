<%@ include file="/header.jspf" %> 

<script type="text/javascript">
$(document).ready(function() {

	$("#dataStatusFinal").mask("99/99/9999");
	$("#logisticaDataAssinatura").mask("99/99/9999");
	$("#dataConcluido").mask("99/99/9999");
	$("#dataDigitacao").mask("99/99/9999");
	$("#dataQuitacao").mask("99/99/9999");
	$("#dataAgendado").mask("99/99/9999");
	$("#telefoneRes").mask("(99) 9999-9999?9");
	$("#telefoneCel").mask("(99) 9999-9999?9");
	$("#valorQuitacao").maskMoney({symbol:"",decimal:".",thousands:""});
	
	$('.data-table').dataTable({
		"bJQueryUI": true,
		"sPaginationType": "full_numbers",
		"sDom": '<""l>t<"F"fp>',
		"aaSorting": []
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
	var valorQuitacao = document.getElementById("valorQuitacao");
	var bairro = document.getElementById("bairro");
	var cidade = document.getElementById("cidade");
	var endereco = document.getElementById("endereco");
	var numero = document.getElementById("numero");

	if(bairro != undefined) {
		if( bairro.value == "")
			desabilita(bairro);
	}
	
	if(cidade != undefined) {
		if( cidade.value == "")
			desabilita(cidade);
	}
	
	if(endereco != undefined) {
		if( endereco.value == "")
			desabilita(endereco);
	}
	
	if(numero != undefined) {
		if( numero.value == "")
			desabilita(numero);
	}

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

	var propostaBanco = document.getElementById("propostaBanco");
	var contratoBanco = document.getElementById("contratoBanco");
	var organizacaoDigitacao = document.getElementById("organizacaoDigitacao");
	var contratoProduto = $("#contratoProduto").val();
	var valorQuitacao = document.getElementById("valorQuitacao");
	var bairro = document.getElementById("bairro");
	var cidade = document.getElementById("cidade");
	var endereco = document.getElementById("endereco");
	var numero = document.getElementById("numero");

	if(status == 'Aprovado') {
		habilita(dataStatusFinal);
		$("#dataStatusFinal").val(getCurrentDate());
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
	
	if(status != 'Aprovado' && status != 'Recusado' && status != 'Concluído') {
		desabilita(dataStatusFinal);
		$("#dataStatusFinal").val("");
	}

	if(status != 'Recusado')
		desabilita(justificativa);

	if(status == 'Enviado DataPrev' || status == 'Quitado'){

		if(contratoProduto == 'MARGEM LIMPA' || contratoProduto == 'REFINANCIAMENTO' || contratoProduto == 'RETENÇÃO')
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

	campo.disabled = true;
	campo.required = false;
	campo.className = 'span10';

}



function habilita(campo){

	campo.disabled = false;
	campo.required = true;
	campo.className = 'span10';

}

function validaForm(form) {

	var consultorTipo = document.getElementById("consultorTipo").value;

	if ($(form).validate().form() === true) {

		if(consultorTipo == 'C') {
			if(confirm("Os telefones de contato e endereço deste cliente estão corretos?") == true){
		 		$(form).submit();
		 	} else {
		 		return false;
		 	}
		} else {
			$(form).submit();
		}

	} else {
		return false;
	}

};

function openPopup(url) {
	 window.open(url, "popup_id", "scrollbars,resizable,width=650,height=750");
 return false;
}



</script>

	<div id="content-header">
		<h1>Formulário</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users" href="<c:url value="/funcionario/equipe/${usuarioInfo.usuario.usuario_id }"/>"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="<c:url value="/menu/inicio/${usuarioInfo.perfil.chave}" />" title="Dashboard" class="tip-bottom"><i class="icon-home"></i> Dashboard</a>
		<a href="javascript:history.go(-1)">Contratos</a>
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
						<h5>Dados Cliente</h5>
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
								</div>
								<div class="span2">
									<label for="Agencia">Agencia</label>
									<input type="text" class="input-medium" id="Agencia" name="Agencia" value="${formulario.parceiroInfoBanco.agenciaNumero }" />
								</div>
								<div class="span2">
									<label for="Conta">Conta</label>
									<input type="text" class="input-medium" id="Conta" name="Conta" value="${formulario.parceiroInfoBanco.contaCorrente }" />
								</div>
								<div class="span2">
									<label for="TipoConta">Tipo Conta</label>
									<input type="text" class="input-medium" id="TipoConta" name="TipoConta" value="${formulario.parceiroInfoBanco.contaBancaria.tipoConta  }" />
								</div>
								<div class="span2">
									<label for="TipoPagamento">Tipo Pagamento</label>
									<input  class="input-medium" id="TipoPagamento" name="TipoPagamento" type="text" value="${formulario.parceiroInfoBanco.meioPagamento.nome }" />
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
						<h5>Dados Contrato</h5>
						<div class="buttons"><a href="#" class="btn btn-mini" onclick="mostra('${contrato.contrato_id}');"><i class="icon-refresh"></i> Altera Contrato</a></div>
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
													<a href="#" onclick="return showObs('${contrato.observacao}');" style="border: 0"><img src="../img/lupa.gif" border="0"/></a>
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

							</select>
						</div>
					</div>
					
					<div class="form-actions">
						<div class="span1" style="float: left;">
							<input value="Salva" type="submit" class="btn btn-primary" >
						</div>
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
						<table class="table table-bordered table-striped table-hover data-table">
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
											<fmt:formatDate pattern="dd/MM/yyyy HH:mm"  type="time" value="${historico.created.time }" />
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
								<div class="span2">
									<a href="#myModal" role="button" class="btn" data-toggle="modal">Nova Logística</a>
								</div>
								<div class="span1">
									<form action="<c:url value="/checklist/${formulario.formulario_id}"/>">
										<input type="submit" value="CheckList" class="btn"/>
									</form>
								</div>
							</div>

							<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
							  <div class="modal-header">
							    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
							    <h3 id="myModalLabel">Cadastro Logística</h3>
							  </div>
							  <form action="<c:url value='/logistica/salva'/>"  method="post">
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
												<select id="logisticaTipoLogisticaId" name="logistica.tipoLogistica.tipoLogistica_id">
													<c:forEach var="tipoLogistica" items="${tiposLogistica }">
														<option value="${tipoLogistica.tipoLogistica_id }">${tipoLogistica.nome }</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">Período : </label>
											<div class="controls">
												<select id="logisticaPeriodoId" name="logistica.periodo.periodo_id">
													<c:forEach var="periodo" items="${periodos }">
														<option value="${periodo.periodo_id }">${periodo.nome }</option>
													</c:forEach>
												</select>
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
								    <button class="btn btn-primary" type="submit">Salva</button>
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
						<div class="buttons"><a href="#" class="btn btn-mini" onclick="javascript:return boleto('${contrato.contrato_id}');"><i class="icon-refresh"></i> Alterar</a></div>
					</div>	
					<div class="widget-content padding">
						<div class="row-fluid">
							<div class="span6">
								<div id="divBoleto" style="margin-left: 50px">	
									<div class="control-group">

										<div class="controls">
										<c:if test="${not empty boleto.controle_id }">

											realizada por ${boleto.usuario.nome } em <fmt:formatDate pattern="dd/MM/yyyy HH:mm"  type="time" value="${boleto.dataAtuacao.time }" />

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
									<table class="table table-striped table-bordered" id="lista">
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
													<td><fmt:formatDate pattern="dd/MM/yyyy"  type="time" value="${historico.created.time }" /></td>
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
						<div class="buttons"><a href="#" class="btn btn-mini" onclick="javascript:return averbacao('${contrato.contrato_id}');"><i class="icon-refresh"></i> Alterar</a></div>
					</div>	
					<div class="widget-content padding">
						<div class="row-fluid">
							<div class="span6">
								<div id="divAverbacao" style="margin-left: 50px">	
									<div class="control-group">
										<div class="controls">
										<c:if test="${not empty averbacao.controle_id }">
											realizada por ${averbacao.usuario.nome } em <fmt:formatDate pattern="dd/MM/yyyy HH:mm"  type="time" value="${averbacao.dataAtuacao.time }" />
											
											<div class="control-group">
												<label class="control-label">Próxima Atuação : <fmt:formatDate pattern="dd/MM/yyyy" value="${averbacao.dataProximaAtuacao.time }" /></label>
											</div>
											<div class="control-group">
												<label class="control-label">Previsão de Chegada : <fmt:formatDate pattern="dd/MM/yyyy" value="${averbacao.dataPrevisao.time }" /></label>
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
									<table class="table table-striped table-bordered" id="lista">
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
													<td><fmt:formatDate pattern="dd/MM/yyyy"  type="time" value="${historico.created.time }" /></td>
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