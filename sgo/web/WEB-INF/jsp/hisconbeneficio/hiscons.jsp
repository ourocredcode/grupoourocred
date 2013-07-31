<%@ include file="/header.jspf"%>

	<script type="text/javascript">

	$(document).ready(function() { 
		
		$("#busca_DataInicio").mask("99/99/9999");
		$("#busca_DataFim").mask("99/99/9999");
		
		$('#busca_DataInicio').datepicker({
			dateFormat: 'dd/mm/yy'
		});
		$('#busca_DataFim').datepicker({
			dateFormat: 'dd/mm/yy'
		});

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
		
		$("#busca_Supervisor").change(function() {   
			
			var supervisor_id = $("#busca_Supervisor").val();

			if(supervisor_id != '')
				$("#busca_Consultor").load('<c:url value="/hisconbeneficio/consultores" />', {'supervisor_id': supervisor_id});
			else
				$('#busca_Consultor option').remove();

		});
		
		$('#loading').ajaxStart(function() {
			 $(this).show();
			 $('#resultado').hide();
			 }).ajaxStop(function() {
			 $(this).hide();
			 $('#resultado').fadeIn('fast');
		});
		
		
		var status = $("#busca_Etapas").val();
		var posicao = $("#busca_Posicoes").val();
		var cliente = $("#busca_Cliente").val();
		var documento = $("#busca_Documento").val();
		var dataInicio = $("#busca_DataInicio").val();
		var dataFim = $("#busca_DataFim").val();

		if(dataInicio != '' || dataFim != '' || cliente != '' || documento != '' || status != '' || posicao != '' ){
			$(buscaHiscons);
		}
		
		
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
	
	function buscaHiscons(){

		var status = $("#busca_Etapas").val();
		var posicao = $("#busca_Posicoes").val();
		var cliente = $("#busca_Cliente").val();
		var documento = $("#busca_Documento").val();
		var dataInicio = $("#busca_DataInicio").val();
		var dataFim = $("#busca_DataFim").val();
		var consultor = $("#busca_Consultor").val();
		var supervisor = $("#busca_Supervisor").val();

		if(dataInicio == undefined || dataInicio == '__/__/____' ){
			dataInicio = '';
		};

		if(dataFim == undefined || dataFim == '__/__/____'){
			dataFim = '';
		};

		if(status == null){
			status = new Array();
			status[0] = "";
		};

		if(posicao == null){
			posicao = new Array();
			posicao[0] = "";
		};

		if(consultor == undefined)
			consultor = "";

		if(supervisor == undefined)
			supervisor = "";

		if(consultor == "")
			consultor = supervisor;

		if(dataInicio != '' || dataFim != '' || cliente != '' || documento != ''){
			$("#resultado").load('<c:url value="/hisconbeneficio/lista" />',
					{'status': status,'posicao': posicao,'cliente': cliente, 'documento' :documento, 'dataInicio' :dataInicio, 'dataFim' :dataFim, 'consultor' : consultor});
		};

	}

	 </script>

	<div id="content-header">
		<h1>Busca Hiscons</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users" href="<c:url value="/funcionario/equipe/${usuarioInfo.usuario.usuario_id }"/>"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
	</div>

	<div id="breadcrumb">
		<a href="<c:url value="/menu/inicio/${usuarioInfo.perfil.chave}" />" title="Dashboard" class="tip-bottom"><i class="icon-home"></i> Dashboard</a>
		<a href="#" class="current">Hiscons</a>
	</div>
	
	

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
			<div class="widget-box">
			<div class="widget-title"><span class="icon">
				<i class="icon-signal"></i></span><h5>Filtros</h5>
				<div class="buttons"><a href="javascript:buscaHiscons();" class="btn btn-mini"><i class="icon-refresh"></i> Busca</a></div>
			</div>
			
			<input type="hidden" id="busca_Tipo" name="busca_Tipo" value="${tipobusca }"/>
			
			
			<div class="widget-content" style="padding: 8px;">
				<div class="row-fluid">
				
					<div class="span2">
						
						<label for="busca_Etapas">Etapas</label>
	  					<select id="busca_Etapas" name="busca_Etapas" class="input-medium" MULTIPLE >
							<option value="">Todos...</option>
							<c:forEach items="${etapas }" var="etapa">
								<option value="${etapa.nome }">${etapa.nome }</option>
							</c:forEach>
						</select>
						
						<label for="busca_Posicoes">Posições</label>
	  					<select id="busca_Posicoes" name="busca_Posicoes" class="input-medium" MULTIPLE >
							<option value="">Todos...</option>
							<c:forEach items="${posicoes }" var="posicao">
								<option value="${posicao.nome }">${posicao.nome }</option>
							</c:forEach>
						</select>

					</div>

	 				<div class="span3">
	
						<label for="busca_Cliente">Cliente</label>
						<input id="busca_Cliente" name="busca_Cliente" class="input-large" type="text" />
		
						<label for="busca_Documento">Número Documento (Benefício/Cpf):</label>
						<input id="busca_Documento" name="busca_Documento" class="input-large" type="text" />

					</div>
					
					<div class="span2">

							<label for="busca_DataInicio">Data Início</label>
							<input id="busca_DataInicio" name="busca_DataInicio"  class="input-small" type="text" />

							<c:if test="${usuarioInfo.perfil.chave == 'Administrativo' || usuarioInfo.perfil.chave == 'Gestor'}">
								<label for="busca_Supervisor">Supervisor</label>
								<select id="busca_Supervisor" name="busca_Supervisor" class="input-medium">
									<option value="">Todos</option>
									<c:forEach items="${supervisores}" var="supervisor">
										<option value="${supervisor.usuario_id}">${supervisor.nome }</option>
									</c:forEach>
								</select>	
							</c:if>

							<c:if test="${usuarioInfo.perfil.chave == 'Supervisor'}">
								<label for="busca_Consultor">Consultor</label>
								<select id="busca_Consultor" name="busca_Consultor" class="input-medium">
									<option value="">Selecione um Consultor</option>
									<c:forEach var="consultor" items="${consultores }">
										<option value="${consultor.usuario_id }">${consultor.nome }</option>
									</c:forEach>
								</select>
							</c:if>

					</div>

					<div class="span2">

							<label for="busca_DataFim">Data Fim</label>
							<input id="busca_DataFim" name="busca_DataFim" class="input-small" type="text"  />

							<c:if test="${usuarioInfo.perfil.chave == 'Administrativo' || usuarioInfo.perfil.chave == 'Gestor'}">
								<label for="busca_Consultor">Consultor</label>
								<select id="busca_Consultor" name="busca_Consultor" class="input-medium">
									<option value="">Selecione um Supervisor</option>
								</select>
							</c:if>
							
					
					</div>
				
				</div>
			</div>
						

			<!-- FIM -->
		</div>
	</div>
	</div>
	</div>
	
	<div id="loading" style="display:none;color:#1b5790; font-weight:bold;float:left;clear: both;margin-left: 600px;">CARREGANDO...</div>

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">

				<div class="widget-box">
					<div class="widget-title"><span class="icon"><i class="icon-signal"></i></span><h5>HISCONS</h5></div>
					<div id="resultado" class="widget-content"></div>
				</div>
			</div>
		</div>
	</div>	


<%@ include file="/footer.jspf"%>
