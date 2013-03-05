<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){
	
	$('#janela-li-a').click(function() {
		window.location.href = '<c:url value="/janela/cadastro" />';
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
				<li class="" id="janela-li"><a href="#janela-div" data-toggle="tab" id="janela-li-a">Janela</a></li>
				<li class="" id="formulariosjanela-li"><a href="#formulariosjanela-div" data-toggle="tab" id="formulariosjanela-li-a">Formul�rios Janela</a></li>
				<li class="active" id="campoformulario-li"><a href="#campoformulario-div" data-toggle="tab" id="campoformulario-li-a">Campo Formul�rio</a></li>
				<li class="" id="tabelabd-li"><a href="#tabelabd-div" data-toggle="tab" id="tabelabd-li-a">Tabela BD</a></li>
				<li class="" id="colunabd-li"><a href="#colunabd-div" data-toggle="tab" id="colunabd-li-a">Coluna BD</a></li>	
				<li class="" id="elementobd-li"><a href="#elementobd-div" data-toggle="tab" id="elementobd-li-a">Elemento BD</a></li>
				<li class="" id="tipodadobd-li"><a href="#tipodadobd-div" data-toggle="tab" id="tipodadobd-li-a">TipoDado BD</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">
			
				<div class="tab-pane fade" id="janela-div">
					
				</div>
				
				<div class="tab-pane fade" id="formulariosjanela-div">
					
				</div>

				<div class="tab-pane fade active in" id="campoformulario-div">

					<form id="campoFormularioForm" name="campoFormularioForm" action="<c:url value="/campoformulario/salva"/>" method="POST">

						<div class="control-group">
							<label class="control-label" for="tipoDadoBdEmpresa">Empresa</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="tipoDadoBdEmpresa" name="tipoDadoBd.empresa.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="tipoDadoBdEmpresaId" name="tipoDadoBd.empresa.empresa_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="tipoDadoBdOrganizacao">Organiza��o</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="tipoDadoBdOrganizacao" name="tipoDadoBd.organizacao.nome" type="text" required onChange="limpaForm();">
	      						<input class="span2" id="tipoDadoBdOrganizacaoId" name="tipoDadoBd.organizacao.organizacao_id" type="hidden">
	    					</div>
						</div>

						 <div class="btn-toolbar">
								<div class="btn-group">
									<button type="submit" class="btn btn-primary">Salvar</button>
								</div>	
							</div>
					</form>


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
