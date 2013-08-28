<%@ include file="/header.jspf"%>

<script type="text/javascript">

function altera(linha, atributo, funcionario_id, valor) {

	if(atributo == 'exclui'){
		if (window.confirm("Deseja realmente excluir o consultor selecionado?"))
			$.post('<c:url value='/funcionario/altera' />'
			, { 'funcionario.isActive' : valor , 'funcionario.funcionario_id' : funcionario_id}
			, function(resposta) { excluiLinha(linha, resposta); });
	}

	if(atributo == 'supervisor'){
		if (window.confirm("Deseja realmente alterar o consultor selecionado?"))
			$.post('<c:url value='/funcionario/altera' />'
			, { 'funcionario.supervisorFuncionario.funcionario_id' : valor , 'funcionario.funcionario_id' : funcionario_id}
			, function(resposta) { excluiLinha(linha, resposta); });
	}
	
	if(atributo == 'apelido'){
		if (window.confirm("Deseja realmente alterar o nome do consultor?"))
			$.post('<c:url value='/funcionario/altera' />'
			, { 'funcionario.apelido' : valor , 'funcionario.funcionario_id' : funcionario_id}
			, function(resposta) { alert(resposta); });
	}

	return false;

}
	
function excluiLinha(linha, resposta) {
	
	var objTR = linha.parentNode.parentNode;
	var objTable = objTR.parentNode;

	objTable.deleteRow(objTR.rowIndex - 1);

	return false;
}



</script>

<div id="content-header">
		<h1>Equipe - ${usuarioInfo.usuario.nome }</h1>
		<div class="btn-group">
			<a class="btn btn-large tip-bottom" title="Manage Files"><i class="icon-file"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Users"><i class="icon-user"></i></a>
			<a class="btn btn-large tip-bottom" title="Manage Comments"><i class="icon-comment"></i><span class="label label-important">5</span></a>
			<a class="btn btn-large tip-bottom" title="Manage Orders"><i class="icon-shopping-cart"></i></a>
		</div>
	</div>
	
	<div id="breadcrumb">
		<a href="<c:url value="/menu/inicio/${usuarioInfo.perfil.chave}" />" title="Dashboard" class="tip-bottom"><i class="icon-home"></i> Dashboard</a>
		<a href="#" class="current">Equipe</a>
	</div>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">

			<table class="table table-bordered table-striped table-hover data-table" style="font-size: 12px">
				<thead>	
					<tr>
						<th>
							Login/CPF
						</th>
						<th>
							Nome
						</th>
						<th>
							Apelido
						</th>
						<th>
							Supervisor
						</th>
						<th>
							Excluir
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${funcionarios }" var="funcionario">
						<tr>
							<td>
								${funcionario.parceiroNegocio.cpf }
							</td>
							<td>
								${funcionario.nome }
							</td>
							<td>
								<input type="text" name="funcionario.apelido" value="${funcionario.apelido }" class="input-xlarge" onChange="return altera(this,'apelido','${funcionario.funcionario_id }', this.value);" />
							</td>
							<td>
								<select name="funcionario.supervisorFuncionario.funcionario_id" onChange="return altera(this,'supervisor','${funcionario.funcionario_id }', this.value);" >
									<c:forEach items="${supervisores }" var="supervisor">
										<option value="${supervisor.funcionario_id }" 
											<c:if test="${funcionario.supervisorFuncionario.funcionario_id eq supervisor.funcionario_id }">selected="selected"</c:if>>${supervisor.nome }</option>
									</c:forEach>
								</select>
							</td>
							<td style="text-align: center;">
								<a href="#" onClick="return altera(this,'exclui','${funcionario.funcionario_id }','0');">X</a>
							</td>
						</tr>					

					</c:forEach>
				</tbody>						
			</table>

		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>
