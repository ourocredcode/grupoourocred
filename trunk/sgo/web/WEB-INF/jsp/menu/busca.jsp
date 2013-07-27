<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="/WEB-INF/lib/formattag.jar" prefix="f" %>

<script type="text/javascript">

$(document).ready(function() {

	$('.data-table').dataTable( {
		
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
			  			/* 9 - Parcela */     { "bVisible":    false },
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
			  			/* 21 - Pós Venda */     { "bVisible":    false }
			  		] ,
		
		"sDom": 'C<"clear">lfrtip',
		"oColVis": {
			"activate": "mouseover",
			"buttonText": "Selecione Colunas",
			"aiExclude": [ 0,1,2,3,4,5,6,7,8,9,12,,13,14,15,16 ],
			
		}
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
												Pós Venda
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
															  contrato.etapa.nome eq 'Recalcular' }">class="error"</c:if> >
												<td >
													<a href="<c:url value="/contrato/status/${contrato.contrato_id}"/>">${contrato.etapa.nome }</a>
												</td>
												<td >
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
													${contrato.valorContrato }
												</td>
												<td class="${alert}">
													<c:choose>
														<c:when test="${contrato.produto.nome eq 'MARGEM LIMPA' || contrato.produto.nome eq 'RECOMPRA RMC' || contrato.produto.nome eq 'RECOMPRA INSS'}">
															<fmt:formatNumber type="NUMBER" value="${contrato.valorContrato }" minFractionDigits="2" />
														</c:when>
														<c:when test="${contrato.produto.nome eq 'RETENÇÃO' || contrato.produto.nome eq 'REFINANCIAMENTO'}">
															<fmt:formatNumber type="NUMBER" value="${contrato.valorLiquido }" minFractionDigits="2" />
														</c:when>
													</c:choose>
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
													${contrato.formulario.posvenda.etapa.nome }
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