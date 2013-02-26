<%@ include file="/header.jspf"%>

<div class="span9">

	<section id="tabs">
		<div class="bs-docs-example">

			<ul id="myTab" class="nav nav-tabs">
				<li class="active">
					<a href="#login" data-toggle="tab">Login</a>
				</li>
				<li class="">
					<a href="#perfil" data-toggle="tab">Perfil</a>
				</li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="login">

					<p></p>

					<form id="loginForm" action="<c:url value="/home/login"/>" method="POST">					
						<div class="control-group">
							<label class="control-label" for="login">Login</label>
							<div class="controls">
								<input type="text" id="login" name="login" placeholder="Digite seu CPF" required>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="password">Senha</label>
							<div class="controls">
								<input type="password" id="password" name="password" placeholder="Senha" required>
							</div>
							<div class="control-group">
								<div class="controls">
									<p></p>
									<button type="submit" class="btn btn-primary">OK</button>
								</div>
							</div>
						</div>
					</form>

				</div>

				<div class="tab-pane fade"  id="perfil">
					<p></p>
					<div class="control-group">
						<label class="control-label" for="inputEmail">Perfil</label>
						<div class="controls">
							<input type="text" id="perfil_id"
								placeholder="Selecione o Perfil">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="inputEmail">Empresa</label>
						<div class="controls">
							<input type="text" id="empresa_id"
								placeholder="Selecione a Empresa">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="inputEmail">Organiza&ccedil;&atilde;o</label>
						<div class="controls">
							<input type="text" id="organizacao_id"
								placeholder="Selecione a Organiza&ccedil;&atilde;o">
						</div>
						<div class="control-group">
							<div class="controls">
								<p></p>
								<button type="submit" class="btn btn-primary">OK</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>

<%@ include file="/footer.jspf"%>
