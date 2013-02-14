<%@ include file="/header.jspf" %>

Cadastro Elemento BD

<br/><br/>

<form id="elementoBdForm" name="elementoBdForm" action="<c:url value="/elementobd/salva" />" method="POST">

	<label for="elementoBdNome">Nome do Elemento</label><br/>
	<input id="elementoBdNome" type="text" name="elementoBd.nome" value="${elementoBd.nome}"/><br/>
	
	<label for="elementoBdColunaBd">Nome da Coluna Banco</label><br/>
	<input id="elementoBdColunaBd" type="text" name="elementoBd.nomeColunaBd" value="${elementoBd.nomecolunabd}"/><br/>

	<label for="elementoBdDescricao">Descrição</label><br/>
	<input id="elementoBdDescricao" type="text" name="elementoBd.descricao" value="${elementoBd.descricao}"/><br/>
	
	<input type="hidden" value="${empresa.empresa_id}" name="elementoBd.empresa.empresa_id" />
	<input type="hidden" value="${organizacao.organizacao_id}" name="elementoBd.organizacao.organizacao_id" />

	<input type="submit" name="elementoBdButton" />

</form>

<%@ include file="/footer.jspf" %> 