<%@ include file="/header.jspf" %> 


CADASTRO DE PERFIL USUÁRIO 
<br/><br/>

	<form id="usuarioForm" name="usuarioForm" action="<c:url value="/usuario/salva" />" method="POST">

		<label for="usuarioNome">Usuario </label><br/>
		<input id="usuarioNome" type="text" name="usuario.nome" value="${usuario.nome}"/><br/>

		Selecione o Peril

		<select>
			<option>PERFIL 1</option>
			<option>PERFIL 2</option>
			<option>PERFIL 3</option>
		</select>

		<br/><br/>
		<input type="submit" name="usuarioBdButton" />

	</form>

<%@ include file="/footer.jspf" %> 