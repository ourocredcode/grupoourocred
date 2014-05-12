<%@ include file="/header.jspf"%>

	<script type="text/javascript">

	 $(document).ready(function() {

		$('select').select2();
		 
		$("#busca_DataDigitacaoInicio").mask("99/99/9999");
		$("#busca_DataDigitacaoFim").mask("99/99/9999");
		
		$('#busca_DataDigitacaoInicio').datepicker({
			dateFormat: 'dd/mm/yy'
		});
		$('#busca_DataDigitacaoFim').datepicker({
			dateFormat: 'dd/mm/yy'
		});

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
		var digitacaoInicio = $("#busca_DataDigitacaoInicio").val();
		var digitacaoFim = $("#busca_DataDigitacaoFim").val();
		var empresas = $("#busca_Empresa").val();
		var bancos = $("#busca_Banco").val();

		if(digitacaoInicio == undefined || digitacaoInicio == '__/__/____')
			digitacaoInicio = "";
		if(digitacaoFim == undefined || digitacaoFim == '__/__/____')
			digitacaoFim = "";
		
		if(empresas == null){
			empresas = new Array();
			empresas[0] = "";
		}
		
		if(bancos == null){
			bancos = new Array();
			bancos[0] = "";
		}

		if(propostaBanco == '' && contratoBanco == '' && digitacaoInicio == '' && digitacaoFim == ''){
			alert('Pesquise por um dos campos acima.');
			$("#busca_PropostaBanco").focus();
		} else {
			$("#resultado").load('<c:url value="/menu/contrato/propostaContrato" />',{'propostaBanco': propostaBanco,'contratoBanco': contratoBanco, 
				'digitacaoInicio':digitacaoInicio , 'digitacaoFim':digitacaoFim , 'empresas':empresas ,'bancos' : bancos});	
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
							<div class="row-fluid">
								
								<label for="busca_Banco">Banco</label>
								<select id="busca_Banco" name="busca_Banco"  class="input-medium" MULTIPLE>
									<option value="">Selecione um banco</option>
									<c:forEach items="${bancos }" var="banco">
										<option value="${banco.banco_id }">${banco.nome }</option>
									</c:forEach>
								</select>
							
							</div>
							<div class="row-fluid">
							
								<label for="busca_Empresa">Empresa</label>
								<select id="busca_Empresa" name="busca_Empresa" class="input-medium" MULTIPLE>
									<option value="">Todos</option>
									<option value="5">ATGGOLD</option>
									<option value="3">GOCX</option>
									<option value="4">GRGOLD</option>
									<option value="2">OUROCRED MATRIZ</option>
									<option value="6">RIBEIRAO</option>
									<option value="7">RJ</option>
									<option value="8">USECRED</option>
								</select>
							
							</div>

						</div>

						<div class="span3">	

							<div class="row-fluid">

								<div class="span5">								
									<label for="busca_DataDigitacaoInicio">Dt Digitação Início</label>
									<input id="busca_DataDigitacaoInicio" name="busca_DataDigitacaoInicio"  class="input-small" type="text" />
								</div>
								<div class="span5">
									<label for="busca_DataDigitacaoFim">Dt Digitação Fim</label>
									<input id="busca_DataDigitacaoFim" name="busca_DataDigitacaoFim" class="input-small" type="text"  />
								</div>

							</div>
							
							<div class="row-fluid">
							
								<div class="span5">	
									<label for="busca_Contrato">Contrato</label>
									<input type="text" id="busca_ContratoBanco" name="busca_ContratoBanco" class="input-small" />
								</div>

								<div class="span5">	
									<label for="busca_Proposta">Proposta</label>
									<input type="text" id="busca_PropostaBanco" name="busca_PropostaBanco"  class="input-small" />
								</div>
							
							</div>

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
