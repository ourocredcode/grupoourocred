<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){
	
		$('#parceiroNegocioDataNascimento').datepicker();
	
		$('#parceironegocio-li-a').click(function() {
			window.location.href = '<c:url value="/parceironegocio/cadastro" />';
		});
		
		$('#localidade-li-a').click(function() {
			window.location.href = '<c:url value="/localidade/cadastro" />';
		});
	
		

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
	   
	   $("#parceiroNegocioIsCliente").click(function(evento){

		   if ($('#parceiroNegocioIsCliente').is(':checked')){
		         $("#parceiroNegocioClienteSearch").css("display", "block");
		      }else{
		         $("#parceiroNegocioClienteSearch").css("display", "none");
		      }
		   
	   });
	   
	   $("#parceiroNegocioIsFuncionario").click(function(evento){

		   if ($('#parceiroNegocioIsFuncionario').is(':checked')){
		         $("#parceiroNegocioFuncionarioSearch").css("display", "block");
		      }else{
		         $("#parceiroNegocioFuncionarioSearch").css("display", "none");
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

function salvaEndereco() {

	var cep = $("#localidadeCep").val();
	var endereco = $("#localidadeEndereco").val();
	var numero = $("#parceirolocalidadeNumero").val();
	var complemento = $("#parceirolocalidadeComplemento").val();
	var pontoreferencia = $("#parceirolocalidadePontoReferencia").val();
	var bairro = $("#localidadeBairro").val();
	var cidadeId = $("#localidadeCidadeId").val();
	var regiaoId = $("#localidadeRegiaoId").val();
	var parceiroId = $("#parceiroNegocioId").val();
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
				'parceiroLocalidade.parceiroNegocio.parceiroNegocio_id': parceiroId}
		, function(resposta) { $('#enderecos').html(resposta); } );

	return false;
}


</script>


<div class="span9">

	<section id="tabs">

		<div class="bs-docs-example">

		<ul id="myTab" class="nav nav-tabs">
			<li class="active" id="parceironegocio-li"><a href="#parceironegocio-div" data-toggle="tab" id="parceironegocio-li-a">Parceiro de Negócios</a></li>
			<li class="" id="localidade-li"><a href="#localidade-div" data-toggle="tab" id="localidade-li-a">Endereço</a></li>
		</ul>

		<div id="myTabContent" class="tab-content">	

			<div class="tab-pane fade active in" id="parceironegocio-div" >

					<div class="control-group"></div>
					
					<div class="controls controls-row" style=" height: 45px">

						<div id="parceiroNegocioClienteSearch" style="display: none;margin-top: 10px">
							<form class="form-search" action="<c:url value="/parceironegocio/busca.cliente" />" method="POST">
								<div class="input-append">
									<input type="text" class="span2 search-query">
									<button type="submit" class="btn">Busca PN</button>
								</div>
							</form>
						</div>
	
						<div id="parceiroNegocioFuncionarioSearch" style="display: none;margin-top: 10px">
							<form class="form-search" action="<c:url value="/parceironegocio/busca.funcionario"  />" method="POST">
								<div class="input-append">
									<input type="text" class="span2 search-query" id="doc" name="doc">
									<button type="submit" class="btn">Busca Funcionário</button>
								</div>
							</form>
						</div>
					
					</div>
					
					<div class="navbar">
							<div class="navbar-inner">
								<div class="container">
								
								<div class="control-group"></div>
								<div class="page-header">
									<h1><small>Parceiro Negócio</small></h1>
								</div>

								<form id="parceiroNegocioForm" name="parceiroNegocioForm" action="<c:url value="/parceironegocio/salva"/>" method="POST">
			
								<input id="parceiroNegocioId" name="parceiroNegocio.parceiroNegocio_id" value="${parceiroNegocio.parceiroNegocio_id }" type="hidden"/>
			
								<div class="controls controls-row">
									<input class="span2" id="parceiroNegocioEmpresa" name="parceiroNegocio.empresa.nome" value="${usuarioInfo.usuario.empresa.nome }" type="text" required />
									<input class="span2" id="parceiroNegocioEmpresaId" name="parceiroNegocio.empresa.empresa_id" type="hidden" value="${usuarioInfo.usuario.empresa.empresa_id }" />
			
									<input class="span2" id="parceiroNegocioOrganizacao" name="parceiroNegocio.organizacao.nome" value="${usuarioInfo.usuario.organizacao.nome }" type="text" required />
									<input id="parceiroNegocioOrganizacaoId" name="parceiroNegocio.organizacao.organizacao_id"  value="${usuarioInfo.usuario.organizacao.organizacao_id }" type="hidden" />
									
									<input  class="input-large" id="parceiroNegocioCategoriaParceiro" name="parceiroNegocio.categoriaParceiro.nome" type="text" value="Serviço">
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
			
											<select  id="parceiroNegocioSexoId" name="parceiroNegocio.sexo.sexo_id" class="input-medium">
												<option value="">Sexo:</option>
												<c:forEach var="sexo" items="${sexos }">
												 	<option value="${sexo.sexo_id }" <c:if test="${parceiroNegocio.sexo.sexo_id eq sexo.sexo_id }"> selected="selected"</c:if>>${sexo.nome }</option>
												</c:forEach>
											</select>
			
											<select  id="parceiroNegocioEstadoCivilId" name="parceiroNegocio.estadoCivil.estadoCivil_id" class="input-medium">
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
										<c:if test="${parceiroNegocio.isFuncionario || parceiroNegocio.isCliente }">style="display: block;"</c:if>
										<c:if test="${parceiroNegocio.isFornecedor || empty parceiroNegocio}">style="display: none;"</c:if>>
			
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
								
								<div id="ajax_endereco" style="display: block;">
			
									<div class="navbar">
										<div class="navbar-inner">
											<div class="container">

												<div class="control-group"></div>
												<div class="page-header">
													<h1><small>Endereço</small></h1>
												</div>
												
												<div class="input-append">
													<input class="span2" id="localidadeCep" name="localidade.cep" type="text" placeholder="Busca Cep" value="${localidade.cep }" />
													<span class="add-on"><i class="icon-search"></i></span>
												</div>

												<div class="row-fluid">
													<input class="span5" id="localidadeEndereco" name="localidade.endereco" type="text" placeholder="Endereço" value="${localidade.endereco }"/>
													<input class="span1" id="parceirolocalidadeNumero" name="parceiroLocalidade.numero" type="text" placeholder="Número"/>
													<input class="span2" id="localidadeBairro" name="localidade.bairro" type="text" placeholder="Bairro" value="${localidade.bairro }" />
													<input class="span2" id="localidadeCidade" name="localidade.cidade" type="text" placeholder="Cidade" value="${localidade.cidade.nome }" />
													<input class="span1" id="localidadeRegiao" name="localidade.regiao" type="text" placeholder="UF"  value="${localidade.regiao.chave }"  />
												</div>
				
												<div id="alertCEP" style="position:absolute; float: right;width: 250px;margin-left: 100px;margin-top: -27px;"><i></i></div>
				
												<div class="row-fluid">		

													<input class="span2" id="parceirolocalidadeComplemento" name="parceirolocalidade.complemento" type="text" placeholder="Complemento" />
													<input class="span9" id="parceirolocalidadePontoReferencia" name="parceirolocalidade.pontoreferencia" type="text" placeholder="Ponto de Referência" />

													<input  id="localidadeCidadeId" name="localidade.cidade.cidade_id" type="hidden"  value="${localidade.cidade.cidade_id }" />
													<input  id="localidadeRegiaoId" name="localidade.regiao.regiao_id" type="hidden" value="${localidade.regiao.regiao_id }" />
													<input  id="localidadePaisId" name="localidade.pais.pais_id" type="hidden"  value="${localidade.pais.pais_id }" />
												</div>

												<c:if test="${not empty parceiroNegocio }">
													<div class="btn-toolbar">
														<div class="btn-group">
															<button type="button" class="btn btn-primary" id="bttLocalidade" onClick="salvaEndereco();">Adicionar Endereço</button>
														</div>	
													</div>
												</c:if>
												
											</div>
										</div>
									</div>
			
								</div>
			
								<c:if test="${not empty parceiroLocalidades}">
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
													<th>Ação</th>
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
														<td>${parceiroLocalidade.numero }</td>
														<td>${parceiroLocalidade.complemento }</td>
														<td>${parceiroLocalidade.tipoEndereco.nome }</td>
														<td>
															<button type="button" class="btn btn-primary">Alterar</button>
															
														</td>
														<td>
															<button type="button" class="btn btn-primary">Excluir</button>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
				
									</div>
								</c:if>
								
								<div class="controls controls-row">
									<label class="checkbox inline">
										<input type="checkbox" id="parceiroNegocioIsFuncionario" name="parceiroNegocio.isFuncionario" value="1"> Funcionário
									</label>
									<label class="checkbox inline">
										<input type="checkbox" id="parceiroNegocioIsCliente" name="parceiroNegocio.isCliente" value="1"> Cliente
									</label>
									<label class="checkbox inline">
										<input type="checkbox" id="parceiroNegocioIsFornecedor" name="parceiroNegocio.isFornecedor" value="1"> Fornecedor
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

	</section>

</div>

<%@ include file="/footer.jspf"%>
