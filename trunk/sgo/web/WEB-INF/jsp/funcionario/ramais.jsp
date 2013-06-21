<%@ include file="/header.jspf"%>

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
							Supervisor
						</th>
						<th>
							Nome
						</th>
						<th>
							Login/CPF
						</th>
						<th>
							Apelido
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${funcionarios }" var="funcionario">
						<tr>
							<td>
								${funcionario.supervisor.nome}
							</td>
							<td>
								${funcionario.parceiroNegocio.nome }
							</td>
							<td>
								${funcionario.parceiroNegocio.cpf }
							</td>
							<td>
								${funcionario.apelido }
							</td>
							
						</tr>					

					</c:forEach>
				</tbody>						
			</table>

		</div>
	</div>
</div>

<%@ include file="/footer.jspf"%>
