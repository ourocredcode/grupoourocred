<%@ include file="/header.jspf" %> 

<form id="loginForm" action="<c:url value="/home/login"/>" method="POST" style="margin-top: 35px;margin-left: 20px;">
	<table style="width: 250px">
		<tr>
			<td>
				<label for="login" class="label_txt">Login</label>
			</td>	
		</tr>
		<tr>
			<td>
				<input id="login" type="text" name="login" class="required" minlength="1"  maxlength="20" />
			</td>
		</tr>
		<tr>
			<td>
				<label for="password" class="label_txt">Senha</label>
			</td>
		</tr>
		<tr>
			<td>
				<input id="password" type="password" name="password" class="required" minlength="1"  maxlength="20"/>
			</td>
		</tr>
		<tr>
			<td>
				<button type="submit" class="form_button" >Entrar</button>
				<button type="button" class="form_button" onclick="window.location='../consultor/formularioSenha' ">Alterar Senha</button>
			</td>
		</tr>
	</table>
</form>

<script type="text/javascript">
	$('#loginForm').validate();
</script>

<%@ include file="/footer.jspf" %> 