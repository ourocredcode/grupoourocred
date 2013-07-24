<%@ include file="/header.jspf"%>

<link rel="stylesheet" href="<c:url value="/css/select2.css"/>" />
<script type="text/javascript" src="<c:url value="/js/select2.js"/>"></script>

<script type="text/javascript">
jQuery(function($){

	$("#localidadeCep").mask("99999999");

	$('#organizacao-li-a').click(function() {
		window.location.href = '<c:url value="/organizacao/cadastro" />';
	});

	 $("#localidadeCep").change(function(){
		var enderecoCEP = $("#localidadeCep").val();
		$.ajax({
	           type: "POST",
	           url: "/sgo/localidade/busca.localidade",
	           data: "enderecoCEP=" + enderecoCEP,
	           beforeSend: function() {
	             $('#alertCEP').html('');
	           },
	           success: function(txt) {
	              if(txt!='ERRO'){
	            	  $('#ajax_endereco').html(txt);
	            	  $('#alertCEP').html('');
	              }else{
	                  $('#alertCEP').html('');
	              }
	           },
	           error: function(txt) {
	             alert('Houve um problema interno. tente novamente mais tarde.');
	           }
	       });
	});

	$('.data-table').dataTable({
		"bJQueryUI": true,
		"sPaginationType": "full_numbers",
		"sDom": '<""l>t<"F"fp>'
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

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/organizacao/cadastro" />';
	});

	$('#btnNovo').click(function() {
		limpaForm();
	});

	$("#isActive").change(function(e){
		if(document.organizacaoForm.isActive.checked==true){
			document.organizacaoForm.isActive.value=true;
		}else{
			document.organizacaoForm.isActive.value=false;
		}
	});
	
	 $('#loading').ajaxStart(function() {
		 $(this).show();
		 $('#resultado').hide();
		 }).ajaxStop(function() {
		 $(this).hide();
		 $('#resultado').fadeIn('fast');
	});

});

function limpaForm() {
	if (!(navigator.userAgent.indexOf("Firefox") != -1)) {
		document.organizacaoForm.reset();
	}
}

function altera(linha, id) {

	var valor = linha.checked == true ? true : false ;

	if (window.confirm("Deseja alterar a Organização selecionado?"))
		$.post('<c:url value="/organizacao/altera" />', {
			'organizacao.organizacao_id' : id, 'organizacao.isActive' : valor
		});

	return false;
}

function salvaLocalidade() {
	
	var cep = $("#localidadeCep").val();
	var endereco = $("#localidadeEndereco").val();
	var numero = $("#parceirolocalidadeNumero").val();
	var complemento = $("#parceirolocalidadeComplemento").val();
	var pontoreferencia = $("#parceirolocalidadePontoReferencia").val();
	var bairro = $("#localidadeBairro").val();
	var tipoLocalidadeId = $("#localidadeTipoLocalidadeId").val();
	var cidadeId = $("#localidadeCidadeId").val();
	var regiaoId = $("#localidadeRegiaoId").val();
	var parceiroId = $("#parceiroNegocioId").val();
	var localidadeId = $("#localidadeId").val();
	var paisId = $("#localidadePaisId").val();

	if (window.confirm("Deseja salvar o endereço?"))
		$.post('<c:url value='/parceironegocio/salvaLocalidade' />',{
				'localidade.cep' : cep ,
				'localidade.endereco' : endereco,
				'localidade.bairro' : bairro,
				'parceiroLocalidade.complemento' : complemento,
				'parceiroLocalidade.pontoReferencia' : pontoreferencia,
				'parceiroLocalidade.numero' : numero,
				'localidade.cidade.cidade_id' : cidadeId,
				'localidade.regiao.regiao_id' : regiaoId,
				'localidade.pais.pais_id' : paisId,
				'localidade.tipoLocalidade.tipoLocalidade_id' : tipoLocalidadeId,
				'localidade.localidade_id' : localidadeId,
				'parceiroLocalidade.parceiroNegocio.parceiroNegocio_id': parceiroId}
		, function(resposta) { 
			if(resposta.indexOf("Erro") != -1){
				alert(resposta);
			} else {
				$('#enderecos').html(resposta);	
			};
		} );

	return false;

}

function mostraEndereco() {
	
	 $("#ajax_endereco").css("display", "block");

	 var mudar = document.getElementById('bttLocalidade');
	 mudar.setAttribute('onclick', 'salvaLocalidade()');
	
}

function cancelaEndereco() {
	
	 $("#ajax_endereco").css("display", "none");

	 var mudar = document.getElementById('bttLocalidade');
	 mudar.setAttribute('onclick', 'mostraEndereco()');
	
}
</script>

<div id="content-header">
	<h1>Cadastro Organização</h1>
	<div class="btn-group">
		<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
		<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
	</div>
</div>

<div id="breadcrumb">
	<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Cadastro</a>
	<a href="#" class="current">Organização</a>
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
<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">

			<ul id="myTab" class="nav nav-tabs">
				<li class="active" id="organizacao-li"><a href="#organizacao-div" data-toggle="tab" id="organizacao-li-a">Organização</a></li>				
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="organizacao-div">

					<form id="organizacaoForm" name="organizacaoForm" action="<c:url value="/organizacao/salva"/>" method="POST">
						<div class="row-fluid">
							<div class="span1">
								<label for="empresa">Empresa</label>
	      						<input class="input-xlarge" id="empresa" name="organizacao.empresa.nome" type="text" value="${usuarioInfo.empresa.nome }" readonly="readonly">
	      						<input class="span1" id="empresaId" name="organizacao.empresa.empresa_id" type="hidden" value="${usuarioInfo.empresa.empresa_id }" >
							</div>
						</div>
						<div class="row-fluid">
							<div class="span3">
								<label for="nome">Nome</label>
								<input class="input-xlarge" id="nome" name="organizacao.nome" placeholder="Nome" type="text" required>
							</div>
							<div class="span3">
								<label for="nomefantasia">Nome Fantasia</label>
								<input class="input-xlarge" id="nomefantasia" name="organizacaoInfo.nomefantasia" placeholder="Nome Fantasia" type="text" required>
							</div>
						</div>

						<div class="widget-box collapsible">
							<div class="widget-title">
								<a href="#collapseOne" data-toggle="collapse">
									<span class="icon"><i class="icon-arrow-right"></i></span>
									<h5>Informações da Organização</h5>
								</a>
							</div>
							<div class="collapse in" id="collapseOne">
								<div class="widget-content">								
									<div class="row-fluid">
										<div class="span2">
											<label for="cnpj">CNPJ</label>
											<input class="input-medium" id="cnpj" name="organizacaoInfo.cnpj" placeholder="cnpj" type="text" required>
										</div>
										<div class="span2">
											<label for="ie">Inscrição Estadual</label>
											<input class="input-medium" id="ie" name="organizacaoInfo.ie" placeholder="Inscrição Estadual" type="text" required>
										</div>
										<div class="span2">
											<label for="tipoOrganizacaoId">Tipo de Organização</label>
											<select  id="tipoOrganizacaoId" name="organizacaoInfo.tipoOrganizacao.tipoOrganizacao_id" class="input-medium">
												<option value="">Selecione o Tipo organização</option>
												<c:forEach items="${tiposOrganizacao }" var="tipoOrganizacao">
													<option value="${tipoOrganizacao.tipoOrganizacao_id }"> ${tipoOrganizacao.nome }</option>
												</c:forEach>
											</select>
										</div>
										<div class="span2">
											<label for="organizacaoPaiId">Organização Pai</label>
											<select  id="organizacaoPaiId" name="organizacaoInfo.organizacaoPai.organizacao_id" class="input-medium">
												<option value="">Selecione a organização</option>
												<c:forEach items="${organizacoes }" var="organizacao">
													<option value="${organizacao.organizacao_id }"> ${organizacao.nome }</option>
												</c:forEach>
											</select>
										</div>
										<div class="span2">
											<label for="supervisorOrganizacaoId">Supervisor Organização</label>
											<select  id="supervisorOrganizacaoId" name="organizacaoInfo.supervisorOrganizacao.funcionario_id" class="input-xlarge">
												<option value="">Selecione o supervisor</option>
												<c:forEach items="${funcionarios }" var="funcionario">
													<option value="${funcionario.funcionario_id }"> ${funcionario.nome }</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span3">
											<label for="contato">Contato</label>
											<input class="input-xlarge" id="email" name="organizacaoInfo.contato" placeholder="Contato" type="text" required>
										</div>
										<div class="span2">
											<label for="email">Email</label>
											<input class="input-xlarge" id="email" name="organizacaoInfo.email" placeholder="E-mail" type="text" required>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span1">
											<label for="dddfone1">DDD</label>
											<input class="input-mini" id="dddfone1" name="organizacaoInfo.dddfone1" placeholder="ddd" type="text" required>											
										</div>
										<div class="span2">
											<label for="telefone1">Telefone1</label>
											<input class="input-medium" id="telefone1" name="organizacaoInfo.telefone1" placeholder="telefone1" type="text" required>
										</div>									
										<div class="span1">
											<label for="dddfone2">DDD</label>
											<input class="input-mini" id="dddfone2" name="organizacaoInfo.dddfone2" placeholder="ddd" type="text" required>
										</div>
										<div class="span2">
											<label for="telefone2">Telefone2</label>
											<input class="input-medium" id="telefone2" name="organizacaoInfo.telefone2" placeholder="telefone2" type="text" required>
										</div>									
										<div class="span1">
											<label for="dddfax">DDD</label>
											<input class="input-mini" id="dddfax" name="organizacaoInfo.dddfax" placeholder="ddd" type="text" required>
										</div>
										<div class="span2">
											<label for="fax">Fax</label>
											<input class="input-medium" id="fax" name="organizacaoInfo.fax" placeholder="fax" type="text" required>
										</div>
										<div class="span1">
											<label for="isActive">Ativo</label>
											<input id="isActive" name="organizacao.isActive" type="checkbox" checked="checked" value="1" >
										</div>
									</div>
								</div>
							</div>
						</div>
						
						
						
						
						<c:if test="${not empty parceiroLocalidades}">
							<div class="navbar" style="clear: both;width: 900px;">
				
								<div class="navbar-inner"  >
									<div class="container">
				
										<div class="page-header">
											<h2><small>Endereços</small></h2>
										</div>
				
										<div id="enderecos" style="margin-top: 15px;">
											<table class="table table-striped table-bordered" id="lista">
												<thead>
													<tr>
														<th>Cep</th>
														<th>Bairro</th>
														<th>Cidade</th>
														<th>Tipo</th>
														<th>Endereço</th>
														<th>Número</th>
														<th>Complemento</th>
														<th>Tipo</th>
														<th>Excluir</th>
													</tr>
												</thead>
												<tbody>	
													<c:forEach items="${parceiroLocalidades}" var="parceiroLocalidade">
														<tr>
															<td>${localidade.cep }</td>
															<td>${parceiroLocalidade.localidade.bairro }</td>
															<td>${parceiroLocalidade.localidade.cidade.nome }</td>
															<td>${parceiroLocalidade.localidade.tipoLocalidade.nome }</td>
															<td>${parceiroLocalidade.localidade.endereco }</td>
															<td><input type="text" id="parceiroLocalidadeNumeroLista" value="${parceiroLocalidade.numero }" class="input-mini" onChange="return altera(this,'numero','${parceiroLocalidade.parceiroLocalidade_id}', this.value);"/></td>
															<td><input type="text" id="parceiroLocalidadeComplementoLista" value="${parceiroLocalidade.complemento }" class="input-mini" onChange="return altera(this,'complemento','${parceiroLocalidade.parceiroLocalidade_id}', this.value);"/></td>
															<td>
															<select id="parceiroLocalidadeTipoEnderecoLista" onChange="return altera(this,'tipoEndereco','${parceiroLocalidade.parceiroLocalidade_id}', this.value);" class="input-small">
																<option value="0" selected="selected">Selecione</option>
																	<c:forEach var="tipoEndereco" items="${tiposEndereco}">
																		<option value="${tipoEndereco.tipoEndereco_id}" <c:if test="${parceiroLocalidade.tipoEndereco.tipoEndereco_id eq tipoEndereco.tipoEndereco_id}">SELECTED</c:if>>${tipoEndereco.nome}</option>
																	</c:forEach>
															</select>
															</td>
															<td style="text-align: center;">
																<button type="button" class="btn btn-danger btn-mini" onClick="return exclui(this,'${parceiroLocalidade.parceiroLocalidade_id}');">Excluir</button>
															</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
				
										</div>
									</div>
								</div>										
						</div>
					</c:if>
					
					
					
					
						
						
						
						
						
						<div class="navbar" style="clear: both;width: 920px;">
							<div class="navbar-inner">
								<div class="container">
								
								<div class="control-group"></div>
									<div class="page-header">
										<h2><small>Endereço</small></h2>
									</div>
								
								<div id="ajax_endereco">
				
									
									<div class="row-fluid">
										<div class="span3">
											<div class="input-append">
												<input class="span10" id="localidadeCep" name="localidade.cep" type="text" placeholder="Busca Cep" value="${localidade.cep }" />
												<span class="add-on"><i class="icon-search"></i></span>
											</div>
										</div>
										<div class="span2">
											<div id="loading" style="display:none;color:#1b5790; font-weight:bold;float:right;clear: both;margin-left: 600px;margin-top: 1px;">BUSCANDO...</div>
										</div>
									</div>
				
									<div class="row-fluid">
										<input class="span2" id="localidadeTipoLocalidade" name="localidade.tipoLocalidade.nome" type="text" placeholder="Tipo" value="${localidade.tipoLocalidade.nome }"/>
										<input class="span5" id="localidadeEndereco" name="localidade.endereco" type="text" placeholder="Endereço" value="${localidade.endereco }"/>
										<input class="span1" id="parceirolocalidadeNumero" name="parceiroLocalidade.numero" type="text" placeholder="Número" value="${parceiroLocalidade.numero }" />
										<input class="span2" id="localidadeBairro" name="localidade.bairro" type="text" placeholder="Bairro" value="${localidade.bairro }" />
										<input class="span1" id="localidadeRegiao" name="localidade.regiao" type="text" placeholder="UF"  value="${localidade.regiao.chave }"  />
										<input class="span2" id="localidadeCidade" name="localidade.cidade" type="text" placeholder="Cidade" value="${localidade.cidade.nome }" />
									</div>
				
									<div id="alertCEP" style="position:absolute; float: right;width: 250px;margin-left: 100px;margin-top: -27px;"><i></i></div>
				
									<div class="row-fluid">		
				
										<input class="span2" id="parceirolocalidadeComplemento" name="parceiroLocalidade.complemento" type="text" placeholder="Complemento" value="${parceiroLocalidade.complemento }" />
										<input class="span9" id="parceirolocalidadePontoReferencia" name="parceiroLocalidade.pontoReferencia" type="text" placeholder="Ponto de Referência" value="${parceiroLocalidade.pontoReferencia }" />
				
										<input  id="localidadeTipoLocalidadeId" name="localidade.tipoLocalidade.tipoLocalidade_id" type="hidden"  value="${localidade.tipoLocalidade.tipoLocalidade_id }" />
										<input  id="localidadeCidadeId" name="localidade.cidade.cidade_id" type="hidden"  value="${localidade.cidade.cidade_id }" />
										<input  id="localidadeRegiaoId" name="localidade.regiao.regiao_id" type="hidden" value="${localidade.regiao.regiao_id }" />
										<input  id="localidadePaisId" name="localidade.pais.pais_id" type="hidden"  value="${localidade.pais.pais_id }" />
								
									</div>
				
								</div>
								
								<c:if test="${not empty organizacao }">
									<div class="btn-toolbar" align="right">
										<div class="row-fluid">
											<div class="btn-group">
												<button type="button" class="btn btn-primary" id="bttLocalidade" onClick="mostraEndereco();">Novo</button>
											</div>	
											<div class="btn-group">
												<button type="button" class="btn" id="bttCancelar" onClick="cancelaEndereco();">Fechar</button>
											</div>
										</div>
									</div>
								</c:if>
							</div>
						</div>
					</div>
					
					
					
					
						
						
						
					 	<div class="btn-group">
							<button type="submit" class="btn btn-primary" id="btnSalvar">Salvar</button>
						</div>
						<div class="btn-group">
							<button type="button" class="btn btn-primary" id="btnNovo" >Novo</button>
						</div>
						<div class="btn-group">
							<button type="button" class="btn btn-primary" id="btnSair" >Sair</button>
						</div>						
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<div class="widget-box">
				<div class="widget-title">
					<span class="icon"><i class="icon-signal"></i> </span>
					<h5>Organizacao</h5>
				</div>
				<div id="resultado" class="widget-content">
					<c:if test="${not empty organizacoes}">
						<table
							class="table table-bordered table-striped table-hover data-table"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>Empresa</th>									
									<th>Nome</th>
									<th>Ativo</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${organizacoes }" var="organizacao">
									<tr>
										<td>${organizacao.empresa.nome }</td>										
										<td>${organizacao.nome }</td>
										<td>
											<label class="checkbox inline">
												<input type="checkbox" id="isActiveLine" name="organizacao.isActive"
												<c:if test="${organizacao.isActive == true }"> checked="checked"</c:if> onchange="altera(this,'${organizacao.organizacao_id}');">
											</label>
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

<%@ include file="/footer.jspf"%>