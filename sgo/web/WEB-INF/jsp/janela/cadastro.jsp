<%@ include file="/header.jspf" %>

<form id="janelaForm" name="janelaForm" action="<c:url value="/janela/salva" />" method="POST">

	<label for="janelaNome">Nome da janela</label><br/>
	<input id="janelaNome" type="text" name="janela.nome" value="${janela.nome}"/><br/>

	<input type="hidden" value="${empresa.empresa_id}" name="janela.empresa.empresa_id" />
	<input type="hidden" value="${organizacao.organizacao_id}" name="janela.organizacao.organizacao_id" />


	<br/><br/>
	<input type="submit" name="colunaBdButton" />

</form>

<%@ include file="/footer.jspf" %>
