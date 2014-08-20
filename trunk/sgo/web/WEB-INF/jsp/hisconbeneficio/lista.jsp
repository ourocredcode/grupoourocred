<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript">

	 $(document).ready(function() {
		 
		 $('.data-table').dataTable( {
			 
			 "aLengthMenu": [[10, 25, 50, 100, 200, -1], [10, 25, 50, 100, 200, "Todos"]],
			 
			 "iDisplayLength": -1,
			 
			 "iCookieDuration": 60 * 5,
			 
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
			 
		 	"sDom": ' T C <"clear">lfrtip',

				"oTableTools": {
					"aButtons": [
						{
							"sExtends": "xls",
							"mColumns": [1,2,3,4,5,9,10,11,12,13,15],
							"sButtonText": "Excel"
						}
					]
				},
				
			"oColVis": {
				"activate": "mouseover",
				"buttonText": "Selecione Colunas",
				"aiExclude": [ 0,4,5,7,13,15 ]
			},	
			 
		 });

	 });

</script>	 

<table class="table table-bordered table-hover data-table">
	<thead>
		<tr>
			<!-- 0 --><th>Pdf</th> 								
			<!-- 1 --><th>Data solicitação</th> 					
			<!-- 2 --><th>Data solicitação Adm</th> 				
			<!-- 3 --><th>Data Envio Adm</th> 
			<!-- 4 --><th style="display: none">Supervisor</th> 
			<!-- 5 --><th style="display: none">Consultor</th> 
			<!-- 6 --><th>Supervisor</th> 
			<c:if test="${usuarioInfo.perfil.chave == 'Gestor'}">
			<!-- 7 --><th>Troca Consultor (Apenas Gestor)</th>	
			</c:if>
			<c:if test="${usuarioInfo.perfil.chave != 'Gestor'}">
			<!-- 7 --><th style="display: none"></th>	 
			</c:if>
			<!-- 8 --><th>Consultor</th> 
			<!-- 9 --><th>Cliente</th> 
			<!-- 10 --><th>Nascimento</th> 
			<!-- 11 --><th>Cpf</th> 
			<!-- 12 --><th>Número Benefício</th> 
			<!-- 13 --><th style="display: none">Status Atual</th> 
			<!-- 14 --><th>Status Atual</th> 
			<!-- 15 --><th style="display: none">Posição</th> 
			<!-- 16 --><th>Posição</th> 
			<c:if test="${usuarioInfo.perfil.chave != 'Consultor' }">
			<!-- 17 --><th>QT</th> 
			</c:if>
			
			
		</tr>
	</thead>
	<tbody>	
		<c:forEach items="${hisconsBeneficio}" var="hiscon">
			<tr <c:if test="${hiscon.countHiscons >= 3 }"> class="error"</c:if>>
				<td>
					<c:if test="${hiscon.isEnviado}">
						<a href="<c:url value="/visualizaHiscon/${hiscon.hisconBeneficio_id}"/>"><img src='<c:url  value="/img/pdf.gif" />' border="0"/></a>
					</c:if>
				</td>
				<td><fmt:formatDate pattern="dd/MM/yy HH:mm" type="date" value="${hiscon.created.time}" /></td>
				<td><fmt:formatDate pattern="dd/MM/yy HH:mm" type="date" value="${hiscon.dataAdm.time}" /></td>	
				<td><fmt:formatDate pattern="dd/MM/yy HH:mm" type="date" value="${hiscon.dataEnvio.time}" /></td>	
				
				<td style="display: none">${hiscon.usuario.supervisorUsuario.apelido }</td>
				<td style="display: none">${hiscon.usuario.apelido }</td>
				
				<c:if test="${usuarioInfo.perfil.chave != 'Gestor'}">

					<td>${hiscon.usuario.supervisorUsuario.apelido }</td>
					
					<td style="display: none"></td>
					
				</c:if>

				<c:if test="${usuarioInfo.perfil.chave == 'Gestor'}">
					<td>
						<select id="busca_Supervisor_lista" name="busca_Supervisor_lista" class="input-small" onchange="showConsultores(this.value);">
							<c:forEach items="${supervisoresLista}" var="supervisorLista">
								<option value="${supervisorLista.usuario_id}" <c:if test="${supervisorLista.usuario_id == hiscon.usuario.supervisorUsuario.usuario_id}">selected</c:if> >${supervisorLista.apelido }</option>
							</c:forEach>
						</select>	
					</td>
					<td>
						<select id="busca_ConsultorLista" name="busca_ConsultorLista" class="input-small" onchange="return altera('usuario.usuario_id','${hiscon.hisconBeneficio_id}', this.value);">
							<option value="">Selecione um Consultor</option>
							<c:forEach var="consultor" items="${consultores }">
								<option value="${consultor.usuario_id }">${consultor.apelido }</option>
							</c:forEach>
						</select>
					</td>					
				</c:if>						

				<c:if test="${usuarioInfo.perfil.chave != 'Supervisor'}">
					<td>${hiscon.usuario.apelido }</td>
				</c:if>
				<c:if test="${usuarioInfo.perfil.chave == 'Supervisor'}">
					<td>
						<select id="hisconBeneficioConsultor" name="hisconBeneficioConsultor" class="input-small" onchange="return altera('usuario.usuario_id','${hiscon.hisconBeneficio_id}', this.value);">
							<option value="">Selecione um Consultor</option>
							<c:forEach var="consultor" items="${consultores }">
								<option value="${consultor.usuario_id }" <c:if test="${consultor.usuario_id == hiscon.usuario.usuario_id}">selected</c:if>>${consultor.apelido }</option>
							</c:forEach>
						</select>
					</td>
				</c:if>	
													
				<td><a data-toggle="modal" onclick="showcontatos(${hiscon.parceiroBeneficio.parceiroNegocio.parceiroNegocio_id});">${hiscon.parceiroBeneficio.parceiroNegocio.nome }</a></td>
				<td><fmt:formatDate pattern="dd/MM/yy" type="date" value="${hiscon.parceiroBeneficio.parceiroNegocio.dataNascimento.time }" /></td>
				<td>${hiscon.parceiroBeneficio.parceiroNegocio.cpf }</td>
				<td>${hiscon.parceiroBeneficio.numeroBeneficio }</td>				
				
				<td style="display: none">${hiscon.etapa.nome }</td>
				
				<td>
					<select id="hisconBeneficioStatus" class="input-medium" onchange="return altera('etapa.etapa_id','${hiscon.hisconBeneficio_id}', this.value);" >
						<c:forEach var="etapa" items="${hiscon.etapas }">
							<option value="${etapa.etapa_id }" <c:if test="${etapa.etapa_id == hiscon.etapa.etapa_id}">selected</c:if>>${etapa.nome }</option>
						</c:forEach>
					</select>
				</td>
				
				<td style="display: none">${hiscon.etapaPosicao.nome }</td>
				
				<td>
					<select id="hisconBeneficioPosicoes" class="input-medium" onchange="return altera('etapaPosicao.etapa_id','${hiscon.hisconBeneficio_id}', this.value);" >
						<c:forEach var="etapaPosicao" items="${hiscon.posicoes }">
							<option value="${etapaPosicao.etapa_id }" <c:if test="${etapaPosicao.etapa_id == hiscon.etapaPosicao.etapa_id}">selected</c:if>>${etapaPosicao.nome }</option>
						</c:forEach>
					</select>
				</td>
				<c:if test="${usuarioInfo.perfil.chave != 'Consultor' }">
					<td style="text-align: center;">
						<a href='<c:url value="/hisconbeneficio/detalhe/${hiscon.parceiroBeneficio.parceiroBeneficio_id}"/>'><h5><small>${hiscon.countHiscons }</small></h5></a>
					</td>
				</c:if>
				
				
				
			</tr>
		</c:forEach>
	</tbody>
</table>

<div id="myModal" class="modal hide fade" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div> 


<script type="text/javascript">
	function showcontatos(id){

		$("#myModal").load('<c:url value="/hisconbeneficio/parceironegocio/contatos" />', {'parceironegocio_id': id});
		$('#myModal').modal('show');

	}
	
	function showConsultores(id){

		$("#busca_ConsultorLista").load('<c:url value="/hisconbeneficio/consultores" />', {'supervisor_id': id});

	}
</script>