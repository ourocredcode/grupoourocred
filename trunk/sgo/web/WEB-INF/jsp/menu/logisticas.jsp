<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="/WEB-INF/lib/formattag.jar" prefix="f" %>

<script type="text/javascript">

$(document).ready(function() {

	TableTools.DEFAULTS.aButtons = [ "print", "xls" ];

	$('#mytableLogistica').dataTable( {
		
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
			  			/* 0 - Dt ass */   { "sType": "custom_euro_date" },
			  			/* 1 - Super */  null,
			  			/* 2 - Consultor */ null,
			  			/* 3 - Periodo */ null,
			  			/* 4 - Hr Assinat */    null,
			  			/* 5 - Cliente  */    null,
			  			/* 6 - Beneficio */    null,
			  			/* 7 - Contato */    null,
			  			/* 8 - Endereco */    null,
			  			/* 9 - Complement */  { "bVisible":    false },
			  			/* 10 - Ponto Ref */  { "bVisible":    false },
			  			/* 11 - Bairro */     null,
			  			/* 12 - Cep */    null,
			  			/* 13 - Cidade */    null,
			  			/* 14 - Descrição */    null,
			  			/* 15 - Vl Meta */    null
			  			
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
			"buttonText": "Selecione Colunas"
		}

	});
	
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
});

</script>


<c:if test="${empty logisticas}">

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


<c:if test="${not empty logisticas}">

		<div class="container-fluid">
			<div class="row-fluid" style="margin-top: 1px;">
				<div class="span12" style="margin-top: 1px;">
	
					<div class="widget-box">
						<div class="widget-title"><span class="icon"><i class="icon-signal"></i></span><h5>Logísticas</h5></div>
						<div class="widget-content">
							<c:if test="${not empty logisticas}">
		
								<table id="mytableLogistica" class="table table-bordered table-hover data-table" style="font-size: 11px;">
									<thead>	
										<tr>
											<th >
												Dt Ass.
											</th>
											<th >
												Supervisor
											</th>
											<th >
												Consultor
											</th>
											<th >
												Período
											</th>
											<th >
												Hr Assinat
											</th>
											<th >
												Cliente
											</th>
											<th >
												Benefício
											</th>
											<th >
												Contato
											</th>
											<th >
												Endereço
											</th>
											<th >
												Complemento
											</th>
											<th >
												P.Referencia
											</th>
											<th >
												Bairro
											</th>
											<th >
												CEP
											</th>
											<th >
												Cidade
											</th>
											<th >
												Descrição
											</th>
											<th >
												Vl Meta
											</th>
										</tr>
									</thead>
									<tbody>		
										<c:forEach items="${logisticas}" var="logistica">
											<tr>
												<td >
													<fmt:formatDate value="${logistica.dataAssinatura.time}" pattern="dd/MM/yy" />
												</td>
												<td >
													${logistica.contrato.usuario.supervisorUsuario.apelido }
												</td>
												<td >
													${logistica.contrato.usuario.apelido }
												</td>
												<td >
													${logistica.periodo.nome }
												</td>
												<td >
													<fmt:formatDate value="${logistica.horaAssinaturaInicio.time}" pattern="HH:mm" />/<fmt:formatDate value="${logistica.horaAssinaturaFim.time}" pattern="HH:mm" /> 
												</td>
												<td >
													${logistica.contrato.formulario.parceiroNegocio.nome }
												</td>
												<td >
												   ${logistica.contrato.formulario.parceiroBeneficio.numeroBeneficio }
												</td>
												<td >
												   ${logistica.contrato.formulario.parceiroNegocio.descricao }
												</td>
												<td >
													${logistica.contrato.formulario.parceiroLocalidade.localidade.tipoLocalidade.nome } ${logistica.contrato.formulario.parceiroLocalidade.localidade.endereco } , ${logistica.contrato.formulario.parceiroLocalidade.numero }
												</td>
												<td >
													${logistica.contrato.formulario.parceiroLocalidade.complemento }
												</td>
												<td >
													${logistica.contrato.formulario.parceiroLocalidade.pontoReferencia }
												</td>
												<td >
													${logistica.contrato.formulario.parceiroLocalidade.localidade.bairro }
												</td>
												<td >
													${logistica.contrato.formulario.parceiroLocalidade.localidade.cep }
												</td>
												<td >
													${logistica.contrato.formulario.parceiroLocalidade.localidade.cidade.nome }
												</td>
												<td >
													${logistica.descricao }
												</td>
												<td >
													R$ <fmt:formatNumber type="NUMBER" value="${logistica.contrato.valorMeta }" minFractionDigits="2" />
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