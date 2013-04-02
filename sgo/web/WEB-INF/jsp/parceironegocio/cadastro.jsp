<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

		$('#parceiroNegocioDataNascimento').datepicker();

	   $("#parceiroNegocioTipoParceiroId").change(function(evento){

		   var id = $("#parceiroNegocioTipoParceiroId").val();

		   if(id == 1){
			   $("#parceiroNegocioTipoPessoaFisica").css("display", "block");
		   } else {
			   $("#parceiroNegocioTipoPessoaFisica").css("display", "none");
		   }
		   
		   if(id == 2){
			   $("#parceiroNegocioTipoPessoaJuridica").css("display", "block");
		   } else {
			   $("#parceiroNegocioTipoPessoaJuridica").css("display", "none");
		   }

	   });
	   
	   $("#parceiroNegocioIsFuncionario").click(function(evento){

		   if ($('#parceiroNegocioIsFuncionario').is(':checked')){
		         $("#parceiroNegocioFuncionario").css("display", "block");
		      }else{
		         $("#parceiroNegocioFuncionario").css("display", "none");
		      }
		   
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
	   
	   $('#bttNovo').click(function() {
			window.location.href = '<c:url value="/parceironegocio/cadastro" />';
		});
});

function limpaForm(){
	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.usuarioForm.reset();
	}	
}

function salvaLocalidade() {

	var cep = $("#localidadeCep").val();
	var endereco = $("#localidadeEndereco").val();
	var numero = $("#parceirolocalidadeNumero").val();
	var complemento = $("#parceirolocalidadeComplemento").val();
	var pontoreferencia = $("#parceirolocalidadePontoReferencia").val();
	var bairro = $("#localidadeBairro").val();
	var cidadeId = $("#localidadeCidadeId").val();
	var regiaoId = $("#localidadeRegiaoId").val();
	var parceiroId = $("#parceiroNegocioId").val();
	var localidadeId = $("#localidadeId").val();
	var paisId = $("#localidadePaisId").val();

	if (window.confirm("Deseja salvar o endere�o?"))
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

function salvaContato() {
	
	var parceiroId = $("#parceiroNegocioId").val();
	var parceiroContatoTipoContatoId = $("#parceiroContatoTipoContatoNovo").val();
	var parceiroContatoNome = $("#parceiroContatoNomeNovo").val();

	if (window.confirm("Deseja salvar o endere�o?"))
		$.post('<c:url value='/parceironegocio/salvaContato' />',{
				'parceiroContato.tipoContato.tipoContato_id' : parceiroContatoTipoContatoId ,
				'parceiroContato.nome' : parceiroContatoNome,
				'parceiroContato.parceiroNegocio.parceiroNegocio_id ' : parceiroId}
		, function(resposta) { 
			if(resposta.indexOf("Erro") != -1){
				alert(resposta);
			} else {
				$('#parceiroContatosDiv').html(resposta);	
			};
		} );

	return false;

}

function salvaBeneficio() {
	
	var parceiroId = $("#parceiroNegocioId").val();
	var parceiroBeneficioNumero = $("#parceiroBeneficioNovo").val();

	if (window.confirm("Deseja salvar o n�mero do benef�cio?"))
		$.post('<c:url value='/parceironegocio/salvaBeneficio' />',{
				'parceiroBeneficio.numeroBeneficio' : parceiroBeneficioNumero,
				'parceiroContato.parceiroNegocio.parceiroNegocio_id ' : parceiroId}
		, function(resposta) { 
			if(resposta.indexOf("Erro") != -1){
				alert(resposta);
			} else {
				$('#parceiroBeneficiosDiv').html(resposta);	
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

function exclui(linha, id) {

	if (window.confirm("Deseja realmente excluir a Localidade do Parceiro ?"))
		$.post('<c:url value='/parceironegocio/excluiLocalidade' />'
		, {'parceiroLocalidade.parceiroLocalidade_id' : id}
		, function(resposta) { alert(resposta); excluiLinha(linha, resposta); });

	return false;
}

function excluiContato(linha, id) {

	if (window.confirm("Deseja realmente excluir o Contato do Parceiro ?"))
		$.post('<c:url value='/parceironegocio/excluiContato' />'
		, {'parceiroContato.parceiroContato_id' : id}
		, function(resposta) { alert(resposta); excluiLinha(linha, resposta); });

	return false;
}

function excluiLinha(linha, resposta) {

	var objTR = linha.parentNode.parentNode;
	var objTable = objTR.parentNode;

	objTable.deleteRow(objTR.rowIndex - 1);

	return false;
}

function altera(linha, atributo,parceiroLocalidade_id,valor) {

	if(atributo == 'numero'){
		var attr = {'parceiroLocalidade.parceiroLocalidade_id' : parceiroLocalidade_id ,
				 'parceiroLocalidade.numero' : valor };
	}
	
	if(atributo == 'complemento'){
		var attr = {'parceiroLocalidade.parceiroLocalidade_id' : parceiroLocalidade_id ,
				 'parceiroLocalidade.complemento ' : valor };
	}

	if(atributo == 'tipoEndereco'){
		var attr = {'parceiroLocalidade.parceiroLocalidade_id' : parceiroLocalidade_id ,
				 'parceiroLocalidade.tipoEndereco.tipoEndereco_id' : valor} ;
	}

	if (window.confirm("Deseja realmente alterar o atributo do Parceiro Localidade?"))
		$.post('<c:url value='/parceironegocio/alteraParceiroLocalidade' />'
		, attr , function(resposta) { 

				if(resposta.indexOf("Erro") != -1){
					alert(resposta);
					window.location.reload();
				} else {
					alert(resposta);	
				};
				
		});

	return false;
}

function alteraContato(linha, atributo,parceiroContato_id,valor) {

	if(atributo == 'nome'){
		var attr = {'parceiroContato.parceiroContato_id' : parceiroContato_id ,
				 'parceiroContato.nome' : valor };
	}

	if(atributo == 'tipoContato'){
		var attr = {'parceiroContato.parceiroContato_id' : parceiroContato_id ,
				 'parceiroContato.tipoContato.tipoContato_id' : valor} ;
	}

	if (window.confirm("Deseja realmente alterar o atributo do Contato Localidade?"))
		$.post('<c:url value='/parceironegocio/alteraParceiroContato' />'
		, attr , function(resposta) { 

				if(resposta.indexOf("Erro") != -1){
					alert(resposta);
					window.location.reload();
				} else {
					alert(resposta);	
				};
				
		});

	return false;
}

</script>

<div style="float: left;margin-left: 25px">
	
	<div id="myTabContent" class="tab-content">

			<div class="control-group"></div>

			<div class="navbar">
				<div class="navbar-inner">
					<div class="container">

						<div class="page-header">
							<h2><small>Buscas</small></h2>
						</div>
					
						<div id="parceiroNegocioClienteSearch" style="margin-top: 20px">
								<form class="form-search" action="<c:url value="/parceironegocio/cadastro" />" method="POST">
								<div class="input-append">
									<input type="text" class="input-small" id="doc" name="doc">
									<button type="submit" class="btn">Busca PN</button>
								</div>
								</form>
						</div>
						
						<div id="parceiroNegocioFuncionarioSearch">
							<form class="form-search" action="<c:url value="/parceironegocio/busca.funcionario"  />" method="POST">
								<div class="input-append">
									<input type="text" class="input-small" id="doc" name="doc">
									<button type="submit" class="btn">Busca CPF</button>
								</div>
							</form>
						</div>
					
					</div>
				</div>
			</div>

	</div>

</div>

<div style="float: left;margin-left: 25px">

		<div id="myTabContent" class="tab-content">	

			<div class="tab-pane fade active in" id="parceironegocio-div" >
				
				<div class="control-group"></div>
					
					<div class="navbar">
							<div class="navbar-inner">
								<div class="container">
								
								<div class="page-header">
									<h2><small>Parceiro Neg�cio</small></h2>
								</div>

							<form id="parceiroNegocioForm" name="parceiroNegocioForm" action="<c:url value="/parceironegocio/salva"/>" method="POST">
			
								<input id="parceiroNegocioId" name="parceiroNegocio.parceiroNegocio_id" value="${parceiroNegocio.parceiroNegocio_id }" type="hidden"/>
								<input id="parceiroNegocioPnId" name="parceiroNegocio.pn_id" value="${parceiroNegocio.pn_id }" type="hidden"/>

								<div class="controls controls-row">
									<input class="span2" id="parceiroNegocioEmpresa" name="parceiroNegocio.empresa.nome" value="${usuarioInfo.usuario.empresa.nome }" type="text" required />
									<input class="span2" id="parceiroNegocioEmpresaId" name="parceiroNegocio.empresa.empresa_id" type="hidden" value="${usuarioInfo.usuario.empresa.empresa_id }" />
			
									<input class="span2" id="parceiroNegocioOrganizacao" name="parceiroNegocio.organizacao.nome" value="${usuarioInfo.usuario.organizacao.nome }" type="text" required />
									<input id="parceiroNegocioOrganizacaoId" name="parceiroNegocio.organizacao.organizacao_id"  value="${usuarioInfo.usuario.organizacao.organizacao_id }" type="hidden" />
									
									<input  class="input-large" id="parceiroNegocioCategoriaParceiro" name="parceiroNegocio.categoriaParceiro.nome" type="text" value="Servi�o">
									<input  class="input-large" id="parceiroNegocioCategoriaParceiroId" name="parceiroNegocio.categoriaParceiro.categoriaParceiro_id" type="hidden" value="1">
			
									<input  class="input-large" id="parceiroNegocioClassificacaoParceiro" name="parceiroNegocio.classificacaoParceiro.nome" type="text" value="Normal">
									<input  class="input-large" id="parceiroNegocioClassificacaoParceiroId" name="parceiroNegocio.classificacaoParceiro.classificacaoParceiro_id" type="hidden" value="1">
			
									<input  class="input-large" id="parceiroNegocioGrupoParceiro" name="parceiroNegocio.grupoParceiro.nome" type="text" value="Teste">
									<input  class="input-large" id="parceiroNegocioGrupoParceiroId" name="parceiroNegocio.grupoParceiro.grupoParceiro_id" type="hidden" value="1">
								</div>	
			
								<div class="control-group"></div>
					
								<div class="controls controls-row">
									<select id="parceiroNegocioTipoParceiroId" name="parceiroNegocio.tipoParceiro.tipoParceiro_id">
										<option value="">Tipo Parceiro:</option>
										<c:forEach var="tipoParceiro" items="${tiposParceiro }">
										 	<option value="${tipoParceiro.tipoParceiro_id }" <c:if test="${parceiroNegocio.tipoParceiro.tipoParceiro_id eq tipoParceiro.tipoParceiro_id }"> selected="selected"</c:if>>${tipoParceiro.nome }</option>
										</c:forEach>
									</select>
								</div>
			
								<div class="controls controls-row">
			
									<input  class="input-xlarge" id="parceiroNegocioNome" name="parceiroNegocio.nome" type="text" placeholder="Nome" value="${parceiroNegocio.nome }">
			
								</div>
								
								<div id="parceiroNegocioTipoPessoaFisica" class="row-fluid" 
										<c:if test="${parceiroNegocio.isFuncionario || parceiroNegocio.isCliente }">style="display: block;"</c:if>
										<c:if test="${parceiroNegocio.isFornecedor || empty parceiroNegocio}">style="display: none;"</c:if>>
			
										<div class="controls controls-row">
			
											<input  class="input-medium" id="parceiroNegocioCpf" name="parceiroNegocio.cpf" type="text" placeholder="Cpf" value="${parceiroNegocio.cpf }">
											<input  class="input-medium" id="parceiroNegocioRg" name="parceiroNegocio.rg" type="text" placeholder="Rg" value="${parceiroNegocio.rg }">
											<input  class="input-medium" id="parceiroNegocioDataNascimento" name="parceiroNegocio.dataNascimento" type="text" placeholder="Nasc." 
											value="<fmt:formatDate pattern="dd/MM/yyyy"  type="time" value="${parceiroNegocio.dataNascimento.time }" />">
			
											<select  id="parceiroNegocioSexoId" name="parceiroNegocio.sexo.sexo_id" class="input-medium" required>
												<option value="">Sexo:</option>
												<c:forEach var="sexo" items="${sexos }">
												 	<option value="${sexo.sexo_id }" <c:if test="${parceiroNegocio.sexo.sexo_id eq sexo.sexo_id }"> selected="selected"</c:if>>${sexo.nome }</option>
												</c:forEach>
											</select>
			
											<select  id="parceiroNegocioEstadoCivilId" name="parceiroNegocio.estadoCivil.estadoCivil_id" class="input-medium" required>
												<option value="">Est Civil:</option>
												<c:forEach var="estadoCivil" items="${estadosCivis }">
												 	<option value="${estadoCivil.estadoCivil_id }" <c:if test="${parceiroNegocio.estadoCivil.estadoCivil_id eq estadoCivil.estadoCivil_id }"> selected="selected"</c:if>>${estadoCivil.nome }</option>
												</c:forEach>
			
											</select>
										</div>						
									</div>
			
									<div id="parceiroNegocioTipoPessoaJuridica" class="row-fluid" style="display: none;">
										<input  class="input-large" id="parceiroNegocioCnpj" name="parceiroNegocio.cnpj" type="text" placeholder="Cnpj">
										<input  class="input-large" id="parceiroNegocioIe" name="parceiroNegocio.ie" type="text" placeholder="Insc Estadual">
									</div>
			
								<div id="parceiroNegocioFuncionario" class="row-fluid" 
										<c:if test="${parceiroNegocio.isFuncionario }">style="display: block;"</c:if>
										<c:if test="${parceiroNegocio.isCliente || parceiroNegocio.isFornecedor ||  empty parceiroNegocio}">style="display: none;"</c:if>>
			
									<div class="controls controls-row">
			
										<select  id="funcionarioDepartamentoId" name="funcionario.departamento.departamento_id" >
											<option value="">Selecione</option>
											<c:forEach var="departamento" items="${departamentos }">
												<option value="${departamento.departamento_id }" <c:if test="${funcionario.departamento.departamento_id eq departamento.departamento_id }"> selected="selected"</c:if>> ${departamento.nome }</option>
											</c:forEach>
										</select>
			
										<select  id="funcionarioFuncaoId" name="funcionario.funcao.funcao_id" >
											<option value="">Selecione</option>
											<c:forEach var="funcao" items="${funcoes }">
												<option value="${funcao.funcao_id }" <c:if test="${funcionario.funcao.funcao_id eq funcao.funcao_id }"> selected="selected"</c:if>> ${funcao.nome }</option>
											</c:forEach>
										</select>
			
									</div>
								</div>

								<br/>
								
								<c:if test="${not empty parceiroContatos}">	
									<div class="navbar" style="display: block;width: 350px;float: left">
										
										<div class="navbar-inner" >
									
											<div class="container">
											
												<div class="control-group">
												</div>
												
													<div id="parceiroContatosDiv">	
														
														<table class="table table-striped table-bordered" id="lista">
														<thead>
															<tr>
																<th>TipoContato</th>
																<th>Contato</th>
																<th>Excluir</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${parceiroContatos}" var="parceiroContato" varStatus="status">
																<tr>
																	<td>
																		<select id="parceiroContatoTipoContatoLista"  name="parceiroContatos[${status.index}].tipoContato.tipoContato_id" onChange="return alteraContato(this,'tipoContato','${parceiroContato.parceiroContato_id}', this.value);" class="input-small">
																			<option value="0" selected="selected">Selecione</option>
																			<c:forEach var="tipoContato" items="${tiposContato}">
																				<option value="${tipoContato.tipoContato_id}" <c:if test="${parceiroContato.tipoContato.tipoContato_id eq tipoContato.tipoContato_id}">SELECTED</c:if>>${tipoContato.chave}</option>
																			</c:forEach>
																		</select>
																	</td>
																	<td><input type="text" id="parceiroContatoNomeLista" name="parceiroContatos[${status.index}].nome" value="${parceiroContato.nome }" class="input-small" onChange="return alteraContato(this,'nome','${parceiroContato.parceiroContato_id}', this.value);"/></td>
																	<td style="text-align: center;">
																		<button type="button" class="btn btn-danger btn-mini" onClick="return excluiContato(this,'${parceiroContato.parceiroContato_id}');">Excluir</button>
																	</td>
																</tr>
															</c:forEach>
															<c:if test="${not empty parceiroNegocio.parceiroNegocio_id}">
																<tr>
																	<td>
																		<select id="parceiroContatoTipoContatoNovo" class="input-small">
																			<option value="0" selected="selected">Selecione</option>
																			<c:forEach var="tipoContato" items="${tiposContato}">
																				<option value="${tipoContato.tipoContato_id}" >${tipoContato.chave}</option>
																			</c:forEach>
																		</select>
																	</td>
																	<td><input type="text" id="parceiroContatoNomeNovo" value="${parceiroContato.nome }" class="input-small"/></td>
																	<td style="text-align: center;">
																		<button type="button" class="btn btn-mini" id="bttParceiroContatoNovo" onClick="return salvaContato();">Novo</button>
																	</td>
																</tr>
															</c:if>
														</tbody>
														</table>
														
													</div>
												
											</div>
										</div>
									
									</div>
								</c:if>
								
								<c:if test="${not empty parceiroBeneficios}">
									<div class="navbar" style="display: block;width: 350px;float: left">
										
									<div class="navbar-inner" >
									
										<div class="container">
											<div class="control-group"></div>
													
												<div id="parceiroBeneficiosDiv">	
													<table class="table table-striped table-bordered" id="lista">
														<thead>
															<tr>
																<th>Benef�cio</th>
																<th>Excluir</th>
															</tr>
														</thead>
														<tbody>	
															<c:forEach items="${parceiroBeneficios}" var="parceiroBeneficio" varStatus="status">
																<tr>
																	<td><input type="text" id="parceiroBeneficioNumeroLista" name="parceiroBeneficios[${status.index}].numeroBeneficio" value="${parceiroBeneficio.numeroBeneficio }" class="input-small" onChange="return alteraBeneficio(this,'nome','${parceiroBeneficio.parceiroBeneficio_id}', this.value);"/></td>
																	<td style="text-align: center;">
																		<button type="button" class="btn btn-danger btn-mini" onClick="return excluiBeneficio(this,'${parceiroBeneficio.parceiroBeneficio_id}');">Excluir</button>
																	</td>
																</tr>
															</c:forEach>
															<c:if test="${not empty parceiroNegocio.parceiroNegocio_id}">
																<tr>
																	<td><input type="text" id="parceiroBeneficioNumeroNovo" value="${parceiroBeneficio.numeroBeneficio }" class="input-small"/></td>
																	<td style="text-align: center;">
																		<button type="button" class="btn btn-mini" id="bttParceiroBeneficioNovo" onClick="return salvaBeneficio();">Novo</button>
																	</td>
																</tr>
															</c:if>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
								</c:if>

								<c:if test="${not empty parceiroLocalidades}">
									<div class="navbar" style="clear: both;width: 900px;">

										<div class="navbar-inner"  >
											<div class="container">

												<div class="page-header">
													<h3><small>Endere�os</small></h3>
												</div>

												<div id="enderecos" style="margin-top: 15px;">
													<table class="table table-striped table-bordered" id="lista">
														<thead>
															<tr>
																<th>Cep</th>
																<th>Bairro</th>
																<th>Cidade</th>
																<th>Endere�o</th>
																<th>N�mero</th>
																<th>Complemento</th>
																<th>Tipo</th>
																<th>Excluir</th>
															</tr>
														</thead>
														<tbody>	
															<c:forEach items="${parceiroLocalidades}" var="parceiroLocalidade">
																<tr>
																	<td>${parceiroLocalidade.localidade.cep }</td>
																	<td>${parceiroLocalidade.localidade.bairro }</td>
																	<td>${parceiroLocalidade.localidade.cidade.nome }</td>
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

							<div class="navbar" style="clear: both;width: 900px;">
									<div class="navbar-inner">
										<div class="container">
										
										<div id="ajax_endereco" <c:if test="${not empty parceiroNegocio && !parceiroNegocio.isCliente}">style="display: none;"</c:if>>

											<div class="control-group"></div>
											<div class="page-header">
												<h2><small>Novo Endere�o</small></h2>
											</div>
											
											<div class="input-append">
												<input class="span2" id="localidadeCep" name="localidade.cep" type="text" placeholder="Busca Cep" value="${localidade.cep }" />
												<span class="add-on"><i class="icon-search"></i></span>
											</div>

											<div class="row-fluid">
												<input class="span5" id="localidadeEndereco" name="localidade.endereco" type="text" placeholder="Endere�o" value="${localidade.endereco }"/>
												<input class="span1" id="parceirolocalidadeNumero" name="parceiroLocalidade.numero" type="text" placeholder="N�mero" value="${parceiroLocalidade.numero }" />
												<input class="span2" id="localidadeBairro" name="localidade.bairro" type="text" placeholder="Bairro" value="${localidade.bairro }" />
												<input class="span2" id="localidadeCidade" name="localidade.cidade" type="text" placeholder="Cidade" value="${localidade.cidade.nome }" />
												<input class="span1" id="localidadeRegiao" name="localidade.regiao" type="text" placeholder="UF"  value="${localidade.regiao.chave }"  />
											</div>
			
											<div id="alertCEP" style="position:absolute; float: right;width: 250px;margin-left: 100px;margin-top: -27px;"><i></i></div>
			
											<div class="row-fluid">		

												<input class="span2" id="parceirolocalidadeComplemento" name="parceiroLocalidade.complemento" type="text" placeholder="Complemento" value="${parceiroLocalidade.complemento }" />
												<input class="span9" id="parceirolocalidadePontoReferencia" name="parceiroLocalidade.pontoReferencia" type="text" placeholder="Ponto de Refer�ncia" value="${parceiroLocalidade.pontoReferencia }" />

												<input  id="localidadeCidadeId" name="localidade.cidade.cidade_id" type="hidden"  value="${localidade.cidade.cidade_id }" />
												<input  id="localidadeRegiaoId" name="localidade.regiao.regiao_id" type="hidden" value="${localidade.regiao.regiao_id }" />
												<input  id="localidadePaisId" name="localidade.pais.pais_id" type="hidden"  value="${localidade.pais.pais_id }" />
												<input  id="localidadeId" name="localidade.localidade_id" type="hidden"  value="${localidade.localidade_id }" />
											</div>

										</div>
										
										<c:if test="${not empty parceiroNegocio }">
											<div class="btn-toolbar" align="right">
												<div class="btn-group">
													<button type="button" class="btn btn-primary btn-mini" id="bttLocalidade" onClick="mostraEndereco();"><i class="icon-plus"></i></button>
												</div>	
												<div class="btn-group">
													<button type="button" class="btn btn-primary btn-mini" id="bttCancelar" onClick="cancelaEndereco();"><i class="icon-remove"></i></button>
												</div>
											</div>
										</c:if>
										
									</div>
								</div>
		
							</div>
	
							<div class="controls controls-row" style="clear: both;">
								<label class="checkbox inline">
									<input type="checkbox" id="parceiroNegocioIsFuncionario" name="parceiroNegocio.isFuncionario" value="1"
										<c:if test="${parceiroNegocio.isFuncionario }">checked="checked"</c:if>> Funcion�rio
								</label>
								<label class="checkbox inline">
									<input type="checkbox" id="parceiroNegocioIsCliente" name="parceiroNegocio.isCliente" value="1"
										<c:if test="${parceiroNegocio.isCliente }">checked="checked"</c:if>> Cliente
								</label>
								<label class="checkbox inline">
									<input type="checkbox" id="parceiroNegocioIsFornecedor" name="parceiroNegocio.isFornecedor" value="1"
										<c:if test="${parceiroNegocio.isFornecedor }">checked="checked"</c:if>> Fornecedor
								</label>
							</div>
		
							<div class="controls controls-row">
								<label class="checkbox">
									<input type="checkbox" id="parceiroNegocioIsActive" name="parceiroNegocio.isActive" checked="checked" value="1"> Ativo
								</label>							
							</div>
				
							<div class="btn-toolbar">
								<div class="btn-group">
									<button type="submit" class="btn btn-primary">Salvar</button>
								</div>		
								<div class="btn-group">
									<button type="button" class="btn btn-primary" id="bttNovo">Novo</button>
								</div>	
							</div>
						</form>		
							
						</div>
					</div>
				</div>

			</div>

			<div class="tab-pane fade" id="localidade-div"></div>

		</div>

</div>
<%@ include file="/footer.jspf"%>
