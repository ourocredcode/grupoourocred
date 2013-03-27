<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="ajax_endereco" style="display: block;width: 1050px;">

	<div class="control-group"></div>
	<div class="page-header">
		<h1><small>Endereço</small></h1>
	</div>
	
	<div class="input-append">
		<input class="span2" id="localidadeCep" name="localidade.cep" type="text" placeholder="Busca Cep" value="${localidade.cep }" />
		<span class="add-on"><i class="icon-search"></i></span>
	</div>

	<div class="row-fluid">
		<input class="span5" id="localidadeEndereco" name="localidade.endereco" type="text" placeholder="Endereço" value="${localidade.endereco }"/>
		<input class="span1" id="parceirolocalidadeNumero" name="parceiroLocalidade.numero" type="text" placeholder="Número"/>
		<input class="span2" id="localidadeBairro" name="localidade.bairro" type="text" placeholder="Bairro" value="${localidade.bairro }" />
		<input class="span2" id="localidadeCidade" name="localidade.cidade" type="text" placeholder="Cidade" value="${localidade.cidade.nome }" />
		<input class="span1" id="localidadeRegiao" name="localidade.regiao" type="text" placeholder="UF"  value="${localidade.regiao.chave }"  />
	</div>

	<div id="alertCEP" style="position:absolute; float: right;width: 250px;margin-left: 100px;margin-top: -27px;"><i></i></div>

	<div class="row-fluid">		

		<input class="span2" id="parceirolocalidadeComplemento" name="parceiroLocalidade.complemento" type="text" placeholder="Complemento" />
		<input class="span9" id="parceirolocalidadePontoReferencia" name="parceiroLocalidade.pontoReferencia" type="text" placeholder="Ponto de Referência" />

		<input  id="localidadeCidadeId" name="localidade.cidade.cidade_id" type="hidden"  value="${localidade.cidade.cidade_id }" />
		<input  id="localidadeRegiaoId" name="localidade.regiao.regiao_id" type="hidden" value="${localidade.regiao.regiao_id }" />
		<input  id="localidadePaisId" name="localidade.pais.pais_id" type="hidden"  value="${localidade.pais.pais_id }" />
		<input  id="localidadeId" name="localidade.localidade_id" type="hidden"  value="${localidade.localidade_id }" />
	</div>

</div>