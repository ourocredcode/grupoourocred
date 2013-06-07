<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<table class="table table-striped table-bordered">
	<thead>
		<tr>
			<th>Empresa</th>
			<th>Organização</th>
			<th>Perfil</th>
			<th>Supervisor Usuário</th>
			<th>Ativo</th>			
		</tr>
	</thead>
	<tbody>	
		<c:forEach items="${perfis }" var="perfil">
			<tr>
				<td>${perfil.empresa.nome }</td>
				<td>${perfil.organizacao.nome }</td>
				<td>${perfil.nome }</td>
				<td>${perfil.supervisorUsuario.nome }</td>				
				<td>${perfil.isActive }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>