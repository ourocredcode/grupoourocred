<%@ include file="/header.jspf" %> 

<script type="text/javascript">
$(document).ready(function() {

	$("#dataStatusFinal").mask("99/99/9999");
	$("#dataConcluido").mask("99/99/9999");
	$("#dataDigitacao").mask("99/99/9999");
	$("#dataQuitacao").mask("99/99/9999");
	$("#dataAgendado").mask("99/99/9999");
	$("#telefoneRes").mask("(99) 9999-9999?9");
	$("#telefoneCel").mask("(99) 9999-9999?9");
	$("#valorQuitacao").maskMoney({symbol:"",decimal:".",thousands:""});

	var justificativa = document.getElementById("justificativa");
	var dataQuitacao = document.getElementById("dataQuitacao");
	var dataDigitacao = document.getElementById("dataDigitacao");
	var dataAgendado = document.getElementById("dataAgendado");
	var dataStatusFinal = document.getElementById("dataStatusFinal");
	var dataConcluido = document.getElementById("dataConcluido");
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
	
	if(empresa != undefined) {
		if(empresa.value == "")
			desabilita(empresa);	
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
	var dataAgendado = document.getElementById("dataAgendado");
	var dataStatusFinal = document.getElementById("dataStatusFinal");
	var dataConcluido = document.getElementById("dataConcluido");
	var dataQuitacao = document.getElementById("dataQuitacao");
	var dataDigitacao = document.getElementById("dataDigitacao");

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

function conferencia(id){
	$('#divContrato').load('<c:url value="/conferencia/analise"/>',{'id' : id});
}

function boleto(contrato_id){
	$('#divBoleto').load('<c:url value="/controle/boleto"/>',{'contrato_id' : contrato_id});
}

function averbacao(contrato_id){
	$('#divAverbacao').load('<c:url value="/controle/averbacao"/>',{'contrato_id' : contrato_id});
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
							<div id="div-contratos" style="float: none;clear:both;">
	
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
						<h5>Logística</h5>
					</div>
					<div class="widget-content padding">
						<div class="row-fluid"> 

							<a href="#myModal" role="button" class="btn" data-toggle="modal">Nova Logística</a>

							<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
							  <div class="modal-header">
							    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
							    <h3 id="myModalLabel">Cadastro Logística</h3>
							  </div>
							  <form action="<c:url value='/logistica/salva'/>"  method="post">
								  <div class="modal-body">
								  		
								  			<div class="control-group">
												<label class="control-label">Data Assinatura :</label>
												<div class="controls">
													<input id="logisticaDataAssinatura" name="logistica.dataAssinatura" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${logistica.dataAssinatura.time }" />" class="input-medium" />
												</div>
											</div>
											<div class="control-group">
												<label class="control-label">Tipo Logística :</label>
												<div class="controls">
													<input id="logisticaTipoLogistica" name="logistica.tipoLogistica" value="${logistica.tipoLogistica }" class="input-medium" />
												</div>
											</div>
											<div class="control-group">
												<label class="control-label">Período : </label>
												<div class="controls">
													<select id="logisticaPeriodo" name="logistica.periodo">
														<c:forEach var="periodo" items="${periodos }">
															<option value="${periodo.periodo_id }">${periodo.nome }</option>
														</c:forEach>
													</select>
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
					<div class="widget-title"><span class="icon">
						<i class="icon-signal"></i></span><h5>Controle Boleto</h5>
						<c:if test="${empty boleto.controle_id }">
							<c:if test="${usuarioInfo.perfil.chave == 'Q' || usuarioInfo.perfil.chave == 'P' || usuarioInfo.perfil.chave == 'S'}">
								<div class="buttons"><a href="#" class="btn btn-mini" onclick="boleto('${contrato.contrato_id}');"><i class="icon-refresh"></i> Alterar</a></div>
							</c:if>	
						</c:if>
						<div id="divBoleto" style="float: left;margin-top: 10px;clear: both;" >	
							<c:if test="${not empty boleto.controle_id }">
								<div class="buttons"><a href="#" class="btn btn-mini" onclick="boleto('${contrato.contrato_id}');"><i class="icon-refresh"></i> Alterar</a></div>
								<div class="control-group">
									<label class="control-label">Última Atuação :</label>
									<div class="controls">
									<c:if test="${not empty boleto.controle_id }">
										realizada por ${boleto.usuario.nome } em <fmt:formatDate pattern="dd/MM/yyyy HH:mm"  type="time" value="${boleto.dataAtuacao.time }" />
									</c:if>
									<c:if test="${empty boleto.controle_id }">
										Ainda não realizado
									</c:if> 
									</div>
								 </div>
								<div class="control-group">
									<label class="control-label">Previsão de Chegada :</label>
									<div class="controls">
										<input id="boletoDataPrevisao" name="boleto.dataPrevisao" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${boleto.dataPrevisao.time }" />" class="input-medium" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">Próxima Atuação :</label>
									<div class="controls">
										<input id="boletoDataProximaAtuacao" name="boleto.dataProximaAtuacao" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${boleto.dataProximaAtuacao.time }" />" class="input-medium" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">Data de Chegada : </label>
									<div class="controls">
										<input id="boletoDataChegada" name="boleto.dataChegada" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${boleto.dataChegada.time }" />" class="input-medium" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">Data de Vencimento : </label>
									<div class="controls">
										<input id="boletoDataVencimento" name="boleto.dataVencimento" value="<fmt:formatDate pattern="dd/MM/yyyy"  value="${boleto.dataVencimento.time }" />"  class="input-medium" />
									</div>
								</div>	
							</c:if>

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
						<c:if test="${empty averbacao.controle_id }">
							<c:if test="${usuarioInfo.perfil.chave == 'Q' || usuarioInfo.perfil.chave == 'P' || usuarioInfo.perfil.chave == 'S'}">
								<div class="buttons"><a href="#" class="btn btn-mini" onclick="averbacao('${contrato.contrato_id}');"><i class="icon-refresh"></i> Alterar</a></div>
							</c:if>	
						</c:if>
						<div id="divAverbacao" style="float: left;margin-top: 10px;clear: both;" >	
							<c:if test="${not empty averbacao.controle_id }">
								<div class="buttons"><a href="#" class="btn btn-mini" onclick="averbacao('${contrato.contrato_id}');"><i class="icon-refresh"></i> Alterar</a></div>
								
								<div class="control-group">
									<label class="control-label">Última Atuação :</label>
									<div class="controls">
									<c:if test="${not empty averbacao.controle_id }">
										realizada por ${averbacao.usuario.nome } em <fmt:formatDate pattern="dd/MM/yyyy HH:mm"  type="time" value="${averbacao.dataAtuacao.time }" />
									</c:if>
									<c:if test="${empty averbacao.controle_id }">
										Ainda não realizado
									</c:if> 
									</div>
								 </div>
								<div class="control-group">
									<label class="control-label">Próxima Atuação :</label>
									<div class="controls">
										<input id="averbacaoDataProximaAtuacao" name="averbacao.dataProximaAtuacao" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${averbacao.dataProximaAtuacao.time }" />" class="input-medium" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">Previsão de Chegada :</label>
									<div class="controls">
										<input id="averbacaoDataPrevisao" name="averbacao.dataPrevisao" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${averbacao.dataPrevisao.time }" />" class="input-medium" />
									</div>
								</div>
							
								<div class="control-group">
									<label class="control-label">Observacao : </label>
									<div class="controls">
										<textarea  style="width: 100%;" id="observacao" name="observacao" class="input-medium" rows="5" cols="120" maxlength="255"></textarea>
									</div>
								</div>
								<div class="form-actions">
									<input type="button" value="Salvar" class="btn btn-primary" onclick="javascript:validaForm('#averbacaoForm');">
									<button type="button" value="Fecha" class="btn btn-primary" onclick="window.location.reload(true);">Fecha</button>
								</div>
								
									
							</c:if>

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

						<div class="row-fluid"> 
	
							<div class="span2">
								<label for="dataAgendado">Data Agendado</label>
								<input id="dataAgendado" type="text" name="contratoStatus.dataAgendado" value="<fmt:formatDate pattern="dd/MM/yyyy"  type="time" value="${contratoStatus.dataAgendado.time}" />" class="input-medium"/>
							</div>
							
							<div class="span2">
								<label for="valorQuitacao">Valor Quitação</label>
								<input id="valorQuitacao" type="text" name="contratoStatus.valorQuitacao" value="${contratoStatus.valorQuitacao}" class="input-medium" />
							</div>
							
							<div class="span2">
								<label for="dataQuitacao">Data Quitação</label>
								<input id="dataQuitacao" type="text" name="contratoStatus.dataQuitacao" value="<fmt:formatDate pattern="dd/MM/yyyy"  type="time" value="${contratoStatus.dataQuitacao.time}" />" class="input-medium"/>
							</div>
						</div>
						
						<div class="row-fluid">
	
							<div class="span2">
								<label for="dataDigitacao">Data Digitacao</label>
								<input id="dataDigitacao" type="text" name="contratoStatus.dataDigitacao" value="<fmt:formatDate pattern="dd/MM/yyyy"  type="time" value="${contratoStatus.dataDigitacao.time}" />" class="input-medium" />
							</div>
							<div class="span2">	
								<label for="contratoBanco">Contrato Banco</label>	
								<input id="contratoBanco" type="text" name="contratoStatus.contratoBanco" value="${contratoStatus.contratoBanco}" class="input-medium" />
							</div>
							<div class="span2">	
								<label for="propostaBanco">Proposta Banco</label>	
								<input id="propostaBanco" type="text" name="contratoStatus.propostaBanco" value="${contratoStatus.propostaBanco}" class="input-medium" />
							</div>
							<div class="span2">	
								<label for="empresa">Empresa</label>	
								<select id="empresa" name="contratoStatus.empresa" class="input-medium" >
									<option value="">Selecione</option>
									<option value="ATGGOLD" <c:if test="${contratoStatus.empresa == 'ATGGOLD'}">selected</c:if>>ATGGOLD</option>
									<option value="GOCX" <c:if test="${contratoStatus.empresa == 'GOCX'}">selected</c:if>>GOCX</option>
									<option value="GRGOLD" <c:if test="${contratoStatus.empresa == 'GRGOLD'}">selected</c:if>>GRGOLD</option>
									<option value="OUROCRED" <c:if test="${contratoStatus.empresa == 'OUROCRED'}">selected</c:if>>OUROCRED</option>
								</select>
							</div>
	
						</div>
					</div>

				</div>
			</div>										
		</div>

		<div class="navbar" style="clear: both;">
			<div class="navbar-inner"  >
				<div class="container">

					<div class="control-group"></div>

						<div class="row-fluid">

						<div class="span2">
							<label for="contratoStatus">Status Contrato</label>
							<select id="contratoStatus" name="contrato.workflowEtapa.workflowEtapa_id" class="input-medium" onchange="verificaStatus();">
								<c:forEach var="etapa" items="${etapas }">
									<option value="${etapa.workflowEtapa_id}" 
									<c:if test="${etapa.workflowEtapa_id == contrato.workflowEtapa.workflowEtapa_id}">selected</c:if>>${etapa.nome }</option>
								</c:forEach>
							</select>
						</div>

						<div class="span2">
							<label for="dataStatusFinal">Data Aprova/Recusa</label>
							<input id="dataStatusFinal" type="text" name="contratoStatus.dataStatusFinal" value="<fmt:formatDate pattern="dd/MM/yyyy"  type="time" value="${contratoStatus.dataStatusFinal.time}" />" class="input-medium"/>
						</div>
						
						<div class="span2">
							<label for="dataConcluido">Data Conclusão</label>
							<input id="dataConcluido" type="text" name="contratoStatus.dataConcluido" value="<fmt:formatDate pattern="dd/MM/yyyy"  type="time" value="${contratoStatus.dataConcluido.time}" />" class="input-medium"/>
						</div>

						<div class="span2">
							<label for="justificativa">Justificativa</label>
							<select id="justificativa" name="contratoStatus.justificativa" class="input-medium">

								<option value="" selected="selected">Selecione</option>
								<option value="Cliente com pendência de documentos" <c:if test="${contratoStatus.justificativa == 'Cliente com pendência de documentos'}">selected</c:if>>Cliente com pendência de documentos</option>
								<option value="Cliente fechou por outro banco" <c:if test="${contratoStatus.justificativa == 'Cliente fechou por outro banco'}">selected</c:if>>Cliente fechou por outro banco</option>
								<option value="Cliente não assinou" <c:if test="${contratoStatus.justificativa == 'Cliente não assinou'}">selected</c:if>>Cliente não assinou</option>
								<option value="Cliente não retirou o boleto" <c:if test="${contratoStatus.justificativa == 'Cliente não retirou o boleto'}">selected</c:if>>Cliente não retirou o boleto</option>
								<option value="Cliente refinanciou" <c:if test="${contratoStatus.justificativa == 'Cliente refinanciou'}">selected</c:if>>Cliente refinanciou</option>
								<option value="Dívida maior que o previsto" <c:if test="${contratoStatus.justificativa == 'Dívida maior que o previsto'}">selected</c:if>>Dívida maior que o previsto</option>
								<option value="Erro de análise" <c:if test="${contratoStatus.justificativa == 'Erro de análise'}">selected</c:if>>Erro de análise</option>
								<option value="Erro de preenchimento" <c:if test="${contratoStatus.justificativa == 'Erro de preenchimento'}">selected</c:if>>Erro de preenchimento</option>
								<option value="Família não deixou" <c:if test="${contratoStatus.justificativa == 'Família não deixou'}">selected</c:if>>Família não deixou</option>
								<option value="Junção de Parcelas" <c:if test="${contratoStatus.justificativa == 'Junção de Parcelas'}">selected</c:if>>Junção de Parcelas</option>
								<option value="Operador trocou de equipe" <c:if test="${contratoStatus.justificativa == 'Operador trocou de equipe'}">selected</c:if>>Operador trocou de equipe</option>
								<option value="Recusado Banco" <c:if test="${contratoStatus.justificativa == 'Recusado Banco'}">selected</c:if>>Recusado Banco</option>
								<option value="Recusado Qualidade" <c:if test="${contratoStatus.justificativa == 'Recusado Qualidade'}">selected</c:if>>Recusado Qualidade</option>
								<option value="Recusado Pós Venda" <c:if test="${contratoStatus.justificativa == 'Recusado Pós Venda'}">selected</c:if>>Recusado Pós Venda</option>

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

<%@ include file="/footer.jspf" %> 