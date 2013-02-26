<%@ include file="/header.jspf" %>

<form id="colunaBdForm" name="colunaBdForm" action="<c:url value="/colunabd/salva" />" method="POST">

	<label for="colunaBdNome">Nome do coluna</label><br/>
	<input id="colunaBdNome" type="text" name="colunaBd.nome" value="${colunaBd.nome}"/><br/>

	<input type="hidden" value="${empresa.empresa_id}" name="colunaBd.empresa.empresa_id" />
	<input type="hidden" value="${organizacao.organizacao_id}" name="colunaBd.organizacao.organizacao_id" />
	<input type="hidden" value="${tabelaBd.tabelabd_id}" name="colunaBd.tabelaBd.tabelabd_id" />
	<input type="hidden" value="${tipoDadoBd.tipodadobd_id}" name="colunaBd.tipoDadoBd.tipodadobd_id" />
	<input type="hidden" value="${elementoBd.elementobd_id}" name="colunaBd.elementoBd.elementobd_id" />

	<br/><br/>
	<input type="submit" name="colunaBdButton" />

</form>

<%@ include file="/footer.jspf" %>