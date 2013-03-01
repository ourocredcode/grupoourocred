<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

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

});

function limpaCampo(campo,campoId){
	campo.value='';
	campoId.value='';
}


</script>

<div class="span9">

	<section id="tabs">
		<div class="bs-docs-example">

			<ul id="myTab" class="nav nav-tabs">
				<li class="" id="tabelabd-li"><a href="#" data-toggle="tab" id="tabelabd-li-a">Tabela BD</a></li>
				<li class="" id="colunabd-li"><a href="#colunabd-div" data-toggle="tab" id="colunabd-li-a">Coluna BD</a></li>	
				<li class="" id="elementobd-li"><a href="#elementobd-div" data-toggle="tab" id="elementobd-li-a">Elemento BD</a></li>
				<li class="active" id="tipodadobd-li"><a href="#tipodadobd-div" data-toggle="tab" id="tipodadobd-li-a">TipoDado BD</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade " id="tabelabd-div">

				</div>
				<div class="tab-pane fade " id="colunabd-div">

				</div>
				<div class="tab-pane fade" id="elementobd-div">

				</div>
				<div class="tab-pane fade active in" id="tipodadobd-div">
				
					<form id="tabelaBdForm" name="tabelaBdForm" action="<c:url value="/tabelabd/salva"/>" method="POST">
						<div class="control-group">
							<label class="control-label" for="tabelaBdEmpresa">Empresa</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="tabelaBdEmpresa" name="tabelaBd.empresa.nome" type="text" required onChange="limpaCampo(tabelaBdEmpresa,tabelaBdEmpresaId);">
	      						<input class="span2" id="tabelaBdEmpresaId" name="tabelaBd.empresa.empresa_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="tabelaBdOrganizacao">Organização</label>
							<div class="input-prepend">
								<span class="add-on"><i class="icon-plus-sign"></i></span>
	      						<input class="span2" id="tabelaBdOrganizacao" name="tabelaBd.organizacao.nome" type="text" required onChange="limpaCampo(tabelaBdOrganizacao,tabelaBdOrganizacaoId);">
	      						<input class="span2" id="tabelaBdOrganizacaoId" name="tabelaBd.organizacao.organizacao_id" type="hidden">
	    					</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="tabelaBd.nometabelabd">Tipo Dado BD</label>
							<div class="controls">
								<input type="text" id="tabelaBd.nometabelabd" name="tabelaBd.nometabelabd" placeholder="Nome do tipo de dado BD" required>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="tabelaBd.nome">Nome</label>
							<div class="controls">
								<input type="text" id="tabelaBd.nome" name="tabelaBd.nome" placeholder="Nome" required>
							</div>
						</div>
						
						 <div class="btn-toolbar">
								<div class="btn-group">
									<button type="submit" class="btn btn-primary">Salvar</button>
								</div>	
							</div>
					</form>

				</div>
			</div>
		</div>
	</section>
</div>

<%@ include file="/footer.jspf"%>
