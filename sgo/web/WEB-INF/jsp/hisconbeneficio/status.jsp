<%@ include file="/header.jspf" %> 

<script type="text/javascript">
$(document).ready(function() {

	$("#dataAssinatura").mask("99/99/9999");
	$("#dataStatusFinal").mask("99/99/9999");
	$("#dataConcluido").mask("99/99/9999");
	$("#dataDigitacao").mask("99/99/9999");
	$("#dataQuitacao").mask("99/99/9999");
	$("#dataAgendado").mask("99/99/9999");
	$("#telefoneRes").mask("(99) 9999-9999?9");
	$("#telefoneCel").mask("(99) 9999-9999?9");
	$("#valorQuitacao").maskMoney({symbol:"",decimal:".",thousands:""});

	var justificativa = document.getElementById("justificativa");
	var logistica = document.getElementById("logistica");
	var dataAssinatura = document.getElementById("dataAssinatura");
	var periodoAssinatura = document.getElementById("periodoAssinatura");
	var tipoPagamento = document.getElementById("tipoPagamento");
	var informacaoSaque = document.getElementById("informacaoSaque");
	var dataQuitacao = document.getElementById("dataQuitacao");
	var dataDigitacao = document.getElementById("dataDigitacao");
	var dataAgendado = document.getElementById("dataAgendado");
	var dataStatusFinal = document.getElementById("dataStatusFinal");
	var dataConcluido = document.getElementById("dataConcluido");
	var recusaBanco = document.getElementById("recusaBanco");
	var propostaBanco = document.getElementById("propostaBanco");
	var contratoBanco = document.getElementById("contratoBanco");
	var empresa = document.getElementById("empresa");
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

	if(logistica != undefined) {
		if( logistica.value == "")
			desabilita(logistica);
	}
	
	if(dataAssinatura != undefined) {
		if( dataAssinatura.value == "")
			desabilita(dataAssinatura);
	}
	
	if(periodoAssinatura != undefined) {
		if( periodoAssinatura.value == "")
			desabilita(periodoAssinatura);
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
	
	if(recusaBanco != undefined) {
		if( recusaBanco.value == "")
			desabilita(recusaBanco);
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
	
	if(empresa != undefined) {
		if(empresa.value == "")
			desabilita(empresa);	
	}

	if(tipoPagamento != undefined) {
		if(tipoPagamento.value == "")
			desabilita(tipoPagamento);	
	}

	if(informacaoSaque != undefined) {
		if(informacaoSaque.value == "")
			desabilita(informacaoSaque);	
	}

	if(valorQuitacao != undefined) {
		if(valorQuitacao.value == "" || valorQuitacao.value == 0.0){
			valorQuitacao.value = '';
			desabilita(valorQuitacao);
		}
	}

	$("#logistica").change(function () {

		var dataAssinatura = document.getElementById("dataAssinatura");

		habilita(dataAssinatura);

	});

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
	
	$('#dataAssinatura').focus( function() {
		$(this).calendario({
			target:'#dataAssinatura',
			top:0,
			left:100
		});
	});

	$('#dataDigitacao').focus( function() {
		$(this).calendario({
			target:'#dataDigitacao',
			top:0,
			left:100
		});
	});

	$('#dataQuitacao').focus( function() {
		$(this).calendario({
			target:'#dataQuitacao',
			top:0,
			left:100
		});
	});

	$('#dataAgendado').focus( function() {
		$(this).calendario({
			target:'#dataAgendado',
			top:0,
			left:100
		});
	});

	$('#dataConcluido').focus( function() {
		$(this).calendario({
			target:'#dataConcluido',
			top:0,
			left:100
		});
	});

	$('#dataStatusFinal').focus( function() {
		$(this).calendario({
			target:'#dataStatusFinal',
			top:0,
			left:100
		});
	});
});

function buscaByCalendar(){
	return false;
}


function verificaStatus() {

	var contratoStatus = document.getElementById("contratoStatus");
	var status = contratoStatus.options[contratoStatus.selectedIndex].text;

	var justificativa = document.getElementById("justificativa");
	var logistica = document.getElementById("logistica");
	var dataAssinatura = document.getElementById("dataAssinatura");
	var periodoAssinatura = document.getElementById("periodoAssinatura");
	var dataAgendado = document.getElementById("dataAgendado");
	var dataStatusFinal = document.getElementById("dataStatusFinal");
	var dataConcluido = document.getElementById("dataConcluido");
	var tipoPagamento = document.getElementById("tipoPagamento");
	var dataQuitacao = document.getElementById("dataQuitacao");
	var dataDigitacao = document.getElementById("dataDigitacao");
	var recusaBanco = document.getElementById("recusaBanco");
	var propostaBanco = document.getElementById("propostaBanco");
	var contratoBanco = document.getElementById("contratoBanco");
	var empresa = document.getElementById("empresa");
	var contratoProduto = $("#contratoProduto").val();
	var valorQuitacao = document.getElementById("valorQuitacao");
	var bairro = document.getElementById("bairro");
	var cidade = document.getElementById("cidade");
	var endereco = document.getElementById("endereco");
	var numero = document.getElementById("numero");

	if(status == 'Aprovado') {
		habilita(dataStatusFinal);
		$("#dataStatusFinal").val(getCurrentDate());
		habilita(tipoPagamento);
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
		desabilita(tipoPagamento);
	}

	if(status != 'Recusado')
		desabilita(justificativa);

	if(status == 'Enviado DataPrev' || status == 'Quitado'){

		$("#recusaBanco").val('');

		if(contratoProduto == 'MARGEM LIMPA' || contratoProduto == 'REFINANCIAMENTO' || contratoProduto == 'RETENÇÃO')
			desabilita(dataQuitacao);
		else
			habilita(dataQuitacao);

		desabilita(recusaBanco);

	}

	if(status == 'Pendente Banco'){
		$("#dataQuitacao").val('');
		desabilita(dataQuitacao);
		habilita(recusaBanco);
	}
	
	if(status == 'Pendente Administrativo'){
		habilita(recusaBanco);
	}

	if(status == 'Pendente Agendamento'){
		habilita(recusaBanco);
	}

	if(status == 'Pendente Coeficiente'){
		habilita(recusaBanco);
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

		habilita(propostaBanco);
		habilita(empresa);
		habilita(dataDigitacao);
		$("#dataDigitacao").val(getCurrentDate());
		contratoBanco.disabled=0;

	} else {

		if(propostaBanco.value == '')
			desabilita(propostaBanco);
		if(empresa.value == '')
			desabilita(empresa);
		if(dataDigitacao.value == '')
			desabilita(dataDigitacao);
		if(contratoBanco.value == '')
			contratoBanco.disabled=1;

	}

	if(status == 'Contrato Fora Planilha'){

		logistica.value = '';
		desabilita(logistica);

		dataAssinatura.value='';
		desabilita(dataAssinatura);

		periodoAssinatura.value='';
		desabilita(periodoAssinatura);

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
		habilita(logistica);
		habilita(dataAssinatura);
		habilita(periodoAssinatura);
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
	$('#divHisconBeneficio').load('<c:url value="/hisconbeneficio/cadastro"/>',{'id' : id});
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

function openPopup(url) {
	 window.open(url, "popup_id", "scrollbars,resizable,width=650,height=750");
 return false;
}



</script>

	<div id="content-header">
		<h1>Formulário</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
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
						<h5>Dados Contrato</h5>
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
									<input type="text" class="input-medium" id="parceiroBeneficioNumeroBeneficio" name="parceiroBeneficio.numeroBeneficio" value="${parceiroBeneficio.numeroBeneficio }" />
								</div>
								<div class="span2">
									<label for="formularioParceiroNegocioDataNascimento">Dt Nascimento</label>
									<input type="text" class="input-medium" id="formularioParceiroNegocioDataNascimento" name="formulario.parceiroNegocio.dataNascimento"  placeholder="Nasc." 
									value="<fmt:formatDate pattern="dd/MM/yyyy" value="${formulario.parceiroNegocio.dataNascimento.time }" />">
								</div>
								<div class="span2">
									<label for="parceiroLocalidadeLocalidadeCep">CEP</label>
									<input  class="input-medium" id="parceiroLocalidadeLocalidadeCep" name="parceiroLocalidade.localidade.cep" type="text" value="${parceiroLocalidade.localidade.cep }" />
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div id="formDadosPagamento">

								<div class="span2">
									<label for="Banco">Banco</label>
									<input type="text" class="input-medium" id="Banco" name="Banco" value="${parceiroInfoBanco.banco.nome }"/>	
								</div>
								<div class="span2">
									<label for="Agencia">Agencia</label>
									<input type="text" class="input-medium" id="Agencia" name="Agencia" value="${parceiroInfoBanco.agencia.nome }" />
								</div>
								<div class="span2">
									<label for="Conta">Conta</label>
									<input type="text" class="input-medium" id="Conta" name="Conta" value="${parceiroInfoBanco.contaBancaria }" />
								</div>
								<div class="span2">
									<label for="TipoConta">Tipo Conta</label>
									<input type="text" class="input-medium" id="TipoConta" name="TipoConta" value="${parceiroInfoBanco.contaBancaria.tipoConta }" />
								</div>
								<div class="span2">
									<label for="TipoPagamento">Tipo Pagamento</label>
									<input  class="input-medium" id="TipoPagamento" name="TipoPagamento" type="text" value="${parceiroInfoBanco.meioPagamento }" />
								</div>
							</div>
						</div>
						<div class="row-fluid">
							<div id="div-hiscons" style="float: none;clear:both;">
	
								<table class="table table-striped table-bordered">
									<c:if test="${not empty hisconBeneficio}">
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
			<div class="navbar" style="clear: both;">
				<div class="navbar-inner"  >
					<div class="container">
	
					<div class="control-group"></div>

						<div class="row-fluid">	
							<div class="span2">
								<label for="contratoStatus">status</label>
								<select id="contratoStatus" class="input-medium" onchange="verificaStatus();">
									<c:forEach var="etapa" items="${etapas }">
										<option value="${etapa.workflowEtapa_id}" 
										<c:if test="${etapa.workflowEtapa_id == contrato.workflowEtapa.workflowEtapa_id}">selected</c:if>>${etapa.nome }</option>
									</c:forEach>
								</select>
							</div>
						</div>	
						<div class="form-actions">
							<div class="span1" style="float: left;">
								<input value="Voltar" type="button" class="btn" onclick="javascript:window.location='/sgo/menu/inicio'">
							</div>
							<div class="span1" style="float: left;">
								<input value="Formulário" type="button" class="btn" onclick="javascript:window.location='/sgo/formulario/visualiza/${formulario.formulario_id}'">
							</div>
						</div>				

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>	

<%@ include file="/footer.jspf" %> 