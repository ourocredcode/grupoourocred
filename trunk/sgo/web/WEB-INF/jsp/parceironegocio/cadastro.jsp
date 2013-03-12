<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

});

function limpaForm(){
	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.usuarioForm.reset();
	}	
}
</script>

	<ul id="myTab" class="nav nav-tabs">
		<li class="active" id="parceiroNegocio-li"><a href="#parceiroNegocio-div" data-toggle="tab" id="parceiroNegocio-li-a">Cadastro Parceiro Negócio</a></li>
	</ul>

	<div id="myTabContent" class="tab-content">

		<div class="tab-pane fade active in" id="parceiroNegocio-div">		
				
				<div class="row25MarginTop">
					<div class="span3">

						<form id="parceiroNegocioForm" name="parceiroNegocioForm" action="<c:url value="/parceiroNegocio/salva"/>" method="POST">
							<div class="control-group">
								<label class="control-label" for="parceiroNegocioEmpresa">Empresa</label>
								<div class="input-prepend">
									<span class="add-on"><i class="icon-plus-sign"></i></span>
		      						<input class="span2" id="parceiroNegocioEmpresa" name="parceiroNegocio.empresa.nome" type="text" required onChange="limpaForm();">
		      						<input class="span2" id="parceiroNegocioEmpresaId" name="parceiroNegocio.empresa.empresa_id" type="hidden">
		    					</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="parceiroNegocioOrganizacao">Organização</label>
								<div class="input-prepend">
									<span class="add-on"><i class="icon-plus-sign"></i></span>
		      						<input class="span2" id="parceiroNegocioOrganizacao" name="parceiroNegocio.organizacao.nome" type="text" required onChange="limpaForm();">
		      						<input class="span2" id="parceiroNegocioOrganizacaoId" name="parceiroNegocio.organizacao.organizacao_id" type="hidden">
		    					</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="parceiroNegocioNome">Nome</label>
								<div class="controls">
									<input type="text" id="parceiroNegocioNome" name="parceiroNegocio.nome" placeholder="Nome do parceiro" required>
								</div>
							</div>

    						<label class="radio">
  								<input type="radio" name="optionsRadios" id="parceiroNegocioIs" value="funcionario" checked> Funcionário
							</label>
							<label class="radio">
  								<input type="radio" name="optionsRadios" id="parceiroNegocioIs" value="fornecedor"> Fornecedor
							</label>
							<label class="radio">
  								<input type="radio" name="optionsRadios" id="parceiroNegocioIs" value="cliente"> Cliente
							</label>

							<div class="btn-toolbar">
								<div class="btn-group">
									<button type="submit" class="btn btn-primary">Salvar</button>
								</div>	
							</div>
						</form>
		
					</div>
				</div>
		</div>

	</div>

<%@ include file="/footer.jspf"%>
