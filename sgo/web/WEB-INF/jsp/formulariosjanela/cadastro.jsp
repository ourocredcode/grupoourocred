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
	
	$('#formulariosjanela-li-a').click(function() {
		window.location.href = '<c:url value="/formulariosjanela/cadastro" />';
	});
	
	$('#perfiljanelaacesso-li-a').click(function() {
		window.location.href = '<c:url value="/perfiljanelaacesso/cadastro" />';
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
				<li class="" id="janela-li"><a href="#janela-div" data-toggle="tab" id="janela-li-a">Janela</a></li>
				<li class="" id="perfiljanelaacesso-li"><a href="#perfiljanelaacesso-div" data-toggle="tab" id="perfiljanelaacesso-li-a">Janela Perfil</a></li>
				<li class="active" id="formulariosjanela-li"><a href="#formulariosjanela-div" data-toggle="tab" id="formulariosjanela-li-a">Formulários Janela</a></li>
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
			
				<div class="tab-pane fade" id="janela-div">
					
				</div>
				
				<div class="tab-pane fade" id="perfiljanelaacesso-div">
					
				</div>
				
				<div class="tab-pane fade active in" id="formulariosjanela-div">
					
					<form id="formularioJanelaForm" name="formularioJanelaForm" action="<c:url value="/formulariojanela/salva"/>" method="POST">

						<div class="control-group">
							<label class="control-label" for="formularioJanelaEmpresa">Empresa</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="formularioJanelaEmpresa" name="formularioJanelaEmpresa.empresa.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="formularioJanelaEmpresaId" name="formularioJanelaEmpresa.empresa.empresa_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="formularioJanelaOrganizacao">Organização</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="formularioJanelaOrganizacao" name="formularioJanela.organizacao.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="formularioJanelaOrganizacaoId" name="formularioJanela.organizacao.organizacao_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="formularioJanelaJanela">Janela</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="formularioJanelaJanela" name="formularioJanela.janela.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="formularioJanelaJanelaId" name="formularioJanela.janela.janela_id" type="hidden">
	    					</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="formularioJanelaTabelaBd">Tabela Bd</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="formularioJanelaTabelaBd" name="formularioJanela.tabelabd.nometabelabd" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="formularioJanelaTabelaBdId" name="formularioJanela.tabelabd.tabelabd_id" type="hidden">
	    					</div>
						</div>						
						
						<div class="control-group">
							<label class="control-label" for="formularioJanelaChave">Chave</label>
							<div class="controls">
								<input type="text" id="formularioJanelaChave" name="formularioJanelaChave.chave" placeholder="Chave" required>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="formularioJanelaNome">Nome</label>
							<div class="controls">
								<input type="text" id="formularioJanelaNome" name="formularioJanelaNome.nome" placeholder="Nome" required>
							</div>
						</div>						
						<div class="control-group">
							<label class="control-label" for="formularioJanelaIsMostrado">Mostrado</label>
							<div class="controls">
								<input type="checkbox" id="formularioJanelaIsMostrado" name="formularioJanelaIsMostrado.ismostrado">							
							</div>							
						</div>
						<div class="control-group">
							<label class="control-label" for="formularioJanelaIsSomenteLeitura">Somente Leitura</label>
							<div class="controls">
								<input type="checkbox" id="formularioJanelaIsSomenteLeitura" name="formularioJanelaIsSomenteLeitura.issomenteleitura">							
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
