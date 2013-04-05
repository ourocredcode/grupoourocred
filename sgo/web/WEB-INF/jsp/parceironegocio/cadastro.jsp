<%@ include file="/header.jspf"%>

	<script type="text/javascript">
	jQuery(function($){

		   $("#localidadeCep").mask("99999999");
		   $("#parceiroNegocioCpf").mask("99999999999");
		   $("#parceiroNegocioDataNascimento").mask("99/99/9999");
	
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
	
		if (window.confirm("Deseja salvar o contato?"))
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
	
		if (window.confirm("Deseja salvar o número do benefício?"))
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

	<div id="content-header">
		<h1>Parceiro Negócio</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
		<a href="#" class="current">Parceiro Négocio</a>
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

	<div id="buscaParceiroDiv" style="float: right;margin-top: 20px;margin-left: 760px;position: absolute;">
		<form id="buscaParceiroForm" class="form-search" action="<c:url value="/parceironegocio/cadastro" />" method="POST">
			<div class="input-append">
				<input type="text" class="input-medium" id="doc" name="doc" placeholder="Escolha o tipo de busca"/>
				<span class="add-on" onclick="submit();"><i class="icon-search"></i></span>
			</div>
		</form>
	</div>

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">

					<form id="parceiroNegocioForm" name="parceiroNegocioForm" action="<c:url value="/parceironegocio/salva"/>" method="POST">
				
						<input id="parceiroNegocioId" name="parceiroNegocio.parceiroNegocio_id" value="${parceiroNegocio.parceiroNegocio_id }" type="hidden"/>
						<input id="parceiroNegocioPnId" name="parceiroNegocio.pn_id" value="${parceiroNegocio.pn_id }" type="hidden"/>

						<div class="control-group">
							<div class="controls controls-row">
								<label class="checkbox inline"><input type="checkbox" id="parceiroNegocioIsFuncionario" name="parceiroNegocio.isFuncionario" value="1"
									<c:if test="${parceiroNegocio.isFuncionario }">checked="checked"</c:if>> Funcionário
								</label>
								<label class="checkbox inline"><input type="checkbox" id="parceiroNegocioIsCliente" name="parceiroNegocio.isCliente" value="1"
									<c:if test="${parceiroNegocio.isCliente }">checked="checked"</c:if>> Cliente
								</label>
								<label class="checkbox inline"><input type="checkbox" id="parceiroNegocioIsFornecedor" name="parceiroNegocio.isFornecedor" value="1"
									<c:if test="${parceiroNegocio.isFornecedor }">checked="checked"</c:if>> Fornecedor
							</label>
							</div>
						</div>
						
						<br/>
				
						<div class="row-fluid">
							<div class="span2">
								<label for="parceiroNegocioEmpresa">Empresa</label>
								<input class="input-medium" id="parceiroNegocioEmpresa" name="parceiroNegocio.empresa.nome" value="${usuarioInfo.usuario.empresa.nome }" type="text" required />
								<input class="input-medium" id="parceiroNegocioEmpresaId" name="parceiroNegocio.empresa.empresa_id" type="hidden" value="${usuarioInfo.usuario.empresa.empresa_id }" />
							</div>
							<div class="span2">
								<label for="parceiroNegocioOrganizacao">Organização</label>
								<input  class="input-medium" id="parceiroNegocioOrganizacao" name="parceiroNegocio.organizacao.nome" value="${usuarioInfo.usuario.organizacao.nome }" type="text" required />
								<input  class="input-medium" id="parceiroNegocioOrganizacaoId" name="parceiroNegocio.organizacao.organizacao_id"  value="${usuarioInfo.usuario.organizacao.organizacao_id }" type="hidden" />
							</div>
							<div class="span2">
								<label for="parceiroNegocioCategoriaParceiro">Categoria</label>
								<input  class="input-medium" id="parceiroNegocioCategoriaParceiro" name="parceiroNegocio.categoriaParceiro.nome" type="text" value="Serviço">
								<input  class="input-medium" id="parceiroNegocioCategoriaParceiroId" name="parceiroNegocio.categoriaParceiro.categoriaParceiro_id" type="hidden" value="1">
							</div>
							<div class="span2">
								<label for="parceiroNegocioClassificacaoParceiro">Classificação</label>
								<input  class="input-medium" id="parceiroNegocioClassificacaoParceiro" name="parceiroNegocio.classificacaoParceiro.nome" type="text" value="Normal">
								<input  class="input-medium" id="parceiroNegocioClassificacaoParceiroId" name="parceiroNegocio.classificacaoParceiro.classificacaoParceiro_id" type="hidden" value="1">
							</div>
							<div class="span2">
								<label for="parceiroNegocioGrupoParceiro">Grupo</label>
								<input  class="input-medium" id="parceiroNegocioGrupoParceiro" name="parceiroNegocio.grupoParceiro.nome" type="text" value="Teste">
								<input  class="input-medium" id="parceiroNegocioGrupoParceiroId" name="parceiroNegocio.grupoParceiro.grupoParceiro_id" type="hidden" value="1">
							</div>
						</div>	
				
						<div class="control-group"></div>
				
						<div class="controls controls-row">
							<label for="parceiroNegocioTipoParceiroId">Tipo Parceiro</label>
							<select id="parceiroNegocioTipoParceiroId" name="parceiroNegocio.tipoParceiro.tipoParceiro_id" class="input-medium">
								<c:forEach var="tipoParceiro" items="${tiposParceiro }">
								 	<option value="${tipoParceiro.tipoParceiro_id }" <c:if test="${parceiroNegocio.tipoParceiro.tipoParceiro_id eq tipoParceiro.tipoParceiro_id }"> selected="selected"</c:if>>${tipoParceiro.nome }</option>
								</c:forEach>
							</select>
						</div>
						
						<div class="control-group"></div>
				
						<div class="controls controls-row">
							<label for="parceiroNegocioNome">Nome</label>
							<input  class="input-xxlarge" id="parceiroNegocioNome" name="parceiroNegocio.nome" type="text" placeholder="Nome" value="${parceiroNegocio.nome }">
				
						</div>
						
						<div id="parceiroNegocioTipoPessoaFisica" class="row-fluid" 
								<c:if test="${parceiroNegocio.isFuncionario || parceiroNegocio.isCliente || empty parceiroNegocio }">style="display: block;"</c:if>
								<c:if test="${parceiroNegocio.isFornecedor}">style="display: none;"</c:if>>

								<div class="span2">
									<label for="parceiroNegocioCpf">CPF</label>
									<input  class="input-medium" id="parceiroNegocioCpf" name="parceiroNegocio.cpf" type="text" placeholder="Cpf" value="${parceiroNegocio.cpf }">
								</div>
								<div class="span2">
									<label for="parceiroNegocioRg">RG</label>
									<input  class="input-medium" id="parceiroNegocioRg" name="parceiroNegocio.rg" type="text" placeholder="Rg" value="${parceiroNegocio.rg }">
								</div>
								<div class="span2">
									<label for="parceiroNegocioDataNascimento">Dt Nascimento</label>
									<input  class="input-medium" id="parceiroNegocioDataNascimento" name="parceiroNegocio.dataNascimento" type="text" placeholder="Nasc." 
									value="<fmt:formatDate pattern="dd/MM/yyyy"  type="time" value="${parceiroNegocio.dataNascimento.time }" />">
								</div>
								<div class="span2">
									<label for="parceiroNegocioSexoId">Sexo</label>
									<select  id="parceiroNegocioSexoId" name="parceiroNegocio.sexo.sexo_id" class="input-medium" required>
										<c:forEach var="sexo" items="${sexos }">
										 	<option value="${sexo.sexo_id }" <c:if test="${parceiroNegocio.sexo.sexo_id eq sexo.sexo_id }"> selected="selected"</c:if>>${sexo.nome }</option>
										</c:forEach>
									</select>
								</div>
								<div class="span2">
									<label for="parceiroNegocioEstadoCivilId">Estado Civil</label>
									<select  id="parceiroNegocioEstadoCivilId" name="parceiroNegocio.estadoCivil.estadoCivil_id" class="input-medium" required>										
										<c:forEach var="estadoCivil" items="${estadosCivis }">
										 	<option value="${estadoCivil.estadoCivil_id }" <c:if test="${parceiroNegocio.estadoCivil.estadoCivil_id eq estadoCivil.estadoCivil_id }"> selected="selected"</c:if>>${estadoCivil.nome }</option>
										</c:forEach>
									</select>
								</div>
			
							</div>
				
							<div id="parceiroNegocioTipoPessoaJuridica" class="row-fluid" style="display: none;">
								<div class="span2">
									<label for="parceiroNegocioCnpj">CNPJ</label>
									<input  class="input-medium" id="parceiroNegocioCnpj" name="parceiroNegocio.cnpj" type="text" placeholder="Cnpj">
								</div>
								<div class="span2">
									<label for="parceiroNegocioIe">Inscrição Estadual</label>
									<input  class="input-medium" id="parceiroNegocioIe" name="parceiroNegocio.ie" type="text" placeholder="Insc Estadual">
								</div>
							</div>
				
						<div id="parceiroNegocioFuncionario" class="row-fluid" 
								<c:if test="${parceiroNegocio.isFuncionario }">style="display: block;"</c:if>
								<c:if test="${parceiroNegocio.isCliente || parceiroNegocio.isFornecedor ||  empty parceiroNegocio}">style="display: none;"</c:if>>
				
							<div class="controls controls-row">
								
								<div class="span2">
									<label for="funcionarioDepartamentoId">Departamento</label>
									<select  id="funcionarioDepartamentoId" name="funcionario.departamento.departamento_id" class="input-medium">
										<c:forEach var="departamento" items="${departamentos }">
											<option value="${departamento.departamento_id }" <c:if test="${funcionario.departamento.departamento_id eq departamento.departamento_id }"> selected="selected"</c:if>> ${departamento.nome }</option>
										</c:forEach>
									</select>
								</div>
								
								<div class="span2">
									<label for="funcionarioFuncaoId">Função</label>
									<select  id="funcionarioFuncaoId" name="funcionario.funcao.funcao_id" class="input-medium">
										<c:forEach var="funcao" items="${funcoes }">
											<option value="${funcao.funcao_id }" <c:if test="${funcionario.funcao.funcao_id eq funcao.funcao_id }"> selected="selected"</c:if>> ${funcao.nome }</option>
										</c:forEach>
									</select>
								</div>
								
				
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
														<th>Benefício</th>
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
											<h2><small>Endereços</small></h2>
										</div>
				
										<div id="enderecos" style="margin-top: 15px;">
											<table class="table table-striped table-bordered" id="lista">
												<thead>
													<tr>
														<th>Cep</th>
														<th>Bairro</th>
														<th>Cidade</th>
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
										<h2><small>Endereço</small></h2>
									</div>
									
									<div class="input-append">
										<input class="span10" id="localidadeCep" name="localidade.cep" type="text" placeholder="Busca Cep" value="${localidade.cep }" />
										<span class="add-on"><i class="icon-search"></i></span>
									</div>
				
									<div class="row-fluid">
										<input class="span5" id="localidadeEndereco" name="localidade.endereco" type="text" placeholder="Endereço" value="${localidade.endereco }"/>
										<input class="span1" id="parceirolocalidadeNumero" name="parceiroLocalidade.numero" type="text" placeholder="Número" value="${parceiroLocalidade.numero }" />
										<input class="span2" id="localidadeBairro" name="localidade.bairro" type="text" placeholder="Bairro" value="${localidade.bairro }" />
										<input class="span2" id="localidadeCidade" name="localidade.cidade" type="text" placeholder="Cidade" value="${localidade.cidade.nome }" />
										<input class="span1" id="localidadeRegiao" name="localidade.regiao" type="text" placeholder="UF"  value="${localidade.regiao.chave }"  />
									</div>
				
									<div id="alertCEP" style="position:absolute; float: right;width: 250px;margin-left: 100px;margin-top: -27px;"><i></i></div>
				
									<div class="row-fluid">		
				
										<input class="span2" id="parceirolocalidadeComplemento" name="parceiroLocalidade.complemento" type="text" placeholder="Complemento" value="${parceiroLocalidade.complemento }" />
										<input class="span9" id="parceirolocalidadePontoReferencia" name="parceiroLocalidade.pontoReferencia" type="text" placeholder="Ponto de Referência" value="${parceiroLocalidade.pontoReferencia }" />
				
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
		

<%@ include file="/footer.jspf"%>
