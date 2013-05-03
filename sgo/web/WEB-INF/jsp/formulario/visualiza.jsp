<%@ include file="/header.jspf" %> 

<script type="text/javascript">

	</script>

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
							<input type="text" class="input-medium" id="parceiroBeneficioNumeroBeneficio" name="parceiroBeneficio.numeroBeneficio" value="${parceiroBeneficio.numeroBeneficio }" />
						</div>
						<div class="span2">
							<label for="formularioParceiroNegocioDataNascimento">Dt Nascimento</label>
							<input type="text" class="input-medium" id="formularioParceiroNegocioDataNascimento" name="formulario.parceiroNegocio.dataNascimento"  placeholder="Nasc." 
							value="<fmt:formatDate pattern="dd/MM/yyyy" value="${formulario.parceiroNegocio.dataNascimento.time }" />">
						</div>
						<div class="span2">
							<label for="parceiroLocalidadeLocalidadeCep">CEP</label>
							<input  class="input-medium" id="parceiroLocalidadeLocalidadeCep" name="parceiroLocalidade.localidade.cep" type="text" value="${parceiroLocalidade.localidade.cep }" />
						</div>
					</div>

				</div>

				<div id="formDadosPagamento">
					<div class="row-fluid">
						<div class="span2">
							<label for="Banco">Banco</label>
							<input type="text" class="input-medium" id="Banco" name="Banco" value="${parceiroInfoBanco.banco.nome }"/>	
						</div>
						<div class="span2">
							<label for="Agencia">Agencia</label>
							<input type="text" class="input-medium" id="Agencia" name="Agencia" value="${parceiroInfoBanco.agencia.nome }" />
						</div>
						<div class="span2">
							<label for="Conta">Conta</label>
							<input type="text" class="input-medium" id="Conta" name="Conta" value="${parceiroInfoBanco.contaBancaria }" />
						</div>
						<div class="span2">
							<label for="TipoConta">Tipo Conta</label>
							<input type="text" class="input-medium" id="TipoConta" name="TipoConta" value="${parceiroInfoBanco.contaBancaria.tipoConta }" />
						</div>
						<div class="span2">
							<label for="TipoPagamento">Tipo Pagamento</label>
							<input  class="input-medium" id="TipoPagamento" name="TipoPagamento" type="text" value="${parceiroInfoBanco.meioPagamento }" />
						</div>
					</div>
				</div>

				<div id="div-contratos" style="float: none;clear:both;">

					<table class="table table-striped table-bordered">
						<c:if test="${not empty formulario.contratos}">
						<thead>	
							<tr>
								<th colspan="14">
									Total de contratos 
								</th>
							</tr>
							<tr>
								<th scope="col">
									Banco
								</th>
								<th scope="col">
									Produto
								</th>
								<th scope="col">
									Banco Comprado
								</th>
								<th scope="col">
									Parcela Aberto
								</th>
								<th scope="col">
									Contrato:
								</th>
								<th scope="col">
									Parcel:
								</th>
								<th scope="col">
									Prazo:
								</th>
								<th scope="col">
									Dívida
								</th>
								<th scope="col">
									Seguro
								</th>
								<th scope="col">
									Desconto
								</th>
								<th scope="col">
									Valor Liquido
								</th>
								<th scope="col">
									Coeficiente
								</th>
								<th scope="col">
									Observação
								</th>
									<th scope="col">
									Status
								</th>
							</tr>
						</thead>
						<tbody>		
							<c:forEach items="${formulario.contratos}" var="contrato">
								<tr>
									<td>
										${contrato.banco.nome }
									</td>
									<td>
										${contrato.produto.nome }
									</td>
									<td>
										${contrato.recompraBanco.nome }
									</td>
									<td>
										${contrato.qtdParcelasAberto }
									</td>
									<td>
										${contrato.valorContrato }
									</td>
									<td>
										${contrato.valorParcela }
									</td>
									<td>
										${contrato.prazo }
									</td>
									<td>
										${contrato.valorDivida }
									</td>
									<td>
										${contrato.valorSeguro }
									</td>
									<td>
										${contrato.desconto }
									</td>
									<td>
										${contrato.valorLiquido }
									</td>
									<td>
										<fmt:formatNumber type="number" pattern="#.#####" value="${contrato.coeficiente.valor }" />
									</td>
									<td class="label_txt" style="text-align: center">
										<c:if test="${not empty contrato.observacao}">
											<a href="#" onclick="return showObs('${contrato.observacao}');" style="border: 0"><img src="../img/lupa.gif" border="0"/></a>
										</c:if>
									</td>
									<td>
										<a href="<c:url value="/contrato/status/${contrato.contrato_id}"/>">${contrato.workflowEtapa.nome }</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>	
						</c:if>
					</table>
				</div>

				<div class="row-fluid">
					<div class="span1" style="float: left;">
						<input value="Voltar" type="button" class="btn" onclick="javascript:window.location='/sgo/menu/inicio'">
					</div>
					<div class="span1" style="float: left;">
						<input value="Imprimir" type="button" class="btn" onclick="javascript:window.location='/sgo/formulario/impressao/${formulario.formulario_id}'">
					</div>
  
				</div>

			</div>
		</div>	
	</div>


<%@ include file="/footer.jspf" %> 