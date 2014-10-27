<%@ include file="/header.jspf"%>

	<script type="text/javascript">

	 $(document).ready(function() {

		$('select').select2();
		 
		$("#busca_DataAssinaturaInicio").mask("99/99/9999");
		$("#busca_DataAssinaturaFim").mask("99/99/9999");
		
		$('#busca_DataAssinaturaInicio').datepicker({
			dateFormat: 'dd/mm/yy'
		});
		$('#busca_DataAssinaturaFim').datepicker({
			dateFormat: 'dd/mm/yy'
		});

		 $('#loading').ajaxStart(function() {
			 $(this).show();
			 $('#resultado').hide();
			 }).ajaxStop(function() {
			 $(this).hide();
			 $('#resultado').fadeIn('fast');
		});
		 
		 $("#supervisor_id").change(function() {   
				
			var supervisor_id = $("#supervisor_id").val();
		
			if(supervisor_id != '')
				$("#consultor_id").load('<c:url value="/menu/consultores" />', {'supervisor_id': supervisor_id});
			else
				$('#consultor_id option').remove();
		
		});

	 });

	function buscaPropostaContrato(){

		var assinaturaInicio = $("#busca_DataAssinaturaInicio").val();
		var assinaturaFim = $("#busca_DataAssinaturaFim").val();
		var bancos = $("#busca_Banco").val();
		var status = $("#busca_Status").val();
		var consultor = $("#busca_Consultor").val();
		var supervisor = $("#busca_Supervisor").val();
		var tipoLogistica = $("#busca_TipoLogistica").val();
		var periodo = $("#busca_Periodo").val();
		var regiao = $("#busca_Regiao").val();

		if(assinaturaInicio == undefined || assinaturaInicio == '__/__/____')
			assinaturaInicio = "";
		if(assinaturaFim == undefined || assinaturaFim == '__/__/____')
			assinaturaFim = "";
		if(supervisor == undefined)
			supervisor = "";
		if(consultor == undefined)
			consultor = "";

		if(bancos == null){
			bancos = new Array();
			bancos[0] = "";
		}
		
		if(status == null){
			status = new Array();
			status[0] = "";
		}

		var isSupervisorApoio = consultor == supervisor ? true : false;

		if(consultor == ""){
			if(supervisor == ""){
				consultor = "";
			} else {
				consultor = supervisor;
			}
		}

		if(assinaturaInicio == '' && assinaturaFim == ''){
			alert('Pesquise por um dos campos acima.');
			$("#busca_DataAssinaturaInicio").focus();
		} else {
			$("#resultado").load('<c:url value="/menu/contrato/logistica" />',{'assinaturaInicio':assinaturaInicio , 
				'assinaturaFim':assinaturaFim ,'bancos' : bancos,'status': status, 'consultor' : consultor , 'isSupervisorApoio' : isSupervisorApoio, 
				'tipoLogistica':tipoLogistica,'periodo':periodo, 'regiao':regiao});	
		}
	
	}

	 function limpa(){
		 window.location.href = '<c:url value="/menu/contratos/logistica" />';
	 }
	</script>

	<div id="content-header">
		<h1>Relatório Logística</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users" href="<c:url value="/funcionario/equipe/${usuarioInfo.usuario.usuario_id }"/>"><i class="icon-user"></i></a>
		</div>
	</div>

	<div id="breadcrumb">
		<a href="<c:url value="/menu/inicio/${usuarioInfo.perfil.chave}" />" title="Dashboard" class="tip-bottom"><i class="icon-home"></i> Dashboard</a>
		<a href="#" class="current">Relatório Logística</a>
	</div>

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-title"><span class="icon">
						<i class="icon-signal"></i></span><h5>Filtros</h5>
						<div class="buttons"><a href="javascript:buscaPropostaContrato();" class="btn btn-mini"><i class="icon-search"></i> Busca</a></div>
						<div class="buttons"><a href="javascript:limpa();" class="btn btn-mini"><i class="icon-trash"></i> Limpa</a></div>
					</div>
	
					<div class="widget-content" style="padding: 6px;">
						<div class="row-fluid">
						
							<div class="span2">
	
								
									
									<label for="busca_Status">Status Contrato</label>
				  					<select id="busca_Status" name="busca_Status" class="input-medium" MULTIPLE >
										<option value="">Todos...</option>
										<c:forEach items="${etapas }" var="etapa">
											<option value="${etapa.nome }">${etapa.nome }</option>
										</c:forEach>
									</select>
									
									<label for="busca_Banco">Banco Contrato</label>
									<select id="busca_Banco" name="busca_Banco"  class="input-medium" MULTIPLE>
										<option value="">Selecione um banco</option>
										<c:forEach items="${bancos }" var="banco">
											<option value="${banco.banco_id }">${banco.nome }</option>
										</c:forEach>
									</select>
								
								
	
							</div>
							
							<div class="span2">								
								
								<label for="busca_Supervisor">Supervisor</label>
								<select id="busca_Supervisor" name="busca_Supervisor" class="input-medium">
									<option value="">Todos</option>
									<c:forEach items="${supervisores}" var="supervisor">
										<option value="${supervisor.usuario_id}">${supervisor.apelido}</option>
									</c:forEach>
								</select>
								
								<label for="busca_Consultor">Consultor</label>
								<select id="busca_Consultor" name="busca_Consultor" class="input-medium">
	
									<option value="">Selecione um Supervisor</option>
	
								</select>
								
							</div>
	
							<div class="span3">	
	
								<div class="span5">								
									<label for="busca_DataAssinaturaInicio">Dt Assinatura Início</label>
									<input id="busca_DataAssinaturaInicio" name="busca_DataAssinaturaInicio"  class="input-small" type="text" />
								</div>
								<div class="span5">
									<label for="busca_DataAssinaturaFim">Dt Assinatura Fim</label>
									<input id="busca_DataAssinaturaFim" name="busca_DataAssinaturaFim" class="input-small" type="text"  />
								</div>
	
							</div>
							
							<div class="span2">	
							
								<label class="control-label">Tipo Logística</label>
								<select  id="busca_TipoLogistica" name="busca_TipoLogistica" class="input-small">
									<option value=""></option>
									<c:forEach items="${tiposLogistica }" var="tipoLogistica">
										<option value="${tipoLogistica.tipoLogistica_id }">${tipoLogistica.nome }</option>
									</c:forEach>
								</select>
								
								<label class="control-label">Período</label>
								<select  id="busca_Periodo" name="busca_Periodo" class="input-small">
									<option value=""></option>
									<c:forEach items="${periodos }" var="periodo">
										<option value="${periodo.periodo_id }">${periodo.nome }</option>
									</c:forEach>
								</select>							
								
								
							</div>
							
							<div class="span2">	
							
								<label class="control-label">UF</label>
								<select  id="busca_Regiao" name="busca_Regiao" class="input-small">
									<option value=""></option>
									<c:forEach items="${regioes }" var="regiao">
										<option value="${regiao.regiao_id }">${regiao.nome }</option>
									</c:forEach>
								</select>
	
							</div>
	
						</div>
					</div>	

				</div>
			</div>
		</div>
	</div>
	
	<div id="loading" style="display:none;color:#1b5790; font-weight:bold;float:left;clear: both;margin-left: 600px;margin-top: 1px;">CARREGANDO...</div>

	<div class="container-fluid">
		<div class="row-fluid" style="margin-top: 1px;">
			<div class="span12" style="margin-top: 1px;">

				<div class="widget-box">
					<div class="widget-title"><span class="icon"><i class="icon-signal"></i></span><h5>Contratos</h5></div>
					<div id="resultado" class="widget-content">
												
					</div>
				</div>
			</div>
		</div>
	</div>	


<%@ include file="/footer.jspf"%>
