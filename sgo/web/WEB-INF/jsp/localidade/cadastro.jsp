<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">

$(document).ready(function() { 

	$("#localidadeCep").mask("99999999");

	$("#localidadeCep").change(function(){
		var enderecoCEP = $("#localidadeCep").val();
		$.ajax({
	           type: "POST",
	           url: "/sgo/localidade/busca.localidade",
	           data: "enderecoCEP=" + enderecoCEP,
	           beforeSend: function() {
	             $('#alertCEP').html('');
	           },
	           success: function(txt) {
	              if(txt!='ERRO'){
	            	  $('#ajax_endereco').html(txt);
	            	  $('#alertCEP').html('');
	              }else{
	                  $('#alertCEP').html('');
	              }
	           },
	           error: function(txt) {
	             alert('Houve um problema interno. tente novamente mais tarde.');
	           }
	       });
	});

	$("#localidadeRegiaoId").change(function() {   

		var regiao_id = $("#localidadeRegiaoId").val();

		if(regiao_id != '')
			$("#localidadeCidadeId").load('<c:url value="/localidade/busca.cidades" />', {'regiao_id': regiao_id});

	});
	
	 $('#loading').ajaxStart(function() {
		 $(this).show();
		 $('#resultado').hide();
		 }).ajaxStop(function() {
		 $(this).hide();
		 $('#resultado').fadeIn('fast');
	});

});

</script>

<div id="ajax_endereco" style="display: block;width: 920px;">

	<div class="row-fluid">
		<div class="span3">
			<div class="input-append">
				<input class="span10" id="localidadeCep" name="localidade.cep" type="text" placeholder="Busca Cep" value="${localidade.cep }" />
				<span class="add-on"><i class="icon-search"></i></span>
			</div>
		</div>
		<div class="span2">
			<div id="loading" style="display:none;color:#1b5790; font-weight:bold;float:right;clear: both;margin-left: 600px;margin-top: 1px;">BUSCANDO...</div>
		</div>
	</div>

	<div class="row-fluid">

		<select id="localidadeTipoLocalidadeId" name="localidade.tipoLocalidade.tipoLocalidade_id" class="span2" >
			<option value="">Selecione</option>
			<c:forEach var="tipoLocalidade" items="${tiposLocalidade }">
				<option value="${tipoLocalidade.tipoLocalidade_id }">${tipoLocalidade.nome }</option>
			</c:forEach>
		</select>

		<input class="span5" id="localidadeEndereco" name="localidade.endereco" type="text" placeholder="Endere�o" value="${localidade.endereco }"/>

		<input class="span1" id="parceirolocalidadeNumero" name="parceiroLocalidade.numero" type="text" placeholder="N�mero"/>

		<input class="span2" id="localidadeBairro" name="localidade.bairro" type="text" placeholder="Bairro" value="${localidade.bairro }" />

		<select id="localidadeRegiaoId" name="localidade.regiao.regiao_id" class="span1" >
			<option value="">UF</option>
			<c:forEach var="regiao" items="${regioes }">
				<option value="${regiao.regiao_id }">${regiao.chave }</option>
			</c:forEach>
		</select>

		<select id="localidadeCidadeId" name="localidade.cidade.cidade_id" class="span2" >
			<option value="">Escolha UF..</option>
		</select>

	</div>

	<div id="alertCEP" style="position:absolute; float: right;width: 250px;margin-left: 100px;margin-top: -27px;"><i></i></div>

	<div class="row-fluid">		

		<input class="span2" id="parceirolocalidadeComplemento" name="parceiroLocalidade.complemento" type="text" placeholder="Complemento" />
		<input class="span9" id="parceirolocalidadePontoReferencia" name="parceiroLocalidade.pontoReferencia" type="text" placeholder="Ponto de Refer�ncia" />

		<input  id="localidadePaisId" name="localidade.pais.pais_id" type="hidden"  value="1" />
	</div>

</div>