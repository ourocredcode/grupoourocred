<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${perfis == null}">
	<option value="">Selecione o Perfil</option>
</c:if>

<c:if test="${perfis != null}">
	<option value="">Selecione o Perfil</option>
	<c:forEach items="${perfis }" var="perfil">
		<option value="${perfil.perfil_id }">${perfil.nome}</option>
	</c:forEach>
</c:if>