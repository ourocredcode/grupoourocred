<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="/WEB-INF/lib/formattag.jar" prefix="f" %>

<script type="text/javascript">

$(document).ready(function() {

	$('.data-table').dataTable( {
		
		"aLengthMenu": [[10, 25, 50, 100, 200, -1], [10, 25, 50, 100, 200, "Todos"]],

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
			  			/* 23 - Pós Venda */     { "bVisible":    false }
			  		] ,
		
		"sDom": 'C<"clear">lfrtip',
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
												Última Atuação
											</th>
											<th >
												Dt Quitação
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
													<fmt:formatDate value="${contrato.formulario.created.time}" pattern="dd/MM/yy" />
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
													<fmt:formatDate value="${contrato.dataQuitacao.time }" pattern="dd/MM" />
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