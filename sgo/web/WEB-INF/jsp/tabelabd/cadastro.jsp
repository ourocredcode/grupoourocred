<%@ include file="/header.jspf" %>

Cadastro Tabela BD

<br/><br/>

<form id="tabelaBdForm" name="tabelaBdForm" action="<c:url value="/tabelabd/salva" />" method="POST">

	<label class="control-label" for="tabelaBdNome"></label>	
	<input type="text" id="tabelaBdNome" placeholder="Nome" name="tabelaBd.nome" value="${tabelaBd.nome}">	
	<br/>
	<label for="elementoBdColunaBd"></label><br/>
	<input id="elementoBdColunaBd" type="text" name="elementoBd.nomeColunaBd" value="${tabelaBd.nometabelabd}"/><br/>
	
	<input type="hidden" value="${empresa.empresa_id}" name="tabelaBd.empresa.empresa_id" />
	<input type="hidden" value="${organizacao.organizacao_id}" name="tabelaBd.organizacao.organizacao_id" />

	<input type="submit" name="tabelaBdButton" />

</form>

<%@ include file="/footer.jspf" %> 