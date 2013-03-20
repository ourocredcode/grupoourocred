<%@ include file="/header.jspf"%>

<script type="text/javascript">
jQuery(function($){
	
		$('#parceironegocio-li-a').click(function() {
			window.location.href = '<c:url value="/parceironegocio/cadastro" />';
		});
		
		$('#localidade-li-a').click(function() {
			window.location.href = '<c:url value="/localidade/cadastro" />';
		});
	
		$("#localidadeCep").change(function(){
			var enderecoCEP = $("#localidadeCep").val();
			$.ajax({
		           type: "POST",
		           url: "/sgo/localidade/busca.localidade",
		           data: "enderecoCEP=" + enderecoCEP,
		           beforeSend: function() {
		             $('#alertCEP').html('Processando...');
		           },
		           success: function(txt) {
		              if(txt!='ERRO'){
		            	  $('#ajax_endereco').html(txt);
		            	  $('#alertCEP').html('CEP Encontrado');
		              }else{
		                  $('#alertCEP').html('CEP Inexistente');
		              }
		           },
		           error: function(txt) {
		             alert('Houve um problema interno. tente novamente mais tarde.');
		           }
		       });
		});

});

function limpaForm(){
	if(!(navigator.userAgent.indexOf("Firefox") != -1)){
		document.usuarioForm.reset();
	}	
}
</script>


<div class="span9">

	<section id="tabs">

		<div class="bs-docs-example">

			<ul id="myTab" class="nav nav-tabs">
				<li class="" id="parceironegocio-li"><a href="#parceironegocio-div" data-toggle="tab" id="parceironegocio-li-a">Parceiro de Negócios</a></li>
				<li class="active" id="localidade-li"><a href="#localidade-div" data-toggle="tab" id="localidade-li-a">Endereço</a></li>
			</ul>
	
			<div id="myTabContent" class="tab-content">	
	
				<div class="tab-pane fade" id="parceironegocio-div"></div>

				<div class="tab-pane fade active in" id="localidade-div">
				
					<div id="ajax_endereco">

						<div class="controls controls-row">
							<input class="span2" id="localidadeCep" name="localidadeCep" type="text" placeholder="Busca Cep" />
						</div>
						<div id="alertCEP" style="position:absolute; float: right;width: 250px;margin-left: 180px;margin-top: -27px;"><i>Digite para buscar</i></div>

						<div class="controls controls-row">
							<input class="span7" id="localidadeEndereco" name="localidadeEndereco" type="text" placeholder="Endereço"/>
							<input class="span1" id="localidadeNumero" name="localidadeNumero" type="text" placeholder="Número"/>
						</div>

						<div class="controls controls-row">
							<input class="span3" id="localidadeComplemento" name="localidadeComplemento" type="text" placeholder="Complemento" />
							<input class="span2" id="localidadeBairro" name="localidadeBairro" type="text" placeholder="Bairro" />
							<input class="span2" id="localidadeCidade" name="localidadeCidade" type="text" placeholder="Cidade" />
							<input class="span1" id="localidadeRegiao" name="localidadeRegiao" type="text" placeholder="UF"  />
						</div>
		
					</div>
				
				</div>

			</div>

		</div>

	</section>

</div>

<%@ include file="/footer.jspf"%>
