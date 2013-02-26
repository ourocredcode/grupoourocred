<%@ include file="/header.jspf" %>

<form id="perfilForm" name="perfilForm" action="<c:url value="/perfil/salva" />" method="POST">

	<label for="perfilNome">Nome do Perfil</label><br/>
	<input id="perfilNome" type="text" name="perfil.nome" value="${perfil.nome}"/><br/>
	
	<label for="perfilDescricao">Descrição do Perfil</label><br/>
	<input id="perfilDescricao" type="text" name="perfil.descricao" value="${perfil.descricao}"/><br/>

	<input type="hidden" value="${empresa.empresa_id}" name="perfil.empresa.empresa_id" />
	<input type="hidden" value="${organizacao.organizacao_id}" name="perfil.organizacao.organizacao_id" />

	<br/><br/>
	<input type="submit" name="colunaBdButton" />

</form>

<%@ include file="/footer.jspf" %>