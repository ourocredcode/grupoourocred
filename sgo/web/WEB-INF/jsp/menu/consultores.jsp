<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${consultores == null}">
	<option value="">Selecione um Supervisor</option>
</c:if>

<c:if test="${consultores != null}">
	<option value="">Selecione um Consultor</option>
	<c:forEach var="consultor" items="${consultores}">
		<option value="${consultor.login}">${consultor.nome}</option>
	</c:forEach>
</c:if>








