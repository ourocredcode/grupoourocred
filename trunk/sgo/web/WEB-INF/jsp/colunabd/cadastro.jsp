<%@ include file="/header.jspf" %>

Cadastro Coluna BD

<br/><br/>

<form id="colunaBdForm" name="colunaBdForm" action="<c:url value="/colunabd/salva" />" method="POST">

	<label for="colunaBdNome">Nome do coluna</label><br/>
	<input id="colunaBdNome" type="text" name="colunaBd.nome" value="${colunaBd.nome}"/><br/>

	<input type="hidden" value="${empresa.empresa_id}" name="colunaBd.empresa.empresa_id" />
	<input type="hidden" value="${organizacao.organizacao_id}" name="colunaBd.organizacao.organizacao_id" />
	<input type="hidden" value="${tabela.tabela_id}" name="colunaBd.tabela.organizacao_id" />
	<input type="hidden" value="${tipoDado.tipoDado_id}" name="colunaBd.tipoDado.tipoDado_id" />

	<input type="submit" name="colunaBdButton" />

</form>

<%@ include file="/footer.jspf" %> 