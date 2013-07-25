<%@ include file="/header.jspf"%>

	<script type="text/javascript">
	
	 $(document).ready(function() {

		 $('#loading').ajaxStart(function() {
			 $(this).show();
			 $('#resultado').hide();
			 }).ajaxStop(function() {
			 $(this).hide();
			 $('#resultado').fadeIn('fast');
		});

	 });

	function buscaPropostaContrato(){
		
		var propostaBanco = $("#busca_PropostaBanco").val();
		var contratoBanco = $("#busca_ContratoBanco").val();
	
		if(propostaBanco == '' && contratoBanco == ''){
			alert('Pesquise por um dos campos acima.');
			$("#busca_PropostaBanco").focus();
		} else {
			$("#resultado").load('<c:url value="/menu/contrato/propostaContrato" />',{'propostaBanco': propostaBanco,'contratoBanco': contratoBanco });	
		}
	
	}

	 function limpa(){
		 window.location.href = '<c:url value="/menu/contratos/proposta" />';
	 }
	</script>

	<div id="content-header">
		<h1>Busca Contratos</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users" href="<c:url value="/funcionario/equipe/${usuarioInfo.usuario.usuario_id }"/>"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
	</div>

	<div id="breadcrumb">
		<a href="<c:url value="/menu/inicio/${usuarioInfo.perfil.chave}" />" title="Dashboard" class="tip-bottom"><i class="icon-home"></i> Dashboard</a>
		<a href="#" class="current">Contratos</a>
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
							<label for="busca_Proposta">Proposta</label>
							<input type="text" id="busca_PropostaBanco" name="busca_PropostaBanco"  class="input-medium" />

						</div>
						<div class="span2">
							<label for="busca_Contrato">Contrato</label>
							<input type="text" id="busca_ContratoBanco" name="busca_ContratoBanco" class="input-medium" />
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
