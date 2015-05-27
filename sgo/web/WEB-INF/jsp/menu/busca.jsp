<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="/WEB-INF/lib/formattag.jar" prefix="f" %>

<script type="text/javascript">

$(document).ready(function() {

	TableTools.DEFAULTS.aButtons = [ "print", "xls" ];

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
			  			/* 1 - Data */  { "sType": "custom_euro_date_hour" },
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
			  			/* 17 - Data Chegada */     { "bVisible":    false, "sType": "custom_euro_date" },
			  			/* 18 - Data Previsão */     { "bVisible":    false, "sType": "custom_euro_date" },
			  			/* 19 - Data Vencimento */     { "bVisible":    false,  "sType": "custom_euro_date" },
			  			/* 20 - Data Próx Atuação */     { "bVisible":    false,  "sType": "custom_euro_date" },
			  			/* 21 - Data Últ Atuação */     { "bVisible":    false,  "sType": "custom_euro_date" },
			  			/* 22 - Data Quitação */     { "bVisible":    false,  "sType": "custom_euro_date" },
			  			/* 23 - Data Digitação */     { "bVisible":    false,  "sType": "custom_euro_date" },
			  			/* 24 - Proposta Banco */     { "bVisible":    false },
			  			/* 25 - Contrato Banco */     { "bVisible":    false },
			  			/* 26 - Numero Portabilidade */     { "bVisible":    false },
			  			/* 27 - Data Solicitacao Saldo */     { "bVisible":    false },
			  			/* 28 - Pós Venda */     { "bVisible":    false },
			  			/* 29 - Motivo Recusa */     { "bVisible":    false },
			  			/* 30 - Data Status Final */     { "bVisible":    false, "sType": "custom_euro_date" },
			  			/* 31 - Repasse */     { "bVisible":    false },
			  			/* 32 - % Repasse */     { "bVisible":    false },
			  			/* 33 - Tabela */     { "bVisible":    false },
			  			/* 34 - QtdParcelasAberto */     { "bVisible":    false },
			  			/* 35 - Contrato ID */     { "bVisible":    false },
			  			/* 36 - Dias Ultima Atualização */     { "bVisible":    false },
			  			/* 37 - Desconto */     { "bVisible":    false }
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
			"aiExclude": [ 0,1,2,3,4,5,6,7,12,,13,14,15,16 ]
			
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
	
	
	
});

</script>


<c:if test="${empty contratos}">

		<div class="container-fluid">
			<div class="row-fluid" style="margin-top: 1px;">
				<div class="span12" style="margin-top: 1px;">
					<table class="table table-bordered table-striped table-hover" style="width: 100%;float: left;font-size: 11px;">
						<tr>
							<th> Não foram encontrados resultados para a pesquisa. </th>
						</tr>
					</table>
				</div>
			</div>
		</div>			

</c:if>


<c:if test="${not empty contratos}">
	
	
		<div class="container-fluid">
			<div class="row-fluid" style="margin-top: 1px;">
				<div class="span12" style="margin-top: 1px;">
					<table class="table table-bordered table-striped table-hover" style="width: 500px;float: right;font-size: 10px;text-align: center;">
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
						<tr>
							<th colspan="5" style="text-align: left"> Média por contrato - Quantidade ( <c:out value="${ countContratos }" /> ) </th>
						</tr>
						<tr>
							<td>R$ <fmt:formatNumber type="NUMBER" value="${totalValorContratos / countContratos}" minFractionDigits="2" /></td>
							<td>R$ <fmt:formatNumber type="NUMBER" value="${totalContratoLiquido / countContratos}" minFractionDigits="2" /></td>
							<td>R$ <fmt:formatNumber type="NUMBER" value="${totalValorDivida / countContratos}" minFractionDigits="2" /></td>
							<td>R$ <fmt:formatNumber type="NUMBER" value="${totalValorLiquido / countContratos}" minFractionDigits="2" /></td>
							<td>R$ <fmt:formatNumber type="NUMBER" value="${totalValorMeta / countContratos}" minFractionDigits="2" /></td>
						</tr>
						<tr>
							<th colspan="5" style="text-align: left"> Média por cliente - Quantidade ( <c:out value="${ countClientesByCpf }" /> ) </th>
						</tr>
						<tr>
							<td>R$ <fmt:formatNumber type="NUMBER" value="${totalValorContratos / countClientesByCpf}" minFractionDigits="2" /></td>
							<td>R$ <fmt:formatNumber type="NUMBER" value="${totalContratoLiquido / countClientesByCpf}" minFractionDigits="2" /></td>
							<td>R$ <fmt:formatNumber type="NUMBER" value="${totalValorDivida / countClientesByCpf}" minFractionDigits="2" /></td>
							<td>R$ <fmt:formatNumber type="NUMBER" value="${totalValorLiquido / countClientesByCpf}" minFractionDigits="2" /></td>
							<td>R$ <fmt:formatNumber type="NUMBER" value="${totalValorMeta / countClientesByCpf}" minFractionDigits="2" /></td>
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
							<c:if test="${not empty contratos}">
		
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
												Número Portabilidade
											</th>
											<th >
												Dt Solicitacao Saldo
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
												% Repasse
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
											<th >
												Qtd Dias
											</th>
											<th >
												Desconto
											</th>
										</tr>
									</thead>
									<tbody>		
										<c:forEach items="${contratos}" var="contrato">
											<!-- 
											<tr <c:if test="${contrato.formulario.posvenda.etapa.nome eq 'Pendente' || 
															  contrato.etapa.nome eq 'Pendente Administrativo' ||
															  contrato.etapa.nome eq 'Pendente Agendamento' ||
															  contrato.etapa.nome eq 'Pendente Banco' ||
															  contrato.etapa.nome eq 'Pendente Coeficiente' ||
															  contrato.etapa.nome eq 'Pendente Apoio Comercial' ||
															  contrato.etapa.nome eq 'Pendente Comercial' ||
															  contrato.etapa.nome eq 'Recalcular' }">class="error"</c:if> >
															  
											 -->
											 <tr
											 <c:choose>
												<c:when test="${contrato.etapa.nome eq 'Aguardando Boleto' }">
														<c:if test="${contrato.qtdDias >= 0 && contrato.qtdDias <= 10 }">class="success"</c:if>
														<c:if test="${contrato.qtdDias >= 11 && contrato.qtdDias <= 15 }">class="warning"</c:if>
														<c:if test="${contrato.qtdDias >= 16 }">class="error"</c:if>
												</c:when>
												<c:when test="${contrato.etapa.nome eq 'Aguardando Correio' }">
														<c:if test="${contrato.qtdDias >= 0 && contrato.qtdDias <= 7 }">class="success"</c:if>
														<c:if test="${contrato.qtdDias >= 8 && contrato.qtdDias <= 10 }">class="warning"</c:if>
														<c:if test="${contrato.qtdDias >= 11 }">class="error"</c:if>
												</c:when>
												<c:when test="${contrato.etapa.nome eq 'Aguardando Digitação' }">
														<c:if test="${contrato.qtdDias >= 0 && contrato.qtdDias <= 2 }">class="success"</c:if>
														<c:if test="${contrato.qtdDias == 3 }">class="warning"</c:if>
														<c:if test="${contrato.qtdDias >= 4 }">class="error"</c:if>
												</c:when>
												<c:when test="${contrato.etapa.nome eq 'Aguardando Integração' }">
														<c:if test="${contrato.qtdDias >= 0 && contrato.qtdDias <= 7 }">class="success"</c:if>
														<c:if test="${contrato.qtdDias >= 8 && contrato.qtdDias <= 10 }">class="warning"</c:if>
														<c:if test="${contrato.qtdDias >= 11 }">class="error"</c:if>
												</c:when>
												<c:when test="${contrato.etapa.nome eq 'Aguardando Pós Venda' }">
														<c:if test="${contrato.qtdDias == 1 }">class="success"</c:if>
														<c:if test="${contrato.qtdDias == 2 }">class="warning"</c:if>
														<c:if test="${contrato.qtdDias >= 3 }">class="error"</c:if>
												</c:when>
												<c:when test="${contrato.etapa.nome eq 'Aguardando Qualidade' }">
														<c:if test="${contrato.qtdDias == 1 }">class="success"</c:if>
														<c:if test="${contrato.qtdDias == 2 }">class="warning"</c:if>
														<c:if test="${contrato.qtdDias >= 3 }">class="error"</c:if>
												</c:when>
												<c:when test="${contrato.etapa.nome eq 'Aguardando Redigitação' }">
														<c:if test="${contrato.qtdDias >= 0 && contrato.qtdDias <= 2 }">class="success"</c:if>
														<c:if test="${contrato.qtdDias == 3 }">class="warning"</c:if>
														<c:if test="${contrato.qtdDias >= 4 }">class="error"</c:if>
												</c:when>
												<c:when test="${contrato.etapa.nome eq 'Aguardando Remarcação' }">
														<c:if test="${contrato.qtdDias == 0 }">class="success"</c:if>
														<c:if test="${contrato.qtdDias >= 1 }">class="warning"</c:if>
												</c:when>
												<c:when test="${contrato.etapa.nome eq 'Aguardando Saldo Quitação' }">
														<c:if test="${contrato.qtdDias >= 0 && contrato.qtdDias <= 7 }">class="success"</c:if>
														<c:if test="${contrato.qtdDias == 8 }">class="warning"</c:if>
														<c:if test="${contrato.qtdDias >= 9 }">class="error"</c:if>
												</c:when>
												<c:when test="${contrato.etapa.nome eq 'Aguardando Status' }">
														<c:if test="${contrato.qtdDias == 0 }">class="warning"</c:if>
														<c:if test="${contrato.qtdDias >= 1 }">class="error"</c:if>
												</c:when>
												<c:when test="${contrato.etapa.nome eq 'Aguardando Logística' }">
														<c:if test="${contrato.qtdDias == 0 }">class="success"</c:if>
														<c:if test="${contrato.qtdDias == 1 }">class="warning"</c:if>
														<c:if test="${contrato.qtdDias >= 2 }">class="error"</c:if>
												</c:when>
												<c:when test="${contrato.etapa.nome eq 'Digitado' }">
														<c:if test="${contrato.qtdDias == 0 }">class="success"</c:if>
														<c:if test="${contrato.qtdDias >= 1 }">class="error"</c:if>
												</c:when>
												<c:when test="${contrato.etapa.nome eq 'Em Análise' }">
														<c:if test="${contrato.qtdDias == 0 }">class="success"</c:if>
														<c:if test="${contrato.qtdDias >= 1 }">class="error"</c:if>
												</c:when>
												<c:when test="${contrato.etapa.nome eq 'Em Assinatura' }">
														<c:if test="${contrato.qtdDias <= 1 }">class="success"</c:if>
														<c:if test="${contrato.qtdDias == 2 }">class="warning"</c:if>
														<c:if test="${contrato.qtdDias >= 3 }">class="error"</c:if>
												</c:when>
												<c:when test="${contrato.etapa.nome eq 'Em Conferência' }">
														<c:if test="${contrato.qtdDias == 1 }">class="success"</c:if>
														<c:if test="${contrato.qtdDias == 2 }">class="warning"</c:if>
														<c:if test="${contrato.qtdDias >= 3 }">class="error"</c:if>
												</c:when>
												<c:when test="${contrato.etapa.nome eq 'Pendente Administrativo' }">
														<c:if test="${contrato.qtdDias == 0 }">class="success"</c:if>
														<c:if test="${contrato.qtdDias == 1 }">class="warning"</c:if>
														<c:if test="${contrato.qtdDias >= 2 }">class="error"</c:if>
												</c:when>
												<c:when test="${contrato.etapa.nome eq 'Pendente Banco' }">
														<c:if test="${contrato.qtdDias >= 0 && contrato.qtdDias <= 3 }">class="success"</c:if>
														<c:if test="${contrato.qtdDias >= 4 && contrato.qtdDias <= 30 }">class="warning"</c:if>
														<c:if test="${contrato.qtdDias >= 31 }">class="error"</c:if>
												</c:when>
												<c:when test="${contrato.etapa.nome eq 'Pendente Comercial' }">
														<c:if test="${contrato.qtdDias == 0 }">class="success"</c:if>
														<c:if test="${contrato.qtdDias == 1 }">class="warning"</c:if>
														<c:if test="${contrato.qtdDias >= 2 }">class="error"</c:if>
												</c:when>
												<c:when test="${contrato.etapa.nome eq 'Pendente Conferência' }">
														<c:if test="${contrato.qtdDias == 0 }">class="success"</c:if>
														<c:if test="${contrato.qtdDias == 1 }">class="warning"</c:if>
														<c:if test="${contrato.qtdDias >= 2 }">class="error"</c:if>
												</c:when>
												<c:when test="${contrato.etapa.nome eq 'Pendente Pós Venda' }">
														<c:if test="${contrato.qtdDias == 0 }">class="warning"</c:if>
														<c:if test="${contrato.qtdDias >= 1 }">class="error"</c:if>
												</c:when>
												<c:when test="${contrato.etapa.nome eq 'Quitado' }">
														<c:if test="${contrato.qtdDias >= 0 && contrato.qtdDias <= 7 }">class="success"</c:if>
														<c:if test="${contrato.qtdDias >= 8 && contrato.qtdDias <= 10 }">class="warning"</c:if>
														<c:if test="${contrato.qtdDias >= 11 }">class="error"</c:if>
												</c:when>
												<c:when test="${contrato.etapa.nome eq 'Recalcular' }">
														<c:if test="${contrato.qtdDias == 0 }">class="warning"</c:if>
														<c:if test="${contrato.qtdDias >= 1 }">class="error"</c:if>
												</c:when>
												<c:when test="${contrato.etapa.nome eq 'Troca de Físico' }">
														<c:if test="${contrato.qtdDias <= 1 }">class="success"</c:if>
														<c:if test="${contrato.qtdDias == 2 }">class="warning"</c:if>
														<c:if test="${contrato.qtdDias >= 3 }">class="error"</c:if>
												</c:when>
												<c:when test="${contrato.etapa.nome eq 'Retorno Troca de Físico' }">
														<c:if test="${contrato.qtdDias == 1 }">class="success"</c:if>
														<c:if test="${contrato.qtdDias >= 2 }">class="error"</c:if>
												</c:when>
											 	</c:choose> >			  
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
													${contrato.numeroPortabilidade }
												</td>
												<td >
													<fmt:formatDate value="${contrato.dataSolicitacaoSaldo.time}" pattern="dd/MM/yy" />
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
													${contrato.percentualRepasse }
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
												<td >
													${contrato.qtdDias }
												</td>
												<td >
													${contrato.desconto }
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
</c:if>