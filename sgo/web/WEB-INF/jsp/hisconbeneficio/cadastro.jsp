<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){
	
	$('.data-table').dataTable( {
		
		"aLengthMenu": [[10, 25, 50, 100, 200, -1], [10, 25, 50, 100, 200, "Todos"]],

		"iDisplayLength" : -1,

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
	} );

	$('#hisconbeneficio-li-a').click(function() {
		window.location.href = '<c:url value="/hisconbeneficio/cadastro" />';
	});

	$("#hisconBeneficioIsActive").change(function(e){
		if(document.hisconBeneficioForm.hisconBeneficioIsActive.checked==true){
			document.hisconBeneficioForm.hisconBeneficioIsActive.value=true;
		}else{
			document.hisconBeneficioForm.hisconBeneficioIsActive.value=false;
		}
	});

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/hisconbeneficio/cadastro" />';
	});

});

function altera(atributo, id, valor) {

	var atributo = "hisconBeneficio." + atributo;

	var temp = "$.post( ";
	temp += "	'<c:url value='/hisconbeneficio/altera' />', ";
	temp += "	{ '" + atributo + "' : valor, 'hisconBeneficio.hisconBeneficio_id' : id }, ";
	temp += "	function(resposta) { }";
	temp += ");";

	eval(temp);

}

function showcontatos(id){
	
	$("#myModal").load('<c:url value="/hisconbeneficio/parceironegocio/contatos" />', {'parceironegocio_id': id});
	$('#myModal').modal('show');

}

</script>

	<div id="content-header">
		<h1>Cadastro de Hiscon</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
		<a href="#" class="current">Cadastro de Hiscon</a>
	</div>
	
	<div class="alert alert-info">
		Busque o cliente através do  <strong> número de matrícula </strong> . <a href="#" data-dismiss="alert" class="close">×</a>
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

	<div id="buscaHisconBeneficioDiv" style="float: left; margin-top: 10px; margin-left: 20px;position: absolute;">
		<form id="buscaHisconBeneficioDiv" class="form-search" action="<c:url value="/hisconbeneficio/cadastro" />" method="POST">
			<div class="input-append">
				<input type="hidden" id="hisconBeneficioEmpresaId" name="empresa_id" value="${usuarioInfo.empresa.empresa_id }" />
				<input type="hidden" id="hisconBeneficioOrganizacaoId" name="organizacao_id" value="${usuarioInfo.organizacao.organizacao_id }" />
				<input type="text" class="input-medium" id="numeroBeneficio" name="numeroBeneficio" placeholder="Benefício"/>
				<button type="submit" class="btn btn-mini" style="height: 26px;">Busca</button>
			</div>
		</form>
	</div>
	<br>	
	<div class="container-fluid">
		<div class="row-fluid">
			
			
			
			<div class="span12">			
				<form id="hisconBeneficioForm" name="hisconBeneficioForm" action="<c:url value="/hisconbeneficio/salva"/>" method="POST">
				
					<div class="row-fluid">
						<div class="span3">
							<label for="hisconBeneficioEmpresa">Empresa</label>
							<input class="input-medium" id="hisconBeneficioEmpresaId" name="hisconBeneficio.empresa.empresa_id" value="${usuarioInfo.empresa.empresa_id }" type="hidden" readonly="readonly"/>
							<input class="input-xlarge" id="hisconBeneficioEmpresa" name="hisconBeneficio.empresa.nome" value="${usuarioInfo.empresa.nome }" type="text" readonly="readonly"/>
						</div>
		
						<div class="span3">
							<label for="hisconBeneficioOrganizacao">Organização</label>
							<input  class="input-medium" id="hisconBeneficioOrganizacaoId" name="hisconBeneficio.organizacao.organizacao_id"  value="${usuarioInfo.organizacao.organizacao_id }" type="hidden" readonly="readonly"/>
							<input  class="input-xlarge" id="hisconBeneficioOrganizacao" name="hisconBeneficio.organizacao.nome" value="${usuarioInfo.organizacao.nome }" type="text" readonly="readonly" />							
						</div>
					</div>

					<div class="row-fluid">
						<div class="controls controls-row">
							<label for="hisconBeneficioCpf">Cpf</label>
							<input  class="input-medium" id="hisconBeneficioCpf" name="hisconBeneficio.parceiroBeneficio.parceiroNegocio.cpf" type="text" placeholder="Cpf" value="${hisconBeneficio.parceiroBeneficio.parceiroNegocio.cpf }">
						</div>
						<div class="controls controls-row">
							<label for="hisconBeneficioParceiroNegocio">Parceiro de Negócios</label>
							<input  class="input-xxlarge" id="hisconBeneficioParceiroNegocio" name="hisconBeneficio.parceiroBeneficio.parceiroNegocio.nome" type="text" placeholder="Parceiro de Negócios" value="${hisconBeneficio.parceiroBeneficio.parceiroNegocio.nome }">
							<input  class="input-medium" id="hisconBeneficioParceiroNegocioId" name="hisconBeneficio.parceiroBeneficio.parceiroNegocio.parceiroNegocio_id" type="hidden" value="${hisconBeneficio.parceiroBeneficio.parceiroNegocio.parceiroNegocio_id }">
							<input  class="input-medium" id="hisconBeneficioParceiroBeneficioId" name="hisconBeneficio.parceiroBeneficio.parceiroBeneficio_id" type="hidden" value="${hisconBeneficio.parceiroBeneficio.parceiroBeneficio_id }">
						</div>
					</div>

				</form>
				
				<div class="row-fluid">

					<div class="span1">
						<div class="btn-group">
							<form action="<c:url value="/hisconbeneficio/limpar"/>" method="POST" >
								<input type="submit" value="Limpar" class="btn"/>
							</form>
						</div>
					</div>
					<div class="span1">
						<c:if test="${hisconBeneficio.parceiroBeneficio.parceiroNegocio.parceiroNegocio_id != null}">
							<div class="btn-group">
								<form action="<c:url value="/hisconbeneficio/salva"/>" method="POST">
									<button type="submit" class="btn btn-primary" id="btnSalvar" value="Solicitar Hiscon">Solicitar Hiscon</button>									
								</form>
							</div>
						</c:if>
					</div>
				</div>

				<c:if test="${usuarioInfo.perfil.chave == 'Hiscon'}">
					<form action="<c:url value="/uploadHiscon"/>" enctype="multipart/form-data" method="post">	
						<table id="myform">
							<tr>
								<td>
									Carregar Hiscon:
								</td>
								<td>
									<input type="file" name="zip" class="btn" />
								</td>
								<td>
									<input type="submit" class="btn" value="Carregar"/>	
								</td>
							</tr>
						</table>
					</form>	
				</c:if>

				
			<div class="container-fluid">
			<div class="row-fluid" style="margin-top: 1px;">
				<div class="span12" style="margin-top: 1px;">
	
					<div class="widget-box">
						<div class="widget-title"><span class="icon"><i class="icon-signal"></i></span><h5>Contratos</h5></div>
						<div class="widget-content">
							<table class="table table-bordered table-hover data-table" id="lista">
								<thead>
									<tr>
										<th>Imagem</th>
										<th>Data solicitação</th>
										<th>Data solicitação Adm</th>
										<th>Supervisor</th>
										<th>Consultor</th>
										<th>Cliente</th>
										<th>Nascimento</th>
										<th>Cpf</th>
										<th>Número Benefício</th>
										<th>Status Atual</th>
										<c:if test="${usuarioInfo.perfil.chave != 'Consultor'}">
											<th>Quantidade</th>
										</c:if>
									</tr>
								</thead>
								<tbody>	
									<c:forEach items="${hiscons}" var="hiscon">
										<tr <c:if test="${hiscon.countHiscons >= 3 }"> class="error"</c:if>>
											<td>
												<c:if test="${hiscon.isEnviado}">
													<a href="<c:url value="/visualizaHiscon/${hiscon.hisconBeneficio_id}"/>"><img src="../img/pdf.gif" border="0"/></a>
												</c:if>
											</td>
											<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm" type="date" value="${hiscon.created.time}" /></td>
											<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm" type="date" value="${hiscon.dataAdm.time}" /></td>
											<td>${hiscon.usuario.supervisorUsuario.apelido }</td>
											<td>${hiscon.usuario.apelido }</td>									
											<td><a data-toggle="modal" onclick="showcontatos(${hiscon.parceiroBeneficio.parceiroNegocio.parceiroNegocio_id});">${hiscon.parceiroBeneficio.parceiroNegocio.nome }</a></td>
											<td><fmt:formatDate pattern="dd/MM/yyyy" type="date" value="${hiscon.parceiroBeneficio.parceiroNegocio.dataNascimento.time }" /></td>
											<td>${hiscon.parceiroBeneficio.parceiroNegocio.cpf }</td>
											<td>${hiscon.parceiroBeneficio.numeroBeneficio }</td>
											<td>
												<select id="hisconBeneficioStatus" class="input-medium" onchange="return altera('etapa.etapa_id','${hiscon.hisconBeneficio_id}', this.value);" >
													<c:forEach var="etapa" items="${hiscon.etapas }">
														<option value="${etapa.etapa_id }" <c:if test="${etapa.etapa_id == hiscon.etapa.etapa_id}">selected</c:if>>${etapa.nome }</option>
													</c:forEach>
												</select>
											</td>
											<c:if test="${usuarioInfo.perfil.chave != 'Consultor' }">
												<td style="text-align: center;">
													<a href='<c:url value="/hisconbeneficio/detalhe/${hiscon.parceiroBeneficio.parceiroBeneficio_id}"/>'><h3><small>${hiscon.countHiscons }</small></h3></a>
												</td>
											</c:if>
										</tr>
			
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			</div>
			
			
							

			</div>						
		</div>
	</div>
	
	<div id="myModal" class="modal hide fade" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div> 

<%@ include file="/footer.jspf"%>
