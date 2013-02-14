<%@ include file="/header.jspf" %>

Empresa : ${empresa.nome } <br/>
Organização : ${organizacao.nome } <br/>

<br/><br/>

Cadastro Usuário

<br/><br/>

<form id="usuarioForm" name="usuarioForm" action="<c:url value="/usuario/salva" />" method="POST">

	<label for="usuarioNome">Nome </label><br/>
	<input id="usuarioNome" type="text" name="usuario.nome" value="${usuario.nome}"/><br/>

	<label for="usuarioEmail">Email</label><br/>
	<input id="usuarioEmail" type="text" name="usuario.email" value="${usuario.email}"/><br/>

	<label for="usuarioSenha">Senha</label><br/>
	<input id="usuarioSenha" type="password" name="usuario.senha" value="${usuario.senha}"/><br/>

	<label for="usuarioTelefone">Telefone</label><br/>
	<input id="usuarioTelefone" type="text" name="usuario.telefone" value="${usuario.telefone}"/><br/>

	<input type="hidden" value="${empresa.empresa_id}" name="usuario.empresa.empresa_id" />
	<input type="hidden" value="${organizacao.organizacao_id}" name="usuario.organizacao.organizacao_id" />

	<input type="submit" name="usuarioBdButton" />

</form>

<%@ include file="/footer.jspf" %> 