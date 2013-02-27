<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${perfis == null}">
	<option value="">Selecione um Perfil</option>
</c:if>

<c:if test="${perfis != null}">
	<option value="">Selecione o perfil</option>

	<c:forEach var="perfil" items="${perfis}">
	<option value="${perfil.perfil_id}">${perfil.nome}</option>
	</c:forEach>

</c:if>