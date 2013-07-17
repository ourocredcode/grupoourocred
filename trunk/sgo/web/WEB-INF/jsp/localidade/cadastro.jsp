<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">

$(document).ready(function() { 

	$("#localidadeRegiaoId").change(function() {   

		var regiao_id = $("#localidadeRegiaoId").val();

		if(regiao_id != '')
			$("#localidadeCidadeId").load('<c:url value="/localidade/busca.cidades" />', {'regiao_id': regiao_id});

	});

});

</script>

<div id="ajax_endereco" style="display: block;width: 920px;">

	<div class="input-append">
		<input  class="span10" id="localidadeCep" name="localidade.cep" type="text" placeholder="Busca Cep" value="${localidade.cep }" />
		<span class="add-on"><i class="icon-search"></i></span>
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