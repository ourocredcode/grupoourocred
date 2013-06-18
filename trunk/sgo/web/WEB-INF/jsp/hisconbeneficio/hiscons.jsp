<%@ include file="/header.jspf"%>

	<script type="text/javascript">
 
	

	 </script>

	<div id="content-header">
		<h1>DashBoard</h1>
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
				<div class="buttons"><a href="javascript:${function }" class="btn btn-mini"><i class="icon-refresh"></i> Busca</a></div>
			</div>
			
			<input type="hidden" id="busca_Tipo" name="busca_Tipo" value="${tipobusca }"/>
			
			
			<div class="widget-content" style="padding: 8px;">
				<div class="row-fluid">

					<div class="span2">

						<label for="busca_Status">Status</label>
	  					<select id="busca_Status" name="busca_Status" class="input-medium" MULTIPLE >
							<option value="">Todos...</option>
							<c:forEach items="${etapas }" var="etapa">
								<option value="${etapa.nome }">${etapa.nome }</option>
							</c:forEach>
						</select>
						
						<label for="busca_Produto">Produto</label>
						<select id="busca_Produto" name="busca_Produto" class="input-medium" MULTIPLE >
							<option value="Todos">Todos</option>
							<c:forEach items="${produtos }" var="produto">
								<option value="${produto.nome }">${produto.nome }</option>
							</c:forEach>
						</select>

						<label for="busca_Empresa">Empresa</label>
						<select id="busca_Empresa" name="busca_Empresa" class="input-medium" MULTIPLE>
							<option value="Todos">Todos</option>
							<option value="ATGGOLD">ATGGOLD</option>
							<option value="GOCX">GOCX</option>
							<option value="GRGOLD">GRGOLD</option>
							<option value="OUROCRED">OUROCRED</option>
						</select>

						<label for="busca_Banco">Banco</label>
						<select id="busca_Banco" name="busca_Banco"  class="input-medium" MULTIPLE>
							<option value="">Selecione um banco</option>
							<c:forEach items="${bancos }" var="banco">
								<option value="${banco.nome }">${banco.nome }</option>
							</c:forEach>
						</select>
						
						<label for="busca_BancoComprado">Banco Comprado</label>
						<select id="busca_BancoComprado" name="busca_BancoComprado"  class="input-medium" MULTIPLE>
							<option value="">Selecione um banco</option>
							<c:forEach items="${bancosComprados }" var="bancoComprado">
								<option value="${bancoComprado.nome }">${bancoComprado.nome }</option>
							</c:forEach>
						</select>
	  				</div>

	 				<div class="span3">
	
						<label for="busca_Cliente">Cliente</label>
						<input id="busca_Cliente" name="busca_Cliente" class="input-large" type="text" />
		
						<label for="busca_Documento">Documento</label>
						<input id="busca_Documento" name="busca_Documento" class="input-large" type="text" />
						
						<div class="row-fluid">
		
							<div class="span6">								
								<label for="busca_Data">Data Início</label>
								<input id="busca_Data" name="busca_Data"  class="input-small" type="text" />
							</div>
							<div class="span6">
								<label for="busca_DataFim">Data Fim</label>
								<input id="busca_DataFim" name="busca_DataFim" class="input-small" type="text"  />
							</div>
		
						</div>
						
						<div class="row-fluid">
		
							<div class="span6">								
								<label for="busca_Supervisor">Supervisor</label>
								<select id="busca_Supervisor" name="busca_Supervisor" class="input-small">
									<option value="">Todos</option>
									<c:forEach items="${supervisores}" var="supervisor">
										<option value="${supervisor.usuario_id}">${supervisor.nome}</option>
									</c:forEach>
								</select>
							</div>
							<div class="span6">
								<label for="busca_Consultor">Consultor</label>
								<select id="busca_Consultor" name="busca_Consultor" class="input-small">
									<option value="">Selecione um Supervisor</option>
								</select>
							</div>

						</div>
					</div>

	
					<div id="consultaBoleto" class="span7" style="display: ${buscaBoleto};">

						<div class="span2">
							<div class="control-group">
								<label class="control-label">Prev Início</label>
								<div class="controls">
									<input id="busca_DataPrevisaoInicio" name="busca_DataPrevisaoInicio" type="text" class="input-small"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Chegada Início</label>
								<div class="controls">
									<input id="busca_DataChegadaInicio" name="busca_DataChegadaInicio" type="text" class="input-small"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Procedimento</label>
								<select  id="busca_Procedimento" name="busca_Procedimento" class="input-medium">
									<option value="">Apoio Agregado</option>
									<option value="">Apoio Agregado</option>
									<option value="">Apoio Agregado</option>
									<option value="">Apoio Agregado</option>
									<option value="">Apoio Agregado</option>
									<option value="">Apoio Agregado</option>
								</select>
							</div>
						</div>
						<div class="span2">
							<div class="control-group">
								<label class="control-label">Prev Fim</label>
								<div class="controls">
									<input id="busca_DataPrevisaoFim" name="busca_DataPrevisaoFim" type="text" class="input-small"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Chegada Fim</label>
								<div class="controls">
									<input id="busca_DataChegadaInicio" name="busca_DataChegadaInicio" type="text" class="input-small"/>
								</div>
							</div>
						</div>
						<div class="span2">
							<div class="control-group">
								<label class="control-label">Prox Atua Início</label>
								<div class="controls">
									<input id="busca_DataProximaAtuacaoInicio" name="busca_DataProximaAtuacaoInicio" type="text" class="input-small"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Venc. Início</label>
								<div class="controls">
									<input id="busca_DataVencimentoInicio" name="busca_DataVencimentoInicio" type="text" class="input-small"/>
								</div>
							</div>
						</div>
						<div class="span2">
							<div class="control-group">
								<label class="control-label">Prox Atua Fim</label>
								<div class="controls">
									<input id="busca_DataProximaAtuacaoFim" name="busca_DataProximaAtuacaoFim" type="text" class="input-small"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Venc. Fim</label>
								<div class="controls">
									<input id="busca_DataVencimentoFim" name="busca_DataVencimentoFim" type="text" class="input-small"/>
								</div>
							</div>
						</div>
					</div>

			
					<div class="span4"  style="display: ${buscaAprovado};">

							<div class="row-fluid">
			
								<div class="span4">								
									<label for="busca_DataAprovadoInicio">Aprovado Inicio</label>
									<input id="busca_DataAprovadoInicio" name="busca_DataAprovadoInicio"  class="input-small" type="text" />
								</div>
								<div class="span4">
									<label for="busca_DataAprovadoFim">Aprovado Fim</label>
									<input id="busca_DataAprovadoFim" name="busca_DataAprovadoFim" class="input-small" type="text"  />
								</div>
								<div class="span4">
									<label for="busca_TipoAprovado">Tipo Aprovado</label>
									<select id="busca_TipoAprovado" name="busca_TipoAprovado" class="input-small">
										<option value="Todos">Todos</option>
										<option value="Aprovado">Aprovado</option>
										<option value="Concluído">Concluído</option>
									</select>
								</div>
			
							</div>
							
							<div class="row-fluid">
			
								<div class="span4">								
									<label for="busca_DataConcluidoInicio">Concluído Início</label>
									<input id="busca_DataConcluidoInicio" name="busca_DataConcluidoInicio"  class="input-small" type="text" />
								</div>
								<div class="span4">
									<label for="busca_DataConcluidoFim">Concluído Fim</label>
									<input id="busca_DataConcluidoFim" name="busca_DataConcluidoFim" class="input-small" type="text"  />
								</div>
			
							</div>
							
							<div class="row-fluid">
			
								<div class="span4">								
									
									<label for="busca_TipoPagamento">Tipo Pagamento</label>
									<select id="busca_TipoPagamento" name="busca_TipoPagamento" style="width:100px" >
										<option value="Todos">Todos</option>
										<option value="OP">OP</option>
										<option value="TED">TED</option>
									</select>
									
								</div>
								<div class="span4">
									
									<label for="busca_InformacaoSaque">Info Saque</label>
									<select id="busca_InformacaoSaque" name="busca_InformacaoSaque" style="width:100px" >
										<option value="Todos">Todos</option>
										<option value="Aguardando Saque">Aguardando Saque</option>
										<option value="Saque Efetuado">Saque Efetuado</option>
									</select>
									
								</div>
			
							</div>

					</div>
					
					<div class="span3"  style="display: ${buscaAprovado};">
					
						<div class="row-fluid">
		
							<div class="span5">								
								<label for="busca_DataRecusadoInicio">Recusado Início</label>
								<input id="busca_DataRecusadoInicio" name="busca_DataRecusadoInicio"  class="input-small" type="text" />
							</div>
							<div class="span5">
								<label for="busca_DataRecusadoFim">Recusado Fim</label>
								<input id="busca_DataRecusadoFim" name="busca_DataRecusadoFim" class="input-small" type="text"  />
							</div>
		
						</div>
						
						<div class="row-fluid">
		
							<div class="span5">
								<label for="busca_TipoRecusado">Tipo Recusa</label>								
								<select id="busca_TipoRecusado" name="busca_TipoRecusado" class="input-small" >
									<option value="Todos">Todos</option>
									<option value="false">Dentro da Planilha</option>
									<option value="true">Fora da Planilha</option>
								</select>
							</div>
							<div class="span5">
								<label for="busca_Justificativa">Justificativa</label>
								<select id="busca_Justificativa" name="busca_Justificativa" class="input-small">
									<option value="Todos">Todos</option>
									<option value="Cliente com pendência de documentos">Cliente com pendência de documentos</option>
									<option value="Cliente desistiu">Cliente desistiu</option>
									<option value="Cliente fechou por outro banco">Cliente fechou por outro banco</option>
									<option value="Cliente inadimplente">Cliente inadimplente</option>
									<option value="Cliente não assinou">Cliente não assinou</option>
									<option value="Cliente não retirou o boleto">Cliente não retirou o boleto</option>
									<option value="Cliente refinanciou">Cliente refinanciou</option>
									<option value="Dívida maior que o previsto">Dívida maior que o previsto</option>
									<option value="Erro de análise">Erro de análise</option>
									<option value="Erro de preenchimento">Erro de preenchimento</option>
									<option value="Família não deixou">Família não deixou</option>
									<option value="Filho não deixou">Filho não deixou</option>
									<option value="Junção de Parcelas">Junção de Parcelas</option>
									<option value="Operador trocou de equipe">Operador trocou de equipe</option>
									<option value="Recusado Banco">Recusado Banco</option>
									<option value="Recusado Qualidade">Recusado Qualidade</option>
									<option value="Recusado Pós Venda">Recusado Pós Venda</option>
								</select>
							</div>
						</div>
					</div>
				</div>
			</div>
						

			<!-- FIM -->
			</div>
		</div>
		</div>
		</div>
	
	

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">

				<div class="widget-box">
					<div class="widget-title"><span class="icon"><i class="icon-signal"></i></span><h5>HISCONS</h5></div>
					<div id="resultado" class="widget-content">
							
							RESULTADO	
											
					</div>
				</div>
			</div>
		</div>
	</div>	


<%@ include file="/footer.jspf"%>
