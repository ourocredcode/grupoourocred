<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){

	$('#btnSair').click(function() {
		window.location.href = '<c:url value="/tabelabd/configuracao" />';
	});

}); 

</script>

<div class="span9">

	<section id="tabs">
		<div class="bs-docs-example">

			<ul id="myTab" class="nav nav-tabs">
				<li class="active" id="tabelabd-li"><a href="#tabelabd-div" data-toggle="tab" id="tabelabd-li-a">Tabela BD</a></li>
				<li class="" id="colunabd-li"><a href="#colunabd-div" data-toggle="tab" id="colunabd-li-a">Coluna BD</a></li>	
				<li class="" id="elementobd-li"><a href="#elementobd-div" data-toggle="tab" id="elementobd-li-a">Elemento BD</a></li>
				<li class="" id="tipodadobd-li"><a href="#tipodadobd-div" data-toggle="tab" id="tipodadobd-li-a">TipoDado BD</a></li>
			</ul>

			<div id="myTabContent" class="tab-content">

				<div class="tab-pane fade active in" id="tabelabd-div">
					
					<div class="control-group">
						<label class="control-label" for="tabelaBd.empresa">Empresa</label>
						<div class="input-prepend">
							<span class="add-on"><i class="icon-plus-sign"></i></span>
      						<input class="span2" id="tabelaBd.empresa" type="text">
    					</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="tabelaBd.organizacao">Organização</label>
						<div class="input-prepend">
							<span class="add-on"><i class="icon-plus-sign"></i></span>
      						<input class="span2" id="tabelaBd.organizacao" type="text">
    					</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="tabelaBd.nometabelabd">Nome Tabela BD</label>
						<div class="controls">
							<input type="text" id="tabelaBd.nometabelabd" name="tabelaBd.nometabelabd" placeholder="Nome da tabela BD" required>
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
							<div class="btn-group">
								<button type="button" class="btn btn-primary" id="btnSair" >Sair</button>
							</div>
						</div>
					
					
				</div>
				<div class="tab-pane fade active in" id="colunabd-div">


					
					

				</div>
				<div class="tab-pane fade active in" id="elementobd-div">


				
					

				</div>
				
				<div class="tab-pane fade active in" id="tipodadobd-div">

	
				
					

				</div>

			
			</div>
		</div>
	</section>
</div>

<%@ include file="/footer.jspf"%>
