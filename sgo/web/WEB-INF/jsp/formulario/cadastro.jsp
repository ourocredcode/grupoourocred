<%@ include file="/header.jspf"%>

	<div id="content-header">
		<h1>Formulário</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="#" title="Go to Home" class="tip-bottom"><i class="icon-home"></i> Home</a>
		<a href="#" class="current">Formulário</a>
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

				<div id="buscaParceiroDiv">
					<form id="buscaParceiroForm" class="form-search" action="<c:url value="/formulario/cliente" />" method="POST">
						<label for="numeroBeneficio">Busca Cliente</label>
						<div class="input-append">
							<input type="text" class="input-medium" id="numeroBeneficio" name="numeroBeneficio" placeholder="Benefício"/>
							<span class="add-on" onclick="submit();"><i class="icon-search"></i></span>
						</div>
					</form>
				</div>
				
				<div id="formCliente">
				
					<div class="row-fluid">
						<div class="span2">
							<label for="formularioParceiroNegocioNome">Nome</label>
							<input type="text" class="input-medium" id="formularioParceiroNegocioNome" name="formulario.parceiroNegocio.nome" value="${formulario.parceiroNegocio.nome }"/>	
						</div>
						<div class="span2">
							<label for="formularioParceiroNegocioCpf">Cpf</label>
							<input type="text" class="input-medium" id="formularioParceiroNegocioCpf" name="formulario.parceiroNegocio.cpf" value="${formulario.parceiroNegocio.cpf }" />
						</div>
						<div class="span2">
							<label for="parceiroBeneficioNumeroBeneficio">Beneficio</label>
							<input type="text" class="input-medium" id="parceiroBeneficioNumeroBeneficio" name="parceiroBeneficio.numeroBeneficio" value="${formulario.parceiroBeneficio.numeroBeneficio }" />
						</div>
						<div class="span2">
							<label for="formularioParceiroNegocioDataNascimento">Dt Nascimento</label>
							<input type="text" class="input-medium" id="formularioParceiroNegocioDataNascimento" name="formulario.parceiroNegocio.dataNascimento"  placeholder="Nasc." 
							value="<fmt:formatDate pattern="dd/MM/yyyy" value="${formulario.parceiroNegocio.dataNascimento.time }" />">
						</div>
						<div class="span2">
							<label for="parceiroLocalidadeLocalidadeCep">CEP</label>
							<input  class="input-medium" id="parceiroLocalidadeLocalidadeCep" name="parceiroLocalidade.localidade.cep" type="text" value="${formulario.parceiroLocalidade.localidade.cep }" />
						</div>
					</div>
				</div>
				<div id="formDadosPagamento">
					<div class="row-fluid">
						<div class="span2">
							<label for="Banco">Banco</label>
							<input type="text" class="input-medium" id="Banco" name="Banco" value="${formulario.parceiroInfoBanco.banco.nome }"/>	
						</div>
						<div class="span2">
							<label for="Agencia">Agencia</label>
							<input type="text" class="input-medium" id="Agencia" name="Agencia" value="${formulario.parceiroInfoBanco.agenciaNumero }" />
						</div>
						<div class="span2">
							<label for="Conta">Conta</label>
							<input type="text" class="input-medium" id="Conta" name="Conta" value="${formulario.parceiroInfoBanco.contaCorrente }" />
						</div>
						<div class="span2">
							<label for="TipoConta">Tipo Conta</label>
							<input type="text" class="input-medium" id="TipoConta" name="TipoConta" value="${formulario.parceiroInfoBanco.contaBancaria.tipoConta }" />
						</div>
						<div class="span2">
							<label for="TipoPagamento">Tipo Pagamento</label>
							<input  class="input-medium" id="TipoPagamento" name="TipoPagamento" type="text" value="${formulario.parceiroInfoBanco.meioPagamento.nome }" />
						</div>
					</div>
				</div>

				<c:if test="${not empty formulario.parceiroNegocio }">

					<div class="row-fluid">
						<div id="div-cadastro-contratos">
							<c:if test="${formulario.formulario_id == null}">
								<%@ include file="../contrato/cadastro.jsp" %> 
							</c:if>
						</div>
					</div>
					
					<div id="div-contratos" style="float: none;clear:both;">

						<table class="table table-striped table-bordered">
							<c:if test="${not empty formulario.contratos}">
							<thead>	
								<tr>
									<th colspan="13">Total de contratos</th>
								</tr>
								<tr>
									<th scope="col"></th>
									<th scope="col"></th>
									<th scope="col">Banco Comprado</th>
									<th scope="col">Parcela Aberto</th>
									<th scope="col">Contrato:</th>
									<th scope="col">Parcel:</th>
									<th scope="col">Prazo:</th>
									<th scope="col">Dívida</th>
									<th scope="col">Seguro</th>
									<th scope="col">Desconto</th>
									<th scope="col">Valor Liquido</th>
									<th scope="col">Coeficiente</th>
									<th scope="col">Observação</th>
								</tr>
							</thead>
							<tbody>		
								<c:forEach items="${formulario.contratos}" var="contrato">
									<tr>
										<td class="label_txt">${contrato.banco.nome }</td>
										<td class="label_txt">${contrato.produto.nome }</td>
										<td class="label_txt">${contrato.recompraBanco.nome }</td>
										<td class="label_txt">${contrato.qtdParcelasAberto }</td>
										<td class="label_txt">${contrato.valorContrato }</td>
										<td class="label_txt">${contrato.valorParcela }</td>
										<td class="label_txt">${contrato.prazo }</td>
										<td class="label_txt">${contrato.valorDivida }</td>
										<td class="label_txt">${contrato.valorSeguro }</td>
										<td class="label_txt">${contrato.desconto }</td>
										<td class="label_txt">${contrato.valorLiquido }</td>
										<td class="label_txt"><fmt:formatNumber type="number" pattern="#.#####" value="${contrato.coeficiente.valor }" /></td>
										<td class="label_txt" style="text-align: center">
											<c:if test="${not empty contrato.observacao}">
												<a href="#" onclick="return showObs('${contrato.observacao}');" style="border: 0"><img src="../img/lupa.gif" border="0"/></a>
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</tbody>	
							</c:if>
						</table>
					</div>

				</c:if>

				<div class="row-fluid">
					<div class="span3">
						<div class="btn-group">
							<form id="salvaForm" action="<c:url value="/formulario/salva"/>" method="POST">
								<button type="submit" class="btn btn-primary" id="btnNovo" onclick="return confirm('Deseja encerrar o cadastro de contratos?');" >Salvar</button>
							</form>
						</div>
						<div class="btn-group">
							<form action="<c:url value="/formulario/limpar"/>" method="POST" >
								<input type="submit" value="Limpar" class="btn"/>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>	
	</div>

<%@ include file="/footer.jspf"%>
