<%@ include file="/header.jspf" %> 

<script type="text/javascript">

$('#Banco').autocomplete({
	source: function( request, response ) {
        $.ajax({
          url: "<c:url value='/banco/busca.json' />",
          dataType: "json",
          data : {empresa_id: $('#agenciaEmpresaId').val() == '' ? '0' :  $('#agenciaEmpresaId').val(), 
        		  organizacao_id: $('#agenciaOrganizacaoId').val() == '' ? '0' :  $('#agenciaOrganizacaoId').val(),
        		  nome : $('#Banco').val()},
          success : function(data) {  

        	  if (!data || data.length == 0) {
     	            $('#Banco').val('');
     	           $('#BancoId').val('');
     	        }

        	  response($.map(data, function(banco) {  
        		  return {
        			  label: banco.nome,
        			  value: banco.banco_id
                  };
              }));  
           }
        });
     },
     focus: function( event, ui ) {
      	 $('#Banco').val(ui.item.label);
           return false;
       } ,
     select: function( event, ui ) {
         $('#Banco').val(ui.item.label);
         $('#BancoId').val(ui.item.value);
         return false;
     }
});

function showObs(value){
	alert(value);
	return false;
}

function altera(atributo, id, valor) {

	var atributo = "formulario." + atributo;

	var temp = "$.post( ";
	temp += "	'<c:url value='/formulario/altera' />', ";
	temp += "	{ '" + atributo + "' : valor, 'formulario.id' : id }, ";
	temp += "	function(resposta) {  }";
	temp += ");";

	eval(temp);

}

$(document).ready(function() {

	$("#supervisor").change(function() {   

		var supervisor = $("#supervisor").val();

		$("#consultor").load('<c:url value="/controle/consultores" />', {'supervisor': supervisor});

	});
	
	
	$("textarea[maxlength]").keyup(function(event){

		var length = this.value.length;
		var btt = document.getElementById("historicoBtt");

		if(length > 0){
			btt.style.display = "inline";
		};
		if(length == 0){
			btt.style.display = "none";
		};
	});
});

function mostra(formulario_id){
	$('#divPosVenda').load('<c:url value="/controleformulario/posvenda"/>',{'formulario_id' : formulario_id});
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
		<a href="#" class="current">Formulário</a>
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

				<div id="formCliente">
				
					<div class="row-fluid">
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

				<div id="formDadosPagamento">
					<div class="row-fluid">
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
						<div class="span3">
							<label for="meioPagamento">Tipo Pagamento</label>
							<select class="input-medium" id="meioPagamento" name="formulario.parceiroInfoBanco.meioPagamento.meioPagamento_id">
								<option value="">Escolha Tipo Pagamento</option>
								<c:forEach items="${meiosPagamento }" var="meioPagamento">
									<option value="${meioPagamento.meioPagamento_id }">${meioPagamento.nome }</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>

				<div id="div-contratos" style="float: none;clear:both;">

					<table class="table table-striped table-bordered">
						<c:if test="${not empty formulario.contratos}">
						<thead>	
							<tr>
								<th colspan="14">
									Total de contratos 
								</th>
							</tr>
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
									<th scope="col">
									Status
								</th>
							</tr>
						</thead>
						<tbody>		
							<c:forEach items="${formulario.contratos}" var="contrato">
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
									<td>
										<a href="<c:url value="/contrato/status/${contrato.contrato_id}"/>">${contrato.etapa.nome }</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>	
						</c:if>
					</table>
				</div>

				<div class="container-fluid">
					<div class="row-fluid">
						<div class="span12">
							<div class="widget-box">
								<div class="widget-title">
									<span class="icon">
										<i class="icon-align-justify"></i>									
									</span>
									<h5>Pós Venda</h5>
								</div>
								<div class="widget-content padding">
									
									<c:if test="${empty posvenda }">
										
											<div class="row-fluid">
												<div id="divPosVenda">
													<div class="row-fluid">
														<div class="span8">
														
															Não realizado
														
														</div>
													</div>
												</div>
											</div>			

									</c:if>
									
									<c:if test="${not empty posvenda }">
											
											<div class="row-fluid">
												<div id="divPosVenda">
													<div class="row-fluid">
														<div class="span8">

															<table class="table table-striped table-bordered">
															<tbody>
																<tr>
																	<td>
																		Quantidade de Contratos
																	</td>
																	<td>
																		<input id="qtContratos" name="posvenda.quantidadeContrato" value="${posvenda.quantidadeContrato}" type="text" class="input-medium" maxlength="20" onblur="verificaValor();" />
																	</td>
																</tr>
																<tr>
																	<td>
																		Valor Parcela confirmado
																	</td>
																	<td>
																		<input id="valorParcela" name="posvenda.valorParcela" value="${posvenda.valorParcela}" type="text" class="input-medium" maxlength="20" onblur="verificaValor();"/>
																	</td>
																</tr>
																	<tr>
																	<td>
																		Prazo confirmado:
																	</td>
																	<td>
																		<select id="prazo" name="posvenda.confirmaPrazo" class="input-medium">
																			<option value="1" <c:if test="${posvenda.confirmaPrazo}">selected</c:if>>Confirma</option>
																			<option value="0" <c:if test="${!posvenda.confirmaPrazo}">selected</c:if>>Não Confirma</option>
																		</select>
																	</td>
																</tr>
																<tr>
																	<td>
																		Valor Líquido confirmado:
																	</td>
																	<td>
																		<input id="valorLiquido" name="posvenda.valorLiquido" value="${posvenda.valorLiquido}" type="text" class="input-medium" maxlength="20" onblur="verificaValor();" />
																		
																	</td>
																</tr>	
																<tr>
																	<td>
																		Status:
																	</td>
																	<td>
																		<select id="status" name="posvenda.etapa.etapa_id" class="input-medium">
																			<option value="">Selecione</option>
																			<c:forEach var="etapa" items="${etapas }">
																				<option value="${etapa.etapa_id }" <c:if test="${posvenda.etapa.etapa_id == etapa.etapa_id}">selected="selected"</c:if>>${etapa.nome }</option>
																			</c:forEach>
																		</select>
																	</td>
																</tr>
																<tr>	
																	<td>
																		Motivo :
																	</td>
																	<td>
																		<select id="motivo" name="posvenda.etapaPendencia.etapa_id" class="input-medium">
																			<option value="">Selecione</option>
																			<c:forEach var="motivo" items="${motivos }">
																				<option value="${motivo.etapa_id }" <c:if test="${posvenda.etapaPendencia.etapa_id == motivo.etapa_id}">selected</c:if>>${motivo.nome }</option>
																			</c:forEach>
																		</select>
																	</td>
																</tr>	
															</tbody>
															</table>

														</div>
														<div class="span4">
															<table class="table table-striped table-bordered">
																<thead>
																	<tr>
																		<th>Data</th>
																		<th>Responsável</th>
																		<th>Observação</th>
																	</tr>
																</thead>
																<tbody>
																	<c:forEach items="${historicos }" var="historico">
																	<tr>
																		<td><fmt:formatDate pattern="dd/MM/yyyy"  type="time" value="${historico.created.time }" /></td>
																		<td>${historico.createdBy.nome }</td>
																		<td>${historico.observacao }</td>
																	</tr>
																	</c:forEach>
																</tbody>
															</table>
														</div>
													</div>
												</div>
											</div>
		
											<div class="row-fluid">
												<div style="float: left;clear: both;">
													
													
													<form id="posvendahistoricoform" name="posvendahistoricoform" action="<c:url value="/controleformulario/incluiComunicacao" />" method="post">
													
													<input type="hidden" id="historico.controleFormulario.controleFormulario_id" name="historico.controleFormulario.controleFormulario_id" value="${historico.controleFormulario.controleFormulario_id }" />
													<input type="hidden" id="historico.formulario.formulario_id" name="historico.formulario.formulario_id" value="${historico.formulario.formulario_id }" />
													<input type="hidden" id="historico.createdBy.usuario_id" name="historico.createdBy.usuario_id" value="${historico.createdBy.usuario_id }" />
													<input type="hidden" id="historico.empresa.empresa_id" name="historico.empresa.empresa_id" value="${historico.empresa.empresa_id }" />	
													<input type="hidden" id="historico.organizacao.organizacao_id" name="historico.organizacao.organizacao_id" value="${historico.organizacao.organizacao_id }" />
													<input type="hidden" id="historico.perfil.perfil_id " name="historico.perfil.perfil_id" value="${historico.perfil.perfil_id }" />	
													
														<table id="myform" style="width: 650px;display: left;">
															<tr>
																<th style="text-align: right;">Incluir observação:</th>
																<td> <textarea id="historico.observacao" name="historico.observacao" rows="3" cols="55" maxlength="190"><c:out value="${historico.observacao}" /></textarea></td>
															</tr>
															<tr>
																<td class="btt">
																	
																</td>
																<td class="btt">
																	<button id="historicoBtt" type="submit" class="form_button" style="display: none;float: left;">Salvar</button>
																</td>
															</tr>
														</table>
											
													</form>
										
												</div>
												
											</div>
									
									
									</c:if>
									
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="row-fluid">

					<div class="span1" style="float: left;">
						<input value="Voltar" type="button" class="btn" onclick="javascript:window.location='/sgo/menu/inicio/${usuarioInfo.perfil.chave}'">
					</div>

					<c:if test="${posvenda.etapa.nome == 'Aprovado pela Análise' }">
						<div class="span1" style="float: left;">
							<input value="Imprimir" type="button" class="btn" onclick="javascript:window.location='/sgo/formulario/impressao/${formulario.formulario_id}'">
						</div>
					</c:if>

					<c:if test="${usuarioInfo.perfil.chave == 'Administrativo' }">
						<div class="span1" style="float: left;">	
							<input value="Pós Venda" type="button" class="btn" onclick="mostra('${formulario.formulario_id}');">
						</div>
					</c:if>
				</div>

			</div>
		</div>	
	</div>
	


<%@ include file="/footer.jspf" %> 