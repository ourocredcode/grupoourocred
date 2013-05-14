<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table class="table table-striped table-bordered">
	<thead>
		<tr>
			<th>Empresa</th>
			<th>Organiza��o</th>
			<th>Worklflow</th>
			<th>Worklflow Etapa</th>
			<th>Leitura e Escrita</th>
			<th>Ativo</th>
		</tr>
	</thead>
	<tbody>	
		<c:forEach items="${workflowEtapasPerfilAcesso}" var="workflowEtapaPerfilAcesso">
			<tr>
				<td>${workflowEtapaPerfilAcesso.empresa.nome }</td>
				<td>${workflowEtapaPerfilAcesso.organizacao.nome }</td>
				<td>${workflowEtapaPerfilAcesso.workflowEtapa.nome }</td>
				<td>${workflowEtapaPerfilAcesso.perfil.nome }</td>								
				<td>${workflowEtapaPerfilAcesso.isLeituraEscrita }</td>
				<td>${workflowEtapaPerfilAcesso.isActive }</td>									
			</tr>
		</c:forEach>
	</tbody>
</table>