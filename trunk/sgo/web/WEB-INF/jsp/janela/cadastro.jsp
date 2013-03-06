<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#perfil-li-a').click(function() {
		window.location.href = '<c:url value="/perfil/cadastro" />';
	});

	$('#perfilorganizacao-li-a').click(function() {
		window.location.href = '<c:url value="/perfil/cadastro" />';
	});
	
	$('#janela-li-a').click(function() {
		window.location.href = '<c:url value="/janela/cadastro" />';
	});
	
	$('#perfiljanelaacesso-li-a').click(function() {
		window.location.href = '<c:url value="/perfiljanelaacesso/cadastro" />';
	});
	
	$('#formulariosjanela-li-a').click(function() {
		window.location.href = '<c:url value="/formulariosjanela/cadastro" />';
	});
	
	$('#campoformulario-li-a').click(function() {
		window.location.href = '<c:url value="/campoformulario/cadastro" />';
	});

	$('#tabelabd-li-a').click(function() {
		window.location.href = '<c:url value="/tabelabd/cadastro" />';
	});
	$('#colunabd-li-a').click(function() {
		window.location.href = '<c:url value="/colunabd/cadastro" />';
	});
	$('#elementobd-li-a').click(function() {
		window.location.href = '<c:url value="/elementobd/cadastro" />';
	});
	$('#tipodadobd-li-a').click(function() {
		window.location.href = '<c:url value="/tipodadobd/cadastro" />';
	});

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/colunabd/cadastro" />';
	});

});

function limpaForm(){

	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.colunaBdForm.reset();
	}

}


</script>

<div class="span9">

	<section id="tabs">
		<div class="bs-docs-example">

			<ul id="myTab" class="nav nav-tabs">
				<li class="" id="perfil-li"><a href="#perfil-div" data-toggle="tab" id="perfil-li-a">Perfil</a></li>
				<li class="" id="perfilorganizacao-li"><a href="#perfilperfilorganizacao-div" data-toggle="tab" id="perfilorganizacao-li-a">Perfil Organização</a></li>
				<li class="active" id="janela-li"><a href="#janela-div" data-toggle="tab" id="janela-li-a">Janela</a></li>
				<li class="" id="perfiljanelaacesso-li"><a href="#perfiljanelaacesso-div" data-toggle="tab" id="perfiljanelaacesso-li-a">Janela Perfil</a></li>
				<li class="" id="formulariosjanela-li"><a href="#formulariosjanela-div" data-toggle="tab" id="formulariosjanela-li-a">Formulários Janela</a></li>
				<li class="" id="campoformulario-li"><a href="#campoformulario-div" data-toggle="tab" id="campoformulario-li-a">Campo Formulário</a></li>
				<li class="" id="tabelabd-li"><a href="#tabelabd-div" data-toggle="tab" id="tabelabd-li-a">Tabela BD</a></li>
				<li class="" id="colunabd-li"><a href="#colunabd-div" data-toggle="tab" id="colunabd-li-a">Coluna BD</a></li>	
				<li class="" id="elementobd-li"><a href="#elementobd-div" data-toggle="tab" id="elementobd-li-a">Elemento BD</a></li>
				<li class="" id="tipodadobd-li"><a href="#tipodadobd-div" data-toggle="tab" id="tipodadobd-li-a">TipoDado BD</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">
			
				<div class="tab-pane fade" id="perfil-div">					

				</div>
				
				<div class="tab-pane fade" id="perfilorganizacao-div">					

				</div>
			
				<div class="tab-pane fade active in" id="janela-div">
					
					<form id="janelaForm" name="janelaForm" action="<c:url value="/janela/salva"/>" method="POST">

						<div class="control-group">
							<label class="control-label" for="janelaEmpresa">Empresa</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="janelaEmpresa" name="janelaEmpresa.empresa.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="janelaEmpresaId" name="janelaEmpresa.empresa.empresa_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="janelaOrganizacao">Organização</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="janelaOrganizacao" name="janela.organizacao.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="janelaOrganizacaoId" name="janela.organizacao.organizacao_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="janelaImagem">Imagem</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="janelaImagem" name="janela.imagem.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="janelaImagemId" name="janela.imagem.imagem_id" type="hidden">
	    					</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="janelaCor">Cor</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="janelaCor" name="janela.cor.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="janelaCorId" name="janela.cor.cor_id" type="hidden">
	    					</div>
						</div>						
						
						<div class="control-group">
							<label class="control-label" for="janelaChave">Chave</label>
							<div class="controls">
								<input type="text" id="janelaChave" name="janelaChave.chave" placeholder="Chave" required>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="janelaNome">Nome</label>
							<div class="controls">
								<input type="text" id="janelaNome" name="janelaNome.nome" placeholder="Nome" required>
							</div>
						</div>						
						<div class="control-group">
							<label class="control-label" for="formularioJanelaIsActive">Ativo</label>
							<div class="controls">
								<input type="checkbox" id="formularioJanelaIsActive" name="formularioJanelaIsActive.isactive">							
							</div>							
						</div>
						<div class="btn-toolbar">
							<div class="btn-group">
								<button type="submit" class="btn btn-primary" id="btnSalvar">Salvar</button>
							</div>	
							<div class="btn-group">
								<button type="button" class="btn btn-primary" id="btnNovo" >Novo</button>
							</div>
							<div class="btn-group">
								<button type="button" class="btn btn-primary" id="btnSair" >Sair</button>
							</div>
						</div>

					</form>

				</div>
				
				<div class="tab-pane fade" id="perfiljanelaacesso-div">
					
				</div>

				<div class="tab-pane fade" id="formulariosjanela-div">

				</div>

				<div class="tab-pane fade" id="campoformulario-div">

				</div>

				<div class="tab-pane fade" id="tabelabd-div">

				</div>
				<div class="tab-pane fade" id="colunabd-div">

				</div>
				<div class="tab-pane fade" id="elementobd-div">

				</div>
				<div class="tab-pane fade" id="tipodadobd-div">

				</div>
			</div>
		</div>
	</section>
</div>

<%@ include file="/footer.jspf"%>
